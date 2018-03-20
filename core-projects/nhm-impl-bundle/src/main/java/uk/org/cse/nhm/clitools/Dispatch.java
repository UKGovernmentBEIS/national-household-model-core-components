package uk.org.cse.nhm.clitools;

import java.util.Arrays;

import uk.org.cse.nhm.clitools.bundle.NationalHouseholdModel;

/** The real main method */
public class Dispatch {
    private static final String[] USAGE = {
        "Usage:",
        "validate " + Validator.USAGE,
        "run " + Runner.USAGE,
        "run-snapshot " + SnapshotRunner.USAGE,
        "gen-snapshot " + SnapshotGenerator.USAGE,
        "import " + StockImporter.USAGE,
        "lang " + Helper.USAGE,
        "expand " + Expander.USAGE,
        "man " + Manual.USAGE,
        "version - get the model version"
    };
    
	public static void main(final String[] args) throws Exception {
        if (args.length > 0) {
            String subUsage = "";
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            try {
                switch (args[0]) {
                case "validate":
                    subUsage = Validator.USAGE;
                    Validator.main(subArgs);
                    return;
                case "run":
                    subUsage = Runner.USAGE;
                    Runner.main(subArgs);
                    return;
                case "run-snapshot":
                    subUsage = SnapshotRunner.USAGE;
                    SnapshotRunner.main(subArgs);
                    return;
                case "gen-snapshot":
                    subUsage = SnapshotGenerator.USAGE;
                    SnapshotGenerator.main(subArgs);
                    return;
                case "import":
                    subUsage = StockImporter.USAGE;
                    StockImporter.main(subArgs);
                    return;
                case "lang":
                    Helper.main(subArgs);
                    return;
                case "expand":
                    Expander.main(subArgs);
                    return;
                case "help":
                    break;
                case "man":
                    Manual.main(subArgs);
                    return;
                case "version":
                    System.out.println(NationalHouseholdModel.create().version());
                    return;
                }

            } catch (final IllegalArgumentException iae) {
                if (!iae.getMessage().isEmpty()) System.err.println(iae.getMessage());
                System.err.println(args[0] + " " + subUsage);
                return;
            }
        }

        for (final String l : USAGE) System.err.println(l);
	}
}
