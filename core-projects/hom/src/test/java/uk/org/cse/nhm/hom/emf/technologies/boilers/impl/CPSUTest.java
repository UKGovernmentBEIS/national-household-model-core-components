package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;
import uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl;

public class CPSUTest {

    @Test
    public void testTankLosses() {
        final ICPSU cpsu = IBoilersFactory.eINSTANCE.createCPSU();
        cpsu.setFuel(FuelType.MAINS_GAS);
        final IWaterTank tank = mock(WaterTankImpl.class);

        when(tank.getStandingLosses(any(IInternalParameters.class), eq(1.08d))).thenReturn(100d * 1.08d);

        cpsu.setStore(tank);

        final IInternalParameters parameters = mock(IInternalParameters.class);
        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        double d = cpsu.getContainedTankLosses(parameters);

        Assert.assertEquals(100d * 1.08, d, 0d);
    }

    // test disabled until functionality is implemented.
    public void testTankLossesWithTimeControl() {
        final ICPSU cpsu = IBoilersFactory.eINSTANCE.createCPSU();
        cpsu.setFuel(FuelType.MAINS_GAS);
        final IWaterTank tank = mock(WaterTankImpl.class);

        when(tank.getStandingLosses(any(IInternalParameters.class), 1.08d * 0.81d)).thenReturn(100d * 1.08d * 0.81d);

        cpsu.setStore(tank);

        final IInternalParameters parameters = mock(IInternalParameters.class);
        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        double d = cpsu.getContainedTankLosses(parameters);

        Assert.assertEquals(100d * 1.08 * 0.81d, d, 0d);
    }
}
