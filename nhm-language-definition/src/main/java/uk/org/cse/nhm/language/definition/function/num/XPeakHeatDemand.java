package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("house.peak-load")
@Doc(value = {"The peak heating load, in kW, for this house, determined using the specific heat loss, the demand temperature, and the coldest outside temperature.",
		"This is determined using Newton's law of cooling, so peak load = temperature difference * specific heat loss."
})
public class XPeakHeatDemand extends XHouseNumber {
	public static final class P {
		public static final String internalTemperature = "internalTemperature";
		public static final String externalTemperature = "externalTemperature";
	}
	
	private double internalTemperature = 19;
	private double externalTemperature = -5;
	
	@Doc("The design internal temperature for the house; this is the temperature to which the inhabitants would like the house heated.")
	
	@BindNamedArgument("internal-temperature")
	public double getInternalTemperature() {
		return internalTemperature;
	}
	public void setInternalTemperature(final double internalTemperature) {
		this.internalTemperature = internalTemperature;
	}
	
	@Doc("The design minimum external temperature for the house; this should be something like the lowest two day average temperature recorded ten times in a twenty year period.")
	
	@BindNamedArgument("external-temperature")
	public double getExternalTemperature() {
		return externalTemperature;
	}
	public void setExternalTemperature(final double externalTemperature) {
		this.externalTemperature = externalTemperature;
	}
}
