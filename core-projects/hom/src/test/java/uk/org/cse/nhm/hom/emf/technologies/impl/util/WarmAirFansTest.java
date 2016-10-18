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
import uk.org.cse.nhm.hom.testutil.TestUtil;

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
		
		// pump and fan usage should be 6 kWh / year, for the 9 months heating is on
		// so that is
		// 6000 * (3600) kWs / ((9 / 12) * 365 * 24 * 3600) = 12 * 6000 / (9 * 365 * 24) = .91324200913242009132
		
		verify(state).increaseElectricityDemand(eq(0.8d), TestUtil.around(.91324200, 0.001));
	
		verify(state).increaseSupply(eq(EnergyType.GainsPUMP_AND_FAN_GAINS), TestUtil.around(.91324200, 0.001));
	}
}
