package uk.org.cse.nhm.simulator.state.dimensions.impl;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class CanonicalDimension<T> implements IInternalDimension<T> {

    public static final String DIMENSION_NAME = "canonicaldimension.name";

    private final String name;

    private Object[] values = new Object[0];
    private int[] generations = new int[0];

    final IDimensionManager<T> manager;
    private final int index;

    @Inject
    public CanonicalDimension(
            final DimensionCounter dc,
            @Named(DIMENSION_NAME) final String name,
            final IDimensionManager<T> manager) {
        this.name = name;
        this.manager = manager;
        this.index = dc.next();
    }

    private void check(final int capacity) {
        if (values.length <= capacity) {
            values = Arrays.copyOf(values, Math.max(capacity + 1, values.length * 2));
            generations = Arrays.copyOf(generations, Math.max(capacity + 1, generations.length * 2));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(final IDwelling instance) {
        check(instance.getID());
        final T result = (T) values[instance.getID()];
        if (result == null) {
            return manager.getDefaultValue();
        }
        return result;
    }

    @Override
    public T copy(final IDwelling instance) {
        return manager.copy(get(instance));
    }

    @Override
    public boolean set(final IDwelling instance, final T value) {
        check(instance.getID());
        final T internal = manager.internalise(value);
//		if (!manager.isEqual(get(instance), internal)) {
        if (values[instance.getID()] != internal) {
            values[instance.getID()] = internal;
            generations[instance.getID()]++;
            return true;
        } else {
            return false;
        }
//		} else {
//			return false;
//		}
    }

    public boolean isCanonical() {
        return true;
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
        return "D:" + name + "(" + System.identityHashCode(this) + ")";
    }

    @Override
    public boolean isSettable() {
        return true;
    }

    @Override
    public int getGeneration(final IDwelling instance) {
        check(instance.getID());
        return generations[instance.getID()];
    }

    @Override
    public boolean isEqual(final T a, final T b) {
        return manager.isEqual(a, b);
    }

    @Override
    public void merge(final IDwelling instance, final IInternalDimension<T> branch_) {
        final BranchDimension<T> branch = (BranchDimension<T>) branch_;
        final int id = instance.getID();
        check(id);

        values[id]
                = //manager.internalise(
                branch.__get(id) //)
                ;
        if (values[id] == null) {
            throw new RuntimeException(name + " for " + id + " is null!");
        }
        generations[id] = branch.__generation(id);
    }

    @Override
    public int index() {
        return this.index;
    }
}
