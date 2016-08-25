package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants controlling the hot water demand and hot water energy content.")
public enum HotWaterConstants09 implements IConstant {
	@ConstantDescription("The volume of hot water used independent of the number of people in the house (liters)")
	BASE_VOLUME(36.0),
	@ConstantDescription("The volume of additional hot water use per person (liters per person)")
	PERSON_DEPENDENT_VOLUME(25.0),
	
	// in SAP we have 4.190 * volume * days * delta t / 3600 kWh/month
	// 				  ^
	//        specific heat
	//        kJ/(l . kelvin)      l		d		k
	// = kJ days / 3600 ---> kWh
	// however, in our energy calculator the days live elsewhere and everything is in watts
	// so we want just 4.190 * volume/day * delta T 
	// which is kJ / (l . kelvin) * l/day * kelvin
	// which is kJ/day
	// 4.190 kJ/day = 0.04849 etc watts
	// then we multiply by 0.85 because 0.15 * that is distribution losses
	// so 0.85 * that is not distribution losses (i.e. actual demand)
	
	@ConstantDescription("The energy in watts per unit volume of water degree temperature rise (watts)")
	ENERGY_PER_VOLUME(0.048494594 * 0.85),
	
	/*
	BEISDOC
	NAME: Hot water usage factor
	DESCRIPTION: The hot water usage factor, by month of the year - this is used to scale the demand for hot water
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 1c
	BREDEM: Table 7
	DEPS: 
	GET:
	SET: context.energy-constants
	ID: monthly-water-usage-factor
	CODSIEB
	*/
	@ConstantDescription("The hot water usage factor, by month of the year - this is used to scale the demand for hot water")
	USAGE_FACTOR(1.10, 1.06, 1.02, 0.98,	0.94, 0.90, 0.90, 0.94, 0.98, 1.02, 1.06, 1.10),
	
	/*
	BEISDOC
	NAME: Hot water temperature factor
	DESCRIPTION: The hot water temperature rise required, by month of the year
	TYPE: value
	UNIT: ℃
	SAP: Table 1d
	BREDEM: Table 8
	DEPS: deps
	GET: get
	SET: set
	ID: id
	CODSIEB
	*/

	@ConstantDescription("The hot water temperature rise required, by month of the year (kelvin)")
	RISE_TEMPERATURE(41.2, 41.4, 40.1, 37.6,	36.4, 33.9, 30.4, 33.4, 33.5, 36.3, 39.4, 39.9)
	;

	private final double[] values;
	
	HotWaterConstants09(final double... values) {
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
