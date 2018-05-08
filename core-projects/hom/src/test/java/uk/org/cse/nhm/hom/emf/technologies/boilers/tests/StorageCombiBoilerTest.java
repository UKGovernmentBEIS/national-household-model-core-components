/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.tests;

import junit.textui.TestRunner;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Storage Combi Boiler</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class StorageCombiBoilerTest extends CombiBoilerTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(String[] args) {
        TestRunner.run(StorageCombiBoilerTest.class);
    }

    /**
     * Constructs a new Storage Combi Boiler test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public StorageCombiBoilerTest(String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Storage Combi Boiler test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected IStorageCombiBoiler getFixture() {
        return (IStorageCombiBoiler) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#setUp()
     *
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(IBoilersFactory.eINSTANCE.createStorageCombiBoiler());
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

} //StorageCombiBoilerTest
