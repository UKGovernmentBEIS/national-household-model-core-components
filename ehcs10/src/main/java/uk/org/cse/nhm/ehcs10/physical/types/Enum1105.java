package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1105 {
	@SavEnumMapping({"Unknown", "Question Not Applicable"})
	__MISSING,

	@SavEnumMapping("Under 1.5 metres")
	Under1_5Metres,

	@SavEnumMapping("Between 1.5 metres and 3 metres")
	Between1_5MetresAnd3Metres,

	@SavEnumMapping("Over 3 metres")
	Over3Metres,

}
