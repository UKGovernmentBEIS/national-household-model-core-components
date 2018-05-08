package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum2004;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface WallstruEntry extends SurveyEntry {

    @SavVariableMapping("FEXWS2TM")
    public Integer getBACK_ReplacementPeriod();

    @SavVariableMapping("FEXWS2LV")
    public Integer getBACK_Leave();

    @SavVariableMapping("FEXWS1AG")
    public Integer getFRONT_Age();

    @SavVariableMapping("FEXWS2TE")
    public Integer getBACK_TenthsOfArea();

    @SavVariableMapping("FEXWS2AG")
    public Integer getBACK_Age();

    @SavVariableMapping("FEXWS2RN")
    public Integer getBACK_RebuildRenew();

    @SavVariableMapping("FEXWS1TE")
    public Integer getFRONT_TenthsOfArea();

    @SavVariableMapping("FEXWS1LV")
    public Integer getFRONT_Leave();

    @SavVariableMapping("FEXWS1RP")
    public Integer getFRONT_Repair();

    @SavVariableMapping("FEXWS1TM")
    public Integer getFRONT_ReplacementPeriod();

    @SavVariableMapping("FEXWS1RN")
    public Integer getFRONT_RebuildRenew();

    @SavVariableMapping("FEXWS2RP")
    public Integer getBACK_Repair();

    @SavVariableMapping("FEXWS2FL")
    public Enum10 getBACK_Faults();

    @SavVariableMapping("FEXWS1FL")
    public Enum10 getFRONT_Faults();

    @SavVariableMapping("FEXWS1UR")
    public Enum10 getFRONT_Urgent();

    @SavVariableMapping("FEXWS2UR")
    public Enum10 getBACK_Urgent();

    @SavVariableMapping("TYPE")
    public Enum2004 getTypeOfWallStructure();

}
