package org.spoofax.asm;

import static org.spoofax.interpreter.core.Tools.hasConstructor;
import static org.spoofax.interpreter.core.Tools.isTermAppl;
import static org.spoofax.interpreter.core.Tools.javaInt;
import static org.spoofax.interpreter.core.Tools.javaIntAt;
import static org.spoofax.interpreter.core.Tools.javaString;
import static org.spoofax.interpreter.core.Tools.javaStringAt;
import static org.spoofax.interpreter.core.Tools.termAt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

public class ClassGenerator {
	public byte[] generateClass(Context context, IStrategoTerm term) {
		if (isTermAppl(term) && hasConstructor((IStrategoAppl) term, "ASMClass", 8)) {
			IStrategoTerm versionTerm = termAt(term, 0);
			IStrategoTerm flagsTerm = termAt(term, 1);
			IStrategoTerm nameTerm = termAt(term, 2);
			IStrategoTerm signatureTerm = termAt(term, 3);
			IStrategoTerm superNameTerm = termAt(term, 4);
			IStrategoTerm interfacesTerm = termAt(term, 5);
			IStrategoTerm fieldsTerm = termAt(term, 6);
			IStrategoTerm methodsTerm = termAt(term, 7);
			return generateClass(context, versionTerm, flagsTerm, nameTerm, signatureTerm, superNameTerm, interfacesTerm, fieldsTerm, methodsTerm);
		} else {
			throw new IllegalArgumentException("Invalid class term.");
		}
	}
	
	public byte[] generateClass(Context context, IStrategoTerm versionTerm,
			IStrategoTerm flagsTerm, IStrategoTerm nameTerm,
			IStrategoTerm signatureTerm, IStrategoTerm superNameTerm,
			IStrategoTerm interfacesTerm, IStrategoTerm fieldsTerm,
			IStrategoTerm methodsTerm) {
		int version = javaInt(versionTerm);
		int flags = javaInt(flagsTerm);
		String name = javaString(nameTerm);
		String signature = isNone(signatureTerm)
				? null
				: javaString(signatureTerm);
		String superName = isNone(superNameTerm)
				? null
				: javaString(superNameTerm);
		List<String> interfaces = new ArrayList<String>();
		IStrategoList interfacesList = (IStrategoList) interfacesTerm;
		for (int i = 0; i < interfacesList.getSubtermCount(); i++) {
			interfaces.add(javaString(termAt(interfacesList, i)));
		}
		IStrategoList fieldsList = (IStrategoList) fieldsTerm;
		IStrategoList methodsList = (IStrategoList) methodsTerm;
		
		ClassWriter cw = new ClassWriter(0);
		cw.visit(version, flags, name, signature, superName, interfaces.toArray(new String[0]));
		
		for (IStrategoTerm field : fieldsList.getAllSubterms()) {
			generateField(cw, context, field);
		}
		
		for (IStrategoTerm method : methodsList.getAllSubterms()) {
			generateMethod(cw, context, method);
		}
		
		cw.visitEnd();
		return cw.toByteArray();
	}
	

	private void generateField(ClassWriter cw, Context context,
			IStrategoTerm field) {
		if (isTermAppl(field) && hasConstructor((IStrategoAppl) field, "ASMField", 5)) {
			IStrategoTerm flagsTerm = termAt(field, 0);
			IStrategoTerm nameTerm = termAt(field, 1);
			IStrategoTerm descTerm = termAt(field, 2);
			IStrategoTerm signatureTerm = termAt(field, 3);
			IStrategoTerm initValueTerm = termAt(field, 4);
			
			generateField(cw, context, flagsTerm, nameTerm, descTerm, signatureTerm, initValueTerm);
		} else {
			throw new IllegalArgumentException("Invalid field term.");
		}
	}

	private void generateField(ClassWriter cw, Context context,
			IStrategoTerm flagsTerm, IStrategoTerm nameTerm,
			IStrategoTerm descTerm, IStrategoTerm signatureTerm,
			IStrategoTerm initValueTerm) {
		int flags = javaInt(flagsTerm);
		String name = javaString(nameTerm);
		String desc = javaString(descTerm);
		String signature = isNone(signatureTerm) ? null : javaString(signatureTerm);
		Object initValue = null;
		if (!isNone(initValueTerm)) {
			// TODO retrieve the initValue object.
		}
		
		cw.visitField(flags, name, desc, signature, initValue);
		cw.visitEnd();
	}
	
