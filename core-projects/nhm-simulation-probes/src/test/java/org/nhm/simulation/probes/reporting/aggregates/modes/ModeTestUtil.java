package org.nhm.simulation.probes.reporting.aggregates.modes;

import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;

class ModeTestUtil {

    private static final DateTime NOW = new DateTime(0);
    private static final String PARAMETER = "parameter";
    private static final String PARAMETER2 = "parameter2";
    private static final String GROUP = "group";

    static void shouldAlwaysCalculate(final IReportMode mode) {
        Assert.assertTrue("Should always calculate.", mode.shouldCalculateRow(NOW));
        Assert.assertTrue("Should always calculate.", mode.shouldCalculateRow(NOW));
    }

    static ImmutableMap<String, Object> data(final Double a, final Double b) {
        return ImmutableMap.<String, Object>of(PARAMETER, a, PARAMETER2, b);
    }

    static void updateWithoutAssertions(final IReportMode mode, final ImmutableMap<String, Object> values, final boolean isFinalStep, final DateTime when) {
        mode.updateAndMaybeReturn(GROUP, values, isFinalStep, when);
    }

    static void noDataPlease(final String message, final IReportMode mode, final ImmutableMap<String, Object> values, final boolean isFinalStep, final DateTime when) {
        Assert.assertEquals(message, Optional.<Map<String, Object>>absent(), mode.updateAndMaybeReturn(GROUP, values, isFinalStep, when));
    }

    static void thisDataPlease(final String message, final IReportMode mode, final ImmutableMap<String, Object> values, final boolean isFinalStep, final DateTime when, final double resulta, final double resultb) {
        final Optional<ImmutableMap<String, Object>> maybeResult = mode.updateAndMaybeReturn(GROUP, values, isFinalStep, when);
        final ImmutableMap<String, Object> result = maybeResult.get();
        Assert.assertEquals(message, resulta, ((Double) result.get(PARAMETER)).doubleValue(), 0.01);
        Assert.assertEquals(message, resultb, ((Double) result.get(PARAMETER2)).doubleValue(), 0.01);
    }
}
