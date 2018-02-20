package uk.org.cse.nhm.energycalculator.api.types;

import java.util.Optional;

/**
 * This is derived from a combination of:
 * The worksheet from SAP 2012 https://www.bre.co.uk/filelibrary/SAP/2012/SAP-2012_9-92.pdf
 * BREDEM 2012 (only partially included, unfinished)
 */
public enum EnergyCalculationStep {
    /**
     *  Dimensions and Structure
     */
    // TODO: areas of individual floors
    TotalFloorArea(SAPWorksheetSection.Dimensions.cell(4), BREDEMSection.LightsAppliancesAndCooking.var("TFA")),
    DwellingVolume(SAPWorksheetSection.Dimensions.cell(5), null),

    /**
     * Ventilation
     */
    ChimneyVentilation(SAPWorksheetSection.Ventilation.subCell(6, 'a'), null),
    OpenFluesVentilation(SAPWorksheetSection.Ventilation.subCell(6, 'b'), null),
    IntermittentFansVentilation(SAPWorksheetSection.Ventilation.subCell(7, 'a'), null),
    PassiveVentsVentilation(SAPWorksheetSection.Ventilation.subCell(7, 'b'), null),
    FluelessGasFiresVentilation(SAPWorksheetSection.Ventilation.subCell(7 ,'c'), null),

    AirChanges_ChimneysFluesFansAndPSVs(SAPWorksheetSection.Ventilation.cell(8), null),
    Storeys(SAPWorksheetSection.Ventilation.cell(9), null),
    InfiltrationAdditionalStackEffect(SAPWorksheetSection.Ventilation.cell(10), null),
    InfiltrationStructural(SAPWorksheetSection.Ventilation.cell(11), null),
    InfiltrationGroundFloor(SAPWorksheetSection.Ventilation.cell(12), null),
    InfiltrationAbsenceOfDraughtLobby(SAPWorksheetSection.Ventilation.cell(13), null),

    // TODO: this is percent in the worksheet?
    ProportionWindowsDraughtProofed(SAPWorksheetSection.Ventilation.cell(14), null),
    InfiltrationWindows(SAPWorksheetSection.Ventilation.cell(15), null),
    InfiltrationRate_Initial(SAPWorksheetSection.Ventilation.cell(16), null),

    AirPermabilityValue(SAPWorksheetSection.Ventilation.cell(17), null, SkipReason.AirPressureTests_Unsupported),

    InfiltrationRateMaybePressureTest(SAPWorksheetSection.Ventilation.cell(18), null),
    SidesSheltered(SAPWorksheetSection.Ventilation.cell(19), null),
    ShelterFactor(SAPWorksheetSection.Ventilation.cell(20), null),
    InfiltrationRate_IncludingShelter(SAPWorksheetSection.Ventilation.cell(21), null),

    AverageWindSpeed(SAPWorksheetSection.Ventilation.cell(22), null),
    WindFactor(SAPWorksheetSection.Ventilation.cell(23), null),
    InfiltrationRate_IncludingShelterAndWind(SAPWorksheetSection.Ventilation.cell(24), null),

    SiteExposureFactor(null, BREDEMSection.HeatLoss.var("ShE")),
    InfiltrationRate_IncludingShelterAndWindAndSiteExposure(null, BREDEMSection.HeatLoss.step('E', "Lsub,m")),

    // Unimplemented technologies
    AirChanges_MechanicalVentilation(SAPWorksheetSection.Ventilation.subCell(23, 'a'), null, SkipReason.Unsupported_Technology),
    AirChanges_ExhaustAirHeatPump(SAPWorksheetSection.Ventilation.subCell(23, 'b'), null, SkipReason.Unsupported_Technology),
    AirChanges_BalancedWithHeatRecovery(SAPWorksheetSection.Ventilation.subCell(23, 'c'), null, SkipReason.Unsupported_Technology),

