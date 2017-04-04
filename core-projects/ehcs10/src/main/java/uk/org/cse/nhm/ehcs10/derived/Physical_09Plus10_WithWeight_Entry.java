package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.ehcs10.derived.types.Enum101;
import uk.org.cse.nhm.ehcs10.derived.types.Enum102;
import uk.org.cse.nhm.ehcs10.derived.types.Enum103;
import uk.org.cse.nhm.ehcs10.derived.types.Enum105;
import uk.org.cse.nhm.ehcs10.derived.types.Enum106;
import uk.org.cse.nhm.ehcs10.derived.types.Enum107;
import uk.org.cse.nhm.ehcs10.derived.types.Enum109;
import uk.org.cse.nhm.ehcs10.derived.types.Enum110;
import uk.org.cse.nhm.ehcs10.derived.types.Enum111;
import uk.org.cse.nhm.ehcs10.derived.types.Enum113;
import uk.org.cse.nhm.ehcs10.derived.types.Enum114;
import uk.org.cse.nhm.ehcs10.derived.types.Enum116;
import uk.org.cse.nhm.ehcs10.derived.types.Enum117;
import uk.org.cse.nhm.ehcs10.derived.types.Enum118;
import uk.org.cse.nhm.ehcs10.derived.types.Enum119;
import uk.org.cse.nhm.ehcs10.derived.types.Enum120;
import uk.org.cse.nhm.ehcs10.derived.types.Enum121;
import uk.org.cse.nhm.ehcs10.derived.types.Enum122;
import uk.org.cse.nhm.ehcs10.derived.types.Enum123;
import uk.org.cse.nhm.ehcs10.derived.types.Enum124;
import uk.org.cse.nhm.ehcs10.derived.types.Enum125;
import uk.org.cse.nhm.ehcs10.derived.types.Enum126;
import uk.org.cse.nhm.ehcs10.derived.types.Enum128;
import uk.org.cse.nhm.ehcs10.derived.types.Enum129;
import uk.org.cse.nhm.ehcs10.derived.types.Enum131;
import uk.org.cse.nhm.ehcs10.derived.types.Enum133;
import uk.org.cse.nhm.ehcs10.derived.types.Enum135;
import uk.org.cse.nhm.ehcs10.derived.types.Enum136;
import uk.org.cse.nhm.ehcs10.derived.types.Enum138;
import uk.org.cse.nhm.ehcs10.derived.types.Enum139;
import uk.org.cse.nhm.ehcs10.derived.types.Enum140;
import uk.org.cse.nhm.ehcs10.derived.types.Enum143;
import uk.org.cse.nhm.ehcs10.derived.types.Enum144;
import uk.org.cse.nhm.ehcs10.derived.types.Enum145;
import uk.org.cse.nhm.ehcs10.derived.types.Enum146;
import uk.org.cse.nhm.ehcs10.derived.types.Enum147;
import uk.org.cse.nhm.ehcs10.derived.types.Enum150;
import uk.org.cse.nhm.ehcs10.derived.types.Enum151;
import uk.org.cse.nhm.ehcs10.derived.types.Enum153;
import uk.org.cse.nhm.ehcs10.derived.types.Enum154;
import uk.org.cse.nhm.ehcs10.derived.types.Enum155;
import uk.org.cse.nhm.ehcs10.derived.types.Enum16;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Physical_09Plus10_WithWeight_Entry extends SurveyEntry {
	@SavVariableMapping("AAGPD910")
	public Integer getDwellWeight_PairedCases2009_10And2010_11();

	@SavVariableMapping("LOFTINSX")
	public Integer getLoftInsulationThickness();

	@SavVariableMapping("STOREYX")
	public Integer getNumberOfFloorsAboveGround();

	@SavVariableMapping("LVNUMX")
	public Integer getNumberOfLiveabilityProblemsPresent();

	@SavVariableMapping("CSTACTBX")
	public Double getBasicRepairCosts_Actual_();

	@SavVariableMapping("CSTACTCX")
	public Double getComprehensiveRepairCosts_Actual_();

	@SavVariableMapping("CSTSTDBX")
	public Double getBasicRepairCosts_PerSquareMetre_();

	@SavVariableMapping("FLOORX")
	public Double getUseableFloorArea_Sqm_();

	@SavVariableMapping("CSTSTDUX")
	public Double getUrgentRepairCosts_PerSquareMetre_();

	@SavVariableMapping("CSTSTDCX")
	public Double getComprehensiveRepairCosts_PerSquareMetre_();

	@SavVariableMapping("CSTACTUX")
	public Double getUrgentRepairCosts_Actual_();

	@SavVariableMapping("EPCEIR09")
	public Double getEnvironmentalImpactRating_EhsSAP2009_();

	@SavVariableMapping("AAGPH910")
	public Double getHhldWeight_PairedCases2009_10And2010_11();

	@SavVariableMapping("DHCOSTY")
	public Double getCostToMakeDecent_Hhsrs15Model_();

	@SavVariableMapping("SAP09")
	public Double getEnergyEfficiency_SAP09_Rating();

	@SavVariableMapping("DWTYPE3X")
	public Enum101 getDwellingType();

	@SavVariableMapping("NEIVISX")
	public Enum102 getAppearanceOfArea();

	@SavVariableMapping("TYPEWIN")
	public Enum103 getPredominantTypeOfWindow();

	@SavVariableMapping("LVANYX")
	public Enum69 getPoorQualityEnvironment();

	@SavVariableMapping("DWAGE4X")
	public Enum105 getDwellingAge();

	@SavVariableMapping("LOFTINS6")
	public Enum106 getLoftInsulationThickness_LOFTINS6();

	@SavVariableMapping("DHREASNY")
	public Enum107 getDecentHomesCriteriaNotMet_Hhsrs15Model_();

	@SavVariableMapping("EPCEEB09")
	public Enum16 getEnergyEfficiencyRatingBand_EhsSAP2009_();

	@SavVariableMapping("DHNUMZ")
	public Enum109 getDecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_();

	@SavVariableMapping("TYPERCOV")
	public Enum110 getPredominantTypeOfRoofCovering();

	@SavVariableMapping("DHTCREAS")
	public Enum111 getReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__();

	@SavVariableMapping("DHREASNZ")
	public Enum107 getDecentHomesCriteriaNotMet_Hhsrs26Model_();

	@SavVariableMapping("SECURE")
	public Enum113 getSecureWindowsAndDoors();

	@SavVariableMapping("FLOOR5X")
	public Enum114 getUseableFloorArea();

	@SavVariableMapping("BASEMENT")
	public Enum69 getBasementPresentInDwelling();

	@SavVariableMapping("HEAT7X")
	public Enum116 getMainHeatingSystem();

	@SavVariableMapping("SAP409")
	public Enum117 getEnergyEfficiency_SAP09_Rating_SAP409();

	@SavVariableMapping("FUELX")
	public Enum118 getMainFuelType();

	@SavVariableMapping("TYPEWFIN")
	public Enum119 getPredominantTypeOfWallFinish();

	@SavVariableMapping("DHOMESZ")
	public Enum120 getDecentHomes_HHSRS26Model();

	@SavVariableMapping("PARKING")
	public Enum121 getParkingProvisionOfSurveyDwelling();

	@SavVariableMapping("WALLINSX")
	public Enum122 getTypeOfWallAndInsulation();

	@SavVariableMapping("DHDISRX")
	public Enum123 getDecentHomesRepairCriterion();

	@SavVariableMapping("DBLGLAZ4")
	public Enum124 getExtentOfDoubleGlazing();

	@SavVariableMapping("ALLTYPEX")
	public Enum125 getDwellingAgeAndType();

	@SavVariableMapping("LOFTINS4")
	public Enum126 getLoftInsulationThickness_LOFTINS4();

	@SavVariableMapping("DHOMESY")
	public Enum120 getDecentHomes_HHSRS15Model();

	@SavVariableMapping("DWAGE5X")
	public Enum128 getDwellingAge_DWAGE5X();

	@SavVariableMapping("DWTYPENX")
	public Enum129 getDwellingType_DWTYPENX();

	@SavVariableMapping("EPCEIB09")
	public Enum16 getEnvironmentalImpactRatingBand_EhsSAP2009_();

	@SavVariableMapping("TYPERSTR")
	public Enum131 getPredominantTypeOfRoofStucture();

	@SavVariableMapping("DHHHSRSX")
	public Enum123 getDecentHomesHhsrs_15Hazards();

	@SavVariableMapping("DWTYPE7X")
	public Enum133 getDwellingType_DWTYPE7X();

	@SavVariableMapping("ATTIC")
	public Enum69 getAtticPresentInDwelling();

	@SavVariableMapping("DBLGLAZ2")
	public Enum135 getExtentOfDoubleGlazing_DBLGLAZ2();

	@SavVariableMapping("DWTYPE8X")
	public Enum136 getDwellingType_DWTYPE8X();

	@SavVariableMapping("LV2TRAFX")
	public Enum69 getPoorQualityEnvironment_TrafficProblems();

	@SavVariableMapping("WALLCAVX")
	public Enum138 getTypeOfWall();

	@SavVariableMapping("DWAGE6X")
	public Enum139 getDwellingAge_DWAGE6X();

	@SavVariableMapping("AREA3X")
	public Enum140 getTypeOfArea();

	@SavVariableMapping("DHTHERMY")
	public Enum123 getDecentHomesThermalComfortCriterion();

	@SavVariableMapping("LV3UTILX")
	public Enum69 getPoorQualityEnvironment_UtilisationProblems();

	@SavVariableMapping("HEAT4X")
	public Enum143 getMainHeatingSystem_HEAT4X();

	@SavVariableMapping("DWAGE9X")
	public Enum144 getDwellingAge_DWAGE9X();

	@SavVariableMapping("SYSAGE")
	public Enum145 getAgeOfHeatingSystem();

	@SavVariableMapping("TYPEWSTR")
	public Enum146 getPredominantTypeOfWallStucture();

	@SavVariableMapping("DHTCACTY")
	public Enum147 getRequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_();

	@SavVariableMapping("LV1UPKPX")
	public Enum69 getPoorQualityEnvironment_UpkeepProblems();

	@SavVariableMapping("DHMODX")
	public Enum123 getDecentHomesModernFacilitiesCriterion();

	@SavVariableMapping("ARNATX")
	public Enum150 getNatureOfArea();

	@SavVariableMapping("MAINFUEL")
	public Enum151 getMainHeatingFuel();

	@SavVariableMapping("DHNUMY")
	public Enum109 getDecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_();

	@SavVariableMapping("WATERSYS")
	public Enum153 getWaterHeatingSystem();

	@SavVariableMapping("BOILER")
	public Enum154 getTypeOfBoiler();

	@SavVariableMapping("HOUSEX")
	public Enum155 getDwellingType_HOUSEX();

	@SavVariableMapping("DHHHSRSY")
	public Enum123 getDecentHomesHhsrs_26Hazards();

}

