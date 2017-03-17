package uk.org.cse.nhm.simulator.state.functions.impl.health;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

import com.google.common.collect.ImmutableSet;

public class PermeabilityFunction extends AbstractNamed implements IComponentsFunction<Number> {
	private final IDimension<StructureModel> structure;
	private final IDimension<IPowerTable> energy;

	@Inject
	public PermeabilityFunction(
			final IDimension<StructureModel> structure,
			final IDimension<IPowerTable> energy) {
		super();
		this.structure = structure;
		this.energy = energy;
	}

	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		final StructureModel structure = scope.get(this.structure);
		final IPowerTable energy = scope.get(this.energy);

		final double achPerHour = energy.getAirChangeRate();
		final double envelope =	structure.getEnvelopeArea();
		final double volume = structure.getVolume();

		return achPerHour * volume * 20 / envelope;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.of(structure, energy);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}