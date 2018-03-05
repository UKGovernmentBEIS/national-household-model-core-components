package uk.org.cse.nhm.simulator.integration.tests;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;

/**
 * ProductsPolicyApplicanceTests.
 *
 * @author trickyBytes
 */
public class ProductsPolicyApplicanceTests extends SimulatorIntegrationTest {

    @Test
    public void testScenarioCompiles() throws Exception {
        runSimulation(super.restrictHouseCases(dataService, "K5072323"),
                loadScenario("adjusting/adjustappliances.s"), true, Collections.<Class<?>> emptySet());
    }
}
