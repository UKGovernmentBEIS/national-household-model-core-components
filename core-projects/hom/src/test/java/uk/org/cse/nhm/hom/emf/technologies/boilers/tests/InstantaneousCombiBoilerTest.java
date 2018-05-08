/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.tests;

import junit.textui.TestRunner;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Instantaneous Combi
 * Boiler</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class InstantaneousCombiBoilerTest extends CombiBoilerTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(String[] args) {
        TestRunner.run(InstantaneousCombiBoilerTest.class);
    }

    /**
     * Constructs a new Instantaneous Combi Boiler test case with the given
     * name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public InstantaneousCombiBoilerTest(String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Instantaneous Combi Boiler test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected IInstantaneousCombiBoiler getFixture() {
        return (IInstantaneousCombiBoiler) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#setUp()
     *
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(IBoilersFactory.eINSTANCE.createInstantaneousCombiBoiler());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#tearDown()
     *
     * @generated
     */
    @Override
    protected void tearDown() throws Exception {
        setFixture(null);
    }

} //InstantaneousCombiBoilerTest
