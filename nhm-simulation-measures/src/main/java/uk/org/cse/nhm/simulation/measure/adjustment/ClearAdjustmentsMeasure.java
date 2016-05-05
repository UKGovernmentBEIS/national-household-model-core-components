package uk.org.cse.nhm.simulation.measure.adjustment;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ClearAdjustmentsMeasure extends AbstractMeasure implements IModifier<ITechnologyModel> {
	private final IDimension<ITechnologyModel> technologies;
	
	@AssistedInject
	public ClearAdjustmentsMeasure(
			final IDimension<ITechnologyModel> technologies
			) {
		this.technologies = technologies;
	}

	@Override
	public boolean modify(final ITechnologyModel modifiable) {
		modifiable.getAdjusters().clear();
		return true;
	}
	
	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		if (isSuitable(scope, lets)) {
			scope.modify(technologies, this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return true;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
