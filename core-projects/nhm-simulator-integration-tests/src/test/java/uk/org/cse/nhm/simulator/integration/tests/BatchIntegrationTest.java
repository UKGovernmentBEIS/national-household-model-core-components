package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.SimplePrinter;
import com.larkery.jasb.sexp.errors.IErrorHandler;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.language.builder.batch.BatchExpander;
import uk.org.cse.nhm.language.builder.batch.IBatchInstance;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.definition.batch.XBatch;
import uk.org.cse.nhm.language.sexp.IScenarioParser;
import uk.org.cse.nhm.language.sexp.IScenarioParser.IResult;
import uk.org.cse.nhm.logging.logentry.batch.BatchInputEntry;
import uk.org.cse.nhm.logging.logentry.batch.BatchOutputEntry;
import uk.org.cse.nhm.reporting.batch.BatchLogEntryAdapter;

public class BatchIntegrationTest extends SimulatorIntegrationTest {

    private BatchExpander batchExpander;
    private IScenarioParser<XBatch> batchParser;

    protected IResult<XBatch> loadXBatch(final String scenarioName) {
        return batchParser.parse(
                classpathSource.get(getClasspathURI(scenarioName), IErrorHandler.RAISE)
        );
    }

    @Before
    public void createBatchParser() {
        batchParser = parserFactory.buildBatchParser();
        batchExpander = new BatchExpander(batchParser);
    }

    private Iterator<IBatchInstance> loadBatch(final String batchName) {
        final IResult<XBatch> xBatch = loadXBatch(batchName);
        if (xBatch.hasErrors()) {
            throw new ScenarioLoadException(batchName, xBatch.getErrors());
        }

        return batchExpander.expandXBatch(xBatch);
    }

