package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1233;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1553;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1556;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1583;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1584;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface InteriorEntry extends SurveyEntry {
	@SavVariableMapping("FINLIVLE")
	public String getLivingRoomLevel();

	@SavVariableMapping("FINKITLE")
	public String getKitchenLevel();

	@SavVariableMapping("FINBEDLE")
	public String getBedroomLevel();

	@SavVariableMapping("FINBATLE")
	public String getBathroomLevel();

	@SavVariableMapping("FINGARLE")
	public String getIntegralGarage_Level();

	@SavVariableMapping("FINBALLE")
	public String getIntegralBalcony_Level();

	@SavVariableMapping("FINEX1LE")
	public String getExtraRoom1_Level();

	@SavVariableMapping("FINEX2LE")
	public String getExtraRoom2_Level();

	@SavVariableMapping("FINEX3LE")
	public String getExtraRoom3_Level();

	@SavVariableMapping("FINEX4LE")
	public String getExtraRoom4_Level();

	@SavVariableMapping("FINEX5LE")
	public String getExtraRoom5_Level();

	@SavVariableMapping("FINEX6LE")
	public String getExtraRoom6_Level();

	@SavVariableMapping("FINEX7LE")
	public String getExtraRoom7_Level();

	@SavVariableMapping("FINCOSIZ")
	public Integer getConservatory_AreaInM2();

	@SavVariableMapping("FINROOMS")
	public Integer getNumberHabitableRooms();

	@SavVariableMapping("FINCIRCL")
	public Double getCirculationCeilingHeight_Metres_();

	@SavVariableMapping("FINBEDCL")
	public Double getBedroomCeilingHeight_Metres_();

	@SavVariableMapping("FINLIVCL")
	public Double getLivingRoomCeilingHeight_Metres_();

	@SavVariableMapping("FINBEDWI")
	public Double getBedroomWidth_Metres_();

	@SavVariableMapping("FINBEDDE")
	public Double getBedroomDepth_Metres_();

	@SavVariableMapping("FINKITCL")
	public Double getKitchenCeilingHeight_Metres_();

	@SavVariableMapping("FINBATCL")
	public Double getBathroomCeilingHeight_Metres_();

	@SavVariableMapping("FINLIVDE")
	public Double getLivingRoomDepth_Metres_();

	@SavVariableMapping("FINKITDE")
	public Double getKitchenDepth_Metres_();

	@SavVariableMapping("FINKITWI")
	public Double getKitchenWidth_Metres_();

	@SavVariableMapping("FINLIVWI")
	public Double getLivingRoomWidth_Metres_();

	@SavVariableMapping("FINOTAL")
	public Enum10 getInadequateArtificialLighting_OtherRooms();

	@SavVariableMapping("FINSTROP")
	public Enum10 getStairsWithinDwelling_OpenPlan_();

	@SavVariableMapping("FINEX6FU")
	public Enum1553 getExtraRoom6_Function();

	@SavVariableMapping("FINLIVEX")
	public Enum10 getDoesLivingRoomExist_();

	@SavVariableMapping("FINHSDHY")
	public Enum1233 getHEALTHANDSAFETY_DomesticHygiene();

	@SavVariableMapping("FINSECME")
	public Enum1556 getSecurityOfDwelling_MainEntranceDoor();

	@SavVariableMapping("FINOTMO")
	public Enum10 getSeriousCondensation_OtherRooms();

	@SavVariableMapping("FINCIRIN")
	public Enum10 getCirculationInspected();

	@SavVariableMapping("FINEX5EX")
	public Enum10 getExtraRoom5_DoesRoomExist_();

	@SavVariableMapping("FINCORAD")
	public Enum1282 getFixedRadiatorOrOtherFixedRadiatorPresent_();

	@SavVariableMapping("FINHSSTR")
	public Enum1233 getHEALTHANDSAFETY_FallsOnStairs();

	@SavVariableMapping("FINEX1FU")
	public Enum1553 getExtraRoom1_Function();

	@SavVariableMapping("FINSTRFL")
	public Enum10 getStairsWithinDwelling_Faults_();

	@SavVariableMapping("FINOTIN")
	public Enum10 getWoodBoringInsect_OtherRooms();

	@SavVariableMapping("FINLANDS")
	public Enum10 getAccessibility_StraightStairsWithLandingsGreaterThan900Mm_();

	@SavVariableMapping("FINCODOR")
	public Enum10 getConservatory_ClosableDoor();

	@SavVariableMapping("FINEX5FU")
	public Enum1553 getExtraRoom5_Function();

	@SavVariableMapping("FINWAWEN")
	public Enum10 getAccessibility_WheelchairAccessibleWCAtEntranceLevel_();

	@SavVariableMapping("FINOTPD")
	public Enum10 getPenetratingDamp_OtherRooms();

	@SavVariableMapping("FINBEDFU")
	public Enum1553 getBedroomFunction();

	@SavVariableMapping("FINEX4FU")
	public Enum1553 getExtraRoom4_Function();

	@SavVariableMapping("FINOTVT")
	public Enum10 getInadequateApplianceVentialtion_OtherRooms();

	@SavVariableMapping("FINHSNOI")
	public Enum1233 getHEALTHANDSAFETY_Noise();

	@SavVariableMapping("FINCIRCU")
	public Enum10 getAccessibility_DoorsetsAndCirculationMeetPartM_();

	@SavVariableMapping("FINBEDEN")
	public Enum10 getAccessibility_RoomOnEntranceLevelSuitableForBedroom_();

	@SavVariableMapping("FINSTRTR")
	public Enum10 getStairsWithinDwelling_ReplaceTreads();

	@SavVariableMapping("FINHSHOT")
	public Enum1233 getHEALTHANDSAFETY_HotSurfaces();

	@SavVariableMapping("FINBATIN")
	public Enum10 getBathroomInspected_();

	@SavVariableMapping("FINEX2EX")
	public Enum10 getExtraRoom2_DoesRoomExist_();

	@SavVariableMapping("FINLIVFU")
	public Enum1553 getLivingRoomFunction();

	@SavVariableMapping("FINBEDEX")
	public Enum10 getDoesBedroomExist_();

	@SavVariableMapping("FINHSBTW")
	public Enum1233 getHEALTHANDSAFETY_FallsBetweenStairs();

	@SavVariableMapping("FINCOWIN")
	public Enum1583 getConservatory_WindowType();

	@SavVariableMapping("FINCOROF")
	public Enum1584 getConservatory_RoofType();

	@SavVariableMapping("FINEX3FU")
	public Enum1553 getExtraRoom3_Function();

	@SavVariableMapping("FINSECBA")
	public Enum10 getSecurityOfDwelling_BurglarAlarmPresent();

	@SavVariableMapping("FINEX1EX")
	public Enum10 getExtraRoom1_DoesRoomExist_();

	@SavVariableMapping("FINSEPUN")
	public Enum10 getSeparableUnits_();

	@SavVariableMapping("FINTRPEN")
	public Enum10 getAccessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_();

	@SavVariableMapping("FINEX7FU")
	public Enum1553 getExtraRoom7_Function();

	@SavVariableMapping("FINLIFTS")
	public Enum10 getAdaptationsForTheDisabled_StairLift();

	@SavVariableMapping("FINKITEX")
	public Enum10 getDoesKitchenExist_();

	@SavVariableMapping("FINEX7EX")
	public Enum10 getExtraRoom7_DoesRoomExist_();

	@SavVariableMapping("FINSECWN")
	public Enum1556 getSecurityOfDwelling_AccessibleWindows();

	@SavVariableMapping("FINLIVIN")
	public Enum10 getLivingRoomInspected_();

	@SavVariableMapping("FINBEDIN")
	public Enum10 getBedroomInspected_();

	@SavVariableMapping("FINKITFU")
	public Enum1553 getKitchenFunction();

	@SavVariableMapping("FINHSLIT")
	public Enum1233 getHEALTHANDSAFETY_Lighting();

	@SavVariableMapping("FINHSCEN")
	public Enum1233 getHEALTHANDSAFETY_CollisionAndEntrapment();

	@SavVariableMapping("FINOTRD")
	public Enum10 getRisingDamp_OtherRooms();

	@SavVariableMapping("FINFLUSH")
	public Enum10 getAccessibility_FlushThresholdLessThan15Mm_();

	@SavVariableMapping("FINSECSM")
	public Enum10 getSecurityOfDwelling_SmokeDetectorsPresent();

	@SavVariableMapping("FINOTRV")
	public Enum10 getInadequateRoomVentilation_OtherRooms();

	@SavVariableMapping("FINRAMPS")
	public Enum10 getAdaptationsForTheDisabled_Ramps();

	@SavVariableMapping("FINSTRRP")
	public Enum10 getStairsWithinDwelling_RepairRefixTreadsBalustrades();

	@SavVariableMapping("FINSECOT")
	public Enum1556 getSecurityOfDwelling_OtherExternalDoors();

	@SavVariableMapping("FINGAREX")
	public Enum10 getIntegralGarage_DoesRoomExist_();

	@SavVariableMapping("FINEX2FU")
	public Enum1553 getExtraRoom2_Function();

	@SavVariableMapping("FINGRABR")
	public Enum10 getAdaptationsForTheDisabled_GrabRails();

	@SavVariableMapping("FINHSDAM")
	public Enum1233 getHEALTHANDSAFETY_DampAndMould();

	@SavVariableMapping("FINSECVW")
	public Enum10 getSecurityOfDwelling_DoorViewerPresent();

	@SavVariableMapping("FINSTRRN")
	public Enum10 getStairsWithinDwelling_ReplaceStructure();

	@SavVariableMapping("FINBATEX")
	public Enum10 getDoesBathroomExist_();

	@SavVariableMapping("FINHOIST")
	public Enum10 getAdaptationsForTheDisabled_Hoists();

	@SavVariableMapping("FINEX4EX")
	public Enum10 getExtraRoom4_DoesRoomExist_();

	@SavVariableMapping("FINCIREX")
	public Enum10 getDoesCirculationExist_();

	@SavVariableMapping("FINBALEX")
	public Enum10 getIntegralBalcony_DoesRoomExist_();

	@SavVariableMapping("FINSTRBL")
	public Enum10 getStairsWithinDwelling_ReplaceBalustrades();

	@SavVariableMapping("FINHSFIR")
	public Enum1233 getHEALTHANDSAFETY_Fire();

	@SavVariableMapping("FINEX3EX")
	public Enum10 getExtraRoom3_DoesRoomExist_();

	@SavVariableMapping("FINHSENT")
	public Enum1233 getHEALTHANDSAFETY_EntryByIntruders();

	@SavVariableMapping("FINOTNL")
	public Enum10 getInadequateNaturalLighting_OtherRooms();

	@SavVariableMapping("FINEX6EX")
	public Enum10 getExtraRoom6_DoesRoomExist_();

	@SavVariableMapping("FINELECM")
	public Enum10 getAdaptationsForTheDisabled_ElectricalModifications();

	@SavVariableMapping("FINWCEN")
	public Enum10 getAccessibility_WCAtEntranceLevel_();

	@SavVariableMapping("FINKITIN")
	public Enum10 getKitchenInspected_();

	@SavVariableMapping("FINSTRPR")
	public Enum10 getStairsWithinDwelling_Present_();

	@SavVariableMapping("FINHSLVL")
	public Enum1233 getHEALTHANDSAFETY_FallsOnTheLevel();

	@SavVariableMapping("FINHSEXH")
	public Enum1233 getHEALTHANDSAFETY_ExcessHeat();

	@SavVariableMapping("FINBATEN")
	public Enum10 getAccessibility_BathroomAtEntranceLevel_();

	@SavVariableMapping("FINOTRT")
	public Enum10 getDryWetRot_OtherRooms();

}

