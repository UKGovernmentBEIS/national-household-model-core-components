package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1003;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1004;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1012;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1014;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1018;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1021;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1022;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1023;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface VacantEntry extends SurveyEntry {
	@SavVariableMapping("VNLSTYR")
	public Integer getYearLastOccupied();

	@SavVariableMapping("VNCRFRMV")
	public Enum69 getWaitingForOwnersFriends_RelsToMoveIn();

	@SavVariableMapping("VNCRLET")
	public Enum69 getLet_ButNewTenantsNotMovedIn();

	@SavVariableMapping("VNCRDKRF")
	public Enum69 getCurrentlyUnoccupied_Refused_DK();

	@SavVariableMapping("VNMVHCI")
	public Enum69 getWentIntoHospital_Care_Institution();

	@SavVariableMapping("VNMVEVIC")
	public Enum69 getEvicted_PropertyReposessed();

	@SavVariableMapping("VNSTRNMD")
	public Enum69 getUndergoingRenovation_Modernisation();

	@SavVariableMapping("VNCRAWLT")
	public Enum69 getAwaitingLetting();

	@SavVariableMapping("VNMVDKMV")
	public Enum69 getMoved_Don_TKnowReason();

	@SavVariableMapping("VNSTOTHR")
	public Enum69 getCurrentStatus_Other();

	@SavVariableMapping("VNSTDKRF")
	public Enum69 getCurrentStatus_Don_TKnow_Refused();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("VNMVOTHR")
	public Enum69 getWhyMoved_Other();

	@SavVariableMapping("VNMVDAMG")
	public Enum69 getPropertyDamaged_EgFire_Flood_();

	@SavVariableMapping("VNCRNEVR")
	public Enum69 getWillNeverBeReOccupiedAgain_E_G_Derelict_();

	@SavVariableMapping("VNCRSOLD")
	public Enum69 getSold_NewOwnersNotYetMovedIn();

	@SavVariableMapping("VNMVOTMV")
	public Enum69 getMoved_OtherReason();

	@SavVariableMapping("VNLSTTEN")
	public Enum1003 getTenureWhenLastOccupied();

	@SavVariableMapping("VNLSTUSE")
	public Enum1004 getLastUse();

	@SavVariableMapping("VNCRMDRQ")
	public Enum69 getRepairs_ModsRequired();

	@SavVariableMapping("VNCRAWSL")
	public Enum69 getAwaitingSale();

	@SavVariableMapping("VNCRRMR")
	public Enum69 getBeingRepaired_Mod_Renovated();

	@SavVariableMapping("VNSTAWLT")
	public Enum69 getAwaitingLetting_VNSTAWLT();

	@SavVariableMapping("VNSTSDNO")
	public Enum69 getSold_AwaitingNewOccupants();

	@SavVariableMapping("VNSTAWNW")
	public Enum69 getAwaiting_New_Tenants();

	@SavVariableMapping("VNSTNONE")
	public Enum69 getCurrentStatus_NoneOfThese();

	@SavVariableMapping("VNSEAS")
	public Enum1012 getMonthLastOccupied();

	@SavVariableMapping("VNSTDRDM")
	public Enum69 getDerelictOrAwaitingDemolition();

	@SavVariableMapping("VNCRNTON")
	public Enum1014 getCurrentOwner();

	@SavVariableMapping("VNMVRPMD")
	public Enum69 getPropertyNeededRepair_Mod_Renovation();

	@SavVariableMapping("VNCROWMB")
	public Enum69 getWaitingForOwnerToMoveBackIn();

	@SavVariableMapping("VNMVDIED")
	public Enum69 getDied();

	@SavVariableMapping("VNCHANGE")
	public Enum1018 getChangedOwnership();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("VNEVER")
	public Enum1021 getEverOccupied();

	@SavVariableMapping("VNLLRD2")
	public Enum1022 getLandlordWhenLastOccupied();

	@SavVariableMapping("VNTENIN")
	public Enum1023 getLikelyTenureWhenLastOccupied();

	@SavVariableMapping("VNCRNGEQ")
	public Enum69 getInNegEquity_WaitingForValueToRise();

	@SavVariableMapping("VNMVDKRF")
	public Enum69 getWhyMoved_Refused_DontKnow();

	@SavVariableMapping("VNCROTHR")
	public Enum69 getCurrentlyUnoccupied_Other();

	@SavVariableMapping("VNSTAWSL")
	public Enum69 getAwaitingSale_VNSTAWSL();

}

