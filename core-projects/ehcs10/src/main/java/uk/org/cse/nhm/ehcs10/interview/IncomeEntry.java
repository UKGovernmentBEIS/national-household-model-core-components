package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface IncomeEntry extends SurveyEntry {
	@SavVariableMapping("BNSSPAM")
	public Integer getSSPAmount();

	@SavVariableMapping("GROHHCOR")
	public Integer getHRP_OtherSourcesGrossAnnual_Core();

	@SavVariableMapping("GRPPHCOR")
	public Integer getHRP_PriPenGrossAnnual_Core();

	@SavVariableMapping("GROHPCOR")
	public Integer getPart_OtherSourcesGrossAnnual_Core();

	@SavVariableMapping("GERNHCOR")
	public Integer getHRP_SeGrossAnnualEarnings_Core();

	@SavVariableMapping("GRNMPCOR")
	public Integer getPart_EmplGrossAnnualEarnings_Core();

	@SavVariableMapping("HHLDDV2")
	public Integer getDVForWeeklyAmount();

	@SavVariableMapping("SOURCESH")
	public Integer getHRP_NoOfIncomeSourcesAtSrcInc08();

	@SavVariableMapping("HHLDAMT2")
	public Integer getTotalIncomeOfHouseholdBeforeDeductions();

	@SavVariableMapping("GERNPCOR")
	public Integer getPart_SeGrossAnnualEarnings_Core();

	@SavVariableMapping("TELDVANP")
	public Integer getPart_GrossIncome_Annualised();

	@SavVariableMapping("GRPPPCOR")
	public Integer getPart_PriPenGrossAnnual_Core();

	@SavVariableMapping("GRNMHCOR")
	public Integer getHRP_EmplGrossAnnualEarnings_Core();

	@SavVariableMapping("SOURCESP")
	public Integer getPart_NoOfIncomeSourcesAtSrcInc08();

	@SavVariableMapping("GRENPHCO")
	public Integer getHRP_OccPenGrossAnnual_Core();

	@SavVariableMapping("TELDVANH")
	public Integer getHRPGrossIncome_Annualised();

	@SavVariableMapping("GRENPPCO")
	public Integer getPart_OccPenGrossAnnual_Core();

	@SavVariableMapping("WTCSEGP")
	public Double getWTC_CTCInclInPartnerSeGrossMainEarnings();

	@SavVariableMapping("V215_A")
	public Double getWPPaidWithOtherBenefit();

	@SavVariableMapping("BNINCPDK")
	public Double getIBPaidWithOtherBen();

	@SavVariableMapping("BNICADK2")
	public Double getICAPaidWithOtherBen();

	@SavVariableMapping("BNINCPWK")
	public Double getIBWklyAmount();

	@SavVariableMapping("BNSDAWK")
	public Double getSDAWklyAmt();

	@SavVariableMapping("BNCTCDK")
	public Double getCTCPaidWithOtherBen();

	@SavVariableMapping("BNSSPDK")
	public Double getSSPPaidWithOtherBen();

	@SavVariableMapping("BNODISAM")
	public Double getOthDisAmount();

	@SavVariableMapping("BNDCCAM")
	public Double getDLACareAmount();

	@SavVariableMapping("BNIIDBAM")
	public Double getIDBAmount();

	@SavVariableMapping("WTCSE2")
	public Double getWTC_CTCInclInHRP_PartSeNetEarnings();

	@SavVariableMapping("BNINSPAM")
	public Double getIncSupAmtLastTime();

	@SavVariableMapping("BNWRDPAM")
	public Double getWDPAmount();

	@SavVariableMapping("BNDCCDK2")
	public Double getDLACarePaidWithOtherBen();

	@SavVariableMapping("BNDISPWK")
	public Double getDisPremWklyAmt();

	@SavVariableMapping("V214_A")
	public Double getMAPaidWithOtherBen();

	@SavVariableMapping("ALLWPCP")
	public Double getPartnerMaintenancePaymentPeriod();

	@SavVariableMapping("BNESAAM")
	public Double getESAAmount();

	@SavVariableMapping("BNSDADK")
	public Double getSDAPaidWithOtherBen();

	@SavVariableMapping("BNICAWK")
	public Double getICAWklyAmt();

	@SavVariableMapping("BNESADK")
	public Double getESAPaidWithOtherBen();

	@SavVariableMapping("BNCTCWK")
	public Double getCTCWklyAmount();

	@SavVariableMapping("WTCMJGH")
	public Double getWTC_CTCIncInHRPEmplGrossMainEarnings();

	@SavVariableMapping("BNWRDPWK")
	public Double getWDPWklyAmt();

	@SavVariableMapping("BNDCCDK")
	public Double getDLACarePaidWithOtherBen_BNDCCDK();

	@SavVariableMapping("BNSTPNWK")
	public Double getOAPWklyAmountt();

	@SavVariableMapping("V224_A")
	public Double getOthDisPaidWithOtherBen();

	@SavVariableMapping("BNPENCPM")
	public Double getDoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit();

	@SavVariableMapping("BNAAWK")
	public Double getAAWklyAmt();

	@SavVariableMapping("ALLWAMTP")
	public Double getPartnerMaintenancePaymentAmount();

	@SavVariableMapping("BNSDAAM")
	public Double getSDAAmount();

	@SavVariableMapping("BNINCPAM")
	public Double getIBAmount();

	@SavVariableMapping("BNNNE")
	public Double getNoneOfTheseOtherBenefits();

	@SavVariableMapping("V218_A")
	public Double getIDBPaidWithOtherBen();

	@SavVariableMapping("BNODISDK")
	public Double getOthDisPaidWithOtherBen_BNODISDK();

	@SavVariableMapping("BNICADK")
	public Double getICAPaidWithOtherBen_BNICADK();

	@SavVariableMapping("BNJSADK")
	public Double getJSAPaidWithOtherBen();

	@SavVariableMapping("BNMATAWK")
	public Double getMAWklyAmt();

	@SavVariableMapping("BNIIDBWK")
	public Double getIDBWklyAmt();

	@SavVariableMapping("WTCMJNP")
	public Double getWTC_CTCInclInPartnerEmplNetMainEarnings();

	@SavVariableMapping("V216_A")
	public Double getWDPPaidWithOtherBen();

	@SavVariableMapping("V207_A")
	public Double getIBPaidWithOtherBen_V207_A();

	@SavVariableMapping("BNWDPNDK")
	public Double getWPPaidWithOtherBenefit_BNWDPNDK();

	@SavVariableMapping("BNJSAAM")
	public Double getJSAAmount();

	@SavVariableMapping("BNMATAAM")
	public Double getMAAmount();

	@SavVariableMapping("BNWDPNAM")
	public Double getWPAmount();

	@SavVariableMapping("BNWDPNWK")
	public Double getWPWklyAmt();

	@SavVariableMapping("BNODKNW")
	public Double getOtherBenefits_Don_TKnow();

	@SavVariableMapping("BNINSPWK")
	public Double getIncSupWklyAmt();

	@SavVariableMapping("BNDCCWK")
	public Double getDLACareWklyAmt();

	@SavVariableMapping("BNPENCDK")
	public Double getPenCPaidWithOtherBen();

	@SavVariableMapping("BNAADK2")
	public Double getAAPaidWithOtherBen();

	@SavVariableMapping("BNJSADK2")
	public Double getJSAPaidWithOtherBen_BNJSADK2();

	@SavVariableMapping("BNDISPDK")
	public Double getDisPremPaidWithOtherBen();

	@SavVariableMapping("WTCSE1")
	public Double getWTC_CTCInclInHRP_PartSeGrossEarnings();

	@SavVariableMapping("V206_A")
	public Double getOAPPaidWithOtherBen();

	@SavVariableMapping("BNODISWK")
	public Double getOthDisWklyAmt();

	@SavVariableMapping("BNDMCDK2")
	public Double getDLAMobPaidWithOtherBen();

	@SavVariableMapping("BNSSPDK2")
	public Double getSSPPaidWithOtherBen_BNSSPDK2();

	@SavVariableMapping("BNPRIUM")
	public Double getDisabilityPremWithIncSup_HB();

	@SavVariableMapping("BNWTCDK2")
	public Double getWTCPaidWithOtherBen();

	@SavVariableMapping("BNLIDBWK")
	public Double getBNLIDBWK();

	@SavVariableMapping("BNDMCAM")
	public Double getDLAMobAmount();

	@SavVariableMapping("WTCSENP")
	public Double getWTC_CTCInclInPartnrSeNetMainEarnings();

	@SavVariableMapping("BNIIDBDK")
	public Double getIDBPaidWithOtherBen_BNIIDBDK();

	@SavVariableMapping("BNDISPAM")
	public Double getDisPremAmount();

	@SavVariableMapping("BNSDADK2")
	public Double getSDAPaidWithOtherBen_BNSDADK2();

	@SavVariableMapping("V203_A")
	public Double getPenCPaidWithOtherBen_V203_A();

	@SavVariableMapping("BNSSPWK")
	public Double getSSPWklyAmt();

	@SavVariableMapping("BNMATADK")
	public Double getMAPaidWithOtherBen_BNMATADK();

	@SavVariableMapping("MISIAMT")
	public Double getIncSup_MortInterestAmt();

	@SavVariableMapping("BNSTPNAM")
	public Double getOldAgePensionAmount();

	@SavVariableMapping("BNWRDPDK")
	public Double getWDPPaidWithOtherBen_BNWRDPDK();

	@SavVariableMapping("BNDMCDK")
	public Double getDLAMobPaidWithOtherBen_BNDMCDK();

	@SavVariableMapping("BNPENCAM")
	public Double getPenCAmount();

	@SavVariableMapping("BNDMCWK")
	public Double getDLAMobWklyAmt();

	@SavVariableMapping("SEPFILTP")
	public Double getPartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership();

	@SavVariableMapping("BNESAWK")
	public Double getESAWklyAmount();

	@SavVariableMapping("BNPENCWK")
	public Double getPenCWklyAmt();

	@SavVariableMapping("WTCSENH")
	public Double getWTC_CTCInclInHRPSeNetMainEarnings();

	@SavVariableMapping("BNINSPPM")
	public Double getDoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport();

	@SavVariableMapping("BNDISPPD")
	public Double getDisPremTimePeriod();

	@SavVariableMapping("WTCMJ2")
	public Double getWTC_CTCInclInHRP_PartEmplNetEarnings();

	@SavVariableMapping("BNINSPDK")
	public Double getIncSupPaidWithOtherBen();

	@SavVariableMapping("WTCMJGP")
	public Double getWTC_CTCInclInPartnerEmplGrossMainEarnings();

	@SavVariableMapping("DPCHK")
	public Double getAmtOfHB_IncSupInclDisabilityPrem();

	@SavVariableMapping("V201_A")
	public Double getIncSupPaidWithOtherBen_V201_A();

	@SavVariableMapping("BNCTCDK2")
	public Double getCTCPaidWithOtherBen_BNCTCDK2();

	@SavVariableMapping("BNAADK")
	public Double getAAPaidWithOtherBen_BNAADK();

	@SavVariableMapping("BNCTCAM")
	public Double getCTCAmount();

	@SavVariableMapping("BNWTCDK")
	public Double getWTCPaidWithOtherBen_BNWTCDK();

	@SavVariableMapping("GRERNGS2")
	public Double getDidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple();

	@SavVariableMapping("BNESADK2")
	public Double getESAPaidWithOtherBen_BNESADK2();

	@SavVariableMapping("GRERNGS4")
	public Double getDidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple();

	@SavVariableMapping("WTCSEGH")
	public Double getWTC_CTCInclInHRPSeGrossMainEarnings();

	@SavVariableMapping("BNAAAM")
	public Double getAAAmount();

	@SavVariableMapping("DVMISI")
	public Double getIncSup_MortInterestMthlyAmt();

	@SavVariableMapping("BNICAAM")
	public Double getICAAmount();

	@SavVariableMapping("BNWTCWK")
	public Double getWTCWklyAmount();

	@SavVariableMapping("BNJSAWK")
	public Double getJSAWklyAmount();

	@SavVariableMapping("BNSTPNDK")
	public Double getOAPPaidWithOtherBen_BNSTPNDK();

	@SavVariableMapping("BNWTCAM")
	public Double getWTCAmount();

	@SavVariableMapping("WTCMJNH")
	public Double getWTC_CTCInclInHRPEmplNetMainEarnings();

	@SavVariableMapping("BNONONE")
	public Double getNoneOfTheseOtherBenefits_BNONONE();

	@SavVariableMapping("ALLWAMTH")
	public Double getHRPMaintenancePaymentAmount();

	@SavVariableMapping("BNORF")
	public Double getOtherBenefits_Refused();

	@SavVariableMapping("BNJSATYP")
	public Double getIsYourJobSeekersAllowance();

	@SavVariableMapping("WTCMJ1")
	public Double getWTC_CTCInclInHRP_PartEmplGrossEarnings();

	@SavVariableMapping("BNDCCPD")
	public Enum501 getDLACareTimePeriod();

	@SavVariableMapping("NTERNPPH")
	public Enum502 getHRP_PriPenNetAnnual();

	@SavVariableMapping("BNPENCPD")
	public Enum501 getPenCTimePeriod();

	@SavVariableMapping("PENCONH")
	public Enum69 getHRP_SuperannuationContribs();

	@SavVariableMapping("BNDMCPD")
	public Enum501 getDLAMobTimePeriod();

	@SavVariableMapping("BNWTC")
	public Enum69 getWorkingTaxCredit();

	@SavVariableMapping("NETERNH")
	public Enum502 getHRP_SeNetAnnualEarnings();

	@SavVariableMapping("RECPYHP")
	public Enum69 getHRP_RegularPaytOfSeEarnings();

	@SavVariableMapping("HHLDPER2")
	public Enum509 getIsThatAWeekly_MonthlyOrAnnualAmount_();

	@SavVariableMapping("GROSSP")
	public Enum69 getWhetherHRP_SPartnerPotentiallyAskedEarningsBlocks();

	@SavVariableMapping("ESATYPE")
	public Enum511 getESAAllowanceType();

	@SavVariableMapping("NTERNMP")
	public Enum502 getPart_EmplNetAnnualEarnings();

	@SavVariableMapping("DLACCHK")
	public Enum69 getDLACareComponentAtHigherRate();

	@SavVariableMapping("BNPENCRD")
	public Enum69 getPensionCredit();

	@SavVariableMapping("NETLOSSH")
	public Enum515 getHRPLossOfNetEarningsFromSelfEmployment();

	@SavVariableMapping("BNJSA")
	public Enum69 getJobseekersAllowance();

	@SavVariableMapping("GRERNPPH")
	public Enum502 getHRP_PriPenGrossAnnual();

	@SavVariableMapping("BNAAPD")
	public Enum501 getAATimePeriod();

	@SavVariableMapping("AMTSVNG1")
	public Enum69 getSavings_MoneyInvested();

	@SavVariableMapping("MORGP1")
	public Enum69 getMPPIReceipts();

	@SavVariableMapping("BNICAPD")
	public Enum501 getICATimePeriod();

	@SavVariableMapping("BNRTWC")
	public Enum69 getReturnToWorkCredit();

	@SavVariableMapping("ISCHK")
	public Enum69 getIncludeIncSup();

	@SavVariableMapping("BNSSP")
	public Enum69 getStatutorySickPay();

	@SavVariableMapping("BNODISPD")
	public Enum501 getOthDisTimePeriod();

	@SavVariableMapping("BNIWC")
	public Enum69 getInWorkCredit();

	@SavVariableMapping("ESACHK")
	public Enum69 getCurrentlyWithinThe13WeekESAAssessmentPhase();

	@SavVariableMapping("BNINCSUP")
	public Enum69 getIncomeSupport();

	@SavVariableMapping("RECPYSP")
	public Enum69 getPart_RegularPaytOfSeEarnings();

	@SavVariableMapping("NETLOSSP")
	public Enum515 getPartnerLossOfNetEarningsFromSelfEmployment();

	@SavVariableMapping("BNESA")
	public Enum69 getEmploymentSupportAllowance();

	@SavVariableMapping("GRERNPPT")
	public Enum502 getPart_OccPenGrossAnnual();

	@SavVariableMapping("GRERNOIH")
	public Enum502 getHRP_OtherSourcesGrossAnnual();

	@SavVariableMapping("GRERNOIP")
	public Enum502 getPart_OtherSourcesGrossAnnual();

	@SavVariableMapping("NTERNPH")
	public Enum502 getHRP_OccPenNetAnnual();

	@SavVariableMapping("BNDLACC")
	public Enum69 getDLA_CareComponent();

	@SavVariableMapping("GRERNGSH")
	public Enum502 getHRP_GovtSchemes_LastTime();

	@SavVariableMapping("BNSTPNCK")
	public Enum69 getPenCAmtIncludesStatePen();

	@SavVariableMapping("BNWTCPD")
	public Enum501 getWTCTimePeriod();

	@SavVariableMapping("BNJSAPD")
	public Enum501 getJSATimePeriod();

	@SavVariableMapping("BNWIDPEN")
	public Enum69 getWidows_WidowersPen();

	@SavVariableMapping("MISIPD")
	public Enum501 getIncSup_MortInterestPeriod();

	@SavVariableMapping("BNWDPNPD")
	public Enum501 getWPTimePeriod();

	@SavVariableMapping("BNDLAMC")
	public Enum69 getDLA_MobilityComponent();

	@SavVariableMapping("BNIIDB")
	public Enum69 getIndustrialInjuriesDisablementBen();

	@SavVariableMapping("BNWRDPPD")
	public Enum501 getWDPTimePeriod();

	@SavVariableMapping("BNCHILDB")
	public Enum69 getChildBenefit();

	@SavVariableMapping("BNPENCCK")
	public Enum69 getStatePenAmtIncludesPenC();

	@SavVariableMapping("ALLWPCH")
	public Enum501 getHRPMaintenancePaymentPeriod();

	@SavVariableMapping("INCGSP")
	public Enum550 getPart_AskedEarningsFromGov_Schemes();

	@SavVariableMapping("GRERNMP")
	public Enum502 getPart_EmplGrossAnnualEarnings();

	@SavVariableMapping("BNSDA")
	public Enum69 getSevereDisablementAllow();

	@SavVariableMapping("GRERNPH")
	public Enum502 getHRP_OccPenGrossAnnual();

	@SavVariableMapping("MORGCOV")
	public Enum554 getMortgageContributionUnderMortgagePaymentProtection();

	@SavVariableMapping("BNNONE")
	public Enum69 getNoneOfTheseStateBenefits();

	@SavVariableMapping("GROSSH")
	public Enum69 getWhetherHRPPotentiallyAskedEarningsBlocks();

	@SavVariableMapping("ISMICOV")
	public Enum557 getIncSup_Part_FullMortInterest();

	@SavVariableMapping("GRSERNP")
	public Enum502 getPart_SeGrossAnnualEarnings();

	@SavVariableMapping("GRERNMH")
	public Enum502 getHRP_EmplGrossAnnualEarnings();

	@SavVariableMapping("BNINCAP")
	public Enum69 getIncapacityBenefit();

	@SavVariableMapping("GRSLOSSP")
	public Enum515 getPartnerLossOfGrossEarningsFromSelfEmployment();

	@SavVariableMapping("GRERNPPP")
	public Enum502 getPart_PriPenGrossAnnual();

	@SavVariableMapping("BNNRF")
	public Enum69 getStateBenefits_Refused();

	@SavVariableMapping("BNSTATEP")
	public Enum69 getNIRetirement_OldAgePension();

	@SavVariableMapping("BNICA")
	public Enum69 getCarersAllow_InvalidCareAllow_();

	@SavVariableMapping("BNCTC")
	public Enum69 getChildTaxCredit();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("BNSDAPD")
	public Enum501 getSDATimePeriod();

	@SavVariableMapping("BNIIDBPD")
	public Enum501 getIDBTimePeriod();

	@SavVariableMapping("BNESAPD")
	public Enum501 getESATimePeriod();

	@SavVariableMapping("GRSLOSSH")
	public Enum515 getHRPLossOfGrossEarningsFromSelfEmployment();

	@SavVariableMapping("BNDKNW")
	public Enum69 getStateBenefits_Don_TKnow();

	@SavVariableMapping("GRERNGSP")
	public Enum502 getPart_GovtSchemes_LastTime();

	@SavVariableMapping("BNMATAPD")
	public Enum501 getMATimePeriod();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("NTERNPPT")
	public Enum502 getPart_OccPenNetAnnual();

	@SavVariableMapping("NTERNPPP")
	public Enum502 getPart_PriPenNetAnnual();

	@SavVariableMapping("ESACHK2")
	public Enum578 getESAGroup();

	@SavVariableMapping("INCGSH")
	public Enum69 getHRP_AskedEarningsFromGov_Schemes();

	@SavVariableMapping("NTERNMH")
	public Enum502 getHRP_EmplNetAnnualEarnings();

	@SavVariableMapping("BNWARDP")
	public Enum69 getWarDisablementPen();

	@SavVariableMapping("BNODIS")
	public Enum69 getOtherDisabilityBenefit();

	@SavVariableMapping("BNCTCPD")
	public Enum501 getCTCTimePeriod();

	@SavVariableMapping("MORGPCOV")
	public Enum557 getMPPIReceipts_All_PartOfMorg();

	@SavVariableMapping("V191_A")
	public Enum585 getAmountOfSavings_MoneyInvested();

	@SavVariableMapping("PENCONP")
	public Enum550 getPart_SuperannuationContribs();

	@SavVariableMapping("BNSTPNPD")
	public Enum501 getOAPTimePeriod();

	@SavVariableMapping("NETERNP")
	public Enum502 getPart_SeNetAnnualEarnings();

	@SavVariableMapping("BNINCPPD")
	public Enum501 getIBTimePeriod();

	@SavVariableMapping("HHLDBAN2")
	public Enum590 getWhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions();

	@SavVariableMapping("BNAA")
	public Enum69 getAttendanceAllow();

	@SavVariableMapping("BNMATA")
	public Enum69 getMatAllowance();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("BNINSPPD")
	public Enum501 getIncSupTimePeriod();

	@SavVariableMapping("GRSERNH")
	public Enum502 getHRP_SeGrossAnnualEarnings();

	@SavVariableMapping("BNSSPPD")
	public Enum501 getSSPTimePeriod();

	@SavVariableMapping("SEPFILTH")
	public Enum69 getHRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership();

	@SavVariableMapping("RECISMI")
	public Enum69 getIncSup_MortInterestHelp();

}

