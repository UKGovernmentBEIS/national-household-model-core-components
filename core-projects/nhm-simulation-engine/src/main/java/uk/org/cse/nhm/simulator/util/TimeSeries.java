package uk.org.cse.nhm.simulator.util;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSortedSet;

public class TimeSeries<T> implements ITimeSeries<T> {
	private final SortedMap<DateTime, T> values = new TreeMap<DateTime, T>();
	private T defaultValue;
	
	public TimeSeries() {
		this(null);
	}
	
	public TimeSeries(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void addPoint(final DateTime when, final T value) {
		values.put(when, value);
	}
	
	@Override
	public SortedSet<DateTime> getDateTimes() {
		return ImmutableSortedSet.copyOf(values.keySet());
	}

	@Override
	public T getValue(DateTime dateTime) {
		if (values.isEmpty()) return defaultValue;
		if (values.containsKey(dateTime)) return values.get(dateTime);
		SortedMap<DateTime, T> headMap = values.headMap(dateTime);
		if (headMap.isEmpty()) return defaultValue == null ? values.get(values.firstKey()) : defaultValue;
		else return headMap.get(headMap.lastKey());
	}
}
