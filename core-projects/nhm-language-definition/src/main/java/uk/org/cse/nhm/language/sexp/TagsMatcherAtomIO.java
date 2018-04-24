package uk.org.cse.nhm.language.sexp;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

import uk.org.cse.nhm.language.definition.tags.TagState;

public class TagsMatcherAtomIO implements IAtomIO {

	@Override
	public boolean canWrite(final Object object) {
		return object instanceof TagState;
	}

	@Override
	public String write(final Object object) {
		return ((TagState) object).toString();
	}

	@Override
	public boolean canReadTo(final Class<?> output) {
		return output.isAssignableFrom(TagState.class);
	}

	@Override
	public <T> Optional<T> read(final String in, final Class<T> out) {
		if (canReadTo(out)) {
			final Optional<TagState> state = TagState.of(in);
			if (state.isPresent()) {
				return Optional.of(out.cast(state.get()));
			}
		}
		return Optional.absent();
	}

	@Override
	public Set<String> getLegalValues(final Class<?> output) {
		if (output.isAssignableFrom(TagState.class)) {
			return ImmutableSet.of(
					"this-flag", 
					"!not-this-flag");
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
		return "Positive or Negative Tags";
	}
}
