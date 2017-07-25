package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;

/**
 * CombinationChoiceTest.
 *
 * @author trickyBytes
 */
public class CombinationChoiceTest extends SimulatorIntegrationTest {

    @Test
    public void testCompilation() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(dataService,
                loadScenario("combinationsChoice.s"), true, Collections.<Class<?>> emptySet());
    }
}
