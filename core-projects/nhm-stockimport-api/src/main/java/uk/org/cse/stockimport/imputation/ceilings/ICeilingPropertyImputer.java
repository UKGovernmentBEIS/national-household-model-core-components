package uk.org.cse.stockimport.imputation.ceilings;

import uk.org.cse.nhm.hom.components.fabric.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

/**
 * Impute u, k values etc. for ceilings (inc. roofs)
 * 
 * @author hinton
 * @since 1.0
 */
public interface ICeilingPropertyImputer {
	/**
	 * Get the U value for an external roof surface.
	 * @param constructionType
	 * @param insulationThickness
	 * @param roomInRoof
	 * @return
	 * @since 1.0
	 */
	public double getRoofUValue(final RoofConstructionType constructionType, final double insulationThickness, final boolean roomInRoof);
	
	/**
	 * @param constructionInterval
	 * @param constructionType
	 * @param roomInRoof
	 * @return the u value for a roof with unknown insulation thickness constructed in the given interval, with the given construction type.
	 * @since 1.0
	 */
	public double getRoofUValue(final Band ageBand, final RoofConstructionType constructionType, final boolean roomInRoof);

}
