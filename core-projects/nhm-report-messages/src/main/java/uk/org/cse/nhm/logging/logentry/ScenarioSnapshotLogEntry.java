package uk.org.cse.nhm.logging.logentry;

import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;

@AutoProperty
public class ScenarioSnapshotLogEntry extends AbstractLogEntry {

    private final IScenarioSnapshot snapshot;

    @JsonCreator
    public ScenarioSnapshotLogEntry(@JsonProperty("snapshot") final IScenarioSnapshot snapshot) {
        super();
        this.snapshot = snapshot;
    }

    public IScenarioSnapshot getSnapshot() {
        return snapshot;
    }
}
