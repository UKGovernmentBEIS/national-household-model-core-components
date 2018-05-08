package uk.org.cse.nhm.simulator.state.dimensions.energy;

import java.util.Collection;

import javax.inject.Inject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.obligations.impl.AnnualObligation;
import uk.org.cse.nhm.simulator.obligations.impl.BaseObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IEmissions;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

/**
 * An obligation to pay your fuel bill every year; this just gets annual total
 * cost from the {@link IEmissions} dimension and charges you that much paying
 * it to {@link ITransaction.Counterparties#ENERGY_COMPANIES}.
 *
 * @since 3.7.0
 *
 */
public class AnnualFuelObligation extends AnnualObligation {

    private final IDimension<ITariffs> tariffs;
    private final EnergyMeterDimension meters;
    private final ITimeDimension time;

    @Inject
    public AnnualFuelObligation(
            final EnergyMeterDimension meters,
            final ITimeDimension time,
            final IDimension<ITariffs> tariffs,
            final ICanonicalState state,
            final ISimulator simulator
    ) {
        super(time, state, simulator, 0);
        setIdentifier(Name.of("Fuel Bills"));
        this.meters = meters;
        this.time = time;
        this.tariffs = tariffs;
    }

    @Override
    protected Collection<IPayment> doGeneratePayments(final IComponentsScope state, final ILets lets) {
        final ISettableComponentsScope hypothesis = state.createHypothesis();
        applyToScope(hypothesis, lets);
        return hypothesis.getAllNotes(IPayment.class);
    }

    private final void applyToScope(final ISettableComponentsScope scope, final ILets lets) {
        final ITariffs tariffs = scope.get(this.tariffs);

        tariffs.computeCharges(scope, lets);
    }

    /**
     * This is overriding the method in {@link BaseObligation} to short-cut the
     * control flow, which would otherwise invoke
     * {@link AnnualObligation#generatePayments(org.joda.time.DateTime, IComponentsScope)},
     * which would in turn invoke
     * {@link #doGeneratePayments(IComponentsScope, ILets)} above, which would
     * produce an unnecessary hypothesis branch, which would be a waste.
     */
    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        if (isFiringDate(scope.get(time).get(lets))) {
            applyToScope(scope, lets);
            // horror: this should not happen anywhere except here.
            meters.reset(scope.getDwellingID());
        }
        return true;
    }
}
