package uk.org.cse.nhm.simulator.action.fuels.extracharges;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IExtraCharge;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;

public class ExtraChargeAction extends AbstractNamed implements IComponentsAction {

	private final IDimension<ITariffs> tariffsDimension;
	private final IExtraCharge extraCharge;

	@AssistedInject
	public ExtraChargeAction(final IDimension<ITariffs> tariffsDimension, @Assisted final IExtraCharge extraCharge) {
		this.tariffsDimension = tariffsDimension;
		this.extraCharge = extraCharge;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
		if (isSuitable(scope, lets)) {
			scope.modify(tariffsDimension, new IModifier<ITariffs>(){
	
				@Override
				public boolean modify(final ITariffs tariffs) {
					tariffs.addExtraCharge(extraCharge);
					return true;
				}});
		
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return !scope.get(tariffsDimension).hasExtraCharge(extraCharge);
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
}
