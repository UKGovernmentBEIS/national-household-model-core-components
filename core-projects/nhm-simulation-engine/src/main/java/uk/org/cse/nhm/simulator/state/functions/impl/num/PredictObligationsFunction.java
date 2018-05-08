package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public class PredictObligationsFunction extends ObligationForecastingFunction {

    private final ITimeDimension time;
    private final int years;
    private final boolean includeScope;
    private final boolean includeHistory;
    private final IDimension<IObligationHistory> history;

    @AssistedInject
    public PredictObligationsFunction(
            final Set<IDimension<?>> allDimensions,
            final IDimension<IEnergyMeter> meterDimension,
            final IDimension<IPowerTable> powerDimension,
            final IDimension<IObligationHistory> history,
            final ITimeDimension time,
            @Assisted final int years,
            @Assisted("includeScope") final boolean includeScope,
            @Assisted("includeHistory") final boolean includeHistory,
            @Assisted final Predicate<Collection<String>> requiredTags) {
        super(allDimensions, meterDimension, powerDimension, requiredTags);
        this.history = history;
        this.time = time;
        this.years = years;
        this.includeScope = includeScope;
        this.includeHistory = includeHistory;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final DateTime now = scope.get(time).get(lets);
        final DateTime then = now.plusYears(years);

        final Multimap<DateTime, IPayment> forecast
                = predictObligations(scope, collectObligations(scope), now, then);

        double accumulator = 0;
        for (final IPayment payment : forecast.values()) {
            accumulator += payment.getAmount();
        }
        return accumulator;
    }

    private Iterable<IObligation> collectObligations(final IComponentsScope scope) {
        if (includeHistory && includeScope) {
            return scope.get(history);
        } else if (includeHistory) {
            final Set<IObligation> scopeNotes = new HashSet<>(scope.getAllNotes(IObligation.class));
            final HashSet<IObligation> matches = new HashSet<>();
            for (final IObligation o : scope.get(history)) {
                if (scopeNotes.contains(o)) {
                    continue;
                }
                matches.add(o);
            }
            return matches;
        } else if (includeScope) {
            return scope.getAllNotes(IObligation.class);
        } else {
            return Collections.emptySet();
        }
    }

}
