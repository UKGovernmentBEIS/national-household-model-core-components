package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

public class DirectElectricHeatTransducerTest {

    @Test
    public void testTransduction() {
        final DirectElectricHeatTransducer transducer = new DirectElectricHeatTransducer(1, 1);
        final IEnergyState state = mock(IEnergyState.class);
        final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);
        final IInternalParameters p = mock(IInternalParameters.class);
        final ISpecificHeatLosses sh = mock(ISpecificHeatLosses.class);

        final IConstants constants = mock(IConstants.class);
        when(constants.get(SplitRateConstants.DIRECT_ELECTRIC_FRACTIONS, ElectricityTariffType.FLAT_RATE)).thenReturn(0.5);
        when(p.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);
        when(p.getConstants()).thenReturn(constants);
        when(state.getBoundedTotalHeatDemand(1d)).thenReturn(100.0);

        transducer.generate(hc, p, sh, state);

        verify(state).getBoundedTotalHeatDemand(1);
        verify(state).increaseSupply(EnergyType.DemandsHEAT, 100.0);

        verify(state, atLeastOnce()).increaseElectricityDemand(0.5, 100d);

        verifyNoMoreInteractions(state);
    }
}
