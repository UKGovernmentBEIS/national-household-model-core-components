package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XSpaceHeatingSystem;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HeatingResponsivenessFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final XSpaceHeatingSystem system;
	private final IDimension<ITechnologyModel> techDimension;
	private final IConstants constants;

	@AssistedInject
	public HeatingResponsivenessFunction(
			@Assisted final XSpaceHeatingSystem system,
			final IConstants constants,
			final IDimension<ITechnologyModel> techDimension) {
				this.system = system;
				this.constants = constants;
				this.techDimension = techDimension;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final ITechnologyModel tech = scope.get(techDimension);
		
		switch(system) {
		case PrimarySpaceHeating:
			
			if (tech.getPrimarySpaceHeater() == null) {
				return 0.0;
			} else {
				return tech.getPrimarySpaceHeater().getDerivedResponsiveness(constants);
			}
			
		case SecondarySpaceHeating:
			if (tech.getSecondarySpaceHeater() == null) {
				return 0.0; 
			} else {
				return tech.getSecondarySpaceHeater().getResponsiveness();
			}
		default:
			throw new IllegalArgumentException("Unknown space heating system type " + system);
		}
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(techDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return ImmutableSet.of();
	}
}
