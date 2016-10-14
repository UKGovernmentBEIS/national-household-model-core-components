package uk.org.cse.nhm.energycalculator.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator;
import uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator;
import uk.org.cse.nhm.energycalculator.impl.SpecificHeatLosses;

public class EnerggyCalculatorTest {
	@Test
	public void testCalculateBackgroundTemperatures() {
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		final SpecificHeatLosses heatLosses = 
            new SpecificHeatLosses(1d, 0d, 3.6*16, 1, 0, 0, 0);
		
		when(parameters.getZoneOneDemandTemperature()).thenReturn(20d);
		when(parameters.getZoneTwoDemandTemperature()).thenReturn(20d);
		
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		
		when(parameters.getClimate()).thenReturn(climate);
		
		when(climate.getExternalTemperature()).thenReturn(19d); // delta t = 1

		final double[][] temps = EnergyCalculatorCalculator.calculateBackgroundTemperatures(parameters, heatLosses, 1, 1, 2);
		
		// utilisation factor should be 2/3
		
		// unresponsive temperature should be 19
		// responsive temperature ought to be 19 + 2/3 * 1 / 1
		
		Assert.assertEquals(19 + 2/3.0, temps[0][0],  0.000001);
		Assert.assertEquals(19 + 2/3.0, temps[0][1], 0.000001);
		Assert.assertEquals(19d, temps[1][0], 0.01);
		Assert.assertEquals(19d, temps[1][1], 0.01);
	}
	
	@Test
	public void testCalculateGainsUtilisationFactor() {
		//values produced from spreadsheet.
		Assert.assertEquals(
				0.9271425103, EnergyCalculatorCalculator.calculateGainsUtilisationFactor(20, 10, 5, 10, 1.5), 0.0001);
		Assert.assertEquals(
				0.6, EnergyCalculatorCalculator.calculateGainsUtilisationFactor(20, 10, 1, 10, 1.5), 0.0001);
		Assert.assertEquals(
				1.0, EnergyCalculatorCalculator.calculateGainsUtilisationFactor(20, 10, -1, 10, 1.5), 0.0001);
	}
	
	@Test
	public void testThermalBridgingLosses() {
		// losses due to thermal bridging are calculated as 
		// a magic number multiplied with the total external area, allocated by floor area.
		final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();
		
		final IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(parameters.getClimate()).thenReturn(climate);
		final IStructuralInfiltrationAccumulator infiltration = mock(IStructuralInfiltrationAccumulator.class);

		final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();
		
		when(houseCase.getBuildYear()).thenReturn(2005);
		when(houseCase.getHouseVolume()).thenReturn(0d); // force ventilation loss = 0
		when(houseCase.getFloorArea()).thenReturn(100d);
		
		final ISpecificHeatLosses heatLosses = calc.calculateSpecificHeatLosses(houseCase, parameters, 0, 0, 25+50d,infiltration,
				 ventilationSystems);
	
		// specific heat loss should be (25 + 50) * TBP(2005) = 0.15/4.0
		Assert.assertEquals((25 + 50) * 0.15 / 4.0, heatLosses.getSpecificHeatLoss(), 0.01);
	}
	
	@Test
	public void testThermalBridgingLossesBefore03() {
		// losses due to thermal bridging are calculated as 
		// a magic number multiplied with the total external area, allocated by floor area.
		final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();
		
		final IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(parameters.getClimate()).thenReturn(climate);
		final IStructuralInfiltrationAccumulator infiltration = mock(IStructuralInfiltrationAccumulator.class);
		final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();
		when(houseCase.getBuildYear()).thenReturn(2002);
		when(houseCase.getHouseVolume()).thenReturn(0d); // force ventilation loss = 0
		when(houseCase.getFloorArea()).thenReturn(100d);
		
		final ISpecificHeatLosses heatLosses = calc.calculateSpecificHeatLosses(houseCase, parameters,0,0,25+50, infiltration,  ventilationSystems);
	
		// specific heat loss should be (25 + 50) * TBP(2005) = 0.15/4.0
		Assert.assertEquals((25 + 50) * 0.15, heatLosses.getSpecificHeatLoss(), 0.01);
	}
	
	@Test
	public void testVentilationHeatLoss() {
		final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();
		
		final IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
		final IStructuralInfiltrationAccumulator infiltration = mock(IStructuralInfiltrationAccumulator.class);
		final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();

		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		
		when(parameters.getClimate()).thenReturn(climate);
		
		when(climate.getSiteWindSpeed()).thenReturn(4d);
		when(houseCase.getHouseVolume()).thenReturn(100d);
		when(houseCase.getFloorArea()).thenReturn(100d);
		when(infiltration.getAirChangeRate(houseCase, parameters)).thenReturn(100d);
		
		final ISpecificHeatLosses heatLosses = calc.calculateSpecificHeatLosses(houseCase, parameters, 0d,0d,0d, infiltration, ventilationSystems);
	
		// heat losses should be entirely due to ach rate.
		// heat loss is 0.33 * volume; volume = (100 * 100) = (10000), *0.33 = 3300
		Assert.assertEquals(3300d, heatLosses.getSpecificHeatLoss(), 0.01);
	}
	@Test
	
