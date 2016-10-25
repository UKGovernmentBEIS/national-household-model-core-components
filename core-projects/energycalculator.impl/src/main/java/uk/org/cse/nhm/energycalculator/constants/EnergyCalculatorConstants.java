package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Miscellaneous Energy Calculator constants.")
public enum EnergyCalculatorConstants implements IConstant {
	/*
	BEISDOC
	NAME: Ventilation heat loss coefficient
	DESCRIPTION: This converts from M^3/hr of air changes to W/degree temperature difference.
	TYPE: value
	UNIT: W.hr/℃/M^3
	SAP: (38)
	BREDEM: 3G
	DEPS:
	GET:
	SET: context.energy-calculator-constants
	ID: ventilation-heat-loss-coefficient
	CODSIEB
	*/
	@ConstantDescription("This converts from M3/hr of air changes to W/degree temperature difference.")
	VENTILATION_HEAT_LOSS_COEFFICIENT(0.33),
	
	@ConstantDescription("This is the number of extra air changes per hour caused by storeys of the house above the ground floor")
	STACK_EFFECT_AIR_CHANGE_PARAMETER(0.1),

	/*
	BEISDOC
	NAME: Draught lobby air change rate
	DESCRIPTION: The number of air changes per hour reduced by having a draught lobby
	TYPE: constant
	UNIT: ach/h
	SAP: (13)
	BREDEM: Table 19
	DEPS: 
	GET: 
	SET: context.energy-calculator-constants
	ID: draught-lobby-constant
	CODSIEB
	*/
	@ConstantDescription("This is the number of air changes per hour caused by not having a draught lobby (double front doors)")
	DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER(0.05), 
	
	/*
	BEISDOC
	NAME: Wind factor divisor
	DESCRIPTION: The wind exposure factor is divided by this number
	TYPE: value
	UNIT: Dimensionless
	SAP: (22a)
	BREDEM: 3E
	SET: context.energy-constants
	ID: wind-factor-divisor
	CODSIEB
	*/
	@ConstantDescription("The wind exposure factor is divided by this number")
	WIND_FACTOR_DIVISOR(4.0), 
	
	/*
	BEISDOC
	NAME: Unresponsive heating system temperature reduction
	DESCRIPTION: This is the amount below demand temperature a fully unresponsive heating system is assumed to provide.
	TYPE: value
	UNIT: ℃
	SAP: Table 9b
	BREDEM: 7L, 7T
	SET: context.energy-constants
	ID: unresponsive-temperature-reduction
	CODSIEB
	*/
	@ConstantDescription("This is the amount below demand temperature a fully unresponsive heating system is assumed to provide.")
	UNRESPONSIVE_HEATING_SYSTEM_DELTA_T(2.0), 
	
	/*
	BEISDOC
	NAME: Metabolic gains per person
	DESCRIPTION: The heat gain per person due to human metabolism. 
	TYPE: value
	UNIT: W/person
	SAP: (66), Table 5
	BREDEM: 6A
	SET: context.energy-constants
	ID: metabolic-gains-per-person
	CODSIEB
	*/
	@ConstantDescription("The gains per occupant, in watts due to metabolism")
	METABOLIC_GAINS_PER_PERSON(60.0),

	/*
	BEISDOC
	NAME: Reduced Metabolic gains per person
	DESCRIPTION: The heat gain per person due to human metabolism when using reduced internal gains. 
	TYPE: value
	UNIT: W/person
	SAP: Table 5
	SET: context.energy-constants
	ID: metabolic-gains-per-person
	CODSIEB
	*/
	@ConstantDescription("The gains per occupant, in watts due to metabolism")
	REDUCED_METABOLIC_GAINS_PER_PERSON(50.0),
	
	/*
	BEISDOC
	NAME: Evaporation loss per person
	DESCRIPTION: The heat lost from the house due to evaporation per person.
	TYPE: value
	UNIT: W/person
	SAP: (71), Table 5 (losses row)
	BREDEM: 6F
	SET: context.energy-constants
	ID: evaporation-loss-per-person
	CODSIEB
	*/
	@ConstantDescription("The gains per occupant due to evaporation, in watts")
	EVAPORATION_GAINS_PER_PERSON(40.0),
	
