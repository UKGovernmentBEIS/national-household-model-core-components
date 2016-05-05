package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1185;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1230;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1233;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1236;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1246;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1255;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface CommonEntry extends SurveyEntry {
	@SavVariableMapping("FCPHSFIR")
	public Enum1233 getHEALTHANDSAFETY_Fire();

	@SavVariableMapping("FCPLFTEX")
	public Enum10 getLIFTS_DoesAccessAreaExist_();

	@SavVariableMapping("FCPINAPP")
	public Enum1185 getCONTRIBUTIONTOPROBLEMS_InappropriateUse();

	@SavVariableMapping("FCPALMAC")
	public Enum1236 getFIREPRECAUTIONS_AlarmSystem_Action_();

	@SavVariableMapping("FCPWEART")
	public Enum1185 getCONTRIBUTIONTOPROBLEMS_NormalWear_Tear();

	@SavVariableMapping("FCPSGNAC")
	public Enum1185 getFIREPRECAUTIONS_SignPosting_Action_();

	@SavVariableMapping("FCPPROAC")
	public Enum1236 getFIREPRECAUTIONS_ProtectionToStairsLobbies_Action_();

	@SavVariableMapping("FCPALMPR")
	public Enum10 getFIREPRECAUTIONS_AlarmSystem_Present_();

	@SavVariableMapping("FCPINADM")
	public Enum1185 getCONTRIBUTIONTOPROBLEMS_InadequateMaintenance();

	@SavVariableMapping("FCPEMLAC")
	public Enum1236 getFIREPRECAUTIONS_EmergencyLighting_Action_();

	@SavVariableMapping("FCPLFTIN")
	public Enum10 getLIFTS_InModule();

	@SavVariableMapping("FCPENTIN")
	public Enum10 getSECURITY_DoorEntrySystemInModule();

	@SavVariableMapping("FCPPROPR")
	public Enum10 getFIREPRECAUTIONS_ProtectionToStairsLobbies_Present_();

	@SavVariableMapping("FCPACCES")
	public Enum1246 getSECURITY_TypeOfAccess();

	@SavVariableMapping("FCPHSBTW")
	public Enum1233 getHEALTHANDSAFETY_FallsBetweenLevels();

	@SavVariableMapping("FCPEMLPR")
	public Enum10 getFIREPRECAUTIONS_EmergencyLighting_Present_();

	@SavVariableMapping("FCPHSHOT")
	public Enum1233 getHEALTHANDSAFETY_HotSurfaces();

	@SavVariableMapping("FCPCONIN")
	public Enum10 getSECURITY_ConciergeSystemInModule();

	@SavVariableMapping("FCPLFTSZ")
	public Enum1230 getLIFTS_SpaciousAverageTight();

	@SavVariableMapping("FCPCLOAC")
	public Enum1236 getFIREPRECAUTIONS_SelfClosingFireDoors_Action_();

	@SavVariableMapping("FCPENTPR")
	public Enum10 getSECURITY_DoorEntrySystemPresent();

	@SavVariableMapping("FCPENTWK")
	public Enum10 getSECURITY_DoorEntrySystemWorking();

	@SavVariableMapping("FCPESCAP")
	public Enum1255 getFIRESAFETY_EscapeRouteFromFlatSurveyedToFinalExitFromBuilding();

	@SavVariableMapping("FCPDESIG")
	public Enum1185 getCONTRIBUTIONTOPROBLEMS_PoorDesignSpecification();

	@SavVariableMapping("FCPHSCEN")
	public Enum1233 getHEALTHANDSAFETY_CollisionAndEntrapment();

	@SavVariableMapping("FCPSGNPR")
	public Enum10 getFIREPRECAUTIONS_SignPosting_Present_();

	@SavVariableMapping("FCPHSENT")
	public Enum1233 getHEALTHANDSAFETY_EntryByIntruders();

	@SavVariableMapping("FCPCONPR")
	public Enum10 getSECURITY_ConciergeSystemPresent();

	@SavVariableMapping("FCPHSLVL")
	public Enum1233 getHEALTHANDSAFETY_FallsOnTheLevel();

	@SavVariableMapping("FCPHSNOI")
	public Enum1233 getHEALTHANDSAFETY_Noise();

	@SavVariableMapping("FCPREFWK")
	public Enum10 getREFUSECHUTES_Working();

	@SavVariableMapping("FCPPRES")
	public Enum10 getDoCommonPartsExist_();

	@SavVariableMapping("FCPHSSTR")
	public Enum1233 getHEALTHANDSAFETY_FallsOnStairs();

	@SavVariableMapping("FCPLFTWU")
	public Enum10 getLiftControlsAccessibleToWheelchairUser_();

	@SavVariableMapping("FCPGRAFF")
	public Enum1185 getCONTRIBUTIONTOPROBLEMS_Graffiti();

	@SavVariableMapping("FCPEXTAC")
	public Enum1236 getFIREPRECAUTIONS_FireExtinguishers_Action_();

	@SavVariableMapping("FCPSAFPR")
	public Enum10 getFIREPRECAUTIONS_SafePractices_Present_();

	@SavVariableMapping("FCPCONWK")
	public Enum10 getSECURITY_ConciergeSystemWorking();

	@SavVariableMapping("FCPALTPR")
	public Enum10 getFIREPRECAUTIONS_AlternativeRoute_Present_();

	@SavVariableMapping("FCPHSDAM")
	public Enum1233 getHEALTHANDSAFETY_DampAndMould();

	@SavVariableMapping("FCPLITTR")
	public Enum1185 getCONTRIBUTIONTOPROBLEMS_LitterRubbish();

	@SavVariableMapping("FCPREFIN")
	public Enum10 getREFUSECHUTES_InModule();

	@SavVariableMapping("FCPVANDA")
	public Enum1185 getCONTRIBUTIONTOPROBLEMS_Vandalism();

	@SavVariableMapping("FCPEXTPR")
	public Enum10 getFIREPRECAUTIONS_FireExtinguishers_Present_();

	@SavVariableMapping("FCPCLOPR")
	public Enum10 getFIREPRECAUTIONS_SelfClosingFireDoors_Present_();

	@SavVariableMapping("FCPLFTVP")
	public Enum10 getLiftControlsAccessibleToAVisuallyImpairedPerson_();

	@SavVariableMapping("FCPREFEX")
	public Enum10 getREFUSECHUTES_DoesAccessAreaExist_();

	@SavVariableMapping("FCPLFTWK")
	public Enum10 getLIFTS_Working();

	@SavVariableMapping("FCPREFSZ")
	public Enum1230 getREFUSECHUTES_SpaciousAverageTight();

}

