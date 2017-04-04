package uk.org.cse.nhm.simulator.measure.impl;

import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.Units;

public class SizingResult implements ISizingResult {
	private double size;
	private boolean suitable;
	private Units units;

	public static final ISizingResult suitable(final double size, final Units units) {
		return new SizingResult(size, units, true);
	}

	public static final ISizingResult unsuitable(final double size, final Units units) {
		return new SizingResult(size, units, false);
	}
	
	private SizingResult(final double size, final Units units, final boolean suitable) {
		this.size = size;
		this.units = units;
		this.suitable = suitable;
	}
	@Override
	public double getSize() {
		return size;
	}

	@Override
	public Units getUnits() {
		return units;
	}

	@Override
	public boolean isSuitable() {
		return suitable;
	}
}
