package uk.org.cse.nhm.logging.logentry.batch;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.logging.logentry.LogEntryTestUtility;

public class BatchOutputEntryTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final BatchOutputEntry logEntry = new BatchOutputEntry(UUID.randomUUID(), ImmutableMap.of("category", "value from category"), ImmutableMap.of("function result", 5d));
        LogEntryTestUtility.testLogDeSerialisation(logEntry, BatchOutputEntry.class);
    }
}
