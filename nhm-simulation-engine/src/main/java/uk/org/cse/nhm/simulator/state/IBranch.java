package uk.org.cse.nhm.simulator.state;

import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.Priority;

/**
 * An {@link IBranch} is a non-canonical description of a state which is different to the
 * canonical state.
 * 
 * @author hinton
 *
 */
public interface IBranch extends IState {
	public interface IModifier<T> {
		public boolean modify(final T modifiable);
	}
	/**
	 * Perform an operation on the given dwelling/dimension
	 * 
	 * @param dimension
	 * @param instance
	 * @return
	 */
	public <T> void modify(final IDimension<T> dimension, final IDwelling instance, final IModifier<T> modifier);
	/**
	 * Change this hypothetical so that the given dimension of the given instance
	 * takes the given value.
	 * @param dimension
	 * @param instance
	 * @param value
	 */
	public <T> void set(final IDimension<T> dimension, final IDwelling instance, final T value);

	/**
	 * @return a new {@link IDwelling} that will exist within this state and any branches from it
	 */
	public IDwelling createDwelling(final float weight);
	/**
	 * @param lenny remove the given {@link IDwelling} from the set of all that exist, in this state
	 */
	public void destroyDwelling(final IDwelling lenny);
	/**
	 * @return all the instances created so far in this branch.
	 */
	public Set<IDwelling> getCreated();
	/**
	 * 
	 * @return all the dwellings destroyed in this branch
	 */
	public Set<IDwelling> getDestroyed();
	/**
	 * @return all the pre-existing dwellings altered in this branch
	 */
	public Set<IDwelling> getModified();
	
	/**
	 * @return whether or not globals were changed in this branch
	 */
	public boolean wereGlobalsModified();
	
	/**
	 * @return true if this is a hypothetical, in which case it is not legal to merge it
	 */
	public boolean isHypothetical();

    /**
	 * Fold a branch state from a sequence of {@link #branch(int)} calls back onto this.
	 * 
	 * @param branch
	 * @param cause TODO
	 */
	public void merge(final IBranch branch);
	
	/**
	 * Schedule an event to happen later, if this branch becomes true
	 */
	public void schedule(final DateTime dateTime, Priority priority, IDateRunnable callback);
}
