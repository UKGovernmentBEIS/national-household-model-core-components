package uk.org.cse.nhm.simulator.scope;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

class ShimDimension<T> implements IInternalDimension<T> {

    private final int generationOffset;
    private final IDwelling supportedInstance;
    private final IInternalDimension<T> delegate;

    ShimDimension(
            final int generationOffset,
            final IDwelling supportedInstance,
            final IInternalDimension<T> delegate) {
        super();
        this.generationOffset = generationOffset;
        this.supportedInstance = supportedInstance;
        this.delegate = delegate;
    }

    @Override
    public boolean isSettable() {
        return delegate.isSettable();
    }

    @Override
    public T get(final IDwelling instance) {
        check(instance);
        return delegate.get(instance);
    }

    @Override
    public int getGeneration(final IDwelling instance) {
        check(instance);
        return delegate.getGeneration(instance) + generationOffset;
    }

    private void check(final IDwelling instance) {
        if (!instance.equals(supportedInstance)) {
            throw new IllegalArgumentException("Shimmed dimension for dwelling " + supportedInstance + " does not support " + instance);
        }
    }

    @Override
    public T copy(final IDwelling instance) {
        check(instance);
        return delegate.copy(instance);
    }

    @Override
    public boolean set(final IDwelling instance, final T value) {
        check(instance);
        return delegate.set(instance, value);
    }

    @Override
    public void merge(final IDwelling instance, final IInternalDimension<T> branch) {
        throw new UnsupportedOperationException("A shimmed dimension cannot be merged");
    }

    @Override
    public IInternalDimension<T> branch(final IBranch forkingState, final int capacity) {
        return of(generationOffset, supportedInstance, delegate.branch(forkingState, capacity));
    }

    @Override
    public boolean isEqual(final T a, final T b) {
        return delegate.isEqual(a, b);
    }

    static <T> IInternalDimension<T> of(
            final int offset,
            final IDwelling dwelling,
            final IInternalDimension<T> internal) {
        return new ShimDimension<>(offset, dwelling, internal);
    }

    @Override
    public int index() {
        return delegate.index();
    }
}
