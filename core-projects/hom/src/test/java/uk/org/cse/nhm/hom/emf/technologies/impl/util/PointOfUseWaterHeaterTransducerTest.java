package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class PointOfUseWaterHeaterTransducerTest {

    @Test
    public void testElectric() {
        final PointOfUseWaterHeaterTransducer t = new PointOfUseWaterHeaterTransducer(1, FuelType.ELECTRICITY, 0.5, 1, false);

        final IInternalParameters parameters = mock(IInternalParameters.class);

        when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.ECONOMY_7);
        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        final IEnergyState state = mock(IEnergyState.class);

        when(state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, 0.5)).thenReturn(100d);

        t.generate(null, parameters, null, state);

        verify(state).increaseElectricityDemand(DefaultConstants.INSTANCE.get(SplitRateConstants.DEFAULT_FRACTIONS, ElectricityTariffType.ECONOMY_7), 100d);

        verify(state).increaseSupply(EnergyType.DemandsHOT_WATER, 100d);

        verify(state).increaseSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS, 100d);
    }

    @Test
    public void testNonElectric() {
        final PointOfUseWaterHeaterTransducer t = new PointOfUseWaterHeaterTransducer(1, FuelType.MAINS_GAS, 0.5, 0.8, false);

        final IInternalParameters parameters = mock(IInternalParameters.class);

        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        final IEnergyState state = mock(IEnergyState.class);

        when(state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, 0.5)).thenReturn(100d);

        t.generate(null, parameters, null, state);

        verify(state).increaseDemand(EnergyType.FuelGAS, 100d / 0.8);

        verify(state).increaseSupply(EnergyType.DemandsHOT_WATER, 100d);

        verify(state).increaseSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS, 100d);
    }
}
