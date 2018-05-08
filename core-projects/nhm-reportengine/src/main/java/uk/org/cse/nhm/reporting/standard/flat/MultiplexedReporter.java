package uk.org.cse.nhm.reporting.standard.flat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;

/**
 * A class for handling reports which are multiplexed
 *
 * @author hinton
 *
 */
public abstract class MultiplexedReporter<T extends ISimulationLogEntry> implements IReporter {

    private final Class<T> entryClass;
    protected final IOutputStreamFactory factory;
    private final Map<String, IReporter> delegates = new HashMap<>();

    public MultiplexedReporter(Class<T> entryClass,
            IOutputStreamFactory factory) {
        super();
        this.entryClass = entryClass;
        this.factory = factory;
    }

    @Override
    public void close() throws IOException {
        for (final IReporter s : delegates.values()) {
            s.close();
        }
    }

    protected Set<String> getChildNames() {
        return delegates.keySet();
    }

    @Override
    public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
        return ImmutableSet.<Class<? extends ISimulationLogEntry>>of(entryClass);
    }

    @Override
    public void handle(final ISimulationLogEntry entry) {
        if (entryClass.isInstance(entry)) {
            doHandle(entryClass.cast(entry));
        }
    }

    private void doHandle(final T entry) {
        final String name = getName(entry);

        if (!delegates.containsKey(name)) {
            delegates.put(name, createDelegate(name, factory));
        }

        delegates.get(name).handle(entry);
    }

    protected abstract IReporter createDelegate(final String name, final IOutputStreamFactory factory);

    protected abstract String getName(final T entry);

}
