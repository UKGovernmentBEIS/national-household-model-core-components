package uk.org.cse.nhm.simulator.action.hypothetical;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

class ReplacementValueShim<T> implements IInternalDimension<T> {

    private final T value;
    private final int index;

    public ReplacementValueShim(final int index, final T value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public boolean isSettable() {
        return false;
    }

    @Override
    public T get(final IDwelling instance) {
        return value;
    }

    @Override
    public int getGeneration(final IDwelling instance) {
        return 0;
    }

    @Override
    public T copy(final IDwelling instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean set(final IDwelling instance, final T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void merge(final IDwelling instance, final IInternalDimension<T> branch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IInternalDimension<T> branch(final IBranch forkingState, final int capacity) {
        return this;
    }

    @Override
    public boolean isEqual(final T a, final T b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
}
