package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.derived.types.Enum71;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum720;
import uk.org.cse.nhm.ehcs10.interview.types.Enum721;
import uk.org.cse.nhm.ehcs10.interview.types.Enum724;
import uk.org.cse.nhm.ehcs10.interview.types.Enum725;
import uk.org.cse.nhm.ehcs10.interview.types.Enum727;
import uk.org.cse.nhm.ehcs10.interview.types.Enum752;
import uk.org.cse.nhm.ehcs10.interview.types.Enum756;
import uk.org.cse.nhm.ehcs10.interview.types.Enum765;
import uk.org.cse.nhm.ehcs10.interview.types.Enum769;
import uk.org.cse.nhm.ehcs10.interview.types.Enum772;
import uk.org.cse.nhm.ehcs10.interview.types.Enum784;
import uk.org.cse.nhm.ehcs10.interview.types.Enum791;
import uk.org.cse.nhm.ehcs10.interview.types.Enum797;
import uk.org.cse.nhm.ehcs10.interview.types.Enum805;
import uk.org.cse.nhm.ehcs10.interview.types.Enum811;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface PeopleEntry extends SurveyEntry {
	@SavVariableMapping("AGE")
	public Integer getAge();

	@SavVariableMapping("JNTELDB")
	public Integer getEldestJointHhldr();

	@SavVariableMapping("AFAM")
	public Integer getFamilyUnit();

	@SavVariableMapping("WHOLODNO")
	public Integer getNumberOfPayingLodgersInHousehold();

	@SavVariableMapping("GROSSTEL")
	public Integer getTotalPersonalIncomeBeforeDeductions();

	@SavVariableMapping("TELDV")
	public Integer getDVForWeeklyAmount();

	@SavVariableMapping("HRPPART")
	public Integer getPersonNoOfHRP_SPartner();

	@SavVariableMapping("PERSNO")
	public Integer getPersonIdentifier();

	@SavVariableMapping("JNTELDA")
	public Integer getEldestJointHhldrWithHighestIncome();

	@SavVariableMapping("DVHSIZE")
	public Integer getNoOfPeopleInHhld();

	@SavVariableMapping("AGEIF")
	public Integer getAge_IfDOBUnknown_();

	@SavVariableMapping("DVBENU")
	public Integer getBenefitUnit();

	@SavVariableMapping("HRP")
	public Integer getPersonNumberOfHRP();

	@SavVariableMapping("NUMNONR2")
	public Integer getNumberOfPeopleNotDirectlyRelatedToHRP();

	@SavVariableMapping("EDAGECOR")
	public Integer getAgeFinishedFull_TimeEducation();

	@SavVariableMapping("HIHNUM")
	public Integer getJointHhldrWithHighestIncome();

	@SavVariableMapping("QUALWORK")
	public Double getWork_Related();

	@SavVariableMapping("QUALDKNW")
	public Double getD_KQualifications();

	@SavVariableMapping("QUALHOME")
	public Double getFromHomeEducation_WhenSchoolAge_();

	@SavVariableMapping("QUALNONE")
	public Double getHasNoneOfTheseQualifications();

	@SavVariableMapping("QUALSCHL")
	public Double getFromSchool_CollegeOrUniversity();

	@SavVariableMapping("QUALGVSM")
	public Double getFromGovernmentSchemes();

	@SavVariableMapping("QUALMDAP")
	public Double getFromAModernApprenticeship();

	@SavVariableMapping("V110_A")
	public Enum69 getFromSchoolOrHome_Schooling();

	@SavVariableMapping("TR05")
	public Enum720 getRelationshipToP5();

	@SavVariableMapping("R05")
	public Enum721 getRelationshipToP5_R05();

	@SavVariableMapping("SRCIALLW")
	public Enum69 getOtherRegularAllowance();

	@SavVariableMapping("TYPQWORK")
	public Enum69 getWorkRelatedOrVocational();

	@SavVariableMapping("QUALCHK")
	public Enum724 getQualCurrentlyWorkingFor();

	@SavVariableMapping("ATTENCOR")
	public Enum725 getStillAttendingCourse();

	@SavVariableMapping("HRPID")
	public Enum69 getPersonallyOwns_RentsAccom();

	@SavVariableMapping("HHLDR")
	public Enum727 getSole_JointHhldr();

	@SavVariableMapping("TYPQPROF")
	public Enum69 getProfessionalQualification();

	@SavVariableMapping("TYPQNONE")
	public Enum69 getNoFormalQualification();

	@SavVariableMapping("MARCHK")
	public Enum69 getHusband_Wife_PartnerInHhld();

	@SavVariableMapping("TR016")
	public Enum720 getRelationshipToP16();

	@SavVariableMapping("ECONPART")
	public Enum69 getWorkingLessThan30Hrs_Wk();

	@SavVariableMapping("ECONSTDT")
	public Enum69 getFull_TimeStudent();

	@SavVariableMapping("TR015")
	public Enum720 getRelationshipToP15();

	@SavVariableMapping("R12")
	public Enum721 getRelationshipToP12();

	@SavVariableMapping("SRCIRENT")
	public Enum69 getIncomeFromRent();

	@SavVariableMapping("V112_A")
	public Enum69 getWork_Related_V112_A();

	@SavVariableMapping("SRCIOTHR")
	public Enum69 getOtherIncomeSources();

	@SavVariableMapping("ENROLCOR")
	public Enum69 getEnrolledOnFT_PTCourse();

	@SavVariableMapping("R16")
	public Enum721 getRelationshipToP16_R16();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("ECONRTRD")
	public Enum69 getRetired_IncEarlyRetired_();

	@SavVariableMapping("SRCIINVT")
	public Enum69 getInterestFromInvestments();

	@SavVariableMapping("SRCISVNG")
	public Enum69 getInterestFromSavings();

	@SavVariableMapping("TR014")
	public Enum720 getRelationshipToP14();

	@SavVariableMapping("ECONSICK")
	public Enum69 getNotWorkingDueToLongTermSickness();

	@SavVariableMapping("R03")
	public Enum721 getRelationshipToP3();

	@SavVariableMapping("R10")
	public Enum721 getRelationshipToP10();

	@SavVariableMapping("SRCISPEN")
	public Enum69 getStatePension();

	@SavVariableMapping("R07")
	public Enum721 getRelationshipToP7();

	@SavVariableMapping("TYPQFRGN")
	public Enum69 getForeignQualification();

	@SavVariableMapping("NOOFHRSR")
	public Enum752 getHoursNormallyWorked_Wk();

	@SavVariableMapping("TR08")
	public Enum720 getRelationshipToP8();

	@SavVariableMapping("SRCIISUP")
	public Enum69 getIncomeSupport();

	@SavVariableMapping("SEX")
	public Enum71 getSex();

	@SavVariableMapping("RELTOP")
	public Enum756 getRelationshipToPartner();

	@SavVariableMapping("SRCIFEMP")
	public Enum69 getPensionFromFormerEmployer();

	@SavVariableMapping("R15")
	public Enum721 getRelationshipToP15_R15();

	@SavVariableMapping("R02")
	public Enum721 getRelationshipToP2();

	@SavVariableMapping("V113_A")
	public Enum69 getFromGovernmentSchemes_V113_A();

	@SavVariableMapping("TR06")
	public Enum720 getRelationshipToP6();

	@SavVariableMapping("ECONOTHR")
	public Enum69 getOtherWorkStatus();

	@SavVariableMapping("ECONSKNG")
	public Enum69 getNotRegisteredUnemployedButSeekingWork();

	@SavVariableMapping("R09")
	public Enum721 getRelationshipToP9();

	@SavVariableMapping("TELBAND")
	public Enum765 getBandRepresentingTotalPersonalIncomeBeforeAllDeductions();

	@SavVariableMapping("TR04")
	public Enum720 getRelationshipToP4();

	@SavVariableMapping("TR012")
	public Enum720 getRelationshipToP12_TR012();

	@SavVariableMapping("SRCITXCR")
	public Enum69 getTaxCredits();

	@SavVariableMapping("COURSCOR")
	public Enum769 getTypeOfEducationCourse();

	@SavVariableMapping("TR013")
	public Enum720 getRelationshipToP13();

	@SavVariableMapping("R13")
	public Enum721 getRelationshipToP13_R13();

	@SavVariableMapping("HIGHED1")
	public Enum772 getHighestLevelOfQualification();

	@SavVariableMapping("SRCICBEN")
	public Enum69 getChildBenefit();

	@SavVariableMapping("R06")
	public Enum721 getRelationshipToP6_R06();

	@SavVariableMapping("TR01")
	public Enum720 getRelationshipToP1();

	@SavVariableMapping("V114_A")
	public Enum69 getFromAModernApprenticeship_V114_A();

	@SavVariableMapping("TR011")
	public Enum720 getRelationshipToP11();

	@SavVariableMapping("DVWORK")
	public Enum69 getDV_InPaidWork_();

	@SavVariableMapping("TR02")
	public Enum720 getRelationshipToP2_TR02();

	@SavVariableMapping("QUALLTST")
	public Enum69 getGainedInLeisureTimeOrSelfTaught();

	@SavVariableMapping("ECONFULL")
	public Enum69 getWorking30Hrs_WkOrMore();

	@SavVariableMapping("SRCIPPEN")
	public Enum69 getPersonalPension();

	@SavVariableMapping("LODGER")
	public Enum69 getWhetherPaysRentAsALodger();

	@SavVariableMapping("GRSSTIME")
	public Enum784 getIsThatWeekly_MonthlyOrAnnualAmount_();

	@SavVariableMapping("R01")
	public Enum721 getRelationshipToP1_R01();

	@SavVariableMapping("SRCIEMPY")
	public Enum69 getEarningsFromEmployment();

	@SavVariableMapping("TR09")
	public Enum720 getRelationshipToP9_TR09();

	@SavVariableMapping("LIVWTH")
	public Enum69 getLivingTogetherAsACouple();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("SRCINONE")
	public Enum69 getNoSourceOfIncome();

	@SavVariableMapping("XMARSTA")
	public Enum791 getLegalMaritalStatus();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("TR07")
	public Enum720 getRelationshipToP7_TR07();

	@SavVariableMapping("V118_A")
	public Enum69 getD_KQualifications_V118_A();

	@SavVariableMapping("QUALOTHR")
	public Enum69 getObtainedInSomeOtherWay();

	@SavVariableMapping("R14")
	public Enum721 getRelationshipToP14_R14();

	@SavVariableMapping("RELTOHRP")
	public Enum797 getRelationshipToHrp();

	@SavVariableMapping("ECONRGUN")
	public Enum69 getRegisteredUnemployed();

	@SavVariableMapping("TR03")
	public Enum720 getRelationshipToP3_TR03();

	@SavVariableMapping("R08")
	public Enum721 getRelationshipToP8_R08();

	@SavVariableMapping("HALLRES")
	public Enum69 getLivingInHallsOfResidence();

	@SavVariableMapping("R11")
	public Enum721 getRelationshipToP11_R11();

	@SavVariableMapping("R04")
	public Enum721 getRelationshipToP4_R04();

	@SavVariableMapping("V117_A")
	public Enum69 getHasNoneOfTheseQualifications_V117_A();

	@SavVariableMapping("INCPROX")
	public Enum805 getIncomeProxy();

	@SavVariableMapping("QUALCOLL")
	public Enum69 getFromCollegeOrUniversity();

	@SavVariableMapping("SRCISELF")
	public Enum69 getEarningsFromSelf_Employment();

	@SavVariableMapping("ECONNSKG")
	public Enum69 getAtHomeNotSeekingWork();

	@SavVariableMapping("TR010")
	public Enum720 getRelationshipToP10_TR010();

	@SavVariableMapping("ECONGOVT")
	public Enum69 getWorkingGovernmentTrainingScheme();

	@SavVariableMapping("WHINFORM")
	public Enum811 getWhyIsPersonLivingHere();

	@SavVariableMapping("SRCIOBEN")
	public Enum69 getOtherStateBenefits();

}

