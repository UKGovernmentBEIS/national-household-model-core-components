package uk.org.cse.nhm.simulator.hooks;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSortedSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class DatesHook extends AbstractHook implements Initializable, IDateRunnable {

    private final ICanonicalState state;
    private final ISimulator simulator;
    private final ImmutableSortedSet<DateTime> dates;

    @AssistedInject
    public DatesHook(final ICanonicalState state,
            final ISimulator simulator,
            @Named(SimulatorConfigurationConstants.START_DATE) final DateTime startDate,
            @Named(SimulatorConfigurationConstants.END_DATE) final DateTime endDate,
            @Assisted final Collection<DateTime> dates,
            @Assisted final List<IHookRunnable> delegates) {
        super(delegates, simulator);
        this.simulator = simulator;
        this.state = state;
        this.dates = ImmutableSortedSet.copyOf(dates).subSet(startDate, true, endDate, true);
    }

    @Override
    public void initialize() throws NHMException {
        final Priority p = Priority.ofIdentifier(this.getIdentifier());
        for (final DateTime dt : dates) {
            simulator.schedule(dt, p, this);
        }
    }

    @Override
    public void run(final DateTime date) {
        super.runDelegates(state, date, Collections.<IStateChangeSource>emptySet(), Collections.<IDwelling>emptySet());
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.TRIGGER;
    }
}
