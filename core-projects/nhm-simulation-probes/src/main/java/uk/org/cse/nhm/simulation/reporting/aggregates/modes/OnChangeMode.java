package uk.org.cse.nhm.simulation.reporting.aggregates.modes;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;

public class OnChangeMode implements IReportMode {
	
	private Map<Object, ImmutableMap<String, Object>> lastValuesByGroup = new HashMap<>();

	@Override
	public boolean shouldCalculateRow(DateTime now) {
		return true;
	}

	@Override
	public Optional<ImmutableMap<String, Object>> updateAndMaybeReturn(Object key, ImmutableMap<String, Object> newValues, boolean finalValue, DateTime now) {
		if(unchanged(key, newValues)) {
			return Optional.absent();
		} else {
			lastValuesByGroup.put(key, newValues);
			return Optional.of(newValues);
		}
	}

	private boolean unchanged(Object group, ImmutableMap<String, Object> newValues) {
		return lastValuesByGroup.containsKey(group) && lastValuesByGroup.get(group).equals(newValues);
	}
	
	@Override
	public SortedSet<DateTime> extraDates() {
		return ImmutableSortedSet.<DateTime>of();
	}
}
