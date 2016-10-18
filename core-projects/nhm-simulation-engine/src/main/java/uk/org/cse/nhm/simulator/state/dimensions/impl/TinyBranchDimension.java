package uk.org.cse.nhm.simulator.state.dimensions.impl;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class TinyBranchDimension<T> extends BranchDimension<T> {
	public TinyBranchDimension(final CanonicalDimension<T> canonicalDimension, final IBranch forkingState, final int capacity) {
		super(canonicalDimension, forkingState, capacity);
	}
	
	public TinyBranchDimension(final BranchDimension<T> forkedDimension, final IBranch forkingState, final int capacity) {
		super(forkedDimension, forkingState, capacity);
	}

	private int singleIdentity = -1;
	private T singleValue = null;
	private int singleGeneration = -1;
	
	@Override
	public T get(final IDwelling instance) {
		if (singleIdentity < 0) {
			singleIdentity = instance.getID();
			singleValue = parent.get(instance);
			singleGeneration = parent.getGeneration(instance);
		} else if (singleIdentity != instance.getID()) {
            return parent.get(instance);
        }
		if (singleValue == null) {
			throw new NullPointerException();
		}
		return singleValue;
	}
	@Override
	public T copy(final IDwelling instance) {
		return manager.copy(get(instance));
	}
	@Override
	public boolean set(final IDwelling instance, final T value) {
		final T internal = manager.internalise(value);
        if (singleIdentity >= 0 && singleIdentity != instance.getID()) {
            throw new RuntimeException("Attempted to modify house "
                                       + instance.getID() +
                                       " in an operation that should only be affecting " +
                                       singleIdentity);
        }
		if (get(instance) != internal) {	
			singleValue = internal;
			if (internal == null) {
				throw new NullPointerException();
			}
			singleGeneration++; // we can safely bump generation because we just did a get
			return true;
		} else {
			return false;
		}
	}
	@Override
	public void merge(final IDwelling instance, final IInternalDimension<T> branch_) {
		final TinyBranchDimension<T> branch = (TinyBranchDimension<T>) branch_;
		if (branch.singleIdentity >= 0) {
			this.singleIdentity = branch.singleIdentity;
			this.singleValue = branch.singleValue;
			this.singleGeneration = branch.singleGeneration;
		}
	}
	@Override
	public IInternalDimension<T> branch(final IBranch forkingState, final int capacity) {
		if (capacity > 1) {
			throw new IllegalArgumentException("Cannot go from a single-house state to a multi-house state");
		}
		return new TinyBranchDimension<>(this, forkingState, capacity);
	}
	@Override
	public int getGeneration(final IDwelling instance) {
		if (singleGeneration < 0 || instance.getID() != singleIdentity) {
			return parent.getGeneration(instance);
		} else {
			return singleGeneration;
		}
	}
	
	@Override
	protected int __generation(final int id) {
        return singleGeneration;
	}
	
	@Override
	protected T __get(final int id) {
        return singleValue;
	}
}
