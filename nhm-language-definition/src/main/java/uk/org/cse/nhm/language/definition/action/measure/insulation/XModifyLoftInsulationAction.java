package uk.org.cse.nhm.language.definition.action.measure.insulation;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("action.set-loft-insulation")
@Doc({	"An action which directly changes the properties of all loft insulation of a house to a specific value."})
@Unsuitability("Only houses which have a loft are suitable.")
public class XModifyLoftInsulationAction extends XModifyInsulationAction {
	private XNumber uValue;

	@BindNamedArgument("u-value")
	@Prop(P.uvalue)
	@Doc("Optional. New u-value for the exposed ceilings.")
	public XNumber getuValue() {
		return uValue;
	}

	public void setuValue(final XNumber uValue) {
		this.uValue = uValue;
	}
}
