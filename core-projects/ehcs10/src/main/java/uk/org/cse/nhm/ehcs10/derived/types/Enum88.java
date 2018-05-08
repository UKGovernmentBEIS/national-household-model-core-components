package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum88 {
    @SavEnumMapping({"does not apply", "no answer"})
    __MISSING,
    @SavEnumMapping("none working and none retired")
    NoneWorkingAndNoneRetired,
    @SavEnumMapping("1 or more work full time")
    _1OrMoreWorkFullTime,
    @SavEnumMapping("1 or more work part time")
    _1OrMoreWorkPartTime,
    @SavEnumMapping("none working, one or more retired")
    NoneWorking_OneOrMoreRetired,

}
