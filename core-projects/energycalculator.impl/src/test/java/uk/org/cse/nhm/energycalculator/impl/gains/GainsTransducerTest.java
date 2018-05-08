package uk.org.cse.nhm.energycalculator.impl.gains;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

public class GainsTransducerTest {

    public void testGainsPassThrough(final EnergyType gainsType, final double expectedProportion) {
        final GainsTransducer gt = new GainsTransducer(DefaultConstants.INSTANCE);

        final IEnergyState state = mock(IEnergyState.class);

        when(state.getTotalSupply(gainsType)).thenReturn(100d);

        gt.generate(null, null, null, state);

        verify(state).increaseSupply(EnergyType.GainsUSEFUL_GAINS, 100d * expectedProportion);
    }

    @Test
    public void testIndividualGainsPassedThrough() {
        testGainsPassThrough(EnergyType.GainsAPPLIANCE_GAINS, 1);
        testGainsPassThrough(EnergyType.GainsLIGHTING_GAINS, 0.85);
        testGainsPassThrough(EnergyType.GainsHOT_WATER_USAGE_GAINS, 0.25);
        testGainsPassThrough(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, 0.80);
        testGainsPassThrough(EnergyType.GainsCOOKING_GAINS, 1);
        testGainsPassThrough(EnergyType.GainsSOLAR_GAINS, 1);
    }
}
