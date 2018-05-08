package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface HhldtypeEntry extends SurveyEntry {

    @SavVariableMapping("HMWKAFT")
    public Enum69 getWeekdayAfternoon_2Pm_5Pm_();

    @SavVariableMapping("OCCTYPEW")
    public Enum453 getOccupancy_Whole_Dwelling();

    @SavVariableMapping("OCCTYPEA")
    public Enum454 getWholeOrPartDwelling();

    @SavVariableMapping("OCCTYPEP")
    public Enum455 getOccupancy_Part_Dwelling();

    @SavVariableMapping("LLORDSH")
    public Enum456 getLandlordTypeForSharedOwners();

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("HMPYGAS")
    public Enum458 getGasPaymentMethod();

    @SavVariableMapping("TIED")
    public Enum69 getAccomTiedToJob();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("CTPAID")
    public Enum69 getPaysCouncilTax();

    @SavVariableMapping("HMWKMORN")
    public Enum69 getWeekdayMorning_9Am_12Pm_();

    @SavVariableMapping("HMPYELEC")
    public Enum463 getElectricityPaymentMethod();

    @SavVariableMapping("TEN1")
    public Enum464 getTenure();

    @SavVariableMapping("HMALLDAY")
    public Enum69 getHomeAllDay_AllTheTime();

    @SavVariableMapping("HMWINTDK")
    public Enum69 getWhenHome_Don_TKnow();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

    @SavVariableMapping("FURN")
    public Enum468 getWhetherFurnished();

    @SavVariableMapping("DVOCC")
    public Enum469 getOccupancyType();

    @SavVariableMapping("LLORD")
    public Enum470 getLandlordType();

    @SavVariableMapping("HMWKLUN")
    public Enum69 getWeekdayLunchtime_12Pm_2Pm_();

    @SavVariableMapping("DVTENSET")
    public Enum472 getGroupedTenureSet();

    @SavVariableMapping("CTDISCH")
    public Enum69 getSinglePersonDiscount();

    @SavVariableMapping("CTBENFT")
    public Enum69 getCouncilTaxBenefit();

    @SavVariableMapping("HMHIVAR")
    public Enum69 getWhenHome_HighlyVariable();

    @SavVariableMapping("CTBENFT1")
    public Enum476 getBenefitCoversAll_PartOfCouncilTax();

    @SavVariableMapping("WHOOWNS")
    public Enum477 getPersonOwning_BuyingAccom();

    @SavVariableMapping("HMWKEVE")
    public Enum69 getWeekdayEvenings();

    @SavVariableMapping("HMWENDEV")
    public Enum69 getWeekendEvenings();

    @SavVariableMapping("HMWENDDY")
    public Enum69 getWeekendDaytimes();

}
