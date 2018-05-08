package uk.org.cse.nhm.logging.logentry;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

@AutoProperty
public class ProbeLogEntry extends AbstractDatedLogEntry {

    private final String name;
    private final int houseID;
    private final int sequence;
    private final ImmutableMap<String, Object> fields;
    private final float weight;

    @JsonCreator
    public ProbeLogEntry(
            @JsonProperty("name") final String name,
            @JsonProperty("weight") final float weight,
            @JsonProperty("date") final DateTime date,
            @JsonProperty("sequence") final int sequence,
            @JsonProperty("houseID") final int houseID,
            @JsonProperty("fields") final ImmutableMap<String, Object> fields) {
        super(date);
        this.name = name;
        this.weight = weight;
        this.sequence = sequence;
        this.fields = ImmutableMap.copyOf(fields);
        this.houseID = houseID;
    }

    public String getName() {
        return name;
    }

    public int getSequence() {
        return sequence;
    }

    public int getHouseID() {
        return houseID;
    }

    public float getWeight() {
        return weight;
    }

    public ImmutableMap<String, Object> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return Pojomatic.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }
}
