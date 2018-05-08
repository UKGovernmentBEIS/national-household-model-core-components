package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

/**
 * ProductsPolicyApplicanceTests.
 *
 * @author trickyBytes
 */
public class ProductsPolicyApplicanceTests extends SimulatorIntegrationTest {

    @Test
    public void testScenarioCompiles() throws Exception {
        runSimulation(super.restrictHouseCases(dataService, "K5072323"),
                loadScenario("adjusting/adjustappliances.s"), true, Collections.<Class<?>>emptySet());
    }
}
