package uk.org.cse.nhm.language.definition.context.calibration;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc({ "Replaces the energy calculation for a set of fuels with",
		"an equation of your choosing. This is the most flexible kind of calibration." })
@Bind("replace")
public class XReplacementCalibration extends XCalibrationRule {
	public static class P {
		public static final String value = "value";
	}
	private XNumber value;

	@Doc("The function to use when computing the new energy consumption")
	@BindPositionalArgument(0)
	@NotNull(message = "A function is required to compute the new energy consumption")
	@Prop(P.value)
	public XNumber getValue() {
		return value;
	}

	public void setValue(final XNumber value) {
		this.value = value;
	}
}