package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface AdaptEntry extends SurveyEntry {

    @SavVariableMapping("DSMOBPB")
    public Integer getPersonWithMostSevereProblemsGettingAroundHouse();

    @SavVariableMapping("HASBCOST")
    public Double getWouldCostMoreThanCanAfford();

    @SavVariableMapping("HASBLDPY")
    public Double getLandlordWon_TPay();

    @SavVariableMapping("HASBOTHR")
    public Double getOtherReasonForNoModification();

    @SavVariableMapping("HASBEXPT")
    public Double getExpectModificationsWillBeMade_NotEnoughTimeYet();

    @SavVariableMapping("HASBLDAW")
    public Double getLandlordWon_TAllowIt();

    @SavVariableMapping("HASBNWTH")
    public Double getModificationsNotWorthDoing();

    @SavVariableMapping("HAS443")
    public Double getIsYourAccommodationSuitableForThePerson_S_WhoHas_HaveThisIllnessOrDisability();

    @SavVariableMapping("HASBNTKW")
    public Double getWouldn_TKnowHowToGetSomethingDone();

    @SavVariableMapping("HASBGRNT")
    public Double getCan_T_Wouldn_TGetAGrant();

    @SavVariableMapping("HASBTRST")
    public Double getDoesn_TTrustBuilders();

    @SavVariableMapping("HAS443C")
    public Enum69 getAreYouAttemptingToMoveInOrderToGetSomewhereMoreSuitableToCopeWithTheDisability_();

    @SavVariableMapping("DSADAPT")
    public Enum69 getDisabilityOf_Most_DisabledPersonInHouseholdRequiresAdaptationToTheHome();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

}
