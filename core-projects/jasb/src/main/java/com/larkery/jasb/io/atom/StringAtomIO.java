package com.larkery.jasb.io.atom;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

public class StringAtomIO implements IAtomIO {

    @Override
    public boolean canReadTo(final Class<?> output) {
        return output.isAssignableFrom(String.class);
    }

    @Override
    public <T> Optional<T> read(final String in, final Class<T> out) {
        return Optional.of(out.cast(in));
    }

    @Override
    public boolean canWrite(final Object object) {
        return object instanceof String;
    }

    @Override
    public String write(final Object object) {
        return (String) object;
    }

    @Override
    public Set<String> getLegalValues(final Class<?> output) {
        return ImmutableSet.of("some-text", "\"quoted text with spaces\"");
    }

    @Override
    public boolean isBounded() {
        return false;
    }

    @Override
    public String getDisplayName(final Class<?> javaType) {
        return "Text";
    }
}
