package uk.org.cse.nhm.simulation.reporting.aggregates.modes;

import java.util.SortedSet;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public class DateMode implements IReportMode {
	private final SortedSet<DateTime> dates;

	public DateMode(SortedSet<DateTime> dates) {
		this.dates = dates;
	}

	@Override
	public boolean shouldCalculateRow(DateTime now) {
		return dates.contains(now);
	}

	@Override
	public Optional<ImmutableMap<String, Object>> updateAndMaybeReturn(Object key, ImmutableMap<String, Object> newValues, boolean isFinalStep, DateTime now) {
		if(dates.contains(now)) {
			return Optional.of(newValues);
		} else {
			return Optional.absent();
		}
	}
	
	@Override
	public SortedSet<DateTime> extraDates() {
		return dates;
	}
}
