package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1698;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface RooffeatEntry extends SurveyEntry {

    @SavVariableMapping("FEXRF2RN")
    public Integer getBACK_ReplaceRelay();

    @SavVariableMapping("FEXRF1RN")
    public Integer getFRONT_ReplaceRelay();

    @SavVariableMapping("FEXRF2RP")
    public Integer getBACK_RepairRepaint();

    @SavVariableMapping("FEXRF1TM")
    public Integer getFRONT_ReplacementPeriod();

    @SavVariableMapping("FEXRF2TM")
    public Integer getBACK_ReplacementPeriod();

    @SavVariableMapping("FEXRF2LV")
    public Integer getBACK_Leave();

    @SavVariableMapping("FEXRF1LV")
    public Integer getFRONT_Leave();

    @SavVariableMapping("FEXRF1RP")
    public Integer getFRONT_RepairRepaint();

    @SavVariableMapping("FEXRF2FL")
    public Enum10 getBACK_Faults();

    @SavVariableMapping("FEXRF1UR")
    public Enum10 getFRONT_Urgent_();

    @SavVariableMapping("TYPE")
    public Enum1698 getTypeOfRoofFeature();

    @SavVariableMapping("FEXRF2PR")
    public Enum10 getBACK_Present();

    @SavVariableMapping("FEXRF1FL")
    public Enum10 getFRONT_Faults();

    @SavVariableMapping("FEXRF2UR")
    public Enum10 getBACK_Urgent_();

    @SavVariableMapping("FEXRF1PR")
    public Enum10 getFRONT_Present();

}
