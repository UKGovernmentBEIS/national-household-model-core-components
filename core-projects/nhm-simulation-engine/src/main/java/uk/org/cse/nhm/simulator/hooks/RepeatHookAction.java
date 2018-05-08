package uk.org.cse.nhm.simulator.hooks;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.language.definition.sequence.XScope;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class RepeatHookAction
        extends AbstractNamed
        implements IHookRunnable,
        IStateChangeSource {

    private static final Logger log = LoggerFactory.getLogger(RepeatHookAction.class);

    private final List<IHookRunnable> delegates;
    private final Set<String> keepGlobals;
    private final Set<String> keepRegisters;
    private final IComponentsFunction<Boolean> termination;
    private final boolean rollback;
    private final IDimension<IFlags> flags;
    private final ISimulator simulator;
    private final IProfilingStack profiler;

    @SuppressWarnings("incomplete-switch")
    @AssistedInject
    public RepeatHookAction(final IDimension<IFlags> flags,
            final ISimulator simulator,
            final IProfilingStack profiler,
            @Assisted final List<IHookRunnable> delegates,
            @Assisted final IComponentsFunction<Boolean> termination,
            @Assisted final List<KeepValue> valuesToKeep) {
        this.simulator = simulator;
        this.profiler = profiler;
        this.delegates = ImmutableList.copyOf(delegates);
        this.termination = termination;

        {
            final ImmutableSet.Builder<String> keepGlobals = ImmutableSet.builder();
            final ImmutableSet.Builder<String> keepRegisters = ImmutableSet.builder();

            for (final KeepValue kv : valuesToKeep) {
                switch (kv.scope) {
                    case Simulation:
                        keepGlobals.add(kv.name);
                        break;
                    case House:
                        keepRegisters.add(kv.name);
                        break;
                }
            }

            this.keepGlobals = keepGlobals.build();
            this.keepRegisters = keepRegisters.build();
        }

        this.rollback = !(this.keepGlobals.isEmpty() && this.keepRegisters.isEmpty());

        this.flags = flags;
    }

    @Override
    public void run(final IStateScope scope,
            final DateTime date,
            final Set<IStateChangeSource> causes,
            final ILets lets) {
        IStateScope branch = scope.branch(this);

        int iterations = 0;
        while (true) {
            iterations++;
            log.info("About to perform iteration {} of {}", iterations, this);
            // opportunity to break out of running just in case.

            int delegateCounter = 0;
            for (final IHookRunnable delegate : delegates) {
                simulator.dieIfStopped();
                delegate.run(branch, date, causes, lets);
                delegateCounter++;
                log.info("In repeat {}, completed {} ({} / {})", this, delegate, delegateCounter, delegates.size());
            }

            final IComponentsScope scopeAfterChanges = branch.getState().detachedScope(null);
            final boolean terminate = termination.compute(scopeAfterChanges, lets);

            if (terminate) {
                log.info("Terminated {}", this);
                break;
            } else if (rollback) {
                // new branch to rollback into
                final IStateScope newBranch = scope.branch(this);
                // need to copy preserved values over into the new branch
                // which requires knowledge of the kinds of things that they are

                if (!preserveValues(branch, newBranch)) {
                    log.error("On iteration {} of {}, nothing changed between ticks", iterations, this);
                    profiler.die("Preserved values did not change in iteration " + iterations, this, scopeAfterChanges);
                    break;
                } else {
                    log.info("Termination condition for {} not met - rolling back", this);
                }

                // finally replace the live branch with the new branch
                branch = newBranch;
            } else {
                // just tick along
                log.info("Termination condition for {} not met - continuing", this);
            }
        }

        scope.merge(branch);
    }

    private boolean preserveValues(final IStateScope from, final IStateScope into) {
        for (final String s : keepGlobals) {
            log.info("{} preserved, old value {}, new value {}",
                    s,
                    into.getState().getGlobals().getVariable(s, Object.class),
                    from.getState().getGlobals().getVariable(s, Object.class));
        }
        boolean changed = into.getState().getGlobals().copyValues(keepGlobals, from.getState().getGlobals());
        if (!keepRegisters.isEmpty()) {
            if (preserveFlags(from.getState(), into.getState())) {
                changed = true;
            }
        }
        return changed;
    }

    private boolean preserveFlags(final IBranch from, final IBranch to) {
        boolean changed = false;
        for (final IDwelling d : Sets.intersection(to.getDwellings(), from.getDwellings())) {
            if (from.getGeneration(flags, d) == to.getGeneration(flags, d)) {
                continue;
            }

            final IFlags fromFlags = from.get(flags, d);
            final IFlags toFlags = to.get(flags, d);
            IFlags safeCopy = null;
            for (final String s : keepRegisters) {
                final Optional<Double> fromRegister = fromFlags.getRegister(s);
                final Optional<Double> toRegister = toFlags.getRegister(s);
                if (fromRegister.isPresent() && !toRegister.equals(fromRegister)) {
                    if (safeCopy == null) {
                        safeCopy = toFlags.copy();
                    }
                    safeCopy.setRegister(s, fromRegister.get());
                }
            }
            if (safeCopy != null) {
                to.set(flags, d, safeCopy);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.TRIGGER;
    }

    public static class KeepValue {

        public final String name;
        public final XScope scope;

        public KeepValue(final String name, final XScope scope) {
            this.name = name;
            this.scope = scope;
        }
    }
}
