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
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.AdjusterType;
import uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.EnergyUseTransducer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Energy Use Adjuster</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.EnergyUseAdjusterImpl#getName <em>Name</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.EnergyUseAdjusterImpl#getConstantTerm <em>Constant Term</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.EnergyUseAdjusterImpl#getLinearTerm <em>Linear Term</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.EnergyUseAdjusterImpl#getAdjustmentType <em>Adjustment Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnergyUseAdjusterImpl extends MinimalEObjectImpl implements IEnergyUseAdjuster {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getConstantTerm() <em>Constant Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstantTerm()
	 * @generated
	 * @ordered
	 */
	protected static final double CONSTANT_TERM_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getConstantTerm() <em>Constant Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstantTerm()
	 * @generated
	 * @ordered
	 */
	protected double constantTerm = CONSTANT_TERM_EDEFAULT;

	/**
	 * The default value of the '{@link #getLinearTerm() <em>Linear Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinearTerm()
	 * @generated
	 * @ordered
	 */
	protected static final double LINEAR_TERM_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getLinearTerm() <em>Linear Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinearTerm()
	 * @generated
	 * @ordered
	 */
	protected double linearTerm = LINEAR_TERM_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdjustmentType() <em>Adjustment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdjustmentType()
	 * @generated
	 * @ordered
	 */
	protected static final AdjusterType ADJUSTMENT_TYPE_EDEFAULT = AdjusterType.APPLIANCE;

	/**
	 * The offset of the flags representing the value of the '{@link #getAdjustmentType() <em>Adjustment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int ADJUSTMENT_TYPE_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getAdjustmentType() <em>Adjustment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int ADJUSTMENT_TYPE_EFLAG_DEFAULT = ADJUSTMENT_TYPE_EDEFAULT.ordinal() << ADJUSTMENT_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link AdjusterType Adjuster Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final AdjusterType[] ADJUSTMENT_TYPE_EFLAG_VALUES = AdjusterType.values();

	/**
	 * The flag representing the value of the '{@link #getAdjustmentType() <em>Adjustment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdjustmentType()
	 * @generated
	 * @ordered
	 */
	protected static final int ADJUSTMENT_TYPE_EFLAG = 1 << ADJUSTMENT_TYPE_EFLAG_OFFSET;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnergyUseAdjusterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.ENERGY_USE_ADJUSTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ENERGY_USE_ADJUSTER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getConstantTerm() {
		return constantTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstantTerm(double newConstantTerm) {
		double oldConstantTerm = constantTerm;
		constantTerm = newConstantTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ENERGY_USE_ADJUSTER__CONSTANT_TERM, oldConstantTerm, constantTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLinearTerm() {
		return linearTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinearFactor(double newLinearTerm) {
		double oldLinearTerm = linearTerm;
		linearTerm = newLinearTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ENERGY_USE_ADJUSTER__LINEAR_TERM, oldLinearTerm, linearTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdjusterType getAdjustmentType() {
		return ADJUSTMENT_TYPE_EFLAG_VALUES[(flags & ADJUSTMENT_TYPE_EFLAG) >>> ADJUSTMENT_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdjustmentType(AdjusterType newAdjustmentType) {
		AdjusterType oldAdjustmentType = ADJUSTMENT_TYPE_EFLAG_VALUES[(flags & ADJUSTMENT_TYPE_EFLAG) >>> ADJUSTMENT_TYPE_EFLAG_OFFSET];
		if (newAdjustmentType == null) newAdjustmentType = ADJUSTMENT_TYPE_EDEFAULT;
		flags = flags & ~ADJUSTMENT_TYPE_EFLAG | newAdjustmentType.ordinal() << ADJUSTMENT_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ENERGY_USE_ADJUSTER__ADJUSTMENT_TYPE, oldAdjustmentType, newAdjustmentType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		//TODO: Don't add if SAP....
	    
	    ServiceType serviceType;
	    switch (this.getAdjustmentType()) {
            case COOKER:
                serviceType = ServiceType.COOKING;
                break;
            default:
                serviceType = ServiceType.APPLIANCES;
                break;
        }
	    
	    EnergyUseTransducer eu = new EnergyUseTransducer(serviceType, getLinearTerm(), getConstantTerm());
	    visitor.visitEnergyTransducer(eu);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__NAME:
				return getName();
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__CONSTANT_TERM:
				return getConstantTerm();
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__LINEAR_TERM:
				return getLinearTerm();
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__ADJUSTMENT_TYPE:
				return getAdjustmentType();
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
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__NAME:
				setName((String)newValue);
				return;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__CONSTANT_TERM:
				setConstantTerm((Double)newValue);
				return;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__LINEAR_TERM:
				setLinearFactor((Double)newValue);
				return;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__ADJUSTMENT_TYPE:
				setAdjustmentType((AdjusterType)newValue);
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
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__CONSTANT_TERM:
				setConstantTerm(CONSTANT_TERM_EDEFAULT);
				return;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__LINEAR_TERM:
				setLinearFactor(LINEAR_TERM_EDEFAULT);
				return;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__ADJUSTMENT_TYPE:
				setAdjustmentType(ADJUSTMENT_TYPE_EDEFAULT);
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
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__CONSTANT_TERM:
				return constantTerm != CONSTANT_TERM_EDEFAULT;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__LINEAR_TERM:
				return linearTerm != LINEAR_TERM_EDEFAULT;
			case ITechnologiesPackage.ENERGY_USE_ADJUSTER__ADJUSTMENT_TYPE:
				return (flags & ADJUSTMENT_TYPE_EFLAG) != ADJUSTMENT_TYPE_EFLAG_DEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", constantTerm: ");
		result.append(constantTerm);
		result.append(", linearTerm: ");
		result.append(linearTerm);
		result.append(", adjustmentType: ");
		result.append(ADJUSTMENT_TYPE_EFLAG_VALUES[(flags & ADJUSTMENT_TYPE_EFLAG) >>> ADJUSTMENT_TYPE_EFLAG_OFFSET]);
		result.append(')');
		return result.toString();
	}

} //EnergyUseAdjusterImpl
