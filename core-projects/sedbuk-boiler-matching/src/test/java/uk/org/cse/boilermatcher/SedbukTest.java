package uk.org.cse.boilermatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.boilermatcher.sedbuk.IBoilerTable;
import uk.org.cse.boilermatcher.sedbuk.Sedbuk;
import uk.org.cse.boilermatcher.types.FlueType;

public class SedbukTest {
	@Test
	public void testLoadSedbuk() throws FileNotFoundException, IOException {
		final Sedbuk s = new Sedbuk(new InputStreamReader(getClass().getResourceAsStream("/sedbuk/pcdf2009.dat")));
		
		Assert.assertEquals(333, s.getRevision());
		Assert.assertTrue(s.hasTable(104));
		Assert.assertEquals("010641", s.getTable(104).getString(0, 0));
	
		final IBoilerTable boilers = s.getTable(IBoilerTable.class, IBoilerTable.ID);
		
		Assert.assertEquals((Integer) 672, boilers.matchByBrandAndModel("", "Ti30"));
		
		List<Integer> findByModelName = boilers.findByModelName("FRS 52");
		Assert.assertEquals(Arrays.asList(new Integer[] {2, 10}), findByModelName);
//		2011/Sep/06 13:11
		Assert.assertEquals(new DateTime(2011, 9, 6, 13, 11), boilers.getEntryUpdatedDate(0));
		Assert.assertEquals(FlueType.FAN_ASSISTED_BALANCED_FLUE, boilers.getFlueType(0));
	}
}
