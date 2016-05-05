package uk.org.cse.nhm.simulator.state.dimensions.impl;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;

import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public abstract class DerivedDimensionWithCache<T> implements IInternalDimension<T> {
	static abstract class InnerCache<T> {
		public abstract T get(final IDwelling instance);
		public abstract int getLastEvalGeneration(final IDwelling instance);
		public abstract void insert(final IDwelling d, final int gen, final T value);
		public abstract boolean contains(final IDwelling instance);
	}
	
	static class TinyCache<T> extends InnerCache<T> {
		private int dwellingID = -1;
		private T value = null;
		private int evalGeneration = -1;
		
		@Override
		public boolean contains(final IDwelling instance) {
			return instance.getID() == dwellingID;
		}
		
		@Override
		public T get(final IDwelling instance) {
			if (instance.getID() != dwellingID) throw new IllegalArgumentException("Small dimension used for more than one value");
			return value;
		}
		
		@Override
		public int getLastEvalGeneration(final IDwelling instance) {
			return evalGeneration;
		}
		@Override
		public void insert(final IDwelling d, final int gen, final T value) {
			if (this.dwellingID >= 0 && this.dwellingID != d.getID()) throw new IllegalArgumentException("Small dimension used for more than one value");
			this.dwellingID = d.getID();
			this.evalGeneration = gen;
			this.value = value;
		}
	}
	
	static class LargeCache<T> extends InnerCache<T> {
		private final Map<IDwelling, SoftReference<T>> cache;
		private final ObjectIntOpenHashMap<IDwelling> lastEvalGeneration;
		public LargeCache(final int capacity) {
			super();
			this.cache = new HashMap<IDwelling, SoftReference<T>>(capacity);
			this.lastEvalGeneration = new ObjectIntOpenHashMap<>(capacity);
		}
		
		@Override
		public boolean contains(final IDwelling instance) {
			return cache.containsKey(instance);
		}
		
		@Override
		public T get(final IDwelling instance) {
			final SoftReference<T> ref = cache.get(instance);
			if (ref == null) return null;
			return ref.get();
		}
		
		@Override
		public int getLastEvalGeneration(final IDwelling instance) {
			return lastEvalGeneration.get(instance);
		}
		
		@Override
		public void insert(final IDwelling d, final int gen, final T value) {
			cache.put(d, new SoftReference<T>(value));
			lastEvalGeneration.put(d, gen);
		}
	}
	
	
	private final InnerCache<T> cache;
	private final DerivedDimensionWithCache<T> parent;
	protected final int index;
	
	protected DerivedDimensionWithCache(final int index, final DerivedDimensionWithCache<T> parent, final int capacity) {
		this.index = index;
		this.parent = parent;
		
		if (capacity == 1) {
			this.cache = new TinyCache<T>();
		} else {
			this.cache = new LargeCache<T>(capacity);
		}
	}
	
	@Override
	public final int index() {
		return index;
	}
	
	protected boolean hasParent() {
		return parent != null;
	}
	
	private T findInCache(final IDwelling instance, final int generation) {
        if (parent != null) {
            final int parentGeneration = parent.getGeneration(instance);
            if (parentGeneration == generation) {
                return parent.get(instance);
            }
        }

        final int lastGeneration = cache.getLastEvalGeneration(instance);

        if (generation == lastGeneration) {
			return cache.get(instance);
		} else {
			return null;
		}
	}
	
	@Override
	public final boolean isSettable() {
		return false;
	}

	@Override
	public T get(final IDwelling instance) {
		final int generation = getGeneration(instance);
		
		T result = findInCache(instance, generation);
		
		if (result == null) {
			result = doGet(instance);
			cache.insert(instance, generation, result);
		}
		
		return result;
	}

	protected abstract T doGet(IDwelling instance);

    protected String debugGenerations(final IDwelling instance) {
        return "";
    }
    
	@Override
	public void merge(final IDwelling instance, final IInternalDimension<T> branch_) {
		final DerivedDimensionWithCache<T> branch = (DerivedDimensionWithCache<T>) branch_;
		
		final int thisGeneration = getGeneration(instance);
		final int nextGeneration = branch.getGeneration(instance);
		if (nextGeneration < thisGeneration) {
			throw new RuntimeException(
					String.format("%s generation regression from %d to %d - this should never happen\n%s\n%s",
                                  this, thisGeneration, nextGeneration,
                                  debugGenerations(instance),
                                  branch.debugGenerations(instance)
                        ));
		} else {
			if (nextGeneration > thisGeneration || findInCache(instance, nextGeneration) == null) {
				final T val = branch.findInCache(instance, nextGeneration);
					
				if (val != null) {
					cache.insert(instance, nextGeneration, val);
				}
			}
		}
	}
	
	@Override
	public final T copy(final IDwelling instance) {
		throw new RuntimeException("Cannot copy derived dimensions!");
	}

	@Override
	public boolean set(final IDwelling instance, final T value) {
		throw new RuntimeException("Cannot set derived dimensions!");
	}

	@Override
	public boolean isEqual(final T a, final T b) {
		return a.equals(b);
	}

	public T getMostRecentValue(final IDwelling instance) {
		if (cache.contains(instance)) {
			final T value = cache.get(instance);
			if (value != null) return value;
		} 
		if (parent != null) {
			return parent.getMostRecentValue(instance);
		} else {
			return null;
		}
	}
}
