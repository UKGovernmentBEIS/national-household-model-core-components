package uk.org.cse.nhm.simulator.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * The simulation random source. This extends the functions of the standard
 * java.util.random to provide some extra features:
 * 1. the random state can be changed after construction
 * 2. there are convenience methods for shuffling and picking
 * 3. the random algorithm is a better one (XORShift due to George Marsaglia)
 */
public class RandomSource extends Random {
	private static final long serialVersionUID = 1L;
	private long seed;
    private int justConsumed = 0;
    
    public RandomSource(final long seed) {
    	this(seed, 0);
	}
    
    public RandomSource(final long seed, final int justConsumed) {
		super();
		this.seed = seed == 0 ? 1 : seed;
		this.justConsumed = justConsumed;
	}

	public RandomSource dup() {
    	return new RandomSource(seed, justConsumed);
    }

	public synchronized long getSeed() {
		return seed;
	}

    @Override
	public synchronized void setSeed(final long seed) {
		this.seed = seed;
		super.setSeed(seed);
	}

    public void sync(final RandomSource other) {
        setSeed(other.getSeed());
        justConsumed = other.justConsumed;
    }

    /**
     * Utility to shuffle a list
     */
    public void shuffle(final List<?> things) {
        Collections.shuffle(things, this);
    }

    /**
     * Utility to get a gaussian which is scaled and shifted
     */
    public double nextGaussian(final double mean,
                               final double standardDeviation) {
        return nextGaussian() * standardDeviation + mean;
    }

    /**
     * Utility to select something from a list of options, weighted by some weights.
     */
    public <T> T chooseOne(final List<T> options,
                           final List<Double> weights) {    	
    	Preconditions.checkNotNull(options);
    	Preconditions.checkNotNull(weights);
    	Preconditions.checkArgument(
    			options.size() == weights.size(), 
    			"number of options to choose from (%d) does not equal number of weights (%d)", 
    			options.size(), weights.size());
    	
    	if (options.isEmpty()) return null;
        // compute the total
        double sum = 0;
        for (final double d : weights) sum +=d;
        // choose a limit uniformly somewhere between 0 and sum.
        final double limit = nextDouble() * sum;
        
        // find the item without whom the total weight is less or equal to the limit,
        // and with whom the total weight is above the limit.
        int index = 0;
        double acc = 0;
        for (final double weight : weights) {
            if (acc <= limit && limit < (acc + weight)) return options.get(index);
			acc += weight;
            index++;
        }
        return options.get(options.size() - 1);
    }

    /**
     * Randomly draw a population of items from input so that their total weight under the
     * weight function is around the given amount, placing them into output. This is
     * statistically correct in the limit of the weight of each thing being very small and
     * the number being very large.
     */
    public <T> void chooseMany(final Collection<T> input,
                               final double amount,
                               final Function<T, Double> weight,
                               final Collection<T> output) {
        // randomize the input
        final List<T> copyOfInput = new ArrayList<T>(input);
        shuffle(copyOfInput);
        // go through it until we have enough weight
        double acc = 0;
        for (final T thing : copyOfInput) {
            if (acc >= amount) break;
            final double weightOfThing = weight.apply(thing);
            final double acc2 = acc + weightOfThing;
            // this 1 here is because we expect the amounts + weights to be large.
            if (acc2 < amount) {
                output.add(thing);
            } else {
                // adding the thing will take us over the threshold quite a lot so we will
                // either keep it and be a bit over or throw it away and be a bit
                // under. we do this randomly, based on the magnitude of the error
                // introduced.
                final double exclusionError = amount - acc;
                final double inclusionError = acc2 - amount;
                final double threshold = exclusionError / (exclusionError + inclusionError);

                // exclusion error = 0
                // threshold = 0
                // nextDouble is never 0
                // we never include

                // inclusion error = 0
                // threshold = 1
                // nextDouble is always less than or equal to 1
                // we always include
                
                if (nextDouble() <= threshold) {
                    output.add(thing);
                }
                    
                break;
            }
            acc = acc2;
        }
    }
    
    public void consumeRandomness(int nbits) {
    	if (nbits == justConsumed || nbits == 0) return;
    	justConsumed = nbits;
        for (int i = 0; i<nbits; i++) nextInt();
    }
    
    /*
     * XORShift to advance the rng
     */
    @Override
	protected int next(final int nbits) {
		long x = seed;
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		seed = x;
		x &= ((1L << nbits) - 1);
		return (int) x;
	}
}

