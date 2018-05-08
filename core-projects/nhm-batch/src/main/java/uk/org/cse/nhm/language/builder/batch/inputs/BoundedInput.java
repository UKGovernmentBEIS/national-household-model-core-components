package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;

public class BoundedInput implements IBatchInputs {

    private final Optional<Integer> bound;
    private final IBatchInputs delegate;

    public BoundedInput(int bound, IBatchInputs delegate) {
        Optional<Integer> delegateBound = delegate.getBound();
        if (delegateBound.isPresent()) {
            this.bound = Optional.of(Math.min(bound, delegateBound.get()));
        } else {
            this.bound = Optional.of(bound);
        }

        this.delegate = delegate;
    }

    @Override
    public Iterator<List<Object>> iterator() {
        return new Iterator<List<Object>>() {
            private final Iterator<List<Object>> delegateIterator = delegate.iterator();
            private int accum = 0;

            @Override
            public boolean hasNext() {
                return accum < bound.get();
            }

            @Override
            public List<Object> next() {
                accum += 1;
                return delegateIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not Implemented");
            }
        };
    }

    @Override
    public Optional<Integer> getBound() {
        return bound;
    }

    @Override
    public List<String> getPlaceholders() {
        return delegate.getPlaceholders();
    }

}
