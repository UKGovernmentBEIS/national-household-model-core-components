package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface IdentityEntry extends SurveyEntry {
	@SavVariableMapping("HRP")
	public Integer getPersonNumberOfHRP();

	@SavVariableMapping("CAMEY2")
	public Integer getYearLastArrivedInTheUK();

	@SavVariableMapping("ETHMX")
	public Integer getEthnicity_IfMixed_();

	@SavVariableMapping("RESMTH")
	public Integer getMonthsAtThisAddress();

	@SavVariableMapping("RESTME2")
	public Integer getYearsAtThisAddress();

	@SavVariableMapping("ETHWH")
	public Integer getEthnicity_IfWhite_();

	@SavVariableMapping("PROXYNUM")
	public Integer getPersonNoOfRespondent();

	@SavVariableMapping("ETHAS")
	public Integer getEthnicity_IfAsian_AsianBritish_();

	@SavVariableMapping("ETHBL")
	public Integer getEthnicity_IfBlack_BlackBritish_();

	@SavVariableMapping("NTIDIRSH")
	public Integer getIrish();

	@SavVariableMapping("RELIG")
	public Integer getReligion();

	@SavVariableMapping("CAMEYR")
	public Integer getYearArrivedInUK();

	@SavVariableMapping("PERSNO")
	public Integer getPersonIdentifier();

	@SavVariableMapping("ETH01")
	public Integer getEthnicGroup();

	@SavVariableMapping("RELIGE")
	public Double getReligion_RELIGE();

	@SavVariableMapping("ETHE")
	public Double getEthnicGroup_ETHE();

	@SavVariableMapping("NTIDNI")
	public Double getNorthernIrish();

	@SavVariableMapping("NTIDOTHR")
	public Enum69 getOtherNationalIdentity();

	@SavVariableMapping("DUALINT")
	public Enum385 getIntroductionToDualNationality();

	@SavVariableMapping("OTHNAT")
	public Enum69 getDualNationality();

	@SavVariableMapping("NTIDSCOT")
	public Enum69 getScottish();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("DVHLONG1")
	public Enum486 getTimeAtThisAddress();

	@SavVariableMapping("M3CRY")
	public Enum487 getWhereLiving3MonthsAgo();

	@SavVariableMapping("PERSPROX")
	public Enum488 getInterviewInPerson_Proxy();

	@SavVariableMapping("RESBBY")
	public Enum69 getBabyBornInLast3Mths();

	@SavVariableMapping("NTIDWLSH")
	public Enum69 getWelsh();

	@SavVariableMapping("OYCRY")
	public Enum491 getWhereLiving12MonthsAgo();

	@SavVariableMapping("CONTUK")
	public Enum69 getLivedInUKContinuously();

	@SavVariableMapping("DVRESTME")
	public Enum493 getYearsAtThisAddress_DVRESTME();

	@SavVariableMapping("NTIDBRIT")
	public Enum69 getBritish();

	@SavVariableMapping("CRY01")
	public Enum495 getCountryOfBirth();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("CAMEMT")
	public Enum386 getMonthArrivedInTheUK();

	@SavVariableMapping("OYEQM3")
	public Enum499 getLivingAtSameAddressAsM3Cry12MonthsAgo();

	@SavVariableMapping("NTIDENGH")
	public Enum69 getEnglish();

}

