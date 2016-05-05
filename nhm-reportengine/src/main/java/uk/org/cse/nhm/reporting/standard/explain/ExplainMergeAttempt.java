package uk.org.cse.nhm.reporting.standard.explain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.reporting.standard.explain.model.Edge;
import uk.org.cse.nhm.reporting.standard.explain.model.Node;

public class ExplainMergeAttempt {
	private Node fromOutside;
	private Node toOutside;
	private ExplainLogEntry entry;
	private Map<String, Node> originNodes;
	private Map<String, Node> targets;

	private boolean success;
	private ArrayList<Edge> newEdges = new ArrayList<Edge>();
	private Map<String, Node> newTargets = new HashMap<String, Node>();
	private Set<Node> changedSources = new HashSet<Node>();

	public ExplainMergeAttempt(Node fromOutside, Node toOutside, ExplainLogEntry entry, Map<String, Node> originNodes, Map<String, Node> targets) {
		this.fromOutside = fromOutside;
		this.toOutside = toOutside;
		this.entry = entry;
		this.originNodes = originNodes;
		this.targets = targets;

		success = tryMergeEntry();
	}

	public boolean isSuccess() {
		return success;
	}

	public Map<String, Node> getNewTargets() {
		return newTargets;
	}

	public Set<Node> getChangedSources() {
		return changedSources;
	}

	public List<Edge> getNewEdges() {
		return newEdges;
	}

	private boolean tryMergeEntry() {
		for (ExplainArrow arrow : entry.getArrows()) {
			if (arrow.getCount() > 0) {
				String sourceName = arrow.getFrom();
				Node source;
				if (sourceName.equals(ExplainArrow.OUTSIDE)) {
					source = fromOutside;
				} else if (originNodes.containsKey(sourceName)) {
					source = originNodes.get(sourceName);
					changedSources.add(source);
				} else {
					/**
					 * Causality is violated because we are trying to take
					 * things from a node which doesn't exist yet, so the merge
					 * must be aborted.
					 */
					return false;
				}

				String targetName = arrow.getTo();
				Node target;
				if (targetName.equals(ExplainArrow.OUTSIDE)) {
					target = toOutside;
				} else if (targets.containsKey(targetName)) {
					target = targets.get(targetName);
				} else {
					if (newTargets.containsKey(targetName)) {
						target = newTargets.get(targetName);
					} else {
						target = new Node(targetName);
						newTargets.put(targetName, target);
					}
				}

				newEdges.add(new Edge(source, target, arrow.getCount(), entry.getCause(), entry.getDate()));
			}
		}

		if (mergeDoesNotViolateCausality(changedSources, newEdges)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean mergeDoesNotViolateCausality(Set<Node> newChangedSources, List<Edge> newEdges) {
		for (Node changedSource : newChangedSources) {
			if (changedSource.getRemainingSizeToAllocateToChildrenSpeculative(newEdges) < 0) {
				return false;
			}
		}
		return true;
	}
}
