/**
 */
package uk.org.cse.nhm.hom.emf.technologies.showers.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import uk.org.cse.nhm.hom.emf.technologies.showers.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ShowersFactoryImpl extends EFactoryImpl implements IShowersFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IShowersFactory init() {
		try {
			IShowersFactory theShowersFactory = (IShowersFactory)EPackage.Registry.INSTANCE.getEFactory(IShowersPackage.eNS_URI);
			if (theShowersFactory != null) {
				return theShowersFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ShowersFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShowersFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IShowersPackage.MIXER_SHOWER: return createMixerShower();
			case IShowersPackage.ELECTRIC_SHOWER: return createElectricShower();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IMixerShower createMixerShower() {
		MixerShowerImpl mixerShower = new MixerShowerImpl();
		return mixerShower;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IElectricShower createElectricShower() {
		ElectricShowerImpl electricShower = new ElectricShowerImpl();
		return electricShower;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IShowersPackage getShowersPackage() {
		return (IShowersPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IShowersPackage getPackage() {
		return IShowersPackage.eINSTANCE;
	}

} //ShowersFactoryImpl
