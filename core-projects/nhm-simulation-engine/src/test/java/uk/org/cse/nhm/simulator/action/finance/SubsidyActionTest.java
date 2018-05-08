package uk.org.cse.nhm.simulator.action.finance;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class SubsidyActionTest {

    private static final double NO_ERROR = 0.0;

    private SubsidyAction action;
    private String tag;
    private String counterparty;
    private IComponentsFunction<Number> subsidyFunction;
    private ILogEntryHandler loggingService;
    private ISettableComponentsScope scope;
    private ArgumentCaptor<IPayment> paymentCaptor;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        tag = "tag";
        counterparty = "counterparty";
        subsidyFunction = mock(IComponentsFunction.class);
        loggingService = mock(ILogEntryHandler.class);

        scope = mock(ISettableComponentsScope.class);
        final IComponentsAction delegate = mock(IComponentsAction.class);
        when(scope.apply(delegate, ILets.EMPTY)).thenReturn(true);

        action = new SubsidyAction(ImmutableSet.of(tag), delegate, counterparty, subsidyFunction, loggingService);

        paymentCaptor = ArgumentCaptor.forClass(IPayment.class);
    }

    @Test
    public void subsidisesHouse() {
        final double amount = 5.0;
        when(subsidyFunction.compute(scope, ILets.EMPTY)).thenReturn(amount);

        Assert.assertTrue("Subsidy should succeed.", action.apply(scope, ILets.EMPTY));

        verifyPayment(-amount);
    }

    @Test
    public void cannotHaveNegativeSubsidy() {
        final double amount = -9.0;
        when(subsidyFunction.compute(scope, ILets.EMPTY)).thenReturn(amount);

        Assert.assertTrue("Subsidy should succeed.", action.apply(scope, ILets.EMPTY));

        verifyPayment(0);

        final ArgumentCaptor<WarningLogEntry> warningCaptor = ArgumentCaptor.forClass(WarningLogEntry.class);
        verify(loggingService, times(1)).acceptLogEntry(warningCaptor.capture());
    }

    private void verifyPayment(final double amount) {
        verify(scope, times(1)).addTransaction(paymentCaptor.capture());

        final IPayment payment = paymentCaptor.getValue();

        Assert.assertEquals("Amount should be correct.", amount, payment.getAmount(), NO_ERROR);
        Assert.assertEquals("Payee should be the counterparty.", counterparty, payment.getPayee());
        Assert.assertEquals("Tags should be those passed in, plus the subsidy tag.",
                ImmutableSet.of(tag, ITransaction.Tags.SUBSIDY, ITransaction.Tags.SUBSIDY.substring(1)), payment.getTags());
    }

}
