package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableList;

public class TechnologyInstallationLogEntryTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final TechnologyInstallationLogEntry logEntry
                = new TechnologyInstallationLogEntry(new DateTime(),
                        ImmutableList.of(new TechnologyInstallationRecord("hello", "world", 9, "things", 12)),
                        4f,
                        9);
        LogEntryTestUtility.testLogDeSerialisation(logEntry, TechnologyInstallationLogEntry.class);
    }

}
