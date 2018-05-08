package uk.org.cse.nhm.ehcs10.fuel_poverty;

import uk.org.cse.nhm.ehcs10.fuel_poverty.types.Enum220;
import uk.org.cse.nhm.ehcs10.fuel_poverty.types.Enum221;
import uk.org.cse.nhm.ehcs10.fuel_poverty.types.Enum222;
import uk.org.cse.nhm.ehcs10.fuel_poverty.types.Enum223;
import uk.org.cse.nhm.ehcs10.fuel_poverty.types.Enum224;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Fuel_Poverty_Dataset_2010Entry extends SurveyEntry {

    @SavVariableMapping("AAGPH910")
    public Integer getHouseholdWeight_CoreCases2009_10_2010_11_();

    @SavVariableMapping("WATHCOST")
    public Double getCostOfEnergyToHeatWater___();

    @SavVariableMapping("COOKCOST")
    public Double getTotalEnergyCostForCooking___();

    @SavVariableMapping("FPBASINC")
    public Double getAnnualBasicHouseholdIncome___();

    @SavVariableMapping("SPAHCOST")
    public Double getTotalSpaceHeatingCost___();

    @SavVariableMapping("FPFULLIN")
    public Double getAnnualFullHouseholdIncome___();

    @SavVariableMapping("LITECOST")
    public Double getTotalCostForLightsAndApplianceUse___();

    @SavVariableMapping("FPINDF")
    public Double getFuelPovertyIndex_FullIncomeDefinition();

    @SavVariableMapping("FPINDB")
    public Double getFuelPovertyIndex_BasicIncomeDefinition();

    @SavVariableMapping("FUELEXPN")
    public Double getTotalFuelCosts___();

    @SavVariableMapping("FPVULN")
    public Enum220 getVulnerableFlag_FuelPovertyDefinition();

    @SavVariableMapping("FPFLGB")
    public Enum221 getFuelPovertyFlag_BasicIncomeDefinition();

    @SavVariableMapping("UNOC")
    public Enum222 getUnderOccupancy();

    @SavVariableMapping("FPFLGF")
    public Enum223 getFuelPovertyFlag_FullIncomeDefinition();

    @SavVariableMapping("GASMOP")
    public Enum224 getMethodOfPayment_Gas();

    @SavVariableMapping("ELECMOP")
    public Enum224 getMethodOfPayment_Electricity();

}
