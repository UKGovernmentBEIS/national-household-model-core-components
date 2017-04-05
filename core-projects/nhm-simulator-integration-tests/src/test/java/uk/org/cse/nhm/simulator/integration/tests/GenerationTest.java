package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

public class GenerationTest extends SimulatorIntegrationTest {

	@Test
	public void solarPhotovoltaic() throws NHMException,
			InterruptedException {
		final IntegrationTestOutput output = super.runSimulation(
				fewerHouseCases(dataService, 0.1),
				loadScenario("generation/solarPhotovoltaic.s"),
				true, Collections.<Class<?>>emptySet());

		int solarCount = 0;
		for (final IDwelling d : output.state.getDwellings()) {

			if (output.state.get(output.flags, d).testFlag("solar-panels")) {
				solarCount++;

				final IPowerTable power = output.state.get(output.power, d);

				final double generation = -(power.getFuelUseByEnergyService(ServiceType.GENERATION, FuelType.PEAK_ELECTRICITY) +
						power.getFuelUseByEnergyService(ServiceType.GENERATION, FuelType.EXPORTED_ELECTRICITY)
						);
				final double roofArea = output.state.get(output.structure, d).getExternalRoofArea(true);

				/* Actual generation will vary a bit by region. */
				final double expectedGeneration = 50 * roofArea;

				Assert.assertTrue(
						String.format("Expected generation of at least %f, was %f.",
								expectedGeneration,
								generation),
						generation > expectedGeneration);

				final double photons = power.getFuelUseByEnergyService(ServiceType.GENERATION, FuelType.PHOTONS);

				final double expectedPhotons = 1000.0;
				Assert.assertTrue(
						String.format("Solar panel should have used some phtons. Expected %f, was %f", expectedPhotons, photons),
						photons > expectedPhotons);

				final DwellingTransactionHistory finances = output.state.get(output.transactionHistory, d);
				Assert.assertEquals("£10000 should have been spent installing the panels.", -10000.0, finances.getBalance(), 1);
			}
		}

		final int expectedinstallations = 4000;
		Assert.assertTrue(
				String.format("Expected at least %d houses to get solar photoVoltaic, but was %d.", expectedinstallations, solarCount),
				solarCount > expectedinstallations);
	}
}
