package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.builder.batch.inputs.SingleInput;

abstract class Distribution extends SingleInput {
	private final Random random;

	protected Distribution(final Random random, final String placeholder) {
		super(placeholder);
		this.random = random;
	}
	
	@Override
	public Optional<Integer> getBound() {
		return Optional.absent();
	}
	
	@Override
	public final Iterator<List<Object>> iterator() {
		return new Iterator<List<Object>>() {
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not Implemented");
			}
			
			@Override
			public List<Object> next() {
				return Collections.singletonList(nextRandom(random));
			}
			
			@Override
			public boolean hasNext() {
				return true;
			}
		};
	}
	
	abstract protected Object nextRandom(Random random);
}
