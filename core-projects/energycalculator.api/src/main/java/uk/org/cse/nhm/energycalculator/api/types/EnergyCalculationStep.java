package uk.org.cse.nhm.energycalculator.api.types;

import java.util.Optional;

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
    // TODO: areas of individual floors
    FloorArea_Basement(Units.MetreSquared, Period.Annual, SAPWorksheetSection.Dimensions.subCell(1, 'a'), null),
    FloorArea_Ground(Units.MetreSquared, Period.Annual, SAPWorksheetSection.Dimensions.subCell(1, 'b'), null),
    FloorArea_First(Units.MetreSquared, Period.Annual, SAPWorksheetSection.Dimensions.subCell(1, 'c'), null),
    FloorArea_Second(Units.MetreSquared, Period.Annual, SAPWorksheetSection.Dimensions.subCell(1, 'd'), null),
    // BRE lump all of the upper floors together so that they can fit into a spreadsheet with a fixed number of columns.
    FloorArea_Third_and_Above(Units.MetreSquared, Period.Annual, SAPWorksheetSection.Dimensions.subCell(1, 'e'), null),

    // v2a to 2n (storey heights) and v3a to 3n (storey volumes) omitted, because they weren't present in BRE's sample spreadsheet.

    TotalFloorArea(Units.MetreSquared, Period.Annual, SAPWorksheetSection.Dimensions.cell(4), BREDEMSection.LightsAppliancesAndCooking.var("TFA")),
    DwellingVolume(Units.MetreCubed, Period.Annual, SAPWorksheetSection.Dimensions.cell(5), null),

    /**
     * Ventilation
     */
    ChimneyVentilation(Units.MetreCubed_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.subCell(6, 'a'), null),
    OpenFluesVentilation(Units.MetreCubed_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.subCell(6, 'b'), null),
    IntermittentFansVentilation(Units.MetreCubed_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.subCell(7, 'a'), null),
    PassiveVentsVentilation(Units.MetreCubed_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.subCell(7, 'b'), null),
    FluelessGasFiresVentilation(Units.MetreCubed_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.subCell(7 ,'c'), null),

    AirChanges_ChimneysFluesFansAndPSVs(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(8), null),
    Storeys(Units.Count, Period.Annual, SAPWorksheetSection.Ventilation.cell(9), null),
    InfiltrationAdditionalStackEffect(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(10), null),
    InfiltrationStructural(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(11), null),
    InfiltrationGroundFloor(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(12), null),
    InfiltrationAbsenceOfDraughtLobby(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(13), null),

    // TODO: this is percent in the worksheet?
    ProportionWindowsDraughtProofed(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Ventilation.cell(14), null),
    InfiltrationWindows(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(15), null),
    InfiltrationRate_Initial(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(16), null),

    AirPermabilityValue(null, Period.Annual, SAPWorksheetSection.Ventilation.cell(17), null, SkipReason.AirPressureTests_Unsupported),

    InfiltrationRateMaybePressureTest(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(18), null),
    SidesSheltered(Units.Count, Period.Annual, SAPWorksheetSection.Ventilation.cell(19), null),
    ShelterFactor(Units.Dimensionless, Period.Annual, SAPWorksheetSection.Ventilation.cell(20), null),
    InfiltrationRate_IncludingShelter(Units.AirChange_per_Hour, Period.Annual, SAPWorksheetSection.Ventilation.cell(21), null),

    AverageWindSpeed(Units.Metre_per_Second, Period.MonthlyMean, SAPWorksheetSection.Ventilation.cell(22), null),
    WindFactor(Units.Dimensionless, Period.MonthlyMean, SAPWorksheetSection.Ventilation.cell(23), null),
    InfiltrationRate_IncludingShelterAndWind(Units.AirChange_per_Hour, Period.MonthlyMean, SAPWorksheetSection.Ventilation.cell(24), null),

    SiteExposureFactor(Units.Dimensionless, Period.Annual, null, BREDEMSection.HeatLoss.var("ShE")),
    InfiltrationRate_IncludingShelterAndWindAndSiteExposure(Units.AirChange_per_Hour, Period.MonthlyMean, null, BREDEMSection.HeatLoss.step('E', "Lsub,m")),

    // Unimplemented technologies
    AirChanges_MechanicalVentilation(null, Period.MonthlyMean, SAPWorksheetSection.Ventilation.subCell(23, 'a'), null, SkipReason.Unsupported_Technology),
    AirChanges_ExhaustAirHeatPump(null, Period.MonthlyMean, SAPWorksheetSection.Ventilation.subCell(23, 'b'), null, SkipReason.Unsupported_Technology),
    AirChanges_BalancedWithHeatRecovery(null, Period.MonthlyMean, SAPWorksheetSection.Ventilation.subCell(23, 'c'), null, SkipReason.Unsupported_Technology),

    // The only type of ventilation we use is natural
    Ventilation_BalancedMechanicalWithHeatRecovery(null, Period.MonthlyMean, SAPWorksheetSection.Ventilation.subCell(24, 'a'), null, SkipReason.Unsupported_Technology),
    Ventilation_BalancedMechanicalWithoutHeatRecovery(null, Period.MonthlyMean, SAPWorksheetSection.Ventilation.subCell(24, 'b'), null, SkipReason.Unsupported_Technology),
    Ventilation_WholeHouseExtractOrPositiveFromOutside(null, Period.MonthlyMean, SAPWorksheetSection.Ventilation.subCell(24, 'c'), null, SkipReason.Unsupported_Technology),
    Ventilation_NaturalOrPositiveFromLoft(Units.AirChange_per_Hour, Period.MonthlyMean, SAPWorksheetSection.Ventilation.subCell(24, 'd'), null),

    // We ignore the note about Appendix Q here
    AirChanges_Effective(Units.AirChange_per_Hour, Period.MonthlyMean, SAPWorksheetSection.Ventilation.cell(25), null),

    /**
     * Heat losses and heat loss parameter
     */
    // For external fabric elements, this is the A x U (W/K) columns
    // TODO: record doors
    HeatLossCoefficient_DoorsSolid(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(26), null),
    HeatLossCoefficient_DoorsSemiGlazed(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(26, 'a'), null),
    HeatLossCoefficient_Window(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(27), null),
    // Extra breakdown of cell 27 as requested by BRE.
    // TODO; record windows aggregated by frame type
    HeatLossCoefficient_Window_UPVC_Or_Wood(Units.Watt_per_Kelvin, Period.Annual, null, null),
    HeatLossCoefficient_Window_Metal(Units.Watt_per_Kelvin, Period.Annual, null, null),
    // We don't have any roof windows in the NHM
    HeatLossCoefficient_Window_Roof(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(27, 'a'), null, 0d),
    // TODO: record floors
    HeatLossCoefficient_BasementFloor(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(28), null),
    HeatLossCoefficient_GroundFloor(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(28, 'a'), null),
    HeatLossCoefficient_ExposedFLoor(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(28, 'b'), null),
    // Basement walls are treated as External walls in the NHM
    HeatLossCoefficient_BasementWall(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(29), null, 0d),
    HeatLossCoefficient_ExternalWall(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(29, 'a'), null),
    HeatLossCoefficient_Roof(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(30), null),

    AreaExternal(Units.MetreSquared, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(31), null),

    // For internal and part fabric elements, this is the net area (m^2) column
    AreaPartyWall(Units.MetreSquared, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(32), null),
    AreaPartyFloor(Units.MetreSquared, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'a'), null),
    AreaPartyCeiling(Units.MetreSquared, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'b'), null),
    AreaInternalWall(Units.MetreSquared, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'c'), null),
    // In the NHM, we treat Internal Floors and Ceilings as if they were Party Floors and Ceilings.
    // This should make no difference practically.
    AreaInternalFloor(Units.MetreSquared, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'd'), null),
    AreaInternalCeiling(Units.MetreSquared, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.subCell(32, 'e'), null),

    FabricHeatLoss(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(33), null),
    HeatCapacity(null, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(34), null, SkipReason.TMP_Complex_Unsupported),
    // Uses indicative values from Table 1f
    ThermalMassParameter(Units.Kilo_Joule_per_MetreSquared_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(35), null),
    // Uses simple calculation
    ThermalBridges(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(36), null),
    FabricLossTotal(Units.Watt_per_Kelvin, Period.Annual, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(37), null),
    VentilationHeatLoss(Units.Watt_per_Kelvin, Period.MonthlyMean, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(38), null),
    HeatTransferCoefficient(Units.Watt_per_Kelvin, Period.MonthlyMean, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(39), null),
    HeatLossParameter(Units.Watt_per_MetreSquared_Kelvin, Period.MonthlyMean, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(40), null),
    DaysInMonth(Units.Count, Period.MonthlySum, SAPWorksheetSection.HeatLosses_and_Heat_Loss_Parameter.cell(41), null),

    /**
     * Water Heating
     */
    Occupancy(Units.Count, Period.Annual, SAPWorksheetSection.Water_Heating.cell(42),
            BREDEMSection.LightsAppliancesAndCooking.step('a', "N")),

    // TODO: In SAP, this is annual.
    WaterHeating_Usage_Initial(Units.Litre_per_Day, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(43), null),

    // TODO: in SAP, this is summed over the year? Weird?
    WaterHeating_Usage_MonthAdjusted(Units.Litre_per_Day, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(44), null),

    WaterHeating_EnergyContent(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Water_Heating.cell(45), null),
    WaterHeating_DistributionLoss(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Water_Heating.cell(46), null),

    // TODO: in SAP, this is annual
    WaterHeating_StorageVolume(Units.MetreCubed, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(47), null),

    // 48,49 and 50 ignored as we don't know manufacturer's declared loss factor

    // TODO: in SAP, this is annual
    WaterHeating_StorageLossFactor(Units.Kilo_Watt_Hour_per_Litre_per_Day, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(51), null),
    WaterHeating_StorageVolumeFactor(Units.Dimensionless, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(52), null),
    WaterHeating_StorageTemperatureFactor(Units.Dimensionless, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(53), null),
    WaterHeating_StorageLosses_Daily_Calculated(Units.Kilo_Watt_Hour_per_Day, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(54), null),
    WaterHeating_StorageLosses_Daily(Units.Kilo_Watt_Hour_per_Day, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(55), null),
    WaterHeating_StorageLosses_Monthly(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(56), null),

    WaterHeating_StorageLosses_Monthly_ExcludeSolar(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(57), null),

    // There's no step 58 in the SAP worksheet

    WaterHeating_PrimaryCircuitLoss_Monthly(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(59), null),

    // There's no step 60 in the SAP worksheet


    WaterHeating_CombiLoss_Monthly(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(61), null),
    WaterHeating_TotalHeat_Monthly_BeforeSolar(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(62), null),
    WaterHeating_Solar(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(63), null),
    WaterHeating_TotalHeat_Monthly(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Water_Heating.cell(64), null),

    /**
     * Internal Gains (watts)
     */
    Gains_HotWater_Monthly(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Water_Heating.cell(65), null),
    Gains_Metabolic(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(66), null),
    Gains_Lighting(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(67), null),
    Gains_Appliances(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(68), null),
    Gains_Cooking(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(69), null),
    Gains_PumpsAndFans(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(70), null),
    // These are actually losses.
    Gains_Evaporation(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(71), null),
    // This is the same as the other hot water gains in the NHM. In SAP, this represents a conversion.
    Gains_HotWater(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(72), null),

    Gains_Internal(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Internal.cell(73), null),

    /**
     * Solar Gains (watts)
     */
    // Solar window gains (cells 74 to 81) omitted as they are difficult to get out of the NHM.

    // We don't support roof windows in the NHM
    Gains_Solar_Roof(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Solar.cell(82), null, 0d),

    Gains_Solar(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Solar.cell(83), null),

    Gains(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Gains_Solar.cell(84), null),

    /**
     * Mean Internal Temperature
     */
    DemandTemperature_LivingArea(Units.Centigrade, Period.Annual, SAPWorksheetSection.Mean_Internal_Temperature.cell(85), null),
    GainsUtilisation_LivingArea(Units.DimensionlessProportion, Period.MonthlyMean, SAPWorksheetSection.Mean_Internal_Temperature.cell(86), null),
    MeanInternalTemperature_LivingArea(Units.Centigrade, Period.MonthlyMean, SAPWorksheetSection.Mean_Internal_Temperature.cell(87), null),

    DemandTemperature_RestOfDwelling(Units.Centigrade, Period.MonthlyMean, SAPWorksheetSection.Mean_Internal_Temperature.cell(88), null),
    GainsUtilisation_RestOfDwelling(Units.DimensionlessProportion, Period.MonthlyMean, SAPWorksheetSection.Mean_Internal_Temperature.cell(89), null),
    MeanInternalTemperature_RestOfDwelling(Units.Centigrade, Period.MonthlyMean, SAPWorksheetSection.Mean_Internal_Temperature.cell(90), null),

    LivingAreaFraction(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Mean_Internal_Temperature.cell(91), null),
    MeanInternalTemperature_Unadjusted(Units.Centigrade, Period.MonthlyMean, SAPWorksheetSection.Mean_Internal_Temperature.cell(92), null),
    MeanInternalTemperature(Units.Centigrade, Period.MonthlyMean, SAPWorksheetSection.Mean_Internal_Temperature.cell(93), null),

    /**
     * Space Heating
     */
    GainsUtilisation(Units.DimensionlessProportion, Period.MonthlyMean, SAPWorksheetSection.Space_Heating.cell(94), null),
    Gains_Useful(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Space_Heating.cell(95), null),
    ExternalTemperature(Units.Centigrade, Period.MonthlyMean, SAPWorksheetSection.Space_Heating.cell(96), null),
    HeatLossRate(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Space_Heating.cell(97), null),
    SpaceHeating(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Space_Heating.cell(98), null),
    // Feeds into step 109, which we aren't doing.
    SpaceHeating_PerFloorArea(Units.Kilo_Watt_Hour_per_MetreSquared_per_Month, Period.MonthlySum, SAPWorksheetSection.Space_Heating.cell(99), null, SkipReason.FabricEnergyEfficiencyNotSupported),

    /**
     * Space Cooling (not implemented)
     * If we were to implement this, we probably need to duplicate a lot of the stuff above. Ugh.
     */
    HeatLossRate_Cooling(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Space_Cooling.cell(100), null, SkipReason.SpaceCooling_Unsupported),
    LossUtilisation(Units.DimensionlessProportion, Period.MonthlyMean, SAPWorksheetSection.Space_Cooling.cell(101), null, SkipReason.SpaceCooling_Unsupported),
    Loss_Useful(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Space_Cooling.cell(102), null, SkipReason.SpaceCooling_Unsupported),
    Gains_Cooling(Units.Watt, Period.MonthlyMean, SAPWorksheetSection.Space_Cooling.cell(103), null, SkipReason.SpaceCooling_Unsupported),
    SpaceCooling_Continuous(Units.Kilo_Watt_Hour_per_Month, Period.MonthlyMean, SAPWorksheetSection.Space_Cooling.cell(104), null, SkipReason.SpaceCooling_Unsupported),
    CooledFraction(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Space_Cooling.cell(105), null, SkipReason.SpaceCooling_Unsupported),
    // This seems like a mistake in SAP? Why are summing up this proportion?
    CoolingIntermittencyFactor(Units.DimensionlessProportion, Period.MonthlySum, SAPWorksheetSection.Space_Cooling.cell(106), null, SkipReason.SpaceCooling_Unsupported),
    SpaceCooling(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Space_Cooling.cell(107), null, SkipReason.SpaceCooling_Unsupported),
    SpaceCooling_PerFloorArea(Units.Kilo_Watt_Hour_per_MetreSquared_per_Month, Period.MonthlySum, SAPWorksheetSection.Space_Cooling.cell(108), null, SkipReason.SpaceCooling_Unsupported),

    /**
     * Fabric Energy efficiency
     */
    FabricEnergyEfficiency(Units.Kilo_Watt_Hour_per_MetreSquared_per_Month, Period.MonthlySum, SAPWorksheetSection.Fabric_Energy_Efficiency.cell(109), null, SkipReason.FabricEnergyEfficiencyNotSupported),

    // Steps 110 to 200 don't exist in the SAP worksheet.

    /**
     * Energy Requirements
     */
    SpaceHeating_Fraction_Secondary(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(201), null),

    SpaceHeating_Fraction_Main(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(202), null),

    // Always 0% - we never use any heat from system 2 since it isn't supported.
    SpaceHeating_FractionWithinMainSystem(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(203), null, 0d),

    // The same as SpaceHeatingFraction_Main
    SpaceHeating_Fraction_Main_System1(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(204), null),

    // Always 0%, because main space heating system 2 isn't supported
    SpaceHeating_Fraction_Main_System2(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(205), null, 0d),

    SpaceHeating_Efficiency_Main_System1(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(206), null),

    SpaceHeating_Efficency_Main_System2(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(207), null, SkipReason.SpaceHeating_Main_System2_Unsupported),

    SpaceHeating_Efficiency_Secondary(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(208), null),

    SpaceCooling_EfficencyRatio(Units.DimensionlessProportion, Period.Annual, SAPWorksheetSection.Energy_Requirements.cell(209), null, SkipReason.SpaceCooling_Unsupported),

    // Step 210 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Main_System1(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(211), null, SkipReason.InEnergyCalculatorResult),

    // Step 212 doesn't exist in the SAP worksheet

    // Always 0, because main space heating system 2 isn't supported.
    Energy_SpaceHeating_Fuel_Main_system2(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(213), null, 0d),

    // Step 214 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Secondary(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(215), null, SkipReason.InEnergyCalculatorResult),

    Energy_WaterHeating_TotalHeat_Annual(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(216), null),

    WaterHeating_Efficiency(Units.DimensionlessProportion, Period.MonthlyMean, SAPWorksheetSection.Energy_Requirements.cell(217), null),

    // Step 218 doesn't exist in the SAP worksheet

    Energy_WaterHeatingFuel(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(219), null, SkipReason.InEnergyCalculatorResult),

    // Step 220 doesn't exist in the SAP worksheet

    Energy_SpaceCooling(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(221), null, 0d),

    // The worksheet confusingly repeats 211, 213, 215, 219 and 221 at this point. Ignore this.

    // Steps 222 to 229 don't exist in the SAP worksheet.

    // The NHM doesn't include mechanical ventilation fans
    PumpsFansAndKeepHot_MechanicalVentilationFans(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'a'), null, 0d),
    PumpsFansAndKeepHot_WarmAirFans(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'b'), null),
    PumpsFansAndKeepHot_WaterPump(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'c'), null),
    PumpsFansAndKeepHot_OilBoilerPump(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'd'), null),
    // This can also be a heat pump fan flue
    PumpsFansAndKeepHot_BoilerFlueFan(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'e'), null),
    // In the NHM, the keep hot facility uses the same fuel as the combi boiler
    PumpsFansAndKeepHot_KeepHot(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'f'), null, 0d),
    PumpsFansAndKeepHot_SolarWaterHeatingPump(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'g'), null),
    // Storage WWHRS pump not implemented
    PumpsFansAndKeepHot_StorageWWHRSPump(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(230, 'h'), null, 0d),

    PumpsFansAndKeepHot(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(231), null),
    Lighting(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(232), null, SkipReason.InEnergyCalculatorResult),

    Generation_PhotoVoltaic(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(233), null),
    // Wind turbines not implemented in the NHM
    Generation_WindTurbines(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(234), null, 0d),
    // MicroCHP not implemented in the NHM
    Generation_MicroCHP(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(235), null, 0d),
    // Hydro generators not implemented in the NHM
    Generation_Hydro(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.subCell(235, 'a'), null, 0d),

    Total_Energy(Units.Kilo_Watt_Hour_per_Month, Period.MonthlySum, SAPWorksheetSection.Energy_Requirements.cell(238), null, SkipReason.InEnergyCalculatorResult),

    /**
     * Fuel Costs (happens outside energy calculator)
     */
    Cost_SpaceHeating_Main_System1(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(240), null, SkipReason.Outside_Energy_Calculation),
    Cost_SpaceHeating_Main_System2(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(241), null, SkipReason.Outside_Energy_Calculation),
    Cost_SpaceHeating_Secondary(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(242), null, SkipReason.Outside_Energy_Calculation),

    Cost_WaterHeating_ElecHighRateFraction(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(243), null, SkipReason.Outside_Energy_Calculation),
    Cost_WaterHeating_ElecLowRateFraction(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(244), null, SkipReason.Outside_Energy_Calculation),
    Cost_WaterHeating_ElecHighRate(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(245), null, SkipReason.Outside_Energy_Calculation),
    Cost_waterHeating_ElecLowRate(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(246), null, SkipReason.Outside_Energy_Calculation),
    Cost_WaterHeating_NonElec(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(247), null, SkipReason.Outside_Energy_Calculation),

    Cost_SpaceCooling(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(248), null, SkipReason.Outside_Energy_Calculation),
    Cost_PumpsFansAndKeepHot(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(249), null, SkipReason.Outside_Energy_Calculation),
    Cost_Lighting(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(250), null, SkipReason.Outside_Energy_Calculation),
    Cost_StandingCharges(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(251), null, SkipReason.Outside_Energy_Calculation),
    Cost_Generation(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(252), null, SkipReason.Outside_Energy_Calculation),

    // Appendix Q steps 253 and 254 skipped

    Cost(Units.PoundSterling, Period.Annual, SAPWorksheetSection.Fuel_Costs.cell(255), null, SkipReason.Outside_Energy_Calculation),

    /**
     * SAP Rating (happens outside energy calculator)
     */
    EnergyCostDeflator(Units.Unknown, Period.Annual, SAPWorksheetSection.SAP_Rating.cell(256), null, SkipReason.Outside_Energy_Calculation),
    EnergyCostFactor(Units.Unknown, Period.Annual, SAPWorksheetSection.SAP_Rating.cell(257), null, SkipReason.Outside_Energy_Calculation),
    SAPRating(Units.Dimensionless, Period.Annual, SAPWorksheetSection.SAP_Rating.cell(258), null, SkipReason.Outside_Energy_Calculation),

    /**
     * CO2 Emissions (happens outside energy calculator)
     * The units are unknown here, because they depend on user inputs.
     */
    Emissions_SpaceHeating_Main_System1(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(261), null, SkipReason.Outside_Energy_Calculation),
    Emissions_SpaceHeating_Main_System2(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(262), null, SkipReason.Outside_Energy_Calculation),
    Emissions_SpaceHeating_Secondary(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(263), null, SkipReason.Outside_Energy_Calculation),
    Emissions_WaterHeating(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(264), null, SkipReason.Outside_Energy_Calculation),
    Emissions_SpaceAndWaterHeating(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(265), null, SkipReason.Outside_Energy_Calculation),

    // Always 0 - space cooling not implemented
    Emissions_SpaceCooling(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(266), null, SkipReason.Outside_Energy_Calculation),

    Emissions_PumpsFansAndKeepHot(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(267), null, SkipReason.Outside_Energy_Calculation),
    Emissions_Lighting(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(268), null, SkipReason.Outside_Energy_Calculation),
    Emissions_Generation(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(269), null, SkipReason.Outside_Energy_Calculation),

    // Appendix Q steps 270 and 271 skipped

    Emissions(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(272), null, SkipReason.Outside_Energy_Calculation),
    Emissions_PerArea(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(273), null, SkipReason.Outside_Energy_Calculation),
    Emissions_EIRating(Units.Unknown, Period.Annual, SAPWorksheetSection.Emissions.cell(274), null, SkipReason.Outside_Energy_Calculation),

    /**
     * Primary Energy - this section ignored as it is not implemented in the NHM.
     */

    /**
     * Community Heating sections - this section of the worksheet is ignored as it is not implemented in the NHM.
     * We have community heating, but we do not divide it up by the different heat sources.
     */
    ;

    public static class SkipReason {
        public static final String Annual_Not_Used = "Annual value not used.";
        public static final String Outside_Energy_Calculation = "Annual value not used.";
        public static final String SpaceHeating_Main_System2_Unsupported = "Using a second main space heating system is not supported in the NHM.";
        public static final String SpaceCooling_Unsupported = "Space cooling systems are not supported in the NHM.";
        public static final String TMP_Complex_Unsupported = "The NHM always uses the simplified Table 1f lookup to calculate the Thermal Mass Parameter, as requested by BEIS.";
        public static final String AirPressureTests_Unsupported = "The NHM always uses the estimation method to calculate permeability of the envelope, as the English Housing Survey doesn't include data from air pressure tests.";
        public static final String Unsupported_Technology = "The NHM does not support this technology.";
        public static final String FabricEnergyEfficiencyNotSupported = "The NHM does not perform the fabric energy efficiency calculation.";
        public static final String InEnergyCalculatorResult = "We don't want to output these fields in our report for now. They are fiddly to get, and are already available in IEnergyCalculationResult.";
    }

    public final Units conversion;
    public final Period period;
    public final Optional<SAPLocation> sapLocation;
    public final Optional<BREDEMLocation> bredemLocation;
    public final Optional<Double> defaultValue;
    public final Optional<String> skipReason;

    private EnergyCalculationStep(Units conversion, Period period, SAPLocation sapLocation, BREDEMLocation bredemLocation) {
        this(conversion, period, sapLocation, bredemLocation, Optional.empty(), Optional.empty());
    }

    private EnergyCalculationStep(Units conversion, Period period, SAPLocation sapLocation, BREDEMLocation bredemLocation, String skipReason) {
        this(conversion, period, sapLocation, bredemLocation, Optional.empty(), Optional.of(skipReason));
    }

    private EnergyCalculationStep(Units conversion, Period period, SAPLocation sapLocation, BREDEMLocation bredemLocation, Double defaultValue) {
        this(conversion, period, sapLocation, bredemLocation, Optional.of(defaultValue), Optional.empty());
    }

    private EnergyCalculationStep(Units conversion, Period period, SAPLocation sapLocation, BREDEMLocation bredemLocation, Optional<Double> defaultValue, Optional<String> skipReason) {
        this.conversion = conversion;
        this.period = period;
        this.sapLocation = Optional.ofNullable(sapLocation);
        this.bredemLocation = Optional.ofNullable(bredemLocation);
        this.defaultValue = defaultValue;
        this.skipReason = skipReason;
    }

    public boolean isSkipped() {
        return skipReason.isPresent();
    }

    public boolean hasDefault() {
        return defaultValue.isPresent();
    }

    public double getDefault() {
        if (hasDefault()) {
            return defaultValue.get();
        } else {
            throw new UnsupportedOperationException("Tried to call getDefault on an energy calculation step which has no default.");
        }
    }

    public static class SAPLocation {
        public final SAPWorksheetSection section;
        public final int cell;
        public final Character subcell;

        SAPLocation(SAPWorksheetSection section, int cell, Character subcell) {
            this.section = section;
            this.cell = cell;
            this.subcell = subcell;
        }

        @Override
        public String toString() {
            return String.format("SAP 2012 worksheet cell (%d%s) in section %s.",
                    cell,
                    subcell == null ? "" : subcell,
                    section.toString()
            );
        }
    }

    public enum Period {
        MonthlyMean {
            @Override
            public double getMonth(double[] data, int month) {
                return getMonthImpl(data, month);
            }

            @Override
            public double getAnnual(double[] data) {
                return Period.sumAnnual(data) / 12d;
            }

            @Override
            public String toString() { return "Monthly, or averaged over all the months in the year."; }
        },
        MonthlySum {
            @Override
            public double getMonth(double[] data, int month) {
                return getMonthImpl(data, month);
            }

            @Override
            public double getAnnual(double[] data) {
                return Period.sumAnnual(data);
            }

            public String toString() { return "Monthly, or summed over all the months in the year."; }
        },
        Annual {
            @Override
            public double getMonth(double[] data, int month) {
                throw new UnsupportedOperationException("Can't get a monthly value for an annual quantity.");
            }

            @Override
            public double getAnnual(double[] data) {
                return data[0];
            }

            public String toString() { return "Annual."; }
        };

        public static double getMonthImpl(double[] data, int month) {
            return data[month - 1];
        }

        public static double sumAnnual(double[] data) {
            double result = 0d;

            for (double d : data) {
                result += d;
            }

            return result;
        }

        abstract public String toString();

        abstract public double getMonth(double[] data, int month);
        abstract public double getAnnual(double[] data);
    }

    public static enum SAPWorksheetSection {
        Dimensions,
        Ventilation,
        HeatLosses_and_Heat_Loss_Parameter,
        Water_Heating,
        Gains_Internal,
        Gains_Solar,
        Mean_Internal_Temperature,
        Space_Heating,
        Space_Cooling,
        Fabric_Energy_Efficiency,
        Energy_Requirements,
        Fuel_Costs,
        SAP_Rating,
        Emissions,
        Primary_Energy,
        Energy_Requirements_Community,
        Fuel_Costs_Community,
        SAP_Rating_Community,
        Emissions_Community,
        Primary_Energy_Community
        ;

        public SAPLocation cell(int cell) {
            return new SAPLocation(this, cell, null);
        }

        public SAPLocation subCell(int cell, Character subCell) {
            return new SAPLocation(this, cell, subCell);
        }

        @Override
        public String toString() {
            return name().replace('_', ' ');
        }
    }


    static class BREDEMLocation {
        private final BREDEMSection section;
        private final Character step;
        private final String variable;
        private final TableLocation table;

        BREDEMLocation(BREDEMSection section, Character step, String variable, TableLocation table) {
            this.section = section;
            this.step = step;
            this.variable = variable;
            this.table = table;
        }
    }

    static class TableLocation {

    }

    public static enum BREDEMSection {
        LightsAppliancesAndCooking("1"),
        WaterVolumeAndEnergyContent("2.1"),
        WaterHeatingLosses("2.2"),
        ElectricShowerEnergy("2.3"),
        SolarEnergy("2.4.1"),
        WaterHeatingSolarOutput("2.4.2"),
        WaterHeatingEnergyRequirement("2.5"),
        HeatLoss("3"),
        ThermalMassParameter("4"),
        Gains_Solar("5"),
        Gains_InternalAndTotal("6"),
        MeanInternalTemperature("7"),
        SpaceHeating("8"),
        SpaceCooling("9"),
        PhotovoltaicsAndWindTurbines("10"),
        UsingOutputs("11"),
        ExternalTemperatureAndSolarRadiation("Appendix A"),
        EfficiencyAndRepsonsiveness("Appendix B"),
        ThermalBridgingValues("Appendix C"),
        ThermalMassOfBuildingElements("Appendix D")
        ;

        private final String id;

        private BREDEMSection(String id) {
            this.id = id;
        }

        public BREDEMLocation var(String variable) {
            return new BREDEMLocation(this, null, variable, null);
        }

        public BREDEMLocation step(char step, String variable) {
            return new BREDEMLocation(this, step, variable, null);
        }
    }

    public enum Units {
        Unknown(Unit.Unknown),
        Dimensionless(Unit.Dimensionless),
        DimensionlessProportion(Unit.DimensionlessProportion),
        Count(Unit.Count),

        Watt(Unit.Watts),

        MetreCubed_per_Hour(Unit.MetresCubed_per_Hour),
        Watt_per_Kelvin(Unit.Watts_per_Kelvin),
        Watt_per_MetreSquared_Kelvin(Unit.Watts_per_MetreSquared_Kelvin),
        AirChange_per_Hour(Unit.AirChanges_per_Hour),
        MetreSquared(Unit.MetresSquared),
        MetreCubed(Unit.MetresCubed),
        Metre_per_Second(Unit.Metres_per_Second),
        Kilo_Joule_per_MetreSquared_Kelvin(Unit.Kilo_Joules_per_MetreSquared_Kelvin),
        Litre_per_Day(Unit.Litres_per_Day),

        Centigrade(Unit.Centigrade),

        PoundSterling(Unit.PoundSterling),

        Kilo_Watt_Hour_per_Day(Unit.Watts, Unit.Kilo_Watt_Hours_per_Day) {
            @Override
            public double convert(double nhmValue, int daysInMonth) { return nhmValue / (1000 * 24); }
        },

        Kilo_Watt_Hour_per_Month(Unit.Watts, Unit.Kilo_Watt_Hours_per_Month) {
            @Override
            public double convert(double nhmValue, int daysInMonth) { return nhmValue / (1000 * daysInMonth * 24); }
        },

        Kilo_Watt_Hour_per_Litre_per_Day(Unit.Watts_per_Litre, Unit.Kilo_Watt_Hours_per_Litre_per_Day) {
            @Override
            public double convert(double nhmValue, int daysInMonth) {
                return nhmValue * 24 / 1000;
            }
        },

        Kilo_Watt_Hour_per_MetreSquared_per_Month(Unit.Watts_per_MetreSquared, Unit.Kilo_Watt_Hours_per_MetreSquared_per_Month) {
            @Override
            public double convert(double nhmValue, int daysInMonth) {
                return nhmValue / (1000 * daysInMonth * 24);
            }
        }
        ;

        private final Unit nhm;
        private final Unit sap;

        Units(Unit both) {
            this(both, both);
        }

        Units(Unit nhm, Unit sap) {
            this.nhm = nhm;
            this.sap = sap;
        }

        public double convert(double nhmValue, int daysInMonth) {
            return nhmValue;
        }

        @Override
        public String toString() {
            if (sap == nhm) {
                return sap.toString();
            } else {
                return nhm.toString() + " inside the NHM, converted to " + sap.toString() + ".";
            }
        }

        public enum Unit {
            Unknown,

            Dimensionless,
            DimensionlessProportion,
            Count,
            Boolean,

            Radians,

            Metres,
            MetresSquared,
            MetresCubed,

            Second,
            Hours,
            Days,
            Months,

            Watts,
            Centigrade,
            Kelvin,

            Litres,

            PoundSterling,

            AirChanges,

            MetresCubed_per_Hour,
            AirChanges_per_Hour,
            Watts_per_MetreSquared,
            Watts_per_Person,
            Watts_per_Kelvin,
            Watts_per_MetreSquared_Kelvin,
            Kilo_Watt_Hours_per_Day,
            Kilo_Watt_Hours_per_MetreSquared_per_Month,
            Kilo_Watt_Hours_per_Month,
            Metres_per_Second,

            Litres_per_Day,
            Watts_per_Litre,
            Kilo_Watt_Hours_per_Litre_per_Day,

            Kilo_Joules_per_MetreSquared_Kelvin,

            ;

            @Override
            public String toString() {
                return name().replace('_', ' ');
            }
        }
    }
}