    private void testBatch(final String file, final IStockService houses) throws NHMException, IOException, InterruptedException {
        testBatch(file, houses, new ILogEntryHandler() {

            @Override
            public void close() throws IOException {
                // NOOP

            }

            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                // NOOP
            }
        });
    }

    private void testBatch(final String file, final IStockService houses, final ILogEntryHandler logger) throws IOException, NHMException, InterruptedException {
        final Iterator<IBatchInstance> batch = loadBatch(file);
        int i = 1;

        while (batch.hasNext()) {
            final IBatchInstance current = batch.next();
            log.debug(String.format("Running %s batch run %d with inputs %s", file, i, current.getParameters()));
            log.debug(SimplePrinter.toString(current));

            final UUID id = UUID.randomUUID();

            loggingService = new BatchLogEntryAdapter(logger, id);

            loggingService.acceptLogEntry(new BatchInputEntry(id, ImmutableMap.copyOf(current.getParameters())));

            final IResult<XScenario> parse = parser.parse(current);
            if (parse.hasErrors()) {
                throw new ScenarioLoadException(file, parse.getErrors());
            }

            runSimulation(houses, parse.getOutput().get(), false, Collections.<Class<?>>emptySet());

            i++;

            tearDown();
        }

        logger.close();
    }

    @Test
    public void testSwitchingFuelTypesBatch() throws NHMException, InterruptedException, IOException {
        testBatch("batch/switchingFuelTypesBatch.s", restrictHouseCases(dataService, "H0012401"));
    }

    @Test
    public void testVaryingEfficiencyBatch() throws NHMException, InterruptedException, IOException {
        testBatch("batch/varyingEfficiencyBatch.s", restrictHouseCases(dataService, "H0012401"));
    }

    @Test
    public void testTimeSeriesBatch() throws NHMException, InterruptedException, IOException {
        testBatch(
                "batch/timeSeriesBatch.s",
                restrictHouseCases(dataService, "H0012401"),
                new ILogEntryHandler() {
            private final DateTimeFormatter dateFormat = DateTimeFormat.shortDate();
            private final Results results = new Results();
            private final DateTime twentyThirteen = new DateTime(2013, 01, 01, 0, 0);
            private final DateTime twentyFourteen = new DateTime(2014, 01, 01, 0, 0);

            @Override
            public void close() throws IOException {
                results.verify();
            }

            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                if (entry instanceof BatchOutputEntry) {
                    results.addEntry((BatchOutputEntry) entry);
                }
            }

            class Results {

                Map<UUID, RunResult> runs = new HashMap<>();

                void addEntry(final BatchOutputEntry output) {
                    final UUID runID = output.getRunID();
                    if (!runs.containsKey(runID)) {
                        runs.put(runID, new RunResult());
                    }
                    runs.get(runID).addEntry(output);
                }

                void verify() {
                    Assert.assertEquals("There should have been 4 scenario runs in this batch.", 4, runs.size());
                    for (final RunResult r : runs.values()) {
                        r.verify();
                    }
                }

                class RunResult {

                    Map<DateTime, Double> numbers = new HashMap<>();

                    void addEntry(final BatchOutputEntry entry) {
                        final DateTime date = dateFormat.parseDateTime(entry.getFullRowKey().get("date"));

                        final Double number = getByPrefix(entry.getColumns(), "time-changing-number");

                        numbers.put(date, number);
                    }

                    public void verify() {
                        Assert.assertEquals("Should be a number for 2013 and another for 2014.", ImmutableSet.of(
                                twentyThirteen,
                                twentyFourteen),
                                numbers.keySet());

                        final Double before = numbers.get(twentyThirteen);
                        final Double after = numbers.get(twentyFourteen);

                        Assert.assertNotNull("2013 value should have been added. Map contained " + numbers, before);
                        Assert.assertNotNull("2014 value should have been added. Map contained " + numbers, after);
                        Assert.assertEquals(String.format("Number should go up by 1 from 2013 %f to 2014 %f.", before, after), 1.0, after - before, 0.01);
                    }
                }
            }

            private <T> T getByPrefix(final Map<String, T> map, final String keyPrefix) {
                for (final String key : map.keySet()) {
                    if (key.startsWith(keyPrefix)) {
                        return map.get(key);
                    }
                }
                return null;
            }
        });
    }

    @Test
    public void testAffordableWarmthBatch() throws NHMException, IOException, InterruptedException {
        testBatch("green-deal/affordableWarmthBatch.s", fewerHouseCases(dataService, 0.1), new ILogEntryHandler() {
            Map<UUID, Map<String, String>> inputs = new LinkedHashMap<>();
            Map<UUID, Map<String, Double>> outputs = new LinkedHashMap<>();

            @Override
            public void close() throws IOException {
                for (final Entry<UUID, ? extends Object> e : inputs.entrySet()) {
                    System.out.println(String.format("%s -> %s", e.getValue(), outputs.get(e.getKey())));
                }

                assertIncreases("sum-of-AW-points");
                assertIncreases("sum-of-measure-costs");
                assertIncreases("count-of-installations");
                assertRatioIncreases("sum-of-measure-costs", "sum-of-AW-points");
            }

            private void assertRatioIncreases(final String numerator, final String denominator) {
                final String numKey = matchKey(numerator);
                final String denKey = matchKey(denominator);

                Double value = 0.0;
                for (final Entry<UUID, Map<String, Double>> e : outputs.entrySet()) {
                    final Double newValue = e.getValue().get(numKey) / e.getValue().get(denKey);
                    if (newValue < value) {
                        Assert.fail(String.format("Ratio %s/%s decreased from %d to %d for input %s, but should always increase.", numKey, denKey, value, newValue, getPoundPerPoint(e)));
                    }
                    value = newValue;
                }
            }

            private void assertIncreases(final String key) {
                final String actualKey = matchKey(key);

                Double value = 0.0;

                for (final Entry<UUID, Map<String, Double>> e : outputs.entrySet()) {
                    final Double newValue = e.getValue().get(actualKey);
                    if (newValue < value) {
                        Assert.fail(String.format("Value %s decreased from %d to %d for input %s, but should always increase.", key, value, newValue, getPoundPerPoint(e)));
                    }
                    value = newValue;
                }
            }

            private String getPoundPerPoint(final Entry<UUID, Map<String, Double>> e) {
                return inputs.get(e.getKey()).get("$poundPerPoint");
            }

            private String matchKey(final String key) {
                final Entry<UUID, Map<String, Double>> exampleOutput = outputs.entrySet().iterator().next();
                final Set<String> potentialKeys = exampleOutput.getValue().keySet();
                for (final String s : potentialKeys) {
                    if (s.startsWith(key)) {
                        return s;
                    }
                }
                throw new IllegalArgumentException(String.format("Could not find report column beginning with %s in %s.", key, potentialKeys));
            }

            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                if (entry instanceof BatchInputEntry) {
                    final BatchInputEntry input = (BatchInputEntry) entry;
                    if (!inputs.containsKey(input.getRunID())) {
                        inputs.put(input.getRunID(), new LinkedHashMap<String, String>());
                    }
                    inputs.get(input.getRunID()).putAll(input.getColumns());

                } else if (entry instanceof BatchOutputEntry) {
                    final BatchOutputEntry output = (BatchOutputEntry) entry;
                    if (!outputs.containsKey(output.getRunID())) {
                        outputs.put(output.getRunID(), new LinkedHashMap<String, Double>());
                    }
                    outputs.get(output.getRunID()).putAll(output.getColumns());
                }
            }
        });
    }
}
