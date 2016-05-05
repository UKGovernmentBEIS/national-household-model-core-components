package uk.org.cse.nhm.language.definition.action.measure.heating;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("measure.heating-control")
@Doc("Installs a new heating control into a house")
@Unsuitability({
	"That type of heating control is already installed.",
	"The primary space heater is a room heater, and the type of control is any control other than an appliance thermostat.",
	"The primary space heater is a storage heater (for storage heater controls, see measure.storage-heater).",
	"The dwelling has no primary space heater."
})
public class XHeatingControlMeasure extends XMeasure {
	public static final class P {
		public static final String type = "type";
		public static final String capex = "capex";
	}
	@Doc({
		"A type of heating system control; the presence or absence of heating controls",
		"affects a range of values within the SAP calculation, mostly around the mean",
		"temperature in the house."
	})
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
	private XNumber capex = new XNumberConstant();
	
	@Prop(P.type)
	@BindNamedArgument
	@Doc("The type of heating control to install")
	public XHeatingControlType getType() {
		return type;
	}
	public void setType(final XHeatingControlType type) {
		this.type = type;
	}
	
	@Prop(P.capex)
	@BindNamedArgument
	@Doc("A function for computing the capital cost of installing the heating control")
	public XNumber getCapex() {
		return capex;
	}
	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}
}
