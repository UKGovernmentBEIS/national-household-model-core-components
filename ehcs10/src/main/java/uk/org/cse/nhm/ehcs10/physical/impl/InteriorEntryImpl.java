package uk.org.cse.nhm.ehcs10.physical.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.InteriorEntry;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1233;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1553;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1556;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1583;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1584;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class InteriorEntryImpl extends SurveyEntryImpl implements InteriorEntry {
	private String LivingRoomLevel;
	private String KitchenLevel;
	private String BedroomLevel;
	private String BathroomLevel;
	private String IntegralGarage_Level;
	private String IntegralBalcony_Level;
	private String ExtraRoom1_Level;
	private String ExtraRoom2_Level;
	private String ExtraRoom3_Level;
	private String ExtraRoom4_Level;
	private String ExtraRoom5_Level;
	private String ExtraRoom6_Level;
	private String ExtraRoom7_Level;
	private Integer Conservatory_AreaInM2;
	private Integer NumberHabitableRooms;
	private Double CirculationCeilingHeight_Metres_;
	private Double BedroomCeilingHeight_Metres_;
	private Double LivingRoomCeilingHeight_Metres_;
	private Double BedroomWidth_Metres_;
	private Double BedroomDepth_Metres_;
	private Double KitchenCeilingHeight_Metres_;
	private Double BathroomCeilingHeight_Metres_;
	private Double LivingRoomDepth_Metres_;
	private Double KitchenDepth_Metres_;
	private Double KitchenWidth_Metres_;
	private Double LivingRoomWidth_Metres_;
	private Enum10 InadequateArtificialLighting_OtherRooms;
	private Enum10 StairsWithinDwelling_OpenPlan_;
	private Enum1553 ExtraRoom6_Function;
	private Enum10 DoesLivingRoomExist_;
	private Enum1233 HEALTHANDSAFETY_DomesticHygiene;
	private Enum1556 SecurityOfDwelling_MainEntranceDoor;
	private Enum10 SeriousCondensation_OtherRooms;
	private Enum10 CirculationInspected;
	private Enum10 ExtraRoom5_DoesRoomExist_;
	private Enum1282 FixedRadiatorOrOtherFixedRadiatorPresent_;
	private Enum1233 HEALTHANDSAFETY_FallsOnStairs;
	private Enum1553 ExtraRoom1_Function;
	private Enum10 StairsWithinDwelling_Faults_;
	private Enum10 WoodBoringInsect_OtherRooms;
	private Enum10 Accessibility_StraightStairsWithLandingsGreaterThan900Mm_;
	private Enum10 Conservatory_ClosableDoor;
	private Enum1553 ExtraRoom5_Function;
	private Enum10 Accessibility_WheelchairAccessibleWCAtEntranceLevel_;
	private Enum10 PenetratingDamp_OtherRooms;
	private Enum1553 BedroomFunction;
	private Enum1553 ExtraRoom4_Function;
	private Enum10 InadequateApplianceVentialtion_OtherRooms;
	private Enum1233 HEALTHANDSAFETY_Noise;
	private Enum10 Accessibility_DoorsetsAndCirculationMeetPartM_;
	private Enum10 Accessibility_RoomOnEntranceLevelSuitableForBedroom_;
	private Enum10 StairsWithinDwelling_ReplaceTreads;
	private Enum1233 HEALTHANDSAFETY_HotSurfaces;
	private Enum10 BathroomInspected_;
	private Enum10 ExtraRoom2_DoesRoomExist_;
	private Enum1553 LivingRoomFunction;
	private Enum10 DoesBedroomExist_;
	private Enum1233 HEALTHANDSAFETY_FallsBetweenStairs;
	private Enum1583 Conservatory_WindowType;
	private Enum1584 Conservatory_RoofType;
	private Enum1553 ExtraRoom3_Function;
	private Enum10 SecurityOfDwelling_BurglarAlarmPresent;
	private Enum10 ExtraRoom1_DoesRoomExist_;
	private Enum10 SeparableUnits_;
	private Enum10 Accessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_;
	private Enum1553 ExtraRoom7_Function;
	private Enum10 AdaptationsForTheDisabled_StairLift;
	private Enum10 DoesKitchenExist_;
	private Enum10 ExtraRoom7_DoesRoomExist_;
	private Enum1556 SecurityOfDwelling_AccessibleWindows;
	private Enum10 LivingRoomInspected_;
	private Enum10 BedroomInspected_;
	private Enum1553 KitchenFunction;
	private Enum1233 HEALTHANDSAFETY_Lighting;
	private Enum1233 HEALTHANDSAFETY_CollisionAndEntrapment;
	private Enum10 RisingDamp_OtherRooms;
	private Enum10 Accessibility_FlushThresholdLessThan15Mm_;
	private Enum10 SecurityOfDwelling_SmokeDetectorsPresent;
	private Enum10 InadequateRoomVentilation_OtherRooms;
	private Enum10 AdaptationsForTheDisabled_Ramps;
	private Enum10 StairsWithinDwelling_RepairRefixTreadsBalustrades;
	private Enum1556 SecurityOfDwelling_OtherExternalDoors;
	private Enum10 IntegralGarage_DoesRoomExist_;
	private Enum1553 ExtraRoom2_Function;
	private Enum10 AdaptationsForTheDisabled_GrabRails;
	private Enum1233 HEALTHANDSAFETY_DampAndMould;
	private Enum10 SecurityOfDwelling_DoorViewerPresent;
	private Enum10 StairsWithinDwelling_ReplaceStructure;
	private Enum10 DoesBathroomExist_;
	private Enum10 AdaptationsForTheDisabled_Hoists;
	private Enum10 ExtraRoom4_DoesRoomExist_;
	private Enum10 DoesCirculationExist_;
	private Enum10 IntegralBalcony_DoesRoomExist_;
	private Enum10 StairsWithinDwelling_ReplaceBalustrades;
	private Enum1233 HEALTHANDSAFETY_Fire;
	private Enum10 ExtraRoom3_DoesRoomExist_;
	private Enum1233 HEALTHANDSAFETY_EntryByIntruders;
	private Enum10 InadequateNaturalLighting_OtherRooms;
	private Enum10 ExtraRoom6_DoesRoomExist_;
	private Enum10 AdaptationsForTheDisabled_ElectricalModifications;
	private Enum10 Accessibility_WCAtEntranceLevel_;
	private Enum10 KitchenInspected_;
	private Enum10 StairsWithinDwelling_Present_;
	private Enum1233 HEALTHANDSAFETY_FallsOnTheLevel;
	private Enum1233 HEALTHANDSAFETY_ExcessHeat;
	private Enum10 Accessibility_BathroomAtEntranceLevel_;
	private Enum10 DryWetRot_OtherRooms;

    /**
     * Default Constructor.
     */
    public InteriorEntryImpl() {
        super();
    }

	public String getLivingRoomLevel() {
		return LivingRoomLevel;	}

	public void setLivingRoomLevel(final String LivingRoomLevel) {
		this.LivingRoomLevel = LivingRoomLevel;	}

	public String getKitchenLevel() {
		return KitchenLevel;	}

	public void setKitchenLevel(final String KitchenLevel) {
		this.KitchenLevel = KitchenLevel;	}

	public String getBedroomLevel() {
		return BedroomLevel;	}

	public void setBedroomLevel(final String BedroomLevel) {
		this.BedroomLevel = BedroomLevel;	}

	public String getBathroomLevel() {
		return BathroomLevel;	}

	public void setBathroomLevel(final String BathroomLevel) {
		this.BathroomLevel = BathroomLevel;	}

	public String getIntegralGarage_Level() {
		return IntegralGarage_Level;	}

	public void setIntegralGarage_Level(final String IntegralGarage_Level) {
		this.IntegralGarage_Level = IntegralGarage_Level;	}

	public String getIntegralBalcony_Level() {
		return IntegralBalcony_Level;	}

	public void setIntegralBalcony_Level(final String IntegralBalcony_Level) {
		this.IntegralBalcony_Level = IntegralBalcony_Level;	}

	public String getExtraRoom1_Level() {
		return ExtraRoom1_Level;	}

	public void setExtraRoom1_Level(final String ExtraRoom1_Level) {
		this.ExtraRoom1_Level = ExtraRoom1_Level;	}

	public String getExtraRoom2_Level() {
		return ExtraRoom2_Level;	}

	public void setExtraRoom2_Level(final String ExtraRoom2_Level) {
		this.ExtraRoom2_Level = ExtraRoom2_Level;	}

	public String getExtraRoom3_Level() {
		return ExtraRoom3_Level;	}

	public void setExtraRoom3_Level(final String ExtraRoom3_Level) {
		this.ExtraRoom3_Level = ExtraRoom3_Level;	}

	public String getExtraRoom4_Level() {
		return ExtraRoom4_Level;	}

	public void setExtraRoom4_Level(final String ExtraRoom4_Level) {
		this.ExtraRoom4_Level = ExtraRoom4_Level;	}

	public String getExtraRoom5_Level() {
		return ExtraRoom5_Level;	}

	public void setExtraRoom5_Level(final String ExtraRoom5_Level) {
		this.ExtraRoom5_Level = ExtraRoom5_Level;	}

	public String getExtraRoom6_Level() {
		return ExtraRoom6_Level;	}

	public void setExtraRoom6_Level(final String ExtraRoom6_Level) {
		this.ExtraRoom6_Level = ExtraRoom6_Level;	}

	public String getExtraRoom7_Level() {
		return ExtraRoom7_Level;	}

	public void setExtraRoom7_Level(final String ExtraRoom7_Level) {
		this.ExtraRoom7_Level = ExtraRoom7_Level;	}

	public Integer getConservatory_AreaInM2() {
		return Conservatory_AreaInM2;	}

	public void setConservatory_AreaInM2(final Integer Conservatory_AreaInM2) {
		this.Conservatory_AreaInM2 = Conservatory_AreaInM2;	}

	public Integer getNumberHabitableRooms() {
		return NumberHabitableRooms;	}

	public void setNumberHabitableRooms(final Integer NumberHabitableRooms) {
		this.NumberHabitableRooms = NumberHabitableRooms;	}

	public Double getCirculationCeilingHeight_Metres_() {
		return CirculationCeilingHeight_Metres_;	}

	public void setCirculationCeilingHeight_Metres_(final Double CirculationCeilingHeight_Metres_) {
		this.CirculationCeilingHeight_Metres_ = CirculationCeilingHeight_Metres_;	}

	public Double getBedroomCeilingHeight_Metres_() {
		return BedroomCeilingHeight_Metres_;	}

	public void setBedroomCeilingHeight_Metres_(final Double BedroomCeilingHeight_Metres_) {
		this.BedroomCeilingHeight_Metres_ = BedroomCeilingHeight_Metres_;	}

	public Double getLivingRoomCeilingHeight_Metres_() {
		return LivingRoomCeilingHeight_Metres_;	}

	public void setLivingRoomCeilingHeight_Metres_(final Double LivingRoomCeilingHeight_Metres_) {
		this.LivingRoomCeilingHeight_Metres_ = LivingRoomCeilingHeight_Metres_;	}

	public Double getBedroomWidth_Metres_() {
		return BedroomWidth_Metres_;	}

	public void setBedroomWidth_Metres_(final Double BedroomWidth_Metres_) {
		this.BedroomWidth_Metres_ = BedroomWidth_Metres_;	}

	public Double getBedroomDepth_Metres_() {
		return BedroomDepth_Metres_;	}

	public void setBedroomDepth_Metres_(final Double BedroomDepth_Metres_) {
		this.BedroomDepth_Metres_ = BedroomDepth_Metres_;	}

	public Double getKitchenCeilingHeight_Metres_() {
		return KitchenCeilingHeight_Metres_;	}

	public void setKitchenCeilingHeight_Metres_(final Double KitchenCeilingHeight_Metres_) {
		this.KitchenCeilingHeight_Metres_ = KitchenCeilingHeight_Metres_;	}

	public Double getBathroomCeilingHeight_Metres_() {
		return BathroomCeilingHeight_Metres_;	}

	public void setBathroomCeilingHeight_Metres_(final Double BathroomCeilingHeight_Metres_) {
		this.BathroomCeilingHeight_Metres_ = BathroomCeilingHeight_Metres_;	}

	public Double getLivingRoomDepth_Metres_() {
		return LivingRoomDepth_Metres_;	}

	public void setLivingRoomDepth_Metres_(final Double LivingRoomDepth_Metres_) {
		this.LivingRoomDepth_Metres_ = LivingRoomDepth_Metres_;	}

	public Double getKitchenDepth_Metres_() {
		return KitchenDepth_Metres_;	}

	public void setKitchenDepth_Metres_(final Double KitchenDepth_Metres_) {
		this.KitchenDepth_Metres_ = KitchenDepth_Metres_;	}

	public Double getKitchenWidth_Metres_() {
		return KitchenWidth_Metres_;	}

	public void setKitchenWidth_Metres_(final Double KitchenWidth_Metres_) {
		this.KitchenWidth_Metres_ = KitchenWidth_Metres_;	}

	public Double getLivingRoomWidth_Metres_() {
		return LivingRoomWidth_Metres_;	}

	public void setLivingRoomWidth_Metres_(final Double LivingRoomWidth_Metres_) {
		this.LivingRoomWidth_Metres_ = LivingRoomWidth_Metres_;	}

	public Enum10 getInadequateArtificialLighting_OtherRooms() {
		return InadequateArtificialLighting_OtherRooms;	}

	public void setInadequateArtificialLighting_OtherRooms(final Enum10 InadequateArtificialLighting_OtherRooms) {
		this.InadequateArtificialLighting_OtherRooms = InadequateArtificialLighting_OtherRooms;	}

	public Enum10 getStairsWithinDwelling_OpenPlan_() {
		return StairsWithinDwelling_OpenPlan_;	}

	public void setStairsWithinDwelling_OpenPlan_(final Enum10 StairsWithinDwelling_OpenPlan_) {
		this.StairsWithinDwelling_OpenPlan_ = StairsWithinDwelling_OpenPlan_;	}

	public Enum1553 getExtraRoom6_Function() {
		return ExtraRoom6_Function;	}

	public void setExtraRoom6_Function(final Enum1553 ExtraRoom6_Function) {
		this.ExtraRoom6_Function = ExtraRoom6_Function;	}

	public Enum10 getDoesLivingRoomExist_() {
		return DoesLivingRoomExist_;	}

	public void setDoesLivingRoomExist_(final Enum10 DoesLivingRoomExist_) {
		this.DoesLivingRoomExist_ = DoesLivingRoomExist_;	}

	public Enum1233 getHEALTHANDSAFETY_DomesticHygiene() {
		return HEALTHANDSAFETY_DomesticHygiene;	}

	public void setHEALTHANDSAFETY_DomesticHygiene(final Enum1233 HEALTHANDSAFETY_DomesticHygiene) {
		this.HEALTHANDSAFETY_DomesticHygiene = HEALTHANDSAFETY_DomesticHygiene;	}

	public Enum1556 getSecurityOfDwelling_MainEntranceDoor() {
		return SecurityOfDwelling_MainEntranceDoor;	}

	public void setSecurityOfDwelling_MainEntranceDoor(final Enum1556 SecurityOfDwelling_MainEntranceDoor) {
		this.SecurityOfDwelling_MainEntranceDoor = SecurityOfDwelling_MainEntranceDoor;	}

	public Enum10 getSeriousCondensation_OtherRooms() {
		return SeriousCondensation_OtherRooms;	}

	public void setSeriousCondensation_OtherRooms(final Enum10 SeriousCondensation_OtherRooms) {
		this.SeriousCondensation_OtherRooms = SeriousCondensation_OtherRooms;	}

	public Enum10 getCirculationInspected() {
		return CirculationInspected;	}

	public void setCirculationInspected(final Enum10 CirculationInspected) {
		this.CirculationInspected = CirculationInspected;	}

	public Enum10 getExtraRoom5_DoesRoomExist_() {
		return ExtraRoom5_DoesRoomExist_;	}

	public void setExtraRoom5_DoesRoomExist_(final Enum10 ExtraRoom5_DoesRoomExist_) {
		this.ExtraRoom5_DoesRoomExist_ = ExtraRoom5_DoesRoomExist_;	}

	public Enum1282 getFixedRadiatorOrOtherFixedRadiatorPresent_() {
		return FixedRadiatorOrOtherFixedRadiatorPresent_;	}

	public void setFixedRadiatorOrOtherFixedRadiatorPresent_(final Enum1282 FixedRadiatorOrOtherFixedRadiatorPresent_) {
		this.FixedRadiatorOrOtherFixedRadiatorPresent_ = FixedRadiatorOrOtherFixedRadiatorPresent_;	}

	public Enum1233 getHEALTHANDSAFETY_FallsOnStairs() {
		return HEALTHANDSAFETY_FallsOnStairs;	}

	public void setHEALTHANDSAFETY_FallsOnStairs(final Enum1233 HEALTHANDSAFETY_FallsOnStairs) {
		this.HEALTHANDSAFETY_FallsOnStairs = HEALTHANDSAFETY_FallsOnStairs;	}

	public Enum1553 getExtraRoom1_Function() {
		return ExtraRoom1_Function;	}

	public void setExtraRoom1_Function(final Enum1553 ExtraRoom1_Function) {
		this.ExtraRoom1_Function = ExtraRoom1_Function;	}

	public Enum10 getStairsWithinDwelling_Faults_() {
		return StairsWithinDwelling_Faults_;	}

	public void setStairsWithinDwelling_Faults_(final Enum10 StairsWithinDwelling_Faults_) {
		this.StairsWithinDwelling_Faults_ = StairsWithinDwelling_Faults_;	}

	public Enum10 getWoodBoringInsect_OtherRooms() {
		return WoodBoringInsect_OtherRooms;	}

	public void setWoodBoringInsect_OtherRooms(final Enum10 WoodBoringInsect_OtherRooms) {
		this.WoodBoringInsect_OtherRooms = WoodBoringInsect_OtherRooms;	}

	public Enum10 getAccessibility_StraightStairsWithLandingsGreaterThan900Mm_() {
		return Accessibility_StraightStairsWithLandingsGreaterThan900Mm_;	}

	public void setAccessibility_StraightStairsWithLandingsGreaterThan900Mm_(final Enum10 Accessibility_StraightStairsWithLandingsGreaterThan900Mm_) {
		this.Accessibility_StraightStairsWithLandingsGreaterThan900Mm_ = Accessibility_StraightStairsWithLandingsGreaterThan900Mm_;	}

	public Enum10 getConservatory_ClosableDoor() {
		return Conservatory_ClosableDoor;	}

	public void setConservatory_ClosableDoor(final Enum10 Conservatory_ClosableDoor) {
		this.Conservatory_ClosableDoor = Conservatory_ClosableDoor;	}

	public Enum1553 getExtraRoom5_Function() {
		return ExtraRoom5_Function;	}

	public void setExtraRoom5_Function(final Enum1553 ExtraRoom5_Function) {
		this.ExtraRoom5_Function = ExtraRoom5_Function;	}

	public Enum10 getAccessibility_WheelchairAccessibleWCAtEntranceLevel_() {
		return Accessibility_WheelchairAccessibleWCAtEntranceLevel_;	}

	public void setAccessibility_WheelchairAccessibleWCAtEntranceLevel_(final Enum10 Accessibility_WheelchairAccessibleWCAtEntranceLevel_) {
		this.Accessibility_WheelchairAccessibleWCAtEntranceLevel_ = Accessibility_WheelchairAccessibleWCAtEntranceLevel_;	}

	public Enum10 getPenetratingDamp_OtherRooms() {
		return PenetratingDamp_OtherRooms;	}

	public void setPenetratingDamp_OtherRooms(final Enum10 PenetratingDamp_OtherRooms) {
		this.PenetratingDamp_OtherRooms = PenetratingDamp_OtherRooms;	}

	public Enum1553 getBedroomFunction() {
		return BedroomFunction;	}

	public void setBedroomFunction(final Enum1553 BedroomFunction) {
		this.BedroomFunction = BedroomFunction;	}

	public Enum1553 getExtraRoom4_Function() {
		return ExtraRoom4_Function;	}

	public void setExtraRoom4_Function(final Enum1553 ExtraRoom4_Function) {
		this.ExtraRoom4_Function = ExtraRoom4_Function;	}

	public Enum10 getInadequateApplianceVentialtion_OtherRooms() {
		return InadequateApplianceVentialtion_OtherRooms;	}

	public void setInadequateApplianceVentialtion_OtherRooms(final Enum10 InadequateApplianceVentialtion_OtherRooms) {
		this.InadequateApplianceVentialtion_OtherRooms = InadequateApplianceVentialtion_OtherRooms;	}

	public Enum1233 getHEALTHANDSAFETY_Noise() {
		return HEALTHANDSAFETY_Noise;	}

	public void setHEALTHANDSAFETY_Noise(final Enum1233 HEALTHANDSAFETY_Noise) {
		this.HEALTHANDSAFETY_Noise = HEALTHANDSAFETY_Noise;	}

	public Enum10 getAccessibility_DoorsetsAndCirculationMeetPartM_() {
		return Accessibility_DoorsetsAndCirculationMeetPartM_;	}

	public void setAccessibility_DoorsetsAndCirculationMeetPartM_(final Enum10 Accessibility_DoorsetsAndCirculationMeetPartM_) {
		this.Accessibility_DoorsetsAndCirculationMeetPartM_ = Accessibility_DoorsetsAndCirculationMeetPartM_;	}

	public Enum10 getAccessibility_RoomOnEntranceLevelSuitableForBedroom_() {
		return Accessibility_RoomOnEntranceLevelSuitableForBedroom_;	}

	public void setAccessibility_RoomOnEntranceLevelSuitableForBedroom_(final Enum10 Accessibility_RoomOnEntranceLevelSuitableForBedroom_) {
		this.Accessibility_RoomOnEntranceLevelSuitableForBedroom_ = Accessibility_RoomOnEntranceLevelSuitableForBedroom_;	}

	public Enum10 getStairsWithinDwelling_ReplaceTreads() {
		return StairsWithinDwelling_ReplaceTreads;	}

	public void setStairsWithinDwelling_ReplaceTreads(final Enum10 StairsWithinDwelling_ReplaceTreads) {
		this.StairsWithinDwelling_ReplaceTreads = StairsWithinDwelling_ReplaceTreads;	}

	public Enum1233 getHEALTHANDSAFETY_HotSurfaces() {
		return HEALTHANDSAFETY_HotSurfaces;	}

	public void setHEALTHANDSAFETY_HotSurfaces(final Enum1233 HEALTHANDSAFETY_HotSurfaces) {
		this.HEALTHANDSAFETY_HotSurfaces = HEALTHANDSAFETY_HotSurfaces;	}

	public Enum10 getBathroomInspected_() {
		return BathroomInspected_;	}

	public void setBathroomInspected_(final Enum10 BathroomInspected_) {
		this.BathroomInspected_ = BathroomInspected_;	}

	public Enum10 getExtraRoom2_DoesRoomExist_() {
		return ExtraRoom2_DoesRoomExist_;	}

	public void setExtraRoom2_DoesRoomExist_(final Enum10 ExtraRoom2_DoesRoomExist_) {
		this.ExtraRoom2_DoesRoomExist_ = ExtraRoom2_DoesRoomExist_;	}

	public Enum1553 getLivingRoomFunction() {
		return LivingRoomFunction;	}

	public void setLivingRoomFunction(final Enum1553 LivingRoomFunction) {
		this.LivingRoomFunction = LivingRoomFunction;	}

	public Enum10 getDoesBedroomExist_() {
		return DoesBedroomExist_;	}

	public void setDoesBedroomExist_(final Enum10 DoesBedroomExist_) {
		this.DoesBedroomExist_ = DoesBedroomExist_;	}

	public Enum1233 getHEALTHANDSAFETY_FallsBetweenStairs() {
		return HEALTHANDSAFETY_FallsBetweenStairs;	}

	public void setHEALTHANDSAFETY_FallsBetweenStairs(final Enum1233 HEALTHANDSAFETY_FallsBetweenStairs) {
		this.HEALTHANDSAFETY_FallsBetweenStairs = HEALTHANDSAFETY_FallsBetweenStairs;	}

	public Enum1583 getConservatory_WindowType() {
		return Conservatory_WindowType;	}

	public void setConservatory_WindowType(final Enum1583 Conservatory_WindowType) {
		this.Conservatory_WindowType = Conservatory_WindowType;	}

	public Enum1584 getConservatory_RoofType() {
		return Conservatory_RoofType;	}

	public void setConservatory_RoofType(final Enum1584 Conservatory_RoofType) {
		this.Conservatory_RoofType = Conservatory_RoofType;	}

	public Enum1553 getExtraRoom3_Function() {
		return ExtraRoom3_Function;	}

	public void setExtraRoom3_Function(final Enum1553 ExtraRoom3_Function) {
		this.ExtraRoom3_Function = ExtraRoom3_Function;	}

	public Enum10 getSecurityOfDwelling_BurglarAlarmPresent() {
		return SecurityOfDwelling_BurglarAlarmPresent;	}

	public void setSecurityOfDwelling_BurglarAlarmPresent(final Enum10 SecurityOfDwelling_BurglarAlarmPresent) {
		this.SecurityOfDwelling_BurglarAlarmPresent = SecurityOfDwelling_BurglarAlarmPresent;	}

	public Enum10 getExtraRoom1_DoesRoomExist_() {
		return ExtraRoom1_DoesRoomExist_;	}

	public void setExtraRoom1_DoesRoomExist_(final Enum10 ExtraRoom1_DoesRoomExist_) {
		this.ExtraRoom1_DoesRoomExist_ = ExtraRoom1_DoesRoomExist_;	}

	public Enum10 getSeparableUnits_() {
		return SeparableUnits_;	}

	public void setSeparableUnits_(final Enum10 SeparableUnits_) {
		this.SeparableUnits_ = SeparableUnits_;	}

	public Enum10 getAccessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_() {
		return Accessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_;	}

	public void setAccessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_(final Enum10 Accessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_) {
		this.Accessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_ = Accessibility_ChangeInFloorLevel_TripStepsAtEntranceLevel_;	}

	public Enum1553 getExtraRoom7_Function() {
		return ExtraRoom7_Function;	}

	public void setExtraRoom7_Function(final Enum1553 ExtraRoom7_Function) {
		this.ExtraRoom7_Function = ExtraRoom7_Function;	}

	public Enum10 getAdaptationsForTheDisabled_StairLift() {
		return AdaptationsForTheDisabled_StairLift;	}

	public void setAdaptationsForTheDisabled_StairLift(final Enum10 AdaptationsForTheDisabled_StairLift) {
		this.AdaptationsForTheDisabled_StairLift = AdaptationsForTheDisabled_StairLift;	}

	public Enum10 getDoesKitchenExist_() {
		return DoesKitchenExist_;	}

	public void setDoesKitchenExist_(final Enum10 DoesKitchenExist_) {
		this.DoesKitchenExist_ = DoesKitchenExist_;	}

	public Enum10 getExtraRoom7_DoesRoomExist_() {
		return ExtraRoom7_DoesRoomExist_;	}

	public void setExtraRoom7_DoesRoomExist_(final Enum10 ExtraRoom7_DoesRoomExist_) {
		this.ExtraRoom7_DoesRoomExist_ = ExtraRoom7_DoesRoomExist_;	}

	public Enum1556 getSecurityOfDwelling_AccessibleWindows() {
		return SecurityOfDwelling_AccessibleWindows;	}

	public void setSecurityOfDwelling_AccessibleWindows(final Enum1556 SecurityOfDwelling_AccessibleWindows) {
		this.SecurityOfDwelling_AccessibleWindows = SecurityOfDwelling_AccessibleWindows;	}

	public Enum10 getLivingRoomInspected_() {
		return LivingRoomInspected_;	}

	public void setLivingRoomInspected_(final Enum10 LivingRoomInspected_) {
		this.LivingRoomInspected_ = LivingRoomInspected_;	}

	public Enum10 getBedroomInspected_() {
		return BedroomInspected_;	}

	public void setBedroomInspected_(final Enum10 BedroomInspected_) {
		this.BedroomInspected_ = BedroomInspected_;	}

	public Enum1553 getKitchenFunction() {
		return KitchenFunction;	}

	public void setKitchenFunction(final Enum1553 KitchenFunction) {
		this.KitchenFunction = KitchenFunction;	}

	public Enum1233 getHEALTHANDSAFETY_Lighting() {
		return HEALTHANDSAFETY_Lighting;	}

	public void setHEALTHANDSAFETY_Lighting(final Enum1233 HEALTHANDSAFETY_Lighting) {
		this.HEALTHANDSAFETY_Lighting = HEALTHANDSAFETY_Lighting;	}

	public Enum1233 getHEALTHANDSAFETY_CollisionAndEntrapment() {
		return HEALTHANDSAFETY_CollisionAndEntrapment;	}

	public void setHEALTHANDSAFETY_CollisionAndEntrapment(final Enum1233 HEALTHANDSAFETY_CollisionAndEntrapment) {
		this.HEALTHANDSAFETY_CollisionAndEntrapment = HEALTHANDSAFETY_CollisionAndEntrapment;	}

	public Enum10 getRisingDamp_OtherRooms() {
		return RisingDamp_OtherRooms;	}

	public void setRisingDamp_OtherRooms(final Enum10 RisingDamp_OtherRooms) {
		this.RisingDamp_OtherRooms = RisingDamp_OtherRooms;	}

	public Enum10 getAccessibility_FlushThresholdLessThan15Mm_() {
		return Accessibility_FlushThresholdLessThan15Mm_;	}

	public void setAccessibility_FlushThresholdLessThan15Mm_(final Enum10 Accessibility_FlushThresholdLessThan15Mm_) {
		this.Accessibility_FlushThresholdLessThan15Mm_ = Accessibility_FlushThresholdLessThan15Mm_;	}

	public Enum10 getSecurityOfDwelling_SmokeDetectorsPresent() {
		return SecurityOfDwelling_SmokeDetectorsPresent;	}

	public void setSecurityOfDwelling_SmokeDetectorsPresent(final Enum10 SecurityOfDwelling_SmokeDetectorsPresent) {
		this.SecurityOfDwelling_SmokeDetectorsPresent = SecurityOfDwelling_SmokeDetectorsPresent;	}

	public Enum10 getInadequateRoomVentilation_OtherRooms() {
		return InadequateRoomVentilation_OtherRooms;	}

	public void setInadequateRoomVentilation_OtherRooms(final Enum10 InadequateRoomVentilation_OtherRooms) {
		this.InadequateRoomVentilation_OtherRooms = InadequateRoomVentilation_OtherRooms;	}

	public Enum10 getAdaptationsForTheDisabled_Ramps() {
		return AdaptationsForTheDisabled_Ramps;	}

	public void setAdaptationsForTheDisabled_Ramps(final Enum10 AdaptationsForTheDisabled_Ramps) {
		this.AdaptationsForTheDisabled_Ramps = AdaptationsForTheDisabled_Ramps;	}

	public Enum10 getStairsWithinDwelling_RepairRefixTreadsBalustrades() {
		return StairsWithinDwelling_RepairRefixTreadsBalustrades;	}

	public void setStairsWithinDwelling_RepairRefixTreadsBalustrades(final Enum10 StairsWithinDwelling_RepairRefixTreadsBalustrades) {
		this.StairsWithinDwelling_RepairRefixTreadsBalustrades = StairsWithinDwelling_RepairRefixTreadsBalustrades;	}

	public Enum1556 getSecurityOfDwelling_OtherExternalDoors() {
		return SecurityOfDwelling_OtherExternalDoors;	}

	public void setSecurityOfDwelling_OtherExternalDoors(final Enum1556 SecurityOfDwelling_OtherExternalDoors) {
		this.SecurityOfDwelling_OtherExternalDoors = SecurityOfDwelling_OtherExternalDoors;	}

	public Enum10 getIntegralGarage_DoesRoomExist_() {
		return IntegralGarage_DoesRoomExist_;	}

	public void setIntegralGarage_DoesRoomExist_(final Enum10 IntegralGarage_DoesRoomExist_) {
		this.IntegralGarage_DoesRoomExist_ = IntegralGarage_DoesRoomExist_;	}

	public Enum1553 getExtraRoom2_Function() {
		return ExtraRoom2_Function;	}

	public void setExtraRoom2_Function(final Enum1553 ExtraRoom2_Function) {
		this.ExtraRoom2_Function = ExtraRoom2_Function;	}

	public Enum10 getAdaptationsForTheDisabled_GrabRails() {
		return AdaptationsForTheDisabled_GrabRails;	}

	public void setAdaptationsForTheDisabled_GrabRails(final Enum10 AdaptationsForTheDisabled_GrabRails) {
		this.AdaptationsForTheDisabled_GrabRails = AdaptationsForTheDisabled_GrabRails;	}

	public Enum1233 getHEALTHANDSAFETY_DampAndMould() {
		return HEALTHANDSAFETY_DampAndMould;	}

	public void setHEALTHANDSAFETY_DampAndMould(final Enum1233 HEALTHANDSAFETY_DampAndMould) {
		this.HEALTHANDSAFETY_DampAndMould = HEALTHANDSAFETY_DampAndMould;	}

	public Enum10 getSecurityOfDwelling_DoorViewerPresent() {
		return SecurityOfDwelling_DoorViewerPresent;	}

	public void setSecurityOfDwelling_DoorViewerPresent(final Enum10 SecurityOfDwelling_DoorViewerPresent) {
		this.SecurityOfDwelling_DoorViewerPresent = SecurityOfDwelling_DoorViewerPresent;	}

	public Enum10 getStairsWithinDwelling_ReplaceStructure() {
		return StairsWithinDwelling_ReplaceStructure;	}

	public void setStairsWithinDwelling_ReplaceStructure(final Enum10 StairsWithinDwelling_ReplaceStructure) {
		this.StairsWithinDwelling_ReplaceStructure = StairsWithinDwelling_ReplaceStructure;	}

	public Enum10 getDoesBathroomExist_() {
		return DoesBathroomExist_;	}

	public void setDoesBathroomExist_(final Enum10 DoesBathroomExist_) {
		this.DoesBathroomExist_ = DoesBathroomExist_;	}

	public Enum10 getAdaptationsForTheDisabled_Hoists() {
		return AdaptationsForTheDisabled_Hoists;	}

	public void setAdaptationsForTheDisabled_Hoists(final Enum10 AdaptationsForTheDisabled_Hoists) {
		this.AdaptationsForTheDisabled_Hoists = AdaptationsForTheDisabled_Hoists;	}

	public Enum10 getExtraRoom4_DoesRoomExist_() {
		return ExtraRoom4_DoesRoomExist_;	}

	public void setExtraRoom4_DoesRoomExist_(final Enum10 ExtraRoom4_DoesRoomExist_) {
		this.ExtraRoom4_DoesRoomExist_ = ExtraRoom4_DoesRoomExist_;	}

	public Enum10 getDoesCirculationExist_() {
		return DoesCirculationExist_;	}

	public void setDoesCirculationExist_(final Enum10 DoesCirculationExist_) {
		this.DoesCirculationExist_ = DoesCirculationExist_;	}

	public Enum10 getIntegralBalcony_DoesRoomExist_() {
		return IntegralBalcony_DoesRoomExist_;	}

	public void setIntegralBalcony_DoesRoomExist_(final Enum10 IntegralBalcony_DoesRoomExist_) {
		this.IntegralBalcony_DoesRoomExist_ = IntegralBalcony_DoesRoomExist_;	}

	public Enum10 getStairsWithinDwelling_ReplaceBalustrades() {
		return StairsWithinDwelling_ReplaceBalustrades;	}

	public void setStairsWithinDwelling_ReplaceBalustrades(final Enum10 StairsWithinDwelling_ReplaceBalustrades) {
		this.StairsWithinDwelling_ReplaceBalustrades = StairsWithinDwelling_ReplaceBalustrades;	}

	public Enum1233 getHEALTHANDSAFETY_Fire() {
		return HEALTHANDSAFETY_Fire;	}

	public void setHEALTHANDSAFETY_Fire(final Enum1233 HEALTHANDSAFETY_Fire) {
		this.HEALTHANDSAFETY_Fire = HEALTHANDSAFETY_Fire;	}

	public Enum10 getExtraRoom3_DoesRoomExist_() {
		return ExtraRoom3_DoesRoomExist_;	}

	public void setExtraRoom3_DoesRoomExist_(final Enum10 ExtraRoom3_DoesRoomExist_) {
		this.ExtraRoom3_DoesRoomExist_ = ExtraRoom3_DoesRoomExist_;	}

	public Enum1233 getHEALTHANDSAFETY_EntryByIntruders() {
		return HEALTHANDSAFETY_EntryByIntruders;	}

	public void setHEALTHANDSAFETY_EntryByIntruders(final Enum1233 HEALTHANDSAFETY_EntryByIntruders) {
		this.HEALTHANDSAFETY_EntryByIntruders = HEALTHANDSAFETY_EntryByIntruders;	}

	public Enum10 getInadequateNaturalLighting_OtherRooms() {
		return InadequateNaturalLighting_OtherRooms;	}

	public void setInadequateNaturalLighting_OtherRooms(final Enum10 InadequateNaturalLighting_OtherRooms) {
		this.InadequateNaturalLighting_OtherRooms = InadequateNaturalLighting_OtherRooms;	}

	public Enum10 getExtraRoom6_DoesRoomExist_() {
		return ExtraRoom6_DoesRoomExist_;	}

	public void setExtraRoom6_DoesRoomExist_(final Enum10 ExtraRoom6_DoesRoomExist_) {
		this.ExtraRoom6_DoesRoomExist_ = ExtraRoom6_DoesRoomExist_;	}

	public Enum10 getAdaptationsForTheDisabled_ElectricalModifications() {
		return AdaptationsForTheDisabled_ElectricalModifications;	}

	public void setAdaptationsForTheDisabled_ElectricalModifications(final Enum10 AdaptationsForTheDisabled_ElectricalModifications) {
		this.AdaptationsForTheDisabled_ElectricalModifications = AdaptationsForTheDisabled_ElectricalModifications;	}

	public Enum10 getAccessibility_WCAtEntranceLevel_() {
		return Accessibility_WCAtEntranceLevel_;	}

	public void setAccessibility_WCAtEntranceLevel_(final Enum10 Accessibility_WCAtEntranceLevel_) {
		this.Accessibility_WCAtEntranceLevel_ = Accessibility_WCAtEntranceLevel_;	}

	public Enum10 getKitchenInspected_() {
		return KitchenInspected_;	}

	public void setKitchenInspected_(final Enum10 KitchenInspected_) {
		this.KitchenInspected_ = KitchenInspected_;	}

	public Enum10 getStairsWithinDwelling_Present_() {
		return StairsWithinDwelling_Present_;	}

	public void setStairsWithinDwelling_Present_(final Enum10 StairsWithinDwelling_Present_) {
		this.StairsWithinDwelling_Present_ = StairsWithinDwelling_Present_;	}

	public Enum1233 getHEALTHANDSAFETY_FallsOnTheLevel() {
		return HEALTHANDSAFETY_FallsOnTheLevel;	}

	public void setHEALTHANDSAFETY_FallsOnTheLevel(final Enum1233 HEALTHANDSAFETY_FallsOnTheLevel) {
		this.HEALTHANDSAFETY_FallsOnTheLevel = HEALTHANDSAFETY_FallsOnTheLevel;	}

	public Enum1233 getHEALTHANDSAFETY_ExcessHeat() {
		return HEALTHANDSAFETY_ExcessHeat;	}

	public void setHEALTHANDSAFETY_ExcessHeat(final Enum1233 HEALTHANDSAFETY_ExcessHeat) {
		this.HEALTHANDSAFETY_ExcessHeat = HEALTHANDSAFETY_ExcessHeat;	}

	public Enum10 getAccessibility_BathroomAtEntranceLevel_() {
		return Accessibility_BathroomAtEntranceLevel_;	}

	public void setAccessibility_BathroomAtEntranceLevel_(final Enum10 Accessibility_BathroomAtEntranceLevel_) {
		this.Accessibility_BathroomAtEntranceLevel_ = Accessibility_BathroomAtEntranceLevel_;	}

	public Enum10 getDryWetRot_OtherRooms() {
		return DryWetRot_OtherRooms;	}

	public void setDryWetRot_OtherRooms(final Enum10 DryWetRot_OtherRooms) {
		this.DryWetRot_OtherRooms = DryWetRot_OtherRooms;	}

}

