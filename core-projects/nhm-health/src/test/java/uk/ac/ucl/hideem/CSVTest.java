package uk.ac.ucl.hideem;

import org.junit.Assert;
import org.junit.Test;

public class CSVTest {
	@Test
	public void plainRow() {
		Assert.assertArrayEquals(
				new String[] {"this", " that", " the", " other"},
				CSV.parse("this, that, the, other", false, ',')
				);
	}
	
	@Test
	public void quotedRow() {
		Assert.assertArrayEquals(
				new String[] {"this, that", " the", " other"},
				CSV.parse("\"this, that\", the, other", false, ',')
				);
		
		
		Assert.assertArrayEquals(
				new String[] {"something something something", "else"},
				CSV.parse("\"something something\" \"something\",else", false, ',')
				);
	}
	
	@Test
	public void escapedRow() {
		Assert.assertArrayEquals(
				new String[] {"this, \" that", " the", " other"},
				CSV.parse("\"this, \"\" that\", the, other", false,',')
				);
	}
	
	@Test
	public void bidirectional() {
		for (final String[] row : new String[][] {
				{""},
				{"a", "b", "c"},
				{"with a space", "and_an_underscore"},
				{"with, a comma", "and \" a quote"}
		}) {
			Assert.assertArrayEquals(row, CSV.parse(CSV.format(row), false, ','));
		}
	}
}
