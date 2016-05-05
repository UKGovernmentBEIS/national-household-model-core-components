package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1655;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1656;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1657;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface NumflatsEntry extends SurveyEntry {
	@SavVariableMapping("FNOLOWES")
	public String getLevelOfLowestFlat();

	@SavVariableMapping("FNOVACNT")
	public Integer getOTHERFLATSINMODULEApprox_NumberOfVacantFlatsInModule();

	@SavVariableMapping("FNOFLATS")
	public Integer getNumberOfFlatsInModule();

	@SavVariableMapping("FNORESAR")
	public Integer getNONRESIDENTIALUSEIfNon_Residential__TotalFloorAreaInNon_ResidentialUse();

	@SavVariableMapping("FNOGRUSE")
	public Enum1655 getUseOfGroundFloor();

	@SavVariableMapping("FNOOTHER")
	public Enum1656 getOTHERFLATSINMODULEAreThey____();

	@SavVariableMapping("FNOREUSE")
	public Enum1657 getIfDwellingWithNon_Residential_Non_ResidentialUse();

	@SavVariableMapping("FNOBSUSE")
	public Enum1655 getUseOfBasement();

	@SavVariableMapping("FNORESFD")
	public Enum1282 getDoesNon_ResidentialUseIncludeHandlingOfFood_();

}

