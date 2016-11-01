/**
 */
package uk.org.cse.nhm.hom.emf.technologies.showers;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.showers.IShowersFactory
 * @model kind="package"
 * @generated
 */
public interface IShowersPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "showers";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.cse.org.uk/nhm/schema/emf/technology/showers/1/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "showers";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IShowersPackage eINSTANCE = uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersPackageImpl.init();

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowerImpl <em>Shower</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersPackageImpl#getShower()
	 * @generated
	 */
	int SHOWER = 0;

	/**
	 * The feature id for the '<em><b>Technology Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOWER__TECHNOLOGY_MODEL = ITechnologiesPackage.VISITOR_ACCEPTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Shower</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHOWER_FEATURE_COUNT = ITechnologiesPackage.VISITOR_ACCEPTER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.showers.impl.MixerShowerImpl <em>Mixer Shower</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.MixerShowerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersPackageImpl#getMixerShower()
	 * @generated
	 */
	int MIXER_SHOWER = 1;

	/**
	 * The feature id for the '<em><b>Technology Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIXER_SHOWER__TECHNOLOGY_MODEL = SHOWER__TECHNOLOGY_MODEL;

	/**
	 * The number of structural features of the '<em>Mixer Shower</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIXER_SHOWER_FEATURE_COUNT = SHOWER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.showers.impl.ElectricShowerImpl <em>Electric Shower</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ElectricShowerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersPackageImpl#getElectricShower()
	 * @generated
	 */
	int ELECTRIC_SHOWER = 2;

	/**
	 * The feature id for the '<em><b>Technology Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELECTRIC_SHOWER__TECHNOLOGY_MODEL = SHOWER__TECHNOLOGY_MODEL;

	/**
	 * The number of structural features of the '<em>Electric Shower</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELECTRIC_SHOWER_FEATURE_COUNT = SHOWER_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.showers.IShower <em>Shower</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shower</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.IShower
	 * @generated
	 */
	EClass getShower();

	/**
	 * Returns the meta object for the container reference '{@link uk.org.cse.nhm.hom.emf.technologies.showers.IShower#getTechnologyModel <em>Technology Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Technology Model</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.IShower#getTechnologyModel()
	 * @see #getShower()
	 * @generated
	 */
	EReference getShower_TechnologyModel();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.showers.IMixerShower <em>Mixer Shower</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mixer Shower</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.IMixerShower
	 * @generated
	 */
	EClass getMixerShower();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.showers.IElectricShower <em>Electric Shower</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Electric Shower</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.IElectricShower
	 * @generated
	 */
	EClass getElectricShower();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IShowersFactory getShowersFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowerImpl <em>Shower</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersPackageImpl#getShower()
		 * @generated
		 */
		EClass SHOWER = eINSTANCE.getShower();

		/**
		 * The meta object literal for the '<em><b>Technology Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHOWER__TECHNOLOGY_MODEL = eINSTANCE.getShower_TechnologyModel();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.showers.impl.MixerShowerImpl <em>Mixer Shower</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.MixerShowerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersPackageImpl#getMixerShower()
		 * @generated
		 */
		EClass MIXER_SHOWER = eINSTANCE.getMixerShower();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.showers.impl.ElectricShowerImpl <em>Electric Shower</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ElectricShowerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.showers.impl.ShowersPackageImpl#getElectricShower()
		 * @generated
		 */
		EClass ELECTRIC_SHOWER = eINSTANCE.getElectricShower();

	}

} //IShowersPackage
