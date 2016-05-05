package uk.org.cse.stockimport.imputation.walls;

import java.util.Set;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;

public interface IWallKValueImputer {

	/**
	 * @since 1.0
	 */
	public abstract double getKValue(WallConstructionType constructionType,
			Set<WallInsulationType> insulationTypes);

	/**
	 * @since 1.0
	 */
	public abstract double getInternalWallKValue();

}