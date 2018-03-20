package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Doc("A special action which, when applied to a house, will recompute the operational costs of all relevant technologies within the house.")
@Bind("action.reset-opex")
@Category(CategoryType.RESETACTIONS)
public class XResetOpex extends XDwellingAction {
	public static final class P {
		public static final String opex = "opex";
	}
	private XNumber opex = new XNumberConstant();

	@Doc("The function to use to recompute the opex for each part of the house.")
	@BindPositionalArgument(0)
	public XNumber getOpex() {
		return opex;
	}

	public void setOpex(final XNumber opex) {
		this.opex = opex;
	}
}
