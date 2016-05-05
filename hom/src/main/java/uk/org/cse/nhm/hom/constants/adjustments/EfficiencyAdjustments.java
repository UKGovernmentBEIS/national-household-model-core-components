package uk.org.cse.nhm.hom.constants.adjustments;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

/**
 * These are constants from SAP table 4c
 * @author hinton
 *
 */
@ConstantDescription("Efficiency adjustments for boilers and heat pumps, in various conditions. Mostly from SAP table 4c.")
public enum EfficiencyAdjustments implements IConstant {
	@ConstantDescription("Efficiency adjustment for condensing boiler with underfloor heating (gas vs oil/lpg)")
	CONDENSING_UNDERFLOOR_HEATING(3/100.0, 2/100.0),
	
	@ConstantDescription("Efficiency adjustment for condensing boiler with weather or enhanced load compensator")
	CONDENSING_ADVANCED_COMPENSATOR(3/100.0, 1.5/100.0),
	
	@ConstantDescription("Efficiency adjustment for regular & combi boilers when there is no interlock or thermostat (combi boilers apply only to space heat efficiency)")
	BOILER_WITHOUT_INTERLOCK(-5/100.0),

	@ConstantDescription("Efficiency adjustment for space heating heat pumps with fan coils")
	HEAT_PUMP_WITH_FAN_COILS(0.85),
	
	@ConstantDescription("Efficiency adjustment for space heat pump with radiators & load compensator")
	HEAT_PUMP_WITH_RADIATORS_AND_COMPENSATOR(0.75),
	
	@ConstantDescription("Efficiency adjustment for space heat pump with radiators & no load compensator")
	HEAT_PUMP_WITH_RADIATORS(0.7),
	
	@ConstantDescription("Efficiency adjustment for heat pump supplying all DHW")
	HEAT_PUMP_SUPPLYING_ALL_DHW(0.7),
	
	;
	
	private final double[] values;
	
	EfficiencyAdjustments(final double... values) {
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
