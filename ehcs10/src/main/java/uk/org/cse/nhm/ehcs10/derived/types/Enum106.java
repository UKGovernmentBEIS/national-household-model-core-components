package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum106 {
    @SavEnumMapping({ "no loft", "not applicable - no roof directly above" })
    __MISSING,

    @SavEnumMapping("50 up to 99mm")
    _50UpTo99Mm,

    @SavEnumMapping("less than 50mm")
    LessThan50Mm,

    @SavEnumMapping("100 up to 149mm")
    _100UpTo149Mm,

    @SavEnumMapping("none")
    None,

    @SavEnumMapping("150 up to 199mm")
    _150UpTo199Mm,

    @SavEnumMapping("200mm or more")
    _200MmOrMore,

}
