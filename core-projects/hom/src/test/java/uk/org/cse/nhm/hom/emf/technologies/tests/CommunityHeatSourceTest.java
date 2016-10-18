/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.ECollections;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Community Heat Source</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CommunityHeatSourceTest extends HeatSourceTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(final String[] args) {
		TestRunner.run(CommunityHeatSourceTest.class);
	}

	/**
	 * Constructs a new Community Heat Source test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunityHeatSourceTest(final String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Community Heat Source test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ICommunityHeatSource getFixture() {
		return (ICommunityHeatSource)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createCommunityHeatSource());
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
	public void testAcceptFromHeating__IInternalParameters_IEnergyCalculatorVisitor_double_int() {
		final ICommunityHeatSource hs = getFixture();
		
		configureForHeat();
		
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		final IInternalParameters params = mock(IInternalParameters.class);
		when(params.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		hs.acceptFromHeating(DefaultConstants.INSTANCE, params, visitor, 0.5, 10);
		
		final ArgumentCaptor<IEnergyTransducer> ac = ArgumentCaptor.forClass(IEnergyTransducer.class);
		
		verify(visitor).visitEnergyTransducer(ac.capture());
		
		final IEnergyTransducer t = ac.getValue();
		
		Assert.assertEquals(10, t.getPriority());
		
		final IEnergyState es = mock(IEnergyState.class);
		
		when(es.getBoundedTotalHeatDemand(0.5)).thenReturn(100d);
		
		t.generate(mock(IEnergyCalculatorHouseCase.class), params, mock(ISpecificHeatLosses.class), es);
		
		
		testHeatingState(es);
	}
	
	protected void configureForHeat() {
		final ICommunityHeatSource hs = getFixture();
		hs.setFuel(FuelType.MAINS_GAS);
		hs.setChargingUsageBased(false);
		hs.setHeatEfficiency(Efficiency.fromDouble(0.5));
		
		final CentralHeatingSystemImpl heatingSystem = mock(CentralHeatingSystemImpl.class);
		when(heatingSystem.getControls()).thenReturn(ECollections.<HeatingSystemControlType>emptyEList());
		hs.setSpaceHeater(heatingSystem);
	}

	protected void testHeatingState(final IEnergyState es) {
		verify(es).increaseSupply(EnergyType.DemandsHEAT, 100d);
		
		// control factor but not efficiency?
		verify(es).increaseDemand(EnergyType.FuelCOMMUNITY_HEAT, 100 * 1.1 * 1.1);
		
		verify(es).increaseDemand(EnergyType.CommunityGAS, 2 * 100 * 1.1 * 1.1);
	}

	@Override
	public void testGenerateHotWater__IInternalParameters_IEnergyState_IWaterTank_boolean_double_double_double() {
		final ICommunityHeatSource hs = getFixture();
		
		configureForHotWater();
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		when(state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, 0.8)).thenReturn(234d);
		
		hs.generateHotWaterAndPrimaryGains(parameters, state, null, false, 0.6, 1, 0.8);
		
		testHotWaterState(state);
	}
	
	protected void configureForHotWater() {
		final ICommunityHeatSource hs = getFixture();
		hs.setFuel(FuelType.MAINS_GAS);
		hs.setHeatEfficiency(Efficiency.fromDouble(0.5));
	}

	protected void testHotWaterState(final IEnergyState state) {
		verify(state).increaseSupply(EnergyType.DemandsHOT_WATER, 234d);
		verify(state).increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, 0.6 * 41.0683);
		
		verify(state).increaseDemand(EnergyType.FuelCOMMUNITY_HEAT, (234 + 0.6*41.0683) * 1.1 * 1.05);
		verify(state).increaseDemand(EnergyType.CommunityGAS, 2 * (234 + 0.6*41.0683) * 1.1 * 1.05);
	}
} //CommunityHeatSourceTest
