package uk.org.cse.nhm.reporting.standard.timeseries.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Point implements Comparable<Point> {
	private Double y;
	@JsonIgnore
	private DateTime date;

	public Point(DateTime date, Double value) {
		this.date = date;
		this.y = value;
	}

	public BigDecimal getX() {
		return SecondsUtil.secondsSinceUnixEra(date);
	}

	public Double getY() {
		return y;
	}
	
	@JsonIgnore
	public DateTime getDate() {
		return date;
	}

	@Override
	public int compareTo(Point o) {
		if(!date.equals(o.date)) {
			return date.compareTo(o.date);
		} else {
			return y.compareTo(getY());
		}
	}

	public boolean isAtDate(DateTime d) {
		return this.date.equals(d);
	}

	public boolean isAfterDate(DateTime d) {
		return this.date.isAfter(d);
	}

	public boolean isBeforeDate(DateTime d) {
		return this.date.isBefore(d);
	}
}
