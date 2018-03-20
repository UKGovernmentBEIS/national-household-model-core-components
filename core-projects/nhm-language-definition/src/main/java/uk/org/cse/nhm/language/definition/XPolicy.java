package uk.org.cse.nhm.language.definition;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.two.actions.XApplyHookAction;
import uk.org.cse.nhm.language.definition.two.hooks.XDateHook;

@Doc("A policy is an organisational element, which collects together a set of related targets and cases.")
@Bind("policy")
@Obsolete(
	reason = XDateHook.SCHEDULING_OBSOLESCENCE,
	inFavourOf = {XDateHook.class, XApplyHookAction.class}
		)
@Category(CategoryType.OBSOLETE)
@TopLevel
public class XPolicy extends XScenarioElement {
	public static final class P {
		public static final String ACTIONS = "actions";
	}
	
	private List<XPolicyAction> actions = new ArrayList<XPolicyAction>();
	
	@Prop(P.ACTIONS)
	
	@Doc("A sequence of things to do in this scenario.")
	@BindRemainingArguments
	@Size(min = 1, message = "policy must include at least one target or case")
	public List<XPolicyAction> getActions() {
		return actions;
	}
	
	public void setActions(final List<XPolicyAction> actions) {
		this.actions = actions;
	}
}
