package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

@Doc({"An action which selects between several alternatives by evaluating boolean conditions.",
	"",
	"Each action.case may contain a number of <code>when</code> statements; for each house, the <emphasis>first</emphasis>",
	"<code>when</code> whose condition is true determines the action that will be taken."
})
@Bind("action.case")
@Unsuitability({"The first when whose condition passes is unsuitable", "No case has a legal when, and the default action is unsuitable"})
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XCaseAction extends XFlaggedDwellingAction {
	@Bind("when")
	@Doc("One of the alternatives in action.case.")
	public static class XCaseActionWhen extends XElement {
		public static final class P {
			public static final String test = "test";
			public static final String action = "action";
		}
		private XBoolean test;
		private XDwellingAction action;
		@Doc("A boolean condition to decide whether to use the following action")
		@BindPositionalArgument(0)
		@Prop(P.test)
		public XBoolean getTest() {
			return test;
		}
		public void setTest(final XBoolean test) {
			this.test = test;
		}
		@Doc("The action to perform if the preceding boolean condition is true")
		@BindPositionalArgument(1)
		@Prop(P.action)
		public XDwellingAction getAction() {
			return action;
		}
		public void setAction(final XDwellingAction action) {
			this.action = action;
		}
	}

	public static final class P {
		public static final String whens = "whens";
		public static final String defaultAction = "defaultAction";
	}
	
	private List<XCaseActionWhen> whens = new ArrayList<>();
	private XDwellingAction defaultAction = new XDoNothingAction();
	@Doc("A sequence of cases, which relate possible actions to take to the conditions in which they will be chosen.")
	@BindRemainingArguments
	@Prop(P.whens)
	public List<XCaseActionWhen> getWhens() {
		return whens;
	}
	public void setWhens(final List<XCaseActionWhen> whens) {
		this.whens = whens;
	}
	
	@Doc("The action to take if none of the when conditions pass.")
	@BindNamedArgument("default")
	@Prop(P.defaultAction)
	public XDwellingAction getDefaultAction() {
		return defaultAction;
	}
	public void setDefaultAction(final XDwellingAction defaultAction) {
		this.defaultAction = defaultAction;
	}
}
