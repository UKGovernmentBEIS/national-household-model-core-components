/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>technologies</b></em>' package.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TechnologiesTests extends TestSuite {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static Test suite() {
        TestSuite suite = new TechnologiesTests("technologies Tests");
        suite.addTestSuite(TechnologyModelTest.class);
        suite.addTestSuite(ApplianceTest.class);
        suite.addTestSuite(LightTest.class);
        suite.addTestSuite(CentralWaterSystemTest.class);
        suite.addTestSuite(CentralHeatingSystemTest.class);
        suite.addTestSuite(MainWaterHeaterTest.class);
        suite.addTestSuite(SolarWaterHeaterTest.class);
        suite.addTestSuite(ImmersionHeaterTest.class);
        suite.addTestSuite(CookerTest.class);
        suite.addTestSuite(StorageHeaterTest.class);
        suite.addTestSuite(CommunityHeatSourceTest.class);
        suite.addTestSuite(CommunityCHPTest.class);
        suite.addTestSuite(RoomHeaterTest.class);
        suite.addTestSuite(HeatPumpTest.class);
        suite.addTestSuite(WarmAirSystemTest.class);
        suite.addTestSuite(PointOfUseWaterHeaterTest.class);
        suite.addTestSuite(HeatPumpWarmAirSystemTest.class);
        suite.addTestSuite(WarmAirCirculatorTest.class);
        return suite;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public TechnologiesTests(String name) {
        super(name);
    }

} //TechnologiesTests
