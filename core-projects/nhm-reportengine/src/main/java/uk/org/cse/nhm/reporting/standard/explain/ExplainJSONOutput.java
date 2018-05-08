package uk.org.cse.nhm.reporting.standard.explain;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableSortedSet.Builder;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.report.SizeRecordingReportOutput;
import uk.org.cse.nhm.reporting.standard.explain.model.Edge;
import uk.org.cse.nhm.reporting.standard.explain.model.FromOutsideNode;
import uk.org.cse.nhm.reporting.standard.explain.model.Node;
import uk.org.cse.nhm.reporting.standard.explain.model.NodesAndLinks;
import uk.org.cse.nhm.reporting.standard.explain.model.OriginNode;
import uk.org.cse.nhm.reporting.standard.explain.model.ToOutsideNode;
import uk.org.cse.nhm.reporting.standard.jsonp.JSONPOutputUtility;

/**
 * @since 3.1.1
 * @author glenns
 */
public class ExplainJSONOutput extends SizeRecordingReportOutput {

    protected final Node fromOutside = new FromOutsideNode();
    protected final Node toOutside = new ToOutsideNode();
    protected final List<Node> nodes = new ArrayList<Node>();
    protected final List<Edge> edges = new ArrayList<Edge>();
    protected final SortedSet<ExplainLogEntry> logs;
    protected final String path;

    protected Map<String, Node> originNodes = new HashMap<String, Node>();
    protected int step = 0;

    public ExplainJSONOutput(String reportName, SortedSet<ExplainLogEntry> logs) {
        this.path = String.format("explain-%s.jsonp", reportName);

        AfterElimination afterElimination = tryEliminateStockCreator(logs);

        nodes.addAll(afterElimination.originNodes);
        for (Node origin : afterElimination.originNodes) {
            originNodes.put(origin.getName(), origin);
        }

        this.logs = afterElimination.remainingLogs;
    }

    @Override
    public String getPath() {
        return IReportOutput.DATA + path;
    }

    @Override
    public void doWriteContent(OutputStream outputStream) throws IOException {
        JSONPOutputUtility.writeAsJSONP(this, buildReport(), outputStream);
    }

    @Override
    public String getTemplate() {
        return IReportOutput.GENERIC_TEMPLATE;
    }

    @Override
    public Type getType() {
        return Type.Data;
    }

    public NodesAndLinks buildReport() {
        buildReportSteps(logs);

        connectUnconnectedNodes();

        for (Edge edge : edges) {
            edge.setNodeSearchSource(nodes);
        }

        if (fromOutside.getTotalSize() > 0) {
            nodes.add(fromOutside);
        }
        if (toOutside.getTotalSize() > 0) {
            nodes.add(toOutside);
        }

        return new NodesAndLinks(nodes, edges);
    }

    /**
     * Because we remove the stock creation step, nodes may be left unconnected
     * if there are no subsequent changes made to them. This method adds an edge
     * to indicate that this group was unchanged during the scenario.
     */
    private void connectUnconnectedNodes() {
        List<Node> generatedEndpoints = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isUnconnected()) {
                Node endPoint = new Node(node.getName());
                generatedEndpoints.add(endPoint);
                Edge unchanged = new Edge(node, endPoint, node.calcRemainingSizeToAllocateToChildren(), "unchanged");
                edges.add(unchanged);
            }
        }
        nodes.addAll(generatedEndpoints);
    }

    protected void buildReportSteps(SortedSet<ExplainLogEntry> logs) {
        SortedSetMultimap<DateTime, ExplainLogEntry> logsByDate = getEntriesGroupsByDateAndSortedBySequenceNumber(logs);
        for (DateTime date : logsByDate.keySet()) {
            ExplainStep step = new ExplainStep(logsByDate.get(date), originNodes, fromOutside, toOutside);
            originNodes = step.getNewOriginNodes();
            nodes.addAll(step.getNewNodes());
            edges.addAll(step.getNewEdges());
        }
    }

    private AfterElimination tryEliminateStockCreator(Collection<ExplainLogEntry> entriesToAdd) {
        ImmutableList.Builder<Node> createdOriginNodes = ImmutableList.builder();
        Builder<ExplainLogEntry> filteredLogs = ImmutableSortedSet.naturalOrder();

        for (ExplainLogEntry entry : entriesToAdd) {
            if (entry.isFromStockCreator()) {
                for (ExplainArrow arrow : entry.getArrows()) {
                    if (arrow.getCount() > 0) {
                        createdOriginNodes.add(new OriginNode(arrow.getTo(), 0, arrow.getCount()));
                    }
                }
            } else {
                filteredLogs.add(entry);
            }
        }
        return new AfterElimination(filteredLogs.build(), createdOriginNodes.build());
    }

    static class AfterElimination {

        SortedSet<ExplainLogEntry> remainingLogs;
        List<Node> originNodes;

        AfterElimination(SortedSet<ExplainLogEntry> remainingLogs, List<Node> originNodes) {
            this.remainingLogs = remainingLogs;
            this.originNodes = originNodes;
        }
    }

    private SortedSetMultimap<DateTime, ExplainLogEntry> getEntriesGroupsByDateAndSortedBySequenceNumber(Collection<ExplainLogEntry> logs) {
        SortedSetMultimap<DateTime, ExplainLogEntry> logsByDate = TreeMultimap.<DateTime, ExplainLogEntry>create(new Comparator<DateTime>() {
            @Override
            public int compare(DateTime o1, DateTime o2) {
                return o1.compareTo(o2);
            }
        }, new Comparator<ExplainLogEntry>() {

            @Override
            public int compare(ExplainLogEntry o1, ExplainLogEntry o2) {
                return o1.getSequenceNumber().compareTo(o2.getSequenceNumber());
            }
        });
        for (ExplainLogEntry log : logs) {
            logsByDate.put(log.getDate(), log);
        }
        return logsByDate;
    }
}
