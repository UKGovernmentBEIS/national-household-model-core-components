package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class AggregateLogEntryTest {

    @Test
    public void emptyMapsOK() throws JsonGenerationException, JsonMappingException, IOException {
        final AggregateLogEntry logEntry = new AggregateLogEntry(
                "name",
                new ImmutableSet.Builder<String>().build(),
                ImmutableMap.of("group", "my-group"),
                new DateTime(),
                ImmutableMap.<String, Double>of());
        LogEntryTestUtility.testLogDeSerialisation(logEntry, AggregateLogEntry.class);
    }

    @Test
    public void nonEmptyMapsOK() {
        final AggregateLogEntry logEntry = new AggregateLogEntry(
                "hello",
                ImmutableSet.of("x", "y"),
                ImmutableMap.of("group", "my-group"),
                new DateTime(),
                ImmutableMap.of("a", 10d));
        LogEntryTestUtility.testLogDeSerialisation(logEntry, AggregateLogEntry.class);
    }
}
