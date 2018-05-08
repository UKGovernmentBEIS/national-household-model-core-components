package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.ehcs10.derived.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Interview_09Plus10Entry extends SurveyEntry {

    @SavVariableMapping("AGEOLDX")
    public Integer getAgeOfOldestPersonInHousehold();

    @SavVariableMapping("HHSIZEX")
    public Integer getNumberOfPersonsInTheHousehold();

    @SavVariableMapping("AGEPARTX")
    public Integer getAgeOfPartner_Continuous();

    @SavVariableMapping("OLDERX")
    public Integer getNoOfPeopleAged60PlusWhoAreHRPOrPartner();

    @SavVariableMapping("PYNGX")
    public Integer getAgeOfYoungestPersonInHousehold();

    @SavVariableMapping("LENRES")
    public Integer getLengthOfResidence_Years_();

    @SavVariableMapping("NDEPCHIL")
    public Integer getNumberOfDependentChildrenInHousehold();

    @SavVariableMapping("AGEHRPX")
    public Integer getAgeOfHRP_Continuous();

    @SavVariableMapping("FAMNUMX")
    public Integer getNumberOfFamilyUnitsInHhold();

    @SavVariableMapping("LENOWN")
    public Integer getLengthOfOwnership_Years_();

    @SavVariableMapping("NBEDSX")
    public Integer getTotalNoOfBedroomsHouseholdActuallyHas();

    @SavVariableMapping("BEDRQX")
    public Integer getNo_OfBedroomsRequiredByTheHousehold();

    @SavVariableMapping("MORTWKX")
    public Double getWeeklyMortgagePayments();

    @SavVariableMapping("BHCINCEQ")
    public Double getBHCEquivalisedWeeklyIncome_ModifiedOECDScale_();

    @SavVariableMapping("AMTHBENX")
    public Double getWeeklyHousingBenefit();

    @SavVariableMapping("EQUITYR")
    public Double getEquityInHome_BasedOnRespondentValuationOnly_();

    @SavVariableMapping("RENTEXS")
    public Double getTotalWeeklyRentExcludingTheCostOfServices();

    @SavVariableMapping("HHINCX")
    public Double getEHSBasicIncome_AnnualNetHouseholdIncome_HRP_Partner_IncludingSavings_();

    @SavVariableMapping("RENTWKX")
    public Double getTotalWeeklyRentPayable_RentPlusHousingBenefit_();

    @SavVariableMapping("AHCINCEQ")
    public Double getAHCEquivalisedWeeklyIncome_ModifiedOECDScale_();

    @SavVariableMapping("HHTYPE7")
    public Enum59 getHouseholdType_7Categories();

    @SavVariableMapping("AGEHRP4X")
    public Enum60 getAgeOfHouseholdReferencePerson_4Band();

    @SavVariableMapping("V59_A")
    public Enum8 getAHCEquivalisedIncomeQuintiles_WeightedByPeoplegross_();

    @SavVariableMapping("TENEX")
    public Enum62 getExtendedTenureOfHousehold();

    @SavVariableMapping("RENTEXSF")
    public Enum63 getRentExcludingServicesChanged_Imputed();

    @SavVariableMapping("OTHFAMLP")
    public Enum64 getTypeOfAdditionalFamiliesInHousehold();

    @SavVariableMapping("V60_A")
    public Enum0 getAHC_Below60_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("AGEHRP6X")
    public Enum66 getAgeOfHouseholdReferencePerson_6Band();

    @SavVariableMapping("EQUITYR5")
    public Enum67 getEquityInHome_BasedOnRespondentValuationOnly__EQUITYR5();

    @SavVariableMapping("EMPPRTX")
    public Enum68 getEmploymentStatus_Primary_OfPartner();

    @SavVariableMapping("OTHERFAM")
    public Enum69 getAdditionalFamiliesPresentInHousehold();

    @SavVariableMapping("LONCOUPX")
    public Enum70 getSingleHouseholderOrWithPartner();

    @SavVariableMapping("SEXHRP")
    public Enum71 getSexOfHouseholdReferencePerson();

    @SavVariableMapping("HHINC5X")
    public Enum72 getAllHouseholds_IncomeIn5Bands();

    @SavVariableMapping("FREELEAS")
    public Enum73 getFreeholdOrLeasehold();

    @SavVariableMapping("HHBENSX")
    public Enum10 getHouseholdOnMeansTestedBensOrTaxCreditsWithARelevantIncomeBelowTheThreshold();

    @SavVariableMapping("HOUSBENX")
    public Enum10 getHousehold_HRP_Partner_ReceivesAnyHousingBenefit_();

    @SavVariableMapping("OWNTYPE")
    public Enum76 getTypeOfOwnership();

    @SavVariableMapping("HHTYPE11")
    public Enum77 getHousehldType_Full11Categories();

    @SavVariableMapping("HHLTSICK")
    public Enum69 getAnyoneInHholdHaveLessThanIllnessOrDisability_();

    @SavVariableMapping("EMPHRPX")
    public Enum68 getEmploymentStatus_Primary_OfHRP();

    @SavVariableMapping("HHINCFLG")
    public Enum80 getImputationsUsedToCreateNetTotalHholdIncome();

    @SavVariableMapping("V62_A")
    public Enum8 getBHCEquivalisedIncomeQuintiles_WeightedByPeoplegross_();

    @SavVariableMapping("EMPHRP3X")
    public Enum82 getWorkingStatusOfHRP_Primary__3Categories();

    @SavVariableMapping("LENRESB")
    public Enum83 getLengthOfResidence();

    @SavVariableMapping("HHCOMPX")
    public Enum84 getHouseholdComposition();

    @SavVariableMapping("HHVULX")
    public Enum10 getHouseholdVulnerable_OnMeansTestedOrCertainDisabilityRelatedBenefits_();

    @SavVariableMapping("ETHHRP4X")
    public Enum86 getEthnicOriginOfHRP_4Categories();

    @SavVariableMapping("ETHHRP8X")
    public Enum87 getEthnicOriginOfHRP_8Categories();

    @SavVariableMapping("HHEMPX")
    public Enum88 getEmploymentStatusOfHRPAndPartnerCombined();

    @SavVariableMapping("EMPPRT3X")
    public Enum82 getWorkingStatusOfPartner_Primary__3Categories();

    @SavVariableMapping("PYNGBX")
    public Enum90 getAgeBandOfYoungestPersonInHousehold();

    @SavVariableMapping("LENOWNB")
    public Enum83 getLengthOfOwnershipToDateOfSurvey();

    @SavVariableMapping("V63_A")
    public Enum0 getBHC_Below60_OfMedianIncome_WeightedByPeoplegross_();

    @SavVariableMapping("HPREGDIS")
    public Enum69 getHRPOrPartnerRegisteredDisabled_();

    @SavVariableMapping("HHTYPE6")
    public Enum94 getHouseholdType_6Categories();

    @SavVariableMapping("AGEHRP2X")
    public Enum95 getAgeOfHouseholdReferencePerson_2Band();

    @SavVariableMapping("AGEOLDBX")
    public Enum96 getAgeOfOldestPersonInHousehold_Banded();

    @SavVariableMapping("WORKLESS")
    public Enum97 getHouseholdWithNoOneOfWorkingAgeEmployed_ILODefn();

    @SavVariableMapping("ETHHRP2X")
    public Enum98 getEthnicOriginOfHRP_2Categories();

    @SavVariableMapping("BEDSTDX")
    public Enum99 getBedroomStandard();

    @SavVariableMapping("RENTFLG")
    public Enum100 getRent_HousingBenefitChanged_Imputed();

}
