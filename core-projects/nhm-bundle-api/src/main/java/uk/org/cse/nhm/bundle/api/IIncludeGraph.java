package uk.org.cse.nhm.bundle.api;

import java.util.Set;

/**
 * Stores which scenarios have included which other scenarios.
 */
public interface IIncludeGraph<P> {

    public Set<P> getInputs(final P scenario, final boolean transitive);

    public Set<P> getOutputs(final P scenario, final boolean transitive);
}
