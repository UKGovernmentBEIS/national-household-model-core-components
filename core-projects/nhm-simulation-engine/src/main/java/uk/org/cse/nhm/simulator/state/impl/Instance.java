package uk.org.cse.nhm.simulator.state.impl;

import uk.org.cse.nhm.simulator.state.IDwelling;

public class Instance implements IDwelling {
	private final int id;
	private final float weight;

	public Instance(final int id, final float weight) {
		this.id = id;
		this.weight = weight;
	}

	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "D" + id;
	}
	
	@Override
	public float getWeight() {
		return weight;
	}

    @Override
    public boolean equals(Object other) {
        return (other instanceof IDwelling && ((IDwelling) other).getID() == id);
    }

    @Override
    public int hashCode() {
        return ((Integer) id).hashCode();
    }
}
