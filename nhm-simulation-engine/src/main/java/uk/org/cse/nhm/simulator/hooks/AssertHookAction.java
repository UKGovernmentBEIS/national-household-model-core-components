package uk.org.cse.nhm.simulator.hooks;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap;

public class AssertHookAction extends AbstractNamed implements IHookRunnable {
    private final Optional<IDwellingSet> over;
    private final IComponentsFunction<Boolean> test;
    private final boolean isFatal;
    private final ILogEntryHandler log;
    final List<IComponentsFunction<?>> debug;

    @AssistedInject
    public AssertHookAction(final ILogEntryHandler log,
                            @Assisted final Optional<IDwellingSet> over,
                            @Assisted final IComponentsFunction<Boolean> test,
                            @Assisted final boolean isFatal,
                            @Assisted final List<IComponentsFunction<?>> debug) {
        this.over = over;
        this.test = test;
        this.isFatal = isFatal;
        this.log = log;
        this.debug = debug;
    }

    @Override
    public void run(final IStateScope scope_,
                    final DateTime date,
                    final Set<IStateChangeSource> causes,
                    final ILets lets) {
        final IState state = scope_.getState();
        if (over.isPresent()) {
            final Set<IDwelling> dwellings = over.get().get(state, lets);
            for (final IDwelling dwelling : dwellings) {
                final IComponentsScope scope = state.detachedScope(dwelling);
                final Boolean b = test.compute(scope, lets);
                if (b == null || !b) {
                    final DeduplicatingMap.Builder<String> captured = DeduplicatingMap.stringBuilder();
                    captured.put("dwelling-id", String.valueOf(dwelling.getID()));
                    captured.put("Date", String.valueOf(date));
                    for (final IComponentsFunction<?> f : debug) {
                        captured.put(String.valueOf(f), String.valueOf(f.compute(scope, lets)));
                    }
                    fail(captured.build());
                }
            }
        } else {
            final IComponentsScope scope = state.detachedScope(null);
            final Boolean b = test.compute(scope, lets);
            if (b == null || !b) {
                // bad thing happened
                final DeduplicatingMap.Builder<String> captured = DeduplicatingMap.stringBuilder();
                captured.put("Date", String.valueOf(date));
                for (final IComponentsFunction<?> f : debug) {
                    captured.put(String.valueOf(f), String.valueOf(f.compute(scope, lets)));
                }
                fail(captured.build());                
            }
        }
    }

    private void fail(final Map<String, String> data) {
        final String message = String.format("Assertion failed: %s", this);
            
        if (isFatal) {
            throw new RuntimeException(message + " " + String.valueOf(data));
        } else {
            log.acceptLogEntry(new WarningLogEntry(message, data));
        }
    }
}
