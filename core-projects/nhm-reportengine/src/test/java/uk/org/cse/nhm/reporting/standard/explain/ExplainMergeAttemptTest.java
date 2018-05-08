package uk.org.cse.nhm.reporting.standard.explain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.reporting.standard.explain.model.Edge;
import uk.org.cse.nhm.reporting.standard.explain.model.FromOutsideNode;
import uk.org.cse.nhm.reporting.standard.explain.model.Node;
import uk.org.cse.nhm.reporting.standard.explain.model.ToOutsideNode;

public class ExplainMergeAttemptTest {

    private static double ERROR_DELTA = 0.01d;

    private Node fromOutside;
    private Node toOutside;
    private ExplainLogEntry entry;

    private Node emptySource;
    private Node source;
    private Map<String, Node> sourceNodes;

    private Node target;
    private Map<String, Node> targetNodes;

    private List<ExplainArrow> arrows;

    ExplainMergeAttempt merge;

    @Before
    public void setUp() {
        fromOutside = new FromOutsideNode();
        toOutside = new ToOutsideNode();

        entry = mock(ExplainLogEntry.class);
        arrows = new ArrayList<ExplainArrow>();
        when(entry.getArrows()).thenReturn(arrows);

        emptySource = new Node("emptySource");
        source = new Node("source");
        final Edge e = mock(Edge.class);
        when(e.getValue()).thenReturn(1d);
        source.addParentEdge(e);
        sourceNodes = new HashMap<String, Node>();
        sourceNodes.put(emptySource.getName(), emptySource);
        sourceNodes.put(source.getName(), source);

        target = new Node("target");
        targetNodes = new HashMap<String, Node>();
        targetNodes.put(target.getName(), target);
    }

    @Test
    public void arrowsWithCount0AreIgnored() {
        final ExplainArrow a = mock(ExplainArrow.class);
        when(a.getFrom()).thenReturn("never exists");
        when(a.getCount()).thenReturn(0f);
        arrows.add(a);

        merge();
        Assert.assertEquals(true, merge.isSuccess());
        Assert.assertEquals(ImmutableSet.of(), merge.getChangedSources());
        Assert.assertEquals(ImmutableMap.of(), merge.getNewTargets());
        Assert.assertEquals(ImmutableList.of(), merge.getNewEdges());
    }

    @Test
    public void shouldNotSucceedIfSourceNodeDoesNotExist() {
        final ExplainArrow a = mock(ExplainArrow.class);
        when(a.getFrom()).thenReturn("never exists");
        when(a.getCount()).thenReturn(1f);
        arrows.add(a);

        merge();
        Assert.assertEquals(false, merge.isSuccess());
    }

    @Test
    public void canRemoveThingsFromOutside() {
        addArrowBetween(fromOutside, toOutside, 1);

        merge();
        Assert.assertEquals(true, merge.isSuccess());
    }

    @Test
    public void cannotRemoveMoreThingsThanExistFromASource() {
        addArrowBetween(emptySource, toOutside, 1);

        merge();
        Assert.assertEquals(false, merge.isSuccess());
    }

    @Test
    public void canRemoveFromExistingSources() {
        addArrowBetween(source, toOutside, 1);

        merge();
        Assert.assertEquals(true, merge.isSuccess());
    }

    @Test
    public void testMergeToExistingTarget() {
        addArrowBetween(source, target, 1);

        merge();
        Assert.assertEquals(true, merge.isSuccess());
        Assert.assertEquals(ImmutableMap.of(), merge.getNewTargets());

        Assert.assertEquals(1, merge.getNewEdges().size());
        final Edge e = merge.getNewEdges().get(0);
        Assert.assertEquals(source, e.getSource());
        Assert.assertEquals(target, e.getTarget());
        Assert.assertEquals(1d, e.getValue(), ERROR_DELTA);

        Assert.assertEquals("ExplainMergeAttempt is not responsible for connecting its new edges, so it should not change the sizes of any nodes.", 0d, target.getTotalSize(), ERROR_DELTA);
    }

    @Test
    public void testMergeToNewTarget() {
        arrows.add(new ExplainArrow(source.getName(), "newTarget", 1));

        merge();
        Assert.assertEquals(true, merge.isSuccess());
        Assert.assertEquals(1, merge.getNewTargets().size());
        final Node newTarget = Iterables.get(merge.getNewTargets().values(), 0);

        Assert.assertEquals(1, merge.getNewEdges().size());
        final Edge e = merge.getNewEdges().get(0);
        Assert.assertEquals(source, e.getSource());
        Assert.assertEquals(newTarget, e.getTarget());
        Assert.assertEquals(1d, e.getValue(), ERROR_DELTA);

        Assert.assertEquals("ExplainMergeAttempt is not responsible for connecting its new edges, so it should not change the sizes of any nodes.", 0d, newTarget.getTotalSize(), ERROR_DELTA);
    }

    @Test
    public void mergeShouldAddSourcesToChangedSources() {
        addArrowBetween(source, toOutside, 1);

        merge();
        Assert.assertEquals(true, merge.isSuccess());
        Assert.assertEquals(ImmutableSet.of(source), merge.getChangedSources());
    }

    @Test
    public void mergeShouldNotAddOutsideSourceToChangedSources() {
        addArrowBetween(fromOutside, toOutside, 1);
        merge();
        Assert.assertEquals(true, merge.isSuccess());
        Assert.assertEquals(ImmutableSet.of(), merge.getChangedSources());
    }

    private void merge() {
        merge = new ExplainMergeAttempt(fromOutside, toOutside, entry, sourceNodes, targetNodes);
    }

    private ExplainArrow addArrowBetween(final Node source, final Node target, final int count) {
        final ExplainArrow a = new ExplainArrow(source.getName(), target.getName(), count);
        arrows.add(a);
        return a;
    }
}
