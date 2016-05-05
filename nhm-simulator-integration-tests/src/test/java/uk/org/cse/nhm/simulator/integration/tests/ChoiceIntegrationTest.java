package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class ChoiceIntegrationTest extends SimulatorIntegrationTest {

	@Test
	public void testFallbackSelector() throws NHMException, InterruptedException {
		final IntegrationTestOutput output = runSimulation(fewerHouseCases(dataService, 0.1), loadScenario("choices/fallbackSelector.s"), true, Collections.<Class<?>>emptySet());
		
		for (final IDwelling d : output.state.getDwellings()) {
			final IFlags flags = output.state.get(output.flags, d);
			final BasicCaseAttributes basicCaseAttributes = output.state.get(output.basicAttributes, d);
			
			final boolean isUrban = basicCaseAttributes.getMorphologyType().equals(MorphologyType.Urban);
			
			if (isUrban) {
				Assert.assertTrue("Urban dwellings should have gotten the urban flag.", flags.testFlag("urban"));
			} else {
				Assert.assertTrue("Non-urban dwellings should have gotten the not-urban flag.", flags.testFlag("not-urban"));
			}
		}
	}
	
	@Test
	public void testNPVHeatingReplacement() throws NHMException, InterruptedException {
		final XScenario scenario = loadScenario("choices/npvHeatingReplacementScenario.s");
		final IntegrationTestOutput simulation = prepareSimulation(fewerHouseCases(dataService, 0.05), scenario, Collections.<Class<?>>emptySet());
		int districtHeatingCounter = 0;
		final IState state = simulation.state;
		simulation.simulator.step();
		for (final IDwelling d : state.getDwellings()) {
			if (state.get(testExtension.technology, d).getCommunityHeatSource() != null) {
				districtHeatingCounter++;
			}
		}

		while (simulation.simulator.step() > 0)
			;

		int afterCounter = 0;
		for (final IDwelling d : state.getDwellings()) {
			if (state.get(testExtension.technology, d).getCommunityHeatSource() != null) {
				afterCounter++;
			}
		}

		Assert.assertTrue("Lots of DH installed, because it's really cheap (free to install and free fuel) " + "before = " + districtHeatingCounter + ", after = " + afterCounter, afterCounter > (districtHeatingCounter + 500));
	}
}
