package uk.org.cse.nhm.language.definition.action.measure.adjust;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.sequence.XDeclaration;

@Bind("def-adjustment")
@Doc({"Declares an adjustment which can be inserted into a house's energy calculation.",
	"Each adjustment represents the installation of an unknown better appliance or technology into a house,",
	"which changes the demand for different fuels and may also introduce some missed gains, increasing the need for heat.",
	"Each house may only have each adjustment once.",
	"Adjustments will not have any effect when the energy calculator is in SAP 2012 mode."
})
@Category(CategoryType.DECLARATIONS)
public class XAdjustment extends XDeclaration {
	private double missedGains = 0;
	private List<XFuelType> fuelTypes = new ArrayList<>(0);
	private List<Double> values = new ArrayList<>(0);
	
	@BindNamedArgument("missed-gains")
	@Doc({"If installing the appliance would cause some gains to be missed, you can specify the amount here, in kWh/month.", 
		 "This much additional heat will be required in each heating month"})
	public double getMissedGains() {
		return missedGains;
	}
	public void setMissedGains(final double missedGains) {
		this.missedGains = missedGains;
	}
	
	@Doc("The types of fuel for which to adjust consumption.")
	@BindPositionalArgument(1)
	public List<XFuelType> getFuelTypes() {
		return fuelTypes;
	}
	public void setFuelTypes(final List<XFuelType> fuelTypes) {
		this.fuelTypes = fuelTypes;
	}
	
	@Doc({"The values associated with each of the fuel types, in kWh/month. This much extra fuel will be consumed, ",
		"or if negative this much consumption will be avoided if there is sufficient non-space-heat consumption to take it from."
	})
	@BindPositionalArgument(2)
	public List<Double> getValues() {
		return values;
	}
	public void setValues(final List<Double> values) {
		this.values = values;
	}
}
