package uk.org.cse.nhm.reporting.standard.explain.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class EdgeTest {

    private static final DateTime NOW = new DateTime(0);

    private Node source;
    private Node target;
    private Edge defaultEdge;

    @Before
    public void setUp() {
        source = mock(Node.class);
        target = mock(Node.class);
        defaultEdge = new Edge(source, target, 0, "", NOW);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sourceMustNotBeNull() {
        new Edge(null, target, 0, "", NOW);
    }

    @Test(expected = IllegalArgumentException.class)
    public void targetMustNotBeNull() {
        new Edge(source, null, 0, "", NOW);
    }

    @Test
    public void connectJoinsSourceAndTarget() {
        defaultEdge.connect();
        verify(source, times(1)).addChildEdge(defaultEdge);
        verify(target, times(1)).addParentEdge(defaultEdge);
    }

    @Test(expected = RuntimeException.class)
    public void getSourceIndexThrowsErrorIfSearchListNotSet() {
        defaultEdge.getSourceIndex();
    }

    @Test(expected = RuntimeException.class)
    public void getTargetIndexThrowsErrorIfSearchListNotSet() {
        defaultEdge.getTargetIndex();
    }

    @Test(expected = RuntimeException.class)
    public void throwsErrorIfSourceNotInList() {
        defaultEdge.setNodeSearchSource(ImmutableList.<Node>of());
        defaultEdge.getSourceIndex();
    }

    @Test(expected = RuntimeException.class)
    public void throwsErrorIfTargetNotInList() {
        defaultEdge.setNodeSearchSource(ImmutableList.<Node>of());
        defaultEdge.getTargetIndex();
    }

    @Test
    public void findsSourceAndTargetInList() {
        defaultEdge.setNodeSearchSource(ImmutableList.<Node>of(source, target));
        Assert.assertEquals(0, defaultEdge.getSourceIndex());
        Assert.assertEquals(1, defaultEdge.getTargetIndex());
    }
}
