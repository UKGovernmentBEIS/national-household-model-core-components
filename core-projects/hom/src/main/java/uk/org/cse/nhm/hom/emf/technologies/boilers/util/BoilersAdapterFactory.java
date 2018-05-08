/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue;
import uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear;
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
 * The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage
 * @generated
 */
public class BoilersAdapterFactory extends AdapterFactoryImpl {

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected static IBoilersPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public BoilersAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = IBoilersPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the
     * model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     *
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected BoilersSwitch<Adapter> modelSwitch
            = new BoilersSwitch<Adapter>() {
        @Override
        public Adapter caseBoiler(IBoiler object) {
            return createBoilerAdapter();
        }

        @Override
        public Adapter caseCPSU(ICPSU object) {
            return createCPSUAdapter();
        }

        @Override
        public Adapter caseKeepHotFacility(IKeepHotFacility object) {
            return createKeepHotFacilityAdapter();
        }

        @Override
        public Adapter caseStorageCombiBoiler(IStorageCombiBoiler object) {
            return createStorageCombiBoilerAdapter();
        }

        @Override
        public Adapter caseInstantaneousCombiBoiler(IInstantaneousCombiBoiler object) {
            return createInstantaneousCombiBoilerAdapter();
        }

        @Override
        public Adapter caseCombiBoiler(ICombiBoiler object) {
            return createCombiBoilerAdapter();
        }

        @Override
        public Adapter caseFuelAndFlue(IFuelAndFlue object) {
            return createFuelAndFlueAdapter();
        }

        @Override
        public Adapter caseOperationalCost(IOperationalCost object) {
            return createOperationalCostAdapter();
        }

        @Override
        public Adapter caseHasInstallationYear(IHasInstallationYear object) {
            return createHasInstallationYearAdapter();
        }

        @Override
        public Adapter caseHeatSource(IHeatSource object) {
            return createHeatSourceAdapter();
        }

        @Override
        public Adapter caseIndividualHeatSource(IIndividualHeatSource object) {
            return createIndividualHeatSourceAdapter();
        }

        @Override
        public Adapter caseVisitorAccepter(IVisitorAccepter object) {
            return createVisitorAccepterAdapter();
        }

        @Override
        public Adapter caseStoreContainer(IStoreContainer object) {
            return createStoreContainerAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler
     * <em>Boiler</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler
     * @generated
     */
    public Adapter createBoilerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU
     * <em>CPSU</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU
     * @generated
     */
    public Adapter createCPSUAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility
     * <em>Keep Hot Facility</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility
     * @generated
     */
    public Adapter createKeepHotFacilityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler
     * <em>Storage Combi Boiler</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler
     * @generated
     */
    public Adapter createStorageCombiBoilerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler
     * <em>Instantaneous Combi Boiler</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler
     * @generated
     */
    public Adapter createInstantaneousCombiBoilerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler
     * <em>Combi Boiler</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler
     * @generated
     */
    public Adapter createCombiBoilerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource
     * <em>Heat Source</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.IHeatSource
     * @generated
     */
    public Adapter createHeatSourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource
     * <em>Individual Heat Source</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource
     * @generated
     */
    public Adapter createIndividualHeatSourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter
     * <em>Visitor Accepter</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter
     * @generated
     */
    public Adapter createVisitorAccepterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue
     * <em>Fuel And Flue</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue
     * @generated
     */
    public Adapter createFuelAndFlueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.IOperationalCost
     * <em>Operational Cost</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.IOperationalCost
     * @generated
     */
    public Adapter createOperationalCostAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear
     * <em>Has Installation Year</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear
     * @generated
     */
    public Adapter createHasInstallationYearAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link uk.org.cse.nhm.hom.emf.technologies.IStoreContainer
     * <em>Store Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see uk.org.cse.nhm.hom.emf.technologies.IStoreContainer
     * @generated
     */
    public Adapter createStoreContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //BoilersAdapterFactory
