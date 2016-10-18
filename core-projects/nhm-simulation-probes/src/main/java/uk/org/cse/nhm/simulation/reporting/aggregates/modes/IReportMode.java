package uk.org.cse.nhm.simulation.reporting.aggregates.modes;

import java.util.SortedSet;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public interface IReportMode {
	/**
	 * @return Whether or not the report cares about the value at this date at all.
	 */
	boolean shouldCalculateRow(DateTime now);
	
	/**
	 * The report mode may update its internal state using the values calculated by the aggregation functions.
	 * @return A row of data may optionally be returned.
	 */
	Optional<ImmutableMap<String, Object>> updateAndMaybeReturn(Object key, ImmutableMap<String, Object> newValues, boolean isFinalStep, DateTime now);
	
	/**
	 * @return Extra dates which the report must schedule itself for.
	 */
	SortedSet<DateTime> extraDates();
}
