/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.tests;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.tests.HeatSourceTest;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Boiler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class BoilerTest extends HeatSourceTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(BoilerTest.class);
	}

	/**
	 * Constructs a new Boiler test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoilerTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Boiler test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IBoiler getFixture() {
		return (IBoiler)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IBoilersFactory.eINSTANCE.createBoiler());
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

} //BoilerTest
