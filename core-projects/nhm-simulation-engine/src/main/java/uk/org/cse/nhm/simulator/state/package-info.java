/**
 * This package contains the key interfaces which define the state change mechanism used in the
 * simulation engine to speculatively evaluate the condition of houses, and notify interested parties about
 * changes to the condition of houses.
 * <p>
 * Each quantum of housing (see
 * {@link uk.org.cse.nhm.simulator.SimulatorConfigurationConstants#GRANULARITY})
 * is represented by an instance of
 * {@link uk.org.cse.nhm.simulator.state.IDwelling}. An
 * {@link uk.org.cse.nhm.simulator.state.IDwelling} is unique within a single
 * simulation run. The {@link uk.org.cse.nhm.simulator.state.IDwelling} itself
 * acts more like a key for a particular house, and doesn't contain any
 * information about the house except a unique-in-run number.
 * <p>
 * To represent the condition of an
 * {@link uk.org.cse.nhm.simulator.state.IDwelling}, we have to introduce the
 * concept of an {@link uk.org.cse.nhm.simulator.state.IDimension}, which is a
 * category of information within which the condition of an
 * {@link uk.org.cse.nhm.simulator.state.IDwelling} can vary, and an
 * {@link uk.org.cse.nhm.simulator.state.IState}, which represents a possible
 * condition for the entire simulated housing stock.
 * <p>
 * Together, an
 * {@link uk.org.cse.nhm.simulator.state.IState}, {@link uk.org.cse.nhm.simulator.state.IDimension}
 * and {@link uk.org.cse.nhm.simulator.state.IDwelling} allow access to details
 * about a particular house, through use of
 * {@link uk.org.cse.nhm.simulator.state.IState#get(IDimension, IDwelling)}.
 * <p>
 * Typically there are two kinds of
 * {@link uk.org.cse.nhm.simulator.state.IState} present in a simulation; one
 * state, the
 * <em>canonical</em> state is a within-run singleton, representing the true
 * condition of the housing stock at whatever the current point in simulated
 * time is. The additional methods relevant for the canonical state are
 * specified on {@link uk.org.cse.nhm.simulator.state.ICanonicalState}. The
 * second kind of state is a hypothetical state, created by modifying the
 * condition of some or all of the houses on some or all of their dimensions;
 * the extra behaviours of a hypothetical, or <em>branch</em> state are
 * specified on {@link uk.org.cse.nhm.simulator.state.IBranch}. Key among these
 * are the
 * {@link uk.org.cse.nhm.simulator.state.IBranch#copy(IDimension, IDwelling)}
 * and
 * {@link uk.org.cse.nhm.simulator.state.IBranch#set(IDimension, IDwelling, Object)}
 * methods, which allow (a) a dimension's value on a house to be converted into
 * a detached copy which can be safely altered, and (b) allow a copied value to
 * be indicated as the new condition of a dimension for a house.
 * <p>
 * The simulation typically proceeds by the execution of an event on the
 * {@link uk.org.cse.nhm.simulator.IEventQueue}; this will invoke
 * {@link uk.org.cse.nhm.simulator.state.IState#branch(int)} on the
 * {@link uk.org.cse.nhm.simulator.state.ICanonicalState} singleton to acquire
 * an {@link uk.org.cse.nhm.simulator.state.IBranch}, wherein it will use the
 * copy/set methods to modify some
 * {@link uk.org.cse.nhm.simulator.state.IDwelling}s, possibly including the
 * creation of further speculative branches at lower levels; once the
 * hypothetical next state has been chosen,
 * {@link uk.org.cse.nhm.simulator.state.IState#merge(IBranch, IStateChangeSource)},
 * (or the convenience method
 * {@link uk.org.cse.nhm.simulator.state.IBranch#mergeWithCanonical(IStateChangeSource)})
 * is invoked, to make the hypothetical state become the new canonical state,
 * and any {@link uk.org.cse.nhm.simulator.state.IStateListener}s registered on
 * the {@link uk.org.cse.nhm.simulator.state.ICanonicalState} are notified of
 * the deltas so that they can respond accordingly by logging, aggregating, or
 * scheduling future events as they see fit.
 */
package uk.org.cse.nhm.simulator.state;
