package uk.org.cse.boilermatcher;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.boilermatcher.sedbuk.AbstractBoilerTable;

public class AbstractBoilerTableTest {
	@Test
	public void testInexactMatch() {
		Assert.assertTrue(AbstractBoilerTable.inexactMatch("Ti30", "Ti30-AX"));
		Assert.assertFalse(AbstractBoilerTable.inexactMatch("Ti30", "Ti-AX-30"));
	}
}
