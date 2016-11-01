package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class FloorInsulationIntegrationTest extends SimulatorIntegrationTest {
	private static final double QUANTUM = 400;

	@Test
	public void floorInsulationAppliedToRightKindOfFloors() throws Exception {
		final IntegrationTestOutput output = super.runSimulation(dataService,
																 loadScenario("insulation/floorinsulation.s"),
																 true, Collections.<Class<?>>emptySet());

		boolean affectedAtLeastOneSuspendedTimber = false;
		boolean affectedAtLeastOneSolidFloor = false;
		
		int countPurchased = 0;
		
		for (final IDwelling dwelling : output.dwellingsWithFlag("suspended-insulated")) {
			Assert.assertEquals(FloorConstructionType.SuspendedTimberSealed,
								output.state.get(output.structure, dwelling).getGroundFloorConstructionType());
			affectedAtLeastOneSuspendedTimber = true;
			
			for (final Storey storey : output.state.get(output.structure, dwelling).getStoreys()) {
				Assert.assertEquals(1234d, storey.getFloorUValue(), 0);
			}
			
			countPurchased++;
		}
		
		for (final IDwelling dwelling : output.dwellingsWithFlag("solid-insulated")) {
			Assert.assertEquals(FloorConstructionType.Solid,
								output.state.get(output.structure, dwelling).getGroundFloorConstructionType());
			affectedAtLeastOneSolidFloor = true;
			countPurchased++;
		}
		
		Assert.assertTrue(affectedAtLeastOneSolidFloor);
		Assert.assertTrue(affectedAtLeastOneSuspendedTimber);
		
		final GlobalTransactionHistory globalAccount = output.state.getGlobals().getGlobalAccount(ITransaction.Counterparties.MARKET);
		
		Assert.assertEquals(1000d * countPurchased * QUANTUM, globalAccount.getBalance(), 0.01);
	}
}
