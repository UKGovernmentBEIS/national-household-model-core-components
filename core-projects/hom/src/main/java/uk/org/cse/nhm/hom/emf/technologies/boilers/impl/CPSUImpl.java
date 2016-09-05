/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.constants.CylinderConstants;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IStoreContainer;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CPSU</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CPSUImpl#getStore <em>Store</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CPSUImpl#getStoreTemperature <em>Store Temperature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CPSUImpl extends BoilerImpl implements ICPSU {
	private static final Logger log = LoggerFactory.getLogger(CPSUImpl.class);
	
	/**
	 * The cached value of the '{@link #getStore() <em>Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStore()
	 * @generated
	 * @ordered
	 */
	protected IWaterTank store;

	/**
	 * The default value of the '{@link #getStoreTemperature() <em>Store Temperature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStoreTemperature()
	 * @generated
	 * @ordered
	 */
	protected static final double STORE_TEMPERATURE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getStoreTemperature() <em>Store Temperature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStoreTemperature()
	 * @generated
	 * @ordered
	 */
	protected double storeTemperature = STORE_TEMPERATURE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CPSUImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBoilersPackage.Literals.CPSU;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IWaterTank getStore() {
		return store;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStore(IWaterTank newStore, NotificationChain msgs) {
		IWaterTank oldStore = store;
		store = newStore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBoilersPackage.CPSU__STORE, oldStore, newStore);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStore(IWaterTank newStore) {
		if (newStore != store) {
			NotificationChain msgs = null;
			if (store != null)
				msgs = ((InternalEObject)store).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBoilersPackage.CPSU__STORE, null, msgs);
			if (newStore != null)
				msgs = ((InternalEObject)newStore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBoilersPackage.CPSU__STORE, null, msgs);
			msgs = basicSetStore(newStore, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.CPSU__STORE, newStore, newStore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getStoreTemperature() {
		return storeTemperature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStoreTemperature(double newStoreTemperature) {
		double oldStoreTemperature = storeTemperature;
		storeTemperature = newStoreTemperature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.CPSU__STORE_TEMPERATURE, oldStoreTemperature, storeTemperature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IBoilersPackage.CPSU__STORE:
				return basicSetStore(null, msgs);
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
			case IBoilersPackage.CPSU__STORE:
				return getStore();
			case IBoilersPackage.CPSU__STORE_TEMPERATURE:
				return getStoreTemperature();
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
			case IBoilersPackage.CPSU__STORE:
				setStore((IWaterTank)newValue);
				return;
			case IBoilersPackage.CPSU__STORE_TEMPERATURE:
				setStoreTemperature((Double)newValue);
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
			case IBoilersPackage.CPSU__STORE:
				setStore((IWaterTank)null);
				return;
			case IBoilersPackage.CPSU__STORE_TEMPERATURE:
				setStoreTemperature(STORE_TEMPERATURE_EDEFAULT);
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
			case IBoilersPackage.CPSU__STORE:
				return store != null;
			case IBoilersPackage.CPSU__STORE_TEMPERATURE:
				return storeTemperature != STORE_TEMPERATURE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IStoreContainer.class) {
			switch (derivedFeatureID) {
				case IBoilersPackage.CPSU__STORE: return ITechnologiesPackage.STORE_CONTAINER__STORE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IStoreContainer.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.STORE_CONTAINER__STORE: return IBoilersPackage.CPSU__STORE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * Get the high rate fraction, for an electrically fuelled CPSU
	 */
	@Override
	protected double getHighRateFraction(final IInternalParameters parameters,
			final ISpecificHeatLosses losses, final IEnergyState state, final double qWater,
			final double qHeat) {
		if (getFuel() != FuelType.ELECTRICITY) {
			throw new RuntimeException("Cannot get the high-rate fraction for a non-electric CPSU!");
		}
		
		final double usefulGains = state.getTotalSupply(EnergyType.DemandsHEAT, ServiceType.INTERNALS);
		
		return getHighRateFraction(
				parameters.getConstants(), 
				parameters.getClimate().getExternalTemperature(),
				state.getTotalSupply(EnergyType.HackMEAN_INTERNAL_TEMPERATURE),
				losses.getSpecificHeatLoss(),
				usefulGains,
				qWater,
				qHeat);
	}
		
	@Override
	protected boolean isIntermediatePowerRequired() {
		return getFuel() == FuelType.ELECTRICITY || super.isIntermediatePowerRequired();
	}

	/**
	 * As we all know, SAP 2009 table 3 declares that CPSUs have no primary pipework losses.
	 */
	@Override
	protected double getPrimaryPipeworkLosses(final IInternalParameters parameters,
			final boolean tankPresentAndThermostatic, final double primaryCorrectionFactor) {
		return 0;
	}

	protected double getDegreeDays(final double temperature, final double outsideTemperature) {
		final double deltaT = temperature - outsideTemperature;
		if (deltaT == 0) {
			return 1;
		} else {
			return (deltaT / (1-Math.exp(-deltaT)));
		}
	}
	
	protected double getHighRateFraction(final IConstants constants, final double externalTemperature, final double internalTemperature, final double specificHeatLoss, final double usefulGains, final double hotWaterPower, final double spacePower) {
		final double lowRatePower = constants.get(HeatingSystemConstants.ELECTRIC_CPSU_LOW_RATE_HEAT_CONSTANT)
				* getStore().getVolume() * (getStoreTemperature() - constants.get(HeatingSystemConstants.ELECTRIC_CPSU_WINTER_TEMPERATURE_OFFSET));
		// calculate minimum external temperature for which a CPSU of this size can satisfy the heat demand using the tank
		final double lowestExternalTemperature = 
				((specificHeatLoss * internalTemperature) - lowRatePower + hotWaterPower - usefulGains) / specificHeatLoss;
		
		// this is the number of degree days of temperature difference /in one day/.
		final double degreeDays = getDegreeDays(lowestExternalTemperature, externalTemperature);

		// in SAP we have
		// DD1 (deg * day) * H (w/deg) * nd = E_(on peak) (watt days -> kWh)
		// that gives kWh in the month being considered
		// then in SAP the high rate fraction is taken to be (on peak) / (total = hw + space)
		// because we are doing watts rather than kWh, and num days is thus not present in any of the parts of the equation
		// we can just use
		
		// DD1 * H = watt days for 1 day / 1 day = watts
		
		final double peakEnergy = degreeDays * specificHeatLoss;
		
		final double highRateFraction = peakEnergy / (hotWaterPower + spacePower);
		
		return highRateFraction;
	}

	/**
	 * Rules from SAP 2009 table 2b
	 */
	@Override
	public double getStorageTemperatureFactor(final IInternalParameters parameters,
			final IWaterTank store, final boolean storeInPrimaryCircuit) {
		/*
		BEISDOC
		NAME: CPSU Storage Temperature Factor
		DESCRIPTION: The storage temperature factor for a CPSU.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (53), Table 2b
		BREDEM: 2.2B.c, Table 9
		DEPS: cpsu-electric-storage-temperature-factor,cpsu-gas-storage-temperature-factor
		NOTES: Does not implement footnotes (c) or (d), due to lack of data.
		ID: cpsu-storage-temperature-factor
		CODSIEB
		*/
		if (store != getStore()) {
			log.error("CPSU should not be used with an external storage tank!");
		}
		
		final IConstants constants = parameters.getConstants();
		if (getFuel() == FuelType.ELECTRICITY) {
			return constants.get(CylinderConstants.TEMPERATURE_FACTOR_ELECTRIC_CPSU);
		} else {
			final double result = constants.get(CylinderConstants.TEMPERATURE_FACTOR_GAS_CPSU);
		
			// TODO multiply by 0.81 if there is a timer
			// TODO multiply by 1.1 if not in airing cupboard
			
			return result;
		}
	}
	
	@Override
	public double getContainedTankLosses(final IInternalParameters parameters) {
		final IWaterTank store = getStore();
		
		return store.getStandingLosses(parameters) * getStorageTemperatureFactor(parameters, store, true);
	}

	/**
	 * Because in SAP table 4c (efficiency adjustments) CPSU water efficiency is not affected by presence
	 * of interlock, we override the method from the basic boiler here.
	 */
	@Override
	protected double getWaterHeatingEfficiency(final IConstants constants, final double qWater, final double qSpace) {
		/*
		BEISDOC
		NAME: CPSU Water Heating Efficiency
		DESCRIPTION: Because in SAP table 4c (efficiency adjustments) CPSU water efficiency is not affected by presence of interlock, we override the method from the basic boiler here.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (206), Table 4c
		ID: cpsu-boiler-hot-water-efficiency
		CODSIEB
		*/
		return getSeasonalEfficiency(qWater, qSpace);
	}

	/**
	 * SAP Table 4e defines a 0.1 degree reduction on CPSU demand temperature.
	 */
	@Override
	public double getDemandTemperatureAdjustment(
			final IInternalParameters parameters,
			final EList<HeatingSystemControlType> controls) {
		return super.getDemandTemperatureAdjustment(parameters, controls) +
				parameters.getConstants().get(TemperatureAdjustments.CPSU_OR_INTEGRATED_THERMAL_STORE);
	}

	/**
	 * Electric CPSU and storage boilers have a fixed responsiveness, for no reason.
	 */
	@Override
	public double getResponsivenessImpl(final IConstants parameters,
			final EList<HeatingSystemControlType> controls, final EmitterType emitter) {
		if (getFuel() == FuelType.ELECTRICITY) {
			return getBasicResponsiveness();			
		} else {
			return super.getSAPTable4dResponsiveness(parameters, controls, emitter);
		}
	}
} //CPSUImpl
