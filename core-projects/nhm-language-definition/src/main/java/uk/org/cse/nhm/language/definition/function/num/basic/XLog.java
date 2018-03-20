package uk.org.cse.nhm.language.definition.function.num.basic;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;


@Bind("log")
@Doc({"Returns the logarithm of a number to the given base."})
@Category(CategoryType.ARITHMETIC)
public class XLog extends XNumber {
	public static class P {
		public static final String delegate = "delegate";
		public static final String base = "base";
	}
	
	private double base = Math.E;
	private XNumber delegate;
	
	@Prop(P.delegate)
	@NotNull(message = "log must always contain another function.")
	@Doc("The function to take a logarithm of")
	@BindPositionalArgument(0)
	public XNumber getDelegate() {
		return delegate;
	}

	public void setDelegate(final XNumber delegate) {
		this.delegate = delegate;
	}

	@Prop(P.base)
	@Doc("The base of the logarithm - the default value is Euler's number e.")
	@BindNamedArgument
	public double getBase() {
		return base;
	}

	public void setBase(final double base) {
		this.base = base;
	}
}
