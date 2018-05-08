package uk.org.cse.nhm.bundle.api;

import java.util.Set;

/**
 * The definition of a user-defined thing in a scenario
 */
public interface IDefinition<P> extends ILocated<P> {

    public enum DefinitionType {
        Template,
        Variable,
        Action,
        Test,
        Function,
        Entity
    }

    public Set<IArgument> arguments();

    public String name();

    public DefinitionType type();
}
