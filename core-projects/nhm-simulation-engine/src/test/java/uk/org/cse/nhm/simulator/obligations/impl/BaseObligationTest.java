package uk.org.cse.nhm.simulator.obligations.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.Payment;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class BaseObligationTest {

    private static class TestableBaseObligation extends BaseObligation {

        public DateTime nextDate;

        public Map<IComponentsScope, Set<IPayment>> payments = new HashMap<>();

        protected TestableBaseObligation(final ITimeDimension time, final ICanonicalState state, final ISimulator simulator) {
            super(time, state, simulator, 0);

        }

        @Override
        public Optional<DateTime> getNextTransactionDate(final DateTime state) {
            return Optional.fromNullable(nextDate);
        }

        @Override
        public Collection<IPayment> generatePayments(final DateTime onDate, final IComponentsScope state) {
            return payments.get(state);
        }

    }

    private TestableBaseObligation obligation;
    private ISimulator simulator;
    private ICanonicalState state;
    private IBranch branch;
    private ITimeDimension time;

    @Before
    public void setup() {
        simulator = mock(ISimulator.class);
        state = mock(ICanonicalState.class);
        time = mock(ITimeDimension.class);
        obligation = new TestableBaseObligation(time, state, simulator);
        branch = mock(IBranch.class);
        when(state.get(time, null)).thenReturn(TimeUtil.mockTime(new DateTime(0)));
        when(branch.get(time, null)).thenReturn(TimeUtil.mockTime(new DateTime(0)));
    }

    @Test
    public void handleSchedulesEventWhenAppropriate() {
        obligation.nextDate = new DateTime();

        obligation.handle(mock(IDwelling.class));
        final ArgumentCaptor<IDateRunnable> captor = ArgumentCaptor.forClass(IDateRunnable.class);
        verify(simulator).schedule(eq(obligation.nextDate), any(Priority.class), captor.capture());

        Assert.assertEquals(1, captor.getAllValues().size());

        obligation.handle(mock(IDwelling.class));
        verifyNoMoreInteractions(simulator);
    }

    @Test
    public void handleDoesNotScheduleWhenInappropriate() {
        obligation.handle(mock(IDwelling.class));
        verifyNoMoreInteractions(simulator);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void runningScheduledEventCausesGenerationOfPayments() {
        obligation.nextDate = new DateTime();
        final Set<IDwelling> ds = ImmutableSet.of(mock(IDwelling.class), mock(IDwelling.class));
        for (final IDwelling d : ds) {
            obligation.handle(d);
        }

        final ArgumentCaptor<IDateRunnable> captor = ArgumentCaptor.forClass(IDateRunnable.class);
        verify(simulator).schedule(eq(obligation.nextDate), any(Priority.class), captor.capture());
        final IDateRunnable runnable = captor.getValue();

        runnable.run(obligation.nextDate);

        final ArgumentCaptor<IStateAction> stateActionCaptor = ArgumentCaptor.forClass(IStateAction.class);

        verify(state).apply(
                any(IStateChangeSource.class),
                stateActionCaptor.capture(),
                eq(ds), any(ILets.class));

        final IStateAction action = stateActionCaptor.getValue();

        Assert.assertSame("The obligation should run itself on its firing dates", action, obligation);

        // next run the obligation
        final IStateScope scope = mock(IStateScope.class);
        when(scope.getState()).thenReturn(branch);
        action.apply(scope, ds, ILets.EMPTY);

        final ArgumentCaptor<IComponentsAction> dwellingActionsCaptor = ArgumentCaptor.forClass(IComponentsAction.class);
        final ArgumentCaptor<Set> dwellingSetsCaptor = ArgumentCaptor.forClass(Set.class);
        verify(scope, times(2)).apply(dwellingActionsCaptor.capture(), dwellingSetsCaptor.capture(), eq(ILets.EMPTY));

        final List<IComponentsAction> allDwellingActions = dwellingActionsCaptor.getAllValues();

        // poke some fake scopes into the test fixture to use
        final ISettableComponentsScope scope1 = mock(ISettableComponentsScope.class);
        final ISettableComponentsScope scope2 = mock(ISettableComponentsScope.class);

        when(scope1.get(time)).thenReturn(TimeUtil.mockTime(new DateTime(0)));
        when(scope2.get(time)).thenReturn(TimeUtil.mockTime(new DateTime(0)));

        final IPayment p1 = Payment.of("a", 100, ImmutableSet.of("a"));
        final IPayment p2 = Payment.of("b", 200, ImmutableSet.of("b"));

        obligation.payments.put(scope1, ImmutableSet.of(p1));
        obligation.payments.put(scope2, ImmutableSet.of(p2));

        // now finally run the sub-actions which do each house
        final Set<IDwelling> done = new HashSet<IDwelling>();

        for (final Set s : dwellingSetsCaptor.getAllValues()) {
            done.addAll(s);
        }

        Assert.assertEquals("Done all the dwellings", ds, done);

        allDwellingActions.get(0).apply(scope1, ILets.EMPTY);
        allDwellingActions.get(1).apply(scope2, ILets.EMPTY);

        verify(scope1).addTransaction(p1);
        verify(scope2).addTransaction(p2);
    }
}
