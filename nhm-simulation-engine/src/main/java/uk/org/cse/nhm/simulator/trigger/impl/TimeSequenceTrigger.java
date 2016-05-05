package uk.org.cse.nhm.simulator.trigger.impl;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;

public class TimeSequenceTrigger extends TimedTrigger {
	private DateTime startDate;
	private SortedSet<DateTime> dates = new TreeSet<DateTime>();
	private Iterator<IDwellingGroupSampler> samplerIterator;
	
	@Inject
	public TimeSequenceTrigger(final ISimulator simulator, final ICanonicalState state,
			@Named(SimulatorConfigurationConstants.START_DATE) final DateTime startDate,
			@Assisted final List<DateTime> dates,
			@Assisted final IDwellingGroup group,
			@Assisted final List<IDwellingGroupSampler> samplers,
			@Assisted final IStateAction action,
			@Assisted final Name name
			) {
		super(simulator, state, group, action);
		this.setIdentifier(name);
		this.startDate = startDate;
		this.dates.addAll(dates);
		this.samplerIterator = samplers.iterator();
		if (dates.size() != samplers.size()) throw new RuntimeException("Time sequence trigger must be constructed with equal number of dates and samplers ("
				+ dates.size() + " vs. " + samplers.size() + ")");
	}
	
	@Override
	protected IDwellingGroupSampler getSampler() {
		return samplerIterator.next();
	}

	@Override
	public void initialize() throws NHMException {
		for (final DateTime dt : dates) {
			if (dt.isBefore(startDate)) continue;
			simulator.schedule(dt, getPriority(), this);
		}
	}
}
