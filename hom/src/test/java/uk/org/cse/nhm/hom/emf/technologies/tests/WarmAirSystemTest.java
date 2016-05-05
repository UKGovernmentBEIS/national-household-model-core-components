/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.impl.HeatTransducer;
import uk.org.cse.nhm.hom.DummyHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.WarmAirFans;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Warm Air System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class WarmAirSystemTest extends SpaceHeaterTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(WarmAirSystemTest.class);
	}

	/**
	 * Constructs a new Warm Air System test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WarmAirSystemTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Warm Air System test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IWarmAirSystem getFixture() {
		return (IWarmAirSystem)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createWarmAirSystem());
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
		// warm air systems are quite simple, and should just make some heat and add a fan
		final IWarmAirSystem system = getFixture();
		system.setFuelType(FuelType.MAINS_GAS);
		system.setEfficiency(Efficiency.fromDouble(1));
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		
		system.accept(DefaultConstants.INSTANCE, parameters, visitor, new AtomicInteger(), DummyHeatProportions.instance);
		
		final ArgumentCaptor<IEnergyTransducer> etArgumentCaptor = ArgumentCaptor.forClass(IEnergyTransducer.class);
		verify(visitor, times(2)).visitEnergyTransducer(etArgumentCaptor.capture());
		
		boolean seenFans = false;
		boolean seenHT = false;
		
		for (final IEnergyTransducer et : etArgumentCaptor.getAllValues()) {
			if (et instanceof WarmAirFans) {
				seenFans = true;
			} else if (et instanceof HeatTransducer) {
				seenHT = true;
			}
		}
		
		Assert.assertTrue(seenFans);
		Assert.assertTrue(seenHT);
	}
} //WarmAirSystemTest
