package uk.ac.ucl.hideem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.io.PrintWriter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;


public class Main
{
    
    public static void main( final String[] args ) throws IOException
    {
        final HealthModule module = new HealthModule();

        // load the people into here; keyed on house ID
        // format expected to be:
        // code, age, sex, smoker
        final ListMultimap<String, Person> people = ArrayListMultimap.create();

        System.out.println("Reading people from " + args[0]);

        for (final Map<String, String> row : CSV.mapReader(Files.newBufferedReader(Paths.get(args[0]), StandardCharsets.UTF_8))) {
            final Person p = Person.readPerson(row);
            people.put(row.get("code"), p);
        }

        // now process the houses
        System.out.println("Reading house data from " + args[1]);
               
        try (final PrintWriter exposuresOut = new PrintWriter(args[2]);
        		final PrintWriter qalysOut = new PrintWriter(args[3]);
        		final PrintWriter morbQalysOut = new PrintWriter(args[4]);
        		final PrintWriter costsOut = new PrintWriter(args[5]);)
        		{
			        
			        for (final Map<String, String> row : CSV.mapReader(Files.newBufferedReader(Paths.get(args[1]), StandardCharsets.UTF_8))) {
			        	// Should get the totals here
			        	
			        	final double e1 = Double.parseDouble(row.get("e1"));
			            final double e2 = Double.parseDouble(row.get("e2"));
			            
                        final HealthOutcome outcome = module.effectOf(
                            CumulativeHealthOutcome.factory(Integer.parseInt(row.get("horizon"))),
			                // get fields from row here
			                module.getInternalTemperature(e1, 1),
			                module.getInternalTemperature(e2, 1),
                            Double.parseDouble(row.get("e1")),
                            Double.parseDouble(row.get("e2")),
			                Double.parseDouble(row.get("p1")),
                            Double.parseDouble(row.get("p2")),
                            BuiltForm.Type.valueOf(row.get("form")),
			                Double.parseDouble(row.get("floor_area")),
                            BuiltForm.Region.Wales,
			                Integer.parseInt(row.get("level")),
			                Boolean.valueOf(row.get("extract")),
			                Boolean.valueOf(row.get("trickle")),

                            Boolean.valueOf(row.get("dblglazing80pctplus")),
                            // todo:
                            Boolean.valueOf(row.get("dblglazing80pctplus")),

                            people.get(row.get("code")));
			            
			            //put outputs into files
                        //exposuresOut.println(row.get("code"));
                        // this doesn't work any more because I broke it.
                    }
        		};
    }
}
