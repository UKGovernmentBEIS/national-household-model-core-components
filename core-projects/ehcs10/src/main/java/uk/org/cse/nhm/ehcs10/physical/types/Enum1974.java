package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1974 {
	@SavEnumMapping({"Unknown", "section not applicable", "question not applicable"})
	__MISSING,

	@SavEnumMapping("Extreme risk")
	ExtremeRisk,

	@SavEnumMapping("lower than average risk")
	LowerThanAverageRisk,

	@SavEnumMapping("average risk")
	AverageRisk,

	@SavEnumMapping("Higher than average risk")
	HigherThanAverageRisk,

}
