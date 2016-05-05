package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1202 {
	@SavEnumMapping({"Unknown", "Question Not Applicable"})
	__MISSING,

	@SavEnumMapping("Very exposed")
	VeryExposed,

	@SavEnumMapping("Slightly exposed")
	SlightlyExposed,

	@SavEnumMapping("Exposed")
	Exposed,

	@SavEnumMapping("Not exposed")
	NotExposed,

}
