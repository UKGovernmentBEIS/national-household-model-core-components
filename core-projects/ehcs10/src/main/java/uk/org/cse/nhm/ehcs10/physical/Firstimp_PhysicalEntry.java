package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Firstimp_PhysicalEntry extends SurveyEntry {
	@SavVariableMapping("FRENOSV")
	public String getSection1_SurveyRecord_EnterReasonForNon_Survey();

	@SavVariableMapping("FODOCOTH")
	public String getOccupancy_VACANTOther_Specify();

	@SavVariableMapping("FODSOTH")
	public String getSourceOfInformationOnTenure_Other_Specify();

	@SavVariableMapping("FRECL5MT")
	public Integer getRecordMonthOfCall();

	@SavVariableMapping("FRECL2SM")
	public Integer getStartTime_Minutes();

	@SavVariableMapping("FRECL1FH")
	public Integer getFinishTime_Hours();

	@SavVariableMapping("FRECL4SM")
	public Integer getStartTime_Minutes_FRECL4SM();

	@SavVariableMapping("FODEXDAY")
	public Integer getMovedInWithinLast6Months_Day();

	@SavVariableMapping("FRECL2MT")
	public Integer getRecordMonthOfCall_FRECL2MT();

	@SavVariableMapping("FRECL4MT")
	public Integer getRecordMonthOfCall_FRECL4MT();

	@SavVariableMapping("FRECL1SH")
	public Integer getStartTime_Hours();

	@SavVariableMapping("FRECL5YR")
	public Integer getRecordYearOfCall();

	@SavVariableMapping("FRECL3MT")
	public Integer getRecordMonthOfCall_FRECL3MT();

	@SavVariableMapping("FRECL3SM")
	public Integer getStartTime_Minutes_FRECL3SM();

	@SavVariableMapping("FRECL4FH")
	public Integer getFinishTime_Hours_FRECL4FH();

	@SavVariableMapping("FADSAMB")
	public Integer getMultipleDwellings_NumberOfDwellingsAtAddress();

	@SavVariableMapping("FODVACNY")
	public Integer getVacant_HowLongHasTheDwellingBeenVacant_ENTERYEARS();

	@SavVariableMapping("FRECL2YR")
	public Integer getRecordYearOfCall_FRECL2YR();

	@SavVariableMapping("FRECL4DY")
	public Integer getRecordDayOfCall();

	@SavVariableMapping("FRECL4FM")
	public Integer getFinishTime_Minutes();

	@SavVariableMapping("FRECL1FM")
	public Integer getFinishTime_Minutes_FRECL1FM();

	@SavVariableMapping("FRECL1DY")
	public Integer getRecordDayOfCall_FRECL1DY();

	@SavVariableMapping("FADSAMA")
	public Integer getPartDwelling_NumberOfAddressesAtDwelling();

	@SavVariableMapping("FRECL3SH")
	public Integer getStartTime_Hours_FRECL3SH();

	@SavVariableMapping("FRECL5SM")
	public Integer getStartTime_Minutes_FRECL5SM();

	@SavVariableMapping("FRECL4YR")
	public Integer getRecordYearOfCall_FRECL4YR();

	@SavVariableMapping("FRECL1MT")
	public Integer getRecordMonthOfCall_FRECL1MT();

	@SavVariableMapping("FRECL5FM")
	public Integer getFinishTime_Minutes_FRECL5FM();

	@SavVariableMapping("FRECL3DY")
	public Integer getRecordDayOfCall_FRECL3DY();

	@SavVariableMapping("FRECL5DY")
	public Integer getRecordDayOfCall_FRECL5DY();

	@SavVariableMapping("FODEXMON")
	public Integer getMovedInWithinLast6Months_Month();

	@SavVariableMapping("FRECL1SM")
	public Integer getStartTime_Minutes_FRECL1SM();

	@SavVariableMapping("FRECL3FM")
	public Integer getFinishTime_Minutes_FRECL3FM();

	@SavVariableMapping("FRECL1YR")
	public Integer getRecordYearOfCall_FRECL1YR();

	@SavVariableMapping("FRECL2FM")
	public Integer getFinishTime_Minutes_FRECL2FM();

	@SavVariableMapping("FRECL2SH")
	public Integer getStartTime_Hours_FRECL2SH();

	@SavVariableMapping("FMODNON")
	public Integer getNumberOfUnitsWhichShareAmenities();

	@SavVariableMapping("FODLIVEM")
	public Integer getOccupied_ENTERMONTHS();

	@SavVariableMapping("FODEXYRS")
	public Integer getMovedInWithinLast6Months_Year();

	@SavVariableMapping("FODVACNM")
	public Integer getVacant_ENTERMONTHS();

	@SavVariableMapping("FADSAMC")
	public Integer getMixedWithNon_Residential_NumberOfDwellingsAtAddress();

	@SavVariableMapping("FRECL2DY")
	public Integer getRecordDayOfCall_FRECL2DY();

	@SavVariableMapping("FODCONAC")
	public Integer getActualDateOfConstruction();

	@SavVariableMapping("FRECL4SH")
	public Integer getStartTime_Hours_FRECL4SH();

	@SavVariableMapping("FODLIVEY")
	public Integer getOccupied_HowLongHaveTheCurrentOccupantsLivedHere_ENTERYEARS();

	@SavVariableMapping("FRECL2FH")
	public Integer getFinishTime_Hours_FRECL2FH();

	@SavVariableMapping("FMODSC")
	public Integer getNumberOfUnitsWithExclusiveUseOfAmenities();

	@SavVariableMapping("FRECL3YR")
	public Integer getRecordYearOfCall_FRECL3YR();

	@SavVariableMapping("FRECL5FH")
	public Integer getFinishTime_Hours_FRECL5FH();

	@SavVariableMapping("FRECL3FH")
	public Integer getFinishTime_Hours_FRECL3FH();

	@SavVariableMapping("FRECL5SH")
	public Integer getStartTime_Hours_FRECL5SH();

	@SavVariableMapping("FODISHMO")
	public Enum1324 getTypeOfOccupancy();

	@SavVariableMapping("FRECL5VM")
	public Enum10 getVisitMade_();

	@SavVariableMapping("FODDTYPE")
	public Enum1326 getDwellingType();

	@SavVariableMapping("FRECL2VM")
	public Enum10 getVisitMade__FRECL2VM();

	@SavVariableMapping("FRECL5AP")
	public Enum10 getWasThisABookedAppointment_();

	@SavVariableMapping("FRECL3CO")
	public Enum1329 getCodeOutcomeOfThisCall();

	@SavVariableMapping("FODSORCE")
	public Enum1330 getSourceOfInformationOnTenureAndOccupancy();

	@SavVariableMapping("FADINTV")
	public Enum10 getAddressSurveyedSameAsFromInterviewer_();

	@SavVariableMapping("FODBOARD")
	public Enum10 getDwellingBoardedUp_Secured_();

	@SavVariableMapping("FADSAM")
	public Enum1333 getIsAddress____();

	@SavVariableMapping("FRECL4CO")
	public Enum1329 getCodeOutcomeOfThisCall_FRECL4CO();

	@SavVariableMapping("FRECL2CO")
	public Enum1329 getCodeOutcomeOfThisCall_FRECL2CO();

	@SavVariableMapping("FADINTA")
	public Enum1336 getIsAddressASingleDwelling_();

	@SavVariableMapping("FRECL4VM")
	public Enum10 getVisitMade__FRECL4VM();

	@SavVariableMapping("FODCONST")
	public Enum1338 getConstructionDate();

	@SavVariableMapping("FRECL3VM")
	public Enum10 getVisitMade__FRECL3VM();

	@SavVariableMapping("FRECL1CO")
	public Enum1329 getCodeOutcomeOfThisCall_FRECL1CO();

	@SavVariableMapping("FRECL1AP")
	public Enum10 getWasThisABookedAppointment__FRECL1AP();

	@SavVariableMapping("FRECL3AP")
	public Enum10 getWasThisABookedAppointment__FRECL3AP();

	@SavVariableMapping("FMODISSC")
	public Enum1343 getHaveAllAccommodationUnitsExclusiveUseOfKeyAmenities_();

	@SavVariableMapping("FODTENUR")
	public Enum1344 getTenure();

	@SavVariableMapping("FRECL4AP")
	public Enum10 getWasThisABookedAppointment__FRECL4AP();

	@SavVariableMapping("FODPERES")
	public Enum1346 getPermanentResidence_();

	@SavVariableMapping("FRECL1VM")
	public Enum10 getVisitMade__FRECL1VM();

	@SavVariableMapping("FRECL5CO")
	public Enum1329 getCodeOutcomeOfThisCall_FRECL5CO();

	@SavVariableMapping("FMODULE")
	public Enum1349 getTypeOfModule();

	@SavVariableMapping("FODOCCUP")
	public Enum1350 getOccupancy();

	@SavVariableMapping("FRECL2AP")
	public Enum10 getWasThisABookedAppointment__FRECL2AP();

}

