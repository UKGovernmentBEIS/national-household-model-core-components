package uk.org.cse.nhm.simulator.state.functions.impl.health;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import uk.ac.ucl.hideem.IHealthModule;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.language.definition.function.num.XEfficiencyMeasurement;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatingEfficiencyFunction;

import com.google.common.collect.ImmutableSet;

public class SITFunction extends AbstractNamed implements IComponentsFunction<Number> {
	private final IDimension<IPowerTable> energy;
	private final IDimension<ITechnologyModel> technologies;
	private final IHealthModule health;

	@Inject
	public SITFunction(
			final IHealthModule health,
			final IDimension<IPowerTable> energy,
			final IDimension<ITechnologyModel> technologies) {
		super();
		this.health = health;
		this.energy = energy;
		this.technologies = technologies;
	}

	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		final IPowerTable energy = scope.get(this.energy);
		final ITechnologyModel technologies = scope.get(this.technologies);

		// watts / k
		final double heatLoss = energy.getSpecificHeatLoss();
		final double efficiency = HeatingEfficiencyFunction.getAnySpaceHeatingEfficiency(technologies, XEfficiencyMeasurement.Winter);
		return health.getInternalTemperature(heatLoss, efficiency);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.of(technologies, energy);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}