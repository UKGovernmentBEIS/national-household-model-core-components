package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.state.IState;

public class CarbonIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void testCarbonFactorsWithLookupTimeseries() throws Exception {
		final XScenario scenario = loadScenario("carbonfactors.s");
		final IState state = runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet()).state;
		
		//0.2599	
		
		//0.0542
		
		Assert.assertEquals(0.2599, state.getGlobals().getVariable("cf-2014", Double.class).get().doubleValue(), 0.01f);
		Assert.assertEquals(0.0542, state.getGlobals().getVariable("cf-2029", Double.class).get().doubleValue(), 0.01f);
	}
}
