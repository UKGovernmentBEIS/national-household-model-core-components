package uk.org.cse.nhm.simulator.groups.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;

/**
 * This group implements the three basic set operations, UNION, DIFFERENCE and INTERSECTION.
 * 
 * The group should be set up with {@link #init(Operation, List)}.
 * 
 * @author hinton
 *
 */
public class SetOperationDwellingGroups extends BaseDwellingGroup {
	public enum Operation {
    	/**
    	 * If this operation is selected, the group will contain all elements in any of its source groups
    	 */
        UNION,
        /**
         * If this operation is selected, the group will contain all the elements common to all its source groups
         */
        INTERSECTION,
        /**
         * If this operation is selected, the group will contain all the elements in its first source group which are in
         * none of the subsequent source groups.
         */
        DIFFERENCE
    }

	@Inject
    public SetOperationDwellingGroups(@Assisted final List<IDwellingGroup> sources, @Assisted final Operation operation) {
        init(operation, sources);
    }
    
    /**
     * Initialize the group; don't call this more than once please.
     * 
     * @param operation The operation for this group
     * @param sources the sources for this group to draw elements from
     */
    private void init(final Operation operation, final List<IDwellingGroup> sources) {
		// this list is used to track the mod counts of our sources; if we get a change notification from one
        // source, and the modcounts of other sources are not up to date, that implies that we are going to get
        // a change notification from other sources as well, so we might as well wait to trigger our clients to update 
        // themselves. Consequently, the update is only done when there is only one modcount out of date.
        // this is handled by the #checkModCounts() method 
        final int[] modCounts = new int[sources.size()];
        
        // depending on the operation, we add different listeners and set a different initial state
        
        switch (operation) {
            case UNION:
                final IDwellingGroupListener unionListener = new IDwellingGroupListener() {                	
                	final LinkedHashSet<IDwelling> added2 = new LinkedHashSet<IDwelling>();
                	final LinkedHashSet<IDwelling> removed2 = new LinkedHashSet<IDwelling>();
                	
                    @Override
                    public void dwellingGroupChanged(IStateChangeNotification cause, IDwellingGroup source, Set<IDwelling> added, Set<IDwelling> removed) {
                    	// We need to add all added elements, because any element that's been added to any group is in this group.
                    	added2.addAll(added);
                    	
                    	// we need to remove only those elements which are removed from all the groups
                    	removed2.addAll(removed);
                    	// remove any elements which we are going to add from any other change
                    	removed2.removeAll(added2);
                    	
                    	// remove any elements which are in some of our other groups, as we must contain them too.
                        for (final IDwellingGroup other : sources) {
                            removed2.removeAll(other.getContents());
                        }
                        
                        // don't raise notification if we don't need to
                        if (checkAwaitingModification(modCounts, source, sources)) return;
                        update(cause, added2, removed2);
                        added2.clear();
                        removed2.clear();
                    }
                };
                
                
                for (final IDwellingGroup h : sources) {
                    h.addListener(unionListener);
                   
                }
                break;
            case INTERSECTION:
                final IDwellingGroupListener intersectionListener = new IDwellingGroupListener() {
                	final LinkedHashSet<IDwelling> added2 = new LinkedHashSet<IDwelling>();
                	final LinkedHashSet<IDwelling> removed2 = new LinkedHashSet<IDwelling>();
                	
                    @Override
                    public void dwellingGroupChanged(IStateChangeNotification cause, IDwellingGroup source,Set<IDwelling> added, Set<IDwelling> removed) {
                    	// first remove everything which has been removed; anything missing from any group is not in all groups.
                    	removed2.addAll(removed);

                    	// add everything that could be added
                    	added2.addAll(added);
                    	
                    	// now add only things which are not being removed at some point
                    	added2.removeAll(removed2);
                    	
                    	// now retain only those things which are in all our sources.
                        for (final IDwellingGroup hig : sources) {
                            added2.retainAll(hig.getContents());
                        }
                        
                        if (checkAwaitingModification(modCounts, source, sources)) return;
                        update(cause, added2, removed2);
                        added2.clear();
                        removed2.clear();
                    }
                };
                
                for (final IDwellingGroup h : sources) {
                    h.addListener(intersectionListener);
                }
                break;
            case DIFFERENCE:
            	// this one is a bit more difficult; difference group has a special case with group one.
                if (sources.size() == 0) break;
                
                // this is the group we are adding from
                final IDwellingGroup add = sources.get(0);
                // these are the groups whose elements we are subtracting from ourself
                final List<IDwellingGroup> subtract = sources.subList(1, sources.size());
                
                // in this case we listen to the add group
                final LinkedHashSet<IDwelling> added2 = new LinkedHashSet<IDwelling>();
                final LinkedHashSet<IDwelling> removed2 = new LinkedHashSet<IDwelling>();
                
                final IDwellingGroupListener addDifferenceListener = new IDwellingGroupListener() {
					@Override
					public void dwellingGroupChanged(IStateChangeNotification cause, IDwellingGroup source,Set<IDwelling> added, Set<IDwelling> removed) {
						// we definitely want to remove everything that's been removed from the first group
						removed2.addAll(removed);
						// and we want to consider adding everything that's been added
						added2.addAll(added);

						// now we go through what we might be adding and take out anything which is in the subtract groups
						for (final IDwellingGroup sub : subtract) {
							added2.removeAll(sub.getContents());
						}
	
						if (checkAwaitingModification(modCounts, source, sources)) return;
						update(cause, added2, removed2);
						added2.clear();
						removed2.clear();
					}
                };

                final IDwellingGroupListener removeDifferenceListener = new IDwellingGroupListener() {
                	@Override
                    public void dwellingGroupChanged(IStateChangeNotification cause, IDwellingGroup source, Set<IDwelling> added, Set<IDwelling> removed) {
                		// OK, so if something has been removed from source, and is not in any other group, and is in the add group, we want to add it
                		// if something has been added to source, we want to remove it
                		final LinkedHashSet<IDwelling> removedFromSource = new LinkedHashSet<IDwelling>(removed);
                        
                		// anything added to a subtract group needs removing
                		removed2.addAll(added);
                		added2.removeAll(added);
                		
                		// keep all the things source has lost that are in the add group
                		removedFromSource.retainAll(add.getContents());
                		// go through all the subtract groups which aren't the source for this change, and remove all 
                		// stuff which they contain (can't be added
                        for (final IDwellingGroup sub : subtract) {
                            if (sub != source) {
                                removedFromSource.removeAll(sub.getContents());
                            }
                        }
                        
                        added2.addAll(removedFromSource);

                        if (checkAwaitingModification(modCounts, source, sources)) return;
                        update(cause, added2, removed2);
                        added2.clear();
                        removed2.clear();
                    }
                };
                
                add.addListener(addDifferenceListener);
                for (final IDwellingGroup sub : subtract) {
                    sub.addListener(removeDifferenceListener);
                }
                break;
        }
    }

