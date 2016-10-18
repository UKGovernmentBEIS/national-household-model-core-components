package uk.org.cse.nhm.language.definition.function.house;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XHouseIsOnTariff;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("house.tariff")
@Doc(value = "The tariff that a house uses for a given fuel type.")
@SeeAlso(XHouseIsOnTariff.class)
@RequireParent(IHouseContext.class)
@Category(CategoryType.TARIFFS)
public class XHouseTariff extends XFunction {
	public static class P {
		public static final String fuel = "fuel"; 
	}
	
	private XFuelType fuel;

	@Prop(P.fuel)
	
@BindNamedArgument
	@NotNull(message = "house.tariff function must specify a fuel")
	@Doc("The fuel type to look up the tariff for.")
	public XFuelType getFuel() {
		return fuel;
	}

	public void setFuel(final XFuelType fuel) {
		this.fuel = fuel;
	}
}
