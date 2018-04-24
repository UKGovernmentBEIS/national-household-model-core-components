package uk.org.cse.nhm.language.definition.action.measure.insulation;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Category(CategoryType.RESETACTIONS)
public abstract class XModifyInsulationAction extends XFlaggedDwellingAction {
	public static final class P {
		public static final String insulationthickness = "insulationthickness";
		public static final String uvalue = "uvalue";
	}
	private XNumber insulationThickness;

	@BindNamedArgument("thickness")
	@Doc("The new insulation thickness to set it to.")
	@Prop(P.insulationthickness)
	public XNumber getInsulationThickness() {
		return insulationThickness;
	}

	public void setInsulationThickness(final XNumber insulationThickness) {
		this.insulationThickness = insulationThickness;
	}
	
}
