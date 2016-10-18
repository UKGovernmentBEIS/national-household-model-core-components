package uk.org.cse.nhm.simulator.state.functions.impl.logic;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchSimYear extends AbstractNamed implements IComponentsFunction<Boolean> {
	private final ITimeDimension time;
	private final Predicate<Integer> test;
	private final ImmutableSet<DateTime> changeDates;
	private final Optional<XForesightLevel> foresight;
	
	@Inject
	public MatchSimYear(
			final ITimeDimension time,
			@Assisted final Optional<XForesightLevel> foresight, 
			@Named(SimulatorConfigurationConstants.START_DATE) final DateTime start,
			@Named(SimulatorConfigurationConstants.END_DATE) final DateTime end,
			@Assisted final Predicate<Integer> test) {
		this.time = time;
		this.foresight = foresight;
		this.test = test;
		
		boolean lastValue = test.apply(start.getYear());
		
		final ImmutableSet.Builder<DateTime> b = ImmutableSet.builder();
		
		for (int year = start.getYear(); year <= end.getYear(); year++) {
			final boolean thisValue = test.apply(year);
			if (thisValue != lastValue) {
				b.add(new DateTime(year, 1, 1, 0, 0, 0, 0));
			}
			lastValue = thisValue;
		}
		
		this.changeDates = b.build();
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return test.apply(scope.get(time).get(foresight, lets).getYear());
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>>singleton(time);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return changeDates;
	}
}
