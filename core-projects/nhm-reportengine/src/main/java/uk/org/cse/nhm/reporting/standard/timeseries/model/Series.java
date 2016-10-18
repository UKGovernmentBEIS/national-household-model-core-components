package uk.org.cse.nhm.reporting.standard.timeseries.model;

import java.util.SortedSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;


@JsonSerialize
public class Series {
	private final SortedSet<Point> data;
	private final GroupAndVariable series;

	public Series(GroupAndVariable series, SortedSet<Point> collection) {
		this.series = series;
		this.data = collection;
	}

	public String getName() {
		return series.toString();
	}

	public SortedSet<Point> getData() {
		return data;
	}

	@JsonIgnore
	public ImmutableMap<String, String> getGroup() {
		return series.getGroup();
	}
	
	@JsonIgnore
	public String getVariable() {
		return series.getVariable();
	}
}
