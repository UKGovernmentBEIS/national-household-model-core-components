package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1668;
import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1671;
import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1677;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1789;
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

	@SavVariableMapping("FALYELCO")
	public Integer getIMPROVEMENTSALTERATIONSExactYearOfLoftConversion();

	@SavVariableMapping("FDHMFLRS")
	public Integer getEXTERNALDIMENSIONSOFHOUSEMODULEMainStructure_NumberOfFloors();

	@SavVariableMapping("FDHMWID1")
	public Double getMAIN1STLEVEL_Width();

	@SavVariableMapping("FDHAWID1")
	public Double getADDITIONAL1STLEVEL_Width();

	@SavVariableMapping("FDHMWID3")
	public Double getMAIN3RDLEVEL_Width();

	@SavVariableMapping("FDHAWID3")
	public Double getADDITIONAL3RDLEVEL_Width();

	@SavVariableMapping("FDHMDEP1")
	public Double getMAIN1STLEVEL_Depth();

	@SavVariableMapping("FDHADEP2")
	public Double getADDITIONAL2NDLEVEL_Depth();

	@SavVariableMapping("FDHADEP1")
	public Double getADDITIONAL1STLEVEL_Depth();

	@SavVariableMapping("FDHAWID2")
	public Double getADDITIONAL2NDLEVEL_Width();

	@SavVariableMapping("FDHMDEP2")
	public Double getMAIN2NDLEVEL_Depth();

	@SavVariableMapping("FDHADEP3")
	public Double getADDITIONAL3RDLEVEL_Depth();

	@SavVariableMapping("FDHMWID2")
	public Double getMAIN2NDLEVEL_Width();

	@SavVariableMapping("FDHMDEP3")
	public Double getMAIN3RDLEVEL_Depth();

	@SavVariableMapping("FALSPACE")
	public Enum1789 getIMPROVEMENTSALTERATIONSRearrangementOfInternalSpace();

	@SavVariableMapping("FALEXLIV")
	public Enum1789 getIMPROVEMENTSALTERATIONSExtensionAddedForLivingSpace();

	@SavVariableMapping("FALLRAD")
	public Enum1789 getIMPROVEMENTSALTERATIONSRadonRemedialWorks();

	@SavVariableMapping("FALOROOF")
	public Enum1789 getIMPROVEMENTSALTERATIONSOver_Roofing();

	@SavVariableMapping("FSHADDIT")
	public Enum1677 getMODULESHAPEAdditionalPart_Location();

	@SavVariableMapping("FMTPROPS")
	public Enum1282 getMATERIALCONSTRUCTION_ProprietarySystem();

	@SavVariableMapping("FALREFUR")
	public Enum1789 getIMPROVEMENTSALTERATIONSCompleteRefurbishment();

	@SavVariableMapping("FALCOMBI")
	public Enum1789 getIMPROVEMENTSALTERATIONSTwoOrMoreDwellingsCombined();

	@SavVariableMapping("FALEXTAM")
	public Enum1789 getIMPROVEMENTSALTERATIONSExtensionAddedForAmenities();

	@SavVariableMapping("FALAPEAR")
	public Enum1789 getIMPROVEMENTSALTERATIONSAlterationOfExternalAppearance();

	@SavVariableMapping("FALOCLAD")
	public Enum1789 getIMPROVEMENTSALTERATIONSOver_Cladding();

	@SavVariableMapping("FMTCONST")
	public Enum1671 getMATERIALANDCONSTRUCTIONOFHOUSEMODULE();

	@SavVariableMapping("FALMORED")
	public Enum1789 getIMPROVEMENTSALTERATIONSConversionToMoreThan1Dwelling();

	@SavVariableMapping("FALLOFTS")
	public Enum1789 getIMPROVEMENTSALTERATIONSLoftConversion();

	@SavVariableMapping("FSHATTIC")
	public Enum1668 getHOUSEMODULESHAPE_AtticBasementInHouseModule();

	@SavVariableMapping("FALNORES")
	public Enum1789 getIMPROVEMENTSALTERATIONSConversionFromNon_ResidentialUse();

	@SavVariableMapping("FALSTRUC")
	public Enum1789 getIMPROVEMENTSALTERATIONSStructureReplaced();

	@SavVariableMapping("FALHMOED")
	public Enum1789 getIMPROVEMENTSALTERATIONSConversionToHMOUse();

}

