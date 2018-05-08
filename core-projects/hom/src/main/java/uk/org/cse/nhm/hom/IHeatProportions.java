package uk.org.cse.nhm.hom;

import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;

public interface IHeatProportions {

    public double spaceHeatingProportion(final ISpaceHeater heatSource);

    public boolean providesHotWater(final IWaterHeater heatSource);
}
