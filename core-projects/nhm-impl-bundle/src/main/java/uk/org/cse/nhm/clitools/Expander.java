package uk.org.cse.nhm.clitools;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;

import com.larkery.jasb.sexp.BetterPrinter;

public class Expander {
    public static final String USAGE = "<scenario>\n\tProduces the fully template-expanded form of the scenario, which is what the model really runs.";
    public static void main(String[] args) {
        Validator.disableLogging();

        if (args.length < 1) {
            throw new IllegalArgumentException(USAGE);
        }

        final Path file = Paths.get(args[0]);
    	final Path base = args.length  == 2 ? Paths.get(args[1]).toAbsolutePath() :
    		file.toAbsolutePath().getParent();

        if (!Files.exists(file)) {
            throw new IllegalArgumentException(file + " not found");
        }

        if (!Files.exists(base)) {
            throw new IllegalArgumentException(base + " not found");
        }

        expand(file, base);
    }

    private static void expand(final Path path, final Path base) {
        final IScenarioSnapshot snapshot = Util.loadSnapshot(path, base);
        System.out.println(BetterPrinter.print(snapshot, 1));
    }
}

