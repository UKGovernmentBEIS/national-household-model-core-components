package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchHasCentralHeating extends AbstractNamed implements IComponentsFunction<Boolean> {
	
	private final IDimension<ITechnologyModel> techDimension;
	private final boolean includeBroken;

	@AssistedInject
	public MatchHasCentralHeating(
			@Assisted final boolean includeBroken,
			final IDimension<ITechnologyModel> techDimension
			) {
		this.includeBroken = includeBroken;
		this.techDimension = techDimension;
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		final IPrimarySpaceHeater primarySpaceHeater = scope.get(techDimension).getPrimarySpaceHeater();
		if (primarySpaceHeater instanceof ICentralHeatingSystem) {
			if(includeBroken) {
				return true;
			} else {
				return  !((ICentralHeatingSystem)primarySpaceHeater).isBroken();
			}
		}
		return false;
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
