package uk.org.cse.nhm.simulator.action;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ReducedInternalGainsAction extends AbstractNamed implements IComponentsAction, IModifier<StructureModel> {

	private final IDimension<StructureModel> structureDimension;

	@AssistedInject
	public ReducedInternalGainsAction(
			final IDimension<StructureModel> structureDimension
			) {
				this.structureDimension = structureDimension;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean modify(StructureModel structure) {
		structure.setReducedInternalGains(true);
		return true;
	}

	@Override
	public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
		scope.modify(structureDimension, this);
		return true;
	}

	@Override
	public boolean isSuitable(IComponentsScope scope, ILets lets) {
		return true;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}
}
