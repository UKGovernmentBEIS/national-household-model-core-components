package uk.org.cse.nhm.hom.structure;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;

/**
 * Adds mutator methods to {@link IWall}
 * @author hinton
 *
 */
public interface IMutableWall extends IWall {
	/**
	 * @param type the new wall construction type for this wall
	 */
	public void setWallConstructionType(final WallConstructionType type);
	/**
	 * @param u the new u-value for this wall
	 */
	public void setUValue(final double u);

	public void setAirChangeRate(final double airChangeRate);
	/**
	 * Splits this wall into two parts, according to the proportion.  For example, if you invoke
	 * split(0.1), this wall will be reduced to 10% of its length, and the next wall will be the
	 * remaining 90%. The newly created wall will be initialized to have the same properties as this
	 * wall (u, k, construction type).
	 * <br />
	 * This method is intended as a convenience for specifying walls which are partially party walls
	 * 
	 * @param proportion
	 */
	public void split(final double proportion);

	/**
	 * Sets the wall's insulation thickness. If the thickness is 0, ensures that
	 * the type of insulation specified is not present on the wall. If the
	 * thickness is > 0, ensures that the type of insulation specified is
	 * present on the wall.
	 * 
	 * @param type
	 * @param thickness
	 */
	void setWallInsulationThicknessAndAddOrRemoveInsulation(WallInsulationType type, double thickness);

	/**
	 * Add insulation of the given thickness
	 * @param type insulation type to add
	 * @param thickness thickness to add (mm)
	 * @param rValue r-value (r / mm)
	 */
	public void addInsulation(final WallInsulationType type, final double thickness, final double rValue);
	
	/**
	 * This sets the thickness of the wall so that with current internal/external insulation it
	 * has the given overall thickness.
	 * @param newThickness
	 */
	public void setThicknessWithExistingInsulation(double newThickness);
}
