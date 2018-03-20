package uk.org.cse.commons.names;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

@AutoProperty
public class Path {
    private final String component;
    private final Path parent;

    private static final Interner<String> components =
        Interners.newWeakInterner();

    private static final Interner<Path> parents =
        Interners.newWeakInterner();
    
    private Path(final String component, final Path parent) {
        this.component = components.intern(component);
        this.parent = parent == null ? null : parents.intern(parent);
    }

    public static Path get(final String component, final Path parent) {
        return parents.intern(new Path(component, parent));
    }

    public static Path get(final String component) {
        return parents.intern(new Path(component, null));
    }
    
    // TODO: Memoize and ignore?
    public String toString() {
        return parent == null ? "/" + component :
            String.valueOf(parent) + "/" + component;
    }

    public String getComponent() { return component; }
    public Path getParent() { return parent; }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return Pojomatic.equals(this, other);
    }
}
