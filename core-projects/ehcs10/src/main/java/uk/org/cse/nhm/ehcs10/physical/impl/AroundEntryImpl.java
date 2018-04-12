package uk.org.cse.nhm.ehcs10.physical.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.AroundEntry;
import uk.org.cse.nhm.ehcs10.physical.types.*;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class AroundEntryImpl extends SurveyEntryImpl implements AroundEntry {
	private String DRAINAGE_OtherFaultSpecified;
	private Integer PROBLEMSINLOCALAREAConditionOfDwellings;
	private Integer PARKING_CarSpaces;
	private Integer ApproxNumberOfHouses_ModulesInDisrepairInBlock;
	private Integer PARKING_CarSpaces_FCUPORSP;
	private Integer PROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads;
	private Integer FrontPlotDepth;
	private Integer PROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture;
	private Integer PROBLEMSINLOCALAREANuisanceFromStreetParking;
	private Integer RearPlotDepth;
	private Integer PARKINGPROVISIONOFSURVEYDWELLINGCarSpaces;
	private Integer PROBLEMSINLOCALAREAIntrusiveIndustry;
	private Integer WidthOfPlot;
	private Integer PROBLEMSINLOCALAREAScruffyNeglectedBuildings;
	private Integer PROBLEMSINLOCALAREADogOtherExcrement;
	private Integer NumberOfHousesModulesInBlock;
	private Integer PROBLEMSINLOCALAREAVacantBoarded_UpBuildings;
	private Integer PROBLEMSINLOCALAREALitterRubblish;
	private Integer PROBLEMSINLOCALAREAGraffiti;
	private Integer PROBLEMSINLOCALAREARailwayAircraftNoise;
	private Integer PROBLEMSINLOCALAREAScruffyGardensLandscaping;
	private Integer PROBLEMSINLOCALAREAHeavyTraffic;
	private Integer PARKING_CarSpaces_FCUATTSP;
	private Integer VisualQualityOfLocalArea;
	private Integer PROBLEMSINLOCALAREANon_ConformingUses;
	private Integer PROBLEMSINLOCALAREAVandalism;
	private Integer PARKING_CarSpaces_FCUSPASP;
	private Integer PROBLEMSINLOCALAREAVacantSites;
	private Integer PROBLEMSINLOCALAREAAmbientAirQuality;
	private Enum1150 NumberOfDwellingsOnEstate;
	private Enum1151 ACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance;
	private Enum1152 PredominantTenure;
	private Enum1153 HHSRS_DampAndMould;
	private Enum1154 PredominantResidentialBuildingType;
	private Enum10 DRAINAGE_Blockage;
	private Enum10 PARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present;
	private Enum10 PARKING_DetachedGarage_Present;
	private Enum10 ACCESSIBILITY_IsPathFirmAndEven_;
	private Enum1159 HHSRS_FallsBetweenLevel;
	private Enum10 PARKING_CarPort_Present;
	private Enum1161 PARKING_WhoOwnsCarPlotParking;
	private Enum10 CWICheck_LoftSpace;
	private Enum1163 NumberOfDwellingsInArea;
	private Enum1164 PredominantAge;
	private Enum1165 SituationOfBlock;
	private Enum10 ACCESSIBILITY_IsPathAtLeast900MmWide;
	private Enum1167 UNDERGROUNDDRAINAGE_DrainageSystem;
	private Enum1161 PARKING_WhoOwnsDetachedGarageParking;
	private Enum10 ACCESSIBILITY_IsGradientLessThan1In12;
	private Enum1170 NatureOfArea;
	private Enum10 DRAINAGE_Other;
	private Enum10 CWICheck_AreaAroundMeters;
	private Enum10 DoesFrontPlotExist_;
	private Enum10 PARKING_DesignatedParking_Present;
	private Enum1153 HHSRS_DomesticHygiene;
	private Enum10 CWICheck_AirBricks;
	private Enum1159 HHSRS_FallsOnStairs_Steps;
	private Enum10 CWICheck_ElevationFeatures;
	private Enum10 CWICheck_OccupantResponse;
	private Enum1180 PARKING_SteetParking;
	private Enum10 IsCavityWallInsulationPresent_;
	private Enum10 PARKING_OnPlot;
	private Enum1161 PARKING_WhoOwnsDesignatedParkingSpace;
	private Enum1161 PARKING_WhoOwnsAttachedGarageParking;
	private Enum1185 PARKING_Action;
	private Enum1186 TypeOfPlot;
	private Enum1159 HHSRS_FallsOnTheLevel;
	private Enum10 DRAINAGE_Faults;
	private Enum1153 HHSRS_CollisionAndEntrapment;
	private Enum1190 IfAreaIsLAEstate__OfRTBDwellings;
	private Enum1185 LitterRubbishAroundHouseModule;
	private Enum1192 ACCESSIBILITY_SpaceForRamp;
	private Enum1185 PARKING_Action_FCUINTAC;
	private Enum10 PARKING_OnPlot_FCUPORLO;
	private Enum1153 HHSRS_PersonalHygiene;
	private Enum10 ACCESSIBILITY_IsEntranceAdequatelyLit;
	private Enum1161 PARKING_WhoOwnsGarageParking_;
	private Enum10 PARKING_AttachedGarage_Present;
	private Enum1153 HHSRS_EntryByIntruders;
	private Enum10 ACCESSIBILITY_IsEntranceCovered;
	private Enum1185 PARKING_Action_FCUPORAC;
	private Enum1202 EXPOSUREIsTheDwellingInAnExposedPosition_;
	private Enum10 DoesRearPlotExist_;
	private Enum1185 PARKING_Action_FCUATTAC;
	private Enum10 PARKINGPROVISIONOFSURVEYDWELLINGOnPlot;
	private Enum10 PARKING_OnPlot_FCUDETLO;
	private Enum10 PARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_;
	private Enum10 PARKING_OnPlot_FCUATTLO;
	private Enum1185 PARKING_Action_FCUSPAAC;
	public String getDRAINAGE_OtherFaultSpecified() {
		return DRAINAGE_OtherFaultSpecified;	}

	public void setDRAINAGE_OtherFaultSpecified(final String DRAINAGE_OtherFaultSpecified) {
		this.DRAINAGE_OtherFaultSpecified = DRAINAGE_OtherFaultSpecified;	}

	public Integer getPROBLEMSINLOCALAREAConditionOfDwellings() {
		return PROBLEMSINLOCALAREAConditionOfDwellings;	}

	public void setPROBLEMSINLOCALAREAConditionOfDwellings(final Integer PROBLEMSINLOCALAREAConditionOfDwellings) {
		this.PROBLEMSINLOCALAREAConditionOfDwellings = PROBLEMSINLOCALAREAConditionOfDwellings;	}

	public Integer getPARKING_CarSpaces() {
		return PARKING_CarSpaces;	}

	public void setPARKING_CarSpaces(final Integer PARKING_CarSpaces) {
		this.PARKING_CarSpaces = PARKING_CarSpaces;	}

	public Integer getApproxNumberOfHouses_ModulesInDisrepairInBlock() {
		return ApproxNumberOfHouses_ModulesInDisrepairInBlock;	}

	public void setApproxNumberOfHouses_ModulesInDisrepairInBlock(final Integer ApproxNumberOfHouses_ModulesInDisrepairInBlock) {
		this.ApproxNumberOfHouses_ModulesInDisrepairInBlock = ApproxNumberOfHouses_ModulesInDisrepairInBlock;	}

	public Integer getPARKING_CarSpaces_FCUPORSP() {
		return PARKING_CarSpaces_FCUPORSP;	}

	public void setPARKING_CarSpaces_FCUPORSP(final Integer PARKING_CarSpaces_FCUPORSP) {
		this.PARKING_CarSpaces_FCUPORSP = PARKING_CarSpaces_FCUPORSP;	}

	public Integer getPROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads() {
		return PROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads;	}

	public void setPROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads(final Integer PROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads) {
		this.PROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads = PROBLEMSINLOCALAREAIntrusionFromMotorwaysArterialRoads;	}

	public Integer getFrontPlotDepth() {
		return FrontPlotDepth;	}

	public void setFrontPlotDepth(final Integer FrontPlotDepth) {
		this.FrontPlotDepth = FrontPlotDepth;	}

	public Integer getPROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture() {
		return PROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture;	}

	public void setPROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture(final Integer PROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture) {
		this.PROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture = PROBLEMSINLOCALAREAConditionOfRoad_PavementsAndStreetFurniture;	}

	public Integer getPROBLEMSINLOCALAREANuisanceFromStreetParking() {
		return PROBLEMSINLOCALAREANuisanceFromStreetParking;	}

	public void setPROBLEMSINLOCALAREANuisanceFromStreetParking(final Integer PROBLEMSINLOCALAREANuisanceFromStreetParking) {
		this.PROBLEMSINLOCALAREANuisanceFromStreetParking = PROBLEMSINLOCALAREANuisanceFromStreetParking;	}

	public Integer getRearPlotDepth() {
		return RearPlotDepth;	}

	public void setRearPlotDepth(final Integer RearPlotDepth) {
		this.RearPlotDepth = RearPlotDepth;	}

	public Integer getPARKINGPROVISIONOFSURVEYDWELLINGCarSpaces() {
		return PARKINGPROVISIONOFSURVEYDWELLINGCarSpaces;	}

	public void setPARKINGPROVISIONOFSURVEYDWELLINGCarSpaces(final Integer PARKINGPROVISIONOFSURVEYDWELLINGCarSpaces) {
		this.PARKINGPROVISIONOFSURVEYDWELLINGCarSpaces = PARKINGPROVISIONOFSURVEYDWELLINGCarSpaces;	}

	public Integer getPROBLEMSINLOCALAREAIntrusiveIndustry() {
		return PROBLEMSINLOCALAREAIntrusiveIndustry;	}

	public void setPROBLEMSINLOCALAREAIntrusiveIndustry(final Integer PROBLEMSINLOCALAREAIntrusiveIndustry) {
		this.PROBLEMSINLOCALAREAIntrusiveIndustry = PROBLEMSINLOCALAREAIntrusiveIndustry;	}

	public Integer getWidthOfPlot() {
		return WidthOfPlot;	}

	public void setWidthOfPlot(final Integer WidthOfPlot) {
		this.WidthOfPlot = WidthOfPlot;	}

	public Integer getPROBLEMSINLOCALAREAScruffyNeglectedBuildings() {
		return PROBLEMSINLOCALAREAScruffyNeglectedBuildings;	}

	public void setPROBLEMSINLOCALAREAScruffyNeglectedBuildings(final Integer PROBLEMSINLOCALAREAScruffyNeglectedBuildings) {
		this.PROBLEMSINLOCALAREAScruffyNeglectedBuildings = PROBLEMSINLOCALAREAScruffyNeglectedBuildings;	}

	public Integer getPROBLEMSINLOCALAREADogOtherExcrement() {
		return PROBLEMSINLOCALAREADogOtherExcrement;	}

	public void setPROBLEMSINLOCALAREADogOtherExcrement(final Integer PROBLEMSINLOCALAREADogOtherExcrement) {
		this.PROBLEMSINLOCALAREADogOtherExcrement = PROBLEMSINLOCALAREADogOtherExcrement;	}

	public Integer getNumberOfHousesModulesInBlock() {
		return NumberOfHousesModulesInBlock;	}

	public void setNumberOfHousesModulesInBlock(final Integer NumberOfHousesModulesInBlock) {
		this.NumberOfHousesModulesInBlock = NumberOfHousesModulesInBlock;	}

	public Integer getPROBLEMSINLOCALAREAVacantBoarded_UpBuildings() {
		return PROBLEMSINLOCALAREAVacantBoarded_UpBuildings;	}

	public void setPROBLEMSINLOCALAREAVacantBoarded_UpBuildings(final Integer PROBLEMSINLOCALAREAVacantBoarded_UpBuildings) {
		this.PROBLEMSINLOCALAREAVacantBoarded_UpBuildings = PROBLEMSINLOCALAREAVacantBoarded_UpBuildings;	}

	public Integer getPROBLEMSINLOCALAREALitterRubblish() {
		return PROBLEMSINLOCALAREALitterRubblish;	}

	public void setPROBLEMSINLOCALAREALitterRubblish(final Integer PROBLEMSINLOCALAREALitterRubblish) {
		this.PROBLEMSINLOCALAREALitterRubblish = PROBLEMSINLOCALAREALitterRubblish;	}

	public Integer getPROBLEMSINLOCALAREAGraffiti() {
		return PROBLEMSINLOCALAREAGraffiti;	}

	public void setPROBLEMSINLOCALAREAGraffiti(final Integer PROBLEMSINLOCALAREAGraffiti) {
		this.PROBLEMSINLOCALAREAGraffiti = PROBLEMSINLOCALAREAGraffiti;	}

	public Integer getPROBLEMSINLOCALAREARailwayAircraftNoise() {
		return PROBLEMSINLOCALAREARailwayAircraftNoise;	}

	public void setPROBLEMSINLOCALAREARailwayAircraftNoise(final Integer PROBLEMSINLOCALAREARailwayAircraftNoise) {
		this.PROBLEMSINLOCALAREARailwayAircraftNoise = PROBLEMSINLOCALAREARailwayAircraftNoise;	}

	public Integer getPROBLEMSINLOCALAREAScruffyGardensLandscaping() {
		return PROBLEMSINLOCALAREAScruffyGardensLandscaping;	}

	public void setPROBLEMSINLOCALAREAScruffyGardensLandscaping(final Integer PROBLEMSINLOCALAREAScruffyGardensLandscaping) {
		this.PROBLEMSINLOCALAREAScruffyGardensLandscaping = PROBLEMSINLOCALAREAScruffyGardensLandscaping;	}

	public Integer getPROBLEMSINLOCALAREAHeavyTraffic() {
		return PROBLEMSINLOCALAREAHeavyTraffic;	}

	public void setPROBLEMSINLOCALAREAHeavyTraffic(final Integer PROBLEMSINLOCALAREAHeavyTraffic) {
		this.PROBLEMSINLOCALAREAHeavyTraffic = PROBLEMSINLOCALAREAHeavyTraffic;	}

	public Integer getPARKING_CarSpaces_FCUATTSP() {
		return PARKING_CarSpaces_FCUATTSP;	}

	public void setPARKING_CarSpaces_FCUATTSP(final Integer PARKING_CarSpaces_FCUATTSP) {
		this.PARKING_CarSpaces_FCUATTSP = PARKING_CarSpaces_FCUATTSP;	}

	public Integer getVisualQualityOfLocalArea() {
		return VisualQualityOfLocalArea;	}

	public void setVisualQualityOfLocalArea(final Integer VisualQualityOfLocalArea) {
		this.VisualQualityOfLocalArea = VisualQualityOfLocalArea;	}

	public Integer getPROBLEMSINLOCALAREANon_ConformingUses() {
		return PROBLEMSINLOCALAREANon_ConformingUses;	}

	public void setPROBLEMSINLOCALAREANon_ConformingUses(final Integer PROBLEMSINLOCALAREANon_ConformingUses) {
		this.PROBLEMSINLOCALAREANon_ConformingUses = PROBLEMSINLOCALAREANon_ConformingUses;	}

	public Integer getPROBLEMSINLOCALAREAVandalism() {
		return PROBLEMSINLOCALAREAVandalism;	}

	public void setPROBLEMSINLOCALAREAVandalism(final Integer PROBLEMSINLOCALAREAVandalism) {
		this.PROBLEMSINLOCALAREAVandalism = PROBLEMSINLOCALAREAVandalism;	}

	public Integer getPARKING_CarSpaces_FCUSPASP() {
		return PARKING_CarSpaces_FCUSPASP;	}

	public void setPARKING_CarSpaces_FCUSPASP(final Integer PARKING_CarSpaces_FCUSPASP) {
		this.PARKING_CarSpaces_FCUSPASP = PARKING_CarSpaces_FCUSPASP;	}

	public Integer getPROBLEMSINLOCALAREAVacantSites() {
		return PROBLEMSINLOCALAREAVacantSites;	}

	public void setPROBLEMSINLOCALAREAVacantSites(final Integer PROBLEMSINLOCALAREAVacantSites) {
		this.PROBLEMSINLOCALAREAVacantSites = PROBLEMSINLOCALAREAVacantSites;	}

	public Integer getPROBLEMSINLOCALAREAAmbientAirQuality() {
		return PROBLEMSINLOCALAREAAmbientAirQuality;	}

	public void setPROBLEMSINLOCALAREAAmbientAirQuality(final Integer PROBLEMSINLOCALAREAAmbientAirQuality) {
		this.PROBLEMSINLOCALAREAAmbientAirQuality = PROBLEMSINLOCALAREAAmbientAirQuality;	}

	public Enum1150 getNumberOfDwellingsOnEstate() {
		return NumberOfDwellingsOnEstate;	}

	public void setNumberOfDwellingsOnEstate(final Enum1150 NumberOfDwellingsOnEstate) {
		this.NumberOfDwellingsOnEstate = NumberOfDwellingsOnEstate;	}

	public Enum1151 getACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance() {
		return ACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance;	}

	public void setACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance(final Enum1151 ACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance) {
		this.ACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance = ACCESSIBILITY_NumberOfStepsFromPavement_GateToEntrance;	}

	public Enum1152 getPredominantTenure() {
		return PredominantTenure;	}

	public void setPredominantTenure(final Enum1152 PredominantTenure) {
		this.PredominantTenure = PredominantTenure;	}

	public Enum1153 getHHSRS_DampAndMould() {
		return HHSRS_DampAndMould;	}

	public void setHHSRS_DampAndMould(final Enum1153 HHSRS_DampAndMould) {
		this.HHSRS_DampAndMould = HHSRS_DampAndMould;	}

	public Enum1154 getPredominantResidentialBuildingType() {
		return PredominantResidentialBuildingType;	}

	public void setPredominantResidentialBuildingType(final Enum1154 PredominantResidentialBuildingType) {
		this.PredominantResidentialBuildingType = PredominantResidentialBuildingType;	}

	public Enum10 getDRAINAGE_Blockage() {
		return DRAINAGE_Blockage;	}

	public void setDRAINAGE_Blockage(final Enum10 DRAINAGE_Blockage) {
		this.DRAINAGE_Blockage = DRAINAGE_Blockage;	}

	public Enum10 getPARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present() {
		return PARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present;	}

	public void setPARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present(final Enum10 PARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present) {
		this.PARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present = PARKINGPROVISIONOFSURVEYDWELLINGIntegralGarage_Present;	}

	public Enum10 getPARKING_DetachedGarage_Present() {
		return PARKING_DetachedGarage_Present;	}

	public void setPARKING_DetachedGarage_Present(final Enum10 PARKING_DetachedGarage_Present) {
		this.PARKING_DetachedGarage_Present = PARKING_DetachedGarage_Present;	}

	public Enum10 getACCESSIBILITY_IsPathFirmAndEven_() {
		return ACCESSIBILITY_IsPathFirmAndEven_;	}

	public void setACCESSIBILITY_IsPathFirmAndEven_(final Enum10 ACCESSIBILITY_IsPathFirmAndEven_) {
		this.ACCESSIBILITY_IsPathFirmAndEven_ = ACCESSIBILITY_IsPathFirmAndEven_;	}

	public Enum1159 getHHSRS_FallsBetweenLevel() {
		return HHSRS_FallsBetweenLevel;	}

	public void setHHSRS_FallsBetweenLevel(final Enum1159 HHSRS_FallsBetweenLevel) {
		this.HHSRS_FallsBetweenLevel = HHSRS_FallsBetweenLevel;	}

	public Enum10 getPARKING_CarPort_Present() {
		return PARKING_CarPort_Present;	}

	public void setPARKING_CarPort_Present(final Enum10 PARKING_CarPort_Present) {
		this.PARKING_CarPort_Present = PARKING_CarPort_Present;	}

	public Enum1161 getPARKING_WhoOwnsCarPlotParking() {
		return PARKING_WhoOwnsCarPlotParking;	}

	public void setPARKING_WhoOwnsCarPlotParking(final Enum1161 PARKING_WhoOwnsCarPlotParking) {
		this.PARKING_WhoOwnsCarPlotParking = PARKING_WhoOwnsCarPlotParking;	}

	public Enum10 getCWICheck_LoftSpace() {
		return CWICheck_LoftSpace;	}

	public void setCWICheck_LoftSpace(final Enum10 CWICheck_LoftSpace) {
		this.CWICheck_LoftSpace = CWICheck_LoftSpace;	}

	public Enum1163 getNumberOfDwellingsInArea() {
		return NumberOfDwellingsInArea;	}

	public void setNumberOfDwellingsInArea(final Enum1163 NumberOfDwellingsInArea) {
		this.NumberOfDwellingsInArea = NumberOfDwellingsInArea;	}

	public Enum1164 getPredominantAge() {
		return PredominantAge;	}

	public void setPredominantAge(final Enum1164 PredominantAge) {
		this.PredominantAge = PredominantAge;	}

	public Enum1165 getSituationOfBlock() {
		return SituationOfBlock;	}

	public void setSituationOfBlock(final Enum1165 SituationOfBlock) {
		this.SituationOfBlock = SituationOfBlock;	}

	public Enum10 getACCESSIBILITY_IsPathAtLeast900MmWide() {
		return ACCESSIBILITY_IsPathAtLeast900MmWide;	}

	public void setACCESSIBILITY_IsPathAtLeast900MmWide(final Enum10 ACCESSIBILITY_IsPathAtLeast900MmWide) {
		this.ACCESSIBILITY_IsPathAtLeast900MmWide = ACCESSIBILITY_IsPathAtLeast900MmWide;	}

	public Enum1167 getUNDERGROUNDDRAINAGE_DrainageSystem() {
		return UNDERGROUNDDRAINAGE_DrainageSystem;	}

	public void setUNDERGROUNDDRAINAGE_DrainageSystem(final Enum1167 UNDERGROUNDDRAINAGE_DrainageSystem) {
		this.UNDERGROUNDDRAINAGE_DrainageSystem = UNDERGROUNDDRAINAGE_DrainageSystem;	}

	public Enum1161 getPARKING_WhoOwnsDetachedGarageParking() {
		return PARKING_WhoOwnsDetachedGarageParking;	}

	public void setPARKING_WhoOwnsDetachedGarageParking(final Enum1161 PARKING_WhoOwnsDetachedGarageParking) {
		this.PARKING_WhoOwnsDetachedGarageParking = PARKING_WhoOwnsDetachedGarageParking;	}

	public Enum10 getACCESSIBILITY_IsGradientLessThan1In12() {
		return ACCESSIBILITY_IsGradientLessThan1In12;	}

	public void setACCESSIBILITY_IsGradientLessThan1In12(final Enum10 ACCESSIBILITY_IsGradientLessThan1In12) {
		this.ACCESSIBILITY_IsGradientLessThan1In12 = ACCESSIBILITY_IsGradientLessThan1In12;	}

	public Enum1170 getNatureOfArea() {
		return NatureOfArea;	}

	public void setNatureOfArea(final Enum1170 NatureOfArea) {
		this.NatureOfArea = NatureOfArea;	}

	public Enum10 getDRAINAGE_Other() {
		return DRAINAGE_Other;	}

	public void setDRAINAGE_Other(final Enum10 DRAINAGE_Other) {
		this.DRAINAGE_Other = DRAINAGE_Other;	}

	public Enum10 getCWICheck_AreaAroundMeters() {
		return CWICheck_AreaAroundMeters;	}

	public void setCWICheck_AreaAroundMeters(final Enum10 CWICheck_AreaAroundMeters) {
		this.CWICheck_AreaAroundMeters = CWICheck_AreaAroundMeters;	}

	public Enum10 getDoesFrontPlotExist_() {
		return DoesFrontPlotExist_;	}

	public void setDoesFrontPlotExist_(final Enum10 DoesFrontPlotExist_) {
		this.DoesFrontPlotExist_ = DoesFrontPlotExist_;	}

	public Enum10 getPARKING_DesignatedParking_Present() {
		return PARKING_DesignatedParking_Present;	}

	public void setPARKING_DesignatedParking_Present(final Enum10 PARKING_DesignatedParking_Present) {
		this.PARKING_DesignatedParking_Present = PARKING_DesignatedParking_Present;	}

	public Enum1153 getHHSRS_DomesticHygiene() {
		return HHSRS_DomesticHygiene;	}

	public void setHHSRS_DomesticHygiene(final Enum1153 HHSRS_DomesticHygiene) {
		this.HHSRS_DomesticHygiene = HHSRS_DomesticHygiene;	}

	public Enum10 getCWICheck_AirBricks() {
		return CWICheck_AirBricks;	}

	public void setCWICheck_AirBricks(final Enum10 CWICheck_AirBricks) {
		this.CWICheck_AirBricks = CWICheck_AirBricks;	}

	public Enum1159 getHHSRS_FallsOnStairs_Steps() {
		return HHSRS_FallsOnStairs_Steps;	}

	public void setHHSRS_FallsOnStairs_Steps(final Enum1159 HHSRS_FallsOnStairs_Steps) {
		this.HHSRS_FallsOnStairs_Steps = HHSRS_FallsOnStairs_Steps;	}

	public Enum10 getCWICheck_ElevationFeatures() {
		return CWICheck_ElevationFeatures;	}

	public void setCWICheck_ElevationFeatures(final Enum10 CWICheck_ElevationFeatures) {
		this.CWICheck_ElevationFeatures = CWICheck_ElevationFeatures;	}

	public Enum10 getCWICheck_OccupantResponse() {
		return CWICheck_OccupantResponse;	}

	public void setCWICheck_OccupantResponse(final Enum10 CWICheck_OccupantResponse) {
		this.CWICheck_OccupantResponse = CWICheck_OccupantResponse;	}

	public Enum1180 getPARKING_SteetParking() {
		return PARKING_SteetParking;	}

	public void setPARKING_SteetParking(final Enum1180 PARKING_SteetParking) {
		this.PARKING_SteetParking = PARKING_SteetParking;	}

	public Enum10 getIsCavityWallInsulationPresent_() {
		return IsCavityWallInsulationPresent_;	}

	public void setIsCavityWallInsulationPresent_(final Enum10 IsCavityWallInsulationPresent_) {
		this.IsCavityWallInsulationPresent_ = IsCavityWallInsulationPresent_;	}

	public Enum10 getPARKING_OnPlot() {
		return PARKING_OnPlot;	}

	public void setPARKING_OnPlot(final Enum10 PARKING_OnPlot) {
		this.PARKING_OnPlot = PARKING_OnPlot;	}

	public Enum1161 getPARKING_WhoOwnsDesignatedParkingSpace() {
		return PARKING_WhoOwnsDesignatedParkingSpace;	}

	public void setPARKING_WhoOwnsDesignatedParkingSpace(final Enum1161 PARKING_WhoOwnsDesignatedParkingSpace) {
		this.PARKING_WhoOwnsDesignatedParkingSpace = PARKING_WhoOwnsDesignatedParkingSpace;	}

	public Enum1161 getPARKING_WhoOwnsAttachedGarageParking() {
		return PARKING_WhoOwnsAttachedGarageParking;	}

	public void setPARKING_WhoOwnsAttachedGarageParking(final Enum1161 PARKING_WhoOwnsAttachedGarageParking) {
		this.PARKING_WhoOwnsAttachedGarageParking = PARKING_WhoOwnsAttachedGarageParking;	}

	public Enum1185 getPARKING_Action() {
		return PARKING_Action;	}

	public void setPARKING_Action(final Enum1185 PARKING_Action) {
		this.PARKING_Action = PARKING_Action;	}

	public Enum1186 getTypeOfPlot() {
		return TypeOfPlot;	}

	public void setTypeOfPlot(final Enum1186 TypeOfPlot) {
		this.TypeOfPlot = TypeOfPlot;	}

	public Enum1159 getHHSRS_FallsOnTheLevel() {
		return HHSRS_FallsOnTheLevel;	}

	public void setHHSRS_FallsOnTheLevel(final Enum1159 HHSRS_FallsOnTheLevel) {
		this.HHSRS_FallsOnTheLevel = HHSRS_FallsOnTheLevel;	}

	public Enum10 getDRAINAGE_Faults() {
		return DRAINAGE_Faults;	}

	public void setDRAINAGE_Faults(final Enum10 DRAINAGE_Faults) {
		this.DRAINAGE_Faults = DRAINAGE_Faults;	}

	public Enum1153 getHHSRS_CollisionAndEntrapment() {
		return HHSRS_CollisionAndEntrapment;	}

	public void setHHSRS_CollisionAndEntrapment(final Enum1153 HHSRS_CollisionAndEntrapment) {
		this.HHSRS_CollisionAndEntrapment = HHSRS_CollisionAndEntrapment;	}

	public Enum1190 getIfAreaIsLAEstate__OfRTBDwellings() {
		return IfAreaIsLAEstate__OfRTBDwellings;	}

	public void setIfAreaIsLAEstate__OfRTBDwellings(final Enum1190 IfAreaIsLAEstate__OfRTBDwellings) {
		this.IfAreaIsLAEstate__OfRTBDwellings = IfAreaIsLAEstate__OfRTBDwellings;	}

	public Enum1185 getLitterRubbishAroundHouseModule() {
		return LitterRubbishAroundHouseModule;	}

	public void setLitterRubbishAroundHouseModule(final Enum1185 LitterRubbishAroundHouseModule) {
		this.LitterRubbishAroundHouseModule = LitterRubbishAroundHouseModule;	}

	public Enum1192 getACCESSIBILITY_SpaceForRamp() {
		return ACCESSIBILITY_SpaceForRamp;	}

	public void setACCESSIBILITY_SpaceForRamp(final Enum1192 ACCESSIBILITY_SpaceForRamp) {
		this.ACCESSIBILITY_SpaceForRamp = ACCESSIBILITY_SpaceForRamp;	}

	public Enum1185 getPARKING_Action_FCUINTAC() {
		return PARKING_Action_FCUINTAC;	}

	public void setPARKING_Action_FCUINTAC(final Enum1185 PARKING_Action_FCUINTAC) {
		this.PARKING_Action_FCUINTAC = PARKING_Action_FCUINTAC;	}

	public Enum10 getPARKING_OnPlot_FCUPORLO() {
		return PARKING_OnPlot_FCUPORLO;	}

	public void setPARKING_OnPlot_FCUPORLO(final Enum10 PARKING_OnPlot_FCUPORLO) {
		this.PARKING_OnPlot_FCUPORLO = PARKING_OnPlot_FCUPORLO;	}

	public Enum1153 getHHSRS_PersonalHygiene() {
		return HHSRS_PersonalHygiene;	}

	public void setHHSRS_PersonalHygiene(final Enum1153 HHSRS_PersonalHygiene) {
		this.HHSRS_PersonalHygiene = HHSRS_PersonalHygiene;	}

	public Enum10 getACCESSIBILITY_IsEntranceAdequatelyLit() {
		return ACCESSIBILITY_IsEntranceAdequatelyLit;	}

	public void setACCESSIBILITY_IsEntranceAdequatelyLit(final Enum10 ACCESSIBILITY_IsEntranceAdequatelyLit) {
		this.ACCESSIBILITY_IsEntranceAdequatelyLit = ACCESSIBILITY_IsEntranceAdequatelyLit;	}

	public Enum1161 getPARKING_WhoOwnsGarageParking_() {
		return PARKING_WhoOwnsGarageParking_;	}

	public void setPARKING_WhoOwnsGarageParking_(final Enum1161 PARKING_WhoOwnsGarageParking_) {
		this.PARKING_WhoOwnsGarageParking_ = PARKING_WhoOwnsGarageParking_;	}

	public Enum10 getPARKING_AttachedGarage_Present() {
		return PARKING_AttachedGarage_Present;	}

	public void setPARKING_AttachedGarage_Present(final Enum10 PARKING_AttachedGarage_Present) {
		this.PARKING_AttachedGarage_Present = PARKING_AttachedGarage_Present;	}

	public Enum1153 getHHSRS_EntryByIntruders() {
		return HHSRS_EntryByIntruders;	}

	public void setHHSRS_EntryByIntruders(final Enum1153 HHSRS_EntryByIntruders) {
		this.HHSRS_EntryByIntruders = HHSRS_EntryByIntruders;	}

	public Enum10 getACCESSIBILITY_IsEntranceCovered() {
		return ACCESSIBILITY_IsEntranceCovered;	}

	public void setACCESSIBILITY_IsEntranceCovered(final Enum10 ACCESSIBILITY_IsEntranceCovered) {
		this.ACCESSIBILITY_IsEntranceCovered = ACCESSIBILITY_IsEntranceCovered;	}

	public Enum1185 getPARKING_Action_FCUPORAC() {
		return PARKING_Action_FCUPORAC;	}

	public void setPARKING_Action_FCUPORAC(final Enum1185 PARKING_Action_FCUPORAC) {
		this.PARKING_Action_FCUPORAC = PARKING_Action_FCUPORAC;	}

	public Enum1202 getEXPOSUREIsTheDwellingInAnExposedPosition_() {
		return EXPOSUREIsTheDwellingInAnExposedPosition_;	}

	public void setEXPOSUREIsTheDwellingInAnExposedPosition_(final Enum1202 EXPOSUREIsTheDwellingInAnExposedPosition_) {
		this.EXPOSUREIsTheDwellingInAnExposedPosition_ = EXPOSUREIsTheDwellingInAnExposedPosition_;	}

	public Enum10 getDoesRearPlotExist_() {
		return DoesRearPlotExist_;	}

	public void setDoesRearPlotExist_(final Enum10 DoesRearPlotExist_) {
		this.DoesRearPlotExist_ = DoesRearPlotExist_;	}

	public Enum1185 getPARKING_Action_FCUATTAC() {
		return PARKING_Action_FCUATTAC;	}

	public void setPARKING_Action_FCUATTAC(final Enum1185 PARKING_Action_FCUATTAC) {
		this.PARKING_Action_FCUATTAC = PARKING_Action_FCUATTAC;	}

	public Enum10 getPARKINGPROVISIONOFSURVEYDWELLINGOnPlot() {
		return PARKINGPROVISIONOFSURVEYDWELLINGOnPlot;	}

	public void setPARKINGPROVISIONOFSURVEYDWELLINGOnPlot(final Enum10 PARKINGPROVISIONOFSURVEYDWELLINGOnPlot) {
		this.PARKINGPROVISIONOFSURVEYDWELLINGOnPlot = PARKINGPROVISIONOFSURVEYDWELLINGOnPlot;	}

	public Enum10 getPARKING_OnPlot_FCUDETLO() {
		return PARKING_OnPlot_FCUDETLO;	}

	public void setPARKING_OnPlot_FCUDETLO(final Enum10 PARKING_OnPlot_FCUDETLO) {
		this.PARKING_OnPlot_FCUDETLO = PARKING_OnPlot_FCUDETLO;	}

	public Enum10 getPARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_() {
		return PARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_;	}

	public void setPARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_(final Enum10 PARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_) {
		this.PARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_ = PARKING_IsOff_PlotParkingWithin30MOfEntrance_EvenAccessRoute_;	}

	public Enum10 getPARKING_OnPlot_FCUATTLO() {
		return PARKING_OnPlot_FCUATTLO;	}

	public void setPARKING_OnPlot_FCUATTLO(final Enum10 PARKING_OnPlot_FCUATTLO) {
		this.PARKING_OnPlot_FCUATTLO = PARKING_OnPlot_FCUATTLO;	}

	public Enum1185 getPARKING_Action_FCUSPAAC() {
		return PARKING_Action_FCUSPAAC;	}

	public void setPARKING_Action_FCUSPAAC(final Enum1185 PARKING_Action_FCUSPAAC) {
		this.PARKING_Action_FCUSPAAC = PARKING_Action_FCUSPAAC;	}

}

