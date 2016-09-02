package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants determining the peak/offpeak charging for electricity, for different devices.")
public enum SplitRateConstants implements IConstant {
	/*
	BEISDOC
	NAME: Default Split Rate
	DESCRIPTION: The high rate fraction for all default appliances
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12a (row for 'All other, and locally generated electricity')
	SET: context.energy-constants
	ID: default-split-rate
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for all default appliances (from SAP 2009 table 12a)")
	DEFAULT_FRACTIONS(0.9, 0.8),
	
	/*
	BEISDOC
	NAME: Direct Electric Space Heater Split Rate 
	DESCRIPTION: The high rate fraction for a direct electric space heater
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12a (row for 'Other direct-acting electric heating')
	SET: context.energy-constants
	ID: direct-electric-split-rate
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for a direct electric space heater (SAP 2009 table 12a)")
	DIRECT_ELECTRIC_FRACTIONS(1, 0.5),
	
	/*
	BEISDOC
	NAME: Electrict Boiler Split Rate 
	DESCRIPTION: The high rate fraction for all default appliances
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12a (row for 'Direct acting electric boiler')
	SET: context.energy-constants
	ID: electric-boiler-split-rate
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for an electric boiler (SAP 2009 12a)")
	ELECTRIC_BOILER_FRACTIONS(0.9, 0.5),
	
	/*
	BEISDOC
	NAME: Dual-coil Immersion split-rate terms
	DESCRIPTION: The terms in the split-rate equation for dual coil immersion heaters, for 7-hour tarriff and 10-hour tarriff. The equation looks like (A - B * v) * N + C - D * v.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 13 (footnote 2)
	SET: context.energy-constants
	CONVERSION: conversion
	ID: dual-coil-immersion-split-rate-terms
	CODSIEB
	*/
	@ConstantDescription("The first term in the split-rate equation for a dual-coil immersion heater, for "
			+ "7-hour tarriff and 10-hour tarriff. The equation looks like (A - B * v) * N + C - D * v. See SAP Table 13.")
	DUAL_IMMERSION_TERM1(6.8, 6.8),
	DUAL_IMMERSION_TERM2(0.024, 0.036),
	DUAL_IMMERSION_TERM3(14, 14),
	DUAL_IMMERSION_TERM4(0.07, 0.105),
	
	/*
	BEISDOC
	NAME: Single-coil Immersion split-rate terms
	DESCRIPTION: The terms in the split-rate equation for single coil immersion heaters, for 7-hour tariff and 10-hour tariff. The equation looks like (A - B * N) / (C * v) - D + E * N.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 13 (footnote 2)
	SET: context.energy-constants
	CONVERSION: conversion
	ID: single-coil-immersion-split-rate-terms
	CODSIEB
	*/
	@ConstantDescription("The first term in the split-rate equation for a single-coil immersion heater, for "
			+"7 hour tarriff and 10-hour tarriff. The equation looks like the one for dual coil. See SAP Table 13.")
	SINGLE_IMMERSION_TERM1(14530.0, 14530.0),
	SINGLE_IMMERSION_TERM2(762.0, 762.0),
	SINGLE_IMMERSION_TERM3(1.0, 1.5),
	SINGLE_IMMERSION_TERM4(80, 80),
	SINGLE_IMMERSION_TERM5(10, 10), 
	
	/*
	BEISDOC
	NAME: Integrated Storage Heater Split Rate
	DESCRIPTION: The high rate fraction for integrated storage heaters.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12a (integrated systems)
	SET: context.energy-constants
	ID: integrated-storage-split-rate
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for integrated storage heaters.")
	INTEGRATED_STORAGE_HEATER_FRACTIONS(0.2, 0.2),

	/*
	BEISDOC
	NAME: Ground Source Space Heating Split Rate
	DESCRIPTION: The high rate fraction for a space-heating ground source heat pump
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12a (Ground/water source heat pump, not calculated by Appendix N)
	SET: context.energy-constants
	ID: gshp-split-rate 
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for a space-heating ground source heat pump")
	GROUND_SOURCE_SPACE_HEAT(0.7, 0.6),
	
	/*
	BEISDOC
	NAME: Air Source Space Heating Split Rate
	DESCRIPTION: The high rate fraction for a space-heating air source heat pump
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12a (Air source heat pump, not calcualted by Appendix N)
	SET: context.energy-constants
	ID: ashp-split-rate 
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for a space-heating air source heat pump")
	AIR_SOURCE_SPACE_HEAT(0.9, 0.6), 
	
	/*
	BEISDOC
	NAME: Heat Pump Water Heating With Immersion Split Rate
	DESCRIPTION: The high rate fraction for a heat pump providing DHW with an off-peak immersion heater
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12c (both 'water heating with off-peak immersion' rows)
	SET: context.energy-constants
	ID: heat-pump-water-with-immersion-split-rate
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for a heat pump providing DHW with an off-peak immersion heater")
	HEAT_PUMP_DHW_WITH_IMMERSION_HEATER(0.17, 0.17),
	
	/*
	BEISDOC
	NAME: Heat Pump Water Heating Without Immersion Split Rate
	DESCRIPTION: The high rate fraction for a heat pump providing DHW without an off-peak immersion heater
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 12c (both 'water heating without immersion' rows)
	SET: context.energy-constants
	ID: heat-pump-water-without-immersion-split-rate
	CODSIEB
	*/
	@ConstantDescription("The high rate fraction for a heat pump providing DHW without an off-peak immersion heater")
	HEAT_PUMP_DHW_WITHOUT_IMMERSION_HEATER(0.7, 0.7);
	
	;
	
	private final double[] values;
	
	SplitRateConstants(final double economySeven, final double economyTen) {
		this.values = new double[] {1, economySeven, economyTen};
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
