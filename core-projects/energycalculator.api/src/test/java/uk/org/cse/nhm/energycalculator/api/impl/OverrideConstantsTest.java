package uk.org.cse.nhm.energycalculator.api.impl;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.IConstant;

public class OverrideConstantsTest {

    private static enum TestConstant implements IConstant {
        First,
        Second;

        @Override
        public <T> T getValue(Class<T> clazz) {
            if (this == First) {
                return clazz.cast((Double) 10.0);
            } else if (this == Second) {
                return clazz.cast(new double[]{1, 2});
            } else {
                return null;
            }
        }

    }

    @Test
    public void testOverridesDouble() {
        final OverrideConstants oc = new OverrideConstants(DefaultConstants.INSTANCE,
                ImmutableMap.<Enum<?>, Object>of(TestConstant.First, 9.2));

        Assert.assertEquals("First is overridden", 9.2, oc.get(TestConstant.First), 0);
        Assert.assertEquals("Second is unaffected", 1, oc.get(TestConstant.Second, 0), 0);
        Assert.assertEquals("Second is unaffected", 2, oc.get(TestConstant.Second, 1), 0);
    }

    @Test
    public void testOverridesDoubleArrays() {
        final OverrideConstants oc = new OverrideConstants(DefaultConstants.INSTANCE,
                ImmutableMap.<Enum<?>, Object>of(TestConstant.Second, new double[]{3.3, 4}));

        Assert.assertEquals("First is unchanged", 10.0, oc.get(TestConstant.First), 0);
        Assert.assertEquals("Second[0] is 3", 3.3, oc.get(TestConstant.Second, 0), 0);
        Assert.assertEquals("Second[1] is 4", 4, oc.get(TestConstant.Second, 1), 0);
    }
}
