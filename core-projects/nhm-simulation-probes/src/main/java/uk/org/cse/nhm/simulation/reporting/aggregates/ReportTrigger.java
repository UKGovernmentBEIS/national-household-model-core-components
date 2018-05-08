package uk.org.cse.nhm.simulation.reporting.aggregates;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;

public class ReportTrigger {

    @Inject
    public ReportTrigger(
            final ISimulator simulator,
            @Assisted final IReportMode mode,
            @Assisted final IGroups division
    ) {

        final IDateRunnable fire = new IDateRunnable() {

            @Override
            public void run(DateTime date) {
                division.triggerManually();
            }
        };

        for (final DateTime date : mode.extraDates()) {
            simulator.schedule(date, Priority.ofReports(), fire);
        }
    }
}
