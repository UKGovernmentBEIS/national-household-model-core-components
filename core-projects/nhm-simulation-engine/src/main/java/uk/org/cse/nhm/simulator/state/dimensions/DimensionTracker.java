package uk.org.cse.nhm.simulator.state.dimensions;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;

import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

public class DimensionTracker<T> {
	private final IDimension<T> dimension;
	private final IState state;
	private final ObjectIntOpenHashMap<IDwelling> generations = new ObjectIntOpenHashMap<IDwelling>();
	
	public DimensionTracker(IDimension<T> dimension, IState state) {
		this.dimension = dimension;
		this.state = state;
	}

	public boolean isOutOfDate(final IDwelling dwelling) {
		if (generations.containsKey(dwelling)) {
			final int generation = state.getGeneration(dimension, dwelling);
			final int lastGeneration = generations.get(dwelling);
			return lastGeneration != generation;
		} else {
			return true;
		}
	}
	
	public T get(final IDwelling dwelling) {
		final T value = state.get(dimension, dwelling);
		generations.put(dwelling, state.getGeneration(dimension, dwelling));
		return value;
	}

	public int getGeneration(IDwelling instance) {
		return state.getGeneration(dimension, instance);
	}
}
