package uk.org.cse.nhm.simulator.trigger.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.trigger.ITrigger;

/**
 * A trigger which listens to its group, and fires its action on all elements added to the group after a
 * specified delay (settable through {@link #setDelay(Period)}).
 * 
 * @author hinton
 *
 */
public class DelayedGroupMembershipTrigger extends AbstractNamed implements ITrigger, IDwellingGroupListener {
	private final IStateAction action;
	private final Period delay;
	private final ICanonicalState state;

	private final ISimulator simulator;

	private Priority priority;
	
	@Inject
	public DelayedGroupMembershipTrigger(
			final ICanonicalState state,
			final ISimulator simulator,
			@Assisted final Period delay,
			@Assisted final IDwellingGroup group, 
			@Assisted final IStateAction action,
			@Assisted final Name name
			) {
		this.setIdentifier(name);
		this.delay = delay;
		this.state = state;
		this.action = action;
		this.simulator = simulator;
        
		group.addListener(this);
	}

	@Override
	public void setIdentifier(final Name newName) {
		super.setIdentifier(newName);
		this.priority = Priority.ofIdentifier(newName);
	}

	@Override
	public void dwellingGroupChanged(final IStateChangeNotification cause, final IDwellingGroup source, final Set<IDwelling> added, final Set<IDwelling> removed) {
		final Set<IDwelling> filtered = new LinkedHashSet<IDwelling>(added);
		if (!filtered.isEmpty()) {
			simulator.schedule(cause.getDate().plus(delay), priority, new IDateRunnable() {
				@Override
				public void run(final DateTime date) {
					state.apply(DelayedGroupMembershipTrigger.this, action, filtered, ILets.EMPTY);
				}
				
				@Override
				public String toString() {
					return "Delayed event for " + DelayedGroupMembershipTrigger.this;
				}
			});
		}
	}


	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.TRIGGER;
	}
}
