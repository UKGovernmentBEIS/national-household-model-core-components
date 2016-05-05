package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum154 {
	@SavEnumMapping({"no boiler"})
	__MISSING,

	@SavEnumMapping("standard boiler (floor or wall)")
	StandardBoiler_FloorOrWall_,

	@SavEnumMapping("condensing boiler")
	CondensingBoiler,

	@SavEnumMapping("combination boiler")
	CombinationBoiler,

	@SavEnumMapping("back boiler (to fire or stove)")
	BackBoiler_ToFireOrStove_,

	@SavEnumMapping("condensing-combination boiler")
	Condensing_CombinationBoiler,

}
