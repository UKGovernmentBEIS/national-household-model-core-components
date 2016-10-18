package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Miscellaneous Energy Calculator constants.")
public enum EnergyCalculatorConstants implements IConstant {
	@ConstantDescription("This converts from M3/hr of air changes to W/degree temperature difference.")
	VENTILATION_HEAT_LOSS_COEFFICIENT(0.33), 
	@ConstantDescription("This is the number of extra air changes per hour caused by storeys of the house above the ground floor")
	STACK_EFFECT_AIR_CHANGE_PARAMETER(0.1),
	@ConstantDescription("This is the number of air changes per hour caused by not having a draught lobby (double front doors)")
	DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER(0.05), 
	@ConstantDescription("The wind exposure factor is divided by this number")
	WIND_FACTOR_DIVISOR(4.0), 
	@ConstantDescription("This is the amount below demand temperature a fully unresponsive heating system is assumed to provide.")
	UNRESPONSIVE_HEATING_SYSTEM_DELTA_T(2.0), 
	@ConstantDescription("The gains per occupant, in watts due to metabolism")
	METABOLIC_GAINS_PER_PERSON(60.0),
	@ConstantDescription("The gains per occupant due to evaporation, in watts")
	EVAPORATION_GAINS_PER_PERSON(40.0),
	@ConstantDescription("The thermal bridging coefficient used to estimate the thermal bridge contribution to heat loss parameter, for buildings which are older than the improvement year")
	OLD_THERMAL_BRIDGING_COEFFICIENT(0.15), 
	@ConstantDescription("The thermal bridging coefficient used to estimate the thermal bridge contribution for buildings which are built in or after the improvement year")
	NEW_THERMAL_BRIDGING_COEFFICIENT(0.15/4.0), 
	@ConstantDescription("The year in which the thermal bridging coefficient switches from OLD_THERMAL_BRIDGING_COEFFICIENT to NEW_THERMAL_BRIDGING_COEFFICIENT")
	THERMAL_BRIDING_COEFFICIENT_IMPROVEMENT_YEAR(2003), 
	@ConstantDescription("The multipliers for OvershadingType which affect the degree of solar gains.")
	SOLAR_GAINS_OVERSHADING(new double[] {1.0, 1.0, 0.77, 0.54, 0.3}), 
	@ConstantDescription("The reflection factor in solar gains (how much solar gain is lost because of reflectivity)")
	SOLAR_GAINS_REFLECTION_FACTOR(0.9), 
	@ConstantDescription("The value to which the heat loss parameter is compared when determining the demand temperature in zone 2 (depending on heating system control parameter)")
	REFERENCE_HEAT_LOSS_PARAMETER(6.0),
	@ConstantDescription("The heat loss parameter multiplier used in calculating the time constant")
	TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER(3.6),
	@ConstantDescription("The minimum cooling time, for average temperature calculation")
	MINIMUM_COOLING_TIME(4.0),
	@ConstantDescription("Cooling time time constant multiplier, for average temperature calculation")
	COOLING_TIME_TIME_CONSTANT_MULTIPLIER(0.25),
	@ConstantDescription("Divisor of time constant for utilisation factor calculation")
	UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR(15.0),
	@ConstantDescription("Threshold degree days value, used for determining heating on factor")
	THRESHOLD_DEGREE_DAYS_VALUE(5.0), 
	
	@ConstantDescription("The amount below the demand temperature the threshold temperature is for gains utilisation factor calculation")
	GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE(4.5), 
	@ConstantDescription("The air change rate due to doors and windows for a house where none of the doors and windows are draught stripped")
	WINDOW_INFILTRATION(0.25), 
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
