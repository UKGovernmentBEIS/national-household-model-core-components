package uk.org.cse.nhm.ehcs10.interview.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.derived.types.Enum73;
import uk.org.cse.nhm.ehcs10.interview.OwnerEntry;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class OwnerEntryImpl extends SurveyEntryImpl implements OwnerEntry {
	private Integer PurchasePriceOfHome;
	private Integer AmountBorrowedInAdditionToMainMortgage;
	private Integer TotalGroundRent;
	private Integer AmountOutstandingOnMainMortgage_Loan;
	private Integer UsualPaymentOnAllMortgages_Loans;
	private Integer Householder_SViewOnPropertyValue;
	private Integer PropertyValuedAt;
	private Integer PercentageEquityShareNow;
	private Integer Deposit_In_S_;
	private Integer TotalServiceCharge;
	private Integer PercentageEquityShareAtTimeOfPurchase;
	private Integer Deposit_AsA_;
	private Integer LengthOfMortgage_Years;
	private Integer AmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage;
	private Integer FlexibleMortgage_AverageMonthlyPayment;
	private Integer AcquiredHome_Year;
	private Integer YearCurrentMortgageTakenOut;
	private Integer CurrentMortgage_OriginalAmount;
	private Double CurrentInterestRateDeal;
	private Double Spouse_PartnerLeft_Died;
	private Double ServiceChargeWeeklyAmount;
	private Double Spouse_PartnerDied;
	private Double CurrentInterestRateDeal_INTTYPE3;
	private Double AmountOutstandingOnOtherLoans;
	private Double HowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months;
	private Double AmountOutstandingOnOtherLoans_V149_A;
	private Double GroundRentWeeklyAmount;
	private Double ByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear;
	private Double ByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months;
	private Double Spouse_PartnerLeftHome;
	private Enum69 SameHoursForLessPay;
	private Enum230 FieldworkQuarter;
	private Enum69 Repay_NoneOfThese;
	private Enum602 ChangedTypeOfMortgageOnTheProperty;
	private Enum69 MoneyFromPrivateLandlordToEncourageMove;
	private Enum69 NoActionTaken;
	private Enum69 PaymentsIntoPensionMortgage;
	private Enum69 StartedBusiness;
	private Enum69 PaymentsIntoOtherSavings_InvestmentScheme;
	private Enum608 ExtentOfFreehold;
	private Enum69 All_In_OneAccountMortgage;
	private Enum69 AcquiredAsAGift;
	private Enum611 PropertyBoughtFrom;
	private Enum69 ContributorLeftHome;
	private Enum69 NoneOfThese;
	private Enum614 HowMortgagePaid_1StOptionThatApplies_;
	private Enum69 SpentEquity_Other;
	private Enum616 MortgagePaymentPeriod;
	private Enum69 Don_TKnow;
	private Enum69 PaymentsOnRepaymentMortgage;
	private Enum69 HaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy;
	private Enum69 PaymentsIntoPensionMortgage_REPAPNSN;
	private Enum69 NewGoodsForTheProperty;
	private Enum622 AmountOfMortgageInterestPaidByDWP;
	private Enum73 OwnershipType;
	private Enum69 InterestOnlyPaymentsAgreed;
	private Enum69 MortgageProtectionPlanInsufficient;
	private Enum69 Windfall;
	private Enum69 NoAgreementMade;
	private Enum69 LoanForDeposit_BridgingLoan;
	private Enum69 OneOrMoreRegularPaymentsMissed;
	private Enum630 ComplexTenureStatus;
	private Enum631 AnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months;
	private Enum69 HomeImprovements_Renovations;
	private Enum633 CurrentAccountOrOffsetMortgage;
	private Enum69 HaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP;
	private Enum635 AcquiredHome_Month;
	private Enum69 IsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome;
	private Enum69 MovedToNewLender;
	private Enum69 ContributorPregnant_Baby;
	private Enum69 Gift_LoanFromFamily_Friend;
	private Enum69 NoneOfThese_ONNONE;
	private Enum641 CurrentMortgageRepaymentSituation;
	private Enum69 AreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing;
	private Enum69 PaymentsIntoAnEndowmentPolicy;
	private Enum644 FreeholdOwnedBy;
	private Enum69 MortgageHolidayAgreed;
	private Enum69 PaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS;
	private Enum69 PaymentsIntoUnit_InvestmentTrust;
	private Enum69 PaymentsIntoPep_ISA;
	private Enum649 WhatDoesTheInsurancePolicyCover;
	private Enum69 SaleOfPreviousHome;
	private Enum651 NumberOfMortgages_LoansOnProperty;
	private Enum69 BuildingInsurance;
	private Enum653 OriginalCashPayment_Pounds_Percent;
	private Enum69 Redundancy_Unemployment;
	private Enum69 OtherReason;
	private Enum69 AnyEarlierMortgagePaymentsOutstanding;
	private Enum69 MortgageAcctShowsBehind_NotTrue;
	private Enum69 _100_Mortgage;
	private Enum69 Acquired_Other;
	private Enum660 EverCouncil_HAHouse_Flat;
	private Enum69 ContentsInsurance;
	private Enum229 StatusOfCase;
	private Enum69 PaymentsIntoUnit_InvestmentTrust_RENDTRST;
	private Enum69 Invested_SavedTheMoney;
	private Enum69 LostOvertime_WorkedReducedHours;
	private Enum69 LostEarningsFromSickness_Injury;
	private Enum69 ExtensionToLoanPeriodAgreed;
	private Enum69 InheritedMoney;
	private Enum69 OtherSource;
	private Enum69 BoughtCar_OtherVehicle;
	private Enum69 SpentEquity_Don_TKnowHow;
	private Enum69 ServiceChargePaid;
	private Enum69 ReducedMonthlyPaymentAgreed;
	private Enum69 BoughtWithMortgage_Loan_S_;
	private Enum69 PaysGroundRent;
	private Enum69 DiscussionsWithLender;
	private Enum69 Savings;
	private Enum69 WereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears;
	private Enum69 MortgageProtectionPlan;
	private Enum69 MoneyPaidByLA_HAToEncourageMove;
	private Enum69 PaidForHoliday;
	private Enum69 ContributesToOne_OffRepair_MaintenanceCosts;
	private Enum69 PaidForUniversityCosts;
	private Enum69 PayingOffDebts;
	private Enum69 ValuedInLast12Months;
	private Enum686 HowAreYouCopingWithPaymentsAtTheMoment;
	private Enum687 InterestOnly_ExpectedRepaymentMethod;
	private Enum69 EndowmentPolicyPremium;
	private Enum689 CommonholdType;
	private Enum690 HowLongAgoDidYouFirstFallBehindWithRepayments;
	private Enum691 TypeOfMortgage_Loan;
	private Enum69 InheritedProperty;
	private Enum693 WhetherOutstandingMortgage_Loan;
	private Enum69 PaymentsIntoPEP_ISA;
	private Enum69 AreYouConsideringContactingYourLenderForHelp;
	private Enum69 SaleOfExistingHouseOnly;
	private Enum697 NameInWhichFreeholdHeld;
	private Enum69 OnlyPartOfRegularMorgagePaid;
	private Enum69 BoughtWithCash_PaidOutright;
	private Enum69 PaidForSchoolFees;
	private Enum701 LengthOfRemainingLease;
	private Enum69 PurchaseOfAnotherPropertyAbroad;
	private Enum69 SelfEmployedIncomeReduced;
	private Enum704 FreeholdOwnership;
	private Enum69 PaidForMedicalFees_NursingHome;
	private Enum69 HaveYouBeenAbleToPayTheRestOfTheMortgagePayment;
	private Enum30 Region_EHSOrder;
	private Enum69 GivenPropertyInDivorceSettlement;
	private Enum69 PurchaseOfPropertyForFamilyMember;
	private Enum701 LengthOfFullLeaseOnThisProperty;
	private Enum69 OtherPaymentsIncreased;
	private Enum69 PurchaseOfAnotherPropertyInUK;
	private Enum501 ServiceChargePeriod;
	private Enum69 RepaymentOfArrears;
	private Enum69 Endowment_NoneOfThese;
	private Enum501 GroundRentPeriod;
	private Enum69 OtherPayment;
	private Enum69 MortgagePaymentsIncreased;
	public Integer getPurchasePriceOfHome() {
		return PurchasePriceOfHome;	}

	public void setPurchasePriceOfHome(final Integer PurchasePriceOfHome) {
		this.PurchasePriceOfHome = PurchasePriceOfHome;	}

	public Integer getAmountBorrowedInAdditionToMainMortgage() {
		return AmountBorrowedInAdditionToMainMortgage;	}

	public void setAmountBorrowedInAdditionToMainMortgage(final Integer AmountBorrowedInAdditionToMainMortgage) {
		this.AmountBorrowedInAdditionToMainMortgage = AmountBorrowedInAdditionToMainMortgage;	}

	public Integer getTotalGroundRent() {
		return TotalGroundRent;	}

	public void setTotalGroundRent(final Integer TotalGroundRent) {
		this.TotalGroundRent = TotalGroundRent;	}

	public Integer getAmountOutstandingOnMainMortgage_Loan() {
		return AmountOutstandingOnMainMortgage_Loan;	}

	public void setAmountOutstandingOnMainMortgage_Loan(final Integer AmountOutstandingOnMainMortgage_Loan) {
		this.AmountOutstandingOnMainMortgage_Loan = AmountOutstandingOnMainMortgage_Loan;	}

	public Integer getUsualPaymentOnAllMortgages_Loans() {
		return UsualPaymentOnAllMortgages_Loans;	}

	public void setUsualPaymentOnAllMortgages_Loans(final Integer UsualPaymentOnAllMortgages_Loans) {
		this.UsualPaymentOnAllMortgages_Loans = UsualPaymentOnAllMortgages_Loans;	}

	public Integer getHouseholder_SViewOnPropertyValue() {
		return Householder_SViewOnPropertyValue;	}

	public void setHouseholder_SViewOnPropertyValue(final Integer Householder_SViewOnPropertyValue) {
		this.Householder_SViewOnPropertyValue = Householder_SViewOnPropertyValue;	}

	public Integer getPropertyValuedAt() {
		return PropertyValuedAt;	}

	public void setPropertyValuedAt(final Integer PropertyValuedAt) {
		this.PropertyValuedAt = PropertyValuedAt;	}

	public Integer getPercentageEquityShareNow() {
		return PercentageEquityShareNow;	}

	public void setPercentageEquityShareNow(final Integer PercentageEquityShareNow) {
		this.PercentageEquityShareNow = PercentageEquityShareNow;	}

	public Integer getDeposit_In_S_() {
		return Deposit_In_S_;	}

	public void setDeposit_In_S_(final Integer Deposit_In_S_) {
		this.Deposit_In_S_ = Deposit_In_S_;	}

	public Integer getTotalServiceCharge() {
		return TotalServiceCharge;	}

	public void setTotalServiceCharge(final Integer TotalServiceCharge) {
		this.TotalServiceCharge = TotalServiceCharge;	}

	public Integer getPercentageEquityShareAtTimeOfPurchase() {
		return PercentageEquityShareAtTimeOfPurchase;	}

	public void setPercentageEquityShareAtTimeOfPurchase(final Integer PercentageEquityShareAtTimeOfPurchase) {
		this.PercentageEquityShareAtTimeOfPurchase = PercentageEquityShareAtTimeOfPurchase;	}

	public Integer getDeposit_AsA_() {
		return Deposit_AsA_;	}

	public void setDeposit_AsA_(final Integer Deposit_AsA_) {
		this.Deposit_AsA_ = Deposit_AsA_;	}

	public Integer getLengthOfMortgage_Years() {
		return LengthOfMortgage_Years;	}

	public void setLengthOfMortgage_Years(final Integer LengthOfMortgage_Years) {
		this.LengthOfMortgage_Years = LengthOfMortgage_Years;	}

	public Integer getAmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage() {
		return AmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage;	}

	public void setAmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage(final Integer AmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage) {
		this.AmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage = AmountOfNegativeBalance_OverdraftOnCurrentA_CMortgage;	}

	public Integer getFlexibleMortgage_AverageMonthlyPayment() {
		return FlexibleMortgage_AverageMonthlyPayment;	}

	public void setFlexibleMortgage_AverageMonthlyPayment(final Integer FlexibleMortgage_AverageMonthlyPayment) {
		this.FlexibleMortgage_AverageMonthlyPayment = FlexibleMortgage_AverageMonthlyPayment;	}

	public Integer getAcquiredHome_Year() {
		return AcquiredHome_Year;	}

	public void setAcquiredHome_Year(final Integer AcquiredHome_Year) {
		this.AcquiredHome_Year = AcquiredHome_Year;	}

	public Integer getYearCurrentMortgageTakenOut() {
		return YearCurrentMortgageTakenOut;	}

	public void setYearCurrentMortgageTakenOut(final Integer YearCurrentMortgageTakenOut) {
		this.YearCurrentMortgageTakenOut = YearCurrentMortgageTakenOut;	}

	public Integer getCurrentMortgage_OriginalAmount() {
		return CurrentMortgage_OriginalAmount;	}

	public void setCurrentMortgage_OriginalAmount(final Integer CurrentMortgage_OriginalAmount) {
		this.CurrentMortgage_OriginalAmount = CurrentMortgage_OriginalAmount;	}

	public Double getCurrentInterestRateDeal() {
		return CurrentInterestRateDeal;	}

	public void setCurrentInterestRateDeal(final Double CurrentInterestRateDeal) {
		this.CurrentInterestRateDeal = CurrentInterestRateDeal;	}

	public Double getSpouse_PartnerLeft_Died() {
		return Spouse_PartnerLeft_Died;	}

	public void setSpouse_PartnerLeft_Died(final Double Spouse_PartnerLeft_Died) {
		this.Spouse_PartnerLeft_Died = Spouse_PartnerLeft_Died;	}

	public Double getServiceChargeWeeklyAmount() {
		return ServiceChargeWeeklyAmount;	}

	public void setServiceChargeWeeklyAmount(final Double ServiceChargeWeeklyAmount) {
		this.ServiceChargeWeeklyAmount = ServiceChargeWeeklyAmount;	}

	public Double getSpouse_PartnerDied() {
		return Spouse_PartnerDied;	}

	public void setSpouse_PartnerDied(final Double Spouse_PartnerDied) {
		this.Spouse_PartnerDied = Spouse_PartnerDied;	}

	public Double getCurrentInterestRateDeal_INTTYPE3() {
		return CurrentInterestRateDeal_INTTYPE3;	}

	public void setCurrentInterestRateDeal_INTTYPE3(final Double CurrentInterestRateDeal_INTTYPE3) {
		this.CurrentInterestRateDeal_INTTYPE3 = CurrentInterestRateDeal_INTTYPE3;	}

	public Double getAmountOutstandingOnOtherLoans() {
		return AmountOutstandingOnOtherLoans;	}

	public void setAmountOutstandingOnOtherLoans(final Double AmountOutstandingOnOtherLoans) {
		this.AmountOutstandingOnOtherLoans = AmountOutstandingOnOtherLoans;	}

	public Double getHowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months() {
		return HowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months;	}

	public void setHowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months(final Double HowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months) {
		this.HowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months = HowDoYouExpectTheInterestRateForTheMortgageOnYourHomeToChangeOverTheNext12Months;	}

	public Double getAmountOutstandingOnOtherLoans_V149_A() {
		return AmountOutstandingOnOtherLoans_V149_A;	}

	public void setAmountOutstandingOnOtherLoans_V149_A(final Double AmountOutstandingOnOtherLoans_V149_A) {
		this.AmountOutstandingOnOtherLoans_V149_A = AmountOutstandingOnOtherLoans_V149_A;	}

	public Double getGroundRentWeeklyAmount() {
		return GroundRentWeeklyAmount;	}

	public void setGroundRentWeeklyAmount(final Double GroundRentWeeklyAmount) {
		this.GroundRentWeeklyAmount = GroundRentWeeklyAmount;	}

	public Double getByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear() {
		return ByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear;	}

	public void setByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear(final Double ByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear) {
		this.ByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear = ByHowMuchDoYouThinkYourHomeHasChangedInValueOverThePastYear;	}

	public Double getByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months() {
		return ByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months;	}

	public void setByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months(final Double ByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months) {
		this.ByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months = ByHowMuchDoYouThinkYourHomeWillChangeInValueOverTheNext12Months;	}

	public Double getSpouse_PartnerLeftHome() {
		return Spouse_PartnerLeftHome;	}

	public void setSpouse_PartnerLeftHome(final Double Spouse_PartnerLeftHome) {
		this.Spouse_PartnerLeftHome = Spouse_PartnerLeftHome;	}

	public Enum69 getSameHoursForLessPay() {
		return SameHoursForLessPay;	}

	public void setSameHoursForLessPay(final Enum69 SameHoursForLessPay) {
		this.SameHoursForLessPay = SameHoursForLessPay;	}

	public Enum230 getFieldworkQuarter() {
		return FieldworkQuarter;	}

	public void setFieldworkQuarter(final Enum230 FieldworkQuarter) {
		this.FieldworkQuarter = FieldworkQuarter;	}

	public Enum69 getRepay_NoneOfThese() {
		return Repay_NoneOfThese;	}

	public void setRepay_NoneOfThese(final Enum69 Repay_NoneOfThese) {
		this.Repay_NoneOfThese = Repay_NoneOfThese;	}

	public Enum602 getChangedTypeOfMortgageOnTheProperty() {
		return ChangedTypeOfMortgageOnTheProperty;	}

	public void setChangedTypeOfMortgageOnTheProperty(final Enum602 ChangedTypeOfMortgageOnTheProperty) {
		this.ChangedTypeOfMortgageOnTheProperty = ChangedTypeOfMortgageOnTheProperty;	}

	public Enum69 getMoneyFromPrivateLandlordToEncourageMove() {
		return MoneyFromPrivateLandlordToEncourageMove;	}

	public void setMoneyFromPrivateLandlordToEncourageMove(final Enum69 MoneyFromPrivateLandlordToEncourageMove) {
		this.MoneyFromPrivateLandlordToEncourageMove = MoneyFromPrivateLandlordToEncourageMove;	}

	public Enum69 getNoActionTaken() {
		return NoActionTaken;	}

	public void setNoActionTaken(final Enum69 NoActionTaken) {
		this.NoActionTaken = NoActionTaken;	}

	public Enum69 getPaymentsIntoPensionMortgage() {
		return PaymentsIntoPensionMortgage;	}

	public void setPaymentsIntoPensionMortgage(final Enum69 PaymentsIntoPensionMortgage) {
		this.PaymentsIntoPensionMortgage = PaymentsIntoPensionMortgage;	}

	public Enum69 getStartedBusiness() {
		return StartedBusiness;	}

	public void setStartedBusiness(final Enum69 StartedBusiness) {
		this.StartedBusiness = StartedBusiness;	}

	public Enum69 getPaymentsIntoOtherSavings_InvestmentScheme() {
		return PaymentsIntoOtherSavings_InvestmentScheme;	}

	public void setPaymentsIntoOtherSavings_InvestmentScheme(final Enum69 PaymentsIntoOtherSavings_InvestmentScheme) {
		this.PaymentsIntoOtherSavings_InvestmentScheme = PaymentsIntoOtherSavings_InvestmentScheme;	}

	public Enum608 getExtentOfFreehold() {
		return ExtentOfFreehold;	}

	public void setExtentOfFreehold(final Enum608 ExtentOfFreehold) {
		this.ExtentOfFreehold = ExtentOfFreehold;	}

	public Enum69 getAll_In_OneAccountMortgage() {
		return All_In_OneAccountMortgage;	}

	public void setAll_In_OneAccountMortgage(final Enum69 All_In_OneAccountMortgage) {
		this.All_In_OneAccountMortgage = All_In_OneAccountMortgage;	}

	public Enum69 getAcquiredAsAGift() {
		return AcquiredAsAGift;	}

	public void setAcquiredAsAGift(final Enum69 AcquiredAsAGift) {
		this.AcquiredAsAGift = AcquiredAsAGift;	}

	public Enum611 getPropertyBoughtFrom() {
		return PropertyBoughtFrom;	}

	public void setPropertyBoughtFrom(final Enum611 PropertyBoughtFrom) {
		this.PropertyBoughtFrom = PropertyBoughtFrom;	}

	public Enum69 getContributorLeftHome() {
		return ContributorLeftHome;	}

	public void setContributorLeftHome(final Enum69 ContributorLeftHome) {
		this.ContributorLeftHome = ContributorLeftHome;	}

	public Enum69 getNoneOfThese() {
		return NoneOfThese;	}

	public void setNoneOfThese(final Enum69 NoneOfThese) {
		this.NoneOfThese = NoneOfThese;	}

	public Enum614 getHowMortgagePaid_1StOptionThatApplies_() {
		return HowMortgagePaid_1StOptionThatApplies_;	}

	public void setHowMortgagePaid_1StOptionThatApplies_(final Enum614 HowMortgagePaid_1StOptionThatApplies_) {
		this.HowMortgagePaid_1StOptionThatApplies_ = HowMortgagePaid_1StOptionThatApplies_;	}

	public Enum69 getSpentEquity_Other() {
		return SpentEquity_Other;	}

	public void setSpentEquity_Other(final Enum69 SpentEquity_Other) {
		this.SpentEquity_Other = SpentEquity_Other;	}

	public Enum616 getMortgagePaymentPeriod() {
		return MortgagePaymentPeriod;	}

	public void setMortgagePaymentPeriod(final Enum616 MortgagePaymentPeriod) {
		this.MortgagePaymentPeriod = MortgagePaymentPeriod;	}

	public Enum69 getDon_TKnow() {
		return Don_TKnow;	}

	public void setDon_TKnow(final Enum69 Don_TKnow) {
		this.Don_TKnow = Don_TKnow;	}

	public Enum69 getPaymentsOnRepaymentMortgage() {
		return PaymentsOnRepaymentMortgage;	}

	public void setPaymentsOnRepaymentMortgage(final Enum69 PaymentsOnRepaymentMortgage) {
		this.PaymentsOnRepaymentMortgage = PaymentsOnRepaymentMortgage;	}

	public Enum69 getHaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy() {
		return HaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy;	}

	public void setHaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy(final Enum69 HaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy) {
		this.HaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy = HaveAnInsurancePolicyToPayMortgageInEventOfAccident_SicknessOrUnemployment_Redundancy;	}

	public Enum69 getPaymentsIntoPensionMortgage_REPAPNSN() {
		return PaymentsIntoPensionMortgage_REPAPNSN;	}

	public void setPaymentsIntoPensionMortgage_REPAPNSN(final Enum69 PaymentsIntoPensionMortgage_REPAPNSN) {
		this.PaymentsIntoPensionMortgage_REPAPNSN = PaymentsIntoPensionMortgage_REPAPNSN;	}

	public Enum69 getNewGoodsForTheProperty() {
		return NewGoodsForTheProperty;	}

	public void setNewGoodsForTheProperty(final Enum69 NewGoodsForTheProperty) {
		this.NewGoodsForTheProperty = NewGoodsForTheProperty;	}

	public Enum622 getAmountOfMortgageInterestPaidByDWP() {
		return AmountOfMortgageInterestPaidByDWP;	}

	public void setAmountOfMortgageInterestPaidByDWP(final Enum622 AmountOfMortgageInterestPaidByDWP) {
		this.AmountOfMortgageInterestPaidByDWP = AmountOfMortgageInterestPaidByDWP;	}

	public Enum73 getOwnershipType() {
		return OwnershipType;	}

	public void setOwnershipType(final Enum73 OwnershipType) {
		this.OwnershipType = OwnershipType;	}

	public Enum69 getInterestOnlyPaymentsAgreed() {
		return InterestOnlyPaymentsAgreed;	}

	public void setInterestOnlyPaymentsAgreed(final Enum69 InterestOnlyPaymentsAgreed) {
		this.InterestOnlyPaymentsAgreed = InterestOnlyPaymentsAgreed;	}

	public Enum69 getMortgageProtectionPlanInsufficient() {
		return MortgageProtectionPlanInsufficient;	}

	public void setMortgageProtectionPlanInsufficient(final Enum69 MortgageProtectionPlanInsufficient) {
		this.MortgageProtectionPlanInsufficient = MortgageProtectionPlanInsufficient;	}

	public Enum69 getWindfall() {
		return Windfall;	}

	public void setWindfall(final Enum69 Windfall) {
		this.Windfall = Windfall;	}

	public Enum69 getNoAgreementMade() {
		return NoAgreementMade;	}

	public void setNoAgreementMade(final Enum69 NoAgreementMade) {
		this.NoAgreementMade = NoAgreementMade;	}

	public Enum69 getLoanForDeposit_BridgingLoan() {
		return LoanForDeposit_BridgingLoan;	}

	public void setLoanForDeposit_BridgingLoan(final Enum69 LoanForDeposit_BridgingLoan) {
		this.LoanForDeposit_BridgingLoan = LoanForDeposit_BridgingLoan;	}

	public Enum69 getOneOrMoreRegularPaymentsMissed() {
		return OneOrMoreRegularPaymentsMissed;	}

	public void setOneOrMoreRegularPaymentsMissed(final Enum69 OneOrMoreRegularPaymentsMissed) {
		this.OneOrMoreRegularPaymentsMissed = OneOrMoreRegularPaymentsMissed;	}

	public Enum630 getComplexTenureStatus() {
		return ComplexTenureStatus;	}

	public void setComplexTenureStatus(final Enum630 ComplexTenureStatus) {
		this.ComplexTenureStatus = ComplexTenureStatus;	}

	public Enum631 getAnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months() {
		return AnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months;	}

	public void setAnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months(final Enum631 AnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months) {
		this.AnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months = AnyDifficultiesKeepingUpWithMortgagePaymentsInTheLast12Months;	}

	public Enum69 getHomeImprovements_Renovations() {
		return HomeImprovements_Renovations;	}

	public void setHomeImprovements_Renovations(final Enum69 HomeImprovements_Renovations) {
		this.HomeImprovements_Renovations = HomeImprovements_Renovations;	}

	public Enum633 getCurrentAccountOrOffsetMortgage() {
		return CurrentAccountOrOffsetMortgage;	}

	public void setCurrentAccountOrOffsetMortgage(final Enum633 CurrentAccountOrOffsetMortgage) {
		this.CurrentAccountOrOffsetMortgage = CurrentAccountOrOffsetMortgage;	}

	public Enum69 getHaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP() {
		return HaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP;	}

	public void setHaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP(final Enum69 HaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP) {
		this.HaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP = HaveYouBeenAbleToPayTheMortgageInterestNotPaidByDWP;	}

	public Enum635 getAcquiredHome_Month() {
		return AcquiredHome_Month;	}

	public void setAcquiredHome_Month(final Enum635 AcquiredHome_Month) {
		this.AcquiredHome_Month = AcquiredHome_Month;	}

	public Enum69 getIsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome() {
		return IsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome;	}

	public void setIsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome(final Enum69 IsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome) {
		this.IsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome = IsYourMortgageLenderCurrentlyTakingLegalActionToRepossessYourHome;	}

	public Enum69 getMovedToNewLender() {
		return MovedToNewLender;	}

	public void setMovedToNewLender(final Enum69 MovedToNewLender) {
		this.MovedToNewLender = MovedToNewLender;	}

	public Enum69 getContributorPregnant_Baby() {
		return ContributorPregnant_Baby;	}

	public void setContributorPregnant_Baby(final Enum69 ContributorPregnant_Baby) {
		this.ContributorPregnant_Baby = ContributorPregnant_Baby;	}

	public Enum69 getGift_LoanFromFamily_Friend() {
		return Gift_LoanFromFamily_Friend;	}

	public void setGift_LoanFromFamily_Friend(final Enum69 Gift_LoanFromFamily_Friend) {
		this.Gift_LoanFromFamily_Friend = Gift_LoanFromFamily_Friend;	}

	public Enum69 getNoneOfThese_ONNONE() {
		return NoneOfThese_ONNONE;	}

	public void setNoneOfThese_ONNONE(final Enum69 NoneOfThese_ONNONE) {
		this.NoneOfThese_ONNONE = NoneOfThese_ONNONE;	}

	public Enum641 getCurrentMortgageRepaymentSituation() {
		return CurrentMortgageRepaymentSituation;	}

	public void setCurrentMortgageRepaymentSituation(final Enum641 CurrentMortgageRepaymentSituation) {
		this.CurrentMortgageRepaymentSituation = CurrentMortgageRepaymentSituation;	}

	public Enum69 getAreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing() {
		return AreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing;	}

	public void setAreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing(final Enum69 AreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing) {
		this.AreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing = AreThereAnyMortgagePaymentsOutstandingFromBeforeDWPStartedContributing;	}

	public Enum69 getPaymentsIntoAnEndowmentPolicy() {
		return PaymentsIntoAnEndowmentPolicy;	}

	public void setPaymentsIntoAnEndowmentPolicy(final Enum69 PaymentsIntoAnEndowmentPolicy) {
		this.PaymentsIntoAnEndowmentPolicy = PaymentsIntoAnEndowmentPolicy;	}

	public Enum644 getFreeholdOwnedBy() {
		return FreeholdOwnedBy;	}

	public void setFreeholdOwnedBy(final Enum644 FreeholdOwnedBy) {
		this.FreeholdOwnedBy = FreeholdOwnedBy;	}

	public Enum69 getMortgageHolidayAgreed() {
		return MortgageHolidayAgreed;	}

	public void setMortgageHolidayAgreed(final Enum69 MortgageHolidayAgreed) {
		this.MortgageHolidayAgreed = MortgageHolidayAgreed;	}

	public Enum69 getPaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS() {
		return PaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS;	}

	public void setPaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS(final Enum69 PaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS) {
		this.PaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS = PaymentsIntoOtherSavings_InvestmentScheme_RENDSVGS;	}

	public Enum69 getPaymentsIntoUnit_InvestmentTrust() {
		return PaymentsIntoUnit_InvestmentTrust;	}

	public void setPaymentsIntoUnit_InvestmentTrust(final Enum69 PaymentsIntoUnit_InvestmentTrust) {
		this.PaymentsIntoUnit_InvestmentTrust = PaymentsIntoUnit_InvestmentTrust;	}

	public Enum69 getPaymentsIntoPep_ISA() {
		return PaymentsIntoPep_ISA;	}

	public void setPaymentsIntoPep_ISA(final Enum69 PaymentsIntoPep_ISA) {
		this.PaymentsIntoPep_ISA = PaymentsIntoPep_ISA;	}

	public Enum649 getWhatDoesTheInsurancePolicyCover() {
		return WhatDoesTheInsurancePolicyCover;	}

	public void setWhatDoesTheInsurancePolicyCover(final Enum649 WhatDoesTheInsurancePolicyCover) {
		this.WhatDoesTheInsurancePolicyCover = WhatDoesTheInsurancePolicyCover;	}

	public Enum69 getSaleOfPreviousHome() {
		return SaleOfPreviousHome;	}

	public void setSaleOfPreviousHome(final Enum69 SaleOfPreviousHome) {
		this.SaleOfPreviousHome = SaleOfPreviousHome;	}

	public Enum651 getNumberOfMortgages_LoansOnProperty() {
		return NumberOfMortgages_LoansOnProperty;	}

	public void setNumberOfMortgages_LoansOnProperty(final Enum651 NumberOfMortgages_LoansOnProperty) {
		this.NumberOfMortgages_LoansOnProperty = NumberOfMortgages_LoansOnProperty;	}

	public Enum69 getBuildingInsurance() {
		return BuildingInsurance;	}

	public void setBuildingInsurance(final Enum69 BuildingInsurance) {
		this.BuildingInsurance = BuildingInsurance;	}

	public Enum653 getOriginalCashPayment_Pounds_Percent() {
		return OriginalCashPayment_Pounds_Percent;	}

	public void setOriginalCashPayment_Pounds_Percent(final Enum653 OriginalCashPayment_Pounds_Percent) {
		this.OriginalCashPayment_Pounds_Percent = OriginalCashPayment_Pounds_Percent;	}

	public Enum69 getRedundancy_Unemployment() {
		return Redundancy_Unemployment;	}

	public void setRedundancy_Unemployment(final Enum69 Redundancy_Unemployment) {
		this.Redundancy_Unemployment = Redundancy_Unemployment;	}

	public Enum69 getOtherReason() {
		return OtherReason;	}

	public void setOtherReason(final Enum69 OtherReason) {
		this.OtherReason = OtherReason;	}

	public Enum69 getAnyEarlierMortgagePaymentsOutstanding() {
		return AnyEarlierMortgagePaymentsOutstanding;	}

	public void setAnyEarlierMortgagePaymentsOutstanding(final Enum69 AnyEarlierMortgagePaymentsOutstanding) {
		this.AnyEarlierMortgagePaymentsOutstanding = AnyEarlierMortgagePaymentsOutstanding;	}

	public Enum69 getMortgageAcctShowsBehind_NotTrue() {
		return MortgageAcctShowsBehind_NotTrue;	}

	public void setMortgageAcctShowsBehind_NotTrue(final Enum69 MortgageAcctShowsBehind_NotTrue) {
		this.MortgageAcctShowsBehind_NotTrue = MortgageAcctShowsBehind_NotTrue;	}

	public Enum69 get_100_Mortgage() {
		return _100_Mortgage;	}

	public void set_100_Mortgage(final Enum69 _100_Mortgage) {
		this._100_Mortgage = _100_Mortgage;	}

	public Enum69 getAcquired_Other() {
		return Acquired_Other;	}

	public void setAcquired_Other(final Enum69 Acquired_Other) {
		this.Acquired_Other = Acquired_Other;	}

	public Enum660 getEverCouncil_HAHouse_Flat() {
		return EverCouncil_HAHouse_Flat;	}

	public void setEverCouncil_HAHouse_Flat(final Enum660 EverCouncil_HAHouse_Flat) {
		this.EverCouncil_HAHouse_Flat = EverCouncil_HAHouse_Flat;	}

	public Enum69 getContentsInsurance() {
		return ContentsInsurance;	}

	public void setContentsInsurance(final Enum69 ContentsInsurance) {
		this.ContentsInsurance = ContentsInsurance;	}

	public Enum229 getStatusOfCase() {
		return StatusOfCase;	}

	public void setStatusOfCase(final Enum229 StatusOfCase) {
		this.StatusOfCase = StatusOfCase;	}

	public Enum69 getPaymentsIntoUnit_InvestmentTrust_RENDTRST() {
		return PaymentsIntoUnit_InvestmentTrust_RENDTRST;	}

	public void setPaymentsIntoUnit_InvestmentTrust_RENDTRST(final Enum69 PaymentsIntoUnit_InvestmentTrust_RENDTRST) {
		this.PaymentsIntoUnit_InvestmentTrust_RENDTRST = PaymentsIntoUnit_InvestmentTrust_RENDTRST;	}

	public Enum69 getInvested_SavedTheMoney() {
		return Invested_SavedTheMoney;	}

	public void setInvested_SavedTheMoney(final Enum69 Invested_SavedTheMoney) {
		this.Invested_SavedTheMoney = Invested_SavedTheMoney;	}

	public Enum69 getLostOvertime_WorkedReducedHours() {
		return LostOvertime_WorkedReducedHours;	}

	public void setLostOvertime_WorkedReducedHours(final Enum69 LostOvertime_WorkedReducedHours) {
		this.LostOvertime_WorkedReducedHours = LostOvertime_WorkedReducedHours;	}

	public Enum69 getLostEarningsFromSickness_Injury() {
		return LostEarningsFromSickness_Injury;	}

	public void setLostEarningsFromSickness_Injury(final Enum69 LostEarningsFromSickness_Injury) {
		this.LostEarningsFromSickness_Injury = LostEarningsFromSickness_Injury;	}

	public Enum69 getExtensionToLoanPeriodAgreed() {
		return ExtensionToLoanPeriodAgreed;	}

	public void setExtensionToLoanPeriodAgreed(final Enum69 ExtensionToLoanPeriodAgreed) {
		this.ExtensionToLoanPeriodAgreed = ExtensionToLoanPeriodAgreed;	}

	public Enum69 getInheritedMoney() {
		return InheritedMoney;	}

	public void setInheritedMoney(final Enum69 InheritedMoney) {
		this.InheritedMoney = InheritedMoney;	}

	public Enum69 getOtherSource() {
		return OtherSource;	}

	public void setOtherSource(final Enum69 OtherSource) {
		this.OtherSource = OtherSource;	}

	public Enum69 getBoughtCar_OtherVehicle() {
		return BoughtCar_OtherVehicle;	}

	public void setBoughtCar_OtherVehicle(final Enum69 BoughtCar_OtherVehicle) {
		this.BoughtCar_OtherVehicle = BoughtCar_OtherVehicle;	}

	public Enum69 getSpentEquity_Don_TKnowHow() {
		return SpentEquity_Don_TKnowHow;	}

	public void setSpentEquity_Don_TKnowHow(final Enum69 SpentEquity_Don_TKnowHow) {
		this.SpentEquity_Don_TKnowHow = SpentEquity_Don_TKnowHow;	}

	public Enum69 getServiceChargePaid() {
		return ServiceChargePaid;	}

	public void setServiceChargePaid(final Enum69 ServiceChargePaid) {
		this.ServiceChargePaid = ServiceChargePaid;	}

	public Enum69 getReducedMonthlyPaymentAgreed() {
		return ReducedMonthlyPaymentAgreed;	}

	public void setReducedMonthlyPaymentAgreed(final Enum69 ReducedMonthlyPaymentAgreed) {
		this.ReducedMonthlyPaymentAgreed = ReducedMonthlyPaymentAgreed;	}

	public Enum69 getBoughtWithMortgage_Loan_S_() {
		return BoughtWithMortgage_Loan_S_;	}

	public void setBoughtWithMortgage_Loan_S_(final Enum69 BoughtWithMortgage_Loan_S_) {
		this.BoughtWithMortgage_Loan_S_ = BoughtWithMortgage_Loan_S_;	}

	public Enum69 getPaysGroundRent() {
		return PaysGroundRent;	}

	public void setPaysGroundRent(final Enum69 PaysGroundRent) {
		this.PaysGroundRent = PaysGroundRent;	}

	public Enum69 getDiscussionsWithLender() {
		return DiscussionsWithLender;	}

	public void setDiscussionsWithLender(final Enum69 DiscussionsWithLender) {
		this.DiscussionsWithLender = DiscussionsWithLender;	}

	public Enum69 getSavings() {
		return Savings;	}

	public void setSavings(final Enum69 Savings) {
		this.Savings = Savings;	}

	public Enum69 getWereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears() {
		return WereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears;	}

	public void setWereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears(final Enum69 WereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears) {
		this.WereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears = WereYouBehindWithPaymentsForThisPropertyBeforeThePresentArrears;	}

	public Enum69 getMortgageProtectionPlan() {
		return MortgageProtectionPlan;	}

	public void setMortgageProtectionPlan(final Enum69 MortgageProtectionPlan) {
		this.MortgageProtectionPlan = MortgageProtectionPlan;	}

	public Enum69 getMoneyPaidByLA_HAToEncourageMove() {
		return MoneyPaidByLA_HAToEncourageMove;	}

	public void setMoneyPaidByLA_HAToEncourageMove(final Enum69 MoneyPaidByLA_HAToEncourageMove) {
		this.MoneyPaidByLA_HAToEncourageMove = MoneyPaidByLA_HAToEncourageMove;	}

	public Enum69 getPaidForHoliday() {
		return PaidForHoliday;	}

	public void setPaidForHoliday(final Enum69 PaidForHoliday) {
		this.PaidForHoliday = PaidForHoliday;	}

	public Enum69 getContributesToOne_OffRepair_MaintenanceCosts() {
		return ContributesToOne_OffRepair_MaintenanceCosts;	}

	public void setContributesToOne_OffRepair_MaintenanceCosts(final Enum69 ContributesToOne_OffRepair_MaintenanceCosts) {
		this.ContributesToOne_OffRepair_MaintenanceCosts = ContributesToOne_OffRepair_MaintenanceCosts;	}

	public Enum69 getPaidForUniversityCosts() {
		return PaidForUniversityCosts;	}

	public void setPaidForUniversityCosts(final Enum69 PaidForUniversityCosts) {
		this.PaidForUniversityCosts = PaidForUniversityCosts;	}

	public Enum69 getPayingOffDebts() {
		return PayingOffDebts;	}

	public void setPayingOffDebts(final Enum69 PayingOffDebts) {
		this.PayingOffDebts = PayingOffDebts;	}

	public Enum69 getValuedInLast12Months() {
		return ValuedInLast12Months;	}

	public void setValuedInLast12Months(final Enum69 ValuedInLast12Months) {
		this.ValuedInLast12Months = ValuedInLast12Months;	}

	public Enum686 getHowAreYouCopingWithPaymentsAtTheMoment() {
		return HowAreYouCopingWithPaymentsAtTheMoment;	}

	public void setHowAreYouCopingWithPaymentsAtTheMoment(final Enum686 HowAreYouCopingWithPaymentsAtTheMoment) {
		this.HowAreYouCopingWithPaymentsAtTheMoment = HowAreYouCopingWithPaymentsAtTheMoment;	}

	public Enum687 getInterestOnly_ExpectedRepaymentMethod() {
		return InterestOnly_ExpectedRepaymentMethod;	}

	public void setInterestOnly_ExpectedRepaymentMethod(final Enum687 InterestOnly_ExpectedRepaymentMethod) {
		this.InterestOnly_ExpectedRepaymentMethod = InterestOnly_ExpectedRepaymentMethod;	}

	public Enum69 getEndowmentPolicyPremium() {
		return EndowmentPolicyPremium;	}

	public void setEndowmentPolicyPremium(final Enum69 EndowmentPolicyPremium) {
		this.EndowmentPolicyPremium = EndowmentPolicyPremium;	}

	public Enum689 getCommonholdType() {
		return CommonholdType;	}

	public void setCommonholdType(final Enum689 CommonholdType) {
		this.CommonholdType = CommonholdType;	}

	public Enum690 getHowLongAgoDidYouFirstFallBehindWithRepayments() {
		return HowLongAgoDidYouFirstFallBehindWithRepayments;	}

	public void setHowLongAgoDidYouFirstFallBehindWithRepayments(final Enum690 HowLongAgoDidYouFirstFallBehindWithRepayments) {
		this.HowLongAgoDidYouFirstFallBehindWithRepayments = HowLongAgoDidYouFirstFallBehindWithRepayments;	}

	public Enum691 getTypeOfMortgage_Loan() {
		return TypeOfMortgage_Loan;	}

	public void setTypeOfMortgage_Loan(final Enum691 TypeOfMortgage_Loan) {
		this.TypeOfMortgage_Loan = TypeOfMortgage_Loan;	}

	public Enum69 getInheritedProperty() {
		return InheritedProperty;	}

	public void setInheritedProperty(final Enum69 InheritedProperty) {
		this.InheritedProperty = InheritedProperty;	}

	public Enum693 getWhetherOutstandingMortgage_Loan() {
		return WhetherOutstandingMortgage_Loan;	}

	public void setWhetherOutstandingMortgage_Loan(final Enum693 WhetherOutstandingMortgage_Loan) {
		this.WhetherOutstandingMortgage_Loan = WhetherOutstandingMortgage_Loan;	}

	public Enum69 getPaymentsIntoPEP_ISA() {
		return PaymentsIntoPEP_ISA;	}

	public void setPaymentsIntoPEP_ISA(final Enum69 PaymentsIntoPEP_ISA) {
		this.PaymentsIntoPEP_ISA = PaymentsIntoPEP_ISA;	}

	public Enum69 getAreYouConsideringContactingYourLenderForHelp() {
		return AreYouConsideringContactingYourLenderForHelp;	}

	public void setAreYouConsideringContactingYourLenderForHelp(final Enum69 AreYouConsideringContactingYourLenderForHelp) {
		this.AreYouConsideringContactingYourLenderForHelp = AreYouConsideringContactingYourLenderForHelp;	}

	public Enum69 getSaleOfExistingHouseOnly() {
		return SaleOfExistingHouseOnly;	}

	public void setSaleOfExistingHouseOnly(final Enum69 SaleOfExistingHouseOnly) {
		this.SaleOfExistingHouseOnly = SaleOfExistingHouseOnly;	}

	public Enum697 getNameInWhichFreeholdHeld() {
		return NameInWhichFreeholdHeld;	}

	public void setNameInWhichFreeholdHeld(final Enum697 NameInWhichFreeholdHeld) {
		this.NameInWhichFreeholdHeld = NameInWhichFreeholdHeld;	}

	public Enum69 getOnlyPartOfRegularMorgagePaid() {
		return OnlyPartOfRegularMorgagePaid;	}

	public void setOnlyPartOfRegularMorgagePaid(final Enum69 OnlyPartOfRegularMorgagePaid) {
		this.OnlyPartOfRegularMorgagePaid = OnlyPartOfRegularMorgagePaid;	}

	public Enum69 getBoughtWithCash_PaidOutright() {
		return BoughtWithCash_PaidOutright;	}

	public void setBoughtWithCash_PaidOutright(final Enum69 BoughtWithCash_PaidOutright) {
		this.BoughtWithCash_PaidOutright = BoughtWithCash_PaidOutright;	}

	public Enum69 getPaidForSchoolFees() {
		return PaidForSchoolFees;	}

	public void setPaidForSchoolFees(final Enum69 PaidForSchoolFees) {
		this.PaidForSchoolFees = PaidForSchoolFees;	}

	public Enum701 getLengthOfRemainingLease() {
		return LengthOfRemainingLease;	}

	public void setLengthOfRemainingLease(final Enum701 LengthOfRemainingLease) {
		this.LengthOfRemainingLease = LengthOfRemainingLease;	}

	public Enum69 getPurchaseOfAnotherPropertyAbroad() {
		return PurchaseOfAnotherPropertyAbroad;	}

	public void setPurchaseOfAnotherPropertyAbroad(final Enum69 PurchaseOfAnotherPropertyAbroad) {
		this.PurchaseOfAnotherPropertyAbroad = PurchaseOfAnotherPropertyAbroad;	}

	public Enum69 getSelfEmployedIncomeReduced() {
		return SelfEmployedIncomeReduced;	}

	public void setSelfEmployedIncomeReduced(final Enum69 SelfEmployedIncomeReduced) {
		this.SelfEmployedIncomeReduced = SelfEmployedIncomeReduced;	}

	public Enum704 getFreeholdOwnership() {
		return FreeholdOwnership;	}

	public void setFreeholdOwnership(final Enum704 FreeholdOwnership) {
		this.FreeholdOwnership = FreeholdOwnership;	}

	public Enum69 getPaidForMedicalFees_NursingHome() {
		return PaidForMedicalFees_NursingHome;	}

	public void setPaidForMedicalFees_NursingHome(final Enum69 PaidForMedicalFees_NursingHome) {
		this.PaidForMedicalFees_NursingHome = PaidForMedicalFees_NursingHome;	}

	public Enum69 getHaveYouBeenAbleToPayTheRestOfTheMortgagePayment() {
		return HaveYouBeenAbleToPayTheRestOfTheMortgagePayment;	}

	public void setHaveYouBeenAbleToPayTheRestOfTheMortgagePayment(final Enum69 HaveYouBeenAbleToPayTheRestOfTheMortgagePayment) {
		this.HaveYouBeenAbleToPayTheRestOfTheMortgagePayment = HaveYouBeenAbleToPayTheRestOfTheMortgagePayment;	}

	public Enum30 getRegion_EHSOrder() {
		return Region_EHSOrder;	}

	public void setRegion_EHSOrder(final Enum30 Region_EHSOrder) {
		this.Region_EHSOrder = Region_EHSOrder;	}

	public Enum69 getGivenPropertyInDivorceSettlement() {
		return GivenPropertyInDivorceSettlement;	}

	public void setGivenPropertyInDivorceSettlement(final Enum69 GivenPropertyInDivorceSettlement) {
		this.GivenPropertyInDivorceSettlement = GivenPropertyInDivorceSettlement;	}

	public Enum69 getPurchaseOfPropertyForFamilyMember() {
		return PurchaseOfPropertyForFamilyMember;	}

	public void setPurchaseOfPropertyForFamilyMember(final Enum69 PurchaseOfPropertyForFamilyMember) {
		this.PurchaseOfPropertyForFamilyMember = PurchaseOfPropertyForFamilyMember;	}

	public Enum701 getLengthOfFullLeaseOnThisProperty() {
		return LengthOfFullLeaseOnThisProperty;	}

	public void setLengthOfFullLeaseOnThisProperty(final Enum701 LengthOfFullLeaseOnThisProperty) {
		this.LengthOfFullLeaseOnThisProperty = LengthOfFullLeaseOnThisProperty;	}

	public Enum69 getOtherPaymentsIncreased() {
		return OtherPaymentsIncreased;	}

	public void setOtherPaymentsIncreased(final Enum69 OtherPaymentsIncreased) {
		this.OtherPaymentsIncreased = OtherPaymentsIncreased;	}

	public Enum69 getPurchaseOfAnotherPropertyInUK() {
		return PurchaseOfAnotherPropertyInUK;	}

	public void setPurchaseOfAnotherPropertyInUK(final Enum69 PurchaseOfAnotherPropertyInUK) {
		this.PurchaseOfAnotherPropertyInUK = PurchaseOfAnotherPropertyInUK;	}

	public Enum501 getServiceChargePeriod() {
		return ServiceChargePeriod;	}

	public void setServiceChargePeriod(final Enum501 ServiceChargePeriod) {
		this.ServiceChargePeriod = ServiceChargePeriod;	}

	public Enum69 getRepaymentOfArrears() {
		return RepaymentOfArrears;	}

	public void setRepaymentOfArrears(final Enum69 RepaymentOfArrears) {
		this.RepaymentOfArrears = RepaymentOfArrears;	}

	public Enum69 getEndowment_NoneOfThese() {
		return Endowment_NoneOfThese;	}

	public void setEndowment_NoneOfThese(final Enum69 Endowment_NoneOfThese) {
		this.Endowment_NoneOfThese = Endowment_NoneOfThese;	}

	public Enum501 getGroundRentPeriod() {
		return GroundRentPeriod;	}

	public void setGroundRentPeriod(final Enum501 GroundRentPeriod) {
		this.GroundRentPeriod = GroundRentPeriod;	}

	public Enum69 getOtherPayment() {
		return OtherPayment;	}

	public void setOtherPayment(final Enum69 OtherPayment) {
		this.OtherPayment = OtherPayment;	}

	public Enum69 getMortgagePaymentsIncreased() {
		return MortgagePaymentsIncreased;	}

	public void setMortgagePaymentsIncreased(final Enum69 MortgagePaymentsIncreased) {
		this.MortgagePaymentsIncreased = MortgagePaymentsIncreased;	}

}

