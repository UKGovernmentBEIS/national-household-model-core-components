package uk.org.cse.nhm.ehcs10.fuel_poverty;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.fuel_poverty.types.Enum215;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface FuelPovertyDataset2010_AdvancedUseDatasetEntry extends SurveyEntry {

    @SavVariableMapping("WFPX")
    public Integer getIncomeSplit_WinterFuelPayment___();

    @SavVariableMapping("SAVINGSX")
    public Integer getIncomeSplit_SavingsAndInvestment___();

    @SavVariableMapping("OTHERX")
    public Double getIncomeSplit_AllOtherIncomeSourcesIncludingIncomeFromAdditionalAdults___();

    @SavVariableMapping("SOLID_WA")
    public Double getAnnualCostOfSolidFuelForWaterHeating___();

    @SavVariableMapping("SOLID_SP")
    public Double getAnnualCostOfSolidFuelForSpaceHeating___();

    @SavVariableMapping("ELEC_LIG")
    public Double getAnnualCostOfElectricityForLightsAndAppliances___();

    @SavVariableMapping("ELEC_WAT")
    public Double getAnnualCostOfElectricityForWaterHeating___();

    @SavVariableMapping("USEGAS_W")
    public Double getEnergyUsedForWaterHeating_GasKWh_Yr_();

    @SavVariableMapping("USEOIL_W")
    public Double getEnergyUsedForWaterHeating_OilKWh_Yr_();

    @SavVariableMapping("ELEC_SPA")
    public Double getAnnualCostOfElectricityForSpaceHeating___();

    @SavVariableMapping("USEOIL_S")
    public Double getEnergyUsedForSpaceHeating_OilKWh_Yr_();

    @SavVariableMapping("OIL_WATC")
    public Double getAnnualCostOfOil_LPG_PropaneForWaterHeating___();

    @SavVariableMapping("EARNINGS")
    public Double getIncomeSplit_Earnings___();

    @SavVariableMapping("USESOLID")
    public Double getEnergyUsedForSpaceHeating_SolidKWh_Yr_();

    @SavVariableMapping("GAS_COOK")
    public Double getAnnualCostOfGasForCooking___();

    @SavVariableMapping("OIL_SPAC")
    public Double getAnnualCostFoOil_LPG_PropaneForSpaceHeating___();

    @SavVariableMapping("USEELEC")
    public Double getEnergyUsedForSpaceHeating_ElecKWh_Yr_();

    @SavVariableMapping("V25_A")
    public Double getEnergyUsedForLightsAndAppliances_ElecKWh_Yr_();

    @SavVariableMapping("V23_A")
    public Double getEnergyUsedForWaterHeating_ElecKWh_Yr_();

    @SavVariableMapping("USEGAS_S")
    public Double getEnergyUsedForSpaceHeating_GasKWh_Yr_();

    @SavVariableMapping("USEGAS_C")
    public Double getEnergyUsedForCooking_GasKWh_Yr_();

    @SavVariableMapping("GAS_WATC")
    public Double getAnnualCostOfGas_CommunalForWaterHeating___();

    @SavVariableMapping("ELEC_COO")
    public Double getAnnualCostOfElectricityForCooking___();

    @SavVariableMapping("GAS_SPAC")
    public Double getAnnualCostOfGas_CommunalForSpaceHeating___();

    @SavVariableMapping("V29_A")
    public Double getEnergyUsedForWaterHeating_SolidKWh_Yr_();

    @SavVariableMapping("BENEFITS")
    public Double getIncomeSplit_Benefits_IncludingHousingRelatedBenefits____();

    @SavVariableMapping("V24_A")
    public Double getEnergyUsedForCooking_ElecKWh_Yr_();

    @SavVariableMapping("WFG_PREA")
    public Enum10 getEligibleForWarmFrontGrant_BasedUponCriteriaPriorToApril2011_();

    @SavVariableMapping("WFG_POST")
    public Enum10 getEligibleForWarmFrontGrant_BasedUponCriteriaPostApril2011_();

    @SavVariableMapping("HEATREGM")
    public Enum215 getHeatingRegime();

    @SavVariableMapping("INCSPLIT")
    public Enum10 getIncomeSplitsEstimatedDueToMissingInformation();

    @SavVariableMapping("CERT_SPG")
    public Enum10 getInCERTSuperPriorityGroup();

    @SavVariableMapping("CERTPRIO")
    public Enum10 getInCERTPriorityGroup();

    @SavVariableMapping("ONGAS")
    public Enum10 getWhetherDwellingIsOnTheGasNetwork();

}
