package uk.org.cse.nhm.language.sexp;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

import uk.org.cse.commons.Glob;

public class GlobMatcherAtomIO implements IAtomIO {
	@Override
	public boolean canWrite(final Object object) {
		return object instanceof Glob;
	}

	@Override
	public String write(final Object object) {
		return ((Glob) object).toString();
	}

	@Override
	public boolean canReadTo(final Class<?> output) {
		return output.isAssignableFrom(Glob.class);
	}

	@Override
	public <T> Optional<T> read(final String in, final Class<T> out) {
		if (canReadTo(out)) {
            try {
                return Optional.of(out.cast(Glob.of(in)));
            } catch (Exception e) {}
		}
		return Optional.absent();
	}

	@Override
	public Set<String> getLegalValues(final Class<?> output) {
		if (output.isAssignableFrom(Glob.class)) {
			return ImmutableSet.of(
					"exactly-this",
					"starts-with-*",
					"*-ends-with",
					"<one,or,another>"
					);
		} else {
			return ImmutableSet.<String>of();
		}
	}
	
	@Override
	public boolean isBounded() {
		return false;
	}

	@Override
	public String getDisplayName(final Class<?> javaType) {
		return "Patterns";
	}
}
