package jasmin.strategies;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
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

            // For some reason, without this call waitFor sometimes hangs?
            process.exitValue();

            String out = IOUtils.toString(process.getInputStream());
            String err = IOUtils.toString(process.getErrorStream());

            return factory.makeAppl(
                factory.makeConstructor("Output", 2),
                factory.makeString(out),
                factory.makeString(err)
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return factory.makeAppl(
            factory.makeConstructor("Output", 2),
            factory.makeString("An error occurred while executing java"),
            factory.makeString("An error occurred while executing java")
        );
    }
}
