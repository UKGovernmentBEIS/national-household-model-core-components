/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import org.junit.Assert;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Central Heating System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#isThermostaticallyControlled() <em>Is Thermostatically Controlled</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters,  uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class CentralHeatingSystemTest extends SpaceHeaterTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CentralHeatingSystemTest.class);
	}

	/**
	 * Constructs a new Central Heating System test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CentralHeatingSystemTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Central Heating System test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ICentralHeatingSystem getFixture() {
		return (ICentralHeatingSystem)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createCentralHeatingSystem());
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
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#isThermostaticallyControlled() <em>Is Thermostatically Controlled</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#isThermostaticallyControlled()
	 * @generated no
	 */
	public void testIsThermostaticallyControlled() {
		ICentralHeatingSystem system = getFixture();
		
		for (final HeatingSystemControlType ct : new HeatingSystemControlType[] {
				HeatingSystemControlType.APPLIANCE_THERMOSTAT,
				HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE,
				HeatingSystemControlType.ROOM_THERMOSTAT,
				HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL
				
		}) {
			system.getControls().clear();
			Assert.assertFalse(system.isThermostaticallyControlled());
			system.getControls().add(ct);
			Assert.assertTrue(system.isThermostaticallyControlled());
		}
	}

	/**
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters,  uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters,  uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
	 * @generated no
	 */
	public void testAccept__IInternalParameters_IEnergyCalculatorVisitor_AtomicInteger() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

	/**
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept( uk.org.cse.nhm.energycalculator.api.IInternalParameters,  uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor) <em>Accept</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept( uk.org.cse.nhm.energycalculator.api.IInternalParameters,  uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor)
	 * @generated not
	 */
	public void testAccept__IInternalParameters_IEnergyCalculatorVisitor() {
		// Ensure that you remove @generated or mark it @generated NOT

	}

} //CentralHeatingSystemTest
