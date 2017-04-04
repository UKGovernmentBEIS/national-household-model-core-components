package uk.org.cse.nhm.language.definition.context.calibration;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

@Doc(
		{
			"Applies a polynomial correction to the energy use for some fuels.",
			"This is more efficient than using a replacement rule to write out the polynomial",
			"function by hand, as the polynomial coefficients will not be recomputed as many times."
		}
	)
@Bind("polynomial")
public class XPolynomialCalibration extends XCalibrationRule {
	public static class P {
		public static final String terms = "terms";
	}
	private List<XNumber> terms = new ArrayList<>();

	@Prop(P.terms)
	@Doc("The terms of the polynomial, in increasing power order (first argument is constant, second is linear, third is quadratic, and so on).")
	@BindRemainingArguments
	public List<XNumber> getTerms() {
		return terms;
	}

	public void setTerms(final List<XNumber> terms) {
		this.terms = terms;
	}
}
