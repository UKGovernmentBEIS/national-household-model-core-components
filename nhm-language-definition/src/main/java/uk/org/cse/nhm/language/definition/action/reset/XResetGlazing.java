package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("action.reset-glazing")
@Doc("A special action which updates the condition of each window in a house in turn.")
@Category(CategoryType.RESETACTIONS)
public class XResetGlazing extends XFlaggedDwellingAction {
	public static final class P {
		public static final String frameFactor = "frameFactor";
		public static final String uValue = "uValue";
		public static final String lightTransmittance = "lightTransmittance";
		public static final String gainsTransmittance = "gainsTransmittance";
	}
	
	private XNumber frameFactor;
	private XNumber uValue;
	private XNumber lightTransmittance;
	private XNumber gainsTransmittance;
	
	@Prop(P.frameFactor)
	@Doc("A function which will be used to recompute the frame factor for each window.")
	@BindNamedArgument("frame-factor")
	public XNumber getFrameFactor() {
		return frameFactor;
	}
	public void setFrameFactor(final XNumber frameFactor) {
		this.frameFactor = frameFactor;
	}
	
	@Prop(P.uValue)
	@Doc("A function which will be used to recompute the u-value for each window.")
	@BindNamedArgument("u-value")
	public XNumber getuValue() {
		return uValue;
	}
	public void setuValue(final XNumber uValue) {
		this.uValue = uValue;
	}
	
	@Prop(P.lightTransmittance)
	@Doc("A function which will be used to recompute the visible light transmittance for each window.")
	@BindNamedArgument("light-transmittance")
	public XNumber getLightTransmittance() {
		return lightTransmittance;
	}
	public void setLightTransmittance(final XNumber lightTransmittance) {
		this.lightTransmittance = lightTransmittance;
	}
	
	@Prop(P.gainsTransmittance)
	@Doc("A function which will be used to recompute the thermal gains transmittance for each window.")
	@BindNamedArgument("gains-transmittance")
	public XNumber getGainsTransmittance() {
		return gainsTransmittance;
	}
	public void setGainsTransmittance(final XNumber gainsTransmittance) {
		this.gainsTransmittance = gainsTransmittance;
	}
	
	
}
