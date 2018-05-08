package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.ehcs10.derived.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Physical_09Plus10Entry extends SurveyEntry {

    @SavVariableMapping("STOREYX")
    public Integer getNumberOfFloorsAboveGround();

    @SavVariableMapping("LVNUMX")
    public Integer getNumberOfLiveabilityProblemsPresent();

    @SavVariableMapping("LOFTINSX")
    public Integer getLoftInsulationThickness();

    @SavVariableMapping("CSTACTCX")
    public Double getComprehensiveRepairCosts_Actual_();

    @SavVariableMapping("CSTSTDUX")
    public Double getUrgentRepairCosts_PerSquareMetre_();

    @SavVariableMapping("SAP09")
    public Double getEnergyEfficiency_SAP09_Rating();

    @SavVariableMapping("CSTSTDCX")
    public Double getComprehensiveRepairCosts_PerSquareMetre_();

    @SavVariableMapping("CSTACTBX")
    public Double getBasicRepairCosts_Actual_();

    @SavVariableMapping("EPCEIR09")
    public Double getEnvironmentalImpactRating_EhsSAP2009_();

    @SavVariableMapping("FLOORX")
    public Double getUseableFloorArea_Sqm_();

    @SavVariableMapping("CSTACTUX")
    public Double getUrgentRepairCosts_Actual_();

    @SavVariableMapping("DHCOSTY")
    public Double getCostToMakeDecent_Hhsrs15Model_();

    @SavVariableMapping("CSTSTDBX")
    public Double getBasicRepairCosts_PerSquareMetre_();

    @SavVariableMapping("TYPEWIN")
    public Enum103 getPredominantTypeOfWindow();

    @SavVariableMapping("DWTYPE3X")
    public Enum101 getDwellingType();

    @SavVariableMapping("LV1UPKPX")
    public Enum69 getPoorQualityEnvironment_UpkeepProblems();

    @SavVariableMapping("DHREASNY")
    public Enum107 getDecentHomesCriteriaNotMet_Hhsrs15Model_();

    @SavVariableMapping("WALLCAVX")
    public Enum138 getTypeOfWall();

    @SavVariableMapping("LOFTINS4")
    public Enum126 getLoftInsulationThickness_LOFTINS4();

    @SavVariableMapping("LVANYX")
    public Enum69 getPoorQualityEnvironment();

    @SavVariableMapping("DWAGE5X")
    public Enum128 getDwellingAge();

    @SavVariableMapping("AREA3X")
    public Enum140 getTypeOfArea();

    @SavVariableMapping("DHNUMZ")
    public Enum109 getDecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_();

    @SavVariableMapping("HEAT7X")
    public Enum116 getMainHeatingSystem();

    @SavVariableMapping("ARNATX")
    public Enum150 getNatureOfArea();

    @SavVariableMapping("DWTYPE8X")
    public Enum136 getDwellingType_DWTYPE8X();

    @SavVariableMapping("TYPEWFIN")
    public Enum119 getPredominantTypeOfWallFinish();

    @SavVariableMapping("HOUSEX")
    public Enum155 getDwellingType_HOUSEX();

    @SavVariableMapping("DWTYPE7X")
    public Enum133 getDwellingType_DWTYPE7X();

    @SavVariableMapping("DHOMESY")
    public Enum120 getDecentHomes_HHSRS15Model();

    @SavVariableMapping("LOFTINS6")
    public Enum106 getLoftInsulationThickness_LOFTINS6();

    @SavVariableMapping("DHHHSRSY")
    public Enum123 getDecentHomesHhsrs_26Hazards();

    @SavVariableMapping("TYPEWSTR")
    public Enum146 getPredominantTypeOfWallStucture();

    @SavVariableMapping("TYPERCOV")
    public Enum110 getPredominantTypeOfRoofCovering();

    @SavVariableMapping("FUELX")
    public Enum118 getMainFuelType();

    @SavVariableMapping("FLOOR5X")
    public Enum114 getUseableFloorArea();

    @SavVariableMapping("LV3UTILX")
    public Enum69 getPoorQualityEnvironment_UtilisationProblems();

    @SavVariableMapping("DWAGE4X")
    public Enum105 getDwellingAge_DWAGE4X();

    @SavVariableMapping("DBLGLAZ4")
    public Enum124 getExtentOfDoubleGlazing();

    @SavVariableMapping("DHNUMY")
    public Enum109 getDecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_();

    @SavVariableMapping("MAINFUEL")
    public Enum151 getMainHeatingFuel();

    @SavVariableMapping("SECURE")
    public Enum113 getSecureWindowsAndDoors();

    @SavVariableMapping("PARKING")
    public Enum121 getParkingProvisionOfSurveyDwelling();

    @SavVariableMapping("ATTIC")
    public Enum69 getAtticPresentInDwelling();

    @SavVariableMapping("DBLGLAZ2")
    public Enum135 getExtentOfDoubleGlazing_DBLGLAZ2();

    @SavVariableMapping("NEIVISX")
    public Enum102 getAppearanceOfArea();

    @SavVariableMapping("DWAGE6X")
    public Enum139 getDwellingAge_DWAGE6X();

    @SavVariableMapping("DHTHERMY")
    public Enum123 getDecentHomesThermalComfortCriterion();

    @SavVariableMapping("ALLTYPEX")
    public Enum125 getDwellingAgeAndType();

    @SavVariableMapping("LV2TRAFX")
    public Enum69 getPoorQualityEnvironment_TrafficProblems();

    @SavVariableMapping("EPCEIB09")
    public Enum16 getEnvironmentalImpactRatingBand_EhsSAP2009_();

    @SavVariableMapping("BASEMENT")
    public Enum69 getBasementPresentInDwelling();

    @SavVariableMapping("DHTCACTY")
    public Enum147 getRequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_();

    @SavVariableMapping("DWAGE9X")
    public Enum144 getDwellingAge_DWAGE9X();

    @SavVariableMapping("WALLINSX")
    public Enum122 getTypeOfWallAndInsulation();

    @SavVariableMapping("DHTCREAS")
    public Enum111 getReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__();

    @SavVariableMapping("EPCEEB09")
    public Enum16 getEnergyEfficiencyRatingBand_EhsSAP2009_();

    @SavVariableMapping("DHREASNZ")
    public Enum107 getDecentHomesCriteriaNotMet_Hhsrs26Model_();

    @SavVariableMapping("BOILER")
    public Enum154 getTypeOfBoiler();

    @SavVariableMapping("DHMODX")
    public Enum123 getDecentHomesModernFacilitiesCriterion();

    @SavVariableMapping("TYPERSTR")
    public Enum131 getPredominantTypeOfRoofStucture();

    @SavVariableMapping("DWTYPENX")
    public Enum129 getDwellingType_DWTYPENX();

    @SavVariableMapping("SYSAGE")
    public Enum145 getAgeOfHeatingSystem();

    @SavVariableMapping("WATERSYS")
    public Enum153 getWaterHeatingSystem();

    @SavVariableMapping("SAP409")
    public Enum117 getEnergyEfficiency_SAP09_Rating_SAP409();

    @SavVariableMapping("HEAT4X")
    public Enum143 getMainHeatingSystem_HEAT4X();

    @SavVariableMapping("DHOMESZ")
    public Enum120 getDecentHomes_HHSRS26Model();

    @SavVariableMapping("DHHHSRSX")
    public Enum123 getDecentHomesHhsrs_15Hazards();

    @SavVariableMapping("DHDISRX")
    public Enum123 getDecentHomesRepairCriterion();

}
