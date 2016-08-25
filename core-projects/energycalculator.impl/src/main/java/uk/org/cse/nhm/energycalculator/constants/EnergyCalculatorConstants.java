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
	NAME: Thermal Briding Coefficient
	DESCRIPTION: The thermal bridging coefficient used to estimate the thermal bridge contribution to heat loss parameter
	TYPE: value
	UNIT: W/℃/m^2
	SAP: (36)
	BREDEM: 3A
	DEPS: 
	GET: 
	SET: context.energy-calculator-constants
	ID: thermal-bridging-coefficient
	CODSIEB
	*/

	@ConstantDescription("The thermal bridging coefficient used to estimate the thermal bridge contribution to heat loss parameter, for buildings which are older than the improvement year")
	OLD_THERMAL_BRIDGING_COEFFICIENT(0.15), 
	@ConstantDescription("The thermal bridging coefficient used to estimate the thermal bridge contribution for buildings which are built in or after the improvement year")
	NEW_THERMAL_BRIDGING_COEFFICIENT(0.15/4.0), 
	@ConstantDescription("The year in which the thermal bridging coefficient switches from OLD_THERMAL_BRIDGING_COEFFICIENT to NEW_THERMAL_BRIDGING_COEFFICIENT")
	THERMAL_BRIDING_COEFFICIENT_IMPROVEMENT_YEAR(2003),
	
	/*
	BEISDOC
	NAME: Overshading factor
	DESCRIPTION: A constant multiplier due to overshading which reduces solar gains.
	TYPE: 5 values (one for each overshading type)
	UNIT: Dimensionless
	SAP: Table 6d (winter solar access factor column)
	BREDEM: Table 23 
	SET: context.energy-constants
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
	
	@ConstantDescription("Threshold degree days value, used for determining heating on factor")
	THRESHOLD_DEGREE_DAYS_VALUE(5.0), 
	
	@ConstantDescription("The amount below the demand temperature the threshold temperature is for gains utilisation factor calculation")
	GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE(4.5),
	
	/*
	BEISDOC
	NAME: Base window infiltration
	DESCRIPTION: Air change rate due to doors and windows for a house where none of the doors and windows are draught stripped
	TYPE: value
	UNIT: ach/h
	SAP: (15)
	BREDEM: Not applicable
	DEPS: 
	GET: 
	SET: context.energy-calculator-constants
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
	BREDEM: Not applicable
	DEPS: 
	GET: 
	SET: context.energy-calculator-constants
	ID: draught-stripped-factor-constant
	CODSIEB
	*/
	@ConstantDescription("The reduction in air change rate due to doors and windows for a house where all of the doors and windows are draught stripped")
	DRAUGHT_STRIPPED_FACTOR(0.2), 
	@ConstantDescription("The reduction in effective local wind speed due to having 1 sheltered side")
	SHELTERED_SIDES_EXPOSURE_FACTOR(0.075),
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
