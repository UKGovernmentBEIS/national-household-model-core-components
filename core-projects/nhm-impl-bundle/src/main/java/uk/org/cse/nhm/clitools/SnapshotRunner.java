package uk.org.cse.nhm.clitools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.org.cse.nhm.bundle.api.IRunInformation;
import uk.org.cse.nhm.bundle.api.ISimulationCallback;
import uk.org.cse.nhm.bundle.api.IValidationResult;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;
import uk.org.cse.nhm.clitools.bundle.NationalHouseholdModel;

public class SnapshotRunner {
    public static final String USAGE = "<snapshot to run> [<stock-name> <path-to-stock>]+\n\tRuns the given scenario snapshot, generating a report in the results.zip file in the same directory as the snapshot. Any stock whose name is supplied name will be read from the path following the name. Snapshot path and stock paths can be relative to working directory or absolute.";

    public static void main(final String[] args) throws IOException {
        final NationalHouseholdModel nhm = NationalHouseholdModel.create();

        if (args.length < 3) throw new IllegalArgumentException(USAGE);
        
        final Path path = Paths.get(args[0]).toAbsolutePath();
        final Path output = path.getParent().resolve("results.zip");

        final Map<String, Path> stocks = new HashMap<String, Path>();
        for (int i = 1; i<args.length; i+=2) {
            if (i + 1 < args.length) {
                stocks.put(args[i], Paths.get(args[i+1]));
            } else {
                System.err.println("No stock supplied for key " + args[i]);
            }
        }

        final String snapshot = new String(Files.readAllBytes(path),
                                           StandardCharsets.UTF_8);
        try {
            System.err.println("NHM START");
            nhm.simulate(PathFS.INSTANCE, new IRunInformation<Path>() {
                    @Override
                    public Map<String, Path> stocks() {
                        return stocks;
                    }
                    @Override
                    public List<String> snapshots() {
                        return Collections.singletonList(snapshot);
                    }
                    @Override
                    public boolean isBatch() {
                        throw new RuntimeException("isBatch should not be called here - it's unknown!");
                    }
                },
                new ISimulationCallback<String>() {
                    @Override
                    public void log(final String source, final String message) {
                        System.err.println(String.format("NHM LOG [%s] %s", source, message));
                    }

                    @Override
                    public void progress(final double proportion) {
                        System.err.println(String.format("NHM PROGRESS %f", proportion));
                    }

                    @Override
                    public void completed(final Path pathToResult) {
                        // move file to right place
                        try {
                            Files.move(pathToResult, output);
                        } catch (final IOException ex) {
                            System.err.println("NHM IO ERROR " + ex.getMessage());
                            throw new RuntimeException(ex.getMessage(), ex);
                        }
                        System.err.println("NHM RUN SUCCESS " + output);
                    }

                    @Override
                    public void invalid(final IValidationResult<String> validation) {
                        for (final IValidationProblem<String> problem : validation.problems()) {
                            log("NHM VALIDATION-" + String.valueOf(problem.level()).toUpperCase(),
                                problem.message());
                        }
                        System.err.println("NHM RUN INVALID");
                    }
                    @Override
                    public void failed() {
                        System.err.println("NHM RUN FAILED");
                    }

                    @Override
                    public void cancelled() {
                        System.err.println("NHM RUN CANCELLED");
                    }

                    @Override
                    public boolean shouldCancel() {
                        return false;
                    }
                });
        } finally {
            System.err.println("NHM STOP");
        }
	}
}
