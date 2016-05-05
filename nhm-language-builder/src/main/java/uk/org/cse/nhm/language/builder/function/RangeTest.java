package uk.org.cse.nhm.language.builder.function;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

/**
 * 
 * @author hinton
 * 
 */
final class RangeTest implements Predicate<Integer> {
	private Optional<Integer> low;
	private Optional<Integer> exact;
	private Optional<Integer> high;

	public RangeTest(final Optional<Integer> low, final Optional<Integer> exact, final Optional<Integer> high) {
		this.low = low;
		this.exact = exact;
		this.high = high;
	}

	@Override
	public boolean apply(@Nullable final Integer integer) {
		if (integer == null)
			return false;
		if (exact.isPresent()) {
			return integer.equals(exact.get());
		}
		if (low.isPresent()) {
			if (integer <= low.get())
				return false;
		}

		if (high.isPresent()) {
			if (integer >= high.get())
				return false;
		}

		return true;
	}
	
	@Override
	public String toString() {
		if ( exact.isPresent() ) {
			return String.format("{} = %d", exact.get());
		}
		
		if (low.isPresent() || high.isPresent()) {
			return 
					((low.isPresent()) ? String.format("%d < ", low.get()) : "")
					+
					"{}"
					+
					((high.isPresent()) ? String.format(" < %d", high.get()) : "");
		}
		
		return "{} = any value";
	}
}