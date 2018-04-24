package uk.org.cse.nhm.ehcs10.physical.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.types.*;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class ServicesEntryImpl extends SurveyEntryImpl implements ServicesEntry {
	private String Boiler_ManufacturerName;
	private String Boiler_ModelName_Number;
	private String Other_Type_Fuel_Specify;
	private String Loft_DescribeProblems;
	private Integer SeparateInstantaneousHeaterMultiPoin;
	private Integer DualImmersionHeater_Age;
	private Integer SingleImmersionHeater_Age;
	private Integer BackBoiler_Age;
	private Integer PrimaryHeatingSystem_NumberOfDwell;
	private Integer Communal_Age;
	private Integer Other_Age;
	private Integer Boiler_Age;
	private Integer Boiler_WaterHeatingOnly__Age;
	private Integer TotalNumberOfOpenFireplaces;
	private Integer Boiler_Code;
	private Integer OtherHeating_Age;
	private Integer CentralHeatingDistribution_Age;
	private Integer HotWaterSystem_OtherFuelCode;
	private Integer SeparateInstantaneousHeaterSinglePoi;
	private Enum10 INTERIORSPACEHEATING_PrimaryHeating;
	private Enum1282 PrimaryHeatingControls_CelectTypeC;
	private Enum1710 PrimaryHeatingSystem_TypeOfSystem;
	private Enum1711 PrimaryHeatingSystem_LocationOfSys;
	private Enum1233 HEALTHANDSAFETY_Explosions;
	private Enum1713 BoilerGroup;
	private Enum1060 SingleImmersionHeater_Action;
	private Enum1282 PrimaryHeatingControls_OverallOn_Of;
	private Enum1282 PrimaryHeatingControls_RoomThermost;
	private Enum10 Boiler_WaterHeatingOnly__Present;
	private Enum1718 ElectricalSystems_PersonalProtection;
	private Enum1719 DualImmersionHeater_Type_Fuel;
	private Enum1282 PrimaryHeatingControls_TimeAndTemp;
	private Enum1721 Communal_Type_Fuel;
	private Enum1722 DwellingType;
	private Enum1282 PrimaryHeatingControls_AutomaticCha;
	private Enum1060 OtherHeating_Action;
	private Enum10 SECTION5INTERIORWATERHEATING_HotWa;
	private Enum10 EvidenceOfCWIInTheLoft_;
	private Enum1727 Cylinder_Size_Volume;
	private Enum10 GasSystem_MainsSupply;
	private Enum1729 ElectricalSystem_PowerSockets;
	private Enum1730 ElectricalSystem_ConsumerUnitArrang;
	private Enum1282 WaterHeatingControls_CylinderThermos;
	private Enum1060 ElectricalSystem_Action;
	private Enum1282 PrimaryHeatingControls_DelayedTime;
	private Enum10 OtherHeating_Present;
	private Enum1282 PrimaryHeatingControls_RadiatorCont;
	private Enum1060 DualImmersionHeater_Action;
	private Enum1282 PrimaryHeatingControls_BoilerThermo;
	private Enum10 SingleImmersionHeater_Present;
	private Enum1282 WaterHeatingControls_TimeClockForW;
	private Enum1060 Boiler_WaterHeatingOnly__Action;
	private Enum10 GasSystem_Present;
	private Enum1742 SeparateInstantaneousHeaterMultiPoin_FINWMPTY;
	private Enum1060 SeparateInstantaneousHeaterSinglePoi_FINWSPAC;
	private Enum10 BackBoiler_Present;
	private Enum10 BoilerWithCentralHeating_Present;
	private Enum10 OtherHeating_MainHeatSourceInWint;
	private Enum10 Other_Present_;
	private Enum1748 TypeOfLoftInsulation;
	private Enum10 ElectricalSystem_NormalMainsSupply;
	private Enum1060 Boiler_Action;
	private Enum1751 ElectricalSystem_LocationOfDistribu;
	private Enum10 EvidenceOfCWIInElectricyOrGasMeters_;
	private Enum1753 ElectricalSystem_LightingCircuits;
	private Enum1719 SingleImmersionHeater_Type_Fuel;
	private Enum1755 Loft_InformationFrom;
	private Enum10 Loft_AnyRoofStructureProblems;
	private Enum1757 Loft_ApproxThickness;
	private Enum1758 Loft_Type;
	private Enum1060 CentralHeatingDistribution_Action;
	private Enum1282 PRIMARYHEATINGCONTROLS_STORGAEHEATER;
	private Enum1060 SeparateInstantaneousHeaterMultiPoin_FINWMPAC;
	private Enum10 PrimaryHeatingSystem_MainHeatSourc;
	private Enum1742 SeparateInstantaneousHeaterSinglePoi_FINWSPTY;
	private Enum1764 ElectricalSystem_TypeOfWiring;
	private Enum1233 HEALTHANDSAFETY_UncombustedGas;
	private Enum1766 HotWaterCylinder_Insulation;
	private Enum1233 HEALTHANDSAFETY_ElectricalSafety;
	private Enum1282 PrimaryHeatingControls_CentralTimer;
	private Enum10 ElectricalSystem_OffPeakSupply;
	private Enum1770 ElectricalSystem_OverloadProtection;
	private Enum1771 HotWaterCylinder_Thickness_Mms_;
	private Enum10 SeparateInstantaneousHeaterSinglePoi_FINWSPPR;
	private Enum1282 Cylinder_Present;
	private Enum1233 HEALTHANDSAFETY_CarbonMonoxide;
	private Enum10 DualImmersionHeater_Present;
	private Enum1776 OtherHeating_TypeOfSystem;
	private Enum1777 MainHeatingFuel;
	private Enum10 SeparateInstantaneousHeaterMultiPoin_FINWMPPR;
	private Enum1779 Boiler_WaterHeatingOnly__Type_Fuel;
	private Enum10 ElectricalSystem_Present;
	private Enum10 Communal_Present_;
	private Enum1779 BackBoiler_Type_Fuel;
	private Enum1282 PrimaryHeatingControls_Thermostatic;
	private Enum1784 ElectricalSystem_EarthingOfWires;
	private Enum1060 BackBoiler_Action;
	private Enum1282 PrimaryHeatingControls_ManualOverri;
	private Enum1282 RoofInsulationAboveLivingSpace;
	private Enum1060 GasSystem_Action;
	public String getBoiler_ManufacturerName() {
		return Boiler_ManufacturerName;	}

	public void setBoiler_ManufacturerName(final String Boiler_ManufacturerName) {
		this.Boiler_ManufacturerName = Boiler_ManufacturerName;	}

	public String getBoiler_ModelName_Number() {
		return Boiler_ModelName_Number;	}

	public void setBoiler_ModelName_Number(final String Boiler_ModelName_Number) {
		this.Boiler_ModelName_Number = Boiler_ModelName_Number;	}

	public String getOther_Type_Fuel_Specify() {
		return Other_Type_Fuel_Specify;	}

	public void setOther_Type_Fuel_Specify(final String Other_Type_Fuel_Specify) {
		this.Other_Type_Fuel_Specify = Other_Type_Fuel_Specify;	}

	public String getLoft_DescribeProblems() {
		return Loft_DescribeProblems;	}

	public void setLoft_DescribeProblems(final String Loft_DescribeProblems) {
		this.Loft_DescribeProblems = Loft_DescribeProblems;	}

	public Integer getSeparateInstantaneousHeaterMultiPoin() {
		return SeparateInstantaneousHeaterMultiPoin;	}

	public void setSeparateInstantaneousHeaterMultiPoin(final Integer SeparateInstantaneousHeaterMultiPoin) {
		this.SeparateInstantaneousHeaterMultiPoin = SeparateInstantaneousHeaterMultiPoin;	}

	public Integer getDualImmersionHeater_Age() {
		return DualImmersionHeater_Age;	}

	public void setDualImmersionHeater_Age(final Integer DualImmersionHeater_Age) {
		this.DualImmersionHeater_Age = DualImmersionHeater_Age;	}

	public Integer getSingleImmersionHeater_Age() {
		return SingleImmersionHeater_Age;	}

	public void setSingleImmersionHeater_Age(final Integer SingleImmersionHeater_Age) {
		this.SingleImmersionHeater_Age = SingleImmersionHeater_Age;	}

	public Integer getBackBoiler_Age() {
		return BackBoiler_Age;	}

	public void setBackBoiler_Age(final Integer BackBoiler_Age) {
		this.BackBoiler_Age = BackBoiler_Age;	}

	public Integer getPrimaryHeatingSystem_NumberOfDwell() {
		return PrimaryHeatingSystem_NumberOfDwell;	}

	public void setPrimaryHeatingSystem_NumberOfDwell(final Integer PrimaryHeatingSystem_NumberOfDwell) {
		this.PrimaryHeatingSystem_NumberOfDwell = PrimaryHeatingSystem_NumberOfDwell;	}

	public Integer getCommunal_Age() {
		return Communal_Age;	}

	public void setCommunal_Age(final Integer Communal_Age) {
		this.Communal_Age = Communal_Age;	}

	public Integer getOther_Age() {
		return Other_Age;	}

	public void setOther_Age(final Integer Other_Age) {
		this.Other_Age = Other_Age;	}

	public Integer getBoiler_Age() {
		return Boiler_Age;	}

	public void setBoiler_Age(final Integer Boiler_Age) {
		this.Boiler_Age = Boiler_Age;	}

	public Integer getBoiler_WaterHeatingOnly__Age() {
		return Boiler_WaterHeatingOnly__Age;	}

	public void setBoiler_WaterHeatingOnly__Age(final Integer Boiler_WaterHeatingOnly__Age) {
		this.Boiler_WaterHeatingOnly__Age = Boiler_WaterHeatingOnly__Age;	}

	public Integer getTotalNumberOfOpenFireplaces() {
		return TotalNumberOfOpenFireplaces;	}

	public void setTotalNumberOfOpenFireplaces(final Integer TotalNumberOfOpenFireplaces) {
		this.TotalNumberOfOpenFireplaces = TotalNumberOfOpenFireplaces;	}

	public Integer getBoiler_Code() {
		return Boiler_Code;	}

	public void setBoiler_Code(final Integer Boiler_Code) {
		this.Boiler_Code = Boiler_Code;	}

	public Integer getOtherHeating_Age() {
		return OtherHeating_Age;	}

	public void setOtherHeating_Age(final Integer OtherHeating_Age) {
		this.OtherHeating_Age = OtherHeating_Age;	}

	public Integer getCentralHeatingDistribution_Age() {
		return CentralHeatingDistribution_Age;	}

	public void setCentralHeatingDistribution_Age(final Integer CentralHeatingDistribution_Age) {
		this.CentralHeatingDistribution_Age = CentralHeatingDistribution_Age;	}

	public Integer getHotWaterSystem_OtherFuelCode() {
		return HotWaterSystem_OtherFuelCode;	}

	public void setHotWaterSystem_OtherFuelCode(final Integer HotWaterSystem_OtherFuelCode) {
		this.HotWaterSystem_OtherFuelCode = HotWaterSystem_OtherFuelCode;	}

	public Integer getSeparateInstantaneousHeaterSinglePoi() {
		return SeparateInstantaneousHeaterSinglePoi;	}

	public void setSeparateInstantaneousHeaterSinglePoi(final Integer SeparateInstantaneousHeaterSinglePoi) {
		this.SeparateInstantaneousHeaterSinglePoi = SeparateInstantaneousHeaterSinglePoi;	}

	public Enum10 getINTERIORSPACEHEATING_PrimaryHeating() {
		return INTERIORSPACEHEATING_PrimaryHeating;	}

	public void setINTERIORSPACEHEATING_PrimaryHeating(final Enum10 INTERIORSPACEHEATING_PrimaryHeating) {
		this.INTERIORSPACEHEATING_PrimaryHeating = INTERIORSPACEHEATING_PrimaryHeating;	}

	public Enum1282 getPrimaryHeatingControls_CelectTypeC() {
		return PrimaryHeatingControls_CelectTypeC;	}

	public void setPrimaryHeatingControls_CelectTypeC(final Enum1282 PrimaryHeatingControls_CelectTypeC) {
		this.PrimaryHeatingControls_CelectTypeC = PrimaryHeatingControls_CelectTypeC;	}

	public Enum1710 getPrimaryHeatingSystem_TypeOfSystem() {
		return PrimaryHeatingSystem_TypeOfSystem;	}

	public void setPrimaryHeatingSystem_TypeOfSystem(final Enum1710 PrimaryHeatingSystem_TypeOfSystem) {
		this.PrimaryHeatingSystem_TypeOfSystem = PrimaryHeatingSystem_TypeOfSystem;	}

	public Enum1711 getPrimaryHeatingSystem_LocationOfSys() {
		return PrimaryHeatingSystem_LocationOfSys;	}

	public void setPrimaryHeatingSystem_LocationOfSys(final Enum1711 PrimaryHeatingSystem_LocationOfSys) {
		this.PrimaryHeatingSystem_LocationOfSys = PrimaryHeatingSystem_LocationOfSys;	}

	public Enum1233 getHEALTHANDSAFETY_Explosions() {
		return HEALTHANDSAFETY_Explosions;	}

	public void setHEALTHANDSAFETY_Explosions(final Enum1233 HEALTHANDSAFETY_Explosions) {
		this.HEALTHANDSAFETY_Explosions = HEALTHANDSAFETY_Explosions;	}

	public Enum1713 getBoilerGroup() {
		return BoilerGroup;	}

	public void setBoilerGroup(final Enum1713 BoilerGroup) {
		this.BoilerGroup = BoilerGroup;	}

	public Enum1060 getSingleImmersionHeater_Action() {
		return SingleImmersionHeater_Action;	}

	public void setSingleImmersionHeater_Action(final Enum1060 SingleImmersionHeater_Action) {
		this.SingleImmersionHeater_Action = SingleImmersionHeater_Action;	}

	public Enum1282 getPrimaryHeatingControls_OverallOn_Of() {
		return PrimaryHeatingControls_OverallOn_Of;	}

	public void setPrimaryHeatingControls_OverallOn_Of(final Enum1282 PrimaryHeatingControls_OverallOn_Of) {
		this.PrimaryHeatingControls_OverallOn_Of = PrimaryHeatingControls_OverallOn_Of;	}

	public Enum1282 getPrimaryHeatingControls_RoomThermost() {
		return PrimaryHeatingControls_RoomThermost;	}

	public void setPrimaryHeatingControls_RoomThermost(final Enum1282 PrimaryHeatingControls_RoomThermost) {
		this.PrimaryHeatingControls_RoomThermost = PrimaryHeatingControls_RoomThermost;	}

	public Enum10 getBoiler_WaterHeatingOnly__Present() {
		return Boiler_WaterHeatingOnly__Present;	}

	public void setBoiler_WaterHeatingOnly__Present(final Enum10 Boiler_WaterHeatingOnly__Present) {
		this.Boiler_WaterHeatingOnly__Present = Boiler_WaterHeatingOnly__Present;	}

	public Enum1718 getElectricalSystems_PersonalProtection() {
		return ElectricalSystems_PersonalProtection;	}

	public void setElectricalSystems_PersonalProtection(final Enum1718 ElectricalSystems_PersonalProtection) {
		this.ElectricalSystems_PersonalProtection = ElectricalSystems_PersonalProtection;	}

	public Enum1719 getDualImmersionHeater_Type_Fuel() {
		return DualImmersionHeater_Type_Fuel;	}

	public void setDualImmersionHeater_Type_Fuel(final Enum1719 DualImmersionHeater_Type_Fuel) {
		this.DualImmersionHeater_Type_Fuel = DualImmersionHeater_Type_Fuel;	}

	public Enum1282 getPrimaryHeatingControls_TimeAndTemp() {
		return PrimaryHeatingControls_TimeAndTemp;	}

	public void setPrimaryHeatingControls_TimeAndTemp(final Enum1282 PrimaryHeatingControls_TimeAndTemp) {
		this.PrimaryHeatingControls_TimeAndTemp = PrimaryHeatingControls_TimeAndTemp;	}

	public Enum1721 getCommunal_Type_Fuel() {
		return Communal_Type_Fuel;	}

	public void setCommunal_Type_Fuel(final Enum1721 Communal_Type_Fuel) {
		this.Communal_Type_Fuel = Communal_Type_Fuel;	}

	public Enum1722 getDwellingType() {
		return DwellingType;	}

	public void setDwellingType(final Enum1722 DwellingType) {
		this.DwellingType = DwellingType;	}

	public Enum1282 getPrimaryHeatingControls_AutomaticCha() {
		return PrimaryHeatingControls_AutomaticCha;	}

	public void setPrimaryHeatingControls_AutomaticCha(final Enum1282 PrimaryHeatingControls_AutomaticCha) {
		this.PrimaryHeatingControls_AutomaticCha = PrimaryHeatingControls_AutomaticCha;	}

	public Enum1060 getOtherHeating_Action() {
		return OtherHeating_Action;	}

	public void setOtherHeating_Action(final Enum1060 OtherHeating_Action) {
		this.OtherHeating_Action = OtherHeating_Action;	}

	public Enum10 getSECTION5INTERIORWATERHEATING_HotWa() {
		return SECTION5INTERIORWATERHEATING_HotWa;	}

	public void setSECTION5INTERIORWATERHEATING_HotWa(final Enum10 SECTION5INTERIORWATERHEATING_HotWa) {
		this.SECTION5INTERIORWATERHEATING_HotWa = SECTION5INTERIORWATERHEATING_HotWa;	}

	public Enum10 getEvidenceOfCWIInTheLoft_() {
		return EvidenceOfCWIInTheLoft_;	}

	public void setEvidenceOfCWIInTheLoft_(final Enum10 EvidenceOfCWIInTheLoft_) {
		this.EvidenceOfCWIInTheLoft_ = EvidenceOfCWIInTheLoft_;	}

	public Enum1727 getCylinder_Size_Volume() {
		return Cylinder_Size_Volume;	}

	public void setCylinder_Size_Volume(final Enum1727 Cylinder_Size_Volume) {
		this.Cylinder_Size_Volume = Cylinder_Size_Volume;	}

	public Enum10 getGasSystem_MainsSupply() {
		return GasSystem_MainsSupply;	}

	public void setGasSystem_MainsSupply(final Enum10 GasSystem_MainsSupply) {
		this.GasSystem_MainsSupply = GasSystem_MainsSupply;	}

	public Enum1729 getElectricalSystem_PowerSockets() {
		return ElectricalSystem_PowerSockets;	}

	public void setElectricalSystem_PowerSockets(final Enum1729 ElectricalSystem_PowerSockets) {
		this.ElectricalSystem_PowerSockets = ElectricalSystem_PowerSockets;	}

	public Enum1730 getElectricalSystem_ConsumerUnitArrang() {
		return ElectricalSystem_ConsumerUnitArrang;	}

	public void setElectricalSystem_ConsumerUnitArrang(final Enum1730 ElectricalSystem_ConsumerUnitArrang) {
		this.ElectricalSystem_ConsumerUnitArrang = ElectricalSystem_ConsumerUnitArrang;	}

	public Enum1282 getWaterHeatingControls_CylinderThermos() {
		return WaterHeatingControls_CylinderThermos;	}

	public void setWaterHeatingControls_CylinderThermos(final Enum1282 WaterHeatingControls_CylinderThermos) {
		this.WaterHeatingControls_CylinderThermos = WaterHeatingControls_CylinderThermos;	}

	public Enum1060 getElectricalSystem_Action() {
		return ElectricalSystem_Action;	}

	public void setElectricalSystem_Action(final Enum1060 ElectricalSystem_Action) {
		this.ElectricalSystem_Action = ElectricalSystem_Action;	}

	public Enum1282 getPrimaryHeatingControls_DelayedTime() {
		return PrimaryHeatingControls_DelayedTime;	}

	public void setPrimaryHeatingControls_DelayedTime(final Enum1282 PrimaryHeatingControls_DelayedTime) {
		this.PrimaryHeatingControls_DelayedTime = PrimaryHeatingControls_DelayedTime;	}

	public Enum10 getOtherHeating_Present() {
		return OtherHeating_Present;	}

	public void setOtherHeating_Present(final Enum10 OtherHeating_Present) {
		this.OtherHeating_Present = OtherHeating_Present;	}

	public Enum1282 getPrimaryHeatingControls_RadiatorCont() {
		return PrimaryHeatingControls_RadiatorCont;	}

	public void setPrimaryHeatingControls_RadiatorCont(final Enum1282 PrimaryHeatingControls_RadiatorCont) {
		this.PrimaryHeatingControls_RadiatorCont = PrimaryHeatingControls_RadiatorCont;	}

	public Enum1060 getDualImmersionHeater_Action() {
		return DualImmersionHeater_Action;	}

	public void setDualImmersionHeater_Action(final Enum1060 DualImmersionHeater_Action) {
		this.DualImmersionHeater_Action = DualImmersionHeater_Action;	}

	public Enum1282 getPrimaryHeatingControls_BoilerThermo() {
		return PrimaryHeatingControls_BoilerThermo;	}

	public void setPrimaryHeatingControls_BoilerThermo(final Enum1282 PrimaryHeatingControls_BoilerThermo) {
		this.PrimaryHeatingControls_BoilerThermo = PrimaryHeatingControls_BoilerThermo;	}

	public Enum10 getSingleImmersionHeater_Present() {
		return SingleImmersionHeater_Present;	}

	public void setSingleImmersionHeater_Present(final Enum10 SingleImmersionHeater_Present) {
		this.SingleImmersionHeater_Present = SingleImmersionHeater_Present;	}

	public Enum1282 getWaterHeatingControls_TimeClockForW() {
		return WaterHeatingControls_TimeClockForW;	}

	public void setWaterHeatingControls_TimeClockForW(final Enum1282 WaterHeatingControls_TimeClockForW) {
		this.WaterHeatingControls_TimeClockForW = WaterHeatingControls_TimeClockForW;	}

	public Enum1060 getBoiler_WaterHeatingOnly__Action() {
		return Boiler_WaterHeatingOnly__Action;	}

	public void setBoiler_WaterHeatingOnly__Action(final Enum1060 Boiler_WaterHeatingOnly__Action) {
		this.Boiler_WaterHeatingOnly__Action = Boiler_WaterHeatingOnly__Action;	}

	public Enum10 getGasSystem_Present() {
		return GasSystem_Present;	}

	public void setGasSystem_Present(final Enum10 GasSystem_Present) {
		this.GasSystem_Present = GasSystem_Present;	}

	public Enum1742 getSeparateInstantaneousHeaterMultiPoin_FINWMPTY() {
		return SeparateInstantaneousHeaterMultiPoin_FINWMPTY;	}

	public void setSeparateInstantaneousHeaterMultiPoin_FINWMPTY(final Enum1742 SeparateInstantaneousHeaterMultiPoin_FINWMPTY) {
		this.SeparateInstantaneousHeaterMultiPoin_FINWMPTY = SeparateInstantaneousHeaterMultiPoin_FINWMPTY;	}

	public Enum1060 getSeparateInstantaneousHeaterSinglePoi_FINWSPAC() {
		return SeparateInstantaneousHeaterSinglePoi_FINWSPAC;	}

	public void setSeparateInstantaneousHeaterSinglePoi_FINWSPAC(final Enum1060 SeparateInstantaneousHeaterSinglePoi_FINWSPAC) {
		this.SeparateInstantaneousHeaterSinglePoi_FINWSPAC = SeparateInstantaneousHeaterSinglePoi_FINWSPAC;	}

	public Enum10 getBackBoiler_Present() {
		return BackBoiler_Present;	}

	public void setBackBoiler_Present(final Enum10 BackBoiler_Present) {
		this.BackBoiler_Present = BackBoiler_Present;	}

	public Enum10 getBoilerWithCentralHeating_Present() {
		return BoilerWithCentralHeating_Present;	}

	public void setBoilerWithCentralHeating_Present(final Enum10 BoilerWithCentralHeating_Present) {
		this.BoilerWithCentralHeating_Present = BoilerWithCentralHeating_Present;	}

	public Enum10 getOtherHeating_MainHeatSourceInWint() {
		return OtherHeating_MainHeatSourceInWint;	}

	public void setOtherHeating_MainHeatSourceInWint(final Enum10 OtherHeating_MainHeatSourceInWint) {
		this.OtherHeating_MainHeatSourceInWint = OtherHeating_MainHeatSourceInWint;	}

	public Enum10 getOther_Present_() {
		return Other_Present_;	}

	public void setOther_Present_(final Enum10 Other_Present_) {
		this.Other_Present_ = Other_Present_;	}

	public Enum1748 getTypeOfLoftInsulation() {
		return TypeOfLoftInsulation;	}

	public void setTypeOfLoftInsulation(final Enum1748 TypeOfLoftInsulation) {
		this.TypeOfLoftInsulation = TypeOfLoftInsulation;	}

	public Enum10 getElectricalSystem_NormalMainsSupply() {
		return ElectricalSystem_NormalMainsSupply;	}

	public void setElectricalSystem_NormalMainsSupply(final Enum10 ElectricalSystem_NormalMainsSupply) {
		this.ElectricalSystem_NormalMainsSupply = ElectricalSystem_NormalMainsSupply;	}

	public Enum1060 getBoiler_Action() {
		return Boiler_Action;	}

	public void setBoiler_Action(final Enum1060 Boiler_Action) {
		this.Boiler_Action = Boiler_Action;	}

	public Enum1751 getElectricalSystem_LocationOfDistribu() {
		return ElectricalSystem_LocationOfDistribu;	}

	public void setElectricalSystem_LocationOfDistribu(final Enum1751 ElectricalSystem_LocationOfDistribu) {
		this.ElectricalSystem_LocationOfDistribu = ElectricalSystem_LocationOfDistribu;	}

	public Enum10 getEvidenceOfCWIInElectricyOrGasMeters_() {
		return EvidenceOfCWIInElectricyOrGasMeters_;	}

	public void setEvidenceOfCWIInElectricyOrGasMeters_(final Enum10 EvidenceOfCWIInElectricyOrGasMeters_) {
		this.EvidenceOfCWIInElectricyOrGasMeters_ = EvidenceOfCWIInElectricyOrGasMeters_;	}

	public Enum1753 getElectricalSystem_LightingCircuits() {
		return ElectricalSystem_LightingCircuits;	}

	public void setElectricalSystem_LightingCircuits(final Enum1753 ElectricalSystem_LightingCircuits) {
		this.ElectricalSystem_LightingCircuits = ElectricalSystem_LightingCircuits;	}

	public Enum1719 getSingleImmersionHeater_Type_Fuel() {
		return SingleImmersionHeater_Type_Fuel;	}

	public void setSingleImmersionHeater_Type_Fuel(final Enum1719 SingleImmersionHeater_Type_Fuel) {
		this.SingleImmersionHeater_Type_Fuel = SingleImmersionHeater_Type_Fuel;	}

	public Enum1755 getLoft_InformationFrom() {
		return Loft_InformationFrom;	}

	public void setLoft_InformationFrom(final Enum1755 Loft_InformationFrom) {
		this.Loft_InformationFrom = Loft_InformationFrom;	}

	public Enum10 getLoft_AnyRoofStructureProblems() {
		return Loft_AnyRoofStructureProblems;	}

	public void setLoft_AnyRoofStructureProblems(final Enum10 Loft_AnyRoofStructureProblems) {
		this.Loft_AnyRoofStructureProblems = Loft_AnyRoofStructureProblems;	}

	public Enum1757 getLoft_ApproxThickness() {
		return Loft_ApproxThickness;	}

	public void setLoft_ApproxThickness(final Enum1757 Loft_ApproxThickness) {
		this.Loft_ApproxThickness = Loft_ApproxThickness;	}

	public Enum1758 getLoft_Type() {
		return Loft_Type;	}

	public void setLoft_Type(final Enum1758 Loft_Type) {
		this.Loft_Type = Loft_Type;	}

	public Enum1060 getCentralHeatingDistribution_Action() {
		return CentralHeatingDistribution_Action;	}

	public void setCentralHeatingDistribution_Action(final Enum1060 CentralHeatingDistribution_Action) {
		this.CentralHeatingDistribution_Action = CentralHeatingDistribution_Action;	}

	public Enum1282 getPRIMARYHEATINGCONTROLS_STORGAEHEATER() {
		return PRIMARYHEATINGCONTROLS_STORGAEHEATER;	}

	public void setPRIMARYHEATINGCONTROLS_STORGAEHEATER(final Enum1282 PRIMARYHEATINGCONTROLS_STORGAEHEATER) {
		this.PRIMARYHEATINGCONTROLS_STORGAEHEATER = PRIMARYHEATINGCONTROLS_STORGAEHEATER;	}

	public Enum1060 getSeparateInstantaneousHeaterMultiPoin_FINWMPAC() {
		return SeparateInstantaneousHeaterMultiPoin_FINWMPAC;	}

	public void setSeparateInstantaneousHeaterMultiPoin_FINWMPAC(final Enum1060 SeparateInstantaneousHeaterMultiPoin_FINWMPAC) {
		this.SeparateInstantaneousHeaterMultiPoin_FINWMPAC = SeparateInstantaneousHeaterMultiPoin_FINWMPAC;	}

	public Enum10 getPrimaryHeatingSystem_MainHeatSourc() {
		return PrimaryHeatingSystem_MainHeatSourc;	}

	public void setPrimaryHeatingSystem_MainHeatSourc(final Enum10 PrimaryHeatingSystem_MainHeatSourc) {
		this.PrimaryHeatingSystem_MainHeatSourc = PrimaryHeatingSystem_MainHeatSourc;	}

	public Enum1742 getSeparateInstantaneousHeaterSinglePoi_FINWSPTY() {
		return SeparateInstantaneousHeaterSinglePoi_FINWSPTY;	}

	public void setSeparateInstantaneousHeaterSinglePoi_FINWSPTY(final Enum1742 SeparateInstantaneousHeaterSinglePoi_FINWSPTY) {
		this.SeparateInstantaneousHeaterSinglePoi_FINWSPTY = SeparateInstantaneousHeaterSinglePoi_FINWSPTY;	}

	public Enum1764 getElectricalSystem_TypeOfWiring() {
		return ElectricalSystem_TypeOfWiring;	}

	public void setElectricalSystem_TypeOfWiring(final Enum1764 ElectricalSystem_TypeOfWiring) {
		this.ElectricalSystem_TypeOfWiring = ElectricalSystem_TypeOfWiring;	}

	public Enum1233 getHEALTHANDSAFETY_UncombustedGas() {
		return HEALTHANDSAFETY_UncombustedGas;	}

	public void setHEALTHANDSAFETY_UncombustedGas(final Enum1233 HEALTHANDSAFETY_UncombustedGas) {
		this.HEALTHANDSAFETY_UncombustedGas = HEALTHANDSAFETY_UncombustedGas;	}

	public Enum1766 getHotWaterCylinder_Insulation() {
		return HotWaterCylinder_Insulation;	}

	public void setHotWaterCylinder_Insulation(final Enum1766 HotWaterCylinder_Insulation) {
		this.HotWaterCylinder_Insulation = HotWaterCylinder_Insulation;	}

	public Enum1233 getHEALTHANDSAFETY_ElectricalSafety() {
		return HEALTHANDSAFETY_ElectricalSafety;	}

	public void setHEALTHANDSAFETY_ElectricalSafety(final Enum1233 HEALTHANDSAFETY_ElectricalSafety) {
		this.HEALTHANDSAFETY_ElectricalSafety = HEALTHANDSAFETY_ElectricalSafety;	}

	public Enum1282 getPrimaryHeatingControls_CentralTimer() {
		return PrimaryHeatingControls_CentralTimer;	}

	public void setPrimaryHeatingControls_CentralTimer(final Enum1282 PrimaryHeatingControls_CentralTimer) {
		this.PrimaryHeatingControls_CentralTimer = PrimaryHeatingControls_CentralTimer;	}

	public Enum10 getElectricalSystem_OffPeakSupply() {
		return ElectricalSystem_OffPeakSupply;	}

	public void setElectricalSystem_OffPeakSupply(final Enum10 ElectricalSystem_OffPeakSupply) {
		this.ElectricalSystem_OffPeakSupply = ElectricalSystem_OffPeakSupply;	}

	public Enum1770 getElectricalSystem_OverloadProtection() {
		return ElectricalSystem_OverloadProtection;	}

	public void setElectricalSystem_OverloadProtection(final Enum1770 ElectricalSystem_OverloadProtection) {
		this.ElectricalSystem_OverloadProtection = ElectricalSystem_OverloadProtection;	}

	public Enum1771 getHotWaterCylinder_Thickness_Mms_() {
		return HotWaterCylinder_Thickness_Mms_;	}

	public void setHotWaterCylinder_Thickness_Mms_(final Enum1771 HotWaterCylinder_Thickness_Mms_) {
		this.HotWaterCylinder_Thickness_Mms_ = HotWaterCylinder_Thickness_Mms_;	}

	public Enum10 getSeparateInstantaneousHeaterSinglePoi_FINWSPPR() {
		return SeparateInstantaneousHeaterSinglePoi_FINWSPPR;	}

	public void setSeparateInstantaneousHeaterSinglePoi_FINWSPPR(final Enum10 SeparateInstantaneousHeaterSinglePoi_FINWSPPR) {
		this.SeparateInstantaneousHeaterSinglePoi_FINWSPPR = SeparateInstantaneousHeaterSinglePoi_FINWSPPR;	}

	public Enum1282 getCylinder_Present() {
		return Cylinder_Present;	}

	public void setCylinder_Present(final Enum1282 Cylinder_Present) {
		this.Cylinder_Present = Cylinder_Present;	}

	public Enum1233 getHEALTHANDSAFETY_CarbonMonoxide() {
		return HEALTHANDSAFETY_CarbonMonoxide;	}

	public void setHEALTHANDSAFETY_CarbonMonoxide(final Enum1233 HEALTHANDSAFETY_CarbonMonoxide) {
		this.HEALTHANDSAFETY_CarbonMonoxide = HEALTHANDSAFETY_CarbonMonoxide;	}

	public Enum10 getDualImmersionHeater_Present() {
		return DualImmersionHeater_Present;	}

	public void setDualImmersionHeater_Present(final Enum10 DualImmersionHeater_Present) {
		this.DualImmersionHeater_Present = DualImmersionHeater_Present;	}

	public Enum1776 getOtherHeating_TypeOfSystem() {
		return OtherHeating_TypeOfSystem;	}

	public void setOtherHeating_TypeOfSystem(final Enum1776 OtherHeating_TypeOfSystem) {
		this.OtherHeating_TypeOfSystem = OtherHeating_TypeOfSystem;	}

	public Enum1777 getMainHeatingFuel() {
		return MainHeatingFuel;	}

	public void setMainHeatingFuel(final Enum1777 MainHeatingFuel) {
		this.MainHeatingFuel = MainHeatingFuel;	}

	public Enum10 getSeparateInstantaneousHeaterMultiPoin_FINWMPPR() {
		return SeparateInstantaneousHeaterMultiPoin_FINWMPPR;	}

	public void setSeparateInstantaneousHeaterMultiPoin_FINWMPPR(final Enum10 SeparateInstantaneousHeaterMultiPoin_FINWMPPR) {
		this.SeparateInstantaneousHeaterMultiPoin_FINWMPPR = SeparateInstantaneousHeaterMultiPoin_FINWMPPR;	}

	public Enum1779 getBoiler_WaterHeatingOnly__Type_Fuel() {
		return Boiler_WaterHeatingOnly__Type_Fuel;	}

	public void setBoiler_WaterHeatingOnly__Type_Fuel(final Enum1779 Boiler_WaterHeatingOnly__Type_Fuel) {
		this.Boiler_WaterHeatingOnly__Type_Fuel = Boiler_WaterHeatingOnly__Type_Fuel;	}

	public Enum10 getElectricalSystem_Present() {
		return ElectricalSystem_Present;	}

	public void setElectricalSystem_Present(final Enum10 ElectricalSystem_Present) {
		this.ElectricalSystem_Present = ElectricalSystem_Present;	}

	public Enum10 getCommunal_Present_() {
		return Communal_Present_;	}

	public void setCommunal_Present_(final Enum10 Communal_Present_) {
		this.Communal_Present_ = Communal_Present_;	}

	public Enum1779 getBackBoiler_Type_Fuel() {
		return BackBoiler_Type_Fuel;	}

	public void setBackBoiler_Type_Fuel(final Enum1779 BackBoiler_Type_Fuel) {
		this.BackBoiler_Type_Fuel = BackBoiler_Type_Fuel;	}

	public Enum1282 getPrimaryHeatingControls_Thermostatic() {
		return PrimaryHeatingControls_Thermostatic;	}

	public void setPrimaryHeatingControls_Thermostatic(final Enum1282 PrimaryHeatingControls_Thermostatic) {
		this.PrimaryHeatingControls_Thermostatic = PrimaryHeatingControls_Thermostatic;	}

	public Enum1784 getElectricalSystem_EarthingOfWires() {
		return ElectricalSystem_EarthingOfWires;	}

	public void setElectricalSystem_EarthingOfWires(final Enum1784 ElectricalSystem_EarthingOfWires) {
		this.ElectricalSystem_EarthingOfWires = ElectricalSystem_EarthingOfWires;	}

	public Enum1060 getBackBoiler_Action() {
		return BackBoiler_Action;	}

	public void setBackBoiler_Action(final Enum1060 BackBoiler_Action) {
		this.BackBoiler_Action = BackBoiler_Action;	}

	public Enum1282 getPrimaryHeatingControls_ManualOverri() {
		return PrimaryHeatingControls_ManualOverri;	}

	public void setPrimaryHeatingControls_ManualOverri(final Enum1282 PrimaryHeatingControls_ManualOverri) {
		this.PrimaryHeatingControls_ManualOverri = PrimaryHeatingControls_ManualOverri;	}

	public Enum1282 getRoofInsulationAboveLivingSpace() {
		return RoofInsulationAboveLivingSpace;	}

	public void setRoofInsulationAboveLivingSpace(final Enum1282 RoofInsulationAboveLivingSpace) {
		this.RoofInsulationAboveLivingSpace = RoofInsulationAboveLivingSpace;	}

	public Enum1060 getGasSystem_Action() {
		return GasSystem_Action;	}

	public void setGasSystem_Action(final Enum1060 GasSystem_Action) {
		this.GasSystem_Action = GasSystem_Action;	}

}

