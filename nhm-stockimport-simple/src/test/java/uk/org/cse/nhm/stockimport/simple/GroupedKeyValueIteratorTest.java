package uk.org.cse.nhm.stockimport.simple;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.stockimport.simple.dto.GroupedKeyValueIterator;

public class GroupedKeyValueIteratorTest {
	@Test
	public void groupedKeyValueIteratorWorks() throws Exception {
		final GroupedKeyValueIterator iterator = new GroupedKeyValueIterator(
				Paths.get("nopath"),
				new BufferedReader(new StringReader(
						"fst, aacode, snd\n" +
						"9, xyz, 12\n" +
						"1, xyz, 13\n" +
						"3, abc, 44\n" +
						"2, abc, 1\n" +
						"1, wer, 89\n" +
						"5, qwe, 33"
						)
				), "aacode");
	
		Assert.assertArrayEquals(new String[] {"fst", "aacode", "snd"}, iterator.getHeader());
		Assert.assertEquals("xyz", iterator.getKey());
		iterator.next();
		Assert.assertEquals("abc", iterator.getKey());
		iterator.next();
		Assert.assertEquals("wer", iterator.getKey());
		iterator.next();
		Assert.assertEquals("qwe", iterator.getKey());
		
	}
}
