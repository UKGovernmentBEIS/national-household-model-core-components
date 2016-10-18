package uk.org.cse.nhm.reporting.standard.explain.model;

import java.util.List;

import uk.org.cse.nhm.logging.logentry.ExplainArrow;

public abstract class OutsideNode extends Node {
	protected OutsideNode() {
		super(ExplainArrow.OUTSIDE);
	}

	@Override
	public double calcRemainingSizeToAllocateToChildren() {
		return 0;
	}

	@Override
	public double getRemainingSizeToAllocateToChildrenSpeculative(List<Edge> newEdges) {
		return 0;
	}
}
