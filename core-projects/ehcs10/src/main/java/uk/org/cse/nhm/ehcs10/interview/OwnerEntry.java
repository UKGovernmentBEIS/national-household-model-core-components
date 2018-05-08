package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.derived.types.Enum73;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface OwnerEntry extends SurveyEntry {

    @SavVariableMapping("ONPURPC")
    public Integer getPurchasePriceOfHome();

    @SavVariableMapping("OTHLOAN")
    public Integer getAmountBorrowedInAdditionToMainMortgage();

    @SavVariableMapping("RYGRDAMT")
    public Integer getTotalGroundRent();

    @SavVariableMapping("OUTSTAND")
    public Integer getAmountOutstandingOnMainMortgage_Loan();

    @SavVariableMapping("MORGPAYU")
    public Integer getUsualPaymentOnAllMortgages_Loans();

    @SavVariableMapping("PRPTVAL2")
    public Integer getHouseholder_SViewOnPropertyValue();

    @SavVariableMapping("PRPTVAL1")
    public Integer getPropertyValuedAt();

    @SavVariableMapping("SOEQSHR2")
    public Integer getPercentageEquityShareNow();

    @SavVariableMapping("ONDEPPND")
    public Integer getDeposit_In_S_();

    @SavVariableMapping("RYSVAMT")
    public Integer getTotalServiceCharge();

    @SavVariableMapping("SOEQSHR1")
    public Integer getPercentageEquityShareAtTimeOfPurchase();

    @SavVariableMapping("ONDEPPER")
    public Integer getDeposit_AsA_();

    @SavVariableMapping("MORGLNTH")
    public Integer getLengthOfMortgage_Years();

    @SavVariableMapping("CURRBAL")
    public Integer getAmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage();

    @SavVariableMapping("ONMNTRPY")
    public Integer getFlexibleMortgage_AverageMonthlyPayment();

    @SavVariableMapping("ONBYYEAR")
    public Integer getAcquiredHome_Year();

    @SavVariableMapping("YRMORGST")
    public Integer getYearCurrentMortgageTakenOut();

    @SavVariableMapping("ONORGMRG")
    public Integer getCurrentMortgage_OriginalAmount();

    @SavVariableMapping("INTTYPE2")
    public Double getCurrentInterestRateDeal();

    @SavVariableMapping("MRGLHDD")
    public Double getSpouse_PartnerLeft_Died();

    @SavVariableMapping("RYSVWK")
    public Double getServiceChargeWeeklyAmount();

    @SavVariableMapping("MRGDIED")
    public Double getSpouse_PartnerDied();

    @SavVariableMapping("INTTYPE3")
    public Double getCurrentInterestRateDeal_INTTYPE3();

    @SavVariableMapping("OUTSTA_1")
    public Double getAmountOutstandingOnOtherLoans();

    @SavVariableMapping("INTCHNGE")
    public Double getHowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months();

    @SavVariableMapping("V149_A")
    public Double getAmountOutstandingOnOtherLoans_V149_A();

    @SavVariableMapping("RYGRDWK")
    public Double getGroundRentWeeklyAmount();

    @SavVariableMapping("PRPCHNG1")
    public Double getByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear();

    @SavVariableMapping("PRPCHNG2")
    public Double getByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months();

    @SavVariableMapping("MRGLHOM")
    public Double getSpouse_PartnerLeftHome();

    @SavVariableMapping("MRGLESPA")
    public Enum69 getSameHoursForLessPay();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

    @SavVariableMapping("REPANONE")
    public Enum69 getRepay_NoneOfThese();

    @SavVariableMapping("ONCHMRG")
    public Enum602 getChangedTypeOfMortgageOnTheProperty();

    @SavVariableMapping("SOURLLRD")
    public Enum69 getMoneyFromPrivateLandlordToEncourageMove();

    @SavVariableMapping("MRGNOACT")
    public Enum69 getNoActionTaken();

    @SavVariableMapping("RENDPNSN")
    public Enum69 getPaymentsIntoPensionMortgage();

    @SavVariableMapping("EQSDBUSI")
    public Enum69 getStartedBusiness();

    @SavVariableMapping("REPASCHM")
    public Enum69 getPaymentsIntoOtherSavings_InvestmentScheme();

    @SavVariableMapping("FREEHLD1")
    public Enum608 getExtentOfFreehold();

    @SavVariableMapping("CURRACC")
    public Enum69 getAll_In_OneAccountMortgage();

    @SavVariableMapping("ONGIFT")
    public Enum69 getAcquiredAsAGift();

    @SavVariableMapping("ONSELLR")
    public Enum611 getPropertyBoughtFrom();

    @SavVariableMapping("MRGLEFT")
    public Enum69 getContributorLeftHome();

    @SavVariableMapping("MRGNONE")
    public Enum69 getNoneOfThese();

    @SavVariableMapping("MRGARN2")
    public Enum614 getHowMortgagePaid_1StOptionThatApplies_();

    @SavVariableMapping("EQSDOTHR")
    public Enum69 getSpentEquity_Other();

    @SavVariableMapping("MORGPERL")
    public Enum616 getMortgagePaymentPeriod();

    @SavVariableMapping("ONDTKNOW")
    public Enum69 getDon_TKnow();

    @SavVariableMapping("REPARMRG")
    public Enum69 getPaymentsOnRepaymentMortgage();

    @SavVariableMapping("POLSKUN")
    public Enum69 getHaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy();

    @SavVariableMapping("REPAPNSN")
    public Enum69 getPaymentsIntoPensionMortgage_REPAPNSN();

    @SavVariableMapping("EQSDGOOD")
    public Enum69 getNewGoodsForTheProperty();

    @SavVariableMapping("DSSMORG")
    public Enum622 getAmountOfMortgageInterestPaidByDWP();

    @SavVariableMapping("LEASE")
    public Enum73 getOwnershipType();

    @SavVariableMapping("MRGINT")
    public Enum69 getInterestOnlyPaymentsAgreed();

    @SavVariableMapping("MRGPPLOW")
    public Enum69 getMortgageProtectionPlanInsufficient();

    @SavVariableMapping("SOURWIND")
    public Enum69 getWindfall();

    @SavVariableMapping("MRGNAGRE")
    public Enum69 getNoAgreementMade();

    @SavVariableMapping("SOURLOAN")
    public Enum69 getLoanForDeposit_BridgingLoan();

    @SavVariableMapping("MRGRGMIS")
    public Enum69 getOneOrMoreRegularPaymentsMissed();

    @SavVariableMapping("ONCMPSIT")
    public Enum630 getComplexTenureStatus();

    @SavVariableMapping("MRGAR21")
    public Enum631 getAnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months();

    @SavVariableMapping("EQSDHOME")
    public Enum69 getHomeImprovements_Renovations();

    @SavVariableMapping("ALLONE")
    public Enum633 getCurrentAccountOrOffsetMortgage();

    @SavVariableMapping("MRGARN2A")
    public Enum69 getHaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP();

    @SavVariableMapping("ONBYSEAS")
    public Enum635 getAcquiredHome_Month();

    @SavVariableMapping("MRGLEGAL")
    public Enum69 getIsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome();

    @SavVariableMapping("MRGMOVED")
    public Enum69 getMovedToNewLender();

    @SavVariableMapping("MRGBABY")
    public Enum69 getContributorPregnant_Baby();

    @SavVariableMapping("SOURFMLY")
    public Enum69 getGift_LoanFromFamily_Friend();

    @SavVariableMapping("ONNONE")
    public Enum69 getNoneOfThese_ONNONE();

    @SavVariableMapping("MRGARR")
    public Enum641 getCurrentMortgageRepaymentSituation();

    @SavVariableMapping("MRGARN2C")
    public Enum69 getAreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing();

    @SavVariableMapping("REPAEDMT")
    public Enum69 getPaymentsIntoAnEndowmentPolicy();

    @SavVariableMapping("FRHLDER")
    public Enum644 getFreeholdOwnedBy();

    @SavVariableMapping("MRGHOL")
    public Enum69 getMortgageHolidayAgreed();

    @SavVariableMapping("RENDSVGS")
    public Enum69 getPaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS();

    @SavVariableMapping("REPATRST")
    public Enum69 getPaymentsIntoUnit_InvestmentTrust();

    @SavVariableMapping("RENDPEP")
    public Enum69 getPaymentsIntoPep_ISA();

    @SavVariableMapping("MPOLCOV")
    public Enum649 getWhatDoesTheInsurancePolicyCover();

    @SavVariableMapping("SOURSALE")
    public Enum69 getSaleOfPreviousHome();

    @SavVariableMapping("PURCLOAN")
    public Enum651 getNumberOfMortgages_LoansOnProperty();

    @SavVariableMapping("ONBLDINS")
    public Enum69 getBuildingInsurance();

    @SavVariableMapping("ONDOWNPM")
    public Enum653 getOriginalCashPayment_Pounds_Percent();

    @SavVariableMapping("MRGRDUEM")
    public Enum69 getRedundancy_Unemployment();

    @SavVariableMapping("MRGOTHER")
    public Enum69 getOtherReason();

    @SavVariableMapping("MRGAR4")
    public Enum69 getAnyEarlierMortgagePaymentsOutstanding();

    @SavVariableMapping("MRGFALSE")
    public Enum69 getMortgageAcctShowsBehind_NotTrue();

    @SavVariableMapping("SOURNONE")
    public Enum69 get_100_Mortgage();

    @SavVariableMapping("ONOTHR")
    public Enum69 getAcquired_Other();

    @SavVariableMapping("EVERCLHA")
    public Enum660 getEverCouncil_HAHouse_Flat();

    @SavVariableMapping("ONCONINS")
    public Enum69 getContentsInsurance();

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("RENDTRST")
    public Enum69 getPaymentsIntoUnit_InvestmentTrust_RENDTRST();

    @SavVariableMapping("EQSDSAVD")
    public Enum69 getInvested_SavedTheMoney();

    @SavVariableMapping("MRGOTRH")
    public Enum69 getLostOvertime_WorkedReducedHours();

    @SavVariableMapping("MRGSKINJ")
    public Enum69 getLostEarningsFromSickness_Injury();

    @SavVariableMapping("MRGEXT")
    public Enum69 getExtensionToLoanPeriodAgreed();

    @SavVariableMapping("SOURINHR")
    public Enum69 getInheritedMoney();

    @SavVariableMapping("SOUROTHR")
    public Enum69 getOtherSource();

    @SavVariableMapping("EQSDVHCL")
    public Enum69 getBoughtCar_OtherVehicle();

    @SavVariableMapping("EQSDDKNW")
    public Enum69 getSpentEquity_Don_TKnowHow();

    @SavVariableMapping("RYSVCCHG")
    public Enum69 getServiceChargePaid();

    @SavVariableMapping("MRGRED")
    public Enum69 getReducedMonthlyPaymentAgreed();

    @SavVariableMapping("ONMORG")
    public Enum69 getBoughtWithMortgage_Loan_S_();

    @SavVariableMapping("RYGRDRNT")
    public Enum69 getPaysGroundRent();

    @SavVariableMapping("MRGDISC")
    public Enum69 getDiscussionsWithLender();

    @SavVariableMapping("SOURSVGS")
    public Enum69 getSavings();

    @SavVariableMapping("MRGAR6")
    public Enum69 getWereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears();

    @SavVariableMapping("ONMRTPP")
    public Enum69 getMortgageProtectionPlan();

    @SavVariableMapping("SOURLAHA")
    public Enum69 getMoneyPaidByLA_HAToEncourageMove();

    @SavVariableMapping("EQSDHLDY")
    public Enum69 getPaidForHoliday();

    @SavVariableMapping("RYCONRPR")
    public Enum69 getContributesToOne_OffRepair_MaintenanceCosts();

    @SavVariableMapping("EQSDUNIV")
    public Enum69 getPaidForUniversityCosts();

    @SavVariableMapping("EQSDDEBT")
    public Enum69 getPayingOffDebts();

    @SavVariableMapping("VALUED")
    public Enum69 getValuedInLast12Months();

    @SavVariableMapping("MRGARN4")
    public Enum686 getHowAreYouCopingWithPaymentsAtTheMoment();

    @SavVariableMapping("HOWPAYM")
    public Enum687 getInterestOnly_ExpectedRepaymentMethod();

    @SavVariableMapping("ONENDPOL")
    public Enum69 getEndowmentPolicyPremium();

    @SavVariableMapping("COMMH")
    public Enum689 getCommonholdType();

    @SavVariableMapping("MRGAR5")
    public Enum690 getHowLongAgoDidYouFirstFallBehindWithRepayments();

    @SavVariableMapping("MORGTYP1")
    public Enum691 getTypeOfMortgage_Loan();

    @SavVariableMapping("ONINHRT")
    public Enum69 getInheritedProperty();

    @SavVariableMapping("ONOUTMRG")
    public Enum693 getWhetherOutstandingMortgage_Loan();

    @SavVariableMapping("REPAPEP")
    public Enum69 getPaymentsIntoPEP_ISA();

    @SavVariableMapping("MRGAR21C")
    public Enum69 getAreYouConsideringContactingYourLenderForHelp();

    @SavVariableMapping("RENDSALE")
    public Enum69 getSaleOfExistingHouseOnly();

    @SavVariableMapping("FREEHL_1")
    public Enum697 getNameInWhichFreeholdHeld();

    @SavVariableMapping("MRGPRTPD")
    public Enum69 getOnlyPartOfRegularMorgagePaid();

    @SavVariableMapping("ONPAID")
    public Enum69 getBoughtWithCash_PaidOutright();

    @SavVariableMapping("EQSDFEES")
    public Enum69 getPaidForSchoolFees();

    @SavVariableMapping("LGTHLN")
    public Enum701 getLengthOfRemainingLease();

    @SavVariableMapping("EQSDBTAD")
    public Enum69 getPurchaseOfAnotherPropertyAbroad();

    @SavVariableMapping("MRGSEINC")
    public Enum69 getSelfEmployedIncomeReduced();

    @SavVariableMapping("CHLEASE")
    public Enum704 getFreeholdOwnership();

    @SavVariableMapping("EQSDMEDI")
    public Enum69 getPaidForMedicalFees_NursingHome();

    @SavVariableMapping("MRGARN2B")
    public Enum69 getHaveYouBeenAbleToPayTheRestOfTheMortgagePayment();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("ONDIVRC")
    public Enum69 getGivenPropertyInDivorceSettlement();

    @SavVariableMapping("EQSDBTFY")
    public Enum69 getPurchaseOfPropertyForFamilyMember();

    @SavVariableMapping("LGTHLF")
    public Enum701 getLengthOfFullLeaseOnThisProperty();

    @SavVariableMapping("MRGINCOT")
    public Enum69 getOtherPaymentsIncreased();

    @SavVariableMapping("EQSDBTUK")
    public Enum69 getPurchaseOfAnotherPropertyInUK();

    @SavVariableMapping("RYSVPER")
    public Enum501 getServiceChargePeriod();

    @SavVariableMapping("ONARREAR")
    public Enum69 getRepaymentOfArrears();

    @SavVariableMapping("RENDNONE")
    public Enum69 getEndowment_NoneOfThese();

    @SavVariableMapping("RYGRDPER")
    public Enum501 getGroundRentPeriod();

    @SavVariableMapping("ONOTHER")
    public Enum69 getOtherPayment();

    @SavVariableMapping("MRGINCPT")
    public Enum69 getMortgagePaymentsIncreased();

}
