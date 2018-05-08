package uk.org.cse.nhm.clitools;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.errors.JasbErrorException;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.language.builder.batch.BatchExpander;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.definition.batch.XBatch;
import uk.org.cse.nhm.language.sexp.IScenarioParser;
import uk.org.cse.nhm.language.sexp.IScenarioParser.IResult;
import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;
import uk.org.cse.nhm.stock.io.StandardJacksonModule;

public class Validator {

    private static final int ENOUGH_PROBLEMS_TO_BE_GETTING_ON_WITH = 100;
    private static final int BATCH_VALIDATION_LIMIT = 100000;

    public static final String USAGE = "<scenario to validate> [base path for includes]\n"
            + "\t Validates the scenario given by the first argument; includes are resolved relative to it, or relative to the given base path. Each validation error is printed on a single line of output.";

    public static Validator create() {
        final Injector injector = Guice.createInjector(new StandardJacksonModule());
        final Validator validator = injector.getInstance(Validator.class);
        return validator;
    }

    public static void main(final String[] args) {
        disableLogging();

        final Validator validator = create();

        if (args.length < 1) {
            throw new IllegalArgumentException("");
        }

        final Path file = Paths.get(args[0]);
        final Path base = args.length == 2 ? Paths.get(args[1]).toAbsolutePath()
                : file.toAbsolutePath().getParent();

        if (!Files.exists(file)) {
            throw new IllegalArgumentException(file + " not found");
        }

        if (!Files.exists(base)) {
            throw new IllegalArgumentException(base + " not found");
        }

        validator.validate(
                file, base
        );
    }

    public static void disableLogging() {
        @SuppressWarnings("unchecked")
        final List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for (final Logger logger : loggers) {
            logger.setLevel(Level.OFF);
        }
    }

    private final IScenarioParser<XScenario> standardParser;
    private final IScenarioParser<XBatch> batchParser;
    private final BatchExpander batchExpander;
    private final IScenarioParser<XScenario> batchInstanceParser;

    @Inject
    Validator(final ScenarioParserFactory factory) {
        super();
        this.standardParser = factory.buildDefaultScenarioParser();
        this.batchParser = factory.buildBatchParser();
        this.batchExpander = new BatchExpander(batchParser);
        this.batchInstanceParser = factory.buildBatchInstanceParser();
    }

    private void validate(final Path path, final Path base) {
        try {
            final IScenarioSnapshot snapshot = Util.loadSnapshot(path, base);
            final List<? extends IError> problems = validate(snapshot);
            for (final IError p : problems.subList(0, Math.min(20, problems.size()))) {
                for (final Location loc : p.getLocation()) {
                    System.out.println(String.format("%s:%d:%d:%s",
                            Paths.get(loc.name),
                            loc.line,
                            loc.column,
                            p.getMessage()
                    ));
                }
            }
        } catch (final JasbErrorException jee) {
            for (final IError e : jee.getErrors()) {
                Util.errors(base).handle(e);
            }
        }
        // lame
    }

    public List<? extends IError> validate(final IScenarioSnapshot snapshot) {
        final String firstElement = snapshot.getExpandedFirstElement().or("unknown-element");

        List<? extends IError> problems;
        Optional<IError> extraProblem = Optional.absent();

        switch (firstElement) {
            case "batch":
                problems = validateBatch(snapshot);
                break;
            default:
                extraProblem = Optional.of((BasicError.nowhere("This is not a scenario or a batch, and so cannot be directly evaluated")));
            case "scenario":
                final IResult<?> parse = standardParser.parseUnknown(snapshot);
                problems = ImmutableList.<IError>builder().addAll(parse.getErrors()).addAll(parse.getWarnings()).addAll(extraProblem.asSet()).addAll(snapshot.getProblems()).build();
                break;
        }

        return problems;
    }

    private List<IError> validateBatch(final IScenarioSnapshot snapshot) {
        final IResult<XBatch> xBatch = batchParser.parse(snapshot);

        final List<IError> problems = new ArrayList<>();

        problems.addAll(xBatch.getErrors());
        problems.addAll(xBatch.getWarnings());

        if (problems.isEmpty()) {
            final Iterator<? extends ISExpression> expanded = batchExpander.expandXBatch(xBatch);
            int counter = 0;
            while (expanded.hasNext()) {
                counter++;
                if (counter >= BATCH_VALIDATION_LIMIT) {
                    break;
                }
                final ISExpression e = expanded.next();

                final IResult<XScenario> parse = batchInstanceParser.parse(e);
                problems.addAll(parse.getErrors());
                problems.addAll(parse.getWarnings());

                if (problems.size() > ENOUGH_PROBLEMS_TO_BE_GETTING_ON_WITH) {
                    return problems;
                }
            }
        }

        return problems;
    }
}
