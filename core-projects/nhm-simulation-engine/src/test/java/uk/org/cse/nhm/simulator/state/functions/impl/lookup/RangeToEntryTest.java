package uk.org.cse.nhm.simulator.state.functions.impl.lookup;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.language.definition.function.lookup.LookupRule;

public class RangeToEntryTest {
	private static RangeToEntry create(final String... defs) {
		final List<LookupRule> rules = new ArrayList<>();
		final List<Integer> ints = new ArrayList<>();
		int i = 0;
		for (final String def : defs) {
			final LookupRule of = LookupRule.of(def);
			Assert.assertEquals(LookupRule.Type.Range, of.getType());
			rules.add(of);
			ints.add(i++);
		}
		
		return new RangeToEntry(rules, ints);
	}
	
	private static void check(final RangeToEntry rte, final double point, final int... values) {
		final BitSet bs = new BitSet();
		for (final int i : values) bs.set(i);
		
		final BitSet bitSet = rte.get(point);
		
		Assert.assertEquals("At " + point + " did not expect these entries", bs, bitSet);
	}
	
	@Test
	public void emptyRangeHasNoEntries() {
		final RangeToEntry rte = create();
		check(rte, 0);
		check(rte, Double.NEGATIVE_INFINITY);
		check(rte, Double.POSITIVE_INFINITY);
		check(rte, 1);
		check(rte, -1);
	}
	
	@Test
	public void lessThanRangeWorks() {
		final RangeToEntry rte = create("<5.2");	
		check(rte, Double.NEGATIVE_INFINITY, 0);
		check(rte, 0, 0);
		check(rte, 5.1, 0);
		check(rte, 5.2);
		check(rte, 10);
	}
	
	@Test
	public void leqRangeWorks() {
		final RangeToEntry rte = create("<=5.2");
		check(rte, Double.NEGATIVE_INFINITY, 0);
		check(rte, 0, 0);
		check(rte, 5.1, 0);
		check(rte, 5.2, 0);
		check(rte, 10);
	}
	
	@Test
	public void gtRangeWorks() {
		final RangeToEntry rte = create(">5.2");
		check(rte, Double.NEGATIVE_INFINITY);
		check(rte, 0);
		check(rte, 5.1);
		check(rte, 5.2);
		check(rte, 5.21, 0);
		check(rte, 10, 0);
		check(rte, Double.POSITIVE_INFINITY, 0);
	}
	
	@Test
	public void geqRangeWorks() {
		final RangeToEntry rte = create(">=5.2");
		check(rte, Double.NEGATIVE_INFINITY);
		check(rte, 0);
		check(rte, 5.1);
		check(rte, 5.2, 0);
		check(rte, 5.21, 0);
		check(rte, 10, 0);
		check(rte, Double.POSITIVE_INFINITY, 0);
	}
	
	@Test
	public void dotsRangeWorks() {
		final RangeToEntry rte = create("-1..1");
		check(rte, Double.NEGATIVE_INFINITY);
		check(rte, -1.1);
		check(rte, -1, 0);
		check(rte, 0, 0);
		check(rte, 1, 0);
		check(rte, 1.1);
		check(rte, Double.POSITIVE_INFINITY);
	}
	
	@Test
	public void nonOverlappingRangesWork() {
		final RangeToEntry rte = create("-1..1", "10..11");
		check(rte, Double.NEGATIVE_INFINITY);
		check(rte, -1.1);
		check(rte, -1, 0);
		check(rte, 0, 0);
		check(rte, 1, 0);
		check(rte, 1.1);
		check(rte, 10, 1);
		check(rte, 11, 1);
		check(rte, 11.1);
		check(rte, Double.POSITIVE_INFINITY);
	}
	
	@Test
	public void overlappingRangesWork() {
		final RangeToEntry rte = create("-1..1", ">0");
		check(rte, Double.NEGATIVE_INFINITY);
		check(rte, -1.1);
		check(rte, -1, 0);
		check(rte, 0, 0);
		check(rte, 1, 0, 1);
		check(rte, 1.1, 1);
		check(rte, 10, 1);
		check(rte, 11, 1);
		check(rte, 11.1, 1);
		check(rte, Double.POSITIVE_INFINITY, 1);
	}
	
	@Test
	public void coincidentRangesWork() {
		final RangeToEntry rte = create("-1..1", "1..2");
		check(rte, Double.NEGATIVE_INFINITY);
		check(rte, -1.1);
		check(rte, -1, 0);
		check(rte, 0, 0);
		check(rte, 1, 0, 1);
		check(rte, 1.1, 1);
		check(rte, 10);
		check(rte, 11);
		check(rte, Double.POSITIVE_INFINITY);
	}
}
