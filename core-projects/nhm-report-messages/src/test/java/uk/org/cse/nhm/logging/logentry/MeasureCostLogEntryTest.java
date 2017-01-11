package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import uk.org.cse.nhm.logging.logentry.MeasureCostLogEntry.Stats;

public class MeasureCostLogEntryTest {


	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException {
		final MeasureCostLogEntry logEntry =
				new MeasureCostLogEntry("a", "b", new Stats(1, 2, 4, 5, 6), new Stats(1, 2, 4, 5, 2),
						new Stats(1, 2, 4, 5, 2),
						1, new DateTime(), "things");
		LogEntryTestUtility.testLogDeSerialisation(logEntry, MeasureCostLogEntry.class);
	}
}
