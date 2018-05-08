/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Water Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class WaterHeaterImpl extends MinimalEObjectImpl implements IWaterHeater {

    /**
     * A set of bit flags representing the values of boolean attributes and
     * whether unsettable features have been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected int flags = 0;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    protected WaterHeaterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ITechnologiesPackage.Literals.WATER_HEATER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public boolean isBroken() {
        return false;
    }

} //WaterHeaterImpl
