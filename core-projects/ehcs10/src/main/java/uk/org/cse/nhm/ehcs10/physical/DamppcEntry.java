package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1284;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface DamppcEntry extends SurveyEntry {

    @SavVariableMapping("FEXDP2LV")
    public Integer getBACK_Leave();

    @SavVariableMapping("FEXDP2TE")
    public Integer getBACK_TenthsOfLength();

    @SavVariableMapping("FEXDP1RN")
    public Integer getFRONT_ReplaceInstall();

    @SavVariableMapping("FEXDP2RN")
    public Integer getBACK_ReplaceInstall();

    @SavVariableMapping("FEXDP1TE")
    public Integer getFRONT_TenthsOfLength();

    @SavVariableMapping("FEXDP1LV")
    public Integer getFRONT_Leave();

    @SavVariableMapping("FEXDP2TM")
    public Integer getBACK_ReplacementPeriod();

    @SavVariableMapping("FEXDP1TM")
    public Integer getFRONT_ReplacementPeriod();

    @SavVariableMapping("FEXDP2FL")
    public Enum1282 getBACK_Faults();

    @SavVariableMapping("FEXDP2UR")
    public Enum10 getBACK_Urgent();

    @SavVariableMapping("TYPE")
    public Enum1284 getTypeOfDampProofCourse();

    @SavVariableMapping("FEXDP1UR")
    public Enum10 getFRONT_Urgent();

    @SavVariableMapping("FEXDP1FL")
    public Enum1282 getFRONT_Faults();

}
