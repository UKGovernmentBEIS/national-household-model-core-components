package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public class LightingTest extends SimulatorIntegrationTest {
	@Test
	/**
	 * This test has a random chance of failure if you happen to sample a bit of the stock that uses lots of energy.
	 */
	public void testLighting() throws NHMException, InterruptedException {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("lighting/lowEnergyLighting.s"), true, Collections.<Class<?>>emptySet());
		
		double standardElectricity = 0;
		double lowEnergyLightingElectricity = 0;
		
		double standardCount = 0;
		double lowEnergyCount = 0;
		
		for (final IDwelling d : output.state.getDwellings()) {
			final IPowerTable power = output.state.get(output.power, d);
			final double electricity = power.getPowerByFuel(FuelType.PEAK_ELECTRICITY) + power.getPowerByFuel(FuelType.OFF_PEAK_ELECTRICITY);
			
			
			if (output.state.get(output.flags, d).testFlag("better-lighting")) {
				lowEnergyLightingElectricity += electricity;
				lowEnergyCount++;
			} else {
				standardElectricity += electricity;
				standardCount++;
			}
		}
		
		Assert.assertNotSame("Some houses should have low energy lighting.", 0, lowEnergyCount);
		
		final double avgLowEnergy = lowEnergyLightingElectricity / lowEnergyCount;
		final double avgStandard = standardElectricity / standardCount;
		
		System.out.println("Low avg: " + avgLowEnergy);
		System.out.println("Standard avg: " + avgStandard);
		
		Assert.assertEquals(
				String.format("Low energy lighting should use less power. Low: %f, standard %f", avgLowEnergy, avgStandard),
				0.8,
				avgLowEnergy / avgStandard,
				0.05);
	}
}
