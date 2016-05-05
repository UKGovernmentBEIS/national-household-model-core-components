package uk.org.cse.nhm.logging.logentry;

import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.logging.logentry.batch.IDatedKeyValueLogEntry;

@AutoProperty
public class AggregateLogEntry extends AbstractDatedLogEntry implements IDatedKeyValueLogEntry {
	final String name;
	final Set<String> causes;
	final ImmutableMap<String, String> reducedRowKey;
	final ImmutableMap<String, Double> columns;
	
	private static final DateTimeFormatter dateFormat = DateTimeFormat.shortDate();

	@JsonCreator
	public AggregateLogEntry(
			@JsonProperty("name") String name, 
			@JsonProperty("causes") Set<String> causes, 
			@JsonProperty("reducedRowKey") ImmutableMap<String, String> reducedRowKey, 
			@JsonProperty("date") DateTime date, 
			@JsonProperty("columns") ImmutableMap<String, Double> columns) {
		super(date);
		this.name = name;
		this.causes = causes;
		this.reducedRowKey = reducedRowKey;
		this.columns = columns; 
	}

	public String getName() {
		return name;
	}
	
	public Set<String> getCauses() {
		return causes;
	}

	@Override
	public ImmutableMap<String, String> getReducedRowKey() {
		return reducedRowKey;
	}

	public ImmutableMap<String, Double> getColumns() {
		return columns;
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

	@JsonIgnore
	@Override
	public ImmutableMap<String, String> getFullRowKey() {
		return ImmutableMap.<String, String>builder()
				.put("date", dateFormat.print(this.getDate()))
				.put("name", name)
				.putAll(reducedRowKey)
				.build();
	}
}
