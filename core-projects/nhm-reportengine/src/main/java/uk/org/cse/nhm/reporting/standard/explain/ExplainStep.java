package uk.org.cse.nhm.reporting.standard.explain;

import java.util.*;

import org.joda.time.DateTime;

import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.reporting.standard.explain.model.Edge;
import uk.org.cse.nhm.reporting.standard.explain.model.Node;

/**
 * Attempts to build a part of the report using as few steps as possible, where
 * a step will correspond to one vertical slice of the chart produced.
 */
public class ExplainStep {
	private SortedSet<ExplainLogEntry> entries;
	private Map<String, Node> originNodes;
	private List<Node> newNodes = new ArrayList<Node>();
	private List<Edge> newEdges = new ArrayList<Edge>();
	private Node fromOutside;
	private Node toOutside;

	public ExplainStep(SortedSet<ExplainLogEntry> entries, Map<String, Node> originNodes, Node fromOutside, Node toOutside) {
		this.entries = entries;
		this.fromOutside = fromOutside;
		this.toOutside = toOutside;
		this.originNodes = new HashMap<String, Node>(originNodes);
		buildInFewestSteps();
	}

	public List<Node> getNewNodes() {
		return newNodes;
	}

	public List<Edge> getNewEdges() {
		return newEdges;
	}

	public Map<String, Node> getNewOriginNodes() {
		return originNodes;
	}
	
	private void buildInFewestSteps() {
		Deque<ExplainLogEntry> entriesToAdd = new LinkedList<ExplainLogEntry>();
		entriesToAdd.addAll(entries);

		while (!entriesToAdd.isEmpty()) {
			Map<String, Node> targets = new TreeMap<String, Node>();
			Set<Node> changedSources = new HashSet<Node>();
			boolean mergeFailed = false;
			DateTime startDate = entriesToAdd.peek().getDate();
			DateTime endDate = startDate;

			while (!entriesToAdd.isEmpty() && !mergeFailed) {
				ExplainLogEntry toMerge = entriesToAdd.pop();
				if (toMerge.getDate().isAfter(endDate)) {
					endDate = toMerge.getDate();
				}

				ExplainMergeAttempt attempt = new ExplainMergeAttempt(fromOutside, toOutside, toMerge, originNodes, targets);
				if (attempt.isSuccess()) {
					changedSources.addAll(attempt.getChangedSources());
					newEdges.addAll(attempt.getNewEdges());
					targets.putAll(attempt.getNewTargets());
					for (Edge e : attempt.getNewEdges()) {
						e.connect();
					}
				} else {
					mergeFailed = true;
					entriesToAdd.push(toMerge);
				}
			}

			if (targets.isEmpty()) {
				return;
			}

			for (Node source : changedSources) {
				Node target;
				String targetName = source.getName();
				if (targets.containsKey(targetName)) {
					target = targets.get(targetName);
				} else {
					target = new Node(targetName);
					targets.put(targetName, target);
				}
				createLinkForUnchangedItems(newEdges, target, source, startDate, endDate);
			}

			for (Node target : targets.values()) {
				if (originNodes.containsKey(target.getName())) {
					Node source = originNodes.get(target.getName());
					createLinkForUnchangedItems(newEdges, target, source, startDate, endDate);
				}
			}

			updateOriginNodesToPointToTargets(originNodes, targets.values());

			newNodes.addAll(targets.values());
		}
	}

	private void createLinkForUnchangedItems(List<Edge> edges, Node target, Node source, DateTime startDate, DateTime endDate) {
		if (source.calcRemainingSizeToAllocateToChildren() > 0) {
			Edge e = new Edge(source, target, source.calcRemainingSizeToAllocateToChildren(), "unchanged", startDate, endDate);
			e.connect();
			edges.add(e);
		}
	}

	private void updateOriginNodesToPointToTargets(Map<String, Node> originNodes, Collection<Node> targetNodes) {
		for (Node target : targetNodes) {
			originNodes.put(target.getName(), target);
		}
	}
}
