package uk.org.cse.nhm.ehcs10.physical.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.PlotwallEntry;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1683;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1685;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class PlotwallEntryImpl extends SurveyEntryImpl implements PlotwallEntry {

    private Integer FRONT_Replace_Metres_;
    private Integer BACK_Demolish_Metres_;
    private Integer BACK_Repair_Metres_;
    private Integer FRONT_Repair_Metres_;
    private Integer BACK_ReplacementPeriod;
    private Integer FRONT_Demolish_Metres_;
    private Integer BACK_Replace_Metres_;
    private Integer FRONT_ReplacementPeriod;
    private Enum1683 TypeOfPlot;
    private Enum10 BACK_Present;
    private Enum1685 TypeOfPlotWall;
    private Enum10 FRONT_Present;
    private Enum10 BACK_Faults;
    private Enum10 BACK_Urgent_;
    private Enum10 FRONT_Urgent_;
    private Enum10 FRONT_Faults;

    public Integer getFRONT_Replace_Metres_() {
        return FRONT_Replace_Metres_;
    }

    public void setFRONT_Replace_Metres_(final Integer FRONT_Replace_Metres_) {
        this.FRONT_Replace_Metres_ = FRONT_Replace_Metres_;
    }

    public Integer getBACK_Demolish_Metres_() {
        return BACK_Demolish_Metres_;
    }

    public void setBACK_Demolish_Metres_(final Integer BACK_Demolish_Metres_) {
        this.BACK_Demolish_Metres_ = BACK_Demolish_Metres_;
    }

    public Integer getBACK_Repair_Metres_() {
        return BACK_Repair_Metres_;
    }

    public void setBACK_Repair_Metres_(final Integer BACK_Repair_Metres_) {
        this.BACK_Repair_Metres_ = BACK_Repair_Metres_;
    }

    public Integer getFRONT_Repair_Metres_() {
        return FRONT_Repair_Metres_;
    }

    public void setFRONT_Repair_Metres_(final Integer FRONT_Repair_Metres_) {
        this.FRONT_Repair_Metres_ = FRONT_Repair_Metres_;
    }

    public Integer getBACK_ReplacementPeriod() {
        return BACK_ReplacementPeriod;
    }

    public void setBACK_ReplacementPeriod(final Integer BACK_ReplacementPeriod) {
        this.BACK_ReplacementPeriod = BACK_ReplacementPeriod;
    }

    public Integer getFRONT_Demolish_Metres_() {
        return FRONT_Demolish_Metres_;
    }

    public void setFRONT_Demolish_Metres_(final Integer FRONT_Demolish_Metres_) {
        this.FRONT_Demolish_Metres_ = FRONT_Demolish_Metres_;
    }

    public Integer getBACK_Replace_Metres_() {
        return BACK_Replace_Metres_;
    }

    public void setBACK_Replace_Metres_(final Integer BACK_Replace_Metres_) {
        this.BACK_Replace_Metres_ = BACK_Replace_Metres_;
    }

    public Integer getFRONT_ReplacementPeriod() {
        return FRONT_ReplacementPeriod;
    }

    public void setFRONT_ReplacementPeriod(final Integer FRONT_ReplacementPeriod) {
        this.FRONT_ReplacementPeriod = FRONT_ReplacementPeriod;
    }

    public Enum1683 getTypeOfPlot() {
        return TypeOfPlot;
    }

    public void setTypeOfPlot(final Enum1683 TypeOfPlot) {
        this.TypeOfPlot = TypeOfPlot;
    }

    public Enum10 getBACK_Present() {
        return BACK_Present;
    }

    public void setBACK_Present(final Enum10 BACK_Present) {
        this.BACK_Present = BACK_Present;
    }

    public Enum1685 getTypeOfPlotWall() {
        return TypeOfPlotWall;
    }

    public void setTypeOfPlotWall(final Enum1685 TypeOfPlotWall) {
        this.TypeOfPlotWall = TypeOfPlotWall;
    }

    public Enum10 getFRONT_Present() {
        return FRONT_Present;
    }

    public void setFRONT_Present(final Enum10 FRONT_Present) {
        this.FRONT_Present = FRONT_Present;
    }

    public Enum10 getBACK_Faults() {
        return BACK_Faults;
    }

    public void setBACK_Faults(final Enum10 BACK_Faults) {
        this.BACK_Faults = BACK_Faults;
    }

    public Enum10 getBACK_Urgent_() {
        return BACK_Urgent_;
    }

    public void setBACK_Urgent_(final Enum10 BACK_Urgent_) {
        this.BACK_Urgent_ = BACK_Urgent_;
    }

    public Enum10 getFRONT_Urgent_() {
        return FRONT_Urgent_;
    }

    public void setFRONT_Urgent_(final Enum10 FRONT_Urgent_) {
        this.FRONT_Urgent_ = FRONT_Urgent_;
    }

    public Enum10 getFRONT_Faults() {
        return FRONT_Faults;
    }

    public void setFRONT_Faults(final Enum10 FRONT_Faults) {
        this.FRONT_Faults = FRONT_Faults;
    }

}
