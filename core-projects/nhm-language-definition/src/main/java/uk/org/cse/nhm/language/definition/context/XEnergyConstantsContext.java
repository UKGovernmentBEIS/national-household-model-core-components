package uk.org.cse.nhm.language.definition.context;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.validate.ValidEnergyConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;


@Bind("context.energy-constants")
@Doc(
		{
			"Sets the energy calculator constants to be used within a simulation.",
			"These constants will be used throughout the run, and are mostly ill-documented internals",
			"of the SAP calculation."
		}
	)
@Category(CategoryType.CALIBRATION)
public class XEnergyConstantsContext extends XContextParameter {
	/** Generated by ConstantsTableGenerator */
	@Doc("Defines all of the available energy constants, and their default values")
	
	/** Generated by ConstantsTableGenerator */
	public enum XEnergyConstantType {
		@Doc("Efficiency adjustment for condensing boiler with underfloor heating (gas vs oil/lpg) - default is 0.030, 0.020 ")
		 EfficiencyAdjustments_CONDENSING_UNDERFLOOR_HEATING(2),
		@Doc("Efficiency adjustment for condensing boiler with weather or enhanced load compensator - default is 0.030, 0.015 ")
		 EfficiencyAdjustments_CONDENSING_ADVANCED_COMPENSATOR(2),
		@Doc("Efficiency adjustment for regular and combi boilers when there is no interlock or thermostat (combi boilers apply only to space heat efficiency) - default is -0.050 ")
		 EfficiencyAdjustments_BOILER_WITHOUT_INTERLOCK(1),
		@Doc("The constant term in all loss factor equations - default is 0.208 ")
		 CylinderConstants_LOSS_FACTOR_CONSTANT_TERM(1),
		@Doc("The loss factor equation terms for loose jacket cylinders - default is 73.333, 12.800 ")
		 CylinderConstants_LOOSE_JACKET_FACTORS(2),
		@Doc("The loss factor equation terms for factory jacket cylinders - default is 22.917, 4.000 ")
		 CylinderConstants_FACTORY_JACKET_FACTORS(2),
		@Doc("The terms for the volume factor equation - default is 120.000, 0.333 ")
		 CylinderConstants_VOLUME_FACTOR_TERMS(2),
		@Doc("The storage temperature factor used in most cases - default is 0.600 ")
		 CylinderConstants_TEMPERATURE_FACTOR_BASIC(1),
		@Doc("The storage temperature factor multiplier when there is no thermostat - default is 1.300 ")
		 CylinderConstants_TEMPERATURE_FACTOR_NO_THERMOSTAT_MULTIPLIER(1),
		@Doc("The storage temperature factor used when there is a separate hot water timer - default is 0.900 ")
		 CylinderConstants_TEMPERATURE_FACTOR_SEPARATE_HW_TIMER(1),
		@Doc("The storage temperature factor terms for a combi with store in primary circuit - default is 0.820, 0.002, 115.000 ")
		 CylinderConstants_TEMPERATURE_FACTOR_PRIMARY_STORAGE_COMBI(3),
		@Doc("The STF terms for a combi with store in secondary circuit - default is 0.600, 0.002, 115.000 ")
		 CylinderConstants_TEMPERATURE_FACTOR_SECONDARY_STORAGE_COMBI(3),
		@Doc("The STF for a gas CPSU - default is 1.080 ")
		 CylinderConstants_TEMPERATURE_FACTOR_GAS_CPSU(1),
		@Doc("The storage temperature factor for an electric CPSU. - default is 1.000 ")
		 CylinderConstants_TEMPERATURE_FACTOR_ELECTRIC_CPSU(1),
		@Doc("The high rate fraction for all default appliances (from SAP 2009 table 12a) - default is 1.000, 0.900, 0.800 ")
		 SplitRateConstants_DEFAULT_FRACTIONS(3),
		@Doc("The high rate fraction for a direct electric space heater (SAP 2009 table 12a) - default is 1.000, 1.000, 0.500 ")
		 SplitRateConstants_DIRECT_ELECTRIC_FRACTIONS(3),
		@Doc("The high rate fraction for an electric boiler (SAP 2009 12a) - default is 1.000, 0.900, 0.500 ")
		 SplitRateConstants_ELECTRIC_BOILER_FRACTIONS(3),
		@Doc("The first term in the split-rate equation for a dual-coil immersion heater, for 7-hour tarriff and 10-hour tarriff. The equation looks like (A - B * v) * N + C - D * v. See SAP Table 13. - default is 1.000, 6.800, 6.800 ")
		 SplitRateConstants_DUAL_IMMERSION_TERM1(3),
		@Doc("no description - default is 1.000, 0.024, 0.036 ")
		 SplitRateConstants_DUAL_IMMERSION_TERM2(3),
		@Doc("no description - default is 1.000, 14.000, 14.000 ")
		 SplitRateConstants_DUAL_IMMERSION_TERM3(3),
		@Doc("no description - default is 1.000, 0.070, 0.105 ")
		 SplitRateConstants_DUAL_IMMERSION_TERM4(3),
		@Doc("The first term in the split-rate equation for a single-coil immersion heater, for 7 hour tarriff and 10-hour tarriff. The equation looks like the one for dual coil. See SAP Table 13. - default is 1.000, 14530.000, 14530.000 ")
		 SplitRateConstants_SINGLE_IMMERSION_TERM1(3),
		@Doc("no description - default is 1.000, 762.000, 762.000 ")
		 SplitRateConstants_SINGLE_IMMERSION_TERM2(3),
		@Doc("no description - default is 1.000, 1.000, 1.500 ")
		 SplitRateConstants_SINGLE_IMMERSION_TERM3(3),
		@Doc("no description - default is 1.000, 80.000, 80.000 ")
		 SplitRateConstants_SINGLE_IMMERSION_TERM4(3),
		@Doc("no description - default is 1.000, 10.000, 10.000 ")
		 SplitRateConstants_SINGLE_IMMERSION_TERM5(3),
		@Doc("The high rate fraction for integrated storage heaters. - default is 1.000, 0.200, 0.200 ")
		 SplitRateConstants_INTEGRATED_STORAGE_HEATER_FRACTIONS(3),
		@Doc("The high rate fraction for a space-heating ground source heat pump - default is 1.000, 0.700, 0.600 ")
		 SplitRateConstants_GROUND_SOURCE_SPACE_HEAT(3),
		@Doc("The high rate fraction for a space-heating air source heat pump - default is 1.000, 0.900, 0.600 ")
		 SplitRateConstants_AIR_SOURCE_SPACE_HEAT(3),
		@Doc("The high rate fraction for a heat pump providing DHW with an off-peak immersion heater - default is 1.000, 0.170, 0.170 ")
		 SplitRateConstants_HEAT_PUMP_DHW_WITH_IMMERSION_HEATER(3),
		@Doc("The high rate fraction for a heat pump providing DHW without an off-peak immersion heater - default is 1.000, 0.700, 0.700 ")
		 SplitRateConstants_HEAT_PUMP_DHW_WITHOUT_IMMERSION_HEATER(3),
		@Doc("The number '14' which is used as a multiplier in the primary pipework losses calculation (converted to W from kWh/day). - default is 583.330 ")
		 HeatingSystemConstants_PRIMARY_PIPEWORK_COEFFICIENT(1),
		@Doc("The value which is multiplied by pipework insulated fraction in the primary pipework losses calculation. - default is 0.009 ")
		 HeatingSystemConstants_PRIMARY_PIPEWORK_INSULATED_MULTIPLIER(1),
		@Doc("The value which is multiplied by (1 - pipework insulated fraction) in the primary pipework losses calculation. - default is 0.025 ")
		 HeatingSystemConstants_PRIMARY_PIPEWORK_UNINSULATED_MULTIPLIER(1),
		@Doc("The final constant term in the primary pipework losses calculation. - default is 0.026 ")
		 HeatingSystemConstants_PRIMARY_PIPEWORK_CONSTANT(1),
		@Doc("The number of hours per day the primary pipework is hot (a) in winter without a cylinder thermostat, (b) in winter without a separate water heating timer and (c) otherwise. - default is 11.000, 5.000, 3.000 ")
		 HeatingSystemConstants_HOURS_PIPEWORK_HOT(3),
		@Doc("Distribution losses from a centrally heated system, as a proportion of hot water energy - default is 0.176 ")
		 HeatingSystemConstants_CENTRAL_HEATING_DISTRIBUTION_LOSSES(1),
		@Doc("Solar primary pipework correction factor (by month) - default is 1.000, 1.000, 0.940, 0.700, 0.450, 0.440, 0.440, 0.480, 0.760, 0.940, 1.000, 1.000 ")
		 HeatingSystemConstants_CENTRAL_HEATING_SOLAR_PPCF(12),
		@Doc("The proportion of hot water energy that is used showering (for electric showers) - default is 0.348 ")
		 HeatingSystemConstants_SHOWER_DEMAND_PROPORTION(1),
		@Doc("The proportion of hot water volume that is consumed by electric showers - default is 0.250 ")
		 HeatingSystemConstants_SHOWER_VOLUME_PROPORTION(1),
		@Doc("Electric CPSU low-rate heat constant - default is 0.146 ")
		 HeatingSystemConstants_ELECTRIC_CPSU_LOW_RATE_HEAT_CONSTANT(1),
		@Doc("Electric CPSU temperature offset (SAP appendix F) - default is 48.000 ")
		 HeatingSystemConstants_ELECTRIC_CPSU_WINTER_TEMPERATURE_OFFSET(1),
		@Doc("Combi additional loss factor daily hot water usage limit (SAP 2009 table 3a) - default is 100.000 ")
		 HeatingSystemConstants_COMBI_HOT_WATER_USAGE_LIMIT(1),
		@Doc("Instantaneous combi without KHF loss factor (SAP 2009 table 3a, row 1, converted from kWh/year to watts) - default is 68.448 ")
		 HeatingSystemConstants_INSTANTANEOUS_COMBI_FACTOR(1),
		@Doc("The threshold volume above which a storage combi boiler has no additional losses (SAP 2009 table 3a) - default is 55.000 ")
		 HeatingSystemConstants_STORAGE_COMBI_VOLUME_THRESHOLD(1),
		@Doc("The additional wattage of combi losses when using a combi with a keep hot facility with a timeclock (SAP 2009 table 3a, converted to watts) - default is 68.448 ")
		 HeatingSystemConstants_INSTANTANEOUS_COMBI_FACTOR_KHF_WITH_TIMECLOCK(1),
		@Doc("The additional wattage of combi losses when using a combi with a keep hot facility without a timeclock (SAP 2009 table 3a, converted to watts) - default is 102.671 ")
		 HeatingSystemConstants_INSTANTANEOUS_COMBI_FACTOR_KHF_WITHOUT_TIMECLOCK(1),
		@Doc("The terms from the additional wattage equation for storage combis with store volume under 55l in SAP 2009 table 3a (in SAP this is fu * (600 - (volume - 15) * 15) - these values are in the same order here) - default is 68.448, 15.000, 1.711 ")
		 HeatingSystemConstants_STORAGE_COMBI_LOSS_TERMS(3),
		@Doc("In the lighting demand equation A*(floor area * occupants) ^ B, this is B - default is 0.471 ")
		 LightingConstants09_LIGHT_DEMAND_EXPONENT(1),
		@Doc("The maximum value for the daylight saving coefficient - default is 0.095 ")
		 LightingConstants09_DAYLIGHT_PARAMETER_MAXIMUM(1),
		@Doc("The daylight saving coefficient, if below the threshold, is computed as Ax^2 + Bx + C; this is C - default is 1.433 ")
		 LightingConstants09_DAYLIGHT_PARAMETER_0_COEFFICIENT(1),
		@Doc("The daylight saving coefficient, if below the threshold, is computed as Ax^2 + Bx + C; this is B - default is -9.940 ")
		 LightingConstants09_DAYLIGHT_PARAMETER_1_COEFFICIENT(1),
		@Doc("The daylight saving coefficient, if below the threshold, is computed as Ax^2 + Bx + C; this is A - default is 52.200 ")
		 LightingConstants09_DAYLIGHT_PARAMETER_2_COEFFICIENT(1),
		@Doc("This is the mean loss factor for overshading (an array) - default is 1.000, 0.830, 0.670, 0.500 ")
		 LightingConstants09_OVERSHADING_ACCESS_FACTORS(4),
		@Doc("In the lighting monthly adjustment equation a + b * cos( 2 pi * (month - c) / 12, these are a, b, and c - default is 1.000, 0.500, 0.200 ")
		 LightingConstants09_ADJUSTMENT_FACTOR_TERMS(3),
		@Doc("Primary pipework losses for community heating system - default is 41.068 ")
		 CommunityHeatingConstants_PRIMARY_PIPEWORK_LOSSES(1),
		@Doc("Demand temperature adjustment for 2301, 2302 - default is 0.300 ")
		 CommunityHeatingConstants_DEMAND_TEMPERATURE_ADJUSTMENT(1),
		@Doc("The space energy multiplier in SAP table 4c(3) for system codes 2301, 2302 - default is 1.100 ")
		 CommunityHeatingConstants_HIGH_SPACE_USAGE_MULTIPLER(1),
		@Doc("The space energy multiplier in SAP table 4c(3) for system codes 2303, 2304, 2307, 2305, 2308, 2309 - default is 1.050 ")
		 CommunityHeatingConstants_MEDIUM_SPACE_USAGE_MULTIPLER(1),
		@Doc("The space energy multiplier in SAP table 4c(3) for system code 2310, 2306 - default is 1.000 ")
		 CommunityHeatingConstants_LOW_SPACE_USAGE_MULTIPLIER(1),
		@Doc("The hot water energy multiplier in SAP table 4c(3) for systems 2301, 2302, 2303, 2304, 2307, 2305, and community DHW only with flat-rate charging - default is 1.050 ")
		 CommunityHeatingConstants_HIGH_WATER_USAGE_MULTIPLIER(1),
		@Doc("The hot water energy multiplier in SAP table 4c(3) for systems 2308, 2309, 2310, 2306 - default is 1.000 ")
		 CommunityHeatingConstants_LOW_WATER_USAGE_MULTIPLIER(1),
		@Doc("The community heat distribution loss factor to be used if there is no information (appendix C3.1) - adjusted to match CHM's 1.1 - default is 1.100 ")
		 CommunityHeatingConstants_DEFAULT_DISTRIBUTION_LOSS_FACTOR(1),
		@Doc("The demand temperature adjustment for boiler systems with no thermostatic controls (top two rows of T4e Group 1) - default is 0.600 ")
		 TemperatureAdjustments_BOILER_NO_THERMOSTAT(1),
		@Doc("The demand temperature adjustment for boiler systems with a delayed start thermostat - default is -0.150 ")
		 TemperatureAdjustments_BOILER_DELAYED_START_THERMOSTAT(1),
		@Doc("The demand temperature adjustment for systems with a CPSU or integrated thermal store - default is -0.100 ")
		 TemperatureAdjustments_CPSU_OR_INTEGRATED_THERMAL_STORE(1),
		@Doc("Demand temperature adjustment by storage heater control type (manual, automatic, celect) - default is 0.700, 0.400, 0.400 ")
		 TemperatureAdjustments_STORAGE_HEATER(3),
		@Doc("Demand temperature adjustment for non-thermostatic room heaters - default is 0.300 ")
		 TemperatureAdjustments_ROOM_HEATER_NO_THERMOSTAT(1),
		@Doc("Demand temperature adjustment for non-thermostatic warm air systems - default is 0.300 ")
		 TemperatureAdjustments_WARM_AIR_SYSTEM_NO_THERMOSTAT(1),
		@Doc("Demand temperature adjustment for non-thermostatic warm air systems - default is 0.300 ")
		 TemperatureAdjustments_HEAT_PUMP_NO_THERMOSTAT(1),
		@Doc("The wattage of the central heating circulation pump - this is from SAP table 4f, converted into watts and assigned over the 9 heating months equally - default is 13.690 ")
		 PumpAndFanConstants_CENTRAL_HEATING_PUMP_WATTAGE(1),
		@Doc("The gains of the central heating circulation pump - this is from SAP table 5a - default is 10.000 ")
		 PumpAndFanConstants_CENTRAL_HEATING_PUMP_GAINS(1),
		@Doc("The wattage of oil boiler fuel pump - from SAP table 4f, converted into watts and distributed over the 9 heating months - default is 11.410 ")
		 PumpAndFanConstants_OIL_FUEL_PUMP_WATTAGE(1),
		@Doc("The gains from oil boiler fuel pump - from SAP table 5a - default is 10.000 ")
		 PumpAndFanConstants_OIL_FUEL_PUMP_GAINS(1),
		@Doc("The wattage of gas boiler or gas heat pump flue fan - from SAP table 4f, converted into watts and distributed over the year - default is 5.134 ")
		 PumpAndFanConstants_GAS_BOILER_FLUE_FAN_WATTAGE(1),
		@Doc("The wattage of gas heat pump flue fan - from SAP table 4f, converted into watts and distributed over the year - default is 5.134 ")
		 PumpAndFanConstants_GAS_HEAT_PUMP_FLUE_FAN_WATTAGE(1),
		@Doc("The multiplier for room thermostat being missing, applied to central heating and oil boiler pump power requirement, from SAP table 4f note (a) - default is 1.300 ")
		 PumpAndFanConstants_NO_ROOM_THERMOSTAT_MULTIPLIER(1),
		@Doc("The volume multiplier for a warm air system's circulating fan - default is 0.060 ")
		 PumpAndFanConstants_WARM_AIR_SYSTEM_VOLUME_MULTIPLIER(1),
		@Doc("Ventilation rate for open flue - default is 20.000 ")
		EnergyCalculatorConstants_OPEN_FLUE_VENTILATION_RATE(1),
		@Doc("Ventilation rate for chimney. - default is 40.000 ")
		EnergyCalculatorConstants_CHIMNEY_VENTILATION_RATE(1),
		@Doc("The volume of hot water used independent of the number of people in the house (liters) - default is 36.000 ")
		 HotWaterConstants09_BASE_VOLUME(1),
		@Doc("The volume of additional hot water use per person (liters per person) - default is 25.000 ")
		 HotWaterConstants09_PERSON_DEPENDENT_VOLUME(1),
		@Doc("The energy in watts per unit volume of water degree temperature rise (watts) - default is 0.041 ")
		 HotWaterConstants09_ENERGY_PER_VOLUME(1),
		@Doc("The hot water usage factor, by month of the year - this is used to scale the demand for hot water - default is 1.100, 1.060, 1.020, 0.980, 0.940, 0.900, 0.900, 0.940, 0.980, 1.020, 1.060, 1.100 ")
		 HotWaterConstants09_USAGE_FACTOR(12),
		@Doc("The hot water temperature rise required, by month of the year (kelvin) - default is 41.200, 41.400, 40.100, 37.600, 36.400, 33.900, 30.400, 33.400, 33.500, 36.300, 39.400, 39.900 ")
		 HotWaterConstants09_RISE_TEMPERATURE(12),
		@Doc("Minimum volume factor - default is 1.000 ")
		 SolarConstants_MINIMUM_VOLUME_FACTOR(1),
		@Doc("Volume factor coefficient term - default is 0.200 ")
		 SolarConstants_VOLUME_FACTOR_COEFFICIENT(1),
		@Doc("Amount of cylinder above secondary coil (used by solar heater) - default is 0.300 ")
		 SolarConstants_CYLINDER_REMAINDER_FACTOR(1),
		@Doc("Threshold for linear heatloss coefficient / zero loss efficiency to use lower expansion coefficients - default is 20.000 ")
		 SolarConstants_HLC_OVER_E_THRESHOLD(1),
		@Doc("Coefficients for quadratic model of collector performance factor when HLC/e is below the threshold - default is 0.970, -0.037, 0.001 ")
		 SolarConstants_LOW_HLC_EXPANSION(3),
		@Doc("Coefficients for quadratic model of collector performance factor when HLC/e is above or equal to the threshold - default is 0.693, -0.011, 0.000 ")
		 SolarConstants_HIGH_HLC_EXPANSION(3),
		@Doc("Solar overshading factor by overshading type - default is 1.000, 0.800, 0.650, 0.500 ")
		 SolarConstants_OVERSHADING_FACTOR(4),
		@Doc("Solar utilisation factor is multiplied by this if there is no tank thermostat - default is 0.900 ")
		 SolarConstants_UTILISATION_FACTOR_THERMOSTAT_FACTOR(1),
		@Doc("The average wattage of the circulation pump - default is 5.700 ")
		 SolarConstants_CIRCULATION_PUMP_WATTAGE(1),
		@Doc("The proportion of lighting energy that provides useful gains - default is 0.850 ")
		 GainsConstants_LIGHTING_GAIN_USEFULNESS(1),
		@Doc("The proportion of hot water energy that provides useful gains - default is 0.250 ")
		 GainsConstants_HOT_WATER_DIRECT_GAINS(1),
		@Doc("The proportion of hot water system losses (primary pipework, tank, distribution etc) that provides useful gains - default is 0.800 ")
		 GainsConstants_HOT_WATER_SYSTEM_GAINS(1),
		@Doc("This converts from M3/hr of air changes to W/degree temperature difference. - default is 0.330 ")
		 EnergyCalculatorConstants_VENTILATION_HEAT_LOSS_COEFFICIENT(1),
		@Doc("This is the number of extra air changes per hour caused by storeys of the house above the ground floor - default is 0.100 ")
		 EnergyCalculatorConstants_STACK_EFFECT_AIR_CHANGE_PARAMETER(1),
		@Doc("This is the number of air changes per hour caused by not having a draught lobby (double front doors) - default is 0.050 ")
		 EnergyCalculatorConstants_DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER(1),
		@Doc("The wind exposure factor is divided by this number - default is 4.000 ")
		 EnergyCalculatorConstants_WIND_FACTOR_DIVISOR(1),
		@Doc("This is the amount below demand temperature a fully unresponsive heating system is assumed to provide. - default is 2.000 ")
		 EnergyCalculatorConstants_UNRESPONSIVE_HEATING_SYSTEM_DELTA_T(1),
		@Doc("The gains per occupant, in watts due to metabolism - default is 60.000 ")
		 EnergyCalculatorConstants_METABOLIC_GAINS_PER_PERSON(1),
		 @Doc("The gains per occupant, in watts due to metabolism, when using reduced internal gains - default is 50.000 ")
		 EnergyCalculatorConstants_REDUCED_METABOLIC_GAINS_PER_PERSON(1),
		@Doc("The gains per occupant due to evaporation, in watts - default is 40.000 ")
		 EnergyCalculatorConstants_EVAPORATION_GAINS_PER_PERSON(1),
		@Doc("The thermal bridging coefficient used to estimate the thermal bridge contribution to heat loss parameter, for buildings which are older than the improvement year - default is 0.150 ")
		 EnergyCalculatorConstants_OLD_THERMAL_BRIDGING_COEFFICIENT(1),
		@Doc("The thermal bridging coefficient used to estimate the thermal bridge contribution for buildings which are built in or after the improvement year - default is 0.038 ")
		 EnergyCalculatorConstants_NEW_THERMAL_BRIDGING_COEFFICIENT(1),
		@Doc("The year in which the thermal bridging coefficient switches from OLD_THERMAL_BRIDGING_COEFFICIENT to NEW_THERMAL_BRIDGING_COEFFICIENT - default is 2003.000 ")
		 EnergyCalculatorConstants_THERMAL_BRIDING_COEFFICIENT_IMPROVEMENT_YEAR(1),
		@Doc("The multipliers for OvershadingType which affect the degree of solar gains. - default is 1.000, 1.000, 0.770, 0.540, 0.300 ")
		 EnergyCalculatorConstants_SOLAR_GAINS_OVERSHADING(5),
		@Doc("The reflection factor in solar gains (how much solar gain is lost because of reflectivity) - default is 0.900 ")
		 EnergyCalculatorConstants_SOLAR_GAINS_REFLECTION_FACTOR(1),
		@Doc("The value to which the heat loss parameter is compared when determining the demand temperature in zone 2 (depending on heating system control parameter) - default is 6.000 ")
		 EnergyCalculatorConstants_REFERENCE_HEAT_LOSS_PARAMETER(1),
		@Doc("The heat loss parameter multiplier used in calculating the time constant - default is 3.600 ")
		 EnergyCalculatorConstants_TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER(1),
		@Doc("The minimum cooling time, for average temperature calculation - default is 4.000 ")
		 EnergyCalculatorConstants_MINIMUM_COOLING_TIME(1),
		@Doc("Cooling time time constant multiplier, for average temperature calculation - default is 0.250 ")
		 EnergyCalculatorConstants_COOLING_TIME_TIME_CONSTANT_MULTIPLIER(1),
		@Doc("Divisor of time constant for utilisation factor calculation - default is 15.000 ")
		 EnergyCalculatorConstants_UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR(1),
		@Doc("Threshold degree days value, used for determining heating on factor - default is 5.000 ")
		 EnergyCalculatorConstants_THRESHOLD_DEGREE_DAYS_VALUE(1),
		@Doc("The amount below the demand temperature the threshold temperature is for gains utilisation factor calculation - default is 4.500 ")
		 EnergyCalculatorConstants_GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE(1),
		@Doc("The air change rate due to doors and windows for a house where none of the doors and windows are draught stripped - default is 0.250 ")
		 EnergyCalculatorConstants_WINDOW_INFILTRATION(1),
		@Doc("The reduction in air change rate due to doors and windows for a house where all of the doors and windows are draught stripped - default is 0.200 ")
		 EnergyCalculatorConstants_DRAUGHT_STRIPPED_FACTOR(1),
		@Doc("Proportional reduction in air changes for to each side of the house which is sheltered - default is 0.075 ")
		 EnergyCalculatorConstants_SHELTERED_SIDES_EXPOSURE_FACTOR(1),
		@Doc("no description - default is 10.000 ")
		 TestConstant_First(1),
		@Doc("no description - default is 1.000, 2.000 ")
		 TestConstant_Second(2),
		@Doc("Wet system responsivness by emitter type (rads, underfloor timber, underfloor screed, underfloor concrete, fan convectors) - default is 1.000, 1.000, 0.750, 0.250, 1.000 ")
		 ResponsivenessAdjustments_WET_SYSTEM_RESPONSIVENESS(5),
		@Doc("The coefficient in the appliance demand formula (watts) - default is 21.081 ")
		 ApplianceConstants09_APPLIANCE_DEMAND_COEFFICIENT_BREDEM(1),
		@Doc("no description - default is 23.705 ")
		 ApplianceConstants09_APPLIANCE_DEMAND_COEFFICIENT_SAP(1),
		@Doc("The exponent in the appliance demand formula - default is 0.471 ")
		 ApplianceConstants09_APPLIANCE_DEMAND_EXPONENT(1),
		@Doc("The coefficient of the cosine in the appliance demand monthly adjustment factor - default is 0.157 ")
		 ApplianceConstants09_APPLIANCE_DEMAND_COSINE_COEFFICIENT(1),
		@Doc("The offset within the cosine in the appliance demand monthly adjustment factor - default is 1.780 ")
		 ApplianceConstants09_APPLIANCE_DEMAND_COSINE_OFFSET(1),
		@Doc("The high rate fraction for appliances, by tarrif type (an array) - default is 1.000, 0.900, 0.800 ")
		 ApplianceConstants09_APPLIANCE_HIGH_RATE_FRACTION(3);

