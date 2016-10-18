package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.pojomatic.Pojomatic;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.ICopyable;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.simulation.measure.boilers.DistrictHeatingMeasure;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput.MeasureInvocation;
import uk.org.cse.nhm.simulator.measure.ITechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class OtherIntegrationTests extends SimulatorIntegrationTest {

    @Test
    public void staticProperty() throws NHMException, InterruptedException {
        runSimulation(dataService, loadScenario("functions/staticproperty.s"), true, Collections.<Class<?>>emptySet());
    }

    @Test
    public void testQuantumFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = runSimulation(dataService, loadScenario("functions/quantumfunction.s"),
                true, Collections.<Class<?>>emptySet());

        for (final IDwelling d : output.state.getDwellings()) {
            final Optional<Double> register = output.state.get(output.flags, d).getRegister("quantum");

            Assert.assertTrue("Register should have been set", register.isPresent());
            Assert.assertEquals("Register should have been set to scenario quantum", 500.0, register.get(), 0);
        }
    }

    @Test
    public void testAlterWallHeatLossMeasureChangesOnlySpecifiedWalls() throws Exception {
        final double expectedUValue = 1000.00;

        final XScenario scenario = loadScenario("alterHeatLossScenario.s");
        final IState state = runSimulation(restrictHouseCases(dataService, "H0504303"), scenario, true, Collections.<Class<?>>emptySet()).state;

        for (final IDwelling dwelling : state.getDwellings()) {
            final StructureModel structure = state.get(testExtension.structure, dwelling);

            for (final Storey story : structure.getStoreys()) {
                for (final IMutableWall wall : story.getWalls()) {
                    Assert.assertEquals("U-value should be set", expectedUValue, wall.getUValue(), 0.01);
                }
            }
        }
    }

    @Test
    @Ignore
    public void greenDealPerformance() throws NHMException, InterruptedException {
        runSimulation(fewerHouseCases(dataService, 0.01), loadScenario("green-deal/greenDealPerformance.s"), true, Collections.<Class<?>>emptySet());
    }

    @Test
    public void automaticFlagsWorkCorrectly() throws NHMException, InterruptedException {
        final ICanonicalState state = runSimulation(dataService, loadScenario("automaticFlags.s"), true, Collections.<Class<?>>emptySet()).state;

        for (final IDwelling d : state.getDwellings()) {
            final IFlags flags = state.get(testExtension.flags, d);
            final ImmutableMap<String, Boolean> condition =
                    ImmutableMap.of(
                            "a", flags.testFlag("a"),
                            "b", flags.testFlag("b"));

            final ImmutableMap<String, Boolean> consequence =
                    ImmutableMap.of(
                            "affected", flags.testFlag("affected"),
                            "c", flags.testFlag("c"));
            if (condition.equals(ImmutableMap.of("a", true, "b", false))) {
                // a and not b
                Assert.assertEquals(ImmutableMap.of("affected", true, "c", false), consequence);
            } else {
                Assert.assertFalse(consequence.get("affected"));
            }
        }
    }

    @Test
    public void testTimeSeriesFunction() throws NHMException, InterruptedException {
        final XScenario scenario = loadScenario("functions/timeSeriesFunctionScenario.s");

        final ImmutableList.Builder<Double> result = ImmutableList.builder();
        loggingService = new ILogEntryHandler() {

            @Override
            public void close() throws IOException {
                // no-op
            }

            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                if (entry instanceof AggregateLogEntry) {
                    result.add(
                            Iterables.get(
                                    ((AggregateLogEntry) entry).getColumns().values(),
                                    0));
                }
            }
        };

        runSimulation(restrictHouseCases(dataService, "H0012401"), scenario, true, Collections.<Class<?>>emptySet());

        Assert.assertEquals("The time series numbers should have come out of the report.",
                ImmutableList.of(0.0, 1.0, 10.0), result.build());
    }

    @Test
    public void testChangeEfficiency() throws NHMException, InterruptedException {
        runSimulation(fewerHouseCases(dataService, 0.1), loadScenario("changeEfficiencyScenario.s"), true, Collections.<Class<?>>emptySet());
    }

    @Test
    public void testDatesMode() throws NHMException, InterruptedException {
        final Set<DateTime> reportedOn = new HashSet<>();

        loggingService = new ILogEntryHandler() {
            @Override
            public void close() throws IOException {
                // no-op
            }

            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                if (entry instanceof AggregateLogEntry) {
                    reportedOn.add(((AggregateLogEntry) entry).getDate());
                }
            }
        };

        runSimulation(restrictHouseCases(dataService, "H0012401"), loadScenario("reports/datesMode.s"), true, Collections.<Class<?>>emptySet());

        Assert.assertEquals("Should have received log entries on the requested dates.", ImmutableSet.of(
                new DateTime(2013, 01, 01, 0, 0, DateTimeZone.UTC),
                new DateTime(2013, 04, 01, 0, 0, DateTimeZone.UTC),
                new DateTime(2013, 07, 01, 0, 0, DateTimeZone.UTC),
                new DateTime(2013, 10, 01, 0, 0, DateTimeZone.UTC),
                new DateTime(2014, 01, 01, 0, 0, DateTimeZone.UTC)
                ), reportedOn);
    }

    @Test
    public void testScenarioRunsWithConstantsLoaded() throws NHMException, InterruptedException {
        final XScenario scenario = loadScenario("useDefaultEnergyConstants.s");
        runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet());
    }

    @Test
    public void testSuitabilityFilter() throws NHMException, InterruptedException {
        final XScenario scenario = loadScenario("suitabilityFilterScenario.s");

        final CountingStateListener listener = new CountingStateListener() {
            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                if (notification.getRootScope().getTag().getIdentifier().getName()
                        .startsWith("install district heating, testing its suitability first")) {
                    incrementChanges();
                    Assert.assertTrue("Dwellings should have been changed.", notification.getAllChangedDwellings()
                            .size() > 0);
                } else if (notification.getRootScope().getTag().getIdentifier().getName()
                        .startsWith("should do nothing because all dwellings will be unsuitable")) {
                    Assert.fail("The do nothing target should never make a change notification, as it should be unsuitable.");
                } else {
                    System.err.println("wat?" + notification.getRootScope().getTag().getIdentifier().getName());
                }
            }
        };

        final String houseKnownToBeSuitableForDistrictHeating = "H0271307";
        runSimulation(restrictHouseCases(dataService, houseKnownToBeSuitableForDistrictHeating), scenario, true,
                Collections.<Class<?>>emptySet(), listener);

        Assert.assertEquals("The target should have been successfully applied.", 1, listener.getChangeCount());
    }

    private boolean isCausedByMeasure(final IStateChangeNotification notification) {
        return (notification.getRootScope().getTag().getSourceType() == StateChangeSourceType.ACTION)
                || (notification.getRootScope().getTag().getSourceType() == StateChangeSourceType.TRIGGER);
    }

    @Test
    public void testProbes() throws NHMException, InterruptedException {
        final List<ExplainLogEntry> explanations = new ArrayList<>();

        loggingService = new ILogEntryHandler() {
            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                if (entry instanceof ExplainLogEntry) {
                    explanations.add((ExplainLogEntry) entry);
                }
            }

            @Override
            public void close() throws IOException {

            }
        };

        final XScenario scenario = loadScenario("probeScenario.s");
        runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet());
        Assert.assertEquals(
                "Expected 2 explain log entries: first when everything was created by the stock creator, and again when it was all destroyed by the destroy action.",
                2, explanations.size());

        final List<ExplainArrow> create = explanations.get(0).getArrows();
        Assert.assertEquals(1, create.size());
        final ExplainArrow createArrow = create.get(0);
        Assert.assertEquals("OUTSIDE", createArrow.getFrom());
        Assert.assertEquals(2.23E7, createArrow.getCount(), 1E5);

        final List<ExplainArrow> destroy = explanations.get(1).getArrows();
        Assert.assertEquals(1, destroy.size());
        final ExplainArrow destroyArrow = destroy.get(0);
        Assert.assertEquals("OUTSIDE", destroyArrow.getTo());
        Assert.assertEquals(2.23E7, destroyArrow.getCount(), 1E5);
    }

    @Test
    public void testHousePropertyStore() throws NHMException, InterruptedException {
        final XScenario scenario = loadScenario("housePropertiesScenario.s");
        final CountingStateListener listener = new CountingStateListener() {

            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                if (notification.getRootScope().getTag().getSourceType() == StateChangeSourceType.TRIGGER) {
                    incrementChanges();
                    final String changeName = notification.getRootScope().getTag().getIdentifier().getName();
                    Assert.assertTrue("Change was caused by something other than the target: " + changeName,
                            changeName.startsWith("build more EHS fuel poverty homes"));
                    Assert.assertEquals(8789, notification.getCreatedDwellings().size());
                }
            }
        };

        runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet(), listener);
        Assert.assertEquals(1, listener.getChangeCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShortExposureTimeScenarioFailsWithNiceError() throws JAXBException, NHMException,
            InterruptedException {
        final XScenario scenario = loadScenario("shortExposureTimeScenario.s");
        runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet()).state.getDwellings();
    }

    @Test
    public void testZeroChoiceProportionScenario() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("zeroChoiceProportionScenario.s");
        runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet(), new IStateListener() {
            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                Assert.assertFalse("Zero choice proportion target should not cause changes.",
                        isCausedByMeasure(notification));
            }
        });
    }

    @Test
    public void testAllHousesScenario() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("allHousesScenario.s");
        final CountingStateListener listener = new CountingStateListener() {

            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                if (isCausedByMeasure(notification)) {
                    incrementChanges();

                    Assert.assertEquals("Measure should have affected all dwellings.",
                            Sets.newHashSet(state.getDwellings()),
                            Sets.newHashSet(notification.getAllChangedDwellings()));
                }
            }
        };

        runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet(), listener);

        Assert.assertEquals("State should have changed during this test.", 1, listener.getChangeCount());
    }

    @Test
    public void testDemolishHouses() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("demolishHousesScenario.s");
        final IState state = runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet()).state;
        Assert.assertEquals("All dwellings should have been demolished.", 0, state.getDwellings().size());
    }

    @Test
    public void testTimeSeriesExposure() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("timeSeriesExposureScenario.s");
        runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet());
        final List<MeasureInvocation> pingdates = testExtension.getInvocationsForId("ping");
        Assert.assertEquals(3, pingdates.size());
        Assert.assertEquals(100, pingdates.get(0).count);
        Assert.assertEquals(300, pingdates.get(1).count);
        Assert.assertEquals(400, pingdates.get(2).count);

        Assert.assertEquals(new DateTime(2013, 01, 01, 0, 0, 0, 0, DateTimeZone.UTC), pingdates.get(0).when);
        Assert.assertEquals(new DateTime(2014, 01, 01, 0, 0, 0, 0, DateTimeZone.UTC), pingdates.get(1).when);
        Assert.assertEquals(new DateTime(2015, 01, 01, 0, 0, 0, 0, DateTimeZone.UTC), pingdates.get(2).when);
    }

    @Test
    public void testSimpleConDemScenario() throws NHMException, InterruptedException {
        final XScenario scenario = loadScenario("simpleConDemScenario.s");
        runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet());
    }

    @Test
    public void testLoftInsulationIsInstalledToMaxThickness() throws Exception {
        final double expectedInsulationDepth = 1000.00;

        final XScenario scenario = loadScenario("loftInsulationScenario.s");
        final IState state = runSimulation(restrictHouseCases(dataService, "H0504303"), scenario, true, Collections.<Class<?>>emptySet()).state;
        final IDwelling instance = state.getDwellings().iterator().next();
        final StructureModel structure = state.get(testExtension.structure, instance);

        Assert.assertEquals(expectedInsulationDepth, structure.getRoofInsulationThickness(), 0.01);
    }

    @Test
    public void testConstructHouses() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("constructHousesScenario.s");
        final CountingStateListener listener = new CountingStateListener() {

            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                final Set<? extends IDwelling> created = notification.getCreatedDwellings();
                if (created.size() > 0) {
                    incrementChanges();
                    if (getChangeCount() > 1) {
                        Assert.assertEquals(
                                "Running the construct houses action once should have duplicated all existing houses.",
                                state.getDwellings().size() / 2, created.size());
                    }
                }
            }
        };
        runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet(), listener);
        Assert.assertEquals(
                "Buildings should have been constructed twice: once at scenario start, then again when the measure was invoked.",
                2, listener.getChangeCount());
    }

    /**
     * Builds and runs a simulator with the xml from scenario2.s and one house case (H0132105), which is known to be
     * suitable for district heating. The scenario is modified to run for 1 year only. Tests that district heating was
     * installed.
     *
     * @throws JAXBException
     * @throws NHMException
     * @throws InterruptedException
     */
    @Test
    public void testInstallsDistrictHeating() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("installDistrictHeating.s");

        runSimulation(restrictHouseCases(dataService, "H0132105"), scenario, true, Collections.<Class<?>>emptySet());
        final Collection<? extends IDwelling> dwellings = testExtension.state.getDwellings();
        final IPrimarySpaceHeater primaryHeating = testExtension.state.get(testExtension.technology,
                Iterables.get(dwellings, 0)).getPrimarySpaceHeater();
        Assert.assertTrue(primaryHeating instanceof ICentralHeatingSystem);
        Assert.assertTrue(((ICentralHeatingSystem) primaryHeating).getHeatSource() instanceof ICommunityHeatSource);
    }

    @Test
    public void testMeasureSizingUsesPeakLoad() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("sizingScenario.s");

        final CountingStateListener listener = new CountingStateListener() {

            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                if (notification.getRootScope().getTag().getIdentifier().getName()
                        .startsWith("install DH for all houses")) {
                    incrementChanges();

                    for (final IDwelling dwelling : state.getDwellings()) {
                        final List<ITechnologyInstallationDetails> installationLog = notification.
                                getRootScope().getComponentsScope(dwelling).get()
                                .getAllNotes(ITechnologyInstallationDetails.class);
                        for (final ITechnologyInstallationDetails details : installationLog) {
                            if (details.getInstallationSource() instanceof DistrictHeatingMeasure) {
                                if (details.getInstalledQuantity() == 100d) {
                                    return;
                                }
                            }
                        }
                    }

                    Assert.fail("Should have installed a measure of sufficient size to meet peak load.");
                }
            }
        };

        // dwelling known to have no existing heating
        runSimulation(restrictHouseCases(dataService, "H0271307"), scenario, true, Collections.<Class<?>>emptySet(), listener);

        Assert.assertEquals("1 measure should have been installed by this scenario.", 1, listener.getChangeCount());
    }

    @Test
    public void testMeasuresSizingAffectedByColdestDayAndDemandTemperatureScenarioParameters() throws JAXBException,
            NHMException, InterruptedException {
        final XScenario scenario = loadScenario("sizingScenarioWarmDaysAndLowHeatingDemands.s");

        final CountingStateListener listener = new CountingStateListener() {
            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                if (notification.getRootScope().getTag().getIdentifier().getName()
                        .startsWith("install DH for all houses")) {
                    incrementChanges();
                    for (final IDwelling dwelling : state.getDwellings()) {
                        final List<ITechnologyInstallationDetails> installLog =
                                notification.getAllNotes(dwelling, ITechnologyInstallationDetails.class);
                        Assert.assertEquals("Should have installed a measure of small size due to small peak load.",
                                0d, installLog.get(0).getInstalledQuantity(), 0.01);
                    }
                }
            }
        };

        // dwelling known to have no existing heating
        runSimulation(restrictHouseCases(dataService, "H0271307"), scenario, true, Collections.<Class<?>>emptySet(), listener);

        Assert.assertEquals("1 measure should have been installed by this scenario.", 1, listener.getChangeCount());
    }

    @Test
    public void testCaseWhen() throws NHMException, InterruptedException, JAXBException {
        final XScenario scenario = loadScenario("caseWhenScenario.s");

        runSimulation(restrictHouseCases(dataService, "H0271307"), scenario, true, Collections.<Class<?>>emptySet());

        assertProbeHitOnceWithNDwellings("False condition should not affect any dwellings.", "ignore", 0);
        assertProbeHitOnceWithNDwellings("All remaining dwellings should fall through.", "catch", 3);

        assertProbeHitOnceWithNDwellings("True condition as first case should affect all dwellings.", "catch first", 3);
        assertProbeHitOnceWithNDwellings("No dwellings should be left to fall through.", "nothing left", 0);
    }

    private void assertProbeHitOnceWithNDwellings(final String message, final String id, final int dwellingsAffected) {
        final List<MeasureInvocation> invocations = testExtension.getInvocationsForId(id);
        Assert.assertEquals(1, invocations.size());
        Assert.assertEquals(message, dwellingsAffected, invocations.get(0).count);
    }

    private <T> void testCopyThing(final ICopyable<T> thing) {
        if (!(thing.equals(thing.copy()))) {
            Assert.fail("Copied sub model has bad diff: " + Pojomatic.diff((Object) thing, (Object) thing.copy()));
        }
    }

    @Test
    public void testCopyModels() {
        final List<SurveyCase> allSurveyCases = dataService.getSurveyCases(surveyCaseSetID, ADDITIONAL_PROPERTIES);
        int counter = 0;
        for (final SurveyCase s : allSurveyCases) {
            /*
             * There are some wrong houses in the stock we are running against. They have loft insulation thickness, but
             * no loft. testCopyThing(s.getStructure());
             */
            testCopyThing(s.getBasicAttributes());
            testCopyThing(s.getFinancialAttributes());
            testCopyThing(s.getPeople());
            counter++;
        }
        log.debug("Copied all {} houses OK", counter);
    }

    @Test
    public void testInstallHotWaterCylinderStat() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("hotwaterstat.s");
        runSimulation(restrictHouseCases(dataService, "H0764401"), scenario, true, Collections.<Class<?>>emptySet());

        checkFlagCount(testExtension, "has-thermostat", 1301);
    }
    
    @Test
    public void testInstallHotWaterCylinderInsulation() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("hotwatercylinderinsulation.s");
        runSimulation(restrictHouseCases(dataService, "H0764401"), scenario, true, Collections.<Class<?>>emptySet());

        checkFlagCount(testExtension, "insulation-installed", 1301);
    }
    
    @Test
    public void testInstallWarmAirHeatingSystem() throws JAXBException, NHMException, InterruptedException {
        final XScenario scenario = loadScenario("warmairheatingsystem.s");
        //House case with a warm-air-heating system fitted which uses mains gas and has an efficiency of 0.7
        runSimulation(restrictHouseCases(dataService, "H0181311"), scenario, true, Collections.<Class<?>>emptySet());

        checkFlagCount(testExtension, "insulation-installed", 1644d);
    }

    @Test
    public void housesAreConstructedOverTime() throws Exception {
        runSimulation(dataService, loadScenario("constructOverTime.s"),
                      true, Collections.<Class<?>>emptySet());
    }
}
