package uk.org.cse.nhm.language.definition.action.measure.lighting;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("measure.lighting")
@Doc({
	"Replace all lighting with lighting types in the given proportion.",
	"Proportions for the different type sof lightingshould sum to 1.",
	"Efficiencies are defined in Lumens Per Watt (lpw).",
	"When using SAP2012 calculator efficiences better than 11.2 lpw will be clamped to 20.4417 lpw (SAP CFL)"})
public class XLightingProportionsMeasure extends XMeasure {
	public static final class P {
		public static final String proportionOfCfl = "proportion-cfl";
		public static final String proportionOfIcandescent = "proportion-incandescent";
		public static final String proportionOfHAL = "proportion-hal";
		public static final String proportionOfLED = "proportion-led";
	}
	
	private XNumber proportionOfIncandescent = null;
	private XNumber propotionOfHAL = null;
	private XNumber proportionOfLED = null;
	private XNumber proportionOfCfl = null;

	@BindNamedArgument("proportion-cfl")
	@Prop(P.proportionOfCfl)
	@Doc("The proportion of compact fluorescent lamp with an efficiency of 67 lpw")
	public XNumber getProportionOfCfl() {
		return proportionOfCfl;
	}
	
	@BindNamedArgument("proportion-incandescent")
	@Prop(P.proportionOfIcandescent)
	@Doc("The proportion incandescent with an efficiency of 11.2 lpw")
	public XNumber getProportionOfIncandescent() {
		return proportionOfIncandescent;
	}
	
	@BindNamedArgument("proportion-hal")
	@Prop(P.proportionOfHAL)
	@Doc("The proportion of halogen lighting with an efficiency of 15.7 lpw")
	public XNumber getPropotionOfHAL() {
		return propotionOfHAL;
	}
	
	@BindNamedArgument("proportion-led")
	@Prop(P.proportionOfLED)
	@Doc("The proportion of LED with an efficiency of 67 lpw")
	public XNumber getProportionOfLED() {
		return proportionOfLED;
	}

	public void setProportionOfIncandescent(XNumber proportionOfIncandescent) {
		this.proportionOfIncandescent = proportionOfIncandescent;
	}

	public void setPropotionOfHAL(XNumber propotionOfHAL) {
		this.propotionOfHAL = propotionOfHAL;
	}

	public void setProportionOfLED(XNumber proportionOfLED) {
		this.proportionOfLED = proportionOfLED;
	}

	public void setProportionOfCfl(XNumber proportionOfCfl) {
		this.proportionOfCfl = proportionOfCfl;
	}
}
