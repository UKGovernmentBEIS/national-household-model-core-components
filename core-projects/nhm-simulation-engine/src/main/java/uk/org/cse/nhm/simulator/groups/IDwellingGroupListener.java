package uk.org.cse.nhm.simulator.groups;

import java.util.Set;

import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;

public interface IDwellingGroupListener {

    public void dwellingGroupChanged(IStateChangeNotification cause, final IDwellingGroup source, final Set<IDwelling> added, final Set<IDwelling> removed);
}
