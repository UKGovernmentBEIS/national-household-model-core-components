package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;


@Bind("get")
@Doc({
	"Gets a value that has been bound to a name by (set), (increase) or (decrease)"
})
public class XGet extends XNumber {
	public static final class P {
		public static final String var = "var";
	}
	
	private XNumberDeclaration var;
	
	@Prop(P.var)
	
	@BindPositionalArgument(0)
	@NotNull(message="get needs the name of a variable to get")
	@Doc("The name of the variable to get")
	public XNumberDeclaration getVar() {
		return var;
	}
	public void setVar(final XNumberDeclaration var) {
		this.var = var;
	}
}
