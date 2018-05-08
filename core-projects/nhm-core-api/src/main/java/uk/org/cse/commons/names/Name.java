package uk.org.cse.commons.names;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.Location;

public class Name {

    private final String name;
    private Path path;
    private String pathString = null;
    private final int priority;
    private final Optional<Location> location;

    public static final Name of(final String name) {
        return new Name(name, Path.get(name), 0, Optional.<Location>absent());
    }

    public static final Name of(final String name, final Path path, final int priority, final Location location) {
        return new Name(name, path, priority, Optional.fromNullable(location));
    }

    private Name(String name, Path path, final int priority, Optional<Location> location) {
        super();
        this.name = name;
        this.path = path;
        this.priority = priority;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        if (pathString == null) {
            pathString = String.valueOf(path);
            path = null;
        }
        return pathString;
    }

    public String toString() {
        return getName();
    }

    public int getPriority() {
        return priority;
    }

    public Optional<Location> getLocation() {
        return location;
    }
}
