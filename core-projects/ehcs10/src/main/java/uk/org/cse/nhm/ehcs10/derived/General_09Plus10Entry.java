package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.ehcs10.derived.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface General_09Plus10Entry extends SurveyEntry {
	@SavVariableMapping("~AAGPD\\d+")
	public Integer getDwellWeight_PairedCases2009_10And2010_11();

	@SavVariableMapping("~AAGPH\\d+")
	public Double getHhldWeight_PairedCases2009_10And2010_11();

    @SavVariableMapping("IMD1010")
    public Enum21 getIMD2010DecileRankingOfAreas_LowerLayerSOA_();

    @SavVariableMapping("TENURE4X")
    public Enum22 getTenure();

    @SavVariableMapping("TENURE8X")
    public Enum23 getTenure_TENURE8X();

    @SavVariableMapping("GOREHCS")
    public Enum24 getRegion();

    @SavVariableMapping("VACLNGTH")
    public Enum25 getLengthOfVacancy();

    @SavVariableMapping("TENURE2X")
    public Enum26 getTenure_TENURE2X();

    @SavVariableMapping("REGION3X")
    public Enum27 getOverallRegionOfEngland();

    @SavVariableMapping("RUMORPH")
    public Enum28 getRurality_Morphology_COA_();

    @SavVariableMapping("VACANTX")
    public Enum29 getTypeOfVacancy();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

}
