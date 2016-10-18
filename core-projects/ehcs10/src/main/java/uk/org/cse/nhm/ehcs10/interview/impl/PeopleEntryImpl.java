package uk.org.cse.nhm.ehcs10.interview.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.derived.types.Enum71;
import uk.org.cse.nhm.ehcs10.interview.PeopleEntry;
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
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class PeopleEntryImpl extends SurveyEntryImpl implements PeopleEntry {
	private Integer Age;
	private Integer EldestJointHhldr;
	private Integer FamilyUnit;
	private Integer NumberOfPayingLodgersInHousehold;
	private Integer TotalPersonalIncomeBeforeDeductions;
	private Integer DVForWeeklyAmount;
	private Integer PersonNoOfHRP_SPartner;
	private Integer PersonIdentifier;
	private Integer EldestJointHhldrWithHighestIncome;
	private Integer NoOfPeopleInHhld;
	private Integer Age_IfDOBUnknown_;
	private Integer BenefitUnit;
	private Integer PersonNumberOfHRP;
	private Integer NumberOfPeopleNotDirectlyRelatedToHRP;
	private Integer AgeFinishedFull_TimeEducation;
	private Integer JointHhldrWithHighestIncome;
	private Double Work_Related;
	private Double D_KQualifications;
	private Double FromHomeEducation_WhenSchoolAge_;
	private Double HasNoneOfTheseQualifications;
	private Double FromSchool_CollegeOrUniversity;
	private Double FromGovernmentSchemes;
	private Double FromAModernApprenticeship;
	private Enum69 FromSchoolOrHome_Schooling;
	private Enum720 RelationshipToP5;
	private Enum721 RelationshipToP5_R05;
	private Enum69 OtherRegularAllowance;
	private Enum69 WorkRelatedOrVocational;
	private Enum724 QualCurrentlyWorkingFor;
	private Enum725 StillAttendingCourse;
	private Enum69 PersonallyOwns_RentsAccom;
	private Enum727 Sole_JointHhldr;
	private Enum69 ProfessionalQualification;
	private Enum69 NoFormalQualification;
	private Enum69 Husband_Wife_PartnerInHhld;
	private Enum720 RelationshipToP16;
	private Enum69 WorkingLessThan30Hrs_Wk;
	private Enum69 Full_TimeStudent;
	private Enum720 RelationshipToP15;
	private Enum721 RelationshipToP12;
	private Enum69 IncomeFromRent;
	private Enum69 Work_Related_V112_A;
	private Enum69 OtherIncomeSources;
	private Enum69 EnrolledOnFT_PTCourse;
	private Enum721 RelationshipToP16_R16;
	private Enum229 StatusOfCase;
	private Enum69 Retired_IncEarlyRetired_;
	private Enum69 InterestFromInvestments;
	private Enum69 InterestFromSavings;
	private Enum720 RelationshipToP14;
	private Enum69 NotWorkingDueToLongTermSickness;
	private Enum721 RelationshipToP3;
	private Enum721 RelationshipToP10;
	private Enum69 StatePension;
	private Enum721 RelationshipToP7;
	private Enum69 ForeignQualification;
	private Enum752 HoursNormallyWorked_Wk;
	private Enum720 RelationshipToP8;
	private Enum69 IncomeSupport;
	private Enum71 Sex;
	private Enum756 RelationshipToPartner;
	private Enum69 PensionFromFormerEmployer;
	private Enum721 RelationshipToP15_R15;
	private Enum721 RelationshipToP2;
	private Enum69 FromGovernmentSchemes_V113_A;
	private Enum720 RelationshipToP6;
	private Enum69 OtherWorkStatus;
	private Enum69 NotRegisteredUnemployedButSeekingWork;
	private Enum721 RelationshipToP9;
	private Enum765 BandRepresentingTotalPersonalIncomeBeforeAllDeductions;
	private Enum720 RelationshipToP4;
	private Enum720 RelationshipToP12_TR012;
	private Enum69 TaxCredits;
	private Enum769 TypeOfEducationCourse;
	private Enum720 RelationshipToP13;
	private Enum721 RelationshipToP13_R13;
	private Enum772 HighestLevelOfQualification;
	private Enum69 ChildBenefit;
	private Enum721 RelationshipToP6_R06;
	private Enum720 RelationshipToP1;
	private Enum69 FromAModernApprenticeship_V114_A;
	private Enum720 RelationshipToP11;
	private Enum69 DV_InPaidWork_;
	private Enum720 RelationshipToP2_TR02;
	private Enum69 GainedInLeisureTimeOrSelfTaught;
	private Enum69 Working30Hrs_WkOrMore;
	private Enum69 PersonalPension;
	private Enum69 WhetherPaysRentAsALodger;
	private Enum784 IsThatWeekly_MonthlyOrAnnualAmount_;
	private Enum721 RelationshipToP1_R01;
	private Enum69 EarningsFromEmployment;
	private Enum720 RelationshipToP9_TR09;
	private Enum69 LivingTogetherAsACouple;
	private Enum230 FieldworkQuarter;
	private Enum69 NoSourceOfIncome;
	private Enum791 LegalMaritalStatus;
	private Enum30 Region_EHSOrder;
	private Enum720 RelationshipToP7_TR07;
	private Enum69 D_KQualifications_V118_A;
	private Enum69 ObtainedInSomeOtherWay;
	private Enum721 RelationshipToP14_R14;
	private Enum797 RelationshipToHrp;
	private Enum69 RegisteredUnemployed;
	private Enum720 RelationshipToP3_TR03;
	private Enum721 RelationshipToP8_R08;
	private Enum69 LivingInHallsOfResidence;
	private Enum721 RelationshipToP11_R11;
	private Enum721 RelationshipToP4_R04;
	private Enum69 HasNoneOfTheseQualifications_V117_A;
	private Enum805 IncomeProxy;
	private Enum69 FromCollegeOrUniversity;
	private Enum69 EarningsFromSelf_Employment;
	private Enum69 AtHomeNotSeekingWork;
	private Enum720 RelationshipToP10_TR010;
	private Enum69 WorkingGovernmentTrainingScheme;
	private Enum811 WhyIsPersonLivingHere;
	private Enum69 OtherStateBenefits;
	public Integer getAge() {
		return Age;	}

	public void setAge(final Integer Age) {
		this.Age = Age;	}

	public Integer getEldestJointHhldr() {
		return EldestJointHhldr;	}

	public void setEldestJointHhldr(final Integer EldestJointHhldr) {
		this.EldestJointHhldr = EldestJointHhldr;	}

	public Integer getFamilyUnit() {
		return FamilyUnit;	}

	public void setFamilyUnit(final Integer FamilyUnit) {
		this.FamilyUnit = FamilyUnit;	}

	public Integer getNumberOfPayingLodgersInHousehold() {
		return NumberOfPayingLodgersInHousehold;	}

	public void setNumberOfPayingLodgersInHousehold(final Integer NumberOfPayingLodgersInHousehold) {
		this.NumberOfPayingLodgersInHousehold = NumberOfPayingLodgersInHousehold;	}

	public Integer getTotalPersonalIncomeBeforeDeductions() {
		return TotalPersonalIncomeBeforeDeductions;	}

	public void setTotalPersonalIncomeBeforeDeductions(final Integer TotalPersonalIncomeBeforeDeductions) {
		this.TotalPersonalIncomeBeforeDeductions = TotalPersonalIncomeBeforeDeductions;	}

	public Integer getDVForWeeklyAmount() {
		return DVForWeeklyAmount;	}

	public void setDVForWeeklyAmount(final Integer DVForWeeklyAmount) {
		this.DVForWeeklyAmount = DVForWeeklyAmount;	}

	public Integer getPersonNoOfHRP_SPartner() {
		return PersonNoOfHRP_SPartner;	}

	public void setPersonNoOfHRP_SPartner(final Integer PersonNoOfHRP_SPartner) {
		this.PersonNoOfHRP_SPartner = PersonNoOfHRP_SPartner;	}

	public Integer getPersonIdentifier() {
		return PersonIdentifier;	}

	public void setPersonIdentifier(final Integer PersonIdentifier) {
		this.PersonIdentifier = PersonIdentifier;	}

	public Integer getEldestJointHhldrWithHighestIncome() {
		return EldestJointHhldrWithHighestIncome;	}

	public void setEldestJointHhldrWithHighestIncome(final Integer EldestJointHhldrWithHighestIncome) {
		this.EldestJointHhldrWithHighestIncome = EldestJointHhldrWithHighestIncome;	}

	public Integer getNoOfPeopleInHhld() {
		return NoOfPeopleInHhld;	}

	public void setNoOfPeopleInHhld(final Integer NoOfPeopleInHhld) {
		this.NoOfPeopleInHhld = NoOfPeopleInHhld;	}

	public Integer getAge_IfDOBUnknown_() {
		return Age_IfDOBUnknown_;	}

	public void setAge_IfDOBUnknown_(final Integer Age_IfDOBUnknown_) {
		this.Age_IfDOBUnknown_ = Age_IfDOBUnknown_;	}

	public Integer getBenefitUnit() {
		return BenefitUnit;	}

	public void setBenefitUnit(final Integer BenefitUnit) {
		this.BenefitUnit = BenefitUnit;	}

	public Integer getPersonNumberOfHRP() {
		return PersonNumberOfHRP;	}

	public void setPersonNumberOfHRP(final Integer PersonNumberOfHRP) {
		this.PersonNumberOfHRP = PersonNumberOfHRP;	}

	public Integer getNumberOfPeopleNotDirectlyRelatedToHRP() {
		return NumberOfPeopleNotDirectlyRelatedToHRP;	}

	public void setNumberOfPeopleNotDirectlyRelatedToHRP(final Integer NumberOfPeopleNotDirectlyRelatedToHRP) {
		this.NumberOfPeopleNotDirectlyRelatedToHRP = NumberOfPeopleNotDirectlyRelatedToHRP;	}

	public Integer getAgeFinishedFull_TimeEducation() {
		return AgeFinishedFull_TimeEducation;	}

	public void setAgeFinishedFull_TimeEducation(final Integer AgeFinishedFull_TimeEducation) {
		this.AgeFinishedFull_TimeEducation = AgeFinishedFull_TimeEducation;	}

	public Integer getJointHhldrWithHighestIncome() {
		return JointHhldrWithHighestIncome;	}

	public void setJointHhldrWithHighestIncome(final Integer JointHhldrWithHighestIncome) {
		this.JointHhldrWithHighestIncome = JointHhldrWithHighestIncome;	}

	public Double getWork_Related() {
		return Work_Related;	}

	public void setWork_Related(final Double Work_Related) {
		this.Work_Related = Work_Related;	}

	public Double getD_KQualifications() {
		return D_KQualifications;	}

	public void setD_KQualifications(final Double D_KQualifications) {
		this.D_KQualifications = D_KQualifications;	}

	public Double getFromHomeEducation_WhenSchoolAge_() {
		return FromHomeEducation_WhenSchoolAge_;	}

	public void setFromHomeEducation_WhenSchoolAge_(final Double FromHomeEducation_WhenSchoolAge_) {
		this.FromHomeEducation_WhenSchoolAge_ = FromHomeEducation_WhenSchoolAge_;	}

	public Double getHasNoneOfTheseQualifications() {
		return HasNoneOfTheseQualifications;	}

	public void setHasNoneOfTheseQualifications(final Double HasNoneOfTheseQualifications) {
		this.HasNoneOfTheseQualifications = HasNoneOfTheseQualifications;	}

	public Double getFromSchool_CollegeOrUniversity() {
		return FromSchool_CollegeOrUniversity;	}

	public void setFromSchool_CollegeOrUniversity(final Double FromSchool_CollegeOrUniversity) {
		this.FromSchool_CollegeOrUniversity = FromSchool_CollegeOrUniversity;	}

	public Double getFromGovernmentSchemes() {
		return FromGovernmentSchemes;	}

	public void setFromGovernmentSchemes(final Double FromGovernmentSchemes) {
		this.FromGovernmentSchemes = FromGovernmentSchemes;	}

	public Double getFromAModernApprenticeship() {
		return FromAModernApprenticeship;	}

	public void setFromAModernApprenticeship(final Double FromAModernApprenticeship) {
		this.FromAModernApprenticeship = FromAModernApprenticeship;	}

	public Enum69 getFromSchoolOrHome_Schooling() {
		return FromSchoolOrHome_Schooling;	}

	public void setFromSchoolOrHome_Schooling(final Enum69 FromSchoolOrHome_Schooling) {
		this.FromSchoolOrHome_Schooling = FromSchoolOrHome_Schooling;	}

	public Enum720 getRelationshipToP5() {
		return RelationshipToP5;	}

	public void setRelationshipToP5(final Enum720 RelationshipToP5) {
		this.RelationshipToP5 = RelationshipToP5;	}

	public Enum721 getRelationshipToP5_R05() {
		return RelationshipToP5_R05;	}

	public void setRelationshipToP5_R05(final Enum721 RelationshipToP5_R05) {
		this.RelationshipToP5_R05 = RelationshipToP5_R05;	}

	public Enum69 getOtherRegularAllowance() {
		return OtherRegularAllowance;	}

	public void setOtherRegularAllowance(final Enum69 OtherRegularAllowance) {
		this.OtherRegularAllowance = OtherRegularAllowance;	}

	public Enum69 getWorkRelatedOrVocational() {
		return WorkRelatedOrVocational;	}

	public void setWorkRelatedOrVocational(final Enum69 WorkRelatedOrVocational) {
		this.WorkRelatedOrVocational = WorkRelatedOrVocational;	}

	public Enum724 getQualCurrentlyWorkingFor() {
		return QualCurrentlyWorkingFor;	}

	public void setQualCurrentlyWorkingFor(final Enum724 QualCurrentlyWorkingFor) {
		this.QualCurrentlyWorkingFor = QualCurrentlyWorkingFor;	}

	public Enum725 getStillAttendingCourse() {
		return StillAttendingCourse;	}

	public void setStillAttendingCourse(final Enum725 StillAttendingCourse) {
		this.StillAttendingCourse = StillAttendingCourse;	}

	public Enum69 getPersonallyOwns_RentsAccom() {
		return PersonallyOwns_RentsAccom;	}

	public void setPersonallyOwns_RentsAccom(final Enum69 PersonallyOwns_RentsAccom) {
		this.PersonallyOwns_RentsAccom = PersonallyOwns_RentsAccom;	}

	public Enum727 getSole_JointHhldr() {
		return Sole_JointHhldr;	}

	public void setSole_JointHhldr(final Enum727 Sole_JointHhldr) {
		this.Sole_JointHhldr = Sole_JointHhldr;	}

	public Enum69 getProfessionalQualification() {
		return ProfessionalQualification;	}

	public void setProfessionalQualification(final Enum69 ProfessionalQualification) {
		this.ProfessionalQualification = ProfessionalQualification;	}

	public Enum69 getNoFormalQualification() {
		return NoFormalQualification;	}

	public void setNoFormalQualification(final Enum69 NoFormalQualification) {
		this.NoFormalQualification = NoFormalQualification;	}

	public Enum69 getHusband_Wife_PartnerInHhld() {
		return Husband_Wife_PartnerInHhld;	}

	public void setHusband_Wife_PartnerInHhld(final Enum69 Husband_Wife_PartnerInHhld) {
		this.Husband_Wife_PartnerInHhld = Husband_Wife_PartnerInHhld;	}

	public Enum720 getRelationshipToP16() {
		return RelationshipToP16;	}

	public void setRelationshipToP16(final Enum720 RelationshipToP16) {
		this.RelationshipToP16 = RelationshipToP16;	}

	public Enum69 getWorkingLessThan30Hrs_Wk() {
		return WorkingLessThan30Hrs_Wk;	}

	public void setWorkingLessThan30Hrs_Wk(final Enum69 WorkingLessThan30Hrs_Wk) {
		this.WorkingLessThan30Hrs_Wk = WorkingLessThan30Hrs_Wk;	}

	public Enum69 getFull_TimeStudent() {
		return Full_TimeStudent;	}

	public void setFull_TimeStudent(final Enum69 Full_TimeStudent) {
		this.Full_TimeStudent = Full_TimeStudent;	}

	public Enum720 getRelationshipToP15() {
		return RelationshipToP15;	}

	public void setRelationshipToP15(final Enum720 RelationshipToP15) {
		this.RelationshipToP15 = RelationshipToP15;	}

	public Enum721 getRelationshipToP12() {
		return RelationshipToP12;	}

	public void setRelationshipToP12(final Enum721 RelationshipToP12) {
		this.RelationshipToP12 = RelationshipToP12;	}

	public Enum69 getIncomeFromRent() {
		return IncomeFromRent;	}

	public void setIncomeFromRent(final Enum69 IncomeFromRent) {
		this.IncomeFromRent = IncomeFromRent;	}

	public Enum69 getWork_Related_V112_A() {
		return Work_Related_V112_A;	}

	public void setWork_Related_V112_A(final Enum69 Work_Related_V112_A) {
		this.Work_Related_V112_A = Work_Related_V112_A;	}

	public Enum69 getOtherIncomeSources() {
		return OtherIncomeSources;	}

	public void setOtherIncomeSources(final Enum69 OtherIncomeSources) {
		this.OtherIncomeSources = OtherIncomeSources;	}

	public Enum69 getEnrolledOnFT_PTCourse() {
		return EnrolledOnFT_PTCourse;	}

	public void setEnrolledOnFT_PTCourse(final Enum69 EnrolledOnFT_PTCourse) {
		this.EnrolledOnFT_PTCourse = EnrolledOnFT_PTCourse;	}

	public Enum721 getRelationshipToP16_R16() {
		return RelationshipToP16_R16;	}

	public void setRelationshipToP16_R16(final Enum721 RelationshipToP16_R16) {
		this.RelationshipToP16_R16 = RelationshipToP16_R16;	}

	public Enum229 getStatusOfCase() {
		return StatusOfCase;	}

	public void setStatusOfCase(final Enum229 StatusOfCase) {
		this.StatusOfCase = StatusOfCase;	}

	public Enum69 getRetired_IncEarlyRetired_() {
		return Retired_IncEarlyRetired_;	}

	public void setRetired_IncEarlyRetired_(final Enum69 Retired_IncEarlyRetired_) {
		this.Retired_IncEarlyRetired_ = Retired_IncEarlyRetired_;	}

	public Enum69 getInterestFromInvestments() {
		return InterestFromInvestments;	}

	public void setInterestFromInvestments(final Enum69 InterestFromInvestments) {
		this.InterestFromInvestments = InterestFromInvestments;	}

	public Enum69 getInterestFromSavings() {
		return InterestFromSavings;	}

	public void setInterestFromSavings(final Enum69 InterestFromSavings) {
		this.InterestFromSavings = InterestFromSavings;	}

	public Enum720 getRelationshipToP14() {
		return RelationshipToP14;	}

	public void setRelationshipToP14(final Enum720 RelationshipToP14) {
		this.RelationshipToP14 = RelationshipToP14;	}

	public Enum69 getNotWorkingDueToLongTermSickness() {
		return NotWorkingDueToLongTermSickness;	}

	public void setNotWorkingDueToLongTermSickness(final Enum69 NotWorkingDueToLongTermSickness) {
		this.NotWorkingDueToLongTermSickness = NotWorkingDueToLongTermSickness;	}

	public Enum721 getRelationshipToP3() {
		return RelationshipToP3;	}

	public void setRelationshipToP3(final Enum721 RelationshipToP3) {
		this.RelationshipToP3 = RelationshipToP3;	}

	public Enum721 getRelationshipToP10() {
		return RelationshipToP10;	}

	public void setRelationshipToP10(final Enum721 RelationshipToP10) {
		this.RelationshipToP10 = RelationshipToP10;	}

	public Enum69 getStatePension() {
		return StatePension;	}

	public void setStatePension(final Enum69 StatePension) {
		this.StatePension = StatePension;	}

	public Enum721 getRelationshipToP7() {
		return RelationshipToP7;	}

	public void setRelationshipToP7(final Enum721 RelationshipToP7) {
		this.RelationshipToP7 = RelationshipToP7;	}

	public Enum69 getForeignQualification() {
		return ForeignQualification;	}

	public void setForeignQualification(final Enum69 ForeignQualification) {
		this.ForeignQualification = ForeignQualification;	}

	public Enum752 getHoursNormallyWorked_Wk() {
		return HoursNormallyWorked_Wk;	}

	public void setHoursNormallyWorked_Wk(final Enum752 HoursNormallyWorked_Wk) {
		this.HoursNormallyWorked_Wk = HoursNormallyWorked_Wk;	}

	public Enum720 getRelationshipToP8() {
		return RelationshipToP8;	}

	public void setRelationshipToP8(final Enum720 RelationshipToP8) {
		this.RelationshipToP8 = RelationshipToP8;	}

	public Enum69 getIncomeSupport() {
		return IncomeSupport;	}

	public void setIncomeSupport(final Enum69 IncomeSupport) {
		this.IncomeSupport = IncomeSupport;	}

	public Enum71 getSex() {
		return Sex;	}

	public void setSex(final Enum71 Sex) {
		this.Sex = Sex;	}

	public Enum756 getRelationshipToPartner() {
		return RelationshipToPartner;	}

	public void setRelationshipToPartner(final Enum756 RelationshipToPartner) {
		this.RelationshipToPartner = RelationshipToPartner;	}

	public Enum69 getPensionFromFormerEmployer() {
		return PensionFromFormerEmployer;	}

	public void setPensionFromFormerEmployer(final Enum69 PensionFromFormerEmployer) {
		this.PensionFromFormerEmployer = PensionFromFormerEmployer;	}

	public Enum721 getRelationshipToP15_R15() {
		return RelationshipToP15_R15;	}

	public void setRelationshipToP15_R15(final Enum721 RelationshipToP15_R15) {
		this.RelationshipToP15_R15 = RelationshipToP15_R15;	}

	public Enum721 getRelationshipToP2() {
		return RelationshipToP2;	}

	public void setRelationshipToP2(final Enum721 RelationshipToP2) {
		this.RelationshipToP2 = RelationshipToP2;	}

	public Enum69 getFromGovernmentSchemes_V113_A() {
		return FromGovernmentSchemes_V113_A;	}

	public void setFromGovernmentSchemes_V113_A(final Enum69 FromGovernmentSchemes_V113_A) {
		this.FromGovernmentSchemes_V113_A = FromGovernmentSchemes_V113_A;	}

	public Enum720 getRelationshipToP6() {
		return RelationshipToP6;	}

	public void setRelationshipToP6(final Enum720 RelationshipToP6) {
		this.RelationshipToP6 = RelationshipToP6;	}

	public Enum69 getOtherWorkStatus() {
		return OtherWorkStatus;	}

	public void setOtherWorkStatus(final Enum69 OtherWorkStatus) {
		this.OtherWorkStatus = OtherWorkStatus;	}

	public Enum69 getNotRegisteredUnemployedButSeekingWork() {
		return NotRegisteredUnemployedButSeekingWork;	}

	public void setNotRegisteredUnemployedButSeekingWork(final Enum69 NotRegisteredUnemployedButSeekingWork) {
		this.NotRegisteredUnemployedButSeekingWork = NotRegisteredUnemployedButSeekingWork;	}

	public Enum721 getRelationshipToP9() {
		return RelationshipToP9;	}

	public void setRelationshipToP9(final Enum721 RelationshipToP9) {
		this.RelationshipToP9 = RelationshipToP9;	}

	public Enum765 getBandRepresentingTotalPersonalIncomeBeforeAllDeductions() {
		return BandRepresentingTotalPersonalIncomeBeforeAllDeductions;	}

	public void setBandRepresentingTotalPersonalIncomeBeforeAllDeductions(final Enum765 BandRepresentingTotalPersonalIncomeBeforeAllDeductions) {
		this.BandRepresentingTotalPersonalIncomeBeforeAllDeductions = BandRepresentingTotalPersonalIncomeBeforeAllDeductions;	}

	public Enum720 getRelationshipToP4() {
		return RelationshipToP4;	}

	public void setRelationshipToP4(final Enum720 RelationshipToP4) {
		this.RelationshipToP4 = RelationshipToP4;	}

	public Enum720 getRelationshipToP12_TR012() {
		return RelationshipToP12_TR012;	}

	public void setRelationshipToP12_TR012(final Enum720 RelationshipToP12_TR012) {
		this.RelationshipToP12_TR012 = RelationshipToP12_TR012;	}

	public Enum69 getTaxCredits() {
		return TaxCredits;	}

	public void setTaxCredits(final Enum69 TaxCredits) {
		this.TaxCredits = TaxCredits;	}

	public Enum769 getTypeOfEducationCourse() {
		return TypeOfEducationCourse;	}

	public void setTypeOfEducationCourse(final Enum769 TypeOfEducationCourse) {
		this.TypeOfEducationCourse = TypeOfEducationCourse;	}

	public Enum720 getRelationshipToP13() {
		return RelationshipToP13;	}

	public void setRelationshipToP13(final Enum720 RelationshipToP13) {
		this.RelationshipToP13 = RelationshipToP13;	}

	public Enum721 getRelationshipToP13_R13() {
		return RelationshipToP13_R13;	}

	public void setRelationshipToP13_R13(final Enum721 RelationshipToP13_R13) {
		this.RelationshipToP13_R13 = RelationshipToP13_R13;	}

	public Enum772 getHighestLevelOfQualification() {
		return HighestLevelOfQualification;	}

	public void setHighestLevelOfQualification(final Enum772 HighestLevelOfQualification) {
		this.HighestLevelOfQualification = HighestLevelOfQualification;	}

	public Enum69 getChildBenefit() {
		return ChildBenefit;	}

	public void setChildBenefit(final Enum69 ChildBenefit) {
		this.ChildBenefit = ChildBenefit;	}

	public Enum721 getRelationshipToP6_R06() {
		return RelationshipToP6_R06;	}

	public void setRelationshipToP6_R06(final Enum721 RelationshipToP6_R06) {
		this.RelationshipToP6_R06 = RelationshipToP6_R06;	}

	public Enum720 getRelationshipToP1() {
		return RelationshipToP1;	}

	public void setRelationshipToP1(final Enum720 RelationshipToP1) {
		this.RelationshipToP1 = RelationshipToP1;	}

	public Enum69 getFromAModernApprenticeship_V114_A() {
		return FromAModernApprenticeship_V114_A;	}

	public void setFromAModernApprenticeship_V114_A(final Enum69 FromAModernApprenticeship_V114_A) {
		this.FromAModernApprenticeship_V114_A = FromAModernApprenticeship_V114_A;	}

	public Enum720 getRelationshipToP11() {
		return RelationshipToP11;	}

	public void setRelationshipToP11(final Enum720 RelationshipToP11) {
		this.RelationshipToP11 = RelationshipToP11;	}

	public Enum69 getDV_InPaidWork_() {
		return DV_InPaidWork_;	}

	public void setDV_InPaidWork_(final Enum69 DV_InPaidWork_) {
		this.DV_InPaidWork_ = DV_InPaidWork_;	}

	public Enum720 getRelationshipToP2_TR02() {
		return RelationshipToP2_TR02;	}

	public void setRelationshipToP2_TR02(final Enum720 RelationshipToP2_TR02) {
		this.RelationshipToP2_TR02 = RelationshipToP2_TR02;	}

	public Enum69 getGainedInLeisureTimeOrSelfTaught() {
		return GainedInLeisureTimeOrSelfTaught;	}

	public void setGainedInLeisureTimeOrSelfTaught(final Enum69 GainedInLeisureTimeOrSelfTaught) {
		this.GainedInLeisureTimeOrSelfTaught = GainedInLeisureTimeOrSelfTaught;	}

	public Enum69 getWorking30Hrs_WkOrMore() {
		return Working30Hrs_WkOrMore;	}

	public void setWorking30Hrs_WkOrMore(final Enum69 Working30Hrs_WkOrMore) {
		this.Working30Hrs_WkOrMore = Working30Hrs_WkOrMore;	}

	public Enum69 getPersonalPension() {
		return PersonalPension;	}

	public void setPersonalPension(final Enum69 PersonalPension) {
		this.PersonalPension = PersonalPension;	}

	public Enum69 getWhetherPaysRentAsALodger() {
		return WhetherPaysRentAsALodger;	}

	public void setWhetherPaysRentAsALodger(final Enum69 WhetherPaysRentAsALodger) {
		this.WhetherPaysRentAsALodger = WhetherPaysRentAsALodger;	}

	public Enum784 getIsThatWeekly_MonthlyOrAnnualAmount_() {
		return IsThatWeekly_MonthlyOrAnnualAmount_;	}

	public void setIsThatWeekly_MonthlyOrAnnualAmount_(final Enum784 IsThatWeekly_MonthlyOrAnnualAmount_) {
		this.IsThatWeekly_MonthlyOrAnnualAmount_ = IsThatWeekly_MonthlyOrAnnualAmount_;	}

	public Enum721 getRelationshipToP1_R01() {
		return RelationshipToP1_R01;	}

	public void setRelationshipToP1_R01(final Enum721 RelationshipToP1_R01) {
		this.RelationshipToP1_R01 = RelationshipToP1_R01;	}

	public Enum69 getEarningsFromEmployment() {
		return EarningsFromEmployment;	}

	public void setEarningsFromEmployment(final Enum69 EarningsFromEmployment) {
		this.EarningsFromEmployment = EarningsFromEmployment;	}

	public Enum720 getRelationshipToP9_TR09() {
		return RelationshipToP9_TR09;	}

	public void setRelationshipToP9_TR09(final Enum720 RelationshipToP9_TR09) {
		this.RelationshipToP9_TR09 = RelationshipToP9_TR09;	}

	public Enum69 getLivingTogetherAsACouple() {
		return LivingTogetherAsACouple;	}

	public void setLivingTogetherAsACouple(final Enum69 LivingTogetherAsACouple) {
		this.LivingTogetherAsACouple = LivingTogetherAsACouple;	}

	public Enum230 getFieldworkQuarter() {
		return FieldworkQuarter;	}

	public void setFieldworkQuarter(final Enum230 FieldworkQuarter) {
		this.FieldworkQuarter = FieldworkQuarter;	}

	public Enum69 getNoSourceOfIncome() {
		return NoSourceOfIncome;	}

	public void setNoSourceOfIncome(final Enum69 NoSourceOfIncome) {
		this.NoSourceOfIncome = NoSourceOfIncome;	}

	public Enum791 getLegalMaritalStatus() {
		return LegalMaritalStatus;	}

	public void setLegalMaritalStatus(final Enum791 LegalMaritalStatus) {
		this.LegalMaritalStatus = LegalMaritalStatus;	}

	public Enum30 getRegion_EHSOrder() {
		return Region_EHSOrder;	}

	public void setRegion_EHSOrder(final Enum30 Region_EHSOrder) {
		this.Region_EHSOrder = Region_EHSOrder;	}

	public Enum720 getRelationshipToP7_TR07() {
		return RelationshipToP7_TR07;	}

	public void setRelationshipToP7_TR07(final Enum720 RelationshipToP7_TR07) {
		this.RelationshipToP7_TR07 = RelationshipToP7_TR07;	}

	public Enum69 getD_KQualifications_V118_A() {
		return D_KQualifications_V118_A;	}

	public void setD_KQualifications_V118_A(final Enum69 D_KQualifications_V118_A) {
		this.D_KQualifications_V118_A = D_KQualifications_V118_A;	}

	public Enum69 getObtainedInSomeOtherWay() {
		return ObtainedInSomeOtherWay;	}

	public void setObtainedInSomeOtherWay(final Enum69 ObtainedInSomeOtherWay) {
		this.ObtainedInSomeOtherWay = ObtainedInSomeOtherWay;	}

	public Enum721 getRelationshipToP14_R14() {
		return RelationshipToP14_R14;	}

	public void setRelationshipToP14_R14(final Enum721 RelationshipToP14_R14) {
		this.RelationshipToP14_R14 = RelationshipToP14_R14;	}

	public Enum797 getRelationshipToHrp() {
		return RelationshipToHrp;	}

	public void setRelationshipToHrp(final Enum797 RelationshipToHrp) {
		this.RelationshipToHrp = RelationshipToHrp;	}

	public Enum69 getRegisteredUnemployed() {
		return RegisteredUnemployed;	}

	public void setRegisteredUnemployed(final Enum69 RegisteredUnemployed) {
		this.RegisteredUnemployed = RegisteredUnemployed;	}

	public Enum720 getRelationshipToP3_TR03() {
		return RelationshipToP3_TR03;	}

	public void setRelationshipToP3_TR03(final Enum720 RelationshipToP3_TR03) {
		this.RelationshipToP3_TR03 = RelationshipToP3_TR03;	}

	public Enum721 getRelationshipToP8_R08() {
		return RelationshipToP8_R08;	}

	public void setRelationshipToP8_R08(final Enum721 RelationshipToP8_R08) {
		this.RelationshipToP8_R08 = RelationshipToP8_R08;	}

	public Enum69 getLivingInHallsOfResidence() {
		return LivingInHallsOfResidence;	}

	public void setLivingInHallsOfResidence(final Enum69 LivingInHallsOfResidence) {
		this.LivingInHallsOfResidence = LivingInHallsOfResidence;	}

	public Enum721 getRelationshipToP11_R11() {
		return RelationshipToP11_R11;	}

	public void setRelationshipToP11_R11(final Enum721 RelationshipToP11_R11) {
		this.RelationshipToP11_R11 = RelationshipToP11_R11;	}

	public Enum721 getRelationshipToP4_R04() {
		return RelationshipToP4_R04;	}

	public void setRelationshipToP4_R04(final Enum721 RelationshipToP4_R04) {
		this.RelationshipToP4_R04 = RelationshipToP4_R04;	}

	public Enum69 getHasNoneOfTheseQualifications_V117_A() {
		return HasNoneOfTheseQualifications_V117_A;	}

	public void setHasNoneOfTheseQualifications_V117_A(final Enum69 HasNoneOfTheseQualifications_V117_A) {
		this.HasNoneOfTheseQualifications_V117_A = HasNoneOfTheseQualifications_V117_A;	}

	public Enum805 getIncomeProxy() {
		return IncomeProxy;	}

	public void setIncomeProxy(final Enum805 IncomeProxy) {
		this.IncomeProxy = IncomeProxy;	}

	public Enum69 getFromCollegeOrUniversity() {
		return FromCollegeOrUniversity;	}

	public void setFromCollegeOrUniversity(final Enum69 FromCollegeOrUniversity) {
		this.FromCollegeOrUniversity = FromCollegeOrUniversity;	}

	public Enum69 getEarningsFromSelf_Employment() {
		return EarningsFromSelf_Employment;	}

	public void setEarningsFromSelf_Employment(final Enum69 EarningsFromSelf_Employment) {
		this.EarningsFromSelf_Employment = EarningsFromSelf_Employment;	}

	public Enum69 getAtHomeNotSeekingWork() {
		return AtHomeNotSeekingWork;	}

	public void setAtHomeNotSeekingWork(final Enum69 AtHomeNotSeekingWork) {
		this.AtHomeNotSeekingWork = AtHomeNotSeekingWork;	}

	public Enum720 getRelationshipToP10_TR010() {
		return RelationshipToP10_TR010;	}

	public void setRelationshipToP10_TR010(final Enum720 RelationshipToP10_TR010) {
		this.RelationshipToP10_TR010 = RelationshipToP10_TR010;	}

	public Enum69 getWorkingGovernmentTrainingScheme() {
		return WorkingGovernmentTrainingScheme;	}

	public void setWorkingGovernmentTrainingScheme(final Enum69 WorkingGovernmentTrainingScheme) {
		this.WorkingGovernmentTrainingScheme = WorkingGovernmentTrainingScheme;	}

	public Enum811 getWhyIsPersonLivingHere() {
		return WhyIsPersonLivingHere;	}

	public void setWhyIsPersonLivingHere(final Enum811 WhyIsPersonLivingHere) {
		this.WhyIsPersonLivingHere = WhyIsPersonLivingHere;	}

	public Enum69 getOtherStateBenefits() {
		return OtherStateBenefits;	}

	public void setOtherStateBenefits(final Enum69 OtherStateBenefits) {
		this.OtherStateBenefits = OtherStateBenefits;	}

}

