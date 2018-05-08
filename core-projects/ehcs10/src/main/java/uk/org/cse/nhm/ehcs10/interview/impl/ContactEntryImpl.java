package uk.org.cse.nhm.ehcs10.interview.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.ContactEntry;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum270;
import uk.org.cse.nhm.ehcs10.interview.types.Enum271;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class ContactEntryImpl extends SurveyEntryImpl implements ContactEntry {

    private Integer HMOHousehold;
    private Enum270 WhetherMoreThanOneHousehold;
    private Enum271 WhoInterviewed;
    private Enum30 Region_EHSOrder;
    private Enum229 StatusOfCase;
    private Enum69 WillingToAgreeToSurveyorVisit;
    private Enum230 FieldworkQuarter;

    public Integer getHMOHousehold() {
        return HMOHousehold;
    }

    public void setHMOHousehold(final Integer HMOHousehold) {
        this.HMOHousehold = HMOHousehold;
    }

    public Enum270 getWhetherMoreThanOneHousehold() {
        return WhetherMoreThanOneHousehold;
    }

    public void setWhetherMoreThanOneHousehold(final Enum270 WhetherMoreThanOneHousehold) {
        this.WhetherMoreThanOneHousehold = WhetherMoreThanOneHousehold;
    }

    public Enum271 getWhoInterviewed() {
        return WhoInterviewed;
    }

    public void setWhoInterviewed(final Enum271 WhoInterviewed) {
        this.WhoInterviewed = WhoInterviewed;
    }

    public Enum30 getRegion_EHSOrder() {
        return Region_EHSOrder;
    }

    public void setRegion_EHSOrder(final Enum30 Region_EHSOrder) {
        this.Region_EHSOrder = Region_EHSOrder;
    }

    public Enum229 getStatusOfCase() {
        return StatusOfCase;
    }

    public void setStatusOfCase(final Enum229 StatusOfCase) {
        this.StatusOfCase = StatusOfCase;
    }

    public Enum69 getWillingToAgreeToSurveyorVisit() {
        return WillingToAgreeToSurveyorVisit;
    }

    public void setWillingToAgreeToSurveyorVisit(final Enum69 WillingToAgreeToSurveyorVisit) {
        this.WillingToAgreeToSurveyorVisit = WillingToAgreeToSurveyorVisit;
    }

    public Enum230 getFieldworkQuarter() {
        return FieldworkQuarter;
    }

    public void setFieldworkQuarter(final Enum230 FieldworkQuarter) {
        this.FieldworkQuarter = FieldworkQuarter;
    }

}
