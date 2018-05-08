package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTimeZone;
import org.junit.Assert;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;

public class LogEntryTestUtility {

    /**
     * Test (de)serialising the given log entry using an object mapper.
     * <br />
     * Note the object mapper has been configured to include type information
     * (Java class name) in the JSON enabling us to deserialise concrete
     * implementations to the interface they all implement and successfully
     * compare against the original concrete instance.
     *
     * @see http://wiki.fasterxml.com/JacksonPolymorphicDeserialization
     *
     *
     * @since 3.7.0
     * @param logEntry
     * @param class1
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractLogEntry> void testLogDeSerialisation(T logEntry, Class<T> class1) {
        try {
            System.out.println("logEntry: " + logEntry);
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            mapper.registerModule(new GuavaModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.enableDefaultTyping(); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            mapper.setTimeZone(DateTimeZone.getDefault().toTimeZone());

            String json = mapper.writeValueAsString(logEntry);
            System.out.println("json: " + json);
            Assert.assertNotNull("json should not be null", json);
            final T logEntry2 = (T) mapper.readValue(json, ISimulationLogEntry.class);
            System.out.println("logEntry2: " + logEntry2);
            Assert.assertEquals("Deserialised log entry should match the original.", logEntry, logEntry2);
            final ISimulationLogEntry logEntry3 = mapper.readValue(json, ISimulationLogEntry.class);
            System.out.println("logEntry3: " + logEntry3);
            Assert.assertEquals("Deserialised log entry should match the original.", logEntry, logEntry3);
        } catch (IOException th) {
            throw new RuntimeException(th.getMessage(), th);
        }
    }
}
