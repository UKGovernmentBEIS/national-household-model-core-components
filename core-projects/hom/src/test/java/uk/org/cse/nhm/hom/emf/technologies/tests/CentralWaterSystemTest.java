/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Central Water System</b></em>'.
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
public class CentralWaterSystemTest extends WaterHeaterTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(String[] args) {
        TestRunner.run(CentralWaterSystemTest.class);
    }

    /**
     * Constructs a new Central Water System test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public CentralWaterSystemTest(String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Central Water System test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected ICentralWaterSystem getFixture() {
        return (ICentralWaterSystem) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#setUp()
     *
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(ITechnologiesFactory.eINSTANCE.createCentralWaterSystem());
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
        // TODO: implement this operation test method
        // Ensure that you remove @generated or mark it @generated NOT
//		fail();
    }

    /**
     * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(uk.org.cse.nhm.energycalculator.api.IInternalParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor)
     * <em>Accept</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(uk.org.cse.nhm.energycalculator.api.IInternalParameters,
     * uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor)
     * @generated NOT
     */
    public void testAccept__IInternalParameters_IEnergyCalculatorVisitor() {
        // Ensure that you remove @generated or mark it @generated NOT

    }

} //CentralWaterSystemTest
