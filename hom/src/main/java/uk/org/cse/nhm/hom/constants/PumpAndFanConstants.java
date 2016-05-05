package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants controlling the power and gains of various pumps and fans.")
public enum PumpAndFanConstants implements IConstant {
	@ConstantDescription("The wattage of the central heating circulation pump - this is from SAP table 4f, converted into watts and assigned over the 9 heating months equally")
	CENTRAL_HEATING_PUMP_WATTAGE(11.1227),
	
	@ConstantDescription("The gains of the central heating circulation pump - this is from SAP table 5a")
	CENTRAL_HEATING_PUMP_GAINS(10),
	
	@ConstantDescription("The wattage of oil boiler fuel pump - from SAP table 4f, converted into watts and distributed over the 9 heating months")
	OIL_FUEL_PUMP_WATTAGE(8.5560),
	
	@ConstantDescription("The gains from oil boiler fuel pump - from SAP table 5a")
	OIL_FUEL_PUMP_GAINS(10),
	
	@ConstantDescription("The wattage of gas boiler or gas heat pump flue fan - from SAP table 4f, converted into watts and distributed over the year")
	GAS_BOILER_FLUE_FAN_WATTAGE(5.1336),
	
	@ConstantDescription("The wattage of gas heat pump flue fan - from SAP table 4f, converted into watts and distributed over the year")
	GAS_HEAT_PUMP_FLUE_FAN_WATTAGE(5.1336),
	
	@ConstantDescription("The wattage of a solar heating pump - from SAP table 4f, assuming solar pump runs equally all year")
	SOLAR_WATER_HEATING_PUMP_WATTAGE(8.5560),
	
	@ConstantDescription("The multiplier for room thermostat being missing, applied to central heating & oil boiler pump power requirement, from SAP table 4f note (a)")
	NO_ROOM_THERMOSTAT_MULTIPLIER(1.3),
	
	@ConstantDescription("The volume multiplier for a warm air system's circulating fan")
	WARM_AIR_SYSTEM_VOLUME_MULTIPLIER(0.091324200) // 0.6 V kwH/year; make pro-rata for when heating is on, conver to watts
	;
	
	private final double[] values;
	
	PumpAndFanConstants(final double... values) {
		this.values = values;
	}
	
	@Override
	public <T> T getValue(Class<T> clazz) {
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
