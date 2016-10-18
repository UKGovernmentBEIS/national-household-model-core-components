package uk.org.cse.nhm.simulation.reporting.aggregates.modes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;

abstract class AccumulateAndYieldOnFinalStep implements IReportMode {
	private final Map<Object, ImmutableMap<String, Object>> accumulators = new HashMap<>();
	
	@Override
	public final boolean shouldCalculateRow(final DateTime now) {
		return true;
	}
	
	@Override
	public final Optional<ImmutableMap<String, Object>> updateAndMaybeReturn(final Object key, final ImmutableMap<String, Object> newValues, final boolean isFinalStep, final DateTime now) {
		if(!accumulators.containsKey(key)) {
			final ImmutableMap.Builder<String, Object> data = ImmutableMap.builder();
			for(final String columnHeader : newValues.keySet()) {
				data.put(columnHeader, 0.0);
			}
			
			accumulators.put(key, data.build());
		}
		
		final ImmutableMap<String, Object> oldData = accumulators.get(key);
		final ImmutableMap.Builder<String, Object> newData = ImmutableMap.builder();
		for(final Entry<String, Object> datum : newValues.entrySet()) {
			final String valueKey = datum.getKey();
			
			final Object oldValue;
			if (oldData.containsKey(valueKey)) {
				oldValue = oldData.get(valueKey);
			} else {
				oldValue = 0;
			}
			
			newData.put(valueKey, maybeAccumulate(oldValue, datum.getValue(), valueKey, key, now));
		}
		accumulators.put(key, newData.build());
		
		updateClock(now, key);
		
		if(isFinalStep) {
			return Optional.of(accumulators.get(key));
		} else {
			return Optional.absent();
		}
	}

	/**
	 *  Run at the end of each update. Use this if you need to keep track of what time the last update was.
	 */
	protected abstract void updateClock(final DateTime newTime, Object key);
	
	private Object maybeAccumulate(final Object oldVal, final Object newVal, final String valueKey, final Object groupKey, final DateTime now) {
		if(oldVal instanceof Double && newVal instanceof Double) {
			return accumulate((Double) oldVal, (Double) newVal, valueKey, groupKey, now);
		} else {
			return newVal;
		}
	}

	@Override
	public SortedSet<DateTime> extraDates() {
		return ImmutableSortedSet.<DateTime>of();
	}
	
	protected abstract Double accumulate(Double accumulatedValue, Double newValue, String valueKey, Object groupKey, DateTime now);
}