	/*
	BEISDOC
	NAME: Overshading factor
	DESCRIPTION: A constant multiplier due to overshading which reduces solar gains.
	TYPE: 5 values (one for each overshading type)
	UNIT: Dimensionless
	SAP: Table 6d (winter solar access factor column)
	BREDEM: Table 23 
	SET: context.energy-constants
	DEPS: overshading
	NOTES: Only the middle overshading factor is ever used.
	ID: overshading-factor
	CODSIEB
	*/
	@ConstantDescription("The multipliers for OvershadingType which affect the degree of solar gains.")
	SOLAR_GAINS_OVERSHADING(new double[] {1.0, 1.0, 0.77, 0.54, 0.3}), 
	
	/*
	BEISDOC
	NAME: Solar gains reflection
	DESCRIPTION: A constant multiplier due to reflection which reduces solar gains. 
	TYPE: value
	UNIT: Dimensionless
	SAP: (74-82)
	BREDEM: 5A
	SET: context.energy-constants
	ID: solar-reflection
	CODSIEB
	*/
	@ConstantDescription("The reflection factor in solar gains (how much solar gain is lost because of reflectivity)")
	SOLAR_GAINS_REFLECTION_FACTOR(0.9),
	
	/*
	BEISDOC
	NAME: Reference Heat Loss Parameter
	DESCRIPTION: The value to which the heat loss parameter is compared when determining the demand temperature in zone 2 (depending on heating system control parameter)
	TYPE: value
	UNIT: W/m^2/℃
	SAP: Table 9 (note at bottom of table)
	BREDEM: 7A,7B
	SET: context.energy-constants
	ID: reference-heat-loss-parameter
	CODSIEB
	*/
	@ConstantDescription("The value to which the heat loss parameter is compared when determining the demand temperature in zone 2 (depending on heating system control parameter)")
	REFERENCE_HEAT_LOSS_PARAMETER(6.0),

	/*
	BEISDOC
	NAME: Time constant heat loss parameter multiplier
	DESCRIPTION: The heat loss parameter multiplier used in calculating the time constant
	TYPE: value
	UNIT: hours/W/m^2/℃
	SAP: Table 9a
	BREDEM: 7F
	SET: context.energy-constants
	ID: time-constant-heat-loss-parameter-multiplier
	CODSIEB
	*/
	@ConstantDescription("The heat loss parameter multiplier used in calculating the time constant")
	TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER(3.6),
	
	/*
	BEISDOC
	NAME: Minimum Cooling Time
	DESCRIPTION: The minimum cooling time, for average temperature calculation
	TYPE: value
	UNIT: Hours
	SAP: Table 9b
	BREDEM: 7F
	SET: context.energy-constants
	ID: mimimum-cooling-time
	CODSIEB
	*/
	@ConstantDescription("The minimum cooling time, for average temperature calculation")
	MINIMUM_COOLING_TIME(4.0),
	
	/*
	BEISDOC
	NAME: Cooling time time constant multiplier
	DESCRIPTION: Cooling time time constant multiplier, for average temperature calculation
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 9b
	BREDEM: 7F
	SET: context.energy-constants
	ID: cooling-time-time-constant-multiplier
	CODSIEB
	*/
	@ConstantDescription("Cooling time time constant multiplier, for average temperature calculation")
	COOLING_TIME_TIME_CONSTANT_MULTIPLIER(0.25),
	
	/*
	BEISDOC
	NAME: Utilisation Factor Time Constant Divisor
	DESCRIPTION: Divisor of time constant for utilisation factor calculation
	TYPE: value
	UNIT: Hours
	SAP: Table 9a
	BREDEM: 7G
	SET: context.energy-constants
	ID: utilisation-factor-time-constant-divisor
	CODSIEB
	*/
	@ConstantDescription("Divisor of time constant for utilisation factor calculation")
	UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR(15.0),
	
