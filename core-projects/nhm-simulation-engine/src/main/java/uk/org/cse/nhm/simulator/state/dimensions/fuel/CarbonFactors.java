package uk.org.cse.nhm.simulator.state.dimensions.fuel;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class CarbonFactors implements ICarbonFactors {

    private final double[] factors = new double[FuelType.values().length];

    public static final CarbonFactors of(final EnumMap<FuelType, Double> factors) {
        final CarbonFactors cf = new CarbonFactors();
        for (final Map.Entry<FuelType, Double> e : factors.entrySet()) {
            cf.setCarbonFactor(e.getKey(), e.getValue());
        }
        return cf;
    }

    @Override
    public double getCarbonFactor(final FuelType ft) {
        return factors[ft.ordinal()];
    }

    public void setCarbonFactor(final FuelType ft, final double d) {
        factors[ft.ordinal()] = d;
    }

    @Override
    public String toString() {
        return Arrays.toString(factors);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(factors);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarbonFactors other = (CarbonFactors) obj;
        if (!Arrays.equals(factors, other.factors)) {
            return false;
        }
        return true;
    }
}
