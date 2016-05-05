package uk.org.cse.nhm.simulation.measure.boilers;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class BreakBoilerMeasure extends AbstractMeasure {
	private final IDimension<ITechnologyModel> techDimension;
	private final ITechnologyOperations operations;

	@AssistedInject
	public BreakBoilerMeasure(
			final IDimension<ITechnologyModel> techDimension,
			final ITechnologyOperations operations) {
				this.techDimension = techDimension;
				this.operations = operations;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		scope.modify(techDimension, new IModifier<ITechnologyModel>(){

			@Override
			public boolean modify(final ITechnologyModel tech) {
				final Optional<IBoiler> boiler = operations.getBoiler(tech);
				if(!boiler.isPresent()) {
					return false;
				} else {
					operations.removeHeatSource(boiler.get());
					return true;
				}
			}});
		return true;
	}
	
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final ITechnologyModel tech = scope.get(techDimension);
		return operations.getBoiler(tech).isPresent();
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
		}
}
