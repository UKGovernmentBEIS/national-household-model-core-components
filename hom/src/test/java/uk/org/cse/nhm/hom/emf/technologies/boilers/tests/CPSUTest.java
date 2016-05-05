/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.tests;

import junit.textui.TestRunner;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>CPSU</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CPSUTest extends BoilerTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CPSUTest.class);
	}

	/**
	 * Constructs a new CPSU test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPSUTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this CPSU test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ICPSU getFixture() {
		return (ICPSU)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IBoilersFactory.eINSTANCE.createCPSU());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //CPSUTest
