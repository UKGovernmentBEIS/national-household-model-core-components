package com.larkery.jasb.io.atom;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class DateAtomIOTest {
	@Test
	public void testWrongDate() {
		wrongDates(ImmutableSet.of(
			"2013/01/01",
			"01-01-2013",
			"01/13/2013",
			"40/12/2013"
				));
	}
	
	@Test
	public void correctDates() throws Exception {
		correctDates(ImmutableMap.of(
				"2013", new DateTime(2013, 01, 01, 0, 0, DateTimeZone.UTC),
				"01/01/2013", new DateTime(2013, 01, 01, 0, 0, DateTimeZone.UTC)
				));
	}
	
	private void wrongDates(final Set<String> dates) {
		for(final String date : dates) {
			try {
				final DateTime parsed = new DateAtomIO().read(date, DateTime.class).get();
				Assert.fail(String.format("Date %s should have failed to parse, but came out as %s.", date, parsed));
			} catch (final Exception e) {
				// NO-OP
			}
		}
	}
	
	private void correctDates(final Map<String, DateTime> dates) throws Exception {
		for(final Entry<String, DateTime> entry : dates.entrySet()) {
			Assert.assertEquals("Parsed date should match expected date.", entry.getValue(), new DateAtomIO().read(entry.getKey(), DateTime.class).get());
		}
	}
}
