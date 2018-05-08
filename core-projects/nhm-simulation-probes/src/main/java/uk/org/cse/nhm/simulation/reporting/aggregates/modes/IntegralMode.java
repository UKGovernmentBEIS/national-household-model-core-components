package uk.org.cse.nhm.simulation.reporting.aggregates.modes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class IntegralMode extends AccumulateAndYieldOnFinalStep {

    private static final double STANDARD_JULIAN_YEAR_IN_MILLISECONDS = 365.25 * 24 * 60 * 60 * 1000;
    Map<Object, DateTime> lastUpdateTimes = new LinkedHashMap<>();
    private final Map<Object, Map<String, Double>> lastValues = new HashMap<>();

    @Override
    protected Double accumulate(final Double accumulatedValue, final Double newValue, final String valueKey, final Object groupKey, final DateTime now) {
        final double elapsedYears;
        if (lastUpdateTimes.containsKey(groupKey)) {
            final Duration elapsed = new Duration(lastUpdateTimes.get(groupKey), now);
            elapsedYears = elapsed.getMillis() / STANDARD_JULIAN_YEAR_IN_MILLISECONDS;
        } else {
            elapsedYears = 0.0;
        }

        /*
		 * We want to accumulate based on the previous value: the new value has only just taken effect, and so has no time to be integrated over yet.
         */
        final double lastValue;
        if (!lastValues.containsKey(groupKey)) {
            lastValues.put(groupKey, new HashMap<String, Double>());
        }

        final Map<String, Double> lastValuesForKey = lastValues.get(groupKey);
        if (!lastValuesForKey.containsKey(valueKey)) {
            lastValue = 0;
        } else {
            lastValue = lastValuesForKey.get(valueKey);
        }

        lastValuesForKey.put(valueKey, newValue);

        return accumulatedValue + (lastValue * elapsedYears);
    }

    @Override
    protected void updateClock(final DateTime newTime, final Object key) {
        lastUpdateTimes.put(key, newTime);
    }
}
