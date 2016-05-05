package org.nhm.simulation.probes.reporting.aggregates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulation.reporting.aggregates.Sum;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SumTest {
	@Test
	public void testSumSums() {
		final Map<Integer, Double> values = new HashMap<Integer, Double>();
		final IComponentsFunction<Number> func = new IComponentsFunction<Number>() {
			@Override
			public Double compute(final IComponentsScope scope, final ILets lets) {
				return values.get(scope.getDwellingID());
			}

			@Override
			public Set<IDimension<?>> getDependencies() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Set<DateTime> getChangeDates() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public Name getIdentifier() {
				return null;
			}
		};
		final Random r = new Random();
		final IState state = mock(IState.class);
		double acc = 0;
		
		final Set<IDwelling> ds = new HashSet<IDwelling>();
		
		for (int i = 0; i<100; i++) {
			final double value = r.nextDouble() * 100;
			acc+= value;
			final IDwelling d = mock(IDwelling.class);
			when(d.getWeight()).thenReturn(1f);
			when(d.getID()).thenReturn(i);
			values.put(i, value);
			
			final IComponentsScope c = mock(IComponentsScope.class);
			
			when(c.getDwellingID()).thenReturn(i);
			
			when(state.detachedScope(d)).thenReturn(c);
			ds.add(d);
		}
		final ILets lets = ILets.EMPTY;
		final Sum s = new Sum(func);
		Assert.assertEquals(acc, s.evaluate(state, lets, ds), 0.000001);
	}
}
