package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class DraughtProofingIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void draughtProofing() throws NHMException, InterruptedException, IOException {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("insulation/draughtproofing.s"), true, Collections.<Class<?>>emptySet());
		for(IDwelling d:  output.state.getDwellings()) {
			StructureModel structureModel = output.state.get(output.structure, d);
			Assert.assertEquals("Draught proofing installed should match the measure draught proofing proportion", structureModel.getDraughtStrippedProportion(), 1d, 0d);
		}
	}
}
