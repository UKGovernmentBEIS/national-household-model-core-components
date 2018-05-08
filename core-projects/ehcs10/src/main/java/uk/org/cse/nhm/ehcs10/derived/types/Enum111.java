package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum111 {
    @SavEnumMapping({"meets thermal comfort criterion"})
    __MISSING,
    @SavEnumMapping("failed on heating measures only")
    FailedOnHeatingMeasuresOnly,
    @SavEnumMapping("failed on heating and insulation measures")
    FailedOnHeatingAndInsulationMeasures,
    @SavEnumMapping("failed on insulation measures only")
    FailedOnInsulationMeasuresOnly,

}
