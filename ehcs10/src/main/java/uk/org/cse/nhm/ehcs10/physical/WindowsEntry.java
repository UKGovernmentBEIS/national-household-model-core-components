package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum2008;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface WindowsEntry extends SurveyEntry {
	@SavVariableMapping("FEXWN2LV")
	public Integer getBACK_Leave();

	@SavVariableMapping("FEXWN1RN")
	public Integer getFRONT_Replace();

	@SavVariableMapping("FEXWN1PA")
	public Integer getFRONT_RepaintReputty();

	@SavVariableMapping("FEXWN2AG")
	public Integer getBACK_Age();

	@SavVariableMapping("FEXWN2TM")
	public Integer getBACK_ReplacementPeriod();

	@SavVariableMapping("FEXWN1TM")
	public Integer getFront_ReplacementPeriod();

	@SavVariableMapping("FEXWN2NO")
	public Integer getBACK_Number();

	@SavVariableMapping("FEXWN1EA")
	public Integer getFRONT_EaseSashes();

	@SavVariableMapping("FEXWN1NO")
	public Integer getFRONT_Number();

	@SavVariableMapping("FEXWN2RP")
	public Integer getBACK_RepairReplaceSashMember();

	@SavVariableMapping("FEXWN2PA")
	public Integer getBACK_RepaintReputty();

	@SavVariableMapping("FEXWN1LV")
	public Integer getFRONT_Leave();

	@SavVariableMapping("FEXWN2EA")
	public Integer getBACK_EaseSashes();

	@SavVariableMapping("FEXWN2RN")
	public Integer getBACK_Replace();

	@SavVariableMapping("FEXWN1RP")
	public Integer getFRONT_RepairReplaceSashMember();

	@SavVariableMapping("FEXWN1AG")
	public Integer getFRONT_Age();

	@SavVariableMapping("FEXWN1FL")
	public Enum10 getFRONT_Faults();

	@SavVariableMapping("FEXWN2UR")
	public Enum10 getBACK_Urgent();

	@SavVariableMapping("FEXWN1UR")
	public Enum10 getFRONT_Urgent();

	@SavVariableMapping("TYPE")
	public Enum2008 getTypeOfWindow();

	@SavVariableMapping("FEXWN2FL")
	public Enum10 getBACK_Faults();

}

