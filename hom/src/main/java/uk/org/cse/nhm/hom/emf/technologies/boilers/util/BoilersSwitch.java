/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue;
import uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear;
import uk.org.cse.nhm.hom.emf.technologies.IHasOverrideResponsiveness;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.IStoreContainer;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage
 * @generated
 */
public class BoilersSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBoilersPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoilersSwitch() {
		if (modelPackage == null) {
			modelPackage = IBoilersPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case IBoilersPackage.BOILER: {
				IBoiler boiler = (IBoiler)theEObject;
				T result = caseBoiler(boiler);
				if (result == null) result = caseIndividualHeatSource(boiler);
				if (result == null) result = caseVisitorAccepter(boiler);
				if (result == null) result = caseHeatSource(boiler);
				if (result == null) result = caseFuelAndFlue(boiler);
				if (result == null) result = caseOperationalCost(boiler);
				if (result == null) result = caseHasOverrideResponsiveness(boiler);
				if (result == null) result = caseHasInstallationYear(boiler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBoilersPackage.CPSU: {
				ICPSU cpsu = (ICPSU)theEObject;
				T result = caseCPSU(cpsu);
				if (result == null) result = caseBoiler(cpsu);
				if (result == null) result = caseStoreContainer(cpsu);
				if (result == null) result = caseIndividualHeatSource(cpsu);
				if (result == null) result = caseVisitorAccepter(cpsu);
				if (result == null) result = caseHeatSource(cpsu);
				if (result == null) result = caseFuelAndFlue(cpsu);
				if (result == null) result = caseOperationalCost(cpsu);
				if (result == null) result = caseHasOverrideResponsiveness(cpsu);
				if (result == null) result = caseHasInstallationYear(cpsu);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBoilersPackage.KEEP_HOT_FACILITY: {
				IKeepHotFacility keepHotFacility = (IKeepHotFacility)theEObject;
				T result = caseKeepHotFacility(keepHotFacility);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBoilersPackage.STORAGE_COMBI_BOILER: {
				IStorageCombiBoiler storageCombiBoiler = (IStorageCombiBoiler)theEObject;
				T result = caseStorageCombiBoiler(storageCombiBoiler);
				if (result == null) result = caseCombiBoiler(storageCombiBoiler);
				if (result == null) result = caseStoreContainer(storageCombiBoiler);
				if (result == null) result = caseBoiler(storageCombiBoiler);
				if (result == null) result = caseIndividualHeatSource(storageCombiBoiler);
				if (result == null) result = caseVisitorAccepter(storageCombiBoiler);
				if (result == null) result = caseHeatSource(storageCombiBoiler);
				if (result == null) result = caseFuelAndFlue(storageCombiBoiler);
				if (result == null) result = caseOperationalCost(storageCombiBoiler);
				if (result == null) result = caseHasOverrideResponsiveness(storageCombiBoiler);
				if (result == null) result = caseHasInstallationYear(storageCombiBoiler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBoilersPackage.INSTANTANEOUS_COMBI_BOILER: {
				IInstantaneousCombiBoiler instantaneousCombiBoiler = (IInstantaneousCombiBoiler)theEObject;
				T result = caseInstantaneousCombiBoiler(instantaneousCombiBoiler);
				if (result == null) result = caseCombiBoiler(instantaneousCombiBoiler);
				if (result == null) result = caseBoiler(instantaneousCombiBoiler);
				if (result == null) result = caseIndividualHeatSource(instantaneousCombiBoiler);
				if (result == null) result = caseVisitorAccepter(instantaneousCombiBoiler);
				if (result == null) result = caseHeatSource(instantaneousCombiBoiler);
				if (result == null) result = caseFuelAndFlue(instantaneousCombiBoiler);
				if (result == null) result = caseOperationalCost(instantaneousCombiBoiler);
				if (result == null) result = caseHasOverrideResponsiveness(instantaneousCombiBoiler);
				if (result == null) result = caseHasInstallationYear(instantaneousCombiBoiler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBoilersPackage.COMBI_BOILER: {
				ICombiBoiler combiBoiler = (ICombiBoiler)theEObject;
				T result = caseCombiBoiler(combiBoiler);
				if (result == null) result = caseBoiler(combiBoiler);
				if (result == null) result = caseIndividualHeatSource(combiBoiler);
				if (result == null) result = caseVisitorAccepter(combiBoiler);
				if (result == null) result = caseHeatSource(combiBoiler);
				if (result == null) result = caseFuelAndFlue(combiBoiler);
				if (result == null) result = caseOperationalCost(combiBoiler);
				if (result == null) result = caseHasOverrideResponsiveness(combiBoiler);
				if (result == null) result = caseHasInstallationYear(combiBoiler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boiler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boiler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoiler(IBoiler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CPSU</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CPSU</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCPSU(ICPSU object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Keep Hot Facility</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Keep Hot Facility</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKeepHotFacility(IKeepHotFacility object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Storage Combi Boiler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Storage Combi Boiler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStorageCombiBoiler(IStorageCombiBoiler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Instantaneous Combi Boiler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Instantaneous Combi Boiler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInstantaneousCombiBoiler(IInstantaneousCombiBoiler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Combi Boiler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Combi Boiler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCombiBoiler(ICombiBoiler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Heat Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Heat Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHeatSource(IHeatSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Individual Heat Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Individual Heat Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndividualHeatSource(IIndividualHeatSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Visitor Accepter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Visitor Accepter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisitorAccepter(IVisitorAccepter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fuel And Flue</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fuel And Flue</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFuelAndFlue(IFuelAndFlue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operational Cost</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operational Cost</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperationalCost(IOperationalCost object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Has Override Responsiveness</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Has Override Responsiveness</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHasOverrideResponsiveness(IHasOverrideResponsiveness object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Has Installation Year</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Has Installation Year</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHasInstallationYear(IHasInstallationYear object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Store Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Store Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStoreContainer(IStoreContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //BoilersSwitch
