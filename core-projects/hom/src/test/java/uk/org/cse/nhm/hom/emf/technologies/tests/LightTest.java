/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import junit.framework.TestCase;
import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Light</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class LightTest extends TestCase {

	/**
	 * The fixture for this Light test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ILight fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(LightTest.class);
	}

	/**
	 * Constructs a new Light test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LightTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Light test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ILight fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Light test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ILight getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createLight());
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

	/**
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
	 * @generated no
	 */
	public void testAccept__IInternalParameters_IEnergyCalculatorVisitor_AtomicInteger() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

	/**
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(uk.org.cse.nhm.energycalculator.api.IInternalParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor) <em>Accept</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(uk.org.cse.nhm.energycalculator.api.IInternalParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor)
	 * @generated NOT
	 */
	public void testAccept__IInternalParameters_IEnergyCalculatorVisitor() {
		// Ensure that you remove @generated or mark it @generated NOT

	}

} //LightTest
