package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum84 {
	@SavEnumMapping("one person aged 60 or over")
	OnePersonAged60OrOver,

	@SavEnumMapping("does not apply")
	DoesNotApply,

	@SavEnumMapping("couple, no dependent child(ren) aged 60 or over")
	Couple_NoDependentChild_Ren_Aged60OrOver,

	@SavEnumMapping("other multi-person households")
	OtherMulti_PersonHouseholds,

	@SavEnumMapping("couple, no dependent child(ren) under 60")
	Couple_NoDependentChild_Ren_Under60,

	@SavEnumMapping("couple with dependent child(ren)")
	CoupleWithDependentChild_Ren_,

	@SavEnumMapping("no answer")
	NoAnswer,

	@SavEnumMapping("lone parent with dependent child(ren)")
	LoneParentWithDependentChild_Ren_,

	@SavEnumMapping("one person under 60")
	OnePersonUnder60,

}
