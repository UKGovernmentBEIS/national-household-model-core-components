package uk.org.cse.nhm.clitools;

import uk.org.cse.nhm.clitools.bundle.NationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.*;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class Runner {
    public static final String USAGE = "<scenario to run> <output zip file>\n\tRuns the given scenario, generating a report in the output zip file.";

    public static void main(final String[] args) throws IOException {
        final NationalHouseholdModel nhm = NationalHouseholdModel.create();

        if (args.length != 2) throw new IllegalArgumentException(USAGE);
        
		final Path path = Paths.get(args[0]);
		final Path output = Paths.get(args[1]);

        nhm.simulate(PathFS.INSTANCE, path, new ISimulationCallback<Path>() {
                @Override
                public void log(final String source, final String message) {
                    System.err.println(String.format("[%s] %s", source, message));
                }

                @Override
                public void progress(final double proportion) {
                    System.out.println(String.format("PROGRESS %f", proportion));
                }
                
                @Override
                public void completed(final Path pathToResult) {
                    System.out.println("COMPLETE");
                    // move file to right place
                    try {
                        Files.move(pathToResult, output);
                    } catch (final IOException ex) {
                        throw new RuntimeException(ex.getMessage(), ex);
                    }
                }

                @Override
                public void invalid(final IValidationResult<Path> validation) {
                    for (final IValidationProblem<Path> problem : validation.problems()) {
                        log("VALIDATION-" + String.valueOf(problem.level()).toUpperCase(),
                            problem.message());
                    }
                    System.out.println("INVALID");
                }
                @Override
                public void failed() {
                    System.out.println("FAILED");
                }
                
                @Override
                public void cancelled() {
                    System.out.println("CANCELLED");
                }
                
                @Override
                public boolean shouldCancel() {
                    return false;
                }
            });
	}
}
