package uk.org.cse.nhm.simulator.groups.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;

public class AllHousesGroupWrapper extends AbstractNamed implements IDwellingGroup {

    final AllHousesDwellingGroup group;

    @Inject
    AllHousesGroupWrapper(final AllHousesDwellingGroup group) {
        super();
        this.group = group;
    }

    @Override
    public Set<IDwelling> getContents() {
        return group.getContents();
    }

    class Wrap implements IDwellingGroupListener {

        private final IDwellingGroupListener delegate;

        public Wrap(final IDwellingGroupListener delegate) {
            this.delegate = delegate;
        }

        @Override
        public void dwellingGroupChanged(
                final IStateChangeNotification cause,
                final IDwellingGroup source,
                final Set<IDwelling> added,
                final Set<IDwelling> removed) {
            delegate.dwellingGroupChanged(cause, AllHousesGroupWrapper.this, added, removed);
        }
    }

    private final Map<IDwellingGroupListener, Wrap> map = new HashMap<>();

    @Override
    public void addListener(final IDwellingGroupListener listener) {
        if (!map.containsKey(listener)) {
            final Wrap wrap = new Wrap(listener);
            map.put(listener, wrap);
            group.addListener(wrap);
        }
    }

    @Override
    public void removeListener(final IDwellingGroupListener listener) {
        if (map.containsKey(listener)) {
            group.removeListener(map.get(listener));
            map.remove(listener);
        }
    }

    @Override
    public int getModificationCount() {
        return group.getModificationCount();
    }

    @Override
    public boolean contains(final IDwelling d) {
        return group.contains(d);
    }
}
