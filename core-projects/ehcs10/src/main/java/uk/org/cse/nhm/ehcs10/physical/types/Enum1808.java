package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1808 {
    @SavEnumMapping({"Unknown", "Question Not Applicable", "Section Not Applicable"})
    __MISSING,
    @SavEnumMapping("1 Step")
    _1Step,
    @SavEnumMapping("2 Step")
    _2Step,
    @SavEnumMapping("3 or more steps")
    _3OrMoreSteps,
    @SavEnumMapping("No step but slope >1:20")
    NoStepButSlopeGreaterThan1_20,
    @SavEnumMapping("Level access")
    LevelAccess,

}
