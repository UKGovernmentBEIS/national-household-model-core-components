package uk.org.cse.nhm.simulator.sequence;

/**
 * An interface for actions which should get some special treatment when they are used within a sequence; in particular
 * they should operate directly within the sequence's scope, so they can see things like the total cost of the sequence.
 */
public interface ISequenceScopeAction {
	
}
