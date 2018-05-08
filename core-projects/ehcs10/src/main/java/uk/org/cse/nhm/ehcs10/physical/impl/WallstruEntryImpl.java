package uk.org.cse.nhm.ehcs10.physical.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.WallstruEntry;
import uk.org.cse.nhm.ehcs10.physical.types.Enum2004;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class WallstruEntryImpl extends SurveyEntryImpl implements WallstruEntry {

    private Integer BACK_ReplacementPeriod;
    private Integer BACK_Leave;
    private Integer FRONT_Age;
    private Integer BACK_TenthsOfArea;
    private Integer BACK_Age;
    private Integer BACK_RebuildRenew;
    private Integer FRONT_TenthsOfArea;
    private Integer FRONT_Leave;
    private Integer FRONT_Repair;
    private Integer FRONT_ReplacementPeriod;
    private Integer FRONT_RebuildRenew;
    private Integer BACK_Repair;
    private Enum10 BACK_Faults;
    private Enum10 FRONT_Faults;
    private Enum10 FRONT_Urgent;
    private Enum10 BACK_Urgent;
    private Enum2004 TypeOfWallStructure;

    public Integer getBACK_ReplacementPeriod() {
        return BACK_ReplacementPeriod;
    }

    public void setBACK_ReplacementPeriod(final Integer BACK_ReplacementPeriod) {
        this.BACK_ReplacementPeriod = BACK_ReplacementPeriod;
    }

    public Integer getBACK_Leave() {
        return BACK_Leave;
    }

    public void setBACK_Leave(final Integer BACK_Leave) {
        this.BACK_Leave = BACK_Leave;
    }

    public Integer getFRONT_Age() {
        return FRONT_Age;
    }

    public void setFRONT_Age(final Integer FRONT_Age) {
        this.FRONT_Age = FRONT_Age;
    }

    public Integer getBACK_TenthsOfArea() {
        return BACK_TenthsOfArea;
    }

    public void setBACK_TenthsOfArea(final Integer BACK_TenthsOfArea) {
        this.BACK_TenthsOfArea = BACK_TenthsOfArea;
    }

    public Integer getBACK_Age() {
        return BACK_Age;
    }

    public void setBACK_Age(final Integer BACK_Age) {
        this.BACK_Age = BACK_Age;
    }

    public Integer getBACK_RebuildRenew() {
        return BACK_RebuildRenew;
    }

    public void setBACK_RebuildRenew(final Integer BACK_RebuildRenew) {
        this.BACK_RebuildRenew = BACK_RebuildRenew;
    }

    public Integer getFRONT_TenthsOfArea() {
        return FRONT_TenthsOfArea;
    }

    public void setFRONT_TenthsOfArea(final Integer FRONT_TenthsOfArea) {
        this.FRONT_TenthsOfArea = FRONT_TenthsOfArea;
    }

    public Integer getFRONT_Leave() {
        return FRONT_Leave;
    }

    public void setFRONT_Leave(final Integer FRONT_Leave) {
        this.FRONT_Leave = FRONT_Leave;
    }

    public Integer getFRONT_Repair() {
        return FRONT_Repair;
    }

    public void setFRONT_Repair(final Integer FRONT_Repair) {
        this.FRONT_Repair = FRONT_Repair;
    }

    public Integer getFRONT_ReplacementPeriod() {
        return FRONT_ReplacementPeriod;
    }

    public void setFRONT_ReplacementPeriod(final Integer FRONT_ReplacementPeriod) {
        this.FRONT_ReplacementPeriod = FRONT_ReplacementPeriod;
    }

    public Integer getFRONT_RebuildRenew() {
        return FRONT_RebuildRenew;
    }

    public void setFRONT_RebuildRenew(final Integer FRONT_RebuildRenew) {
        this.FRONT_RebuildRenew = FRONT_RebuildRenew;
    }

    public Integer getBACK_Repair() {
        return BACK_Repair;
    }

    public void setBACK_Repair(final Integer BACK_Repair) {
        this.BACK_Repair = BACK_Repair;
    }

    public Enum10 getBACK_Faults() {
        return BACK_Faults;
    }

    public void setBACK_Faults(final Enum10 BACK_Faults) {
        this.BACK_Faults = BACK_Faults;
    }

    public Enum10 getFRONT_Faults() {
        return FRONT_Faults;
    }

    public void setFRONT_Faults(final Enum10 FRONT_Faults) {
        this.FRONT_Faults = FRONT_Faults;
    }

    public Enum10 getFRONT_Urgent() {
        return FRONT_Urgent;
    }

    public void setFRONT_Urgent(final Enum10 FRONT_Urgent) {
        this.FRONT_Urgent = FRONT_Urgent;
    }

    public Enum10 getBACK_Urgent() {
        return BACK_Urgent;
    }

    public void setBACK_Urgent(final Enum10 BACK_Urgent) {
        this.BACK_Urgent = BACK_Urgent;
    }

    public Enum2004 getTypeOfWallStructure() {
        return TypeOfWallStructure;
    }

    public void setTypeOfWallStructure(final Enum2004 TypeOfWallStructure) {
        this.TypeOfWallStructure = TypeOfWallStructure;
    }

}
