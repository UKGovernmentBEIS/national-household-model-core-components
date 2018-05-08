package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1185;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1808;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1811;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1813;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1848;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface SharedEntry extends SurveyEntry {

    @SavVariableMapping("FFCHSLVL")
    public Integer getHEALTHANDSAFTEY_FallsOnTheLevel();

    @SavVariableMapping("FFCHSBTW")
    public Integer getHEALTHANDSAFTEY_FallsBetweenLevels();

    @SavVariableMapping("FFCHSSTR")
    public Integer getHEALTHANDSAFTEY_FallsOnStairs();

    @SavVariableMapping("FFCHSENT")
    public Integer getHEALTHANDSAFETY_EntryByIntruders();

    @SavVariableMapping("FFCHSCEN")
    public Integer getHEALTHANDSAFETY_CollisionAndEntrapment();

    @SavVariableMapping("FFCLITPR")
    public Enum10 getCOMMONELECTRICALSERVICESExternalLighting_Present();

    @SavVariableMapping("FFCASTEP")
    public Enum1808 getACCESSIBILITY_NumberOfStepsFromPavementToEntranceOfModule();

    @SavVariableMapping("FFCPATPR")
    public Enum10 getLANDSCAPINGPaths_Present();

    @SavVariableMapping("FFCCOMAC")
    public Enum1185 getSTORES_COMMONROOMSCommunityRoom_Action();

    @SavVariableMapping("FFCPAT90")
    public Enum1811 getDESIGNOfLandscapingPATHS_AtLeast900MmWide();

    @SavVariableMapping("FFCWEART")
    public Enum1185 getCONTRIBUTIONTOPROBLEMSINCONDITIONNormalWearAndTear();

    @SavVariableMapping("FFCDRYLO")
    public Enum1813 getSTORES_COMMONROOMSDryingRoom_Location();

    @SavVariableMapping("FFCPALAC")
    public Enum1185 getSTORES_COMMONROOMSPaladinStores_Action();

    @SavVariableMapping("FFCLAUPR")
    public Enum10 getSTORES_COMMONROOMSLaundry_Present();

    @SavVariableMapping("FFCLITTR")
    public Enum1185 getCONTRIBUTIONTOPROBLEMSINCONDITIONLitterRubbish();

    @SavVariableMapping("FFCRDSAC")
    public Enum1185 getSURFACES_FENCESUnadoptedEstateRoads_Action();

    @SavVariableMapping("FFCCOMPR")
    public Enum10 getSTORES_COMMONROOMSCommunityRoom_Present();

    @SavVariableMapping("FFCUNDLO")
    public Enum1813 getCOMMUNALPARKINGFACILITIESUndergroundParking_Location();

    @SavVariableMapping("FFCCOVLO")
    public Enum1813 getCOMMUNALPARKINGFACILITIESOtherCoveredParking_Location();

    @SavVariableMapping("FFCBURAC")
    public Enum1185 getCOMMONELECTRICALSERVICESBurglarAlarmSystem_Action();

    @SavVariableMapping("FFCDARAC")
    public Enum1185 getSURFACES_FENCESDryingAreas_Action();

    @SavVariableMapping("FFCLAUAC")
    public Enum1185 getSTORES_COMMONROOMSLaundry_Action();

    @SavVariableMapping("FFCCOVPR")
    public Enum10 getCOMMUNALPARKINGFACILITIESOtherCoveredParking_Present();

    @SavVariableMapping("FFCPATAD")
    public Enum1811 getDESIGNOfLandscapingPATHS_ProjectedFromAdjacentDrops();

    @SavVariableMapping("FFCCOMLO")
    public Enum1813 getSTORES_COMMONROOMSCommunityRoom_Location();

    @SavVariableMapping("FFCBURPR")
    public Enum10 getCOMMONELECTRICALSERVICESBurglarAlarmSystem_Present();

    @SavVariableMapping("FFCHTGAC")
    public Enum1185 getCOMMONELECTRICALSERVICESLightningConductors_Action();

    @SavVariableMapping("FFCACOVR")
    public Enum10 getACCESSIBILITY_IsEntranceCovered_();

    @SavVariableMapping("FFCDRYAC")
    public Enum1185 getSTORES_COMMONROOMSDryingRoom_Action();

    @SavVariableMapping("FFCBINLO")
    public Enum1813 getSTORES_COMMONROOMSBinStores_Location();

    @SavVariableMapping("FFCGARPR")
    public Enum10 getCOMMUNALPARKINGFACILITIESGarages_Present();

    @SavVariableMapping("FFCUNDPR")
    public Enum10 getCOMMUNALPARKINGFACILITIESUndergroundParking_Present();

    @SavVariableMapping("FFCROOAC")
    public Enum1185 getCOMMUNALPARKINGFACILITIESRoofParking_Action();

    @SavVariableMapping("FFCINAPP")
    public Enum1185 getCONTRIBUTIONTOPROBLEMSINCONDITIONInappropriateUse();

    @SavVariableMapping("FFCAFIRM")
    public Enum10 getACCESSIBILITY_IsPathFirmAndEven_();

    @SavVariableMapping("FFCHRDAC")
    public Enum1185 getLANDSCAPINGHardLandscaping_Action();

    @SavVariableMapping("FFCCCTAC")
    public Enum1185 getCOMMONELECTRICALSERVICESCCTV_Action();

    @SavVariableMapping("FFCGRAPR")
    public Enum10 getLANDSCAPINGGrassPlanting_Present();

    @SavVariableMapping("FFCBINAC")
    public Enum1185 getSTORES_COMMONROOMSBinStores_Action();

    @SavVariableMapping("FFCINADM")
    public Enum1185 getCONTRIBUTIONTOPROBLEMSINCONDITIONInadequateMaintenance();

    @SavVariableMapping("FFCLAULO")
    public Enum1813 getSTORES_COMMONROOMSLaundry_Location();

    @SavVariableMapping("FFCALIT")
    public Enum10 getACCESSIBILITY_IsEntranceAdequatelyLit_();

    @SavVariableMapping("FFCGRAFF")
    public Enum1185 getCONTRIBUTIONTOPROBLEMSINCONDITIONGraffiti();

    @SavVariableMapping("FFCPLAPR")
    public Enum10 getSURFACES_FENCESChildrenSPlayAreas_Present();

    @SavVariableMapping("FFCHEAAC")
    public Enum1185 getCOMMONELECTRICALSERVICESCommunalHeating_Action();

    @SavVariableMapping("FFCPALPR")
    public Enum10 getSTORES_COMMONROOMSPaladinStores_Present();

    @SavVariableMapping("FFCARAMP")
    public Enum1848 getACCESSIBILITY_SpaceForRamp();

    @SavVariableMapping("FFCWARLO")
    public Enum1813 getSTORES_COMMONROOMSWardenCaretakerOffice_Location();

    @SavVariableMapping("FFCSHARE")
    public Enum10 getSHAREDFACILITIESDoSharedFacilitiesServicesExist_();

    @SavVariableMapping("FFCGARAC")
    public Enum1185 getCOMMUNALPARKINGFACILITIESGarages_Action();

    @SavVariableMapping("FFCTENPR")
    public Enum10 getSTORES_COMMONROOMSTenantStores_Present();

    @SavVariableMapping("FFCWARPR")
    public Enum10 getSTORES_COMMONROOMSWardenCaretakerOffice_Present();

    @SavVariableMapping("FFCMULPR")
    public Enum10 getCOMMUNALPARKINGFACILITIESMulti_StoreyParking_Present();

    @SavVariableMapping("FFCUNDAC")
    public Enum1185 getCOMMUNALPARKINGFACILITIESUndergroundParking_Action();

    @SavVariableMapping("FFCDESIG")
    public Enum1185 getCONTRIBUTIONTOPROBLEMSINCONDITIONPoorDesignSpecification();

    @SavVariableMapping("FFCTENLO")
    public Enum1813 getSTORES_COMMONROOMSTenantStores_Location();

    @SavVariableMapping("FFCMULAC")
    public Enum1185 getCOMMUNALPARKINGFACILITIESMulti_StoreyParking_Action();

    @SavVariableMapping("FFCPATAC")
    public Enum1185 getLANDSCAPINGPaths_Action();

    @SavVariableMapping("FFCPLAAC")
    public Enum1185 getSURFACES_FENCESChildrenSPlayAreas_Action();

    @SavVariableMapping("FFCAIRAC")
    public Enum1185 getCOMMUNALPARKINGFACILITIESOpenAirParkingBays_Action();

    @SavVariableMapping("FFCGARLO")
    public Enum1813 getCOMMUNALPARKINGFACILITIESGarages_Location();

    @SavVariableMapping("FFCHEAPR")
    public Enum10 getCOMMONELECTRICALSERVICESCommunalHeating_Present();

    @SavVariableMapping("FFCVAND")
    public Enum1185 getCONTRIBUTIONTOPROBLEMSINCONDITIONVandalism();

    @SavVariableMapping("FFCCCTPR")
    public Enum10 getCOMMONELECTRICALSERVICESCCTV_Present();

    @SavVariableMapping("FFCDRYPR")
    public Enum10 getSTORES_COMMONROOMSDryingRoom_Present();

    @SavVariableMapping("FFCROOPR")
    public Enum10 getCOMMUNALPARKINGFACILITIESRoofParking_Present();

    @SavVariableMapping("FFCCOVAC")
    public Enum1185 getCOMMUNALPARKINGFACILITIESOtherCoveredParking_Action();

    @SavVariableMapping("FFCPATGR")
    public Enum1811 getDESIGNOfLandscapingPATHS_GradientGentlerThan1In12();

    @SavVariableMapping("FFCHRDPR")
    public Enum10 getLANDSCAPINGHardLandscaping_Present();

    @SavVariableMapping("FFCLITAC")
    public Enum1185 getCOMMONELECTRICALSERVICESExternalLighting_Action();

    @SavVariableMapping("FFCGRAAC")
    public Enum1185 getLANDSCAPINGGrassPlanting_Action();

    @SavVariableMapping("FFCWALPR")
    public Enum10 getLANDSCAPINGWallsFences_Present();

    @SavVariableMapping("FFCDARPR")
    public Enum10 getSURFACES_FENCESDryingAreas_Present();

    @SavVariableMapping("FFCWALAC")
    public Enum1185 getLANDSCAPINGWallsFences_Action();

    @SavVariableMapping("FFCRDSPR")
    public Enum10 getSURFACES_FENCESUnadoptedEstateRoads_Present();

    @SavVariableMapping("FFCWARAC")
    public Enum1185 getSTORES_COMMONROOMSWardenCaretakerOffice_Action();

    @SavVariableMapping("FFCMULLO")
    public Enum1813 getCOMMUNALPARKINGFACILITIESMulti_StoreyParking_Location();

    @SavVariableMapping("FFCHTGPR")
    public Enum10 getCOMMONELECTRICALSERVICESLightningConductors_Present();

    @SavVariableMapping("FFCTVRPR")
    public Enum10 getCOMMONELECTRICALSERVICESTVReception_Present();

    @SavVariableMapping("FFCTENAC")
    public Enum1185 getSTORES_COMMONROOMSTenantStores_Action();

    @SavVariableMapping("FFCPALLO")
    public Enum1813 getSTORES_COMMONROOMSPaladinStores_Location();

    @SavVariableMapping("FFCAIRPR")
    public Enum10 getCOMMUNALPARKINGFACILITIESOpenAirParkingBays_Present();

    @SavVariableMapping("FFCROOLO")
    public Enum1813 getCOMMUNALPARKINGFACILITIESRoofParking_Location();

    @SavVariableMapping("FFCTVRAC")
    public Enum1185 getCOMMONELECTRICALSERVICESTVReception_Action();

    @SavVariableMapping("FFCBINPR")
    public Enum10 getSTORES_COMMONROOMSBinStores_Present();

}
