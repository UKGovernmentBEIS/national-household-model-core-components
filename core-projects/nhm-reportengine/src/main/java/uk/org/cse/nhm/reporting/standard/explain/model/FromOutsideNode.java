package uk.org.cse.nhm.reporting.standard.explain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class FromOutsideNode extends OutsideNode {
	@Override
	public double getTotalSize() {
		double accum = 0;
		for (Edge child : getChildEdges()) {
			accum += child.getValue();
		}
		return accum;
	}

	@Override
	public void addChildEdge(Edge child) {
		children.add(child);
	}

	@Override
	public void addParentEdge(Edge parent) {
		throw new UnsupportedOperationException("From outside nodes never have a parent edge.");
	}
}
