package uk.org.cse.nhm.language.builder.function;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.energycalculator.constants.ApplianceConstants09;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;
import uk.org.cse.nhm.energycalculator.constants.GainsConstants;
import uk.org.cse.nhm.energycalculator.constants.HotWaterConstants09;
import uk.org.cse.nhm.energycalculator.constants.LightingConstants09;
import uk.org.cse.nhm.hom.constants.CommunityHeatingConstants;
import uk.org.cse.nhm.hom.constants.CylinderConstants;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.constants.PumpAndFanConstants;
import uk.org.cse.nhm.hom.constants.SolarConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.constants.adjustments.EfficiencyAdjustments;
import uk.org.cse.nhm.hom.constants.adjustments.ResponsivenessAdjustments;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.language.definition.action.XHeatingTemperaturesAction.XMonth;
import uk.org.cse.nhm.language.definition.action.XSetSiteExposureAction;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XWallInsulationMeasure.XWallInsulationType;
import uk.org.cse.nhm.language.definition.context.XEnergyConstantsContext.XEnergyConstantType;
import uk.org.cse.nhm.language.definition.enums.*;

public class MapEnum {
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Unmapped {
		public String[] value();
	}
	private static <E extends Enum<E>, Q extends Enum<Q>> E mapName(final Class<E> ec, final Q name) {
		return Enum.valueOf(ec, name.name());
	}

	public static final MonthType month(final XMonth month) {
		return mapName(MonthType.class, month);
	}

	public static final SiteExposureType siteExposure(final XSetSiteExposureAction.XSiteExposureType siteExposure) {
		return mapName(SiteExposureType.class, siteExposure);
	}

	public static final FrameType frameType(final XFrameType in) {
		return mapName(FrameType.class, in);
	}

	public static final GlazingType glazingType(final XGlazingType in) {
		return mapName(GlazingType.class, in);
	}

	public static final WindowInsulationType windowInsulationType(final XWindowInsulationType in) {
		return mapName(WindowInsulationType.class, in);
	}

	public static final WindowGlazingAirGap windowGlazingAirGap(final XWindowGlazingAirGap in) {
		return mapName(WindowGlazingAirGap.class, in);
	}

	public static final MorphologyType morphology(final XMorphologyType morph) {
		return mapName(MorphologyType.class, morph);
	}

	public static final TenureType tenure(final XTenureType tenure) {
		return mapName(TenureType.class, tenure);
	}

	public static final BuiltFormType builtForm(final XBuiltFormType builtForm) {
		return mapName(BuiltFormType.class, builtForm);
	}

	public static final RegionType region(final XRegionType region) {
		return mapName(RegionType.class, region);
	}

	public static final FuelType fuel(final XFuelType fuel) {
		switch (fuel) {
		case BiomassPellets: return FuelType.BIOMASS_PELLETS;
		case BiomassWood: return FuelType.BIOMASS_WOOD;
		case BiomassWoodchip: return FuelType.BIOMASS_WOODCHIP;
		case BottledLPG:return FuelType.BOTTLED_LPG;
		case BulkLPG:return FuelType.BULK_LPG;
		case CommunityHeat: return FuelType.COMMUNITY_HEAT;
		case Electricity: return FuelType.ELECTRICITY;
		case PeakElectricity: return FuelType.PEAK_ELECTRICITY;
		case OffPeakElectricity: return FuelType.OFF_PEAK_ELECTRICITY;
		case HouseCoal: return FuelType.HOUSE_COAL;
		case MainsGas:return FuelType.MAINS_GAS;
		case Oil: return FuelType.OIL;
		case Photons: return FuelType.PHOTONS;
		case ExportedElectricity: return FuelType.EXPORTED_ELECTRICITY;
		default: throw new RuntimeException("Unexpected XFuelType " + fuel);
		}
	}

	public static final EnergyCalculatorType energyCalc(final XEnergyCalculatorType calculatorType) {
		switch (calculatorType) {
		case BREDEM2012: return EnergyCalculatorType.BREDEM2012;
		case SAP2012: return EnergyCalculatorType.SAP2012;
		default: throw new RuntimeException("Unexpected XEnergyCalculatorType " + calculatorType);
		}
	}

	@Unmapped({
		"INTERNALS",
		"SOLAR_GAINS",
		"SINKS",
		"METABOLIC_GAINS"
	})

