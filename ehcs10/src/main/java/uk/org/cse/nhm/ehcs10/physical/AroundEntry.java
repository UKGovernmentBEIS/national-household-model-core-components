package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1150;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1151;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1152;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1153;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1154;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1159;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1161;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1163;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1164;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1165;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1167;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1170;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1180;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1185;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1186;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1190;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1192;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1202;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface AroundEntry extends SurveyEntry {
	@SavVariableMapping("FCUOTHSP")
	public String getDRAINAGE_OtherFaultSpecified();

	@SavVariableMapping("FARCOND")
	public Integer getPROBLEMSINLOCALAREAConditionOfDwellings();

	@SavVariableMapping("FCUDETSP")
	public Integer getPARKING_CarSpaces();

	@SavVariableMapping("FBLDEFEC")
	public Integer getApproxNumberOfHouses_ModulesInDisrepairInBlock();

	@SavVariableMapping("FCUPORSP")
	public Integer getPARKING_CarSpaces_FCUPORSP();

	@SavVariableMapping("FARMOTOR")
	public Integer getPROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads();

	@SavVariableMapping("FEXP1FDP")
	public Integer getFrontPlotDepth();

	@SavVariableMapping("FARROADS")
	public Integer getPROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture();

	@SavVariableMapping("FARPARKS")
	public Integer getPROBLEMSINLOCALAREANuisanceFromStreetParking();

	@SavVariableMapping("FEXP2FDP")
	public Integer getRearPlotDepth();

	@SavVariableMapping("FCUINTSP")
	public Integer getPARKINGPROVISIONOFSURVEYDWELLINGCarSpaces();

	@SavVariableMapping("FARINDUS")
	public Integer getPROBLEMSINLOCALAREAIntrusiveIndustry();

	@SavVariableMapping("FEXWIDTH")
	public Integer getWidthOfPlot();

	@SavVariableMapping("FARBLDGS")
	public Integer getPROBLEMSINLOCALAREAScruffyNeglectedBuildings();

	@SavVariableMapping("FAREXCRE")
	public Integer getPROBLEMSINLOCALAREADogOtherExcrement();

	@SavVariableMapping("FBLBLOCK")
	public Integer getNumberOfHousesModulesInBlock();

	@SavVariableMapping("FARVACNT")
	public Integer getPROBLEMSINLOCALAREAVacantBoarded_UpBuildings();

	@SavVariableMapping("FARLITTR")
	public Integer getPROBLEMSINLOCALAREALitterRubblish();

	@SavVariableMapping("FARGRAFF")
	public Integer getPROBLEMSINLOCALAREAGraffiti();

	@SavVariableMapping("FARRAILS")
	public Integer getPROBLEMSINLOCALAREARailwayAircraftNoise();

	@SavVariableMapping("FARGRDNS")
	public Integer getPROBLEMSINLOCALAREAScruffyGardensLandscaping();

	@SavVariableMapping("FARTRAFF")
	public Integer getPROBLEMSINLOCALAREAHeavyTraffic();

	@SavVariableMapping("FCUATTSP")
	public Integer getPARKING_CarSpaces_FCUATTSP();

	@SavVariableMapping("FARQUALI")
	public Integer getVisualQualityOfLocalArea();

	@SavVariableMapping("FARNOCON")
	public Integer getPROBLEMSINLOCALAREANon_ConformingUses();

	@SavVariableMapping("FARVANDA")
	public Integer getPROBLEMSINLOCALAREAVandalism();

	@SavVariableMapping("FCUSPASP")
	public Integer getPARKING_CarSpaces_FCUSPASP();

	@SavVariableMapping("FARSITES")
	public Integer getPROBLEMSINLOCALAREAVacantSites();

	@SavVariableMapping("FARAIRQU")
	public Integer getPROBLEMSINLOCALAREAAmbientAirQuality();

	@SavVariableMapping("FARESTAT")
	public Enum1150 getNumberOfDwellingsOnEstate();

	@SavVariableMapping("FEXDSTEP")
	public Enum1151 getACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance();

	@SavVariableMapping("FARTENUR")
	public Enum1152 getPredominantTenure();

	@SavVariableMapping("FEXHSDAM")
	public Enum1153 getHHSRS_DampAndMould();

	@SavVariableMapping("FARTYPES")
	public Enum1154 getPredominantResidentialBuildingType();

	@SavVariableMapping("FCUBLOCK")
	public Enum10 getDRAINAGE_Blockage();

	@SavVariableMapping("FCUINTPR")
	public Enum10 getPARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present();

	@SavVariableMapping("FCUDETPR")
	public Enum10 getPARKING_DetachedGarage_Present();

	@SavVariableMapping("FEXDFIRM")
	public Enum10 getACCESSIBILITY_IsPathFirmAndEven_();

	@SavVariableMapping("FEXHSBTW")
	public Enum1159 getHHSRS_FallsBetweenLevel();

	@SavVariableMapping("FCUPORPR")
	public Enum10 getPARKING_CarPort_Present();

	@SavVariableMapping("FCUPOROW")
	public Enum1161 getPARKING_WhoOwnsCarPlotParking();

	@SavVariableMapping("FCWICHLS")
	public Enum10 getCWICheck_LoftSpace();

	@SavVariableMapping("FARDWELL")
	public Enum1163 getNumberOfDwellingsInArea();

	@SavVariableMapping("FARPRAGE")
	public Enum1164 getPredominantAge();

	@SavVariableMapping("FBLSITUA")
	public Enum1165 getSituationOfBlock();

	@SavVariableMapping("FEXDESWI")
	public Enum10 getACCESSIBILITY_IsPathAtLeast900MmWide();

	@SavVariableMapping("FCUDRAIN")
	public Enum1167 getUNDERGROUNDDRAINAGE_DrainageSystem();

	@SavVariableMapping("FCUDETOW")
	public Enum1161 getPARKING_WhoOwnsDetachedGarageParking();

	@SavVariableMapping("FEXDESGR")
	public Enum10 getACCESSIBILITY_IsGradientLessThan1In12();

	@SavVariableMapping("FARNATUR")
	public Enum1170 getNatureOfArea();

	@SavVariableMapping("FCUOTHER")
	public Enum10 getDRAINAGE_Other();

	@SavVariableMapping("FCWICHME")
	public Enum10 getCWICheck_AreaAroundMeters();

	@SavVariableMapping("FEXPLOTF")
	public Enum10 getDoesFrontPlotExist_();

	@SavVariableMapping("FCUSPAPR")
	public Enum10 getPARKING_DesignatedParking_Present();

	@SavVariableMapping("FEXHSDHY")
	public Enum1153 getHHSRS_DomesticHygiene();

	@SavVariableMapping("FCWICHAB")
	public Enum10 getCWICheck_AirBricks();

	@SavVariableMapping("FEXHSSTR")
	public Enum1159 getHHSRS_FallsOnStairs_Steps();

	@SavVariableMapping("FCWICHEF")
	public Enum10 getCWICheck_ElevationFeatures();

	@SavVariableMapping("FCWICHOR")
	public Enum10 getCWICheck_OccupantResponse();

	@SavVariableMapping("FCUSTR")
	public Enum1180 getPARKING_SteetParking();

	@SavVariableMapping("FCWIPRES")
	public Enum10 getIsCavityWallInsulationPresent_();

	@SavVariableMapping("FCUSPALO")
	public Enum10 getPARKING_OnPlot();

	@SavVariableMapping("FCUSPAOW")
	public Enum1161 getPARKING_WhoOwnsDesignatedParkingSpace();

	@SavVariableMapping("FCUATTOW")
	public Enum1161 getPARKING_WhoOwnsAttachedGarageParking();

	@SavVariableMapping("FCUDETAC")
	public Enum1185 getPARKING_Action();

	@SavVariableMapping("FEXPLTYP")
	public Enum1186 getTypeOfPlot();

	@SavVariableMapping("FEXHSLVL")
	public Enum1159 getHHSRS_FallsOnTheLevel();

	@SavVariableMapping("FCUFAULT")
	public Enum10 getDRAINAGE_Faults();

	@SavVariableMapping("FEXHSCEN")
	public Enum1153 getHHSRS_CollisionAndEntrapment();

	@SavVariableMapping("FARRTB")
	public Enum1190 getIfAreaIsLAEstate__OfRTBDwellings();

	@SavVariableMapping("FCULITTR")
	public Enum1185 getLitterRubbishAroundHouseModule();

	@SavVariableMapping("FEXDRAMP")
	public Enum1192 getACCESSIBILITY_SpaceForRamp();

	@SavVariableMapping("FCUINTAC")
	public Enum1185 getPARKING_Action_FCUINTAC();

	@SavVariableMapping("FCUPORLO")
	public Enum10 getPARKING_OnPlot_FCUPORLO();

	@SavVariableMapping("FEXHSPHY")
	public Enum1153 getHHSRS_PersonalHygiene();

	@SavVariableMapping("FEXDESFE")
	public Enum10 getACCESSIBILITY_IsEntranceAdequatelyLit();

	@SavVariableMapping("FCUINTOW")
	public Enum1161 getPARKING_WhoOwnsGarageParking_();

	@SavVariableMapping("FCUATTPR")
	public Enum10 getPARKING_AttachedGarage_Present();

	@SavVariableMapping("FEXHSENT")
	public Enum1153 getHHSRS_EntryByIntruders();

	@SavVariableMapping("FEXDCOVR")
	public Enum10 getACCESSIBILITY_IsEntranceCovered();

	@SavVariableMapping("FCUPORAC")
	public Enum1185 getPARKING_Action_FCUPORAC();

	@SavVariableMapping("FCUEXPOS")
	public Enum1202 getEXPOSUREIsTheDwellingInAnExposedPosition_();

	@SavVariableMapping("FEXPLOTR")
	public Enum10 getDoesRearPlotExist_();

	@SavVariableMapping("FCUATTAC")
	public Enum1185 getPARKING_Action_FCUATTAC();

	@SavVariableMapping("FCUINTLO")
	public Enum10 getPARKINGPROVISIONOFSURVEYDWELLINGOnPlot();

	@SavVariableMapping("FCUDETLO")
	public Enum10 getPARKING_OnPlot_FCUDETLO();

	@SavVariableMapping("FCUOPP")
	public Enum10 getPARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_();

	@SavVariableMapping("FCUATTLO")
	public Enum10 getPARKING_OnPlot_FCUATTLO();

	@SavVariableMapping("FCUSPAAC")
	public Enum1185 getPARKING_Action_FCUSPAAC();

}

