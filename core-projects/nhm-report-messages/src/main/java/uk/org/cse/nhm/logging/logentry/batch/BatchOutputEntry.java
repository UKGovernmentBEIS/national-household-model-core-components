package uk.org.cse.nhm.logging.logentry.batch;

import java.util.UUID;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

/**
 * A log entry which contains some batch outputs
 *
 * @since 4.0.0
 */
@AutoProperty
public class BatchOutputEntry extends AbstractBatchEntry<Double> implements IKeyValueLogEntry {

    private final ImmutableMap<String, String> reducedRowKey;

    @JsonCreator
    public BatchOutputEntry(
            @JsonProperty("runID") UUID runID,
            @JsonProperty("reducedRowKey") ImmutableMap<String, String> reducedRowKey,
            @JsonProperty("columns") ImmutableMap<String, Double> columns) {
        super(runID, columns);
        this.reducedRowKey = reducedRowKey;
    }

    @Override
    public ImmutableMap<String, String> getReducedRowKey() {
        return reducedRowKey;
    }

    @Override
    @JsonIgnore
    @Property(policy = PojomaticPolicy.NONE)
    public ImmutableMap<String, String> getFullRowKey() {
        return ImmutableMap.<String, String>builder()
                .put("runID", getRunID().toString())
                .putAll(reducedRowKey)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        return Pojomatic.equals(this, obj);
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }
}
