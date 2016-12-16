package jasmin.strategies;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.Strategy;

public class execute_java_0_1 extends Strategy {
    public static execute_java_0_1 instance = new execute_java_0_1();


    @Override public IStrategoTerm invoke(Context context, IStrategoTerm current, IStrategoTerm path) {
        ITermFactory factory = context.getFactory();

        try {
            Process process = new ProcessBuilder("java", "-cp", ((IStrategoString) path).stringValue(), ((IStrategoString) current).stringValue()).start();
            process.waitFor(10, TimeUnit.SECONDS);

            // For some reason, without this call waitFor sometimes hangs?
            process.exitValue();

            String out = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            String err = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);

            return factory.makeAppl(
                factory.makeConstructor("Output", 2),
                factory.makeString(out),
                factory.makeString(err)
            );
        } catch (IOException e) {
            e.printStackTrace();

            return factory.makeAppl(
                factory.makeConstructor("Output", 2),
                factory.makeString("An IOException occurred while executing java: " + e.getMessage()),
                factory.makeString("An IOException occurred while executing java: " + e.getMessage())
            );
        } catch (InterruptedException e) {
             e.printStackTrace();

            return factory.makeAppl(
                factory.makeConstructor("Output", 2),
                factory.makeString("An InterruptedException occurred while executing java: " + e.getMessage()),
                factory.makeString("An InterruptedException occurred while executing java: " + e.getMessage())
            );
        }
    }
}
