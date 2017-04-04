package com.larkery.jasb.io.atom;

import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

public class DateAtomIO implements IAtomIO {
	private final DateTimeFormatter df =  DateTimeFormat.forPattern("dd/MM/yyyy").withZoneUTC();
	
	@Override
	public boolean canWrite(final Object object) {
		return object instanceof DateTime;
	}

	@Override
	public String write(final Object object) {
		return df.print((DateTime) object);
	}

	@Override
	public boolean canReadTo(final Class<?> output) {
		return output.isAssignableFrom(DateTime.class);
	}

	public Optional<DateTime> tryReading(final String in) {
		try {
			return Optional.of(df.parseDateTime(in));
		} catch (final IllegalArgumentException iae) {
			return Optional.absent();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Optional<T> read(final String in, final Class<T> out) {
		if (in == null || in.trim().isEmpty()) return Optional.absent(); 
		final Optional<DateTime> dt = tryReading(in);
		if (dt.isPresent()) {
			return (Optional<T>) dt;
		} else {
			return (Optional<T>) tryReading("01/01/" + in);
		}
	}

	@Override
	public Set<String> getLegalValues(final Class<?> output) {
		return ImmutableSet.of("01/01/2015", "01/01/2020", "2015");
	}
	
	@Override
	public boolean isBounded() {
		return false;
	}

	@Override
	public String getDisplayName(final Class<?> javaType) {
		return "Date";
	}
}
