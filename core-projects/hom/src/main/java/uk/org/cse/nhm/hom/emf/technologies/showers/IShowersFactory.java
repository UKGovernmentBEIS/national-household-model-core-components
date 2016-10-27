/**
 */
package uk.org.cse.nhm.hom.emf.technologies.showers;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.showers.IShowersPackage
 * @generated
 */
public interface IShowersFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IShowersFactory eINSTANCE = uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Mixer Shower</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mixer Shower</em>'.
	 * @generated
	 */
	IMixerShower createMixerShower();

	/**
	 * Returns a new object of class '<em>Electric Shower</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Electric Shower</em>'.
	 * @generated
	 */
	IElectricShower createElectricShower();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IShowersPackage getShowersPackage();

} //IShowersFactory
