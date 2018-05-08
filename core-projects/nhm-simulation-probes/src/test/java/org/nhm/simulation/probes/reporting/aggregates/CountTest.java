package org.nhm.simulation.probes.reporting.aggregates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.simulation.reporting.aggregates.Count;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class CountTest {

    @Test
    public void testCountCounts() {
        final ILets lets = ILets.EMPTY;
        final Random r = new Random();
        final Count c = new Count();
        for (int i = 0; i < 10; i++) {
            final int k = r.nextInt(100);
            final Set<IDwelling> things = new HashSet<IDwelling>();
            for (int j = 0; j < k; j++) {
                final IDwelling mock = mock(IDwelling.class);
                when(mock.getWeight()).thenReturn(1f);
                things.add(mock);
            }
            Assert.assertEquals(k, c.evaluate((IStateScope) null, lets, things), 0);
        }
    }
}
