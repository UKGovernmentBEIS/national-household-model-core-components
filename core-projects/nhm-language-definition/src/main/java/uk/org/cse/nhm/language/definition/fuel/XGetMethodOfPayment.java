package uk.org.cse.nhm.language.definition.fuel;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Category(CategoryType.TARIFFS)
@Bind("house.payment-method")
@ReturnsEnum(XMethodOfPayment.class)
@Doc({"Gets the method of payment used to pay for a particular fuel - this is defined as part of the tariff",
	"used to pay for that fuel."
})
public class XGetMethodOfPayment extends XCategoryFunction {
	public static class P {
		public static final String fuel = "fuelType"; 
	}
	
	private XFuelType fuelType;

	@Prop(P.fuel)
	@BindPositionalArgument(0)
	@Doc("The fuel type to find the payment method for")
	@NotNull(message="house.payment-method must have a fuel type to get the payment method for")
	public XFuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(final XFuelType fuelType) {
		this.fuelType = fuelType;
	}
}
