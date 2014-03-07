package org.spoofax.asm;

import static org.spoofax.interpreter.core.Tools.asJavaString;
import static org.spoofax.interpreter.core.Tools.hasConstructor;
import static org.spoofax.interpreter.core.Tools.isTermAppl;
import static org.spoofax.interpreter.core.Tools.isTermString;
import static org.spoofax.interpreter.core.Tools.termAt;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

public class FileGenerator {
	private static final String FILE_CONS = "JBCFile";
	private static final int FILE_ARITY = 3;
	private static final String HEADER_CONS = "JBCHeader";
	private static final int HEADER_ARITY = 8;

	public byte[] generateClass(Context context, IStrategoTerm file) {
		if (isTermAppl(file)) {
			IStrategoAppl appl = (IStrategoAppl) file;
			
			if (hasConstructor(appl, FILE_CONS, FILE_ARITY)) {
				IStrategoTerm header = Tools.termAt(appl, 0);
				IStrategoTerm fields = Tools.termAt(appl, 1);
				IStrategoTerm methods = Tools.termAt(appl, 2);
				
				return generateClass(context, header, fields, methods);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public byte[] generateClass(Context context, IStrategoTerm header,
			IStrategoTerm fields, IStrategoTerm methods) {
		if (isTermAppl(header)) {
			IStrategoAppl applHeader = (IStrategoAppl) header;
			
			if (hasConstructor(applHeader, HEADER_CONS, HEADER_ARITY)) {
				IStrategoTerm bytecodeSpec = termAt(applHeader, 0);
				IStrategoTerm sourceSpec = termAt(applHeader, 1);
				IStrategoTerm classSpec = termAt(applHeader, 2);
				IStrategoTerm superSpec  = termAt(applHeader, 3);
				IStrategoTerm implementsSpec = termAt(applHeader, 4);
				IStrategoTerm signatureSpec = termAt(applHeader, 5);
				IStrategoTerm enclosingSpec = termAt(applHeader, 6);
				IStrategoTerm deprecatedSpec = termAt(applHeader, 7);

				ClassWriter cw = new ClassWriter(0);
				
				int version = Opcodes.V1_7;
				int flags = 0;
				String name = getClassName(classSpec);
				String signature = null;
				String zuper = getSuperName(superSpec);
				String[] implementz = null;
				
				cw.visit(version, flags, name, signature, zuper, implementz);
				
				visitFields(cw, context, fields);
				
				cw.visitEnd();
				return cw.toByteArray();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void visitFields(ClassWriter cw, Context context,
			IStrategoTerm fields) {
		if (Tools.isTermList(fields)) {
			IStrategoList list = (IStrategoList) fields;
			
			for (IStrategoTerm field : list) {
				visitField(cw, context, field);
			}
		} else {
			throw new IllegalArgumentException("Invalid FieldSpecs, not a list.");
		}
	}
	
	public void visitField(ClassWriter cw, Context context,
			IStrategoTerm field) {
		if (Tools.isTermAppl(field)) {
			IStrategoAppl appl = (IStrategoAppl) field;
			
			if (hasConstructor(appl, "JBCField", 5)) {
				IStrategoTerm flags = termAt(appl, 0);
				IStrategoTerm name = termAt(appl, 1);
				IStrategoTerm desc = termAt(appl, 2);
				IStrategoTerm sig = termAt(appl, 3);
				IStrategoTerm initial = termAt(appl, 4);
				
				visitField(cw, context, flags, name, desc, sig, initial);
			} else {
				throw new IllegalArgumentException("Invalid FieldSpec.");
			}
		} else {
			throw new IllegalArgumentException("Invalid FieldSpec.");
		}
	}


	private void visitField(ClassWriter cw, Context context,
			IStrategoTerm flags, IStrategoTerm nameTerm, IStrategoTerm descTerm,
			IStrategoTerm sigTerm, IStrategoTerm initial) {
		int access = 0;
		String name = "";
		String descr = "";
		String sig = null;
		Object initValue = null;
		
		/*FieldVisitor fv =*/ cw.visitField(access, name, descr, sig, initValue);		
	}

	public String getClassName(IStrategoTerm classSpec) {
		if (isTermAppl(classSpec)) {
			IStrategoAppl appl = (IStrategoAppl) classSpec;
			
			if (hasConstructor(appl, "JBCClass", 2) || hasConstructor(appl, "JBCInterface", 2)) {
				IStrategoTerm name = termAt(appl, 1);
				
				if (isTermString(name)) {
					return asJavaString(name);
				}
			}
		}
		throw new IllegalArgumentException("Invalid ClassSpec.");
	}
	
	private String getSuperName(IStrategoTerm superSpec) {
		if (isTermAppl(superSpec)) {
			IStrategoAppl appl = (IStrategoAppl) superSpec;
			
			if (hasConstructor(appl, "JBCSuper", 1)) {
				IStrategoTerm name = termAt(appl, 0);
				
				if (isTermString(name)) {
					return asJavaString(name);
				}
			} else if (hasConstructor(appl, "None", 0)) {
				return null;
			}
		}
		throw new IllegalArgumentException("Invalid SuperSpec.");
	}
}
