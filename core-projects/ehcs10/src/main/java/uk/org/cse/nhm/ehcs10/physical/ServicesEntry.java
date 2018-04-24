package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface ServicesEntry extends SurveyEntry {
	@SavVariableMapping("FINCHBMA")
	public String getBoiler_ManufacturerName();

	@SavVariableMapping("FINCHBMO")
	public String getBoiler_ModelName_Number();

	@SavVariableMapping("FINWOTTY")
	public String getOther_Type_Fuel_Specify();

	@SavVariableMapping("FLIDESC")
	public String getLoft_DescribeProblems();

	@SavVariableMapping("FINWMPAG")
	public Integer getSeparateInstantaneousHeaterMultiPoin();

	@SavVariableMapping("FINWDIAG")
	public Integer getDualImmersionHeater_Age();

	@SavVariableMapping("FINWSIAG")
	public Integer getSingleImmersionHeater_Age();

	@SavVariableMapping("FINWHXAG")
	public Integer getBackBoiler_Age();

	@SavVariableMapping("FINCHNOD")
	public Integer getPrimaryHeatingSystem_NumberOfDwell();

	@SavVariableMapping("FINWHLAG")
	public Integer getCommunal_Age();

	@SavVariableMapping("FINWOTAG")
	public Integer getOther_Age();

	@SavVariableMapping("FINCHBAG")
	public Integer getBoiler_Age();

	@SavVariableMapping("FINWHOAG")
	public Integer getBoiler_WaterHeatingOnly__Age();

	@SavVariableMapping("FINNOFIR")
	public Integer getTotalNumberOfOpenFireplaces();

	@SavVariableMapping("FINCHBCD")
	public Integer getBoiler_Code();

	@SavVariableMapping("FINOHAGE")
	public Integer getOtherHeating_Age();

	@SavVariableMapping("FINCHDAG")
	public Integer getCentralHeatingDistribution_Age();

	@SavVariableMapping("FINWOTFU")
	public Integer getHotWaterSystem_OtherFuelCode();

	@SavVariableMapping("FINWSPAG")
	public Integer getSeparateInstantaneousHeaterSinglePoi();

	@SavVariableMapping("FINCHEAT")
	public Enum10 getINTERIORSPACEHEATING_PrimaryHeating();

	@SavVariableMapping("FINCHCTC")
	public Enum1282 getPrimaryHeatingControls_CelectTypeC();

	@SavVariableMapping("FINCHTYP")
	public Enum1710 getPrimaryHeatingSystem_TypeOfSystem();

	@SavVariableMapping("FINCHLOC")
	public Enum1711 getPrimaryHeatingSystem_LocationOfSys();

	@SavVariableMapping("FINHSEXP")
	public Enum1233 getHEALTHANDSAFETY_Explosions();

	@SavVariableMapping("FINMHBOI")
	public Enum1713 getBoilerGroup();

	@SavVariableMapping("FINWSIAC")
	public Enum1060 getSingleImmersionHeater_Action();

	@SavVariableMapping("FINCHOFF")
	public Enum1282 getPrimaryHeatingControls_OverallOn_Of();

	@SavVariableMapping("FINCHROM")
	public Enum1282 getPrimaryHeatingControls_RoomThermost();

	@SavVariableMapping("FINWHOPR")
	public Enum10 getBoiler_WaterHeatingOnly__Present();

	@SavVariableMapping("FINELEPP")
	public Enum1718 getElectricalSystems_PersonalProtection();

	@SavVariableMapping("FINWDITY")
	public Enum1719 getDualImmersionHeater_Type_Fuel();

	@SavVariableMapping("FINCHTZC")
	public Enum1282 getPrimaryHeatingControls_TimeAndTemp();

	@SavVariableMapping("FINWHLTY")
	public Enum1721 getCommunal_Type_Fuel();

	@SavVariableMapping("FINLOPOS")
	public Enum1722 getDwellingType();

	@SavVariableMapping("FINCHACC")
	public Enum1282 getPrimaryHeatingControls_AutomaticCha();

	@SavVariableMapping("FINOHACT")
	public Enum1060 getOtherHeating_Action();

	@SavVariableMapping("FINWHEAT")
	public Enum10 getSECTION5INTERIORWATERHEATING_HotWa();

	@SavVariableMapping("FLICWIEV")
	public Enum10 getEvidenceOfCWIInTheLoft_();

	@SavVariableMapping("FINWHSIZ")
	public Enum1727 getCylinder_Size_Volume();

	@SavVariableMapping("FINGASMS")
	public Enum10 getGasSystem_MainsSupply();

	@SavVariableMapping("FINELEPS")
	public Enum1729 getElectricalSystem_PowerSockets();

	@SavVariableMapping("FINELECU")
	public Enum1730 getElectricalSystem_ConsumerUnitArrang();

	@SavVariableMapping("FINWHTHE")
	public Enum1282 getWaterHeatingControls_CylinderThermos();

	@SavVariableMapping("FINELEAC")
	public Enum1060 getElectricalSystem_Action();

	@SavVariableMapping("FINCHDST")
	public Enum1282 getPrimaryHeatingControls_DelayedTime();

	@SavVariableMapping("FINOHEAT")
	public Enum10 getOtherHeating_Present();

	@SavVariableMapping("FINCHCON")
	public Enum1282 getPrimaryHeatingControls_RadiatorCont();

	@SavVariableMapping("FINWDIAC")
	public Enum1060 getDualImmersionHeater_Action();

	@SavVariableMapping("FINCHTHE")
	public Enum1282 getPrimaryHeatingControls_BoilerThermo();

	@SavVariableMapping("FINWSIPR")
	public Enum10 getSingleImmersionHeater_Present();

	@SavVariableMapping("FINWHCEN")
	public Enum1282 getWaterHeatingControls_TimeClockForW();

	@SavVariableMapping("FINWHOAC")
	public Enum1060 getBoiler_WaterHeatingOnly__Action();

	@SavVariableMapping("FINGASPR")
	public Enum10 getGasSystem_Present();

	@SavVariableMapping("FINWMPTY")
	public Enum1742 getSeparateInstantaneousHeaterMultiPoin_FINWMPTY();

	@SavVariableMapping("FINWSPAC")
	public Enum1060 getSeparateInstantaneousHeaterSinglePoi_FINWSPAC();

	@SavVariableMapping("FINWHXPR")
	public Enum10 getBackBoiler_Present();

	@SavVariableMapping("FINWHCPR")
	public Enum10 getBoilerWithCentralHeating_Present();

	@SavVariableMapping("FINOHPHS")
	public Enum10 getOtherHeating_MainHeatSourceInWint();

	@SavVariableMapping("FINWOTPR")
	public Enum10 getOther_Present_();

	@SavVariableMapping("FININTYP")
	public Enum1748 getTypeOfLoftInsulation();

	@SavVariableMapping("FINELEMS")
	public Enum10 getElectricalSystem_NormalMainsSupply();

	@SavVariableMapping("FINCHBAC")
	public Enum1060 getBoiler_Action();

	@SavVariableMapping("FINELEDC")
	public Enum1751 getElectricalSystem_LocationOfDistribu();

	@SavVariableMapping("FINCWIME")
	public Enum10 getEvidenceOfCWIInElectricyOrGasMeters_();

	@SavVariableMapping("FINELELC")
	public Enum1753 getElectricalSystem_LightingCircuits();

	@SavVariableMapping("FINWSITY")
	public Enum1719 getSingleImmersionHeater_Type_Fuel();

	@SavVariableMapping("FLIINFOR")
	public Enum1755 getLoft_InformationFrom();

	@SavVariableMapping("FLIPROBS")
	public Enum10 getLoft_AnyRoofStructureProblems();

	@SavVariableMapping("FLITHICK")
	public Enum1757 getLoft_ApproxThickness();

	@SavVariableMapping("FLITYPES")
	public Enum1758 getLoft_Type();

	@SavVariableMapping("FINCHDAC")
	public Enum1060 getCentralHeatingDistribution_Action();

	@SavVariableMapping("FINCHMCC")
	public Enum1282 getPRIMARYHEATINGCONTROLS_STORGAEHEATER();

	@SavVariableMapping("FINWMPAC")
	public Enum1060 getSeparateInstantaneousHeaterMultiPoin_FINWMPAC();

	@SavVariableMapping("FINCHPHS")
	public Enum10 getPrimaryHeatingSystem_MainHeatSourc();

	@SavVariableMapping("FINWSPTY")
	public Enum1742 getSeparateInstantaneousHeaterSinglePoi_FINWSPTY();

	@SavVariableMapping("FINELEWI")
	public Enum1764 getElectricalSystem_TypeOfWiring();

	@SavVariableMapping("FINHSUNG")
	public Enum1233 getHEALTHANDSAFETY_UncombustedGas();

	@SavVariableMapping("FINWHINS")
	public Enum1766 getHotWaterCylinder_Insulation();

	@SavVariableMapping("FINHSELS")
	public Enum1233 getHEALTHANDSAFETY_ElectricalSafety();

	@SavVariableMapping("FINCHTIM")
	public Enum1282 getPrimaryHeatingControls_CentralTimer();

	@SavVariableMapping("FINOPELE")
	public Enum10 getElectricalSystem_OffPeakSupply();

	@SavVariableMapping("FINELEOP")
	public Enum1770 getElectricalSystem_OverloadProtection();

	@SavVariableMapping("FINWHMMS")
	public Enum1771 getHotWaterCylinder_Thickness_Mms_();

	@SavVariableMapping("FINWSPPR")
	public Enum10 getSeparateInstantaneousHeaterSinglePoi_FINWSPPR();

	@SavVariableMapping("FINWHCYL")
	public Enum1282 getCylinder_Present();

	@SavVariableMapping("FINHSCO")
	public Enum1233 getHEALTHANDSAFETY_CarbonMonoxide();

	@SavVariableMapping("FINWDIPR")
	public Enum10 getDualImmersionHeater_Present();

	@SavVariableMapping("FINOHTYP")
	public Enum1776 getOtherHeating_TypeOfSystem();

	@SavVariableMapping("FINMHFUE")
	public Enum1777 getMainHeatingFuel();

	@SavVariableMapping("FINWMPPR")
	public Enum10 getSeparateInstantaneousHeaterMultiPoin_FINWMPPR();

	@SavVariableMapping("FINWHOTY")
	public Enum1779 getBoiler_WaterHeatingOnly__Type_Fuel();

	@SavVariableMapping("FINELEPR")
	public Enum10 getElectricalSystem_Present();

	@SavVariableMapping("FINWHLPR")
	public Enum10 getCommunal_Present_();

	@SavVariableMapping("FINWHXTY")
	public Enum1779 getBackBoiler_Type_Fuel();

	@SavVariableMapping("FINCHTRV")
	public Enum1282 getPrimaryHeatingControls_Thermostatic();

	@SavVariableMapping("FINELEEA")
	public Enum1784 getElectricalSystem_EarthingOfWires();

	@SavVariableMapping("FINWHXAC")
	public Enum1060 getBackBoiler_Action();

	@SavVariableMapping("FINCHOVE")
	public Enum1282 getPrimaryHeatingControls_ManualOverri();

	@SavVariableMapping("FLIINSUL")
	public Enum1282 getRoofInsulationAboveLivingSpace();

	@SavVariableMapping("FINGASAC")
	public Enum1060 getGasSystem_Action();

}