    // The only type of ventilation we use is natural
    Ventilation_BalancedMechanicalWithHeatRecovery(SAPWorksheetSection.Ventilation.subCell(24, 'a'), null, SkipReason.Unsupported_Technology),
    Ventilation_BalancedMechanicalWithoutHeatRecovery(SAPWorksheetSection.Ventilation.subCell(24, 'b'), null, SkipReason.Unsupported_Technology),
    Ventilation_WholeHouseExtractOrPositiveFromOutside(SAPWorksheetSection.Ventilation.subCell(24, 'c'), null, SkipReason.Unsupported_Technology),
    Ventilation_NaturalOrPositiveFromLoft(SAPWorksheetSection.Ventilation.subCell(24, 'd'), null),

    // We ignore the note about Appendix Q here
    AirChanges_Effective(SAPWorksheetSection.Ventilation.cell(25), null),

    /**
     * Heat losses and heat loss parameter
     */
    // For external fabric elements, this is the A x U (W/K) columns
    // TODO: record doors
    HeatLossCoefficient_DoorsSolid(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(26), null),
    HeatLossCoefficient_DoorsSemiGlazed(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(26, 'a'), null),
    HeatLossCoefficient_Window(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(27), null),
    // Extra breakdown of cell 27 as requested by BRE.
    // TODO; record windows aggregated by frame type
    HeatLossCoefficient_Window_UPVC_Or_Wood(null, null),
    HeatLossCoefficient_Window_Metal(null, null),
    // We don't have any roof windows in the NHM
    HeatLossCoefficient_Window_Roof(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(27, 'a'), null, 0d),
    // TODO: record floors
    HeatLossCoefficient_BasementFloor(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(28), null),
    HeatLossCoefficient_GroundFloor(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(28, 'a'), null),
    HeatLossCoefficient_ExposedFLoor(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(28, 'b'), null),
    // Basement walls are treated as External walls in the NHM
    HeatLossCoefficient_BasementWall(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(29), null, 0d),
    HeatLossCoefficient_ExternalWall(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(29, 'a'), null),
    HeatLossCoefficient_Roof(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(30), null),

    AreaExternal(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(31), null),

    // For internal and part fabric elements, this is the net area (m^2) column
    AreaPartyWall(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(32), null),
    AreaPartyFloor(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(32, 'a'), null),
    AreaPartyCeiling(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(32, 'b'), null),
    AreaInternalWall(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(32, 'c'), null),
    // In the NHM, we treat Internal Floors and Ceilings as if they were Party Floors and Ceilings.
    // This should make no difference practically.
    AreaInternalFloor(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(32, 'd'), null),
    AreaInternalCeiling(SAPWorksheetSection.HeatLossesAndHeatLossParameter.subCell(32, 'e'), null),

    FabricHeatLoss(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(33), null),
    HeatCapacity(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(34), null, SkipReason.TMP_Complex_Unsupported),
    // Uses indicative values from Table 1f
    ThermalMassParameter(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(35), null),
    // Uses simple calculation
    ThermalBridges(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(36), null),
    FabricLossTotal(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(37), null),
    VentilationHeatLoss(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(38), null),
    HeatTransferCoefficient(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(39), null),
    HeatLossParameter(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(40), null),
    DaysInMonth(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(41), null),

    /**
     * Water Heating
     */
    Occupancy(SAPWorksheetSection.WaterHeating.cell(42),
            BREDEMSection.LightsAppliancesAndCooking.step('a', "N")),
    WaterHeating_Usage_Daily(SAPWorksheetSection.WaterHeating.cell(43), null),
    WaterHeating_Usage_Monthly(SAPWorksheetSection.WaterHeating.cell(44), null),
    WaterHeating_EnergyContent(SAPWorksheetSection.WaterHeating.cell(45), null),
    WaterHeating_DistributionLoss(SAPWorksheetSection.WaterHeating.cell(46), null),
    WaterHeating_StorageVolume(SAPWorksheetSection.WaterHeating.cell(47), null),

    // 48,49 and 50 ignored as we don't know manufacturer's declared loss factor

