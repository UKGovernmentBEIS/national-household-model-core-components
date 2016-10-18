package uk.org.cse.nhm.language.definition.action.choices;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.sequence.IScopingElement;
import uk.org.cse.nhm.language.definition.sequence.XBindingAction;
import uk.org.cse.nhm.language.visit.impl.VisitOrder;

import com.larkery.jasb.bind.BindNamedArgument;

@Category(CategoryType.ACTIONCOMBINATIONS)
public abstract class XChoiceSelector extends XElement implements IScopingElement {
	public static class P {
		public static final String bindings = "bindings";
	}
	private List<XBindingAction> bindings = new ArrayList<>();
	@VisitOrder(0)
	@Prop(P.bindings)
	@BindNamedArgument("do-first")
	@Doc({"A list of bindings to be made before the choice. This is equivalent to writing (do .. bindings .. (choice ...))."})
	public List<XBindingAction> getBindings() {
		return bindings;
	}
	public void setBindings(final List<XBindingAction> bindings) {
		this.bindings = bindings;
	}
}
