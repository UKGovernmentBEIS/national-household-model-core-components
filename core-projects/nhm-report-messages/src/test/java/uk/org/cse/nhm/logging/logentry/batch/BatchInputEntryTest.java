package uk.org.cse.nhm.logging.logentry.batch;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.logging.logentry.LogEntryTestUtility;

public class BatchInputEntryTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final BatchInputEntry logEntry = new BatchInputEntry(UUID.randomUUID(), ImmutableMap.of("column", "value"));
        LogEntryTestUtility.testLogDeSerialisation(logEntry, BatchInputEntry.class);
    }
}
