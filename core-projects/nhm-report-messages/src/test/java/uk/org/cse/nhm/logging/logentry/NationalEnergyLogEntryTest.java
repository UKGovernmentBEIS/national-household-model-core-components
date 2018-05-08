package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class NationalEnergyLogEntryTest {

    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException {
        final NationalEnergyLogEntry logEntry = new NationalEnergyLogEntry(new DateTime(), new DateTime(), ImmutableMap.of(ServiceType.APPLIANCES, 1234d), ImmutableMap.of(FuelType.MAINS_GAS, 100d));
        LogEntryTestUtility.testLogDeSerialisation(logEntry, NationalEnergyLogEntry.class);
    }

}
