package uk.org.cse.nhm.language.definition.batch.inputs.combinators;

import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;


@Bind("cross")
@Doc(value = {"The cross element combines one or more batch inputs using a cartesian join.",
		"It effectively takes all the possible combinations of its various child inputs and turns them into a very large table of values.",
		"A cross element's bound (upper limit of rows it may produce) is calculated as the bounds of all of its child elements multiplied together.",
		"Unbounded child elements are not allowed. Wrap any distributions in a bounded element before using them in here."})
public class XCartesianProduct extends XCombinator {

	@Override
	public boolean hasBound() {
		for(final XInputs d : getDelegates()) {
			if(!d.hasBound()) {
				return false;
			}
		}
		return true;
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
