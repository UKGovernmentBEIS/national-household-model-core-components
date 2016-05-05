package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class ZipCombinator extends WideInputCombinator {
	public ZipCombinator(List<IBatchInputs> delegates) {
		super(delegates);
	}
	
	@Override
	public Iterator<List<Object>> iterator() {
		final List<Iterator<List<Object>>> delegateIterators = new ArrayList<>();
		for(IBatchInputs delegate : delegates) {
			delegateIterators.add(delegate.iterator());
		}
		
		return new Iterator<List<Object>>() {
			@Override
			public boolean hasNext() {
				for(Iterator<List<Object>> i : delegateIterators) {
					if(!i.hasNext()) {
						return false;
					}
				}
				return true;
			}

			@Override
			public List<Object> next() {
				ImmutableList.Builder<Object> builder = ImmutableList.builder();
				
				for(Iterator<List<Object>> i : delegateIterators) {
					builder.addAll(ImmutableList.copyOf(i.next()));
				}
				
				return builder.build();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not Implemented");
			}
		};
	}

	@Override
	public Optional<Integer> getBound() {
		Integer bound = null;
		for(IBatchInputs d : delegates) {
			Optional<Integer> current = d.getBound();
			if(current.isPresent()) {
				if(bound == null || bound > current.get()) {
					bound = current.get();
				}
			}
		}
		return Optional.fromNullable(bound);
	}
}
