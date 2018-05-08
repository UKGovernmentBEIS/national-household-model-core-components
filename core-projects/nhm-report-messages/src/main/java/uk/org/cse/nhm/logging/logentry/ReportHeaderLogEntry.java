package uk.org.cse.nhm.logging.logentry;

import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A log entry used to tell reporters that their simulation-side message
 * producer has been switched on
 *
 * @since 4.0.0
 *
 */
@AutoProperty
public class ReportHeaderLogEntry extends AbstractLogEntry {

    public enum Type {
        State,
        InstallationLog,
        MeasureCosts,
        TechnologyCount,
        HouseCount,
        NationalPower
    }

    private final Type type;

    @JsonCreator
    public ReportHeaderLogEntry(@JsonProperty("type") Type type) {
        super();
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
