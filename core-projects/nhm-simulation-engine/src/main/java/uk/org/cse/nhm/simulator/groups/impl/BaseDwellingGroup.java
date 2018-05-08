package uk.org.cse.nhm.simulator.groups.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;

/**
 * This is a base class for {@link IDwellingGroup} implementors which provides
 * boilerplate functionality, primarily
 * <ol>
 * <li>A set for tracking what is in the group</li>
 * <li>The
 * {@link #update(IStateChangeNotification, LinkedHashSet, LinkedHashSet)}
 * method, which will post notifications to listeners if necessary</li>
 * <li>A track of modification count, for efficient implementation of
 * clients</li>
 * <li>Listener management stuff</li>
 * </ol>
 *
 * @author hinton
 *
 */
public abstract class BaseDwellingGroup extends AbstractNamed implements IDwellingGroup {

    /**
     * This set contains all the house instances that are in this group. It's a
     * {@link LinkedHashSet} to guarantee repeatable behaviour on iteration
     */
    protected HashSet<IDwelling> contents = new LinkedHashSet<IDwelling>();
    /**
     * This list tracks the listeners to this group
     */
    protected ArrayList<IDwellingGroupListener> listeners = new ArrayList<IDwellingGroupListener>();
    /**
     * Each time a subclass invokes
     * {@link #update(IStateChangeNotification, LinkedHashSet, LinkedHashSet)}
     * in a way that makes a change, this number is bumped up.
     */
    private int modificationCount;
    private float lastRemovedCount;
    private float lastAddedCount;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BaseDwellingGroup.class);

    @Override
    public Set<IDwelling> getContents() {
        return Collections.unmodifiableSet(contents);
    }

    /**
     * Subclasses can invoke this when they want to change the group contents
     * and notify any listeners.
     *
     * The method will intelligently preprocess the added and removed items so
     * things which are already in the group are not double-added and items
     * which are not in the group are not double-removed.
     *
     * Listeners are only notified if the contents set is actually changed.
     *
     * Before listeners get notified, the contents will be updated to reflect
     * the new state
     *
     * @param cause TODO
     * @param added these instances are being added to the group
     * @param removed these instances are being removed from the group.
     */
    protected void update(final IStateChangeNotification cause, final LinkedHashSet<IDwelling> added, final LinkedHashSet<IDwelling> removed) {
        // first sanity check the sets (can't add things which we already contain, can't remove things we don't contain)
        added.removeAll(contents);
        removed.retainAll(contents);

        // now perform the change
        contents.addAll(added);
        contents.removeAll(removed);

        lastRemovedCount = 0;
        lastAddedCount = 0;
        for (final IDwelling d : removed) {
            lastRemovedCount += d.getWeight();
        }

        for (final IDwelling d : added) {
            lastAddedCount += d.getWeight();
        }

        // if the change did nothing, return.
        if (added.size() == 0 && removed.size() == 0) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("update {}, added {}, removed {}", new Object[]{this, added.size(), removed.size()});
        }

        // bump the mod count
        modificationCount++;

        // notify our listeners (note the toArray, to prevent comodification exception if a listener removes itself during this call).
        for (final IDwellingGroupListener listener : listeners.toArray(new IDwellingGroupListener[listeners.size()])) {
            listener.dwellingGroupChanged(cause, this, added, removed);
        }
    }

    protected float getLastRemovedCount() {
        return lastRemovedCount;
    }

    protected float getLastAddedCount() {
        return lastAddedCount;
    }

    @Override
    public void addListener(final IDwellingGroupListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(final IDwellingGroupListener listener) {
        listeners.removeAll(Collections.singleton(listener));
    }

    @Override
    public int getModificationCount() {
        return modificationCount;
    }

    @Override
    public boolean contains(final IDwelling d) {
        return contents.contains(d);
    }
}
