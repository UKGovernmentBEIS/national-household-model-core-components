package uk.org.cse.nhm.simulator.state;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.simulator.scope.IStateScope;

/**
 * These are emitted by the {@link IState} when the state changes.
 * 
 * @author hinton
 *
 */
public interface IStateChangeNotification {
	/**
	 * @return a set of all the dwellings that are newly created in this change
	 */
	public Set<IDwelling> getCreatedDwellings();
	
	/**
	 * @return a set of all of the dwellings that have been modified in this change, in their modified states
	 */
	public Set<IDwelling> getAllChangedDwellings();

	/**
	 * @return the set of all dwellings destroyed by this change.
	 */
	public Set<IDwelling> getDestroyedDwellings();
	
	/**
	 * @return the scope which holds all the result data for this state change event.
	 */
	public IStateScope getRootScope();
	
	/**
	 * @param components
	 * @return All dwellings with changes to any of the given components
	 */
	public Set<IDwelling> getChangedDwellings(final Set<IDimension<?>> components);
	
	/**
	 * @param components
	 * @return All dwellings with changes to any of the given components
	 */
	public Set<IDwelling> getChangedDwellings(final IDimension<?>... components);
	
	/**
	 * @return the time of the notification
	 */
	public DateTime getDate();
	
	/**
	 * The components which have changed for this notification.
	 * @param d
	 * @return
	 */
	public Set<IDimension<?>> getChangesToDwelling(final IDwelling d);

	/**
	 * @return true if anything is altered, created, or destroyed in this change.
	 */
	boolean isChange();
	
	/**
	 * A convenience method for {@link #getRootScope()}.getComponentsScope(dwelling).get().getAllNotes(clazz);
	 * @param dwelling
	 * @param clazz
	 * @return
	 * @since 3.7.0
	 */
	public <T> List<T> getAllNotes(final IDwelling dwelling, final Class<T> clazz);

	Set<IStateChangeSource> getCauses();
}
