package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.context.XCarbonFactorContext;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XServiceType;

@Doc(
		{
			"The annual carbon emissions for a house.",
			"The units will be kWh multiplied by the units used in the carbon-factors context."
		}
	)
@SeeAlso(XCarbonFactorContext.class)
@Bind("house.emissions")
public class XCarbonEmissions extends XHouseNumber implements ICalibratedEnergyFunction {
	public static final class P {
		public static final String byFuel = "byFuel";
		public static final String byService = "byService";
	}
	
	private XFuelType byFuel = null;
	private XServiceType byService = null;
	
	
	@BindNamedArgument("by-fuel")
	@Doc("If set, yields the emissions due to use of the given fuel type.")
	public XFuelType getByFuel() {
		return byFuel;
	}
	public void setByFuel(final XFuelType byFuel) {
		this.byFuel = byFuel;
	}
	
	@Doc("If set, yields the emissions due to use of the given service.")
	
	@BindNamedArgument("by-service")
	public XServiceType getByService() {
		return byService;
	}
	public void setByService(final XServiceType byService) {
		this.byService = byService;
	}
}
