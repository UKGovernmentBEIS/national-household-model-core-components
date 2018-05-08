package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import uk.org.cse.nhm.logging.logentry.BasicAttributesLogEntry.Details;

public class BasicAttributesLogEntryTest {

    @Test
    public void testSerialisations() throws JsonGenerationException, JsonMappingException, IOException {
        Details details = new Details();

        details.setAacode("hello");
        final BasicAttributesLogEntry logEntry = new BasicAttributesLogEntry(1, details);
        LogEntryTestUtility.testLogDeSerialisation(logEntry, BasicAttributesLogEntry.class);
    }

}
