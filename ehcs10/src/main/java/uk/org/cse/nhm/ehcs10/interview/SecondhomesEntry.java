package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum251;
import uk.org.cse.nhm.ehcs10.interview.types.Enum921;
import uk.org.cse.nhm.ehcs10.interview.types.Enum923;
import uk.org.cse.nhm.ehcs10.interview.types.Enum935;
import uk.org.cse.nhm.ehcs10.interview.types.Enum939;
import uk.org.cse.nhm.ehcs10.interview.types.Enum975;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface SecondhomesEntry extends SurveyEntry {
	@SavVariableMapping("DSECPROP")
	public Integer getCalculatedNo_OfSecondProperties();

	@SavVariableMapping("EXPRPNUM")
	public Integer getNo_ResidentialPropertiesOwnedElsewhere();

	@SavVariableMapping("NUMASK")
	public Integer getNumberOfLoopsAsked();

	@SavVariableMapping("SECRNTNO")
	public Integer getNo_SecondHomesRentedFromLandlord();

	@SavVariableMapping("WHYS5AWY")
	public Enum69 getAwayFromHome_Working();

	@SavVariableMapping("WHYS2RET")
	public Enum69 getRetirementHome();

	@SavVariableMapping("WHYS4RET")
	public Enum69 getRetirementHome_WHYS4RET();

	@SavVariableMapping("WHYS3OAY")
	public Enum69 getAwayFromHome_Other();

	@SavVariableMapping("WHSEC4")
	public Enum921 getLocationOfSecondHome();

	@SavVariableMapping("WHYS2OTR")
	public Enum69 getOtherReason();

	@SavVariableMapping("WHSECAB2")
	public Enum923 getCountryOfSecondHome();

	@SavVariableMapping("WHYS5MAR")
	public Enum69 getMaritalBreakdown();

	@SavVariableMapping("WHYS1PRV")
	public Enum69 getPreviouslyMainHome();

	@SavVariableMapping("WHYS5PRV")
	public Enum69 getPreviouslyMainHome_WHYS5PRV();

	@SavVariableMapping("WHYS4INV")
	public Enum69 getLong_TermInvestment_SourceOfIncome();

	@SavVariableMapping("WHYS1AWY")
	public Enum69 getAwayFromHome_Working_WHYS1AWY();

	@SavVariableMapping("WHYS3INV")
	public Enum69 getLong_TermInvestment_SourceOfIncome_WHYS3INV();

	@SavVariableMapping("WHYS5RET")
	public Enum69 getRetirementHome_WHYS5RET();

	@SavVariableMapping("WHSEC5")
	public Enum921 getLocationOfSecondHome_WHSEC5();

	@SavVariableMapping("WHYS4OTR")
	public Enum69 getOtherReason_WHYS4OTR();

	@SavVariableMapping("WHYS5HOL")
	public Enum69 getHolidayHome();

	@SavVariableMapping("WHSECAB5")
	public Enum923 getCountryOfSecondHome_WHSECAB5();

	@SavVariableMapping("EXTYPE5")
	public Enum935 getWhatIsPropertyUsedFor_();

	@SavVariableMapping("WHYS4OAY")
	public Enum69 getAwayFromHome_Other_WHYS4OAY();

	@SavVariableMapping("WHSEC2")
	public Enum921 getLocationOfSecondHome_WHSEC2();

	@SavVariableMapping("WHYS3AWY")
	public Enum69 getAwayFromHome_Working_WHYS3AWY();

	@SavVariableMapping("OWNRENT2")
	public Enum939 getOwnOrRentFromSomeoneElse_();

	@SavVariableMapping("EXTYPE2")
	public Enum935 getWhatIsPropertyUsedFor__EXTYPE2();

	@SavVariableMapping("OWNRENT4")
	public Enum939 getOwnOrRentFromSomeoneElse__OWNRENT4();

	@SavVariableMapping("SECRENT")
	public Enum69 getRentsSecondHomeFromLandlord();

	@SavVariableMapping("OWNRENT3")
	public Enum939 getOwnOrRentFromSomeoneElse__OWNRENT3();

	@SavVariableMapping("WHYS4MAR")
	public Enum69 getMaritalBreakdown_WHYS4MAR();

	@SavVariableMapping("WHYS1INV")
	public Enum69 getLong_TermInvestment_SourceOfIncome_WHYS1INV();

	@SavVariableMapping("WHYS3MAR")
	public Enum69 getMaritalBreakdown_WHYS3MAR();

	@SavVariableMapping("WHYS2AWY")
	public Enum69 getAwayFromHome_Working_WHYS2AWY();

	@SavVariableMapping("WHSEC3")
	public Enum921 getLocationOfSecondHome_WHSEC3();

	@SavVariableMapping("WHYS3PRV")
	public Enum69 getPreviouslyMainHome_WHYS3PRV();

	@SavVariableMapping("SECINTR1")
	public Enum251 getFirst_NextMostRecentProperty();

	@SavVariableMapping("SECINTR2")
	public Enum251 getNextMostRecentPropertyPurchased_Rented();

	@SavVariableMapping("WHYS2MAR")
	public Enum69 getMaritalBreakdown_WHYS2MAR();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("EXTYPE4")
	public Enum935 getWhatIsPropertyUsedFor__EXTYPE4();

	@SavVariableMapping("WHSEC1")
	public Enum921 getLocationOfSecondHome_WHSEC1();

	@SavVariableMapping("EXTYPE3")
	public Enum935 getWhatIsPropertyUsedFor__EXTYPE3();

	@SavVariableMapping("WHSECAB3")
	public Enum923 getCountryOfSecondHome_WHSECAB3();

	@SavVariableMapping("WHYS1RET")
	public Enum69 getRetirementHome_WHYS1RET();

	@SavVariableMapping("WHYS4AWY")
	public Enum69 getAwayFromHome_Working_WHYS4AWY();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("WHYS3OTR")
	public Enum69 getOtherReason_WHYS3OTR();

	@SavVariableMapping("WHYS5INV")
	public Enum69 getLong_TermInvestment_SourceOfIncome_WHYS5INV();

	@SavVariableMapping("OWNRENT5")
	public Enum939 getOwnOrRentFromSomeoneElse__OWNRENT5();

	@SavVariableMapping("WHYS4PRV")
	public Enum69 getPreviouslyMainHome_WHYS4PRV();

	@SavVariableMapping("WHYS1OTR")
	public Enum69 getOtherReason_WHYS1OTR();

	@SavVariableMapping("WHYS2HOL")
	public Enum69 getHolidayHome_WHYS2HOL();

	@SavVariableMapping("WHYS5OTR")
	public Enum69 getOtherReason_WHYS5OTR();

	@SavVariableMapping("WHYS1MAR")
	public Enum69 getMaritalBreakdown_WHYS1MAR();

	@SavVariableMapping("WHYS4HOL")
	public Enum69 getHolidayHome_WHYS4HOL();

	@SavVariableMapping("WHYS2INV")
	public Enum69 getLong_TermInvestment_SourceOfIncome_WHYS2INV();

	@SavVariableMapping("WHSECAB1")
	public Enum923 getCountryOfSecondHome_WHSECAB1();

	@SavVariableMapping("WHYS5OAY")
	public Enum69 getAwayFromHome_Other_WHYS5OAY();

	@SavVariableMapping("WHYS1OAY")
	public Enum69 getAwayFromHome_Other_WHYS1OAY();

	@SavVariableMapping("WHYS2OAY")
	public Enum69 getAwayFromHome_Other_WHYS2OAY();

	@SavVariableMapping("SECINTR5")
	public Enum975 getNextMostRecentPropertyPurchased_Rented_SECINTR5();

	@SavVariableMapping("WHYS2PRV")
	public Enum69 getPreviouslyMainHome_WHYS2PRV();

	@SavVariableMapping("SECINTR3")
	public Enum975 getNextMostRecentPropertyPurchased_Rented_SECINTR3();

	@SavVariableMapping("WHYS3HOL")
	public Enum69 getHolidayHome_WHYS3HOL();

	@SavVariableMapping("PROPEXT")
	public Enum69 getOwnsResidentialPropertyElsewhere();

	@SavVariableMapping("SECINTR4")
	public Enum975 getNextMostRecentPropertyPurchased_Rented_SECINTR4();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("OWNRENT1")
	public Enum939 getOwn_RentFromSomeoneElse_();

	@SavVariableMapping("WHYS3RET")
	public Enum69 getRetirementHome_WHYS3RET();

	@SavVariableMapping("WHSECAB4")
	public Enum923 getCountryOfSecondHome_WHSECAB4();

	@SavVariableMapping("EXTYPE1")
	public Enum935 getWhatIsPropertyUsedFor__EXTYPE1();

	@SavVariableMapping("WHYS1HOL")
	public Enum69 getHolidayHome_WHYS1HOL();

}

