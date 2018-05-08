package uk.org.cse.nhm.language.builder.profiler;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.joda.time.DateTime;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;
import com.carrotsearch.hppc.ObjectLongOpenHashMap;
import com.carrotsearch.hppc.cursors.ObjectLongCursor;
import com.google.common.collect.ImmutableList;
//import com.sun.management.OperatingSystemMXBean;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.ProfiledItemLogEntry;
import uk.org.cse.nhm.logging.logentry.ProfilerLogEntry;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;

/**
 * Keeps track of time spent in different parts of the scenario. Each identifier
 * has a name and a path, but lacks the full source location that was used to
 * construct it (i.e. the many lines of a template that may have been involved).
 *
 * At the moment, this just maps each thing to how much time it ran for
 */
public class ProfilingStack implements IProfilingStack, ISimulationStepListener, Initializable {

    /**
     * this is a stack of clock start times.
     */
    private final Deque<Long> clocks = new LinkedList<>();
    private final Deque<Name> path = new LinkedList<>();
    //private final OperatingSystemMXBean mx = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private final ObjectLongOpenHashMap<List<Name>> timers = new ObjectLongOpenHashMap<>();
    private final ObjectIntOpenHashMap<List<Name>> hits = new ObjectIntOpenHashMap<>();
    private final ILogEntryHandler log;
    private final int profilingDepth;
    private final Provider<ISimulator> simulator;

    @Inject
    public ProfilingStack(final Provider<ISimulator> simulator, final ILogEntryHandler log,
            @Named(SimulatorConfigurationConstants.PROFILING_DEPTH) final int profilingDepth) {
        this.log = log;
        this.profilingDepth = profilingDepth;
        this.simulator = simulator;
    }

    @Override
    public void initialize() {
        simulator.get().addSimulationStepListener(this);
    }

    private long time() {
        return 0;
        //return mx.getProcessCpuTime();
    }

    @Override
    public void push(final IIdentified thing) {
        path.push(thing.getIdentifier());
        if (path.size() > profilingDepth) {
            return;
        }
        clocks.push(time());
    }

    @Override
    public void pop(final IIdentified thing) {
        if (clocks.isEmpty()) {
            return;
        }
        if (path.size() > profilingDepth) {
            path.pop();
            return;
        }

        final long delta = time() - clocks.pop();
        final List<Name> path = ImmutableList.copyOf(this.path);

        this.path.pop();

        timers.putOrAdd(path, delta, delta);
        hits.putOrAdd(path, 1, 1);
    }

    @Override
    public NHMException die(final String message, final IIdentified thing, final IComponentsScope scope) {
        final ImmutableList.Builder<Name> builder = ImmutableList.builder();

        final Name top = thing == null ? null : thing.getIdentifier();
        final Name next = scope == null ? null : (scope.getTag() == null ? null : scope.getTag().getIdentifier());

        if (!path.contains(top)) {
            builder.add(top);
        }

        if (!path.contains(next)) {
            builder.add(next);
        }

        builder.addAll(path);

        return new NHMException(builder.build(),
                message
                + (profilingDepth == 0 ? " (for more location information, increase the profiling depth on the scenario element)" : ""));
    }

    @Override
    public void simulationStepped(final DateTime dateOfStep,
            final DateTime nextDate,
            final boolean isFinalStep) {
        if (isFinalStep) {
            final Map<Name, Integer> dedup = new HashMap<>();

            for (final ObjectLongCursor<List<Name>> c : timers) {
                final double time = c.value / 1000000000.0;

                if (time < 0.5) {
                    continue;
                }

                final List<Name> stack = c.key;
                final ImmutableList.Builder<Integer> ddstack = ImmutableList.builder();

                for (final Name n : stack) {
                    if (!dedup.containsKey(n)) {
                        final int key = dedup.size();
                        dedup.put(n, key);
                        log.acceptLogEntry(new ProfiledItemLogEntry(key, n));
                    }
                    ddstack.add(dedup.get(n));
                }

                final ProfilerLogEntry ple = new ProfilerLogEntry(ddstack.build(),
                        time,
                        hits.get(c.key));

                log.acceptLogEntry(ple);
            }
        }
    }
}
