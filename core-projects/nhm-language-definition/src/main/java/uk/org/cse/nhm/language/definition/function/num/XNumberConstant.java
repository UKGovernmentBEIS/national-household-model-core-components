package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;


@Doc("A simple constant numeric value")
@Bind("number")
public class XNumberConstant extends XNumber {
	public static final class P {
		public static final String VALUE = "value";
	}
	
	private double value;

	@Prop(P.VALUE)
	@BindPositionalArgument(0)
	@Doc("The numeric constant in question")
	public double getValue() {
		return value;
	}

	public void setValue(final double value) {
		this.value = value;
	}

	public static XNumber create(final double i) {
		final XNumberConstant result = new XNumberConstant();
		result.setValue(i);
		return result;
	}
}
