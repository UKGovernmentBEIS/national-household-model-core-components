package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.basic.XBasicNumberFunction;

@Bind("house.lighting-proportion")
@Doc({"Returns the proportion of lighting of the house that is within the given efficiency range. Efficiency is defined as watts required to produce 1 watt of visible light",
	"For instance a standard incandescent light bulb requires 6.8 watts of electricty to produce 1 watt of visible light and a cfl half of this at 3.4."})
public class XHouseLightingProportion extends XBasicNumberFunction {
	public static class P {
		public static final String minEfficiency = "minEfficiency";
		public static final String maxEfficiency = "maxEfficiency";
	}
	
	private XNumber maxEfficiency;
	private XNumber minEfficiency;
	
	public void setMinEfficiency(XNumber minEfficiency) {
		this.minEfficiency = minEfficiency;
	}

	public void setMaxEfficiency(XNumber maxEfficiency) {
		this.maxEfficiency = maxEfficiency;
	}
	
	@BindNamedArgument("minEfficiency")
	@Prop(P.minEfficiency)
	@Doc("Minimum in watts of electricty required to produce  1 watt of visible light.")
	public XNumber getMinEfficiency(){
		return minEfficiency;
	}
	
	@BindNamedArgument("maxEfficiency")
	@Prop(P.maxEfficiency)
	@Doc("Maximum in watts of electricty required to produce  1 watt of visible light.")
	public XNumber getMaxEfficiency(){
		return maxEfficiency;
	}
}
