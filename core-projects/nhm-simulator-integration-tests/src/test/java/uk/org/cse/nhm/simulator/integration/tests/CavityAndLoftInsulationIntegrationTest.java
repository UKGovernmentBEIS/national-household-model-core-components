package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class CavityAndLoftInsulationIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void cavityAndLoft() throws NHMException, InterruptedException, IOException {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("insulation/cavityandloft.s"), true, Collections.<Class<?>>emptySet());
		
		for (final IDwelling dwelling : output.dwellingsWithFlag("affected")) {
			final IFlags flags = output.state.get(output.flags, dwelling);
			
			final double before = flags.getRegister("base").get();
			final double cavity = flags.getRegister("with-cavity").get();
			final double loft = flags.getRegister("with-loft").get();
			final double both = flags.getRegister("with-both").get();
			
			final double dcavity = before - cavity;
			final double dloft = before - loft;
			final double dboth = before - both;
			final double dsum = dloft + dcavity;
			
			final double top = dsum - dboth;
			
			Assert.assertTrue("Delta is quite small " + top, Math.abs(top) < 5000);
		}
	}
}
