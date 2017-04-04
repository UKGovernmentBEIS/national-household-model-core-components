package uk.org.cse.nhm.logging.logentry.batch;

import java.util.UUID;

import org.pojomatic.Pojomatic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

public class BatchInputEntry extends AbstractBatchEntry<String> {

	@JsonCreator
	public BatchInputEntry(
			@JsonProperty("runID") UUID runID,
			@JsonProperty("columns") ImmutableMap<String, String> columns) {
		super(runID, columns);
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
}
