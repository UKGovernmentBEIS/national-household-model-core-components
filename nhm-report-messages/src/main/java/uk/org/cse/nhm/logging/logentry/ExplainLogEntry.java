package uk.org.cse.nhm.logging.logentry;

import java.util.List;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@AutoProperty
public class ExplainLogEntry extends AbstractDatedLogEntry implements Comparable<ExplainLogEntry> {
	final String name;
	final String cause;
	final List<ExplainArrow> arrows;
	final int sequenceNumber;
	final boolean isFromStockCreator;

	@JsonCreator
	public ExplainLogEntry(
			@JsonProperty("date")  final DateTime date, 
			@JsonProperty("name")  final String name, 
			@JsonProperty("cause")  final String cause, 
			@JsonProperty("fromStockCreator") final boolean isFromStockCreator,
			@JsonProperty("sequenceNumber")  final int sequenceNumber,
			@JsonProperty("explainArrows") final List<ExplainArrow> explainArrows) {
		super(date);
		this.name = name;
		this.cause = cause;
		this.isFromStockCreator = isFromStockCreator;
		this.sequenceNumber = sequenceNumber;
		this.arrows = explainArrows;
	}

	public String getName() {
		return name;
	}

	public String getCause() {
		return cause;
	}

	public List<ExplainArrow> getArrows() {
		return arrows;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	
	public boolean isFromStockCreator() {
		return isFromStockCreator;
	}

	@Override
	public int compareTo(ExplainLogEntry o) {
		if (this.getDate().equals(o.getDate())) {
			if (this.getSequenceNumber().equals(o.getSequenceNumber())) {
				return ((Integer) this.hashCode()).compareTo(o.hashCode());
			} else {
				return this.getSequenceNumber().compareTo(o.getSequenceNumber());
			}
		} else {
			return this.getDate().compareTo(o.getDate());
		}

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
