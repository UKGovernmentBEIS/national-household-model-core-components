package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.WeeklyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.integration.tests.AnnualizedEnergyCalculator.Result;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public class EnergyCalculatorIntegrationTest extends SimulatorIntegrationTest {

	/**
	 * The purpose of this test is to run the energy calculator directly for the
	 * whole stock, and within the simulator using the
	 * scenarios/common/all-weather.xml climate context, and then check that the
	 * energy results are the same in both cases.
	 *
	 * The direct run uses the tsv files in energyParameters/; if you change
	 * these, use {@link #generateExemplarWeatherContext()} to create new
	 * content for all-weather.xml
	 *
	 * @throws JAXBException
	 * @throws NHMException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test
	public void testEnergyResultsMatch() throws JAXBException, NHMException, InterruptedException, IOException {
		final String TEMPERATURE = "externaltemperature.tsv";
		final String WINDSPEED = "windspeed.tsv";
		final String SOLARFLUX = "solarflux.tsv";

		final RegionalMonthlyTable temperatureTable = getTable(TEMPERATURE);
		final RegionalMonthlyTable solarFluxTable = getTable(SOLARFLUX);
		final RegionalMonthlyTable windSpeedTable = getTable(WINDSPEED);

		final XScenario scenario = loadScenario("energyCalculatorScenario.s");
		runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet());

		final List<SurveyCase> allSurveyCases = dataService.getSurveyCases(surveyCaseSetID, ADDITIONAL_PROPERTIES);
		final Map<String, SurveyCase> surveyCasesByCode = new HashMap<String, SurveyCase>();

		for (final SurveyCase s : allSurveyCases) {
			surveyCasesByCode.put(s.getBasicAttributes().getAacode(), s);
		}

		final AnnualizedEnergyCalculator calc = new AnnualizedEnergyCalculator();

		calc.setExternalTemperature(temperatureTable);
		calc.setHorizontalSolarFlux(solarFluxTable);
		calc.setWindSpeed(windSpeedTable);

		final boolean[] HEATING_MONTHS = { true, true, true, true, true, false, false, false, false, true, true, true };

		calc.setHeatingMonths(HEATING_MONTHS);

		calc.setZoneOneDemandTemperature(19);

		calc.setHeatingSchedule(new WeeklyHeatingSchedule(
				DailyHeatingSchedule.fromHours(7, 8, 18, 23),
				DailyHeatingSchedule.fromHours(7, 23)));

		calc.setInterzoneTemperatureDifference(3);

		calc.setCalculator(new EnergyCalculatorCalculator());

		int passCounter = 0;

		for (final IDwelling d : testExtension.state.getDwellings()) {
			final String aa = testExtension.state.get(testExtension.basicAttributes, d).getAacode();

			final IPowerTable r = testExtension.state.get(testExtension.power, d);
			final Result evaluate = calc.evaluate(surveyCasesByCode.get(testExtension.state.get(testExtension.basicAttributes, d).getAacode()));
			for (final FuelType ft : FuelType.values()) {
				if (ft == FuelType.ELECTRICITY)
					continue;
				if (ft == FuelType.PHOTONS)
					continue;
				for (final ServiceType st : ServiceType.values()) {
					final double d1 = r.getFuelUseByEnergyService(st, ft);
					double d2 = 0;
					if (evaluate.energy.containsKey(st)) {
						if (evaluate.energy.get(st).containsKey(ft.getEnergyType())) {
							d2 = evaluate.energy.get(st).get(ft.getEnergyType());
						}
					}

					final IWeather weather = testExtension.state.get(testExtension.weather, d);

					for (final MonthType m : MonthType.values()) {
						final RegionType region = testExtension.state.get(testExtension.basicAttributes, d).getRegionType();
						Assert.assertEquals(aa + " region = " + region, calc.getExternalTemperature().get(region, m.ordinal()), weather.getExternalTemperature(m), 0.01);
					}

					Assert.assertEquals(aa + " " + ft + " on " + st + " should be equal", d1, d2, 10);
				}
			}

			passCounter++;
		}

		System.out.println(String.format("%d houses passed match", passCounter));
	}
}
