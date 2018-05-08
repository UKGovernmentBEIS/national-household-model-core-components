package uk.org.cse.nhm.ipc.api.tasks.sim;

import java.util.Set;

/**
 * Represents the graph relating a bunch of scenarios
 *
 * @author hinton
 *
 */
public interface IScenarioTree extends IScenarioMetadata {

    public Set<IScenarioTree> getChildren();

    String getMessage();
}
