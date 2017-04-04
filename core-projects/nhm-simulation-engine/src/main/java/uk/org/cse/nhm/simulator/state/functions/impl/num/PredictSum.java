package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.SurrealTimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class PredictSum extends AbstractNamed implements IComponentsFunction<Number> {
	private final IComponentsFunction<Number> horizon;
	private final IComponentsFunction<Number> value;
	
	private final ITimeDimension time;
	private final Set<XForesightLevel> foresight;
	
	@AssistedInject
	public PredictSum(
			@Assisted("horizon") final IComponentsFunction<Number> horizon,
			@Assisted("value") final IComponentsFunction<Number> value, 
			@Assisted final Set<XForesightLevel> foresight,
			final ITimeDimension time) {
		super();
		this.horizon = horizon;
		this.value = value;
		this.time = time;
		this.foresight = foresight;
	}

	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		// evaluate the horizon function
		final int horizon = this.horizon.compute(scope, lets).intValue();
		// special cases
		if (horizon <= 0) return 0;
		else if (horizon == 1) return this.value.compute(scope, lets);
		// create hypothetical scope; at this point cost.sum and stuff is all detached.
		final IHypotheticalComponentsScope hypothesis = scope.createHypothesis();
		// shim in a bogus time dimension
		final DateTime startDate = scope.get(time).get(XForesightLevel.Always);
		final SurrealTimeDimension pseudotime = new SurrealTimeDimension(this.time.index(), startDate, foresight);
		hypothesis.replace(this.time, pseudotime);
		// loop over horizon; advance the predictable years and recompute
		// the target value, accumulating it
		DateTime bogusDate = startDate;
		double acc = 0;
		for (int i = 0; i<horizon; i++) {
			acc += this.value.compute(hypothesis, lets).doubleValue();
			bogusDate = bogusDate.plusYears(1);
			// advance the fake clocks. wat.
			pseudotime.setCurrentTime(bogusDate);
		}
		return acc;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Sets.union(value.getDependencies(), horizon.getDependencies());
	}
	
	@Override
	public Set<DateTime> getChangeDates() {
		throw new UnsupportedOperationException("predict-sum cannot sensibly be used to control things which might change over time");
	}
}
