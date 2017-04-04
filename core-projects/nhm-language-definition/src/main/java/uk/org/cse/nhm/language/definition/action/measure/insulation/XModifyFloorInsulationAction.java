package uk.org.cse.nhm.language.definition.action.measure.insulation;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("action.set-floor-insulation")
@Doc({	"An action which directly changes the properties of all floor insulation of a house to a specific value."})
@Unsuitability(alwaysSuitable=true)
public class XModifyFloorInsulationAction extends XModifyInsulationAction {
	private XNumber uValue;

	@BindNamedArgument("u-value")
	@Prop(P.uvalue)
	@Doc("Optional. New u-value for the floor.")
	public XNumber getuValue() {
		return uValue;
	}

	public void setuValue(final XNumber uValue) {
		this.uValue = uValue;
	}
}