    /**
     * Returns true if we are waiting for another modification to come through, and so shouldn't send out notifications right now
     * 
     * @param modCounts the modification counts which for the groups in sources; this will be updated 
     * @param thisGroup the group which has notified us that it's changed
     * @param sources all the groups we are interested in
     * @return true iff the only group whos modcount is out of date in modCounts is thisGroup.
     */
    protected static boolean checkAwaitingModification(final int[] modCounts, final IDwellingGroup thisGroup, final List<IDwellingGroup> sources) {
        int index = 0;
        
        boolean others = false;
        boolean seenThisGroup = false;
        for (final IDwellingGroup hig : sources) {
        	// if the group at index was last seen some modifications ago, we are out of date
            if (modCounts[index] < hig.getModificationCount()) {
            	// if the group at index is the one which is triggering this check, then we 
            	// want to (a) flag that this group has changed and (b) update the mod count for this group to be current
                if (hig.equals(thisGroup)) {
                    modCounts[index] = thisGroup.getModificationCount();
                    seenThisGroup = true;
                    if (others) return true;
                } else {
                    others = true;
                    // wait until we have updated the mod count for this group.
                    if (seenThisGroup) return true;
                }
            }
            index++;
        }
        
        // return true if something other than this group has a waiting mod.
        return others;
    }
}
