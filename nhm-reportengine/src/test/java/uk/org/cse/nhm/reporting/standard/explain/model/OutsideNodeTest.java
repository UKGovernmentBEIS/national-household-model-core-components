package uk.org.cse.nhm.reporting.standard.explain.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class OutsideNodeTest {

	@Test(expected = UnsupportedOperationException.class)
	public void toOutsideNodesDoNotHaveChildren() {
		new ToOutsideNode().addChildEdge(mock(Edge.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void fromOutsideNodesDoNotHaveParents() {
		new FromOutsideNode().addParentEdge(mock(Edge.class));
	}
	
	@Test
	public void fromOutsideNodeCalculatesSizeFromChildren() {
		Node n = new FromOutsideNode();
		Edge e = mock(Edge.class);
		when(e.getTarget()).thenReturn(mock(Node.class));
		when(e.getValue()).thenReturn(1d);
		n.addChildEdge(e);
		Assert.assertEquals(1d, n.getTotalSize(), 0.1);
	}

	@Test
	public void outsideNodesNeverAllocateSpaceToChildren() {
		Node o = new FromOutsideNode();
		Node i = new ToOutsideNode();

		Edge e = mock(Edge.class);
		when(e.getValue()).thenReturn(1d);
		List<Edge> edges = ImmutableList.of(e);

		Assert.assertEquals(0d, o.getRemainingSizeToAllocateToChildrenSpeculative(edges), 0.1);
		Assert.assertEquals(0d, i.getRemainingSizeToAllocateToChildrenSpeculative(edges), 0.1);
		Assert.assertEquals(0d, o.calcRemainingSizeToAllocateToChildren(), 0.1);
		Assert.assertEquals(0d, i.calcRemainingSizeToAllocateToChildren(), 0.1);
	}
}
