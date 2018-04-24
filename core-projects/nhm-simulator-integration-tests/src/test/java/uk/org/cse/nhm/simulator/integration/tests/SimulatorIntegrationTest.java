package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;
import com.larkery.jasb.sexp.ISExpressionSource;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.parse.IDataSource;
import com.larkery.jasb.sexp.parse.StandardSource;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.stock.IHousingStock;
import uk.org.cse.nhm.ipc.api.scenario.IImportStatus;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.parse.LanguageElements;
import uk.org.cse.nhm.language.sexp.IScenarioParser;
import uk.org.cse.nhm.language.sexp.IScenarioParser.IResult;
import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;
import uk.org.cse.nhm.logging.logentry.SurveyCaseLogEntry;
import uk.org.cse.nhm.macros.ExtraMacros;
import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.guice.StandardReportingModule;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.StockInputReporter;
import uk.org.cse.nhm.simulation.cli.CliStockService;
import uk.org.cse.nhm.simulation.simulator.SimulatorRun;
import uk.org.cse.nhm.simulator.integration.protocols.classpath.Handler;
import uk.org.cse.nhm.simulator.integration.tests.guice.IFunctionAssertion;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestModule;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.integration.tests.xml.XAssertion;
import uk.org.cse.nhm.simulator.integration.tests.xml.XMLMeasureProbe;
import uk.org.cse.nhm.simulator.integration.tests.xml.XNumberDebugger;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.stock.io.StandardJacksonModule;

/**
 * A test which runs the full parse-build-sim cycle and checks the result works OK the scenario generated is quite a
 * simple one, which just installs cavity wall insulation at 5 houses per annum.
 *
 * @author hinton
 */
