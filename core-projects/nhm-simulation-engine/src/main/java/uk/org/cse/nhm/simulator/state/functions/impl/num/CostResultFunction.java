package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

/**
 * Sums all @{link ICost} results of the given name which have been stored on
 * the {@link IComponents}.
 *
 * @author glenns
 * @since 3.4.0
 */
public class CostResultFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final Predicate<Collection<String>> tagged;

    @Inject
    public CostResultFunction(@Assisted final Predicate<Collection<String>> tagged) {
        this.tagged = tagged;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final List<ITransaction> costs = scope.getAllNotes(ITransaction.class);

        double total = 0d;
        for (final ITransaction cost : costs) {
            if (tagged.apply(cost.getTags())) {
                total += cost.getAmount();
            }
        }

        return total;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
