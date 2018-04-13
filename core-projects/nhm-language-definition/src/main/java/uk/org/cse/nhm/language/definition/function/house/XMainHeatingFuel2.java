package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XMainHeatingFuelIs;

@Doc({"The fuel used by one of the heating or hot water systems in the house. Defaults to the main heating system, or the secondary system if there is no main system."
})
@Bind("house.heating-fuel")
@ReturnsEnum(XFuelType.class)
@SeeAlso(XMainHeatingFuelIs.class)
public class XMainHeatingFuel2 extends XCategoryFunction {
	private XHeatingSystem of = XHeatingSystem.PrimarySpaceHeating;

	@BindNamedArgument
	@Doc({"The heating system or hot water system for which to get the fuel.",
				"",
				"Note that in houses with no primary heating system, the result will still be Electricity because of the use",
				"of assumed direct electric heaters per SAP.",
				"",
				"Note also that a central hot water system may use other fuels than the fuel given here,",
				"if it has an immersion heater as well as a boiler connected, or a solar thermal system.",
				"This circumstance can be detected by looking at the fuel type breakdown for the water heating service."
				})
	public XHeatingSystem getOf() {
		return of;
	}

	public void setOf(XHeatingSystem of) {
		this.of = of;
	}
}
