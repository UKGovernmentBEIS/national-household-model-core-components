package uk.org.cse.nhm.utility;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class DeduplicatingMapTest {
    @Test
    public void canBuildEmpty() {
        test(ImmutableMap.<String, String> of());
    }

    @Test
    public void canAddUniqueItems() {
        test(ImmutableMap.of(
                "a", "a",
                "b", "b",
                "c", "c"));
    }

    @Test
    public void canAddDuplicateItems() {
        test(ImmutableMap.of(
                "a", "a",
                "a_1", "a",
                "a_2", "a",
                "b", "b",
                "b_1", "b"
                ));
    }

    @Test
    public void cantTrickTheMap() {
        test(ImmutableMap.of(
                "a", "a",
                "a_1", "a",
                "a_1_1", "a_1"
                ));
        
        test(ImmutableMap.of(
                "a", "a",
                "a_1_1", "a_1",
                "a_1", "a"
                ));
    }

    /*
     * Takes a map of expected output -> input.
     */
    private void test(final Map<String, String> assertions) {
        final DeduplicatingMap.Builder<Object> b = DeduplicatingMap.stringBuilder();

        for (final String k : assertions.values()) {
            b.put(k, new Object());
        }

        final ImmutableMap<String, Object> m = b.build();

        Assert.assertEquals("Assertions and resulting map should be the same size.", assertions.size(), m.size());
        for (final String a : assertions.keySet()) {
            Assert.assertTrue("Resulting map should contain everything we asked for."
            		+ m.keySet() + " / " + a
            		, 
            		m.containsKey(a));
        }
    }
}
