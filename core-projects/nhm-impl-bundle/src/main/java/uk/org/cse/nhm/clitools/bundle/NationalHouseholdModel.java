package uk.org.cse.nhm.clitools.bundle;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Level;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.SimplePrinter;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.parse.DataSourceSnapshot;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.IFS;
import uk.org.cse.nhm.bundle.api.ILanguage;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.IRunInformation;
import uk.org.cse.nhm.bundle.api.ISimulationCallback;
import uk.org.cse.nhm.bundle.api.IStockImportCallback;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem.ProblemLevel;
import uk.org.cse.nhm.clitools.LogHelper;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot.IExpansion;
import uk.org.cse.nhm.language.builder.batch.Batch;
import uk.org.cse.nhm.language.builder.batch.BatchExpander;
import uk.org.cse.nhm.language.builder.batch.IBatchInstance;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.definition.batch.XBatch;
import uk.org.cse.nhm.language.sexp.IScenarioParser;
import uk.org.cse.nhm.language.sexp.IScenarioParser.IResult;
import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;
import uk.org.cse.nhm.logging.logentry.batch.BatchInputEntry;
import uk.org.cse.nhm.logging.logentry.errors.SystemErrorLogEntry;
import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.guice.StandaloneBatchReportingModule;
import uk.org.cse.nhm.reporting.guice.StandardReportingModule;
import uk.org.cse.nhm.reporting.standard.IReportingSession;
import uk.org.cse.nhm.simulation.simulator.SimulatorRun;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.stock.io.StandardJacksonModule;
import uk.org.cse.nhm.stockimport.simple.Util;
import uk.org.cse.nhm.stockimport.simple.dto.DTOImportPhase;
import uk.org.cse.nhm.stockimport.simple.spss.SPSSImportPhase;

public class NationalHouseholdModel implements INationalHouseholdModel {
    private static final int BATCH_PROBLEM_LIMIT = 1000;
    private static final int BATCH_VALIDATION_LIMIT = 50;
    private static final DateTimeFormatter DATE = DateTimeFormat.forPattern("dd/MM/yyyy");

    // stuff we need to make the model work:
    private final ObjectMapper mapper;
    private final BatchExpander batchExpander;
    private final IScenarioParser<XBatch> batchParser;
    private final IScenarioParser<XScenario> batchInstanceParser;
    private final IScenarioParser<XScenario> standardParser;
    private final IReportEngine standardReportEngine;
	private final IReportEngine batchReportEngine;

    public NationalHouseholdModel() {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            final Injector injector = Guice.createInjector(
                new StandaloneBatchReportingModule(),
        		new StandardReportingModule(false),
                new StandardJacksonModule());
            this.mapper = injector.getInstance(ObjectMapper.class);
            this.batchReportEngine = injector.getInstance(Key.get(IReportEngine.class, Names.named(IReportEngine.STANDALONE_BATCH_ENGINE)));
            this.standardReportEngine = injector.getInstance(Key.get(IReportEngine.class, Names.named(IReportEngine.STANDARD_ENGINE)));

            final ScenarioParserFactory factory = injector.getInstance(ScenarioParserFactory.class);
            this.standardParser = factory.buildDefaultScenarioParser();
            this.batchParser = factory.buildBatchParser();
            this.batchExpander = new BatchExpander(this.batchParser);
            this.batchInstanceParser = factory.buildBatchInstanceParser();
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
	}

    private ILanguage language = null;

    @SuppressWarnings({"serial"})
    static class Stop extends RuntimeException {
        public Stop(String s) { super(s); }
        public Stop() { super(); };
    }

    static class CallbackStoppingCriterion implements Callable<Boolean> {
        @SuppressWarnings("rawtypes")
		private final ISimulationCallback callback;
        @SuppressWarnings("rawtypes")
		public CallbackStoppingCriterion(ISimulationCallback callback) {this.callback = callback;}
        @Override public Boolean call() { return callback.shouldCancel(); }
    }

