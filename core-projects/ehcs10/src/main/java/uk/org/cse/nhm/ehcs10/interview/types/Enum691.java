package uk.org.cse.nhm.ehcs10.interview.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum691 {
	@SavEnumMapping("repayment (interest & part of loan)")
	Repayment_Interest_PartOfLoan_,

	@SavEnumMapping("interest only, no linked investment")
	InterestOnly_NoLinkedInvestment,

	@SavEnumMapping("does not apply")
	DoesNotApply,

	@SavEnumMapping("another type")
	AnotherType,

	@SavEnumMapping("both endowment & repayment")
	BothEndowment_Repayment,

	@SavEnumMapping("no answer")
	NoAnswer,

	@SavEnumMapping("other interest only, with linked investments")
	OtherInterestOnly_WithLinkedInvestments,

	@SavEnumMapping("endowment (interest only)")
	Endowment_InterestOnly_,

}
