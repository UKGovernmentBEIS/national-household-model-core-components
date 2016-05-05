package uk.org.cse.nhm.reporting.standard.timeseries.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class Annotation implements Comparable<Annotation> {
	private String text;
	@JsonIgnore
	private DateTime date;

	public Annotation(DateTime date, String text) {
		this.date = date;
		this.text = text;
	}

	@JsonSerialize
	public BigDecimal getDate() {
		return SecondsUtil.secondsSinceUnixEra(date);
	}
	
	@JsonIgnore
	public DateTime getActualDate() {
		return date;
	}

	public String getText() {
		return text;
	}
	
	@Override
	public int compareTo(Annotation o) {
		if(!date.equals(o.date)) {
			return date.compareTo(o.date);
		} else {
			return text.compareTo(getText());
		}
	}
	
	@Override
	public String toString() {
		return text;
	}
}
