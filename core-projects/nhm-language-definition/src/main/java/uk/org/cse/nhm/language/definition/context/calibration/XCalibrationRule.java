package uk.org.cse.nhm.language.definition.context.calibration;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;

import com.larkery.jasb.bind.BindNamedArgument;

@Category(CategoryType.CALIBRATION)
public abstract class XCalibrationRule extends XElement implements IHouseContext {
	public static class P {
		public static final String fuels = "fuels";
	}
	
	private List<XFuelType> fuels = new ArrayList<>();
	
	@Prop(P.fuels)
	@Doc("The fuels for which to compute a new energy consumption")
	@BindNamedArgument("fuels")
	@Size(min = 1, message = "At least one fuel must be specified for a calibration rule")
	public List<XFuelType> getFuels() {
		return fuels;
	}

	public void setFuels(final List<XFuelType> fuels) {
		this.fuels = fuels;
	}
}