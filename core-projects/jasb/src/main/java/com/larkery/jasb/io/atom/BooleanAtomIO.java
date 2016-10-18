package com.larkery.jasb.io.atom;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

public class BooleanAtomIO implements IAtomIO {

	@Override
	public boolean canWrite(final Object object) {
		return object instanceof Boolean;
	}

	@Override
	public String write(final Object object) {
		return "" + object;
	}

	@Override
	public boolean canReadTo(final Class<?> output) {
		return output.isAssignableFrom(Boolean.class);
	}

	@Override
	public <T> Optional<T> read(final String in, final Class<T> out) {
		return Optional.of(out.cast(Boolean.parseBoolean(in)));
	}

	@Override
	public Set<String> getLegalValues(final Class<?> output) {
		return ImmutableSet.of("true", "false");
	}

	@Override
	public String getDisplayName(final Class<?> javaType) {
		return "Boolean";
	}
	
	@Override
	public boolean isBounded() {
		return true;
	}
}
