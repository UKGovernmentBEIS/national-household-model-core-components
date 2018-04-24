/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;

import org.mockito.ArgumentCaptor;

import junit.framework.TestCase;
import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.ClassEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Cooker</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(uk.org.cse.nhm.energycalculator.api.IConstants, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, uk.org.cse.nhm.hom.IHeatProportions) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class CookerTest extends TestCase {

	/**
	 * The fixture for this Cooker test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ICooker fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(final String[] args) {
		TestRunner.run(CookerTest.class);
	}

	/**
	 * Constructs a new Cooker test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CookerTest(final String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Cooker test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(final ICooker fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Cooker test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ICooker getFixture() {
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
		setFixture(ITechnologiesFactory.eINSTANCE.createCooker());
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
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(uk.org.cse.nhm.energycalculator.api.IConstants, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, uk.org.cse.nhm.hom.IHeatProportions) <em>Accept</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(uk.org.cse.nhm.energycalculator.api.IConstants, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, uk.org.cse.nhm.hom.IHeatProportions)
	 * @generated NOT
	 */
	public void testAccept__IConstants_IEnergyCalculatorParameters_IEnergyCalculatorVisitor_AtomicInteger_IHeatProportions() {
		// Noop - I don't understand these EMF generated tests.
	}

	/**
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
	 * @generated NOT
	 */
	public void testAccept__IInternalParameters_IEnergyCalculatorVisitor_AtomicInteger() {
		checkGasHob();
		checkGasOven();
		checkGasHobAndOven();
		checkElectricHobAndOven();
		checkElectricHob();
		checkElectricHobAndGasOven();
	}

	private void checkElectricHobAndGasOven() {

	}

	private void checkElectricHob() {

	}

	private void checkElectricHobAndOven() {

	}

	private void checkGasHobAndOven() {

	}

	private void checkGasOven() {

	}

	private void checkGasHob() {
		final ICooker cooker = createCooker(true);
		cooker.setHobBaseLoad(ICooker.GAS_HOB_BASE_LOAD);
		cooker.setHobFuelType(FuelType.MAINS_GAS);

		final CookerResult evaluate = evaluate(cooker);
	}

	private CookerResult evaluate(final ICooker cooker) {
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		final IEnergyCalculatorHouseCase hc = mock(IEnergyCalculatorHouseCase.class);

		when(hc.getFloorArea()).thenReturn(50d);
		when(parameters.getNumberOfOccupants()).thenReturn(3d);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);
		when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.BREDEM2012);

		cooker.accept(DefaultConstants.INSTANCE, parameters, visitor, new AtomicInteger(), null);

		final ArgumentCaptor<IEnergyTransducer> captor = ArgumentCaptor.forClass(IEnergyTransducer.class);
		verify(visitor).visitEnergyTransducer(captor.capture());

		final IEnergyTransducer value = captor.getValue();

		final IEnergyState state = new ClassEnergyState();

		value.generate(hc, parameters, mock(ISpecificHeatLosses.class), state);


		return new CookerResult(state.getTotalDemand(EnergyType.FuelGAS),
				state.getTotalDemand(EnergyType.FuelPEAK_ELECTRICITY),
				state.getTotalSupply(EnergyType.GainsCOOKING_GAINS));
	}

	private class CookerResult {
		public final double gas;
		public final double electricity;
		public final double gains;

		public CookerResult(final double gas, final double electricity, final double gains) {
			this.gas = gas;
			this.electricity = electricity;
			this.gains = gains;
		}
	}

	private ICooker createCooker(final boolean isGas) {
		return isGas ? CookerImpl.createMixed() : CookerImpl.createElectric();
	}
} //CookerTest
