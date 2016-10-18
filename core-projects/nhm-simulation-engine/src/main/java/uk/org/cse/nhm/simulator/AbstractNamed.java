package uk.org.cse.nhm.simulator;

import uk.org.cse.commons.names.ISettableIdentified;
import uk.org.cse.commons.names.Name;

public abstract class AbstractNamed implements ISettableIdentified {
	private Name name;
	
	public AbstractNamed() {
		this.name = Name.of("unnamed " + getClass().getSimpleName());
	}
	
	public AbstractNamed(final Name name) {
		this.name = name;
	}
	
	@Override
	public Name getIdentifier() {
		return name;
	}
	
	@Override
	public void setIdentifier(final Name newName) {
		this.name = newName;
	}
	
	@Override
	public final String toString() {
		return name.toString();
	}
}
