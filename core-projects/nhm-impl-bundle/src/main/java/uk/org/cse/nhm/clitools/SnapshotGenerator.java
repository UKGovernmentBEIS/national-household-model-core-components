package uk.org.cse.nhm.clitools;

import uk.org.cse.nhm.clitools.bundle.NationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.*;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.io.*;

public class SnapshotGenerator {
    public static final String USAGE = "<scenario to snapshot> <output snapshot> <output stock list>\n\tProduces a snapshot which contains all includes of the given scenario, and a list of stock names to absolute stock file paths.";
    public static void main(String[] args) throws Exception {
        final NationalHouseholdModel nhm = NationalHouseholdModel.create();

        if (args.length != 3) throw new IllegalArgumentException(USAGE);

        final Path scenario = Paths.get(args[0]);
        final Path stocklist = Paths.get(args[2]);

        final IRunInformation<Path> runInformation = nhm.getRunInformation(PathFS.INSTANCE, scenario);

        int index = 1;
        for (final String s : runInformation.snapshots()) {
            final Path snapshot = Paths.get(args[1] + "-" + index++);
            Files.write(snapshot, s.getBytes(StandardCharsets.UTF_8));
        }

        final Properties p = new Properties();
        for (final String s : runInformation.stocks().keySet()) {
            p.put(s, String.valueOf(runInformation.stocks().get(s)));
        }

        try (final BufferedWriter o = Files.newBufferedWriter(stocklist, StandardCharsets.UTF_8)) {
            p.store(o, "Stocks for " + scenario);
        }
    }
}
