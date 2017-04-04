package uk.org.cse.nhm.simulator.state.functions.impl.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.language.definition.function.lookup.LookupRule;
import uk.org.cse.nhm.language.definition.function.lookup.LookupRule.Type;

/**
 * A helper class which relates the number line to entries which match parts of
 * the number line
 * 
 * @author hinton
 * 
 */
class RangeToEntry {
	private final double[] numberLine;
	private final BitSet[] equal;
	private final BitSet[] unequal;

	public RangeToEntry(final List<LookupRule> queries, final List<Integer> indices) {
		final List<IMatcher> matchers = new ArrayList<>();

		for (int i = 0; i < queries.size(); i++) {
			if (queries.get(i).getType() == Type.Range) {
				matchers.add(RangeMatcher.of(queries.get(i), indices.get(i)));
			}
		}

		final SortedSet<Double> inflexions = new TreeSet<Double>();
		inflexions.add(Double.NEGATIVE_INFINITY);
		inflexions.add(Double.POSITIVE_INFINITY);
		for (final IMatcher m : matchers) {
			inflexions.addAll(m.getInflexions());
		}

		numberLine = new double[inflexions.size()];
		equal = new BitSet[inflexions.size()];
		unequal = new BitSet[inflexions.size()];
		
		int i = 0;
		for (final double d : inflexions) {
			equal[i] = new BitSet();
			unequal[i] = new BitSet();
			numberLine[i] = d;
			for (final IMatcher m : matchers) {
				if (m.contains(numberLine[i])) {
					equal[i].set(m.getEntry());
				}

				if (m.containsAbove(numberLine[i])) {
					unequal[i].set(m.getEntry());
				}
			}
			
			i++;
		}
	}

	static class RangeMatcher implements IMatcher {
		private final double lowerBound;
		private final double upperBound;
		private final boolean includesLower;
		private final boolean includesUpper;
		private final int entry;

		RangeMatcher(final double lowerBound, final double upperBound,
				final boolean openLower, final boolean openUpper,
				final int entry) {
			super();
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
			this.includesLower = openLower;
			this.includesUpper = openUpper;
			this.entry = entry;
		}

		public static IMatcher of(final LookupRule rule, final int i) {
			return new RangeMatcher(rule.getLowerBound(), rule.getUpperBound(),
					rule.isLowerBoundIncluded(), rule.isUpperBoundIncluded(), i);
		}

		@Override
		public boolean contains(final double d) {
			return (includesLower ? (d >= lowerBound) : d > lowerBound)
					&& (includesUpper ? (d <= upperBound) : d < upperBound);
		}

		@Override
		public boolean containsAbove(final double d) {
			return lowerBound <= d && d < upperBound;
		}

		@Override
		public int getEntry() {
			return entry;
		}

		@Override
		public List<Double> getInflexions() {
			return ImmutableList.of(lowerBound, upperBound);
		}
	}

	private interface IMatcher {
		public int getEntry();

		public boolean containsAbove(double d);

		public List<Double> getInflexions();

		public boolean contains(final double d);
	}

	public BitSet get(final double point) {
		final int position = Arrays.binarySearch(numberLine, point);
		if (position >= 0) {
			// point is present exactly in the array
			return equal[position];
		} else {
			// (-insert) - 1
			// insert is the position of the first element exceeding point;
			// so if insert = 0, point is off the bottom of the array
			// and so on.
			// if insert = numberline.length then we are off the top of the
			// array
			// so we want to have a bitset available which tells us
			// what entries include but do not equal insert
			// we could cap the line with minus and plus infinity?
			// position = -insert -1
			// position+1 = -insert
			// insert = -(position+1)
			final int insert = -(position + 1);
			// however we want to ask about the number below
			if (insert == 0) {
				return new BitSet();
			} else {
				return unequal[insert-1];
			}
		}
	}
}