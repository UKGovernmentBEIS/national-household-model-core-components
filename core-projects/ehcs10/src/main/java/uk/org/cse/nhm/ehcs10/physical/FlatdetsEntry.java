package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1352;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface FlatdetsEntry extends SurveyEntry {

    @SavVariableMapping("FDFENTRY")
    public String getEntryFloorToDwellingProper();

    @SavVariableMapping("FDFMAINL")
    public String getDIMENSIONSOfFlat_MainFloor_Level();

    @SavVariableMapping("FDFNEXTL")
    public String getDIMENSIONSOfFlat_NextFloor_Level();

    @SavVariableMapping("FDFBCKIA")
    public Integer getTENTHSOFWALLEXPOSEDBackWall_ToInternalAccessways();

    @SavVariableMapping("FDFBCKOF")
    public Integer getTENTHSOFWALLEXPOSEDBackWall_ToOtherFlats();

    @SavVariableMapping("FDFNEXTN")
    public Integer getNextFloorLevel_Numeric();

    @SavVariableMapping("FDFLFTOA")
    public Integer getTENTHSOFWALLEXPOSEDLeftWall_ToOutsideAir();

    @SavVariableMapping("FDFBCKOA")
    public Integer getTENTHSOFWALLEXPOSEDBackWall_ToOutsideAir();

    @SavVariableMapping("FDFLFTOF")
    public Integer getTENTHSOFWALLEXPOSEDLeftWall_ToOtherFlats();

    @SavVariableMapping("FDFRIGIA")
    public Integer getTENTHSOFWALLEXPOSEDRightWall_ToInternalAccessways();

    @SavVariableMapping("FDFRIGOF")
    public Integer getTENTHSOFWALLEXPOSEDRightWall_ToOtherFlats();

    @SavVariableMapping("FDFENTYN")
    public Integer getEntryFloorLevel_Numeric();

    @SavVariableMapping("FDFFROIA")
    public Integer getTENTHSOFWALLEXPOSEDFrontWall_ToInternalAccessways();

    @SavVariableMapping("FDFMAINN")
    public Integer getMainFloorLevel_Numeric();

    @SavVariableMapping("FDFFROOF")
    public Integer getTENTHSOFWALLEXPOSEDFrontWall_ToOtherFlats();

    @SavVariableMapping("FDFLFTIA")
    public Integer getTENTHSOFWALLEXPOSEDLeftWall_ToInternalAccessways();

    @SavVariableMapping("FDFFLOOR")
    public Integer getDIMENSIONSOfFlat_NumberOfFloorsInFlat();

    @SavVariableMapping("FDFFROOA")
    public Integer getTENTHSOFWALLEXPOSEDFrontWall_ToOutsideAir();

    @SavVariableMapping("FDFRIGOA")
    public Integer getTENTHSOFWALLEXPOSEDRightWall_ToOutsideAir();

    @SavVariableMapping("FDFNEXTD")
    public Double getDIMENSIONSOfFlat_Depth_NextFloor_();

    @SavVariableMapping("FDFMAIND")
    public Double getDIMENSIONSOfFlat_Depth_MainFloor_();

    @SavVariableMapping("FDFMAINW")
    public Double getDIMENSIONSOfFlat_Width_MainFloor_();

    @SavVariableMapping("FDFNEXTW")
    public Double getDIMENSIONSOfFlat_Width_NextFloor_();

    @SavVariableMapping("FDFPRIVT")
    public Enum1352 getPrivateEntryStair();

    @SavVariableMapping("FDFSAMED")
    public Enum10 getDIMENSIONSOfFlat_ExternalDimensionsSameAsModule();

}
