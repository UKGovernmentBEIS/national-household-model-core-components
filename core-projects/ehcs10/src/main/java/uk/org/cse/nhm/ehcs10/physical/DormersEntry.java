package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1296;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface DormersEntry extends SurveyEntry {

    @SavVariableMapping("FEXDB1LV")
    public Integer getFRONT_Leave();

    @SavVariableMapping("FEXDB2WO")
    public Integer getBACK_RebuildWallOnly();

    @SavVariableMapping("FEXDB1RW")
    public Integer getFRONT_RebuildRoof_Walls();

    @SavVariableMapping("FEXDB2DE")
    public Integer getBACK_Demolish();

    @SavVariableMapping("FEXDB1TM")
    public Integer getFRONT_ReplacementPeriod();

    @SavVariableMapping("FEXDB2AG")
    public Integer getBACK_Age();

    @SavVariableMapping("FEXDB2RO")
    public Integer getBACK_RebuildRoofOnly();

    @SavVariableMapping("FEXDB2RW")
    public Integer getBACK_RebuildRoof_Walls();

    @SavVariableMapping("FEXDB1MJ")
    public Integer getFRONT_MajorRepairs();

    @SavVariableMapping("FEXDB2TM")
    public Integer getBACK_ReplacementPeriod();

    @SavVariableMapping("FEXDB1MN")
    public Integer getFRONT_MinorRepairs();

    @SavVariableMapping("FEXDB1AG")
    public Integer getFRONT_Age();

    @SavVariableMapping("FEXDB2MN")
    public Integer getBACK_MinorRepairs();

    @SavVariableMapping("FEXDB1DE")
    public Integer getFRONT_Demolish();

    @SavVariableMapping("FEXDB1RO")
    public Integer getFRONT_RebuildRoofOnly();

    @SavVariableMapping("FEXDB2NO")
    public Integer getBACK_Number();

    @SavVariableMapping("FEXDB2MJ")
    public Integer getBACK_MajorRepairs();

    @SavVariableMapping("FEXDB1NO")
    public Integer getFRONT_Number();

    @SavVariableMapping("FEXDB1WO")
    public Integer getFRONT_RebuildWallOnly();

    @SavVariableMapping("FEXDB2LV")
    public Integer getBACK_Leave();

    @SavVariableMapping("FEXDB2PR")
    public Enum10 getBACK_Present();

    @SavVariableMapping("FEXDB1UR")
    public Enum10 getFRONT_Urgent();

    @SavVariableMapping("FEXDB2UR")
    public Enum10 getBACK_Urgent();

    @SavVariableMapping("FEXDB2FL")
    public Enum10 getBACK_Faults();

    @SavVariableMapping("TYPE")
    public Enum1296 getTypeOfDormer_Bay();

    @SavVariableMapping("FEXDB1PR")
    public Enum10 getFRONT_Present();

    @SavVariableMapping("FEXDB1FL")
    public Enum10 getFRONT_Faults();

}
