package uk.org.cse.nhm.energycalculator.api.types.steps;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

class DefaultValue {

    static final DefaultValue None = new DefaultValue(0) {
        @Override
        public List<Double> getMonthly() {
            throw new UnsupportedOperationException("No default value available for this energy calculation step.");
        }

        @Override
        public List<Double> getAnnual() {
            throw new UnsupportedOperationException("No default value available for this energy calculation step.");
        }

        @Override
        public String toString() {
            return "No default.";
        }

        @Override
        public boolean exists() {
            return false;
        }
    };

    private final List<Double> annual;
    private final List<Double> monthly;
    protected final double value;

    DefaultValue(double value) {
        this.value = value;
        this.annual = ImmutableList.of(value);
        this.monthly = Collections.nCopies(12, value);
    }

    List<Double> getAnnual() {
        return annual;
    }

    List<Double> getMonthly() {
        return monthly;
    }

    boolean exists() {
        return true;
    }

    boolean supportedInNHM() {
        return true;
    }

    @Override
    public String toString() {
        return "Default value: " + value + ".";
    }
}

/**
 * A default value for cases when this feature is not implemented in the NHM.
 */
class NotSupportedValuePlaceholder extends DefaultValue {

    NotSupportedValuePlaceholder(double value) {
        super(value);
    }

    @Override
    public boolean supportedInNHM() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Always has value %.1f in the NHM.", value);
    }
}
