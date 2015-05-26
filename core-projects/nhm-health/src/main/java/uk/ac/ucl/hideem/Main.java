package uk.ac.ucl.hideem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

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
        
        //need to set to 42 years for now
        final HealthOutcome total = new HealthOutcome(42);
        //SIT method regression (default is old version)
        final boolean regressionSIT = false;
        
        for (final Map<String, String> row : CSV.mapReader(Files.newBufferedReader(Paths.get(args[1]), StandardCharsets.UTF_8))) {
            // blah blah
        	// Should get the totals here
        	
        	final double e1 = Double.parseDouble(row.get("e1"));
            final double e2 = Double.parseDouble(row.get("e2"));
            final BuiltForm.DwellingAge dwellingAge = BuiltForm.DwellingAge.valueOf(row.get("buildyear"));
            final BuiltForm.Tenure tenure = BuiltForm.Tenure.valueOf(row.get("tenure"));
            final BuiltForm.OwnerAge ownerAge = BuiltForm.OwnerAge.valueOf(row.get("agehr"));
            final boolean children = Boolean.valueOf(row.get("children"));
            final boolean feulPoverty = Boolean.valueOf(row.get("feulpov"));
        	
            final HealthOutcome outcome = module.effectOf(
                // get fields from row here
                module.getInternalTemperature(regressionSIT, e1, 1, dwellingAge, tenure, ownerAge, children, feulPoverty),
                module.getInternalTemperature(regressionSIT, e2, 1, dwellingAge, tenure, ownerAge, children, feulPoverty),
                Double.parseDouble(row.get("p1")),
                Double.parseDouble(row.get("p2")),
                BuiltForm.Type.valueOf(row.get("form")),
                Double.parseDouble(row.get("area")),
                Integer.parseInt(row.get("level")),
                Boolean.valueOf(row.get("extract")),
                Boolean.valueOf(row.get("trickle")),
                people.get(row.get("code")),
                Integer.parseInt(row.get("horizon")));
            System.out.println(row.get("code"));
            System.out.println(outcome);
            
            total.add(outcome, 42);
        }
        System.out.println("Totals:");
        System.out.println(total);
    }
}
