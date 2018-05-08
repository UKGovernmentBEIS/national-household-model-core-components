package uk.org.cse.nhm.simulation.reporting.fuel;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.FuelCostsLogEntry;
import uk.org.cse.nhm.simulator.action.ConstructHousesAction;
import uk.org.cse.nhm.simulator.action.DemolishHousesAction;
import uk.org.cse.nhm.simulator.obligations.impl.TariffFuelAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.energy.AnnualFuelObligation;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class FuelCostsReportTest {

    private IDimension<DwellingTransactionHistory> transactionDimension;
    private ILogEntryHandler loggingService;
    private ICanonicalState state;
    private FuelCostsReport logger;
    private IDwelling dwelling;
    private Set<IDwelling> dwellingSet;
    private IStateChangeNotification changeNotification;

    // Uses 4-year intervals for convenience because the leap year is amortized out in the Julian standard year.
    private static final DateTime START = new DateTime(0);
    private static final DateTime PAST = START.minusYears(4);
    private static final DateTime FUTURE = START.plusYears(4);
    private static final DateTime FAR_FUTURE = START.plusYears(8);
    private IStateScope scopeWithFuelCosts;
    private IStateScope scopeWithoutFuelCosts;
    private int dwellingId;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        transactionDimension = mock(IDimension.class);
        loggingService = mock(ILogEntryHandler.class);

        dwellingId = 1;
        dwelling = mock(IDwelling.class);
        when(dwelling.getWeight()).thenReturn(1f);
        when(dwelling.getID()).thenReturn(dwellingId);
        dwellingSet = Collections.singleton(dwelling);

        state = mock(ICanonicalState.class);

        changeNotification = mock(IStateChangeNotification.class);
        when(changeNotification.getChangedDwellings(transactionDimension)).thenReturn(dwellingSet);

        logger = new FuelCostsReport(transactionDimension, loggingService, state);

        scopeWithFuelCosts = mockStateScope(ImmutableMultimap.<FuelType, Double>of(FuelType.MAINS_GAS, 1.0));
        scopeWithoutFuelCosts = mockStateScope(ImmutableMultimap.<FuelType, Double>of());
    }

    @Test
    public void shouldRegisterAsStateListener() {
        verify(state, times(1)).addStateListener(logger);
    }

    @Test
    public void ignoresOtherKindsOfStateChangeSource() {
        final IStateScope scope = mock(IStateScope.class);
        when(scope.getTag()).thenReturn(mock(IStateChangeSource.class));

        final Optional<IComponentsScope> componentsScope = mockComponentsScope(ImmutableMultimap.<FuelType, Double>of(FuelType.MAINS_GAS, 1.0));
        when(scope.getComponentsScope(dwelling)).thenReturn(componentsScope);

        go(scope, START);

        verifyNeverLogged();
    }

    @Test(expected = FuelCostsReport.DwellingNotKnownException.class)
    public void cannotWorkWithoutStockCreationOrConstructionAction() {
        go(scopeWithFuelCosts, START);
    }

    @Test
    public void constructedHouseMayBeLoggedAgainst() {
        final IStateChangeNotification constructHouses = mockNotification(mock(ConstructHousesAction.class), START);
        when(constructHouses.getCreatedDwellings()).thenReturn(dwellingSet);
        logger.stateChanged(state, constructHouses);

        go(scopeWithFuelCosts, FUTURE);

        verifyAndGetLogs(1);
    }

    @Test
    public void cannotLogAgainstDestroyedDwelling() {
        createStock(START);
        final IStateChangeNotification constructHouses = mockNotification(mock(DemolishHousesAction.class), START);
        when(constructHouses.getDestroyedDwellings()).thenReturn(dwellingSet);

        go(scopeWithFuelCosts, FUTURE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotLogCostsOnSameDateTwice() {
        createStock(START);
        go(scopeWithFuelCosts, START);
    }

    @Test
    public void loggingSameDateTwiceWithNoCostsProducesNoLog() {
        createStock(START);
        go(scopeWithoutFuelCosts, START);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotGoBackInTime() {
        createStock(START);
        go(scopeWithFuelCosts, PAST);
    }

    @Test
    public void calculatesRateFromTransactions() {
        createStock(START);

        final IStateScope scope = mockStateScope(ImmutableMultimap.<FuelType, Double>of(
                FuelType.MAINS_GAS, 1.0,
                FuelType.MAINS_GAS, 3.0));
        go(scope, FUTURE);

        final List<FuelCostsLogEntry> logs = verifyAndGetLogs(1);
        Assert.assertEquals("Should calculate the correct rate using the transactions and elapsed time.", 1.0, logs.get(0).getFuelCosts().get(FuelType.MAINS_GAS), 0.001);
    }

    @Test
    public void shouldNotLogHousesWhichArentInNotification() {
        createStock(START);

        when(changeNotification.getChangedDwellings(transactionDimension)).thenReturn(Collections.<IDwelling>emptySet());

        go(scopeWithFuelCosts, FUTURE);

        verifyNeverLogged();
    }

    @Test
    public void shouldNotLogHousesWhichDidNotChange() {
        createStock(START);

        go(scopeWithFuelCosts, FUTURE);

        // should have been called once
        verifyAndGetLogs(1);

        go(scopeWithFuelCosts, FAR_FUTURE);

        // should not have been called a second time
        verifyAndGetLogs(1);
    }

    @Test
    public void missingFuelsAssumedZero() {
        createStock(START);

        go(scopeWithFuelCosts, FUTURE);

        final List<FuelCostsLogEntry> logs = verifyAndGetLogs(1);
        Assert.assertEquals("Should use zero for missing fuels.", 0.0, logs.get(0).getFuelCosts().get(FuelType.OIL), 0);
    }

    @Test
    public void shouldLogDateAndDwellingAndExecutionId() {
        createStock(START);

        go(scopeWithFuelCosts, FUTURE);

        final List<FuelCostsLogEntry> logs = verifyAndGetLogs(1);
        Assert.assertEquals("Log should include the dwelling id.", dwelling.getID(), logs.get(0).getDwellingId());
        Assert.assertEquals("Log should include the date.", FUTURE, logs.get(0).getDate());
    }

    private void verifyNeverLogged() {
        verify(loggingService, never()).acceptLogEntry(any(ISimulationLogEntry.class));
    }

    private List<FuelCostsLogEntry> verifyAndGetLogs(final int times) {
        final ArgumentCaptor<ISimulationLogEntry> requestArg = ArgumentCaptor.forClass(ISimulationLogEntry.class);
        verify(loggingService, times(times)).acceptLogEntry(requestArg.capture());

        final List<FuelCostsLogEntry> entries = new ArrayList<>();
        for (final ISimulationLogEntry logEntry : requestArg.getAllValues()) {
            Assert.assertTrue("Should only log fuel costs entries.", logEntry instanceof FuelCostsLogEntry);
            entries.add((FuelCostsLogEntry) logEntry);
        }
        return entries;
    }

    private void createStock(final DateTime when) {
        final IStateChangeNotification notification = mockNotification(mock(IStateAction.class), when);
        when(notification.getCreatedDwellings()).thenReturn(dwellingSet);

        logger.stateChanged(state, notification);
    }

    private IStateChangeNotification mockNotification(final IStateAction tag, final DateTime when) {
        final IStateScope scope = mock(IStateScope.class);
        when(scope.getTag()).thenReturn(tag);

        final IStateChangeNotification notification = mock(IStateChangeNotification.class);
        when(notification.getDate()).thenReturn(when);
        when(notification.getRootScope()).thenReturn(scope);

        return notification;
    }

    private void go(final IStateScope scope, final DateTime date) {
        when(changeNotification.getRootScope()).thenReturn(scope);
        when(changeNotification.getDate()).thenReturn(date);
        logger.stateChanged(state, changeNotification);
    }

    private IStateScope mockStateScope(final Multimap<FuelType, Double> billsByFuel) {
        final Optional<IComponentsScope> componentsScope = mockComponentsScope(billsByFuel);

        final IStateScope scope = mock(IStateScope.class);
        when(scope.getTag()).thenReturn(mock(AnnualFuelObligation.class));
        when(scope.getComponentsScope(dwelling)).thenReturn(componentsScope);

        return scope;
    }

    @SuppressWarnings("unchecked")
    private Optional<IComponentsScope> mockComponentsScope(final Multimap<FuelType, Double> billsByFuel) {
        @SuppressWarnings("rawtypes")
        final List subScopes = mockSubScopes(billsByFuel);

        final IComponentsScope componentsScope = mock(IComponentsScope.class);
        when(componentsScope.getSubScopes()).thenReturn(subScopes);

        when(componentsScope.getDwellingID()).thenReturn(dwellingId);

        when(componentsScope.getDwelling()).thenReturn(dwelling);

        return Optional.of(componentsScope);
    }

    private List<IScope<? extends IStateChangeSource>> mockSubScopes(final Multimap<FuelType, Double> billsByFuel) {
        final List<IScope<? extends IStateChangeSource>> subScopes = new ArrayList<>();
        for (final FuelType fuel : billsByFuel.keySet()) {
            final List<ITransaction> mockTransactions = mockTransactions(billsByFuel, fuel);

            final TariffFuelAction tariffAction = mock(TariffFuelAction.class);
            when(tariffAction.getFuelType()).thenReturn(fuel);

            final IComponentsScope subScope = mock(IComponentsScope.class);
            when(subScope.getTag()).thenReturn(tariffAction);
            when(subScope.getAllNotes(ITransaction.class)).thenReturn(mockTransactions);

            subScopes.add(subScope);
        }
        return subScopes;
    }

    private List<ITransaction> mockTransactions(final Multimap<FuelType, Double> billsByFuel, final FuelType fuel) {
        final List<ITransaction> transactions = new ArrayList<>();
        for (final Double cost : billsByFuel.get(fuel)) {
            final ITransaction t = mock(ITransaction.class);
            when(t.getTags()).thenReturn(Collections.singleton(ITransaction.Tags.FUEL));
            when(t.getAmount()).thenReturn(cost);
            transactions.add(t);
        }
        return transactions;
    }
}
