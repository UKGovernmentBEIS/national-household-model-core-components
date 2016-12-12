package uk.org.cse.nhm.language.definition.action;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("action.set-thermal-bridging-factor")
@Doc({
	"Set the thermal bridging factor for a dwelling.",
	"This is the rate of heat loss due to thermal bridging per degree difference between internal and external temperature, per square meter of external area of the house.",
	"It is defined in BREDEM 2012 (see step 3A part b., and footnote vii).",
	"It has no effect when the energy calculator is in SAP 2012 mode (it will always be treated as 0.15, see worksheet step (36))."
	
})
@Category(CategoryType.ACTIONS)
public class XSetThermalBridgingFactorAction extends XFlaggedDwellingAction {
	public static final class P {
		public static final String thermalBridgingFactor = "thermalBridgingFactor";
	}
	
	private XNumber thermalBridgingFactor;
	
	@NotNull(message = "action.set-thermal-bridging-factor must always specify a number or a number function to use to compute the factor")
	@Prop(P.thermalBridgingFactor)
	@BindPositionalArgument(0)
	@Doc("A number which will be used as the thermal bridging factor for this dwelling.")
	public XNumber getThermalBridgingFactor() {
		return thermalBridgingFactor;
	}
	
	public void setThermalBridgingFactor(final XNumber thermalBridgingFactor) {
		this.thermalBridgingFactor = thermalBridgingFactor;
	}
}
