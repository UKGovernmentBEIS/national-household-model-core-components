package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1289;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface DoorsEntry extends SurveyEntry {
	@SavVariableMapping("FEXDF1RN")
	public Integer getFRONT_Replace();

	@SavVariableMapping("FEXDF1EA")
	public Integer getFRONT_EaseReplaceAdjust();

	@SavVariableMapping("FEXDF1AG")
	public Integer getFRONT_Age();

	@SavVariableMapping("FEXDF1PA")
	public Integer getFRONT_Paint();

	@SavVariableMapping("FEXDF1TM")
	public Integer getFRONT_ReplacementPeriod();

	@SavVariableMapping("FEXDF2RN")
	public Integer getBACK_Replace();

	@SavVariableMapping("FEXDF1LV")
	public Integer getFRONT_Leave();

	@SavVariableMapping("FEXDF1NO")
	public Integer getFRONT_Number();

	@SavVariableMapping("FEXDF2TM")
	public Integer getBACK_ReplacementPeriod();

	@SavVariableMapping("FEXDF2RP")
	public Integer getBACK_RepairGlaze();

	@SavVariableMapping("FEXDF2NO")
	public Integer getBACK_Number();

	@SavVariableMapping("FEXDF2PA")
	public Integer getBACK_Paint();

	@SavVariableMapping("FEXDF2EA")
	public Integer getBACK_EaseReplaceAdjust();

	@SavVariableMapping("FEXDF1RP")
	public Integer getFRONT_RepairGlaze();

	@SavVariableMapping("FEXDF2LV")
	public Integer getBACK_Leave();

	@SavVariableMapping("FEXDF2AG")
	public Integer getBACK_Age();

	@SavVariableMapping("FEXDF2FL")
	public Enum10 getBACK_Faults();

	@SavVariableMapping("FEXDF1FL")
	public Enum10 getFRONT_Faults();

	@SavVariableMapping("TYPE")
	public Enum1289 getTypeOfDoor();

	@SavVariableMapping("FEXDF2UR")
	public Enum10 getBACK_Urgent();

	@SavVariableMapping("FEXDF1UR")
	public Enum10 getFRONT_Urgent();

}

