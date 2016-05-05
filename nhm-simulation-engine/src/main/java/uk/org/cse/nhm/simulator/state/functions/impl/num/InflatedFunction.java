package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import javax.management.timer.Timer;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Years;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.inject.name.Named;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class InflatedFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final DateTime startDate;
	private final ITimeDimension time;
	private final IComponentsFunction<? extends Number> delegate;
	private final double rate;
	private final Set<DateTime> changeDates;
	private final Optional<XForesightLevel> foresight;
		
	@AssistedInject
	InflatedFunction(
			@Named(SimulatorConfigurationConstants.START_DATE) final DateTime simStartDate,
			@Named(SimulatorConfigurationConstants.END_DATE) final DateTime simEndDate,
			final ITimeDimension time,
			@Assisted final Optional<XForesightLevel> foresight,
			@Assisted final Optional<DateTime> startDate, 
			@Assisted final IComponentsFunction<? extends Number> delegate, 
			@Assisted final double rate) {
		super();
		this.foresight = foresight;
		this.startDate = startDate.or(simStartDate);
		this.time = time;
		this.delegate = delegate;
		this.rate = rate;
		
		this.changeDates = changeDates(simStartDate, simEndDate, this.startDate);
	}

	private static Set<DateTime> changeDates(
			final DateTime simStartDate, 
			final DateTime simEndDate,
			final DateTime startDate) {
		final ImmutableSet.Builder<DateTime> b = ImmutableSet.builder();
		
		final Years y = Years.years(1);
		
		DateTime winder = startDate;
		while (winder.isAfter(simStartDate)) {
			winder = winder.minus(y);
		}
		
		while (simEndDate.isAfter(winder)) {
			if (!winder.isBefore(simStartDate)) b.add(winder);
			winder = winder.plus(y);
		}
		
		return b.build();
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		return getInflation(scope, lets) * delegate.compute(scope, lets).doubleValue();
	}

	private double getInflation(final IComponentsScope scope, final ILets lets) {
		final DateTime now = scope.get(time).get(foresight, lets);
		
		// so number of years between now and startDate
		final Duration duration = new Duration(startDate, now);
		
		final long years = duration.getMillis() / (Timer.ONE_DAY * 365);
		
		return Math.pow(rate, years);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>builder()
				.addAll(delegate.getDependencies())
				.add(time)
				.build();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return changeDates;
	}
}
