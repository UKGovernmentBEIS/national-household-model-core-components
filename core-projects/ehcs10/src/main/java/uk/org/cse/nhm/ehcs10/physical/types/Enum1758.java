package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1758 {
	@SavEnumMapping({"Unknown", "Section N/A", "Question Not Applicable"})
	__MISSING,

	@SavEnumMapping("Rooms(s) with permanent stairs")
	Rooms_S_WithPermanentStairs,

	@SavEnumMapping("No boarding or partial boarding")
	NoBoardingOrPartialBoarding,

	@SavEnumMapping("No loft - flat or very shallow pitch")
	NoLoft_FlatOrVeryShallowPitch,

	@SavEnumMapping("Fully boarded")
	FullyBoarded,

}
