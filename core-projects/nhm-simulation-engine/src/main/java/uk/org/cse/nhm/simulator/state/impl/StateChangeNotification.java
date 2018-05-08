package uk.org.cse.nhm.simulator.state.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Service.State;

import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

/**
 * Thing which is built up by {@link State} when it is applying a
 * {@link StateChange}.
 *
 * @author hinton
 *
 */
public class StateChangeNotification implements IStateChangeNotification {

    private final IStateScope rootScope;
    private final Set<IDwelling> created;
    private final Set<IDwelling> destroyed;

    private final DateTime date;
    private final Set<? extends IDimension<?>> affectedDimensions;
    private final SetMultimap<IDimension<?>, IDwelling> changes;
    private final ImmutableSet<IDwelling> allChangedDwellings;

    private final Map<Set<IDimension<?>>, Set<IDwelling>> changedDwellingsCache = new HashMap<>();
    private final SetMultimap<IDwelling, IDimension<?>> changesByDwelling;
    private final Set<IStateChangeSource> causes;
    private final boolean globalsChanged;

    public StateChangeNotification(
            final Set<IStateChangeSource> causes,
            final IStateScope rootScope,
            final boolean globalsChanged,
            final Set<IDwelling> created,
            final Set<IDwelling> destroyed,
            final SetMultimap<IDimension<?>, IDwelling> changes,
            final DateTime date
    ) {
        this.causes = causes;
        this.rootScope = rootScope;
        this.globalsChanged = globalsChanged;
        this.affectedDimensions = changes.keySet();
        this.created = created;
        this.destroyed = destroyed;
        this.changes = changes;
        this.date = date;

        this.allChangedDwellings = ImmutableSet.<IDwelling>copyOf(changes.values());

        this.changesByDwelling = Multimaps
                .<IDwelling, IDimension<?>, SetMultimap<IDwelling, IDimension<?>>>invertFrom(
                        changes,
                        LinkedHashMultimap.<IDwelling, IDimension<?>>create());
    }

    @Override
    public Set<IDwelling> getCreatedDwellings() {
        return created;
    }

    @Override
    public Set<IDwelling> getAllChangedDwellings() {
        return allChangedDwellings;
    }

    @Override
    public Set<IDwelling> getDestroyedDwellings() {
        return destroyed;
    }

    @Override
    public Set<IDwelling> getChangedDwellings(Set<IDimension<?>> components) {
        components = Sets.intersection(components, affectedDimensions);
        if (components.isEmpty()) {
            return Collections.emptySet();
        }

        if (changedDwellingsCache.containsKey(components)) {
            return changedDwellingsCache.get(components);
        }

        final Set<IDwelling> matches = new LinkedHashSet<IDwelling>();

        for (final IDimension<?> d : components) {
            matches.addAll(changes.get(d));
        }

        changedDwellingsCache.put(components, matches);

        return matches;
    }

    @Override
    public Set<IStateChangeSource> getCauses() {
        return causes;
    }

    @Override
    public Set<IDwelling> getChangedDwellings(final IDimension<?>... components) {
        return getChangedDwellings(new HashSet<IDimension<?>>(Arrays.asList(components)));
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    @Override
    public Set<IDimension<?>> getChangesToDwelling(final IDwelling d) {
        if (changesByDwelling.containsKey(d)) {
            return changesByDwelling.get(d);
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public boolean isChange() {
        return globalsChanged
                || !getAllChangedDwellings().isEmpty()
                || !getCreatedDwellings().isEmpty()
                || !getDestroyedDwellings().isEmpty();
    }

    @Override
    public IStateScope getRootScope() {
        return rootScope;
    }

    @Override
    public <T> List<T> getAllNotes(final IDwelling dwelling, final Class<T> clazz) {
        final Optional<IComponentsScope> componentsScope = getRootScope().getComponentsScope(dwelling);
        if (componentsScope.isPresent()) {
            return componentsScope.get().getAllNotes(clazz);
        } else {
            return Collections.emptyList();
        }
    }
}