	/** Generated by ConstantsTableGenerator */
	final static Map<XEnergyConstantType, Enum> constantTypeMapping = ImmutableMap.<XEnergyConstantType, Enum>builder()
	.put(XEnergyConstantType.EfficiencyAdjustments_CONDENSING_UNDERFLOOR_HEATING, EfficiencyAdjustments.CONDENSING_UNDERFLOOR_HEATING)
	.put(XEnergyConstantType.EfficiencyAdjustments_CONDENSING_ADVANCED_COMPENSATOR, EfficiencyAdjustments.CONDENSING_ADVANCED_COMPENSATOR)
	.put(XEnergyConstantType.EfficiencyAdjustments_BOILER_WITHOUT_INTERLOCK, EfficiencyAdjustments.BOILER_WITHOUT_INTERLOCK)
	.put(XEnergyConstantType.CylinderConstants_LOSS_FACTOR_CONSTANT_TERM, CylinderConstants.LOSS_FACTOR_CONSTANT_TERM)
	.put(XEnergyConstantType.CylinderConstants_LOOSE_JACKET_FACTORS, CylinderConstants.LOOSE_JACKET_FACTORS)
	.put(XEnergyConstantType.CylinderConstants_FACTORY_JACKET_FACTORS, CylinderConstants.FACTORY_JACKET_FACTORS)
	.put(XEnergyConstantType.CylinderConstants_VOLUME_FACTOR_TERMS, CylinderConstants.VOLUME_FACTOR_TERMS)
	.put(XEnergyConstantType.CylinderConstants_TEMPERATURE_FACTOR_BASIC, CylinderConstants.TEMPERATURE_FACTOR_BASIC)
	.put(XEnergyConstantType.CylinderConstants_TEMPERATURE_FACTOR_NO_THERMOSTAT_MULTIPLIER, CylinderConstants.TEMPERATURE_FACTOR_NO_THERMOSTAT_MULTIPLIER)
	.put(XEnergyConstantType.CylinderConstants_TEMPERATURE_FACTOR_SEPARATE_HW_TIMER, CylinderConstants.TEMPERATURE_FACTOR_SEPARATE_HW_TIMER)
	.put(XEnergyConstantType.CylinderConstants_TEMPERATURE_FACTOR_PRIMARY_STORAGE_COMBI, CylinderConstants.TEMPERATURE_FACTOR_PRIMARY_STORAGE_COMBI)
	.put(XEnergyConstantType.CylinderConstants_TEMPERATURE_FACTOR_SECONDARY_STORAGE_COMBI, CylinderConstants.TEMPERATURE_FACTOR_SECONDARY_STORAGE_COMBI)
	.put(XEnergyConstantType.CylinderConstants_TEMPERATURE_FACTOR_GAS_CPSU, CylinderConstants.TEMPERATURE_FACTOR_GAS_CPSU)
	.put(XEnergyConstantType.CylinderConstants_TEMPERATURE_FACTOR_ELECTRIC_CPSU, CylinderConstants.TEMPERATURE_FACTOR_ELECTRIC_CPSU)
	.put(XEnergyConstantType.SplitRateConstants_DEFAULT_FRACTIONS, SplitRateConstants.DEFAULT_FRACTIONS)
	.put(XEnergyConstantType.SplitRateConstants_DIRECT_ELECTRIC_FRACTIONS, SplitRateConstants.DIRECT_ELECTRIC_FRACTIONS)
	.put(XEnergyConstantType.SplitRateConstants_ELECTRIC_BOILER_FRACTIONS, SplitRateConstants.ELECTRIC_BOILER_FRACTIONS)
	.put(XEnergyConstantType.SplitRateConstants_DUAL_IMMERSION_TERM1, SplitRateConstants.DUAL_IMMERSION_TERM1)
	.put(XEnergyConstantType.SplitRateConstants_DUAL_IMMERSION_TERM2, SplitRateConstants.DUAL_IMMERSION_TERM2)
	.put(XEnergyConstantType.SplitRateConstants_DUAL_IMMERSION_TERM3, SplitRateConstants.DUAL_IMMERSION_TERM3)
	.put(XEnergyConstantType.SplitRateConstants_DUAL_IMMERSION_TERM4, SplitRateConstants.DUAL_IMMERSION_TERM4)
	.put(XEnergyConstantType.SplitRateConstants_SINGLE_IMMERSION_TERM1, SplitRateConstants.SINGLE_IMMERSION_TERM1)
	.put(XEnergyConstantType.SplitRateConstants_SINGLE_IMMERSION_TERM2, SplitRateConstants.SINGLE_IMMERSION_TERM2)
	.put(XEnergyConstantType.SplitRateConstants_SINGLE_IMMERSION_TERM3, SplitRateConstants.SINGLE_IMMERSION_TERM3)
	.put(XEnergyConstantType.SplitRateConstants_SINGLE_IMMERSION_TERM4, SplitRateConstants.SINGLE_IMMERSION_TERM4)
	.put(XEnergyConstantType.SplitRateConstants_SINGLE_IMMERSION_TERM5, SplitRateConstants.SINGLE_IMMERSION_TERM5)
	.put(XEnergyConstantType.SplitRateConstants_INTEGRATED_STORAGE_HEATER_FRACTIONS, SplitRateConstants.INTEGRATED_STORAGE_HEATER_FRACTIONS)
	.put(XEnergyConstantType.SplitRateConstants_GROUND_SOURCE_SPACE_HEAT, SplitRateConstants.GROUND_SOURCE_SPACE_HEAT)
	.put(XEnergyConstantType.SplitRateConstants_AIR_SOURCE_SPACE_HEAT, SplitRateConstants.AIR_SOURCE_SPACE_HEAT)
	.put(XEnergyConstantType.SplitRateConstants_HEAT_PUMP_DHW_WITH_IMMERSION_HEATER, SplitRateConstants.HEAT_PUMP_DHW_WITH_IMMERSION_HEATER)
	.put(XEnergyConstantType.SplitRateConstants_HEAT_PUMP_DHW_WITHOUT_IMMERSION_HEATER, SplitRateConstants.HEAT_PUMP_DHW_WITHOUT_IMMERSION_HEATER)
	.put(XEnergyConstantType.HeatingSystemConstants_PRIMARY_PIPEWORK_COEFFICIENT, HeatingSystemConstants.PRIMARY_PIPEWORK_COEFFICIENT)
	.put(XEnergyConstantType.HeatingSystemConstants_PRIMARY_PIPEWORK_INSULATED_MULTIPLIER, HeatingSystemConstants.PRIMARY_PIPEWORK_INSULATED_MULTIPLIER)
	.put(XEnergyConstantType.HeatingSystemConstants_PRIMARY_PIPEWORK_UNINSULATED_MULTIPLIER, HeatingSystemConstants.PRIMARY_PIPEWORK_UNINSULATED_MULTIPLIER)
	.put(XEnergyConstantType.HeatingSystemConstants_PRIMARY_PIPEWORK_CONSTANT, HeatingSystemConstants.PRIMARY_PIPEWORK_CONSTANT)
	.put(XEnergyConstantType.HeatingSystemConstants_HOURS_PIPEWORK_HOT, HeatingSystemConstants.HOURS_PIPEWORK_HOT)
	.put(XEnergyConstantType.HeatingSystemConstants_CENTRAL_HEATING_DISTRIBUTION_LOSSES, HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES)
	.put(XEnergyConstantType.HeatingSystemConstants_CENTRAL_HEATING_SOLAR_PPCF, HeatingSystemConstants.CENTRAL_HEATING_SOLAR_PPCF)
	.put(XEnergyConstantType.HeatingSystemConstants_ELECTRIC_CPSU_LOW_RATE_HEAT_CONSTANT, HeatingSystemConstants.ELECTRIC_CPSU_LOW_RATE_HEAT_CONSTANT)
	.put(XEnergyConstantType.HeatingSystemConstants_ELECTRIC_CPSU_WINTER_TEMPERATURE_OFFSET, HeatingSystemConstants.ELECTRIC_CPSU_WINTER_TEMPERATURE_OFFSET)
	.put(XEnergyConstantType.HeatingSystemConstants_COMBI_HOT_WATER_USAGE_LIMIT, HeatingSystemConstants.COMBI_HOT_WATER_USAGE_LIMIT)
	.put(XEnergyConstantType.HeatingSystemConstants_INSTANTANEOUS_COMBI_FACTOR, HeatingSystemConstants.INSTANTANEOUS_COMBI_FACTOR)
	.put(XEnergyConstantType.HeatingSystemConstants_STORAGE_COMBI_VOLUME_THRESHOLD, HeatingSystemConstants.STORAGE_COMBI_VOLUME_THRESHOLD)
	.put(XEnergyConstantType.HeatingSystemConstants_INSTANTANEOUS_COMBI_FACTOR_KHF_WITH_TIMECLOCK, HeatingSystemConstants.INSTANTANEOUS_COMBI_FACTOR_KHF_WITH_TIMECLOCK)
	.put(XEnergyConstantType.HeatingSystemConstants_INSTANTANEOUS_COMBI_FACTOR_KHF_WITHOUT_TIMECLOCK, HeatingSystemConstants.INSTANTANEOUS_COMBI_FACTOR_KHF_WITHOUT_TIMECLOCK)
	.put(XEnergyConstantType.HeatingSystemConstants_STORAGE_COMBI_LOSS_TERMS, HeatingSystemConstants.STORAGE_COMBI_LOSS_TERMS)
	.put(XEnergyConstantType.LightingConstants09_LIGHT_DEMAND_EXPONENT, LightingConstants09.LIGHT_DEMAND_EXPONENT)
	.put(XEnergyConstantType.LightingConstants09_DAYLIGHT_PARAMETER_MAXIMUM, LightingConstants09.DAYLIGHT_PARAMETER_MAXIMUM)
	.put(XEnergyConstantType.LightingConstants09_DAYLIGHT_PARAMETER_0_COEFFICIENT, LightingConstants09.DAYLIGHT_PARAMETER_0_COEFFICIENT)
	.put(XEnergyConstantType.LightingConstants09_DAYLIGHT_PARAMETER_1_COEFFICIENT, LightingConstants09.DAYLIGHT_PARAMETER_1_COEFFICIENT)
	.put(XEnergyConstantType.LightingConstants09_DAYLIGHT_PARAMETER_2_COEFFICIENT, LightingConstants09.DAYLIGHT_PARAMETER_2_COEFFICIENT)
	.put(XEnergyConstantType.LightingConstants09_OVERSHADING_ACCESS_FACTORS, LightingConstants09.OVERSHADING_ACCESS_FACTORS)
	.put(XEnergyConstantType.LightingConstants09_ADJUSTMENT_FACTOR_TERMS, LightingConstants09.ADJUSTMENT_FACTOR_TERMS)
	.put(XEnergyConstantType.CommunityHeatingConstants_DEMAND_TEMPERATURE_ADJUSTMENT, CommunityHeatingConstants.DEMAND_TEMPERATURE_ADJUSTMENT)
	.put(XEnergyConstantType.CommunityHeatingConstants_HIGH_SPACE_USAGE_MULTIPLER, CommunityHeatingConstants.HIGH_SPACE_USAGE_MULTIPLER)
	.put(XEnergyConstantType.CommunityHeatingConstants_MEDIUM_SPACE_USAGE_MULTIPLER, CommunityHeatingConstants.MEDIUM_SPACE_USAGE_MULTIPLER)
	.put(XEnergyConstantType.CommunityHeatingConstants_LOW_SPACE_USAGE_MULTIPLIER, CommunityHeatingConstants.LOW_SPACE_USAGE_MULTIPLIER)
	.put(XEnergyConstantType.CommunityHeatingConstants_HIGH_WATER_USAGE_MULTIPLIER, CommunityHeatingConstants.HIGH_WATER_USAGE_MULTIPLIER)
	.put(XEnergyConstantType.CommunityHeatingConstants_LOW_WATER_USAGE_MULTIPLIER, CommunityHeatingConstants.LOW_WATER_USAGE_MULTIPLIER)
	.put(XEnergyConstantType.CommunityHeatingConstants_DEFAULT_DISTRIBUTION_LOSS_FACTOR, CommunityHeatingConstants.DEFAULT_DISTRIBUTION_LOSS_FACTOR)
	.put(XEnergyConstantType.TemperatureAdjustments_BOILER_NO_THERMOSTAT, TemperatureAdjustments.BOILER_NO_THERMOSTAT)
	.put(XEnergyConstantType.TemperatureAdjustments_BOILER_DELAYED_START_THERMOSTAT, TemperatureAdjustments.BOILER_DELAYED_START_THERMOSTAT)
	.put(XEnergyConstantType.TemperatureAdjustments_CPSU_OR_INTEGRATED_THERMAL_STORE, TemperatureAdjustments.CPSU_OR_INTEGRATED_THERMAL_STORE)
	.put(XEnergyConstantType.TemperatureAdjustments_STORAGE_HEATER, TemperatureAdjustments.STORAGE_HEATER)
	.put(XEnergyConstantType.TemperatureAdjustments_ROOM_HEATER_NO_THERMOSTAT, TemperatureAdjustments.ROOM_HEATER_NO_THERMOSTAT)
	.put(XEnergyConstantType.TemperatureAdjustments_WARM_AIR_SYSTEM_NO_THERMOSTAT, TemperatureAdjustments.WARM_AIR_SYSTEM_NO_THERMOSTAT)
	.put(XEnergyConstantType.TemperatureAdjustments_HEAT_PUMP_NO_THERMOSTAT, TemperatureAdjustments.HEAT_PUMP_NO_THERMOSTAT)
	.put(XEnergyConstantType.PumpAndFanConstants_CENTRAL_HEATING_PUMP_WATTAGE, PumpAndFanConstants.CENTRAL_HEATING_PUMP_WATTAGE)
	.put(XEnergyConstantType.PumpAndFanConstants_CENTRAL_HEATING_PUMP_GAINS, PumpAndFanConstants.CENTRAL_HEATING_PUMP_GAINS)
	.put(XEnergyConstantType.PumpAndFanConstants_OIL_FUEL_PUMP_WATTAGE, PumpAndFanConstants.OIL_FUEL_PUMP_WATTAGE)
	.put(XEnergyConstantType.PumpAndFanConstants_OIL_FUEL_PUMP_GAINS, PumpAndFanConstants.OIL_FUEL_PUMP_GAINS)
	.put(XEnergyConstantType.PumpAndFanConstants_GAS_BOILER_FLUE_FAN_WATTAGE, PumpAndFanConstants.GAS_BOILER_FLUE_FAN_WATTAGE)
	.put(XEnergyConstantType.PumpAndFanConstants_GAS_HEAT_PUMP_FLUE_FAN_WATTAGE, PumpAndFanConstants.GAS_HEAT_PUMP_FLUE_FAN_WATTAGE)
	.put(XEnergyConstantType.PumpAndFanConstants_NO_ROOM_THERMOSTAT_MULTIPLIER, PumpAndFanConstants.NO_ROOM_THERMOSTAT_MULTIPLIER)
	.put(XEnergyConstantType.PumpAndFanConstants_WARM_AIR_SYSTEM_VOLUME_MULTIPLIER, PumpAndFanConstants.WARM_AIR_SYSTEM_VOLUME_MULTIPLIER)
	.put(XEnergyConstantType.EnergyCalculatorConstants_OPEN_FLUE_VENTILATION_RATE, EnergyCalculatorConstants.OPEN_FLUE_VENTILATION_RATE)
	.put(XEnergyConstantType.EnergyCalculatorConstants_CHIMNEY_VENTILATION_RATE, EnergyCalculatorConstants.CHIMNEY_VENTILATION_RATE)
	.put(XEnergyConstantType.HotWaterConstants09_BASE_VOLUME, HotWaterConstants09.BASE_VOLUME)
	.put(XEnergyConstantType.HotWaterConstants09_PERSON_DEPENDENT_VOLUME, HotWaterConstants09.PERSON_DEPENDENT_VOLUME)
	.put(XEnergyConstantType.HotWaterConstants09_ENERGY_PER_VOLUME, HotWaterConstants09.ENERGY_PER_VOLUME)
	.put(XEnergyConstantType.HotWaterConstants09_USAGE_FACTOR, HotWaterConstants09.USAGE_FACTOR)
	.put(XEnergyConstantType.HotWaterConstants09_RISE_TEMPERATURE, HotWaterConstants09.RISE_TEMPERATURE)
	.put(XEnergyConstantType.SolarConstants_MINIMUM_VOLUME_FACTOR, SolarConstants.MINIMUM_VOLUME_FACTOR)
	.put(XEnergyConstantType.SolarConstants_VOLUME_FACTOR_COEFFICIENT, SolarConstants.VOLUME_FACTOR_COEFFICIENT)
	.put(XEnergyConstantType.SolarConstants_CYLINDER_REMAINDER_FACTOR, SolarConstants.CYLINDER_REMAINDER_FACTOR)
	.put(XEnergyConstantType.SolarConstants_HLC_OVER_E_THRESHOLD, SolarConstants.HLC_OVER_E_THRESHOLD)
	.put(XEnergyConstantType.SolarConstants_LOW_HLC_EXPANSION, SolarConstants.LOW_HLC_EXPANSION)
	.put(XEnergyConstantType.SolarConstants_HIGH_HLC_EXPANSION, SolarConstants.HIGH_HLC_EXPANSION)
	.put(XEnergyConstantType.SolarConstants_OVERSHADING_FACTOR, SolarConstants.OVERSHADING_FACTOR)
	.put(XEnergyConstantType.SolarConstants_UTILISATION_FACTOR_THERMOSTAT_FACTOR, SolarConstants.UTILISATION_FACTOR_THERMOSTAT_FACTOR)
	.put(XEnergyConstantType.SolarConstants_CIRCULATION_PUMP_WATTAGE, SolarConstants.CIRCULATION_PUMP_WATTAGE)
	.put(XEnergyConstantType.GainsConstants_LIGHTING_GAIN_USEFULNESS, GainsConstants.LIGHTING_GAIN_USEFULNESS)
	.put(XEnergyConstantType.GainsConstants_HOT_WATER_DIRECT_GAINS, GainsConstants.HOT_WATER_DIRECT_GAINS)
	.put(XEnergyConstantType.GainsConstants_HOT_WATER_SYSTEM_GAINS, GainsConstants.HOT_WATER_SYSTEM_GAINS)
	.put(XEnergyConstantType.EnergyCalculatorConstants_VENTILATION_HEAT_LOSS_COEFFICIENT, EnergyCalculatorConstants.VENTILATION_HEAT_LOSS_COEFFICIENT)
	.put(XEnergyConstantType.EnergyCalculatorConstants_STACK_EFFECT_AIR_CHANGE_PARAMETER, EnergyCalculatorConstants.STACK_EFFECT_AIR_CHANGE_PARAMETER)
	.put(XEnergyConstantType.EnergyCalculatorConstants_DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER, EnergyCalculatorConstants.DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER)
	.put(XEnergyConstantType.EnergyCalculatorConstants_WIND_FACTOR_DIVISOR, EnergyCalculatorConstants.WIND_FACTOR_DIVISOR)
	.put(XEnergyConstantType.EnergyCalculatorConstants_UNRESPONSIVE_HEATING_SYSTEM_DELTA_T, EnergyCalculatorConstants.UNRESPONSIVE_HEATING_SYSTEM_DELTA_T)
	.put(XEnergyConstantType.EnergyCalculatorConstants_METABOLIC_GAINS_PER_PERSON, EnergyCalculatorConstants.METABOLIC_GAINS_PER_PERSON)
	.put(XEnergyConstantType.EnergyCalculatorConstants_REDUCED_METABOLIC_GAINS_PER_PERSON, EnergyCalculatorConstants.REDUCED_METABOLIC_GAINS_PER_PERSON)
	.put(XEnergyConstantType.EnergyCalculatorConstants_EVAPORATION_GAINS_PER_PERSON, EnergyCalculatorConstants.EVAPORATION_GAINS_PER_PERSON)
	.put(XEnergyConstantType.EnergyCalculatorConstants_SOLAR_GAINS_OVERSHADING, EnergyCalculatorConstants.SOLAR_GAINS_OVERSHADING)
	.put(XEnergyConstantType.EnergyCalculatorConstants_SOLAR_GAINS_REFLECTION_FACTOR, EnergyCalculatorConstants.SOLAR_GAINS_REFLECTION_FACTOR)
	.put(XEnergyConstantType.EnergyCalculatorConstants_REFERENCE_HEAT_LOSS_PARAMETER, EnergyCalculatorConstants.REFERENCE_HEAT_LOSS_PARAMETER)
	.put(XEnergyConstantType.EnergyCalculatorConstants_TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER, EnergyCalculatorConstants.TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER)
	.put(XEnergyConstantType.EnergyCalculatorConstants_MINIMUM_COOLING_TIME, EnergyCalculatorConstants.MINIMUM_COOLING_TIME)
	.put(XEnergyConstantType.EnergyCalculatorConstants_COOLING_TIME_TIME_CONSTANT_MULTIPLIER, EnergyCalculatorConstants.COOLING_TIME_TIME_CONSTANT_MULTIPLIER)
	.put(XEnergyConstantType.EnergyCalculatorConstants_UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR, EnergyCalculatorConstants.UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR)
	.put(XEnergyConstantType.EnergyCalculatorConstants_THRESHOLD_DEGREE_DAYS_VALUE, EnergyCalculatorConstants.THRESHOLD_DEGREE_DAYS_VALUE)
	.put(XEnergyConstantType.EnergyCalculatorConstants_GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE, EnergyCalculatorConstants.GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE)
	.put(XEnergyConstantType.EnergyCalculatorConstants_WINDOW_INFILTRATION, EnergyCalculatorConstants.WINDOW_INFILTRATION)
	.put(XEnergyConstantType.EnergyCalculatorConstants_DRAUGHT_STRIPPED_FACTOR, EnergyCalculatorConstants.DRAUGHT_STRIPPED_FACTOR)
	.put(XEnergyConstantType.EnergyCalculatorConstants_SHELTERED_SIDES_EXPOSURE_FACTOR, EnergyCalculatorConstants.SHELTERED_SIDES_EXPOSURE_FACTOR)
	.put(XEnergyConstantType.ResponsivenessAdjustments_WET_SYSTEM_RESPONSIVENESS, ResponsivenessAdjustments.WET_SYSTEM_RESPONSIVENESS)
	.put(XEnergyConstantType.ApplianceConstants09_APPLIANCE_DEMAND_COEFFICIENT_BREDEM, ApplianceConstants09.APPLIANCE_DEMAND_COEFFICIENT_BREDEM)
	.put(XEnergyConstantType.ApplianceConstants09_APPLIANCE_DEMAND_COEFFICIENT_SAP, ApplianceConstants09.APPLIANCE_DEMAND_COEFFICIENT_SAP)
	.put(XEnergyConstantType.ApplianceConstants09_APPLIANCE_DEMAND_EXPONENT, ApplianceConstants09.APPLIANCE_DEMAND_EXPONENT)
	.put(XEnergyConstantType.ApplianceConstants09_APPLIANCE_DEMAND_COSINE_COEFFICIENT, ApplianceConstants09.APPLIANCE_DEMAND_COSINE_COEFFICIENT)
	.put(XEnergyConstantType.ApplianceConstants09_APPLIANCE_DEMAND_COSINE_OFFSET, ApplianceConstants09.APPLIANCE_DEMAND_COSINE_OFFSET)
	.put(XEnergyConstantType.ApplianceConstants09_APPLIANCE_HIGH_RATE_FRACTION, ApplianceConstants09.APPLIANCE_HIGH_RATE_FRACTION)