	/*
	BEISDOC
	NAME: Threshold degree days value
	DESCRIPTION: Threshold degree days value, used for determining heating on factor
	TYPE: value
	UNIT: 1 / Degree Days
	BREDEM: 8F, 8G
	SET: context.energy-constants
	NOTES: This is the 1/5 which the degree days are set to if the upper and lower threshold temperatures are equal.
	NOTES: It is also the multiple of 5 inside the exponential, when they are not equal. 
	ID: threshold-degree-days-value
	CODSIEB
	*/
	@ConstantDescription("Threshold degree days value, used for determining heating on factor")
	THRESHOLD_DEGREE_DAYS_VALUE(5.0), 
	
	/*
	BEISDOC
	NAME: Gains Utilisation Factor Threshold Difference
	DESCRIPTION: The amount below the demand temperature the threshold temperature is for gains utilisation factor calculation
	TYPE: value
	UNIT: ℃
	BREDEM: 8D
	SET: context.energy-constants
	CONVERSION: This number is increased by 0.5 compared to BREDEM, because we skip out the +0.5 and convert the -0.5 into -1 later on in the calculation.
	ID: gains-utilisation-factor-threshold-difference 
	CODSIEB
	*/
	@ConstantDescription("The amount below the demand temperature the threshold temperature is for gains utilisation factor calculation")
	GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE(4.5),
	
	/*
	BEISDOC
	NAME: Base window infiltration
	DESCRIPTION: Air change rate due to doors and windows for a house where none of the doors and windows are draught stripped
	TYPE: value
	UNIT: ach/h
	SAP: (15)
	DEPS: 
	GET: 
	SET: context.energy-constants
	ID: window-infiltration-constant
	CODSIEB
	*/
	@ConstantDescription("The air change rate due to doors and windows for a house where none of the doors and windows are draught stripped")
	WINDOW_INFILTRATION(0.25), 
	
	/*
	BEISDOC
	NAME: Draught stripped factor
	DESCRIPTION: The reduction in air change rate due to doors and windows for a house where all of the doors and windows are draught stripped
	TYPE: value
	UNIT: ach/h
	SAP: (15)
	DEPS: 
	GET: 
	SET: context.energy-constants
	ID: draught-stripped-factor-constant
	CODSIEB
	*/
	@ConstantDescription("The reduction in air change rate due to doors and windows for a house where all of the doors and windows are draught stripped")
	DRAUGHT_STRIPPED_FACTOR(0.2), 
	
	/*
	BEISDOC
	NAME: Sheltered sides exposure factor
	DESCRIPTION: Proportional reduction in air changes for to each side of the house which is sheltered
	TYPE: value
	UNIT: Dimensionless
	SAP: (20)
	BREDEM: Table 22
	DEPS: 
	GET: 
	SET: context.energy-constants
	ID: sheltered-sides-exposure-factor
	CODSIEB
	*/
	@ConstantDescription("Proportional reduction in air changes for to each side of the house which is sheltered")
	SHELTERED_SIDES_EXPOSURE_FACTOR(0.075),
	
	/*
	BEISDOC
	NAME: Site Exposure Factor Lookup
	DESCRIPTION: The values for the site exposure factor.
	TYPE: value
	UNIT: Dimensionless
	BREDEM: Table 21
	SET: context.energy-constants
	ID: site-exposure-factor-lookup
	CODSIEB
	*/
	// The related categories are in the SiteExposureType enum.
	SITE_EXPOSURE_FACTOR(new double[]{1.10, 1.05, 1.00, 0.95, 0.90}),
	
	@ConstantDescription("Ventilation rate for open flue")
	OPEN_FLUE_VENTILATION_RATE(20.0), 
	
	@ConstantDescription("Ventilation rate for chimney.")
	CHIMNEY_VENTILATION_RATE(40.0)
	;
	
	private double[] multipleValues;
	private double defaultValue;
	
	private EnergyCalculatorConstants(double defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	private EnergyCalculatorConstants(double[] values) {
		defaultValue = 0;
		multipleValues = values;
	}
	
	@Override
	public <T> T getValue(Class<T> clazz) {
		if (Double.class.isAssignableFrom(clazz)) {
			return clazz.cast((Double) defaultValue);
		} else if (double[].class.isAssignableFrom(clazz)) {
			return clazz.cast(multipleValues);
		} else {
			throw new RuntimeException("EnergyCalc constant " + this + " has no representation as a "+ clazz.getSimpleName());
		}
	}
}