    @Override
    public <P> IncludeGraph<P> getIncludeGraph(final IFS<P> fs, final P top) {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            return FSUtil.load(fs, top).graph;
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    @Override
    public <P> Set<IDefinition<P>> getDefinitions(final IFS<P> fs, final P top) {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            return validate(fs, top).definitions();
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    private static void stopIfAnyFatal(final Collection<? extends IError> errors) {
        for (final IError e : errors) if (e.getType().isFatal()) throw new Stop();
    }

    @Override
    public <P> ValidationResult<P> validate(final IFS<P> fs, final P input) {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            final FSUtil.SnapshotAndGraph<P> snap = FSUtil.load(fs, input);

            return validate(fs, input, snap);
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    public <P> ValidationResult<P> validate(final IFS<P> fs, final P input, FSUtil.SnapshotAndGraph<P> snap) {
        final ImmutableSet.Builder<IDefinition<P>> definitions      = ImmutableSet.builder();
        final ImmutableList.Builder<IValidationProblem<P>> problems = ImmutableList.builder();

        problems.addAll(FSUtil.translateE(fs, input, snap.snapshot.getProblems(), ProblemLevel.SyntacticError));

        boolean runnable = false;
        Optional<XElement> rootElement = Optional.absent();

        try {
            stopIfAnyFatal(snap.snapshot.getProblems());

            // get macro definitions out
            final IExpansion expansion = snap.snapshot.expand();
            definitions.addAll(FSUtil.translateM(fs, input, expansion.macros()));

            problems.addAll(FSUtil.translateE(fs, input, expansion.errors(), ProblemLevel.SyntacticError));
            stopIfAnyFatal(expansion.errors());

            // find top Seq
            Seq top = null;
            for (final Node n : expansion.nodes()) {
                if (n instanceof Seq) {
                    if (top == null) top = (Seq) n;
                    else {
                        problems.add(FSUtil.translateE(fs, input, BasicError.at(n,"This file contains more than one top-level element"), ProblemLevel.SyntacticError));
                        throw new Stop();
                    }
                }
            }

            final String head =
            		top == null ? "no" :
            			top.firstAtom().or((Atom) Atom.create("no")).getValue();

            if (!ImmutableSet.of("scenario", "batch").contains(head)) {
                problems.add(new ValidationProblem<P>(
                                 input,
                                 "This file does not contain a scenario element",
                                 ProblemLevel.SyntacticError
                                 ));
                throw new Stop();
            }

            IResult<? extends XElement> parseResult = null;

            switch (head) {
            case "scenario":
                runnable = true;
                parseResult = standardParser.parse(top);
                // check for existence of stock
                if (parseResult != null && parseResult.getOutput().isPresent()) {
                    final XScenario scenario = (XScenario) parseResult.getOutput().get();
                    if (scenario.getStockID() != null) {
                        for (final String s : scenario.getStockID()) {
                            final P p = fs.resolve(String.valueOf(input), s);
                            final Path p2 = fs.filesystemPath(p);
                            if (p2 == null || !Files.exists(p2)) {
                                problems.add(FSUtil.translateE(fs, input,
                                                               BasicError.at(scenario.getSourceNode(),
                                                                             "The stock " + s + " cannot be located"),
                                                               ProblemLevel.SemanticError));
                            }
                        }

                    }
                }

                break;
            case "batch":
                runnable = true;
                final IResult<XBatch> batchResult = batchParser.parse(top);
                parseResult = batchResult;

                if (!batchResult.hasErrors()) {
                    final Iterator<? extends ISExpression> expanded = this.batchExpander.expandXBatch(batchResult);
        			int counter = 0;
        			int problemCount = 0;
        			while (expanded.hasNext() &&
                           (counter++) < BATCH_VALIDATION_LIMIT &&
                           problemCount < BATCH_PROBLEM_LIMIT) {
        				final ISExpression e = expanded.next();
        				final IResult<XScenario> parse = this.batchInstanceParser.parse(e);
        	    		problemCount += parse.getErrors().size();
                        problems.addAll(FSUtil.translateE(fs, input, parse.getErrors(), ProblemLevel.SemanticError));
                        problems.addAll(FSUtil.translateE(fs, input, parse.getWarnings(), ProblemLevel.SemanticError));
        			}
                }

                break;
            }

            if (parseResult != null) {
                problems.addAll(FSUtil.translateE(fs, input, parseResult.getErrors(), ProblemLevel.SemanticError));
                problems.addAll(FSUtil.translateE(fs, input, parseResult.getWarnings(), ProblemLevel.SemanticError));

                definitions.addAll(FSUtil.translateD(fs, input, parseResult.getDefinitions()));

                rootElement = Optional.<XElement>fromNullable(parseResult.getOutput().orNull());
            }
        } catch (Stop s) {}

        return new ValidationResult<P>(
            runnable,
            snap.graph,
            definitions.build(),
            problems.build(),
            rootElement,
            snap.snapshot
            );
    }

    @Override
    public <P> void simulate(final IFS<P> fs,
                             final P top,
                             final ISimulationCallback<P> callback) {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            callback.log("INFO", "Loading and validating " + top);
            final ValidationResult<P> validate = validate(fs, top);


            runScenario(Optional.<Map<String, String>>absent(),
                        validate, String.valueOf(top),
                        new FSStockService<P>(fs, top, mapper),
                        callback);
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    @Override
    public <P> void simulate(final IFS<P> fs,
                             final IRunInformation<P> totality,
                             final ISimulationCallback<String> callback) {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            try {

                for (final String s : totality.snapshots()) {
                    final PortableSnapshot ps = mapper.readValue(s, PortableSnapshot.class);

                    if (!version().equals(ps.version)) {
                        callback.log("WARNING", String.format("This scenario was serialized using version %s of the model, but is being run against version %s", ps.version, version()
                                         ));
                    }

                    final DataSourceSnapshot dss = ps.data;

                    // dss does not quite contain enough information to write an IFS implementation
                    // but it does contain enough information to run the model

                    runScenario(
                        Optional.fromNullable(ps.parameters),
                        validate(new IFS<String>() {
                                @Override
                                public String deserialize(String arg0) {
                                    return arg0;
                                }

                                @Override
                                public Path filesystemPath(String arg0) {
                                    return Paths.get(arg0);
                                }

                                @Override
                                public Reader open(String arg0) throws IOException {
                                    throw new UnsupportedOperationException("Data missing from snapshot - unable to open "+arg0+ " for reading.");
                                }

                                @Override
                                public String resolve(String arg0, String arg1) {
                                    if (totality.stocks().containsKey(arg1)) {
                                        return String.valueOf(totality.stocks().get(arg1));
                                    } else {
                                        throw new UnsupportedOperationException("Data missing from snapshot - unable to resolve " + arg0 + " against " + arg1);
                                    }
                                }

                            },
                            dss.root(),
                            FSUtil.load(dss)),
                        dss.root(),
                        new FSStockService<P>(fs,
                                              totality.stocks(),
                                              mapper),
                        callback);
                }
            } catch (final Exception ex) {
                callback.log("ERROR", "Unable to deserialize snapshot of scenario: "+ ex.getMessage());
                callback.failed();
            }
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    <P> void runScenario(final Optional<Map<String, String>> batchParameters,
                         final ValidationResult<P> validate,
                         final String topName,
                         final IStockService stocks,
                         final ISimulationCallback<P> callback) {

    	boolean dead = false;
    	for (final IValidationProblem<?> prob : validate.problems()) {
    		if (prob.level().isFatal()) {
    			callback.invalid(validate);
        		callback.failed();
        		return;
    		}
    	}

    	if (!validate.isScenario()) {
    		callback.invalid(validate);
    		callback.failed();
    		return;
    	}

    	callback.progress(0.05);
    	final XScenario scenario;
    	try {
            for (final IValidationProblem<?> problem : validate.problems())  {
                callback.log(String.valueOf(problem.level()).toUpperCase(),
                             String.valueOf(problem.sourceLocation() + ": " + problem.message()));
                if (problem.level().isFatal()) {
                    throw new Stop("scenario contains validation errors");
                }
            }

            // we found the thing
            final XElement root = validate.result.orNull();
            if (root instanceof XScenario) {
                scenario = (XScenario) root;
            } else if (root instanceof XBatch) {
                throw new Stop("desktop NHM does not run batch scenarios yet");
            } else {
                throw new Stop(topName + " does not contain a scenario");
            }
        } catch (Stop s) {
            callback.log("ERROR", s.getMessage());
            callback.failed();
            return;
        }

        final LogHelper log = new LogHelper() {
                @Override
                protected void log(final Level level2, final String msg) {
                    callback.log(level2.toString(), msg);
                }
            };

        log.install(Thread.currentThread());
        try {
            final long now = System.currentTimeMillis();
            final IReportingSession session;

            if (batchParameters.isPresent()) {
                session = this.batchReportEngine.startReportingSession();
                session.acceptLogEntry(new BatchInputEntry(null, ImmutableMap.copyOf(batchParameters.get())));
            } else {
                session = this.standardReportEngine.startReportingSession();
            }

            try {
                final SimulatorRun run = new SimulatorRun(100000);
                try (final ISimulator sim = run.build(scenario, "", stocks,
                                                      SimulatorRun.createStandardParameters(session))) {
                    sim.addStoppingCriterion(new CallbackStoppingCriterion(callback));
                    final long startMillis = scenario.getStartDate().getMillis();
                    final long millis = scenario.getEndDate().getMillis() - startMillis;

                    int maxSteps = 0, stepsNow = 0;
                    while ((stepsNow = sim.step()) > 0) {
                        maxSteps = Math.max(stepsNow, maxSteps);
                        final long millisDone = sim.getCurrentDate().getMillis() - startMillis;
                        callback.progress(0.05 + 0.95 *
                                          (0.75 * (millisDone / (double) millis) +
                                           0.25 * ((maxSteps - stepsNow) /  (double) maxSteps)));
                        callback.log("SIMULATOR", String.format("Simulation date: %s", DATE.print(sim.getCurrentDate())));
                        callback.log("TIME", String.format("Running for %ds", (System.currentTimeMillis() - now) / 1000));
                        callback.log("QUEUE", String.format("%d events remain in queue; maximum was %d", stepsNow, maxSteps));
                    }
                }
            } catch (final NHMException e) {
                callback.log("ERROR", "Error in run" + e.getMessage());
                session.acceptLogEntry(new SystemErrorLogEntry(e.getMessage(),
                                                               e.getModelStackTrace(),
                                                               UUID.randomUUID()));
            } catch (final Throwable th) {
                callback.log("ERROR", "Error in run" + th.getMessage());
                session.acceptLogEntry(new SystemErrorLogEntry(th, UUID.randomUUID()));
            } finally {
                session.close();
                callback.progress(1);
                callback.completed(session.getResultPath());
            }
        } catch (final IOException ex) {
            	callback.log("ERROR", ex.getMessage());
        } finally {
            log.remove();
        }
    }

    static class PortableSnapshot {
        public String version;
        public DataSourceSnapshot data;
        public Map<String, String> parameters;

        public PortableSnapshot() {}
        public PortableSnapshot(final String version, final DataSourceSnapshot data, final Map<String, String> parameters) {
            this.version = version;
            this.data = data;
            this.parameters = parameters;
        }

        public PortableSnapshot(final String version, final DataSourceSnapshot data) {
            this(version, data, null);
        }
    }

    @Override
    public <P> IRunInformation<P> getRunInformation(final IFS<P> fs,
                                                    final P top) {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            try {
                final ValidationResult<P> vr = validate(fs, top);

                final XElement element = vr.result.orNull();

                final Map<String, P> stockMap;

                final ImmutableList.Builder<String> parts = ImmutableList.builder();

                if (element instanceof XScenario) {
                    final List<String> stockID = ((XScenario) element).getStockID();
                    if (stockID == null) {
                        throw new IllegalArgumentException(top + " has a null stock-id");
                    }
                    final Map<String, P> stockMapBuilder = new HashMap<>();
                    for (final String s : stockID) {
                        stockMapBuilder.put(s, fs.resolve(String.valueOf(top), s));
                    }
                    stockMap = ImmutableMap.copyOf(stockMapBuilder);
                    final PortableSnapshot ps = new PortableSnapshot(version(),
                                                                     vr.snapshot.contents(),
                                                                     null);
                    parts.add(mapper.writeValueAsString(ps));
                } else if (element instanceof XBatch) {
                    final Batch expanded = this.batchExpander.expandXBatch((XBatch) element);
                    // for now, we are going to generate all the data in memory as byte arrays.
                    final Map<String, P> stockMapBuilder = new HashMap<>();

                    while (expanded.hasNext()) {
                        final IBatchInstance instance = expanded.next();
                        final IResult<XScenario> parsed = this.batchInstanceParser.parse(instance);
                        // we can find out the stock for this scenario
                        if (parsed.getOutput().isPresent()) {
                            final XScenario part = parsed.getOutput().get();
                            if (part.getStockID() != null) {
                                for (final String s : part.getStockID()) {
                                    final P p = fs.resolve(String.valueOf(top), s);
                                    if (p != null) {
                                        stockMapBuilder.put(s, p);
                                    }
                                }
                            }
                        }
                        final String scenarioText = SimplePrinter.toString(instance);
                        final PortableSnapshot ps = new PortableSnapshot(version(),
                                                                         DataSourceSnapshot.just(scenarioText),
                                                                         instance.getParameters());
                        parts.add(mapper.writeValueAsString(ps));
                    }
                    stockMap = ImmutableMap.copyOf(stockMapBuilder);
                } else {
                    stockMap = ImmutableMap.of();
                }

                return new RunInformation<P>(
                    element instanceof XBatch,
                    parts.build(),
                    stockMap);
            } catch (final Exception ex) {
                throw new RuntimeException (ex.getMessage(), ex);
            }
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    @Override
    public <P> void importStock(final IFS<P> fs,
                                final P stockZipFile,
                                final IStockImportCallback callback) {
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();

        try (final StockCallbackConnector conn = new StockCallbackConnector(callback)) {
            conn.install();// connects log4j up to the stock import log callback

            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            final Path stockZipFilePath = fs.filesystemPath(stockZipFile);

            final Optional<String> string = Util.getTypeOfZip(stockZipFilePath);
            if (string.isPresent()) {
                switch (string.get().toLowerCase()) {
                case "dto":
                    importDTO(stockZipFilePath, callback, conn);
                    break;
                case "spss":
                    importSPSS(stockZipFilePath, callback, conn);
                    break;
                default:
                    callback.log("ERROR", "The job type '" + string.get() + "' is not known; only 'dto' or 'spss' are understood.");
                    callback.failed();
                }
            } else {
                callback.log("ERROR", "Could not read job type from stock metadata file; there should be a file called 'metadata.csv' in the stock zip containing a line like 'type, dto' or 'type,spss'");
                callback.failed();
            }
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    private void importSPSS(final Path stockZipFile, final IStockImportCallback callback, final StockCallbackConnector conn) {
        callback.log("INFO", "Performing EHS SPSS import");
        callback.log("INFO", "The SPSS import code is probably deprecated in favour of a version written in R, which you may want to use instead");
        final SPSSImportPhase phase = new SPSSImportPhase();
        try {
            final Path temporary = Files.createTempFile(stockZipFile.getFileName().toString(), "-dto.zip");
            try {
                phase.run(stockZipFile, temporary, conn);
                importDTO(temporary, callback, conn);
            } finally {
                Files.delete(temporary);
            }
        } catch (Throwable ex) {
            final StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            callback.log("ERROR", sw.toString());
        }
    }

    private void importDTO(final Path stockZipFile, final IStockImportCallback callback, final StockCallbackConnector conn) {
        callback.log("INFO", "Performing DTO import");
        final DTOImportPhase phase = new DTOImportPhase();
        try {
            final Path temporary = Files.createTempFile(stockZipFile.getFileName().toString(), "-stock.zip");
            final List<SurveyCase> run = phase.run(stockZipFile, conn); // errors???

            try (final BufferedWriter bw =
                 new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(Files.newOutputStream(temporary)),
                		 StandardCharsets.UTF_8))) {
                for (final SurveyCase sc : run) {
                    bw.write(mapper.writeValueAsString(sc));
                    bw.write("\n");
                }
                callback.completed(temporary);
                return;
            } catch (Exception ex) {
                Files.delete(temporary);
                throw ex;
            }
        } catch (Throwable ex) {
            final StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            callback.log("ERROR", sw.toString());
        }
        callback.failed();
    }

    @Override public String version()  {return VersionUtil.VERSION;}
    @Override public String toString() {return "NHM " + version();}
    @Override
    public ILanguage language() {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            if (language == null) language = new Language();
            return language;
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

	public static NationalHouseholdModel create() {
		return new NationalHouseholdModel();
	}
}
