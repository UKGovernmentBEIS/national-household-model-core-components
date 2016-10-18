package uk.org.cse.nhm.simulator.main;

import java.util.Arrays;
import java.util.Comparator;

import uk.org.cse.commons.names.Name;

public class Priority {
	private final int[] values;
	final static int STOCK_PRIORITY = 0;
	final static int OBLIGATION_PRIORITY = STOCK_PRIORITY+1;
	final static int SCENARIO_PRIORITY = OBLIGATION_PRIORITY+1;
	final static int REPORT_PRIORITY = SCENARIO_PRIORITY+1;
	
	private static final Priority STOCK_CREATOR = new Priority(new int[] {STOCK_PRIORITY});
	private static final Priority REPORTS = new Priority(new int[] {REPORT_PRIORITY});
	
	private static final Comparator<Priority> COMPARATOR = new Comparator<Priority>() {
		@Override
		public int compare(final Priority fst, final Priority snd) {
			if (fst == snd) {
				return 0;
			} else if (fst == null) {
				return 1;
			} else if (snd == null) {
				return -1;
			} else {
				final int max = Math.max(fst.values.length, snd.values.length);
				for (int i = 0; i < max; i++) {
					if (fst.values[i] < snd.values[i]) return -1;
					if (fst.values[i] > snd.values[i]) return 1;
				}
				
				if (snd.values.length > fst.values.length) return -1;
				if (snd.values.length < fst.values.length) return 1;
				
				return 0;
			}
		}
	};
	
	public static Comparator<Priority> comparator() {
		return COMPARATOR;
	}
	
	Priority(int[] values) {
		this.values = values;
		for (final int value : values) {
			if (value < 0) {
				throw new IllegalArgumentException("No part of a Priority's priority order should be negative ("  +Arrays.toString(values) +")");
			}
		}
	}

	public static Priority ofStockCreator() {
		return STOCK_CREATOR;
	}
	
	public static Priority ofObligation(int index) {
		return new Priority(new int[] {OBLIGATION_PRIORITY, index});
	}
	public static Priority ofIdentifier(Name newName) {
		return new Priority(new int[] {SCENARIO_PRIORITY, newName.getPriority()});
	}
	public static Priority ofReports() {
		return REPORTS;
	}

	public static Priority ofCheckpoints() {
		return STOCK_CREATOR;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(this.values);
	}
}
