package uk.org.cse.nhm.simulator.action.fuels.extracharges;

import com.google.common.base.Optional;
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

public class RemoveChargeAction extends AbstractNamed implements
        IComponentsAction {

    private final IDimension<ITariffs> tariffsDimension;
    private final Optional<IExtraCharge> extraCharge;
    private IModifier<ITariffs> modifier;

    @AssistedInject
    public RemoveChargeAction(final IDimension<ITariffs> tariffsDimension, @Assisted final Optional<IExtraCharge> extraCharge) {
        this.tariffsDimension = tariffsDimension;
        this.extraCharge = extraCharge;
        if (this.extraCharge.isPresent()) {
            this.modifier = new RemoveExtraCharge(this.extraCharge.get());
        } else {
            this.modifier = new ClearExtraCharges();
        }
    }

    private static class RemoveExtraCharge implements IModifier<ITariffs> {

        private final IExtraCharge charge;

        public RemoveExtraCharge(final IExtraCharge charge) {
            this.charge = charge;
        }

        @Override
        public boolean modify(final ITariffs modifiable) {
            return modifiable.removeExtraCharge(charge);
        }
    }

    private static class ClearExtraCharges implements IModifier<ITariffs> {

        @Override
        public boolean modify(final ITariffs modifiable) {
            return modifiable.clearExtraCharges();
        }
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        if (isSuitable(scope, lets)) {
            scope.modify(tariffsDimension, modifier);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        if (extraCharge.isPresent()) {
            return scope.get(tariffsDimension).hasExtraCharge(extraCharge.get());
        } else {
            return true;
        }
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }
}
