package uk.org.cse.stockimport.imputation.floors;

import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.walls.IWallPropertyImputer;

/**
 * Helper for imputing the properties of floors
 * @author hinton
 * @since 1.0
 */
public interface IFloorPropertyImputer {
	/**
	 * Calculate the U value of a floor with the given construction type and so on
	 * 
	 * You may want to use an {@link IWallPropertyImputer} to get wall thickness.
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
	 * @param ageBand
	 * @return
     * @since 1.0	
	 */
	double getExposedFloorUValue(Band ageBand, boolean isInsulated);
	
	/**
	 * Get the K value for an exposed floor
	 * @param isInsulated
	 * @return
     * @since 1.0
	 */
	double getExposedFloorKValue(boolean isInsulated);
	
	/**
	 * Get the K value for a party or internal floor
	 * 
	 * @return
     * @since 1.0
	 */
	double getPartyFloorKValue();
	
	/**
	 * Get the K value for a ground floor of the given construction type.
	 * @param constructionType
	 * @return
     * @since 1.0
	 */
	double getGroundFloorKValue(final FloorConstructionType constructionType);
	
	/**
	 * Lookup the infiltration rate for a floor constructed in the given interval with the given type
	 * @param ageBand
	 * @param constructionType
	 * @return
     * @since 1.0
	 */
	double getFloorInfiltration(Band ageBand,
			FloorConstructionType constructionType);

	/**
	 * Get the floor insulation thickness for a floor constructed in the given place and time
	 * @param constructionInterval
	 * @param region
	 * @param constructionType
	 * @return
     * @since 1.0
	 */
	double getFloorInsulationThickness(Band ageBand,
			RegionType region, FloorConstructionType constructionType);

	/**
	 * Get the probable floor construction type for a floor constructed in a given interval.
	 * 
	 * Note that the CHM has a slightly more detailed behaviour than this which should be elsewhere?
	 * 
	 * @param constructionInterval
	 * @return
     * @since 1.0
	 */
	FloorConstructionType getFloorConstructionType(Band ageBand);
}
