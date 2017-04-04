package uk.org.cse.nhm.logging.logentry;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

/**
 * Provides information from the profiler, if it is switched on.
 */
public class ProfilerLogEntry extends AbstractLogEntry {
    @JsonCreator
    public ProfilerLogEntry(@JsonProperty("stack") final List<Integer> stack,
                            @JsonProperty("time") final double time,
                            @JsonProperty("count") final int count) {
        super();
        this.stack = ImmutableList.copyOf(stack);
        this.time = time;
        this.count = count;
    }

    private final List<Integer> stack;

    @JsonProperty
    public List<Integer> getStack() {
        return stack;
    }
    
    private final double time;

    @JsonProperty
    public double getTime() {
        return time;
    }
    
    private final int count;

    @JsonProperty
    public int getCount() {
        return count;
    }
    
}
