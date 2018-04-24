package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

abstract public class CombinatorTest {

	IBatchInputs combinator;

	protected final IBatchInputs getCombinator(IBatchInputs... delegates) {
		return getCombinator(ImmutableList.copyOf(delegates));
	}
	
	abstract protected IBatchInputs getCombinator(List<IBatchInputs> delegates);
	
	protected IBatchInputs makeDelegate(Integer bound, String... placeholders) {
		IBatchInputs delegate = mock(IBatchInputs.class);
		when(delegate.getBound()).thenReturn(Optional.fromNullable(bound));
		when(delegate.getPlaceholders()).thenReturn(ImmutableList.copyOf(placeholders));
		when(delegate.iterator()).thenReturn(Collections.singletonList(Collections.emptyList()).iterator());
		return delegate;
	}

	protected void addDataToMock(IBatchInputs mock, Object... data) {
		ImmutableList.Builder<List<Object>> dataInLists = ImmutableList.builder();
		for (Object o : data) {
			dataInLists.add(Collections.singletonList(o));
		}

		ImmutableList<List<Object>> built = dataInLists.build();

		when(mock.iterator()).thenReturn(built.iterator());
	}
}