    WaterHeating_StorageLossFactor(SAPWorksheetSection.WaterHeating.cell(51), null),
    WaterHeating_StorageVolumeFactor(SAPWorksheetSection.WaterHeating.cell(52), null),
    WaterHeating_StorageTemperatureFactor(SAPWorksheetSection.WaterHeating.cell(53), null),
    WaterHeating_StorageLosses_Daily_Calculated(SAPWorksheetSection.WaterHeating.cell(54), null),
    WaterHeating_StorageLosses_Daily(SAPWorksheetSection.WaterHeating.cell(55), null),
    WaterHeating_StorageLosses_Monthly(SAPWorksheetSection.WaterHeating.cell(56), null),

    // This should be monthly, but...
    WaterHeating_StorageLosses_Daily_ExcludeSolar(SAPWorksheetSection.WaterHeating.cell(57), null),

    // There's no step 58 in the SAP worksheet

    WaterHeating_PrimaryCircuitLoss_Monthly(SAPWorksheetSection.WaterHeating.cell(59), null),

    // There's no step 60 in the SAP worksheet

    WaterHeating_CombiLoss_Monthly(SAPWorksheetSection.WaterHeating.cell(61), null),
    WaterHeating_TotalHeat_Monthly_BeforeSolar(SAPWorksheetSection.WaterHeating.cell(62), null),
    WaterHeating_Solar(SAPWorksheetSection.WaterHeating.cell(63), null),
    WaterHeating_TotalHeat_Monthly(SAPWorksheetSection.WaterHeating.cell(64), null),

    /**
     * Internal Gains (watts)
     */
    Gains_HotWater_Monthly(SAPWorksheetSection.WaterHeating.cell(65), null),
    Gains_Metabolic(SAPWorksheetSection.Gains_Internal.cell(66), null),
    Gains_Lighting(SAPWorksheetSection.Gains_Internal.cell(67), null),
    Gains_Appliances(SAPWorksheetSection.Gains_Internal.cell(68), null),
    Gains_Cooking(SAPWorksheetSection.Gains_Internal.cell(69), null),
    Gains_PumpsAndFans(SAPWorksheetSection.Gains_Internal.cell(70), null),
    Gains_Evaporation(SAPWorksheetSection.Gains_Internal.cell(71), null),
    // This is the same as the other hot water gains in the NHM. In SAP, this represents a conversion.
    Gains_HotWater(SAPWorksheetSection.Gains_Internal.cell(72), null),

    Gains_Internal(SAPWorksheetSection.Gains_Internal.cell(73), null),

    /**
     * Solar Gains (watts)
     */
    // TODO: Window gains 74 to 81

    // We don't support roof windows in the NHM
    Gains_Solar_Roof(SAPWorksheetSection.Gains_Solar.cell(82), null, 0d),

    Gains_Solar(SAPWorksheetSection.Gains_Solar.cell(83), null),

    Gains(SAPWorksheetSection.Gains_Solar.cell(84), null),

    /**
     * Mean Internal Temperature
     */
    DemandTemperature_LivingArea(SAPWorksheetSection.MeanInternalTemperature.cell(85), null),
    GainsUtilisation_LivingArea(SAPWorksheetSection.MeanInternalTemperature.cell(86), null),
    MeanInternalTemperature_LivingArea(SAPWorksheetSection.MeanInternalTemperature.cell(87), null),

    DemandTemperature_RestOfDwelling(SAPWorksheetSection.MeanInternalTemperature.cell(88), null),
    GainsUtilisation_RestOfDwelling(SAPWorksheetSection.MeanInternalTemperature.cell(89), null),
    MeanInternalTemperature_RestOfDwelling(SAPWorksheetSection.MeanInternalTemperature.cell(90), null),

    LivingAreaFraction(SAPWorksheetSection.MeanInternalTemperature.cell(91), null),
    MeanInternalTemperature_Unadjusted(SAPWorksheetSection.MeanInternalTemperature.cell(92), null),
    MeanInternalTemperature(SAPWorksheetSection.MeanInternalTemperature.cell(93), null),

