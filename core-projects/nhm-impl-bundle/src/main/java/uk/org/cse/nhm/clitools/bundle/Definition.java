package uk.org.cse.nhm.clitools.bundle;

import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.bundle.api.IArgument;
import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.ILocation;

public class Definition<P> extends Located<P> implements IDefinition<P> {

    private final Set<IArgument> arguments;
    private final String name;
    private final DefinitionType type;

    Definition(
            final List<ILocation<P>> locations,
            final Set<IArgument> arguments,
            final String name,
            final DefinitionType type) {
        super(locations);
        this.arguments = arguments;
        this.name = name;
        this.type = type;
    }

    @Override
    public Set<IArgument> arguments() {
        return arguments;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public DefinitionType type() {
        return type;
    }
}
