package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

public class TechnologyDistributionLogTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final TechnologyDistributionLog logEntry = new TechnologyDistributionLog(new DateTime(), new ImmutableMap.Builder<String, Integer>().build());
        LogEntryTestUtility.testLogDeSerialisation(logEntry, TechnologyDistributionLog.class);
    }
}