	public void testFabricHeatLossAndMass() {
		final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();
		
		final IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);

		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(parameters.getClimate()).thenReturn(climate);
		
		final IStructuralInfiltrationAccumulator infiltration = mock(IStructuralInfiltrationAccumulator.class);
		final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();

		when(houseCase.getHouseVolume()).thenReturn(0d); //force ventilation loss to be zero
		when(houseCase.getFloorArea()).thenReturn(100d);
		when(infiltration.getAirChangeRate(houseCase, parameters)).thenReturn(0d);
		
		final ISpecificHeatLosses heatLosses = calc.calculateSpecificHeatLosses(houseCase, parameters, 
				10, 20, 0,
				infiltration, ventilationSystems);
	
		Assert.assertEquals(10d, heatLosses.getSpecificHeatLoss(), 0.01);
		Assert.assertEquals(20d, heatLosses.getThermalMass(), 0.01);
	}
	
	@Test
	public void testHumanVentilation() {
		final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();
		
		final IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
		final IStructuralInfiltrationAccumulator infiltration = mock(IStructuralInfiltrationAccumulator.class);
		final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();
		
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);

		when(parameters.getClimate()).thenReturn(climate);

		when(climate.getSiteWindSpeed()).thenReturn(4d);
		when(houseCase.getHouseVolume()).thenReturn(100d);
		when(houseCase.getFloorArea()).thenReturn(100d);
		when(infiltration.getAirChangeRate(houseCase, parameters)).thenReturn(0d);
		
		final ISpecificHeatLosses heatLosses = calc.calculateSpecificHeatLosses(houseCase, parameters,0,0,0, infiltration, ventilationSystems);
	
		// heat losses should be entirely due to ach rate.
		// when ach rate is low, it get bumped up; it should be 0.5.
		// that should give us a heat loss of 0.5 * 100 * 0.33 = 50*0.33 = 16.5
		Assert.assertEquals(16.5, heatLosses.getSpecificHeatLoss(), 0.01);
	}
	
	@Test
	public void testMechanicalVentilation() {
		final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();
		
		final IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
		final IStructuralInfiltrationAccumulator infiltration = mock(IStructuralInfiltrationAccumulator.class);

		final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();
		final IVentilationSystem system = mock(IVentilationSystem.class);
		ventilationSystems.add(system);
		
		when(system.getAirChangeRate(0d)).thenReturn(123d);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);

		when(parameters.getClimate()).thenReturn(climate);
		when(climate.getSiteWindSpeed()).thenReturn(4d);
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		
		when(houseCase.getHouseVolume()).thenReturn(100d);
		when(houseCase.getFloorArea()).thenReturn(100d);
		when(infiltration.getAirChangeRate(houseCase, parameters)).thenReturn(0d);
		
		final ISpecificHeatLosses heatLosses = calc.calculateSpecificHeatLosses(houseCase, parameters, 
				0,0,0,
				infiltration, ventilationSystems);
	
		// heat losses should be entirely due to ach rate.
		// when ach rate is low, it get bumped up; it should be 0.5.
		// that should give us a heat loss of 0.5 * 100 * 0.33 = 50*0.33 = 16.5
		Assert.assertEquals(123 * 100 * 0.33, heatLosses.getSpecificHeatLoss(), 0.000000001);
	}
	
	@Test
	public void testRunToNonHeatTransducers() {
		final List<IEnergyTransducer> ts = ImmutableList.of(
				mock(IEnergyTransducer.class),
				mock(IEnergyTransducer.class),
				mock(IEnergyTransducer.class));
		
		final IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		final ISpecificHeatLosses heatLosses = mock(ISpecificHeatLosses.class);
		final IInternalParameters adjustedParameters = mock(IInternalParameters.class);
		when(adjustedParameters.getClimate()).thenReturn(mock(ISeasonalParameters.class));
		final IEnergyState state = mock(IEnergyState.class);
		
		final int runToNonHeatTransducers = EnergyCalculatorCalculator.runToNonHeatTransducers(
				houseCase, ts, ts.get(1), heatLosses, adjustedParameters, state);
		
		Assert.assertEquals("Stopped at 1", 1, runToNonHeatTransducers);
		
		final IEnergyTransducer shouldBeRun = ts.get(0);
		verify(shouldBeRun).generate(houseCase, adjustedParameters, heatLosses, state);
		verifyZeroInteractions(ts.get(1), ts.get(2));
	}
	
	@Test
	public void testGetBackgroundTemperature() {
		final IHeatingSystem system = mock(IHeatingSystem.class);
		when(
				system.getBackgroundTemperatures(any(double[].class), any(double[].class), any(double[].class), any(IInternalParameters.class),
						any(IEnergyState.class), any(ISpecificHeatLosses.class))).thenReturn(new double[] { 18, 18 });
		final List<IHeatingSystem> systems = ImmutableList.of(system);
		final Map<IHeatingSystem, Double> proportions = ImmutableMap.of(system, 1.0);
		final ISpecificHeatLosses heatLosses = mock(ISpecificHeatLosses.class);
		final IInternalParameters adjustedParameters = mock(IInternalParameters.class);
		final IEnergyState state = mock(IEnergyState.class);
		final double[] demandTemperature = new double[] {21, 21};
		final double[] idealBackgroundTemperature = new double[] {10, 10};
		final double[] worstCaseBackgroundTemperature = new double[] {15, 15};
		final double[] backgroundTemperatureFromHeatingSystems 
			= EnergyCalculatorCalculator.getBackgroundTemperatureFromHeatingSystems(
				systems,
				proportions,
				heatLosses, 
				adjustedParameters, 
				state, 
				demandTemperature, 
				idealBackgroundTemperature, 
				worstCaseBackgroundTemperature);
		
		Assert.assertTrue(Arrays.equals(backgroundTemperatureFromHeatingSystems, new double[] {18, 18}));
	}
	
	@Test(expected = RuntimeException.class)
	public void cannotGetBackgroundTemperatureWithNoHeatingSystems() {
		final List<IHeatingSystem> systems = ImmutableList.of();
		final Map<IHeatingSystem, Double> proportions = ImmutableMap.of();
		final ISpecificHeatLosses heatLosses = mock(ISpecificHeatLosses.class);
		final IInternalParameters adjustedParameters = mock(IInternalParameters.class);
		final IEnergyState state = mock(IEnergyState.class);
		final double[] demandTemperature = new double[] { 21, 21 };
		final double[] idealBackgroundTemperature = new double[] { 10, 10 };
		final double[] worstCaseBackgroundTemperature = new double[] { 15, 15 };
		
		EnergyCalculatorCalculator.getBackgroundTemperatureFromHeatingSystems(systems, proportions, heatLosses,
				adjustedParameters, state, demandTemperature, idealBackgroundTemperature,
				worstCaseBackgroundTemperature);
	}
	
	@Test
	public void testCalculateZoneTwoDemandTemperature() {
		// check simple cases (where HLP = 6, so there is no adjustment term)
		Assert.assertEquals(10d,
				new EnergyCalculatorCalculator().calculateZoneTwoDemandTemperature(
					0,
					6, 
					1,
					10), 0.01);
		
		Assert.assertEquals(5d,
				new EnergyCalculatorCalculator().calculateZoneTwoDemandTemperature(
					5,
					6, 
					1,
					10), 0.01);
		
		// give smaller hlp.
		Assert.assertEquals(6.25d,
				new EnergyCalculatorCalculator().calculateZoneTwoDemandTemperature(
						5,
					3, 
					1,
                        10), 0.01);
	}
	
	@Test
	public void testSiteExposure() {
		testSiteExposure("Site Exposure not implemented in SAP 2012", 1, EnergyCalculatorType.SAP2012, SiteExposureType.Exposed);
		testSiteExposure("Site Exposure not implemented in SAP 2012", 1, EnergyCalculatorType.SAP2012, SiteExposureType.Sheltered);
		
		testSiteExposure("Site Exposure varies in BREDEM 2012", 1.10, EnergyCalculatorType.BREDEM2012, SiteExposureType.Exposed);
		testSiteExposure("Site Exposure varies in BREDEM 2012", 1.05, EnergyCalculatorType.BREDEM2012, SiteExposureType.AboveAverage);
		testSiteExposure("Site Exposure varies in BREDEM 2012", 1.00, EnergyCalculatorType.BREDEM2012, SiteExposureType.Average);
		testSiteExposure("Site Exposure varies in BREDEM 2012", 0.95, EnergyCalculatorType.BREDEM2012, SiteExposureType.BelowAverage);
		testSiteExposure("Site Exposure varies in BREDEM 2012", 0.90, EnergyCalculatorType.BREDEM2012, SiteExposureType.Sheltered);
	}
	
	private void testSiteExposure(String message, double expected, EnergyCalculatorType calculatorType, SiteExposureType siteExposure) {
		IEnergyCalculatorHouseCase houseCase = mock(IEnergyCalculatorHouseCase.class);
		IInternalParameters parameters = mock(IInternalParameters.class);
		
		when(parameters.getCalculatorType()).thenReturn(calculatorType);
		when(houseCase.getSiteExposure()).thenReturn(siteExposure);
		
		assertEquals(
				message, 
				expected, 
				new EnergyCalculatorCalculator().getSiteExposureFactor(houseCase, parameters), 
				0
			);
	}
}
