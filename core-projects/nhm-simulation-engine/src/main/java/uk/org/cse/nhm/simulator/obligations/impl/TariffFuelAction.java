package uk.org.cse.nhm.simulator.obligations.impl;

import java.util.List;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IExtraCharge;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;

public class TariffFuelAction extends AbstractNamed implements IComponentsAction {

    private final FuelType ft;
    private final ITariff t;
    private final List<IExtraCharge> extraCharges;

    public TariffFuelAction(final FuelType ft, final ITariff t, final List<IExtraCharge> extraCharges) {
        super(t.getIdentifierForFuel(ft));
        this.extraCharges = extraCharges;
        this.ft = ft;
        this.t = t;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.INTERNAL;
    }

    public FuelType getFuelType() {
        return ft;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        t.apply(ft, scope);

        for (final IExtraCharge c : extraCharges) {
            c.apply(scope, lets);
        }

        return true;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }
}
