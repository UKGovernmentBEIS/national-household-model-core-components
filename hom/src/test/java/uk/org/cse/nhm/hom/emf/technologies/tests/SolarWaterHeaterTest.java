/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
import uk.org.cse.nhm.hom.DummyHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.Pump;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Solar Water Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class SolarWaterHeaterTest extends CentralWaterHeaterTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SolarWaterHeaterTest.class);
	}

	/**
	 * Constructs a new Solar Water Heater test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SolarWaterHeaterTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Solar Water Heater test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ISolarWaterHeater getFixture() {
		return (ISolarWaterHeater)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createSolarWaterHeater());
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
		checkPumpIsIntroduced();
		checkPumpIsNotIntroduced();
	}

	private void checkPumpIsNotIntroduced() {
		final ICentralWaterSystem system = ITechnologiesFactory.eINSTANCE.createCentralWaterSystem();
		final ISolarWaterHeater heater = getFixture();
		
		system.setSolarWaterHeater(heater);
		heater.setPumpPhotovolatic(true);
	
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		
		heater.accept(DefaultConstants.INSTANCE, parameters, visitor, new AtomicInteger(), DummyHeatProportions.instance);
		
		verifyNoMoreInteractions(visitor);
	}

	private void checkPumpIsIntroduced() {
		final ICentralWaterSystem system = ITechnologiesFactory.eINSTANCE.createCentralWaterSystem();
		final ISolarWaterHeater heater = getFixture();
		
		system.setSolarWaterHeater(heater);
	
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		
		heater.accept(DefaultConstants.INSTANCE, parameters, visitor, new AtomicInteger(), DummyHeatProportions.instance);
		
		final ArgumentCaptor<IEnergyTransducer> tc = ArgumentCaptor.forClass(IEnergyTransducer.class);
		
		verify(visitor).visitEnergyTransducer(tc.capture());
		
		IEnergyTransducer value = tc.getValue();
		
		Assert.assertTrue(value instanceof Pump); // correct function of pump is checked elsewhere.
	}

} //SolarWaterHeaterTest
