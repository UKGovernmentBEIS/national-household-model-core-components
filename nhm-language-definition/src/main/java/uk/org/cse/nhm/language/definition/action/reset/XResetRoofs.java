package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Doc("A special action which resets the thermal properties of the roofs and ceilings in a house")
@Bind("action.reset-roofs")
@Category(CategoryType.RESETACTIONS)
public class XResetRoofs extends XFlaggedDwellingAction {
	public static final class P {
		public static final String uValue = "uValue";
		public static final String kValue = "kValue";
		public static final String partyKValue = "partyKValue";
	}
	
	private XNumber uValue;
	private XNumber kValue;
	private XNumber partyKValue;
	
	@Prop(P.uValue)
	@BindNamedArgument("u-values")
	@Doc("A function which will be used to compute the u-value for the external area of each roof.")
	public XNumber getuValue() {
		return uValue;
	}
	public void setuValue(final XNumber uValue) {
		this.uValue = uValue;
	}
	
	@Prop(P.kValue)
	@BindNamedArgument("k-values")
	@Doc("A function which will be used to compute the k-value for the external area of each roof.")
	public XNumber getkValue() {
		return kValue;
	}
	public void setkValue(final XNumber kValue) {
		this.kValue = kValue;
	}
	
	@Prop(P.partyKValue)
	@BindNamedArgument("party-k-values")
	@Doc("A function which will be used to compute the party k-value for the party area of each ceiling.")
	public XNumber getPartyKValue() {
		return partyKValue;
	}
	public void setPartyKValue(final XNumber partyKValue) {
		this.partyKValue = partyKValue;
	}
}
