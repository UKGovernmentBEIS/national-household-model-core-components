package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1655 {
	@SavEnumMapping({"Unknown", "Question Not Applicable", "Section Not Applicable"})
	__MISSING,

	@SavEnumMapping("Services only")
	ServicesOnly,

	@SavEnumMapping("Non-residential only")
	Non_ResidentialOnly,

	@SavEnumMapping("Dwelling and non-residential")
	DwellingAndNon_Residential,

	@SavEnumMapping("Other")
	Other,

	@SavEnumMapping("Dwelling only")
	DwellingOnly,

	@SavEnumMapping("Dwelling and services")
	DwellingAndServices,

	@SavEnumMapping("Dwelling and void")
	DwellingAndVoid,

}
