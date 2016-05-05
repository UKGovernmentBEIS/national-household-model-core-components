package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1060 {
	@SavEnumMapping({"Unknown", "Question Not Applicable"})
	__MISSING,

	@SavEnumMapping("Replace")
	Replace,

	@SavEnumMapping("Minor repair")
	MinorRepair,

	@SavEnumMapping("Install")
	Install,

	@SavEnumMapping("Major repair")
	MajorRepair,

	@SavEnumMapping("None")
	None,

}
