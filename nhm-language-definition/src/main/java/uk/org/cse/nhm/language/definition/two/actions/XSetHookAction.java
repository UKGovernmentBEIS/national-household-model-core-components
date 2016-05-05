package uk.org.cse.nhm.language.definition.two.actions;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XScope;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("set")
@Doc("Set a global variable.")
public class XSetHookAction extends XHookAction {
	public static class P {
		public static final String variable = "variable";
		public static final String value = "value";
	}

	private XNumberDeclaration variable;
	private XNumber value;

	@Doc("The declaration of the variable to change - use #variable to refer to a declaration from elsewhere. The variable must be Simulation scoped.")
	@Prop(P.variable)
	@NotNull(message = "set requires a variable to set as its first argument")
	@BindPositionalArgument(0)
	@RequireScope(value = { XScope.Simulation }, message = "This is a global set: the variable it is setting must be Simulation scoped.")
	public XNumberDeclaration getVariable() {
		return variable;
	}

	public void setVariable(final XNumberDeclaration variable) {
		this.variable = variable;
	}

	@Doc("A function to use to compute the new value or adjustment")
	@Prop(P.value)
	@BindPositionalArgument(1)
	@NotNull(message = "set requires a value")
	public XNumber getValue() {
		return value;
	}

	public void setValue(final XNumber value) {
		this.value = value;
	}
}
