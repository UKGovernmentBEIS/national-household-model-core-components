package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1682;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface PlotlvlEntry extends SurveyEntry {

    @SavVariableMapping("FEXPFTS")
    public Integer getPLOT_TenthsSoft();

    @SavVariableMapping("FEXPFTH")
    public Integer getPLOT_TenthsHard();

    @SavVariableMapping("FEXPFRW")
    public Integer getPLOT_RepairRenewRetainingWall();

    @SavVariableMapping("FEXPFRP")
    public Integer getPLOT_RepairRenewSteps();

    @SavVariableMapping("FEXPFTA")
    public Integer getPLOT_InternalTanking_M2_();

    @SavVariableMapping("FEXPFEX")
    public Integer getPLOT_Excavation_M3_();

    @SavVariableMapping("FEXPFRN")
    public Integer getPLOT_RepairRenewPaving();

    @SavVariableMapping("FEXPFGU")
    public Enum10 getPLOT_InstallGully();

    @SavVariableMapping("FEXPFBD")
    public Enum10 getPLOT_BridgedDPC();

    @SavVariableMapping("FEXPFFL")
    public Enum10 getPLOT_Faults();

    @SavVariableMapping("FEXPFIN")
    public Enum10 getPLOT_InadequateReverseFalls();

    @SavVariableMapping("TYPE")
    public Enum1682 getPlot();

}
