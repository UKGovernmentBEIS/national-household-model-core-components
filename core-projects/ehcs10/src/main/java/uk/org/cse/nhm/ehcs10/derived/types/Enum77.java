package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum77 {
	@SavEnumMapping({"does not apply", "no answer"})
	__MISSING,

	@SavEnumMapping("two or more families")
	TwoOrMoreFamilies,

	@SavEnumMapping("one male")
	OneMale,

	@SavEnumMapping("lone parent with dependent child(ren) only")
	LoneParentWithDependentChild_Ren_Only,

	@SavEnumMapping("couple with independent child(ren) only")
	CoupleWithIndependentChild_Ren_Only,

	@SavEnumMapping("couple with dependent child(ren) only")
	CoupleWithDependentChild_Ren_Only,

	@SavEnumMapping("lone parent with dependent and independent children")
	LoneParentWithDependentAndIndependentChildren,

	@SavEnumMapping("couple with no child(ren)")
	CoupleWithNoChild_Ren_,

	@SavEnumMapping("couple with dependent and independent children")
	CoupleWithDependentAndIndependentChildren,

	@SavEnumMapping("lone parent with independent child(ren) only")
	LoneParentWithIndependentChild_Ren_Only,

	@SavEnumMapping("one female")
	OneFemale,

	@SavEnumMapping("lone person sharing with other lone persons")
	LonePersonSharingWithOtherLonePersons,

}
