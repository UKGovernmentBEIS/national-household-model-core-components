package uk.org.cse.nhm.logging.logentry.errors;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;

public class SystemErrorLogEntry implements ISimulationLogEntry {

    private final String message;
    private final String stackTrace;
    private final UUID uuid;

    public SystemErrorLogEntry(Throwable t, UUID uuid) {
        this.uuid = uuid;
        message = t.getMessage();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        stackTrace = stringWriter.toString();
    }

    @JsonCreator
    public SystemErrorLogEntry(
            @JsonProperty("message") String message,
            @JsonProperty("stackTrace") String stackTrace,
            @JsonProperty("uuid") UUID uuid) {
        this.message = message;
        this.stackTrace = stackTrace;
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public UUID getUuid() {
        return uuid;
    }
}
