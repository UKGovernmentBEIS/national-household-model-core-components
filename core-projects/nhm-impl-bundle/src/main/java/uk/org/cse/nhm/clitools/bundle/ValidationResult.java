package uk.org.cse.nhm.clitools.bundle;

import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.IIncludeGraph;
import uk.org.cse.nhm.bundle.api.IValidationResult;
import uk.org.cse.nhm.ipc.api.tasks.impl.ScenarioSnapshot;
import uk.org.cse.nhm.language.definition.XElement;

import com.google.common.base.Optional;

class ValidationResult<P> implements IValidationResult<P> {
	private final boolean isScenario;
	private final IncludeGraph<P> includes;
	private final Set<IDefinition<P>> definitions;
	private final List<IValidationProblem<P>> problems;
	public final Optional<? extends XElement> result;
    public final ScenarioSnapshot snapshot;
	
	ValidationResult(
        boolean isScenario,
        IncludeGraph<P> includes,
        Set<IDefinition<P>> definitions,
        List<IValidationProblem<P>> problems,
        Optional<? extends XElement> result,
        final ScenarioSnapshot snapshot
        ) {
		this.isScenario = isScenario;
		this.includes = includes;
		this.definitions = definitions;
		this.problems = problems;
		this.result = result;
        this.snapshot = snapshot;
	}

	@Override
	public boolean isScenario() {
		return isScenario;
	}

	@Override
	public IIncludeGraph<P> includes() {
		return includes;
	}

	@Override
	public Set<IDefinition<P>> definitions() {
		return definitions;
	}

	@Override
	public List<IValidationProblem<P>> problems() {
		return problems;
    }
}
