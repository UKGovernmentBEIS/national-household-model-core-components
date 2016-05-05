package uk.org.cse.nhm.language.definition.context;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.context.calibration.IEnergyFunction;
import uk.org.cse.nhm.language.validate.contents.ForbidChild;

import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.Identity;

@ForbidChild({IEnergyFunction.class, XAction.class, ICalibratedEnergyFunction.class})
@Category(CategoryType.WEATHER)
public abstract class XWeather extends XElement {
	@Override
	@Identity
	@BindNamedArgument
	@Doc("A name for this element")
	public String getName() {
		return super.getName();
	}
}
