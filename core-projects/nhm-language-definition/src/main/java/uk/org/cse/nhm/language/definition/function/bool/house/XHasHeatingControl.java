package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

@Bind("house.has-heating-control")
@Doc("Checks to see of a dwelling has the given heating control")
public class XHasHeatingControl extends XHouseBoolean {
	public static final class P {
		public static final String type = "type";
	}
	
	public enum XHeatingControlType {
		@Doc("Suitable for wet central heating, warm air systems, and room heaters")
		ApplianceThermostat,
		@Doc("Suitable for wet central heating and warm air systems")
		RoomThermostat,
		@Doc("Suitable for wet central heating and warm air systems")
		ThermostaticRadiatorValve,
		@Doc("Suitable for wet central heating and warm air systems")
		TimeTemperatureZoneControl,
		@Doc("Suitable for wet central heating")
		DelayedStartThermostat,
		@Doc("Suitable for wet central heating")
		Programmer,
		@Doc("Suitable for wet central heating")
		BypassValve
	}
	
	private XHeatingControlType type = XHeatingControlType.Programmer;
	
	@Prop(P.type)
	@BindNamedArgument
	@Doc("The type of heating control to check for")
	public XHeatingControlType getType() {
		return type;
	}
	public void setType(final XHeatingControlType type) {
		this.type = type;
	}
}
