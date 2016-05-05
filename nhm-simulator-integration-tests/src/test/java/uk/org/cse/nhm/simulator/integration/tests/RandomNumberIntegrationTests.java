package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

import uk.org.cse.nhm.NHMException;

public class RandomNumberIntegrationTests extends SimulatorIntegrationTest {
	@Test
	public void testRandomizingTemperatures() throws NHMException, InterruptedException {
		runSimulation(fewerHouseCases(dataService, 0.01), loadScenario("functions/random/randomTemperatures.s"), true, Collections.<Class<?>>emptySet());
	}
}
