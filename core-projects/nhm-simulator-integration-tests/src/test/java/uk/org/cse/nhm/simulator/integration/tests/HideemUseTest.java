package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;

/**
 * HideemUseTest.
 *
 * @author trickyBytes
 */
public class HideemUseTest extends SimulatorIntegrationTest {

    @Test
    public void canUseFunctionInAScenario() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(restrictHouseCases(dataService, "J0182106"),
                loadScenario("hideem/hideem.s"), true, Collections.<Class<?>>emptySet());
    }
}
