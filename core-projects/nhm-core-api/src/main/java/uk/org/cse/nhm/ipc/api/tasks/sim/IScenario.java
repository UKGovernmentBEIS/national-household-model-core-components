package uk.org.cse.nhm.ipc.api.tasks.sim;

import java.util.Set;

public interface IScenario extends IScenarioMetadata {

    /**
     * @return the UUIDs of any children this scenario has.
     */
    Set<String> getChildIDs();

    /**
     * @since 4.0.0
     * @return the raw, UTF-8 encoded version of the scenario
     */
    public String getRawBytes();
}
