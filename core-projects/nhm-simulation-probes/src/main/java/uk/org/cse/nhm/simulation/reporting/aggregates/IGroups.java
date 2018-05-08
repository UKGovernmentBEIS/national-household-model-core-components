package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.Set;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.simulator.state.IDwelling;

public interface IGroups extends IIdentified {

    public interface IListener {

        public void groupChanged(ImmutableMap<String, String> divisions, final Set<IDwelling> contents, Set<String> causes, boolean isFinalStep);
    }

    public void addListener(final IListener listener);

    /**
     * Tells the group that it should mark all groups as dirty.
     */
    public void triggerManually();
}
