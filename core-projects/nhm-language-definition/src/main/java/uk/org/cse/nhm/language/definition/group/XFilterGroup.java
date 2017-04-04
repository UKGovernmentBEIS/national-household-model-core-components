package uk.org.cse.nhm.language.definition.group;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;


@Doc(
		value = {
				"Filter group is useful for restricting the houses to which a target will be applied, or for which a report will be computed.",
				"The group has a test function (or boolean predicate), which is used to restrict the houses contained in the filter group.",
				"At any time, a filter group will contain all of the houses drawn from its source group for which the test function is true."
		}	)
@Bind("group.filter")
public class XFilterGroup extends XGroupWithSource implements IHouseContext{
	public static final class P {

		public static final String FUNCTION = "function";
		
	}
	private XBoolean function;

	
	@NotNull
	@Prop(P.FUNCTION)
	@BindPositionalArgument(0)
	@Doc("The test function for the group - only houses for which this condition is true will be present in the group.")
	public XBoolean getFunction() {
		return function;
	}

	public void setFunction(final XBoolean function) {
		this.function = function;
	}
}
