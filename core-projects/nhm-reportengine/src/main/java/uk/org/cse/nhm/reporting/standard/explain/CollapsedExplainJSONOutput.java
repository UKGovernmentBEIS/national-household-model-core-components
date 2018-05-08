package uk.org.cse.nhm.reporting.standard.explain;

import java.util.SortedSet;

import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;

public class CollapsedExplainJSONOutput extends ExplainJSONOutput {

    public CollapsedExplainJSONOutput(String reportName, SortedSet<ExplainLogEntry> logs) {
        super("simple-" + reportName, logs);
    }

    @Override
    protected void buildReportSteps(SortedSet<ExplainLogEntry> logs) {
        ExplainStep step = new ExplainStep(logs, originNodes, fromOutside, toOutside);
        nodes.addAll(step.getNewNodes());
        edges.addAll(step.getNewEdges());
    }
}
