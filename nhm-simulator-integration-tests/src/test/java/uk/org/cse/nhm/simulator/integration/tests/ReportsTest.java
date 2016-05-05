package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.logging.logentry.ProbeLogEntry;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;

public class ReportsTest extends SimulatorIntegrationTest {
	@Test
	public void unified() throws Exception {
		final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("reports/unified.s"), true, 
				ImmutableSet.<Class<?>>of(ProbeLogEntry.class, AggregateLogEntry.class));
		for (final ISimulationLogEntry e : out.log) {
			System.out.println(e);
		}
	}
	
	@Test
	public void sequence() throws NHMException, InterruptedException {	
		super.runSimulation(dataService, loadScenario("reports/sequence.s"), true, Collections.<Class<?>>emptySet());
	}

	@Test
	public void transitions() throws NHMException, InterruptedException {
		super.runSimulation(dataService, loadScenario("reports/transitions.s"), true, Collections.<Class<?>>emptySet());
	}
}
