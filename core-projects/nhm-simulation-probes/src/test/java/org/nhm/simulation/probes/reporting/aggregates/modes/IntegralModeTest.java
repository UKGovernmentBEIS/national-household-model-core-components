package org.nhm.simulation.probes.reporting.aggregates.modes;

import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.data;
import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.noDataPlease;
import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.thisDataPlease;
import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.updateWithoutAssertions;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IntegralMode;

public class IntegralModeTest {

    private IntegralMode mode;

    private final DateTime now = new DateTime(0);
    private final DateTime future = now.plusYears(1);
    private final DateTime farFuture = future.plusYears(1);

    @Before
    public void setup() {
        mode = new IntegralMode();

    }

    @Test
    public void shouldAlwaysCalculate() {
        ModeTestUtil.shouldAlwaysCalculate(mode);
    }

    @Test
    public void integralOverZeroTimeIsZero() {
        final ImmutableMap<String, Object> values = data(1d, 2d);
        updateWithoutAssertions(mode, values, false, now);
        thisDataPlease("Should return zeroes if no time passed.", mode, values, true, now, 0d, 0d);
    }

    @Test
    public void shouldReturnIntegratedNumbersOnFinalStep() {
        final ImmutableMap<String, Object> values = data(1d, 2d);

        noDataPlease("Should not return values before final step.", mode, values, false, now);
        noDataPlease("Should not return values before final step.", mode, values, false, future);
        thisDataPlease("Should return values on final step.", mode, values, true, farFuture, 2d, 4d);
    }
}
