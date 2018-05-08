package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

public class UnderFunctionTest extends SimulatorIntegrationTest {

    @Test
    public void underCarbon() throws Exception {
        super.runSimulation(dataService, loadScenario("hypothetical/undercarbon.s"), true, Collections.<Class<?>>emptySet());
    }
}
