package uk.ac.ucl.hideem;

import com.google.common.base.Preconditions;

/**
 * Represents a health outcome of intervening. Contents are just some
 * values broken down by some categories. For simplicity, this is done
 * by indexing into some arrays using the ordinals for those
 * categories.
 */
public class HealthOutcome {
    private double[][] exposures;
    private double[][] qalys;
    private double[][][] costs;
    final int years;

    public HealthOutcome(final int years) {
        Preconditions.checkArgument(years > 0, "A health outcome must be defined over a positive number of years (%s)", years);
        this.years = years;
        this.exposures = new double[Exposure.values().length][2];
        this.qalys = new double[Disease.values().length][years];
        this.costs = new double[Disease.values().length]
            [HealthCost.values().length]
            [years];
    }
    
    public double initialExposure(final Exposure e) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][0];
    }
    
    public double finalExposure(final Exposure e) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][1];
    }

    public double qalys(final Disease disease, final int year) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkElementIndex(year, years);
        return qalys[disease.ordinal()][year];
    }
    
    public double cost(final Disease disease, final HealthCost cost, final int year) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkNotNull(cost);
        Preconditions.checkElementIndex(year, years);
        return costs[disease.ordinal()][cost.ordinal()][year];
    }
}