public class SimulatorIntegrationTest {
    protected static final Set<String> ADDITIONAL_PROPERTIES = ImmutableSet.of("FPFLGF", "EMPHRPX");
    protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SimulatorIntegrationTest.class);

    static class ScenarioLoadException extends RuntimeException {

        private final List<IError> problems;

        public ScenarioLoadException(final String string, final List<IError> problems) {
            super(string + Joiner.on("\n").join(problems));
            this.problems = problems;
        }

        private static final long serialVersionUID = 1L;

        public List<IError> getProblems() {
            return problems;
        }
    }

    public void saveStockAsJson() throws IOException {
        final Injector inj = Guice.createInjector(new StandardJacksonModule());

        final IOutputStreamFactory factory = new IOutputStreamFactory() {

            @Override
            public OutputStream createReportFile(final String name,
                    final Optional<IReportDescriptor> descriptor) {
                try {
                	//TODO: Eh?
                    return Files.newOutputStream(Paths.get("/home/hinton/scratch/stock.json"));
                } catch (final IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        final StockInputReporter reporter = new StockInputReporter(factory, inj.getInstance(ObjectMapper.class));

        for (final SurveyCase sc : dataService.getSurveyCases(surveyCaseSetID, ADDITIONAL_PROPERTIES)) {
            reporter.handle(new SurveyCaseLogEntry(sc));
        }

        reporter.close();
    }

    /**
     * This is an evil static, which is here to avoid repeated reloads from {@link #realDataService}. See also the
     * {@link #loadHouses()} method.
     */
    static IStockService dataService;

    /**
     * This is also set in all the scenario XML files.
     */
    protected static final String surveyCaseSetID = "test-spss-collection";

    protected static final double ERROR_DELTA = 0.000001d;
    protected static final double NO_ERROR = 0.0d;

    protected IntegrationTestOutput testExtension;
    protected SimulatorRun runner;

    protected ISimulator lastSimulator;
    protected ILogEntryHandler loggingService;
    protected IReportEngine reportservice;
    public IScenarioParser<XScenario> parser;
    protected ISExpressionSource<URI> classpathSource;
    protected ScenarioParserFactory parserFactory;
    protected Set<Class<?>> elementTypes;

    @Before
    public void createReportingService() {
        final Injector inj = Guice.createInjector(new StandardReportingModule(false));
        reportservice = inj.getInstance(Key.get(IReportEngine.class, Names.named(IReportEngine.STANDARD_ENGINE)));
    }

    /*
     * This is an evil static block, which installs the Handler for classpath:// urls into system.properties. It is
     * useful as it allows the use of XInclude to split up the test scenario files.
     */
    static {
        final String key = "java.protocol.handler.pkgs";
        String newValue = "uk.org.cse.nhm.simulator.integration.protocols";
        if (System.getProperty(key) != null) {
            final String previousValue = System.getProperty(key);
            newValue += "|" + previousValue;
        }
        System.setProperty(key, newValue);
    }

    /**
     * preloads the survey case dataset to avoid repeated mongo hits.
     *
     * @throws IOException
     */
    @Before
    public void loadHouses() throws IOException {
        if (dataService == null) {

            final Injector inj = Guice.createInjector(
                    new StandardJacksonModule()
                    );
            
            Path toZip = Paths.get("src","test","resources", "stock.zip");
            Assert.assertTrue(toZip.toFile().exists());
            
            final ZipInputStream zip = new ZipInputStream(Files.newInputStream(
            		toZip, 
            		StandardOpenOption.READ));
            
            ZipEntry entry;
            do {
                entry = zip.getNextEntry();
                entry.getName();
            } while ((entry != null) && !entry.getName().equals("stock.json"));

            dataService = new CliStockService(inj.getInstance(ObjectMapper.class),
                    Providers.<InputStream> of(zip)
                    );
        }
    }

    @Before
    public void setUp() throws JAXBException {
        Handler.evilClassLoader = getClass().getClassLoader();

        final Set<Class<?>> elementTypes = new HashSet<>();
        for (final Class<?> type : LanguageElements.get().all()) {
            elementTypes.add(type);
        }

        elementTypes.add(XAssertion.class);
        elementTypes.add(XMLMeasureProbe.class);
        elementTypes.add(XNumberDebugger.class);

        this.elementTypes = elementTypes;

        parserFactory = new ScenarioParserFactory();

        runner = new SimulatorRun(100000, new IntegrationTestModule());
        parser = parserFactory.buildCustomParser(XScenario.class, false,
                XAssertion.class,
                XMLMeasureProbe.class,
                XNumberDebugger.class);

        classpathSource = StandardSource.create(
        		new IDataSource<URI>() {
					@Override
					public URI root() {
						return null;
					}

					@Override
					public URI resolve(Seq include, IErrorHandler errors) {
						try {
                          final URI uri = URI.create(Invocation.of(include, IErrorHandler.RAISE).arguments.get("href").toString());
                          if (uri.isAbsolute()) {
                              return uri;
                          } else {
                              final URI around = URI.create(include.getLocation().name);
                              return around.resolve(uri);
                          }
                      } catch (final Exception e) {
                          e.printStackTrace();
                          throw new RuntimeException("Bad include " + include + " " + include.getLocation(), e);
                      }
					}

					@Override
					public Reader open(URI resolved) {
						try {
							return new InputStreamReader(resolved.toURL().openStream());
						} catch (IOException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
					}
				}, ExtraMacros.DEFAULT);
    }

    @After
    public void tearDown() {
        closeLastSimulator();

        loggingService = null;
    }

    public RegionalMonthlyTable getTable(final String string) throws IOException {
        return new RegionalMonthlyTable(new InputStreamReader(getClass().getResourceAsStream(
                "/energyParameters/" + string)));
    }

    abstract class CountingStateListener implements IStateListener {
        private int changes = 0;

        protected void incrementChanges() {
            changes++;
        }

        public int getChangeCount() {
            return changes;
        }
    }

    protected IStockService restrictHouseCases(final IStockService allCases, final String... restrictedSet) {
        final Set<String> aacodes = Sets.newHashSet(restrictedSet);

        return new IStockService() {
            @Override
            public IHousingStock getMetadata(final String stockID) {
                return allCases.getMetadata(stockID);
            }

            @Override
            public List<SurveyCase> getSurveyCases(final String importID, final Set<String> additionalProperties) {
                final ArrayList<SurveyCase> filtered = new ArrayList<SurveyCase>(Collections2.filter(
                        allCases.getSurveyCases(importID, additionalProperties), new Predicate<SurveyCase>() {
                            @Override
                            public boolean apply(final SurveyCase c) {
                                return aacodes.contains(c.getBasicAttributes().getAacode());
                            }
                        }));
                return filtered;
            }

            @Override
            public Set<String> getStockIDs() {
                return aacodes;
            }

            @Override
            public void completeImport(final String name, final IHousingStock metadata, final List<SurveyCase> cases) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void failImport(final String name, final String message) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean startImport(final String id) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void updateImport(final String name, final String message) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Set<String> getImportIDs() {
                throw new UnsupportedOperationException();
            }

            @Override
            public IImportStatus getImportStatus(final String id) {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<? extends IImportStatus> getImportStatuses() {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<? extends IHousingStock> getMetadata() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected IStockService fewerHouseCases(final IStockService allCases, final double proportionToUse) {
        final long oneInX = Math.round(1.0 / proportionToUse);

        return new IStockService() {
            @Override
            public IHousingStock getMetadata(final String stockID) {
                return allCases.getMetadata(stockID);
            }

            @Override
            public List<SurveyCase> getSurveyCases(final String importID, final Set<String> additionalProperties) {
                final List<SurveyCase> filtered = new ArrayList<>();

                long counter = 1;
                for (final SurveyCase c : allCases.getSurveyCases(importID, additionalProperties)) {
                    if (counter == oneInX) {
                        filtered.add(c);
                    }
                    counter = (counter % oneInX) + 1;
                }

                return filtered;
            }

            @Override
            public List<? extends IImportStatus> getImportStatuses() {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<? extends IHousingStock> getMetadata() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Set<String> getStockIDs() {
                throw new UnsupportedOperationException("Should we support this? Not sure.");
            }

            @Override
            public void completeImport(final String name, final IHousingStock metadata, final List<SurveyCase> cases) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void failImport(final String name, final String message) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean startImport(final String id) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void updateImport(final String name, final String message) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Set<String> getImportIDs() {
                throw new UnsupportedOperationException();
            }

            @Override
            public IImportStatus getImportStatus(final String id) {
                throw new UnsupportedOperationException();
            }
        };
    }

    static class LogKeeper implements ILogEntryHandler {
    	private final Set<Class<?>> keep;
    	private final ILogEntryHandler delegate;
    	public List<ISimulationLogEntry> log;
    	
    	public LogKeeper(Set<Class<?>> keep, ILogEntryHandler delegate) {
			super();
			this.keep = keep;
			this.delegate = delegate;
		}

		@Override
		public void close() throws IOException {
			delegate.close();
		}

		@Override
		public void acceptLogEntry(ISimulationLogEntry entry) {
			delegate.acceptLogEntry(entry);
			if (log != null) {
				for (final Class<?> c : keep) {
					if (c.isInstance(entry)) {
						log.add(entry);
						return;
					}
				}
			}
		}
    	
    }
    
    protected IntegrationTestOutput prepareSimulation(final IStockService houseCases, final XScenario scenario,
            Set<Class<?>> keepLog, final IStateListener... listeners) throws NHMException, InterruptedException {
        if (loggingService == null) {
            try {
                loggingService = reportservice.startReportingSession();
            } catch (final IOException e) {
                throw new RuntimeException(e.getMessage() + " creating report session", e);
            }
        }

        LogKeeper lk;
        
        if (keepLog.size() > 0) {
        	lk = new LogKeeper(keepLog, loggingService);
        } else {
        	lk = null;
        }
        
        closeLastSimulator();
        final ISimulator simulator = runner.build(scenario, "integration-test-0000", houseCases,
                SimulatorRun.createStandardParameters(filterSurveyCases(
                		lk == null ? loggingService : lk
                		)));

        testExtension = runner.getFromInjector(IntegrationTestOutput.class);

        if (lk != null) lk.log = testExtension.log;
        
        for (final IStateListener listener : listeners) {
            testExtension.state.addStateListener(listener);
        }

        lastSimulator = simulator;

        return testExtension;
    }

    private void closeLastSimulator() {
        if (lastSimulator != null) {
            lastSimulator.close();
            lastSimulator = null;
        }
    }

    private static ILogEntryHandler filterSurveyCases(final ILogEntryHandler loggingService2) {
        return new ILogEntryHandler() {

            @Override
            public void close() throws IOException {
                loggingService2.close();
            }

            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                if (entry instanceof SurveyCaseLogEntry) {
                    return;
                }
                loggingService2.acceptLogEntry(entry);
            }
        };
    }

    protected IntegrationTestOutput runSimulation(final IStockService houseCases, final XScenario scenario,
            final boolean closeLog, Set<Class<?>> keepLog, final IStateListener... listeners) throws NHMException,
            InterruptedException {
        return runSimulation(houseCases, scenario, closeLog, keepLog,
                Collections.<String, IFunctionAssertion> emptyMap(), listeners);
    }

    protected IntegrationTestOutput runSimulation(final IStockService houseCases, final XScenario scenario,
            final boolean closeLog, Set<Class<?>> keepLog,
            final Map<String, IFunctionAssertion> debugAsserts, final IStateListener... listeners) throws NHMException, InterruptedException {
        final ISimulator simulator = prepareSimulation(houseCases, scenario, keepLog, listeners).simulator;

        testExtension.asserts.clear();
        testExtension.asserts.putAll(debugAsserts);

        while (simulator.step() > 0) {
            ;
        }

        try {
            if (closeLog) {
                loggingService.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return testExtension;
    }

    protected XScenario loadScenario(final String scenarioName) {
        final IResult<XScenario> parse = parser.parse(
                classpathSource.get(getClasspathURI(scenarioName), IErrorHandler.RAISE)
                );

        if (parse.hasErrors()) {
            log.warn("parse errors in {}", scenarioName);

            for (final IError problem : parse.getErrors()) {
                log.warn("problem: {}", problem);
            }

            throw new ScenarioLoadException("Errors in " + scenarioName, parse.getErrors());

        }

        return parse.getOutput().get();
    }

    protected URI getClasspathURI(final String scenarioName) {
        return URI.create(String.format("classpath:///Scenarios/%s", scenarioName));
    }

    protected Map<String, SurveyCase> indexedDataService() {
        final Map<String, SurveyCase> index = new HashMap<>();
        for (final SurveyCase sc : dataService.getSurveyCases(surveyCaseSetID, ADDITIONAL_PROPERTIES)) {
            index.put(sc.getBasicAttributes().getAacode(), sc);
        }
        return index;
    }

    protected void checkFlagCount(final IntegrationTestOutput output, final String flag, final double expectedCount) {
        final Set<IDwelling> dwellings = output.state.getDwellings();
        float numOfDwellings = 0;
        for (final IDwelling d : dwellings) {
            final IFlags iFlags = output.state.get(output.flags, d);
            final boolean testFlag = iFlags.testFlag(flag);

            if (testFlag) {
                numOfDwellings += d.getWeight();
            }
        }

        Assert.assertEquals("Incorrect number", expectedCount, numOfDwellings, 0.1);
    }
}
