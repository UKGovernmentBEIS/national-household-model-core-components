package uk.org.cse.nhm.simulation.reporting.standard;

import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.SequenceLogEntry;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;

public class SequenceLogger implements IStateListener {

    private final ILogEntryHandler loggingService;
    DateTime previous = DateTime.now();

    @Inject
    public SequenceLogger(
            final ILogEntryHandler loggingService,
            final ICanonicalState state) {

        this.loggingService = loggingService;
        state.addStateListener(this);
    }

    private static final int count(final Set<IDwelling> dwellings) {
        double acc = 0;
        for (final IDwelling d : dwellings) {
            acc += d.getWeight();
        }
        return (int) Math.round(acc);
    }

    @Override
    public void stateChanged(final ICanonicalState state,
            final IStateChangeNotification notification) {
        loggingService.acceptLogEntry(
                new SequenceLogEntry(
                        state.getRandom().getSeed(),
                        notification.getDate(),
                        new Duration(previous, DateTime.now()).getStandardSeconds(),
                        notification.getRootScope().getTag().getIdentifier().getPath(),
                        count(notification.getCreatedDwellings()),
                        count(notification.getDestroyedDwellings()),
                        count(notification.getAllChangedDwellings())));
        previous = DateTime.now();
    }

}
