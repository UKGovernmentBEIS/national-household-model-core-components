package uk.org.cse.nhm.language.definition.batch.inputs.combinators;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;


@Doc(value = {
		// TODO: can we describe this better?
		"The zip element combines one or more batch inputs.",
		"Each time a zip element supplies a row of values, it does so by taking one row from each of its child inputs and combining them all into one long row.",
		"A zip element's bound (upper limit of rows it may produce) is the same as the smallest bound of the inputs it contains." })
@Bind("zip")
public class XZip extends XCombinator {
	
	@Override
	public boolean hasBound() {
		for(final XInputs d : getDelegates()) {
			if(d.hasBound()) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Prop(XInputs.P.PLACEHOLDERS)
	public List<String> getPlaceholders() {
		final ImmutableList.Builder<String> result = ImmutableList.builder();
		for(final XInputs d : getDelegates()) {
			result.addAll(d.getPlaceholders());
		}
		return result.build();
	}
}
