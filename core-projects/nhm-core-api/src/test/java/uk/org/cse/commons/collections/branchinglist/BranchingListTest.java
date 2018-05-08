package uk.org.cse.commons.collections.branchinglist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BranchingListTest {

    private IBranchingList<Object> l;
    private IBranchingList<Object> child;

    @Before
    public void setup() {
        l = BranchingList.create();
        child = l.branch();
    }

    @Test
    public void testEmptyList() {
        assertFalse("Should be no elements in iterator.", l.iterator().hasNext());
        assertFalse("Should be no elements in reverse iterator.", l.reverseIterator().hasNext());
        assertTrue("Should be valid", l.isValid());
    }

    @Test
    public void appendToRootNodeAndIterate() {
        Object a = "a";
        Object b = "b";
        l.add(a);
        l.add(b);

        assertIterationOrder("Should iterate in order added.", l.iterator(), a, b);
        assertIterationOrder("Should iterate in reverse of order added, oldest ancestors first.", l.reverseIterator(), b, a);
    }

    @Test
    public void invalidateBranchByModifyingParent() {
        assertTrue("Child should be valid.", child.isValid());

        l.add(new Object());

        assertTrue("Parent should still be valid.", l.isValid());
        assertFalse("Child should be invalid.", child.isValid());
    }

    @Test
    public void invalidateBranchByModifyingGrandParent() {
        IBranchingList<Object> grandChild = child.branch();
        assertTrue("Child should be valid.", child.isValid());
        assertTrue("Grand child should be valid.", grandChild.isValid());

        l.add(new Object());

        assertTrue("Parent should still be valid.", l.isValid());
        assertFalse("Child should be invalid.", child.isValid());
        assertFalse("Grand child should be invalid.", grandChild.isValid());
    }

    @Test(expected = IllegalStateException.class)
    public void invalidBranchCannotBeAddedTo() {
        l.add(new Object());
        child.add(new Object());
    }

    @Test(expected = IllegalStateException.class)
    public void invalidBranchCannotBeIterated() {
        l.add(new Object());
        child.iterator();
    }

    @Test(expected = IllegalStateException.class)
    public void invalidBranchCannotBeReverseIterated() {
        l.add(new Object());
        child.reverseIterator();
    }

    @Test
    public void iterateOverBranchStartsWithParentItems() {
        Object a = "a";
        Object b = "b";
        l.add(a);
        l.add(b);

        child = l.branch();
        Object c = "c";
        Object d = "d";
        child.add(c);
        child.add(d);

        IBranchingList<Object> grandChild = child.branch();
        Object e = "e";
        Object f = "f";
        grandChild.add(e);
        grandChild.add(f);

        assertIterationOrder("Should iterate in order added, oldest ancestors first.", grandChild.iterator(), a, b, c, d, e, f);
        assertIterationOrder("Should iterate in reverse order added, oldest ancestors last.", grandChild.reverseIterator(), f, e, d, c, b, a);
    }

    @SuppressWarnings("unchecked")
    private <T> void assertIterationOrder(String message, Iterator<T> actual, T... expected) {
        assertEquals(message, ImmutableList.copyOf(expected), ImmutableList.copyOf(actual));
    }
}