	private void generateMethod(ClassWriter cw, Context context,
			IStrategoTerm method) {
		if (isTermAppl(method) && hasConstructor((IStrategoAppl) method, "ASMMethod", 6)) {
			IStrategoTerm flagsTerm = termAt(method, 0);
			IStrategoTerm nameTerm = termAt(method, 1);
			IStrategoTerm descTerm = termAt(method, 2);
			IStrategoTerm signatureTerm = termAt(method, 3);
			IStrategoTerm exceptionsTerm = termAt(method, 4);
			IStrategoTerm codeTerm = termAt(method, 5);
			
			generateMethod(cw, context, flagsTerm, nameTerm, descTerm, signatureTerm, exceptionsTerm, codeTerm);
		} else {
			throw new IllegalArgumentException("Invalid method term.");
		}
	}

	private void generateMethod(ClassWriter cw, Context context,
			IStrategoTerm flagsTerm, IStrategoTerm nameTerm,
			IStrategoTerm descTerm, IStrategoTerm signatureTerm,
			IStrategoTerm exceptionsTerm, IStrategoTerm codeTerm) {
		int flags = javaInt(flagsTerm);
		String name = javaString(nameTerm);
		String desc = javaString(descTerm);
		String signature = isNone(signatureTerm) ? null : javaString(signatureTerm);
		List<String> exceptions = new ArrayList<String>();
		IStrategoList exceptionsList = (IStrategoList) exceptionsTerm;
		for (int i = 0; i < exceptionsList.getSubtermCount(); i++) {
			exceptions.add(javaString(termAt(exceptionsList, i)));
		}
		IStrategoList codeList = (IStrategoList) codeTerm;
		List<IStrategoTerm> instructions = new ArrayList<IStrategoTerm>();
		for (int i = 0; i < codeTerm.getSubtermCount(); i++) {
			instructions.add(termAt(codeList, i));
		}
		
		Map<String, Label> labels = new HashMap<String, Label>();
		MethodVisitor mv = cw.visitMethod(flags, name, desc, signature, exceptions.toArray(new String[0]));
		mv.visitCode();
		for (IStrategoTerm instrTerm : instructions) {
			generateInstruction(mv, context, labels, instrTerm);
		}
		mv.visitEnd();
		cw.visitEnd();
	}

	private void generateInstruction(MethodVisitor mv, Context context,
			Map<String, Label> labels, IStrategoTerm instrTerm) {
		if (isTermAppl(instrTerm)) {
			IStrategoAppl instrAppl = (IStrategoAppl) instrTerm;
			if (hasConstructor(instrAppl, "Maxs", 2)) {
				mv.visitMaxs(javaIntAt(instrAppl, 0), javaIntAt(instrAppl, 1));
			} else if (hasConstructor(instrAppl, "Label", 1)) {
				String labelName = javaStringAt(instrAppl, 0);
				mv.visitLabel(getOrAddLabel(labels, labelName));
			} else if (hasConstructor(instrAppl, "Insn", 1)) {
				mv.visitInsn(javaIntAt(instrAppl, 0));
			} else if (hasConstructor(instrAppl, "IntInsn", 2)) {
				mv.visitIntInsn(javaIntAt(instrAppl, 0), javaIntAt(instrAppl, 1));
			} else if (hasConstructor(instrAppl, "VarInsn", 2)) {
				mv.visitVarInsn(javaIntAt(instrAppl, 0), javaIntAt(instrAppl, 1));
			} else if (hasConstructor(instrAppl, "FieldInsn", 4)) {
				mv.visitFieldInsn(javaIntAt(instrAppl, 0), javaStringAt(instrAppl, 1), javaStringAt(instrAppl, 2), javaStringAt(instrAppl, 3));
			} else if (hasConstructor(instrAppl, "MethodInsn", 4)) {
				mv.visitMethodInsn(javaIntAt(instrAppl, 0), javaStringAt(instrAppl, 1), javaStringAt(instrAppl, 2), javaStringAt(instrAppl, 3));
			} else if (hasConstructor(instrAppl, "JumpInsn", 2)) {
				String labelName = javaStringAt(instrAppl, 1);
				mv.visitJumpInsn(javaIntAt(instrAppl, 0), getOrAddLabel(labels, labelName));
			} else if (hasConstructor(instrAppl, "IincInsn", 2)) {
				mv.visitIincInsn(javaIntAt(instrAppl, 0), javaIntAt(instrAppl, 1));
			} else {
				throw new IllegalArgumentException("Invalid instruction term.");
			}
		} else {
			throw new IllegalArgumentException("Invalid instruction term.");
		}
	}

	private Label getOrAddLabel(Map<String, Label> labels, String labelName) {
		Label l = labels.get(labelName);
		if (l == null) {
			l = new Label();
			labels.put(labelName, l);
		}
		return l;
	}

	private boolean isNone(IStrategoTerm term) {
		return isTermAppl(term) && hasConstructor((IStrategoAppl) term, "None", 0);
	}
}
