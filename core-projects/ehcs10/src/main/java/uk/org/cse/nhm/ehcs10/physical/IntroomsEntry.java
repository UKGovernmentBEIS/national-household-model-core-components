package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1650;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface IntroomsEntry extends SurveyEntry {
	@SavVariableMapping("FINDRSRP")
	public Integer getDOORS_Repair_Rehang();

	@SavVariableMapping("FINFLRLV")
	public Integer getFLOORS_Leave();

	@SavVariableMapping("FINWLSRN")
	public Integer getWALLS_RebuildPartitionWall();

	@SavVariableMapping("FINFLRRP")
	public Integer getFLOORS_ReplaceOnlyBoardsOrScreed();

	@SavVariableMapping("FINDRSRN")
	public Integer getDOORS_Renew();

	@SavVariableMapping("FINWLSLV")
	public Integer getWALLS_Leave();

	@SavVariableMapping("FINWLSRP")
	public Integer getWALLS_IsolatedRepair_FillCracks();

	@SavVariableMapping("FINFLRRN")
	public Integer getFLOORS_ReplaceStructure();

	@SavVariableMapping("FINCLGLV")
	public Integer getCEILINGS_Leave();

	@SavVariableMapping("FINWLSPL")
	public Integer getWALLS_HackOff_Replaster();

	@SavVariableMapping("FINCLGRP")
	public Integer getCEILINGS_IsolatedRepair();

	@SavVariableMapping("FINCLGRN")
	public Integer getCEILINGS_TakeDownAndRenew();

	@SavVariableMapping("FINDFXMO")
	public Enum10 getDEFECTS_SeriousCondensation();

	@SavVariableMapping("FINWNDFL")
	public Enum10 getWINDOWS_Faults();

	@SavVariableMapping("FINDFXRV")
	public Enum10 getDEFECTS_InadequateRoomVentilation();

	@SavVariableMapping("FINHTGFX")
	public Enum10 getHEATING_SERVICES_FixedOtherHeater();

	@SavVariableMapping("FINWLSDL")
	public Enum10 getWALLS_DryLiningPresent();

	@SavVariableMapping("FINDRSFL")
	public Enum10 getDOORS_Faults();

	@SavVariableMapping("FINCLGFL")
	public Enum10 getCEILINGS_Faults();

	@SavVariableMapping("FINDFXRT")
	public Enum10 getDEFECTS_Dry_WetRot();

	@SavVariableMapping("FINFLRSF")
	public Enum10 getFLOORS_SolidFloors();

	@SavVariableMapping("FINWLSII")
	public Enum10 getWALLS_InternalInsulationPresent();

	@SavVariableMapping("FINDFXPD")
	public Enum10 getDEFECTS_PenetratingDamp();

	@SavVariableMapping("FINWNDES")
	public Enum10 getWINDOWS_MeansOfEscape();

	@SavVariableMapping("FINDFXIN")
	public Enum10 getDEFECTS_WoodBoringInsectAttack();

	@SavVariableMapping("FINDFXAL")
	public Enum10 getDEFECTS_InadequateArtificialLight();

	@SavVariableMapping("FINDFXVT")
	public Enum10 getDEFECTS_InadequateApplianceVentilation();

	@SavVariableMapping("FINFLRFL")
	public Enum10 getFLOORS_Faults();

	@SavVariableMapping("FINWLSFL")
	public Enum10 getWALLS_Faults();

	@SavVariableMapping("FINWNDSI")
	public Enum10 getWINDOWS_SecondaryGlazing();

	@SavVariableMapping("TYPE")
	public Enum1650 getRoom();

	@SavVariableMapping("FINHTGLG")
	public Enum10 getHEATING_SERVICES_FlourescentLowEnergyLighting();

	@SavVariableMapping("FINDFXNL")
	public Enum10 getDEFECTS_InadequateNaturalLight();

	@SavVariableMapping("FINHTGCH")
	public Enum10 getHEATING_SERVICES_CHProgAppliance();

	@SavVariableMapping("FINDFXRD")
	public Enum10 getDEFECTS_RisingDamp();

}

