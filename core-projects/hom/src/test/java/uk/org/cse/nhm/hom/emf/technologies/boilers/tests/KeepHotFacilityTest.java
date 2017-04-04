/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.tests;

import junit.framework.TestCase;
import junit.textui.TestRunner;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Keep Hot Facility</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility#getAdditionalUsageLosses( uk.org.cse.nhm.energycalculator.api.IInternalParameters, uk.org.cse.nhm.energycalculator.api.IEnergyState) <em>Get Additional Usage Losses</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class KeepHotFacilityTest extends TestCase {

	/**
	 * The fixture for this Keep Hot Facility test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IKeepHotFacility fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(KeepHotFacilityTest.class);
	}

	/**
	 * Constructs a new Keep Hot Facility test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KeepHotFacilityTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Keep Hot Facility test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(IKeepHotFacility fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Keep Hot Facility test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IKeepHotFacility getFixture() {
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
		setFixture(IBoilersFactory.eINSTANCE.createKeepHotFacility());
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
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility#getAdditionalUsageLosses( uk.org.cse.nhm.energycalculator.api.IInternalParameters, uk.org.cse.nhm.energycalculator.api.IEnergyState) <em>Get Additional Usage Losses</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility#getAdditionalUsageLosses( uk.org.cse.nhm.energycalculator.api.IInternalParameters, uk.org.cse.nhm.energycalculator.api.IEnergyState)
	 * @generated no
	 */
	public void testGetAdditionalUsageLosses__IInternalParameters_IEnergyState() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

} //KeepHotFacilityTest
