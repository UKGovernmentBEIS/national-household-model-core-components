package uk.org.cse.nhm.language.definition.function.bool;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("any")
@Doc(
		{
			"This is true if and only if at least one of its contained values (inputs, below) are true.",
			"It is always false if it has no contents (i.e. <sgmltag>any</sgmltag> is equivalent to the boolean value false)."
		}
)
public class XAny extends XBooleanWithInputs {

}
