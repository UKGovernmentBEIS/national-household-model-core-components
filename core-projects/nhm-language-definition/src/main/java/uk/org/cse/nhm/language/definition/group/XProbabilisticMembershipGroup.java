package uk.org.cse.nhm.language.definition.group;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("group.probabilistic-membership")
@Doc(
		value = {
			"This group draws houses from the source group using a Bernoulli sampler for each house.",
			"The bias of the sampler is computed for each candidate using the supplied function.",
			"Sampling occurs when houses enter the source group, or when houses are modified by a policy in such a way as",
			"to change their sample bias."
		}
	)
public class XProbabilisticMembershipGroup extends XGroupWithSource {
	public static final class P {
		public static final String FUNCTION = "function";
	}
	private XNumber function;

	@BindNamedArgument
	@Prop(P.FUNCTION)
	
	@Doc("A function used to determine the bias of the sampler, for each house considered.")
	public XNumber getFunction() {
		return function;
	}

	public void setFunction(final XNumber function) {
		this.function = function;
	}
}
