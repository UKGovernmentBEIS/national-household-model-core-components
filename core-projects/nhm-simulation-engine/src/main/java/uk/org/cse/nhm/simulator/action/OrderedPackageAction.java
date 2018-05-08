package uk.org.cse.nhm.simulator.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class OrderedPackageAction extends AbstractNamed implements IStateAction {

    private static final Logger log = LoggerFactory.getLogger(OrderedPackageAction.class);
    private final IComponentsFunction<Number> objective;
    private final double mul;
    private final List<IComponentsAction> actions;

    @AssistedInject
    public OrderedPackageAction(
            @Assisted final IComponentsFunction<Number> objective,
            @Assisted final boolean ascending,
            @Assisted final List<IComponentsAction> actions) {
        super();
        this.objective = objective;
        this.mul = ascending ? 1 : -1;
        this.actions = actions;
    }

    /**
     * This represents the options for a dwelling; it holds the values for each
     * alternative, and keeps track of the best one.
     */
    static final class DwellingOption implements Comparable<DwellingOption> {

        final double value;
        final int action;
        final IDwelling dwelling;
        final int random;
        public boolean valid = true;

        public DwellingOption(double value, int action, IDwelling dwelling,
                int random) {
            super();
            this.value = value;
            this.action = action;
            this.dwelling = dwelling;
            this.random = random;
        }

        @Override
        public int compareTo(DwellingOption o) {
            final int dc = Double.compare(value, o.value);
            if (dc == 0) {
                return Integer.compare(random, o.random);
            }
            return dc;
        }
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public Set<IDwelling> apply(
            final IStateScope scope,
            final Set<IDwelling> dwellings,
            final ILets lets) throws NHMException {
        final PriorityQueue<DwellingOption> options = new PriorityQueue<>();
        final ListMultimap<IDwelling, DwellingOption> optionsByDwelling = LinkedListMultimap.create();

        if (actions.isEmpty()) {
            return Collections.emptySet();
        }
        log.info("Computing objective function for {} ({} points)",
                this, dwellings.size() * actions.size());
        // generate the initial state for the PQ
        {
            // if two houses have the same objective, we want them to come out in a random order
            // but one that is repeatable. to do this, we create a random ordering of all the dwellings
            // and use that as the secondary comparator for the dwelling options
            final int noptions = dwellings.size() * actions.size();
            final List<Integer> randoms = new ArrayList<>(noptions);
            for (int i = 0; i < noptions; i++) {
                randoms.add(i);
            }
            scope.getState().getRandom().shuffle(randoms);

            // step 1: generate hypotheses and compute values
            int optionCounter = 0;
            for (final IDwelling d : dwellings) {
                for (int i = 0; i < actions.size(); i++) {
                    final IComponentsAction a = actions.get(i);
                    final IHypotheticalComponentsScope hypothesis = scope.createHypothesis(d);
                    final boolean worked = hypothesis.apply(a, lets);
                    if (worked) {
                        final double value = objective.compute(hypothesis, lets).doubleValue() * mul;
                        final DwellingOption dwellingOption = new DwellingOption(value, i, d, randoms.get(optionCounter));
                        optionsByDwelling.put(d, dwellingOption);
                        options.add(dwellingOption);
                    }
                    optionCounter++;
                }
            }
        }

        log.info("Resolving actions for {}", this);

        final ImmutableSet.Builder<IDwelling> affected = ImmutableSet.builder();

        // step 2: iterate through options
        while (!options.isEmpty()) {
            final DwellingOption o = options.poll();
            if (!o.valid) {
                continue;
            }

            final IComponentsAction a = actions.get(o.action);
            final Set<IDwelling> result = scope.apply(a, ImmutableSet.of(o.dwelling), lets);
            if (!result.isEmpty()) {
                // recompute the other options
                // copy them
                final ImmutableList<DwellingOption> futures = ImmutableList.copyOf(optionsByDwelling.get(o.dwelling));
                // throw them away
                optionsByDwelling.removeAll(o.dwelling);
                // go through the copy and recompute them
                for (final DwellingOption o2 : futures) {
                    final IComponentsAction a2 = actions.get(o2.action);
                    final IHypotheticalComponentsScope hypothesis = scope.createHypothesis(o.dwelling);
                    final boolean worked = hypothesis.apply(a2, lets);
                    if (worked) {
                        final double value = objective.compute(hypothesis, lets).doubleValue() * mul;
                        if (value != o2.value) {
                            // the result has changed, so we need to rethink - mark the one in the queue as invalid, and insert a new one
                            o2.valid = false;
                            final DwellingOption dwellingOption = new DwellingOption(value, o2.action, o2.dwelling, o2.random);
                            options.add(dwellingOption);
                            optionsByDwelling.put(o2.dwelling, dwellingOption);
                        } else {
                            // put it back in the per-dwelling map, but otherwise we don't care because its value is unchanged.
                            optionsByDwelling.put(o2.dwelling, o2);
                        }
                    } else {
                        // the action has become unsuitable, so we just kill it. this presumes that no later action can render it suitable.
                        o2.valid = false;
                    }
                }
            }
            affected.addAll(result);
        }

        return affected.build();
    }

    /**
     * The suitable dwellings are those for which at least one action appears
     * suitable. Some dwellings may ultimately be unsuitable, because of
     * interaction between the actions.
     */
    @Override
    public Set<IDwelling> getSuitable(IStateScope scope, Set<IDwelling> dwellings, ILets lets) {
        if (actions.isEmpty()) {
            return Collections.emptySet();
        }

        final ImmutableSet.Builder<IDwelling> suitable = ImmutableSet.builder();

        for (final IDwelling d : dwellings) {
            for (final IComponentsAction a : actions) {
                if (a.isSuitable(scope.getState().detachedScope(d), lets)) {
                    suitable.add(d);
                    break;
                }
            }
        }

        return suitable.build();
    }
}
