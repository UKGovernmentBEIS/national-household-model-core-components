package uk.org.cse.nhm.reporting.standard.explain.model;

public class OriginNode extends Node {
	private double size;

	public OriginNode(String name, int step, double size) {
		super(name);
		this.size = size;
	}

	@Override
	public double getTotalSize() {
		return size;
	}
}
