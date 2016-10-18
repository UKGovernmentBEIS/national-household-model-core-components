package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;

public class RangeInput extends SingleInput {
	private final double start;
	private final double step;
	private final Optional<Double> end;
	
	public RangeInput(final String placeholder, final double start, final double step) {
		this(placeholder, start, step, Optional.<Double>absent());
	}
	
	public RangeInput(final String placeholder, final double start, final double step, final double end) {
		this(placeholder, start, step, Optional.of(end));
	}

	public RangeInput(final String placeholder, final double start, final double step, final Optional<Double> end) {
		super(placeholder);
		this.start = start;
		this.step = step;
		this.end = end;
	}

	@Override
	public Optional<Integer> getBound() {
		if(end.isPresent()) {
			int values = 0;
			double current = start;
			while (current < end.get()) {
				values++;
				current += step;
			}
			
			return Optional.of(values);
		} else {
			return Optional.absent();
		}
	}

	@Override
	public Iterator<List<Object>> iterator() {
		return new Iterator<List<Object>>() {
			double accum = start;

			@Override
			public boolean hasNext() {
				return !(end.isPresent() && accum >= end.get()); 
			}

			@Override
			public List<Object> next() {
				final List<Object> result = Collections.<Object>singletonList(accum);
				accum += step;
				return result;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not Implemented");
			}
		};
	}
}
