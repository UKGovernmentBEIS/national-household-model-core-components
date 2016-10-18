package uk.org.cse.nhm.reporting.standard.explain.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class NodeTest {
	private Edge eSize1;
	private Node n;

	@Before
	public void setUp() {
		n = new Node("test");
		eSize1 = mock(Edge.class);
		when(eSize1.getValue()).thenReturn(1d);
	}

	@Test
	public void totalSizeShoudlBeDeterminedByParentEdges() {
		Assert.assertEquals(0d, n.getTotalSize(), 0.1);

		n.addParentEdge(eSize1);
		Assert.assertEquals(1d, n.getTotalSize(), 0.1);
	}

	@Test
	public void remainingSizeShouldBeDeterminedByParentEdgesMinusChildEdges() {
		n.addParentEdge(eSize1);
		n.addChildEdge(eSize1);

		Assert.assertEquals(0d, n.calcRemainingSizeToAllocateToChildren(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addingChildWhichWouldMakeRemainingSizeNegativeShouldNotBePossible() {
		when(eSize1.getTarget()).thenReturn(mock(Node.class));
		n.addChildEdge(eSize1);
	}

	@Test
	public void remainingSizeSpeculativeShouldIgnoreEdgesSourcedForOtherNodes() {
		Assert.assertEquals(0d, n.getRemainingSizeToAllocateToChildrenSpeculative(ImmutableList.of(eSize1)), 0.1);
	}

	@Test
	public void remainingSizeSpeculativeShouldBeDeterminedByParentEdgesMinusChildEdgesMinusNewPotentialChildEdges() {
		n.addParentEdge(eSize1);
		n.addParentEdge(eSize1);
		n.addChildEdge(eSize1);

		when(eSize1.getSource()).thenReturn(n);
		Assert.assertEquals(0d, n.getRemainingSizeToAllocateToChildrenSpeculative(ImmutableList.of(eSize1)), 0.1);
	}
}