    /**
     * Space Heating
     */
    GainsUtilisation(SAPWorksheetSection.SpaceHeating.cell(94), null),
    Gains_Useful(SAPWorksheetSection.SpaceHeating.cell(95), null),
    ExternalTemperature(SAPWorksheetSection.SpaceHeating.cell(96), null),
    HeatLossRate(SAPWorksheetSection.SpaceHeating.cell(97), null),
    SpaceHeating(SAPWorksheetSection.SpaceHeating.cell(98), null),
    // Feeds into step 109, which we aren't doing.
    SpaceHeating_PerFloorArea(SAPWorksheetSection.SpaceHeating.cell(99), null, SkipReason.FabricEnergyEfficiencyNotSupported),

    /**
     * Space Cooling (not implemented)
     * If we were to implement this, we probably need to duplicate a lot of the stuff above. Ugh.
     */
    HeatLossRate_Cooling(SAPWorksheetSection.SpaceCooling.cell(100), null, SkipReason.SpaceCooling_Unsupported),
    LossUtilisation(SAPWorksheetSection.SpaceCooling.cell(101), null, SkipReason.SpaceCooling_Unsupported),
    Loss_Useful(SAPWorksheetSection.SpaceCooling.cell(102), null, SkipReason.SpaceCooling_Unsupported),
    Gains_Cooling(SAPWorksheetSection.SpaceCooling.cell(103), null, SkipReason.SpaceCooling_Unsupported),
    SpaceCooling_Continuous(SAPWorksheetSection.SpaceCooling.cell(104), null, SkipReason.SpaceCooling_Unsupported),
    CooledFraction(SAPWorksheetSection.SpaceCooling.cell(105), null, SkipReason.SpaceCooling_Unsupported),
    CoolingIntermittencyFactor(SAPWorksheetSection.SpaceCooling.cell(106), null, SkipReason.SpaceCooling_Unsupported),
    SpaceCooling(SAPWorksheetSection.SpaceCooling.cell(107), null, SkipReason.SpaceCooling_Unsupported),
    SpaceCooling_PerFloorArea(SAPWorksheetSection.SpaceCooling.cell(108), null, SkipReason.SpaceCooling_Unsupported),

    /**
     * Fabric Energy efficiency
     */
    FabricEnergyEfficiency(SAPWorksheetSection.FabricEnergyEfficiency.cell(109), null, SkipReason.FabricEnergyEfficiencyNotSupported),

    // Steps 110 to 200 don't exist in the SAP worksheet.

    /**
     * Energy Requirements
     */
    SpaceHeating_Fraction_Secondary(SAPWorksheetSection.EnergyRequirements.cell(201), null),

    SpaceHeating_Fraction_Main(SAPWorksheetSection.EnergyRequirements.cell(202), null),

    // Always 0% - we never use any heat from system 2 since it isn't supported.
    SpaceHeating_FractionWithinMainSystem(SAPWorksheetSection.EnergyRequirements.cell(203), null, 0d),

    // The same as SpaceHeatingFraction_Main
    SpaceHeating_Fraction_Main_System1(SAPWorksheetSection.EnergyRequirements.cell(204), null),

    // Always 0%, because main space heating system 2 isn't supported
    SpaceHeating_Fraction_Main_System2(SAPWorksheetSection.EnergyRequirements.cell(205), null, 0d),

    SpaceHeating_Efficiency_Main_System1(SAPWorksheetSection.EnergyRequirements.cell(206), null),

    SpaceHeating_Efficency_Main_System2(SAPWorksheetSection.EnergyRequirements.cell(207), null, SkipReason.SpaceHeating_Main_System2_Unsupported),

    SpaceHeating_Efficiency_Secondary(SAPWorksheetSection.EnergyRequirements.cell(208), null),

    SpaceCooling_EfficencyRatio(SAPWorksheetSection.EnergyRequirements.cell(209), null, SkipReason.SpaceCooling_Unsupported),

    // Step 210 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Main_System1(SAPWorksheetSection.EnergyRequirements.cell(211), null, SkipReason.InEnergyCalculatorResult),

    // Step 212 doesn't exist in the SAP worksheet

    // Always 0, because main space heating system 2 isn't supported.
    Energy_SpaceHeating_Fuel_Main_system2(SAPWorksheetSection.EnergyRequirements.cell(213), null, 0d),

