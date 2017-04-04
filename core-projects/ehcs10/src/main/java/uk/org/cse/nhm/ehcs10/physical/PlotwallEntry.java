package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1683;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1685;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface PlotwallEntry extends SurveyEntry {
	@SavVariableMapping("FEXBW1RN")
	public Integer getFRONT_Replace_Metres_();

	@SavVariableMapping("FEXBW2DE")
	public Integer getBACK_Demolish_Metres_();

	@SavVariableMapping("FEXBW2RP")
	public Integer getBACK_Repair_Metres_();

	@SavVariableMapping("FEXBW1RP")
	public Integer getFRONT_Repair_Metres_();

	@SavVariableMapping("FEXBW2TM")
	public Integer getBACK_ReplacementPeriod();

	@SavVariableMapping("FEXBW1DE")
	public Integer getFRONT_Demolish_Metres_();

	@SavVariableMapping("FEXBW2RN")
	public Integer getBACK_Replace_Metres_();

	@SavVariableMapping("FEXBW1TM")
	public Integer getFRONT_ReplacementPeriod();

	@SavVariableMapping("FEXPLTYP")
	public Enum1683 getTypeOfPlot();

	@SavVariableMapping("FEXBW2PR")
	public Enum10 getBACK_Present();

	@SavVariableMapping("TYPE")
	public Enum1685 getTypeOfPlotWall();

	@SavVariableMapping("FEXBW1PR")
	public Enum10 getFRONT_Present();

	@SavVariableMapping("FEXBW2FL")
	public Enum10 getBACK_Faults();

	@SavVariableMapping("FEXBW2UR")
	public Enum10 getBACK_Urgent_();

	@SavVariableMapping("FEXBW1UR")
	public Enum10 getFRONT_Urgent_();

	@SavVariableMapping("FEXBW1FL")
	public Enum10 getFRONT_Faults();

}

