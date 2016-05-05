/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.AbstractCooker;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.ElectricCooker;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.SimpleCooker;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cooker</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#isHob <em>Hob</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#isOven <em>Oven</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getFuelType <em>Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getBaseLoad <em>Base Load</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getOccupancyFactor <em>Occupancy Factor</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl#getGainsFactor <em>Gains Factor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CookerImpl extends MinimalEObjectImpl implements ICooker {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #isHob() <em>Hob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHob()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HOB_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isHob() <em>Hob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHob()
	 * @generated
	 * @ordered
	 */
	protected static final int HOB_EFLAG = 1 << 0;

	/**
	 * The default value of the '{@link #isOven() <em>Oven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOven()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OVEN_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isOven() <em>Oven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOven()
	 * @generated
	 * @ordered
	 */
	protected static final int OVEN_EFLAG = 1 << 1;

	/**
	 * The default value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final FuelType FUEL_TYPE_EDEFAULT = FuelType.MAINS_GAS;

	/**
	 * The offset of the flags representing the value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_TYPE_EFLAG_OFFSET = 2;

	/**
	 * The flags representing the default value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_TYPE_EFLAG_DEFAULT = FUEL_TYPE_EDEFAULT.ordinal() << FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FuelType Fuel Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FuelType[] FUEL_TYPE_EFLAG_VALUES = FuelType.values();

	/**
	 * The flags representing the value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_TYPE_EFLAG = 0xf << FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getBaseLoad() <em>Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseLoad()
	 * @generated
	 * @ordered
	 */
	protected static final double BASE_LOAD_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getBaseLoad() <em>Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseLoad()
	 * @generated
	 * @ordered
	 */
	protected double baseLoad = BASE_LOAD_EDEFAULT;

	/**
	 * The default value of the '{@link #getOccupancyFactor() <em>Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOccupancyFactor()
	 * @generated
	 * @ordered
	 */
	protected static final double OCCUPANCY_FACTOR_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getOccupancyFactor() <em>Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOccupancyFactor()
	 * @generated
	 * @ordered
	 */
	protected double occupancyFactor = OCCUPANCY_FACTOR_EDEFAULT;

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
	public boolean isHob() {
		return (flags & HOB_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHob(boolean newHob) {
		boolean oldHob = (flags & HOB_EFLAG) != 0;
		if (newHob) flags |= HOB_EFLAG; else flags &= ~HOB_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__HOB, oldHob, newHob));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOven() {
		return (flags & OVEN_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOven(boolean newOven) {
		boolean oldOven = (flags & OVEN_EFLAG) != 0;
		if (newOven) flags |= OVEN_EFLAG; else flags &= ~OVEN_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__OVEN, oldOven, newOven));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FuelType getFuelType() {
		return FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFuelType(FuelType newFuelType) {
		FuelType oldFuelType = FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET];
		if (newFuelType == null) newFuelType = FUEL_TYPE_EDEFAULT;
		flags = flags & ~FUEL_TYPE_EFLAG | newFuelType.ordinal() << FUEL_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__FUEL_TYPE, oldFuelType, newFuelType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getBaseLoad() {
		return baseLoad;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseLoad(double newBaseLoad) {
		double oldBaseLoad = baseLoad;
		baseLoad = newBaseLoad;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__BASE_LOAD, oldBaseLoad, baseLoad));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getOccupancyFactor() {
		return occupancyFactor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOccupancyFactor(double newOccupancyFactor) {
		double oldOccupancyFactor = occupancyFactor;
		occupancyFactor = newOccupancyFactor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COOKER__OCCUPANCY_FACTOR, oldOccupancyFactor, occupancyFactor));
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
		final AbstractCooker cooker;
		if (getFuelType() == FuelType.ELECTRICITY) {
			cooker = new ElectricCooker();
		} else {
			cooker = new SimpleCooker(getFuelType().getEnergyType());
		}
		
		cooker.setHob(isHob());
		cooker.setOven(isOven());
		cooker.setBaseLoad(getBaseLoad());
		cooker.setPersonSensitivity(getOccupancyFactor());
		cooker.setEfficiency(getGainsFactor());
		visitor.visitEnergyTransducer(cooker);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.COOKER__HOB:
				return isHob();
			case ITechnologiesPackage.COOKER__OVEN:
				return isOven();
			case ITechnologiesPackage.COOKER__FUEL_TYPE:
				return getFuelType();
			case ITechnologiesPackage.COOKER__BASE_LOAD:
				return getBaseLoad();
			case ITechnologiesPackage.COOKER__OCCUPANCY_FACTOR:
				return getOccupancyFactor();
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
			case ITechnologiesPackage.COOKER__HOB:
				setHob((Boolean)newValue);
				return;
			case ITechnologiesPackage.COOKER__OVEN:
				setOven((Boolean)newValue);
				return;
			case ITechnologiesPackage.COOKER__FUEL_TYPE:
				setFuelType((FuelType)newValue);
				return;
			case ITechnologiesPackage.COOKER__BASE_LOAD:
				setBaseLoad((Double)newValue);
				return;
			case ITechnologiesPackage.COOKER__OCCUPANCY_FACTOR:
				setOccupancyFactor((Double)newValue);
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
			case ITechnologiesPackage.COOKER__HOB:
				setHob(HOB_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__OVEN:
				setOven(OVEN_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__FUEL_TYPE:
				setFuelType(FUEL_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__BASE_LOAD:
				setBaseLoad(BASE_LOAD_EDEFAULT);
				return;
			case ITechnologiesPackage.COOKER__OCCUPANCY_FACTOR:
				setOccupancyFactor(OCCUPANCY_FACTOR_EDEFAULT);
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
			case ITechnologiesPackage.COOKER__HOB:
				return ((flags & HOB_EFLAG) != 0) != HOB_EDEFAULT;
			case ITechnologiesPackage.COOKER__OVEN:
				return ((flags & OVEN_EFLAG) != 0) != OVEN_EDEFAULT;
			case ITechnologiesPackage.COOKER__FUEL_TYPE:
				return (flags & FUEL_TYPE_EFLAG) != FUEL_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.COOKER__BASE_LOAD:
				return baseLoad != BASE_LOAD_EDEFAULT;
			case ITechnologiesPackage.COOKER__OCCUPANCY_FACTOR:
				return occupancyFactor != OCCUPANCY_FACTOR_EDEFAULT;
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
		result.append(" (hob: ");
		result.append((flags & HOB_EFLAG) != 0);
		result.append(", oven: ");
		result.append((flags & OVEN_EFLAG) != 0);
		result.append(", fuelType: ");
		result.append(FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET]);
		result.append(", baseLoad: ");
		result.append(baseLoad);
		result.append(", occupancyFactor: ");
		result.append(occupancyFactor);
		result.append(", gainsFactor: ");
		result.append(gainsFactor);
		result.append(')');
		return result.toString();
	}

} //CookerImpl
