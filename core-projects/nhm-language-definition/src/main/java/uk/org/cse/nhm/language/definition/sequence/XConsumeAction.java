package uk.org.cse.nhm.language.definition.sequence;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseWeight;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc({"A shorthand for using action.case, action.fail, and decrease to simulate a having constrained quantity of something.",
	  "The consume action computes the value of its second argument, and subtracts that quantity from the variable referred to in its first argument, so long as the effect of this does not make that variable negative.",
	  "Placing a consume statement within a do statement will ensure that the do statement either uses up the quantity of interest, or",
	  "fails if there is not enough left. You can write it before other actions to work as a precondition, or after them to work as a postcondition."})
@Bind("consume")
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XConsumeAction extends XBindingAction {
	public static final class P {
		public static final String amount = "amount";
		public static final String variable = "variable";
	}
	
	private XNumber amount = new XHouseWeight();
	private XNumberDeclaration variable;

	@Prop(P.variable)
	@Doc("The variable to consume things from; the consume action will succeed if it can remove the value of the amount function from this variable without making it negative.")
	@NotNull(message="consume must have a variable to operate on as its first argument")
	@BindPositionalArgument(0)
	public XNumberDeclaration getVariable() {
		return variable;
	}
	public void setVariable(XNumberDeclaration variable) {
		this.variable = variable;
	}
	
	@Prop(P.amount)
	@Doc("The amount to remove from the variable, if this would result in a non-negative value." +
            " If this is a Simulation scoped variable, then you should usually multiply your value by house.weight.")
	@BindPositionalArgument(1)
	public XNumber getAmount() {
		return amount;
	}
	public void setAmount(XNumber amount) {
		this.amount = amount;
	}
}
