package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1533;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1541;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface HqEntry extends SurveyEntry {
	@SavVariableMapping("FHQWAPAY")
	public Enum1533 getWASTEWATERDISPOSALWhoDoYouPayForWasteWaterDisposal_();

	@SavVariableMapping("FHQWPUFL")
	public Enum10 getDoYouHaveAPushButtonOperatedFlush_();

	@SavVariableMapping("FHQFLDPR")
	public Enum1282 getWASTEWATERDISPOSALHadProblemWithFloodedDrains();

	@SavVariableMapping("FHQFLDCA")
	public Enum10 getWASTEWATERDISPOSALIsProblemLocatedInCommonAreas();

	@SavVariableMapping("FHQWASTE")
	public Enum1282 getWASTEWATERDISPOSALDirectlyConnectedToMainsDrainageOperatedByWaterSewageCompany();

	@SavVariableMapping("FCHALT")
	public Enum10 getSURVEYORCHECK_DateOfImprovements();

	@SavVariableMapping("FHQWLEAK")
	public Enum1282 getIfYes_DoesItEverLeakIntoTheToiletBowl_();

	@SavVariableMapping("FHQGARAG")
	public Enum1282 getGaragePrivateParkingSpace_Access();

	@SavVariableMapping("FHQFLDCP")
	public Enum1541 getWASTEWATERDISPOSALCurrentProblem();

	@SavVariableMapping("FHQFLDGD")
	public Enum10 getWASTEWATERDISPOSALIsProblemLocatedInTheGarden();

	@SavVariableMapping("FHQWAMET")
	public Enum1282 getDoYouHaveAWaterMeter_();

	@SavVariableMapping("FHQWMCH")
	public Enum1282 getIfYes_AreYouChargedForTheAmountYouUse_();

	@SavVariableMapping("FHQFLDHO")
	public Enum10 getWASTEWATERDISPOSALIsProblemLocatedInTheHome();

	@SavVariableMapping("FCHTEN")
	public Enum10 getSURVEYORCHECK_Tenure_Age_LengthOfResidence();

	@SavVariableMapping("FHQASKED")
	public Enum10 getHouseholdInterviewQuestionsAsked_();

	@SavVariableMapping("FCHBOH")
	public Enum10 getSURVEYORCHECK_AgeOfBoiler();

	@SavVariableMapping("FCHREE")
	public Enum10 getSURVEYORCHECK_DateOfRefurbishment();

	@SavVariableMapping("FHQCAVIT")
	public Enum1282 getCavityWallInsulation();

}

