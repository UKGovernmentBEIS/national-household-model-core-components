package uk.org.cse.nhm.reporting.standard.fuel;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.IEnergyReport;

/**
 * For energy reporting: we output the value/house for most fuels, but merge all
 * the biomass fuel types into one column.
 * https://cseresearch.atlassian.net/browse/NHM-567
 *
 * @author glenns
 * @since 3.5.0
 */
public class BiomassFuels {

    private static final Set<FuelType> biomassFuels = ImmutableSet.of(FuelType.BIOMASS_PELLETS, FuelType.BIOMASS_WOOD, FuelType.BIOMASS_WOODCHIP);
    private static final Set<FuelType> nonBiomassFuels = Sets.difference(
            ImmutableSet.copyOf(FuelType.values()),
            biomassFuels);

    public static Set<FuelType> getBiomassFuels() {
        return biomassFuels;
    }

    public static Set<FuelType> getNonBiomassFuels() {
        return nonBiomassFuels;
    }

    public static double getBiomassEnergyUsed(final IEnergyReport energyReport) {
        double accum = 0;
        for (final FuelType fuel : getBiomassFuels()) {
            accum += energyReport.getEnergyUsedByFuel(fuel);
        }
        return accum;
    }

    public static double getBiomassCost(final Map<FuelType, Double> costs) {
        double accum = 0;
        for (final FuelType fuel : getBiomassFuels()) {
            accum += costs.get(fuel);
        }
        return accum;
    }
}
