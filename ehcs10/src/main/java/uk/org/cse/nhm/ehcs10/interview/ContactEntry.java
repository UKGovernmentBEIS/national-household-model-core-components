package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum270;
import uk.org.cse.nhm.ehcs10.interview.types.Enum271;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface ContactEntry extends SurveyEntry {
	@SavVariableMapping("HMOREM")
	public Integer getHMOHousehold();

	@SavVariableMapping("NUMHRES")
	public Enum270 getWhetherMoreThanOneHousehold();

	@SavVariableMapping("RESP")
	public Enum271 getWhoInterviewed();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("WILLING")
	public Enum69 getWillingToAgreeToSurveyorVisit();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

}

