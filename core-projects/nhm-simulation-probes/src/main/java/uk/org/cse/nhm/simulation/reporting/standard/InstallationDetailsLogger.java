package uk.org.cse.nhm.simulation.reporting.standard;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry.Type;
import uk.org.cse.nhm.logging.logentry.TechnologyInstallationLogEntry;
import uk.org.cse.nhm.logging.logentry.TechnologyInstallationRecord;
import uk.org.cse.nhm.simulator.measure.ITechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * A device which listens to the state, and logs the
 * {@link ITechnologyInstallationDetails} which happen when state changes have
 * happened.
 *
 * @author hinton
 *
 */
public class InstallationDetailsLogger {

    /**
     * The log we are writing to
     */
    private final ILogEntryHandler loggingService;

    @Inject
    public InstallationDetailsLogger(final ICanonicalState state, final ILogEntryHandler loggingService) {
        state.addStateListener(new IStateListener() {
            private final Set<StateChangeSourceType> irrelevantTypes = EnumSet.of(StateChangeSourceType.OBLIGATION, StateChangeSourceType.TIME);

            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                if (irrelevantTypes.contains(notification.getRootScope().getTag().getSourceType())) {
                    return;
                }
                handle(notification);
            }
        });

        this.loggingService = loggingService;

        loggingService.acceptLogEntry(new ReportHeaderLogEntry(Type.InstallationLog));
    }

    /**
     * Log the changes in the notification out into the log
     *
     * @param notification
     */
    protected void handle(final IStateChangeNotification notification) {

        for (final IDwelling created : notification.getCreatedDwellings()) {
            final Optional<IComponentsScope> componentsScope = notification.getRootScope().getComponentsScope(created);
            if (componentsScope.isPresent()) {
                logChangesForDwelling(notification.getDate(), componentsScope.get());
            }
        }
        for (final IDwelling modified : notification.getAllChangedDwellings()) {
            final Optional<IComponentsScope> componentsScope = notification.getRootScope().getComponentsScope(modified);
            if (componentsScope.isPresent()) {
                logChangesForDwelling(notification.getDate(), componentsScope.get());
            }
        }
    }

    /**
     * Make a log entry for the given dwelling
     *
     * @param dateTime
     *
     * @param dwelling
     * @param installationLog
     */
    private void logChangesForDwelling(final DateTime dateTime, final IComponentsScope scope) {
        final List<ITechnologyInstallationDetails> installationLog = scope.getAllNotes(ITechnologyInstallationDetails.class);
        if (!installationLog.isEmpty()) {
            // create DTOs for the log, and put them into the entry
            final ImmutableList.Builder<TechnologyInstallationRecord> builder = ImmutableList.builder();
            for (final ITechnologyInstallationDetails installationDetails : installationLog) {
                builder.add(new TechnologyInstallationRecord(
                        String.valueOf(installationDetails.getInstalledTechnology()),
                        "" + installationDetails.getInstallationSource(),
                        installationDetails.getInstalledQuantity(),
                        installationDetails.getUnits() + "",
                        installationDetails.getInstallationCost()));
            }

            final TechnologyInstallationLogEntry entry
                    = new TechnologyInstallationLogEntry(
                            dateTime,
                            builder.build(),
                            scope.getDwelling().getWeight(),
                            scope.getDwellingID()
                    );
            // send the entry off to the logging service to write.
            loggingService.acceptLogEntry(entry);
        }
    }
}
