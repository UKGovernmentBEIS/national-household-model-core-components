/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;

import uk.org.cse.nhm.hom.IHeatProportions;

import uk.org.cse.nhm.hom.emf.technologies.IShower;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shower</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.ShowerImpl#getTechnologyModel <em>Technology Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ShowerImpl extends MinimalEObjectImpl implements IShower {
	private final double BREDEM_SHOWERS_BASE = 0.65;
	private final double BREDEM_SHOWERS_OCCUPANCY_FACTOR = 0.45;

	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ShowerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.SHOWER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ITechnologyModel getTechnologyModel() {
		if (eContainerFeatureID() != ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL) return null;
		return (ITechnologyModel)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTechnologyModel(ITechnologyModel newTechnologyModel, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTechnologyModel, ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTechnologyModel(ITechnologyModel newTechnologyModel) {
		if (newTechnologyModel != eInternalContainer() || (eContainerFeatureID() != ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL && newTechnologyModel != null)) {
			if (EcoreUtil.isAncestor(this, newTechnologyModel))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTechnologyModel != null)
				msgs = ((InternalEObject)newTechnologyModel).eInverseAdd(this, ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER, ITechnologyModel.class, msgs);
			msgs = basicSetTechnologyModel(newTechnologyModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL, newTechnologyModel, newTechnologyModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double solarAdjustment() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		/*
		 * Default accept() method does nothing
		 */
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTechnologyModel((ITechnologyModel)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL:
				return basicSetTechnologyModel(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL:
				return eInternalContainer().eInverseRemove(this, ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER, ITechnologyModel.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL:
				return getTechnologyModel();
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
			case ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL:
				setTechnologyModel((ITechnologyModel)newValue);
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
			case ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL:
				setTechnologyModel((ITechnologyModel)null);
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
			case ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL:
				return getTechnologyModel() != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public double numShowers(final double occupancy) {
		return BREDEM_SHOWERS_BASE + (BREDEM_SHOWERS_OCCUPANCY_FACTOR * occupancy);
	}

} //ShowerImpl
