package uk.org.cse.nhm.simulator.hooks;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class FilterDwellings extends AbstractNamed implements IDwellingSet {

    private final IComponentsFunction<Boolean> filter;
    private final IDwellingSet source;

    @AssistedInject
    public FilterDwellings(
            @Assisted final IComponentsFunction<Boolean> filter,
            @Assisted final IDwellingSet source) {
        super();
        this.filter = filter;
        this.source = source;
    }

    @Override
    public Set<IDwelling> get(final IState state, ILets lets) {
        final Set<IDwelling> ds = source.get(state, lets);

        final Set<IDwelling> result = new LinkedHashSet<IDwelling>(ds.size() / 2);
        for (final IDwelling d : ds) {
            if (filter.compute(state.detachedScope(d), ILets.EMPTY)) {
                result.add(d);
            }
        }

        return result;
    }
}
