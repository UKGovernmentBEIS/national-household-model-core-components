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
			        
			        //SIT method regression (default is old version)
			        final boolean regressionSIT = false;
			        
			        for (final Map<String, String> row : CSV.mapReader(Files.newBufferedReader(Paths.get(args[1]), StandardCharsets.UTF_8))) {
			        	// Should get the totals here
			        	
			        	final double e1 = Double.parseDouble(row.get("e1"));
			            final double e2 = Double.parseDouble(row.get("e2"));
			            final BuiltForm.DwellingAge dwellingAge = BuiltForm.DwellingAge.valueOf(row.get("buildyear"));
			            final BuiltForm.Tenure tenure = BuiltForm.Tenure.valueOf(row.get("tenure"));
			            final BuiltForm.OwnerAge ownerAge = BuiltForm.OwnerAge.valueOf(row.get("agehr"));
			            final boolean children = Boolean.valueOf(row.get("children"));
			            final boolean fuelPoverty = Boolean.valueOf(row.get("fuelpov"));
			        	
			            final HealthOutcome outcome = module.effectOf(
			                // get fields from row here
			                module.getInternalTemperature(regressionSIT, e1, 1, dwellingAge, tenure, ownerAge, children, fuelPoverty),
			                module.getInternalTemperature(regressionSIT, e2, 1, dwellingAge, tenure, ownerAge, children, fuelPoverty),
			                //Double.parseDouble(row.get("e1")),
			                //Double.parseDouble(row.get("e2")),
			                Double.parseDouble(row.get("p1")),
			                Double.parseDouble(row.get("p2")),
			                Double.parseDouble(row.get("e1")),
			                Double.parseDouble(row.get("e2")),
			                BuiltForm.Type.valueOf(row.get("form")),
			                Double.parseDouble(row.get("floor_area")),
			                Integer.parseInt(row.get("gor_ehs")),
			                Integer.parseInt(row.get("level")),
			                Boolean.valueOf(row.get("extract")),
			                Boolean.valueOf(row.get("trickle")),
			                Boolean.valueOf(row.get("rebate")),
			                Boolean.valueOf(row.get("dblglazing80pctplus")),
			                people.get(row.get("code")),
			                Integer.parseInt(row.get("horizon")));
			            
			            //put outputs into files
			            //exposuresOut.println(row.get("code"));
			            exposuresOut.print(outcome.printExposures(row.get("code")));
			            qalysOut.print(outcome.printQalys(row.get("code")));
			            morbQalysOut.print(outcome.printMorbidityQalys(row.get("code")));
			            costsOut.print(outcome.printCosts(row.get("code")));			            
			            
			        }
        		};
    }
}
