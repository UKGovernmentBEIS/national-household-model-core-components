package uk.org.cse.boilermatcher.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import uk.org.cse.boilermatcher.lucene.IBoilerTableEntry;
import uk.org.cse.boilermatcher.lucene.ISedbukIndex;
import uk.org.cse.boilermatcher.lucene.LuceneSedbukIndex;
import uk.org.cse.boilermatcher.lucene.SedbukFix;
import uk.org.cse.boilermatcher.lucene.SedbukIndexCache;
import uk.org.cse.boilermatcher.sedbuk.Sedbuk;
import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;

public class BoilerMatcher {
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			usage();
		}
		
		final String inputFile = args[0];
		final String outputFile = args[1];
		
		final Path inputPath = Paths.get(inputFile);
		final Path outputPath = Paths.get(outputFile);
		
		if (!Files.isRegularFile(inputPath)) {
			System.err.println(inputPath + " is not a regular file");
			System.exit(2);
		}
		
		if (Files.exists(outputPath) && !Files.isRegularFile(outputPath)) {
			System.err.println(outputPath + " is not a regular file");
			System.exit(3);
		}
		
		final Sedbuk sedbuk = new Sedbuk();
		final ISedbukIndex index = new SedbukIndexCache(new LuceneSedbukIndex(sedbuk));
		final SedbukFix fix = new SedbukFix(sedbuk);
		
		try (final CSV.Reader input = CSV.reader(inputPath, true);
			 final CSV.Writer output = CSV.writer(Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8))) {
			output.write(
					"aacode",
                    "manufacturer",
                    "brand",
                    "model",
                    "qualifier",
                    "boilertype",
                    "fueltype",
                    "fluetype",
                    "condensing",
                    "annualefficiency",
                    "winterefficiency",
                    "summerefficiency",
                    "storeboilervolume",
                    "storesolarvolume",
                    "storeinsulationthickness",
                    "row"
                    );
			
			for (final Map<String, String> row : input.maps()) {
				IBoilerTableEntry sedbukMatch = null;
				
				String brand = row.get("make");
				String model = row.get("model");
				FuelType fuelType 		= null;
				FlueType flueType 		= null;
				BoilerType boilerType 	= null;
				
				try {
					fuelType = FuelType.valueOf(row.get("fuel"));
				} catch (final Exception ex) {
				}
				try {
					flueType = FlueType.valueOf(row.get("flue"));
				} catch (final Exception ex) {
				}
				try {
					boilerType = BoilerType.valueOf(row.get("type"));
				} catch (final Exception ex) {
				}
				
				sedbukMatch = index.find(brand, model, fuelType, flueType, boilerType);
				
	            if (sedbukMatch == null) {
	                sedbukMatch = fix.find(brand, model);
	            }
	            
	            if (sedbukMatch != null) {
	                output.write(row.get("aacode"),
	                             String.valueOf(sedbukMatch.getManufacturer()),
	                             String.valueOf(sedbukMatch.getBrand()),
	                             String.valueOf(sedbukMatch.getModel()),
	                             String.valueOf(sedbukMatch.getQualifier()),
	                             String.valueOf(sedbukMatch.getBoilerType()),
	                             String.valueOf(sedbukMatch.getFuelType()),
	                             String.valueOf(sedbukMatch.getFlueType()),
	                             String.valueOf(sedbukMatch.isCondensing()),
	                             String.valueOf(sedbukMatch.getAnnualEfficiency()),
	                             String.valueOf(sedbukMatch.getWinterEfficiency()),
	                             String.valueOf(sedbukMatch.getSummerEfficiency()),
	                             String.valueOf(sedbukMatch.getStoreBoilerVolume()),
	                             String.valueOf(sedbukMatch.getStoreSolarVolume()),
	                             String.valueOf(sedbukMatch.getStoreInsulationThickness()),
	                             String.valueOf(sedbukMatch.getRow()));
	            }
            }
		} catch (final IOException exception) {
			System.err.println("IO Exception: " + exception.getMessage());
			exception.printStackTrace();
			System.exit(4);
		}
	}

	private static void usage() {
		System.out.println("usage: java -jar boilermatcher.jar <input file> <output file>");
		System.out.println("input file columns:");
		System.out.println("\taacode - row ID");
		System.out.println("\tmake - manufacturer text");
		System.out.println("\tmodel - model number");
		System.out.println("\tfuel - MAINS_GAS | BULK_LPG | OIL | <empty>");
		System.out.println("\tflue - FAN_ASSISTED_BALANCED_FLUE | OPEN_FLUE | BALANCED_FLUE | <empty>");
		System.out.println("\ttype - REGULAR | INSTANT_COMBI | STORAGE_COMBI | CPSU | UNKNOWN | <empty>");
		System.exit(1);
	}
}
