package uk.org.cse.nhm.ipc.api.tasks;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

public interface ITaskSession {

    public boolean isCancelled();

    public void progress(final String message, final double proportion, Optional<DateTime> estimate);
}
