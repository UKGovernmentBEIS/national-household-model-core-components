package uk.org.cse.nhm.energycalculator.api.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

public class ElectricHeatTransducerTest {

    @Test
    public void testElectricHeatTransducer() {
        final ElectricHeatTransducer eht = new ElectricHeatTransducer(0.5, 99) {
            @Override
            protected double getHighRateFraction(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
                return 0.33;
            }
        };

        Assert.assertEquals(99, eht.getPriority());

        final IEnergyState es = mock(IEnergyState.class);

        when(es.getBoundedTotalHeatDemand(0.5d)).thenReturn(123d);

        eht.generate(null, null, null, es);

        verify(es).increaseSupply(EnergyType.DemandsHEAT, 123d);

        verify(es).increaseElectricityDemand(0.33, 123d);
    }
}
