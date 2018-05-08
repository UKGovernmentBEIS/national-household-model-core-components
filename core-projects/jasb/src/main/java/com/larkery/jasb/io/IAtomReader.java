package com.larkery.jasb.io;

import java.util.Set;

import com.google.common.base.Optional;

public interface IAtomReader {

    public boolean canReadTo(final Class<?> output);

    public <T> Optional<T> read(final String in, final Class<T> out);

    public Set<String> getLegalValues(final Class<?> output);

    public String getDisplayName(Class<?> javaType);

    public boolean isBounded();
}
