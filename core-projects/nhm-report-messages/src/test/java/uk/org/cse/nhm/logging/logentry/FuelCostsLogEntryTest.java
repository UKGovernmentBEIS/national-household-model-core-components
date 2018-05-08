package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class FuelCostsLogEntryTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final FuelCostsLogEntry logEntry = new FuelCostsLogEntry(Integer.MIN_VALUE, 3f, new DateTime(), new ImmutableMap.Builder<FuelType, Double>().build());
        LogEntryTestUtility.testLogDeSerialisation(logEntry, FuelCostsLogEntry.class);
    }

}
