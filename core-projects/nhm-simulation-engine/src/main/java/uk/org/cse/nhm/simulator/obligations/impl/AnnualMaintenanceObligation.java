package uk.org.cse.nhm.simulator.obligations.impl;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public class AnnualMaintenanceObligation extends AnnualObligation {

    private static final Set<String> TAGS = ImmutableSet.of(ITransaction.Tags.OPEX);
    final IDimension<ITechnologyModel> technologies;

    @Inject
    protected AnnualMaintenanceObligation(
            final ITimeDimension time,
            final IDimension<ITechnologyModel> technologies,
            final ICanonicalState state,
            final ISimulator simulator) {
        super(time, state, simulator, 1);
        this.technologies = technologies;
        setIdentifier(Name.of("Annual Maintenance"));
    }

    protected Set<IPayment> doGeneratePayments(IComponentsScope state, ILets lets) {
        final double opex = state.get(technologies).getTotalOperationalCost();
        if (opex == 0) {
            return Collections.<IPayment>emptySet();
        } else {
            return Collections.<IPayment>singleton(
                    Payment.of(
                            ITransaction.Counterparties.MAINTENANCE,
                            opex,
                            TAGS));
        }
    }
}
