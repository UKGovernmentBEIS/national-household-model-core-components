/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Warm Air Circulator</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class WarmAirCirculatorTest extends CentralWaterHeaterTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(WarmAirCirculatorTest.class);
	}

	/**
	 * Constructs a new Warm Air Circulator test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WarmAirCirculatorTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Warm Air Circulator test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IWarmAirCirculator getFixture() {
		return (IWarmAirCirculator)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createWarmAirCirculator());
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
	
	@Test
	public void testHotWaterGeneration() {
		final IWarmAirCirculator c = getFixture();
		final IWarmAirSystem warmAir = ITechnologiesFactory.eINSTANCE.createWarmAirSystem();
		
		warmAir.setFuelType(FuelType.MAINS_GAS);
		warmAir.setEfficiency(Efficiency.fromDouble(0.9));
		
		final ICentralWaterSystem central = ITechnologiesFactory.eINSTANCE.createCentralWaterSystem();
		
		central.setPrimaryWaterHeater(c);
		c.setWarmAirSystem(warmAir);
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		final IEnergyState state = mock(IEnergyState.class);
		when(state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, 1d)).thenReturn(100d);
		
		c.generateHotWaterAndPrimaryGains(parameters,
				state, 
				null, 
				true, 
				1, 
				1, 
				1);
		
		verify(state).increaseDemand(EnergyType.FuelGAS, (100d + 139.1) / 0.9d);
		verify(state).increaseSupply(EnergyType.DemandsHOT_WATER, 100d);
		verify(state).increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, 139.1);
	}

} //WarmAirCirculatorTest
