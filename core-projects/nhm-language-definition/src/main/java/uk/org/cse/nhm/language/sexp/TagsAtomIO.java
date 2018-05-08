package uk.org.cse.nhm.language.sexp;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

import uk.org.cse.nhm.language.definition.tags.Tag;

public class TagsAtomIO implements IAtomIO {

    @Override
    public String getDisplayName(final Class<?> javaType) {
        return "Tags or Flags";
    }

    @Override
    public boolean canReadTo(final Class<?> output) {
        return output.isAssignableFrom(Tag.class);
    }

    @Override
    public <T> Optional<T> read(final String in, final Class<T> out) {
        if (canReadTo(out)) {
            final Optional<Tag> tag = Tag.of(in);
            if (tag.isPresent()) {
                return Optional.of(out.cast(tag.get()));
            }
        }

        return Optional.absent();
    }

    @Override
    public Set<String> getLegalValues(final Class<?> output) {
        return ImmutableSet.of("a-flag", "another-flag");
    }

    @Override
    public boolean isBounded() {
        return false;
    }

    @Override
    public boolean canWrite(final Object object) {
        return object instanceof Tag;
    }

    @Override
    public String write(final Object object) {
        if (canWrite(object)) {
            return ((Tag) object).getTag();
        } else {
            return "???";
        }
    }
}
