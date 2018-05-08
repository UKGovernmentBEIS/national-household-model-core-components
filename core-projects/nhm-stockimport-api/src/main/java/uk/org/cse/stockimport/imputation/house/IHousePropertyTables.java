package uk.org.cse.stockimport.imputation.house;

import java.util.Map;

/**
 * Contains table data for house properties including Living area fraction.
 *
 * @author richardt
 * @since 3.0
 */
public interface IHousePropertyTables {

    /**
     * Map's number of rooms to a living room fraction
     *
     * @return
     * @since 3.0
     */
    Map<Integer, Double> getlivingAreaFactions();

    /**
     * Default fraction to use if no fraction returned from
     * {@link IHousePropertyImputer#getLivingAreaFraction(int)}
     *
     * @return
     * @since 3.0
     */
    double getDefaultFraction();
}
