package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface RenterEntry extends SurveyEntry {
	@SavVariableMapping("REASTNO")
	public String getReasonForTakingOver_AquiringTenancy();

	@SavVariableMapping("TENSTRTY")
	public Integer getStartOfPreviousTenancy();

	@SavVariableMapping("LDRNTO12")
	public Integer getTenants_PrtHB_FllAmtRentChrg();

	@SavVariableMapping("TOTALB")
	public Integer getTotalWeeklyRent_PartHB_();

	@SavVariableMapping("LDRNHB1A")
	public Integer getTenants_FllHB_AmtHB();

	@SavVariableMapping("RENTHOLW")
	public Integer getForHowManyWeeksDoYouHaveARentHoliday_();

	@SavVariableMapping("TENSTRT")
	public Integer getYearTenancyStarted();

	@SavVariableMapping("LDRNHB1D")
	public Integer getTenantsXsHB_AmtHBRcv();

	@SavVariableMapping("LDRNTO3")
	public Integer getTenants_UnsureHB_AmtRentChrg();

	@SavVariableMapping("LDRNTO2")
	public Integer getTenants_NoHB_AmtRentPd();

	@SavVariableMapping("V49_A")
	public Integer getTenantsXsHB_FllRentChk();

	@SavVariableMapping("NETRENT")
	public Integer getRentExclServices_HB_LHA();

	@SavVariableMapping("TOTALD")
	public Integer getWeeklyExcessBenefit();

	@SavVariableMapping("FEEAMT")
	public Integer getPrevTnncy_AmountOfFee();

	@SavVariableMapping("LDRNAM1C")
	public Integer getTenants_DKHB_AmtRentChrg();

	@SavVariableMapping("LDRNHB1B")
	public Integer getTenants_PrtHB_AmtHBRcv();

	@SavVariableMapping("YRHA")
	public Integer getYearBecameHATenant();

	@SavVariableMapping("TENENDY")
	public Integer getWhenTenancyEnded();

	@SavVariableMapping("LDRSEL3")
	public Integer getTenants_UnsureHB_AmtRentPd();

	@SavVariableMapping("NUMAGR")
	public Integer getNo_OfAgreementsWithinHhld();

	@SavVariableMapping("TOTAMT")
	public Integer getTotalAmountOfRent();

	@SavVariableMapping("LDRSEL1C")
	public Integer getTenants_DKHB_AmtRentPd();

	@SavVariableMapping("LDRSEL1D")
	public Integer getTenantsXsHB_AmtRentPd();

	@SavVariableMapping("LDRSEL1B")
	public Integer getTenants_PrtHB_AmtRentPd();

	@SavVariableMapping("DEPHLDR")
	public Double getDepositWasHeldBy();

	@SavVariableMapping("CDEPALT")
	public Double getGuarantorRequired();

	@SavVariableMapping("CFEEADMN")
	public Double getAdminFee_Non_Ret_();

	@SavVariableMapping("CFEEHLDN")
	public Double getHoldingFee_Non_Ret_();

	@SavVariableMapping("CFEEFNDR")
	public Double getFindersFee_Non_Ret_();

	@SavVariableMapping("CFEEHLDR")
	public Double getHoldingFee_Returnable_();

	@SavVariableMapping("CFEE")
	public Double getThisTnncy_Landlord_AgencyFee();

	@SavVariableMapping("V127_A")
	public Double getIsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes();

	@SavVariableMapping("CFEEAMT")
	public Double getThisTnncy_AmountOfFee();

	@SavVariableMapping("DEPHLDR2")
	public Double getWasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes();

	@SavVariableMapping("CDEPHLDR")
	public Double getDepositBeingHeldBy();

	@SavVariableMapping("CFEEOTHR")
	public Double getOtherFee();

	@SavVariableMapping("CDEPPROP")
	public Double getDepositAsProportionOfRent();

	@SavVariableMapping("TENYTYPE")
	public Enum813 getTenancyType();

	@SavVariableMapping("LLPERMIT")
	public Enum69 getWithLandlord_SAgreement();

	@SavVariableMapping("WHOLSHAR")
	public Enum815 getRent_HBAmountsGivenForWholeAccommodationOrJustYourShare();

	@SavVariableMapping("SERIWTER")
	public Enum69 getWaterAndSewerage();

	@SavVariableMapping("LDPERD1C")
	public Enum817 getTenants_DKHB_RentChrgPer();

	@SavVariableMapping("LDPDTO1D")
	public Enum817 getTenantsXsHB_RentChrgPer();

	@SavVariableMapping("SERINONE")
	public Enum69 getNoneOfThese();

	@SavVariableMapping("SERIHEAT")
	public Enum69 getHeating();

	@SavVariableMapping("LDRNTO1B")
	public Enum69 getTenants_PrtHB_FllRentChk();

	@SavVariableMapping("WHYNBLLS")
	public Enum69 getUnpaidBills();

	@SavVariableMapping("SRWAITL")
	public Enum823 getLengthOfWait();

	@SavVariableMapping("RETRND")
	public Enum824 getDepositReturned();

	@SavVariableMapping("WHYNONE")
	public Enum69 getNoReasonGiven();

	@SavVariableMapping("HBENA")
	public Enum69 getAnyRentCoveredByHB_LHA();

	@SavVariableMapping("LDHSBAM3")
	public Enum827 getAmountOfRentCovered();

	@SavVariableMapping("LIVTEN")
	public Enum69 getAcquiredTenancyWhenLivingWithSomeoneWhoWasATenant();

	@SavVariableMapping("LDCMPSIT")
	public Enum630 getComplexRentingSituation();

	@SavVariableMapping("SMAG1")
	public Enum830 getNo_OfLodgerAgreements();

	@SavVariableMapping("LNGTHTN")
	public Enum831 getTimeAtPropertyBeforeAcquiringTenancy();

	@SavVariableMapping("RENTOWE")
	public Enum69 getRentOwing();

	@SavVariableMapping("ARRPR2")
	public Enum69 getFallenBehindWithRentPaymentsOverTheLast12Months();

	@SavVariableMapping("TOTPER")
	public Enum501 getRentPaymentPeriod();

	@SavVariableMapping("DEPPROP")
	public Enum835 getDepositAsProportionOfRent_DEPPROP();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("LOWSHORT")
	public Enum69 getLowSeasonLet();

	@SavVariableMapping("WHYNOTHR")
	public Enum69 getSomeOtherReason();

	@SavVariableMapping("ARRUNEXP")
	public Enum69 getUnexpectedCouncilTaxOrUtilityBills();

	@SavVariableMapping("WHYNCLNG")
	public Enum69 getPropertyRequiredCleaning();

	@SavVariableMapping("WHYNRENT")
	public Enum69 getUnpaidRent();

	@SavVariableMapping("DEPALT")
	public Enum69 getPrevTnncy_GuarantorRequired();

	@SavVariableMapping("ARRUNEMP")
	public Enum69 getUnemployment();

	@SavVariableMapping("DEPOSIT")
	public Enum69 getPrevTnncy_PaidDeposit();

	@SavVariableMapping("ARROTHER")
	public Enum69 getOtherDebtsOrResponsibilities();

	@SavVariableMapping("INTHB")
	public Enum251 getHousingBenefitAndRent();

	@SavVariableMapping("EVERCLHA")
	public Enum660 getEverCouncil_HAHouse_Flat();

	@SavVariableMapping("FEEFOTHR")
	public Enum69 getOtherFee_FEEFOTHR();

	@SavVariableMapping("LDJOINT")
	public Enum849 getTenants_DivisionOfRentPayment();

	@SavVariableMapping("DVDEPRET")
	public Enum824 getComputed_DepositReturned();

	@SavVariableMapping("DVPRNT")
	public Enum69 getTreatedAsPrivateRentedHousehold();

	@SavVariableMapping("SERITVLI")
	public Enum69 getTVLicenceFee();

	@SavVariableMapping("FEELNDEP")
	public Enum853 getHowFelt();

	@SavVariableMapping("REASTEN")
	public Enum854 getReasonBehindTenancyAcquisition();

	@SavVariableMapping("RENTHBAN")
	public Enum855 getAnsweringHB_LHAForWholeHousehold();

	@SavVariableMapping("WHYNDMGE")
	public Enum69 getDamageToProperty();

	@SavVariableMapping("FEEFFNDR")
	public Enum69 getFindersFee_Non_Ret__FEEFFNDR();

	@SavVariableMapping("PHA229")
	public Enum314 getHowEasyIsItToPayYourRentAfterBenefits();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("LDPDHB1D")
	public Enum817 getTenantsXsHB_HBRcvPer();

	@SavVariableMapping("CAGREE")
	public Enum69 getCouncil_HAAgreedToYourAcquiringTheTenancy();

	@SavVariableMapping("USEDEP")
	public Enum862 getUsedDepositTowardsRent();

	@SavVariableMapping("LDRNTO1D")
	public Enum69 getTenantsXsHB_XsBenefitChk();

	@SavVariableMapping("LDPDTO1B")
	public Enum817 getTenants_PrtHB_RentChrgPer();

	@SavVariableMapping("SRCHECK2")
	public Enum69 getAcceptedAsHomeless();

	@SavVariableMapping("LDPDSE1D")
	public Enum817 getTenantsXsHB_RentPdPer();

	@SavVariableMapping("ARRRENT")
	public Enum69 getIncreaseInRent();

	@SavVariableMapping("CDEPOSIT")
	public Enum69 getThisTnncy_PaidDeposit();

	@SavVariableMapping("WHOPRTN")
	public Enum869 getRelationshipOfPreviousTenant_PersonYouWereLivingWith();

	@SavVariableMapping("FEEFADMN")
	public Enum69 getAdminFee_Non_Ret__FEEFADMN();

	@SavVariableMapping("LDPDSE1C")
	public Enum817 getTenants_DKHB_RentPdPer();

	@SavVariableMapping("SERIHWTR")
	public Enum69 getHotWater();

	@SavVariableMapping("RESTEN")
	public Enum69 getResidentWhenTenancyAcquired();

	@SavVariableMapping("FEEFHLDN")
	public Enum69 getHoldingFee_Non_Ret__FEEFHLDN();

	@SavVariableMapping("ARRILL")
	public Enum69 getIllness();

	@SavVariableMapping("DEPRTN")
	public Enum876 getDepositKeptAsPropnOfRent();

	@SavVariableMapping("LDPDSEL3")
	public Enum817 getTenants_UnsureHB_RentPdPer();

	@SavVariableMapping("NETPER")
	public Enum817 getNetRentPeriod();

	@SavVariableMapping("TRANSHA")
	public Enum69 getTenancyTransferredFromLA();

	@SavVariableMapping("ARRLHOT")
	public Enum69 getWorkingFewerHours_LessOvertime();

	@SavVariableMapping("ARRPR1")
	public Enum69 getUpToDateWithRentPayments();

	@SavVariableMapping("LDPDHB1A")
	public Enum817 getTenants_FllHB_HBPer();

	@SavVariableMapping("RENTHOLA")
	public Enum69 getRentHoliday();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("SERICTAX")
	public Enum69 getCouncilTax();

	@SavVariableMapping("SERIMEAL")
	public Enum69 getRegularMeals();

	@SavVariableMapping("ARRBEN")
	public Enum69 getProblemsWithHousingBenefit_LocalHousingAllowance();

	@SavVariableMapping("LDPDTO2")
	public Enum817 getTenants_NoHB_RentPdPer();

	@SavVariableMapping("SERIFUEL")
	public Enum69 getFuelForCooking();

	@SavVariableMapping("OTHTYPEA")
	public Enum890 getLettingType();

	@SavVariableMapping("LDPDTO3")
	public Enum817 getTenants_UnsureHB_RentChrgPer();

	@SavVariableMapping("FEE")
	public Enum69 getPrevTnncy_Landlord_AgencyFee();

	@SavVariableMapping("SERILGHT")
	public Enum69 getLighting();

	@SavVariableMapping("FEEFHLDR")
	public Enum69 getHoldingFee_Ret_();

	@SavVariableMapping("SMAG")
	public Enum830 getOneOrMoreAgreementsWithLandlord();

	@SavVariableMapping("ARRDOM")
	public Enum69 getDomesticProblems();

	@SavVariableMapping("LDPDHB1B")
	public Enum817 getTenants_PrtHB_HBRcvPer();

	@SavVariableMapping("LDACMOWN")
	public Enum69 getRentPaidToOwner();

	@SavVariableMapping("ARRNONE")
	public Enum69 getNoneOfThese_ARRNONE();

	@SavVariableMapping("HBSTART")
	public Enum69 getReceivingHB_LHAllowanceWhenCurrentTenancyStarted();

	@SavVariableMapping("LDPDSE1B")
	public Enum817 getTenants_PrtHB_AmtRentPdPer();

}

