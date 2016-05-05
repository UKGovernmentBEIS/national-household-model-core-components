package uk.org.cse.nhm.simulator.state.dimensions;

public class DimensionCounter {
	private int count = 0;
	private boolean locked = false;
	public DimensionCounter() {
		
	}
	
	public DimensionCounter(final int i) {
		this.count = i;
	}

	public int next() {
		if (locked) throw new RuntimeException("New dimension registered after someone has used max. count");
		return count++;
	}
	
	public int max() {
		locked = true;
		return count;
	}
}
