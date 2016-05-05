package uk.org.cse.nhm.language.definition.action.choices;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XActionWithDelegates;
import uk.org.cse.nhm.language.definition.sequence.IScopingElement;
import uk.org.cse.nhm.language.definition.sequence.XBindingAction;
import uk.org.cse.nhm.language.visit.impl.VisitOrder;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Unsuitability("none of the choices are suitable")
@Category(CategoryType.ACTIONCOMBINATIONS)
@Bind("choice")
@Doc("An action which tries a number of alternative actions against a house, then users a select device to choose one of them.")
public class XChoiceAction extends XActionWithDelegates implements IScopingElement {
	public static final String CATEGORY = "Choices and packages";

	public static final class P {
		public static final String selector = "selector";
		public static final String bindings = "bindings";
	}
	
	private XChoiceSelector selector;
	
	private List<XBindingAction> bindings = new ArrayList<>();
	
	@VisitOrder(0)
	@Prop(P.bindings)
	@BindNamedArgument("do-first")
	@Doc({"A list of variable modifications to be made before the choice. This is equivalent to writing (do .. bindings .. (choice ...))."})
	public List<XBindingAction> getBindings() {
		return bindings;
	}
	public void setBindings(final List<XBindingAction> bindings) {
		this.bindings = bindings;
	}
	
	@NotNull(message = "choice element must always contain a selector.")
	@BindNamedArgument("select")
	@Prop(P.selector)
	@Doc("The rule for selecting the winning action from this choice.")
	public XChoiceSelector getSelector() {
		return selector;
	}
	
	public void setSelector(final XChoiceSelector selector) {
		this.selector = selector;
	}
}
