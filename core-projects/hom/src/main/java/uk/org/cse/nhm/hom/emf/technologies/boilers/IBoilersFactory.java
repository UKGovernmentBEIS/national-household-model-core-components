/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage
 * @generated
 */
public interface IBoilersFactory extends EFactory {

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    IBoilersFactory eINSTANCE = uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Boiler</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Boiler</em>'.
     * @generated
     */
    IBoiler createBoiler();

    /**
     * Returns a new object of class '<em>CPSU</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>CPSU</em>'.
     * @generated
     */
    ICPSU createCPSU();

    /**
     * Returns a new object of class '<em>Keep Hot Facility</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Keep Hot Facility</em>'.
     * @generated
     */
    IKeepHotFacility createKeepHotFacility();

    /**
     * Returns a new object of class '<em>Storage Combi Boiler</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Storage Combi Boiler</em>'.
     * @generated
     */
    IStorageCombiBoiler createStorageCombiBoiler();

    /**
     * Returns a new object of class '<em>Instantaneous Combi Boiler</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Instantaneous Combi Boiler</em>'.
     * @generated
     */
    IInstantaneousCombiBoiler createInstantaneousCombiBoiler();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    IBoilersPackage getBoilersPackage();

} //IBoilersFactory
