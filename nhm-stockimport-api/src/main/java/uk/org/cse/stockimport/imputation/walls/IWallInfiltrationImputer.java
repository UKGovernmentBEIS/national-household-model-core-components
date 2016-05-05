package uk.org.cse.stockimport.imputation.walls;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;

public interface IWallInfiltrationImputer {

	/**
	 * @since 1.0
	 */
	public abstract double getAirChangeRate(
			WallConstructionType constructionType);

}