package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1303;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface ElevateEntry extends SurveyEntry {

    @SavVariableMapping("FELSUPRF")
    public Integer getRIGHTFACE_MonoSupportingWalls();

    @SavVariableMapping("FELFENLW")
    public Integer getLEFTFACE_FenestrationWindow();

    @SavVariableMapping("FELGABLF")
    public Integer getLEFTFACE_Gables();

    @SavVariableMapping("FELFENLN")
    public Integer getLEFTFACE_FenestrationWall();

    @SavVariableMapping("FELFENBW")
    public Integer getBACKFACE_FenestrationWindow();

    @SavVariableMapping("FELGABBF")
    public Integer getBACKFACE_Gables();

    @SavVariableMapping("FELMAWLF")
    public Integer getLEFTFACE_MainWalls();

    @SavVariableMapping("FELMAWFF")
    public Integer getFRONTFACE_MainWalls();

    @SavVariableMapping("FELFENFV")
    public Integer getFRONTFACE_FenestrationVoid();

    @SavVariableMapping("FELMAWRF")
    public Integer getRIGHTFACE_MainWalls();

    @SavVariableMapping("FELFENRN")
    public Integer getRIGHTFACE_FenestrationWall();

    @SavVariableMapping("FELPARFF")
    public Integer getFRONTFACE_Parapets();

    @SavVariableMapping("FELSUPLF")
    public Integer getLEFTFACE_MonoSupportingWalls();

    @SavVariableMapping("FELPARLF")
    public Integer getLEFTFACE_Parapets();

    @SavVariableMapping("FELGABFF")
    public Integer getFRONTFACE_Gables();

    @SavVariableMapping("FELFENRW")
    public Integer getRIGHTFACE_FenestrationWindow();

    @SavVariableMapping("FVWTENLF")
    public Integer getVIEWSLEFT_TenthsAttached();

    @SavVariableMapping("FELFENFW")
    public Integer getFRONTFACE_FenestrationWindow();

    @SavVariableMapping("FVWTENRF")
    public Integer getVIEWSRIGHT_TenthsAttached();

    @SavVariableMapping("FELPARBF")
    public Integer getBACKFACE_Parapets();

    @SavVariableMapping("FELSUPBF")
    public Integer getBACKFACE_MonoSupportingWalls();

    @SavVariableMapping("FELBASBF")
    public Integer getBACKFACE_BaseWalls();

    @SavVariableMapping("FVWTENFF")
    public Integer getVIEWSFRONT_TenthsAttached();

    @SavVariableMapping("FVWSPEFF")
    public Integer getVIEWSFRONT_FrontFace();

    @SavVariableMapping("FVWTENBF")
    public Integer getVIEWSBACK_TenthsAttached();

    @SavVariableMapping("FELSUPFF")
    public Integer getFRONTFACE_MonoSupportingWalls();

    @SavVariableMapping("FELGABRF")
    public Integer getRIGHTFACE_Gables();

    @SavVariableMapping("FELBASLF")
    public Integer getLEFTFACE_BaseWalls();

    @SavVariableMapping("FELFENLV")
    public Integer getLEFTFACE_FenestrationVoid();

    @SavVariableMapping("FELBASRF")
    public Integer getRIGHTFACE_BaseWalls();

    @SavVariableMapping("FELGUTFF")
    public Integer getFRONTFACE_ValleyGutters();

    @SavVariableMapping("FELFENBV")
    public Integer getBACKFACE_FenestrationVoid();

    @SavVariableMapping("FELBASFF")
    public Integer getFRONTFACE_BaseWalls();

    @SavVariableMapping("FELGUTBF")
    public Integer getBACKFACE_ValleyGutters();

    @SavVariableMapping("FELMAWBF")
    public Integer getBACKFACE_MainWalls();

    @SavVariableMapping("FELGUTLF")
    public Integer getLEFTFACE_ValleyGutters();

    @SavVariableMapping("FELFENBN")
    public Integer getBACKFACE_FenestrationWall();

    @SavVariableMapping("FELPARRF")
    public Integer getRIGHTFACE_Parapets();

    @SavVariableMapping("FELGUTRF")
    public Integer getRIGHTFACE_ValleyGutters();

    @SavVariableMapping("FELFENRV")
    public Integer getRIGHTFACE_FenestrationVoid();

    @SavVariableMapping("FELFENFN")
    public Integer getFRONTFACE_FenestrationWall();

    @SavVariableMapping("FELWTUR")
    public Enum10 getWindTurbinePresent_();

    @SavVariableMapping("FELEXPBF")
    public Enum10 getBACKFACE_IsPartOfFaceUnattached_();

    @SavVariableMapping("FELPVLF")
    public Enum10 getLEFTFACE_SolarPhotovoltaics();

    @SavVariableMapping("FELSOLLF")
    public Enum10 getLEFTFACE_SolarPanels();

    @SavVariableMapping("FVWSPERF")
    public Enum1303 getVIEWSRIGHT_RightFace();

    @SavVariableMapping("FELEXTBF")
    public Enum10 getBACKFACE_ExternalInsulation();

    @SavVariableMapping("FELEXTFF")
    public Enum10 getFRONTFACE_ExternalInsulation();

    @SavVariableMapping("FVWSPELF")
    public Enum1303 getVIEWSLEFT_LeftFace();

    @SavVariableMapping("FELEXTRF")
    public Enum10 getRIGHTFACE_ExternalInsulation();

    @SavVariableMapping("FELPVRF")
    public Enum10 getRIGHTFACE_SolarPhotovoltaics();

    @SavVariableMapping("FELEXPRF")
    public Enum10 getRIGHTFACE_IsPartOfFaceUnattached_();

    @SavVariableMapping("FELCWIAB")
    public Enum10 getEvidenceOfCWIFromTheAirBricks_();

    @SavVariableMapping("FELPVFF")
    public Enum10 getFRONTFACE_SolarPhotovoltaics();

    @SavVariableMapping("FELCAVFF")
    public Enum10 getFRONTFACE_CavityWallInsulation();

    @SavVariableMapping("FELEXPFF")
    public Enum10 getFRONTFACE_IsPartOfFaceUnattached_();

    @SavVariableMapping("FELSOLFF")
    public Enum10 getFRONTFACE_SolarPanels();

    @SavVariableMapping("FELCAVBF")
    public Enum10 getBACKFACE_CavityWallInsulation();

    @SavVariableMapping("FELCAVLF")
    public Enum10 getLEFTFACE_CavityWallInsulation();

    @SavVariableMapping("FELPVBF")
    public Enum10 getBACKFACE_SolarPhotovoltaics();

    @SavVariableMapping("FELSOLBF")
    public Enum10 getBACKFACE_SolarPanels();

    @SavVariableMapping("FELEXPLF")
    public Enum10 getLEFTFACE_IsPartOfFaceUnattached_();

    @SavVariableMapping("FELCAVRF")
    public Enum10 getRIGHTFACE_CavityWallInsulation();

    @SavVariableMapping("FVWSPEBF")
    public Enum1303 getVIEWSBACK_BackFace();

    @SavVariableMapping("FELEXTLF")
    public Enum10 getLEFTFACE_ExternalInsulation();

    @SavVariableMapping("FELSOLRF")
    public Enum10 getRIGHTFACE_SolarPanels();

}
