package uk.org.cse.nhm.ehcs10.interview.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum255 {
	@SavEnumMapping("neither agree nor disagree")
	NeitherAgreeNorDisagree,

	@SavEnumMapping("does not apply")
	DoesNotApply,

	@SavEnumMapping("no opinion (Spontaneous only)")
	NoOpinion_SpontaneousOnly_,

	@SavEnumMapping("tend to agree")
	TendToAgree,

	@SavEnumMapping("tend to disagree")
	TendToDisagree,

	@SavEnumMapping("no answer")
	NoAnswer,

	@SavEnumMapping("strongly disagree")
	StronglyDisagree,

	@SavEnumMapping("strongly agree")
	StronglyAgree,

}
