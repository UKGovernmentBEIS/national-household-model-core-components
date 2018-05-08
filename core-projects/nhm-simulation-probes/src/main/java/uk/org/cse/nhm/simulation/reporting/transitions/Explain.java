package uk.org.cse.nhm.simulation.reporting.transitions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableList;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.impl.Condition;
import uk.org.cse.nhm.simulator.groups.impl.Condition.IConditionGroup;
import uk.org.cse.nhm.simulator.groups.impl.Condition.IConditionListener;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * This class provides wiring which connects a {@link Condition} up to the
 * logger and logs out messages which can be used in the reporting system to
 * explain transitions that happen to houses.
 *
 * @author hinton
 *
 */
public class Explain extends AbstractNamed implements IConditionListener {

    private final List<String> caseNames = new ArrayList<String>();
    private final ILogEntryHandler loggingService;
    private int sequenceNumber = 0;

    @Inject
    public Explain(
            final String executionID,
            final ILogEntryHandler loggingService,
            @Assisted final Condition condition,
            @Assisted final List<String> conditionNames) {
        this.loggingService = loggingService;
        caseNames.addAll(conditionNames);
        condition.addConditionListener(this);
    }

    @Override
    public void conditionMembershipChanged(final IStateChangeNotification cause,
            final List<? extends IConditionGroup> groups, final float[] gained, final float[] lost) {

        final ImmutableList.Builder<ExplainArrow> arrows = ImmutableList.builder();
        int index = 0;
        for (final String caseName : caseNames) {
            if (gained[index] > 0) {
                final ExplainArrow arrow = new ExplainArrow(ExplainArrow.OUTSIDE, caseName, gained[index]);
                arrows.add(arrow);
            }

            if (lost[index] > 0) {
                final ExplainArrow arrow = new ExplainArrow(caseName, ExplainArrow.OUTSIDE, lost[index]);
                arrows.add(arrow);
            }

            index++;
        }

        final IStateChangeSource firstCause = cause.getRootScope().getTag();
        final ExplainLogEntry ele = new ExplainLogEntry(
                cause.getDate(),
                getIdentifier().getName(),
                firstCause.getIdentifier().getPath(),
                isStockCreator(firstCause),
                sequenceNumber++,
                arrows.build());

        log(ele);
    }

    private void log(final ExplainLogEntry ele) {
        if (ele.getArrows().isEmpty()) {
            return;
        }
        loggingService.acceptLogEntry(ele);
    }

    @Override
    public void conditionAssignmentChanged(
            final IStateChangeNotification cause,
            final List<? extends IConditionGroup> groups, final float[][] flows) {

        final ImmutableList.Builder<ExplainArrow> arrows = ImmutableList.builder();
        int fromIndex = 0;
        for (final String fromName : caseNames) {
            int toIndex = 0;
            for (final String toName : caseNames) {
                if (flows[fromIndex][toIndex] > 0) {
                    arrows.add(new ExplainArrow(fromName, toName, flows[fromIndex][toIndex]));
                }
                toIndex++;
            }
            fromIndex++;
        }

        final IStateChangeSource firstCause = cause.getRootScope().getTag();
        final ExplainLogEntry ele = new ExplainLogEntry(
                cause.getDate(),
                getIdentifier().getName(),
                firstCause.getIdentifier().getPath(),
                isStockCreator(firstCause),
                sequenceNumber++,
                arrows.build());

        log(ele);
    }

    private boolean isStockCreator(final IStateChangeSource firstCause) {
        return firstCause.getSourceType() == StateChangeSourceType.CREATION;
    }
}
