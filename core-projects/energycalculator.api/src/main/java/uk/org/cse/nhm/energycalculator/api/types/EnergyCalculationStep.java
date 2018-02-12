package uk.org.cse.nhm.energycalculator.api.types;

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
    FluelessGasFiresVentilation(SAPWorksheetSection.Ventilation.subCell(8 ,'c'), null),

    AirChanges_ChimneysFluesFansAndPSVs(SAPWorksheetSection.Ventilation.cell(8), null),
    Storeys(SAPWorksheetSection.Ventilation.cell(9), null),
    InfiltrationAdditional(SAPWorksheetSection.Ventilation.cell(10), null),
    InfiltrationStructural(SAPWorksheetSection.Ventilation.cell(11), null),
    InfiltrationGroundFloor(SAPWorksheetSection.Ventilation.cell(12), null),
    InfiltrationAbsenceOfDraughtLobby(SAPWorksheetSection.Ventilation.cell(13), null),

    // TODO: this is percent in the worksheet?
    ProportionWindowsDraughtProofed(SAPWorksheetSection.Ventilation.cell(14), null),
    InfiltrationWindows(SAPWorksheetSection.Ventilation.cell(15), null),
    InfiltrationRate_Initial(SAPWorksheetSection.Ventilation.cell(16), null),

    // Unused: for pressure tests
    AirPermabilityValue(SAPWorksheetSection.Ventilation.cell(17), null),

    InfiltrationRateMaybePressureTest(SAPWorksheetSection.Ventilation.cell(18), null),
    SidesSheltered(SAPWorksheetSection.Ventilation.cell(19), null),
    ShelterFactor(SAPWorksheetSection.Ventilation.cell(20), null),
    InfiltrationRate_IncludingShelter(SAPWorksheetSection.Ventilation.cell(21), null),

    AverageWindSpeed(SAPWorksheetSection.Ventilation.cell(22), null),
    WindFactor(SAPWorksheetSection.Ventilation.cell(23), null),
    InfiltrationRate_IncludingShelterAndWind(SAPWorksheetSection.Ventilation.cell(24), null),

    // Unimplemented technologies
    AirChanges_MechanicalVentilation(SAPWorksheetSection.Ventilation.subCell(23, 'a'), null),
    AirChanges_ExhaustAirHeatPump(SAPWorksheetSection.Ventilation.subCell(23, 'b'), null),
    AirChanges_BalancedWithHeatRecovery(SAPWorksheetSection.Ventilation.subCell(23, 'c'), null),

    // The only type of ventilation we use is natural
    Ventilation_BalancedMechanicalWithHeatRecovery(SAPWorksheetSection.Ventilation.subCell(24, 'a'), null),
    Ventilation_BalancedMechanicalWithoutHeatRecovery(SAPWorksheetSection.Ventilation.subCell(24, 'b'), null),
    Ventilation_WholeHouseExtractOrPositiveFromOutside(SAPWorksheetSection.Ventilation.subCell(24, 'c'), null),
    Ventilation_NaturalOrPositiveFromLoft(SAPWorksheetSection.Ventilation.subCell(24, 'd'), null),

    // We ignore the note about Appendix Q here
    AirChanges_Effective(SAPWorksheetSection.Ventilation.cell(25), null),

    /**
     * Heat losses and heat loss parameter
     */
    // TODO fabric elements
    AreaExternal(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(31), null),
    FabricHeatLoss(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(33), null),
    // Unused
    HeatCapacity(SAPWorksheetSection.HeatLossesAndHeatLossParameter.cell(34), null),
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
    Occupancy(
            SAPWorksheetSection.WaterHeating.cell(42),
            BREDEMSection.LightsAppliancesAndCooking.step('a', "N")),
    WaterHeating_Usage_Daily(SAPWorksheetSection.WaterHeating.cell(43), null),
    WaterHeating_UsageFactor(SAPWorksheetSection.WaterHeating.cell(44), null),
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

    WaterHeating_StorageVolume_ExcludeSolar(SAPWorksheetSection.WaterHeating.cell(57), null),

    // There's no step 58 in the SAP worksheet

    WaterHeating_PrimaryCircuitLoss_Monthly(SAPWorksheetSection.WaterHeating.cell(59), null),

    // There's no step 60 in the SAP worksheet

    WaterHeating_CombiLoss_Monthly(SAPWorksheetSection.WaterHeating.cell(61), null),
    WaterHeating_TotalHeat_Monthly_BeforeoOlar(SAPWorksheetSection.WaterHeating.cell(62), null),
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
    Gains_MiscLosses(SAPWorksheetSection.Gains_Internal.cell(71), null),
    // TODO: Unclear how this relates to the other hot water gains?
    Gains_HotWater(SAPWorksheetSection.Gains_Internal.cell(72), null),

    Gains_Internal(SAPWorksheetSection.Gains_Internal.cell(73), null),

    /**
     * Solar Gains (watts)
     */
    // TODO: Window gains 74 to 81

    Gains_Solar_Roof(SAPWorksheetSection.Gains_Solar.cell(82), null),

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
    SpaceHeating_PerFloorArea(SAPWorksheetSection.SpaceHeating.cell(99), null),

    /**
     * Space Cooling (not implemented)
     * If we were to implement this, we probably need to duplicate a lot of the stuff above. Ugh.
     */
    HeatLossRate_Cooling(SAPWorksheetSection.SpaceCooling.cell(100), null),
    LossUtilisation(SAPWorksheetSection.SpaceCooling.cell(101), null),
    Loss_Useful(SAPWorksheetSection.SpaceCooling.cell(102), null),
    Gains_Cooling(SAPWorksheetSection.SpaceCooling.cell(103), null),
    SpaceCooling_Continuous(SAPWorksheetSection.SpaceCooling.cell(104), null),
    CooledFraction(SAPWorksheetSection.SpaceCooling.cell(105), null),
    CoolingIntermittencyFactor(SAPWorksheetSection.SpaceCooling.cell(106), null),
    SpaceCooling(SAPWorksheetSection.SpaceCooling.cell(107), null),
    SpaceCooling_PerFloorArea(SAPWorksheetSection.SpaceCooling.cell(108), null),

    /**
     * Fabric Energy efficiency
     */
    FabricEnergyEfficiency(SAPWorksheetSection.FabricEnergyEfficiency.cell(109), null),

    // Steps 110 to 200 don't exist in the SAP worksheet.

    /**
     * Energy Requirements
     */
    SpaceHeating_Fraction_Secondary(SAPWorksheetSection.EnergyRequirements.cell(201), null),

    SpaceHeating_Fraction_Main(SAPWorksheetSection.EnergyRequirements.cell(202), null),

    // Always 0% - we never use any heat from system 2 since it isn't supported.
    SpaceHeating_FractionWithinMainSystem(SAPWorksheetSection.EnergyRequirements.cell(203), null),

    // The same as SpaceHeatingFraction_Main
    SpaceHeating_Fraction_Main_System1(SAPWorksheetSection.EnergyRequirements.cell(204), null),

    // Always 0%, because system 2 isn't supported
    SpaceHeating_Fraction_Main_System2(SAPWorksheetSection.EnergyRequirements.cell(205), null),

    SpaceHeating_Efficiency_Main_System1(SAPWorksheetSection.EnergyRequirements.cell(206), null),

    // Unused: main space heating system 2 isn't supported
    SpaceHeating_Efficency_Main_System2(SAPWorksheetSection.EnergyRequirements.cell(207), null),

    SpaceHeating_Efficiency_Secondary(SAPWorksheetSection.EnergyRequirements.cell(208), null),

    // Unused: space cooling not supported
    SpaceCooling_EfficencyRatio(SAPWorksheetSection.EnergyRequirements.cell(209), null),

    // Step 210 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Main_System1(SAPWorksheetSection.EnergyRequirements.cell(211), null),

    // Step 212 doesn't exist in the SAP worksheet

    // Unsued: main spaceheating system 2 isn't supported
    Energy_SpaceHeating_Fuel_Main_system2(SAPWorksheetSection.EnergyRequirements.cell(213), null),

    // Step 214 doesn't exist in the SAP worksheet

    Energy_SpaceHeating_Fuel_Secondary(SAPWorksheetSection.EnergyRequirements.cell(215), null),

    // Unused annual calculation
    Energy_WaterHeating_TotalHeat_Annual(SAPWorksheetSection.EnergyRequirements.cell(216), null),

    WaterHeating_Efficiency(SAPWorksheetSection.EnergyRequirements.cell(217), null),

    // Step 218 doesn't exist in the SAP worksheet

    Energy_WaterHeatingFuel(SAPWorksheetSection.EnergyRequirements.cell(219), null),

    // Step 220 doesn't exist in the SAP worksheet

    Energy_SpaceCooling(SAPWorksheetSection.EnergyRequirements.cell(221), null),

    // The worksheet confusingly repeats 211, 213, 215, 219 and 221 at this point. Ignore this.

    // Steps 222 to 229 don't exist in the SAP worksheet.

    PumpsFansAndKeepHot_MechanicalVentilationFans(SAPWorksheetSection.EnergyRequirements.subCell(230, 'a'), null),
    PumpsFansAndKeepHot_WarmAirFans(SAPWorksheetSection.EnergyRequirements.subCell(230, 'b'), null),
    PumpsFansAndKeepHot_WaterPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'c'), null),
    PumpsFansAndKeepHot_OilBoilerPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'd'), null),
    PumpsFansAndKeepHot_BoilerFlueFan(SAPWorksheetSection.EnergyRequirements.subCell(230, 'e'), null),
    PumpsFansAndKeepHot_KeepHot(SAPWorksheetSection.EnergyRequirements.subCell(230, 'f'), null),
    PumpsFansAndKeepHot_SolarWaterHeatingPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'g'), null),
    // Always 0 - storage WWHRS pump not implemented
    PumpsFansAndKeepHot_StorageWWHRSPump(SAPWorksheetSection.EnergyRequirements.subCell(230, 'h'), null),

    PumpsFansAndKeepHot(SAPWorksheetSection.EnergyRequirements.cell(231), null),
    Lighting(SAPWorksheetSection.EnergyRequirements.cell(232), null),

    /**
     * Fuel Costs (happens outside energy calculator)
     */
    Cost_SpaceHeating_Main_System1(SAPWorksheetSection.FuelCosts.cell(240), null),
    Cost_SpaceHeating_Main_System2(SAPWorksheetSection.FuelCosts.cell(241), null),
    Cost_SpaceHeating_Secondary(SAPWorksheetSection.FuelCosts.cell(242), null),

    Cost_WaterHeating_ElecHighRateFraction(SAPWorksheetSection.FuelCosts.cell(243), null),
    Cost_WaterHeating_ElecLowRateFraction(SAPWorksheetSection.FuelCosts.cell(244), null),
    Cost_WaterHeating_ElecHighRate(SAPWorksheetSection.FuelCosts.cell(245), null),
    Cost_waterHeating_ElecLowRate(SAPWorksheetSection.FuelCosts.cell(246), null),
    Cost_WaterHeating_NonElec(SAPWorksheetSection.FuelCosts.cell(247), null),

    Cost_SpaceCooling(SAPWorksheetSection.FuelCosts.cell(248), null),
    Cost_PumpsFansAndKeepHot(SAPWorksheetSection.FuelCosts.cell(249), null),
    Cost_Lighting(SAPWorksheetSection.FuelCosts.cell(250), null),
    Cost_StandingCharges(SAPWorksheetSection.FuelCosts.cell(251), null),
    Cost_Generation(SAPWorksheetSection.FuelCosts.cell(252), null),

    // Appendix Q steps 253 and 254 skipped

    Cost(SAPWorksheetSection.FuelCosts.cell(255), null),

    /**
     * SAP Rating (happens outside energy calculator)
     */
    EnergyCostDeflator(SAPWorksheetSection.SAPRating.cell(256), null),
    EnergyCostFactor(SAPWorksheetSection.SAPRating.cell(257), null),
    SAPRating(SAPWorksheetSection.SAPRating.cell(258), null),

    /**
     * CO2 Emissions (happens outside energy calculator)
     */
    Emissions_SpaceHeating_Main_System1(SAPWorksheetSection.Emissions.cell(261), null),
    Emissions_SpaceHeating_Main_System2(SAPWorksheetSection.Emissions.cell(262), null),
    Emissions_SpaceHeating_Secondary(SAPWorksheetSection.Emissions.cell(263), null),
    Emissions_WaterHeating(SAPWorksheetSection.Emissions.cell(264), null),
    Emissions_SpaceAndWaterHeating(SAPWorksheetSection.Emissions.cell(265), null),

    // Always 0 - space cooling not implemented
    Emissions_SpaceCooling(SAPWorksheetSection.Emissions.cell(266), null),

    Emissions_PumpsFansAndKeepHot(SAPWorksheetSection.Emissions.cell(267), null),
    Emissions_Lighting(SAPWorksheetSection.Emissions.cell(268), null),
    Emissions_Generation(SAPWorksheetSection.Emissions.cell(269), null),

    // Appendix Q steps 270 and 271 skipped

    Emissions(SAPWorksheetSection.Emissions.cell(272), null),
    Emissions_PerArea(SAPWorksheetSection.Emissions.cell(273), null),
    Emissions_EIRating(SAPWorksheetSection.Emissions.cell(274), null),

    /**
     * Primary Energy - this section ignored as it is not implemented in the NHM.
     */

    /**
     * Community Heating sections - this section of the worksheet is ignored as it is not implemented in the NHM.
     * We have community heating, but we do not divide it up by the different heat sources.
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
    // Annual calculation not used
    LightingEnergy_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('H', "EL")),

    /**
     * Appliances
     */
    ApplianceEnergy_Initial(null, BREDEMSection.LightsAppliancesAndCooking.step('I', "EA'")),
    ApplianceEnergy_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('J', "EA,m")),
    // Annual calculation not used
    ApplianceEnergy_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('K', "EA")),
    PumpsAndFansEnergy(null, BREDEMSection.LightsAppliancesAndCooking.step('L', "Ep&f")),

    /**
     * Cooking
     */
    CookingEnergyCombustion_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('M', "EC1 or EC2")),
    CookingEnergyElectri_cAnnual(null, BREDEMSection.LightsAppliancesAndCooking.step('M', "EC1 or EC2")),

    CookingEnergyCombustion_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('N', "EC1,m or EC2,m")),
    CookingEnergyElectric_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('N', "EC1,m or EC2,m")),

    CookingEnergy_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('O', "EC,m")),

    // Range cookers not used
    RangeCookingEnergy_Monthly(null, BREDEMSection.LightsAppliancesAndCooking.step('P', "ER,m")),
    RangeCookingEnergy_Annual(null, BREDEMSection.LightsAppliancesAndCooking.step('Q', "ER"))
    ;

    private final SAPLocation sapLocation;
    private final BREDEMLocation bredemLocation;

    private EnergyCalculationStep(SAPLocation sapLocation, BREDEMLocation bredemLocation) {
        this.sapLocation = sapLocation;
        this.bredemLocation = bredemLocation;
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
