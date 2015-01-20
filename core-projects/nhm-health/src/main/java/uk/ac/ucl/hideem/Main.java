package uk.ac.ucl.hideem;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final HealthModule module = new HealthModule();

        while (true) {
            System.out.println(module.effectOf(something));
        }
    }
}
