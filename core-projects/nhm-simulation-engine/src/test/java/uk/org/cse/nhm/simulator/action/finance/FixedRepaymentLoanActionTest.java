package uk.org.cse.nhm.simulator.action.finance;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.factories.IFinanceFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.ILoan;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class FixedRepaymentLoanActionTest {

    IFinanceFactory factory;
    ISettableComponentsScope scope;
    IComponentsFunction<Number> principal;
    IComponentsAction delegate;
    IObligation obligation;
    ITimeDimension time;
    FixedInterestLoanAction loanAction;
    private IDimension<IObligationHistory> obs;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        obs = mock(IDimension.class);
        factory = mock(IFinanceFactory.class);
        scope = mock(ISettableComponentsScope.class);
        principal = mock(IComponentsFunction.class);
        obligation = mock(IObligation.class);
        delegate = mock(IComponentsAction.class);
        time = mock(ITimeDimension.class);
        when(factory.createFixedRepaymentLoanObligation(
                any(DateTime.class),
                anyDouble(),
                anyInt(),
                anyInt(),
                anyDouble(),
                anyDouble(),
                any(String.class),
                any(Set.class))).thenReturn(obligation);

        loanAction = new FixedInterestLoanAction(
                factory,
                time,
                obs,
                mock(IProfilingStack.class),
                principal,
                delegate,
                ConstantComponentsFunction.<Number>of(Name.of("term"), 3d),
                ConstantComponentsFunction.<Number>of(Name.of("rate"), 0.05d),
                ConstantComponentsFunction.<Number>of(Name.of("tilt"), 0d),
                "test",
                ImmutableSet.of("hello"));

        final IObligationHistory history = mock(IObligationHistory.class);
        when(history.size()).thenReturn(13);
        when(scope.get(obs)).thenReturn(history);
    }

    @Test
    public void transactionIsAddedForPaymentOfPrincipalAndObligationIsAddedForRepayments() {
        when(scope.get(time)).thenReturn(TimeUtil.mockTime(new DateTime(0)));
        when(delegate.apply(scope, ILets.EMPTY)).thenReturn(true);
        when(principal.compute(scope, ILets.EMPTY)).thenReturn(1000d);
        Assert.assertTrue("returns true for successful application", loanAction.apply(scope, ILets.EMPTY));
        final ArgumentCaptor<IPayment> paymentCaptor = ArgumentCaptor.forClass(IPayment.class);

        verify(scope).addTransaction(paymentCaptor.capture());
        verify(scope).addObligation(obligation);

        final IPayment payment = paymentCaptor.getValue();

        Assert.assertEquals("Payment should be value of principal, but negative as it's a payment to the person",
                -1000d, payment.getAmount(), 0.01);

        Assert.assertEquals("Payment should be from test", "test", payment.getPayee());

        Assert.assertEquals("Tags should be principal and hello",
                ImmutableSet.of(ILoan.Tags.Principal, "hello", ILoan.Tags.Loan,
                        ILoan.Tags.Loan.substring(1), ILoan.Tags.Principal.substring(1)
                ), payment.getTags());

        verify(factory).createFixedRepaymentLoanObligation(new DateTime(0), 1000d, 3, 13, 0.05, 0, "test",
                ImmutableSet.of(ILoan.Tags.Repayment, "hello", ILoan.Tags.Loan));

        verify(scope).get(time);

        verify(scope).get(obs);

        verifyNoMoreInteractions(scope);
    }

    @Test
    public void suitabilityIsDelegated() {
        when(delegate.isSuitable(scope, ILets.EMPTY)).thenReturn(true);
        Assert.assertTrue("Suitable if delegate is", loanAction.isSuitable(scope, ILets.EMPTY));
        when(delegate.isSuitable(scope, ILets.EMPTY)).thenReturn(false);
        Assert.assertFalse("Not suitable if delegate isn't", loanAction.isSuitable(scope, ILets.EMPTY));
    }

    @Test
    public void nothingHappensIfDelegateFailsToApply() {
        when(delegate.apply(scope, ILets.EMPTY)).thenReturn(false);
        Assert.assertFalse("returns false for failed application", loanAction.apply(scope, ILets.EMPTY));
        verifyNoMoreInteractions(scope);
        verifyNoMoreInteractions(factory);
    }
}
