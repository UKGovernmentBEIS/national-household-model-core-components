package uk.org.cse.nhm.hom.emf.technologies.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.hom.testutil.TestUtil.around;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;

public class SolarWaterHeaterTest {
	@Test
	public void testSimpleProperties() {
		final SolarWaterHeaterImpl swh = new SolarWaterHeaterImpl();
		Assert.assertTrue(swh.isSolar());
	}
	
	@Test
	public void testPerformanceFactor() {
		final SolarWaterHeaterImpl swh = new SolarWaterHeaterImpl();
		
		final double[][] values = 
			new double[][] {
				new double[] {0.6, 3, 0.8015},
				new double[] {0.75, 6, 0.7148},
				new double[] {0.9, 20, 0.4530}
			};
		
		for (final double[] test : values) {
			swh.setZeroLossEfficiency(test[0]);
			swh.setLinearHeatLossCoefficient(test[1]);
			Assert.assertEquals(test[2], swh.getCollectorPerformanceFactor(DefaultConstants.INSTANCE), 0.001);
		}
	}
	
	@Test
	public void testGenerationWithStoreInTank() {
		final SolarWaterHeaterImpl swh = new SolarWaterHeaterImpl();
	
		swh.setArea(1d);
		swh.setUsefulAreaRatio(1d);
		
		swh.setZeroLossEfficiency(1);
		swh.setLinearHeatLossCoefficient(1);
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(climate.getSolarFlux(0, 0)).thenReturn(200000d);
		when(parameters.getClimate()).thenReturn(climate);
		
		final IEnergyState state = mock(IEnergyState.class);
		when(state.getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME)).thenReturn(200d);
		when(state.getTotalDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		
		final IWaterTank tank = mock(IWaterTank.class);
		when(tank.getVolume()).thenReturn(100d);
		when(tank.getSolarStorageVolume()).thenReturn(100d);
		when(tank.isThermostatFitted()).thenReturn(true);
		
		when(tank.getVolume()).thenReturn(100d);
		
		swh.generateHotWaterAndPrimaryGains(parameters, state, tank, false, 1.0d, DefaultConstants.INSTANCE.get(HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES), 0);
		// check that the right amount is generated
		
		verify(state).increaseSupply(eq(EnergyType.DemandsHOT_WATER), around(94.60d, 0.01));
	}
	
	@Test
	public void testGenerationWithoutStoreInTank() {
		final SolarWaterHeaterImpl swh = new SolarWaterHeaterImpl();
	
		swh.setArea(1d);
		swh.setUsefulAreaRatio(1d);
		
		swh.setZeroLossEfficiency(1);
		swh.setLinearHeatLossCoefficient(1);
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(climate.getSolarFlux(0, 0)).thenReturn(200000d);
		when(parameters.getClimate()).thenReturn(climate);
		
		final IEnergyState state = mock(IEnergyState.class);
		when(state.getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME)).thenReturn(200d);
		when(state.getTotalDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		
		final IWaterTank tank = mock(IWaterTank.class);
		when(tank.getVolume()).thenReturn(300d);
		when(tank.getSolarStorageVolume()).thenReturn(0d);
		when(tank.isThermostatFitted()).thenReturn(true);
		
		when(tank.getVolume()).thenReturn(100d);
		
		swh.generateHotWaterAndPrimaryGains(parameters, state, tank, false, 1.0d, DefaultConstants.INSTANCE.get(HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES), 0);
		// check that the right amount is generated
		
		verify(state).increaseSupply(eq(EnergyType.DemandsHOT_WATER), around(68d, 0.5));
	}
}
