package uk.org.cse.nhm.bundle.api;

import java.util.List;
import java.util.Set;

/**
 * A validation result; contains all the information gathered by running the validator.
 */
public interface IValidationResult<P> {    
    /**
     * @return true iff this was a sensible thing to validate
     */
    public boolean isScenario();

    /**
     * @return the include graph for this bit of validation
     */
    public IIncludeGraph<P> includes();

    /**
     * @return all the user-defined terms that were spotted when validating this
     */
    public Set<IDefinition<P>> definitions();

    /**
     * @return all problems in the validated thing.
     */
    public List<IValidationProblem<P>> problems();
    
    /**
     * A validation problem; has a message, a badness, and a set of locations
     */
    public interface IValidationProblem<P> extends ILocated<P> {
        public String message();

        public ProblemLevel level();
        
        public enum ProblemLevel {
            SemanticWarning,
            SemanticError,
            SyntacticError;

            public boolean isFatal() { return this != SemanticWarning; }
        }
    }
}
