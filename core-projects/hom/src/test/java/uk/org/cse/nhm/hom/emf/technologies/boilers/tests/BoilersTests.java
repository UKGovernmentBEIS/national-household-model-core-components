/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>boilers</b></em>' package.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BoilersTests extends TestSuite {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static Test suite() {
        TestSuite suite = new BoilersTests("boilers Tests");
        suite.addTestSuite(BoilerTest.class);
        suite.addTestSuite(CPSUTest.class);
        suite.addTestSuite(KeepHotFacilityTest.class);
        suite.addTestSuite(StorageCombiBoilerTest.class);
        suite.addTestSuite(InstantaneousCombiBoilerTest.class);
        return suite;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public BoilersTests(String name) {
        super(name);
    }

} //BoilersTests
