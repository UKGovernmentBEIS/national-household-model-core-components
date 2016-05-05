package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Iterables;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.types.MonthType;

public class WeatherIntegrationTests extends SimulatorIntegrationTest {
	/**
	 * Loads a scenario where nothing is defined except the outer scenario
	 * element. Expects to see an exception because the weather is undefined.
	 * 
	 * @throws JAXBException
	 * @throws NHMException
	 * @throws InterruptedException
	 */
	@Test
	public void testDefaultWeatherIfNotDefined() throws JAXBException, NHMException, InterruptedException {
		final XScenario scenario = loadScenario("emptyScenario.s");
		final IState state = runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet()).state;
		final IWeather weather = state.get(testExtension.weather, Iterables.get(state.getDwellings(), 0));
		
		int i = 0;
		for (final double t : new double[] {4.50, 5.00, 6.80, 8.700, 11.70, 14.60, 16.90, 16.90, 14.30, 10.80, 7.00, 4.90}) {
			Assert.assertEquals(t, weather.getExternalTemperature(MonthType.values()[i++]), 0.01);
		}
		
		i = 0;
		for (final double t :new double[] {26.0, 54.0, 94.0, 150.0, 190.0, 201.0, 194.0, 164.0, 116.0, 68.00, 33.0, 21.0}) {
			Assert.assertEquals(t, weather.getHorizontalSolarFlux(MonthType.values()[i++]), 0.01);
		}
		
		i = 0;
		for (final double t : new double[] {5.40, 5.10, 5.10, 4.500, 4.100, 3.900, 3.700, 3.700, 4.200, 4.500, 4.80, 5.10}) {
			Assert.assertEquals(t, weather.getWindSpeed(MonthType.values()[i++]), 0.01);
		}
	}
}
