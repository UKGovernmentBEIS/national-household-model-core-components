/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operational Cost</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.OperationalCostImpl#getAnnualOperationalCost <em>Annual Operational Cost</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperationalCostImpl extends MinimalEObjectImpl implements IOperationalCost {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #getAnnualOperationalCost() <em>Annual Operational Cost</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnualOperationalCost()
	 * @generated
	 * @ordered
	 */
	protected static final double ANNUAL_OPERATIONAL_COST_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getAnnualOperationalCost() <em>Annual Operational Cost</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnualOperationalCost()
	 * @generated
	 * @ordered
	 */
	protected double annualOperationalCost = ANNUAL_OPERATIONAL_COST_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationalCostImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.OPERATIONAL_COST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getAnnualOperationalCost() {
		return annualOperationalCost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnnualOperationalCost(double newAnnualOperationalCost) {
		double oldAnnualOperationalCost = annualOperationalCost;
		annualOperationalCost = newAnnualOperationalCost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST, oldAnnualOperationalCost, annualOperationalCost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST:
				return getAnnualOperationalCost();
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
			case ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST:
				setAnnualOperationalCost((Double)newValue);
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
			case ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST:
				setAnnualOperationalCost(ANNUAL_OPERATIONAL_COST_EDEFAULT);
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
			case ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST:
				return annualOperationalCost != ANNUAL_OPERATIONAL_COST_EDEFAULT;
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
		result.append(" (annualOperationalCost: ");
		result.append(annualOperationalCost);
		result.append(')');
		return result.toString();
	}

} //OperationalCostImpl
