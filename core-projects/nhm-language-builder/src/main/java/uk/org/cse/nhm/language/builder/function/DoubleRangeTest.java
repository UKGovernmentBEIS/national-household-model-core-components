package uk.org.cse.nhm.language.builder.function;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

/**
 *
 * @author hinton
 *
 */
final class DoubleRangeTest implements Predicate<Double> {

    private Optional<Double> low;
    private Optional<Double> exact;
    private Optional<Double> high;

    public DoubleRangeTest(final Optional<Double> low, final Optional<Double> exact, final Optional<Double> high) {
        this.low = low;
        this.exact = exact;
        this.high = high;
    }

    @Override
    public boolean apply(@Nullable final Double value) {
        if (value == null) {
            return false;
        }
        if (exact.isPresent()) {
            return value.equals(exact.get());
        }
        if (low.isPresent()) {
            if (value <= low.get()) {
                return false;
            }
        }

        if (high.isPresent()) {
            if (value >= high.get()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (exact.isPresent()) {
            return String.format("{} = %d", exact.get());
        }

        if (low.isPresent() || high.isPresent()) {
            return ((low.isPresent()) ? String.format("%d < ", low.get()) : "")
                    + "{}"
                    + ((high.isPresent()) ? String.format(" < %d", high.get()) : "");
        }

        return "{} = any value";
    }
}
