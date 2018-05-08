package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;
import uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.testutil.TestUtil;

public class ElectricCPSUTest {

    /**
     * This is similar to the test in {@link ElectricCPSUTest2}, except it sets
     * up the wider environment from which the transducer will get the
     * parameters to go into the high rate fraction calculation.
     */
    @Test
    public void testAggregatingTransducer() {
        final CPSUImpl cpsu = (CPSUImpl) IBoilersFactory.eINSTANCE.createCPSU();

        final IWaterTank tank = ITechnologiesFactory.eINSTANCE.createWaterTank();

        cpsu.setStore(tank);

        cpsu.setFuel(FuelType.ELECTRICITY);

        tank.setVolume(100);

        cpsu.setWinterEfficiency(Efficiency.fromDouble(1));
        cpsu.setSummerEfficiency(Efficiency.fromDouble(1));

        cpsu.setStoreTemperature(85);

        // need to be able to look inside cpsu.
        final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);
        final IEnergyState state = mock(IEnergyState.class);
        final IInternalParameters parameters = mock(IInternalParameters.class);
        final ISpecificHeatLosses losses = mock(ISpecificHeatLosses.class);

        final IEnergyTransducer transducer = cpsu.createWaterTransducer(EnergyType.FuelINTERNAL1);

        when(parameters.getInternalEnergyType(cpsu)).thenReturn(EnergyType.FuelINTERNAL1);

        Assert.assertEquals(ServiceType.WATER_HEATING, transducer.getServiceType());
        // setup mocks
        when(state.getTotalSupply(EnergyType.HackMEAN_INTERNAL_TEMPERATURE)).thenReturn(10d);

        when(state.getTotalDemand(EnergyType.FuelINTERNAL1, ServiceType.WATER_HEATING)).thenReturn(300d);
        when(state.getTotalDemand(EnergyType.FuelINTERNAL1, ServiceType.PRIMARY_SPACE_HEATING)).thenReturn(4000d);

        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
        final ISeasonalParameters cp = mock(ISeasonalParameters.class);
        when(cp.getExternalTemperature()).thenReturn(2d);
        when(parameters.getClimate()).thenReturn(cp);

        when(losses.getSpecificHeatLoss()).thenReturn(150d);
        when(state.getTotalSupply(EnergyType.DemandsHEAT, ServiceType.INTERNALS)).thenReturn(500d);

        transducer.generate(hc, parameters, losses, state);

        verify(state).increaseElectricityDemand(TestUtil.around(0.1124, 0.0001), TestUtil.around(300, 0.0001));
    }

    @Test
    public void testTankLosses() {
        final ICPSU cpsu = IBoilersFactory.eINSTANCE.createCPSU();
        cpsu.setFuel(FuelType.ELECTRICITY);
        final IWaterTank tank = mock(WaterTankImpl.class);

        when(tank.getStandingLosses(any(IInternalParameters.class), eq(1d))).thenReturn(100d);

        cpsu.setStore(tank);

        final IInternalParameters parameters = mock(IInternalParameters.class);
        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        final double d = cpsu.getContainedTankLosses(parameters);

        Assert.assertEquals(100d, d, 0d);
    }

    @Test
    public void testGetHighRateFraction() {
        final CPSUImpl cpsu = (CPSUImpl) IBoilersFactory.eINSTANCE.createCPSU();
        cpsu.setFuel(FuelType.ELECTRICITY);
        final IWaterTank tank = mock(WaterTankImpl.class);

        when(tank.getStandingLosses(any(IInternalParameters.class), eq(1d))).thenReturn(100d);
        when(tank.getVolume()).thenReturn(100d);

        cpsu.setStoreTemperature(85);
        cpsu.setStore(tank);

        // branch 1
        Assert.assertEquals(0.1124, cpsu.getHighRateFraction(DefaultConstants.INSTANCE,
                2,
                10,
                150,
                500,
                300,
                4000), 0.001);

        // branch 2
        Assert.assertEquals(0.02147, cpsu.getHighRateFraction(DefaultConstants.INSTANCE,
                2.0000000000000004,
                10,
                92.3399999999999964,
                500,
                300,
                4000), 0.0001);
    }
}
