package uk.org.cse.nhm.hom.emf.util;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public final class Efficiency {
	private static final double PREC = 1000;
	public final double value;
	private static final Interner<Efficiency> values = 
			Interners.newWeakInterner();
	public static final Efficiency ZERO = fromDouble(0d);
	public static final Efficiency ONE = fromDouble(1d);

	public Efficiency(double d) {
		this.value = Math.round(d * PREC) / PREC;
	}

	public static Efficiency fromDouble(final double d) {
		return values.intern(new Efficiency(d));
	}
	
	public static Efficiency fromString(final String s) {
		return fromDouble(Double.parseDouble(s));
	}
	
	public String asString() {
		return String.format("%.3f", value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Efficiency) return ((Efficiency) obj).value == this.value;
		return false;
	}
}
