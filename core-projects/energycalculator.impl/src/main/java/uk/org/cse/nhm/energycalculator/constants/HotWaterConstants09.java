package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants controlling the hot water demand and hot water energy content.")
public enum HotWaterConstants09 implements IConstant {
	
	/*
	BEISDOC
	NAME: Base Hot Water Demand
	DESCRIPTION: The volume of hot water used independent of the number of people in the house
	TYPE: value
	UNIT: litres
	SAP: (43)
	SET: context.energy-constants
	ID: base-hot-water-demand
	CODSIEB
	*/
	@ConstantDescription("The volume of hot water used independent of the number of people in the house (liters)")
	BASE_VOLUME(36.0),
	
	/*
	BEISDOC
	NAME: Per Person Hot Water Demand
	DESCRIPTION: The volume of additional hot water use per person
	TYPE: value
	UNIT: litres/person
	SAP: (43)
	SET: context.energy-constants
	ID: person-hot-water-demand
	CODSIEB
	*/
	@ConstantDescription("The volume of additional hot water use per person (liters per person)")
	PERSON_DEPENDENT_VOLUME(25.0),
	
	
	/*
	BEISDOC
	NAME: Energy per unit hot water
	DESCRIPTION: The power required to heat a volume of water by a degree.
	TYPE: value
	UNIT: W/l/℃
	SAP: (45,46)
	BREDEM: 2.1H, 2.2A
	SET: context.energy-constants
	CONVERSION: kJ/day to Watts (J/s) multiply through by (1000 / (24 * 60 * 60))
	NOTES: This value excludes the distribution losses (which SAP and BREDEM treat as part of demand). It is therefore 85% of the SAP or BREDEM value.
	ID: energy-per-hot-water
	CODSIEB
	*/
	// in SAP we have 4.18 * volume * days * delta t / 3600 kWh/month
	// 				  ^
	//        specific heat
	//        kJ/(l . kelvin)      l		d		k
	// = kJ days / 3600 ---> kWh
	// however, in our energy calculator the days live elsewhere and everything is in watts
	// so we want just 4.18* volume/day * delta T 
	// which is kJ / (l . kelvin) * l/day * kelvin
	// which is kJ/day
	// 4.18 kJ/day = 0.048495 etc watts
	// then we multiply by 0.85 because 0.15 * that is distribution losses
	// so 0.85 * that is not distribution losses (i.e. actual demand)
	
	@ConstantDescription("The energy in watts per unit volume of water degree temperature rise (watts)")
	ENERGY_PER_VOLUME(0.04849537037 * 0.85),
	
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
	SET: context.energy-constants
	ID: monthly-water-temperature-factor
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
