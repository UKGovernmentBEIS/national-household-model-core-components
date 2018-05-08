package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum279;
import uk.org.cse.nhm.ehcs10.interview.types.Enum283;
import uk.org.cse.nhm.ehcs10.interview.types.Enum284;
import uk.org.cse.nhm.ehcs10.interview.types.Enum289;
import uk.org.cse.nhm.ehcs10.interview.types.Enum291;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface DampEntry extends SurveyEntry {

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("DMPNOTAP")
    public Enum69 getN_A_RoomDoesNotExist();

    @SavVariableMapping("MOULDFLR")
    public Enum69 getRot_Mould_Floors_Carpets();

    @SavVariableMapping("CDWRSRM")
    public Enum279 getWorstAffectedRoom();

    @SavVariableMapping("MOULDWAL")
    public Enum69 getRot_Mould_Walls_Ceilings();

    @SavVariableMapping("NODMPPRB")
    public Enum69 getNoProblemsWithDamp();

    @SavVariableMapping("CDPROB")
    public Enum69 getProblems_Condens_Damp_Mould();

    @SavVariableMapping("CDWHEN")
    public Enum283 getPeriodWhenProblemOccurs_();

    @SavVariableMapping("CDPHMLD")
    public Enum284 getClosestPhoto_Mould_();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

    @SavVariableMapping("STEAMWDW")
    public Enum69 getSteamedUpWindows();

    @SavVariableMapping("OTDMPPRB")
    public Enum69 getOtherDampProblems();

    @SavVariableMapping("MILDWWDW")
    public Enum69 getMildew_Rot_Mould_WindowFrames();

    @SavVariableMapping("ROOM")
    public Enum289 getRoomType();

    @SavVariableMapping("CDPHDMP")
    public Enum284 getClosestPhoto_Damp_();

    @SavVariableMapping("CDAFCT")
    public Enum291 getEffectOfProblem_();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("STEAMWAL")
    public Enum69 getSteamedUp_WetWalls();

}
