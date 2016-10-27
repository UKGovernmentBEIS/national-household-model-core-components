package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("action.reset-floors")
@Doc("Resets the thermal properties of all the floors in a house one by one, using the supplied functions to compute new values.")
@Category(CategoryType.RESETACTIONS)
public class XResetFloors extends XFlaggedDwellingAction {
	public static final class P {
		public static final String uValue = "uValue";
		public static final String infiltration = "infiltration";
	}
	
	private XNumber uValue;
	private XNumber infiltration;
	
	@Prop(P.uValue)
	@BindNamedArgument("u-values")
	@Doc("A function used to compute the new u-value for each floor.")
	public XNumber getuValue() {
		return uValue;
	}
	public void setuValue(final XNumber uValue) {
		this.uValue = uValue;
	}
	
	@Prop(P.infiltration)
	@BindNamedArgument("infiltrations")
	@Doc("A function used to compute the new air-change rate for each floor.")
	public XNumber getInfiltration() {
		return infiltration;
	}
	public void setInfiltration(final XNumber infiltration) {
		this.infiltration = infiltration;
	}
}
