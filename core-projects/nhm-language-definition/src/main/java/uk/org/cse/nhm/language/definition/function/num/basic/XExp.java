package uk.org.cse.nhm.language.definition.function.num.basic;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;


@Bind("exp")
@Doc({"Returns the exponential of a number.", 
	"This is Euler's Number e raised to the power of the result of the function contiained within exp."})
@Category(CategoryType.ARITHMETIC)
public class XExp extends XNumber {
	public static class P {
		public static final String delegate = "delegate";
	}
	
	private XNumber delegate;

	@Prop(P.delegate)
	
	@NotNull(message = "exp must always contain another function.")
	@Doc("The function which will produce the exponent.")
	@BindPositionalArgument(0)
	public XNumber getDelegate() {
		return delegate;
	}

	public void setDelegate(final XNumber delegate) {
		this.delegate = delegate;
	}

}
