package uk.org.cse.nhm.language.builder.batch.inputs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class BoundedInputTest {

	private IBatchInputs delegate;
	private List<String> placeholders;
	
	@Before
	public void setup() {
		delegate = mock(IBatchInputs.class);
		placeholders = ImmutableList.of("$a", "$b");
		when(delegate.getPlaceholders()).thenReturn(placeholders);
		when(delegate.getBound()).thenReturn(Optional.<Integer>absent());
	}
	@Test
	public void getsPlaceholdersFromDelegate() {
		BoundedInput bounded = new BoundedInput(2, delegate);
		Assert.assertEquals("Bounded should have taken placeholders from delegate inputs.", placeholders, bounded.getPlaceholders());
	}
	
	@Test
	public void getsBoundFromMinOfSelfAndDelegate() {
		when(delegate.getBound()).thenReturn(Optional.<Integer>absent());
		BoundedInput bounded = new BoundedInput(2, delegate);
		Assert.assertEquals("If delegate is unbounded, should apply bound.", Optional.of(2), bounded.getBound());
		
		when(delegate.getBound()).thenReturn(Optional.of(1));
		bounded = new BoundedInput(2, delegate);
		Assert.assertEquals("If delegate has smaller bound, use that.", Optional.of(1), bounded.getBound());
		
		when(delegate.getBound()).thenReturn(Optional.of(3));
		bounded = new BoundedInput(2, delegate);
		Assert.assertEquals("If delegate has larger bound, reduce its bound.", Optional.of(2), bounded.getBound());
	}
	
	@Test
	public void useDelegateIteratorLimitedByBound() {
		BoundedInput bounded = new BoundedInput(1, delegate);
		final ImmutableList<Object> data = ImmutableList.of(new Object(), new Object());
		
		when(delegate.iterator()).thenReturn(new Iterator<List<Object>>(){

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public List<Object> next() {
				
				return data;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not Implemented");
			}
		});
		
		
		ImmutableList<List<Object>> results = ImmutableList.copyOf(bounded.iterator());
		Assert.assertEquals("Should only have returned 1 item because of bound.", 1, results.size());
		Assert.assertEquals("Should return data from delegate's iterator.", data, results.get(0));
	}
}
