package uk.org.cse.nhm.simulator.state.impl;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.impl.CanonicalDimension;
import uk.org.cse.nhm.simulator.state.dimensions.impl.IDimensionManager;

public class CanonicalDimensionTest {

    private Object dv;
    private IDimensionManager<Object> manager;
    private CanonicalDimension<Object> fixture;
    private IDwelling dwelling;

    @Before
    public void setup() {
        this.dwelling = mock(IDwelling.class, "Dwelling");
        this.dv = mock(Object.class, "Default");
        this.manager = new IDimensionManager<Object>() {
            @Override
            public Object copy(final Object instance) {
                return mock(Object.class, "Copy of " + instance);
            }

            @Override
            public Object internalise(final Object instance) {
                return instance;
            }

            @Override
            public boolean isEqual(final Object first, final Object second) {
                return first.equals(second);
            }

            @Override
            public Object getDefaultValue() {
                return dv;
            }
        };

        this.fixture = new CanonicalDimension<Object>(new DimensionCounter(), "test", manager);
    }

    @Test
    public void testCanonicalDimension() {
        testDefaultValue(fixture);
        testGenerationCounter(fixture, 0);
        testCopy(fixture);

        final IInternalDimension<Object> fork = fixture.branch(mock(IBranch.class), 1);

        testGenerationCounter(fork, 1);
        testGenerationCounter(fork, 2);
        testCopy(fork);

        // this parallel fork should be unaffected
        final IInternalDimension<Object> fork2 = fixture.branch(mock(IBranch.class), 1);

        testGenerationCounter(fork2, 1);
        testGenerationCounter(fork2, 2);

        // fork3 is a fork off fork2, and so should be starting where fork2 is
        final IInternalDimension<Object> fork3 = fork2.branch(mock(IBranch.class), 1);

        testGenerationCounter(fork3, 3);

        // check fork2 has not been broken  by fork3
        testGenerationCounter(fork2, 3);

        // check that modifying the gen counter in the fork has not broken the original
        testGenerationCounter(fixture, 1);
    }

    public void testGenerationCounter(final IInternalDimension<Object> fixture, final int g) {
        Assert.assertEquals("Generation counter starts as expected", g, fixture.getGeneration(dwelling));

        final Object o = mock(Object.class, "New Value");

        fixture.set(dwelling, o);

        Assert.assertEquals("Generation counter increased", g + 1, fixture.getGeneration(dwelling));

        fixture.set(dwelling, o);

        Assert.assertEquals("Generation counter not increased (identical set)", g + 1, fixture.getGeneration(dwelling));

    }

    public void testDefaultValue(final IInternalDimension<Object> fixture) {
        Assert.assertEquals("default value is present before any change", dv, fixture.get(dwelling));
    }

    public void testCopy(final IInternalDimension<Object> fixture) {
        Assert.assertNotSame("Copy is not original", fixture.get(dwelling), fixture.copy(dwelling));
    }
}
