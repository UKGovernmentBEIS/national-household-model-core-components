package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XServiceType;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Doc(
		{
			"The annual fuel cost for a house"
		}
	)
@Bind("house.fuel-cost")
@Category(CategoryType.MONEY)
public class XFuelCost extends XHouseNumber implements ICalibratedEnergyFunction {
	public static final class P {
		public static final String byFuel = "byFuel";
		public static final String byService = "byService";
		public static final String excludeServices = "excludeServices";
	}
	
	private XFuelType byFuel = null;
	private List<XServiceType> excludeServices = new ArrayList<>();
	
	@BindNamedArgument("by-fuel")
	@Doc("If set, yields the annual cost of the given fuel type.")
	public XFuelType getByFuel() {
		return byFuel;
	}
	public void setByFuel(final XFuelType byFuel) {
		this.byFuel = byFuel;
	}
	
	@BindNamedArgument("exclude-services")
	@Doc({"If true, the fuel bill will be calculated with all appliance energy consumption by these services omitted.",
		"However, the related internal gains and other effects will not be omitted - this is useful for calculating the fuel cost",
		"which is used in the definition of a SAP score.",
		"Note that the total bill may not be the sum of the marginal costs of each service, if you have non-linearly priced tariffs."
	})
	public List<XServiceType> getExcludeServices() {
		return excludeServices;
	}
	public void setExcludeServices(final List<XServiceType> excludeServices) {
		this.excludeServices = excludeServices;
	}
}
