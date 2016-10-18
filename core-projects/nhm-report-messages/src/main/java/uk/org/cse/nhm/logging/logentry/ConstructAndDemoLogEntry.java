package uk.org.cse.nhm.logging.logentry;

import java.util.Map;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.hom.types.RegionType;

/**
 * DemolitionLog.
 * 
 * @author richardt
 * @version $Id: DemolitionLog.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@AutoProperty
public class ConstructAndDemoLogEntry extends AbstractDatedLogEntry {
	
	private final Map<RegionType, Integer> dwellingWeightByRegion;

	@JsonCreator
	public ConstructAndDemoLogEntry(
			@JsonProperty("date") final DateTime date,
			@JsonProperty("dwellingWeightByRegion") final Map<RegionType, Integer> dwellingWeightByRegion) {
		super(date);
		this.dwellingWeightByRegion = ImmutableMap.copyOf(dwellingWeightByRegion);
	}

	/**
	 * Return the dwellingWeightByRegion.
	 * 
	 * @return the dwellingWeightByRegion
	 */
	public Map<RegionType, Integer> getDwellingWeightByRegion() {
		return dwellingWeightByRegion;
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