	.build();


	public static Enum<?> energyConstant(final XEnergyConstantType ct) {
		return constantTypeMapping.get(ct);
	}

	public static WallInsulationType wallInsulationType(final XWallInsulationType wit) {
		switch (wit) {
		case Cavity:
			return WallInsulationType.FilledCavity;
		case External:
			return WallInsulationType.External;
		case Internal:
			return WallInsulationType.Internal;
		default:
			throw new IllegalArgumentException();

		}
	}

	/** Generated by EnergyCalculationstepsGenerate.java */
	final static Map<XEnergyCalculationStep, EnergyCalculationStep> energyCalculationStepMapping = ImmutableMap.<XEnergyCalculationStep, EnergyCalculationStep>builder()
			.put(XEnergyCalculationStep.FloorArea_Basement, EnergyCalculationStep.FloorArea_Basement)
			.put(XEnergyCalculationStep.FloorArea_Ground, EnergyCalculationStep.FloorArea_Ground)
			.put(XEnergyCalculationStep.FloorArea_First, EnergyCalculationStep.FloorArea_First)
			.put(XEnergyCalculationStep.FloorArea_Second, EnergyCalculationStep.FloorArea_Second)
			.put(XEnergyCalculationStep.FloorArea_Third_and_Above, EnergyCalculationStep.FloorArea_Third_and_Above)
			.put(XEnergyCalculationStep.TotalFloorArea, EnergyCalculationStep.TotalFloorArea)
			.put(XEnergyCalculationStep.DwellingVolume, EnergyCalculationStep.DwellingVolume)
			.put(XEnergyCalculationStep.ChimneyVentilation, EnergyCalculationStep.ChimneyVentilation)
			.put(XEnergyCalculationStep.OpenFluesVentilation, EnergyCalculationStep.OpenFluesVentilation)
			.put(XEnergyCalculationStep.IntermittentFansVentilation, EnergyCalculationStep.IntermittentFansVentilation)
			.put(XEnergyCalculationStep.PassiveVentsVentilation, EnergyCalculationStep.PassiveVentsVentilation)
			.put(XEnergyCalculationStep.FluelessGasFiresVentilation, EnergyCalculationStep.FluelessGasFiresVentilation)
			.put(XEnergyCalculationStep.AirChanges_ChimneysFluesFansAndPSVs, EnergyCalculationStep.AirChanges_ChimneysFluesFansAndPSVs)
			.put(XEnergyCalculationStep.Storeys, EnergyCalculationStep.Storeys)
			.put(XEnergyCalculationStep.InfiltrationAdditionalStackEffect, EnergyCalculationStep.InfiltrationAdditionalStackEffect)
			.put(XEnergyCalculationStep.InfiltrationStructural, EnergyCalculationStep.InfiltrationStructural)
			.put(XEnergyCalculationStep.InfiltrationGroundFloor, EnergyCalculationStep.InfiltrationGroundFloor)
			.put(XEnergyCalculationStep.InfiltrationAbsenceOfDraughtLobby, EnergyCalculationStep.InfiltrationAbsenceOfDraughtLobby)
			.put(XEnergyCalculationStep.ProportionWindowsDraughtProofed, EnergyCalculationStep.ProportionWindowsDraughtProofed)
			.put(XEnergyCalculationStep.InfiltrationWindows, EnergyCalculationStep.InfiltrationWindows)
			.put(XEnergyCalculationStep.InfiltrationRate_Initial, EnergyCalculationStep.InfiltrationRate_Initial)
			.put(XEnergyCalculationStep.InfiltrationRateMaybePressureTest, EnergyCalculationStep.InfiltrationRateMaybePressureTest)
			.put(XEnergyCalculationStep.SidesSheltered, EnergyCalculationStep.SidesSheltered)
			.put(XEnergyCalculationStep.ShelterFactor, EnergyCalculationStep.ShelterFactor)
			.put(XEnergyCalculationStep.InfiltrationRate_IncludingShelter, EnergyCalculationStep.InfiltrationRate_IncludingShelter)
			.put(XEnergyCalculationStep.AverageWindSpeed, EnergyCalculationStep.AverageWindSpeed)
			.put(XEnergyCalculationStep.WindFactor, EnergyCalculationStep.WindFactor)
			.put(XEnergyCalculationStep.InfiltrationRate_IncludingShelterAndWind, EnergyCalculationStep.InfiltrationRate_IncludingShelterAndWind)
			.put(XEnergyCalculationStep.SiteExposureFactor, EnergyCalculationStep.SiteExposureFactor)
			.put(XEnergyCalculationStep.InfiltrationRate_IncludingShelterAndWindAndSiteExposure, EnergyCalculationStep.InfiltrationRate_IncludingShelterAndWindAndSiteExposure)
			.put(XEnergyCalculationStep.Ventilation_NaturalOrPositiveFromLoft, EnergyCalculationStep.Ventilation_NaturalOrPositiveFromLoft)
			.put(XEnergyCalculationStep.AirChanges_Effective, EnergyCalculationStep.AirChanges_Effective)
			.put(XEnergyCalculationStep.HeatLossCoefficient_DoorsSolid, EnergyCalculationStep.HeatLossCoefficient_DoorsSolid)
			.put(XEnergyCalculationStep.HeatLossCoefficient_DoorsSemiGlazed, EnergyCalculationStep.HeatLossCoefficient_DoorsSemiGlazed)
			.put(XEnergyCalculationStep.HeatLossCoefficient_Window, EnergyCalculationStep.HeatLossCoefficient_Window)
			.put(XEnergyCalculationStep.HeatLossCoefficient_Window_UPVC, EnergyCalculationStep.HeatLossCoefficient_Window_UPVC)
			.put(XEnergyCalculationStep.HeatLossCoefficient_Window_Wood, EnergyCalculationStep.HeatLossCoefficient_Window_Wood)
			.put(XEnergyCalculationStep.HeatLossCoefficient_Window_Metal, EnergyCalculationStep.HeatLossCoefficient_Window_Metal)
			.put(XEnergyCalculationStep.HeatLossCoefficient_Window_Roof, EnergyCalculationStep.HeatLossCoefficient_Window_Roof)
			.put(XEnergyCalculationStep.HeatLossCoefficient_BasementFloor, EnergyCalculationStep.HeatLossCoefficient_BasementFloor)
			.put(XEnergyCalculationStep.HeatLossCoefficient_GroundFloor, EnergyCalculationStep.HeatLossCoefficient_GroundFloor)
			.put(XEnergyCalculationStep.HeatLossCoefficient_ExposedFloor, EnergyCalculationStep.HeatLossCoefficient_ExposedFloor)
			.put(XEnergyCalculationStep.HeatLossCoefficient_BasementWall, EnergyCalculationStep.HeatLossCoefficient_BasementWall)
			.put(XEnergyCalculationStep.HeatLossCoefficient_ExternalWall, EnergyCalculationStep.HeatLossCoefficient_ExternalWall)
			.put(XEnergyCalculationStep.HeatLossCoefficient_Roof, EnergyCalculationStep.HeatLossCoefficient_Roof)
			.put(XEnergyCalculationStep.AreaExternal, EnergyCalculationStep.AreaExternal)
			.put(XEnergyCalculationStep.AreaPartyWall, EnergyCalculationStep.AreaPartyWall)
			.put(XEnergyCalculationStep.AreaPartyFloor, EnergyCalculationStep.AreaPartyFloor)
			.put(XEnergyCalculationStep.AreaPartyCeiling, EnergyCalculationStep.AreaPartyCeiling)
			.put(XEnergyCalculationStep.AreaInternalWall, EnergyCalculationStep.AreaInternalWall)
			.put(XEnergyCalculationStep.AreaInternalFloor, EnergyCalculationStep.AreaInternalFloor)
			.put(XEnergyCalculationStep.AreaInternalCeiling, EnergyCalculationStep.AreaInternalCeiling)
			.put(XEnergyCalculationStep.FabricHeatLoss, EnergyCalculationStep.FabricHeatLoss)
			.put(XEnergyCalculationStep.ThermalMassParameter, EnergyCalculationStep.ThermalMassParameter)
			.put(XEnergyCalculationStep.ThermalBridges, EnergyCalculationStep.ThermalBridges)
			.put(XEnergyCalculationStep.FabricLossTotal, EnergyCalculationStep.FabricLossTotal)
			.put(XEnergyCalculationStep.VentilationHeatLoss, EnergyCalculationStep.VentilationHeatLoss)
			.put(XEnergyCalculationStep.HeatTransferCoefficient, EnergyCalculationStep.HeatTransferCoefficient)
			.put(XEnergyCalculationStep.HeatLossParameter, EnergyCalculationStep.HeatLossParameter)
			.put(XEnergyCalculationStep.DaysInMonth, EnergyCalculationStep.DaysInMonth)
			.put(XEnergyCalculationStep.Occupancy, EnergyCalculationStep.Occupancy)
			.put(XEnergyCalculationStep.WaterHeating_Usage_Initial, EnergyCalculationStep.WaterHeating_Usage_Initial)
			.put(XEnergyCalculationStep.WaterHeating_Usage_MonthAdjusted, EnergyCalculationStep.WaterHeating_Usage_MonthAdjusted)
			.put(XEnergyCalculationStep.WaterHeating_EnergyContent, EnergyCalculationStep.WaterHeating_EnergyContent)
			.put(XEnergyCalculationStep.WaterHeating_DistributionLoss, EnergyCalculationStep.WaterHeating_DistributionLoss)
			.put(XEnergyCalculationStep.WaterHeating_StorageVolume, EnergyCalculationStep.WaterHeating_StorageVolume)
			.put(XEnergyCalculationStep.WaterHeating_StorageLossFactor, EnergyCalculationStep.WaterHeating_StorageLossFactor)
			.put(XEnergyCalculationStep.WaterHeating_StorageVolumeFactor, EnergyCalculationStep.WaterHeating_StorageVolumeFactor)
			.put(XEnergyCalculationStep.WaterHeating_StorageTemperatureFactor, EnergyCalculationStep.WaterHeating_StorageTemperatureFactor)
			.put(XEnergyCalculationStep.WaterHeating_StorageLosses_Daily_Calculated, EnergyCalculationStep.WaterHeating_StorageLosses_Daily_Calculated)
			.put(XEnergyCalculationStep.WaterHeating_StorageLosses_Daily, EnergyCalculationStep.WaterHeating_StorageLosses_Daily)
			.put(XEnergyCalculationStep.WaterHeating_StorageLosses_Monthly, EnergyCalculationStep.WaterHeating_StorageLosses_Monthly)
			.put(XEnergyCalculationStep.WaterHeating_StorageLosses_Monthly_ExcludeSolar, EnergyCalculationStep.WaterHeating_StorageLosses_Monthly_ExcludeSolar)
			.put(XEnergyCalculationStep.WaterHeating_PrimaryCircuitLoss_Monthly, EnergyCalculationStep.WaterHeating_PrimaryCircuitLoss_Monthly)
			.put(XEnergyCalculationStep.WaterHeating_CombiLoss_Monthly, EnergyCalculationStep.WaterHeating_CombiLoss_Monthly)
			.put(XEnergyCalculationStep.WaterHeating_TotalHeat_Monthly_BeforeSolar, EnergyCalculationStep.WaterHeating_TotalHeat_Monthly_BeforeSolar)
			.put(XEnergyCalculationStep.WaterHeating_Solar, EnergyCalculationStep.WaterHeating_Solar)
			.put(XEnergyCalculationStep.Gains_HotWater_Monthly, EnergyCalculationStep.Gains_HotWater_Monthly)
			.put(XEnergyCalculationStep.Gains_Metabolic, EnergyCalculationStep.Gains_Metabolic)
			.put(XEnergyCalculationStep.Gains_Lighting, EnergyCalculationStep.Gains_Lighting)
			.put(XEnergyCalculationStep.Gains_Appliances, EnergyCalculationStep.Gains_Appliances)
			.put(XEnergyCalculationStep.Gains_Cooking, EnergyCalculationStep.Gains_Cooking)
			.put(XEnergyCalculationStep.Gains_PumpsAndFans, EnergyCalculationStep.Gains_PumpsAndFans)
			.put(XEnergyCalculationStep.Gains_Evaporation, EnergyCalculationStep.Gains_Evaporation)
			.put(XEnergyCalculationStep.Gains_HotWater, EnergyCalculationStep.Gains_HotWater)
			.put(XEnergyCalculationStep.Gains_Internal, EnergyCalculationStep.Gains_Internal)
			.put(XEnergyCalculationStep.Gains_Solar_Roof, EnergyCalculationStep.Gains_Solar_Roof)
			.put(XEnergyCalculationStep.Gains_Solar, EnergyCalculationStep.Gains_Solar)
			.put(XEnergyCalculationStep.Gains, EnergyCalculationStep.Gains)
			.put(XEnergyCalculationStep.DemandTemperature_LivingArea, EnergyCalculationStep.DemandTemperature_LivingArea)
			.put(XEnergyCalculationStep.GainsUtilisation_LivingArea, EnergyCalculationStep.GainsUtilisation_LivingArea)
			.put(XEnergyCalculationStep.MeanInternalTemperature_LivingArea, EnergyCalculationStep.MeanInternalTemperature_LivingArea)
			.put(XEnergyCalculationStep.DemandTemperature_RestOfDwelling, EnergyCalculationStep.DemandTemperature_RestOfDwelling)
			.put(XEnergyCalculationStep.GainsUtilisation_RestOfDwelling, EnergyCalculationStep.GainsUtilisation_RestOfDwelling)
			.put(XEnergyCalculationStep.MeanInternalTemperature_RestOfDwelling, EnergyCalculationStep.MeanInternalTemperature_RestOfDwelling)
			.put(XEnergyCalculationStep.LivingAreaFraction, EnergyCalculationStep.LivingAreaFraction)
			.put(XEnergyCalculationStep.MeanInternalTemperature_Unadjusted, EnergyCalculationStep.MeanInternalTemperature_Unadjusted)
			.put(XEnergyCalculationStep.MeanInternalTemperature, EnergyCalculationStep.MeanInternalTemperature)
			.put(XEnergyCalculationStep.GainsUtilisation, EnergyCalculationStep.GainsUtilisation)
			.put(XEnergyCalculationStep.Gains_Useful, EnergyCalculationStep.Gains_Useful)
			.put(XEnergyCalculationStep.ExternalTemperature, EnergyCalculationStep.ExternalTemperature)
			.put(XEnergyCalculationStep.HeatLossRate, EnergyCalculationStep.HeatLossRate)
			.put(XEnergyCalculationStep.SpaceHeating, EnergyCalculationStep.SpaceHeating)
			.put(XEnergyCalculationStep.SpaceHeating_Fraction_Secondary, EnergyCalculationStep.SpaceHeating_Fraction_Secondary)
			.put(XEnergyCalculationStep.SpaceHeating_Fraction_Main, EnergyCalculationStep.SpaceHeating_Fraction_Main)
			.put(XEnergyCalculationStep.SpaceHeating_FractionWithinMainSystem, EnergyCalculationStep.SpaceHeating_FractionWithinMainSystem)
			.put(XEnergyCalculationStep.SpaceHeating_Fraction_Main_System1, EnergyCalculationStep.SpaceHeating_Fraction_Main_System1)
			.put(XEnergyCalculationStep.SpaceHeating_Fraction_Main_System2, EnergyCalculationStep.SpaceHeating_Fraction_Main_System2)
			.put(XEnergyCalculationStep.SpaceHeating_Efficiency_Main_System1, EnergyCalculationStep.SpaceHeating_Efficiency_Main_System1)
			.put(XEnergyCalculationStep.SpaceHeating_Efficiency_Secondary, EnergyCalculationStep.SpaceHeating_Efficiency_Secondary)
			.put(XEnergyCalculationStep.Energy_SpaceHeating_Fuel_Main_system2, EnergyCalculationStep.Energy_SpaceHeating_Fuel_Main_system2)
			.put(XEnergyCalculationStep.WaterHeating_Efficiency, EnergyCalculationStep.WaterHeating_Efficiency)
			.put(XEnergyCalculationStep.Energy_SpaceCooling, EnergyCalculationStep.Energy_SpaceCooling)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_MechanicalVentilationFans, EnergyCalculationStep.PumpsFansAndKeepHot_MechanicalVentilationFans)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_WarmAirFans, EnergyCalculationStep.PumpsFansAndKeepHot_WarmAirFans)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_WaterPump, EnergyCalculationStep.PumpsFansAndKeepHot_WaterPump)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_OilBoilerPump, EnergyCalculationStep.PumpsFansAndKeepHot_OilBoilerPump)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_BoilerFlueFan, EnergyCalculationStep.PumpsFansAndKeepHot_BoilerFlueFan)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_KeepHot, EnergyCalculationStep.PumpsFansAndKeepHot_KeepHot)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_SolarWaterHeatingPump, EnergyCalculationStep.PumpsFansAndKeepHot_SolarWaterHeatingPump)
			.put(XEnergyCalculationStep.PumpsFansAndKeepHot_StorageWWHRSPump, EnergyCalculationStep.PumpsFansAndKeepHot_StorageWWHRSPump)
			.put(XEnergyCalculationStep.Generation_PhotoVoltaic, EnergyCalculationStep.Generation_PhotoVoltaic)
			.put(XEnergyCalculationStep.Generation_WindTurbines, EnergyCalculationStep.Generation_WindTurbines)
			.put(XEnergyCalculationStep.Generation_MicroCHP, EnergyCalculationStep.Generation_MicroCHP)
			.put(XEnergyCalculationStep.Generation_Hydro, EnergyCalculationStep.Generation_Hydro)
			.put(XEnergyCalculationStep.Community_ChargingFactor_SpaceHeating, EnergyCalculationStep.Community_ChargingFactor_SpaceHeating)
			.put(XEnergyCalculationStep.Community_ChargingFactor_WaterHeating, EnergyCalculationStep.Community_ChargingFactor_WaterHeating)
			.put(XEnergyCalculationStep.Community_DistributionLossFactor, EnergyCalculationStep.Community_DistributionLossFactor)
			.build();

    public static EnergyCalculationStep energyCalculationStep(XEnergyCalculationStep step) {
        return energyCalculationStepMapping.get(step);
    }
}
