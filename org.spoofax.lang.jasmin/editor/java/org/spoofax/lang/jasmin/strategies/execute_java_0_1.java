package org.spoofax.lang.jasmin.strategies;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.Strategy;

/**
 * Example Java strategy implementation.
 *
 * This strategy can be used by editor services and can be called in Stratego modules by declaring it as an
 * external strategy as follows:
 *
 * <code>
 *  external java-strategy(|)
 * </code>
 *
 * @see InteropRegisterer This class registers java_strategy_0_0 for use.
 */
public class execute_java_0_1 extends Strategy {

    public static execute_java_0_1 instance = new execute_java_0_1();

    @Override public IStrategoTerm invoke(Context context, IStrategoTerm current, IStrategoTerm path) {

        // remember system streams
        PrintStream origOut = System.out;
        PrintStream origErr = System.err;

        // create temporary streams to collect output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));

        try {
            // load class
            File file = new File(((IStrategoString) path).stringValue());
            URLClassLoader loader = new URLClassLoader(new URL[] { file.toURI().toURL() });
            Class<?> cls = loader.loadClass(((IStrategoString) current).stringValue());

            // invoke main method
            Method method = cls.getDeclaredMethod("main", new Class[] { String[].class });
            method.invoke(null, (Object) new String[0]);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // reset system streams
        System.setErr(origErr);
        System.setOut(origOut);

        // return output collected in both temporary streams
        ITermFactory factory = context.getFactory();
        return factory.makeAppl(factory.makeConstructor("Output", 2), factory.makeString(out.toString()),
            factory.makeString(err.toString()));
    }
}
