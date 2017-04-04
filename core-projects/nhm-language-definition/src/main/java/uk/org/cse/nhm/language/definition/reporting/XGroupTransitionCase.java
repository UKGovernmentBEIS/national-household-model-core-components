package uk.org.cse.nhm.language.definition.reporting;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.two.actions.XTransitionHookAction;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.Identity;


@Doc("Defines one of the mutually exclusive conditions which is being reported in a group transition report.")
@Bind("when")
@Obsolete(reason = "Since we are moving away from groups in favour of housing sets, we have rewritten the group transitions report as a new element which uses this.", 
version = "5.0.0", 
inFavourOf = {XTransitionHookAction.class})
public class XGroupTransitionCase extends XElement {
	public static final class P {
		public static final String test = "test";
	}
	private XBoolean test;
	
	
	@BindPositionalArgument(0)
	@Prop(P.test)
	@Doc("The boolean test which will be used to determine if a house belongs to this case.")
	public XBoolean getTest() {
		return test;
	}
	public void setTest(final XBoolean test) {
		this.test = test;
	}
	
	@Override
	
	@BindNamedArgument
	@Doc("The name which will be displayed in the group transition report to denote this case.")
	@NotNull(message = "when element must always have a name.")
	
	@Identity
	public String getName() {
		return super.getName();
	}
}
