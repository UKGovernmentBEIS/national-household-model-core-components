package uk.org.cse.nhm.language.definition.function.bool;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;


@Bind("none")
@Doc(
		{
			"This is true if and only if all of its contained values (inputs, below) are false.",
			"It is a more general version of logical not - with a single contained value it is exactly not.",
			"With multiple contained values it is the negation of their disjunction (not or), or equivalently",
			"the conjunction of their negations."
		}
)
public class XNone extends XBooleanWithInputs {

}
