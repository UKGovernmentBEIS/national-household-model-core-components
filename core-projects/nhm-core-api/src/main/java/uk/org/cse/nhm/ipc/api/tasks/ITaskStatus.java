package uk.org.cse.nhm.ipc.api.tasks;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

public interface ITaskStatus {

    public enum Phase {
        Queued,
        Running,
        Failed,
        Cancelled,
        Completed
    }

    public String getUser();

    public DateTime getSubmitted();

    public String getTaskID();

    public String getScenarioID();

    public Phase getPhase();

    public String getMessage();

    public double getProgress();

    public DateTime getUpdated();

    Optional<DateTime> getEstimate();
}
