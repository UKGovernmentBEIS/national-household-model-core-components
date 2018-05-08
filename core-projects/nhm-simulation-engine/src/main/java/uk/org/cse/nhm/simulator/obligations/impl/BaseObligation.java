package uk.org.cse.nhm.simulator.obligations.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.profile.IProfiler;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public abstract class BaseObligation extends AbstractNamed implements IObligation, IStateAction, IDateRunnable {

    private static final Logger log = LoggerFactory.getLogger(BaseObligation.class);
    final ITimeDimension time;
    final ICanonicalState state;

    @Inject
    private IProfiler profiler;

    /**
     * The set of dates we have already scheduled on the queue
     */
    final Set<DateTime> scheduledTimes = new HashSet<DateTime>();

    /**
     * The list of dwellings we are scheduled to apply to.
     */
    final LinkedHashSet<IDwelling> activeDwellings = new LinkedHashSet<>();

    private final ISimulator simulator;
    private final int index;

    protected BaseObligation(final ITimeDimension time, final ICanonicalState state, final ISimulator simulator, final int index) {
        this.time = time;
        this.state = state;
        this.simulator = simulator;
        this.index = index;
    }

    @Override
    public final StateChangeSourceType getSourceType() {
        return StateChangeSourceType.OBLIGATION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        for (final IPayment p : generatePayments(scope.get(time).get(XForesightLevel.Always), scope)) {
            scope.addTransaction(p);
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

    @Override
    public void handle(final IDwelling dwelling) {
        activeDwellings.add(dwelling);
        reschedule(state.get(time, null).get(XForesightLevel.Always));
    }

    @Override
    public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {

        if (profiler != null) {
            profiler.start("eval " + this, "OBL");
        }

        for (final IDwelling d : dwellings) {
            scope.apply(((IComponentsAction) this), Collections.singleton(d), lets);
        }

        reschedule(scope.getState().get(time, null).get(XForesightLevel.Always));
        if (profiler != null) {
            profiler.stop();
        }
        return dwellings;
    }

    @Override
    public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
        return dwellings;
    }

    private void reschedule(final DateTime now) {
        final Optional<DateTime> nextTransactionDate = getNextTransactionDate(now);
        if (nextTransactionDate.isPresent()) {
            scheduleFiring(nextTransactionDate.get());
        } else {
            activeDwellings.clear();
        }
    }

    private void scheduleFiring(final DateTime dateTime) {
        if (scheduledTimes.contains(dateTime)) {
            return;
        }
        log.trace("scheduling {} on {}", this, dateTime);
        scheduledTimes.add(dateTime);
        final Priority priority = Priority.ofObligation(index);
        simulator.schedule(dateTime, priority, this);
    }

    @Override
    public void run(final DateTime date) {
        if (activeDwellings.isEmpty()) {
            return;
        }
        state.apply(this, this, this.activeDwellings, ILets.EMPTY);
    }

    @Override
    public void forget(final IDwelling dwelling) {
        activeDwellings.remove(dwelling);
    }
}
