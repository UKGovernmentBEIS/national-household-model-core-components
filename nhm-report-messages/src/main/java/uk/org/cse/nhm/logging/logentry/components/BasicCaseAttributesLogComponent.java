package uk.org.cse.nhm.logging.logentry.components;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Records the structure by an ID, because logging the actual structure all the time is painfully expensive.
 * @author hinton
 *
 */
@AutoProperty
public class BasicCaseAttributesLogComponent {
	final int attributesID;
	
	@JsonCreator
	public BasicCaseAttributesLogComponent(@JsonProperty("id") final int attributesID) {
		this.attributesID = attributesID;
	}
	
	public int getId() {
		return attributesID;
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
