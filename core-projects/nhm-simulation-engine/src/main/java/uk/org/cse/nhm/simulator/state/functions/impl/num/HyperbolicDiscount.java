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
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HyperbolicDiscount extends AbstractNamed implements IComponentsFunction<Number> {
	private final ITimeDimension time;
	private final IComponentsFunction<Number> beta;
	private final IComponentsFunction<Number> delta;
	private final IComponentsFunction<Number> value;
	
	@AssistedInject
	public HyperbolicDiscount(
			final ITimeDimension time,
			@Assisted("beta") final IComponentsFunction<Number> beta, 
			@Assisted("delta") final IComponentsFunction<Number> delta, 
			@Assisted("value") final IComponentsFunction<Number> value) {
		super();
		this.time = time;
		this.beta = beta;
		this.delta = delta;
		this.value = value;
	}
	
	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		// compute the discount rate
		
		// figure out the year
		final ITime time = scope.get(this.time);
		final double year = time.get(XForesightLevel.Always).getYear() - time.get(XForesightLevel.Never).getYear();
		if (year == 0) return 1;
		
		// compute the discount ratio
		final double beta = this.beta.compute(scope, lets).doubleValue();
		final double delta = this.beta.compute(scope, lets).doubleValue();
		// compute the value function
		final double value = this.value.compute(scope, lets).doubleValue();
		return value * beta * Math.pow(delta, year);
	}
	@Override
	public Set<IDimension<?>> getDependencies() {
		return Sets.union(Sets.union(beta.getDependencies(), delta.getDependencies()), value.getDependencies());
	}
	@Override
	public Set<DateTime> getChangeDates() {
		throw new UnsupportedOperationException("geometric discount should not be used in a time-sensitive context");
	}
}
