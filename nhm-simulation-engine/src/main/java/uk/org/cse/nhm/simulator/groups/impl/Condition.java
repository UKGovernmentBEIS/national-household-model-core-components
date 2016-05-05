package uk.org.cse.nhm.simulator.groups.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Implements a device for constructing and coordinating a sequence of mutually
 * exclusive house instance groups.
 * 
 * After construction, use {@link #addCondition(IComponentsFunction)}, to add a
 * sequence of conditions. This will return you a group, which will contain all
 * the instances which match that condition and none of the earlier conditions.
 * This is effectively like an <code>
 * 	if (condition1) {
 * 
 *  } else if (condition2) {
 *  
 *  } else if (condition3) {
 *  
 *  } else {
 *  
 *  }
 * </code>
 * 
 * device in Java source. To get the group for the final "else", use
 * {@link #getDefaultGroup()}. This is the catchall for other cases.
 * 
 * The Condition adds itself as a listener to its source group, and to the
 * state, and manages the contents of the groups it contains efficiently. This
 * avoids the need to construct a group like (condition) AND (NOT (IN G1 OR G2
 * OR G3 ...)) for each condition.
 * 
 * @author hinton
 * 
 */
public class Condition {
	private final List<Group> groups = new LinkedList<Condition.Group>();
	private final ICanonicalState state;
	private final LinkedHashSet<IConditionListener> listeners = new LinkedHashSet<Condition.IConditionListener>();

	public interface IConditionGroup extends IDwellingGroup {
		public IComponentsFunction<Boolean> getCondition();
	}

	/**
	 * A listener interface to allow the use of conditions to track flows of
	 * dwellings due to state changes.
	 * 
	 * @author hinton
	 * 
	 */
	public interface IConditionListener {
		/**
		 * When a state change has updated the condition's source group, this
		 * method will get invoked
		 * 
		 * @param groups
		 * @param gained
		 *            indexed by groups, this is how many new houses entered
		 *            each group
		 * @param lost
		 *            indexed by groups, this is how many previously contained
		 *            houses went from each group.
		 */
		public void conditionMembershipChanged(final IStateChangeNotification cause, final List<? extends IConditionGroup> groups, final float[] gained, final float[] lost);

		/**
		 * When a state change has caused the condition to reevaluate its
		 * contents, once all the contained groups are updated and have notified
		 * their listeners this method will be invoked on all condition
		 * listeners.
		 * 
		 * The flows parameter contains an array, indexed by the list of groups,
		 * where the [i][j] element indicates the number of dwellings that moved
		 * from group i to group j as a consequence of the notification.
		 * 
		 * @param cause
		 * @param groups
		 * @param flows
		 */
		public void conditionAssignmentChanged(final IStateChangeNotification cause, final List<? extends IConditionGroup> groups, final float[][] flows);
	}

	@Inject
	public Condition(final ICanonicalState state, 
			@Assisted final IDwellingGroup source,
			@Assisted final List<IComponentsFunction<Boolean>> tests) {
		
		this.state = state;
		state.addStateListener(new IStateListener() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
				final float[][] flows = new float[groups.size()][groups.size()];

				dwellings: for (final IDwelling d : notification.getChangedDwellings(getDependencies())) {
					if (source.contains(d)) {
						// then we might contain d
						int acceptedBy = -1;
						int indexOfG = -1;
						int removedFrom = -1;
						for (final Group g : groups) {
							indexOfG++;
							if (acceptedBy >= 0) {
								if (g.reject(d)) {
									flows[indexOfG][acceptedBy]+=d.getWeight();
									continue dwellings;
								}
							} else if (removedFrom >= 0) {
								if (g.accept(state, d)) {
									flows[removedFrom][indexOfG]+=d.getWeight();
									continue dwellings;
								}
							} else {
								switch (g.acceptChange(state, d)) {
								case NEWLY_CONTAINED:
									acceptedBy = indexOfG;
									break;
								case REMOVED:
									removedFrom = indexOfG;
									break;
								case ALREADY_CONTAINED:
									continue dwellings;
								}
							}
						}
					}
				}

				for (final Group g : groups) {
					g.commit(notification, Collections.<IDwelling> emptySet());
				}

				// notify condition listeners
				for (final IConditionListener listener : listeners.toArray(new IConditionListener[listeners.size()])) {
					listener.conditionAssignmentChanged(notification, groups, flows);
				}
			}
		});
		
		source.addListener(this.listener);
		
		for (final IComponentsFunction<Boolean> bcf : tests) {
			addCondition(bcf);
		}
	}

	protected Set<IDimension<?>> getDependencies() {
		final HashSet<IDimension<?>> dependencies = new HashSet<IDimension<?>>();
		for (final Group g : groups) {
			dependencies.addAll(g.condition.getDependencies());
		}
		return dependencies;
	}

	private final IDwellingGroupListener listener = new IDwellingGroupListener() {
		@Override
		public void dwellingGroupChanged(final IStateChangeNotification cause, final IDwellingGroup source, final Set<IDwelling> added, final Set<IDwelling> removed) {
			final float[] deltaIn = new float[groups.size()];
			final float[] deltaOut = new float[groups.size()];

			dwellings: for (final IDwelling a : added) {
				int groupIndex = -1;
				for (final Group g : groups) {
					groupIndex++;
					if (g.accept(state, a)) {
						deltaIn[groupIndex]+=a.getWeight();
						continue dwellings;
					}
				}
			}

			int groupIndex = -1;
			for (final Group g : groups) {
				groupIndex++;
				deltaOut[groupIndex] += g.commit(cause, removed);
			}

			for (final IConditionListener listener : listeners.toArray(new IConditionListener[listeners.size()])) {
				listener.conditionMembershipChanged(cause, groups, deltaIn, deltaOut);
			}
		}
	};
	private IDwellingGroup defaultGroup;

	/**
	 * The {@link IDwellingGroup} implementation that {@link Condition}
	 * builds.
	 * 
	 * @author hinton
	 * 
	 */
	static class Group extends BaseDwellingGroup implements IConditionGroup {
		private final IComponentsFunction<Boolean> condition;
		private final LinkedHashSet<IDwelling> addedInChange = new LinkedHashSet<IDwelling>();
		private final LinkedHashSet<IDwelling> removedInChange = new LinkedHashSet<IDwelling>();

		public enum Change {
			/**
			 * The group contains this dwelling, but that is not a change
			 */
			ALREADY_CONTAINED,
			/**
			 * The group contains this dwelling, but it didn't previously
			 */
			NEWLY_CONTAINED,
			/**
			 * The group doesn't contain this dwelling, and never did
			 */
			NOT_CONTAINED,
			/**
			 * The group no longer contains this dwelling, but it used to.
			 */
			REMOVED
		}

		/**
		 * Make a group matching the given condition
		 * 
		 * @param condition
		 */
		public Group(final IComponentsFunction<Boolean> condition) {
			this.condition = condition;
		}

		@Override
		public IComponentsFunction<Boolean> getCondition() {
			return this.condition;
		}

		/**
		 * This is called when, as a consequence of a state change, a dwelling
		 * is contained in an earlier group than the one which previously
		 * contained it.
		 * 
		 * @param d
		 */
		public boolean reject(final IDwelling d) {
			if (contains(d)) {
				removedInChange.add(d);
				return true;
			} else {
				return false;
			}
		}

		/**
		 * This is called when, as a consequence of a state change, a dwelling
		 * may be moved amongst the groups.
		 * 
		 * @param d
		 * @return {@link Change#ALREADY_CONTAINED}, if d is already in this
		 *         group (so we can break out early), or
		 *         {@link Change#NEWLY_CONTAINED} if d was not in this group but
		 *         now is (so that subsequent groups need to be updated to not
		 *         contain d).
		 */
		public Change acceptChange(final IState state, final IDwelling d) {
			final boolean shouldContain = evaluate(state.detachedScope(d));
			if (contains(d)) {
				if (shouldContain) {
					return Change.ALREADY_CONTAINED;
				} else {
					removedInChange.add(d);
					return Change.REMOVED;
				}
			} else {
				if (shouldContain) {
					addedInChange.add(d);
					return Change.NEWLY_CONTAINED;
				}
			}
			return Change.NOT_CONTAINED;
		}

		private final boolean evaluate(final IComponentsScope componentsScope) {
			final Boolean b = condition.compute(componentsScope, ILets.EMPTY);
			if (b == null)
				return false;
			return b;
		}

		public float commit(final IStateChangeNotification cause, final Set<IDwelling> removed) {
			removedInChange.addAll(removed);

			super.update(cause, addedInChange, removedInChange);

			removedInChange.clear();
			addedInChange.clear();

			return getLastRemovedCount();
		}

		public boolean accept(final IState state, final IDwelling a) {
			if (evaluate(state.detachedScope(a))) {
				addedInChange.add(a);
				return true;
			} else {
				return false;
			}
		}
	}

	public IDwellingGroup addCondition(final IComponentsFunction<Boolean> condition) {
		final Group group = new Group(condition);
		group.setIdentifier(condition.getIdentifier());
		
		if (defaultGroup != null) {
			groups.add(groups.size() - 1, group);
		} else {
			groups.add(group);
		}
		return group;
	}

	public IDwellingGroup getDefaultGroup() {
		if (defaultGroup == null) {
			defaultGroup = addCondition(IComponentsFunction.TRUE);
		}
		return defaultGroup;
	}

	public void addConditionListener(final IConditionListener listener) {
		listeners.add(listener);
	}

	public List<? extends IDwellingGroup> getGroups() {
		if (defaultGroup == null) return groups;
		else return groups.subList(0, groups.size()-1);
	}
}
