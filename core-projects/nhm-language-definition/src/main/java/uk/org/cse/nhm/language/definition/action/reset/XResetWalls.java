package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("action.reset-walls")
@Doc({
		"Reset the properties of all the external walls in a house according to some lookup functions."
	 })
@Category(CategoryType.RESETACTIONS)
public class XResetWalls extends XFlaggedDwellingAction {
	public static final class P {
		public static final String uvalue = "uValue";
		public static final String kvalue = "kValue";
		public static final String infiltration = "infiltration";
		public static final String thickness = "thickness";
		
	}
	
	private XNumber uValue;
	private XNumber infiltration;
	private XNumber kValue;
	private XNumber thickness;
	
	@Prop(P.uvalue)
	@Doc("A function which will be evaluated once for each wall to compute a new u-value for the wall. Applied for external walls.")
	@BindNamedArgument("u-values")
	public XNumber getuValue() {
		return uValue;
	}
	public void setuValue(final XNumber uValue) {
		this.uValue = uValue;
	}

	@Prop(P.thickness)
	@BindNamedArgument("thicknesses")
	@Doc("A function which will be evaluated once for each wall to compute the new thickness for the wall. Applied for external walls.")
	public XNumber getThickness() {
		return thickness;
	}
	public void setThickness(final XNumber thickness) {
		this.thickness = thickness;
	}
	
	@Prop(P.infiltration)
	@Doc("A function which will be evaluated once for each wall to compute a new infiltration rate for the wall. Applied for external walls.")
	@BindNamedArgument("infiltrations")
	public XNumber getInfiltration() {
		return infiltration;
	}
	public void setInfiltration(final XNumber infiltration) {
		this.infiltration = infiltration;
	}
	
	@Prop(P.kvalue)
	@Doc("A function which will be evaluated once for each wall to compute a new k-value for the wall. Applied for external and party walls.")
	@BindNamedArgument("k-values")
	public XNumber getkValue() {
		return kValue;
	}
	public void setkValue(final XNumber kValue) {
		this.kValue = kValue;
	}
}
