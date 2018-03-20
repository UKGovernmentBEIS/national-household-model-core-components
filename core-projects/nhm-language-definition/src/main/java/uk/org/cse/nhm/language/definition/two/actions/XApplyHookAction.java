package uk.org.cse.nhm.language.definition.two.actions;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.two.selectors.ISetOfHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XAllTheHouses;

@Doc({"A command which can be used inside a scheduling command (like on.dates) to modify a given set of houses by using a series of measures or actions on each one.",
	  "You can specify the set of houses to operate on with the to: argument. When the simulator performs the apply, the set of houses",
	  "is computed, and shuffled into a random order. The action argument is then applied on a house-by-house basis, in that random order.",
	  "Because the changes happen in order, what happens to one house can affect the next. For example, a change to a global counter will be",
            "immediately effective for the next house that is to be affected.",
            "If you supply more than one action, the first action is applied to each house in turn, then the second action is applied to each house in turn, and so on."
	 })
@Bind("apply")
@Category(CategoryType.SCHEDULING)
public class XApplyHookAction extends XHookAction {
	public static final class P {
		public static final String houses = "houses";
		public static final String action = "action";
	}
    
    private ISetOfHouses houses = new XAllTheHouses();
	private List<XAction> actions = new ArrayList<>();
	
	@Prop(P.houses)
	@BindNamedArgument("to")
	@Doc("the houses to apply the action to")
    public ISetOfHouses getHouses() {
		return houses;
	}
    public void setHouses(final ISetOfHouses houses) {
		this.houses = houses;
	}
	
	@Prop(P.action)
	@Doc("the actions to apply; the first action will be applied to each house in turn, then the second will be applied to each house in turn, and so on. The order of dwellings is random, but it is consistent between actions.")
	@BindRemainingArguments
	public List<XAction> getActions() {
		return actions;
	}
	public void setActions(final List<XAction> actions) {
		this.actions = actions;
	}
}
