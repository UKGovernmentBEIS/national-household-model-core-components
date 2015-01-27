package uk.ac.ucl.hideem;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.file.Paths;
import com.google.common.collect.*;

public class HealthModule implements IHealthModule {
	private final ListMultimap<String, Exposure> exposures;
    //private final double[][] healthCoefficients;
    
    public HealthModule() {
        // read a csv table from the classpath.

        // There are about 8 million CSV libraries for Java, all of
        // which require some awful enterprise factory bean
        // configurator pattern. I have instead taken the liberty of
        // writing a trivial one which is in the CSV.java file.

        // let's say we are reading data.csv, which has three columns,
        // one with built form and the other two with numbers, and we
        // want to make a mapping from built form to the two
        // numbers. One way of doing this is to use an array indexed
        // on built form ordinals and then on the other column. A more
        // complex thing would require some better structure.

        exposures = ArrayListMultimap.create();

        System.out.println("Reading exposures from: src/main/resources/uk/ac/ucl/hideem/NHM_input_radon_141106.csv");

        try (final CSV.Reader reader = CSV.trimmedReader(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("NHM_input_radon_141106.csv"))))) {
           String[] row = reader.read(); // throw away header line, because we know what it is
           
           while ((row = reader.read()) != null) {
        	   final Exposure e = Exposure.readExposure(row);
        	   exposures.put(row[0], e);
           }
        } catch (final IOException ex) {
                    // problem?
        }   
        
/*        for (final Map<String, String> row : CSV.mapReader(Paths.get("src/main/resources/uk/ac/ucl/hideem/NHM_input_radon_141106.csv"))) {
            final Exposure e = Exposure.readExposure(row);
            exposures.put(row.get("Exposure"), e);
        }   */ 	
    	
        
    	/*healthCoefficients = new double[BuiltForm.values().length][2];
        
        try (final CSV.Reader reader = CSV.trimmedReader(
                 new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("data.csv"))))) {
            String[] row = reader.read(); // throw away header line, because we know what it is
            
            while ((row = reader.read()) != null) {
                // this is just indicative; it is not good.
                final BuiltForm b = Enum.valueOf(BuiltForm.class, row[0]);
                healthCoefficients[b.ordinal()][0] = Double.parseDouble(row[1]);
                healthCoefficients[b.ordinal()][1] = Double.parseDouble(row[2]);
            }
        } catch (final IOException ex) {
            // problem?
        }*/
    }

    public HealthOutcome effectOf(
        // e-values & perm.s
        double e1,
        double e2,
        double p1,
        double p2,
        // case number constituents
        BuiltForm form,
        double floorArea,
        int mainFloorLevel, // fdfmainn (for flats)
        // for vtype:
        int buildYear,
        // finkxtwk and finbxtwk
        boolean hasWorkingExtractorFans, // per finwhatever
        boolean hasTrickleVents,         // this is cooked up elsewhere
        int numberOfFansAndPassiveVents, // per SAP
        // who
        List<Person> people,
        int horizon) {
        
        final HealthOutcome result = new HealthOutcome(horizon);
        
        //Get the exposure
        for(Map.Entry<String, Exposure> e: exposures.entries()) {
        	System.out.println(e.getValue().b0);
        }
        
        
        //result.setInitialExposure(Radon, 22);
        
        // health calculation goes here. Probably be good to sanity check the inputs.
        //result.setQalys(Disease.Cardiovascular, 0, this.healthCoefficients[form.ordinal()][0] * floorArea);
        //result.setQalys(Disease.Cardiopulmonary, 0, this.healthCoefficients[form.ordinal()][1] * Math.pow(floorArea, 2));
        
        return result;
    }
}
