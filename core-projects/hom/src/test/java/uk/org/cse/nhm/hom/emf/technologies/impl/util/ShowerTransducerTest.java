package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;

public class ShowerTransducerTest {
	@Test
	public void testElectricShower() {
		final ShowerTransducer shower = new ShowerTransducer(ShowerTransducer.DEFAULT_POWER);
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		
		final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);
		final ISpecificHeatLosses losses = mock(ISpecificHeatLosses.class);
		final IEnergyState state = mock(IEnergyState.class);
		
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);
		
				when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		when(state.getTotalDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d);
		when(state.getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME)).thenReturn(100d);
		

			shower.generate(hc, parameters, losses, state);
		
		
		final double hwd = 100 * DefaultConstants.INSTANCE.get(HeatingSystemConstants.SHOWER_DEMAND_PROPORTION);
		final double hwv = 100 * DefaultConstants.INSTANCE.get(HeatingSystemConstants.SHOWER_VOLUME_PROPORTION) * ShowerTransducer.DEFAULT_POWER;
		
		verify(state).increaseSupply(EnergyType.DemandsHOT_WATER, hwd);
		verify(state).increaseSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS, hwv);
		verify(state).increaseElectricityDemand(1.0, hwv);
		
	}
}
