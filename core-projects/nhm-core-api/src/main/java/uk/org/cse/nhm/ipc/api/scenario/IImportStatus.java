package uk.org.cse.nhm.ipc.api.scenario;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simple object for talking about import status
 *
 * @author hinton
 *
 */
public interface IImportStatus {

    public enum State {
        RUNNING,
        COMPLETE,
        FAILED
    }

    static class Event {

        @JsonProperty
        public final DateTime date;
        @JsonProperty
        public final String message;

        public Event(final Date date, final String message) {
            this(new DateTime(date), message);
        }

        @JsonCreator
        public Event(@JsonProperty("date") final DateTime date, @JsonProperty("message") final String message) {
            super();
            this.date = date;
            this.message = message;
        }
    }

    public String getName();

    public State getState();

    public String getUser();

    public List<Event> getEvents();
}
