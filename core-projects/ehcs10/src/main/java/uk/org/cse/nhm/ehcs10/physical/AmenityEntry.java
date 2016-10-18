package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1060;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1066;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1069;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1105;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface AmenityEntry extends SurveyEntry {
	@SavVariableMapping("FINBATFL")
	public String getBathroomBathShower_Floor();

	@SavVariableMapping("FINWHBFL")
	public String getBathroomWashHandBasin_Floor();

	@SavVariableMapping("FINLOOFL")
	public String getWC_Floor();

	@SavVariableMapping("FIN2KIFL")
	public String getSecondKitchen_Floor();

	@SavVariableMapping("FIN2BTFL")
	public String getSecondBathShower_Floor();

	@SavVariableMapping("FIN2WHFL")
	public String getSecondWashHandBasin_Floor();

	@SavVariableMapping("FIN2LOFL")
	public String getSecondWC_Floor();

	@SavVariableMapping("FINKITRE")
	public Integer getKITCHENActualDateOfRefurbishment();

	@SavVariableMapping("FINBATRE")
	public Integer getBathroomAmenitiesActualDateOfRefurbishment();

	@SavVariableMapping("FINSURFA")
	public Integer getBathroomAmenities_NumberOfExternalSurfaces();

	@SavVariableMapping("FINLOOAC")
	public Enum1060 getWC_Action();

	@SavVariableMapping("FINWRKPR")
	public Enum10 getKITCHENWorktop_Present();

	@SavVariableMapping("FINBXTPR")
	public Enum10 getBathroomExtractorFan_Present();

	@SavVariableMapping("FINWMWK")
	public Enum10 getKITCHENWashingMachine_Working();

	@SavVariableMapping("FINCUPAC")
	public Enum1060 getKITCHENCupboards_Action();

	@SavVariableMapping("FINKXTPR")
	public Enum10 getKITCHENExtractorFan_Present();

	@SavVariableMapping("FINHSFBA")
	public Enum1066 getHEALTHANDSAFETY_FallsAsstdWithBaths();

	@SavVariableMapping("FINCOKPR")
	public Enum10 getKITCHENCookingProvision_Present();

	@SavVariableMapping("FINKXTWK")
	public Enum10 getKITCHENExtractorFan_Working();

	@SavVariableMapping("FINBATLR")
	public Enum1069 getBathroomAmenitiesLastRefurbished();

	@SavVariableMapping("FINLOOWH")
	public Enum10 getWC_CloseToWashHandBasin();

	@SavVariableMapping("FIN2BTAC")
	public Enum1060 getSecondBathShower_Action();

	@SavVariableMapping("FINBDFSP")
	public Enum10 getBATHROOMAMENITIESSignificantProblemsWithSpace();

	@SavVariableMapping("FINCUPUN")
	public Enum10 getKITCHENAdequateCupboardUnits();

	@SavVariableMapping("FINBATAC")
	public Enum1060 getBathroomBathShower_Action();

	@SavVariableMapping("FINBADLO")
	public Enum10 getBathroomAmenities_BadlyLocated();

	@SavVariableMapping("FINWHBAC")
	public Enum1060 getBathroomWashHandBasin_Action();

	@SavVariableMapping("FINKDFCB")
	public Enum10 getKITCHENDefectiveOnCleanability();

	@SavVariableMapping("FINLOOBA")
	public Enum10 getWC_InBathroom();

	@SavVariableMapping("FINHSFOD")
	public Enum1066 getHEALTHANDSAFETY_FoodSafety();

	@SavVariableMapping("FINLEADA")
	public Enum10 getDrinkingWater_SupplyPipeworkAfterStopcock_LeadPresent();

	@SavVariableMapping("FIN2WHAC")
	public Enum1060 getSecondWashHandBasin_Action();

	@SavVariableMapping("FINWSTAC")
	public Enum1060 getKITCHENFixedWaste_Action();

	@SavVariableMapping("FINHOTPR")
	public Enum10 getKITCHENHotWater_Present();

	@SavVariableMapping("FINLOOEX")
	public Enum10 getWC_ExtractorFan();

	@SavVariableMapping("FINCLDAC")
	public Enum1060 getKITCHENColdWaterSupply_Action();

	@SavVariableMapping("FINCOOKR")
	public Enum10 getKITCHENAdequateCookerSpace();

	@SavVariableMapping("FINBATHC")
	public Enum10 getBathroomBathShower_Hot_Cold();

	@SavVariableMapping("FIN2LOIN")
	public Enum10 getSecondWC_Internal();

	@SavVariableMapping("FINSNKPR")
	public Enum10 getKITCHENSink_Present();

	@SavVariableMapping("FINPIPEA")
	public Enum10 getDrinkingWater_SupplyPipeworkAfterStopcock_PipeworkSeen();

	@SavVariableMapping("FINCUPWK")
	public Enum10 getKITCHENCupboards_Working();

	@SavVariableMapping("FINKITLR")
	public Enum1069 getKITCHENAmenitiesLastRefurbished();

	@SavVariableMapping("FINHOTWK")
	public Enum10 getKITCHENHotWater_Working();

	@SavVariableMapping("FINBDFCB")
	public Enum10 getBATHROOMAMENITIESSignificantProblemsWithCleanability();

	@SavVariableMapping("FINRFPR")
	public Enum10 getKITCHENRefrigerator_Present();

	@SavVariableMapping("FIN2WHES")
	public Enum10 getSecondWashHandBasin_InBedroom_En_Suite_();

	@SavVariableMapping("FINHSPOA")
	public Enum1066 getHEALTHANDSAFETY_PositionAndOperabilityAmenities();

	@SavVariableMapping("FIN2WHPR")
	public Enum10 getSecondWashHandBasin_Present();

	@SavVariableMapping("FINCLDWK")
	public Enum10 getKITCHENColdWaterSupply_Working();

	@SavVariableMapping("FINWRKAC")
	public Enum1060 getKITCHENWorktop_Action();

	@SavVariableMapping("FINBATPR")
	public Enum10 getBathroomBathShower_Present();

	@SavVariableMapping("FINBXTWK")
	public Enum10 getBathroomExtractorFan_Working();

	@SavVariableMapping("FINWHBWK")
	public Enum10 getBathroomWashHandBasin_Working();

	@SavVariableMapping("FINKDFSP")
	public Enum10 getKITCHENDefectiveOnSpace();

	@SavVariableMapping("FINWORKT")
	public Enum1105 getKITCHENWorktop_Metres_();

	@SavVariableMapping("FINCLDPR")
	public Enum10 getKITCHENColdWaterDrinkingSupply_Present();

	@SavVariableMapping("FIN2WHHC")
	public Enum10 getSecondWashHandBasin_HotAndColdWater();

	@SavVariableMapping("FINBATWA")
	public Enum10 getBathroomWheelchairAccessible_();

	@SavVariableMapping("FINLEADB")
	public Enum10 getDrinkingWater_SupplyPipeworkBeforeStopcock_LeadPresent();

	@SavVariableMapping("FINRFWK")
	public Enum10 getKITCHENRefrigerator_Working();

	@SavVariableMapping("FINWHBHC")
	public Enum10 getBathroomWashHandBasin_Hot_ColdWater();

	@SavVariableMapping("FINWHBPR")
	public Enum10 getBathroomWashHandBasin_Present();

	@SavVariableMapping("FINCOKAC")
	public Enum1060 getKITCHENCookingProvision_Action();

	@SavVariableMapping("FIN2LOWK")
	public Enum10 getSecondWC_Working();

	@SavVariableMapping("FINLOOPR")
	public Enum10 getWC_Present();

	@SavVariableMapping("FIN2BTES")
	public Enum10 getSecondBathShower_InBedroom_En_Suite_();

	@SavVariableMapping("FINBDFLO")
	public Enum10 getBATHROOMAMENITIESSignificantProblemsWithLocation();

	@SavVariableMapping("FINCUPPR")
	public Enum10 getKITCHENCupboards_Present();

	@SavVariableMapping("FINSNKAC")
	public Enum1060 getKITCHENSink_Action();

	@SavVariableMapping("FINHSPHY")
	public Enum1066 getHEALTHANDSAFETY_PersonalHygiene();

	@SavVariableMapping("FIN2LOPR")
	public Enum10 getSecondWC_Present();

	@SavVariableMapping("FINMAINS")
	public Enum10 getDrinkingWater_SupplyPipeworkBeforeStopcock_Mains();

	@SavVariableMapping("FINWSTWK")
	public Enum10 getKITCHENFixedWaste_Working();

	@SavVariableMapping("FINBATWK")
	public Enum10 getBathroomBathShower_Working();

	@SavVariableMapping("FINBDFLA")
	public Enum10 getBATHROOMAMENITIESSignificantProblemsWithLayout();

	@SavVariableMapping("FIN2BTWK")
	public Enum10 getSecondBathShower_Working();

	@SavVariableMapping("FINTDPR")
	public Enum10 getKITCHENTumbleDryer_Present();

	@SavVariableMapping("FINLOOWK")
	public Enum10 getWC_Working();

	@SavVariableMapping("FINWSTPR")
	public Enum10 getKITCHENFixedWaste_Present();

	@SavVariableMapping("FIN2LOES")
	public Enum10 getSecondWC_InBedroom_En_Suite_();

	@SavVariableMapping("FINTDWK")
	public Enum10 getKITCHENTumbleDryer_Working();

	@SavVariableMapping("FINKITDU")
	public Enum10 getKITCHENAdaptedForDisabledUse();

	@SavVariableMapping("FINKDFLA")
	public Enum10 getKITCHENDefectiveOnLayout();

	@SavVariableMapping("FIN2BTPR")
	public Enum10 getSecondBathShower_Present();

	@SavVariableMapping("FIN2KIAC")
	public Enum1060 getSecondKitchen_Action();

	@SavVariableMapping("FIN2LOAC")
	public Enum1060 getSecondWC_Action();

	@SavVariableMapping("FINHOTAC")
	public Enum1060 getKITCHENHotWater_Action();

	@SavVariableMapping("FINCOKWK")
	public Enum10 getKITCHENCookingProvision_Working();

	@SavVariableMapping("FINWRKWK")
	public Enum10 getKITCHENWorktop_Working();

	@SavVariableMapping("FINBATDU")
	public Enum10 getBathroomAdaptedForDisabledUse();

	@SavVariableMapping("FIN2BTHC")
	public Enum10 getSecondBathShower_HotAndColdWater();

	@SavVariableMapping("FINHSWAT")
	public Enum1066 getHEALTHANDSAFETY_WaterSupply();

	@SavVariableMapping("FINSNKWK")
	public Enum10 getKITCHENSink_Working();

	@SavVariableMapping("FINWMPR")
	public Enum10 getKITCHENWashingMachine_Present();

	@SavVariableMapping("FIN2WHWK")
	public Enum10 getSecondWashHandBasin_Working();

	@SavVariableMapping("FIN2KIHC")
	public Enum10 getSecondKitchen_Hot_ColdWater();

	@SavVariableMapping("FINLOOIN")
	public Enum10 getWC_Internal();

	@SavVariableMapping("FINPIPEB")
	public Enum10 getDrinkingWater_SupplyPipeworkBeforeStopcock_PipeworkSeen();

	@SavVariableMapping("FIN2KIPR")
	public Enum10 getSecondKitchen_Present();

}