    // Step 214 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Secondary(SAPWorksheetSection.EnergyRequirements.cell(215), null, SkipReason.InEnergyCalculatorResult),

    Energy_WaterHeating_TotalHeat_Annual(SAPWorksheetSection.EnergyRequirements.cell(216), null, SkipReason.Annual_Not_Used),

    WaterHeating_Efficiency(SAPWorksheetSection.EnergyRequirements.cell(217), null),

    // Step 218 doesn't exist in the SAP worksheet

    Energy_WaterHeatingFuel(SAPWorksheetSection.EnergyRequirements.cell(219), null, SkipReason.InEnergyCalculatorResult),

    // Step 220 doesn't exist in the SAP worksheet

    Energy_SpaceCooling(SAPWorksheetSection.EnergyRequirements.cell(221), null, 0d),

    // The worksheet confusingly repeats 211, 213, 215, 219 and 221 at this point. Ignore this.

    // Steps 222 to 229 don't exist in the SAP worksheet.

    // The NHM doesn't include mechanical ventilation fans
    PumpsFansAndKeepHot_MechanicalVentilationFans(SAPWorksheetSection.EnergyRequirements.subCell(230, 'a'), null, 0d),
    PumpsFansAndKeepHot_WarmAirFans(SAPWorksheetSection.EnergyRequirements.subCell(230, 'b'), null),
    PumpsFansAndKeepHot_WaterPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'c'), null),
    PumpsFansAndKeepHot_OilBoilerPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'd'), null),
    // This can also be a heat pump fan flue
    PumpsFansAndKeepHot_BoilerFlueFan(SAPWorksheetSection.EnergyRequirements.subCell(230, 'e'), null),
    // In the NHM, the keep hot facility uses the same fuel as the combi boiler
    PumpsFansAndKeepHot_KeepHot(SAPWorksheetSection.EnergyRequirements.subCell(230, 'f'), null, 0d),
    PumpsFansAndKeepHot_SolarWaterHeatingPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'g'), null),
    // Storage WWHRS pump not implemented
    PumpsFansAndKeepHot_StorageWWHRSPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'h'), null, 0d),

    PumpsFansAndKeepHot(SAPWorksheetSection.EnergyRequirements.cell(231), null),
    Lighting(SAPWorksheetSection.EnergyRequirements.cell(232), null, SkipReason.InEnergyCalculatorResult),

    Generation_PhotoVoltaic(SAPWorksheetSection.EnergyRequirements.cell(233), null),
    // Wind turbines not implemented in the NHM
    Generation_WindTurbines(SAPWorksheetSection.EnergyRequirements.cell(234), null, 0d),
    // MicroCHP not implemented in the NHM
    Generation_MicroCHP(SAPWorksheetSection.EnergyRequirements.cell(235), null, 0d),
    // Hydro generators not implemented in the NHM
    Generation_Hydro(SAPWorksheetSection.EnergyRequirements.subCell(235, 'a'), null, 0d),

    Total_Energy(SAPWorksheetSection.EnergyRequirements.cell(238), null, SkipReason.InEnergyCalculatorResult),

    /**
     * Fuel Costs (happens outside energy calculator)
     */
    Cost_SpaceHeating_Main_System1(SAPWorksheetSection.FuelCosts.cell(240), null, SkipReason.Outside_Energy_Calculation),
    Cost_SpaceHeating_Main_System2(SAPWorksheetSection.FuelCosts.cell(241), null, SkipReason.Outside_Energy_Calculation),
    Cost_SpaceHeating_Secondary(SAPWorksheetSection.FuelCosts.cell(242), null, SkipReason.Outside_Energy_Calculation),

    Cost_WaterHeating_ElecHighRateFraction(SAPWorksheetSection.FuelCosts.cell(243), null, SkipReason.Outside_Energy_Calculation),
    Cost_WaterHeating_ElecLowRateFraction(SAPWorksheetSection.FuelCosts.cell(244), null, SkipReason.Outside_Energy_Calculation),
    Cost_WaterHeating_ElecHighRate(SAPWorksheetSection.FuelCosts.cell(245), null, SkipReason.Outside_Energy_Calculation),
    Cost_waterHeating_ElecLowRate(SAPWorksheetSection.FuelCosts.cell(246), null, SkipReason.Outside_Energy_Calculation),
    Cost_WaterHeating_NonElec(SAPWorksheetSection.FuelCosts.cell(247), null, SkipReason.Outside_Energy_Calculation),

    Cost_SpaceCooling(SAPWorksheetSection.FuelCosts.cell(248), null, SkipReason.Outside_Energy_Calculation),
    Cost_PumpsFansAndKeepHot(SAPWorksheetSection.FuelCosts.cell(249), null, SkipReason.Outside_Energy_Calculation),
    Cost_Lighting(SAPWorksheetSection.FuelCosts.cell(250), null, SkipReason.Outside_Energy_Calculation),
    Cost_StandingCharges(SAPWorksheetSection.FuelCosts.cell(251), null, SkipReason.Outside_Energy_Calculation),
    Cost_Generation(SAPWorksheetSection.FuelCosts.cell(252), null, SkipReason.Outside_Energy_Calculation),

    // Appendix Q steps 253 and 254 skipped

    Cost(SAPWorksheetSection.FuelCosts.cell(255), null, SkipReason.Outside_Energy_Calculation),

    /**
     * SAP Rating (happens outside energy calculator)
     */
    EnergyCostDeflator(SAPWorksheetSection.SAPRating.cell(256), null, SkipReason.Outside_Energy_Calculation),
    EnergyCostFactor(SAPWorksheetSection.SAPRating.cell(257), null, SkipReason.Outside_Energy_Calculation),
    SAPRating(SAPWorksheetSection.SAPRating.cell(258), null, SkipReason.Outside_Energy_Calculation),

    /**
     * CO2 Emissions (happens outside energy calculator)
     */
    Emissions_SpaceHeating_Main_System1(SAPWorksheetSection.Emissions.cell(261), null, SkipReason.Outside_Energy_Calculation),
    Emissions_SpaceHeating_Main_System2(SAPWorksheetSection.Emissions.cell(262), null, SkipReason.Outside_Energy_Calculation),
    Emissions_SpaceHeating_Secondary(SAPWorksheetSection.Emissions.cell(263), null, SkipReason.Outside_Energy_Calculation),
    Emissions_WaterHeating(SAPWorksheetSection.Emissions.cell(264), null, SkipReason.Outside_Energy_Calculation),
    Emissions_SpaceAndWaterHeating(SAPWorksheetSection.Emissions.cell(265), null, SkipReason.Outside_Energy_Calculation),

    // Always 0 - space cooling not implemented
    Emissions_SpaceCooling(SAPWorksheetSection.Emissions.cell(266), null, SkipReason.Outside_Energy_Calculation),

    Emissions_PumpsFansAndKeepHot(SAPWorksheetSection.Emissions.cell(267), null, SkipReason.Outside_Energy_Calculation),
    Emissions_Lighting(SAPWorksheetSection.Emissions.cell(268), null, SkipReason.Outside_Energy_Calculation),
    Emissions_Generation(SAPWorksheetSection.Emissions.cell(269), null, SkipReason.Outside_Energy_Calculation),

    // Appendix Q steps 270 and 271 skipped

    Emissions(SAPWorksheetSection.Emissions.cell(272), null, SkipReason.Outside_Energy_Calculation),
    Emissions_PerArea(SAPWorksheetSection.Emissions.cell(273), null, SkipReason.Outside_Energy_Calculation),
    Emissions_EIRating(SAPWorksheetSection.Emissions.cell(274), null, SkipReason.Outside_Energy_Calculation),

    /**
     * Primary Energy - this section ignored as it is not implemented in the NHM.
     */

    /**
     * Community Heating sections - this section of the worksheet is ignored as it is not implemented in the NHM.
     * We have community heating, but we do not divide it up by the different heat sources.
     */

    /**
     * BREDEM things (unfinished, unused)
     */

    /**
     * Lights
     */
    LightingEnergyBasicRequirement(null, BREDEMSection.LightsAppliancesAndCooking.step('B', "EB")),
    LowEnergyLightingCorrectionFactor(null, BREDEMSection.LightsAppliancesAndCooking.step('C', "C1")),
    DaylightAvailability(null, BREDEMSection.LightsAppliancesAndCooking.step('D', "GDL")),
    DaylightCorrectionFactor(null, BREDEMSection.LightsAppliancesAndCooking.step('E', "C2")),
    LightingEnergy_Initial(null, BREDEMSection.LightsAppliancesAndCooking.step('F', "EL'")),
    LightingEnergy_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('G', "ELM")),
    LightingEnergy_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('H', "EL"), SkipReason.Annual_Not_Used),

    /**
     * Appliances
     */
    ApplianceEnergy_Initial(null, BREDEMSection.LightsAppliancesAndCooking.step('I', "EA'")),
    ApplianceEnergy_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('J', "EA,m")),
    ApplianceEnergy_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('K', "EA"), SkipReason.Annual_Not_Used),
    PumpsAndFansEnergy(null, BREDEMSection.LightsAppliancesAndCooking.step('L', "Ep&f")),

    /**
     * Cooking
     */
    CookingEnergyCombustion_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('M', "EC1 or EC2")),
    CookingEnergyElectric_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('M', "EC1 or EC2")),

    CookingEnergyCombustion_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('N', "EC1,m or EC2,m")),
    CookingEnergyElectric_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('N', "EC1,m or EC2,m")),

    CookingEnergy_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('O', "EC,m")),

    // Range cookers not used
    RangeCookingEnergy_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('P', "ER,m"), 0d),
    RangeCookingEnergy_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('Q', "ER"), 0d)
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

    private final SAPLocation sapLocation;
    private final BREDEMLocation bredemLocation;
    private final Optional<Double> defaultValue;
    private final Optional<String> skipReason;

    private EnergyCalculationStep(SAPLocation sapLocation, BREDEMLocation bredemLocation) {
        this(sapLocation, bredemLocation, Optional.empty(), Optional.empty());
    }

    private EnergyCalculationStep(SAPLocation sapLocation, BREDEMLocation bredemLocation, String skipReason) {
        this(sapLocation, bredemLocation, Optional.empty(), Optional.of(skipReason));
    }

    private EnergyCalculationStep(SAPLocation sapLocation, BREDEMLocation bredemLocation, Double defaultValue) {
        this(sapLocation, bredemLocation, Optional.of(defaultValue), Optional.empty());
    }

    private EnergyCalculationStep(SAPLocation sapLocation, BREDEMLocation bredemLocation, Optional<Double> defaultValue, Optional<String> skipReason) {
        this.sapLocation = sapLocation;
        this.bredemLocation = bredemLocation;
        this.defaultValue = defaultValue;
        this.skipReason = skipReason;
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

    static class SAPLocation {
        private final SAPWorksheetSection section;
        private final int cell;
        private final Character subcell;

        SAPLocation(SAPWorksheetSection section, int cell, Character subcell) {
            this.section = section;
            this.cell = cell;
            this.subcell = subcell;
        }
    }

    public static enum SAPWorksheetSection {
        Dimensions,
        Ventilation,
        HeatLossesAndHeatLossParameter,
        WaterHeating,
        Gains_Internal,
        Gains_Solar,
        MeanInternalTemperature,
        SpaceHeating,
        SpaceCooling,
        FabricEnergyEfficiency,
        EnergyRequirements,
        FuelCosts,
        SAPRating,
        Emissions,
        PrimaryEnergy,
        EnergyRequirements_Community,
        FuelCosts_Community,
        SAPRating_Community,
        Emissions_Community,
        PrimaryEnergy_Community
        ;

        public SAPLocation cell(int cell) {
            return new SAPLocation(this, cell, null);
        }

        public SAPLocation subCell(int cell, Character subCell) {
            return new SAPLocation(this, cell, subCell);
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
}
