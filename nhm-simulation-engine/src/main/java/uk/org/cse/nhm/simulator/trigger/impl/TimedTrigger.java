package uk.org.cse.nhm.simulator.trigger.impl;

import java.util.Set;

import org.joda.time.DateTime;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.trigger.ITrigger;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;

/**
 * A basic implementation of the trigger interface; the only logic in this is in the {@link #apply()} method.
 * @author hinton
 *
 */
@AutoProperty
public abstract class TimedTrigger extends AbstractNamed implements ITrigger, IDateRunnable, Initializable {
	private static final Logger log = LoggerFactory.getLogger(TimedTrigger.class);
	
	private final IDwellingGroup group;
	
	@Property(policy=PojomaticPolicy.NONE)
	private final IStateAction action;
	
	@Property(policy=PojomaticPolicy.NONE)
	private final ICanonicalState state;

	@Property(policy=PojomaticPolicy.NONE)
	protected final ISimulator simulator;
	
	public TimedTrigger(final ISimulator simulator, final ICanonicalState state,
			final IDwellingGroup group, final IStateAction action) {
		this.simulator = simulator;
		this.state = state;
		this.group = group;
		this.action = action;
	}

	private Priority priority;
	
	protected final Priority getPriority() {
		return priority;
	}
	
	@Override
	public void setIdentifier(final Name newName) {
		super.setIdentifier(newName);
		this.priority = Priority.ofIdentifier(newName);
	}

	@Override
	public void run(final DateTime date) throws NHMException {
		// first get the candidates from the exposure function
		final Set<IDwelling> applyTo = getSampler().sample(state.getRandom(), group.getContents());
		if (log.isInfoEnabled()) log.info("{} applied to {} of {} instances", new Object[]{ this, applyTo.size(), group.getContents().size()});
		// next get the proposed change from the action, and apply it to the state
		
		state.apply(TimedTrigger.this, action, applyTo, ILets.EMPTY);
	}
	
	protected abstract IDwellingGroupSampler getSampler();
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.TRIGGER;
	}
}
