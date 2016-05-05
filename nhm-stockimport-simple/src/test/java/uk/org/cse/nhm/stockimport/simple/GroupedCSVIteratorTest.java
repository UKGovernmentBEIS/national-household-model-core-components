package uk.org.cse.nhm.stockimport.simple;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.stockimport.simple.dto.GroupedCSVIterator;

public class GroupedCSVIteratorTest {
	@Test
	public void groupedCsvIteratorGroupsByKey() throws Exception {
		final GroupedCSVIterator iterator = new GroupedCSVIterator(
				CSV.trimmedReader(new BufferedReader(new StringReader(
						"9, xyz, 12\n" +
						"1, xyz, 13\n" +
						"3, abc, 44\n" +
						"2, abc, 1\n" +
						"1, wer, 89\n" +
						"5, qwe, 33"
						)))
				, 1);
		
		final ImmutableList<List<String[]>> copyOf = ImmutableList.copyOf(iterator);
		
		Assert.assertEquals("There are four groups", 4, copyOf.size());
		
		Assert.assertArrayEquals(new String[] {"9", "xyz", "12"}, copyOf.get(0).get(0));
		Assert.assertArrayEquals(new String[] {"1", "xyz", "13"}, copyOf.get(0).get(1));
		Assert.assertArrayEquals(new String[] {"3", "abc", "44"}, copyOf.get(1).get(0));
		Assert.assertArrayEquals(new String[] {"2", "abc", "1"}, copyOf.get(1).get(1));
		Assert.assertArrayEquals(new String[] {"1", "wer", "89"}, copyOf.get(2).get(0));
		Assert.assertArrayEquals(new String[] {"5", "qwe", "33"}, copyOf.get(3).get(0));
	}
}
