/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.common.util.EList;

import uk.org.cse.nhm.hom.ICopyable;
import uk.org.cse.nhm.hom.IHeatProportions;

import uk.org.cse.nhm.hom.emf.technologies.IShower;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Technology Model</b></em>'.
 *
 * WARNING: After regenerating, you have to add the ICopyable<ITechnologyModel> interface.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The technology model is the container for all of the technologies in a house.
 * It can accept an IEnergyCalculatorVisitor, and also has a method {#getTotalOperationalCost()}
 * for summing the operational cost of all the cost-bearing objects contained within it.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getAppliances <em>Appliances</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getLights <em>Lights</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getIndividualHeatSource <em>Individual Heat Source</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCookers <em>Cookers</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getPrimarySpaceHeater <em>Primary Space Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondarySpaceHeater <em>Secondary Space Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCentralWaterSystem <em>Central Water System</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondaryWaterHeater <em>Secondary Water Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCommunityHeatSource <em>Community Heat Source</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSolarPhotovoltaic <em>Solar Photovoltaic</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getAdjusters <em>Adjusters</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getShower <em>Shower</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getEnergyUseAdjusters <em>Energy Use Adjusters</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel()
 * @model
 * @generated
 */
@JsonTypeInfo(use = Id.NONE)
public interface ITechnologyModel extends IVisitorAccepter, ICopyable<ITechnologyModel> {
	/**
	 * Returns the value of the '<em><b>Appliances</b></em>' containment reference list.
	 * The list contents are of type {@link uk.org.cse.nhm.hom.emf.technologies.IAppliance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Appliances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Appliances</em>' containment reference list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_Appliances()
	 * @model containment="true"
	 * @generated
	 */
	EList<IAppliance> getAppliances();

	/**
	 * Returns the value of the '<em><b>Lights</b></em>' containment reference list.
	 * The list contents are of type {@link uk.org.cse.nhm.hom.emf.technologies.ILight}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lights</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Contains all of the lights in the house.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Lights</em>' containment reference list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_Lights()
	 * @model containment="true"
	 * @generated
	 */
	EList<ILight> getLights();

	/**
	 * Returns the value of the '<em><b>Individual Heat Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual Heat Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Contains the house's individual heat source. At the moment a house can only have one of these;
	 * it is something like a boiler or a heat pump.
	 * 
	 * This must be connected to a heating or DHW system to do anything.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Individual Heat Source</em>' containment reference.
	 * @see #setIndividualHeatSource(IIndividualHeatSource)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_IndividualHeatSource()
	 * @model containment="true"
	 * @generated
	 */
	IIndividualHeatSource getIndividualHeatSource();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getIndividualHeatSource <em>Individual Heat Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Individual Heat Source</em>' containment reference.
	 * @see #getIndividualHeatSource()
	 * @generated
	 */
	void setIndividualHeatSource(IIndividualHeatSource value);

	/**
	 * Returns the value of the '<em><b>Cookers</b></em>' containment reference list.
	 * The list contents are of type {@link uk.org.cse.nhm.hom.emf.technologies.ICooker}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cookers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Contains all the cookers in the house
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cookers</em>' containment reference list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_Cookers()
	 * @model containment="true"
	 * @generated
	 */
	EList<ICooker> getCookers();

	/**
	 * Returns the value of the '<em><b>Primary Space Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Space Heater</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This is the main space heating system in the house
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Primary Space Heater</em>' containment reference.
	 * @see #setPrimarySpaceHeater(IPrimarySpaceHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_PrimarySpaceHeater()
	 * @model containment="true"
	 * @generated
	 */
	IPrimarySpaceHeater getPrimarySpaceHeater();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getPrimarySpaceHeater <em>Primary Space Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Space Heater</em>' containment reference.
	 * @see #getPrimarySpaceHeater()
	 * @generated
	 */
	void setPrimarySpaceHeater(IPrimarySpaceHeater value);

	/**
	 * Returns the value of the '<em><b>Secondary Space Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Space Heater</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An optional secondary space heater which may supply some heat
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Secondary Space Heater</em>' containment reference.
	 * @see #setSecondarySpaceHeater(IRoomHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_SecondarySpaceHeater()
	 * @model containment="true"
	 * @generated
	 */
	IRoomHeater getSecondarySpaceHeater();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondarySpaceHeater <em>Secondary Space Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Space Heater</em>' containment reference.
	 * @see #getSecondarySpaceHeater()
	 * @generated
	 */
	void setSecondarySpaceHeater(IRoomHeater value);

	/**
	 * Returns the value of the '<em><b>Central Water System</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getTechnologyModel <em>Technology Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Central Water System</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the house has central DHW, it is presumed to have one DHW system which will be here
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Central Water System</em>' containment reference.
	 * @see #setCentralWaterSystem(ICentralWaterSystem)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_CentralWaterSystem()
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getTechnologyModel
	 * @model opposite="technologyModel" containment="true"
	 * @generated
	 */
	ICentralWaterSystem getCentralWaterSystem();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCentralWaterSystem <em>Central Water System</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Central Water System</em>' containment reference.
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	void setCentralWaterSystem(ICentralWaterSystem value);

	/**
	 * Returns the value of the '<em><b>Secondary Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Water Heater</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A non-central DHW system, like a point-of-use water heater
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Secondary Water Heater</em>' containment reference.
	 * @see #setSecondaryWaterHeater(IWaterHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_SecondaryWaterHeater()
	 * @model containment="true"
	 * @generated
	 */
	IWaterHeater getSecondaryWaterHeater();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondaryWaterHeater <em>Secondary Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Water Heater</em>' containment reference.
	 * @see #getSecondaryWaterHeater()
	 * @generated
	 */
	void setSecondaryWaterHeater(IWaterHeater value);

	/**
	 * Returns the value of the '<em><b>Community Heat Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Community Heat Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Represents the connection (if any) to a community heating scheme
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Community Heat Source</em>' containment reference.
	 * @see #setCommunityHeatSource(ICommunityHeatSource)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_CommunityHeatSource()
	 * @model containment="true"
	 * @generated
	 */
	ICommunityHeatSource getCommunityHeatSource();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCommunityHeatSource <em>Community Heat Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Community Heat Source</em>' containment reference.
	 * @see #getCommunityHeatSource()
	 * @generated
	 */
	void setCommunityHeatSource(ICommunityHeatSource value);

	/**
	 * Returns the value of the '<em><b>Solar Photovoltaic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solar Photovoltaic</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solar Photovoltaic</em>' containment reference.
	 * @see #setSolarPhotovoltaic(ISolarPhotovoltaic)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_SolarPhotovoltaic()
	 * @model containment="true"
	 * @generated
	 */
	ISolarPhotovoltaic getSolarPhotovoltaic();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSolarPhotovoltaic <em>Solar Photovoltaic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solar Photovoltaic</em>' containment reference.
	 * @see #getSolarPhotovoltaic()
	 * @generated
	 */
	void setSolarPhotovoltaic(ISolarPhotovoltaic value);

	/**
	 * Returns the value of the '<em><b>Adjusters</b></em>' containment reference list.
	 * The list contents are of type {@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adjusters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adjusters</em>' containment reference list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_Adjusters()
	 * @model containment="true"
	 * @generated
	 */
	EList<IAdjuster> getAdjusters();

	/**
	 * Returns the value of the '<em><b>Shower</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.IShower#getTechnologyModel <em>Technology Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shower</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shower</em>' containment reference.
	 * @see #setShower(IShower)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_Shower()
	 * @see uk.org.cse.nhm.hom.emf.technologies.IShower#getTechnologyModel
	 * @model opposite="technologyModel" containment="true"
	 * @generated
	 */
	IShower getShower();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getShower <em>Shower</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shower</em>' containment reference.
	 * @see #getShower()
	 * @generated
	 */
	void setShower(IShower value);

	/**
	 * Returns the value of the '<em><b>Energy Use Adjusters</b></em>' containment reference list.
	 * The list contents are of type {@link uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Represents a list of adjustments that can be made to Appliance or Cooking Energy use
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Energy Use Adjusters</em>' containment reference list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getTechnologyModel_EnergyUseAdjusters()
	 * @model containment="true" upper="2"
	 * @generated
	 */
	EList<IEnergyUseAdjuster> getEnergyUseAdjusters();

	/**
	 * <!-- begin-user-doc -->
	 * @since 1.1.0
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns sum of {@link IOperationalCost#getAnnualOperationalCost()} for all contained objects which implement
	 * {@link IOperationalCost}
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (operationalCostCache == null) {\n\tfinal TreeIterator<EObject> children = eAllContents();\n\tdouble accumulator = 0;\n\twhile (children.hasNext()) {\n\t\tfinal EObject child = children.next();\n\t\tif (child instanceof IOperationalCost) {\n\t\t\taccumulator += ((IOperationalCost)child).getAnnualOperationalCost();\n\t\t}\n\t}\n\toperationalCostCache = accumulator;\n\teAdapters().add(cacheEraser);\n}\n\t\t\nreturn operationalCostCache;'"
	 * @generated
	 */
	double getTotalOperationalCost();

	/**
	 * @generated NO
	 */
	IHeatProportions getHeatProportions();

} // ITechnologyModel
