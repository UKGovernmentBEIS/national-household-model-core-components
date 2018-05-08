package uk.org.cse.nhm.simulator.state.components.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.simulator.state.components.IFlags;

@AutoProperty
public class Flags implements IFlags {

    private final Map<String, Object> map = new HashMap<String, Object>();

    public Flags(final Map<String, Object> startingFlags) {
        map.putAll(startingFlags);
    }

    public Flags() {

    }

    private boolean put(final String s, final Object o) {
        if (map.containsKey(s) && map.get(s).equals(o)) {
            return false;
        }
        map.put(s, o);
        return true;
    }

    @Override
    public Set<String> getFlags() {
        final ImmutableSet.Builder<String> b = ImmutableSet.builder();

        for (final Map.Entry<String, Object> e : map.entrySet()) {
            if (Boolean.TRUE.equals(e.getValue())) {
                b.add(e.getKey());
            }
        }

        return b.build();
    }

    @Override
    public boolean addFlag(final String s) {
        return put(s, true);
    }

    @Override
    public boolean removeFlag(final String s) {
        /* This has to be a bit silly because false == unset for our purposes. */
        if (!map.containsKey(s)) {
            return false;
        } else {
            return put(s, false);
        }
    }

    @Override
    public boolean setRegister(final String s, final double value) {
        return put(s, value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Flags other = (Flags) obj;
        if (map == null) {
            if (other.map != null) {
                return false;
            }
        } else if (!map.equals(other.map)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public IFlags copy() {
        return new Flags(map);
    }

    private <T> T readValue(final String name, final Class<T> clazz) {
        if (map.containsKey(name)) {
            final Object o = map.get(name);

            if (clazz.isInstance(o)) {
                return clazz.cast(o);
            } else {
                throw new IllegalArgumentException(
                        "Flag or register variable " + name + " has type "
                        + o.getClass().getSimpleName() + " not " + clazz.getSimpleName());
            }
        }
        return null;
    }

    @Override
    public boolean testFlag(final String flag) {
        final Boolean val = readValue(flag, Boolean.class);
        return val == null ? false : val;
    }

    @Override
    public boolean hasAllFlags(final Set<String> flags) {
        for (final String f : flags) {
            if (!testFlag(f)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasAnyFlag(final Set<String> flags) {
        for (final String f : flags) {
            if (testFlag(f)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Double> getRegister(final String s) {
        return Optional.fromNullable(readValue(s, Double.class));
    }

    @Override
    public boolean flagsMatch(final Collection<Glob> globs) {
        return Glob.requireAndForbid(globs, getFlags());
    }

    @Override
    public boolean modifyFlagsWith(final Collection<Glob> globs) {
        boolean modified = false;

        for (final Glob g : globs) {
            final Optional<String> s = g.getLiteralValue();
            if (s.isPresent()) {
                if (g.isNegated()) {
                    modified = removeFlag(s.get()) || modified;
                } else {
                    modified = addFlag(s.get()) || modified;
                }
            } else if (g.isNegated()) {
                final Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    final Map.Entry<String, Object> e = it.next();
                    if (Boolean.TRUE.equals(e.getValue())) {
                        if (g.removes(e.getKey())) {
                            it.remove();
                            modified = true;
                        }
                    }
                }
            }
        }

        return modified;
    }
}
