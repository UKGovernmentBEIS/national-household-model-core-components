package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface DwellingEntry extends SurveyEntry {

    @SavVariableMapping("YRBULT3")
    public Integer getExactYearBuilt();

    @SavVariableMapping("OREPALND")
    public Double getLender();

    @SavVariableMapping("WYNTFINC")
    public Double getFinancial_EmploymentSituationChanged();

    @SavVariableMapping("OREPAORG")
    public Double getIndependentAdviceOrganisation();

    @SavVariableMapping("OREPHPOS")
    public Double getReceivedAPossessionOrder();

    @SavVariableMapping("WYNTPRPF")
    public Double getDecidedPropertyPricesMightFallFurther();

    @SavVariableMapping("OREPAWEB")
    public Double getGovernment_LAOrOtherWebsite();

    @SavVariableMapping("WYNTDEPT")
    public Double getDidn_TThinkDepositWasLargeEnough();

    @SavVariableMapping("OREPHAP")
    public Double getPriorToGivingUpYourHomeDidAnyOfTheFollowingHappen();

    @SavVariableMapping("OREPAOTH")
    public Double getOtherSource();

    @SavVariableMapping("OREPHLND")
    public Double getAdvisedByLenderToSellHome();

    @SavVariableMapping("WYNTCOMP")
    public Double getApplicationProcessTooComplicated();

    @SavVariableMapping("WYNTCHMD")
    public Double getChangedMindAboutBuying();

    @SavVariableMapping("OREPHCRT")
    public Double getReceivedCourtSummons_HearingDate();

    @SavVariableMapping("WYNTCOST")
    public Double getOverallCostOfMortgageTooHigh();

    @SavVariableMapping("OREPADV")
    public Double getBeforeYouGaveUpYourHome_DidYouSeekAdviceFromAnyOfTheFollowing();

    @SavVariableMapping("APPACPT")
    public Double getWasYourApplicationAccepted();

    @SavVariableMapping("OREPHORG")
    public Double getAdvisedByOtherOrganisationToSellHome();

    @SavVariableMapping("MORTAPPL")
    public Double getHaveYouConsideredApplyingForAMortgage_OrASecuredLoan_ToBuyAPropertyAsYourMainHomeInThePastYear();

    @SavVariableMapping("WYNTDSBL")
    public Double getDiscouragedByBank_Lender();

    @SavVariableMapping("WYNTAPPR")
    public Double getDidn_TThinkApplicationWouldBeApproved();

    @SavVariableMapping("WYNTOTHR")
    public Double getOtherReason();

    @SavVariableMapping("OREPHHEA")
    public Double getAttendedACourtHearing();

    @SavVariableMapping("OREPALA")
    public Double getLocalAuthority();

    @SavVariableMapping("WYNTPERC")
    public Double getPersonalCircumstancesChanges();

    @SavVariableMapping("WYNTINTF")
    public Double getDecidedInterestRatesMightFallFurther();

    @SavVariableMapping("OREPHACT")
    public Double getAdvisedLenderWasTakingLegal_CourtAction();

    @SavVariableMapping("DIDAPPLY")
    public Double getDidYouApply();

    @SavVariableMapping("WHYMMRG")
    public Enum69 getCouldNotAffordMortgagePayments();

    @SavVariableMapping("HAS445")
    public Enum69 getShelteredAccomodation();

    @SavVariableMapping("WHYMSCHL")
    public Enum69 getSoChild_Ren_CouldAttendABetterSchool();

    @SavVariableMapping("PREVACN")
    public Enum318 getPreviousNonTemporaryAccommodation();

    @SavVariableMapping("WHYMLGE")
    public Enum69 getWantedLargerHouse_Flat();

    @SavVariableMapping("OWNPR")
    public Enum69 getWhetherOwnedAnyPreviousAccommodation();

    @SavVariableMapping("TENEMOVE")
    public Enum69 getWantedToMove();

    @SavVariableMapping("SRRECMMT")
    public Enum69 getWouldn_TWantTheCommitment();

    @SavVariableMapping("PREVAC")
    public Enum318 getPreviousAccommodation();

    @SavVariableMapping("WHYMPOOR")
    public Enum69 getPreviousAccomInPoorCondition();

    @SavVariableMapping("SRREOTHR")
    public Enum69 getOtherReasonForNotBuying();

    @SavVariableMapping("SRREFLEX")
    public Enum69 getPreferFlexibilityOfRenting();

    @SavVariableMapping("WHYMMAR")
    public Enum69 getMarriage_BeganLivingTogether();

    @SavVariableMapping("OMB10")
    public Enum328 getYearHomeGivenUp();

    @SavVariableMapping("SRTHIS")
    public Enum69 getWhetherWillBuyThisHouseOrFlat_();

    @SavVariableMapping("MAINR1")
    public Enum330 getMainReasonHRPMoved();

    @SavVariableMapping("PREVLET")
    public Enum331 getTypeOfTenancy();

    @SavVariableMapping("WHYMNOT")
    public Enum69 getAskedToLeaveByLandlord();

    @SavVariableMapping("WLIST")
    public Enum69 getAnotherHhldMemberOnW_List();

    @SavVariableMapping("PREVR")
    public Enum334 getWhoPreviousAccomRentedFrom();

    @SavVariableMapping("SRREWHRE")
    public Enum69 getLikeItWhereIAm();

    @SavVariableMapping("WHYEHB")
    public Enum69 getDifficultiesWithPaymentOfHousingBenefit_LocalHousingAllowance();

    @SavVariableMapping("SRREMNTE")
    public Enum69 getRepairsAndMaintenanceTooCostly();

    @SavVariableMapping("YRBULT1")
    public Enum338 getWhenPropertyBuilt();

    @SavVariableMapping("ACCOM")
    public Enum339 getAccommodationType();

    @SavVariableMapping("WHYESLUS")
    public Enum69 getLandlordWantedToSell_UseProperty();

    @SavVariableMapping("SRLONG")
    public Enum341 getHowLongBeforeYouBuy_();

    @SavVariableMapping("FLTTYP")
    public Enum342 getFlatType();

    @SavVariableMapping("WHYMJOB")
    public Enum69 getJobRelatedReasons();

    @SavVariableMapping("TENEJOB")
    public Enum69 getAccomTiedToJobAndJobEnded();

    @SavVariableMapping("WHYMSML")
    public Enum69 getWantedSmallerHouse_Flat();

    @SavVariableMapping("WHYMCHP")
    public Enum69 getWantedCheaperHouse_Flat();

    @SavVariableMapping("TENEASKE")
    public Enum69 getAskedToLeaveByLandlord_Agent();

    @SavVariableMapping("WHYECOMP")
    public Enum69 getNeighboursComplainedToLandlord();

    @SavVariableMapping("PREV1")
    public Enum349 getWasPreviousAccomSold_();

    @SavVariableMapping("OMB11")
    public Enum350 getBestDescribesHowHomeWasGivenUp();

    @SavVariableMapping("PREV00")
    public Enum351 getPreviousAccom_OwnedOrMortgage();

    @SavVariableMapping("OMB9")
    public Enum69 getGivenUpHomeDueToDifficultiesPayingMortgage();

    @SavVariableMapping("WHYEDIS")
    public Enum69 getLandlordDissatisfied();

    @SavVariableMapping("WHYEOTHR")
    public Enum69 getSomeOtherReason();

    @SavVariableMapping("WHYMOTHR")
    public Enum69 getSomeOtherReason_WHYMOTHR();

    @SavVariableMapping("WHYMDIV")
    public Enum69 getDivorce_Seperation();

    @SavVariableMapping("PREVNEW")
    public Enum357 getPreviousAccom_NewHhldrs();

    @SavVariableMapping("WHYERENT")
    public Enum69 getNonPaymentOfRent();

    @SavVariableMapping("WHYMAREA")
    public Enum69 getToABetterNeighbourhood_Area();

    @SavVariableMapping("SRBUY")
    public Enum360 getWillYouEventuallyBuyAHomeOrShareHomeInTheUK();

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("WHYMLLOR")
    public Enum69 getDidn_TGetOnWithLandlord();

    @SavVariableMapping("WHYMUSUI")
    public Enum69 getPreviousAccomUnsuitable();

    @SavVariableMapping("PLANTEN")
    public Enum364 getLongTerm_HousingExpectedToLiveIn();

    @SavVariableMapping("TEMPINS")
    public Enum251 getIntroductionToAccommodationBeforeTemporaryAccommodation();

    @SavVariableMapping("WHYMFMPS")
    public Enum69 getOtherFamily_PersonalReasons();

    @SavVariableMapping("WHYEPROB")
    public Enum69 getProblemsWithProperty();

    @SavVariableMapping("OWNPRN")
    public Enum368 getWhoOwnedPreviousAccomWith();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("SRREDEBT")
    public Enum69 getWouldn_TWantToBeInDebt();

    @SavVariableMapping("SRREUNBL")
    public Enum69 getUnlikelyToAffordIt();

    @SavVariableMapping("DWELLNEW")
    public Enum69 getFirstResidents();

    @SavVariableMapping("OMB9A")
    public Enum373 getWhoGaveUpTheirHome();

    @SavVariableMapping("HSETYPE")
    public Enum374 getHouseType();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

    @SavVariableMapping("ACCOTH")
    public Enum376 getOtherAccommodationType();

    @SavVariableMapping("WHYMBUY")
    public Enum69 getWantedToBuy();

    @SavVariableMapping("SRRESCJB")
    public Enum69 getDon_THaveASecureJob();

    @SavVariableMapping("TEMPAC")
    public Enum69 getPreviousAccomTemporary_();

    @SavVariableMapping("WHYMOWN")
    public Enum69 getWantedOwnHome_LiveIndependently();

    @SavVariableMapping("TENEMTAG")
    public Enum69 getMutualAgreement();

    @SavVariableMapping("SRREASM")
    public Enum382 getMainReasonNotToBuyOrOwnAHome();

}
