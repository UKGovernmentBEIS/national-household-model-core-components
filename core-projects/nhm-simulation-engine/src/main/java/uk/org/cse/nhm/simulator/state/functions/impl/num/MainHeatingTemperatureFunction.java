package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MainHeatingTemperatureFunction extends AbstractNamed implements IComponentsFunction<Number> {
	final IDimension<IHeatingBehaviour> heating;
	
	@Inject
	public MainHeatingTemperatureFunction(final IDimension<IHeatingBehaviour> heating) {
		super();
		this.heating = heating;
	}

	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(heating).getLivingAreaDemandTemperature();
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(heating);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.<DateTime>emptySet();
	}

}
