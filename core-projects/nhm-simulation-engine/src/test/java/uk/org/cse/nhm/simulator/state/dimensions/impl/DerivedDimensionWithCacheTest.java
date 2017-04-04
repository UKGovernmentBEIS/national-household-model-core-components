package uk.org.cse.nhm.simulator.state.dimensions.impl;

import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class DerivedDimensionWithCacheTest {
	static final class TestDimension extends DerivedDimensionWithCache<Object> {
		public final Map<IDwelling, Integer> gen = new HashMap<IDwelling, Integer>();
		public final Map<IDwelling, Object> value = new HashMap<IDwelling, Object>();
		
		public final Map<IDwelling, Integer> computeCounter = new HashMap<IDwelling, Integer>();
		
		public TestDimension() {
			super(0, null,1);
		}
		
		protected TestDimension(final DerivedDimensionWithCache<Object> parent) {
			super(0, parent, 1);
		}

		
		@Override
		public int getGeneration(final IDwelling instance) {
			return gen.get(instance);
		}

		@Override
		public IInternalDimension<Object> branch(final IBranch forkingState, final int capacity) {
			return new TestDimension(this);
		}

		@Override
		protected Object doGet(final IDwelling instance) {
			if (computeCounter.containsKey(instance)) {
				computeCounter.put(instance, computeCounter.get(instance) +1);
			} else {
				computeCounter.put(instance, 1);
			}
			return value.get(instance);
		}
	}
	
	@Test
	public void testNoRecompute() {
		final TestDimension td = new TestDimension();
		final IDwelling d = mock(IDwelling.class);
		final Object o = new Object();
		td.gen.put(d, 0);
		td.value.put(d, o);
		
		Assert.assertEquals("We have never computed the value for d", null, td.computeCounter.get(d));
		
		Object object = td.get(d);
		
		Assert.assertEquals("We have computed the value for d once", (Integer) 1, td.computeCounter.get(d));
		
		Assert.assertSame("The result is what we expected", object, o);
		
		object = td.get(d);
		
		Assert.assertEquals("We haven't recomputed, because gen hasn't changed", (Integer) 1, td.computeCounter.get(d));
		
		Assert.assertSame("The result is reused", object, o);
		
		// make it look like the dependency is changed
		td.gen.put(d, 1);
		
		final Object o2 = new Object();
		td.value.put(d, o2);
		
		object = td.get(d);
		
		Assert.assertEquals("We have recomputed, because gen has changed", (Integer) 2, td.computeCounter.get(d));
		
		Assert.assertSame("The right answer comes out", object, o2);
	}
	
	@Test
	public void getMostRecentValueIgnoresGeneration() {
		final TestDimension dimension = new TestDimension();
		final IDwelling d = mock(IDwelling.class);
		dimension.gen.put(d,  0);
		final Object o = new Object();
		dimension.value.put(d, o);
		dimension.get(d);
		dimension.gen.put(d,  1);
		
		Assert.assertSame("getMostRecentValue should get the object regardless of generation.",  o, dimension.getMostRecentValue(d));
		
		final TestDimension childDimension = new TestDimension(dimension);
		childDimension.gen.put(d, 2);
		
		Assert.assertSame("getMostRecentValue should look on its parent if it cannot find an example itself.",  o, childDimension.getMostRecentValue(d));
		
		childDimension.value.put(d, new Object());
		childDimension.get(d);
		Assert.assertNotSame("getMostRecentValue should not look at its parent if it has a more recent version of its own.", o, childDimension.getMostRecentValue(d));
	}
}
