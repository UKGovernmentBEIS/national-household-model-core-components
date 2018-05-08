package uk.org.cse.nhm.simulation.measure;

import org.junit.Assert;

import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.simulation.measure.util.Util;

public class WaterHeatingSystemTestUtil {

    public static void testCombinedWaterHeatingSystem(final ITechnologyModel technologies) {
        ICentralHeatingSystem centralHeating = (ICentralHeatingSystem) technologies.getPrimarySpaceHeater();
        ICentralWaterSystem centralHotWater = (ICentralWaterSystem) technologies.getCentralWaterSystem();
        IMainWaterHeater mainWaterHeater = (IMainWaterHeater) centralHotWater.getPrimaryWaterHeater();
        Assert.assertSame(centralHeating.getHeatSource(), mainWaterHeater.getHeatSource());
    }

    public static void testStandardHotWaterCylinderPresent(final ITechnologyModel technologies, double insulationThickness, double volume) {
        ICentralWaterSystem centralHotWater = (ICentralWaterSystem) technologies.getCentralWaterSystem();
        Assert.assertNotNull("No cylinder found", centralHotWater.getStore());
        IWaterTank cylinder = (IWaterTank) centralHotWater.getStore();

        Assert.assertTrue(centralHotWater.isPrimaryPipeworkInsulated());
        Assert.assertTrue(cylinder.isThermostatFitted());
        Assert.assertTrue(cylinder.isFactoryInsulation());
        Assert.assertEquals(insulationThickness, cylinder.getInsulation(), Util.ERROR_DELTA);
        Assert.assertEquals(volume, cylinder.getVolume(), Util.ERROR_DELTA);
    }
}
