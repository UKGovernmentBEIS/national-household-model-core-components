package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum59 {
	@SavEnumMapping({"does not apply", "no answer"})
	__MISSING,

	@SavEnumMapping("one male")
	OneMale,

	@SavEnumMapping("other multi-person households")
	OtherMulti_PersonHouseholds,

	@SavEnumMapping("one person (sex unknown)")
	OnePerson_SexUnknown_,

	@SavEnumMapping("one female")
	OneFemale,

	@SavEnumMapping("couple with dependent child(ren)")
	CoupleWithDependentChild_Ren_,

	@SavEnumMapping("lone parent with dependent child(ren)")
	LoneParentWithDependentChild_Ren_,

	@SavEnumMapping("couple, no dependent child(ren)")
	Couple_NoDependentChild_Ren_,

}
