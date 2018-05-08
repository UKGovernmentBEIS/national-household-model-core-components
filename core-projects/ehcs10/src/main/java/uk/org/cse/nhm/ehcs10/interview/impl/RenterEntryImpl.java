package uk.org.cse.nhm.ehcs10.interview.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.RenterEntry;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class RenterEntryImpl extends SurveyEntryImpl implements RenterEntry {

    private String ReasonForTakingOver_AquiringTenancy;
    private Integer StartOfPreviousTenancy;
    private Integer Tenants_PrtHB_FllAmtRentChrg;
    private Integer TotalWeeklyRent_PartHB_;
    private Integer Tenants_FllHB_AmtHB;
    private Integer ForHowManyWeeksDoYouHaveARentHoliday_;
    private Integer YearTenancyStarted;
    private Integer TenantsXsHB_AmtHBRcv;
    private Integer Tenants_UnsureHB_AmtRentChrg;
    private Integer Tenants_NoHB_AmtRentPd;
    private Integer TenantsXsHB_FllRentChk;
    private Integer RentExclServices_HB_LHA;
    private Integer WeeklyExcessBenefit;
    private Integer PrevTnncy_AmountOfFee;
    private Integer Tenants_DKHB_AmtRentChrg;
    private Integer Tenants_PrtHB_AmtHBRcv;
    private Integer YearBecameHATenant;
    private Integer WhenTenancyEnded;
    private Integer Tenants_UnsureHB_AmtRentPd;
    private Integer No_OfAgreementsWithinHhld;
    private Integer TotalAmountOfRent;
    private Integer Tenants_DKHB_AmtRentPd;
    private Integer TenantsXsHB_AmtRentPd;
    private Integer Tenants_PrtHB_AmtRentPd;
    private Double DepositWasHeldBy;
    private Double GuarantorRequired;
    private Double AdminFee_Non_Ret_;
    private Double HoldingFee_Non_Ret_;
    private Double FindersFee_Non_Ret_;
    private Double HoldingFee_Returnable_;
    private Double ThisTnncy_Landlord_AgencyFee;
    private Double IsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes;
    private Double ThisTnncy_AmountOfFee;
    private Double WasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes;
    private Double DepositBeingHeldBy;
    private Double OtherFee;
    private Double DepositAsProportionOfRent;
    private Enum813 TenancyType;
    private Enum69 WithLandlord_SAgreement;
    private Enum815 Rent_HBAmountsGivenForWholeAccommodationOrJustYourShare;
    private Enum69 WaterAndSewerage;
    private Enum817 Tenants_DKHB_RentChrgPer;
    private Enum817 TenantsXsHB_RentChrgPer;
    private Enum69 NoneOfThese;
    private Enum69 Heating;
    private Enum69 Tenants_PrtHB_FllRentChk;
    private Enum69 UnpaidBills;
    private Enum823 LengthOfWait;
    private Enum824 DepositReturned;
    private Enum69 NoReasonGiven;
    private Enum69 AnyRentCoveredByHB_LHA;
    private Enum827 AmountOfRentCovered;
    private Enum69 AcquiredTenancyWhenLivingWithSomeoneWhoWasATenant;
    private Enum630 ComplexRentingSituation;
    private Enum830 No_OfLodgerAgreements;
    private Enum831 TimeAtPropertyBeforeAcquiringTenancy;
    private Enum69 RentOwing;
    private Enum69 FallenBehindWithRentPaymentsOverTheLast12Months;
    private Enum501 RentPaymentPeriod;
    private Enum835 DepositAsProportionOfRent_DEPPROP;
    private Enum30 Region_EHSOrder;
    private Enum69 LowSeasonLet;
    private Enum69 SomeOtherReason;
    private Enum69 UnexpectedCouncilTaxOrUtilityBills;
    private Enum69 PropertyRequiredCleaning;
    private Enum69 UnpaidRent;
    private Enum69 PrevTnncy_GuarantorRequired;
    private Enum69 Unemployment;
    private Enum69 PrevTnncy_PaidDeposit;
    private Enum69 OtherDebtsOrResponsibilities;
    private Enum251 HousingBenefitAndRent;
    private Enum660 EverCouncil_HAHouse_Flat;
    private Enum69 OtherFee_FEEFOTHR;
    private Enum849 Tenants_DivisionOfRentPayment;
    private Enum824 Computed_DepositReturned;
    private Enum69 TreatedAsPrivateRentedHousehold;
    private Enum69 TVLicenceFee;
    private Enum853 HowFelt;
    private Enum854 ReasonBehindTenancyAcquisition;
    private Enum855 AnsweringHB_LHAForWholeHousehold;
    private Enum69 DamageToProperty;
    private Enum69 FindersFee_Non_Ret__FEEFFNDR;
    private Enum314 HowEasyIsItToPayYourRentAfterBenefits;
    private Enum230 FieldworkQuarter;
    private Enum817 TenantsXsHB_HBRcvPer;
    private Enum69 Council_HAAgreedToYourAcquiringTheTenancy;
    private Enum862 UsedDepositTowardsRent;
    private Enum69 TenantsXsHB_XsBenefitChk;
    private Enum817 Tenants_PrtHB_RentChrgPer;
    private Enum69 AcceptedAsHomeless;
    private Enum817 TenantsXsHB_RentPdPer;
    private Enum69 IncreaseInRent;
    private Enum69 ThisTnncy_PaidDeposit;
    private Enum869 RelationshipOfPreviousTenant_PersonYouWereLivingWith;
    private Enum69 AdminFee_Non_Ret__FEEFADMN;
    private Enum817 Tenants_DKHB_RentPdPer;
    private Enum69 HotWater;
    private Enum69 ResidentWhenTenancyAcquired;
    private Enum69 HoldingFee_Non_Ret__FEEFHLDN;
    private Enum69 Illness;
    private Enum876 DepositKeptAsPropnOfRent;
    private Enum817 Tenants_UnsureHB_RentPdPer;
    private Enum817 NetRentPeriod;
    private Enum69 TenancyTransferredFromLA;
    private Enum69 WorkingFewerHours_LessOvertime;
    private Enum69 UpToDateWithRentPayments;
    private Enum817 Tenants_FllHB_HBPer;
    private Enum69 RentHoliday;
    private Enum229 StatusOfCase;
    private Enum69 CouncilTax;
    private Enum69 RegularMeals;
    private Enum69 ProblemsWithHousingBenefit_LocalHousingAllowance;
    private Enum817 Tenants_NoHB_RentPdPer;
    private Enum69 FuelForCooking;
    private Enum890 LettingType;
    private Enum817 Tenants_UnsureHB_RentChrgPer;
    private Enum69 PrevTnncy_Landlord_AgencyFee;
    private Enum69 Lighting;
    private Enum69 HoldingFee_Ret_;
    private Enum830 OneOrMoreAgreementsWithLandlord;
    private Enum69 DomesticProblems;
    private Enum817 Tenants_PrtHB_HBRcvPer;
    private Enum69 RentPaidToOwner;
    private Enum69 NoneOfThese_ARRNONE;
    private Enum69 ReceivingHB_LHAllowanceWhenCurrentTenancyStarted;
    private Enum817 Tenants_PrtHB_AmtRentPdPer;

    public String getReasonForTakingOver_AquiringTenancy() {
        return ReasonForTakingOver_AquiringTenancy;
    }

    public void setReasonForTakingOver_AquiringTenancy(final String ReasonForTakingOver_AquiringTenancy) {
        this.ReasonForTakingOver_AquiringTenancy = ReasonForTakingOver_AquiringTenancy;
    }

    public Integer getStartOfPreviousTenancy() {
        return StartOfPreviousTenancy;
    }

    public void setStartOfPreviousTenancy(final Integer StartOfPreviousTenancy) {
        this.StartOfPreviousTenancy = StartOfPreviousTenancy;
    }

    public Integer getTenants_PrtHB_FllAmtRentChrg() {
        return Tenants_PrtHB_FllAmtRentChrg;
    }

    public void setTenants_PrtHB_FllAmtRentChrg(final Integer Tenants_PrtHB_FllAmtRentChrg) {
        this.Tenants_PrtHB_FllAmtRentChrg = Tenants_PrtHB_FllAmtRentChrg;
    }

    public Integer getTotalWeeklyRent_PartHB_() {
        return TotalWeeklyRent_PartHB_;
    }

    public void setTotalWeeklyRent_PartHB_(final Integer TotalWeeklyRent_PartHB_) {
        this.TotalWeeklyRent_PartHB_ = TotalWeeklyRent_PartHB_;
    }

    public Integer getTenants_FllHB_AmtHB() {
        return Tenants_FllHB_AmtHB;
    }

    public void setTenants_FllHB_AmtHB(final Integer Tenants_FllHB_AmtHB) {
        this.Tenants_FllHB_AmtHB = Tenants_FllHB_AmtHB;
    }

    public Integer getForHowManyWeeksDoYouHaveARentHoliday_() {
        return ForHowManyWeeksDoYouHaveARentHoliday_;
    }

    public void setForHowManyWeeksDoYouHaveARentHoliday_(final Integer ForHowManyWeeksDoYouHaveARentHoliday_) {
        this.ForHowManyWeeksDoYouHaveARentHoliday_ = ForHowManyWeeksDoYouHaveARentHoliday_;
    }

    public Integer getYearTenancyStarted() {
        return YearTenancyStarted;
    }

    public void setYearTenancyStarted(final Integer YearTenancyStarted) {
        this.YearTenancyStarted = YearTenancyStarted;
    }

    public Integer getTenantsXsHB_AmtHBRcv() {
        return TenantsXsHB_AmtHBRcv;
    }

    public void setTenantsXsHB_AmtHBRcv(final Integer TenantsXsHB_AmtHBRcv) {
        this.TenantsXsHB_AmtHBRcv = TenantsXsHB_AmtHBRcv;
    }

    public Integer getTenants_UnsureHB_AmtRentChrg() {
        return Tenants_UnsureHB_AmtRentChrg;
    }

    public void setTenants_UnsureHB_AmtRentChrg(final Integer Tenants_UnsureHB_AmtRentChrg) {
        this.Tenants_UnsureHB_AmtRentChrg = Tenants_UnsureHB_AmtRentChrg;
    }

    public Integer getTenants_NoHB_AmtRentPd() {
        return Tenants_NoHB_AmtRentPd;
    }

    public void setTenants_NoHB_AmtRentPd(final Integer Tenants_NoHB_AmtRentPd) {
        this.Tenants_NoHB_AmtRentPd = Tenants_NoHB_AmtRentPd;
    }

    public Integer getTenantsXsHB_FllRentChk() {
        return TenantsXsHB_FllRentChk;
    }

    public void setTenantsXsHB_FllRentChk(final Integer TenantsXsHB_FllRentChk) {
        this.TenantsXsHB_FllRentChk = TenantsXsHB_FllRentChk;
    }

    public Integer getRentExclServices_HB_LHA() {
        return RentExclServices_HB_LHA;
    }

    public void setRentExclServices_HB_LHA(final Integer RentExclServices_HB_LHA) {
        this.RentExclServices_HB_LHA = RentExclServices_HB_LHA;
    }

    public Integer getWeeklyExcessBenefit() {
        return WeeklyExcessBenefit;
    }

    public void setWeeklyExcessBenefit(final Integer WeeklyExcessBenefit) {
        this.WeeklyExcessBenefit = WeeklyExcessBenefit;
    }

    public Integer getPrevTnncy_AmountOfFee() {
        return PrevTnncy_AmountOfFee;
    }

    public void setPrevTnncy_AmountOfFee(final Integer PrevTnncy_AmountOfFee) {
        this.PrevTnncy_AmountOfFee = PrevTnncy_AmountOfFee;
    }

    public Integer getTenants_DKHB_AmtRentChrg() {
        return Tenants_DKHB_AmtRentChrg;
    }

    public void setTenants_DKHB_AmtRentChrg(final Integer Tenants_DKHB_AmtRentChrg) {
        this.Tenants_DKHB_AmtRentChrg = Tenants_DKHB_AmtRentChrg;
    }

    public Integer getTenants_PrtHB_AmtHBRcv() {
        return Tenants_PrtHB_AmtHBRcv;
    }

    public void setTenants_PrtHB_AmtHBRcv(final Integer Tenants_PrtHB_AmtHBRcv) {
        this.Tenants_PrtHB_AmtHBRcv = Tenants_PrtHB_AmtHBRcv;
    }

    public Integer getYearBecameHATenant() {
        return YearBecameHATenant;
    }

    public void setYearBecameHATenant(final Integer YearBecameHATenant) {
        this.YearBecameHATenant = YearBecameHATenant;
    }

    public Integer getWhenTenancyEnded() {
        return WhenTenancyEnded;
    }

    public void setWhenTenancyEnded(final Integer WhenTenancyEnded) {
        this.WhenTenancyEnded = WhenTenancyEnded;
    }

    public Integer getTenants_UnsureHB_AmtRentPd() {
        return Tenants_UnsureHB_AmtRentPd;
    }

    public void setTenants_UnsureHB_AmtRentPd(final Integer Tenants_UnsureHB_AmtRentPd) {
        this.Tenants_UnsureHB_AmtRentPd = Tenants_UnsureHB_AmtRentPd;
    }

    public Integer getNo_OfAgreementsWithinHhld() {
        return No_OfAgreementsWithinHhld;
    }

    public void setNo_OfAgreementsWithinHhld(final Integer No_OfAgreementsWithinHhld) {
        this.No_OfAgreementsWithinHhld = No_OfAgreementsWithinHhld;
    }

    public Integer getTotalAmountOfRent() {
        return TotalAmountOfRent;
    }

    public void setTotalAmountOfRent(final Integer TotalAmountOfRent) {
        this.TotalAmountOfRent = TotalAmountOfRent;
    }

    public Integer getTenants_DKHB_AmtRentPd() {
        return Tenants_DKHB_AmtRentPd;
    }

    public void setTenants_DKHB_AmtRentPd(final Integer Tenants_DKHB_AmtRentPd) {
        this.Tenants_DKHB_AmtRentPd = Tenants_DKHB_AmtRentPd;
    }

    public Integer getTenantsXsHB_AmtRentPd() {
        return TenantsXsHB_AmtRentPd;
    }

    public void setTenantsXsHB_AmtRentPd(final Integer TenantsXsHB_AmtRentPd) {
        this.TenantsXsHB_AmtRentPd = TenantsXsHB_AmtRentPd;
    }

    public Integer getTenants_PrtHB_AmtRentPd() {
        return Tenants_PrtHB_AmtRentPd;
    }

    public void setTenants_PrtHB_AmtRentPd(final Integer Tenants_PrtHB_AmtRentPd) {
        this.Tenants_PrtHB_AmtRentPd = Tenants_PrtHB_AmtRentPd;
    }

    public Double getDepositWasHeldBy() {
        return DepositWasHeldBy;
    }

    public void setDepositWasHeldBy(final Double DepositWasHeldBy) {
        this.DepositWasHeldBy = DepositWasHeldBy;
    }

    public Double getGuarantorRequired() {
        return GuarantorRequired;
    }

    public void setGuarantorRequired(final Double GuarantorRequired) {
        this.GuarantorRequired = GuarantorRequired;
    }

    public Double getAdminFee_Non_Ret_() {
        return AdminFee_Non_Ret_;
    }

    public void setAdminFee_Non_Ret_(final Double AdminFee_Non_Ret_) {
        this.AdminFee_Non_Ret_ = AdminFee_Non_Ret_;
    }

    public Double getHoldingFee_Non_Ret_() {
        return HoldingFee_Non_Ret_;
    }

    public void setHoldingFee_Non_Ret_(final Double HoldingFee_Non_Ret_) {
        this.HoldingFee_Non_Ret_ = HoldingFee_Non_Ret_;
    }

    public Double getFindersFee_Non_Ret_() {
        return FindersFee_Non_Ret_;
    }

    public void setFindersFee_Non_Ret_(final Double FindersFee_Non_Ret_) {
        this.FindersFee_Non_Ret_ = FindersFee_Non_Ret_;
    }

    public Double getHoldingFee_Returnable_() {
        return HoldingFee_Returnable_;
    }

    public void setHoldingFee_Returnable_(final Double HoldingFee_Returnable_) {
        this.HoldingFee_Returnable_ = HoldingFee_Returnable_;
    }

    public Double getThisTnncy_Landlord_AgencyFee() {
        return ThisTnncy_Landlord_AgencyFee;
    }

    public void setThisTnncy_Landlord_AgencyFee(final Double ThisTnncy_Landlord_AgencyFee) {
        this.ThisTnncy_Landlord_AgencyFee = ThisTnncy_Landlord_AgencyFee;
    }

    public Double getIsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes() {
        return IsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes;
    }

    public void setIsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes(final Double IsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes) {
        this.IsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes = IsYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes;
    }

    public Double getThisTnncy_AmountOfFee() {
        return ThisTnncy_AmountOfFee;
    }

    public void setThisTnncy_AmountOfFee(final Double ThisTnncy_AmountOfFee) {
        this.ThisTnncy_AmountOfFee = ThisTnncy_AmountOfFee;
    }

    public Double getWasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes() {
        return WasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes;
    }

    public void setWasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes(final Double WasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes) {
        this.WasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes = WasYourDepositProtectedUnderOneOfTheGovernmentAuthorisedTenancyDepositSchemes;
    }

    public Double getDepositBeingHeldBy() {
        return DepositBeingHeldBy;
    }

    public void setDepositBeingHeldBy(final Double DepositBeingHeldBy) {
        this.DepositBeingHeldBy = DepositBeingHeldBy;
    }

    public Double getOtherFee() {
        return OtherFee;
    }

    public void setOtherFee(final Double OtherFee) {
        this.OtherFee = OtherFee;
    }

    public Double getDepositAsProportionOfRent() {
        return DepositAsProportionOfRent;
    }

    public void setDepositAsProportionOfRent(final Double DepositAsProportionOfRent) {
        this.DepositAsProportionOfRent = DepositAsProportionOfRent;
    }

    public Enum813 getTenancyType() {
        return TenancyType;
    }

    public void setTenancyType(final Enum813 TenancyType) {
        this.TenancyType = TenancyType;
    }

    public Enum69 getWithLandlord_SAgreement() {
        return WithLandlord_SAgreement;
    }

    public void setWithLandlord_SAgreement(final Enum69 WithLandlord_SAgreement) {
        this.WithLandlord_SAgreement = WithLandlord_SAgreement;
    }

    public Enum815 getRent_HBAmountsGivenForWholeAccommodationOrJustYourShare() {
        return Rent_HBAmountsGivenForWholeAccommodationOrJustYourShare;
    }

    public void setRent_HBAmountsGivenForWholeAccommodationOrJustYourShare(final Enum815 Rent_HBAmountsGivenForWholeAccommodationOrJustYourShare) {
        this.Rent_HBAmountsGivenForWholeAccommodationOrJustYourShare = Rent_HBAmountsGivenForWholeAccommodationOrJustYourShare;
    }

    public Enum69 getWaterAndSewerage() {
        return WaterAndSewerage;
    }

    public void setWaterAndSewerage(final Enum69 WaterAndSewerage) {
        this.WaterAndSewerage = WaterAndSewerage;
    }

    public Enum817 getTenants_DKHB_RentChrgPer() {
        return Tenants_DKHB_RentChrgPer;
    }

    public void setTenants_DKHB_RentChrgPer(final Enum817 Tenants_DKHB_RentChrgPer) {
        this.Tenants_DKHB_RentChrgPer = Tenants_DKHB_RentChrgPer;
    }

    public Enum817 getTenantsXsHB_RentChrgPer() {
        return TenantsXsHB_RentChrgPer;
    }

    public void setTenantsXsHB_RentChrgPer(final Enum817 TenantsXsHB_RentChrgPer) {
        this.TenantsXsHB_RentChrgPer = TenantsXsHB_RentChrgPer;
    }

    public Enum69 getNoneOfThese() {
        return NoneOfThese;
    }

    public void setNoneOfThese(final Enum69 NoneOfThese) {
        this.NoneOfThese = NoneOfThese;
    }

    public Enum69 getHeating() {
        return Heating;
    }

    public void setHeating(final Enum69 Heating) {
        this.Heating = Heating;
    }

    public Enum69 getTenants_PrtHB_FllRentChk() {
        return Tenants_PrtHB_FllRentChk;
    }

    public void setTenants_PrtHB_FllRentChk(final Enum69 Tenants_PrtHB_FllRentChk) {
        this.Tenants_PrtHB_FllRentChk = Tenants_PrtHB_FllRentChk;
    }

    public Enum69 getUnpaidBills() {
        return UnpaidBills;
    }

    public void setUnpaidBills(final Enum69 UnpaidBills) {
        this.UnpaidBills = UnpaidBills;
    }

    public Enum823 getLengthOfWait() {
        return LengthOfWait;
    }

    public void setLengthOfWait(final Enum823 LengthOfWait) {
        this.LengthOfWait = LengthOfWait;
    }

    public Enum824 getDepositReturned() {
        return DepositReturned;
    }

    public void setDepositReturned(final Enum824 DepositReturned) {
        this.DepositReturned = DepositReturned;
    }

    public Enum69 getNoReasonGiven() {
        return NoReasonGiven;
    }

    public void setNoReasonGiven(final Enum69 NoReasonGiven) {
        this.NoReasonGiven = NoReasonGiven;
    }

    public Enum69 getAnyRentCoveredByHB_LHA() {
        return AnyRentCoveredByHB_LHA;
    }

    public void setAnyRentCoveredByHB_LHA(final Enum69 AnyRentCoveredByHB_LHA) {
        this.AnyRentCoveredByHB_LHA = AnyRentCoveredByHB_LHA;
    }

    public Enum827 getAmountOfRentCovered() {
        return AmountOfRentCovered;
    }

    public void setAmountOfRentCovered(final Enum827 AmountOfRentCovered) {
        this.AmountOfRentCovered = AmountOfRentCovered;
    }

    public Enum69 getAcquiredTenancyWhenLivingWithSomeoneWhoWasATenant() {
        return AcquiredTenancyWhenLivingWithSomeoneWhoWasATenant;
    }

    public void setAcquiredTenancyWhenLivingWithSomeoneWhoWasATenant(final Enum69 AcquiredTenancyWhenLivingWithSomeoneWhoWasATenant) {
        this.AcquiredTenancyWhenLivingWithSomeoneWhoWasATenant = AcquiredTenancyWhenLivingWithSomeoneWhoWasATenant;
    }

    public Enum630 getComplexRentingSituation() {
        return ComplexRentingSituation;
    }

    public void setComplexRentingSituation(final Enum630 ComplexRentingSituation) {
        this.ComplexRentingSituation = ComplexRentingSituation;
    }

    public Enum830 getNo_OfLodgerAgreements() {
        return No_OfLodgerAgreements;
    }

    public void setNo_OfLodgerAgreements(final Enum830 No_OfLodgerAgreements) {
        this.No_OfLodgerAgreements = No_OfLodgerAgreements;
    }

    public Enum831 getTimeAtPropertyBeforeAcquiringTenancy() {
        return TimeAtPropertyBeforeAcquiringTenancy;
    }

    public void setTimeAtPropertyBeforeAcquiringTenancy(final Enum831 TimeAtPropertyBeforeAcquiringTenancy) {
        this.TimeAtPropertyBeforeAcquiringTenancy = TimeAtPropertyBeforeAcquiringTenancy;
    }

    public Enum69 getRentOwing() {
        return RentOwing;
    }

    public void setRentOwing(final Enum69 RentOwing) {
        this.RentOwing = RentOwing;
    }

    public Enum69 getFallenBehindWithRentPaymentsOverTheLast12Months() {
        return FallenBehindWithRentPaymentsOverTheLast12Months;
    }

    public void setFallenBehindWithRentPaymentsOverTheLast12Months(final Enum69 FallenBehindWithRentPaymentsOverTheLast12Months) {
        this.FallenBehindWithRentPaymentsOverTheLast12Months = FallenBehindWithRentPaymentsOverTheLast12Months;
    }

    public Enum501 getRentPaymentPeriod() {
        return RentPaymentPeriod;
    }

    public void setRentPaymentPeriod(final Enum501 RentPaymentPeriod) {
        this.RentPaymentPeriod = RentPaymentPeriod;
    }

    public Enum835 getDepositAsProportionOfRent_DEPPROP() {
        return DepositAsProportionOfRent_DEPPROP;
    }

    public void setDepositAsProportionOfRent_DEPPROP(final Enum835 DepositAsProportionOfRent_DEPPROP) {
        this.DepositAsProportionOfRent_DEPPROP = DepositAsProportionOfRent_DEPPROP;
    }

    public Enum30 getRegion_EHSOrder() {
        return Region_EHSOrder;
    }

    public void setRegion_EHSOrder(final Enum30 Region_EHSOrder) {
        this.Region_EHSOrder = Region_EHSOrder;
    }

    public Enum69 getLowSeasonLet() {
        return LowSeasonLet;
    }

    public void setLowSeasonLet(final Enum69 LowSeasonLet) {
        this.LowSeasonLet = LowSeasonLet;
    }

    public Enum69 getSomeOtherReason() {
        return SomeOtherReason;
    }

    public void setSomeOtherReason(final Enum69 SomeOtherReason) {
        this.SomeOtherReason = SomeOtherReason;
    }

    public Enum69 getUnexpectedCouncilTaxOrUtilityBills() {
        return UnexpectedCouncilTaxOrUtilityBills;
    }

    public void setUnexpectedCouncilTaxOrUtilityBills(final Enum69 UnexpectedCouncilTaxOrUtilityBills) {
        this.UnexpectedCouncilTaxOrUtilityBills = UnexpectedCouncilTaxOrUtilityBills;
    }

    public Enum69 getPropertyRequiredCleaning() {
        return PropertyRequiredCleaning;
    }

    public void setPropertyRequiredCleaning(final Enum69 PropertyRequiredCleaning) {
        this.PropertyRequiredCleaning = PropertyRequiredCleaning;
    }

    public Enum69 getUnpaidRent() {
        return UnpaidRent;
    }

    public void setUnpaidRent(final Enum69 UnpaidRent) {
        this.UnpaidRent = UnpaidRent;
    }

    public Enum69 getPrevTnncy_GuarantorRequired() {
        return PrevTnncy_GuarantorRequired;
    }

    public void setPrevTnncy_GuarantorRequired(final Enum69 PrevTnncy_GuarantorRequired) {
        this.PrevTnncy_GuarantorRequired = PrevTnncy_GuarantorRequired;
    }

    public Enum69 getUnemployment() {
        return Unemployment;
    }

    public void setUnemployment(final Enum69 Unemployment) {
        this.Unemployment = Unemployment;
    }

    public Enum69 getPrevTnncy_PaidDeposit() {
        return PrevTnncy_PaidDeposit;
    }

    public void setPrevTnncy_PaidDeposit(final Enum69 PrevTnncy_PaidDeposit) {
        this.PrevTnncy_PaidDeposit = PrevTnncy_PaidDeposit;
    }

    public Enum69 getOtherDebtsOrResponsibilities() {
        return OtherDebtsOrResponsibilities;
    }

    public void setOtherDebtsOrResponsibilities(final Enum69 OtherDebtsOrResponsibilities) {
        this.OtherDebtsOrResponsibilities = OtherDebtsOrResponsibilities;
    }

    public Enum251 getHousingBenefitAndRent() {
        return HousingBenefitAndRent;
    }

    public void setHousingBenefitAndRent(final Enum251 HousingBenefitAndRent) {
        this.HousingBenefitAndRent = HousingBenefitAndRent;
    }

    public Enum660 getEverCouncil_HAHouse_Flat() {
        return EverCouncil_HAHouse_Flat;
    }

    public void setEverCouncil_HAHouse_Flat(final Enum660 EverCouncil_HAHouse_Flat) {
        this.EverCouncil_HAHouse_Flat = EverCouncil_HAHouse_Flat;
    }

    public Enum69 getOtherFee_FEEFOTHR() {
        return OtherFee_FEEFOTHR;
    }

    public void setOtherFee_FEEFOTHR(final Enum69 OtherFee_FEEFOTHR) {
        this.OtherFee_FEEFOTHR = OtherFee_FEEFOTHR;
    }

    public Enum849 getTenants_DivisionOfRentPayment() {
        return Tenants_DivisionOfRentPayment;
    }

    public void setTenants_DivisionOfRentPayment(final Enum849 Tenants_DivisionOfRentPayment) {
        this.Tenants_DivisionOfRentPayment = Tenants_DivisionOfRentPayment;
    }

    public Enum824 getComputed_DepositReturned() {
        return Computed_DepositReturned;
    }

    public void setComputed_DepositReturned(final Enum824 Computed_DepositReturned) {
        this.Computed_DepositReturned = Computed_DepositReturned;
    }

    public Enum69 getTreatedAsPrivateRentedHousehold() {
        return TreatedAsPrivateRentedHousehold;
    }

    public void setTreatedAsPrivateRentedHousehold(final Enum69 TreatedAsPrivateRentedHousehold) {
        this.TreatedAsPrivateRentedHousehold = TreatedAsPrivateRentedHousehold;
    }

    public Enum69 getTVLicenceFee() {
        return TVLicenceFee;
    }

    public void setTVLicenceFee(final Enum69 TVLicenceFee) {
        this.TVLicenceFee = TVLicenceFee;
    }

    public Enum853 getHowFelt() {
        return HowFelt;
    }

    public void setHowFelt(final Enum853 HowFelt) {
        this.HowFelt = HowFelt;
    }

    public Enum854 getReasonBehindTenancyAcquisition() {
        return ReasonBehindTenancyAcquisition;
    }

    public void setReasonBehindTenancyAcquisition(final Enum854 ReasonBehindTenancyAcquisition) {
        this.ReasonBehindTenancyAcquisition = ReasonBehindTenancyAcquisition;
    }

    public Enum855 getAnsweringHB_LHAForWholeHousehold() {
        return AnsweringHB_LHAForWholeHousehold;
    }

    public void setAnsweringHB_LHAForWholeHousehold(final Enum855 AnsweringHB_LHAForWholeHousehold) {
        this.AnsweringHB_LHAForWholeHousehold = AnsweringHB_LHAForWholeHousehold;
    }

    public Enum69 getDamageToProperty() {
        return DamageToProperty;
    }

    public void setDamageToProperty(final Enum69 DamageToProperty) {
        this.DamageToProperty = DamageToProperty;
    }

    public Enum69 getFindersFee_Non_Ret__FEEFFNDR() {
        return FindersFee_Non_Ret__FEEFFNDR;
    }

    public void setFindersFee_Non_Ret__FEEFFNDR(final Enum69 FindersFee_Non_Ret__FEEFFNDR) {
        this.FindersFee_Non_Ret__FEEFFNDR = FindersFee_Non_Ret__FEEFFNDR;
    }

    public Enum314 getHowEasyIsItToPayYourRentAfterBenefits() {
        return HowEasyIsItToPayYourRentAfterBenefits;
    }

    public void setHowEasyIsItToPayYourRentAfterBenefits(final Enum314 HowEasyIsItToPayYourRentAfterBenefits) {
        this.HowEasyIsItToPayYourRentAfterBenefits = HowEasyIsItToPayYourRentAfterBenefits;
    }

    public Enum230 getFieldworkQuarter() {
        return FieldworkQuarter;
    }

    public void setFieldworkQuarter(final Enum230 FieldworkQuarter) {
        this.FieldworkQuarter = FieldworkQuarter;
    }

    public Enum817 getTenantsXsHB_HBRcvPer() {
        return TenantsXsHB_HBRcvPer;
    }

    public void setTenantsXsHB_HBRcvPer(final Enum817 TenantsXsHB_HBRcvPer) {
        this.TenantsXsHB_HBRcvPer = TenantsXsHB_HBRcvPer;
    }

    public Enum69 getCouncil_HAAgreedToYourAcquiringTheTenancy() {
        return Council_HAAgreedToYourAcquiringTheTenancy;
    }

    public void setCouncil_HAAgreedToYourAcquiringTheTenancy(final Enum69 Council_HAAgreedToYourAcquiringTheTenancy) {
        this.Council_HAAgreedToYourAcquiringTheTenancy = Council_HAAgreedToYourAcquiringTheTenancy;
    }

    public Enum862 getUsedDepositTowardsRent() {
        return UsedDepositTowardsRent;
    }

    public void setUsedDepositTowardsRent(final Enum862 UsedDepositTowardsRent) {
        this.UsedDepositTowardsRent = UsedDepositTowardsRent;
    }

    public Enum69 getTenantsXsHB_XsBenefitChk() {
        return TenantsXsHB_XsBenefitChk;
    }

    public void setTenantsXsHB_XsBenefitChk(final Enum69 TenantsXsHB_XsBenefitChk) {
        this.TenantsXsHB_XsBenefitChk = TenantsXsHB_XsBenefitChk;
    }

    public Enum817 getTenants_PrtHB_RentChrgPer() {
        return Tenants_PrtHB_RentChrgPer;
    }

    public void setTenants_PrtHB_RentChrgPer(final Enum817 Tenants_PrtHB_RentChrgPer) {
        this.Tenants_PrtHB_RentChrgPer = Tenants_PrtHB_RentChrgPer;
    }

    public Enum69 getAcceptedAsHomeless() {
        return AcceptedAsHomeless;
    }

    public void setAcceptedAsHomeless(final Enum69 AcceptedAsHomeless) {
        this.AcceptedAsHomeless = AcceptedAsHomeless;
    }

    public Enum817 getTenantsXsHB_RentPdPer() {
        return TenantsXsHB_RentPdPer;
    }

    public void setTenantsXsHB_RentPdPer(final Enum817 TenantsXsHB_RentPdPer) {
        this.TenantsXsHB_RentPdPer = TenantsXsHB_RentPdPer;
    }

    public Enum69 getIncreaseInRent() {
        return IncreaseInRent;
    }

    public void setIncreaseInRent(final Enum69 IncreaseInRent) {
        this.IncreaseInRent = IncreaseInRent;
    }

    public Enum69 getThisTnncy_PaidDeposit() {
        return ThisTnncy_PaidDeposit;
    }

    public void setThisTnncy_PaidDeposit(final Enum69 ThisTnncy_PaidDeposit) {
        this.ThisTnncy_PaidDeposit = ThisTnncy_PaidDeposit;
    }

    public Enum869 getRelationshipOfPreviousTenant_PersonYouWereLivingWith() {
        return RelationshipOfPreviousTenant_PersonYouWereLivingWith;
    }

    public void setRelationshipOfPreviousTenant_PersonYouWereLivingWith(final Enum869 RelationshipOfPreviousTenant_PersonYouWereLivingWith) {
        this.RelationshipOfPreviousTenant_PersonYouWereLivingWith = RelationshipOfPreviousTenant_PersonYouWereLivingWith;
    }

    public Enum69 getAdminFee_Non_Ret__FEEFADMN() {
        return AdminFee_Non_Ret__FEEFADMN;
    }

    public void setAdminFee_Non_Ret__FEEFADMN(final Enum69 AdminFee_Non_Ret__FEEFADMN) {
        this.AdminFee_Non_Ret__FEEFADMN = AdminFee_Non_Ret__FEEFADMN;
    }

    public Enum817 getTenants_DKHB_RentPdPer() {
        return Tenants_DKHB_RentPdPer;
    }

    public void setTenants_DKHB_RentPdPer(final Enum817 Tenants_DKHB_RentPdPer) {
        this.Tenants_DKHB_RentPdPer = Tenants_DKHB_RentPdPer;
    }

    public Enum69 getHotWater() {
        return HotWater;
    }

    public void setHotWater(final Enum69 HotWater) {
        this.HotWater = HotWater;
    }

    public Enum69 getResidentWhenTenancyAcquired() {
        return ResidentWhenTenancyAcquired;
    }

    public void setResidentWhenTenancyAcquired(final Enum69 ResidentWhenTenancyAcquired) {
        this.ResidentWhenTenancyAcquired = ResidentWhenTenancyAcquired;
    }

    public Enum69 getHoldingFee_Non_Ret__FEEFHLDN() {
        return HoldingFee_Non_Ret__FEEFHLDN;
    }

    public void setHoldingFee_Non_Ret__FEEFHLDN(final Enum69 HoldingFee_Non_Ret__FEEFHLDN) {
        this.HoldingFee_Non_Ret__FEEFHLDN = HoldingFee_Non_Ret__FEEFHLDN;
    }

    public Enum69 getIllness() {
        return Illness;
    }

    public void setIllness(final Enum69 Illness) {
        this.Illness = Illness;
    }

    public Enum876 getDepositKeptAsPropnOfRent() {
        return DepositKeptAsPropnOfRent;
    }

    public void setDepositKeptAsPropnOfRent(final Enum876 DepositKeptAsPropnOfRent) {
        this.DepositKeptAsPropnOfRent = DepositKeptAsPropnOfRent;
    }

    public Enum817 getTenants_UnsureHB_RentPdPer() {
        return Tenants_UnsureHB_RentPdPer;
    }

    public void setTenants_UnsureHB_RentPdPer(final Enum817 Tenants_UnsureHB_RentPdPer) {
        this.Tenants_UnsureHB_RentPdPer = Tenants_UnsureHB_RentPdPer;
    }

    public Enum817 getNetRentPeriod() {
        return NetRentPeriod;
    }

    public void setNetRentPeriod(final Enum817 NetRentPeriod) {
        this.NetRentPeriod = NetRentPeriod;
    }

    public Enum69 getTenancyTransferredFromLA() {
        return TenancyTransferredFromLA;
    }

    public void setTenancyTransferredFromLA(final Enum69 TenancyTransferredFromLA) {
        this.TenancyTransferredFromLA = TenancyTransferredFromLA;
    }

    public Enum69 getWorkingFewerHours_LessOvertime() {
        return WorkingFewerHours_LessOvertime;
    }

    public void setWorkingFewerHours_LessOvertime(final Enum69 WorkingFewerHours_LessOvertime) {
        this.WorkingFewerHours_LessOvertime = WorkingFewerHours_LessOvertime;
    }

    public Enum69 getUpToDateWithRentPayments() {
        return UpToDateWithRentPayments;
    }

    public void setUpToDateWithRentPayments(final Enum69 UpToDateWithRentPayments) {
        this.UpToDateWithRentPayments = UpToDateWithRentPayments;
    }

    public Enum817 getTenants_FllHB_HBPer() {
        return Tenants_FllHB_HBPer;
    }

    public void setTenants_FllHB_HBPer(final Enum817 Tenants_FllHB_HBPer) {
        this.Tenants_FllHB_HBPer = Tenants_FllHB_HBPer;
    }

    public Enum69 getRentHoliday() {
        return RentHoliday;
    }

    public void setRentHoliday(final Enum69 RentHoliday) {
        this.RentHoliday = RentHoliday;
    }

    public Enum229 getStatusOfCase() {
        return StatusOfCase;
    }

    public void setStatusOfCase(final Enum229 StatusOfCase) {
        this.StatusOfCase = StatusOfCase;
    }

    public Enum69 getCouncilTax() {
        return CouncilTax;
    }

    public void setCouncilTax(final Enum69 CouncilTax) {
        this.CouncilTax = CouncilTax;
    }

    public Enum69 getRegularMeals() {
        return RegularMeals;
    }

    public void setRegularMeals(final Enum69 RegularMeals) {
        this.RegularMeals = RegularMeals;
    }

    public Enum69 getProblemsWithHousingBenefit_LocalHousingAllowance() {
        return ProblemsWithHousingBenefit_LocalHousingAllowance;
    }

    public void setProblemsWithHousingBenefit_LocalHousingAllowance(final Enum69 ProblemsWithHousingBenefit_LocalHousingAllowance) {
        this.ProblemsWithHousingBenefit_LocalHousingAllowance = ProblemsWithHousingBenefit_LocalHousingAllowance;
    }

    public Enum817 getTenants_NoHB_RentPdPer() {
        return Tenants_NoHB_RentPdPer;
    }

    public void setTenants_NoHB_RentPdPer(final Enum817 Tenants_NoHB_RentPdPer) {
        this.Tenants_NoHB_RentPdPer = Tenants_NoHB_RentPdPer;
    }

    public Enum69 getFuelForCooking() {
        return FuelForCooking;
    }

    public void setFuelForCooking(final Enum69 FuelForCooking) {
        this.FuelForCooking = FuelForCooking;
    }

    public Enum890 getLettingType() {
        return LettingType;
    }

    public void setLettingType(final Enum890 LettingType) {
        this.LettingType = LettingType;
    }

    public Enum817 getTenants_UnsureHB_RentChrgPer() {
        return Tenants_UnsureHB_RentChrgPer;
    }

    public void setTenants_UnsureHB_RentChrgPer(final Enum817 Tenants_UnsureHB_RentChrgPer) {
        this.Tenants_UnsureHB_RentChrgPer = Tenants_UnsureHB_RentChrgPer;
    }

    public Enum69 getPrevTnncy_Landlord_AgencyFee() {
        return PrevTnncy_Landlord_AgencyFee;
    }

    public void setPrevTnncy_Landlord_AgencyFee(final Enum69 PrevTnncy_Landlord_AgencyFee) {
        this.PrevTnncy_Landlord_AgencyFee = PrevTnncy_Landlord_AgencyFee;
    }

    public Enum69 getLighting() {
        return Lighting;
    }

    public void setLighting(final Enum69 Lighting) {
        this.Lighting = Lighting;
    }

    public Enum69 getHoldingFee_Ret_() {
        return HoldingFee_Ret_;
    }

    public void setHoldingFee_Ret_(final Enum69 HoldingFee_Ret_) {
        this.HoldingFee_Ret_ = HoldingFee_Ret_;
    }

    public Enum830 getOneOrMoreAgreementsWithLandlord() {
        return OneOrMoreAgreementsWithLandlord;
    }

    public void setOneOrMoreAgreementsWithLandlord(final Enum830 OneOrMoreAgreementsWithLandlord) {
        this.OneOrMoreAgreementsWithLandlord = OneOrMoreAgreementsWithLandlord;
    }

    public Enum69 getDomesticProblems() {
        return DomesticProblems;
    }

    public void setDomesticProblems(final Enum69 DomesticProblems) {
        this.DomesticProblems = DomesticProblems;
    }

    public Enum817 getTenants_PrtHB_HBRcvPer() {
        return Tenants_PrtHB_HBRcvPer;
    }

    public void setTenants_PrtHB_HBRcvPer(final Enum817 Tenants_PrtHB_HBRcvPer) {
        this.Tenants_PrtHB_HBRcvPer = Tenants_PrtHB_HBRcvPer;
    }

    public Enum69 getRentPaidToOwner() {
        return RentPaidToOwner;
    }

    public void setRentPaidToOwner(final Enum69 RentPaidToOwner) {
        this.RentPaidToOwner = RentPaidToOwner;
    }

    public Enum69 getNoneOfThese_ARRNONE() {
        return NoneOfThese_ARRNONE;
    }

    public void setNoneOfThese_ARRNONE(final Enum69 NoneOfThese_ARRNONE) {
        this.NoneOfThese_ARRNONE = NoneOfThese_ARRNONE;
    }

    public Enum69 getReceivingHB_LHAllowanceWhenCurrentTenancyStarted() {
        return ReceivingHB_LHAllowanceWhenCurrentTenancyStarted;
    }

    public void setReceivingHB_LHAllowanceWhenCurrentTenancyStarted(final Enum69 ReceivingHB_LHAllowanceWhenCurrentTenancyStarted) {
        this.ReceivingHB_LHAllowanceWhenCurrentTenancyStarted = ReceivingHB_LHAllowanceWhenCurrentTenancyStarted;
    }

    public Enum817 getTenants_PrtHB_AmtRentPdPer() {
        return Tenants_PrtHB_AmtRentPdPer;
    }

    public void setTenants_PrtHB_AmtRentPdPer(final Enum817 Tenants_PrtHB_AmtRentPdPer) {
        this.Tenants_PrtHB_AmtRentPdPer = Tenants_PrtHB_AmtRentPdPer;
    }

}
