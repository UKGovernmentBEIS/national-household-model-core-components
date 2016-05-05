package uk.org.cse.nhm.reporting.standard.explain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class ToOutsideNode extends OutsideNode {
	@Override
	public void addChildEdge(Edge child) {
		throw new UnsupportedOperationException("To outside nodes never have a child edge.");
	}
}
