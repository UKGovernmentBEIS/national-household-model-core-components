package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

/**
 * Generated by EnergyCalculationstepsGenerate.java
 */
@Doc({
        "Steps in the SAP 2012 worksheet http://www.bre.co.uk/filelibrary/SAP/2012/SAP-2012_9-92.pdf which can be output by the model.",
        "In the future, it may be extended to include steps from BREDEM 2012."
})
@Category(CategoryType.CATEGORIES)
public enum XEnergyCalculationStep {
    @Doc({
            "SAP 2012 worksheet cell (4) in section Dimensions.",
            "Annual.",
            "MetresSquared"
    })
    TotalFloorArea,
    @Doc({
            "SAP 2012 worksheet cell (5) in section Dimensions.",
            "Annual.",
            "MetresCubed"
    })
    DwellingVolume,
    @Doc({
            "SAP 2012 worksheet cell (6a) in section Ventilation.",
            "Annual.",
            "MetresCubed per Hour"
    })
    ChimneyVentilation,
    @Doc({
            "SAP 2012 worksheet cell (6b) in section Ventilation.",
            "Annual.",
            "MetresCubed per Hour"
    })
    OpenFluesVentilation,
    @Doc({
            "SAP 2012 worksheet cell (7a) in section Ventilation.",
            "Annual.",
            "MetresCubed per Hour"
    })
    IntermittentFansVentilation,
    @Doc({
            "SAP 2012 worksheet cell (7b) in section Ventilation.",
            "Annual.",
            "MetresCubed per Hour"
    })
    PassiveVentsVentilation,
    @Doc({
            "SAP 2012 worksheet cell (7c) in section Ventilation.",
            "Annual.",
            "MetresCubed per Hour"
    })
    FluelessGasFiresVentilation,
    @Doc({
            "SAP 2012 worksheet cell (8) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    AirChanges_ChimneysFluesFansAndPSVs,
    @Doc({
            "SAP 2012 worksheet cell (9) in section Ventilation.",
            "Annual.",
            "Count"
    })
    Storeys,
    @Doc({
            "SAP 2012 worksheet cell (10) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationAdditionalStackEffect,
    @Doc({
            "SAP 2012 worksheet cell (11) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationStructural,
    @Doc({
            "SAP 2012 worksheet cell (12) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationGroundFloor,
    @Doc({
            "SAP 2012 worksheet cell (13) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationAbsenceOfDraughtLobby,
    @Doc({
            "SAP 2012 worksheet cell (14) in section Ventilation.",
            "Annual.",
            "DimensionlessProportion"
    })
    ProportionWindowsDraughtProofed,
    @Doc({
            "SAP 2012 worksheet cell (15) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationWindows,
    @Doc({
            "SAP 2012 worksheet cell (16) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationRate_Initial,
    @Doc({
            "SAP 2012 worksheet cell (18) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationRateMaybePressureTest,
    @Doc({
            "SAP 2012 worksheet cell (19) in section Ventilation.",
            "Annual.",
            "Count"
    })
    SidesSheltered,
    @Doc({
            "SAP 2012 worksheet cell (20) in section Ventilation.",
            "Annual.",
            "Dimensionless"
    })
    ShelterFactor,
    @Doc({
            "SAP 2012 worksheet cell (21) in section Ventilation.",
            "Annual.",
            "AirChanges per Hour"
    })
    InfiltrationRate_IncludingShelter,
    @Doc({
            "SAP 2012 worksheet cell (22) in section Ventilation.",
            "Monthly, or averaged over all the months in the year.",
            "Metres per Second"
    })
    AverageWindSpeed,
    @Doc({
            "SAP 2012 worksheet cell (23) in section Ventilation.",
            "Monthly, or averaged over all the months in the year.",
            "Dimensionless"
    })
    WindFactor,
    @Doc({
            "SAP 2012 worksheet cell (24) in section Ventilation.",
            "Monthly, or averaged over all the months in the year.",
            "AirChanges per Hour"
    })
    InfiltrationRate_IncludingShelterAndWind,
    @Doc({
            "Annual.",
            "Dimensionless"
    })
    SiteExposureFactor,
    @Doc({
            "Monthly, or averaged over all the months in the year.",
            "AirChanges per Hour"
    })
    InfiltrationRate_IncludingShelterAndWindAndSiteExposure,
    @Doc({
            "SAP 2012 worksheet cell (24d) in section Ventilation.",
            "Monthly, or averaged over all the months in the year.",
            "AirChanges per Hour"
    })
    Ventilation_NaturalOrPositiveFromLoft,
    @Doc({
            "SAP 2012 worksheet cell (25) in section Ventilation.",
            "Monthly, or averaged over all the months in the year.",
            "AirChanges per Hour"
    })
    AirChanges_Effective,
    @Doc({
            "SAP 2012 worksheet cell (26) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_DoorsSolid,
    @Doc({
            "SAP 2012 worksheet cell (26a) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_DoorsSemiGlazed,
    @Doc({
            "SAP 2012 worksheet cell (27) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_Window,
    @Doc({
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_Window_UPVC_Or_Wood,
    @Doc({
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_Window_Metal,
    @Doc({
            "SAP 2012 worksheet cell (27a) in section HeatLosses and Heat Loss Parameter.",
            "Always has value 0.0 in the NHM.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_Window_Roof,
    @Doc({
            "SAP 2012 worksheet cell (28) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_BasementFloor,
    @Doc({
            "SAP 2012 worksheet cell (28a) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_GroundFloor,
    @Doc({
            "SAP 2012 worksheet cell (28b) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_ExposedFLoor,
    @Doc({
            "SAP 2012 worksheet cell (29) in section HeatLosses and Heat Loss Parameter.",
            "Always has value 0.0 in the NHM.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_BasementWall,
    @Doc({
            "SAP 2012 worksheet cell (29a) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_ExternalWall,
    @Doc({
            "SAP 2012 worksheet cell (30) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    HeatLossCoefficient_Roof,
    @Doc({
            "SAP 2012 worksheet cell (31) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "MetresSquared"
    })
    AreaExternal,
    @Doc({
            "SAP 2012 worksheet cell (32) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "MetresSquared"
    })
    AreaPartyWall,
    @Doc({
            "SAP 2012 worksheet cell (32a) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "MetresSquared"
    })
    AreaPartyFloor,
    @Doc({
            "SAP 2012 worksheet cell (32b) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "MetresSquared"
    })
    AreaPartyCeiling,
    @Doc({
            "SAP 2012 worksheet cell (32c) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "MetresSquared"
    })
    AreaInternalWall,
    @Doc({
            "SAP 2012 worksheet cell (32d) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "MetresSquared"
    })
    AreaInternalFloor,
    @Doc({
            "SAP 2012 worksheet cell (32e) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "MetresSquared"
    })
    AreaInternalCeiling,
    @Doc({
            "SAP 2012 worksheet cell (33) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    FabricHeatLoss,
    @Doc({
            "SAP 2012 worksheet cell (35) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Kilo Joules per MetreSquared Kelvin"
    })
    ThermalMassParameter,
    @Doc({
            "SAP 2012 worksheet cell (36) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    ThermalBridges,
    @Doc({
            "SAP 2012 worksheet cell (37) in section HeatLosses and Heat Loss Parameter.",
            "Annual.",
            "Watts per Kelvin"
    })
    FabricLossTotal,
    @Doc({
            "SAP 2012 worksheet cell (38) in section HeatLosses and Heat Loss Parameter.",
            "Monthly, or averaged over all the months in the year.",
            "Watts per Kelvin"
    })
    VentilationHeatLoss,
    @Doc({
            "SAP 2012 worksheet cell (39) in section HeatLosses and Heat Loss Parameter.",
            "Monthly, or averaged over all the months in the year.",
            "Watts per Kelvin"
    })
    HeatTransferCoefficient,
    @Doc({
            "SAP 2012 worksheet cell (40) in section HeatLosses and Heat Loss Parameter.",
            "Monthly, or averaged over all the months in the year.",
            "Watts per MetreSquared Kelvin"
    })
    HeatLossParameter,
    @Doc({
            "SAP 2012 worksheet cell (41) in section HeatLosses and Heat Loss Parameter.",
            "Monthly, or summed over all the months in the year.",
            "Count"
    })
    DaysInMonth,
    @Doc({
            "SAP 2012 worksheet cell (42) in section Water Heating.",
            "Annual.",
            "Count"
    })
    Occupancy,
    @Doc({
            "SAP 2012 worksheet cell (43) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Litres per Day"
    })
    WaterHeating_Usage_Initial,
    @Doc({
            "SAP 2012 worksheet cell (44) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Litres per Day"
    })
    WaterHeating_Usage_MonthAdjusted,
    @Doc({
            "SAP 2012 worksheet cell (45) in section Water Heating.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_EnergyContent,
    @Doc({
            "SAP 2012 worksheet cell (46) in section Water Heating.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_DistributionLoss,
    @Doc({
            "SAP 2012 worksheet cell (47) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "MetresCubed"
    })
    WaterHeating_StorageVolume,
    @Doc({
            "SAP 2012 worksheet cell (51) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts per Litre inside the NHM, converted to Kilo Watt Hours per Litre per Day."
    })
    WaterHeating_StorageLossFactor,
    @Doc({
            "SAP 2012 worksheet cell (52) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Dimensionless"
    })
    WaterHeating_StorageVolumeFactor,
    @Doc({
            "SAP 2012 worksheet cell (53) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Dimensionless"
    })
    WaterHeating_StorageTemperatureFactor,
    @Doc({
            "SAP 2012 worksheet cell (54) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Day."
    })
    WaterHeating_StorageLosses_Daily_Calculated,
    @Doc({
            "SAP 2012 worksheet cell (55) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Day."
    })
    WaterHeating_StorageLosses_Daily,
    @Doc({
            "SAP 2012 worksheet cell (56) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_StorageLosses_Monthly,
    @Doc({
            "SAP 2012 worksheet cell (57) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_StorageLosses_Monthly_ExcludeSolar,
    @Doc({
            "SAP 2012 worksheet cell (59) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_PrimaryCircuitLoss_Monthly,
    @Doc({
            "SAP 2012 worksheet cell (61) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_CombiLoss_Monthly,
    @Doc({
            "SAP 2012 worksheet cell (62) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_TotalHeat_Monthly_BeforeSolar,
    @Doc({
            "SAP 2012 worksheet cell (63) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_Solar,
    @Doc({
            "SAP 2012 worksheet cell (64) in section Water Heating.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    WaterHeating_TotalHeat_Monthly,
    @Doc({
            "SAP 2012 worksheet cell (65) in section Water Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Gains_HotWater_Monthly,
    @Doc({
            "SAP 2012 worksheet cell (66) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Metabolic,
    @Doc({
            "SAP 2012 worksheet cell (67) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Lighting,
    @Doc({
            "SAP 2012 worksheet cell (68) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Appliances,
    @Doc({
            "SAP 2012 worksheet cell (69) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Cooking,
    @Doc({
            "SAP 2012 worksheet cell (70) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_PumpsAndFans,
    @Doc({
            "SAP 2012 worksheet cell (71) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Evaporation,
    @Doc({
            "SAP 2012 worksheet cell (72) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_HotWater,
    @Doc({
            "SAP 2012 worksheet cell (73) in section Gains Internal.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Internal,
    @Doc({
            "SAP 2012 worksheet cell (82) in section Gains Solar.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Solar_Roof,
    @Doc({
            "SAP 2012 worksheet cell (83) in section Gains Solar.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Solar,
    @Doc({
            "SAP 2012 worksheet cell (84) in section Gains Solar.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains,
    @Doc({
            "SAP 2012 worksheet cell (85) in section Mean Internal Temperature.",
            "Annual.",
            "Centigrade"
    })
    DemandTemperature_LivingArea,
    @Doc({
            "SAP 2012 worksheet cell (86) in section Mean Internal Temperature.",
            "Monthly, or averaged over all the months in the year.",
            "DimensionlessProportion"
    })
    GainsUtilisation_LivingArea,
    @Doc({
            "SAP 2012 worksheet cell (87) in section Mean Internal Temperature.",
            "Monthly, or averaged over all the months in the year.",
            "Centigrade"
    })
    MeanInternalTemperature_LivingArea,
    @Doc({
            "SAP 2012 worksheet cell (88) in section Mean Internal Temperature.",
            "Monthly, or averaged over all the months in the year.",
            "Centigrade"
    })
    DemandTemperature_RestOfDwelling,
    @Doc({
            "SAP 2012 worksheet cell (89) in section Mean Internal Temperature.",
            "Monthly, or averaged over all the months in the year.",
            "DimensionlessProportion"
    })
    GainsUtilisation_RestOfDwelling,
    @Doc({
            "SAP 2012 worksheet cell (90) in section Mean Internal Temperature.",
            "Monthly, or averaged over all the months in the year.",
            "Centigrade"
    })
    MeanInternalTemperature_RestOfDwelling,
    @Doc({
            "SAP 2012 worksheet cell (91) in section Mean Internal Temperature.",
            "Annual.",
            "DimensionlessProportion"
    })
    LivingAreaFraction,
    @Doc({
            "SAP 2012 worksheet cell (92) in section Mean Internal Temperature.",
            "Monthly, or averaged over all the months in the year.",
            "Centigrade"
    })
    MeanInternalTemperature_Unadjusted,
    @Doc({
            "SAP 2012 worksheet cell (93) in section Mean Internal Temperature.",
            "Monthly, or averaged over all the months in the year.",
            "Centigrade"
    })
    MeanInternalTemperature,
    @Doc({
            "SAP 2012 worksheet cell (94) in section Space Heating.",
            "Monthly, or averaged over all the months in the year.",
            "DimensionlessProportion"
    })
    GainsUtilisation,
    @Doc({
            "SAP 2012 worksheet cell (95) in section Space Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    Gains_Useful,
    @Doc({
            "SAP 2012 worksheet cell (96) in section Space Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Centigrade"
    })
    ExternalTemperature,
    @Doc({
            "SAP 2012 worksheet cell (97) in section Space Heating.",
            "Monthly, or averaged over all the months in the year.",
            "Watts"
    })
    HeatLossRate,
    @Doc({
            "SAP 2012 worksheet cell (98) in section Space Heating.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    SpaceHeating,
    @Doc({
            "SAP 2012 worksheet cell (201) in section Energy Requirements.",
            "Annual.",
            "DimensionlessProportion"
    })
    SpaceHeating_Fraction_Secondary,
    @Doc({
            "SAP 2012 worksheet cell (202) in section Energy Requirements.",
            "Annual.",
            "DimensionlessProportion"
    })
    SpaceHeating_Fraction_Main,
    @Doc({
            "SAP 2012 worksheet cell (203) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Annual.",
            "DimensionlessProportion"
    })
    SpaceHeating_FractionWithinMainSystem,
    @Doc({
            "SAP 2012 worksheet cell (204) in section Energy Requirements.",
            "Annual.",
            "DimensionlessProportion"
    })
    SpaceHeating_Fraction_Main_System1,
    @Doc({
            "SAP 2012 worksheet cell (205) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Annual.",
            "DimensionlessProportion"
    })
    SpaceHeating_Fraction_Main_System2,
    @Doc({
            "SAP 2012 worksheet cell (206) in section Energy Requirements.",
            "Annual.",
            "DimensionlessProportion"
    })
    SpaceHeating_Efficiency_Main_System1,
    @Doc({
            "SAP 2012 worksheet cell (208) in section Energy Requirements.",
            "Annual.",
            "DimensionlessProportion"
    })
    SpaceHeating_Efficiency_Secondary,
    @Doc({
            "SAP 2012 worksheet cell (213) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Energy_SpaceHeating_Fuel_Main_system2,
    @Doc({
            "SAP 2012 worksheet cell (216) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Energy_WaterHeating_TotalHeat_Annual,
    @Doc({
            "SAP 2012 worksheet cell (217) in section Energy Requirements.",
            "Monthly, or averaged over all the months in the year.",
            "DimensionlessProportion"
    })
    WaterHeating_Efficiency,
    @Doc({
            "SAP 2012 worksheet cell (221) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Energy_SpaceCooling,
    @Doc({
            "SAP 2012 worksheet cell (230a) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_MechanicalVentilationFans,
    @Doc({
            "SAP 2012 worksheet cell (230b) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_WarmAirFans,
    @Doc({
            "SAP 2012 worksheet cell (230c) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_WaterPump,
    @Doc({
            "SAP 2012 worksheet cell (230d) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_OilBoilerPump,
    @Doc({
            "SAP 2012 worksheet cell (230e) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_BoilerFlueFan,
    @Doc({
            "SAP 2012 worksheet cell (230f) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_KeepHot,
    @Doc({
            "SAP 2012 worksheet cell (230g) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_SolarWaterHeatingPump,
    @Doc({
            "SAP 2012 worksheet cell (230h) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot_StorageWWHRSPump,
    @Doc({
            "SAP 2012 worksheet cell (231) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    PumpsFansAndKeepHot,
    @Doc({
            "SAP 2012 worksheet cell (233) in section Energy Requirements.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Generation_PhotoVoltaic,
    @Doc({
            "SAP 2012 worksheet cell (234) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Generation_WindTurbines,
    @Doc({
            "SAP 2012 worksheet cell (235) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Generation_MicroCHP,
    @Doc({
            "SAP 2012 worksheet cell (235a) in section Energy Requirements.",
            "Always has value 0.0 in the NHM.",
            "Monthly, or summed over all the months in the year.",
            "Watts inside the NHM, converted to Kilo Watt Hours per Month."
    })
    Generation_Hydro,;
}
