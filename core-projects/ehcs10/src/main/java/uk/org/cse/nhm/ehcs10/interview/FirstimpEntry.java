package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum444;
import uk.org.cse.nhm.ehcs10.interview.types.Enum445;
import uk.org.cse.nhm.ehcs10.interview.types.Enum447;
import uk.org.cse.nhm.ehcs10.interview.types.Enum448;
import uk.org.cse.nhm.ehcs10.interview.types.Enum451;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface FirstimpEntry extends SurveyEntry {

    @SavVariableMapping("PROPNONR")
    public Enum69 getWhetherSampleAddressContainsNon_ResidentialUnits();

    @SavVariableMapping("SMPINEL")
    public Enum444 getAddressEligibility();

    @SavVariableMapping("FRSTIMPB")
    public Enum445 getFirstImpressions_Building();

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("FLOORS")
    public Enum447 getNumberOfFloorsInBuilding();

    @SavVariableMapping("PROPTYP")
    public Enum448 getPropertyType_FirstImpressions();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("FRSTIMPN")
    public Enum451 getFirstImpressions_Neighbourhood();

}
