package uk.org.cse.nhm.logging.logentry;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@AutoProperty
public class ExplainArrow {
	public static final String OUTSIDE = "OUTSIDE";
	
	private final String from;
	private final String to;
	private final float count;
	
	@JsonCreator
	public ExplainArrow(
			@JsonProperty("from") final String from, 
			@JsonProperty("to") final String to, 
			@JsonProperty("count") final float count) {
		this.from = from;
		this.to = to;
		this.count = count;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public float getCount() {
		return count;
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
