package uk.org.cse.nhm.simulator.integration.tests;

import org.junit.Assert;
import org.junit.Test;


public class VerifyScenariosIntegrationTest extends SimulatorIntegrationTest {
	private void assertInvalid(final String scenario) {
		try {
			loadScenario("invalid/" + scenario);
		} catch (final Exception e) {
			return;
		}
		Assert.fail("Scenario " + scenario + " was not as invalid as expected");
	}
	
	private static final String[] invalidScenarios = {
		"missingscenarioparams.s",
		"reusedname.s",
		"nohouse.s"
	};
	
	@Test
	public void testInvalidScenariosAreInvalid() {
		for (final String s : invalidScenarios) {
			assertInvalid(s);
		}
	}
}
