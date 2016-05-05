package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Iterables;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class GranularityIntegrationTests extends SimulatorIntegrationTest {

	@Test(expected = ScenarioLoadException.class)
	public void testZeroGranularityScenarioFailsWithNiceError() throws JAXBException, NHMException, InterruptedException {
		final XScenario scenario = loadScenario("zeroGranularityScenario.s");
		runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet());
	}

	@Test(expected = ScenarioLoadException.class)
	public void testOneGranularityScenarioHasSameNumberOfDwellingsAsCaseWeight() throws JAXBException, NHMException, InterruptedException {
		final XScenario scenario = loadScenario("oneGranularityScenario.s");
		final ICanonicalState state = runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet()).state;

		final double caseWeight = state.get(testExtension.basicAttributes, Iterables.get(state.getDwellings(), 0)).getDwellingCaseWeight();
		Assert.assertEquals("With granularity one, and a single house case the number of dwellings produce should equal the case weight.", caseWeight, state.getDwellings().size(), 0.0001);
	}

	@Test
	public void testLargeGranularityScenarioHasZeroDwellingsDueToRoundingDown() throws JAXBException, NHMException, InterruptedException {
		final XScenario scenario = loadScenario("largeGranularityScenario.s");
		final Collection<? extends IDwelling> dwellings = runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet()).state.getDwellings();

		Assert.assertEquals("With very large granularity, the number of dwellings produce should equal 0 due to rounding down.", 0, dwellings.size());
	}
}
