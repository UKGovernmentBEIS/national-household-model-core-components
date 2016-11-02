package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.integration.tests.guice.IFunctionAssertion;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.types.MonthType;

/*
 * Tests for flags, registers, let, get, yield, snapshot etc.
 */
public class VariablesIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void underEvaluates() throws NHMException, InterruptedException {
		final AtomicInteger hits = new AtomicInteger();
		final AtomicInteger hits2 = new AtomicInteger();
		
		runSimulation(
				fewerHouseCases(dataService, 0.05), 
				loadScenario("sapimputation.s"),
				true,
				Collections.<Class<?>>emptySet(), ImmutableMap.<String, IFunctionAssertion>of(
						"debug-under",
						new IFunctionAssertion() {
							@Override
							public void evaluate(
									final String name, 
									final IntegrationTestOutput output,
									final IComponentsScope scope, 
									final ILets lets, final double value) {
								final IWeather weather = scope.get(output.weather);
								Assert.assertEquals("weather in jan is set to 10 in test scenario's under", 10, weather.getExternalTemperature(MonthType.January), 0);
								final ICarbonFactors carbon = scope.get(output.fuelPricing);
								Assert.assertEquals("carbon factor for gas set to 2 in test scenario", 2, carbon.getCarbonFactor(FuelType.MAINS_GAS), 0);
								hits.incrementAndGet();
							}
						},
						"debug-outside",
						new IFunctionAssertion() {
							@Override
							public void evaluate(final String name, final IntegrationTestOutput output,
									final IComponentsScope scope, final ILets lets, final double value) {
								hits2.incrementAndGet();
								final IWeather weather = scope.get(output.weather);
								Assert.assertFalse("outside under things are fine", 10d == weather.getExternalTemperature(MonthType.January));
								final ICarbonFactors carbon = scope.get(output.fuelPricing);
								Assert.assertFalse("outside under things are fine", 2d == carbon.getCarbonFactor(FuelType.MAINS_GAS));
							}
						}
						
					)
				);
		
		Assert.assertTrue("test has run", hits.get() > 0);
		Assert.assertTrue("test has run", hits2.get() > 0);
	}
	
	@Test
	public void testRegisters() throws NHMException, InterruptedException {
		final ICanonicalState state = runSimulation(restrictHouseCases(dataService, "H0012401"), loadScenario("registersScenario.s"), true, Collections.<Class<?>>emptySet()).state;
		for(final IDwelling d : state.getDwellings()) {
			final IFlags flags = state.get(testExtension.flags, d);
			
			Assert.assertEquals("Register should have gone as follows: null (default 50), doubled to 100, add 100, doubled to 400.", 400.0, flags.getRegister("accumulator").get(), NO_ERROR);
		}
	}
	
	@Test
	public void testSetAndGetU()  throws NHMException, InterruptedException {
		final ICanonicalState state = runSimulation(fewerHouseCases(dataService, 0.05), loadScenario("modifyUValue.s"), true, Collections.<Class<?>>emptySet()).state;
		
		for (final IDwelling d : state.getDwellings()) {
			final IFlags flags = state.get(testExtension.flags, d);
			
			final double u = flags.getRegister("uvalue").get();
			
			Assert.assertEquals(99d, u, 0.001);
		}
	}
	
	@Test
	public void testSnapshotDelta() throws NHMException, InterruptedException {
		final ICanonicalState state = runSimulation(restrictHouseCases(dataService, "H0012401"), loadScenario("snapshotDeltaScenario.s"), true, Collections.<Class<?>>emptySet()).state;
		for(final IDwelling d : state.getDwellings()) {
			final IFlags flags = state.get(testExtension.flags, d);
			
			Assert.assertEquals("Register should have the value 1.0, which is the change observed in the house.", 1.0, flags.getRegister("value").get(), NO_ERROR);
		}
	}
	
	/**
	 * This test builds a simulator with the xml from scenario1.s and runs it
	 * 
	 * it checks that at the end all the houses have CWI.
	 * 
	 * @throws JAXBException
	 * @throws NHMException
	 */
	@Test
	public void testFlagSwitching() throws JAXBException, NHMException, InterruptedException {
		final XScenario scenario = loadScenario("flags.s");

		final ICanonicalState state = runSimulation(fewerHouseCases(dataService, 0.1), scenario, true, Collections.<Class<?>>emptySet()).state;
		
		for(final IDwelling d : state.getDwellings()) {
			final IFlags flags = state.get(testExtension.flags, d);
			if(state.get(testExtension.basicAttributes, d).getRegionType() == RegionType.London) {
				Assert.assertTrue("should have is-in-London flag", flags.testFlag("is-in-london"));
				Assert.assertFalse("should not have not-in-London flag", flags.testFlag("not-in-london"));
			} else {
				Assert.assertTrue("should have not-in-London flag", flags.testFlag("not-in-london"));
				Assert.assertFalse("should not have is-in-London flag", flags.testFlag("is-in-london"));
			}
		}
	}
}
