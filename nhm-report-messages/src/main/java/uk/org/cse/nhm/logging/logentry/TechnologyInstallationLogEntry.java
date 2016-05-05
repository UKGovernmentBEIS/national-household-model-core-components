package uk.org.cse.nhm.logging.logentry;

import java.util.List;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

/**
 * A log entry describing the installation of some technologies on an instance.
 * @author hinton
 *
 */
@AutoProperty
public class TechnologyInstallationLogEntry extends AbstractDatedLogEntry {
	private final List<TechnologyInstallationRecord> records;
	private final int dwellingID;
	private final float weight;
	
	@JsonCreator
	public TechnologyInstallationLogEntry(
			@JsonProperty("date") final DateTime date, 
			@JsonProperty("records") final List<TechnologyInstallationRecord> records,
			@JsonProperty("weight") final float weight,
			@JsonProperty("dwellingID") final int dwellingID) {
		super(date);
		this.weight = weight;
		this.dwellingID = dwellingID;
		this.records = ImmutableList.copyOf(records);
	}

	public List<TechnologyInstallationRecord> getRecords() {
		return records;
	}

	public int getDwellingID() {
		return dwellingID;
	}
	
	public float getWeight() {
		return weight;
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
