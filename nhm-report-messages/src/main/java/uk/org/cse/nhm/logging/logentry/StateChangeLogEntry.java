package uk.org.cse.nhm.logging.logentry;

import java.util.Collection;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.logging.logentry.components.BasicCaseAttributesLogComponent;
import uk.org.cse.nhm.logging.logentry.components.CostsLogComponent;
import uk.org.cse.nhm.logging.logentry.components.EmissionsLogComponent;
import uk.org.cse.nhm.logging.logentry.components.EnergyLogComponent;

/**
 * A log entry for state change events - at the end of a simulation timestep (i.e. a batch of contemporaneous events),
 * one of these will be logged for each dwelling whose state has changed, describing the new state of the dwelling
 * 
 * @author hinton
 *
 */
@AutoProperty
public class StateChangeLogEntry extends AbstractDatedLogEntry {
	public enum EntryType {
		CREATION,
		MODIFICATION,
		DESTRUCTION
	}
	
	private final float weight;
	private final int dwellingID;
	private final EntryType type;
	
	private final BasicCaseAttributesLogComponent basicAttributes;
	private final EnergyLogComponent energy;
	private final EmissionsLogComponent emissions;
	private final CostsLogComponent costs;
	
	@JsonCreator
	public StateChangeLogEntry(
			@JsonProperty("dwellingID") final int dwellingID, 
			@JsonProperty("weight") final float weight, 
			@JsonProperty("type") final EntryType type, 
			@JsonProperty("date") final DateTime date,
			@JsonProperty("basicAttributes") final BasicCaseAttributesLogComponent basicAttributes,
			@JsonProperty("energy") final EnergyLogComponent energy, 
			@JsonProperty("emissions") final EmissionsLogComponent emissions,
			@JsonProperty("costs") final CostsLogComponent costs) {
		super(date);
		this.dwellingID = dwellingID;
		this.weight = weight;
		this.type = type;
		this.basicAttributes = basicAttributes;
		this.energy = energy;
		this.emissions = emissions;
		this.costs = costs;
	}

	public StateChangeLogEntry(
			final int dwellingID,
			final float weight,
			final EntryType type, 
			final DateTime date, final Collection<Object> parts) {
		this(dwellingID, weight, type, date, 
				find(parts, BasicCaseAttributesLogComponent.class),
				find(parts, EnergyLogComponent.class),
				find(parts, EmissionsLogComponent.class),
				find(parts, CostsLogComponent.class));
	}
	
	private static <T> T find(final Collection<Object> parts,
			final Class<T> class1) {
		for (final Object o : parts) {
			if (class1.isInstance(o)) return class1.cast(o);
		}
		return null;
	}

	public float getWeight() {
		return weight;
	}
	
	public int getDwellingID() {
		return dwellingID;
	}

	public EntryType getType() {
		return type;
	}

	@JsonIgnore
	public boolean isEmpty() {
		return (energy == null && basicAttributes == null && emissions == null && costs == null);
	}
	
	public EnergyLogComponent getEnergy() {
		return energy;
	}
	
	public BasicCaseAttributesLogComponent getBasicAttributes() {
		return basicAttributes;
	}
	
	public CostsLogComponent getCosts() {
		return costs;
	}
	
	public EmissionsLogComponent getEmissions() {
		return emissions;
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
