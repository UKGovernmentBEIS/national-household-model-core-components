package uk.org.cse.nhm.ehcs10.physical.old;

import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1660;
import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1668;
import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1671;
import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1677;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface ShapeEntry extends SurveyEntry {
	@SavVariableMapping("FSHENTRY")
	public String getHOUSEMODULESHAPE_EntryFloorToHouseModule();

	@SavVariableMapping("FDHMLEV1")
	public String getMAIN1STLEVEL_Level();

	@SavVariableMapping("FDHMLEV2")
	public String getMAIN2NDLEVEL_Level();

	@SavVariableMapping("FDHMLEV3")
	public String getMAIN3RDLEVEL_Level();

	@SavVariableMapping("FDHAFLRS")
	public String getADDITIONALPART_AdditionalPart_NumberOfFloors();

	@SavVariableMapping("FDHALEV1")
	public String getADDITIONAL1STLEVEL_Level();

	@SavVariableMapping("FDHALEV2")
	public String getADDITIONAL2NDLEVEL_Level();

	@SavVariableMapping("FDHALEV3")
	public String getADDITIONAL3RDLEVEL_Level();

	@SavVariableMapping("FMTCOOTH")
	public String getSpecifyOtherConstruction();

	@SavVariableMapping("FMTDESCR")
	public String getMATERIALCONSTRUCTION__Name();

	@SavVariableMapping("FDHMFLRS")
	public Integer getEXTERNALDIMENSIONSOFHOUSEMODULEMainStructure_NumberOfFloors();

	@SavVariableMapping("SURVEYID")
	public Integer getSurveyID();

	@SavVariableMapping("FALYELCO")
	public Integer getIMPROVEMENTSALTERATIONSExactYearOfLoftConversion();

	@SavVariableMapping("ENTRYMOD")
	public Integer getEntrymod();

	@SavVariableMapping("STATUS")
	public Integer getStatus();

	@SavVariableMapping("CASENO")
	public Integer getCaseno();

	@SavVariableMapping("ADD3RD")
	public Double getAdd3rd();

	@SavVariableMapping("LEV1ST")
	public Double getLev1st();

	@SavVariableMapping("FDHMWID2")
	public Double getMAIN2NDLEVEL_Width();

	@SavVariableMapping("FDHADEP1")
	public Double getADDITIONAL1STLEVEL_Depth();

	@SavVariableMapping("FDHMDEP3")
	public Double getMAIN3RDLEVEL_Depth();

	@SavVariableMapping("FDHADEP2")
	public Double getADDITIONAL2NDLEVEL_Depth();

	@SavVariableMapping("FDHMWID3")
	public Double getMAIN3RDLEVEL_Width();

	@SavVariableMapping("LEV3RD")
	public Double getLev3rd();

	@SavVariableMapping("FDHADEP3")
	public Double getADDITIONAL3RDLEVEL_Depth();

	@SavVariableMapping("FDHAWID1")
	public Double getADDITIONAL1STLEVEL_Width();

	@SavVariableMapping("FDHAWID3")
	public Double getADDITIONAL3RDLEVEL_Width();

	@SavVariableMapping("FDHMDEP1")
	public Double getMAIN1STLEVEL_Depth();

	@SavVariableMapping("ADD1ST")
	public Double getAdd1st();

	@SavVariableMapping("FDHMWID1")
	public Double getMAIN1STLEVEL_Width();

	@SavVariableMapping("FDHMDEP2")
	public Double getMAIN2NDLEVEL_Depth();

	@SavVariableMapping("LEV2ND")
	public Double getLev2nd();

	@SavVariableMapping("FDHAWID2")
	public Double getADDITIONAL2NDLEVEL_Width();

	@SavVariableMapping("ADD2ND")
	public Double getAdd2nd();

	@SavVariableMapping("FALSPACE")
	public Enum1660 getIMPROVEMENTSALTERATIONSRearrangementOfInternalSpace();

	@SavVariableMapping("FALOCLAD")
	public Enum1660 getIMPROVEMENTSALTERATIONSOver_Cladding();

	@SavVariableMapping("FALOROOF")
	public Enum1660 getIMPROVEMENTSALTERATIONSOver_Roofing();

	@SavVariableMapping("FALREFUR")
	public Enum1660 getIMPROVEMENTSALTERATIONSCompleteRefurbishment();

	@SavVariableMapping("FALEXTAM")
	public Enum1660 getIMPROVEMENTSALTERATIONSExtensionAddedForAmenities();

	@SavVariableMapping("FALLOFTS")
	public Enum1660 getIMPROVEMENTSALTERATIONSLoftConversion();

	@SavVariableMapping("FALLRAD")
	public Enum1660 getIMPROVEMENTSALTERATIONSRadonRemedialWorks();

	@SavVariableMapping("FALEXLIV")
	public Enum1660 getIMPROVEMENTSALTERATIONSExtensionAddedForLivingSpace();

	@SavVariableMapping("FSHATTIC")
	public Enum1668 getHOUSEMODULESHAPE_AtticBasementInHouseModule();

	@SavVariableMapping("FMTPROPS")
	public Enum1282 getMATERIALCONSTRUCTION_ProprietarySystem();

	@SavVariableMapping("FALSTRUC")
	public Enum1660 getIMPROVEMENTSALTERATIONSStructureReplaced();

	@SavVariableMapping("FMTCONST")
	public Enum1671 getMATERIALANDCONSTRUCTIONOFHOUSEMODULE();

	@SavVariableMapping("FALHMOED")
	public Enum1660 getIMPROVEMENTSALTERATIONSConversionToHMOUse();

	@SavVariableMapping("FALCOMBI")
	public Enum1660 getIMPROVEMENTSALTERATIONSTwoOrMoreDwellingsCombined();

	@SavVariableMapping("FALNORES")
	public Enum1660 getIMPROVEMENTSALTERATIONSConversionFromNon_ResidentialUse();

	@SavVariableMapping("FALMORED")
	public Enum1660 getIMPROVEMENTSALTERATIONSConversionToMoreThan1Dwelling();

	@SavVariableMapping("FALAPEAR")
	public Enum1660 getIMPROVEMENTSALTERATIONSAlterationOfExternalAppearance();

	@SavVariableMapping("FSHADDIT")
	public Enum1677 getMODULESHAPEAdditionalPart_Location();

}

