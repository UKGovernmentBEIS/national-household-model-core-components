package uk.ac.ucl.hideem;

import com.google.common.base.Preconditions;

/**
 * Contents are just some values broken down by some categories. For
 * simplicity, this is done by indexing into some arrays using the
 * ordinals for those categories.
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

    public void setInitialExposure(final Exposure e, final double d) {
        Preconditions.checkNotNull(e);
        this.exposures[e.ordinal()][0] = d;
    }

    public void setFinalExposure(final Exposure e, final double d) {
        Preconditions.checkNotNull(e);
        this.exposures[e.ordinal()][1] = d;
    }

    public void setQalys(final Disease disease, final int year, final double q) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkElementIndex(year, years);
        this.qalys[disease.ordinal()][year] = q;
    }

    public void setCost(final Disease disease, final HealthCost cost, final int year, final double c) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkNotNull(cost);
        Preconditions.checkElementIndex(year, years);
        this.costs[disease.ordinal()][cost.ordinal()][year] = c;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();

        sb.append("Health Outcome for " + years + " years:\n");
        sb.append("Exposures:\n");
        for (final Exposure e : Exposure.values()) {
            sb.append(String.format("%s: %g -> %g\n", e,
                                    exposures[e.ordinal()][0],
                                    exposures[e.ordinal()][1]));
        }
        sb.append("Qalys:\n");
        sb.append("Year");
        for (final Disease d : Disease.values()) {
            sb.append("\t"+ d);
        }
        sb.append("\n");
        for (int i = 0; i<years; i++) {
            sb.append(String.format("%d", i));
            for (final Disease d : Disease.values()) {
                sb.append(String.format("\t%g", qalys[d.ordinal()][i]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
