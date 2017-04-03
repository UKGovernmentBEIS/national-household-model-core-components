/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.constants.CylinderConstants;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Water Tank</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl#getInsulation <em>Insulation</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl#isFactoryInsulation <em>Factory Insulation</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl#getVolume <em>Volume</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl#isThermostatFitted <em>Thermostat Fitted</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl#getSolarStorageVolume <em>Solar Storage Volume</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl#getImmersionHeater <em>Immersion Heater</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WaterTankImpl extends MinimalEObjectImpl implements IWaterTank {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #getInsulation() <em>Insulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsulation()
	 * @generated
	 * @ordered
	 */
	protected static final double INSULATION_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getInsulation() <em>Insulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsulation()
	 * @generated
	 * @ordered
	 */
	protected double insulation = INSULATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isFactoryInsulation() <em>Factory Insulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFactoryInsulation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FACTORY_INSULATION_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isFactoryInsulation() <em>Factory Insulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFactoryInsulation()
	 * @generated
	 * @ordered
	 */
	protected static final int FACTORY_INSULATION_EFLAG = 1 << 0;

	/**
	 * The default value of the '{@link #getVolume() <em>Volume</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVolume()
	 * @generated
	 * @ordered
	 */
	protected static final double VOLUME_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getVolume() <em>Volume</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVolume()
	 * @generated
	 * @ordered
	 */
	protected double volume = VOLUME_EDEFAULT;

	/**
	 * The default value of the '{@link #isThermostatFitted() <em>Thermostat Fitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isThermostatFitted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean THERMOSTAT_FITTED_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isThermostatFitted() <em>Thermostat Fitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isThermostatFitted()
	 * @generated
	 * @ordered
	 */
	protected static final int THERMOSTAT_FITTED_EFLAG = 1 << 1;

	/**
	 * The default value of the '{@link #getSolarStorageVolume() <em>Solar Storage Volume</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolarStorageVolume()
	 * @generated
	 * @ordered
	 */
	protected static final double SOLAR_STORAGE_VOLUME_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSolarStorageVolume() <em>Solar Storage Volume</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolarStorageVolume()
	 * @generated
	 * @ordered
	 */
	protected double solarStorageVolume = SOLAR_STORAGE_VOLUME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getImmersionHeater() <em>Immersion Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImmersionHeater()
	 * @generated
	 * @ordered
	 */
	protected IImmersionHeater immersionHeater;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WaterTankImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.WATER_TANK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getInsulation() {
		return insulation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInsulation(double newInsulation) {
		double oldInsulation = insulation;
		insulation = newInsulation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WATER_TANK__INSULATION, oldInsulation, insulation));
	}

	/**
	 * <!-- begin-user-doc -->
	BEISDOC
	NAME: Cylinder factory insulated
	DESCRIPTION: Whether or not the cylinder is factory insulated
	TYPE: value
	UNIT: Boolean
	SAP: Input
        SAP_COMPLIANT: N/A - value from stock
	BREDEM: Input
        BREDEM_COMPLIANT: N/A - value from stock
	STOCK: water-heating.csv (cylinderfactoryinsulated)
	ID: cylinder-insulation-type
	CODSIEB
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFactoryInsulation() {
		return (flags & FACTORY_INSULATION_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFactoryInsulation(boolean newFactoryInsulation) {
		boolean oldFactoryInsulation = (flags & FACTORY_INSULATION_EFLAG) != 0;
		if (newFactoryInsulation) flags |= FACTORY_INSULATION_EFLAG; else flags &= ~FACTORY_INSULATION_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WATER_TANK__FACTORY_INSULATION, oldFactoryInsulation, newFactoryInsulation));
	}

	/**
	 * <!-- begin-user-doc -->
	BEISDOC
	NAME: Cylinder volume
	DESCRIPTION: The volume of the hot water cylinder (or the internal store of a storage combi or CPSU).
	TYPE: value
	UNIT: m^3
	SAP: (47)
        SAP_COMPLIANT: N/A - value from stock
	BREDEM: User input
        BREDEM_COMPLIANT: N/A - value from stock
	SET: cylinder-volume property when installing a measure
	STOCK: water-heating.csv (cylindervolume)
	ID: cylinder-volume
	CODSIEB
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVolume(double newVolume) {
		double oldVolume = volume;
		volume = newVolume;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WATER_TANK__VOLUME, oldVolume, volume));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isThermostatFitted() {
		return (flags & THERMOSTAT_FITTED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThermostatFitted(boolean newThermostatFitted) {
		boolean oldThermostatFitted = (flags & THERMOSTAT_FITTED_EFLAG) != 0;
		if (newThermostatFitted) flags |= THERMOSTAT_FITTED_EFLAG; else flags &= ~THERMOSTAT_FITTED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WATER_TANK__THERMOSTAT_FITTED, oldThermostatFitted, newThermostatFitted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getSolarStorageVolume() {
		return solarStorageVolume;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolarStorageVolume(double newSolarStorageVolume) {
		double oldSolarStorageVolume = solarStorageVolume;
		solarStorageVolume = newSolarStorageVolume;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WATER_TANK__SOLAR_STORAGE_VOLUME, oldSolarStorageVolume, solarStorageVolume));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IImmersionHeater getImmersionHeater() {
		return immersionHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImmersionHeater(IImmersionHeater newImmersionHeater, NotificationChain msgs) {
		IImmersionHeater oldImmersionHeater = immersionHeater;
		immersionHeater = newImmersionHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER, oldImmersionHeater, newImmersionHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImmersionHeater(IImmersionHeater newImmersionHeater) {
		if (newImmersionHeater != immersionHeater) {
			NotificationChain msgs = null;
			if (immersionHeater != null)
				msgs = ((InternalEObject)immersionHeater).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER, null, msgs);
			if (newImmersionHeater != null)
				msgs = ((InternalEObject)newImmersionHeater).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER, null, msgs);
			msgs = basicSetImmersionHeater(newImmersionHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER, newImmersionHeater, newImmersionHeater));
	}

	/**
	 * Get the standing losses in watts for the cylinder.
	 *
	 * This does not take account of SAP Table 2b, the temperature factor, because the tank does not
	 * have enough information to determine the value correctly.
	 *
	 * Instead, it is the responsibility of the central water heating system and the boiler together.
	 *
	 * @generated Not generated any more!
	 */
	@Override
	public double getStandingLosses(final IInternalParameters parameters) {
		/*
		BEISDOC
		NAME: Tank Storage Losses
		DESCRIPTION: Storage losses for a hot water cylinder. Excludes volume used for solar hot water.
		TYPE: formula
		UNIT: W
		SAP: (54,55)
                SAP_COMPLIANT: Yes
		BREDEM: 2.2B
                BREDEM_COMPLIANT: Yes
		DEPS: cylinder-loss-factor,storage-temperature-factor,volume-factor,cylinder-volume,solar-store-effective-volume
		NOTES: Storage losses apply to system water tanks, CPSU water tanks, and storage combi water tanks.
		NOTES: Storage-combi water tanks are excluded if its efficiency numbers are the SAP defaults, and included if they are the manufacturers declared efficiencies.
		ID: tank-losses
		CODSIEB
		*/
		final IConstants constants = parameters.getConstants();

		final double lossFactor = getCylinderLossFactor(constants);
		final double volumeFactor = getVolumeFactor(constants);
		final double standardLoss = lossFactor * volumeFactor * getVolume();

		if (getSolarStorageVolume() > 0) {
			return standardLoss * ((getVolume() - getSolarStorageVolume()) / getVolume());
		} else {
			return standardLoss;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER:
				return basicSetImmersionHeater(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.WATER_TANK__INSULATION:
				return getInsulation();
			case ITechnologiesPackage.WATER_TANK__FACTORY_INSULATION:
				return isFactoryInsulation();
			case ITechnologiesPackage.WATER_TANK__VOLUME:
				return getVolume();
			case ITechnologiesPackage.WATER_TANK__THERMOSTAT_FITTED:
				return isThermostatFitted();
			case ITechnologiesPackage.WATER_TANK__SOLAR_STORAGE_VOLUME:
				return getSolarStorageVolume();
			case ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER:
				return getImmersionHeater();
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
			case ITechnologiesPackage.WATER_TANK__INSULATION:
				setInsulation((Double)newValue);
				return;
			case ITechnologiesPackage.WATER_TANK__FACTORY_INSULATION:
				setFactoryInsulation((Boolean)newValue);
				return;
			case ITechnologiesPackage.WATER_TANK__VOLUME:
				setVolume((Double)newValue);
				return;
			case ITechnologiesPackage.WATER_TANK__THERMOSTAT_FITTED:
				setThermostatFitted((Boolean)newValue);
				return;
			case ITechnologiesPackage.WATER_TANK__SOLAR_STORAGE_VOLUME:
				setSolarStorageVolume((Double)newValue);
				return;
			case ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER:
				setImmersionHeater((IImmersionHeater)newValue);
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
			case ITechnologiesPackage.WATER_TANK__INSULATION:
				setInsulation(INSULATION_EDEFAULT);
				return;
			case ITechnologiesPackage.WATER_TANK__FACTORY_INSULATION:
				setFactoryInsulation(FACTORY_INSULATION_EDEFAULT);
				return;
			case ITechnologiesPackage.WATER_TANK__VOLUME:
				setVolume(VOLUME_EDEFAULT);
				return;
			case ITechnologiesPackage.WATER_TANK__THERMOSTAT_FITTED:
				setThermostatFitted(THERMOSTAT_FITTED_EDEFAULT);
				return;
			case ITechnologiesPackage.WATER_TANK__SOLAR_STORAGE_VOLUME:
				setSolarStorageVolume(SOLAR_STORAGE_VOLUME_EDEFAULT);
				return;
			case ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER:
				setImmersionHeater((IImmersionHeater)null);
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
			case ITechnologiesPackage.WATER_TANK__INSULATION:
				return insulation != INSULATION_EDEFAULT;
			case ITechnologiesPackage.WATER_TANK__FACTORY_INSULATION:
				return ((flags & FACTORY_INSULATION_EFLAG) != 0) != FACTORY_INSULATION_EDEFAULT;
			case ITechnologiesPackage.WATER_TANK__VOLUME:
				return volume != VOLUME_EDEFAULT;
			case ITechnologiesPackage.WATER_TANK__THERMOSTAT_FITTED:
				return ((flags & THERMOSTAT_FITTED_EFLAG) != 0) != THERMOSTAT_FITTED_EDEFAULT;
			case ITechnologiesPackage.WATER_TANK__SOLAR_STORAGE_VOLUME:
				return solarStorageVolume != SOLAR_STORAGE_VOLUME_EDEFAULT;
			case ITechnologiesPackage.WATER_TANK__IMMERSION_HEATER:
				return immersionHeater != null;
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
		result.append(" (insulation: ");
		result.append(insulation);
		result.append(", factoryInsulation: ");
		result.append((flags & FACTORY_INSULATION_EFLAG) != 0);
		result.append(", volume: ");
		result.append(volume);
		result.append(", thermostatFitted: ");
		result.append((flags & THERMOSTAT_FITTED_EFLAG) != 0);
		result.append(", solarStorageVolume: ");
		result.append(solarStorageVolume);
		result.append(')');
		return result.toString();
	}

	/**
	 * Calculate cylinder volume factor, in accordance with SAP 2009 Table 2a
	 * @param constants
	 * @return
	 */
	private double getVolumeFactor(IConstants constants) {
		/*
		BEISDOC
		NAME: Volume factor
		DESCRIPTION: A mystery factor which the volume of the tank is multiplied by to get its losses.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (52), Table 2a footnote 2
                SAP_COMPLIANT: Yes
		BREDEM: 2.2B.b
                BREDEM_COMPLIANT: Yes
		DEPS: volume-factor-terms,cylinder-volume
		ID: volume-factor
		CODSIEB
		*/
		return Math.pow(constants.get(CylinderConstants.VOLUME_FACTOR_TERMS, 0) / getVolume(),
				constants.get(CylinderConstants.VOLUME_FACTOR_TERMS, 1));
	}

	/**
	 * Estimate cylinder loss factor according to SAP table 2
	 * @param parameters
	 * @return
	 */
	private double getCylinderLossFactor(final IConstants constants) {
		/*
		BEISDOC
		NAME: Cylinder loss factor
		DESCRIPTION: A mystery factor which the volume of the tank is multiplied by to get its losses.
		TYPE: formula
		UNIT: W/litre
		SAP: (51), Table 2
                SAP_COMPLIANT: No, see note
		BREDEM: 2.2B.a
                BREDEM_COMPLIANT: No, see note
		DEPS: cylinder-loss-constant,cylinder-loss-loose-jacket-terms,cylinder-loss-factory-foam-terms,cylinder-insulation-type
		NOTES: Both SAP and BREDEM specify that CPSU cylinder loss factor should always be 0.022. This is not implemented in the NHM.
		CONVERSION: In SAP and BREDEM this is in kWh/litre/day. In the NHM it is scaled to W/litre.

		ID: cylinder-loss-factor
		CODSIEB
		*/
		final double[] terms;
		if (isFactoryInsulation()) {
			terms = constants.get(CylinderConstants.FACTORY_JACKET_FACTORS, double[].class);
		} else {
			terms = constants.get(CylinderConstants.LOOSE_JACKET_FACTORS, double[].class);
		}

		return constants.get(CylinderConstants.LOSS_FACTOR_CONSTANT_TERM) +
				terms[0] / (getInsulation() + terms[1]);
	}


} //WaterTankImpl
