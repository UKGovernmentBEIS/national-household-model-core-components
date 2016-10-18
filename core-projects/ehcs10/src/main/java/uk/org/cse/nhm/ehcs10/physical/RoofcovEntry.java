package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1691;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1693;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface RoofcovEntry extends SurveyEntry {
	@SavVariableMapping("FEXRC2RN")
	public Integer getBACK_Renew();

	@SavVariableMapping("FEXRC1TM")
	public Integer getFRONT_ReplacementPeriod();

	@SavVariableMapping("FEXRC2TM")
	public Integer getBACK_ReplacementPeriod();

	@SavVariableMapping("FEXRC2LV")
	public Integer getBACK_Leave();

	@SavVariableMapping("FEXRC1RN")
	public Integer getFRONT_Renew();

	@SavVariableMapping("FEXRC1TE")
	public Integer getFRONT_TenthsOfArea();

	@SavVariableMapping("FEXRC1IS")
	public Integer getFRONT_IsolatedRepairs();

	@SavVariableMapping("FEXRC2AG")
	public Integer getBACK_Age();

	@SavVariableMapping("FEXRC2IS")
	public Integer getBACK_IsolatedRepairs();

	@SavVariableMapping("FEXRC1AG")
	public Integer getFRONT_Age();

	@SavVariableMapping("FEXRC1LV")
	public Integer getFRONT_Leave();

	@SavVariableMapping("FEXRC2TE")
	public Integer getBACK_TenthsOfArea();

	@SavVariableMapping("FEXRC1FL")
	public Enum1691 getFRONT_Faults();

	@SavVariableMapping("FEXRC1UR")
	public Enum10 getFRONT_Urgent_();

	@SavVariableMapping("TYPE")
	public Enum1693 getTypeOfRoofCovering();

	@SavVariableMapping("FEXRC2FL")
	public Enum1691 getBACK_Faults();

	@SavVariableMapping("FEXRC2UR")
	public Enum10 getBACK_Urgent_();

}

