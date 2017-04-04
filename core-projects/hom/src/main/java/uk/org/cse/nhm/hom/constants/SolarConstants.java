package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants for solar DHW systems")
public enum SolarConstants implements IConstant {
	/*
	BEISDOC
	NAME: Solar Minimum Volume Factor
	DESCRIPTION: Minimum volume factor for solar hot water calculation
	TYPE: value
	UNIT: Dimensionless
	SAP: (H16)
	BREDEM: 2.4.2E
	SET: context.energy-constants
	ID: solar-min-volume-factor
	CODSIEB
	*/
	@ConstantDescription("Minimum volume factor")
	MINIMUM_VOLUME_FACTOR(1.0),

	/*
	BEISDOC
	NAME: Solar Volume Factor Coefficient
	DESCRIPTION: Volume factor coefficient term
	TYPE: value
	UNIT: dimensionless
	SAP: (H16)
	BREDEM: 2.4.2E
	SET: context.energy-constants
	ID: solar-volume-factor-coefficient
	CODSIEB
	*/
	@ConstantDescription("Volume factor coefficient term")
	VOLUME_FACTOR_COEFFICIENT(0.2),
	
	/*
	BEISDOC
	NAME: Solar Cylinder Remainder
	DESCRIPTION: Amount of cylinder above secondary coil (used by solar heater)
	TYPE: value
	UNIT: Dimensionless
	SAP: (H13)
	BREDEM: 2.4.2 (footnote)
	SET: context.energy-constants
	ID: solar-cylinder-remainder
	CODSIEB
	*/
	@ConstantDescription("Amount of cylinder above secondary coil (used by solar heater)")
	CYLINDER_REMAINDER_FACTOR(0.3),
	
	/*
	BEISDOC
	NAME: Collector Performance Ratio Threshold
	DESCRIPTION: Threshold for linear heatloss coefficient / zero loss efficiency to use lower expansion coefficients
	TYPE: value
	UNIT: ???
	SAP: (H10)
	BREDEM: 2.4.2B
	SET: context.energy-constants
	ID: collector-performance-ratio-threshold
	CODSIEB
	*/
	@ConstantDescription("Threshold for linear heatloss coefficient / zero loss efficiency to use lower expansion coefficients")
	HLC_OVER_E_THRESHOLD(20.0),
	
	/*
	BEISDOC
	NAME: Heat Loss Factor Lower Terms
	DESCRIPTION: Coefficients for quadratic model of collector performance factor when HLC/e is below the threshold
	TYPE: value
	UNIT: ???
	SAP: (H10)
	BREDEM: 2.4.2B
	SET: context.energy-constants
	ID: collector-performance-factor-lower-terms
	CODSIEB
	*/
	@ConstantDescription("Coefficients for quadratic model of collector performance factor when HLC/e is below the threshold")
	LOW_HLC_EXPANSION(0.97, -0.0367, 0.0006),
	
	/*
	BEISDOC
	NAME: Heat Loss Factor High Terms
	DESCRIPTION: Coefficients for quadratic model of collector performance factor when HLC/e is above or equal to the threshold
	TYPE: value
	UNIT: ???
	SAP: (H10)
	BREDEM: 2.4.2B
	SET: context.energy-constants
	ID: collector-performance-factor-higher-terms
	CODSIEB
	*/
	@ConstantDescription("Coefficients for quadratic model of collector performance factor when HLC/e is above or equal to the threshold")
	HIGH_HLC_EXPANSION(0.693, -0.0108, 0),
	
	/*
	BEISDOC
	NAME: Solar Overshading Factor
	DESCRIPTION: A modification to the amount of solar flux received on a dwelling's roof.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table H2
	BREDEM: Table 18
	SET: context.energy-constants
	ID: solar-overshading-factor
	CODSIEB
	*/
	@ConstantDescription("Solar overshading factor by overshading type")
	OVERSHADING_FACTOR(1, 0.8, 0.65, 0.5), // none or little, average, above average, heavy - adjusted to match CHM on "average"
	
	/*
	BEISDOC
	NAME: Solar Utilisation Factor Thermostat Factor
	DESCRIPTION: Solar utilisation factor is multiplied by this if there is no tank thermostat
	TYPE: value
	UNIT: Dimensionless
	SAP: (H9) note in italics
	SET: context.energy-constants
	ID: solar-utilisation-factor-thermostat-factor
	CODSIEB
	*/
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
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
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
