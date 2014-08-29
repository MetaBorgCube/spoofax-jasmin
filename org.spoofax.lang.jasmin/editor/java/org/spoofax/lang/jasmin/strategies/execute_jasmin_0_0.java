package org.spoofax.lang.jasmin.strategies;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.Strategy;

/**
 * Example Java strategy implementation.
 *
 * This strategy can be used by editor services and can be called
 * in Stratego modules by declaring it as an external strategy
 * as follows:
 *
 * <code>
 *  external java-strategy(|)
 * </code>
 *
 * @see InteropRegisterer  This class registers java_strategy_0_0 for use.
 */
public class execute_jasmin_0_0 extends Strategy {

	public static execute_jasmin_0_0 instance = new execute_jasmin_0_0();

	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm current) {
		context.getIOAgent().printError(Boolean.toString(current.getTermType() == IStrategoTerm.APPL));
		context.getIOAgent().printError(((IStrategoAppl) current).getConstructor().getName());
		return null;
		
//		fname = "/Users/guwac/Documents/EclipseWorkspaces/IN4303/JasminXT/test/HelloWorld.j" ;
//		cpath = "/Users/guwac/Documents/EclipseWorkspaces/IN4303/JasminXT/test/";
//		
//		File out_file = null;
//        FileOutputStream outp = null;
//        File file = new File(fname);
//        ClassFile classFile = new ClassFile();
//        String iocause = fname + ": file not found";
//
//        try {
//            BufferedReader inp;
//            
//              FileInputStream fs = new FileInputStream(fname);
//              InputStreamReader ir;
//              ir = new InputStreamReader(fs);
//              inp = new BufferedReader(ir);
//            
//            classFile.readJasmin(inp, file.getName(), false);
//            inp.close();
//
//            // if we got some errors, don't output a file - just return.
//            if (classFile.errorCount() > 0) {
//                System.err.println(fname + ": Found "
//                                    + classFile.errorCount() + " errors");
//                return null;
//            }
//
//            String class_path[] = (ScannerUtils.splitClassField(
//                                                classFile.getClassName()));
//            String class_name = class_path[1];
//
//            // determine where to place this class file
//            String dest_dir = cpath;
//            if (class_path[0] != null) {
//                String class_dir = ScannerUtils.convertChars(
//                                           class_path[0], "./",
//                                           File.separatorChar);
//                if (dest_dir != null) {
//                    dest_dir = dest_dir + File.separator + class_dir;
//                } else {
//                    dest_dir = class_dir;
//                }
//            }
//            iocause = class_name + ".class: file can't be created";
//            if (dest_dir == null) {
//                out_file = new File(class_name + ".class");
//            } else {
//                out_file = new File(dest_dir, class_name + ".class");
//
//                // check that dest_dir exists
//
//                File dest = new File(dest_dir);
//                if (!dest.exists()) {
//                    dest.mkdirs();
//                }
//
//                if (!dest.isDirectory()) {
//                    throw new IOException("Cannot create directory");
//                }
//            }
//
//            outp = new FileOutputStream(out_file);
//            classFile.write(outp);
//            outp.close();
//            outp = null; // as marker
//            System.out.println("Generated: " + out_file.getPath());
//
//        } catch (java.io.FileNotFoundException e) {
//            System.err.println(iocause);
//            System.exit(-1);
//        } catch (jasError e) {
//            classFile.report_error("JAS Error: " + e.getMessage(), e.numTag);
//        } catch (Exception e) {
//            if(DEBUG)
//                e.printStackTrace();
//            classFile.report_error(fname + ": exception - <" +
//                              e.getClass().getName() + "> " + e.getMessage() +
//                              ".");
//        }
//        if (classFile.errorCount() > 0) {
//            System.err.println(fname + ": Found "
//                               + classFile.errorCount() + " errors");
//            if (outp != null) {
//                try {
//                  outp.close();
//                  out_file.delete();
//                } catch(Exception e) {}
//            }
//        }

//		return current;
	}
}
