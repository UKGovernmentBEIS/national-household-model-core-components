package uk.org.cse.stockimport.imputation.walls;

import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;

/**
 * Interface for thing which imputes the u-values, k-values etc. for walls.
 *
 * @author hinton
 * @since 1.0
 */
public interface IWallPropertyImputer {
	/**
	 * Get the U-value for a wall based on these attributes for the wall and the property
	 * @param earliestBuild earliest possible build date for the house
	 * @param latestBuild latest possible build date for the house
	 * @param constructionType wall's construction type
	 * @param country where the house is
	 * @param insulationThicknesses a map from applied insulation types to thicknesses in mm
	 * @param wallThickness thickness of the wall, or zero if missing or whatever.
	 * @return a u value for the wall
	 * @since 1.0
	 */
	public double getUValue(final Band ageBand,
			final Country country,
			final WallConstructionType constructionType,
			final Map<WallInsulationType, Double> insulationThicknesses,
			final double wallThickness);

	/**
	 * Get the U-value for a wall based on these attributes for the wall and the property.
	 *
	 * Use {@link #getWallThickness(DateTime, DateTime, RegionType, WallConstructionType, Set)} to get thickness.
	 *
	 * @param earliestBuild earliest possible build date for the house
	 * @param latestBuild latest possible build date for the house
	 * @param constructionType wall's construction type
	 * @param country where the house is
	 * @param insulations a set of insulations present
	 * @return a u value for the wall
	 * @since 1.0
	 */
	public double getUValue(final SAPAgeBandValue.Band ageBand,
			final Country country,
			final WallConstructionType constructionType,
			final Set<WallInsulationType> insulations);

	/**
	 * Get the thickness of a wall based on the given set of attributes
	 * @param earliestDate
	 * @param latestDate
	 * @param country
	 * @param construction
	 * @param insulations
	 * @return
	 * @since 1.0
	 */
	public double getWallThickness(SAPAgeBandValue.Band ageBand, Country country,
			WallConstructionType construction,
			Set<WallInsulationType> insulations);

	/**
     * @since 1.0
     */
    double getAirChangeRate(WallConstructionType constructionType);
}
