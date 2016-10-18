package uk.org.cse.stockimport.imputation.walls;

import java.util.Set;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band;

public interface IWallThicknessImputer {

	/**
	 * @since 1.0
	 */
	public abstract double getWallThickness(Band ageBand,
			RegionType region, WallConstructionType construction,
			Set<WallInsulationType> insulations);

}