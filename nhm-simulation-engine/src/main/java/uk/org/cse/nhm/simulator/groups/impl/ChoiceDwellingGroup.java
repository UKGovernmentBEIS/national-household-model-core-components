package uk.org.cse.nhm.simulator.groups.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;

/**
 * This is a group which chooses a fixed proportion of the elements which are added to its source group.
 * 
 * It does /not/ ensure that it is always a fixed proportion of the source, because of the possibility
 * that elements are removed from the source in a disproportionate way.
 * 
 * Optionally it can be configured as a one-shot group which only responds to the first update from its source.
 *
 * @author tomh
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z tomh $
 * @since $
 */
public class ChoiceDwellingGroup extends BaseDwellingGroup {
	private static final Function<IDwelling, Double> WEIGHT = new Function<IDwelling, Double>() {
		@Override
		public Double apply(final IDwelling input) {
			return Double.valueOf(input.getWeight());
		}
	};

    final ICanonicalState state;
    @Inject
    public ChoiceDwellingGroup(final ICanonicalState state,
    		@Assisted final IDwellingGroup source, 
    		@Assisted final double proportion) {
		init(source, proportion, false);
        this.state = state;
    }
    
    /**
     * The builder can use this method to set up the group once all other sim elements have been built
     * 
     * @param source this is the group from which elements are being chosen
     * @param proportion this is the proportion begin chosen
     * @param oneShot if this is true, this will select a random proportion from the first update to the source group, and stay the same thereafter.
     */
    private void init(final IDwellingGroup source, final double proportion, final boolean oneShot) {
		// we handle this by adding a listener to our source; presumption is that source is initially empty, as the sim starts empty.
        source.addListener(new IDwellingGroupListener() { 
            @Override
            public void dwellingGroupChanged(IStateChangeNotification cause, final IDwellingGroup source, final Set<IDwelling> added, final Set<IDwelling> removed) {
            	// we can't contain anything not in our source group
                final LinkedHashSet<IDwelling> removed2 = new LinkedHashSet<IDwelling>(removed);
                // now we need to pick some stuff to add (a subset of the things added to our source)
                final LinkedHashSet<IDwelling> added2 = new LinkedHashSet<IDwelling>();
                
                // randomly select the relevant proportion of elements into added2
                double size = 0;
                for (final IDwelling d : added) {
                    size += d.getWeight();
                }
                
                state.getRandom().chooseMany(added, proportion * size, WEIGHT, added2);

                // Give up if we don't need any more updates
                if (oneShot) {
                    source.removeListener(this);
                }
                
                // call the update method in the base class for the group.
                update(cause, added2, removed2);
            }
        });
    }
}
