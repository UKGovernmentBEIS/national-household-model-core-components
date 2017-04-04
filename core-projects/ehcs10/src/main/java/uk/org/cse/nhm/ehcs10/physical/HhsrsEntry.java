package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1233;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface HhsrsEntry extends SurveyEntry {
	@SavVariableMapping("FHSMEAS")
	public String getHHSRSSummary_DescriptionOfMeasuredHazards();

	@SavVariableMapping("FHSXRISK")
	public String getHHSRSSummary_DescriptionOfExtremeRisks();

	@SavVariableMapping("FHSFBLIK")
	public Integer getFALLSASSTDWITHBATHS_LikelihoodOfAPersonOver60HavingAFall();

	@SavVariableMapping("FHSCEROQ")
	public Integer getCOLLISIONANDENTRAPMENT_RemoveObstacle_Quantity_Number_();

	@SavVariableMapping("FHSLVCVQ")
	public Integer getFALLSONTHELEVEL_CoverSlipperySurfaces_Quantity_SquareMetres_();

	@SavVariableMapping("FHSNONMQ")
	public Integer getNOISE_NoisyMachinery_Quantity_Number_();

	@SavVariableMapping("FHSBTBUQ")
	public Integer getFALLSBETWEENLEVELS_Brick_UpDangerousOpening_RaiseCillHeight_Quantity_Number_();

	@SavVariableMapping("FHSNOFLQ")
	public Integer getNOISE_SoundproofFloors_Quantity_SqMetres_();

	@SavVariableMapping("FHSDAPDQ")
	public Integer getDAMPANDMOULD_TreatPenetratingDamp_Quantity_Number_();

	@SavVariableMapping("FHSFBRAQ")
	public Integer getFALLSASSTDWITHBATHS_RearrangeBathroom_Quantity_Number_();

	@SavVariableMapping("FHSCEDOQ")
	public Integer getCOLLISIONANDENTRAPMENT_Repair_ReplaceDoors_Quantity_Number_();

	@SavVariableMapping("FHSNOSGQ")
	public Integer getNOISE_SecondaryGlazing_RepairWindows_Quantity_Number_();

	@SavVariableMapping("FHSSTLIK")
	public Integer getFALLSONSTAIRS_ProbabilityOfAPersonOver60HavingAFall();

	@SavVariableMapping("FHSENLIK")
	public Integer getENTRYBYINTRUDERS_LikelihoodOfAPersonBeingAffected();

	@SavVariableMapping("FHSFRRCQ")
	public Integer getFIRE_RelocateCooker_Quantity_Number_();

	@SavVariableMapping("FHSBTLIQ")
	public Integer getFALLSBETWEENLEVELS_Repair_Replace_ProvideAdditionalLighting_Quantity_Number__();

	@SavVariableMapping("FHSCEWIQ")
	public Integer getCOLLISIONANDENTRAPMENT_Repair_ReplaceWindows_Quantity_Number_();

	@SavVariableMapping("FHSFRSOQ")
	public Integer getFIRE_ProvideAdditionalSockets_Quantity_Number_();

	@SavVariableMapping("FHSFROWQ")
	public Integer getFIRE_ProvideSuitableOpenableWindows_Quantity_Number_();

	@SavVariableMapping("FHSNOCEQ")
	public Integer getNOISE_SoundproofCeilings_Quantity_SqMetres_();

	@SavVariableMapping("FHSFRCAQ")
	public Integer getFIRE_Repair_InstallPrecautionsToCommonAreas_Quantity_SquareMetres_();

	@SavVariableMapping("FHSSTIBQ")
	public Integer getFALLSONSTAIRS_InstallBalustrade_Quantity_Metres_();

	@SavVariableMapping("FHSDAEXQ")
	public Integer getDAMPANDMOULD_ExtractorFans_Quantity_Number_();

	@SavVariableMapping("FHSSTIHQ")
	public Integer getFALLSONSTAIRS_InstallHandrail_Quantity_Metres_();

	@SavVariableMapping("FHSHSRKQ")
	public Integer getHOTSURFACES_Re_Fit_Extend_ResiteKitchen_Quantity_Number_();

	@SavVariableMapping("FHSFRHSD")
	public Integer getFIRE_ReplaceInadequateHeatingSystem_DescribedElsewhere_();

	@SavVariableMapping("FHSFRPSQ")
	public Integer getFIRE_ReplaceNonFireResistant_SmokePermeableStructure_Quantity_SquareMetres_();

	@SavVariableMapping("FHSENWIQ")
	public Integer getENTRYBYINTRUDERS_MakeWindowsSecure_Quantity_Number_();

	@SavVariableMapping("FHSCESLQ")
	public Integer getCOLLISIONANDENTRAPMENT_SignpostLowHeadroom_Quantity_Number_();

	@SavVariableMapping("FHSSTCVQ")
	public Integer getFALLSONSTAIRS_CoverDangerousBalustrade_Guarding_Quantity_Metres_();

	@SavVariableMapping("FHSHSLIK")
	public Integer getHOTSURFACES_LikelihoodOfAChildUnder5BeingBurnt_Scalded();

	@SavVariableMapping("FHSENDSQ")
	public Integer getENTRYBYINTRUDERS_DefensibleSpace_Quantity_SqMetres_();

	@SavVariableMapping("FHSBTLIK")
	public Integer getFALLSBETWEENLEVELS_LikelihoodOfAChildUnder5HavingAFall();

	@SavVariableMapping("FHSFBGRQ")
	public Integer getFALLSASSTDWITHBATHS_ProvideGrabrail_Quantity_Number_();

	@SavVariableMapping("FHSDALIK")
	public Integer getDAMPANDMOULD_LikelihoodOfAPersonUnder15SufferingIllness();

	@SavVariableMapping("FHSSTCSQ")
	public Integer getFALLSONSTAIRS_CoverSlipperyStairs_Quantity_Flights_();

	@SavVariableMapping("FHSLVRTQ")
	public Integer getFALLSONTHELEVEL_RemoveTripSteps_Quantity_Number_();

	@SavVariableMapping("FHSHSROQ")
	public Integer getHOTSURFACES_RemoveObstacle_Quantity_Number_();

	@SavVariableMapping("FHSFRROQ")
	public Integer getFIRE_RemoveObstacle_Quantity_Number_();

	@SavVariableMapping("FHSSTROQ")
	public Integer getFALLSONSTAIRS_RemoveObstacle_Quantity_Number_();

	@SavVariableMapping("FHSHSRCQ")
	public Integer getHOTSURFACES_RelocateCooker_Quantity_Number_();

	@SavVariableMapping("FHSHSRHQ")
	public Integer getHOTSURFACES_Repair_ReplaceOrRepositionHeater_HeatingOrHotWaterPipesOrCover_Quantity_Number_();

	@SavVariableMapping("FHSNOPTQ")
	public Integer getNOISE_SoundproofPartitions_Quantity_SqMetres_();

	@SavVariableMapping("FHSENDOQ")
	public Integer getENTRYBYINTRUDERS_MakeDoorsSecure_Quantity_Number_();

	@SavVariableMapping("FHSSTRDQ")
	public Integer getFALLSONSTAIRS_RedesignInternal_CommonOrExternalStaircase_Quantity_Number_();

	@SavVariableMapping("FHSENLTQ")
	public Integer getENTRYBYINTRUDERS_AdditionalLighting_Quantity_Number_();

	@SavVariableMapping("FHSLVROQ")
	public Integer getFALLSONTHELEVEL_RemoveObstacle_Quantity_Number_();

	@SavVariableMapping("FHSLVRDQ")
	public Integer getFALLSONTHELEVEL_RedesignExternalPathways_Quantity_Metres();

	@SavVariableMapping("FHSLVLIQ")
	public Integer getFALLSONTHELEVEL_Repair_Replace_ProvideAdditionalLighting_Quantity_Number_();

	@SavVariableMapping("FHSFRRKQ")
	public Integer getFIRE_Re_Fit_Extend_ResiteKitchen_Quantity_Number_();

	@SavVariableMapping("FHSDAHTQ")
	public Integer getDAMPANDMOULD_Repair_ImproveHeating_Quantity_Number_();

	@SavVariableMapping("FHSFBROQ")
	public Integer getFALLSASSTDWITHBATHS_RemoveObstacle_Quantity_Number_();

	@SavVariableMapping("FHSFBRPQ")
	public Integer getFALLSASSTDWITHBATHS_Repair_ReplaceBath_Quantity_Number_();

	@SavVariableMapping("FHSFRUSQ")
	public Integer getFIRE_UpgradeStairwayToProtectedRoute_Quantity_Flights_();

	@SavVariableMapping("FHSFBRSQ")
	public Integer getFALLSASSTDWITHBATHS_Re_SiteBathroom_Quantity_Number_();

	@SavVariableMapping("FHSCELIK")
	public Integer getCOLLISIONANDENTRAPMENT_LikelihoodOfAChildUnder5BeingInjured();

	@SavVariableMapping("FHSBTROQ")
	public Integer getFALLSBETWEENLEVELS_RemoveObstacle_Quantity_Number_();

	@SavVariableMapping("FHSNOPWQ")
	public Integer getNOISE_SoundproofPartyWalls_Quantity_SqMetres_();

	@SavVariableMapping("FHSFRHTQ")
	public Integer getFIRE_Repair_Replace_RepositionHeater_Quantity_Number_();

	@SavVariableMapping("FHSFRSCQ")
	public Integer getFIRE_ProvideSelf_ClosingDoors_Quantity_Number_();

	@SavVariableMapping("FHSFRIDQ")
	public Integer getFIRE_InstallSmokeDetectors_Quantity_Number_();

	@SavVariableMapping("FHSBTWCQ")
	public Integer getFALLSBETWEENLEVELS_InstallWindowSafetyCatches_Quantity_Number_();

	@SavVariableMapping("FHSDAWIQ")
	public Integer getDAMPANDMOULD_Repair_InstallWindow_Quantity_Number_();

	@SavVariableMapping("FHSSTEXQ")
	public Integer getFALLSONSTAIRS_Repair_ReplaceExternalSteps_Quantity_Number_();

	@SavVariableMapping("FHSNOLIK")
	public Integer getNOISE_LikelihoodOfAPersonBeingAffected();

	@SavVariableMapping("FHSFRFWQ")
	public Integer getFIRE_ProvideFireStopWallToLoftSpace_Quantity_Number_();

	@SavVariableMapping("FHSFRFEQ")
	public Integer getFIRE_ProvideFireEscape_Quantity_Flights_();

	@SavVariableMapping("FHSFRLIK")
	public Integer getFIRE_LikelihoodOfAFireOccurringIfOccupiedByAPersonOver60();

	@SavVariableMapping("FHSBTGIQ")
	public Integer getFALLSBETWEENLEVELS_InstallNewGuarding_Balustrading_Quantity_Metres_();

	@SavVariableMapping("FHSFBAHQ")
	public Integer getFALLSASSTDWITHBATHS_AdditionalHeating_Quantity_Number_();

	@SavVariableMapping("FHSLVLIK")
	public Integer getFALLSONTHELEVEL_ProbabilityOfAPersonOver60HavingAFall();

	@SavVariableMapping("FHSSTLIQ")
	public Integer getFALLSONSTAIRS_Repair_Replace_ProvideAdditionalLightingQuantity_Number_();

	@SavVariableMapping("FHSFBRLQ")
	public Integer getFALLSASSTDWITHBATHS_RepairEtc_Lighting_Quantity_Number_();

	@SavVariableMapping("FHSFRSV")
	public Double getFIRE_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSHSSV")
	public Double getHOTSURFACES_LikelihoodOfServereOutcome();

	@SavVariableMapping("FHSSTSV")
	public Double getFALLSONSTAIRS_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSFBSR")
	public Double getFALLSASSTEDWITHBATHS_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSCESR")
	public Double getCOLLISIONANDENTRAPMENT_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSFRSR")
	public Double getFIRE_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSFBSV")
	public Double getFALLSASSTDWITHBATHS_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSCEEX")
	public Double getCOLLISIONANDENTRAPMENT_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSBTSV")
	public Double getFALLSBETWEENLEVELS_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSENSV")
	public Double getENTRYBYINTRUDERS_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSCESV")
	public Double getCOLLISIONANDENTRAPMENT_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSFBEX")
	public Double getFALLSASSTEDWITHBATHS_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSSTEX")
	public Double getFALLSONSTAIRS_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSNOEX")
	public Double getNOISE_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSDASV")
	public Double getDAMPANDMOULD_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSFREX")
	public Double getFIRE_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSNOSV")
	public Double getNOISE_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSENEX")
	public Double getENTRYBYINTRUDERS_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSLVEX")
	public Double getFALLSONTHELEVEL_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSENSR")
	public Double getENTRYBYINTRUDERS_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSSTSR")
	public Double getFALLSONSTAIRS_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSHSEX")
	public Double getHOTSURFACES_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSLVSR")
	public Double getFALLSONTHELEVEL_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSLVSV")
	public Double getFALLSONTHELEVEL_LikelihoodOfSevereOutcome();

	@SavVariableMapping("FHSHSSR")
	public Double getHOTSURFACES_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSNOSR")
	public Double getNOISE_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSBTEX")
	public Double getFALLSBETWEENLEVELS_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSBTSR")
	public Double getFALLSBETWEENLEVELS_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSDAEX")
	public Double getDAMPANDMOULD_LikelihoodOfExtremeOutcome();

	@SavVariableMapping("FHSDASR")
	public Double getDAMPANDMOULD_LikelihoodOfSeriousOutcome();

	@SavVariableMapping("FHSSTROA")
	public Enum10 getFALLSONSTAIRS_RemoveObstacle_ActionRequired_();

	@SavVariableMapping("FHSLVLIA")
	public Enum10 getFALLSONTHELEVEL_Repair_Replace_ProvideAdditionalLighting_ActionRequired_();

	@SavVariableMapping("FHSFRSOA")
	public Enum10 getFIRE_ProvideAdditionalSockets_ActionRequired_();

	@SavVariableMapping("FHSDAIND")
	public Enum10 getDAMPANDMOULD_ImproveInsulation_DescribedElswhere_();

	@SavVariableMapping("FHSEXPLO")
	public Enum10 getHHSRSSummary_Explosions();

	@SavVariableMapping("FHSSTAIR")
	public Enum1233 getHHSRSSummary_FallsOnStairs();

	@SavVariableMapping("FHSONLEV")
	public Enum1233 getHHSRSSummary_FallsOnTheLevel();

	@SavVariableMapping("FHSAHWA")
	public Enum10 getAreAnyHazardsSignificantlyHigherThanAverage_();

	@SavVariableMapping("FHSDARDA")
	public Enum10 getDAMPANDMOULD_TreatRisingDamp_ActionRequired_();

	@SavVariableMapping("FHSFBGRD")
	public Enum10 getFALLSASSTDWITHBATHS_ProvideGrabrail_DescribedElsewhere_();

	@SavVariableMapping("FHSFRROD")
	public Enum10 getFIRE_RemoveObstacle_DescribedElsewhere_();

	@SavVariableMapping("FHSNONMD")
	public Enum10 getNOISE_NoisyMachinery_DescribedElswhere_();

	@SavVariableMapping("FHSFRUSD")
	public Enum10 getFIRE_UpgradeStairwayToProtectedRoute_DescribedElsewhere_();

	@SavVariableMapping("FHSLVCVA")
	public Enum10 getFALLSONTHELEVEL_CoverSlipperySurfaces_ActionRequired_();

	@SavVariableMapping("FHSBTLEV")
	public Enum1233 getHHSRSSummary_FallsBetweenLevels();

	@SavVariableMapping("FHSBTGIA")
	public Enum10 getFALLSBETWEENLEVELS_InstallNewGuarding_Balustrading_ActionRequired_();

	@SavVariableMapping("FHSFBRAA")
	public Enum10 getFALLSASSTDWITHBATHS_RearrangeBathroom_ActionRequired_();

	@SavVariableMapping("FHSENDSD")
	public Enum10 getENTRYBYINTRUDERS_DefensibleSpace_DescribedElswhere_();

	@SavVariableMapping("FHSENDSA")
	public Enum10 getENTRYBYINTRUDERS_DefensibleSpace_ActionRequired_();

	@SavVariableMapping("FHSFRFED")
	public Enum10 getFIRE_ProvideFireEscape_DescribedElsewhere_();

	@SavVariableMapping("FHSSTLID")
	public Enum10 getFALLSONSTAIRS_Repair_Replace_ProvideAdditionalLightingDescribedElsewhere_();

	@SavVariableMapping("FHSFBRLA")
	public Enum10 getFALLSASSTDWITHBATHS_RepairEtc_Lighting_ActionRequired_();

	@SavVariableMapping("FHSFRWA")
	public Enum10 getFIRE_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSCEDOA")
	public Enum10 getCOLLISIONANDENTRAPMENT_Repair_ReplaceDoors_ActionRequired_();

	@SavVariableMapping("FHSENTRY")
	public Enum1233 getHHSRSSummary_EntryByIntruders();

	@SavVariableMapping("FHSBTBAA")
	public Enum10 getFALLSBETWEENLEVELS_Repair_ReplaceBalconies_ActionRequired_();

	@SavVariableMapping("FHSFRFEA")
	public Enum10 getFIRE_ProvideFireEscape_ActionRequired_();

	@SavVariableMapping("FHSFRSCA")
	public Enum10 getFIRE_ProvideSelf_ClosingDoors_ActionRequired_();

	@SavVariableMapping("FHSENLTA")
	public Enum10 getENTRYBYINTRUDERS_AdditionalLighting_ActionRequired_();

	@SavVariableMapping("FHSBTBUA")
	public Enum10 getFALLSBETWEENLEVELS_Brick_UpDangerousOpening_RaiseCillHeight_ActionRequired_();

	@SavVariableMapping("FHSFIRE")
	public Enum1233 getHHSRSSummary_Fire();

	@SavVariableMapping("FHSSTCVA")
	public Enum10 getFALLSONSTAIRS_CoverDangerousBalustrade_Guarding_ActionRequired_();

	@SavVariableMapping("FHSFOOD")
	public Enum10 getHHSRSSummary_FoodSafety();

	@SavVariableMapping("FHSLVCVD")
	public Enum10 getFALLSONTHELEVEL_CoverSlipperySurfaces_DescribedElsewhere_();

	@SavVariableMapping("FHSSTCOD")
	public Enum10 getFALLSONSTAIRS_Repair_ReplaceExternal_CommonStaircase_DescribedElsewhere_();

	@SavVariableMapping("FHSBTBUD")
	public Enum10 getFALLSBETWEENLEVELS_Brick_UpDangerousOpening_RaiseCillHeight_DescribedElsewhere_();

	@SavVariableMapping("FHSSTCSA")
	public Enum10 getFALLSONSTAIRS_CoverSlipperyStairs_ActionRequired_();

	@SavVariableMapping("FHSHSRCD")
	public Enum10 getHOTSURFACES_RelocateCooker_DescribedElsewhere_();

	@SavVariableMapping("FHSCESLD")
	public Enum10 getCOLLISIONANDENTRAPMENT_SignpostLowHeadroom_DescribedElsewhere_();

	@SavVariableMapping("FHSDAEXD")
	public Enum10 getDAMPANDMOULD_ExtractorFans_DescribedElswhere_();

	@SavVariableMapping("FHSSTCSD")
	public Enum10 getFALLSONSTAIRS_CoverSlipperyStairs_DescribedElsewhere_();

	@SavVariableMapping("FHSSTIBD")
	public Enum10 getFALLSONSTAIRS_InstallBalustrade_DescribedElsewhere_();

	@SavVariableMapping("FHSFRHTA")
	public Enum10 getFIRE_Repair_Replace_RepositionHeater_ActionRequired_();

	@SavVariableMapping("FHSFBRLD")
	public Enum10 getFALLSASSTDWITHBATHS_RepairEtc_Lighting_DescribedElsewhere_();

	@SavVariableMapping("FHSFRCAA")
	public Enum10 getFIRE_Repair_InstallPrecautionsToCommonAreas_ActionRequired_();

	@SavVariableMapping("FHSLVRTA")
	public Enum10 getFALLSONTHELEVEL_RemoveTripSteps_ActionRequired_();

	@SavVariableMapping("FHSDAEXA")
	public Enum10 getDAMPANDMOULD_ExtractorFans_ActionRequired_();

	@SavVariableMapping("FHSBTROA")
	public Enum10 getFALLSBETWEENLEVELS_RemoveObstacle_ActionRequired_();

	@SavVariableMapping("FHSFRSOD")
	public Enum10 getFIRE_ProvideAdditionalSockets_DescribedElsewhere_();

	@SavVariableMapping("FHSFRPSA")
	public Enum10 getFIRE_ReplaceNonFireResistant_SmokePermeableStructure_ActionRequired_();

	@SavVariableMapping("FHSHOTSF")
	public Enum1233 getHHSRSSummary_HotSurfaces();

	@SavVariableMapping("FHSHSRKD")
	public Enum10 getHOTSURFACES_Re_Fit_Extend_ResiteKitchen_DescribedElsewhere_();

	@SavVariableMapping("FHSNOCED")
	public Enum10 getNOISE_SoundproofCeilings_DescribedElswhere_();

	@SavVariableMapping("FHSDHYG")
	public Enum10 getHHSRSSummary_DomesticHygiene();

	@SavVariableMapping("FHSLIGHT")
	public Enum10 getHHSRSSummary_Lighting();

	@SavVariableMapping("FHSDAHTA")
	public Enum10 getDAMPANDMOULD_Repair_ImproveHeating_ActionRequired_();

	@SavVariableMapping("FHSFROWA")
	public Enum10 getFIRE_ProvideSuitableOpenableWindows_ActionRequired_();

	@SavVariableMapping("FHSFRIDA")
	public Enum10 getFIRE_InstallSmokeDetectors_ActionRequired_();

	@SavVariableMapping("FHSDAMP")
	public Enum1233 getHHSRSSummary_DampAndMould();

	@SavVariableMapping("FHSCO")
	public Enum10 getHHSRSSummary_CarbonMonoxide();

	@SavVariableMapping("FHSSTIHA")
	public Enum10 getFALLSONSTAIRS_InstallHandrail_ActionRequired_();

	@SavVariableMapping("FHSBTWCD")
	public Enum10 getFALLSBETWEENLEVELS_InstallWindowSafetyCatches_DescribedElsewhere_();

	@SavVariableMapping("FHSLVRPD")
	public Enum10 getFALLSONTHELEVEL_RepairPaths_ExternalSurfaces_DescribedElsewhere_();

	@SavVariableMapping("FHSFBROA")
	public Enum10 getFALLSASSTDWITHBATHS_RemoveObstacle_ActionRequired_();

	@SavVariableMapping("FHSLVRDA")
	public Enum10 getFALLSONTHELEVEL_RedesignExternalPathways_ActionRequired_();

	@SavVariableMapping("FHSFRFWA")
	public Enum10 getFIRE_ProvideFireStopWallToLoftSpace_ActionRequired_();

	@SavVariableMapping("FHSSTLIA")
	public Enum10 getFALLSONSTAIRS_Repair_Replace_ProvideAdditionalLighting_ActionRequired__();

	@SavVariableMapping("FHSNOFLD")
	public Enum10 getNOISE_SoundproofFloors_DescribedElswhere_();

	@SavVariableMapping("FHSDAPDA")
	public Enum10 getDAMPANDMOULD_TreatPenetratingDamp_ActionRequired_();

	@SavVariableMapping("FHSFRRCA")
	public Enum10 getFIRE_RelocateCooker_ActionRequired_();

	@SavVariableMapping("FHSHSROD")
	public Enum10 getHOTSURFACES_RemoveObstacle_DescribedElsewhere_();

	@SavVariableMapping("FHSFRRKA")
	public Enum10 getFIRE_Re_Fit_Extend_ResiteKitchen_ActionRequired_();

	@SavVariableMapping("FHSNOPWD")
	public Enum10 getNOISE_SoundproofPartyWalls_DescribedElswhere_();

	@SavVariableMapping("FHSFRELD")
	public Enum10 getFIRE_Repair_ReplaceElectricalSystem_DescribedElsewhere_();

	@SavVariableMapping("FHSSTIHD")
	public Enum10 getFALLSONSTAIRS_InstallHandrail_DescribedElsewhere_();

	@SavVariableMapping("FHSBTBAD")
	public Enum10 getFALLSBETWEENLEVELS_Repair_ReplaceBalconies_DescribedElsewhere_();

	@SavVariableMapping("FHSFRIDD")
	public Enum10 getFIRE_InstallSmokeDetectors_DescribedElsewhere_();

	@SavVariableMapping("FHSNOSGD")
	public Enum10 getNOISE_SecondaryGlazing_RepairWindows_DescribedElswhere_();

	@SavVariableMapping("FHSENDOA")
	public Enum10 getENTRYBYINTRUDERS_MakeDoorsSecure_ActionRequired_();

	@SavVariableMapping("FHSNOPTA")
	public Enum10 getNOISE_SoundproofPartitions_ActionRequired_();

	@SavVariableMapping("FHSNOCEA")
	public Enum10 getNOISE_SoundproofCeilings_ActionRequired_();

	@SavVariableMapping("FHSFBRSD")
	public Enum10 getFALLSASSTDWITHBATHS_Re_SiteBathroom_DescribedElsewhere_();

	@SavVariableMapping("FHSFRROA")
	public Enum10 getFIRE_RemoveObstacle_ActionRequired_();

	@SavVariableMapping("FHSFBGRA")
	public Enum10 getFALLSASSTDWITHBATHS_ProvideGrabrail_ActionRequired_();

	@SavVariableMapping("FHSCESLA")
	public Enum10 getCOLLISIONANDENTRAPMENT_SignpostLowHeadroom_ActionRequired_();

	@SavVariableMapping("FHSNOWA")
	public Enum10 getNOISE_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSFROWD")
	public Enum10 getFIRE_ProvideSuitableOpenableWindows_DescribedElsewhere_();

	@SavVariableMapping("FHSBTROD")
	public Enum10 getFALLSBETWEENLEVELS_RemoveObstacle_DescribedElsewhere_();

	@SavVariableMapping("FHSENNPA")
	public Enum10 getENTRYBYINTRUDERS_NeighbourhoodProblems_ActionRequired_();

	@SavVariableMapping("FHSHSROA")
	public Enum10 getHOTSURFACES_RemoveObstacle_ActionRequired_();

	@SavVariableMapping("FHSLVRPA")
	public Enum10 getFALLSONTHELEVEL_RepairPaths_ExternalSurfaces_ActionRequired_();

	@SavVariableMapping("FHSFBWA")
	public Enum10 getFALLSASSTDWITHBATHS_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSENIAA")
	public Enum10 getENTRYBYINTRUDERS_InstallAlarm_CCTV_ActionRequired_();

	@SavVariableMapping("FHSBTGID")
	public Enum10 getFALLSBETWEENLEVELS_InstallNewGuarding_Balustrading_DescribedElsewhere_();

	@SavVariableMapping("FHSFBRSA")
	public Enum10 getFALLSASSTDWITHBATHS_Re_SiteBathroom_ActionRequired_();

	@SavVariableMapping("FHSHSRCA")
	public Enum10 getHOTSURFACES_RelocateCooker_ActionRequired_();

	@SavVariableMapping("FHSFRRKD")
	public Enum10 getFIRE_Re_Fit_Extend_ResiteKitchen_DescribedElsewhere_();

	@SavVariableMapping("FHSDARDD")
	public Enum10 getDAMPANDMOULD_TreatRisingDamp_DescribedElswhere_();

	@SavVariableMapping("FHSCEWID")
	public Enum10 getCOLLISIONANDENTRAPMENT_Repair_ReplaceWindows_DescribedElsewhere_();

	@SavVariableMapping("FHSENIAD")
	public Enum10 getENTRYBYINTRUDERS_InstallAlarm_CCTV_DescribedElswhere_();

	@SavVariableMapping("FHSLVROD")
	public Enum10 getFALLSONTHELEVEL_RemoveObstacle_DescribedElsewhere_();

	@SavVariableMapping("FHSDAWID")
	public Enum10 getDAMPANDMOULD_Repair_InstallWindow_DescribedElswhere_();

	@SavVariableMapping("FHSLVROA")
	public Enum10 getFALLSONTHELEVEL_RemoveObstacle_ActionRequired_();

	@SavVariableMapping("FHSCEROD")
	public Enum10 getCOLLISIONANDENTRAPMENT_RemoveObstacle_DescribedElsewhere_();

	@SavVariableMapping("FHSFBATH")
	public Enum1233 getHHSRSSummary_FallsAsstdWithBaths();

	@SavVariableMapping("FHSFBAHA")
	public Enum10 getFALLSASSTDWITHBATHS_AdditionalHeating_ActionRequired_();

	@SavVariableMapping("FHSSTRDD")
	public Enum10 getFALLSONSTAIRS_RedesignInternal_CommonOrExternalStaircase_DescribedElsewhere_();

	@SavVariableMapping("FHSENCOD")
	public Enum10 getENTRYBYINTRUDERS_ConciergeOrEntryphone_DescribedElswhere_();

	@SavVariableMapping("FHSFRPSD")
	public Enum10 getFIRE_ReplaceNonFireResistant_SmokePermeableStructure_DescribedElsewhere_();

	@SavVariableMapping("FHSSTRDA")
	public Enum10 getFALLSONSTAIRS_RedesignInternal_CommonOrExternalStaircase_ActionRequired_();

	@SavVariableMapping("FHSFRELA")
	public Enum10 getFIRE_Repair_ReplaceElectricalSystem_ActionRequired_();

	@SavVariableMapping("FHSCENT")
	public Enum1233 getHHSRSSummary_CollisionAndEntrapment();

	@SavVariableMapping("FHSBTWCA")
	public Enum10 getFALLSBETWEENLEVELS_InstallWindowSafetyCatches_ActionRequired_();

	@SavVariableMapping("FHSBTGBD")
	public Enum10 getFALLSBETWEENLEVELS_Repair_ReplaceCoverExistingGuarding_Balustrading_DescribedElsewhere_();

	@SavVariableMapping("FHSCEWA")
	public Enum10 getCOLLISIONANDENTRAPMENT_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSENCOA")
	public Enum10 getENTRYBYINTRUDERS_ConciergeOrEntryphone_ActionRequired_();

	@SavVariableMapping("FHSSTRPA")
	public Enum10 getFALLSONSTAIRS_Repair_ReplaceInternalStaircase_ActionRequired_();

	@SavVariableMapping("FHSHSRKA")
	public Enum10 getHOTSURFACES_Re_Fit_Extend_ResiteKitchen_ActionRequired_();

	@SavVariableMapping("FHSDAHTD")
	public Enum10 getDAMPANDMOULD_Repair_ImproveHeating_DescribedElswhere_();

	@SavVariableMapping("FHSBTPLA")
	public Enum10 getFALLSBETWEENLEVELS_RepairsToPlot_ActionRequired_();

	@SavVariableMapping("FHSNOFLA")
	public Enum10 getNOISE_SoundproofFloors_ActionRequired_();

	@SavVariableMapping("FHSELS")
	public Enum10 getHHSRSSummary_ElectricalSafety();

	@SavVariableMapping("FHSBTPLD")
	public Enum10 getFALLSBETWEENLEVELS_RepairsToPlot_DescribedElsewhere_();

	@SavVariableMapping("FHSLVRDD")
	public Enum10 getFALLSONTHELEVEL_RedesignExternalPathways_DescribedElsewhere_();

	@SavVariableMapping("FHSENWA")
	public Enum10 getENTRYBYINTRUDERS_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSNOISE")
	public Enum1233 getHHSRSSummary_Noise();

	@SavVariableMapping("FHSFRRCD")
	public Enum10 getFIRE_RelocateCooker_DescribedElsewhere_();

	@SavVariableMapping("FHSLVRFA")
	public Enum10 getFALLSONTHELEVEL_RepairFloors_ActionRequired_();

	@SavVariableMapping("FHSFRUSA")
	public Enum10 getFIRE_UpgradeStairwayToProtectedRoute_ActionRequired_();

	@SavVariableMapping("FHSDAWA")
	public Enum10 getDAMPANDMOULD_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSNOPTD")
	public Enum10 getNOISE_SoundproofPartitions_DescribedElswhere_();

	@SavVariableMapping("FHSSTCVD")
	public Enum10 getFALLSONSTAIRS_CoverDangerousBalustrade_Guarding_DescribedElsewhere_();

	@SavVariableMapping("FHSSTRPD")
	public Enum10 getFALLSONSTAIRS_Repair_ReplaceInternalStaircase_DescribedElsewhere_();

	@SavVariableMapping("FHSENWID")
	public Enum10 getENTRYBYINTRUDERS_MakeWindowsSecure_DescribedElswhere_();

	@SavVariableMapping("FHSFRHSA")
	public Enum10 getFIRE_ReplaceInadequateHeatingSystem_ActionRequired_();

	@SavVariableMapping("FHSNONMA")
	public Enum10 getNOISE_NoisyMachinery_ActionRequired_();

	@SavVariableMapping("FHSSTCOA")
	public Enum10 getFALLSONSTAIRS_Repair_ReplaceExternal_CommonStaircase_ActionRequired_();

	@SavVariableMapping("FHSFRCAD")
	public Enum10 getFIRE_Repair_InstallPrecautionsToCommonAreas_DescribedElsewhere_();

	@SavVariableMapping("FHSSCOLL")
	public Enum10 getHHSRSSummary_StructuralCollapse();

	@SavVariableMapping("FHSCEDOD")
	public Enum10 getCOLLISIONANDENTRAPMENT_Repair_ReplaceDoors_DescribedElsewhere_();

	@SavVariableMapping("FHSSTEXA")
	public Enum10 getFALLSONSTAIRS_Repair_ReplaceExternalSteps_ActionRequired_();

	@SavVariableMapping("FHSFRFWD")
	public Enum10 getFIRE_ProvideFireStopWallToLoftSpace_DescribedElsewhere_();

	@SavVariableMapping("FHSNOSGA")
	public Enum10 getNOISE_SecondaryGlazing_RepairWindows_ActionRequired_();

	@SavVariableMapping("FHSSTIBA")
	public Enum10 getFALLSONSTAIRS_InstallBalustrade_ActionRequired_();

	@SavVariableMapping("FHSENWIA")
	public Enum10 getENTRYBYINTRUDERS_MakeWindowsSecure_ActionRequired_();

	@SavVariableMapping("FHSBTLID")
	public Enum10 getFALLSBETWEENLEVELS_Repair_Replace_ProvideAdditionalLighting_DescribedElsewhere_();

	@SavVariableMapping("FHSDAINA")
	public Enum10 getDAMPANDMOULD_ImproveInsulation_ActionRequired_();

	@SavVariableMapping("FHSCEROA")
	public Enum10 getCOLLISIONANDENTRAPMENT_RemoveObstacle_ActionRequired_();

	@SavVariableMapping("FHSDAPDD")
	public Enum10 getDAMPANDMOULD_TreatPenetratingDamp_DescribedElswhere_();

	@SavVariableMapping("FHSFBROD")
	public Enum10 getFALLSASSTDWITHBATHS_RemoveObstacle_DescribedElsewhere_();

	@SavVariableMapping("FHSLVWA")
	public Enum10 getFALLSONTHELEVEL_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSFRHTD")
	public Enum10 getFIRE_Repair_Replace_RepositionHeater_DescribedElsewhere_();

	@SavVariableMapping("FHSSTROD")
	public Enum10 getFALLSONSTAIRS_RemoveObstacle_DescribedElsewhere_();

	@SavVariableMapping("FHSPHYG")
	public Enum10 getHHSRSSummary_PersonalHygiene();

	@SavVariableMapping("FHSCEWIA")
	public Enum10 getCOLLISIONANDENTRAPMENT_Repair_ReplaceWindows_ActionRequired_();

	@SavVariableMapping("FHSBTLIA")
	public Enum10 getFALLSBETWEENLEVELS_Repair_Replace_ProvideAdditionalLighting_ActionRequired_();

	@SavVariableMapping("FHSFBRAD")
	public Enum10 getFALLSASSTDWITHBATHS_RearrangeBathroom_DescribedElsewhere_();

	@SavVariableMapping("FHSHSRHD")
	public Enum10 getHOTSURFACES_Repair_ReplaceOrRepositionHeater_HeatingOrHotWaterPipesOrCover_DescribedElsewhere_();

	@SavVariableMapping("FHSSTWA")
	public Enum10 getFALLSONSTAIRS_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSBTWA")
	public Enum10 getFALLSBETWEENLEVELS_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSFBRPD")
	public Enum10 getFALLSASSTDWITHBATHS_Repair_ReplaceBath_DescribedElsewhere_();

	@SavVariableMapping("FHSLVRFD")
	public Enum10 getFALLSONTHELEVEL_RepairFloors_DescribedElsewhere_();

	@SavVariableMapping("FHSWATER")
	public Enum10 getHHSRSSummary_WaterSupply();

	@SavVariableMapping("FHSSTEXD")
	public Enum10 getFALLSONSTAIRS_Repair_ReplaceExternalSteps_DescribedElsewhere_();

	@SavVariableMapping("FHSENDOD")
	public Enum10 getENTRYBYINTRUDERS_MakeDoorsSecure_DescribedElswhere_();

	@SavVariableMapping("FHSHSWA")
	public Enum10 getHOTSURFACES_SignificantlyHigherThanAverage();

	@SavVariableMapping("FHSEXHT")
	public Enum10 getHHSRSSummary_ExcessHeat();

	@SavVariableMapping("FHSNOPWA")
	public Enum10 getNOISE_SoundproofPartyWalls_ActionRequired_();

	@SavVariableMapping("FHSUNGAS")
	public Enum10 getHHSRSSummary_UncombustedGas();

	@SavVariableMapping("FHSDAWIA")
	public Enum10 getDAMPANDMOULD_Repair_InstallWindow_ActionRequired_();

	@SavVariableMapping("FHSPOA")
	public Enum10 getHHSRSSummary_PositionAndOperabilityAmenities();

	@SavVariableMapping("FHSFRSCD")
	public Enum10 getFIRE_ProvideSelf_ClosingDoors_DescribedElsewhere_();

	@SavVariableMapping("FHSENLTD")
	public Enum10 getENTRYBYINTRUDERS_AdditionalLighting_DescribedElswhere_();

	@SavVariableMapping("FHSBTGBA")
	public Enum10 getFALLSBETWEENLEVELS_Repair_ReplaceCoverExistingGuarding_Balustrading_ActionRequired_();

	@SavVariableMapping("FHSFBAHD")
	public Enum10 getFALLSASSTDWITHBATHS_AdditionalHeating_DescribedElsewhere_();

	@SavVariableMapping("FHSLVRTD")
	public Enum10 getFALLSONTHELEVEL_RemoveTripSteps_DescribedElsewhere_();

	@SavVariableMapping("FHSLVLID")
	public Enum10 getFALLSONTHELEVEL_Repair_Replace_ProvideAdditionalLighting_DescribedElsewhere_();

	@SavVariableMapping("FHSFBRPA")
	public Enum10 getFALLSASSTDWITHBATHS_Repair_ReplaceBath_ActionRequired_();

	@SavVariableMapping("FHSHSRHA")
	public Enum10 getHOTSURFACES_Repair_ReplaceOrRepositionHeater_HeatingOrHotWaterPipesOrCover_ActionRequired_();

}

