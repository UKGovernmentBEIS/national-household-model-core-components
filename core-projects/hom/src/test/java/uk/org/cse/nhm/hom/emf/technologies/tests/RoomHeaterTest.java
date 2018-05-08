/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.hom.DummyHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Room Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
 * <em>Accept</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RoomHeaterTest extends SpaceHeaterTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(final String[] args) {
        TestRunner.run(RoomHeaterTest.class);
    }

    /**
     * Constructs a new Room Heater test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public RoomHeaterTest(final String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Room Heater test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected IRoomHeater getFixture() {
        return (IRoomHeater) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#setUp()
     *
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(ITechnologiesFactory.eINSTANCE.createRoomHeater());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#tearDown()
     *
     * @generated
     */
    @Override
    protected void tearDown() throws Exception {
        setFixture(null);
    }

    /**
     * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
     * <em>Accept</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants,
     * IEnergyCalculatorParameters,
     * uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor,
     * java.util.concurrent.atomic.AtomicInteger, double, double)
     * @generated no
     */
    public void testAccept__IInternalParameters_IEnergyCalculatorVisitor_AtomicInteger() {
        final IRoomHeater heater = getFixture();
        heater.setEfficiency(Efficiency.ONE);
        final AtomicInteger ai = new AtomicInteger(101);

        final IInternalParameters parameters = mock(IInternalParameters.class);
        final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);

        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        heater.accept(DefaultConstants.INSTANCE, parameters, visitor, ai, DummyHeatProportions.instance);

        final ArgumentCaptor<IHeatingSystem> hsArgumentCaptor = ArgumentCaptor.forClass(IHeatingSystem.class);

        verify(visitor).visitHeatingSystem(hsArgumentCaptor.capture(), eq(1.0));
        final IHeatingSystem hs = hsArgumentCaptor.getValue();

        Assert.assertEquals(0.3, hs.getDemandTemperatureAdjustment(parameters), 0);

        final ArgumentCaptor<IEnergyTransducer> etArgumentCaptor = ArgumentCaptor.forClass(IEnergyTransducer.class);

        verify(visitor).visitEnergyTransducer(etArgumentCaptor.capture());

//		ArgumentCaptor<IVentilationElement> veArgumentCaptor = ArgumentCaptor.forClass(IVentilationElement.class);
//		verify(visitor).visitVentilationElement(veArgumentCaptor.capture());
    }

} //RoomHeaterTest
