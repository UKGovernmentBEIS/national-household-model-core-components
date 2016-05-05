package uk.org.cse.nhm.simulator.state.dimensions.impl;

import com.carrotsearch.hppc.IntIntOpenHashMap;
import com.carrotsearch.hppc.IntObjectOpenHashMap;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class BranchDimension<T> implements IInternalDimension<T> {
	protected IntObjectOpenHashMap<T> values_;
	protected IntIntOpenHashMap generations_;
	protected final IInternalDimension<T> parent;
	protected final IDimensionManager<T> manager;
	private final int index;
	
	protected BranchDimension(final CanonicalDimension<T> canonicalDimension, final IBranch forkingState, final int capacity) {
		if (capacity > 1) {
			this.values_ = new IntObjectOpenHashMap<T>(capacity);
			this.generations_ = new IntIntOpenHashMap(capacity);
		}
		this.parent = canonicalDimension;
		this.manager = canonicalDimension.manager;
		this.index = canonicalDimension.index();
	}

	protected BranchDimension(final BranchDimension<T> forkedDimension, final IBranch forkingState, final int capacity) {
		if (capacity > 1) {
			this.values_ = new IntObjectOpenHashMap<T>(capacity);
			this.generations_ = new IntIntOpenHashMap(capacity);
		}
		this.parent = forkedDimension;
		this.manager = forkedDimension.manager;
		this.index = forkedDimension.index;
	}

	private final IntObjectOpenHashMap<T> values() {
		if (values_ == null) {
			this.values_ = new IntObjectOpenHashMap<T>(16);
		}
		return this.values_;
	}
	
	private final IntIntOpenHashMap generations() {
		if (generations_ == null) {
			this.generations_ = new IntIntOpenHashMap(16);
		}
		return this.generations_;
	}
	
	@Override
	public T get(final IDwelling instance) {
		final IntObjectOpenHashMap<T> values = values();
		final T result;
		if (values.containsKey(instance.getID()))  {
			result = values.get(instance.getID());
		} else {
			result = parent.get(instance);
		}
				
		return result;
	}

	@Override
	public T copy(final IDwelling instance) {
		final IntObjectOpenHashMap<T> values = values();
		if (values.containsKey(instance.getID())) {
			final T myValue = values.get(instance.getID());
			return manager.copy(myValue);
		} else {
			return parent.copy(instance);
		}
	}

	@Override
	public boolean set(final IDwelling instance, final T value) {
		final T internal = manager.internalise(value);
		if (get(instance) != internal) {	
			forceSet(instance, internal);
			return true;
		} else {
			return false;
		}
	}

	private void forceSet(final IDwelling instance, final T value) {		
		values().put(instance.getID(), value);
		generations().putOrAdd(instance.getID(), parent.getGeneration(instance) + 1, 1);
	}
	
	@Override
	public void merge(final IDwelling instance,final IInternalDimension<T> branch_) {
		final BranchDimension<T> branch = (BranchDimension<T>) branch_;
		
		final int myGeneration = getGeneration(instance);
		final int newGeneration = branch.getGeneration(instance);
		
		if (newGeneration < myGeneration) {
			throw new RuntimeException(toString() + " generation has decreased for " + instance.getID());
		} else if (newGeneration > myGeneration) {			
			values().put(instance.getID(), branch.get(instance));
			generations().put(instance.getID(), newGeneration);
		}		
	}

	public final boolean isCanonical() {
		return false;
	}

	@Override
	public IInternalDimension<T> branch(final IBranch forkingState, final int capacity) {
		if (capacity == 1) {
			return new TinyBranchDimension<>(this, forkingState, 1);
		} else {
			return new BranchDimension<T>(this, forkingState, capacity);
		}
	}
	
	@Override
	public String toString() {
		return "~" + parent.toString();
	}

	@Override
	public boolean isSettable() {
		return true;
	}

	@Override
	public int getGeneration(final IDwelling instance) {
		final IntIntOpenHashMap generations = generations();
		if (generations.containsKey(instance.getID())) {
			return generations.get(instance.getID());
		} else {
			return parent.getGeneration(instance);
		}
	}
	
	@Override
	public boolean isEqual(final T a, final T b) {
		return parent.isEqual(a, b);
	}

	protected int __generation(final int id) {
		return generations().get(id);
	}

	protected T __get(final int id) {
		return values().get(id);
	}
	
	@Override
	public int index() {
		return index;
	}
}
