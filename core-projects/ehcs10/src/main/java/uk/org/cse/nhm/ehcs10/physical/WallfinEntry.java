package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1995;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface WallfinEntry extends SurveyEntry {
	@SavVariableMapping("FEXWF2TM")
	public Integer getBACK_ReplacementPeriod();

	@SavVariableMapping("FEXWF2RP")
	public Integer getBACK_IsolatedRepairs();

	@SavVariableMapping("FEXWF2PA")
	public Integer getBACK_Paint();

	@SavVariableMapping("FEXWF1RP")
	public Integer getFRONT_IsolatedRepairs();

	@SavVariableMapping("FEXWF1AG")
	public Integer getFRONT_Age();

	@SavVariableMapping("FEXWF1TM")
	public Integer getFRONT_ReplacementPeriod();

	@SavVariableMapping("FEXWF1RN")
	public Integer getFRONT_RenewRepoint();

	@SavVariableMapping("FEXWF2RN")
	public Integer getBACK_RenewRepoint();

	@SavVariableMapping("FEXWF2AG")
	public Integer getBACK_Age();

	@SavVariableMapping("FEXWF2RE")
	public Integer getBACK_Render();

	@SavVariableMapping("FEXWF1LV")
	public Integer getFRONT_Leave();

	@SavVariableMapping("FEXWF2TE")
	public Integer getBACK_TenthsOfArea();

	@SavVariableMapping("FEXWF2LV")
	public Integer getBACK_Leave();

	@SavVariableMapping("FEXWF1TE")
	public Integer getFRONT_TenthsOfArea();

	@SavVariableMapping("FEXWF1RE")
	public Integer getFRONT_Render();

	@SavVariableMapping("FEXWF1PA")
	public Integer getFRONT_Paint();

	@SavVariableMapping("TYPE")
	public Enum1995 getTypeOfWallFinish();

	@SavVariableMapping("FEXWF2UR")
	public Enum10 getBACK_Urgent();

	@SavVariableMapping("FEXWF1FL")
	public Enum10 getFRONT_Faults();

	@SavVariableMapping("FEXWF2FL")
	public Enum10 getBACK_Faults();

	@SavVariableMapping("FEXWF1UR")
	public Enum10 getFRONT_Urgent();

}

