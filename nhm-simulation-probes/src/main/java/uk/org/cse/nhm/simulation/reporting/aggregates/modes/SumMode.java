package uk.org.cse.nhm.simulation.reporting.aggregates.modes;

import org.joda.time.DateTime;


public class SumMode extends AccumulateAndYieldOnFinalStep {

	@Override
	protected Double accumulate(final Double accumulatedValue, final Double newValue, final String valueKey, final Object groupKey, final DateTime now) {
		return accumulatedValue + newValue;
	}

	@Override
	protected void updateClock(final DateTime newTime, final Object key) {
		// Noop
	}
}
