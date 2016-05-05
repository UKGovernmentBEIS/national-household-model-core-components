package uk.org.cse.nhm.simulation.cli;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.parse.DataSourceSnapshot;
import com.larkery.jasb.sexp.parse.IDataSource;

import uk.org.cse.nhm.ipc.api.IncludeAddress;
import uk.org.cse.nhm.ipc.api.tasks.ISimulationExecutor;
import uk.org.cse.nhm.ipc.api.tasks.impl.ScenarioSnapshot;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationSession;
import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.guice.StandardReportingModule;
import uk.org.cse.nhm.reporting.standard.IReportingSession;
import uk.org.cse.nhm.simulation.cli.guice.CliSimulationExecutorModule;
import uk.org.cse.nhm.stock.io.StandardJacksonModule;

public class SimulationCommandLineInterface {
    public static final FileSystem FILESYSTEM = FileSystems.getDefault();

    public static void main(final String[] args) throws Exception {
        final UUID sessionId = UUID.randomUUID();

        // Validation
        validateCommmandLineInputs(args);

        // Create properties needed for input
        final Path scenarioRootDirectory = FILESYSTEM.getPath(args[1]);
        final String scenarioNameOrId = args[2];
        final Path reportOutput = FILESYSTEM.getPath(args[3]);
        Path scenarioIncludeDirectory = null;
        try {
            scenarioIncludeDirectory = FILESYSTEM.getPath(args[4]);
            System.out.println(String.format("Using alternate include directory:%s", scenarioIncludeDirectory));
        } catch (final ArrayIndexOutOfBoundsException exp) {
            // Do nothing final arg is optional
        }

        // Construct the simulation
        final Injector injector = Guice.createInjector(
                new CliSimulationExecutorModule(args[0]),
                new StandardReportingModule(true),
                new StandardJacksonModule());

        final IReportEngine reportEngine = injector.getInstance(Key.get(IReportEngine.class,
                Names.named(IReportEngine.STANDARD_ENGINE)));
        final IReportingSession reportingSession = reportEngine.startReportingSession();
        final ISimulationSession simulationSession = new CliSimulationSession(reportingSession);
        final ISExpression scenario = loadScenario(scenarioRootDirectory, scenarioIncludeDirectory, scenarioNameOrId);

        // Run the Simulation
        final DateTime simulationStartTime = DateTime.now();
        final ISimulationExecutor executor = injector.getInstance(ISimulationExecutor.class);
        final List<? extends IError> problems = executor.run(sessionId, scenario, simulationSession);
        simulationSession.close();

        if (!problems.isEmpty()) {
        	boolean errors = false;
            for (final IError p : problems) {
            	Location loc = null;
            	try {
            		loc = p.getLocation().sourceLocation;
            	} catch (Exception e) {}
                System.err
                        .println(String.format("File: %s; Line: %d; %s;", loc == null ? "unknown" : loc.name, 
                        		loc == null ? 1 : loc.line, p.getMessage()));
                if (p.getType().isFatal()) errors = true;
            }
            if (errors) {
            	final int ERROR = -1;
            	System.exit(ERROR);
            }
        }

        // Generate Report
        Files.move(reportingSession.getResultPath(), reportOutput, StandardCopyOption.REPLACE_EXISTING);
        writeCompletionMessage(simulationStartTime, reportOutput);
    }

    protected static void writeCompletionMessage(final DateTime startTime, final Path reportOutput) {
        System.out.println("");
        System.out.println("### Simulation Completed ###");
        System.out.println(String.format("Time taken: %s seconds",
                DateTime.now().getSecondOfDay() - startTime.getSecondOfDay()));
        System.out.printf("Report available here: %s", reportOutput.toString());
    }

    protected static void writeValidationMessage(final String... errors) {
        System.out.println("");
        System.out.println("### NHM Simulation Engine ###");

        for (final String error : errors) {
            System.out.println(error);
        }

        System.out.println("");
        System.out.println("Stacktrace: ");
    }

