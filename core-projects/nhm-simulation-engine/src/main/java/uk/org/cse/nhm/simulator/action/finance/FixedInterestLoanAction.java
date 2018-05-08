package uk.org.cse.nhm.simulator.action.finance;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.ISettableIdentified;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.factories.IFinanceFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.ILoan;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * Implementation of a fixed rate loan action
 *
 * @since 3.7.0
 */
public class FixedInterestLoanAction extends AbstractNamed implements IComponentsAction {

    private final ITimeDimension time;
    private final IComponentsFunction<? extends Number> principal;
    private final IComponentsAction delegate;
    private final IComponentsFunction<Number> term;
    private final IComponentsFunction<Number> rate;
    private final IComponentsFunction<Number> tilt;
    private final String payee;

    private final Set<String> principalTags;
    private final Set<String> paymentTags;

    private final IFinanceFactory financeFactory;
    private final IDimension<IObligationHistory> history;
    private final IProfilingStack profilingStack;

    @AssistedInject
    public FixedInterestLoanAction(
            final IFinanceFactory financeFactory,
            final ITimeDimension time,
            final IDimension<IObligationHistory> history,
            final IProfilingStack profilingStack,
            @Assisted("principal") final IComponentsFunction<? extends Number> principal,
            @Assisted final IComponentsAction delegate,
            @Assisted("term") final IComponentsFunction<Number> term,
            @Assisted("rate") final IComponentsFunction<Number> rate,
            @Assisted("tilt") final IComponentsFunction<Number> tilt,
            @Assisted final String payee,
            @Assisted final Set<String> tags) {
        this.time = time;
        this.history = history;
        this.profilingStack = profilingStack;
        this.principal = principal;
        this.delegate = delegate;
        this.term = term;
        this.rate = rate;
        this.tilt = tilt;
        this.payee = payee;
        this.principalTags = ImmutableSet.<String>builder().addAll(
                tags
        ).add(ILoan.Tags.Principal, ILoan.Tags.Loan).build();
        this.paymentTags = ImmutableSet.<String>builder().addAll(
                tags
        ).add(ILoan.Tags.Repayment, ILoan.Tags.Loan).build();
        this.financeFactory = financeFactory;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        if (delegate.apply(scope, lets)) {
            final int term = this.term.compute(scope, lets).intValue();
            final double rate = this.rate.compute(scope, lets).doubleValue();
            final double tilt = this.tilt.compute(scope, lets).doubleValue();
            final double principal = this.principal.compute(scope, lets).doubleValue();
            if (principal == 0) {
                return true;
            }
            if (principal < 0) {
                throw profilingStack.die("Principal for loan was negative: " + principal, this, scope);
            }
            scope.addTransaction(Payment.of(payee, -principal, principalTags));
            final IObligation obligation = financeFactory.createFixedRepaymentLoanObligation(
                    scope.get(time).get(lets), principal, term,
                    scope.get(history).size(), rate, tilt, payee, paymentTags);
            if (obligation instanceof ISettableIdentified) {
                ((ISettableIdentified) obligation).setIdentifier(getIdentifier());
            }
            scope.addObligation(obligation);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return delegate.isSuitable(scope, lets);
    }

    @Override
    public boolean isAlwaysSuitable() {
        return delegate.isAlwaysSuitable();
    }
}
