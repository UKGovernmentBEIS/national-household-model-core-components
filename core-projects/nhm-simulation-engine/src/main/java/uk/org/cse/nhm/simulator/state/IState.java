package uk.org.cse.nhm.simulator.state;

import java.util.Set;

import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.util.RandomSource;

/**
 * An {@link IState} describes a possible configuration of the
 * {@link IDwelling}s in the simulator. At any given time there will be a single
 * <em>canonical</em> {@link IState} describing the current true configuration
 * of all things. Along with this there may be several hypothetical states
 * existing containing possible futures which may be made canonical.
 *
 * @author hinton
 *
 */
public interface IState {

    /**
     * @return all of the dimensions that are available for {@link IDwelling}s
     * in this state
     */
    public Set<? extends IDimension<?>> getDimensions();

    /**
     * @return all of the {@link IDwelling}s that exist in this state
     */
    public Set<IDwelling> getDwellings();

    /**
     * Get the value of a particular dimension of an instance, within this state
     *
     * @param dimension the dimension to look up
     * @param instance the instance for which to look it up
     * @return the value of the given dimension indicated by this state.
     */
    public <T> T get(final IDimension<T> dimension, final IDwelling instance);

    /**
     * @return true if this is the canonical state for a simulation.
     */
    public boolean isCanonical();

    /**
     * @param dimension
     * @param instance
     * @return the generation counter for this dimension on this instance. You
     * can use this to find out whether a particular dimension has changed since
     * you last looked.
     */
    public int getGeneration(IDimension<?> dimension, IDwelling instance);

    public IComponentsScope detachedScope(IDwelling dwelling);

    public IGlobals getGlobals();

    /**
     * @param capacity TODO
     * @return a speculative, hypothetical change to this state
     */
    public IBranch branch(int capacity);

    /**
     * return an even more hypothetical branch; in this case, you can make
     * impossible changes which can never be merged.
     *
     * @return
     */
    public IHypotheticalBranch hypotheticalBranch();

    /**
     * @return The random number utility for this branch; random number streams
     * branch and merge along with the state of the world, so a branch which is
     * discarded can use all the random numbers it likes without affecting
     * random events in its parent branch.
     */
    public RandomSource getRandom();

    public IState getPriorState();
}
