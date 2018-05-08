package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

public class ProductCombinator extends WideInputCombinator {

    private final Optional<Integer> bound;
    private final List<Integer> delegateBounds;
    private final List<List<List<Object>>> data;

    public ProductCombinator(List<IBatchInputs> delegates) {
        super(delegates);

        this.data = cacheData(delegates);
        this.delegateBounds = getDelegateBounds(delegates);
        this.bound = calcBound(delegateBounds);
    }

    private ImmutableList<Integer> getDelegateBounds(List<IBatchInputs> delegates) throws IllegalArgumentException {
        ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        for (IBatchInputs d : delegates) {
            Optional<Integer> delegateBound = d.getBound();
            if (delegateBound.isPresent()) {
                builder.add(delegateBound.get());
            } else {
                throw new IllegalArgumentException("Inputs to a product combinator must always be bounded.");
            }

        }
        return builder.build();
    }

    /*
	 * We precompute the data for each input, in case any of them are non-deterministic.
     */
    private ImmutableList<List<List<Object>>> cacheData(List<IBatchInputs> delegates) {
        ImmutableList.Builder<List<List<Object>>> builder = ImmutableList.builder();
        for (IBatchInputs delegate : delegates) {
            builder.add(ImmutableList.copyOf(delegate.iterator()));
        }
        return builder.build();
    }

    public Optional<Integer> calcBound(List<Integer> delegateBounds) {
        int bound = 1;
        for (Integer d : delegateBounds) {
            bound *= d;
        }
        return Optional.of(bound);
    }

    @Override
    public Optional<Integer> getBound() {
        return this.bound;
    }

    @Override
    public Iterator<List<Object>> iterator() {
        return new Iterator<List<Object>>() {
            int progress = 0;

            @Override
            public boolean hasNext() {
                return progress < bound.get();
            }

            @Override
            public List<Object> next() {
                ImmutableList.Builder<Object> result = ImmutableList.builder();

                int accum = 1;
                for (int i = 0; i < delegates.size(); i++) {
                    int dBound = delegateBounds.get(i);

                    List<List<Object>> delegateData = data.get(i);
                    result.addAll(delegateData.get(
                            (progress / accum) % dBound));

                    accum *= dBound;
                }

                progress++;
                return result.build();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not Implemented");
            }
        };
    }
}
