package uk.ac.ucl.hideem;

import com.google.common.collect.*;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Map;

public class Main
{
    
    public static void main( String[] args ) throws IOException
    {
        final HealthModule module = new HealthModule();

        // load the people into here; keyed on house ID
        // format expected to be:
        // code, age, sex, smoker
        final ListMultimap<String, Person> people = ArrayListMultimap.create();

        System.out.println("Reading people from " + args[0]);

        for (final Map<String, String> row : CSV.mapReader(Paths.get(args[0]))) {
            final Person p = Person.readPerson(row);
            people.put(row.get("code"), p);
        }

        // now process the houses
        System.out.println("Reading house data from " + args[1]);

        for (final Map<String, String> row : CSV.mapReader(Paths.get(args[1]))) {
            // blah blah
            final HealthOutcome outcome = module.effectOf(
                // get fields from row here
                Double.parseDouble(row.get("e1")),
                Double.parseDouble(row.get("e2")),
                Double.parseDouble(row.get("p1")),
                Double.parseDouble(row.get("p2")),
                BuiltForm.valueOf(row.get("form")),
                Double.parseDouble(row.get("area")),
                Integer.parseInt(row.get("level")),
                Integer.parseInt(row.get("buildyear")),
                Boolean.valueOf(row.get("extract")),
                Boolean.valueOf(row.get("trickle")),
                Integer.parseInt(row.get("fans")),
                people.get(row.get("code")),
                Integer.parseInt(row.get("horizon")));
            System.out.println(row.get("code"));
            System.out.println(outcome);

        }
    }
}
