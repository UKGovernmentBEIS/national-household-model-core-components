package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum908;
import uk.org.cse.nhm.ehcs10.interview.types.Enum909;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface RoomsEntry extends SurveyEntry {
	@SavVariableMapping("NRMS3")
	public Integer getNoOfKitchensLessThan6_5FtWide();

	@SavVariableMapping("SHRMS2")
	public Integer getNoOfLargeSharedKitchens();

	@SavVariableMapping("ACNUMBER")
	public Integer getNoOfHhldsSharedWith();

	@SavVariableMapping("NRMS4")
	public Integer getNoOfLivingRooms();

	@SavVariableMapping("NRMS2")
	public Integer getNoOfKitchensGreaterThan6_5FtWide_();

	@SavVariableMapping("NUMWC")
	public Integer getNoOfInsideFlushToilets();

	@SavVariableMapping("NRMS6")
	public Integer getNoOfUtility_OtherRooms();

	@SavVariableMapping("HIDNUMP")
	public Integer getNoInHiddenHhlds();

	@SavVariableMapping("SHRMS3")
	public Integer getNoOfSmallSharedKitchens();

	@SavVariableMapping("NRMS5")
	public Integer getNoOfBathrooms();

	@SavVariableMapping("SHRMS5")
	public Integer getNoOfSharedBathrooms();

	@SavVariableMapping("SHRMS6")
	public Integer getNoOfSharedUtility_OtherRooms();

	@SavVariableMapping("SHRMS4")
	public Integer getNoOfSharedLivingRooms();

	@SavVariableMapping("DVHIDHH")
	public Integer getDV_No_OfHiddenHhlds();

	@SavVariableMapping("BEDSPACE")
	public Integer getNoOfBedroomsInOtherAccom();

	@SavVariableMapping("HIDNUMH")
	public Integer getNoOfHiddenHhlds_IfGreaterThan1Person_();

	@SavVariableMapping("NRMSEHS")
	public Integer getNoOfBedrooms();

	@SavVariableMapping("NRMS1")
	public Double getNoOfBedrooms_NRMS1();

	@SavVariableMapping("SHSEPKIT")
	public Enum69 getSharedSepKitchen();

	@SavVariableMapping("ESBLET1")
	public Enum69 getSubletPart_NotCurrentlyLet();

	@SavVariableMapping("ACCOOK")
	public Enum69 getKitchenFacilitiesInSoleRoom();

	@SavVariableMapping("DVSOLE")
	public Enum69 getHhldHasSoleUseOfKeyAmentities();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("SHOTHR")
	public Enum69 getSharedOtherRoomForCooking();

	@SavVariableMapping("SHARE2")
	public Enum908 getShareFacilities();

	@SavVariableMapping("NONPRM")
	public Enum909 getSharesFacilitiesWithLandlord();

	@SavVariableMapping("ACPAY")
	public Enum69 getPaidRentByOtherHhld();

	@SavVariableMapping("HIDANY")
	public Enum69 getAnyHiddenHhlds();

	@SavVariableMapping("SHKITDIN")
	public Enum69 getSharedKitchen_Diner();

	@SavVariableMapping("WCSHR")
	public Enum69 getSharesToiletWithOtherHhld();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("SHCIRC")
	public Enum69 getSharesHall_Landing_Staircase();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

}

