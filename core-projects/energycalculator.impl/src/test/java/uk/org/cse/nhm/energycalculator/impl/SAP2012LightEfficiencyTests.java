package uk.org.cse.nhm.energycalculator.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.energycalculator.api.impl.SAP2012LightingTransducer.CFL_EFFICIENCY;
import static uk.org.cse.nhm.energycalculator.api.impl.SAP2012LightingTransducer.INCANDESCENT_EFFICIENCY;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.SAP2012LightingTransducer;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

/**
 * SAP2012LightEfficiencyTests.
 *
 * @author trickyBytes
 */
@RunWith(MockitoJUnitRunner.class)
public class SAP2012LightEfficiencyTests {

    @Mock
    IEnergyCalculatorHouseCase house;
    @Mock
    IInternalParameters parameters;
    @Mock
    ISpecificHeatLosses losses;
    @Mock
    IEnergyState state;

    @Before
    public void setUpTests() {
        when(state.getBoundedTotalDemand(EnergyType.DemandsVISIBLE_LIGHT, 1d)).thenReturn(1d);
    }

    @Test
    public void testIfMoreEfficiencyThanIncandescentThenCFL() throws Exception {
        double multiplier = INCANDESCENT_EFFICIENCY - 0.1;
        double proportion = 1;
        double expectedMultiplier = CFL_EFFICIENCY;

        when(state.getBoundedTotalDemand(EnergyType.DemandsVISIBLE_LIGHT, 1d)).thenReturn(1d);
        when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);

        SAP2012LightingTransducer lightTransducer = build(proportion, multiplier);
        lightTransducer.generate(house, parameters, losses, state);
        verify(state).increaseElectricityDemand(12, proportion * expectedMultiplier);
    }

    @Test
    public void testShouldClampEfficiencyToIncandescentfficiencyIfLower() throws Exception {
        double multiplier = INCANDESCENT_EFFICIENCY + 1;
        double proportion = 1;
        double expectedMultiplier = INCANDESCENT_EFFICIENCY;

        when(state.getBoundedTotalDemand(EnergyType.DemandsVISIBLE_LIGHT, 1d)).thenReturn(1d);
        when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);

        SAP2012LightingTransducer lightTransducer = build(proportion, multiplier);
        lightTransducer.generate(house, parameters, losses, state);
        verify(state).increaseElectricityDemand(12, proportion * expectedMultiplier);
    }

    @Test
    public void testShouldClampEfficiencyToCFLIfHigher() throws Exception {
        double multiplier = CFL_EFFICIENCY - 1;
        double proportion = 1;
        double expectedMultiplier = CFL_EFFICIENCY;

        when(state.getBoundedTotalDemand(EnergyType.DemandsVISIBLE_LIGHT, 1d)).thenReturn(1d);
        when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);

        SAP2012LightingTransducer lightTransducer = build(proportion, multiplier);
        lightTransducer.generate(house, parameters, losses, state);
        verify(state).increaseElectricityDemand(12, proportion * expectedMultiplier);
    }

    protected SAP2012LightingTransducer build(double proportion, double multiplier) {
        return new SAP2012LightingTransducer("xx", proportion, multiplier, new double[]{12, 12});
    }
}
