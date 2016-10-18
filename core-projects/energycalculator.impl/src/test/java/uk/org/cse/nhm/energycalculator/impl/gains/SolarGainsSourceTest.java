package uk.org.cse.nhm.energycalculator.impl.gains;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ITransparentElement;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.impl.gains.SolarGainsSource;

public class SolarGainsSourceTest {
	@Test
	public void testSolarGains() {
		final ISeasonalParameters climate = mock(ISeasonalParameters.class);
		
		when(climate.getSolarFlux(0, 0)).thenReturn(100d);
		when(climate.getSolarFlux(25,25)).thenReturn(50d);
		
		final IInternalParameters p = mock(IInternalParameters.class);
		when(p.getClimate()).thenReturn(climate);
		
		final SolarGainsSource sgs = new SolarGainsSource(DefaultConstants.INSTANCE, EnergyType.GainsSOLAR_GAINS);
		
		final ITransparentElement w1 = mock(ITransparentElement.class, "Average overshading");
		final ITransparentElement w2 = mock(ITransparentElement.class, "Heavy overshading");
		
		when(w1.getOvershading()).thenReturn(OvershadingType.AVERAGE); // 0.77
		when(w2.getOvershading()).thenReturn(OvershadingType.HEAVY); // 0.3
		
		when(w2.getHorizontalOrientation()).thenReturn(25d);
		when(w2.getVerticalOrientation()).thenReturn(25d);
		
		when(w1.getSolarGainTransmissivity()).thenReturn(100d);
		when(w2.getSolarGainTransmissivity()).thenReturn(100d);
		
		sgs.addTransparentElement(w1.getVisibleLightTransmittivity(), w1.getSolarGainTransmissivity(), w1.getHorizontalOrientation(), w1.getVerticalOrientation(), w1.getOvershading());
		sgs.addTransparentElement(w2.getVisibleLightTransmittivity(), w2.getSolarGainTransmissivity(), w2.getHorizontalOrientation(), w2.getVerticalOrientation(), w2.getOvershading());
		
		final IEnergyState state = mock(IEnergyState.class);
		
		sgs.generate(null, p, null, state);
		
		verify(state).increaseSupply(EnergyType.GainsSOLAR_GAINS,0.9 * (100 * 0.77 * 100 + 100 * 0.3 * 50));
	}
}
