package uk.org.cse.nhm.language.definition.action.measure.lighting;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Bind("measure.low-energy-lighting")
@Doc({
	"Replace existing lighting in a dwelling with low-energy CFL lighting.",
	"Low-energy lighting is assumed to be twice as efficient as incandescent lighting (see SAP appendix L, equation L2)."
})
@Unsuitability("The house has more than the threshold proportion of low-energy-lighting already installed.")
public class XLightingMeasure extends XMeasure {
	public static final class P {
		public static final String threshold = "threshold";
		public static final String proportion = "proportion";
		public static final String capex = "capex";
	}
	
	double threshold = 1.0;
	double proportion = 1.0;
	XNumber capex = new XNumberConstant();

	@BindNamedArgument
	@Prop(P.threshold)
	@Doc({
		"Dwellings with a currently installed proportion of low-energy lighting greater than this threashold will be unsuitable for this measure.",
		"If this is set lower than the current proportion, it will be reduced to be the same as the proportion."
	})
	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(final double threshold) {
		this.threshold = threshold;
	}

	@BindNamedArgument
	@Prop(P.proportion)
	@Doc({
		"The target proportion of low-energy lighting which the dwelling will have after the measure has been applied."
	})
	public double getProportion() {
		return proportion;
	}

	public void setProportion(final double proportion) {
		this.proportion = proportion;
	}

	@BindNamedArgument
	@Prop(P.capex)
	@Doc({
		"The capital cost of installing the low energy lighting.", 
		"This may be a function of the quantity of lights installed, which is measured as the part of the floor area serviced by those lights (size.m2)."
	})
	public XNumber getCapex() {
		return capex;
	}

	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}
}
