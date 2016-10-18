package uk.org.cse.nhm.logging.logentry;

import java.util.Map;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

/**
 * A log entry which records the number of houses with each technology installed at each date.
 * 
 * TODO should this just be a date-value series for each technology?
 * 
 * @author hinton
 *
 */
@AutoProperty
public class TechnologyDistributionLog extends AbstractDatedLogEntry {
	private final Map<String, Integer> caseWeightByTechnology;
	
	@JsonCreator
	public TechnologyDistributionLog(
			@JsonProperty("date") final DateTime date, 
			@JsonProperty("caseWeightByTechnology") final Map<String, Integer> caseWeights) {
		super(date);
		this.caseWeightByTechnology = ImmutableMap.copyOf(caseWeights);
	}


	public Map<String, Integer> getCaseWeightByTechnology() {
		return caseWeightByTechnology;
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
