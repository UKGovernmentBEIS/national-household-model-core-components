/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cooker</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getHobBaseLoad <em>Hob Base Load</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getHobOccupancyFactor <em>Hob Occupancy Factor</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getHobFuelType <em>Hob Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getOvenBaseLoad <em>Oven Base Load</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getOvenOccupancyFactor <em>Oven Occupancy Factor</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getOvenFuelType <em>Oven Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getGainsFactor <em>Gains Factor</em>}</li>
 * </ul>
 *
 * @generated no (extra interface) - this generated flag just affects the implemented interface list.
 */
public class CookerImpl extends MinimalEObjectImpl implements ICooker, IEnergyTransducer {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #getHobBaseLoad() <em>Hob Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHobBaseLoad()
	 * @generated
	 * @ordered
	 */
	protected static final double HOB_BASE_LOAD_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHobBaseLoad() <em>Hob Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHobBaseLoad()
	 * @generated
	 * @ordered
	 */
	protected double hobBaseLoad = HOB_BASE_LOAD_EDEFAULT;

	/**
	 * The default value of the '{@link #getHobOccupancyFactor() <em>Hob Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHobOccupancyFactor()
	 * @generated
	 * @ordered
	 */
	protected static final double HOB_OCCUPANCY_FACTOR_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHobOccupancyFactor() <em>Hob Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHobOccupancyFactor()
	 * @generated
	 * @ordered
	 */
	protected double hobOccupancyFactor = HOB_OCCUPANCY_FACTOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getHobFuelType() <em>Hob Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHobFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final FuelType HOB_FUEL_TYPE_EDEFAULT = FuelType.MAINS_GAS;

	/**
	 * The offset of the flags representing the value of the '{@link #getHobFuelType() <em>Hob Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int HOB_FUEL_TYPE_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getHobFuelType() <em>Hob Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int HOB_FUEL_TYPE_EFLAG_DEFAULT = HOB_FUEL_TYPE_EDEFAULT.ordinal() << HOB_FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FuelType Fuel Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FuelType[] HOB_FUEL_TYPE_EFLAG_VALUES = FuelType.values();

	/**
	 * The flags representing the value of the '{@link #getHobFuelType() <em>Hob Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHobFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final int HOB_FUEL_TYPE_EFLAG = 0xf << HOB_FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getOvenBaseLoad() <em>Oven Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOvenBaseLoad()
	 * @generated
	 * @ordered
	 */
	protected static final double OVEN_BASE_LOAD_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getOvenBaseLoad() <em>Oven Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOvenBaseLoad()
	 * @generated
	 * @ordered
	 */
	protected double ovenBaseLoad = OVEN_BASE_LOAD_EDEFAULT;

	/**
	 * The default value of the '{@link #getOvenOccupancyFactor() <em>Oven Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOvenOccupancyFactor()
	 * @generated
	 * @ordered
	 */
	protected static final double OVEN_OCCUPANCY_FACTOR_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getOvenOccupancyFactor() <em>Oven Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOvenOccupancyFactor()
	 * @generated
	 * @ordered
	 */
	protected double ovenOccupancyFactor = OVEN_OCCUPANCY_FACTOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getOvenFuelType() <em>Oven Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOvenFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final FuelType OVEN_FUEL_TYPE_EDEFAULT = FuelType.MAINS_GAS;

