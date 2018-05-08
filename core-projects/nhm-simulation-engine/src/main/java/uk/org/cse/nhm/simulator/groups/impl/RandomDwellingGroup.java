package uk.org.cse.nhm.simulator.groups.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * This group implementation randomly contains elements from another group (its
 * source), with a fixed probability per element.
 *
 *
 * @author hinton
 *
 */
public class RandomDwellingGroup extends BaseDwellingGroup {

    @Inject
    public RandomDwellingGroup(
            final IState state,
            final @Assisted IDwellingGroup source,
            final @Assisted IComponentsFunction<Number> function
    ) {

        source.addListener(new IDwellingGroupListener() {
            @Override
            public void dwellingGroupChanged(final IStateChangeNotification cause, final IDwellingGroup source,
                    final Set<IDwelling> added, final Set<IDwelling> removed) {
                final LinkedHashSet<IDwelling> added2 = new LinkedHashSet<IDwelling>();
                final LinkedHashSet<IDwelling> removed2 = new LinkedHashSet<IDwelling>(removed);

                // flip biased coin for each instance which is being added
                for (final IDwelling hi : added) {
                    if (state.getRandom().nextDouble() < function.compute(state.detachedScope(hi), ILets.EMPTY).doubleValue()) {
                        added2.add(hi);
                    }
                }

                update(cause, added2, removed2);
            }
        });
    }
}
