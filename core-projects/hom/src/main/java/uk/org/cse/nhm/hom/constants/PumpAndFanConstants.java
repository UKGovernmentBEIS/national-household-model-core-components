package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants controlling the power and gains of various pumps and fans.")
public enum PumpAndFanConstants implements IConstant {

	/*
	BEISDOC
	NAME: Central heating pump base power
	DESCRIPTION: The wattage of the central heating circulation pump
	TYPE: constant
	UNIT: W
	SAP: Table 4f
        SAP_COMPLIANT: Yes
	BREDEM: Table 4
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	ID: central-heating-pump-base-power
	CODSIEB
	*/
	@ConstantDescription("The wattage of the central heating circulation pump - this is from SAP table 4f, converted into watts and assigned over the 9 heating months equally")
	CENTRAL_HEATING_PUMP_WATTAGE(13.69),

	/*
	BEISDOC
	NAME: Central heating pump gains
	DESCRIPTION: The gains of the central heating circulation pump
	TYPE: value
	UNIT: W
	SAP: Table 5a
        SAP_COMPLAINT: No, see note
	BREDEM: Table 26
        BREDEM_COMPLIANT: No, see note
	SET: context.energy-constants
	NOTES: We don't have the information needed to identify 2013 and later central heating systems, which have lower gains for their pump.
	ID: central-heating-pump-gains
	CODSIEB
	*/
	@ConstantDescription("The gains of the central heating circulation pump - this is from SAP table 5a")
	CENTRAL_HEATING_PUMP_GAINS(10),

	/*
	BEISDOC
	NAME: Oil boiler pump base power
	DESCRIPTION: The base wattage of a oil boiler fuel pump
	TYPE: value
	UNIT: W
	SAP: Table 4f
        SAP_COMPLIANT: Yes
	BREDEM: Table 4
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	ID: oil-boiler-pump-base-power
	CODSIEB
	*/
	@ConstantDescription("The wattage of oil boiler fuel pump - from SAP table 4f, converted into watts and distributed over the 9 heating months")
	OIL_FUEL_PUMP_WATTAGE(11.41),

	/*
	BEISDOC
	NAME: Oil boiler pump gains
	DESCRIPTION: The gains from oil boiler fuel pump
	TYPE: value
	UNIT: W
	SAP: Table 5a
        SAP_COMPLIANT: Yes
	BREDEM: Table 26
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: oil-boiler-pump-gains
	CODSIEB
	*/
	@ConstantDescription("The gains from oil boiler fuel pump - from SAP table 5a")
	OIL_FUEL_PUMP_GAINS(10),

	/*
	BEISDOC
	NAME: Gas boiler pump power
	DESCRIPTION: The wattage of gas boiler flue fan.
	TYPE: value
	UNIT: W
	SAP: Table 4f
        SAP_COMPLIANT: Yes
	BREDEM: Table 4
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	ID: gas-boiler-pump-power
	CODSIEB
	*/
	@ConstantDescription("The wattage of gas boiler or gas heat pump flue fan - from SAP table 4f, converted into watts and distributed over the year")
	GAS_BOILER_FLUE_FAN_WATTAGE(5.1336),

	/*
	BEISDOC
	NAME: Gas heat pump pump power
	DESCRIPTION: The wattage of gas heat pump flue fan.
	TYPE: value
	UNIT: W
	SAP: Table 4f
        SAP_COMPLIANT: Yes
	BREDEM: Table 4
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	ID: gas-heat-pump-pump-power
	CODSIEB
	*/
	@ConstantDescription("The wattage of gas heat pump flue fan - from SAP table 4f, converted into watts and distributed over the year")
	GAS_HEAT_PUMP_FLUE_FAN_WATTAGE(5.1336),

	/*
	BEISDOC
	NAME: Pump no thermostat multiplier
	DESCRIPTION: The multiplier for room thermostat being missing, applied to central heating and oil boiler pump power requirement
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 4f
        SAP_COMPLIANT: Yes
	BREDEM: Table 4
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	NOTES: Derived by dividing the no room thermostat row by the with room thermostat row
	ID: pump-no-thermostat-modifier
	CODSIEB
	*/
	@ConstantDescription("The multiplier for room thermostat being missing, applied to central heating and oil boiler pump power requirement, from SAP table 4f note (a)")
	NO_ROOM_THERMOSTAT_MULTIPLIER(1.3),

	/*
	BEISDOC
	NAME: Warm Air System Volume Multiplier
	DESCRIPTION: The volume multiplier for a warm air system's circulating fan multiplied by its specific ventilation fan power.
	TYPE: value
	UNIT: W
	SAP: Table 5a (warm air heating system fans)
        SAP_COMPLIANT: Yes
	BREDEM: Table 4, Table 26 (warm air heating system fans)
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	NOTES: Specific ventilation fan power (SFP) assumed to be 1.5.
	ID: warm-air-system-volume-multiplier
	CODSIEB
	*/
	@ConstantDescription("The volume multiplier for a warm air system's circulating fan")
	WARM_AIR_SYSTEM_VOLUME_MULTIPLIER(0.06)
	;

	private final double[] values;

	PumpAndFanConstants(final double... values) {
		this.values = values;
	}

	@Override
	public <T> T getValue(final Class<T> clazz) {
		if (clazz.isAssignableFrom(double[].class)) {
			if (values == null) throw new RuntimeException(this + " cannot be read as a double[]");
			return clazz.cast(values);
		} else if (clazz.isAssignableFrom(Double.class)) {
			if (values.length != 1) throw new RuntimeException(this + " cannot be read as a double");
			return clazz.cast(values[0]);
		} else {
			throw new RuntimeException(this + " cannot be read as a " + clazz.getSimpleName());
		}
	}
}