	private final int arity;

	XEnergyConstantType(final int arity) {
	this.arity = arity;
	}

	public int getArity() { return this.arity; }
	}

	@Doc("Defines a constants override, which relates a constant (by name) to a new value.")
	@ValidEnergyConstant
	@Bind("constant")
	public static class XEnergyConstant extends XElement {
		private XEnergyConstantType constant;
		private String value;
		
		
		@BindPositionalArgument(0)
		@Doc({"The name of the constant. See the energy calculator documentation for a full list of",
			"names, default values, units and descriptions."
		})
		public XEnergyConstantType getConstant() {
			return constant;
		}
		public void setConstant(final XEnergyConstantType key) {
			this.constant = key;
		}
		
		@BindPositionalArgument(1)
		@Doc({
			"The value of the constant; this should be a number or a comma-separated list of numbers.",
			"Information on the meaning of said numbers can be found in the energy calculator documentation."
		})
		public String getValue() {
			return value;
		}
		public void setValue(final String value) {
			this.value = value;
		}
	}
	
	private List<XEnergyConstant> constants = new ArrayList<XEnergyConstant>();

	@BindRemainingArguments
	
	@Doc("A list of all the constants to be overridden from their default values.")
	public List<XEnergyConstant> getConstants() {
		return constants;
	}

	public void setConstants(final List<XEnergyConstant> parameters) {
		this.constants = parameters;
	}
}
