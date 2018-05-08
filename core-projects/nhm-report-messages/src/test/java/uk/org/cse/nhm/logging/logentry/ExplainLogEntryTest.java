package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableList;

public class ExplainLogEntryTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final ExplainLogEntry logEntry = new ExplainLogEntry(new DateTime(), "name", "cause", false, 123, ImmutableList.of(
                new ExplainArrow("from", "to", 50)));
        LogEntryTestUtility.testLogDeSerialisation(logEntry, ExplainLogEntry.class);
    }
}
