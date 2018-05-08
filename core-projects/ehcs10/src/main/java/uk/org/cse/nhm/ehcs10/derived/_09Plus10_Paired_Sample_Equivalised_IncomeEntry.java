package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.ehcs10.derived.types.Enum0;
import uk.org.cse.nhm.ehcs10.derived.types.Enum3;
import uk.org.cse.nhm.ehcs10.derived.types.Enum8;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface _09Plus10_Paired_Sample_Equivalised_IncomeEntry extends SurveyEntry {

    @SavVariableMapping("PEOPLEGR")
    public Integer getQuasiGrossingFactorForIndividualsInTheHouseholdUsingAagph910();

    @SavVariableMapping("AHCINCEQ")
    public Double getAHCEquivalisedWeeklyIncome_ModifiedOECDScale_();

    @SavVariableMapping("BHCINCEQ")
    public Double getBHCEquivalisedWeeklyIncome_ModifiedOECDScale_();

    @SavVariableMapping("BHCINC")
    public Double getBHCAnnualHouseholdIncome();

    @SavVariableMapping("AHCINC")
    public Double getAHCHouseholdAnnualIncome();

    @SavVariableMapping("V15_A")
    public Enum0 getAHC_Below70_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("V7_A")
    public Enum0 getBHC_Below60_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("V14_A")
    public Enum0 getAHC_Below60_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("V5_A")
    public Enum3 getBHCEquivalisedIncomeDeciles_WeightedByPeoplegross_();

    @SavVariableMapping("V13_A")
    public Enum0 getAHC_Below50_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("V12_A")
    public Enum3 getAHCEquivalisedIncomeDeciles();

    @SavVariableMapping("V6_A")
    public Enum0 getBHC_Below50_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("V8_A")
    public Enum0 getBHC_Below70_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("V4_A")
    public Enum8 getBHCEquivalisedIncomeQuintiles_WeightedByPeoplegross_();

    @SavVariableMapping("V11_A")
    public Enum8 getAHCEquivalisedIncomeQuintiles_WeightedByPeoplegross_();

}
