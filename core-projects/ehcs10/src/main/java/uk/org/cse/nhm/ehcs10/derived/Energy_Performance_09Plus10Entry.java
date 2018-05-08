package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.derived.types.Enum16;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Energy_Performance_09Plus10Entry extends SurveyEntry {

    @SavVariableMapping("EPPALC09")
    public Double getPost_ImprovementNotionalTotalEnergyCurrentCost___Yr__EHSSAP2009_();

    @SavVariableMapping("EPPSAP09")
    public Double getPost_ImprovementEnergyEfficiencyRating_EHSSAP2009_();

    @SavVariableMapping("EPCALC09")
    public Double getNotionalTotalEnergyCurrentCost___Yr__EHSSAP2009_();

    @SavVariableMapping("EPPSHC09")
    public Double getPost_ImprovementNotionalSpaceHeatingCurrentCost___Yr__EHSSAP2009_();

    @SavVariableMapping("EPPWHC09")
    public Double getPost_ImprovementNotionalWaterHeatingCurrentCost___Yr__EHSSAP2009_();

    @SavVariableMapping("EPCSHC09")
    public Double getNotionalSpaceHeatingCurrentCost___Yr__EHSSAP2009_();

    @SavVariableMapping("EPUCOS09")
    public Double getEnergyUpgradeCost_AllUpgrades____EHSSAP2009_();

    @SavVariableMapping("EPCLTC09")
    public Double getNotionalLightingCurrentCost___Yr__EHSSAP2009_();

    @SavVariableMapping("EPCUSE09")
    public Double getNotionalPrimaryEnergyUse_KWh_M2PerYear__EHSSAP2009_();

    @SavVariableMapping("EPPCO209")
    public Double getPost_ImprovementNotionalTotalCO2CurrentEmissions_Tonnes_Yr__EHSSAP2009_();

    @SavVariableMapping("EPCWHC09")
    public Double getNotionalWaterHeatingCurrentCost___Yr__EHSSAP2009_();

    @SavVariableMapping("EPCCO209")
    public Double getNotionalTotalCO2CurrentEmissions_Tonnes_Yr__EHSSAP2009_();

    @SavVariableMapping("EPPEIR09")
    public Double getPost_ImprovementEnvironmentalImpactRating_EHSSAP2009_();

    @SavVariableMapping("EPULIN09")
    public Enum10 getEnergyUpgrade_LowCostMeasure__LoftInsulation_EHSSAP2009_();

    @SavVariableMapping("EPUCYT09")
    public Enum10 getEnergyUpgrade_HigherCostMeasure__CylinderThermostat_EHSSAP2009_();

    @SavVariableMapping("EPUCTR09")
    public Enum10 getEnergyUpgrade_HigherCostMeasure__HeatingControls_EHSSAP2009_();

    @SavVariableMapping("EPUWAS09")
    public Enum10 getEnergyUpgrade_HigherCostMeasure__WarmAirSystem_EHSSAP2009_();

    @SavVariableMapping("EPUSTR09")
    public Enum10 getEnergyUpgrade_HigherCostMeasure__StorageRadiators_EHSSAP2009_();

    @SavVariableMapping("EPUBLR09")
    public Enum10 getEnergyUpgrade_HigherCostMeasure__Boiler_EHSSAP2009_();

    @SavVariableMapping("EPPEIB09")
    public Enum16 getPost_ImprovementEnvironmentalImpactRatingBand_EHSSAP2009_();

    @SavVariableMapping("EPUCYI09")
    public Enum10 getEnergyUpgrade_LowCostMeasure__CylinderInsulation_EHSSAP2009_();

    @SavVariableMapping("EPPEEB09")
    public Enum16 getPost_ImprovementEnergyEfficiencyRatingBand_EHSSAP2009_();

    @SavVariableMapping("EPUCWI09")
    public Enum10 getEnergyUpgrade_LowCostMeasure__CavityWallInsulation_EHSSAP2009_();

    @SavVariableMapping("EPUBMS09")
    public Enum10 getEnergyUpgrade_HigherCostMeasure__BiomassSystem_EHSSAP2009_();

}
