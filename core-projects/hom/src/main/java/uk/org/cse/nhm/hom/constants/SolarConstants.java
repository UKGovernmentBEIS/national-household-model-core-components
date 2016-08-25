package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants for solar DHW systems")
public enum SolarConstants implements IConstant {
	@ConstantDescription("Minimum volume factor")
	MINIMUM_VOLUME_FACTOR(1.0),
	@ConstantDescription("Volume factor coefficient term")
	VOLUME_FACTOR_COEFFICIENT(0.2),
	@ConstantDescription("Amount of cylinder above secondary coil (used by solar heater)")
	CYLINDER_REMAINDER_FACTOR(0.3),
	@ConstantDescription("Threshold for linear heatloss coefficient / zero loss efficiency to use lower expansion coefficients")
	HLC_OVER_E_THRESHOLD(20.0),
	@ConstantDescription("Coefficients for quadratic model of collector performance factor when HLC/e is below the threshold")
	LOW_HLC_EXPANSION(0.97, -0.0367, 0.0006),
	@ConstantDescription("Coefficients for quadratic model of collector performance factor when HLC/e is above or equal to the threshold")
	HIGH_HLC_EXPANSION(0.693, -0.0108, 0),
	@ConstantDescription("Solar overshading factor by overshading type")
	OVERSHADING_FACTOR(1, 1, 0.9, 0.8, 0.5), // none, very little, average, above average, heavy - adjusted to match CHM on "average"
	@ConstantDescription("Solar utilisation factor is multiplied by this if there is no tank thermostat")
	UTILISATION_FACTOR_THERMOSTAT_FACTOR(0.9), 
	
	/*
	BEISDOC
	NAME: Solar water pump power
	DESCRIPTION: The average wattage of the circulation pump for a solar water heater, assuming it is not PV powered
	TYPE: value
	UNIT: W
	SAP: Table 4f
	BREDEM: Table 4
	SET: context.energy-constants
	CONVERSION: From kWh/year to W, multiply by (hours/year) and divide by k; (365.25 * 24) / 1000
	ID: solar-water-pump-power
	CODSIEB
	*/
	@ConstantDescription("The average wattage of the circulation pump")
	CIRCULATION_PUMP_WATTAGE(5.70) // this is 50 kWh/year in watts.
	;
	
	private final double[] values;
	
	SolarConstants(final double... values) {
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
