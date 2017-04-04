/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import junit.textui.TestRunner;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Immersion Heater</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ImmersionHeaterTest extends CentralWaterHeaterTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ImmersionHeaterTest.class);
	}

	/**
	 * Constructs a new Immersion Heater test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImmersionHeaterTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Immersion Heater test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IImmersionHeater getFixture() {
		return (IImmersionHeater)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createImmersionHeater());
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

} //ImmersionHeaterTest
