package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum126 {
    @SavEnumMapping({"no loft", "not applicable - no roof directly above"})
    __MISSING,
    @SavEnumMapping("150mm or more")
    _150MmOrMore,
    @SavEnumMapping("100 up to 150mm")
    _100UpTo150Mm,
    @SavEnumMapping("none")
    None,
    @SavEnumMapping("less than 100mm")
    LessThan100Mm,

}
