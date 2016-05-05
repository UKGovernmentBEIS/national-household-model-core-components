package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.simulator.integration.tests.guice.IFunctionAssertion;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.types.MonthType;

public class CounterfactualActionsIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void energyDecalibrationAffectsThingsDerivedFromEnergy() throws Exception {
		final boolean[] didCheckSomething = new boolean[] {false};
		
		runSimulation(dataService, loadScenario("hypothetical/decalibration-affects-others.s"), 
				true, 
				Collections.<Class<?>>emptySet(), ImmutableMap.<String, IFunctionAssertion>of(
						"carbon-is-not-zero",
						new IFunctionAssertion(){
							@Override
							public void evaluate(final String name, final IntegrationTestOutput output,
									final IComponentsScope scope, final ILets lets, final double value) {
								final double uncal = scope.getYielded("uncal-energy").get();
								
								if (uncal > 0) {
									Assert.assertTrue(value > 0);
									didCheckSomething[0] = true;
								}
							}
						}
				));
		
		Assert.assertTrue(didCheckSomething[0]);
	}
	
	@Test
	public void energyDecalibrationRemovesCalibration() throws Exception {
		runSimulation(dataService, loadScenario("hypothetical/decalibration.s"), 
				true, 
				Collections.<Class<?>>emptySet(), ImmutableMap.of(
						"is-99-without-decal",
						new IFunctionAssertion(){
							@Override
							public void evaluate(final String name, final IntegrationTestOutput output,
									final IComponentsScope scope, final ILets lets, final double value) {
								Assert.assertEquals(99d, value, 0.01d);
							}
						},
						"is-not-99-with-decal",
						new IFunctionAssertion(){
							@Override
							public void evaluate(final String name, final IntegrationTestOutput output,
									final IComponentsScope scope, final ILets lets, final double value) {
								Assert.assertFalse(Math.abs(99d-value) < 0.01);
							}
						}
				));
	}
	
	@Test
	public void underMangledWeatherWeatherIsMangled() throws Exception {
		runSimulation(dataService, loadScenario("hypothetical/weather.s"), 
				true, 
				Collections.<Class<?>>emptySet(), ImmutableMap.<String, IFunctionAssertion>of(
						"mangled-weather-is-mangled",
						new IFunctionAssertion(){
							@Override
							public void evaluate(final String name, final IntegrationTestOutput output,
									final IComponentsScope scope, final ILets lets, final double value) {
								final IHypotheticalComponentsScope before = 
										lets.get("before", IHypotheticalComponentsScope.class).get();
								
								final IWeather weatherBefore = before.get(output.weather);
								
								final IWeather weatherHere = scope.get(output.weather);
								
								Assert.assertEquals(0, weatherHere.getExternalTemperature(MonthType.January), 0d);
								Assert.assertEquals(0, weatherHere.getHorizontalSolarFlux(MonthType.January), 0d);
								Assert.assertEquals(0, weatherHere.getWindSpeed(MonthType.January), 0d);
								
								Assert.assertTrue(0 != weatherBefore.getExternalTemperature(MonthType.January));
								Assert.assertTrue(0 != weatherBefore.getHorizontalSolarFlux(MonthType.January));
								Assert.assertTrue(0 != weatherBefore.getWindSpeed(MonthType.January));
							}
						})
					);
	}

	@Test
	public void underMangledWeatherWeatherIsMangled2() throws Exception {
			runSimulation(dataService, loadScenario("hypothetical/weather-with-a-branch.s"), 
				true, 
				Collections.<Class<?>>emptySet(), ImmutableMap.<String, IFunctionAssertion>of(
						"mangled-weather-is-mangled",
						new IFunctionAssertion(){
							@Override
							public void evaluate(final String name, final IntegrationTestOutput output,
									final IComponentsScope scope, final ILets lets, final double value) {
								final IHypotheticalComponentsScope before = 
										lets.get("before", IHypotheticalComponentsScope.class).get();
								
								final IWeather weatherBefore = before.get(output.weather);
								
								final IWeather weatherHere = scope.get(output.weather);
								
								Assert.assertEquals(0, weatherHere.getExternalTemperature(MonthType.January), 0d);
								Assert.assertEquals(0, weatherHere.getHorizontalSolarFlux(MonthType.January), 0d);
								Assert.assertEquals(0, weatherHere.getWindSpeed(MonthType.January), 0d);
								
								Assert.assertTrue(0 != weatherBefore.getExternalTemperature(MonthType.January));
								Assert.assertTrue(0 != weatherBefore.getHorizontalSolarFlux(MonthType.January));
								Assert.assertTrue(0 != weatherBefore.getWindSpeed(MonthType.January));
							}
						})
					);
	}

}
