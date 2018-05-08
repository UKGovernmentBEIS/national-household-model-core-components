package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

/**
 * ConcatCombinator takes some delegate inputs which must have the same
 * placeholders, and turns them into one very long input.
 */
public class ConcatCombinator implements IBatchInputs {

    private final List<IBatchInputs> delegates;
    private final List<String> placeholders;
    private final Optional<Integer> bound;

    public ConcatCombinator(List<IBatchInputs> delegates) {

        this.delegates = delegates;
        this.placeholders = delegates.get(0).getPlaceholders();
        assertPlaceHolders();
        this.bound = calculateBound();
    }

    private Optional<Integer> calculateBound() {
        int accum = 0;
        boolean infinite = false;
        for (IBatchInputs i : delegates) {
            if (infinite) {
                throw new IllegalArgumentException("An unbounded input was passed to a ConcatCombinator which was not the last input.");
            }

            Optional<Integer> delegateBound = i.getBound();
            if (delegateBound.isPresent()) {
                accum += delegateBound.get();
            } else {
                infinite = true;
            }
        }

        return infinite ? Optional.<Integer>absent() : Optional.of(accum);
    }

    private void assertPlaceHolders() {
        for (IBatchInputs i : delegates) {
            if (!i.getPlaceholders().equals(placeholders)) {
                throw new IllegalArgumentException("All delegate inputs passed to a ConcatCombinator must have the same placeholder names.");
            }
        }
    }

    @Override
    public List<String> getPlaceholders() {
        return placeholders;
    }

    @Override
    public Optional<Integer> getBound() {
        return bound;
    }

    @Override
    public Iterator<List<Object>> iterator() {
        return new Iterator<List<Object>>() {
            private final Iterator<IBatchInputs> delegatesIterator = delegates.iterator();
            private Iterator<List<Object>> current;

            @Override
            public boolean hasNext() {
                return delegatesIterator.hasNext() || currentHasNext();
            }

            @Override
            public List<Object> next() {
                if (!currentHasNext()) {
                    current = delegatesIterator.next().iterator();
                }

                return current.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not Implemented");
            }

            private boolean currentHasNext() {
                return current != null && current.hasNext();
            }
        };
    }
}