	/**
	 * The offset of the flags representing the value of the '{@link #getOvenFuelType() <em>Oven Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int OVEN_FUEL_TYPE_EFLAG_OFFSET = 4;

	/**
	 * The flags representing the default value of the '{@link #getOvenFuelType() <em>Oven Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int OVEN_FUEL_TYPE_EFLAG_DEFAULT = OVEN_FUEL_TYPE_EDEFAULT.ordinal() << OVEN_FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FuelType Fuel Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FuelType[] OVEN_FUEL_TYPE_EFLAG_VALUES = FuelType.values();

	/**
	 * The flags representing the value of the '{@link #getOvenFuelType() <em>Oven Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOvenFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final int OVEN_FUEL_TYPE_EFLAG = 0xf << OVEN_FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getGainsFactor() <em>Gains Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGainsFactor()
	 * @generated
	 * @ordered
	 */
	protected static final double GAINS_FACTOR_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getGainsFactor() <em>Gains Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGainsFactor()
	 * @generated
	 * @ordered
	 */
	protected double gainsFactor = GAINS_FACTOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CookerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.COOKER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getHobBaseLoad() {
		return hobBaseLoad;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHobBaseLoad(double newHobBaseLoad) {
		double oldHobBaseLoad = hobBaseLoad;
		hobBaseLoad = newHobBaseLoad;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__HOB_BASE_LOAD, oldHobBaseLoad, hobBaseLoad));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getHobOccupancyFactor() {
		return hobOccupancyFactor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHobOccupancyFactor(double newHobOccupancyFactor) {
		double oldHobOccupancyFactor = hobOccupancyFactor;
		hobOccupancyFactor = newHobOccupancyFactor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__HOB_OCCUPANCY_FACTOR, oldHobOccupancyFactor, hobOccupancyFactor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FuelType getHobFuelType() {
		return HOB_FUEL_TYPE_EFLAG_VALUES[(flags & HOB_FUEL_TYPE_EFLAG) >>> HOB_FUEL_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHobFuelType(FuelType newHobFuelType) {
		FuelType oldHobFuelType = HOB_FUEL_TYPE_EFLAG_VALUES[(flags & HOB_FUEL_TYPE_EFLAG) >>> HOB_FUEL_TYPE_EFLAG_OFFSET];
		if (newHobFuelType == null) newHobFuelType = HOB_FUEL_TYPE_EDEFAULT;
		flags = flags & ~HOB_FUEL_TYPE_EFLAG | newHobFuelType.ordinal() << HOB_FUEL_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__HOB_FUEL_TYPE, oldHobFuelType, newHobFuelType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getOvenBaseLoad() {
		return ovenBaseLoad;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOvenBaseLoad(double newOvenBaseLoad) {
		double oldOvenBaseLoad = ovenBaseLoad;
		ovenBaseLoad = newOvenBaseLoad;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__OVEN_BASE_LOAD, oldOvenBaseLoad, ovenBaseLoad));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getOvenOccupancyFactor() {
		return ovenOccupancyFactor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOvenOccupancyFactor(double newOvenOccupancyFactor) {
		double oldOvenOccupancyFactor = ovenOccupancyFactor;
		ovenOccupancyFactor = newOvenOccupancyFactor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__OVEN_OCCUPANCY_FACTOR, oldOvenOccupancyFactor, ovenOccupancyFactor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FuelType getOvenFuelType() {
		return OVEN_FUEL_TYPE_EFLAG_VALUES[(flags & OVEN_FUEL_TYPE_EFLAG) >>> OVEN_FUEL_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOvenFuelType(FuelType newOvenFuelType) {
		FuelType oldOvenFuelType = OVEN_FUEL_TYPE_EFLAG_VALUES[(flags & OVEN_FUEL_TYPE_EFLAG) >>> OVEN_FUEL_TYPE_EFLAG_OFFSET];
		if (newOvenFuelType == null) newOvenFuelType = OVEN_FUEL_TYPE_EDEFAULT;
		flags = flags & ~OVEN_FUEL_TYPE_EFLAG | newOvenFuelType.ordinal() << OVEN_FUEL_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__OVEN_FUEL_TYPE, oldOvenFuelType, newOvenFuelType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getGainsFactor() {
		return gainsFactor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGainsFactor(double newGainsFactor) {
		double oldGainsFactor = gainsFactor;
		gainsFactor = newGainsFactor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__GAINS_FACTOR, oldGainsFactor, gainsFactor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	public void accept(IConstants constants, IEnergyCalculatorParameters parameters, IEnergyCalculatorVisitor visitor, AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
		visitor.visitEnergyTransducer(this);
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.COOKING;
	}

	@Override
	public void generate(IEnergyCalculatorHouseCase house, IInternalParameters parameters, ISpecificHeatLosses losses,
			IEnergyState state) {

		/*
		BEISDOC
		NAME: Cooking Fuel Energy Demand
		DESCRIPTION: The amount of fuel consumed by cooking.
		TYPE: formula
		UNIT: W
		BREDEM: 1M,1N,1O
		DEPS: occupancy,electric-oven-base-load,electric-oven-occupancy-factor,electric-hob-base-load,electric-hob-occupancy-factor,gas-hob-base-load,gas-hob-occupancy-factor
		NOTES: We implement the same formula in both SAP and BREDEM energy calculator modes here, because SAP 2012 doesn't define cooking energy demand.
		ID: cooking-demand
		CODSIEB
		*/
		final double ovenDemand = getOvenBaseLoad() + (getOvenOccupancyFactor() * parameters.getNumberOfOccupants());
		increaseFuelDemandMaybeElectricity(state, parameters, getOvenFuelType(), ovenDemand);
		
		final double hobDemand = getHobBaseLoad() + (getHobOccupancyFactor() * parameters.getNumberOfOccupants());
		increaseFuelDemandMaybeElectricity(state, parameters, getHobFuelType(), hobDemand);
		
		state.increaseSupply(
				EnergyType.GainsCOOKING_GAINS,
				computeGains(ovenDemand, hobDemand, house, parameters)
			);
	}
	
	protected void increaseFuelDemandMaybeElectricity(IEnergyState state, IInternalParameters parameters, FuelType fuel, double demand) {
		if (fuel == FuelType.ELECTRICITY) {
			state.increaseElectricityDemand(
					parameters.getConstants().get(SplitRateConstants.DEFAULT_FRACTIONS, double[].class)
					[parameters.getTarrifType().ordinal()], 
					demand
				);
		} else {
			state.increaseDemand(fuel.getEnergyType(), demand);
		}
	}

	protected double computeGains(double ovenDemand, double hobDemand, IEnergyCalculatorHouseCase house,
			IInternalParameters parameters) {
		/*
		BEISDOC
		NAME: Cooking Gains
		DESCRIPTION: The amount of internal heat gains due to cooking.
		TYPE: formula
		UNIT: W
		SAP: Table 5
		BREDEM: Table 25, 6D
		DEPS: sap-base-cooking-gains,sap-cooking-gains-occupancy-factor,electric-cooking-gains-factor,gas-and-electric-cooking-gains-factor,cooking-demand
		NOTES: We implement the different SAP 2012 and BREDEM 2012 formulas for their respective energy calculators here.
		NOTES: Note that the SAP 2012 formula is *entirely unrelated* to the actual amount of energy used for cooking.
		ID: cooking-gains
		CODSIEB
		*/
		switch(parameters.getCalculatorType()) {
		case SAP2012:
			return ICooker.SAP_BASE_GAINS + (ICooker.SAP_GAINS_OCCUPANCY_FACTOR * parameters.getNumberOfOccupants());
		case BREDEM2012:
			return (ovenDemand + hobDemand) * getGainsFactor();
		default:
			throw new UnsupportedOperationException("Unknown energy calculator type while computing cooking gains " + parameters.getCalculatorType());
		}
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeEverything;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.COOKER__HOB_BASE_LOAD:
				return getHobBaseLoad();
			case ITechnologiesPackage.COOKER__HOB_OCCUPANCY_FACTOR:
				return getHobOccupancyFactor();
			case ITechnologiesPackage.COOKER__HOB_FUEL_TYPE:
				return getHobFuelType();
			case ITechnologiesPackage.COOKER__OVEN_BASE_LOAD:
				return getOvenBaseLoad();
			case ITechnologiesPackage.COOKER__OVEN_OCCUPANCY_FACTOR:
				return getOvenOccupancyFactor();
			case ITechnologiesPackage.COOKER__OVEN_FUEL_TYPE:
				return getOvenFuelType();
			case ITechnologiesPackage.COOKER__GAINS_FACTOR:
				return getGainsFactor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.COOKER__HOB_BASE_LOAD:
				setHobBaseLoad((Double)newValue);
				return;
			case ITechnologiesPackage.COOKER__HOB_OCCUPANCY_FACTOR:
				setHobOccupancyFactor((Double)newValue);
				return;
			case ITechnologiesPackage.COOKER__HOB_FUEL_TYPE:
				setHobFuelType((FuelType)newValue);
				return;
			case ITechnologiesPackage.COOKER__OVEN_BASE_LOAD:
				setOvenBaseLoad((Double)newValue);
				return;
			case ITechnologiesPackage.COOKER__OVEN_OCCUPANCY_FACTOR:
				setOvenOccupancyFactor((Double)newValue);
				return;
			case ITechnologiesPackage.COOKER__OVEN_FUEL_TYPE:
				setOvenFuelType((FuelType)newValue);
				return;
			case ITechnologiesPackage.COOKER__GAINS_FACTOR:
				setGainsFactor((Double)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.COOKER__HOB_BASE_LOAD:
				setHobBaseLoad(HOB_BASE_LOAD_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__HOB_OCCUPANCY_FACTOR:
				setHobOccupancyFactor(HOB_OCCUPANCY_FACTOR_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__HOB_FUEL_TYPE:
				setHobFuelType(HOB_FUEL_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__OVEN_BASE_LOAD:
				setOvenBaseLoad(OVEN_BASE_LOAD_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__OVEN_OCCUPANCY_FACTOR:
				setOvenOccupancyFactor(OVEN_OCCUPANCY_FACTOR_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__OVEN_FUEL_TYPE:
				setOvenFuelType(OVEN_FUEL_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__GAINS_FACTOR:
				setGainsFactor(GAINS_FACTOR_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.COOKER__HOB_BASE_LOAD:
				return hobBaseLoad != HOB_BASE_LOAD_EDEFAULT;
			case ITechnologiesPackage.COOKER__HOB_OCCUPANCY_FACTOR:
				return hobOccupancyFactor != HOB_OCCUPANCY_FACTOR_EDEFAULT;
			case ITechnologiesPackage.COOKER__HOB_FUEL_TYPE:
				return (flags & HOB_FUEL_TYPE_EFLAG) != HOB_FUEL_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.COOKER__OVEN_BASE_LOAD:
				return ovenBaseLoad != OVEN_BASE_LOAD_EDEFAULT;
			case ITechnologiesPackage.COOKER__OVEN_OCCUPANCY_FACTOR:
				return ovenOccupancyFactor != OVEN_OCCUPANCY_FACTOR_EDEFAULT;
			case ITechnologiesPackage.COOKER__OVEN_FUEL_TYPE:
				return (flags & OVEN_FUEL_TYPE_EFLAG) != OVEN_FUEL_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.COOKER__GAINS_FACTOR:
				return gainsFactor != GAINS_FACTOR_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (hobBaseLoad: ");
		result.append(hobBaseLoad);
		result.append(", hobOccupancyFactor: ");
		result.append(hobOccupancyFactor);
		result.append(", hobFuelType: ");
		result.append(HOB_FUEL_TYPE_EFLAG_VALUES[(flags & HOB_FUEL_TYPE_EFLAG) >>> HOB_FUEL_TYPE_EFLAG_OFFSET]);
		result.append(", ovenBaseLoad: ");
		result.append(ovenBaseLoad);
		result.append(", ovenOccupancyFactor: ");
		result.append(ovenOccupancyFactor);
		result.append(", ovenFuelType: ");
		result.append(OVEN_FUEL_TYPE_EFLAG_VALUES[(flags & OVEN_FUEL_TYPE_EFLAG) >>> OVEN_FUEL_TYPE_EFLAG_OFFSET]);
		result.append(", gainsFactor: ");
		result.append(gainsFactor);
		result.append(')');
		return result.toString();
	}
	
	/**
	 * Helper method for creating a cooker with the BREDEM 2012 defaults for an electric oven.
	 */
	private static ICooker createElectricOven() {
		final ICooker cooker = ITechnologiesFactory.eINSTANCE.createCooker();
		
		cooker.setOvenBaseLoad(ICooker.ELECTRIC_OVEN_BASE_LOAD);
		cooker.setOvenOccupancyFactor(ICooker.ELECTRIC_OVEN_OCCUPANCY_FACTOR);
		cooker.setOvenFuelType(FuelType.ELECTRICITY);
		
		cooker.setGainsFactor(ICooker.GAS_AND_ELECTRIC_GAINS_FACTOR);
		
		return cooker;
	}
	
	/**
	 * Helper method for creating a cooker with the BREDEM 2012 defaults for an electric oven and hob.
	 */
	public static ICooker createElectric() {
		ICooker cooker = createElectricOven();
		
		cooker.setHobBaseLoad(ICooker.ELECTRIC_HOB_BASE_LOAD);
		cooker.setHobOccupancyFactor(ICooker.ELECTRIC_HOB_OCCUPANCY_FACTOR);
		cooker.setHobFuelType(FuelType.ELECTRICITY);
		
		cooker.setGainsFactor(ICooker.ELECTRIC_GAINS_FACTOR);
		
		return cooker;		
	}
	
	/**
	 * Helper method for creating a cooker with the BREDEM 2012 defaults for an electric oven and mains gas hob.
	 */
	public static ICooker createMixed() {
		ICooker cooker = createElectricOven();
		
		cooker.setHobBaseLoad(ICooker.GAS_HOB_BASE_LOAD);
		cooker.setHobOccupancyFactor(ICooker.GAS_HOB_OCCUPANCY_FACTOR);
		cooker.setHobFuelType(FuelType.MAINS_GAS);
		
		cooker.setGainsFactor(ICooker.GAS_AND_ELECTRIC_GAINS_FACTOR);
		
		return cooker;
	}

} //CookerImpl
