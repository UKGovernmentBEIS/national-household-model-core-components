package uk.ac.ucl.hideem;

import java.util.Collections;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        final HealthModule module = new HealthModule();

        //TODO read useful things from here
        while (true) {
            System.out.println(module.effectOf(
                                   0, 0,
                                   0, 0,
                                   BuiltForm.Detached,
                                   100,
                                   0,
                                   1990,
                                   true,
                                   true,
                                   1,
                                   Collections.<Person>emptyList(),
                                   10));
            break;
        }
    }
}
