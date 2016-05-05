package uk.org.cse.nhm.simulator.trigger;

import uk.org.cse.nhm.simulator.state.IStateChangeSource;

/**
 * Triggers are what connect a group, a measure and a sampler together. When {@link #apply()} is invoked,
 * the trigger will sample its group using its sampler, and then run its action against the elements sampled.
 * @author hinton
 *
 */
public interface ITrigger extends IStateChangeSource {
	
}
