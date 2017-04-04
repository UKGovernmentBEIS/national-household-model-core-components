package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1691;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1706;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface RoofstruEntry extends SurveyEntry {
	@SavVariableMapping("FEXRS2LV")
	public Integer getBACK_Leave();

	@SavVariableMapping("FEXRS1TM")
	public Integer getFRONT_ReplacementPeriod();

	@SavVariableMapping("FEXRS1TE")
	public Integer getFRONT_TenthsOfArea();

	@SavVariableMapping("FEXRS2AG")
	public Integer getBACK_Age();

	@SavVariableMapping("FEXRS2ST")
	public Integer getBACK_Strengthen();

	@SavVariableMapping("FEXRS1ST")
	public Integer getFRONT_Strengthen();

	@SavVariableMapping("FEXRS1AG")
	public Integer getFRONT_Age();

	@SavVariableMapping("FEXRS2TM")
	public Integer getBACK_ReplacementPeriod();

	@SavVariableMapping("FEXRS2RN")
	public Integer getBACK_Replace();

	@SavVariableMapping("FEXRS1LV")
	public Integer getFRONT_Leave();

	@SavVariableMapping("FEXRS1RN")
	public Integer getFRONT_Replace();

	@SavVariableMapping("FEXRS2TE")
	public Integer getBACK_TenthsOfArea();

	@SavVariableMapping("FEXRS1FL")
	public Enum1691 getFRONT_Faults();

	@SavVariableMapping("FEXRS2FL")
	public Enum1691 getBACK_Faults();

	@SavVariableMapping("FEXRS1UR")
	public Enum10 getFRONT_Urgent_();

	@SavVariableMapping("TYPE")
	public Enum1706 getTypeOfRoofStructure();

	@SavVariableMapping("FEXRS2UR")
	public Enum10 getBACK_Urgent_();

}

