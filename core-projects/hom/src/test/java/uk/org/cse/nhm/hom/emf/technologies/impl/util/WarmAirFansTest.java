package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

public class WarmAirFansTest {
	@Test
	public void testWarmAirFans() {
		final WarmAirFans f = new WarmAirFans();

		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.ECONOMY_10);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

		final IEnergyState state = mock(IEnergyState.class);

		final IEnergyCalculatorHouseCase house = mock(IEnergyCalculatorHouseCase.class);

		when(house.getHouseVolume()).thenReturn(10d);

		f.generate(house ,parameters , null, state );

		// pump and fan usage should be 6 kWh / year
		// (we no longer pro-rate this - it is independent of the time that the heating is on for)
		verify(state).increaseElectricityDemand(eq(0.8d), eq(0.6));

		verify(state).increaseSupply(eq(EnergyType.GainsPUMP_AND_FAN_GAINS), eq(0.6));
	}
}
