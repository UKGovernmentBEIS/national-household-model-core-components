package uk.org.cse.nhm.clitools.bundle;

import java.util.List;
import java.util.Map;

import uk.org.cse.nhm.bundle.api.IRunInformation;

public class RunInformation<P> implements IRunInformation<P> {

    private final List<String> snapshots;
    private final Map<String, P> stocks;
    private final boolean batch;

    public RunInformation(boolean batch, List<String> snapshots, Map<String, P> stocks) {
        super();
        this.snapshots = snapshots;
        this.stocks = stocks;
        this.batch = batch;
    }

    @Override
    public Iterable<String> snapshots() {
        return snapshots;
    }

    @Override
    public Map<String, P> stocks() {
        return stocks;
    }

    @Override
    public boolean isBatch() {
        return batch;
    }
}
