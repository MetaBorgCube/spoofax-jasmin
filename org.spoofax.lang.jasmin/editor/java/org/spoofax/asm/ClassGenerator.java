package org.spoofax.asm;

import static org.spoofax.interpreter.core.Tools.hasConstructor;
import static org.spoofax.interpreter.core.Tools.isTermAppl;
import static org.spoofax.interpreter.core.Tools.javaInt;
import static org.spoofax.interpreter.core.Tools.javaIntAt;
import static org.spoofax.interpreter.core.Tools.javaString;
import static org.spoofax.interpreter.core.Tools.termAt;

import java.util.List;
import java.util.ArrayList;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

public class ClassGenerator {
	public byte[] generateClass(Context context, IStrategoTerm term) {
		if (isTermAppl(term) && hasConstructor((IStrategoAppl) term, "ASMClass", 7)) {
			IStrategoTerm versionTerm = termAt(term, 0);
			IStrategoTerm flagsTerm = termAt(term, 1);
			IStrategoTerm nameTerm = termAt(term, 2);
			IStrategoTerm signatureTerm = termAt(term, 3);
			IStrategoTerm superNameTerm = termAt(term, 4);
			IStrategoTerm interfacesTerm = termAt(term, 5);
			IStrategoTerm fieldsTerm = termAt(term, 6);
			return generateClass(context, versionTerm, flagsTerm, nameTerm, signatureTerm, superNameTerm, interfacesTerm, fieldsTerm);
		} else {
			throw new IllegalArgumentException("Invalid class term.");
		}
	}
	
	public byte[] generateClass(Context context, IStrategoTerm versionTerm,
			IStrategoTerm flagsTerm, IStrategoTerm nameTerm,
			IStrategoTerm signatureTerm, IStrategoTerm superNameTerm,
			IStrategoTerm interfacesTerm, IStrategoTerm fieldsTerm) {
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
		
		ClassWriter cw = new ClassWriter(0);
		cw.visit(version, flags, name, signature, superName, interfaces.toArray(new String[0]));
		
		for (IStrategoTerm field : fieldsList.getAllSubterms()) {
			generateField(cw, context, field);
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

	private boolean isNone(IStrategoTerm term) {
		return isTermAppl(term) && hasConstructor((IStrategoAppl) term, "None", 0);
	}
}
