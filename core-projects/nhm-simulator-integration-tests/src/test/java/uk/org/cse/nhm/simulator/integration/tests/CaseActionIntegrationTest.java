	package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class CaseActionIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void caseActionWorksCorrectly() throws Exception {
		final IntegrationTestOutput output = runSimulation(fewerHouseCases(dataService, 0.1), loadScenario("action/caseaction.s"), true, Collections.<Class<?>>emptySet());
		
		for (final IDwelling d : output.state.getDwellings()) {
			if (output.state.get(output.basicAttributes, d).getRegionType() == RegionType.London) {
				Assert.assertTrue(output.state.get(output.flags, d).testFlag("in-london"));
				Assert.assertFalse(output.state.get(output.flags, d).testFlag("detached"));
				Assert.assertFalse(output.state.get(output.flags, d).testFlag("others"));
			} else if (output.state.get(output.structure, d).getBuiltFormType() == BuiltFormType.Detached) {
				Assert.assertFalse(output.state.get(output.flags, d).testFlag("in-london"));
				Assert.assertTrue(output.state.get(output.flags, d).testFlag("detached"));
				Assert.assertFalse(output.state.get(output.flags, d).testFlag("others"));
			} else {
				Assert.assertFalse(output.state.get(output.flags, d).testFlag("in-london"));
				Assert.assertFalse(output.state.get(output.flags, d).testFlag("detached"));
				Assert.assertTrue(output.state.get(output.flags, d).testFlag("others"));
			}
		}
	}
}
