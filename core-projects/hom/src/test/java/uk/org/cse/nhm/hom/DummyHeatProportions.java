package uk.org.cse.nhm.hom;

import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;

public class DummyHeatProportions implements IHeatProportions {

    public static DummyHeatProportions instance = new DummyHeatProportions();

    @Override
    public double spaceHeatingProportion(ISpaceHeater heatSource) {
        return 1;
    }

    @Override
    public boolean providesHotWater(IWaterHeater heatSource) {
        return true;
    }

}
