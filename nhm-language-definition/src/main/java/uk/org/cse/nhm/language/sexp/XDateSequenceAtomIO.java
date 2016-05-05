package uk.org.cse.nhm.language.sexp;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.definition.two.dates.XIntervalSequence;
import uk.org.cse.nhm.language.definition.two.dates.XSingleDate;

import com.google.common.base.Optional;
import com.larkery.jasb.io.atom.DateAtomIO;

public class XDateSequenceAtomIO extends DateAtomIO {
	@Override
	public boolean canReadTo(final Class<?> output) {
		return super.canReadTo(output) || 
				output.isAssignableFrom(XSingleDate.class) ||
				output.isAssignableFrom(XIntervalSequence.class);
	}
	
	@Override
	public boolean canWrite(final Object object) {
		return super.canWrite(object) || 
				object instanceof XSingleDate;
	}
	
	private static XSingleDate d(final DateTime dt) {
		final XSingleDate value = new XSingleDate();
		value.setDate(dt);
		return value;
	}
	
	@Override
	public <T> Optional<T> read(final String in, final Class<T> out) {
		if (out.isAssignableFrom(XIntervalSequence.class)) {
			final String[] parts = in.split("\\.\\.");
			if (parts.length == 2) {
				final Optional<DateTime> from = super.read(parts[0], DateTime.class);
				final Optional<DateTime> to = super.read(parts[1], DateTime.class);
				if (from.isPresent() && to.isPresent()) {
					final XIntervalSequence xis = new XIntervalSequence();
					xis.setFrom(d(from.get()));
					xis.setUntil(d(to.get()));
					return Optional.of(out.cast(xis));
				}
			} else if (in.startsWith("..")) {
				final Optional<DateTime> from = 
						super.read(in.substring(2), DateTime.class);
				if (from.isPresent()) {
					final XIntervalSequence xis = new XIntervalSequence();
					xis.setFrom(d(from.get()));
					return Optional.of(out.cast(xis));
				}
			} else if (in.endsWith("..")) {
				final Optional<DateTime> from = 
						super.read(in.substring(0, in.length() - 2), DateTime.class);
				if (from.isPresent()) {
					final XIntervalSequence xis = new XIntervalSequence();
					xis.setUntil(d(from.get()));
					return Optional.of(out.cast(xis));
				}
			}
		}
		
		if (out.isAssignableFrom(XSingleDate.class)) {
			final Optional<DateTime> dt = super.read(in, DateTime.class);
			if (dt.isPresent()) {
				final XSingleDate value = new XSingleDate();
				value.setDate(dt.get());
				return Optional.of(out.cast(value));
			} else {
				return Optional.absent();
			}
		} else {
			return super.read(in, out);
		}
	}
	
	@Override
	public String write(final Object object) {
		if (object instanceof XSingleDate) {
			return super.write(((XSingleDate) object).getDate());
		} else {
			return super.write(object);
		}
	}
}
