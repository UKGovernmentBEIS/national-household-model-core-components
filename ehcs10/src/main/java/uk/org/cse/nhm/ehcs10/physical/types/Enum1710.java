package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1710 {
	@SavEnumMapping({"Unknown", "Question Not Applicable"})
	__MISSING,

	@SavEnumMapping("Electric ceiling/underfloor")
	ElectricCeiling_Underfloor,

	@SavEnumMapping("Room heaters")
	RoomHeaters,

	@SavEnumMapping("Communal/CHP")
	Communal_CHP,

	@SavEnumMapping("Central heating (wet with rads)")
	CentralHeating_WetWithRads_,

	@SavEnumMapping("Warm air")
	WarmAir,

	@SavEnumMapping("Storage heaters")
	StorageHeaters,

}
