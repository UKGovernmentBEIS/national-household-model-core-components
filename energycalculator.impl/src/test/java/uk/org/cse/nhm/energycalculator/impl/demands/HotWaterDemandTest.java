package uk.org.cse.nhm.energycalculator.impl.demands;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.energycalculator.impl.testutil.TestUtil.around;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.impl.demands.HotWaterDemand09;

public class HotWaterDemandTest {
	@Test
	public void testDemand1() {
		final HotWaterDemand09 hwd = new HotWaterDemand09(DefaultConstants.INSTANCE);
		
		final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);
		
		when(hc.getFloorArea()).thenReturn(100d);
		
		final IInternalParameters params = mock(IInternalParameters.class);
		
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		
		when(params.getNumberOfOccupants()).thenReturn(2d);
		when(params.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);
		when(params.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(climate.getMonthOfYear()).thenReturn(1);
		when(params.getClimate()).thenReturn(climate);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		hwd.generate(hc, params, null, state);
		
		final double volume = 1.1*(25.0 * 2 + 36);
		
		verify(state).increaseDemand(eq(EnergyType.DemandsHOT_WATER_VOLUME), around(volume, 0.01));
		verify(state).increaseDemand(eq(EnergyType.DemandsHOT_WATER), around(volume * 41.2 * 0.85 * (4.19/3600) * (1000 / 24.0), 0.01));
	}
}
