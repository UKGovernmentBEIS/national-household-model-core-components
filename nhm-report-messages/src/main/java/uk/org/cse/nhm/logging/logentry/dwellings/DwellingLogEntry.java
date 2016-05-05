package uk.org.cse.nhm.logging.logentry.dwellings;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.logging.logentry.AbstractLogEntry;

public class DwellingLogEntry extends AbstractLogEntry {
	private final String reportName;
	private final DateTime date;
	private final int dwellingID;
	private final ImmutableMap<String, Object> changedDwellingData;
	
	public static final String GROUP_CHANGE = "group-change";
	private final float weight;

	@JsonCreator
	public DwellingLogEntry(
			@JsonProperty("reportName") final String reportName,
			@JsonProperty("date") final DateTime date,
			@JsonProperty("dwellingID") final int dwellingID,
			@JsonProperty("weight") final float weight,
			@JsonProperty("changedDwellingData") final ImmutableMap<String, Object> changedDwellingData
			) {
		this.reportName = reportName;
		this.date = date;
		this.dwellingID = dwellingID;
		this.weight = weight;
		this.changedDwellingData = changedDwellingData;
	}

	public String getReportName() {
		return reportName;
	}

	public DateTime getDate() {
		return date;
	}

	public int getDwellingID() {
		return dwellingID;
	}

	public float getWeight() {
		return weight;
	}
	
	public ImmutableMap<String, Object> getChangedDwellingData() {
		return changedDwellingData;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
}
