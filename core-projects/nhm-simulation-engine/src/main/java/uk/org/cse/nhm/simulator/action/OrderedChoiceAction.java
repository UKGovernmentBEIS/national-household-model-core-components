package uk.org.cse.nhm.simulator.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.management.timer.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class OrderedChoiceAction extends AbstractNamed implements IStateAction {

    private static final Logger log = LoggerFactory.getLogger(OrderedChoiceAction.class);
    private static final int LOG_SECONDS = 10;
    private final IComponentsFunction<Number> objective;
    private final boolean ascending;
    private final List<IComponentsAction> actions;
    private final ISimulator simulator;

    @AssistedInject
    public OrderedChoiceAction(
            final ISimulator simulator,
            @Assisted final IComponentsFunction<Number> objective,
            @Assisted final boolean ascending,
            @Assisted final List<IComponentsAction> actions) {
        super();
        this.objective = objective;
        this.ascending = ascending;
        this.actions = actions;
        this.simulator = simulator;
    }

    /**
     * This represents the options for a dwelling; it holds the values for each
     * alternative, and keeps track of the best one.
     */
    static final class DwellingOptions implements Comparable<DwellingOptions> {

        /**
         * The value of the objective under each action in the actions list
         */
        final double[] values;
        /**
         * The minimum not-NaN value in values
         */
        double best;
        /**
         * The index of the minimum not-NaN value
         */
        int bestIndex;
        /**
         * 1 if the objective is better when it is smaller; -1 if it is better
         * when larger. Used to multiply objective values before storing them.
         */
        final double mul;
        /**
         * True if the values of best and bestIndex are up-to-date. This is
         * cleared by {@link #set(int, double)}, and set by {@link #update()}.
         *
         * {@link #update()} is only called by {@link #any()}, so you
         * <emph>MUST</emph> call {@link #any()} before using the other values,
         * which would include touching the comparator.
         */
        boolean clean = true;
        /**
         * What dwelling this is about
         */
        public final IDwelling dwelling;
        /**
         * A random int, which is not reused in any other
         * {@link DwellingOptions} being considered, so we can use it for tie
         * breaking in a reliable way.
         */
        private final int random;

        public DwellingOptions(boolean ascending, IDwelling dwelling, int random, int size) {
            this.dwelling = dwelling;
            this.random = random;
            this.mul = ascending ? 1 : -1;
            this.values = new double[size];

            // start off with no valid options
            Arrays.fill(values, Double.NaN);
            this.best = Double.NaN;
            this.bestIndex = -1;
        }

        /**
         * Put d in as the objective value for action x, and mark dirty
         *
         * @param x
         * @param d
         */
        public final void set(final int x, final double d) {
            values[x] = d * mul;
            clean = false;
        }

        /**
         * Find the best action
         */
        private final void update() {
            if (clean) {
                return;
            }

            best = Double.NaN;
            bestIndex = -1;

            // find the value and position of the minimum non-NaN
            for (int i = 0; i < values.length; i++) {
                final double v = values[i];
                if ((bestIndex == -1 && !Double.isNaN(v)) || (best > v)) {
                    best = v;
                    bestIndex = i;
                }
            }

            clean = true;
        }

        /**
         * @return true iff there is a suitable action left
         */
        public final boolean any() {
            update();
            return bestIndex >= 0;
        }

        public final double bestValue() {
            return best;
        }

        public final int bestIndex() {
            return bestIndex;
        }

        /**
         * This comparator compares the best objective value first; if two
         * houses have the same best objective value, we use a random int to
         * break the tie.
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(final DwellingOptions o) {
            final double best = bestValue();
            final double other = o.bestValue();
            final int dc = Double.compare(best, other);
            if (dc == 0) {
                return Integer.compare(random, o.random);
            } else {
                return dc;
            }
        }

        @Override
        public String toString() {
            return String.format("%s [%s]: %s", dwelling, dwelling.getWeight(), Arrays.toString(values));
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
        final PriorityQueue<DwellingOptions> options = new PriorityQueue<>();

        if (actions.isEmpty()) {
            return Collections.emptySet();
        }

        long start = System.currentTimeMillis();
        int percent = 0;
        int logpercent = 0;

        log.info("Computing objective function for {} ({} points)",
                this, dwellings.size() * actions.size());
        // generate the initial state for the PQ
        {
            // if two houses have the same objective, we want them to come out in a random order
            // but one that is repeatable. to do this, we create a random ordering of all the dwellings
            // and use that as the secondary comparator for the dwelling options
            final List<Integer> randoms = new ArrayList<>(dwellings.size());
            for (int i = 0; i < dwellings.size(); i++) {
                randoms.add(i);
            }
            scope.getState().getRandom().shuffle(randoms);

            // step 1: generate hypotheses and compute values
            int dwellingcounter = 0;
            for (final IDwelling d : dwellings) {
                final DwellingOptions opts = new DwellingOptions(ascending, d,
                        randoms.get(dwellingcounter),
                        actions.size());
                dwellingcounter++;
                for (int i = 0; i < actions.size(); i++) {
                    final IComponentsAction a = actions.get(i);
                    final IHypotheticalComponentsScope hypothesis = scope.createHypothesis(d);
                    final boolean worked = hypothesis.apply(a, lets);
                    if (worked) {
                        final double value = objective.compute(hypothesis, lets).doubleValue();
                        opts.set(i, value);
                    }
                }

                if (opts.any()) {
                    options.add(opts);
                }

                final int seconds = (int) ((System.currentTimeMillis() - start) / Timer.ONE_SECOND);

                if (seconds > LOG_SECONDS) {
                    percent = 10 * dwellingcounter / dwellings.size();
                    if (percent > logpercent) {
                        logpercent = percent;
                        log.info("computing objective for {}; done {}% in {}s", this, 10 * percent, seconds);
                    }
                }

                simulator.dieIfStopped();
            }
        }

        log.info("Resolving actions for {} from {} suitables", this, options.size());

        start = System.currentTimeMillis();
        int initialCount = options.size();
        percent = 0;
        logpercent = 0;

        final ImmutableSet.Builder<IDwelling> affected = ImmutableSet.builder();

        // step 2: iterate through options
        while (!options.isEmpty()) {
            final DwellingOptions o = options.poll();
            // try and do the best option
            final int best = o.bestIndex();
            if (best >= 0 && best < actions.size()) {
                final IComponentsAction a = actions.get(best);
                final Set<IDwelling> worked = scope.apply(a, ImmutableSet.of(o.dwelling), lets);
                affected.addAll(worked);
                if (worked.isEmpty()) {
                    // the action failed, and the dwelling was not affected
                    // mark the option as dead, and reinsert it if it looks like anything is left
                    // for it.
                    o.set(best, Double.NaN);
                    if (o.any()) {
                        options.add(o);
                    }
                }
            }

            final int seconds = (int) ((System.currentTimeMillis() - start) / Timer.ONE_SECOND);
            if (seconds > LOG_SECONDS) {
                percent = 10 * options.size() / initialCount;
                if (percent > logpercent) {
                    logpercent = percent;
                    log.info("selecting actions for {}; done {}% in {}s", this, (100 - 10 * percent), seconds);
                }
            }

            simulator.dieIfStopped();
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
