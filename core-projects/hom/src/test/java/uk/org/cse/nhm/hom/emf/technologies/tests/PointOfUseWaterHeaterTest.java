/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.hom.DummyHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.PointOfUseWaterHeaterTransducer;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Point Of Use Water Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PointOfUseWaterHeaterTest extends WaterHeaterTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PointOfUseWaterHeaterTest.class);
	}

	/**
	 * Constructs a new Point Of Use Water Heater test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PointOfUseWaterHeaterTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Point Of Use Water Heater test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IPointOfUseWaterHeater getFixture() {
		return (IPointOfUseWaterHeater)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createPointOfUseWaterHeater());
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
		// See tests for PointOfUseWaterHeaterTransducer; this method is a one-liner 
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		getFixture().setEfficiency(Efficiency.ONE);
		getFixture().accept(DefaultConstants.INSTANCE, mock(IInternalParameters.class), visitor, new AtomicInteger(), DummyHeatProportions.instance);
		final ArgumentCaptor<IEnergyTransducer> etArgumentCaptor = ArgumentCaptor.forClass(IEnergyTransducer.class);
		
		verify(visitor).visitEnergyTransducer(etArgumentCaptor.capture());
	
		Assert.assertTrue(
				"This could reasonably fail, in that the implementation could return something else. This is more of a fail-safe.",
				etArgumentCaptor.getValue() instanceof PointOfUseWaterHeaterTransducer);
	}

} //PointOfUseWaterHeaterTest
