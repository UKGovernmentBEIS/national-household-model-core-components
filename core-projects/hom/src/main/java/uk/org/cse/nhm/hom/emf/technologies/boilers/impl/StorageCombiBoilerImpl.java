/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.CylinderConstants;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.IStoreContainer;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.EfficiencySourceType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Storage Combi Boiler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.StorageCombiBoilerImpl#getStore <em>Store</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.StorageCombiBoilerImpl#isStoreInPrimaryCircuit <em>Store In Primary Circuit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StorageCombiBoilerImpl extends CombiBoilerImpl implements IStorageCombiBoiler {
	private static final Logger log = LoggerFactory.getLogger(StorageCombiBoilerImpl.class);
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
	 * The default value of the '{@link #isStoreInPrimaryCircuit() <em>Store In Primary Circuit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStoreInPrimaryCircuit()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STORE_IN_PRIMARY_CIRCUIT_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isStoreInPrimaryCircuit() <em>Store In Primary Circuit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStoreInPrimaryCircuit()
	 * @generated
	 * @ordered
	 */
	protected static final int STORE_IN_PRIMARY_CIRCUIT_EFLAG = 1 << 12;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StorageCombiBoilerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBoilersPackage.Literals.STORAGE_COMBI_BOILER;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBoilersPackage.STORAGE_COMBI_BOILER__STORE, oldStore, newStore);
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
				msgs = ((InternalEObject)store).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBoilersPackage.STORAGE_COMBI_BOILER__STORE, null, msgs);
			if (newStore != null)
				msgs = ((InternalEObject)newStore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBoilersPackage.STORAGE_COMBI_BOILER__STORE, null, msgs);
			msgs = basicSetStore(newStore, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.STORAGE_COMBI_BOILER__STORE, newStore, newStore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStoreInPrimaryCircuit() {
		return (flags & STORE_IN_PRIMARY_CIRCUIT_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStoreInPrimaryCircuit(boolean newStoreInPrimaryCircuit) {
		boolean oldStoreInPrimaryCircuit = (flags & STORE_IN_PRIMARY_CIRCUIT_EFLAG) != 0;
		if (newStoreInPrimaryCircuit) flags |= STORE_IN_PRIMARY_CIRCUIT_EFLAG; else flags &= ~STORE_IN_PRIMARY_CIRCUIT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT, oldStoreInPrimaryCircuit, newStoreInPrimaryCircuit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE:
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
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE:
				return getStore();
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT:
				return isStoreInPrimaryCircuit();
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
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE:
				setStore((IWaterTank)newValue);
				return;
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT:
				setStoreInPrimaryCircuit((Boolean)newValue);
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
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE:
				setStore((IWaterTank)null);
				return;
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT:
				setStoreInPrimaryCircuit(STORE_IN_PRIMARY_CIRCUIT_EDEFAULT);
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
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE:
				return store != null;
			case IBoilersPackage.STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT:
				return ((flags & STORE_IN_PRIMARY_CIRCUIT_EFLAG) != 0) != STORE_IN_PRIMARY_CIRCUIT_EDEFAULT;
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
				case IBoilersPackage.STORAGE_COMBI_BOILER__STORE: return ITechnologiesPackage.STORE_CONTAINER__STORE;
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
				case ITechnologiesPackage.STORE_CONTAINER__STORE: return IBoilersPackage.STORAGE_COMBI_BOILER__STORE;
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

	@Override
	protected double getAdditionalUsageLosses(final IInternalParameters parameters,
			final IEnergyState state) {
		/*
		BEISDOC
		NAME: Storage combi losses
		DESCRIPTION: Extra losses which only apply to combi boilers - storage version
		TYPE: formula
		UNIT: W
		SAP: Table 3a
		BREDEM: Table 13
		DEPS: storage-combi-volume-threshold,storage-combi-storage-loss-factor,combi-loss-water-usage-limit
		ID: combi-losses-storage
		CODSIEB
		*/
		final IConstants constants = parameters.getConstants();
		if (getStore().getVolume()  >= constants.get(HeatingSystemConstants.STORAGE_COMBI_VOLUME_THRESHOLD)) {
			return 0;
		} else {
			final double A = constants.get(HeatingSystemConstants.STORAGE_COMBI_LOSS_TERMS, 0);
			
			final double volumeDemanded = state.getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME);
			
			final double fu = 
			Math.min(1.0d,
					volumeDemanded
					/
					constants.get(HeatingSystemConstants.COMBI_HOT_WATER_USAGE_LIMIT));
			
			final double B = getStore().getVolume() - constants.get(HeatingSystemConstants.STORAGE_COMBI_LOSS_TERMS, 1);
			
			final double combiLosses = fu * (A - B * constants.get(HeatingSystemConstants.STORAGE_COMBI_LOSS_TERMS, 2));
			return combiLosses;
		}
	}

	@Override
	public double getContainedTankLosses(final IInternalParameters parameters) {
		if (getStore() == null) return 0;

        if (getEfficiencySource() == EfficiencySourceType.SAP_DEFAULT) {
			// See SAP 2012 section 4.2 Storage loss
			return 0;
		}
		
		final double standingLosses = getStore().getStandingLosses(parameters);
		
		final double temperatureFactor = getStorageTemperatureFactor(parameters, getStore(), isStoreInPrimaryCircuit());
		
		return standingLosses * temperatureFactor;
	}
	
	@Override
	public double getStorageTemperatureFactor(final IInternalParameters parameters,
			final IWaterTank store, final boolean storeInPrimaryCircuit) {
		if (store != getStore()) {
			log.error("Storage temperature factor requested for {}, which is not my internal store - central water system is not correctly configured", store);
		}
		
		/*
		BEISDOC
		NAME: Storage Combi Storage Temperature Factor
		DESCRIPTION: Storage factor for storage combi boilers.
		TYPE: formula
		UNIT: W
		SAP: (51-55)
		BREDEM: 2.2B
		DEPS: temperature-factor-storage-combi-primary-store-terms,temperature-factor-storage-combi-secondary-store-terms
		ID: storage-combi-storage-temperature-factor
		CODSIEB
		*/
		final IConstants constants = parameters.getConstants();
		final double[] terms;
		if (storeInPrimaryCircuit) {
			terms = constants.get(CylinderConstants.TEMPERATURE_FACTOR_PRIMARY_STORAGE_COMBI, double[].class);
		} else {
			terms = constants.get(CylinderConstants.TEMPERATURE_FACTOR_SECONDARY_STORAGE_COMBI, double[].class);
		}
		
		return terms[0] + Math.min(0, terms[1] * (terms[2] - store.getVolume()));
	}
} //StorageCombiBoilerImpl
