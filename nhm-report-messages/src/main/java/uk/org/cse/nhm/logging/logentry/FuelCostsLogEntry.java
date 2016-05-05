package uk.org.cse.nhm.logging.logentry;

import java.util.Map;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

@AutoProperty
public class FuelCostsLogEntry extends AbstractLogEntry {
	private final int dwellingId;
	private final DateTime date;
	private final Map<FuelType, Double> fuelCosts;
	private final float weight;
	
	@JsonCreator
	public FuelCostsLogEntry(
			@JsonProperty("dwellingId") final int dwellingId, 
			@JsonProperty("weight") final float weight,
			@JsonProperty("date") final DateTime date, 
			@JsonProperty("fuelCosts") final Map<FuelType, Double> fuelCosts) {
		this.dwellingId = dwellingId;
		this.weight = weight;
		this.date = date;
		this.fuelCosts = ImmutableMap.copyOf(fuelCosts);
	}

	public int getDwellingId() {
		return dwellingId;
	}

	public DateTime getDate() {
		return date;
	}
	
	public float getWeight() {
		return weight;
	}

	public Map<FuelType, Double> getFuelCosts() {
		return fuelCosts;
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
