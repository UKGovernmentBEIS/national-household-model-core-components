package uk.org.cse.nhm.simulator.state;

import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;

/**
 * An extension to {@link IState} providing listener methods, as only the canonical state notifies listeners
 * @author hinton
 *
 */
public interface ICanonicalState extends IState {
	/**
	 * Add a listener to be notified of state changes
	 * @param listener
	 */
	public void addStateListener(final IStateListener listener);
	/**
	 * Remove a previously added listener
	 * @param listener
	 */
	public void removeStateListener(final IStateListener listener);
	
	/**
	 * Used by the simulator to tell the state that time has passed, and so
	 * there may be endogenous changes in the various dimensions that are related
	 * to time e.g. fuel, weather, etc.
	 */
	public void checkForEndogenousChanges();

    public IStateScope apply(final IStateChangeSource proximateCause, final IStateAction action, final Set<IDwelling> set, final ILets lets);	

    /**
     * Apply a change and post notifications about it
     */
	public IStateScope apply(final Set<IStateChangeSource> causes, final IStateChangeSource proximate, final IStateAction action, final Set<IDwelling> targets, final ILets lets);

    public IStateScope branch(final IStateChangeSource tag);
    public void apply(final IStateScope branch, final Set<IStateChangeSource> causes);
    
    public DateTime getTrueDate();
}
