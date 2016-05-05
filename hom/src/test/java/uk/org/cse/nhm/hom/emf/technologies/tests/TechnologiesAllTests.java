/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import uk.org.cse.nhm.hom.emf.technologies.boilers.tests.BoilersTests;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>Technologies</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class TechnologiesAllTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new TechnologiesAllTests("Technologies Tests");
		suite.addTest(TechnologiesTests.suite());
		suite.addTest(BoilersTests.suite());
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TechnologiesAllTests(String name) {
		super(name);
	}

} //TechnologiesAllTests
