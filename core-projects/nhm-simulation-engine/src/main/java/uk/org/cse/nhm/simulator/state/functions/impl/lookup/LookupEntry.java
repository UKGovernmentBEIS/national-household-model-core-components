package uk.org.cse.nhm.simulator.state.functions.impl.lookup;

import java.util.List;

import uk.org.cse.nhm.language.definition.function.lookup.LookupRule;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class LookupEntry {

    private final List<LookupRule> rules;
    private final IComponentsFunction<? extends Number> value;

    public LookupEntry(final List<LookupRule> rules,
            final IComponentsFunction<? extends Number> value) {
        super();
        this.rules = rules;
        this.value = value;
    }

    public List<LookupRule> getRules() {
        return rules;
    }

    public IComponentsFunction<? extends Number> getValue() {
        return value;
    }
}
