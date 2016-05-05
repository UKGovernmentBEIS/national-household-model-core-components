package uk.org.cse.nhm.language.definition.tags;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

@Doc({
	"It is often useful to match a tag or flag, or to add or remove same.",
	"Positive or negative flags are either ordinary flags (see the section on these",
	"for the rules about naming flags), or they are ordinary flags prefixed with !, which we will call <emphasis>negated</emphasis> flags.",
	"In contexts where flags are being tested, a normal (non-negated) flag is <emphasis>required</emphasis> to be present,",
	"and a negated flag is <emphasis>forbidden</emphasis>.",
	"When flags are being modified, a normal flag will be <emphasis>added</emphasis>, and a negated flag",
	"<emphasis>removed</emphasis>."
	})
@SeeAlso(Tag.class)
public class TagState {
	private final Tag tag;
	private final boolean state;
	
	public TagState(final boolean state, final Tag tag) {
		this.state = state;
		this.tag = tag;
	}

	public static Optional<TagState> of(final String tag) {
		final String tidied = tag.trim().toLowerCase();
		final String tagOnly;
		boolean state;
		
		if (tidied.startsWith("!")) {
			tagOnly = tidied.substring(1);
			state = false;
		} else {
			tagOnly = tidied;
			state = true;
		}
		
		final Optional<Tag> asTag = Tag.of(tagOnly);
		if (asTag.isPresent()) {
			return Optional.of(new TagState(state, asTag.get()));
		} else {
			return Optional.absent();
		}
	}
	
	@Override
	public String toString() {
		if (state) {
			return tag.getTag();
		} else {
			return "!" + tag.getTag();
		}
	}
	
	public static Set<String> getTagsWithState(final List<TagState> tags, final boolean state) { 
		final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
		
		for (final TagState ts : tags) {
			if (ts.state == state) {
				builder.add(ts.tag.getTag());
			}
		}
		
		return builder.build();
	}
		
//	/**
//	 * Return a predicate which will match any string which is required by a requiring tag and not forbidden by any forbidding tag
//	 * @param tags
//	 * @return
//	 */
//	public static Predicate<Set<String>> conjunctionOf(final List<TagState> tags) {
//		if (tags.isEmpty()) return Predicates.alwaysTrue();
//		final Set<String> require = getTagsWithState(tags, true);
//		final Set<String> forbid = getTagsWithState(tags, false);
//		
//		return new Predicate<Set<String>>() {
//			@Override
//			public boolean apply(final Set<String> arg0) {
//				return arg0.containsAll(require) && Collections.disjoint(arg0, forbid);
//			}
//		};
//	}
//
//	public static Predicate<Set<String>> disjunctionOf(
//			final List<List<TagState>> tags) {
//		if (tags.isEmpty()) return Predicates.alwaysTrue();
//		return Predicates.or(Collections2.transform(tags,
//				new Function<List<TagState>, Predicate<Set<String>>>() {
//					@Override
//					public Predicate<Set<String>> apply(
//							final List<TagState> arg0) {
//						return conjunctionOf(arg0);
//					}
//				}));
//	}
}
