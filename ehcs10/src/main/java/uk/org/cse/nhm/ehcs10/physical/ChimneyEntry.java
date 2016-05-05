package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1216;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface ChimneyEntry extends SurveyEntry {
	@SavVariableMapping("FEXCS1TM")
	public Integer getFRONT_ReplacementPeriod();

	@SavVariableMapping("FEXCS2AG")
	public Integer getBACK_Age();

	@SavVariableMapping("FEXCS1AG")
	public Integer getFRONT_Age();

	@SavVariableMapping("FEXCS1RN")
	public Integer getFRONT_Rebuild();

	@SavVariableMapping("FEXCS1RE")
	public Integer getFRONT_RepointRefixPot();

	@SavVariableMapping("FEXCS1NO")
	public Integer getFRONT_Number();

	@SavVariableMapping("FEXCS2TM")
	public Integer getBACK_ReplacementPeriod();

	@SavVariableMapping("FEXCS2RN")
	public Integer getBACK_Rebuild();

	@SavVariableMapping("FEXCS1PT")
	public Integer getFRONT_PartRebuild();

	@SavVariableMapping("FEXCS1LV")
	public Integer getFRONT_Leave();

	@SavVariableMapping("FEXCS2LV")
	public Integer getBACK_Leave();

	@SavVariableMapping("FEXCS2PT")
	public Integer getBACK_PartRebuild();

	@SavVariableMapping("FEXCS2NO")
	public Integer getBACK_Number();

	@SavVariableMapping("FEXCS2RE")
	public Integer getBACK_RepointRefixPot();

	@SavVariableMapping("FEXCS2FL")
	public Enum10 getBACK_Faults();

	@SavVariableMapping("FEXCS2PR")
	public Enum10 getBACK_Present();

	@SavVariableMapping("FEXCS1FL")
	public Enum10 getFRONT_Faults();

	@SavVariableMapping("FEXCS1PR")
	public Enum10 getFRONT_Present();

	@SavVariableMapping("FEXCS2UR")
	public Enum10 getBACK_Urgent();

	@SavVariableMapping("FEXCS1UR")
	public Enum10 getFRONT_Urgent();

	@SavVariableMapping("TYPE")
	public Enum1216 getTypeOfChimney();

}

