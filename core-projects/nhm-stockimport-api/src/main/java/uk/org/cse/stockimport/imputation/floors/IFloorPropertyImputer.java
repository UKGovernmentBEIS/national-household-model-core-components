package uk.org.cse.stockimport.imputation.floors;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.walls.IWallPropertyImputer;

/**
 * Helper for imputing the properties of floors
 *
 * @author hinton
 * @since 1.0
 */
public interface IFloorPropertyImputer {

    /**
     * Calculate the U value of a floor with the given construction type and so
     * on
     *
     * You may want to use an {@link IWallPropertyImputer} to get wall
     * thickness.
     *
     * @param constructionType
     * @param wallThickness
     * @param insulationThickness
     * @param exposedPerimeter
     * @param area
     * @return
     * @since 1.0
     */
    double getGroundFloorUValue(FloorConstructionType constructionType,
            double wallThickness, double insulationThickness,
            double exposedPerimeter, double area);

    /**
     * Get the u value for an exposed upper floor
     *
     * @param ageBand
     * @return
     * @since 1.0
     */
    double getExposedFloorUValue(Band ageBand, boolean isInsulated);

    /**
     * Get the floor insulation thickness for a floor constructed in the given
     * place and time
     *
     * @param constructionInterval
     * @param region
     * @param constructionType
     * @return
     * @since 1.0
     */
    double getFloorInsulationThickness(Band ageBand,
            Country country, FloorConstructionType constructionType);
}
