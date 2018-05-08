package uk.org.cse.nhm.simulator.hooks;

import java.util.List;
import java.util.Set;

import javax.management.timer.Timer;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

public abstract class AbstractHook extends AbstractNamed implements IStateChangeSource {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AbstractHook.class);

    private static final int LOG_SECONDS = 5;

    private final ImmutableList<IHookRunnable> delegates;
    private final ISimulator simulator;

    protected AbstractHook(final List<IHookRunnable> delegates, final ISimulator simulator) {
        this.simulator = simulator;
        this.delegates = ImmutableList.copyOf(delegates);
    }

    protected void runDelegates(final ICanonicalState state, final DateTime date, final Set<IStateChangeSource> causes, final Set<IDwelling> affected) {
        log.info("about to run {} on {}", this, date);

        final ILets lets = ILets.EMPTY.withBinding(AffectedDwellings.AFFECTED_DWELLINGS_LET_KEY, affected);
        final long now = System.currentTimeMillis();

        final int delegateCount = delegates.size();
        int completedCount = 0;

        // create a statescope here, and merge it back after
        final IStateScope scope = state.branch(this);
        for (final IHookRunnable d : delegates) {
            simulator.dieIfStopped();

            int seconds = (int) ((System.currentTimeMillis() - now) / Timer.ONE_SECOND);
            if (seconds > LOG_SECONDS) {
                log.info("about to run {} from {} on {}", d, this, date);
            }
            d.run(scope, date, causes, lets);
            completedCount++;
            seconds = (int) ((System.currentTimeMillis() - now) / Timer.ONE_SECOND);
            if (seconds > LOG_SECONDS) {
                log.info("completed {} ({} of {}) from {} on {} in {}s", d, completedCount, delegateCount, this, date, seconds);
            }
        }
        state.apply(scope, causes);
        final int seconds = (int) ((System.currentTimeMillis() - now) / Timer.ONE_SECOND);
        log.info("completed {} on {} in {} seconds", this, date, seconds);
    }
}
