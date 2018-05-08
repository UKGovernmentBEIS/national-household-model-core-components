package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum99 {
    @SavEnumMapping({"does not apply", "no answer"})
    __MISSING,
    @SavEnumMapping("one above standard")
    OneAboveStandard,
    @SavEnumMapping("two or more above standard")
    TwoOrMoreAboveStandard,
    @SavEnumMapping("one below standard")
    OneBelowStandard,
    @SavEnumMapping("at standard")
    AtStandard,
    @SavEnumMapping("two or more below standard")
    TwoOrMoreBelowStandard,

}
