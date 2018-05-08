package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1255 {
    @SavEnumMapping({"Unknown", "Question Not Applicable", "Section Not Applicable"})
    __MISSING,
    @SavEnumMapping("Through another flat")
    ThroughAnotherFlat,
    @SavEnumMapping("Through another flat and common areas")
    ThroughAnotherFlatAndCommonAreas,
    @SavEnumMapping("Through common areas")
    ThroughCommonAreas,
    @SavEnumMapping("Flat is final exit")
    FlatIsFinalExit,

}
