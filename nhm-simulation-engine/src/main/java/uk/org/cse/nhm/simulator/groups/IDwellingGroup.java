package uk.org.cse.nhm.simulator.groups;

import java.util.Set;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.simulator.state.IDwelling;

public interface IDwellingGroup extends IIdentified {
    public Set<IDwelling> getContents();
    
    public void addListener(final IDwellingGroupListener listener);
    public void removeListener(final IDwellingGroupListener listener);
    public int getModificationCount();
    
	public boolean contains(IDwelling d);
}
