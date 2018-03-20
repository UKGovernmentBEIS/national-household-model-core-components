package uk.org.cse.nhm.language.definition.two.hooks;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XScenarioElement;
import uk.org.cse.nhm.language.definition.two.actions.XHookAction;

@Category(CategoryType.SCHEDULING)
public abstract class XHook extends XScenarioElement {
	private List<XHookAction> actions = new ArrayList<>();
	public static final class P {
		public static final String actions = "actions";
	}
	
        @Doc("A sequence of actions which will be performed in the order they are written here, on the given dates")
	@Prop(P.actions)
	@BindRemainingArguments
	public List<XHookAction> getActions() {
		return actions;
	}

	public void setActions(final List<XHookAction> actions) {
		this.actions = actions;
	}
}
