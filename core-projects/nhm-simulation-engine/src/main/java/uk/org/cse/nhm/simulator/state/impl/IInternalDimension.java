package uk.org.cse.nhm.simulator.state.impl;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;

public interface IInternalDimension<T> extends IDimension<T> {

    public static final int DEFAULT_CAPACITY = 64;

    /**
     * Yield the value for the given dwelling in this dimension
     *
     * @param instance
     * @return
     */
    public T get(final IDwelling instance);

    /**
     *
     * @param instance
     * @return
     */
    public int getGeneration(final IDwelling instance);

    /**
     * Get a modify-safe copy of the state of this dimension for this instance.
     *
     * @param instance
     * @return
     */
    public T copy(final IDwelling instance);

    /**
     * @param instance
     * @param value
     * @return true if the set was a change
     */
    public boolean set(final IDwelling instance, final T value);

    /**
     * Used only when merging a branch (see
     * {@link IBranch#merge(IBranch, uk.org.cse.nhm.simulator.state.IStateChangeSource)};
     * this is often something like set(instance, branch.get(instance));.
     *
     * For derived dimensions, this offers the opportunity to avoid recomputing
     * values if it is worth doing.
     *
     * @param instance
     * @param branch
     */
    public void merge(final IDwelling instance, final IInternalDimension<T> branch);

    /**
     * Create an {@link IInternalDimension} which is a branch off this one;
     *
     * @param forkingState the new state to which this branch will belong; if
     * this dimension depends on the values of other dimensions you can access
     * their values in the new branch through this state
     * @param capacity TODO
     * @return
     */
    public IInternalDimension<T> branch(final IBranch forkingState, int capacity);

    public boolean isEqual(final T a, final T b);
}
