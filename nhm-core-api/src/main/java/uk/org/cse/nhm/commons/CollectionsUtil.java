package uk.org.cse.nhm.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A utility class for doing things with collections
 *
 * @author tomh
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z tomh $
 * @since $
 */
public class CollectionsUtil {
    /**
     * Take a subset of howMany elements from source and place them in destination, uniformly distributed over possibilities;
     * 
     * O(n) complexity, by requirement from non-random-access source; faster options are available if you have an array (O(k log k) I think)
     * 
     * @param source
     * @param destination
     * @param random
     * @param howMany
     */
    public static <T> void draw(final Collection<T> source, final Collection<T> destination, final Random random, final int howMany) {
        int remainingToIterate = source.size();
        int remainingToTake = howMany;
        
        for (final T element : source) {
            if (remainingToTake == 0) return;
            if (remainingToTake == remainingToIterate) {
                destination.add(element);
            } else {
                final double p = remainingToTake / (double) remainingToIterate;
                if (random.nextDouble() < p) {
                    destination.add(element);
                    remainingToTake--;
                }
                remainingToIterate--;
            }
        }
    }
    
    /**
     * Draws elements from the source, weighted by weight, until the total weight exceeds upper bound
     * 
     * This introduces a small bias towards larger items, but it is around 1% when the largest item is a few hundreds of times larger than the smallest
     * 
     * I think the bias is essentially in the >= test at the end, because bigger items have a better chance there?
     * 
     * @param from
     * @param into
     * @param random
     * @param upperBound
     * @param scales
     */
    public static <T> void drawWeighted(
    		final Collection<T> from, 
    		final Collection<T> into, 
    		final Random random, 
    		final double upperBound,
    		final com.google.common.base.Function<T, Double> scales
    		) {
    	final List<T> temp = new ArrayList<T>(from);
    	Collections.shuffle(temp, random);
    	double acc = 0;
    	for (final T t : temp) {
    		into.add(t);
    		acc += scales.apply(t);
    		if (acc >= upperBound) break;
    	}
    }
    
    /**
     * Take a subset of <em>up to</em> maxElems elements from source and place them in destination according to the 
     * probability associated with the element at the corresponding index in probs array.
     * 
     * @author tomw
     * 
     * @param source
     * @param destination
     * @param random
     * @param probs
     * @param maxElems
     */
    protected static <T> void draw(final Collection<T> source, final Collection<T> destination, final Random random, final double[] probs, final Integer maxElems) {
    	if(source.size()>probs.length)
    		throw new IllegalArgumentException("source ("+source.size()+") has more elements than associated probs ("+probs.length+")");
    	
    	int i=0, count=0;
    	int mxElems = source.size();
    	if(maxElems!=null)
    		mxElems = maxElems.intValue();
    	
    	for(final T e : source) {
    		final double p = probs[i++];
    		if(random.nextDouble()<p) {
    			destination.add(e);
    			if(++count>=mxElems) break;
    		}
    	}
	}
}
