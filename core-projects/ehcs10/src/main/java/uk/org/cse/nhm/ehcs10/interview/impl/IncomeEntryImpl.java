package uk.org.cse.nhm.ehcs10.interview.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.IncomeEntry;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class IncomeEntryImpl extends SurveyEntryImpl implements IncomeEntry {
	private Integer SSPAmount;
	private Integer HRP_OtherSourcesGrossAnnual_Core;
	private Integer HRP_PriPenGrossAnnual_Core;
	private Integer Part_OtherSourcesGrossAnnual_Core;
	private Integer HRP_SeGrossAnnualEarnings_Core;
	private Integer Part_EmplGrossAnnualEarnings_Core;
	private Integer DVForWeeklyAmount;
	private Integer HRP_NoOfIncomeSourcesAtSrcInc08;
	private Integer TotalIncomeOfHouseholdBeforeDeductions;
	private Integer Part_SeGrossAnnualEarnings_Core;
	private Integer Part_GrossIncome_Annualised;
	private Integer Part_PriPenGrossAnnual_Core;
	private Integer HRP_EmplGrossAnnualEarnings_Core;
	private Integer Part_NoOfIncomeSourcesAtSrcInc08;
	private Integer HRP_OccPenGrossAnnual_Core;
	private Integer HRPGrossIncome_Annualised;
	private Integer Part_OccPenGrossAnnual_Core;
	private Double WTC_CTCInclInPartnerSeGrossMainEarnings;
	private Double WPPaidWithOtherBenefit;
	private Double IBPaidWithOtherBen;
	private Double ICAPaidWithOtherBen;
	private Double IBWklyAmount;
	private Double SDAWklyAmt;
	private Double CTCPaidWithOtherBen;
	private Double SSPPaidWithOtherBen;
	private Double OthDisAmount;
	private Double DLACareAmount;
	private Double IDBAmount;
	private Double WTC_CTCInclInHRP_PartSeNetEarnings;
	private Double IncSupAmtLastTime;
	private Double WDPAmount;
	private Double DLACarePaidWithOtherBen;
	private Double DisPremWklyAmt;
	private Double MAPaidWithOtherBen;
	private Double PartnerMaintenancePaymentPeriod;
	private Double ESAAmount;
	private Double SDAPaidWithOtherBen;
	private Double ICAWklyAmt;
	private Double ESAPaidWithOtherBen;
	private Double CTCWklyAmount;
	private Double WTC_CTCIncInHRPEmplGrossMainEarnings;
	private Double WDPWklyAmt;
	private Double DLACarePaidWithOtherBen_BNDCCDK;
	private Double OAPWklyAmountt;
	private Double OthDisPaidWithOtherBen;
	private Double DoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit;
	private Double AAWklyAmt;
	private Double PartnerMaintenancePaymentAmount;
	private Double SDAAmount;
	private Double IBAmount;
	private Double NoneOfTheseOtherBenefits;
	private Double IDBPaidWithOtherBen;
	private Double OthDisPaidWithOtherBen_BNODISDK;
	private Double ICAPaidWithOtherBen_BNICADK;
	private Double JSAPaidWithOtherBen;
	private Double MAWklyAmt;
	private Double IDBWklyAmt;
	private Double WTC_CTCInclInPartnerEmplNetMainEarnings;
	private Double WDPPaidWithOtherBen;
	private Double IBPaidWithOtherBen_V207_A;
	private Double WPPaidWithOtherBenefit_BNWDPNDK;
	private Double JSAAmount;
	private Double MAAmount;
	private Double WPAmount;
	private Double WPWklyAmt;
	private Double OtherBenefits_Don_TKnow;
	private Double IncSupWklyAmt;
	private Double DLACareWklyAmt;
	private Double PenCPaidWithOtherBen;
	private Double AAPaidWithOtherBen;
	private Double JSAPaidWithOtherBen_BNJSADK2;
	private Double DisPremPaidWithOtherBen;
	private Double WTC_CTCInclInHRP_PartSeGrossEarnings;
	private Double OAPPaidWithOtherBen;
	private Double OthDisWklyAmt;
	private Double DLAMobPaidWithOtherBen;
	private Double SSPPaidWithOtherBen_BNSSPDK2;
	private Double DisabilityPremWithIncSup_HB;
	private Double WTCPaidWithOtherBen;
	private Double BNLIDBWK;
	private Double DLAMobAmount;
	private Double WTC_CTCInclInPartnrSeNetMainEarnings;
	private Double IDBPaidWithOtherBen_BNIIDBDK;
	private Double DisPremAmount;
	private Double SDAPaidWithOtherBen_BNSDADK2;
	private Double PenCPaidWithOtherBen_V203_A;
	private Double SSPWklyAmt;
	private Double MAPaidWithOtherBen_BNMATADK;
	private Double IncSup_MortInterestAmt;
	private Double OldAgePensionAmount;
	private Double WDPPaidWithOtherBen_BNWRDPDK;
	private Double DLAMobPaidWithOtherBen_BNDMCDK;
	private Double PenCAmount;
	private Double DLAMobWklyAmt;
	private Double PartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership;
	private Double ESAWklyAmount;
	private Double PenCWklyAmt;
	private Double WTC_CTCInclInHRPSeNetMainEarnings;
	private Double DoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport;
	private Double DisPremTimePeriod;
	private Double WTC_CTCInclInHRP_PartEmplNetEarnings;
	private Double IncSupPaidWithOtherBen;
	private Double WTC_CTCInclInPartnerEmplGrossMainEarnings;
	private Double AmtOfHB_IncSupInclDisabilityPrem;
	private Double IncSupPaidWithOtherBen_V201_A;
	private Double CTCPaidWithOtherBen_BNCTCDK2;
	private Double AAPaidWithOtherBen_BNAADK;
	private Double CTCAmount;
	private Double WTCPaidWithOtherBen_BNWTCDK;
	private Double DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple;
	private Double ESAPaidWithOtherBen_BNESADK2;
	private Double DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple;
	private Double WTC_CTCInclInHRPSeGrossMainEarnings;
	private Double AAAmount;
	private Double IncSup_MortInterestMthlyAmt;
	private Double ICAAmount;
	private Double WTCWklyAmount;
	private Double JSAWklyAmount;
	private Double OAPPaidWithOtherBen_BNSTPNDK;
	private Double WTCAmount;
	private Double WTC_CTCInclInHRPEmplNetMainEarnings;
	private Double NoneOfTheseOtherBenefits_BNONONE;
	private Double HRPMaintenancePaymentAmount;
	private Double OtherBenefits_Refused;
	private Double IsYourJobSeekersAllowance;
	private Double WTC_CTCInclInHRP_PartEmplGrossEarnings;
	private Enum501 DLACareTimePeriod;
	private Enum502 HRP_PriPenNetAnnual;
	private Enum501 PenCTimePeriod;
	private Enum69 HRP_SuperannuationContribs;
	private Enum501 DLAMobTimePeriod;
	private Enum69 WorkingTaxCredit;
	private Enum502 HRP_SeNetAnnualEarnings;
	private Enum69 HRP_RegularPaytOfSeEarnings;
	private Enum509 IsThatAWeekly_MonthlyOrAnnualAmount_;
	private Enum69 WhetherHRP_SPartnerPotentiallyAskedEarningsBlocks;
	private Enum511 ESAAllowanceType;
	private Enum502 Part_EmplNetAnnualEarnings;
	private Enum69 DLACareComponentAtHigherRate;
	private Enum69 PensionCredit;
	private Enum515 HRPLossOfNetEarningsFromSelfEmployment;
	private Enum69 JobseekersAllowance;
	private Enum502 HRP_PriPenGrossAnnual;
	private Enum501 AATimePeriod;
	private Enum69 Savings_MoneyInvested;
	private Enum69 MPPIReceipts;
	private Enum501 ICATimePeriod;
	private Enum69 ReturnToWorkCredit;
	private Enum69 IncludeIncSup;
	private Enum69 StatutorySickPay;
	private Enum501 OthDisTimePeriod;
	private Enum69 InWorkCredit;
	private Enum69 CurrentlyWithinThe13WeekESAAssessmentPhase;
	private Enum69 IncomeSupport;
	private Enum69 Part_RegularPaytOfSeEarnings;
	private Enum515 PartnerLossOfNetEarningsFromSelfEmployment;
	private Enum69 EmploymentSupportAllowance;
	private Enum502 Part_OccPenGrossAnnual;
	private Enum502 HRP_OtherSourcesGrossAnnual;
	private Enum502 Part_OtherSourcesGrossAnnual;
	private Enum502 HRP_OccPenNetAnnual;
	private Enum69 DLA_CareComponent;
	private Enum502 HRP_GovtSchemes_LastTime;
	private Enum69 PenCAmtIncludesStatePen;
	private Enum501 WTCTimePeriod;
	private Enum501 JSATimePeriod;
	private Enum69 Widows_WidowersPen;
	private Enum501 IncSup_MortInterestPeriod;
	private Enum501 WPTimePeriod;
	private Enum69 DLA_MobilityComponent;
	private Enum69 IndustrialInjuriesDisablementBen;
	private Enum501 WDPTimePeriod;
	private Enum69 ChildBenefit;
	private Enum69 StatePenAmtIncludesPenC;
	private Enum501 HRPMaintenancePaymentPeriod;
	private Enum550 Part_AskedEarningsFromGov_Schemes;
	private Enum502 Part_EmplGrossAnnualEarnings;
	private Enum69 SevereDisablementAllow;
	private Enum502 HRP_OccPenGrossAnnual;
	private Enum554 MortgageContributionUnderMortgagePaymentProtection;
	private Enum69 NoneOfTheseStateBenefits;
	private Enum69 WhetherHRPPotentiallyAskedEarningsBlocks;
	private Enum557 IncSup_Part_FullMortInterest;
	private Enum502 Part_SeGrossAnnualEarnings;
	private Enum502 HRP_EmplGrossAnnualEarnings;
	private Enum69 IncapacityBenefit;
	private Enum515 PartnerLossOfGrossEarningsFromSelfEmployment;
	private Enum502 Part_PriPenGrossAnnual;
	private Enum69 StateBenefits_Refused;
	private Enum69 NIRetirement_OldAgePension;
	private Enum69 CarersAllow_InvalidCareAllow_;
	private Enum69 ChildTaxCredit;
	private Enum230 FieldworkQuarter;
	private Enum501 SDATimePeriod;
	private Enum501 IDBTimePeriod;
	private Enum501 ESATimePeriod;
	private Enum515 HRPLossOfGrossEarningsFromSelfEmployment;
	private Enum69 StateBenefits_Don_TKnow;
	private Enum502 Part_GovtSchemes_LastTime;
	private Enum501 MATimePeriod;
	private Enum30 Region_EHSOrder;
	private Enum502 Part_OccPenNetAnnual;
	private Enum502 Part_PriPenNetAnnual;
	private Enum578 ESAGroup;
	private Enum69 HRP_AskedEarningsFromGov_Schemes;
	private Enum502 HRP_EmplNetAnnualEarnings;
	private Enum69 WarDisablementPen;
	private Enum69 OtherDisabilityBenefit;
	private Enum501 CTCTimePeriod;
	private Enum557 MPPIReceipts_All_PartOfMorg;
	private Enum585 AmountOfSavings_MoneyInvested;
	private Enum550 Part_SuperannuationContribs;
	private Enum501 OAPTimePeriod;
	private Enum502 Part_SeNetAnnualEarnings;
	private Enum501 IBTimePeriod;
	private Enum590 WhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions;
	private Enum69 AttendanceAllow;
	private Enum69 MatAllowance;
	private Enum229 StatusOfCase;
	private Enum501 IncSupTimePeriod;
	private Enum502 HRP_SeGrossAnnualEarnings;
	private Enum501 SSPTimePeriod;
	private Enum69 HRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership;
	private Enum69 IncSup_MortInterestHelp;
	public Integer getSSPAmount() {
		return SSPAmount;	}

	public void setSSPAmount(final Integer SSPAmount) {
		this.SSPAmount = SSPAmount;	}

	public Integer getHRP_OtherSourcesGrossAnnual_Core() {
		return HRP_OtherSourcesGrossAnnual_Core;	}

	public void setHRP_OtherSourcesGrossAnnual_Core(final Integer HRP_OtherSourcesGrossAnnual_Core) {
		this.HRP_OtherSourcesGrossAnnual_Core = HRP_OtherSourcesGrossAnnual_Core;	}

	public Integer getHRP_PriPenGrossAnnual_Core() {
		return HRP_PriPenGrossAnnual_Core;	}

	public void setHRP_PriPenGrossAnnual_Core(final Integer HRP_PriPenGrossAnnual_Core) {
		this.HRP_PriPenGrossAnnual_Core = HRP_PriPenGrossAnnual_Core;	}

	public Integer getPart_OtherSourcesGrossAnnual_Core() {
		return Part_OtherSourcesGrossAnnual_Core;	}

	public void setPart_OtherSourcesGrossAnnual_Core(final Integer Part_OtherSourcesGrossAnnual_Core) {
		this.Part_OtherSourcesGrossAnnual_Core = Part_OtherSourcesGrossAnnual_Core;	}

	public Integer getHRP_SeGrossAnnualEarnings_Core() {
		return HRP_SeGrossAnnualEarnings_Core;	}

	public void setHRP_SeGrossAnnualEarnings_Core(final Integer HRP_SeGrossAnnualEarnings_Core) {
		this.HRP_SeGrossAnnualEarnings_Core = HRP_SeGrossAnnualEarnings_Core;	}

	public Integer getPart_EmplGrossAnnualEarnings_Core() {
		return Part_EmplGrossAnnualEarnings_Core;	}

	public void setPart_EmplGrossAnnualEarnings_Core(final Integer Part_EmplGrossAnnualEarnings_Core) {
		this.Part_EmplGrossAnnualEarnings_Core = Part_EmplGrossAnnualEarnings_Core;	}

	public Integer getDVForWeeklyAmount() {
		return DVForWeeklyAmount;	}

	public void setDVForWeeklyAmount(final Integer DVForWeeklyAmount) {
		this.DVForWeeklyAmount = DVForWeeklyAmount;	}

	public Integer getHRP_NoOfIncomeSourcesAtSrcInc08() {
		return HRP_NoOfIncomeSourcesAtSrcInc08;	}

	public void setHRP_NoOfIncomeSourcesAtSrcInc08(final Integer HRP_NoOfIncomeSourcesAtSrcInc08) {
		this.HRP_NoOfIncomeSourcesAtSrcInc08 = HRP_NoOfIncomeSourcesAtSrcInc08;	}

	public Integer getTotalIncomeOfHouseholdBeforeDeductions() {
		return TotalIncomeOfHouseholdBeforeDeductions;	}

	public void setTotalIncomeOfHouseholdBeforeDeductions(final Integer TotalIncomeOfHouseholdBeforeDeductions) {
		this.TotalIncomeOfHouseholdBeforeDeductions = TotalIncomeOfHouseholdBeforeDeductions;	}

	public Integer getPart_SeGrossAnnualEarnings_Core() {
		return Part_SeGrossAnnualEarnings_Core;	}

	public void setPart_SeGrossAnnualEarnings_Core(final Integer Part_SeGrossAnnualEarnings_Core) {
		this.Part_SeGrossAnnualEarnings_Core = Part_SeGrossAnnualEarnings_Core;	}

	public Integer getPart_GrossIncome_Annualised() {
		return Part_GrossIncome_Annualised;	}

	public void setPart_GrossIncome_Annualised(final Integer Part_GrossIncome_Annualised) {
		this.Part_GrossIncome_Annualised = Part_GrossIncome_Annualised;	}

	public Integer getPart_PriPenGrossAnnual_Core() {
		return Part_PriPenGrossAnnual_Core;	}

	public void setPart_PriPenGrossAnnual_Core(final Integer Part_PriPenGrossAnnual_Core) {
		this.Part_PriPenGrossAnnual_Core = Part_PriPenGrossAnnual_Core;	}

	public Integer getHRP_EmplGrossAnnualEarnings_Core() {
		return HRP_EmplGrossAnnualEarnings_Core;	}

	public void setHRP_EmplGrossAnnualEarnings_Core(final Integer HRP_EmplGrossAnnualEarnings_Core) {
		this.HRP_EmplGrossAnnualEarnings_Core = HRP_EmplGrossAnnualEarnings_Core;	}

	public Integer getPart_NoOfIncomeSourcesAtSrcInc08() {
		return Part_NoOfIncomeSourcesAtSrcInc08;	}

	public void setPart_NoOfIncomeSourcesAtSrcInc08(final Integer Part_NoOfIncomeSourcesAtSrcInc08) {
		this.Part_NoOfIncomeSourcesAtSrcInc08 = Part_NoOfIncomeSourcesAtSrcInc08;	}

	public Integer getHRP_OccPenGrossAnnual_Core() {
		return HRP_OccPenGrossAnnual_Core;	}

	public void setHRP_OccPenGrossAnnual_Core(final Integer HRP_OccPenGrossAnnual_Core) {
		this.HRP_OccPenGrossAnnual_Core = HRP_OccPenGrossAnnual_Core;	}

	public Integer getHRPGrossIncome_Annualised() {
		return HRPGrossIncome_Annualised;	}

	public void setHRPGrossIncome_Annualised(final Integer HRPGrossIncome_Annualised) {
		this.HRPGrossIncome_Annualised = HRPGrossIncome_Annualised;	}

	public Integer getPart_OccPenGrossAnnual_Core() {
		return Part_OccPenGrossAnnual_Core;	}

	public void setPart_OccPenGrossAnnual_Core(final Integer Part_OccPenGrossAnnual_Core) {
		this.Part_OccPenGrossAnnual_Core = Part_OccPenGrossAnnual_Core;	}

	public Double getWTC_CTCInclInPartnerSeGrossMainEarnings() {
		return WTC_CTCInclInPartnerSeGrossMainEarnings;	}

	public void setWTC_CTCInclInPartnerSeGrossMainEarnings(final Double WTC_CTCInclInPartnerSeGrossMainEarnings) {
		this.WTC_CTCInclInPartnerSeGrossMainEarnings = WTC_CTCInclInPartnerSeGrossMainEarnings;	}

	public Double getWPPaidWithOtherBenefit() {
		return WPPaidWithOtherBenefit;	}

	public void setWPPaidWithOtherBenefit(final Double WPPaidWithOtherBenefit) {
		this.WPPaidWithOtherBenefit = WPPaidWithOtherBenefit;	}

	public Double getIBPaidWithOtherBen() {
		return IBPaidWithOtherBen;	}

	public void setIBPaidWithOtherBen(final Double IBPaidWithOtherBen) {
		this.IBPaidWithOtherBen = IBPaidWithOtherBen;	}

	public Double getICAPaidWithOtherBen() {
		return ICAPaidWithOtherBen;	}

	public void setICAPaidWithOtherBen(final Double ICAPaidWithOtherBen) {
		this.ICAPaidWithOtherBen = ICAPaidWithOtherBen;	}

	public Double getIBWklyAmount() {
		return IBWklyAmount;	}

	public void setIBWklyAmount(final Double IBWklyAmount) {
		this.IBWklyAmount = IBWklyAmount;	}

	public Double getSDAWklyAmt() {
		return SDAWklyAmt;	}

	public void setSDAWklyAmt(final Double SDAWklyAmt) {
		this.SDAWklyAmt = SDAWklyAmt;	}

	public Double getCTCPaidWithOtherBen() {
		return CTCPaidWithOtherBen;	}

	public void setCTCPaidWithOtherBen(final Double CTCPaidWithOtherBen) {
		this.CTCPaidWithOtherBen = CTCPaidWithOtherBen;	}

	public Double getSSPPaidWithOtherBen() {
		return SSPPaidWithOtherBen;	}

	public void setSSPPaidWithOtherBen(final Double SSPPaidWithOtherBen) {
		this.SSPPaidWithOtherBen = SSPPaidWithOtherBen;	}

	public Double getOthDisAmount() {
		return OthDisAmount;	}

	public void setOthDisAmount(final Double OthDisAmount) {
		this.OthDisAmount = OthDisAmount;	}

	public Double getDLACareAmount() {
		return DLACareAmount;	}

	public void setDLACareAmount(final Double DLACareAmount) {
		this.DLACareAmount = DLACareAmount;	}

	public Double getIDBAmount() {
		return IDBAmount;	}

	public void setIDBAmount(final Double IDBAmount) {
		this.IDBAmount = IDBAmount;	}

	public Double getWTC_CTCInclInHRP_PartSeNetEarnings() {
		return WTC_CTCInclInHRP_PartSeNetEarnings;	}

	public void setWTC_CTCInclInHRP_PartSeNetEarnings(final Double WTC_CTCInclInHRP_PartSeNetEarnings) {
		this.WTC_CTCInclInHRP_PartSeNetEarnings = WTC_CTCInclInHRP_PartSeNetEarnings;	}

	public Double getIncSupAmtLastTime() {
		return IncSupAmtLastTime;	}

	public void setIncSupAmtLastTime(final Double IncSupAmtLastTime) {
		this.IncSupAmtLastTime = IncSupAmtLastTime;	}

	public Double getWDPAmount() {
		return WDPAmount;	}

	public void setWDPAmount(final Double WDPAmount) {
		this.WDPAmount = WDPAmount;	}

	public Double getDLACarePaidWithOtherBen() {
		return DLACarePaidWithOtherBen;	}

	public void setDLACarePaidWithOtherBen(final Double DLACarePaidWithOtherBen) {
		this.DLACarePaidWithOtherBen = DLACarePaidWithOtherBen;	}

	public Double getDisPremWklyAmt() {
		return DisPremWklyAmt;	}

	public void setDisPremWklyAmt(final Double DisPremWklyAmt) {
		this.DisPremWklyAmt = DisPremWklyAmt;	}

	public Double getMAPaidWithOtherBen() {
		return MAPaidWithOtherBen;	}

	public void setMAPaidWithOtherBen(final Double MAPaidWithOtherBen) {
		this.MAPaidWithOtherBen = MAPaidWithOtherBen;	}

	public Double getPartnerMaintenancePaymentPeriod() {
		return PartnerMaintenancePaymentPeriod;	}

	public void setPartnerMaintenancePaymentPeriod(final Double PartnerMaintenancePaymentPeriod) {
		this.PartnerMaintenancePaymentPeriod = PartnerMaintenancePaymentPeriod;	}

	public Double getESAAmount() {
		return ESAAmount;	}

	public void setESAAmount(final Double ESAAmount) {
		this.ESAAmount = ESAAmount;	}

	public Double getSDAPaidWithOtherBen() {
		return SDAPaidWithOtherBen;	}

	public void setSDAPaidWithOtherBen(final Double SDAPaidWithOtherBen) {
		this.SDAPaidWithOtherBen = SDAPaidWithOtherBen;	}

	public Double getICAWklyAmt() {
		return ICAWklyAmt;	}

	public void setICAWklyAmt(final Double ICAWklyAmt) {
		this.ICAWklyAmt = ICAWklyAmt;	}

	public Double getESAPaidWithOtherBen() {
		return ESAPaidWithOtherBen;	}

	public void setESAPaidWithOtherBen(final Double ESAPaidWithOtherBen) {
		this.ESAPaidWithOtherBen = ESAPaidWithOtherBen;	}

	public Double getCTCWklyAmount() {
		return CTCWklyAmount;	}

	public void setCTCWklyAmount(final Double CTCWklyAmount) {
		this.CTCWklyAmount = CTCWklyAmount;	}

	public Double getWTC_CTCIncInHRPEmplGrossMainEarnings() {
		return WTC_CTCIncInHRPEmplGrossMainEarnings;	}

	public void setWTC_CTCIncInHRPEmplGrossMainEarnings(final Double WTC_CTCIncInHRPEmplGrossMainEarnings) {
		this.WTC_CTCIncInHRPEmplGrossMainEarnings = WTC_CTCIncInHRPEmplGrossMainEarnings;	}

	public Double getWDPWklyAmt() {
		return WDPWklyAmt;	}

	public void setWDPWklyAmt(final Double WDPWklyAmt) {
		this.WDPWklyAmt = WDPWklyAmt;	}

	public Double getDLACarePaidWithOtherBen_BNDCCDK() {
		return DLACarePaidWithOtherBen_BNDCCDK;	}

	public void setDLACarePaidWithOtherBen_BNDCCDK(final Double DLACarePaidWithOtherBen_BNDCCDK) {
		this.DLACarePaidWithOtherBen_BNDCCDK = DLACarePaidWithOtherBen_BNDCCDK;	}

	public Double getOAPWklyAmountt() {
		return OAPWklyAmountt;	}

	public void setOAPWklyAmountt(final Double OAPWklyAmountt) {
		this.OAPWklyAmountt = OAPWklyAmountt;	}

	public Double getOthDisPaidWithOtherBen() {
		return OthDisPaidWithOtherBen;	}

	public void setOthDisPaidWithOtherBen(final Double OthDisPaidWithOtherBen) {
		this.OthDisPaidWithOtherBen = OthDisPaidWithOtherBen;	}

	public Double getDoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit() {
		return DoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit;	}

	public void setDoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit(final Double DoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit) {
		this.DoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit = DoYouReceiveEitherADisablityOrCarersPremiumWithYourPensionCredit;	}

	public Double getAAWklyAmt() {
		return AAWklyAmt;	}

	public void setAAWklyAmt(final Double AAWklyAmt) {
		this.AAWklyAmt = AAWklyAmt;	}

	public Double getPartnerMaintenancePaymentAmount() {
		return PartnerMaintenancePaymentAmount;	}

	public void setPartnerMaintenancePaymentAmount(final Double PartnerMaintenancePaymentAmount) {
		this.PartnerMaintenancePaymentAmount = PartnerMaintenancePaymentAmount;	}

	public Double getSDAAmount() {
		return SDAAmount;	}

	public void setSDAAmount(final Double SDAAmount) {
		this.SDAAmount = SDAAmount;	}

	public Double getIBAmount() {
		return IBAmount;	}

	public void setIBAmount(final Double IBAmount) {
		this.IBAmount = IBAmount;	}

	public Double getNoneOfTheseOtherBenefits() {
		return NoneOfTheseOtherBenefits;	}

	public void setNoneOfTheseOtherBenefits(final Double NoneOfTheseOtherBenefits) {
		this.NoneOfTheseOtherBenefits = NoneOfTheseOtherBenefits;	}

	public Double getIDBPaidWithOtherBen() {
		return IDBPaidWithOtherBen;	}

	public void setIDBPaidWithOtherBen(final Double IDBPaidWithOtherBen) {
		this.IDBPaidWithOtherBen = IDBPaidWithOtherBen;	}

	public Double getOthDisPaidWithOtherBen_BNODISDK() {
		return OthDisPaidWithOtherBen_BNODISDK;	}

	public void setOthDisPaidWithOtherBen_BNODISDK(final Double OthDisPaidWithOtherBen_BNODISDK) {
		this.OthDisPaidWithOtherBen_BNODISDK = OthDisPaidWithOtherBen_BNODISDK;	}

	public Double getICAPaidWithOtherBen_BNICADK() {
		return ICAPaidWithOtherBen_BNICADK;	}

	public void setICAPaidWithOtherBen_BNICADK(final Double ICAPaidWithOtherBen_BNICADK) {
		this.ICAPaidWithOtherBen_BNICADK = ICAPaidWithOtherBen_BNICADK;	}

	public Double getJSAPaidWithOtherBen() {
		return JSAPaidWithOtherBen;	}

	public void setJSAPaidWithOtherBen(final Double JSAPaidWithOtherBen) {
		this.JSAPaidWithOtherBen = JSAPaidWithOtherBen;	}

	public Double getMAWklyAmt() {
		return MAWklyAmt;	}

	public void setMAWklyAmt(final Double MAWklyAmt) {
		this.MAWklyAmt = MAWklyAmt;	}

	public Double getIDBWklyAmt() {
		return IDBWklyAmt;	}

	public void setIDBWklyAmt(final Double IDBWklyAmt) {
		this.IDBWklyAmt = IDBWklyAmt;	}

	public Double getWTC_CTCInclInPartnerEmplNetMainEarnings() {
		return WTC_CTCInclInPartnerEmplNetMainEarnings;	}

	public void setWTC_CTCInclInPartnerEmplNetMainEarnings(final Double WTC_CTCInclInPartnerEmplNetMainEarnings) {
		this.WTC_CTCInclInPartnerEmplNetMainEarnings = WTC_CTCInclInPartnerEmplNetMainEarnings;	}

	public Double getWDPPaidWithOtherBen() {
		return WDPPaidWithOtherBen;	}

	public void setWDPPaidWithOtherBen(final Double WDPPaidWithOtherBen) {
		this.WDPPaidWithOtherBen = WDPPaidWithOtherBen;	}

	public Double getIBPaidWithOtherBen_V207_A() {
		return IBPaidWithOtherBen_V207_A;	}

	public void setIBPaidWithOtherBen_V207_A(final Double IBPaidWithOtherBen_V207_A) {
		this.IBPaidWithOtherBen_V207_A = IBPaidWithOtherBen_V207_A;	}

	public Double getWPPaidWithOtherBenefit_BNWDPNDK() {
		return WPPaidWithOtherBenefit_BNWDPNDK;	}

	public void setWPPaidWithOtherBenefit_BNWDPNDK(final Double WPPaidWithOtherBenefit_BNWDPNDK) {
		this.WPPaidWithOtherBenefit_BNWDPNDK = WPPaidWithOtherBenefit_BNWDPNDK;	}

	public Double getJSAAmount() {
		return JSAAmount;	}

	public void setJSAAmount(final Double JSAAmount) {
		this.JSAAmount = JSAAmount;	}

	public Double getMAAmount() {
		return MAAmount;	}

	public void setMAAmount(final Double MAAmount) {
		this.MAAmount = MAAmount;	}

	public Double getWPAmount() {
		return WPAmount;	}

	public void setWPAmount(final Double WPAmount) {
		this.WPAmount = WPAmount;	}

	public Double getWPWklyAmt() {
		return WPWklyAmt;	}

	public void setWPWklyAmt(final Double WPWklyAmt) {
		this.WPWklyAmt = WPWklyAmt;	}

	public Double getOtherBenefits_Don_TKnow() {
		return OtherBenefits_Don_TKnow;	}

	public void setOtherBenefits_Don_TKnow(final Double OtherBenefits_Don_TKnow) {
		this.OtherBenefits_Don_TKnow = OtherBenefits_Don_TKnow;	}

	public Double getIncSupWklyAmt() {
		return IncSupWklyAmt;	}

	public void setIncSupWklyAmt(final Double IncSupWklyAmt) {
		this.IncSupWklyAmt = IncSupWklyAmt;	}

	public Double getDLACareWklyAmt() {
		return DLACareWklyAmt;	}

	public void setDLACareWklyAmt(final Double DLACareWklyAmt) {
		this.DLACareWklyAmt = DLACareWklyAmt;	}

	public Double getPenCPaidWithOtherBen() {
		return PenCPaidWithOtherBen;	}

	public void setPenCPaidWithOtherBen(final Double PenCPaidWithOtherBen) {
		this.PenCPaidWithOtherBen = PenCPaidWithOtherBen;	}

	public Double getAAPaidWithOtherBen() {
		return AAPaidWithOtherBen;	}

	public void setAAPaidWithOtherBen(final Double AAPaidWithOtherBen) {
		this.AAPaidWithOtherBen = AAPaidWithOtherBen;	}

	public Double getJSAPaidWithOtherBen_BNJSADK2() {
		return JSAPaidWithOtherBen_BNJSADK2;	}

	public void setJSAPaidWithOtherBen_BNJSADK2(final Double JSAPaidWithOtherBen_BNJSADK2) {
		this.JSAPaidWithOtherBen_BNJSADK2 = JSAPaidWithOtherBen_BNJSADK2;	}

	public Double getDisPremPaidWithOtherBen() {
		return DisPremPaidWithOtherBen;	}

	public void setDisPremPaidWithOtherBen(final Double DisPremPaidWithOtherBen) {
		this.DisPremPaidWithOtherBen = DisPremPaidWithOtherBen;	}

	public Double getWTC_CTCInclInHRP_PartSeGrossEarnings() {
		return WTC_CTCInclInHRP_PartSeGrossEarnings;	}

	public void setWTC_CTCInclInHRP_PartSeGrossEarnings(final Double WTC_CTCInclInHRP_PartSeGrossEarnings) {
		this.WTC_CTCInclInHRP_PartSeGrossEarnings = WTC_CTCInclInHRP_PartSeGrossEarnings;	}

	public Double getOAPPaidWithOtherBen() {
		return OAPPaidWithOtherBen;	}

	public void setOAPPaidWithOtherBen(final Double OAPPaidWithOtherBen) {
		this.OAPPaidWithOtherBen = OAPPaidWithOtherBen;	}

	public Double getOthDisWklyAmt() {
		return OthDisWklyAmt;	}

	public void setOthDisWklyAmt(final Double OthDisWklyAmt) {
		this.OthDisWklyAmt = OthDisWklyAmt;	}

	public Double getDLAMobPaidWithOtherBen() {
		return DLAMobPaidWithOtherBen;	}

	public void setDLAMobPaidWithOtherBen(final Double DLAMobPaidWithOtherBen) {
		this.DLAMobPaidWithOtherBen = DLAMobPaidWithOtherBen;	}

	public Double getSSPPaidWithOtherBen_BNSSPDK2() {
		return SSPPaidWithOtherBen_BNSSPDK2;	}

	public void setSSPPaidWithOtherBen_BNSSPDK2(final Double SSPPaidWithOtherBen_BNSSPDK2) {
		this.SSPPaidWithOtherBen_BNSSPDK2 = SSPPaidWithOtherBen_BNSSPDK2;	}

	public Double getDisabilityPremWithIncSup_HB() {
		return DisabilityPremWithIncSup_HB;	}

	public void setDisabilityPremWithIncSup_HB(final Double DisabilityPremWithIncSup_HB) {
		this.DisabilityPremWithIncSup_HB = DisabilityPremWithIncSup_HB;	}

	public Double getWTCPaidWithOtherBen() {
		return WTCPaidWithOtherBen;	}

	public void setWTCPaidWithOtherBen(final Double WTCPaidWithOtherBen) {
		this.WTCPaidWithOtherBen = WTCPaidWithOtherBen;	}

	public Double getBNLIDBWK() {
		return BNLIDBWK;	}

	public void setBNLIDBWK(final Double BNLIDBWK) {
		this.BNLIDBWK = BNLIDBWK;	}

	public Double getDLAMobAmount() {
		return DLAMobAmount;	}

	public void setDLAMobAmount(final Double DLAMobAmount) {
		this.DLAMobAmount = DLAMobAmount;	}

	public Double getWTC_CTCInclInPartnrSeNetMainEarnings() {
		return WTC_CTCInclInPartnrSeNetMainEarnings;	}

	public void setWTC_CTCInclInPartnrSeNetMainEarnings(final Double WTC_CTCInclInPartnrSeNetMainEarnings) {
		this.WTC_CTCInclInPartnrSeNetMainEarnings = WTC_CTCInclInPartnrSeNetMainEarnings;	}

	public Double getIDBPaidWithOtherBen_BNIIDBDK() {
		return IDBPaidWithOtherBen_BNIIDBDK;	}

	public void setIDBPaidWithOtherBen_BNIIDBDK(final Double IDBPaidWithOtherBen_BNIIDBDK) {
		this.IDBPaidWithOtherBen_BNIIDBDK = IDBPaidWithOtherBen_BNIIDBDK;	}

	public Double getDisPremAmount() {
		return DisPremAmount;	}

	public void setDisPremAmount(final Double DisPremAmount) {
		this.DisPremAmount = DisPremAmount;	}

	public Double getSDAPaidWithOtherBen_BNSDADK2() {
		return SDAPaidWithOtherBen_BNSDADK2;	}

	public void setSDAPaidWithOtherBen_BNSDADK2(final Double SDAPaidWithOtherBen_BNSDADK2) {
		this.SDAPaidWithOtherBen_BNSDADK2 = SDAPaidWithOtherBen_BNSDADK2;	}

	public Double getPenCPaidWithOtherBen_V203_A() {
		return PenCPaidWithOtherBen_V203_A;	}

	public void setPenCPaidWithOtherBen_V203_A(final Double PenCPaidWithOtherBen_V203_A) {
		this.PenCPaidWithOtherBen_V203_A = PenCPaidWithOtherBen_V203_A;	}

	public Double getSSPWklyAmt() {
		return SSPWklyAmt;	}

	public void setSSPWklyAmt(final Double SSPWklyAmt) {
		this.SSPWklyAmt = SSPWklyAmt;	}

	public Double getMAPaidWithOtherBen_BNMATADK() {
		return MAPaidWithOtherBen_BNMATADK;	}

	public void setMAPaidWithOtherBen_BNMATADK(final Double MAPaidWithOtherBen_BNMATADK) {
		this.MAPaidWithOtherBen_BNMATADK = MAPaidWithOtherBen_BNMATADK;	}

	public Double getIncSup_MortInterestAmt() {
		return IncSup_MortInterestAmt;	}

	public void setIncSup_MortInterestAmt(final Double IncSup_MortInterestAmt) {
		this.IncSup_MortInterestAmt = IncSup_MortInterestAmt;	}

	public Double getOldAgePensionAmount() {
		return OldAgePensionAmount;	}

	public void setOldAgePensionAmount(final Double OldAgePensionAmount) {
		this.OldAgePensionAmount = OldAgePensionAmount;	}

	public Double getWDPPaidWithOtherBen_BNWRDPDK() {
		return WDPPaidWithOtherBen_BNWRDPDK;	}

	public void setWDPPaidWithOtherBen_BNWRDPDK(final Double WDPPaidWithOtherBen_BNWRDPDK) {
		this.WDPPaidWithOtherBen_BNWRDPDK = WDPPaidWithOtherBen_BNWRDPDK;	}

	public Double getDLAMobPaidWithOtherBen_BNDMCDK() {
		return DLAMobPaidWithOtherBen_BNDMCDK;	}

	public void setDLAMobPaidWithOtherBen_BNDMCDK(final Double DLAMobPaidWithOtherBen_BNDMCDK) {
		this.DLAMobPaidWithOtherBen_BNDMCDK = DLAMobPaidWithOtherBen_BNDMCDK;	}

	public Double getPenCAmount() {
		return PenCAmount;	}

	public void setPenCAmount(final Double PenCAmount) {
		this.PenCAmount = PenCAmount;	}

	public Double getDLAMobWklyAmt() {
		return DLAMobWklyAmt;	}

	public void setDLAMobWklyAmt(final Double DLAMobWklyAmt) {
		this.DLAMobWklyAmt = DLAMobWklyAmt;	}

	public Double getPartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership() {
		return PartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership;	}

	public void setPartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership(final Double PartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership) {
		this.PartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership = PartnerMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership;	}

	public Double getESAWklyAmount() {
		return ESAWklyAmount;	}

	public void setESAWklyAmount(final Double ESAWklyAmount) {
		this.ESAWklyAmount = ESAWklyAmount;	}

	public Double getPenCWklyAmt() {
		return PenCWklyAmt;	}

	public void setPenCWklyAmt(final Double PenCWklyAmt) {
		this.PenCWklyAmt = PenCWklyAmt;	}

	public Double getWTC_CTCInclInHRPSeNetMainEarnings() {
		return WTC_CTCInclInHRPSeNetMainEarnings;	}

	public void setWTC_CTCInclInHRPSeNetMainEarnings(final Double WTC_CTCInclInHRPSeNetMainEarnings) {
		this.WTC_CTCInclInHRPSeNetMainEarnings = WTC_CTCInclInHRPSeNetMainEarnings;	}

	public Double getDoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport() {
		return DoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport;	}

	public void setDoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport(final Double DoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport) {
		this.DoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport = DoYouReceiveEitherADisablityOrCarersPremiumWithYourIncomeSupport;	}

	public Double getDisPremTimePeriod() {
		return DisPremTimePeriod;	}

	public void setDisPremTimePeriod(final Double DisPremTimePeriod) {
		this.DisPremTimePeriod = DisPremTimePeriod;	}

	public Double getWTC_CTCInclInHRP_PartEmplNetEarnings() {
		return WTC_CTCInclInHRP_PartEmplNetEarnings;	}

	public void setWTC_CTCInclInHRP_PartEmplNetEarnings(final Double WTC_CTCInclInHRP_PartEmplNetEarnings) {
		this.WTC_CTCInclInHRP_PartEmplNetEarnings = WTC_CTCInclInHRP_PartEmplNetEarnings;	}

	public Double getIncSupPaidWithOtherBen() {
		return IncSupPaidWithOtherBen;	}

	public void setIncSupPaidWithOtherBen(final Double IncSupPaidWithOtherBen) {
		this.IncSupPaidWithOtherBen = IncSupPaidWithOtherBen;	}

	public Double getWTC_CTCInclInPartnerEmplGrossMainEarnings() {
		return WTC_CTCInclInPartnerEmplGrossMainEarnings;	}

	public void setWTC_CTCInclInPartnerEmplGrossMainEarnings(final Double WTC_CTCInclInPartnerEmplGrossMainEarnings) {
		this.WTC_CTCInclInPartnerEmplGrossMainEarnings = WTC_CTCInclInPartnerEmplGrossMainEarnings;	}

	public Double getAmtOfHB_IncSupInclDisabilityPrem() {
		return AmtOfHB_IncSupInclDisabilityPrem;	}

	public void setAmtOfHB_IncSupInclDisabilityPrem(final Double AmtOfHB_IncSupInclDisabilityPrem) {
		this.AmtOfHB_IncSupInclDisabilityPrem = AmtOfHB_IncSupInclDisabilityPrem;	}

	public Double getIncSupPaidWithOtherBen_V201_A() {
		return IncSupPaidWithOtherBen_V201_A;	}

	public void setIncSupPaidWithOtherBen_V201_A(final Double IncSupPaidWithOtherBen_V201_A) {
		this.IncSupPaidWithOtherBen_V201_A = IncSupPaidWithOtherBen_V201_A;	}

	public Double getCTCPaidWithOtherBen_BNCTCDK2() {
		return CTCPaidWithOtherBen_BNCTCDK2;	}

	public void setCTCPaidWithOtherBen_BNCTCDK2(final Double CTCPaidWithOtherBen_BNCTCDK2) {
		this.CTCPaidWithOtherBen_BNCTCDK2 = CTCPaidWithOtherBen_BNCTCDK2;	}

	public Double getAAPaidWithOtherBen_BNAADK() {
		return AAPaidWithOtherBen_BNAADK;	}

	public void setAAPaidWithOtherBen_BNAADK(final Double AAPaidWithOtherBen_BNAADK) {
		this.AAPaidWithOtherBen_BNAADK = AAPaidWithOtherBen_BNAADK;	}

	public Double getCTCAmount() {
		return CTCAmount;	}

	public void setCTCAmount(final Double CTCAmount) {
		this.CTCAmount = CTCAmount;	}

	public Double getWTCPaidWithOtherBen_BNWTCDK() {
		return WTCPaidWithOtherBen_BNWTCDK;	}

	public void setWTCPaidWithOtherBen_BNWTCDK(final Double WTCPaidWithOtherBen_BNWTCDK) {
		this.WTCPaidWithOtherBen_BNWTCDK = WTCPaidWithOtherBen_BNWTCDK;	}

	public Double getDidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple() {
		return DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple;	}

	public void setDidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple(final Double DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple) {
		this.DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple = DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_1StPersonInCouple;	}

	public Double getESAPaidWithOtherBen_BNESADK2() {
		return ESAPaidWithOtherBen_BNESADK2;	}

	public void setESAPaidWithOtherBen_BNESADK2(final Double ESAPaidWithOtherBen_BNESADK2) {
		this.ESAPaidWithOtherBen_BNESADK2 = ESAPaidWithOtherBen_BNESADK2;	}

	public Double getDidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple() {
		return DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple;	}

	public void setDidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple(final Double DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple) {
		this.DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple = DidYouIncludeThisAmountInYourTotalIncomeEarlierOn_2NdPersonInCouple;	}

	public Double getWTC_CTCInclInHRPSeGrossMainEarnings() {
		return WTC_CTCInclInHRPSeGrossMainEarnings;	}

	public void setWTC_CTCInclInHRPSeGrossMainEarnings(final Double WTC_CTCInclInHRPSeGrossMainEarnings) {
		this.WTC_CTCInclInHRPSeGrossMainEarnings = WTC_CTCInclInHRPSeGrossMainEarnings;	}

	public Double getAAAmount() {
		return AAAmount;	}

	public void setAAAmount(final Double AAAmount) {
		this.AAAmount = AAAmount;	}

	public Double getIncSup_MortInterestMthlyAmt() {
		return IncSup_MortInterestMthlyAmt;	}

	public void setIncSup_MortInterestMthlyAmt(final Double IncSup_MortInterestMthlyAmt) {
		this.IncSup_MortInterestMthlyAmt = IncSup_MortInterestMthlyAmt;	}

	public Double getICAAmount() {
		return ICAAmount;	}

	public void setICAAmount(final Double ICAAmount) {
		this.ICAAmount = ICAAmount;	}

	public Double getWTCWklyAmount() {
		return WTCWklyAmount;	}

	public void setWTCWklyAmount(final Double WTCWklyAmount) {
		this.WTCWklyAmount = WTCWklyAmount;	}

	public Double getJSAWklyAmount() {
		return JSAWklyAmount;	}

	public void setJSAWklyAmount(final Double JSAWklyAmount) {
		this.JSAWklyAmount = JSAWklyAmount;	}

	public Double getOAPPaidWithOtherBen_BNSTPNDK() {
		return OAPPaidWithOtherBen_BNSTPNDK;	}

	public void setOAPPaidWithOtherBen_BNSTPNDK(final Double OAPPaidWithOtherBen_BNSTPNDK) {
		this.OAPPaidWithOtherBen_BNSTPNDK = OAPPaidWithOtherBen_BNSTPNDK;	}

	public Double getWTCAmount() {
		return WTCAmount;	}

	public void setWTCAmount(final Double WTCAmount) {
		this.WTCAmount = WTCAmount;	}

	public Double getWTC_CTCInclInHRPEmplNetMainEarnings() {
		return WTC_CTCInclInHRPEmplNetMainEarnings;	}

	public void setWTC_CTCInclInHRPEmplNetMainEarnings(final Double WTC_CTCInclInHRPEmplNetMainEarnings) {
		this.WTC_CTCInclInHRPEmplNetMainEarnings = WTC_CTCInclInHRPEmplNetMainEarnings;	}

	public Double getNoneOfTheseOtherBenefits_BNONONE() {
		return NoneOfTheseOtherBenefits_BNONONE;	}

	public void setNoneOfTheseOtherBenefits_BNONONE(final Double NoneOfTheseOtherBenefits_BNONONE) {
		this.NoneOfTheseOtherBenefits_BNONONE = NoneOfTheseOtherBenefits_BNONONE;	}

	public Double getHRPMaintenancePaymentAmount() {
		return HRPMaintenancePaymentAmount;	}

	public void setHRPMaintenancePaymentAmount(final Double HRPMaintenancePaymentAmount) {
		this.HRPMaintenancePaymentAmount = HRPMaintenancePaymentAmount;	}

	public Double getOtherBenefits_Refused() {
		return OtherBenefits_Refused;	}

	public void setOtherBenefits_Refused(final Double OtherBenefits_Refused) {
		this.OtherBenefits_Refused = OtherBenefits_Refused;	}

	public Double getIsYourJobSeekersAllowance() {
		return IsYourJobSeekersAllowance;	}

	public void setIsYourJobSeekersAllowance(final Double IsYourJobSeekersAllowance) {
		this.IsYourJobSeekersAllowance = IsYourJobSeekersAllowance;	}

	public Double getWTC_CTCInclInHRP_PartEmplGrossEarnings() {
		return WTC_CTCInclInHRP_PartEmplGrossEarnings;	}

	public void setWTC_CTCInclInHRP_PartEmplGrossEarnings(final Double WTC_CTCInclInHRP_PartEmplGrossEarnings) {
		this.WTC_CTCInclInHRP_PartEmplGrossEarnings = WTC_CTCInclInHRP_PartEmplGrossEarnings;	}

	public Enum501 getDLACareTimePeriod() {
		return DLACareTimePeriod;	}

	public void setDLACareTimePeriod(final Enum501 DLACareTimePeriod) {
		this.DLACareTimePeriod = DLACareTimePeriod;	}

	public Enum502 getHRP_PriPenNetAnnual() {
		return HRP_PriPenNetAnnual;	}

	public void setHRP_PriPenNetAnnual(final Enum502 HRP_PriPenNetAnnual) {
		this.HRP_PriPenNetAnnual = HRP_PriPenNetAnnual;	}

	public Enum501 getPenCTimePeriod() {
		return PenCTimePeriod;	}

	public void setPenCTimePeriod(final Enum501 PenCTimePeriod) {
		this.PenCTimePeriod = PenCTimePeriod;	}

	public Enum69 getHRP_SuperannuationContribs() {
		return HRP_SuperannuationContribs;	}

	public void setHRP_SuperannuationContribs(final Enum69 HRP_SuperannuationContribs) {
		this.HRP_SuperannuationContribs = HRP_SuperannuationContribs;	}

	public Enum501 getDLAMobTimePeriod() {
		return DLAMobTimePeriod;	}

	public void setDLAMobTimePeriod(final Enum501 DLAMobTimePeriod) {
		this.DLAMobTimePeriod = DLAMobTimePeriod;	}

	public Enum69 getWorkingTaxCredit() {
		return WorkingTaxCredit;	}

	public void setWorkingTaxCredit(final Enum69 WorkingTaxCredit) {
		this.WorkingTaxCredit = WorkingTaxCredit;	}

	public Enum502 getHRP_SeNetAnnualEarnings() {
		return HRP_SeNetAnnualEarnings;	}

	public void setHRP_SeNetAnnualEarnings(final Enum502 HRP_SeNetAnnualEarnings) {
		this.HRP_SeNetAnnualEarnings = HRP_SeNetAnnualEarnings;	}

	public Enum69 getHRP_RegularPaytOfSeEarnings() {
		return HRP_RegularPaytOfSeEarnings;	}

	public void setHRP_RegularPaytOfSeEarnings(final Enum69 HRP_RegularPaytOfSeEarnings) {
		this.HRP_RegularPaytOfSeEarnings = HRP_RegularPaytOfSeEarnings;	}

	public Enum509 getIsThatAWeekly_MonthlyOrAnnualAmount_() {
		return IsThatAWeekly_MonthlyOrAnnualAmount_;	}

	public void setIsThatAWeekly_MonthlyOrAnnualAmount_(final Enum509 IsThatAWeekly_MonthlyOrAnnualAmount_) {
		this.IsThatAWeekly_MonthlyOrAnnualAmount_ = IsThatAWeekly_MonthlyOrAnnualAmount_;	}

	public Enum69 getWhetherHRP_SPartnerPotentiallyAskedEarningsBlocks() {
		return WhetherHRP_SPartnerPotentiallyAskedEarningsBlocks;	}

	public void setWhetherHRP_SPartnerPotentiallyAskedEarningsBlocks(final Enum69 WhetherHRP_SPartnerPotentiallyAskedEarningsBlocks) {
		this.WhetherHRP_SPartnerPotentiallyAskedEarningsBlocks = WhetherHRP_SPartnerPotentiallyAskedEarningsBlocks;	}

	public Enum511 getESAAllowanceType() {
		return ESAAllowanceType;	}

	public void setESAAllowanceType(final Enum511 ESAAllowanceType) {
		this.ESAAllowanceType = ESAAllowanceType;	}

	public Enum502 getPart_EmplNetAnnualEarnings() {
		return Part_EmplNetAnnualEarnings;	}

	public void setPart_EmplNetAnnualEarnings(final Enum502 Part_EmplNetAnnualEarnings) {
		this.Part_EmplNetAnnualEarnings = Part_EmplNetAnnualEarnings;	}

	public Enum69 getDLACareComponentAtHigherRate() {
		return DLACareComponentAtHigherRate;	}

	public void setDLACareComponentAtHigherRate(final Enum69 DLACareComponentAtHigherRate) {
		this.DLACareComponentAtHigherRate = DLACareComponentAtHigherRate;	}

	public Enum69 getPensionCredit() {
		return PensionCredit;	}

	public void setPensionCredit(final Enum69 PensionCredit) {
		this.PensionCredit = PensionCredit;	}

	public Enum515 getHRPLossOfNetEarningsFromSelfEmployment() {
		return HRPLossOfNetEarningsFromSelfEmployment;	}

	public void setHRPLossOfNetEarningsFromSelfEmployment(final Enum515 HRPLossOfNetEarningsFromSelfEmployment) {
		this.HRPLossOfNetEarningsFromSelfEmployment = HRPLossOfNetEarningsFromSelfEmployment;	}

	public Enum69 getJobseekersAllowance() {
		return JobseekersAllowance;	}

	public void setJobseekersAllowance(final Enum69 JobseekersAllowance) {
		this.JobseekersAllowance = JobseekersAllowance;	}

	public Enum502 getHRP_PriPenGrossAnnual() {
		return HRP_PriPenGrossAnnual;	}

	public void setHRP_PriPenGrossAnnual(final Enum502 HRP_PriPenGrossAnnual) {
		this.HRP_PriPenGrossAnnual = HRP_PriPenGrossAnnual;	}

	public Enum501 getAATimePeriod() {
		return AATimePeriod;	}

	public void setAATimePeriod(final Enum501 AATimePeriod) {
		this.AATimePeriod = AATimePeriod;	}

	public Enum69 getSavings_MoneyInvested() {
		return Savings_MoneyInvested;	}

	public void setSavings_MoneyInvested(final Enum69 Savings_MoneyInvested) {
		this.Savings_MoneyInvested = Savings_MoneyInvested;	}

	public Enum69 getMPPIReceipts() {
		return MPPIReceipts;	}

	public void setMPPIReceipts(final Enum69 MPPIReceipts) {
		this.MPPIReceipts = MPPIReceipts;	}

	public Enum501 getICATimePeriod() {
		return ICATimePeriod;	}

	public void setICATimePeriod(final Enum501 ICATimePeriod) {
		this.ICATimePeriod = ICATimePeriod;	}

	public Enum69 getReturnToWorkCredit() {
		return ReturnToWorkCredit;	}

	public void setReturnToWorkCredit(final Enum69 ReturnToWorkCredit) {
		this.ReturnToWorkCredit = ReturnToWorkCredit;	}

	public Enum69 getIncludeIncSup() {
		return IncludeIncSup;	}

	public void setIncludeIncSup(final Enum69 IncludeIncSup) {
		this.IncludeIncSup = IncludeIncSup;	}

	public Enum69 getStatutorySickPay() {
		return StatutorySickPay;	}

	public void setStatutorySickPay(final Enum69 StatutorySickPay) {
		this.StatutorySickPay = StatutorySickPay;	}

	public Enum501 getOthDisTimePeriod() {
		return OthDisTimePeriod;	}

	public void setOthDisTimePeriod(final Enum501 OthDisTimePeriod) {
		this.OthDisTimePeriod = OthDisTimePeriod;	}

	public Enum69 getInWorkCredit() {
		return InWorkCredit;	}

	public void setInWorkCredit(final Enum69 InWorkCredit) {
		this.InWorkCredit = InWorkCredit;	}

	public Enum69 getCurrentlyWithinThe13WeekESAAssessmentPhase() {
		return CurrentlyWithinThe13WeekESAAssessmentPhase;	}

	public void setCurrentlyWithinThe13WeekESAAssessmentPhase(final Enum69 CurrentlyWithinThe13WeekESAAssessmentPhase) {
		this.CurrentlyWithinThe13WeekESAAssessmentPhase = CurrentlyWithinThe13WeekESAAssessmentPhase;	}

	public Enum69 getIncomeSupport() {
		return IncomeSupport;	}

	public void setIncomeSupport(final Enum69 IncomeSupport) {
		this.IncomeSupport = IncomeSupport;	}

	public Enum69 getPart_RegularPaytOfSeEarnings() {
		return Part_RegularPaytOfSeEarnings;	}

	public void setPart_RegularPaytOfSeEarnings(final Enum69 Part_RegularPaytOfSeEarnings) {
		this.Part_RegularPaytOfSeEarnings = Part_RegularPaytOfSeEarnings;	}

	public Enum515 getPartnerLossOfNetEarningsFromSelfEmployment() {
		return PartnerLossOfNetEarningsFromSelfEmployment;	}

	public void setPartnerLossOfNetEarningsFromSelfEmployment(final Enum515 PartnerLossOfNetEarningsFromSelfEmployment) {
		this.PartnerLossOfNetEarningsFromSelfEmployment = PartnerLossOfNetEarningsFromSelfEmployment;	}

	public Enum69 getEmploymentSupportAllowance() {
		return EmploymentSupportAllowance;	}

	public void setEmploymentSupportAllowance(final Enum69 EmploymentSupportAllowance) {
		this.EmploymentSupportAllowance = EmploymentSupportAllowance;	}

	public Enum502 getPart_OccPenGrossAnnual() {
		return Part_OccPenGrossAnnual;	}

	public void setPart_OccPenGrossAnnual(final Enum502 Part_OccPenGrossAnnual) {
		this.Part_OccPenGrossAnnual = Part_OccPenGrossAnnual;	}

	public Enum502 getHRP_OtherSourcesGrossAnnual() {
		return HRP_OtherSourcesGrossAnnual;	}

	public void setHRP_OtherSourcesGrossAnnual(final Enum502 HRP_OtherSourcesGrossAnnual) {
		this.HRP_OtherSourcesGrossAnnual = HRP_OtherSourcesGrossAnnual;	}

	public Enum502 getPart_OtherSourcesGrossAnnual() {
		return Part_OtherSourcesGrossAnnual;	}

	public void setPart_OtherSourcesGrossAnnual(final Enum502 Part_OtherSourcesGrossAnnual) {
		this.Part_OtherSourcesGrossAnnual = Part_OtherSourcesGrossAnnual;	}

	public Enum502 getHRP_OccPenNetAnnual() {
		return HRP_OccPenNetAnnual;	}

	public void setHRP_OccPenNetAnnual(final Enum502 HRP_OccPenNetAnnual) {
		this.HRP_OccPenNetAnnual = HRP_OccPenNetAnnual;	}

	public Enum69 getDLA_CareComponent() {
		return DLA_CareComponent;	}

	public void setDLA_CareComponent(final Enum69 DLA_CareComponent) {
		this.DLA_CareComponent = DLA_CareComponent;	}

	public Enum502 getHRP_GovtSchemes_LastTime() {
		return HRP_GovtSchemes_LastTime;	}

	public void setHRP_GovtSchemes_LastTime(final Enum502 HRP_GovtSchemes_LastTime) {
		this.HRP_GovtSchemes_LastTime = HRP_GovtSchemes_LastTime;	}

	public Enum69 getPenCAmtIncludesStatePen() {
		return PenCAmtIncludesStatePen;	}

	public void setPenCAmtIncludesStatePen(final Enum69 PenCAmtIncludesStatePen) {
		this.PenCAmtIncludesStatePen = PenCAmtIncludesStatePen;	}

	public Enum501 getWTCTimePeriod() {
		return WTCTimePeriod;	}

	public void setWTCTimePeriod(final Enum501 WTCTimePeriod) {
		this.WTCTimePeriod = WTCTimePeriod;	}

	public Enum501 getJSATimePeriod() {
		return JSATimePeriod;	}

	public void setJSATimePeriod(final Enum501 JSATimePeriod) {
		this.JSATimePeriod = JSATimePeriod;	}

	public Enum69 getWidows_WidowersPen() {
		return Widows_WidowersPen;	}

	public void setWidows_WidowersPen(final Enum69 Widows_WidowersPen) {
		this.Widows_WidowersPen = Widows_WidowersPen;	}

	public Enum501 getIncSup_MortInterestPeriod() {
		return IncSup_MortInterestPeriod;	}

	public void setIncSup_MortInterestPeriod(final Enum501 IncSup_MortInterestPeriod) {
		this.IncSup_MortInterestPeriod = IncSup_MortInterestPeriod;	}

	public Enum501 getWPTimePeriod() {
		return WPTimePeriod;	}

	public void setWPTimePeriod(final Enum501 WPTimePeriod) {
		this.WPTimePeriod = WPTimePeriod;	}

	public Enum69 getDLA_MobilityComponent() {
		return DLA_MobilityComponent;	}

	public void setDLA_MobilityComponent(final Enum69 DLA_MobilityComponent) {
		this.DLA_MobilityComponent = DLA_MobilityComponent;	}

	public Enum69 getIndustrialInjuriesDisablementBen() {
		return IndustrialInjuriesDisablementBen;	}

	public void setIndustrialInjuriesDisablementBen(final Enum69 IndustrialInjuriesDisablementBen) {
		this.IndustrialInjuriesDisablementBen = IndustrialInjuriesDisablementBen;	}

	public Enum501 getWDPTimePeriod() {
		return WDPTimePeriod;	}

	public void setWDPTimePeriod(final Enum501 WDPTimePeriod) {
		this.WDPTimePeriod = WDPTimePeriod;	}

	public Enum69 getChildBenefit() {
		return ChildBenefit;	}

	public void setChildBenefit(final Enum69 ChildBenefit) {
		this.ChildBenefit = ChildBenefit;	}

	public Enum69 getStatePenAmtIncludesPenC() {
		return StatePenAmtIncludesPenC;	}

	public void setStatePenAmtIncludesPenC(final Enum69 StatePenAmtIncludesPenC) {
		this.StatePenAmtIncludesPenC = StatePenAmtIncludesPenC;	}

	public Enum501 getHRPMaintenancePaymentPeriod() {
		return HRPMaintenancePaymentPeriod;	}

	public void setHRPMaintenancePaymentPeriod(final Enum501 HRPMaintenancePaymentPeriod) {
		this.HRPMaintenancePaymentPeriod = HRPMaintenancePaymentPeriod;	}

	public Enum550 getPart_AskedEarningsFromGov_Schemes() {
		return Part_AskedEarningsFromGov_Schemes;	}

	public void setPart_AskedEarningsFromGov_Schemes(final Enum550 Part_AskedEarningsFromGov_Schemes) {
		this.Part_AskedEarningsFromGov_Schemes = Part_AskedEarningsFromGov_Schemes;	}

	public Enum502 getPart_EmplGrossAnnualEarnings() {
		return Part_EmplGrossAnnualEarnings;	}

	public void setPart_EmplGrossAnnualEarnings(final Enum502 Part_EmplGrossAnnualEarnings) {
		this.Part_EmplGrossAnnualEarnings = Part_EmplGrossAnnualEarnings;	}

	public Enum69 getSevereDisablementAllow() {
		return SevereDisablementAllow;	}

	public void setSevereDisablementAllow(final Enum69 SevereDisablementAllow) {
		this.SevereDisablementAllow = SevereDisablementAllow;	}

	public Enum502 getHRP_OccPenGrossAnnual() {
		return HRP_OccPenGrossAnnual;	}

	public void setHRP_OccPenGrossAnnual(final Enum502 HRP_OccPenGrossAnnual) {
		this.HRP_OccPenGrossAnnual = HRP_OccPenGrossAnnual;	}

	public Enum554 getMortgageContributionUnderMortgagePaymentProtection() {
		return MortgageContributionUnderMortgagePaymentProtection;	}

	public void setMortgageContributionUnderMortgagePaymentProtection(final Enum554 MortgageContributionUnderMortgagePaymentProtection) {
		this.MortgageContributionUnderMortgagePaymentProtection = MortgageContributionUnderMortgagePaymentProtection;	}

	public Enum69 getNoneOfTheseStateBenefits() {
		return NoneOfTheseStateBenefits;	}

	public void setNoneOfTheseStateBenefits(final Enum69 NoneOfTheseStateBenefits) {
		this.NoneOfTheseStateBenefits = NoneOfTheseStateBenefits;	}

	public Enum69 getWhetherHRPPotentiallyAskedEarningsBlocks() {
		return WhetherHRPPotentiallyAskedEarningsBlocks;	}

	public void setWhetherHRPPotentiallyAskedEarningsBlocks(final Enum69 WhetherHRPPotentiallyAskedEarningsBlocks) {
		this.WhetherHRPPotentiallyAskedEarningsBlocks = WhetherHRPPotentiallyAskedEarningsBlocks;	}

	public Enum557 getIncSup_Part_FullMortInterest() {
		return IncSup_Part_FullMortInterest;	}

	public void setIncSup_Part_FullMortInterest(final Enum557 IncSup_Part_FullMortInterest) {
		this.IncSup_Part_FullMortInterest = IncSup_Part_FullMortInterest;	}

	public Enum502 getPart_SeGrossAnnualEarnings() {
		return Part_SeGrossAnnualEarnings;	}

	public void setPart_SeGrossAnnualEarnings(final Enum502 Part_SeGrossAnnualEarnings) {
		this.Part_SeGrossAnnualEarnings = Part_SeGrossAnnualEarnings;	}

	public Enum502 getHRP_EmplGrossAnnualEarnings() {
		return HRP_EmplGrossAnnualEarnings;	}

	public void setHRP_EmplGrossAnnualEarnings(final Enum502 HRP_EmplGrossAnnualEarnings) {
		this.HRP_EmplGrossAnnualEarnings = HRP_EmplGrossAnnualEarnings;	}

	public Enum69 getIncapacityBenefit() {
		return IncapacityBenefit;	}

	public void setIncapacityBenefit(final Enum69 IncapacityBenefit) {
		this.IncapacityBenefit = IncapacityBenefit;	}

	public Enum515 getPartnerLossOfGrossEarningsFromSelfEmployment() {
		return PartnerLossOfGrossEarningsFromSelfEmployment;	}

	public void setPartnerLossOfGrossEarningsFromSelfEmployment(final Enum515 PartnerLossOfGrossEarningsFromSelfEmployment) {
		this.PartnerLossOfGrossEarningsFromSelfEmployment = PartnerLossOfGrossEarningsFromSelfEmployment;	}

	public Enum502 getPart_PriPenGrossAnnual() {
		return Part_PriPenGrossAnnual;	}

	public void setPart_PriPenGrossAnnual(final Enum502 Part_PriPenGrossAnnual) {
		this.Part_PriPenGrossAnnual = Part_PriPenGrossAnnual;	}

	public Enum69 getStateBenefits_Refused() {
		return StateBenefits_Refused;	}

	public void setStateBenefits_Refused(final Enum69 StateBenefits_Refused) {
		this.StateBenefits_Refused = StateBenefits_Refused;	}

	public Enum69 getNIRetirement_OldAgePension() {
		return NIRetirement_OldAgePension;	}

	public void setNIRetirement_OldAgePension(final Enum69 NIRetirement_OldAgePension) {
		this.NIRetirement_OldAgePension = NIRetirement_OldAgePension;	}

	public Enum69 getCarersAllow_InvalidCareAllow_() {
		return CarersAllow_InvalidCareAllow_;	}

	public void setCarersAllow_InvalidCareAllow_(final Enum69 CarersAllow_InvalidCareAllow_) {
		this.CarersAllow_InvalidCareAllow_ = CarersAllow_InvalidCareAllow_;	}

	public Enum69 getChildTaxCredit() {
		return ChildTaxCredit;	}

	public void setChildTaxCredit(final Enum69 ChildTaxCredit) {
		this.ChildTaxCredit = ChildTaxCredit;	}

	public Enum230 getFieldworkQuarter() {
		return FieldworkQuarter;	}

	public void setFieldworkQuarter(final Enum230 FieldworkQuarter) {
		this.FieldworkQuarter = FieldworkQuarter;	}

	public Enum501 getSDATimePeriod() {
		return SDATimePeriod;	}

	public void setSDATimePeriod(final Enum501 SDATimePeriod) {
		this.SDATimePeriod = SDATimePeriod;	}

	public Enum501 getIDBTimePeriod() {
		return IDBTimePeriod;	}

	public void setIDBTimePeriod(final Enum501 IDBTimePeriod) {
		this.IDBTimePeriod = IDBTimePeriod;	}

	public Enum501 getESATimePeriod() {
		return ESATimePeriod;	}

	public void setESATimePeriod(final Enum501 ESATimePeriod) {
		this.ESATimePeriod = ESATimePeriod;	}

	public Enum515 getHRPLossOfGrossEarningsFromSelfEmployment() {
		return HRPLossOfGrossEarningsFromSelfEmployment;	}

	public void setHRPLossOfGrossEarningsFromSelfEmployment(final Enum515 HRPLossOfGrossEarningsFromSelfEmployment) {
		this.HRPLossOfGrossEarningsFromSelfEmployment = HRPLossOfGrossEarningsFromSelfEmployment;	}

	public Enum69 getStateBenefits_Don_TKnow() {
		return StateBenefits_Don_TKnow;	}

	public void setStateBenefits_Don_TKnow(final Enum69 StateBenefits_Don_TKnow) {
		this.StateBenefits_Don_TKnow = StateBenefits_Don_TKnow;	}

	public Enum502 getPart_GovtSchemes_LastTime() {
		return Part_GovtSchemes_LastTime;	}

	public void setPart_GovtSchemes_LastTime(final Enum502 Part_GovtSchemes_LastTime) {
		this.Part_GovtSchemes_LastTime = Part_GovtSchemes_LastTime;	}

	public Enum501 getMATimePeriod() {
		return MATimePeriod;	}

	public void setMATimePeriod(final Enum501 MATimePeriod) {
		this.MATimePeriod = MATimePeriod;	}

	public Enum30 getRegion_EHSOrder() {
		return Region_EHSOrder;	}

	public void setRegion_EHSOrder(final Enum30 Region_EHSOrder) {
		this.Region_EHSOrder = Region_EHSOrder;	}

	public Enum502 getPart_OccPenNetAnnual() {
		return Part_OccPenNetAnnual;	}

	public void setPart_OccPenNetAnnual(final Enum502 Part_OccPenNetAnnual) {
		this.Part_OccPenNetAnnual = Part_OccPenNetAnnual;	}

	public Enum502 getPart_PriPenNetAnnual() {
		return Part_PriPenNetAnnual;	}

	public void setPart_PriPenNetAnnual(final Enum502 Part_PriPenNetAnnual) {
		this.Part_PriPenNetAnnual = Part_PriPenNetAnnual;	}

	public Enum578 getESAGroup() {
		return ESAGroup;	}

	public void setESAGroup(final Enum578 ESAGroup) {
		this.ESAGroup = ESAGroup;	}

	public Enum69 getHRP_AskedEarningsFromGov_Schemes() {
		return HRP_AskedEarningsFromGov_Schemes;	}

	public void setHRP_AskedEarningsFromGov_Schemes(final Enum69 HRP_AskedEarningsFromGov_Schemes) {
		this.HRP_AskedEarningsFromGov_Schemes = HRP_AskedEarningsFromGov_Schemes;	}

	public Enum502 getHRP_EmplNetAnnualEarnings() {
		return HRP_EmplNetAnnualEarnings;	}

	public void setHRP_EmplNetAnnualEarnings(final Enum502 HRP_EmplNetAnnualEarnings) {
		this.HRP_EmplNetAnnualEarnings = HRP_EmplNetAnnualEarnings;	}

	public Enum69 getWarDisablementPen() {
		return WarDisablementPen;	}

	public void setWarDisablementPen(final Enum69 WarDisablementPen) {
		this.WarDisablementPen = WarDisablementPen;	}

	public Enum69 getOtherDisabilityBenefit() {
		return OtherDisabilityBenefit;	}

	public void setOtherDisabilityBenefit(final Enum69 OtherDisabilityBenefit) {
		this.OtherDisabilityBenefit = OtherDisabilityBenefit;	}

	public Enum501 getCTCTimePeriod() {
		return CTCTimePeriod;	}

	public void setCTCTimePeriod(final Enum501 CTCTimePeriod) {
		this.CTCTimePeriod = CTCTimePeriod;	}

	public Enum557 getMPPIReceipts_All_PartOfMorg() {
		return MPPIReceipts_All_PartOfMorg;	}

	public void setMPPIReceipts_All_PartOfMorg(final Enum557 MPPIReceipts_All_PartOfMorg) {
		this.MPPIReceipts_All_PartOfMorg = MPPIReceipts_All_PartOfMorg;	}

	public Enum585 getAmountOfSavings_MoneyInvested() {
		return AmountOfSavings_MoneyInvested;	}

	public void setAmountOfSavings_MoneyInvested(final Enum585 AmountOfSavings_MoneyInvested) {
		this.AmountOfSavings_MoneyInvested = AmountOfSavings_MoneyInvested;	}

	public Enum550 getPart_SuperannuationContribs() {
		return Part_SuperannuationContribs;	}

	public void setPart_SuperannuationContribs(final Enum550 Part_SuperannuationContribs) {
		this.Part_SuperannuationContribs = Part_SuperannuationContribs;	}

	public Enum501 getOAPTimePeriod() {
		return OAPTimePeriod;	}

	public void setOAPTimePeriod(final Enum501 OAPTimePeriod) {
		this.OAPTimePeriod = OAPTimePeriod;	}

	public Enum502 getPart_SeNetAnnualEarnings() {
		return Part_SeNetAnnualEarnings;	}

	public void setPart_SeNetAnnualEarnings(final Enum502 Part_SeNetAnnualEarnings) {
		this.Part_SeNetAnnualEarnings = Part_SeNetAnnualEarnings;	}

	public Enum501 getIBTimePeriod() {
		return IBTimePeriod;	}

	public void setIBTimePeriod(final Enum501 IBTimePeriod) {
		this.IBTimePeriod = IBTimePeriod;	}

	public Enum590 getWhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions() {
		return WhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions;	}

	public void setWhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions(final Enum590 WhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions) {
		this.WhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions = WhichBandRepresentsTheTotalIncomeOfHouseholdBeforeDeductions;	}

	public Enum69 getAttendanceAllow() {
		return AttendanceAllow;	}

	public void setAttendanceAllow(final Enum69 AttendanceAllow) {
		this.AttendanceAllow = AttendanceAllow;	}

	public Enum69 getMatAllowance() {
		return MatAllowance;	}

	public void setMatAllowance(final Enum69 MatAllowance) {
		this.MatAllowance = MatAllowance;	}

	public Enum229 getStatusOfCase() {
		return StatusOfCase;	}

	public void setStatusOfCase(final Enum229 StatusOfCase) {
		this.StatusOfCase = StatusOfCase;	}

	public Enum501 getIncSupTimePeriod() {
		return IncSupTimePeriod;	}

	public void setIncSupTimePeriod(final Enum501 IncSupTimePeriod) {
		this.IncSupTimePeriod = IncSupTimePeriod;	}

	public Enum502 getHRP_SeGrossAnnualEarnings() {
		return HRP_SeGrossAnnualEarnings;	}

	public void setHRP_SeGrossAnnualEarnings(final Enum502 HRP_SeGrossAnnualEarnings) {
		this.HRP_SeGrossAnnualEarnings = HRP_SeGrossAnnualEarnings;	}

	public Enum501 getSSPTimePeriod() {
		return SSPTimePeriod;	}

	public void setSSPTimePeriod(final Enum501 SSPTimePeriod) {
		this.SSPTimePeriod = SSPTimePeriod;	}

	public Enum69 getHRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership() {
		return HRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership;	}

	public void setHRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership(final Enum69 HRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership) {
		this.HRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership = HRPMakingRegularMaintenancePaymentsFromFormerMarriage_Partnership;	}

	public Enum69 getIncSup_MortInterestHelp() {
		return IncSup_MortInterestHelp;	}

	public void setIncSup_MortInterestHelp(final Enum69 IncSup_MortInterestHelp) {
		this.IncSup_MortInterestHelp = IncSup_MortInterestHelp;	}

}

