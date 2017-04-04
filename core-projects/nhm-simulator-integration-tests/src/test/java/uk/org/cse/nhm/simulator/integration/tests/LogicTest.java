package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

import uk.org.cse.nhm.NHMException;

public class LogicTest extends SimulatorIntegrationTest {

	@Test
	public void inequalities() throws NHMException, InterruptedException {
		super.runSimulation(dataService, loadScenario("functions/logic/inequalities.s"), true, Collections.<Class<?>>emptySet());
	}
}
