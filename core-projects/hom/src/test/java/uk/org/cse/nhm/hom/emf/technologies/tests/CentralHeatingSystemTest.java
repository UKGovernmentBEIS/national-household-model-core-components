/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.PumpAndFanConstants;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.Pump;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

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
	
	@Test
	public void testPumpGainsForCommunity() {
		final ICentralHeatingSystem system = getFixture();
		
		final IHeatProportions p = new IHeatProportions() {
			
			@Override
			public double spaceHeatingProportion(ISpaceHeater heatSource) {
				return 0;
			}
			
			@Override
			public boolean providesHotWater(IWaterHeater heatSource) {
				return false;
			}
		};
		
		final IHeatSource comm = ITechnologiesFactory.eINSTANCE.createCommunityHeatSource();
		
		system.setHeatSource(comm);
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		final IInternalParameters params = mock(IInternalParameters.class);
		when(params.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(params.getTarrifType()).thenReturn(ElectricityTariffType.ECONOMY_7);
		system.accept(DefaultConstants.INSTANCE,
				params, visitor, new AtomicInteger(), p);
		
		final ArgumentCaptor<IEnergyTransducer> tc = ArgumentCaptor.forClass(IEnergyTransducer.class);
		
		verify(visitor,times(2)).visitEnergyTransducer(tc.capture());
		
		List<IEnergyTransducer> value = tc.getAllValues();
		
		for (final IEnergyTransducer v : value) {
			if (v instanceof Pump) {

				final IEnergyState state = mock(IEnergyState.class);
				v.generate(null, params, null, state);
				
				verify(state, times(0)).increaseSupply(eq(EnergyType.GainsPUMP_AND_FAN_GAINS), any(Double.class));
				return;
			}
		}
		
		Assert.fail("No pump was introduced");
	}


	@Test
	public void testPumpGainsForNotCommunity() {
		final ICentralHeatingSystem system = getFixture();
		
		final IHeatProportions p = new IHeatProportions() {
			
			@Override
			public double spaceHeatingProportion(ISpaceHeater heatSource) {
				return 0;
			}
			
			@Override
			public boolean providesHotWater(IWaterHeater heatSource) {
				return false;
			}
		};
		
		final IBoiler comm = IBoilersFactory.eINSTANCE.createBoiler();
		comm.setWinterEfficiency(Efficiency.ONE);
		comm.setSummerEfficiency(Efficiency.ONE);
		
		system.setHeatSource(comm);
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		final IInternalParameters params = mock(IInternalParameters.class);
		when(params.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(params.getTarrifType()).thenReturn(ElectricityTariffType.ECONOMY_7);
		system.accept(DefaultConstants.INSTANCE,
				params, visitor, new AtomicInteger(), p);
		
		final ArgumentCaptor<IEnergyTransducer> tc = ArgumentCaptor.forClass(IEnergyTransducer.class);
		
		verify(visitor,times(2)).visitEnergyTransducer(tc.capture());
		
		List<IEnergyTransducer> value = tc.getAllValues();
		
		for (final IEnergyTransducer v : value) {
			if (v instanceof Pump) {

				final IEnergyState state = mock(IEnergyState.class);
				v.generate(null, params, null, state);
				
				verify(state, times(1)).increaseSupply(eq(EnergyType.GainsPUMP_AND_FAN_GAINS), eq(PumpAndFanConstants.CENTRAL_HEATING_PUMP_GAINS.getValue(Double.class).doubleValue()));
				return;
			}
		}
		
		Assert.fail("No pump was introduced");
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
