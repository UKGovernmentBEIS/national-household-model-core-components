package uk.org.cse.nhm.language.sexp;

import uk.org.cse.nhm.language.definition.function.lookup.LookupRuleAtomIO;
import uk.org.cse.nhm.language.parse.LanguageElements;

import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;
import com.larkery.jasb.io.atom.BooleanAtomIO;
import com.larkery.jasb.io.atom.EnumAtomIO;
import com.larkery.jasb.io.atom.PeriodAtomIO;
import com.larkery.jasb.io.atom.StringAtomIO;
import com.larkery.jasb.io.impl.JASB;

public class Defaults {
	public static final ImmutableSet<IAtomIO> DEFAULT_ATOM_IO = ImmutableSet.<IAtomIO>of(
			new PeriodAtomIO(),
			new BooleanAtomIO(),
			new TagsAtomIO(),
			new TagsMatcherAtomIO(),
            new GlobMatcherAtomIO(),
			new EnumAtomIO(),
			new StringAtomIO(),
			new XDateSequenceAtomIO(),
			new XNumberAtomIO(),
			new LookupRuleAtomIO());
	
	public static final JASB CONTEXT = 
			JASB.of(LanguageElements.get().allAsSet(), 
					DEFAULT_ATOM_IO);
	
	public static final JASB withExtraClasses(final Class<?>... classes) {
		return JASB.of(ImmutableSet.<Class<?>>builder().addAll(LanguageElements.get().allAsSet())
					.addAll(ImmutableSet.copyOf(classes)).build()
				, DEFAULT_ATOM_IO);
	}
}
