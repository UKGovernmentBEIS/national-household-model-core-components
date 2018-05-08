package uk.org.cse.nhm.logging.logentry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.larkery.jasb.sexp.Location;

import uk.org.cse.commons.names.Name;

public class ProfiledItemLogEntry extends AbstractLogEntry {

    private int key;
    private String name;
    private Optional<Location> location;

    public ProfiledItemLogEntry(final int key, final Name id) {
        this(key,
                id.getName(),
                id.getLocation());
    }

    @JsonCreator
    public ProfiledItemLogEntry(@JsonProperty("key") final int key,
            @JsonProperty("name") final String name,
            @JsonProperty("location") final Optional<Location> location) {
        this.key = key;
        this.name = name;
        this.location = location;
    }

    @JsonProperty
    public int getKey() {
        return key;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Optional<Location> getLocation() {
        return location;
    }
}
