package uk.org.cse.nhm.ehcs10.interview.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.EmploymentEntry;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum385;
import uk.org.cse.nhm.ehcs10.interview.types.Enum386;
import uk.org.cse.nhm.ehcs10.interview.types.Enum388;
import uk.org.cse.nhm.ehcs10.interview.types.Enum392;
import uk.org.cse.nhm.ehcs10.interview.types.Enum393;
import uk.org.cse.nhm.ehcs10.interview.types.Enum398;
import uk.org.cse.nhm.ehcs10.interview.types.Enum400;
import uk.org.cse.nhm.ehcs10.interview.types.Enum403;
import uk.org.cse.nhm.ehcs10.interview.types.Enum404;
import uk.org.cse.nhm.ehcs10.interview.types.Enum410;
import uk.org.cse.nhm.ehcs10.interview.types.Enum413;
import uk.org.cse.nhm.ehcs10.interview.types.Enum414;
import uk.org.cse.nhm.ehcs10.interview.types.Enum421;
import uk.org.cse.nhm.ehcs10.interview.types.Enum422;
import uk.org.cse.nhm.ehcs10.interview.types.Enum424;
import uk.org.cse.nhm.ehcs10.interview.types.Enum425;
import uk.org.cse.nhm.ehcs10.interview.types.Enum429;
import uk.org.cse.nhm.ehcs10.interview.types.Enum430;
import uk.org.cse.nhm.ehcs10.interview.types.Enum434;
import uk.org.cse.nhm.ehcs10.interview.types.Enum435;
import uk.org.cse.nhm.ehcs10.interview.types.Enum436;
import uk.org.cse.nhm.ehcs10.interview.types.Enum438;
import uk.org.cse.nhm.ehcs10.interview.types.Enum439;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class EmploymentEntryImpl extends SurveyEntryImpl implements EmploymentEntry {
	private Integer PersonNumberOfHRP;
	private Integer PersonIdentifier;
	private Integer YearLeftLastPaidJob;
	private Double Socio_EconomicGroup;
	private Enum69 OtherReason;
	private Enum69 Long_TermSick_Disabled;
	private Enum385 QuestionsReferringToTimeSpent;
	private Enum386 MonthLeftLastPaidJob;
	private Enum30 Region_EHSOrder;
	private Enum388 Sector;
	private Enum69 WaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent;
	private Enum69 NotYetStartedLookingForWork;
	private Enum69 BelievesNoJobsAvailable;
	private Enum392 WhyPart_TimeNotFull_TimeJob;
	private Enum393 EconomicActivity_Reported;
	private Enum69 OnGovernmentScheme_OtherPaidWork;
	private Enum69 Sub_Contractor;
	private Enum69 Doesn_TNeedEmployment;
	private Enum69 DoingFreelanceWork;
	private Enum398 MainReasonCouldNotStartWork;
	private Enum69 WhetherWaitingToTakeUpAJobAlreadyObtained;
	private Enum400 EconomicActivity_InternationalDefinition;
	private Enum69 LookingAfterFamily_Home;
	private Enum69 WhetherLookingForAPlaceOnAGovernmentScheme;
	private Enum403 WaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking;
	private Enum404 EmploymentStatusInMainJob_Reported;
	private Enum69 RunningBusiness_ProfessionalPractice;
	private Enum69 WhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment;
	private Enum69 WhetherCouldStartJob_GovernmentSchemeIn2Weeks;
	private Enum69 WorkingForSelf;
	private Enum69 RetiredFromPaidWork;
	private Enum410 ReasonUnableToStartIn2Weeks;
	private Enum69 UnpaidWorkForRelative_SBusiness;
	private Enum69 WhetherAwayFromAJobOrBusinessInTheLastWeek;
	private Enum413 MainReasonDidNotLookForWork;
	private Enum414 TypeOfNon_PrivateOrganisation;
	private Enum404 EmployeeOrSelf_Employed;
	private Enum69 AnyPaidWorkInLast7Days;
	private Enum69 TemporarilySick_Injured;
	private Enum69 EverHadPaidWork_ApartFromCasualOrHolidayWork;
	private Enum69 PaidSalaryOrWageByAnAgency;
	private Enum69 WhetherSuperviseTheWorkOfOtherEmployees;
	private Enum421 WhetherHaveEmployees;
	private Enum422 MainJob_FullOrPart_Time;
	private Enum229 StatusOfCase;
	private Enum424 TypeOfTrainingScheme;
	private Enum425 ManagerialDuties_Type;
	private Enum403 LookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking;
	private Enum69 SoleDirectorOfOwnLimitedBusiness;
	private Enum69 PaidSalaryOrWageByEmployer;
	private Enum429 ActivityOnGovernmentTrainingScheme;
	private Enum430 NumberOfEmployeesAtWorkplace;
	private Enum230 FieldworkQuarter;
	private Enum69 PartnerInBusiness_ProfessionalPractice;
	private Enum69 Student;
	private Enum434 OnGovtTrainingSchemeInLastWeek;
	private Enum435 NewDealOptions;
	private Enum436 NationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion;
	private Enum69 NoneOfTheAbove;
	private Enum438 TypeOfNewDealScheme;
	private Enum439 EmploymentStatus;
	private Enum430 NumberOfPeopleEmploys;
	private Enum69 WhetherLookingForPaidWorkInTheLast4Weeks;
	private Enum69 UnpaidWorkInThatWeekForOwnBusiness;
	public Integer getPersonNumberOfHRP() {
		return PersonNumberOfHRP;	}

	public void setPersonNumberOfHRP(final Integer PersonNumberOfHRP) {
		this.PersonNumberOfHRP = PersonNumberOfHRP;	}

	public Integer getPersonIdentifier() {
		return PersonIdentifier;	}

	public void setPersonIdentifier(final Integer PersonIdentifier) {
		this.PersonIdentifier = PersonIdentifier;	}

	public Integer getYearLeftLastPaidJob() {
		return YearLeftLastPaidJob;	}

	public void setYearLeftLastPaidJob(final Integer YearLeftLastPaidJob) {
		this.YearLeftLastPaidJob = YearLeftLastPaidJob;	}

	public Double getSocio_EconomicGroup() {
		return Socio_EconomicGroup;	}

	public void setSocio_EconomicGroup(final Double Socio_EconomicGroup) {
		this.Socio_EconomicGroup = Socio_EconomicGroup;	}

	public Enum69 getOtherReason() {
		return OtherReason;	}

	public void setOtherReason(final Enum69 OtherReason) {
		this.OtherReason = OtherReason;	}

	public Enum69 getLong_TermSick_Disabled() {
		return Long_TermSick_Disabled;	}

	public void setLong_TermSick_Disabled(final Enum69 Long_TermSick_Disabled) {
		this.Long_TermSick_Disabled = Long_TermSick_Disabled;	}

	public Enum385 getQuestionsReferringToTimeSpent() {
		return QuestionsReferringToTimeSpent;	}

	public void setQuestionsReferringToTimeSpent(final Enum385 QuestionsReferringToTimeSpent) {
		this.QuestionsReferringToTimeSpent = QuestionsReferringToTimeSpent;	}

	public Enum386 getMonthLeftLastPaidJob() {
		return MonthLeftLastPaidJob;	}

	public void setMonthLeftLastPaidJob(final Enum386 MonthLeftLastPaidJob) {
		this.MonthLeftLastPaidJob = MonthLeftLastPaidJob;	}

	public Enum30 getRegion_EHSOrder() {
		return Region_EHSOrder;	}

	public void setRegion_EHSOrder(final Enum30 Region_EHSOrder) {
		this.Region_EHSOrder = Region_EHSOrder;	}

	public Enum388 getSector() {
		return Sector;	}

	public void setSector(final Enum388 Sector) {
		this.Sector = Sector;	}

	public Enum69 getWaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent() {
		return WaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent;	}

	public void setWaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent(final Enum69 WaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent) {
		this.WaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent = WaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent;	}

	public Enum69 getNotYetStartedLookingForWork() {
		return NotYetStartedLookingForWork;	}

	public void setNotYetStartedLookingForWork(final Enum69 NotYetStartedLookingForWork) {
		this.NotYetStartedLookingForWork = NotYetStartedLookingForWork;	}

	public Enum69 getBelievesNoJobsAvailable() {
		return BelievesNoJobsAvailable;	}

	public void setBelievesNoJobsAvailable(final Enum69 BelievesNoJobsAvailable) {
		this.BelievesNoJobsAvailable = BelievesNoJobsAvailable;	}

	public Enum392 getWhyPart_TimeNotFull_TimeJob() {
		return WhyPart_TimeNotFull_TimeJob;	}

	public void setWhyPart_TimeNotFull_TimeJob(final Enum392 WhyPart_TimeNotFull_TimeJob) {
		this.WhyPart_TimeNotFull_TimeJob = WhyPart_TimeNotFull_TimeJob;	}

	public Enum393 getEconomicActivity_Reported() {
		return EconomicActivity_Reported;	}

	public void setEconomicActivity_Reported(final Enum393 EconomicActivity_Reported) {
		this.EconomicActivity_Reported = EconomicActivity_Reported;	}

	public Enum69 getOnGovernmentScheme_OtherPaidWork() {
		return OnGovernmentScheme_OtherPaidWork;	}

	public void setOnGovernmentScheme_OtherPaidWork(final Enum69 OnGovernmentScheme_OtherPaidWork) {
		this.OnGovernmentScheme_OtherPaidWork = OnGovernmentScheme_OtherPaidWork;	}

	public Enum69 getSub_Contractor() {
		return Sub_Contractor;	}

	public void setSub_Contractor(final Enum69 Sub_Contractor) {
		this.Sub_Contractor = Sub_Contractor;	}

	public Enum69 getDoesn_TNeedEmployment() {
		return Doesn_TNeedEmployment;	}

	public void setDoesn_TNeedEmployment(final Enum69 Doesn_TNeedEmployment) {
		this.Doesn_TNeedEmployment = Doesn_TNeedEmployment;	}

	public Enum69 getDoingFreelanceWork() {
		return DoingFreelanceWork;	}

	public void setDoingFreelanceWork(final Enum69 DoingFreelanceWork) {
		this.DoingFreelanceWork = DoingFreelanceWork;	}

	public Enum398 getMainReasonCouldNotStartWork() {
		return MainReasonCouldNotStartWork;	}

	public void setMainReasonCouldNotStartWork(final Enum398 MainReasonCouldNotStartWork) {
		this.MainReasonCouldNotStartWork = MainReasonCouldNotStartWork;	}

	public Enum69 getWhetherWaitingToTakeUpAJobAlreadyObtained() {
		return WhetherWaitingToTakeUpAJobAlreadyObtained;	}

	public void setWhetherWaitingToTakeUpAJobAlreadyObtained(final Enum69 WhetherWaitingToTakeUpAJobAlreadyObtained) {
		this.WhetherWaitingToTakeUpAJobAlreadyObtained = WhetherWaitingToTakeUpAJobAlreadyObtained;	}

	public Enum400 getEconomicActivity_InternationalDefinition() {
		return EconomicActivity_InternationalDefinition;	}

	public void setEconomicActivity_InternationalDefinition(final Enum400 EconomicActivity_InternationalDefinition) {
		this.EconomicActivity_InternationalDefinition = EconomicActivity_InternationalDefinition;	}

	public Enum69 getLookingAfterFamily_Home() {
		return LookingAfterFamily_Home;	}

	public void setLookingAfterFamily_Home(final Enum69 LookingAfterFamily_Home) {
		this.LookingAfterFamily_Home = LookingAfterFamily_Home;	}

	public Enum69 getWhetherLookingForAPlaceOnAGovernmentScheme() {
		return WhetherLookingForAPlaceOnAGovernmentScheme;	}

	public void setWhetherLookingForAPlaceOnAGovernmentScheme(final Enum69 WhetherLookingForAPlaceOnAGovernmentScheme) {
		this.WhetherLookingForAPlaceOnAGovernmentScheme = WhetherLookingForAPlaceOnAGovernmentScheme;	}

	public Enum403 getWaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking() {
		return WaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking;	}

	public void setWaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking(final Enum403 WaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking) {
		this.WaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking = WaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking;	}

	public Enum404 getEmploymentStatusInMainJob_Reported() {
		return EmploymentStatusInMainJob_Reported;	}

	public void setEmploymentStatusInMainJob_Reported(final Enum404 EmploymentStatusInMainJob_Reported) {
		this.EmploymentStatusInMainJob_Reported = EmploymentStatusInMainJob_Reported;	}

	public Enum69 getRunningBusiness_ProfessionalPractice() {
		return RunningBusiness_ProfessionalPractice;	}

	public void setRunningBusiness_ProfessionalPractice(final Enum69 RunningBusiness_ProfessionalPractice) {
		this.RunningBusiness_ProfessionalPractice = RunningBusiness_ProfessionalPractice;	}

	public Enum69 getWhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment() {
		return WhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment;	}

	public void setWhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment(final Enum69 WhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment) {
		this.WhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment = WhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment;	}

	public Enum69 getWhetherCouldStartJob_GovernmentSchemeIn2Weeks() {
		return WhetherCouldStartJob_GovernmentSchemeIn2Weeks;	}

	public void setWhetherCouldStartJob_GovernmentSchemeIn2Weeks(final Enum69 WhetherCouldStartJob_GovernmentSchemeIn2Weeks) {
		this.WhetherCouldStartJob_GovernmentSchemeIn2Weeks = WhetherCouldStartJob_GovernmentSchemeIn2Weeks;	}

	public Enum69 getWorkingForSelf() {
		return WorkingForSelf;	}

	public void setWorkingForSelf(final Enum69 WorkingForSelf) {
		this.WorkingForSelf = WorkingForSelf;	}

	public Enum69 getRetiredFromPaidWork() {
		return RetiredFromPaidWork;	}

	public void setRetiredFromPaidWork(final Enum69 RetiredFromPaidWork) {
		this.RetiredFromPaidWork = RetiredFromPaidWork;	}

	public Enum410 getReasonUnableToStartIn2Weeks() {
		return ReasonUnableToStartIn2Weeks;	}

	public void setReasonUnableToStartIn2Weeks(final Enum410 ReasonUnableToStartIn2Weeks) {
		this.ReasonUnableToStartIn2Weeks = ReasonUnableToStartIn2Weeks;	}

	public Enum69 getUnpaidWorkForRelative_SBusiness() {
		return UnpaidWorkForRelative_SBusiness;	}

	public void setUnpaidWorkForRelative_SBusiness(final Enum69 UnpaidWorkForRelative_SBusiness) {
		this.UnpaidWorkForRelative_SBusiness = UnpaidWorkForRelative_SBusiness;	}

	public Enum69 getWhetherAwayFromAJobOrBusinessInTheLastWeek() {
		return WhetherAwayFromAJobOrBusinessInTheLastWeek;	}

	public void setWhetherAwayFromAJobOrBusinessInTheLastWeek(final Enum69 WhetherAwayFromAJobOrBusinessInTheLastWeek) {
		this.WhetherAwayFromAJobOrBusinessInTheLastWeek = WhetherAwayFromAJobOrBusinessInTheLastWeek;	}

	public Enum413 getMainReasonDidNotLookForWork() {
		return MainReasonDidNotLookForWork;	}

	public void setMainReasonDidNotLookForWork(final Enum413 MainReasonDidNotLookForWork) {
		this.MainReasonDidNotLookForWork = MainReasonDidNotLookForWork;	}

	public Enum414 getTypeOfNon_PrivateOrganisation() {
		return TypeOfNon_PrivateOrganisation;	}

	public void setTypeOfNon_PrivateOrganisation(final Enum414 TypeOfNon_PrivateOrganisation) {
		this.TypeOfNon_PrivateOrganisation = TypeOfNon_PrivateOrganisation;	}

	public Enum404 getEmployeeOrSelf_Employed() {
		return EmployeeOrSelf_Employed;	}

	public void setEmployeeOrSelf_Employed(final Enum404 EmployeeOrSelf_Employed) {
		this.EmployeeOrSelf_Employed = EmployeeOrSelf_Employed;	}

	public Enum69 getAnyPaidWorkInLast7Days() {
		return AnyPaidWorkInLast7Days;	}

	public void setAnyPaidWorkInLast7Days(final Enum69 AnyPaidWorkInLast7Days) {
		this.AnyPaidWorkInLast7Days = AnyPaidWorkInLast7Days;	}

	public Enum69 getTemporarilySick_Injured() {
		return TemporarilySick_Injured;	}

	public void setTemporarilySick_Injured(final Enum69 TemporarilySick_Injured) {
		this.TemporarilySick_Injured = TemporarilySick_Injured;	}

	public Enum69 getEverHadPaidWork_ApartFromCasualOrHolidayWork() {
		return EverHadPaidWork_ApartFromCasualOrHolidayWork;	}

	public void setEverHadPaidWork_ApartFromCasualOrHolidayWork(final Enum69 EverHadPaidWork_ApartFromCasualOrHolidayWork) {
		this.EverHadPaidWork_ApartFromCasualOrHolidayWork = EverHadPaidWork_ApartFromCasualOrHolidayWork;	}

	public Enum69 getPaidSalaryOrWageByAnAgency() {
		return PaidSalaryOrWageByAnAgency;	}

	public void setPaidSalaryOrWageByAnAgency(final Enum69 PaidSalaryOrWageByAnAgency) {
		this.PaidSalaryOrWageByAnAgency = PaidSalaryOrWageByAnAgency;	}

	public Enum69 getWhetherSuperviseTheWorkOfOtherEmployees() {
		return WhetherSuperviseTheWorkOfOtherEmployees;	}

	public void setWhetherSuperviseTheWorkOfOtherEmployees(final Enum69 WhetherSuperviseTheWorkOfOtherEmployees) {
		this.WhetherSuperviseTheWorkOfOtherEmployees = WhetherSuperviseTheWorkOfOtherEmployees;	}

	public Enum421 getWhetherHaveEmployees() {
		return WhetherHaveEmployees;	}

	public void setWhetherHaveEmployees(final Enum421 WhetherHaveEmployees) {
		this.WhetherHaveEmployees = WhetherHaveEmployees;	}

	public Enum422 getMainJob_FullOrPart_Time() {
		return MainJob_FullOrPart_Time;	}

	public void setMainJob_FullOrPart_Time(final Enum422 MainJob_FullOrPart_Time) {
		this.MainJob_FullOrPart_Time = MainJob_FullOrPart_Time;	}

	public Enum229 getStatusOfCase() {
		return StatusOfCase;	}

	public void setStatusOfCase(final Enum229 StatusOfCase) {
		this.StatusOfCase = StatusOfCase;	}

	public Enum424 getTypeOfTrainingScheme() {
		return TypeOfTrainingScheme;	}

	public void setTypeOfTrainingScheme(final Enum424 TypeOfTrainingScheme) {
		this.TypeOfTrainingScheme = TypeOfTrainingScheme;	}

	public Enum425 getManagerialDuties_Type() {
		return ManagerialDuties_Type;	}

	public void setManagerialDuties_Type(final Enum425 ManagerialDuties_Type) {
		this.ManagerialDuties_Type = ManagerialDuties_Type;	}

	public Enum403 getLookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking() {
		return LookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking;	}

	public void setLookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking(final Enum403 LookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking) {
		this.LookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking = LookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking;	}

	public Enum69 getSoleDirectorOfOwnLimitedBusiness() {
		return SoleDirectorOfOwnLimitedBusiness;	}

	public void setSoleDirectorOfOwnLimitedBusiness(final Enum69 SoleDirectorOfOwnLimitedBusiness) {
		this.SoleDirectorOfOwnLimitedBusiness = SoleDirectorOfOwnLimitedBusiness;	}

	public Enum69 getPaidSalaryOrWageByEmployer() {
		return PaidSalaryOrWageByEmployer;	}

	public void setPaidSalaryOrWageByEmployer(final Enum69 PaidSalaryOrWageByEmployer) {
		this.PaidSalaryOrWageByEmployer = PaidSalaryOrWageByEmployer;	}

	public Enum429 getActivityOnGovernmentTrainingScheme() {
		return ActivityOnGovernmentTrainingScheme;	}

	public void setActivityOnGovernmentTrainingScheme(final Enum429 ActivityOnGovernmentTrainingScheme) {
		this.ActivityOnGovernmentTrainingScheme = ActivityOnGovernmentTrainingScheme;	}

	public Enum430 getNumberOfEmployeesAtWorkplace() {
		return NumberOfEmployeesAtWorkplace;	}

	public void setNumberOfEmployeesAtWorkplace(final Enum430 NumberOfEmployeesAtWorkplace) {
		this.NumberOfEmployeesAtWorkplace = NumberOfEmployeesAtWorkplace;	}

	public Enum230 getFieldworkQuarter() {
		return FieldworkQuarter;	}

	public void setFieldworkQuarter(final Enum230 FieldworkQuarter) {
		this.FieldworkQuarter = FieldworkQuarter;	}

	public Enum69 getPartnerInBusiness_ProfessionalPractice() {
		return PartnerInBusiness_ProfessionalPractice;	}

	public void setPartnerInBusiness_ProfessionalPractice(final Enum69 PartnerInBusiness_ProfessionalPractice) {
		this.PartnerInBusiness_ProfessionalPractice = PartnerInBusiness_ProfessionalPractice;	}

	public Enum69 getStudent() {
		return Student;	}

	public void setStudent(final Enum69 Student) {
		this.Student = Student;	}

	public Enum434 getOnGovtTrainingSchemeInLastWeek() {
		return OnGovtTrainingSchemeInLastWeek;	}

	public void setOnGovtTrainingSchemeInLastWeek(final Enum434 OnGovtTrainingSchemeInLastWeek) {
		this.OnGovtTrainingSchemeInLastWeek = OnGovtTrainingSchemeInLastWeek;	}

	public Enum435 getNewDealOptions() {
		return NewDealOptions;	}

	public void setNewDealOptions(final Enum435 NewDealOptions) {
		this.NewDealOptions = NewDealOptions;	}

	public Enum436 getNationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion() {
		return NationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion;	}

	public void setNationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion(final Enum436 NationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion) {
		this.NationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion = NationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion;	}

	public Enum69 getNoneOfTheAbove() {
		return NoneOfTheAbove;	}

	public void setNoneOfTheAbove(final Enum69 NoneOfTheAbove) {
		this.NoneOfTheAbove = NoneOfTheAbove;	}

	public Enum438 getTypeOfNewDealScheme() {
		return TypeOfNewDealScheme;	}

	public void setTypeOfNewDealScheme(final Enum438 TypeOfNewDealScheme) {
		this.TypeOfNewDealScheme = TypeOfNewDealScheme;	}

	public Enum439 getEmploymentStatus() {
		return EmploymentStatus;	}

	public void setEmploymentStatus(final Enum439 EmploymentStatus) {
		this.EmploymentStatus = EmploymentStatus;	}

	public Enum430 getNumberOfPeopleEmploys() {
		return NumberOfPeopleEmploys;	}

	public void setNumberOfPeopleEmploys(final Enum430 NumberOfPeopleEmploys) {
		this.NumberOfPeopleEmploys = NumberOfPeopleEmploys;	}

	public Enum69 getWhetherLookingForPaidWorkInTheLast4Weeks() {
		return WhetherLookingForPaidWorkInTheLast4Weeks;	}

	public void setWhetherLookingForPaidWorkInTheLast4Weeks(final Enum69 WhetherLookingForPaidWorkInTheLast4Weeks) {
		this.WhetherLookingForPaidWorkInTheLast4Weeks = WhetherLookingForPaidWorkInTheLast4Weeks;	}

	public Enum69 getUnpaidWorkInThatWeekForOwnBusiness() {
		return UnpaidWorkInThatWeekForOwnBusiness;	}

	public void setUnpaidWorkInThatWeekForOwnBusiness(final Enum69 UnpaidWorkInThatWeekForOwnBusiness) {
		this.UnpaidWorkInThatWeekForOwnBusiness = UnpaidWorkInThatWeekForOwnBusiness;	}

}

