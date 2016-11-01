package uk.org.cse.stockimport.imputation.walls;

import java.util.Map;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;

public interface IWallUValueImputer {

	/**
	 * Imputes the U value using Tables S6, S7, S8, S5.1.1, S5.1.2
	 * @see IWallPropertyImputer#getUValue(DateTime, DateTime, RegionType, WallConstructionType, Double, Double).
	 * @since 1.0
	 */
	public abstract double getUValue(SAPAgeBandValue.Band ageBand,
			RegionType region, WallConstructionType constructionType,
			Map<WallInsulationType, Double> insulationThicknesses,
			double wallThickness);

}