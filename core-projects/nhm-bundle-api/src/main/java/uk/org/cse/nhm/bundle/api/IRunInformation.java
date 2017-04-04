package uk.org.cse.nhm.bundle.api;

import java.util.Map;
import java.util.List;

/*
 * The inputs the model needs for a run
 */
public interface IRunInformation<P> {
    /*
     * A batch may contain multiple snapshots, so they are presented here
     */
    public Iterable<String> snapshots();
    public Map<String, P> stocks();
    public boolean isBatch();
}
