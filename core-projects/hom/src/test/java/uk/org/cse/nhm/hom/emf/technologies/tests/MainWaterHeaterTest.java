/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import junit.textui.TestRunner;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Main Water Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class MainWaterHeaterTest extends CentralWaterHeaterTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(String[] args) {
        TestRunner.run(MainWaterHeaterTest.class);
    }

    /**
     * Constructs a new Main Water Heater test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public MainWaterHeaterTest(String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Main Water Heater test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected IMainWaterHeater getFixture() {
        return (IMainWaterHeater) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#setUp()
     *
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(ITechnologiesFactory.eINSTANCE.createMainWaterHeater());
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

} //MainWaterHeaterTest
