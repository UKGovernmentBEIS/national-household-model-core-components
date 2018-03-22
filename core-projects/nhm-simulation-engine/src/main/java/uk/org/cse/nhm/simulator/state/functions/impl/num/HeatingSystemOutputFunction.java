package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import com.google.common.base.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HeatingSystemOutputFunction extends AbstractNamed implements IComponentsFunction<Double> {
    private final IDimension<IPowerTable> energy;
	private final XHeatingSystem system;
	private final IDimension<ITechnologyModel> technologies;

    @Inject
	public HeatingSystemOutputFunction(
			final IDimension<IPowerTable> energy,
			final IDimension<ITechnologyModel> technologies,
			@Assisted Optional<XHeatingSystem> system) {
        this.technologies = technologies;
		this.system = system.orNull();
        this.energy = energy;
    }

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final IPowerTable table = scope.get(energy);
		final ITechnologyModel tech = scope.get(technologies);
		
		final boolean usedPrimary = !(tech.getCentralWaterSystem() == null || tech.getCentralWaterSystem().isBroken());
		
		if (system == null) {
			return (double)table.getHotWaterDemand() + table.getPrimaryHeatDemand() + table.getSecondaryHeatDemand();
		} else {
			switch (system) {
			case AuxiliaryHotWater:
				if (usedPrimary) return 0d;
				else return (double)table.getHotWaterDemand();
			case CentralHotWater:
				if (usedPrimary) return (double) table.getHotWaterDemand();
				else return 0d;
			case PrimarySpaceHeating: return (double)table.getPrimaryHeatDemand();
			case SecondarySpaceHeating: return (double)table.getSecondaryHeatDemand();
			default: return null;
			}
		}
    }
    
    @Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(energy);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}