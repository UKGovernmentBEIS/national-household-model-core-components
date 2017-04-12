package uk.org.cse.nhm.simulator.integration.tests;

import static org.junit.Assert.*;

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
        final IntegrationTestOutput output = super.runSimulation(super.fewerHouseCases(dataService, 0.05),
                loadScenario("hideem/hideem.s"), true, Collections.<Class<?>> emptySet());

        
    }
}