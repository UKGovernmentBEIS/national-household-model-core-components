package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum25 {
    @SavEnumMapping("long term (6 months or more)")
    LongTerm_6MonthsOrMore_,
    @SavEnumMapping("short term (less than 6 months)")
    ShortTerm_LessThan6Months_,
    @SavEnumMapping("not applicable - occupied")
    NotApplicable_Occupied,
    @SavEnumMapping("vacant but unknown duration")
    VacantButUnknownDuration,

}
