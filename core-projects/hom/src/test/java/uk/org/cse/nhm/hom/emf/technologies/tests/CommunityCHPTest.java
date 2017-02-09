/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.verify;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Community CHP</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CommunityCHPTest extends CommunityHeatSourceTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(final String[] args) {
		TestRunner.run(CommunityCHPTest.class);
	}

	/**
	 * Constructs a new Community CHP test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunityCHPTest(final String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Community CHP test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ICommunityCHP getFixture() {
		return (ICommunityCHP)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createCommunityCHP());
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

	@Override
	protected void configureForHeat() {
		super.configureForHeat();
		final ICommunityCHP chp = getFixture();
		chp.setElectricalEfficiency(Efficiency.fromDouble(0.25));
	}

	@Override
	protected void configureForHotWater() {
		super.configureForHotWater();
		final ICommunityCHP chp = getFixture();
		chp.setElectricalEfficiency(Efficiency.fromDouble(0.25));
	}

	@Override
	protected void testHeatingState(final IEnergyState es) {
		super.testHeatingState(es);
		verify(es).increaseSupply(EnergyType.CommunityELECTRICITY, 2  * 100 * 1.1 * 1.1 / 4);
	}

	@Override
	protected void testHotWaterState(final IEnergyState state) {
		super.testHotWaterState(state);
		verify(state).increaseSupply(EnergyType.CommunityELECTRICITY, 2 * (234 + 0.6) * 1.1 * 1.05 / 4);
	}
} //CommunityCHPTest