    protected static void validateCommmandLineInputs(final String[] args) throws Exception {
        Path reportOutput;
        Path scenarioFilePath;
        final List<String> errors = new ArrayList<>();

        if ((args == null) || (args.length < 4)) {
            errors.add("Parameters required :-");
            errors.add("1. File path to Stock Json File");
            errors.add("2. Directory where scenarios are stored.");
            errors.add("3. Identifier or name & version for the scenario in the form 'id/scenarioId' or 'name/scenarioName-vScenarioVersion'");
            errors.add("4. File path to report output file (zip)");

        } else {
            String msg = "Error: Could not find scenario file: %s";
            try {
                scenarioFilePath = FileSystems.getDefault().getPath(args[1]);
                if (scenarioFilePath.toFile().exists() == false) {
                    errors.add(String.format(msg, args[1]));
                }
            } catch (final InvalidPathException exp) {
                errors.add(String.format(msg, args[1]));
            }

            msg = "Error: Could not create report output: s%";
            try {
                reportOutput = FileSystems.getDefault().getPath(args[3]);
                if (reportOutput.toFile().exists()) {
                    errors.add(String.format("Error: Report file already exists: %s", args[3]));
                }
            } catch (final InvalidPathException exp) {
                errors.add(String.format(msg, args[1]));
            }
        }

        if (errors.size() > 0) {
            final String[] errorMsgs = errors.toArray(new String[errors.size()]);
            writeValidationMessage(errorMsgs);
            Thread.sleep(100);
            throw new IllegalArgumentException(Arrays.toString(errorMsgs));
        }
    }

    /**
     * Attempts to find a given scenario name, if it does not find the file under root, attempts to use fall-back, when
     * using the fall back both "name/" and "id/" are stripped from the file name.
     *
     * @param root
     * @param fallback
     * @param name
     * @return
     * @since 4.3.1
     */
    static final URI resolveWithFallback(final Path root, final Path fallback, final String name) {
        final Path try1 = root.resolve(name);
        if (Files.exists(try1)) {
            return try1.toUri();
        } else if (fallback != null) {
            String cleanedFileName = StringUtils.remove(name, "name/");
            cleanedFileName = StringUtils.remove(cleanedFileName, "id/");
            return fallback.resolve(cleanedFileName).toUri();
        } else {
            System.out.println(String.format("Could not find scenario file:%s and no include dir supplied", try1));
            return try1.toUri();
        }
    }

    /**
     * Creates a scenario snapshot from the given scenarioFilePath, if includeFilePath is not a null value uses this as
     * a folder which contains the physical files for any xi:includes referenced in the scenario. If this value is not
     * included and includes are in the scenario then will use the default folder /scenario root folder/"names".
     *
     * @param scenarioAbsoluteFilePath
     * @param scenarioRootDirectory
     * @return IScenarioSnapshot
     * @throws IOException
     * @throws TransformerException
     * @throws SAXException
     * @since 4.0
     */
    public static ISExpression loadScenario(final Path scenarioRootDirectory, final Path includeDirectory,
            final String scenarioNameOrId) throws IOException, TransformerException, SAXException {
        return new ScenarioSnapshot(DataSourceSnapshot.copy(new IDataSource<URI>() {
			@Override
			public URI root() {
				return resolveWithFallback(scenarioRootDirectory, includeDirectory, scenarioNameOrId);
			}

			@Override
			public URI resolve(Seq relation, final IErrorHandler errors) {
				return resolveWithFallback(scenarioRootDirectory, includeDirectory, 
						IncludeAddress.parse(relation).makeFileName());
			}

			@Override
			public Reader open(URI resolved) throws IOException {
				return Files.newBufferedReader(Paths.get(resolved), StandardCharsets.UTF_8);
			}
		}, IErrorHandler.RAISE), ImmutableList.<IError>of());
    }
}
