package uk.org.cse.nhm.energycalculator.impl.appliances;

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
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;

public class AppliancesTest {
	@Test
	public void testApplianceDemand() {
		final Appliances09 a = new Appliances09(DefaultConstants.INSTANCE);
		
		final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);
		
		when(hc.getFloorArea()).thenReturn(100d);
		
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		when(climate.getMonthOfYear()).thenReturn(1);
		
		final IInternalParameters params = mock(IInternalParameters.class);
		when(params.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
		
		when(params.getClimate()).thenReturn(climate);
		
		when(params.getNumberOfOccupants()).thenReturn(2d);
		when(params.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);
	
		final IEnergyState state = mock(IEnergyState.class);
		
		a.generate(hc, params, null, state);
		
		verify(state).increaseElectricityDemand(eq(1.0), around(329.6, 0.1));
		verify(state).increaseSupply(eq(EnergyType.GainsAPPLIANCE_GAINS), around(329.6, 0.1));
	}
}
