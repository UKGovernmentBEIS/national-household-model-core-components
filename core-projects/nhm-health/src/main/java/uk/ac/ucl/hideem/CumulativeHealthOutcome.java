package uk.ac.ucl.hideem;

import com.google.common.base.Supplier;

/**
 * A health outcome class which cares only about accumulated effects.
 */
public class CumulativeHealthOutcome extends HealthOutcome {
    // totalled by disease and year.
    private double[][] annualMortality;
    private double[][] annualMorbidity;
    private double[][] annualCost;

    public CumulativeHealthOutcome(int horizon) {
        super(horizon);
        annualMortality = new double[Disease.Type.values().length][horizon];
        annualMorbidity = new double[Disease.Type.values().length][horizon];
        annualCost      = new double[Disease.Type.values().length][horizon];
    }

    public void addEffects(final Disease.Type disease, final int year, final Person whom,
                           final double mortality, final double morbidity, final double cost) {
        final int o = disease.ordinal();
        annualMorbidity[o][year] += morbidity;
        annualMortality[o][year] += mortality;
        annualCost[o][year] += cost;
    }

    public double cost(final Disease.Type disease, final int year) {
        return annualCost[disease.ordinal()][year];
    }

    public double morbidity(final Disease.Type disease, final int year) {
        return annualMorbidity[disease.ordinal()][year];
    }

    public double mortality(final Disease.Type disease, final int year) {
        return annualMortality[disease.ordinal()][year];
    }

    static class P implements Supplier<CumulativeHealthOutcome> {
        private final int horizon;
        public P(int horizon) { this.horizon = horizon; }
        @Override
        public CumulativeHealthOutcome get() {
            return new CumulativeHealthOutcome(horizon);
        }
        @Override
        public boolean equals(final Object other) {
            return other instanceof P && ((P) other).horizon == horizon;
        }
        @Override
        public int hashCode() {
            return horizon;
        }
    }
    
    public static Supplier<CumulativeHealthOutcome> factory(final int horizon) {
        return new P(horizon);
    }
}
