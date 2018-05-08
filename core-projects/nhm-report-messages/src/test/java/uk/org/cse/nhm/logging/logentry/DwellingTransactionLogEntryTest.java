package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DwellingTransactionLogEntryTest {

    @Test
    public void testRiceCrispies() throws JsonGenerationException, JsonMappingException, IOException {
        final TransactionLogEntry logEntry = new TransactionLogEntry(new DateTime(), "Dwelling 39764", 33f, Double.MIN_VALUE, "testPath", "testPayee", "reportName", "tags...tags...tags...");
        LogEntryTestUtility.testLogDeSerialisation(logEntry, TransactionLogEntry.class);
    }
}
