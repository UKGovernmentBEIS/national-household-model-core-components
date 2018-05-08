package uk.org.cse.nhm.simulator.hooks;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

public class UnionDwellings extends AbstractNamed implements IDwellingSet {

    private final List<IDwellingSet> contents;

    @AssistedInject
    UnionDwellings(@Assisted final List<IDwellingSet> contents) {
        this.contents = ImmutableList.copyOf(contents);
    }

    @Override
    public Set<IDwelling> get(final IState state, final ILets lets) {
        // copy all the things?
        final LinkedHashSet<IDwelling> result = new LinkedHashSet<>();
        for (final IDwellingSet c : contents) {
            result.addAll(c.get(state, lets));
        }
        return result;
    }
}
