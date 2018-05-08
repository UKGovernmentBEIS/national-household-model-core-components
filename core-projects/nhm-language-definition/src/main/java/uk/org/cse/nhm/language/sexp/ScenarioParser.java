package uk.org.cse.nhm.language.sexp;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Validator;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.larkery.jasb.io.IAtomIO;
import com.larkery.jasb.io.IModel.IInvocationModel;
import com.larkery.jasb.io.IReader;
import com.larkery.jasb.io.impl.JASB;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.ErrorCollector;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError.Type;
import com.larkery.jasb.sexp.errors.JasbErrorException;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.parse.ValidatingVisitor;
import uk.org.cse.nhm.language.validate.FlagsValidator;
import uk.org.cse.nhm.language.validate.NoCyclesValidatorWithDelegates;
import uk.org.cse.nhm.language.validate.ObsolesenceValidator;
import uk.org.cse.nhm.language.validate.SelfValidatingVisitor;
import uk.org.cse.nhm.language.validate.contents.ContainmentValidator;
import uk.org.cse.nhm.language.validate.let.ScopingError;
import uk.org.cse.nhm.language.validate.let.ScopingValidator;

public class ScenarioParser<X extends XElement> implements IScenarioParser<X> {

    private final Class<X> outputClass;
    private final IReader reader;
    private final Validator validator;
    private final JASB jasb;

    public ScenarioParser(
            final Class<X> outputClass,
            final Validator validator,
            final Set<Class<?>> possibleClasses,
            final Set<IAtomIO> atomReaders) {

        this.outputClass = outputClass;
        this.validator = validator;
        this.jasb = JASB.of(possibleClasses, atomReaders);
        this.reader = jasb.getReader();
    }

    @Override
    public Class<X> getOutputClass() {
        return outputClass;
    }

    @Override
    public IScenarioParser.IResult<X> parse(final IScenarioSnapshot snapshot) {
        final ErrorCollector errors = new ErrorCollector();
        return parse(outputClass, errors, snapshot.withErrorHandler(errors));
    }

    @Override
    public IScenarioParser.IResult<X> parse(final ISExpression expression) {
        return parse(outputClass, new ErrorCollector(), expression);
    }

    private <Q extends XElement> IScenarioParser.IResult<Q> parse(final Class<Q> outputClass, final ErrorCollector errors, final ISExpression source) {
        final IReader.IResult<Q> read;
        try {
            read = reader.read(outputClass, source, errors);
        } catch (final JasbErrorException jee) {
            return new Result<Q>(Optional.<Q>absent(), jee.getErrors(), null, ImmutableMap.<String, Object>of());
        }
        final ImmutableList.Builder<IError> problems = ImmutableList.builder();

        problems.addAll(errors.getErrors());

        if (read.getValue().isPresent()) {
            final Q output = read.getValue().get();

            PathingSequencingVisitor.applyTo(output);

            checkValidation(validator, problems, output);

            checkForCyclesContainmentAndObsolesence(problems, output);
        }

        return new Result<Q>(read.getValue(), problems.build(), read.getNode(), read.getCrossReferences());
    }

    private static void checkValidation(final Validator validator, final ImmutableList.Builder<IError> problems, final XElement output) {
        final ValidatingVisitor validatingVisitor = new ValidatingVisitor(validator);

        output.accept(validatingVisitor);

        problems.addAll(validatingVisitor.getViolations());
    }

    private static void checkForCyclesContainmentAndObsolesence(final ImmutableList.Builder<IError> problems, final XElement output) {
        final ObsolesenceValidator deprecated = new ObsolesenceValidator();
        final ContainmentValidator fcv = new ContainmentValidator();
        final ScopingValidator scopes = new ScopingValidator();
        final SelfValidatingVisitor svv = new SelfValidatingVisitor();
        final FlagsValidator flagv = new FlagsValidator();

        final NoCyclesValidatorWithDelegates noCyclesValidator
                = new NoCyclesValidatorWithDelegates(ImmutableList.of(deprecated, fcv, scopes, svv, flagv));
        output.accept(noCyclesValidator);
        problems.addAll(noCyclesValidator.getProblems());

        for (final ScopingError e : scopes.getErrors()) {
            problems.add(BasicError.at(
                    e.getElement().getLocation(),
                    e.getMessage())
            );
        }

        problems.addAll(deprecated.getProblems());
        problems.addAll(fcv.getProblems());
        problems.addAll(svv.getProblems());
        problems.addAll(flagv.getProblems());
    }

    @SuppressWarnings("unchecked")
    @Override
    public IResult<?> parseUnknown(final IScenarioSnapshot snapshot) {
        final Optional<String> firstAtom = snapshot.getExpandedFirstElement();

        try {
            if (!firstAtom.isPresent()) {
                throw new IllegalArgumentException("Unable to find a starting term to validate.");
            }

            Class<?> tryMatching = null;
            for (final IInvocationModel im : jasb.getModel().getInvocations()) {
                if (im.getName().equals(firstAtom.get())) {
                    if (XElement.class.isAssignableFrom(im.getJavaType())) {
                        if (tryMatching == null) {
                            tryMatching = im.getJavaType();
                        } else {
                            throw new IllegalArgumentException("Multiple language terms are named " + firstAtom.get() + ", so this scenario cannot be unambiguously validated.");
                        }
                    }
                }
            }

            if (tryMatching == null) {
                throw new IllegalArgumentException(firstAtom.get() + " is not a known term in the language.");
            }
            final ErrorCollector collector = new ErrorCollector();

            return parse(
                    (Class<? extends XElement>) tryMatching,
                    collector,
                    snapshot.withErrorHandler(collector));
        } catch (final IllegalArgumentException iae) {
            return new Result<XElement>(Optional.<XElement>absent(),
                    ImmutableList.<IError>of(BasicError.nowhere(iae.getMessage())),
                    null, ImmutableMap.<String, Object>of()
            );
        }
    }

    static class Result<X extends XElement> implements IResult<X> {

        private final Optional<X> output;
        private final Node input;
        private final List<IError> warnings, errors;
        private final Map<String, Object> definitions;

        Result(final Optional<X> output, final List<IError> problems, final Node input, final Map<String, Object> definitions) {
            super();
            this.output = output;
            this.input = input;
            this.definitions = definitions;

            final ImmutableList.Builder<IError> warnings = ImmutableList.builder();
            final ImmutableList.Builder<IError> errors = ImmutableList.builder();
            for (final IError p : problems) {
                if (p.getType() == Type.WARNING) {
                    warnings.add(p);
                } else {
                    errors.add(p);
                }
            }

            this.warnings = warnings.build();
            this.errors = errors.build();
        }

        @Override
        public Map<String, Object> getDefinitions() {
            return definitions;
        }

        @Override
        public boolean hasErrors() {
            return (!errors.isEmpty()) || (!output.isPresent());
        }

        @Override
        public boolean hasWarnings() {
            return !warnings.isEmpty();
        }

        @Override
        public Node getInput() {
            return input;
        }

        @Override
        public Optional<X> getOutput() {
            return output;
        }

        @Override
        public List<IError> getErrors() {
            return errors;
        }

        @Override
        public List<IError> getWarnings() {
            return warnings;
        }
    }
}
