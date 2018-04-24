package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface EmploymentEntry extends SurveyEntry {
	@SavVariableMapping("HRP")
	public Integer getPersonNumberOfHRP();

	@SavVariableMapping("PERSNO")
	public Integer getPersonIdentifier();

	@SavVariableMapping("LEFTYR")
	public Integer getYearLeftLastPaidJob();

	@SavVariableMapping("SEG")
	public Double getSocio_EconomicGroup();

	@SavVariableMapping("NOLOOTHR")
	public Enum69 getOtherReason();

	@SavVariableMapping("NOLOLSCK")
	public Enum69 getLong_TermSick_Disabled();

	@SavVariableMapping("ISTATE")
	public Enum385 getQuestionsReferringToTimeSpent();

	@SavVariableMapping("LEFTM")
	public Enum386 getMonthLeftLastPaidJob();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("SECTOR")
	public Enum388 getSector();

	@SavVariableMapping("NOLOAPPL")
	public Enum69 getWaitingForResultsOfJobApplication_BeingAssessedByETTrainingAgent();

	@SavVariableMapping("NOLONTST")
	public Enum69 getNotYetStartedLookingForWork();

	@SavVariableMapping("NOLONOJB")
	public Enum69 getBelievesNoJobsAvailable();

	@SavVariableMapping("YPTJOB")
	public Enum392 getWhyPart_TimeNotFull_TimeJob();

	@SavVariableMapping("ILODEFR")
	public Enum393 getEconomicActivity_Reported();

	@SavVariableMapping("YTETJB")
	public Enum69 getOnGovernmentScheme_OtherPaidWork();

	@SavVariableMapping("SELFSBCR")
	public Enum69 getSub_Contractor();

	@SavVariableMapping("NOLONOND")
	public Enum69 getDoesn_TNeedEmployment();

	@SavVariableMapping("SELFFREE")
	public Enum69 getDoingFreelanceWork();

	@SavVariableMapping("YSTRTF")
	public Enum398 getMainReasonCouldNotStartWork();

	@SavVariableMapping("WAIT")
	public Enum69 getWhetherWaitingToTakeUpAJobAlreadyObtained();

	@SavVariableMapping("INECACA")
	public Enum400 getEconomicActivity_InternationalDefinition();

	@SavVariableMapping("NOLOHOME")
	public Enum69 getLookingAfterFamily_Home();

	@SavVariableMapping("LKYT4")
	public Enum69 getWhetherLookingForAPlaceOnAGovernmentScheme();

	@SavVariableMapping("LKTIMB")
	public Enum403 getWaitingToTakeUpJob_GovernmentSchemePlacement_TimeSpentLooking();

	@SavVariableMapping("STATR")
	public Enum404 getEmploymentStatusInMainJob_Reported();

	@SavVariableMapping("SELFPRAC")
	public Enum69 getRunningBusiness_ProfessionalPractice();

	@SavVariableMapping("LIKEWK")
	public Enum69 getWhetherWouldYouLikeToHaveARegularPaidJobAtTheMoment();

	@SavVariableMapping("START")
	public Enum69 getWhetherCouldStartJob_GovernmentSchemeIn2Weeks();

	@SavVariableMapping("SELFOWNS")
	public Enum69 getWorkingForSelf();

	@SavVariableMapping("NOLORTRE")
	public Enum69 getRetiredFromPaidWork();

	@SavVariableMapping("YSTART")
	public Enum410 getReasonUnableToStartIn2Weeks();

	@SavVariableMapping("RELBUS")
	public Enum69 getUnpaidWorkForRelative_SBusiness();

	@SavVariableMapping("JBAWAY")
	public Enum69 getWhetherAwayFromAJobOrBusinessInTheLastWeek();

	@SavVariableMapping("NOLWM")
	public Enum413 getMainReasonDidNotLookForWork();

	@SavVariableMapping("SECTRO03")
	public Enum414 getTypeOfNon_PrivateOrganisation();

	@SavVariableMapping("STAT")
	public Enum404 getEmployeeOrSelf_Employed();

	@SavVariableMapping("WRKING")
	public Enum69 getAnyPaidWorkInLast7Days();

	@SavVariableMapping("NOLOTSCK")
	public Enum69 getTemporarilySick_Injured();

	@SavVariableMapping("EVERWK")
	public Enum69 getEverHadPaidWork_ApartFromCasualOrHolidayWork();

	@SavVariableMapping("SELFAGCY")
	public Enum69 getPaidSalaryOrWageByAnAgency();

	@SavVariableMapping("SUPVIS")
	public Enum69 getWhetherSuperviseTheWorkOfOtherEmployees();

	@SavVariableMapping("SOLO")
	public Enum421 getWhetherHaveEmployees();

	@SavVariableMapping("FTPTWK")
	public Enum422 getMainJob_FullOrPart_Time();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("TECLEC4")
	public Enum424 getTypeOfTrainingScheme();

	@SavVariableMapping("MANAGE")
	public Enum425 getManagerialDuties_Type();

	@SavVariableMapping("LKTIMA")
	public Enum403 getLookingForWork_PlaceOnGovernmentScheme_TimeSpentLooking();

	@SavVariableMapping("SELFSOLE")
	public Enum69 getSoleDirectorOfOwnLimitedBusiness();

	@SavVariableMapping("PDWAGE")
	public Enum69 getPaidSalaryOrWageByEmployer();

	@SavVariableMapping("YTETMP")
	public Enum429 getActivityOnGovernmentTrainingScheme();

	@SavVariableMapping("MPNE02")
	public Enum430 getNumberOfEmployeesAtWorkplace();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("SELFPART")
	public Enum69 getPartnerInBusiness_ProfessionalPractice();

	@SavVariableMapping("NOLOSTNT")
	public Enum69 getStudent();

	@SavVariableMapping("SCHM08")
	public Enum434 getOnGovtTrainingSchemeInLastWeek();

	@SavVariableMapping("NEWDEA4")
	public Enum435 getNewDealOptions();

	@SavVariableMapping("NSSEC")
	public Enum436 getNationalStatisticsSocio_EconomicClassification_NS_SEC__LongVersion();

	@SavVariableMapping("SELFNONE")
	public Enum69 getNoneOfTheAbove();

	@SavVariableMapping("NDTYPE4")
	public Enum438 getTypeOfNewDealScheme();

	@SavVariableMapping("ES2000")
	public Enum439 getEmploymentStatus();

	@SavVariableMapping("MPNS02")
	public Enum430 getNumberOfPeopleEmploys();

	@SavVariableMapping("LOOK4")
	public Enum69 getWhetherLookingForPaidWorkInTheLast4Weeks();

	@SavVariableMapping("OWNBUS")
	public Enum69 getUnpaidWorkInThatWeekForOwnBusiness();

}

