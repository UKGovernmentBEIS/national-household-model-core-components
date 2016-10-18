package uk.org.cse.nhm.hom.emf.technologies.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;


public class ImmersionWaterHeaterTest {
	@Test
	public void testSimpleProperties() {
		final ImmersionHeaterImpl wh = new ImmersionHeaterImpl();
//		Assert.assertTrue(wh.getPriority() > 2);
		Assert.assertFalse(wh.isSolar());
	}

	public void testGenerateHotSystemLosses(final ElectricityTariffType tarrif, final boolean dualCoil, final double expectedHRF) {
		final ImmersionHeaterImpl wh = new ImmersionHeaterImpl();
		
		wh.setDualCoil(dualCoil);
		
		Assert.assertEquals(dualCoil, wh.isDualCoil());
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(parameters.getTarrifType()).thenReturn(tarrif);
		
		final IEnergyState state = mock(IEnergyState.class);
		final IWaterTank tank = mock(IWaterTank.class);
		
		when(tank.getVolume()).thenReturn(100d);
		when(parameters.getNumberOfOccupants()).thenReturn(2d);
		
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		
		wh.generateSystemGains(parameters, state, tank, false, 100d);
		
		verify(state).increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, 100d);
		verify(state).increaseElectricityDemand(expectedHRF, 100d);
	}
	
	public void testGenerateHotWater(final ElectricityTariffType tarrif, final boolean dualCoil, final double expectedHRF) {
		final ImmersionHeaterImpl wh = new ImmersionHeaterImpl();
		
		wh.setDualCoil(dualCoil);
		
		Assert.assertEquals(dualCoil, wh.isDualCoil());
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(parameters.getTarrifType()).thenReturn(tarrif);
		
		final IEnergyState state = mock(IEnergyState.class);
		final IWaterTank tank = mock(IWaterTank.class);
		
		when(tank.getVolume()).thenReturn(100d);
		when(parameters.getNumberOfOccupants()).thenReturn(2d);
		
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		
		wh.generateHotWaterAndPrimaryGains(parameters, state, tank, false, 1, 1, 1);
		
		verify(state).increaseSupply(EnergyType.DemandsHOT_WATER, 100d);
		verify(state).increaseElectricityDemand(expectedHRF, 100d);
	}
	
	@Test
	public void testCombinations() {
		testGenerateHotWater(ElectricityTariffType.FLAT_RATE, false, 1.0);
		testGenerateHotWater(ElectricityTariffType.FLAT_RATE, true, 1.0);
		testGenerateHotWater(ElectricityTariffType.ECONOMY_7, true, 0.158);
		testGenerateHotWater(ElectricityTariffType.ECONOMY_7, false, 0.7006);
		testGenerateHotWater(ElectricityTariffType.ECONOMY_10, true, 0.09899999999999999);
		testGenerateHotWater(ElectricityTariffType.ECONOMY_10, false, 0.2670666666666666);
		
		testGenerateHotSystemLosses(ElectricityTariffType.FLAT_RATE, false, 1.0);
		testGenerateHotSystemLosses(ElectricityTariffType.FLAT_RATE, true, 1.0);
		testGenerateHotSystemLosses(ElectricityTariffType.ECONOMY_7, true, 0.158);
		testGenerateHotSystemLosses(ElectricityTariffType.ECONOMY_7, false, 0.7006);
		testGenerateHotSystemLosses(ElectricityTariffType.ECONOMY_10, true, 0.09899999999999999);
		testGenerateHotSystemLosses(ElectricityTariffType.ECONOMY_10, false, 0.2670666666666666);
	}
}
