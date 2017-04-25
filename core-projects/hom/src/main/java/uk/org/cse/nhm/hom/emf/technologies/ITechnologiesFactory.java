/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage
 * @generated
 */
public interface ITechnologiesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ITechnologiesFactory eINSTANCE = uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Technology Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Technology Model</em>'.
	 * @generated
	 */
	ITechnologyModel createTechnologyModel();

	/**
	 * Returns a new object of class '<em>Appliance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Appliance</em>'.
	 * @generated
	 */
	IAppliance createAppliance();

	/**
	 * Returns a new object of class '<em>Light</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Light</em>'.
	 * @generated
	 */
	ILight createLight();

	/**
	 * Returns a new object of class '<em>Central Water System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Central Water System</em>'.
	 * @generated
	 */
	ICentralWaterSystem createCentralWaterSystem();

	/**
	 * Returns a new object of class '<em>Central Heating System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Central Heating System</em>'.
	 * @generated
	 */
	ICentralHeatingSystem createCentralHeatingSystem();

	/**
	 * Returns a new object of class '<em>Main Water Heater</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Main Water Heater</em>'.
	 * @generated
	 */
	IMainWaterHeater createMainWaterHeater();

	/**
	 * Returns a new object of class '<em>Water Tank</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Water Tank</em>'.
	 * @generated
	 */
	IWaterTank createWaterTank();

	/**
	 * Returns a new object of class '<em>Solar Water Heater</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Solar Water Heater</em>'.
	 * @generated
	 */
	ISolarWaterHeater createSolarWaterHeater();

	/**
	 * Returns a new object of class '<em>Immersion Heater</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Immersion Heater</em>'.
	 * @generated
	 */
	IImmersionHeater createImmersionHeater();

	/**
	 * Returns a new object of class '<em>Cooker</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cooker</em>'.
	 * @generated
	 */
	ICooker createCooker();

	/**
	 * Returns a new object of class '<em>Storage Heater</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Storage Heater</em>'.
	 * @generated
	 */
	IStorageHeater createStorageHeater();

	/**
	 * Returns a new object of class '<em>Community Heat Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Community Heat Source</em>'.
	 * @generated
	 */
	ICommunityHeatSource createCommunityHeatSource();

	/**
	 * Returns a new object of class '<em>Community CHP</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Community CHP</em>'.
	 * @generated
	 */
	ICommunityCHP createCommunityCHP();

	/**
	 * Returns a new object of class '<em>Room Heater</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Room Heater</em>'.
	 * @generated
	 */
	IRoomHeater createRoomHeater();

	/**
	 * Returns a new object of class '<em>Heat Pump</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Heat Pump</em>'.
	 * @generated
	 */
	IHeatPump createHeatPump();

	/**
	 * Returns a new object of class '<em>Warm Air System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Warm Air System</em>'.
	 * @generated
	 */
	IWarmAirSystem createWarmAirSystem();

	/**
	 * Returns a new object of class '<em>Point Of Use Water Heater</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Point Of Use Water Heater</em>'.
	 * @generated
	 */
	IPointOfUseWaterHeater createPointOfUseWaterHeater();

	/**
	 * Returns a new object of class '<em>Heat Pump Warm Air System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Heat Pump Warm Air System</em>'.
	 * @generated
	 */
	IHeatPumpWarmAirSystem createHeatPumpWarmAirSystem();

	/**
	 * Returns a new object of class '<em>Warm Air Circulator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Warm Air Circulator</em>'.
	 * @generated
	 */
	IWarmAirCirculator createWarmAirCirculator();

	/**
	 * Returns a new object of class '<em>Back Boiler</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Back Boiler</em>'.
	 * @generated
	 */
	IBackBoiler createBackBoiler();

	/**
	 * Returns a new object of class '<em>Operational Cost</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operational Cost</em>'.
	 * @generated
	 */
	IOperationalCost createOperationalCost();

	/**
	 * Returns a new object of class '<em>Solar Photovoltaic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Solar Photovoltaic</em>'.
	 * @generated
	 */
	ISolarPhotovoltaic createSolarPhotovoltaic();

	/**
	 * Returns a new object of class '<em>Adjuster</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adjuster</em>'.
	 * @generated
	 */
	IAdjuster createAdjuster();

	/**
	 * Returns a new object of class '<em>Hybrid Heater</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hybrid Heater</em>'.
	 * @generated
	 */
	IHybridHeater createHybridHeater();

	/**
	 * Returns a new object of class '<em>Mixer Shower</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mixer Shower</em>'.
	 * @generated
	 */
	IMixerShower createMixerShower();

	/**
	 * Returns a new object of class '<em>Electric Shower</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Electric Shower</em>'.
	 * @generated
	 */
	IElectricShower createElectricShower();

	/**
	 * Returns a new object of class '<em>Energy Use Adjuster</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Energy Use Adjuster</em>'.
	 * @generated
	 */
	IEnergyUseAdjuster createEnergyUseAdjuster();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ITechnologiesPackage getTechnologiesPackage();

} //ITechnologiesFactory
