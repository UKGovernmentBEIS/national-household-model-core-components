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
        public boolean exists() {
            return false;
        }
    };

    // TODO: delete this field
    static final DefaultValue NotImplementedTempPlaceholder = new DefaultValue(0) {};

    private final List<Double> annual;
    private final List<Double> monthly;


    DefaultValue(double value) {
        this.annual = ImmutableList.of(value);
        this.monthly = Collections.nCopies(12, value);
    }

    List<Double> getAnnual() { return annual; }
    List<Double> getMonthly() { return monthly; }
    boolean exists() { return true; }
    boolean supportedInNHM() { return true; }
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
}