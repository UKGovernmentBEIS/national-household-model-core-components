package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;

public class ConstructAndDemoLogEntryTest {

	@Test
	public void testRiceCrispies() throws JsonGenerationException, JsonMappingException, IOException {
		final ConstructAndDemoLogEntry logEntry = new ConstructAndDemoLogEntry(new DateTime(), new ImmutableMap.Builder<RegionType, Integer>().put(RegionType.London, 9).build());
		LogEntryTestUtility.testLogDeSerialisation(logEntry, ConstructAndDemoLogEntry.class);
	}
}
