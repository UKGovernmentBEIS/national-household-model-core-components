package uk.org.cse.nhm.language.definition.batch.inputs.combinators;

import java.util.List;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.validation.DelegatePlaceholdersConsistent;


@Bind("concat")
@DelegatePlaceholdersConsistent(element = "concat")
@Doc(value = {
		"The concat element combines one or more batch inputs by appending them one after the other into a very long list.",
		"Concat's children must all provide the same placeholder names.",
		"The last child of a concat element may be unbounded. The other children must be bounded.",
		"A concat element's bound is the sum of the bounds of all its child inputs, or unbounded if any of its inputs are unbounded."
})
public class XConcatenate extends XCombinator {
	
	@Override
	public boolean hasBound() {
		final boolean infinite = false;
		for(final XInputs d : getDelegates()) {
			if(infinite) {
				throw new IllegalArgumentException("A concat element had an unbounded input which was not the last input.");
			}
			
			if (!d.hasBound()) {
				return false;
			}
		}
		
		return true; 
	}

	@Override
	@Prop(XInputs.P.PLACEHOLDERS)
	public List<String> getPlaceholders() {
		return getDelegates().get(0).getPlaceholders();
	}
}
