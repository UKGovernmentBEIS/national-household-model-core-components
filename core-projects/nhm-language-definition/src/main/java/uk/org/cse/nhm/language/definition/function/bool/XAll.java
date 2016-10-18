package uk.org.cse.nhm.language.definition.function.bool;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("all")
@Doc(
		{
			"This is true if and only if all of its contained values (inputs, below) are true.",
			"It is always true if it has no contents (i.e. <sgmltag>all</sgmltag> is equivalent to the boolean value true)."
		}
)
public class XAll extends XBooleanWithInputs {

}
