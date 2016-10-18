package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XRepetitions;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class RepetitionsInput implements IBatchInputs {
	private final int count;
	private final IBatchInputs vars;
	private final Random random;

	private static final IBatchInputs empty = new IBatchInputs() {
		@Override
		public Iterator<List<Object>> iterator() {
			return ImmutableList.<List<Object>>of(ImmutableList.of()).iterator();
		}
		
		@Override
		public List<String> getPlaceholders() {
			return ImmutableList.<String>of();
		}
		
		@Override
		public Optional<Integer> getBound() {
			return Optional.of(1);
		}
	};
	
	public RepetitionsInput(final Random random, int count, Optional<IBatchInputs> vars) {
		this.random = random;
		this.count = count;
		this.vars = vars.or(empty);
	}

	@Override
	public Iterator<List<Object>> iterator() {
		final Iterator<List<Object>> varsIterator = vars.iterator();
		return new Iterator<List<Object>>() {
			int position = count;
			List<Object> lastVars;
			@Override
			public boolean hasNext() {
				return (position<count) || varsIterator.hasNext();
			}

			@Override
			public List<Object> next() {
				if (position >= count) {
					position = 0;
					lastVars = varsIterator.next();
				}
				
				final long seed = random.nextLong();
				position++;
				
				return ImmutableList.builder()
						.addAll(lastVars)
						.add(seed)
						.build();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Optional<Integer> getBound() {
		final Optional<Integer> val = vars.getBound();
		if (val.isPresent()) {
			return Optional.of(val.get() * count);
		} else {
			return Optional.absent();
		}
	}

	@Override
	public List<String> getPlaceholders() {
		return ImmutableList.<String>builder().
				addAll(vars.getPlaceholders()).
				add(XRepetitions.SEED)
				.build();
	}
}
