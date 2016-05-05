package uk.org.cse.nhm.language.definition.function.bool;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.definition.function.bool.house.XIntegerIs;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("sim.year-is")
@Doc(
		{
			"This element tests the numeric value of the current simulation year.",
			"It can be used to test if the year is above, below or equal to a particular value."
		}
)
@Category(CategoryType.LOGIC)
public class XYearIs extends XIntegerIs {
	private XForesightLevel foresight = null;

	@BindNamedArgument
	@Doc({"The foresight level required to predict the change to this test in a prediction function like predict-sum.",
		"If unset, this is determined from the foresight level where the function is being used; for example, within",
		"the tariff definition this will have foresight level Tariffs."})
	public XForesightLevel getForesight() {
		return foresight;
	}

	public void setForesight(final XForesightLevel foresight) {
		this.foresight = foresight;
	}
}
