package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public class CalibrationIntegrationTests extends SimulatorIntegrationTest {

    @Test
    public void distribute() throws NHMException, InterruptedException {
        final IntegrationTestOutput result = super.runSimulation(dataService, loadScenario("calibration/polydistribute.s"), true, Collections.<Class<?>>emptySet());

        int bad = 0;
        int nan = 0;
        for (final IDwelling d : result.state.getDwellings()) {
            final IPowerTable uncal = result.state.get(result.uncalibratedPower, d);
            final IPowerTable cal = result.state.get(result.power, d);

            final double eluncal = uncal.getPowerByFuel(FuelType.PEAK_ELECTRICITY) + uncal.getPowerByFuel(FuelType.OFF_PEAK_ELECTRICITY);
            final double elcal = cal.getPowerByFuel(FuelType.PEAK_ELECTRICITY) + cal.getPowerByFuel(FuelType.OFF_PEAK_ELECTRICITY);

            if (Double.isNaN(elcal)) {
                nan++;
            } else if (Math.abs(eluncal + 100 - elcal) > 0.1) {
                bad++;
            }
        }

        Assert.assertEquals(0, bad);
        Assert.assertEquals(0, nan);
    }

    @Test
    public void directReplacement() throws NHMException, InterruptedException {
        super.runSimulation(dataService, loadScenario("calibration/directreplacement.s"), true,
                Collections.<Class<?>>emptySet(), new IStateListener() {
            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                for (final IDwelling d : state.getDwellings()) {
                    final IPowerTable uncal = state.get(testExtension.uncalibratedPower, d);
                    final IPowerTable cal = state.get(testExtension.power, d);
                    for (final FuelType ft : FuelType.values()) {
                        switch (ft) {
                            case MAINS_GAS:
                                Assert.assertEquals("Mains gas should be 99",
                                        99d,
                                        cal.getPowerByFuel(ft),
                                        0.001);
                                break;
                            default:
                                Assert.assertEquals("Other fuels should be unaffected",
                                        uncal.getPowerByFuel(ft),
                                        cal.getPowerByFuel(ft),
                                        0.001);
                                break;
                        }
                    }
                }
            }
        });
    }

    @Test
    public void simplePolynomial() throws NHMException, InterruptedException {
        super.runSimulation(dataService, loadScenario("calibration/simplepolynomial.s"), true,
                Collections.<Class<?>>emptySet(), new IStateListener() {
            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                for (final IDwelling d : state.getDwellings()) {
                    final IPowerTable uncal = state.get(testExtension.uncalibratedPower, d);
                    final IPowerTable cal = state.get(testExtension.power, d);
                    for (final FuelType ft : FuelType.values()) {
                        switch (ft) {
                            case MAINS_GAS:
                            case OFF_PEAK_ELECTRICITY:
                            case PEAK_ELECTRICITY:
                                final double u = uncal.getPowerByFuel(ft);
                                final double expected = 1 + 2 * u + 3 * Math.pow(u, 2) + 4 * Math.pow(u, 3);
                                final double actual = cal.getPowerByFuel(ft);
                                Assert.assertEquals(ft + " calibrated to 1 + 2x + 3x^2 + 4x^3",
                                        expected,
                                        actual,
                                        expected / 10000);
                                break;
                            default:
                                Assert.assertEquals("Other fuels should be unaffected",
                                        uncal.getPowerByFuel(ft),
                                        cal.getPowerByFuel(ft),
                                        0.001);
                                break;
                        }
                    }
                }
            }
        }
        );
    }

    @Test
    public void changingPolynomial() throws NHMException, InterruptedException {
        final boolean[] sawSomeFlaggedHouses = new boolean[1];
        super.runSimulation(dataService, loadScenario("calibration/changingpolynomial.s"), true,
                Collections.<Class<?>>emptySet(), new IStateListener() {
            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                System.out.println(notification.getRootScope().getTag().getIdentifier());

                for (final IDwelling d : state.getDwellings()) {
                    final IPowerTable uncal = state.get(testExtension.uncalibratedPower, d);
                    final IPowerTable cal = state.get(testExtension.power, d);
                    final IFlags f = state.get(testExtension.flags, d);

                    if (f.testFlag("affected")) {
                        for (final FuelType ft : FuelType.values()) {
                            if (ft == FuelType.MAINS_GAS) {
                                Assert.assertEquals("Mains gas should be doubled",
                                        2 * uncal.getPowerByFuel(ft),
                                        cal.getPowerByFuel(ft),
                                        0.001);
                                sawSomeFlaggedHouses[0] = true;
                            } else {
                                Assert.assertEquals("Other fuels should be unaffected",
                                        uncal.getPowerByFuel(ft),
                                        cal.getPowerByFuel(ft),
                                        0.001);
                            }
                        }
                    } else {
                        for (final FuelType ft : FuelType.values()) {
                            Assert.assertEquals("Untouched houses should be same forever",
                                    uncal.getPowerByFuel(ft),
                                    cal.getPowerByFuel(ft),
                                    0.001);
                        }
                    }
                }
            }
        }
        );
        Assert.assertTrue("The assertion did actually get tested", sawSomeFlaggedHouses[0]);
    }
}
