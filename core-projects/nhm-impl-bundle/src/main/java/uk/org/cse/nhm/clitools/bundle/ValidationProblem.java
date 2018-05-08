package uk.org.cse.nhm.clitools.bundle;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.bundle.api.ILocation;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;

class ValidationProblem<P> extends Located<P> implements IValidationProblem<P> {

    private final String message;
    private final ProblemLevel level;

    ValidationProblem(
            final List<ILocation<P>> locations,
            final String message,
            final ProblemLevel level) {
        super(locations);
        this.message = message;
        this.level = level;
    }

    public ValidationProblem(P input,
            final String message,
            final ProblemLevel syntacticerror) {
        this(ImmutableList.<ILocation<P>>of(new Location<P>(input)), message, syntacticerror);
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public ProblemLevel level() {
        return level;
    }

    @Override
    public String toString() {
        return String.format("%s : %s - %s", super.toString(), message, level);
    }
}
