package uk.org.cse.nhm.energycalculator.api.types.steps;

import com.google.common.collect.ImmutableList;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;

import java.util.List;

import static uk.org.cse.nhm.energycalculator.api.types.steps.Period.*;
import static uk.org.cse.nhm.energycalculator.api.types.steps.Units.*;

/**
 * This is derived from the SAP 2012 worksheet https://www.bre.co.uk/filelibrary/SAP/2012/SAP-2012_9-92.pdf
 * It's incomplete, because the NHM doesn't implement everything in there.
 * Additionally we do some things in a different way to SAP (e.g. different ordering, or leaving out a pathway.
 *
 * This class was created so that BEIS could have NHM outputs to compare with the outputs from
 * BRE's private internal SAP reference implementation.
 *
 * At some point in the future we might add BREDEM 2012.
 */
public enum EnergyCalculationStep {
    /**
     *  Dimensions and Structure
     */
    FloorArea_Basement(MetreSquared, Annual,
            SAPWorksheetSection.Dimensions.subCell(1, 'a'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    FloorArea_Ground(MetreSquared, Annual,
            SAPWorksheetSection.Dimensions.subCell(1, 'b'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    FloorArea_First(MetreSquared, Annual,
            SAPWorksheetSection.Dimensions.subCell(1, 'c'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    FloorArea_Second(MetreSquared, Annual,
            SAPWorksheetSection.Dimensions.subCell(1, 'd'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    // BRE lump all of the upper floors together so that they can fit into a spreadsheet with a fixed number of columns.
    FloorArea_Third_and_Above(MetreSquared, Annual,
            SAPWorksheetSection.Dimensions.subCell(1, 'e'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    // v2a to 2n (storey heights) and v3a to 3n (storey volumes) omitted, because they weren't present in BRE's sample spreadsheet.

    TotalFloorArea(MetreSquared, Annual,
            SAPWorksheetSection.Dimensions.cell(4),
            BREDEMSection.LightsAppliancesAndCooking.var("TFA"),
            DefaultValue.None),

    DwellingVolume(MetreCubed, Annual,
            SAPWorksheetSection.Dimensions.cell(5),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    /**
     * Ventilation
     */
    ChimneyVentilation(MetreCubed_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.subCell(6, 'a'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    OpenFluesVentilation(MetreCubed_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.subCell(6, 'b'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    IntermittentFansVentilation(MetreCubed_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.subCell(7, 'a'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    PassiveVentsVentilation(MetreCubed_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.subCell(7, 'b'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    FluelessGasFiresVentilation(MetreCubed_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.subCell(7 ,'c'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    AirChanges_ChimneysFluesFansAndPSVs(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(8),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Storeys(Count, Annual,
            SAPWorksheetSection.Ventilation.cell(9),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationAdditionalStackEffect(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(10),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationStructural(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(11),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationGroundFloor(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(12),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationAbsenceOfDraughtLobby(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(13),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    ProportionWindowsDraughtProofed(DimensionlessProportion, Annual,
            SAPWorksheetSection.Ventilation.cell(14),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationWindows(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(15),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationRate_Initial(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(16),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    AirPermabilityValue(null, Annual,
            SAPWorksheetSection.Ventilation.cell(17),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.AirPressureTests_Unsupported),

    InfiltrationRateMaybePressureTest(AirChange_per_Hour, Annual,
            SAPWorksheetSection.Ventilation.cell(18),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    SidesSheltered(Count, MonthlyMean, // Should be Annual
            SAPWorksheetSection.Ventilation.cell(19),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    ShelterFactor(Dimensionless, MonthlyMean, // Should be Annual
            SAPWorksheetSection.Ventilation.cell(20),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationRate_IncludingShelter(AirChange_per_Hour, MonthlyMean, // Should be Annual
            SAPWorksheetSection.Ventilation.cell(21),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    AverageWindSpeed(Metre_per_Second, MonthlyMean,
            SAPWorksheetSection.Ventilation.cell(22),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    WindFactor(Dimensionless, MonthlyMean,
            SAPWorksheetSection.Ventilation.cell(23),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    InfiltrationRate_IncludingShelterAndWind(AirChange_per_Hour, MonthlyMean,
            SAPWorksheetSection.Ventilation.cell(24),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    SiteExposureFactor(Dimensionless, MonthlyMean, // Should be annual
            SAPLocation.None,
            BREDEMSection.HeatLoss.var("ShE"),
            DefaultValue.None),

    InfiltrationRate_IncludingShelterAndWindAndSiteExposure(AirChange_per_Hour, MonthlyMean,
            SAPLocation.None,
            BREDEMSection.HeatLoss.step('E', "Lsub,m"),
            DefaultValue.None),

    // Unimplemented technologies
    AirChanges_MechanicalVentilation(null, MonthlyMean,
            SAPWorksheetSection.Ventilation.subCell(23, 'a'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Unsupported_Technology),

    AirChanges_ExhaustAirHeatPump(null, MonthlyMean,
            SAPWorksheetSection.Ventilation.subCell(23, 'b'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.Unsupported_Technology),

    AirChanges_BalancedWithHeatRecovery(null, MonthlyMean,
            SAPWorksheetSection.Ventilation.subCell(23, 'c'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.Unsupported_Technology),

    // The only type of ventilation we use is natural
    Ventilation_BalancedMechanicalWithHeatRecovery(null, MonthlyMean,
            SAPWorksheetSection.Ventilation.subCell(24, 'a'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.Unsupported_Technology),

    Ventilation_BalancedMechanicalWithoutHeatRecovery(null, MonthlyMean,
            SAPWorksheetSection.Ventilation.subCell(24, 'b'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.Unsupported_Technology),

    Ventilation_WholeHouseExtractOrPositiveFromOutside(null, MonthlyMean,
            SAPWorksheetSection.Ventilation.subCell(24, 'c'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.Unsupported_Technology),

    Ventilation_NaturalOrPositiveFromLoft(AirChange_per_Hour, MonthlyMean,
            SAPWorksheetSection.Ventilation.subCell(24, 'd'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // We ignore the note about Appendix Q here
    AirChanges_Effective(AirChange_per_Hour, MonthlyMean,
            SAPWorksheetSection.Ventilation.cell(25),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    /**
     * Heat losses and heat loss parameter
     */
    // For external fabric elements, this is the A x U (W/K) columns
    HeatLossCoefficient_DoorsSolid(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(26),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatLossCoefficient_DoorsSemiGlazed(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(26, 'a'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatLossCoefficient_Window(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(27),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // Extra breakdown of cell 27 as requested by BRE.
    HeatLossCoefficient_Window_UPVC(Watt_per_Kelvin, Annual,
            SAPLocation.None,
            BREDEMLocation.None,
            DefaultValue.None),

    HeatLossCoefficient_Window_Wood(Watt_per_Kelvin, Annual,
            SAPLocation.None,
            BREDEMLocation.None,
            DefaultValue.None),

    HeatLossCoefficient_Window_Metal(Watt_per_Kelvin, Annual,
            SAPLocation.None,
            BREDEMLocation.None,
            DefaultValue.None),

    // We don't have any roof windows in the NHM
    HeatLossCoefficient_Window_Roof(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(27, 'a'),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    HeatLossCoefficient_BasementFloor(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(28),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatLossCoefficient_GroundFloor(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(28, 'a'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatLossCoefficient_ExposedFloor(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(28, 'b'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // Basement walls are treated as External walls in the NHM
    HeatLossCoefficient_BasementWall(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(29),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    HeatLossCoefficient_ExternalWall(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(29, 'a'),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatLossCoefficient_Roof(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(30),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    AreaExternal(MetreSquared, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(31),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // For internal and part fabric elements, this is the net area (m^2) column
    AreaPartyWall(MetreSquared, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(32), null, DefaultValue.None),

    AreaPartyFloor(MetreSquared, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'a'), null, DefaultValue.None),

    AreaPartyCeiling(MetreSquared, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'b'), null, DefaultValue.None),

    AreaInternalWall(MetreSquared, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'c'), null, DefaultValue.None),

    // In the NHM, we treat Internal Floors and Ceilings as if they were Party Floors and Ceilings.
    // This should make no difference practically.
    AreaInternalFloor(MetreSquared, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'd'),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    AreaInternalCeiling(MetreSquared, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'e'),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    FabricHeatLoss(Watt_per_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(33),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatCapacity(null, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(34),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.TMP_Complex_Unsupported),

    // Uses indicative values from Table 1f
    ThermalMassParameter(Kilo_Joule_per_MetreSquared_Kelvin, Annual,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(35),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // Uses simple calculation
    ThermalBridges(Watt_per_Kelvin, MonthlyMean, // Should be Annual
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(36),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    FabricLossTotal(Watt_per_Kelvin, MonthlyMean, // Should be Annual
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(37),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    VentilationHeatLoss(Watt_per_Kelvin, MonthlyMean,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(38),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatTransferCoefficient(Watt_per_Kelvin, MonthlyMean,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(39),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatLossParameter(Watt_per_MetreSquared_Kelvin, MonthlyMean,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(40),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    DaysInMonth(Day_per_Month, MonthlySum,
            SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(41),
            BREDEMLocation.NotDetermined,
            new DefaultValue(1)),

    /**
     * Water Heating
     */
    Occupancy(Count, Annual,
            SAPWorksheetSection.Water_Heating.cell(42),
            BREDEMSection.LightsAppliancesAndCooking.step('a', "N"),
            DefaultValue.None),

    // In SAP, this is annual.
    WaterHeating_Usage_Initial(Litre_per_Day, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(43),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // WEIRD: This is summed over the year,
    WaterHeating_Usage_MonthAdjusted(Litre_per_Day, MonthlySum,
            SAPWorksheetSection.Water_Heating.cell(44),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    WaterHeating_EnergyContent(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Water_Heating.cell(45),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    WaterHeating_DistributionLoss(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Water_Heating.cell(46),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // In SAP, this is annual
    WaterHeating_StorageVolume(MetreCubed, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(47),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // 48,49 and 50 ignored as we don't know manufacturer's declared loss factor

    // In SAP, this is annual
    WaterHeating_StorageLossFactor(Kilo_Watt_Hour_per_Litre_per_Day, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(51),
            BREDEMLocation.NotDetermined,
            new DefaultValue(Double.NaN)),

    WaterHeating_StorageVolumeFactor(Dimensionless, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(52),
            BREDEMLocation.NotDetermined,
            new DefaultValue(Double.NaN)),

    WaterHeating_StorageTemperatureFactor(Dimensionless, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(53),
            BREDEMLocation.NotDetermined,
            new DefaultValue(Double.NaN)),

    WaterHeating_StorageLosses_Daily_Calculated(Kilo_Watt_Hour_per_Day, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(54),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    WaterHeating_StorageLosses_Daily(Kilo_Watt_Hour_per_Day, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(55),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    WaterHeating_StorageLosses_Monthly(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(56),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    WaterHeating_StorageLosses_Monthly_ExcludeSolar(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(57),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    // There's no step 58 in the SAP worksheet

    WaterHeating_PrimaryCircuitLoss_Monthly(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(59),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // There's no step 60 in the SAP worksheet


    WaterHeating_CombiLoss_Monthly(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(61),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    WaterHeating_TotalHeat_Monthly_BeforeSolar(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(62),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    WaterHeating_Solar(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(63),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    WaterHeating_TotalHeat_Monthly(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Water_Heating.cell(64),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.WaterHeating_TotalHeat_ExcludeSolar_Not_Recorded),

    /**
     * Internal Gains (watts)
     */
    Gains_HotWater_Monthly(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Water_Heating.cell(65),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),


    Gains_Metabolic(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(66),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Gains_Lighting(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(67),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Gains_Appliances(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(68),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Gains_Cooking(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(69),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Gains_PumpsAndFans(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(70),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // These are actually losses.
    Gains_Evaporation(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(71),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // This is the same as the other hot water gains in the NHM. In SAP, this represents a conversion.
    Gains_HotWater(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(72),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Gains_Internal(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Internal.cell(73),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    /**
     * Solar Gains (watts)
     */
    // Solar window gains (cells 74 to 81) omitted as they are difficult to get out of the NHM.

    // We don't support roof windows in the NHM
    Gains_Solar_Roof(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Solar.cell(82),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    Gains_Solar(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Solar.cell(83),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Gains(Watt, MonthlyMean,
            SAPWorksheetSection.Gains_Solar.cell(84),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    /**
     * Mean Internal Temperature
     */
    DemandTemperature_LivingArea(Centigrade, Annual,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(85),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    GainsUtilisation_LivingArea(DimensionlessProportion, MonthlyMean,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(86),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    MeanInternalTemperature_LivingArea(Centigrade, MonthlyMean,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(87),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    DemandTemperature_RestOfDwelling(Centigrade, MonthlyMean,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(88),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    GainsUtilisation_RestOfDwelling(DimensionlessProportion, MonthlyMean,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(89),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    MeanInternalTemperature_RestOfDwelling(Centigrade, MonthlyMean,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(90),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    LivingAreaFraction(DimensionlessProportion, Annual,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(91),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    MeanInternalTemperature_Unadjusted(Centigrade, MonthlyMean,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(92),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    MeanInternalTemperature(Centigrade, MonthlyMean,
            SAPWorksheetSection.Mean_Internal_Temperature.cell(93),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    /**
     * Space Heating
     */
    GainsUtilisation(DimensionlessProportion, MonthlyMean,
            SAPWorksheetSection.Space_Heating.cell(94),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    Gains_Useful(Watt, MonthlyMean,
            SAPWorksheetSection.Space_Heating.cell(95),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    ExternalTemperature(Centigrade, MonthlyMean,
            SAPWorksheetSection.Space_Heating.cell(96),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    HeatLossRate(Watt, MonthlyMean,
            SAPWorksheetSection.Space_Heating.cell(97),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    SpaceHeating(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Space_Heating.cell(98),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // Feeds into step 109, which we aren't doing.
    SpaceHeating_PerFloorArea(Kilo_Watt_Hour_per_MetreSquared_per_Month, MonthlySum,
            SAPWorksheetSection.Space_Heating.cell(99),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.FabricEnergyEfficiencyNotSupported),

    /**
     * Space Cooling (not implemented)
     * If we were to implement this, we probably need to duplicate a lot of the stuff above. Ugh.
     */
    HeatLossRate_Cooling(Watt, MonthlyMean,
            SAPWorksheetSection.Space_Cooling.cell(100),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    LossUtilisation(DimensionlessProportion, MonthlyMean,
            SAPWorksheetSection.Space_Cooling.cell(101),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    Loss_Useful(Watt, MonthlyMean,
            SAPWorksheetSection.Space_Cooling.cell(102),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    Gains_Cooling(Watt, MonthlyMean,
            SAPWorksheetSection.Space_Cooling.cell(103),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    SpaceCooling_Continuous(Kilo_Watt_Hour_per_Month, MonthlyMean,
            SAPWorksheetSection.Space_Cooling.cell(104),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    CooledFraction(DimensionlessProportion, Annual,
            SAPWorksheetSection.Space_Cooling.cell(105),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    // This seems like a mistake in SAP? Why are summing up this proportion?
    CoolingIntermittencyFactor(DimensionlessProportion, MonthlySum,
            SAPWorksheetSection.Space_Cooling.cell(106),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    SpaceCooling(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Space_Cooling.cell(107),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    SpaceCooling_PerFloorArea(Kilo_Watt_Hour_per_MetreSquared_per_Month, MonthlySum,
            SAPWorksheetSection.Space_Cooling.cell(108),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    /**
     * Fabric Energy efficiency
     */
    FabricEnergyEfficiency(Kilo_Watt_Hour_per_MetreSquared_per_Month, MonthlySum,
            SAPWorksheetSection.Fabric_Energy_Efficiency.cell(109),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.FabricEnergyEfficiencyNotSupported),

    // Steps 110 to 200 don't exist in the SAP worksheet.

    /**
     * Energy Requirements
     */
    SpaceHeating_Fraction_Secondary(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(201),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    SpaceHeating_Fraction_Main(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(202),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // Always 0% - we never use any heat from system 2 since it isn't supported.
    SpaceHeating_FractionWithinMainSystem(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(203),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0d)),

    // The same as SpaceHeatingFraction_Main
    SpaceHeating_Fraction_Main_System1(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(204),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // Always 0%, because main space heating system 2 isn't supported
    SpaceHeating_Fraction_Main_System2(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(205),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0d)),

    /**
     * Ignores the efficiency differrence from Hybrid heat pumps
     */
    SpaceHeating_Efficiency_Main_System1(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(206),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    SpaceHeating_Efficency_Main_System2(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(207),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceHeating_Main_System2_Unsupported),

    SpaceHeating_Efficiency_Secondary(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(208),
            BREDEMLocation.NotDetermined,
            new DefaultValue(Double.NaN)),

    SpaceCooling_EfficencyRatio(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements.cell(209),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.SpaceCooling_Unsupported),

    // Step 210 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Main_System1(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(211),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.InEnergyCalculatorResult),

    // Step 212 doesn't exist in the SAP worksheet

    // Always 0, because main space heating system 2 isn't supported.
    Energy_SpaceHeating_Fuel_Main_system2(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(213),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    // Step 214 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Secondary(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(215),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.InEnergyCalculatorResult),

    // This is just 64, summed up over the year
    Energy_WaterHeating_TotalHeat_Annual(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(216),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.WaterHeating_TotalHeat_ExcludeSolar_Not_Recorded),

    WaterHeating_Efficiency(DimensionlessProportion, MonthlyMean,
            SAPWorksheetSection.Energy_Requirements.cell(217),
            BREDEMLocation.NotDetermined,
            DefaultValue.None),

    // Step 218 doesn't exist in the SAP worksheet

    Energy_WaterHeatingFuel(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(219),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.InEnergyCalculatorResult),

    // Step 220 doesn't exist in the SAP worksheet

    Energy_SpaceCooling(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(221),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0d)),

    // The worksheet confusingly repeats 211, 213, 215, 219 and 221 at this point. Ignore this.

    // Steps 222 to 229 don't exist in the SAP worksheet.

    // The NHM doesn't include mechanical ventilation fans
    PumpsFansAndKeepHot_MechanicalVentilationFans(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'a'),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    PumpsFansAndKeepHot_WarmAirFans(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'b'), BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    PumpsFansAndKeepHot_WaterPump(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'c'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    PumpsFansAndKeepHot_OilBoilerPump(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'd'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    // This can also be a heat pump fan flue
    PumpsFansAndKeepHot_BoilerFlueFan(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'e'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    // In the NHM, the keep hot facility uses the same fuel as the combi boiler
    PumpsFansAndKeepHot_KeepHot(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'f'),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    PumpsFansAndKeepHot_SolarWaterHeatingPump(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'g'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    // Storage WWHRS pump not implemented
    PumpsFansAndKeepHot_StorageWWHRSPump(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(230, 'h'),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    PumpsFansAndKeepHot(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(231),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.PumpsFansAndKeepHotSum_Unsupported),

    Lighting(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(232),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.InEnergyCalculatorResult),

    Generation_PhotoVoltaic(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(233),
            BREDEMLocation.NotDetermined,
            new DefaultValue(0)),

    // Wind turbines not implemented in the NHM
    Generation_WindTurbines(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(234),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    // MicroCHP not implemented in the NHM
    Generation_MicroCHP(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(235),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    // Hydro generators not implemented in the NHM
    Generation_Hydro(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.subCell(235, 'a'),
            BREDEMLocation.NotDetermined,
            new NotSupportedValuePlaceholder(0)),

    Total_Energy(Kilo_Watt_Hour_per_Month, MonthlySum,
            SAPWorksheetSection.Energy_Requirements.cell(238),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.InEnergyCalculatorResult),

    /**
     * Fuel Costs (happens outside energy calculator)
     */
    Cost_SpaceHeating_Main_System1(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(240),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_SpaceHeating_Main_System2(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(241),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_SpaceHeating_Secondary(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(242),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_WaterHeating_ElecHighRateFraction(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(243),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_WaterHeating_ElecLowRateFraction(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(244),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_WaterHeating_ElecHighRate(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(245),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_waterHeating_ElecLowRate(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(246),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_WaterHeating_NonElec(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(247),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_SpaceCooling(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(248),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_PumpsFansAndKeepHot(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(249),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_Lighting(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(250),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_StandingCharges(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(251),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Cost_Generation(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(252),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    // Appendix Q steps 253 and 254 skipped

    Cost(PoundSterling, Annual,
            SAPWorksheetSection.Fuel_Costs.cell(255),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    /**
     * SAP Rating (happens outside energy calculator)
     */
    EnergyCostDeflator(Unknown, Annual,
            SAPWorksheetSection.SAP_Rating.cell(256),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    EnergyCostFactor(Unknown, Annual,
            SAPWorksheetSection.SAP_Rating.cell(257),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    SAPRating(Dimensionless, Annual,
            SAPWorksheetSection.SAP_Rating.cell(258),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    /**
     * CO2 Emissions (happens outside energy calculator)
     * The units are unknown here, because they depend on user inputs.
     */
    Emissions_SpaceHeating_Main_System1(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(261),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_SpaceHeating_Main_System2(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(262),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_SpaceHeating_Secondary(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(263),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_WaterHeating(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(264),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_SpaceAndWaterHeating(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(265),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    // Always 0 - space cooling not implemented
    Emissions_SpaceCooling(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(266),
            BREDEMLocation.NotDetermined,
            DefaultValue.None, SkipReason.Outside_Energy_Calculation),

    Emissions_PumpsFansAndKeepHot(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(267),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_Lighting(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(268),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_Generation(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(269),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    // Appendix Q steps 270 and 271 skipped

    Emissions(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(272),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_PerArea(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(273),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    Emissions_EIRating(Unknown, Annual,
            SAPWorksheetSection.Emissions.cell(274),
            BREDEMLocation.NotDetermined,
            DefaultValue.None,
            SkipReason.Outside_Energy_Calculation),

    /**
     * Primary Energy - this section ignored as it is not implemented in the NHM.
     */

    /**
     * Community Heating sections - this section of the worksheet is ignored as it is not implemented in the NHM.
     * We have community heating, but we do not divide it up by the different heat sources.
     *
     * I have, however, included the usage factors.
     */
    Community_ChargingFactor_SpaceHeating(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements_Community.cell(305),
            BREDEMLocation.NotDetermined,
            new DefaultValue(Double.NaN)),

    Community_ChargingFactor_WaterHeating(DimensionlessProportion, MonthlyMean,
            SAPWorksheetSection.Energy_Requirements_Community.subCell(305, 'a'),
            BREDEMLocation.NotDetermined,
            new DefaultValue(Double.NaN)),

    Community_DistributionLossFactor(DimensionlessProportion, Annual,
            SAPWorksheetSection.Energy_Requirements_Community.cell(306),
            BREDEMLocation.NotDetermined,
            new DefaultValue(Double.NaN))
    ;

    public static class SkipReason {
        static final String Outside_Energy_Calculation = "Annual value not used.";
        static final String SpaceHeating_Main_System2_Unsupported = "Using a second main space heating system is not supported in the NHM.";
        static final String SpaceCooling_Unsupported = "Space cooling systems are not supported in the NHM.";
        static final String TMP_Complex_Unsupported = "The NHM always uses the simplified Table 1f lookup to calculate the Thermal Mass Parameter, as requested by BEIS.";
        static final String AirPressureTests_Unsupported = "The NHM always uses the estimation method to calculate permeability of the envelope, as the English Housing Survey doesn't include data from air pressure tests.";
        static final String Unsupported_Technology = "The NHM does not support this technology.";
        static final String FabricEnergyEfficiencyNotSupported = "The NHM does not perform the fabric energy efficiency calculation.";
        static final String InEnergyCalculatorResult = "We don't want to output these fields in our report for now. They are fiddly to get, and are already available in IEnergyCalculationResult.";
        static final String PumpsFansAndKeepHotSum_Unsupported = "The sum of all the pumps fans and keep hot is not supported by the SAP steps mechanism. To get this value, add up all the sub-steps in your scenario.";
        static final String WaterHeating_TotalHeat_ExcludeSolar_Not_Recorded = "This is hot water demand + all losses - demand met by solar. It's cells (64) and (216). It's difficult to get out of the NHM. Calculate it as (62) - (63) instead.";
    }

    public final Units conversion;
    public final Period period;
    public final SAPLocation sapLocation;
    public final BREDEMLocation bredemLocation;

    // The value to output if this is not recorded.
    public final DefaultValue defaultValue;

    public final String skipReason;

    EnergyCalculationStep(Units conversion, Period period, SAPLocation sapLocation, BREDEMLocation bredemLocation, DefaultValue defaultValue) {
        this(conversion, period, sapLocation, bredemLocation, defaultValue, null);
    }

    EnergyCalculationStep(Units conversion, Period period, SAPLocation sapLocation, BREDEMLocation bredemLocation, DefaultValue defaultValue, String skipReason) {
        this.conversion = conversion;
        this.period = period;
        this.sapLocation = sapLocation;
        this.bredemLocation = bredemLocation;
        this.defaultValue = defaultValue;
        this.skipReason = skipReason;
    }

    public boolean isSkipped() {
        return skipReason != null;
    }

    public boolean isMonthly() { return period.isMonthly(); }

    public double convertMonthly(List<Double> data, int month) {
        return conversion.convert(
                period.getMonth(data, month),
                MonthType.values()[month + 1].getStandardDays()
            );
    }

    public double convertAnnual(List<Double> data) {
        if (period.isMonthly()) {
            final ImmutableList.Builder<Double> convertedData = ImmutableList.builder();
            for (int i = 0; i < 12; i++) {
                convertedData.add(conversion.convert(
                        data.get(i),
                        MonthType.values()[i].getStandardDays()
                        )
                );
            }
            return period.getAnnual(convertedData.build());
        } else {
            return period.getAnnual(ImmutableList.of(
                    conversion.convert(
                            data.get(0),
                            null
                    )
            ));
        }
    }

    public boolean hasDefault() {
        return defaultValue.exists();
    }

    public List<Double> getDefault() {
        if (hasDefault()) {
            if (period.isMonthly()) {
                return defaultValue.getMonthly();
            } else {
                return defaultValue.getAnnual();
            }
        } else {
            throw new UnsupportedOperationException("Tried to call getDefault on an energy calculation step which has no default.");
        }
    }
}
