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
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.impl.demands.LightingDemand09;

public class LightingDemandTest {
	@Test
	public void testBaseDemand() {
		final LightingDemand09 ld = new LightingDemand09(DefaultConstants.INSTANCE);
				
		final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);
		
		when(hc.getFloorArea()).thenReturn(100d);
		
		final IInternalParameters params = mock(IInternalParameters.class);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(params.getClimate()).thenReturn(climate);
		when(params.getNumberOfOccupants()).thenReturn(2d);
		when(climate.getMonthOfYear()).thenReturn(1);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		ld.generate(hc, params, null, state);
		
		verify(state).increaseDemand(eq(EnergyType.DemandsVISIBLE_LIGHT), around(25.37, 0.1));
	}
	
	@Test
	public void testWithTransparentElements() {		
		final LightingDemand09 ld = new LightingDemand09(DefaultConstants.INSTANCE);
				
		ld.addTransparentElement(10d, 0, 0, 0, OvershadingType.NONE);
		
		final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);
		
		when(hc.getFloorArea()).thenReturn(100d);
		
		final IInternalParameters params = mock(IInternalParameters.class);
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(params.getClimate()).thenReturn(climate);
		
		when(params.getNumberOfOccupants()).thenReturn(2d);
		when(climate.getMonthOfYear()).thenReturn(1);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		ld.generate(hc, params, null, state);
		
		verify(state).increaseDemand(eq(EnergyType.DemandsVISIBLE_LIGHT), around(17.01, 0.1));
	}
}
