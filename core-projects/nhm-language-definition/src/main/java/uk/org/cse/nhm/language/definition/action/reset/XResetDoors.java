package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("action.reset-doors")
@Doc({
	"An action which will be applied for each door on a house in turn, recomputing its u-value and size.",
	"When the energy calculator is in SAP 2012 mode, these values will be ignored and the relevant SAP tables will be used instead."
})
@Category(CategoryType.RESETACTIONS)
public class XResetDoors extends XFlaggedDwellingAction {
	public static final class P {
		public static final String area = "area";
		public static final String uValue = "uValue";
		public static final String rescale = "rescale";
	}
	
	private XNumber area;
	private XNumber uValue;
	private boolean rescale;
	
	@Prop(P.area)
	@BindNamedArgument("areas")
	@Doc("A function which will be used to recompute the area of each door.")
	public XNumber getArea() {
		return area;
	}
	public void setArea(final XNumber area) {
		this.area = area;
	}
	
	@Prop(P.uValue)
	@BindNamedArgument("u-values")
	@Doc("A function which will be used to recompute the u-value of each door.")
	public XNumber getuValue() {
		return uValue;
	}
	public void setuValue(final XNumber uValue) {
		this.uValue = uValue;
	}
	
	@Prop(P.rescale)
	@BindNamedArgument("rescale")
	@Doc({
		"If true, rescale all the doors using the CHM methodology after modify areas and u-values.",
		"This ensures that doors are rescaled so that no elevation's external area is more than half",
		"doors."
	})
	public boolean isRescale() {
		return rescale;
	}
	public void setRescale(final boolean rescale) {
		this.rescale = rescale;
	}
}
