package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

public class GlobalAccountsLogEntryTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final GlobalAccountsLogEntry logEntry = new GlobalAccountsLogEntry(new DateTime(), new ImmutableMap.Builder<String, Double>().put("thing", 9d).build());
        LogEntryTestUtility.testLogDeSerialisation(logEntry, GlobalAccountsLogEntry.class);
    }

}
