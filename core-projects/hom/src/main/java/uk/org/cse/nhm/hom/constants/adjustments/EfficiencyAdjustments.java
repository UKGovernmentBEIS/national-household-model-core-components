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
	/*
	BEISDOC
	NAME: 
	DESCRIPTION: Efficiency adjustment for condensing boiler with underfloor heating (gas vs oil/lpg)
	TYPE: type
	UNIT: unit
	SAP: Table 4c (temperature limited to 35℃ row).
	SET: context.energy-constants
	ID: condensing-underfloor-adjustment
	CODSIEB
	*/
	@ConstantDescription("Efficiency adjustment for condensing boiler with underfloor heating (gas vs oil/lpg)")
	CONDENSING_UNDERFLOOR_HEATING(3/100.0, 2/100.0),
	
	/*
	BEISDOC
	NAME: Condensing weather compensation
	DESCRIPTION: Efficiency adjustment for condensing boiler with weather or enhanced load compensator
	TYPE: value
	UNIT: Dimenionless
	SAP: Table 4c
	SET: context.energy-constants
	NOTES: TODO SAP specifies this as "from database". I'm not sure where the numbers we have now came from.
	ID: condensing-weather-compensation
	CODSIEB
	*/
	@ConstantDescription("Efficiency adjustment for condensing boiler with weather or enhanced load compensator")
	CONDENSING_ADVANCED_COMPENSATOR(3/100.0, 1.5/100.0),
	
	/*
	BEISDOC
	NAME: Boiler Without Interlock
	DESCRIPTION: Efficiency adjustment for regular & combi boilers when there is no interlock or thermostat (for combi boilers this applies only to space heat efficiency)
	TYPE: value
	UNIT: Dimenionless
	SAP: Table 4c
	SET: context.energy-constants
	NOTES: Boiler interlock is defined as having both room temperature thermostatically controlled and a cylinder thermostat (assuming there is a cylinder present).
	ID: boiler-without-interlock
	CODSIEB
	*/
	@ConstantDescription("Efficiency adjustment for regular & combi boilers when there is no interlock or thermostat (combi boilers apply only to space heat efficiency)")
	BOILER_WITHOUT_INTERLOCK(-5/100.0)
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
