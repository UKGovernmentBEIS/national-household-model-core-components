package uk.org.cse.nhm.logging.logentry.batch;

import java.util.UUID;

import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.logging.logentry.AbstractLogEntry;

@AutoProperty
abstract class AbstractBatchEntry<T> extends AbstractLogEntry {

    private final UUID runID;
    private final ImmutableMap<String, T> columns;

    protected AbstractBatchEntry(UUID runID, ImmutableMap<String, T> columns) {
        this.runID = runID;
        this.columns = columns;

    }

    public UUID getRunID() {
        return runID;
    }

    public ImmutableMap<String, T> getColumns() {
        return columns;
    }
}
