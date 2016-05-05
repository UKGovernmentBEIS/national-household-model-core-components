package uk.org.cse.nhm.simulator.groups;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import uk.org.cse.nhm.simulator.groups.impl.SetOperationDwellingGroups;
import uk.org.cse.nhm.simulator.groups.impl.SetOperationDwellingGroups.Operation;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class TestSetOperationGroups {    
    @Test
    public void testUnionHouseGroupUpdateResponse() {
    	final List<IDwellingGroup> sources = new ArrayList<IDwellingGroup>();
        
        final IDwelling firstInstance = mock(IDwelling.class);
        final IDwelling secondInstance = mock(IDwelling.class);
        final IDwelling thirdInstance = mock(IDwelling.class);
        final IDwelling fourthInstance = mock(IDwelling.class);
        
        final Set<IDwelling> oneAndTwo = new HashSet<IDwelling>();
        oneAndTwo.add(firstInstance);
        oneAndTwo.add(secondInstance);
        
        final Set<IDwelling> twoAndThree = new HashSet<IDwelling>();
        twoAndThree.add(secondInstance);
        twoAndThree.add(thirdInstance);
        
        final IDwellingGroup hig1 = mock(IDwellingGroup.class);
        when(hig1.getContents()).thenReturn(oneAndTwo);
        
        final IDwellingGroup hig2 = mock(IDwellingGroup.class);
        when(hig2.getContents()).thenReturn(twoAndThree);
        
        sources.add(hig1);
        sources.add(hig2);

        final SetOperationDwellingGroups unionGroup = new SetOperationDwellingGroups(sources, Operation.UNION);
        
        final ArgumentCaptor<IDwellingGroupListener> captor = ArgumentCaptor.forClass(IDwellingGroupListener.class);
        
        verify(hig1).addListener(captor.capture());
        
        final IDwellingGroupListener listener = captor.getValue();
        
        listener.dwellingGroupChanged(null, hig1, oneAndTwo, Collections.<IDwelling>emptySet());
        listener.dwellingGroupChanged(null, hig2, twoAndThree, Collections.<IDwelling>emptySet());
        
        Assert.assertTrue("Union contains 1, 2, 3 but not 4 (actual : " + unionGroup.getContents()+")", 
                unionGroup.getContents().contains(firstInstance) &&
                unionGroup.getContents().contains(secondInstance) &&
                unionGroup.getContents().contains(thirdInstance) &&
                !unionGroup.getContents().contains(fourthInstance));
        
        
        
        twoAndThree.remove(thirdInstance);
        
        listener.dwellingGroupChanged(null, hig2, Collections.<IDwelling>emptySet(), Collections.singleton(thirdInstance));
        
        Assert.assertTrue("Union contains 1, 2 but not 3, 4 (actual : " + unionGroup.getContents()+")", 
                unionGroup.getContents().contains(firstInstance) &&
                unionGroup.getContents().contains(secondInstance) &&
                !unionGroup.getContents().contains(thirdInstance) &&
                !unionGroup.getContents().contains(fourthInstance));
        
        twoAndThree.add(fourthInstance);
        
        listener.dwellingGroupChanged(null, hig2, Collections.singleton(fourthInstance), Collections.<IDwelling>emptySet());
        
        Assert.assertTrue("Union contains 1, 2, 4 but not 3 (actual : " + unionGroup.getContents()+")", 
                unionGroup.getContents().contains(firstInstance) &&
                unionGroup.getContents().contains(secondInstance) &&
                !unionGroup.getContents().contains(thirdInstance) &&
                unionGroup.getContents().contains(fourthInstance));
    }
    
    @Test
    public void testIntersectionHouseGroupUpdateResponse() {
        final List<IDwellingGroup> sources = new ArrayList<IDwellingGroup>();
        
        final IDwelling firstInstance = mock(IDwelling.class);
        final IDwelling secondInstance = mock(IDwelling.class);
        final IDwelling thirdInstance = mock(IDwelling.class);
        final IDwelling fourthInstance = mock(IDwelling.class);
        
        final Set<IDwelling> oneAndTwo = new HashSet<IDwelling>();
        oneAndTwo.add(firstInstance);
        oneAndTwo.add(secondInstance);
        
        final Set<IDwelling> twoAndThree = new HashSet<IDwelling>();
        twoAndThree.add(secondInstance);
        twoAndThree.add(thirdInstance);
        
        final IDwellingGroup hig1 = mock(IDwellingGroup.class);
        when(hig1.getContents()).thenReturn(oneAndTwo);
        
        final IDwellingGroup hig2 = mock(IDwellingGroup.class);
        when(hig2.getContents()).thenReturn(twoAndThree);
        
        sources.add(hig1);
        sources.add(hig2);

        final SetOperationDwellingGroups unionGroup = new SetOperationDwellingGroups(sources, Operation.INTERSECTION);
        
        final ArgumentCaptor<IDwellingGroupListener> captor = ArgumentCaptor.forClass(IDwellingGroupListener.class);
        
        verify(hig1).addListener(captor.capture());
        
        final IDwellingGroupListener listener = captor.getValue();
        
        listener.dwellingGroupChanged(null, hig1, oneAndTwo, Collections.<IDwelling>emptySet());
        listener.dwellingGroupChanged(null, hig2, twoAndThree, Collections.<IDwelling>emptySet());
        
        Assert.assertTrue("Union contains 2 (actual : " + unionGroup.getContents()+")", 
                !unionGroup.getContents().contains(firstInstance) &&
                unionGroup.getContents().contains(secondInstance) &&
                !unionGroup.getContents().contains(thirdInstance) &&
                !unionGroup.getContents().contains(fourthInstance));
        
        
        twoAndThree.remove(secondInstance);
        
        listener.dwellingGroupChanged(null, hig2, Collections.<IDwelling>emptySet(), Collections.singleton(secondInstance));
        
        Assert.assertTrue("Empty (actual : " + unionGroup.getContents()+")", 
                !unionGroup.getContents().contains(firstInstance) &&
                !unionGroup.getContents().contains(secondInstance) &&
                !unionGroup.getContents().contains(thirdInstance) &&
                !unionGroup.getContents().contains(fourthInstance));
        
        twoAndThree.add(fourthInstance);
        
        oneAndTwo.add(fourthInstance);
        
        listener.dwellingGroupChanged(null, hig2, Collections.singleton(fourthInstance), Collections.<IDwelling>emptySet());
        listener.dwellingGroupChanged(null, hig1, Collections.singleton(fourthInstance), Collections.<IDwelling>emptySet());
        
        
        Assert.assertTrue("Intersection contains only 4 (actual : " + unionGroup.getContents()+")", 
                !unionGroup.getContents().contains(firstInstance) &&
                !unionGroup.getContents().contains(secondInstance) &&
                !unionGroup.getContents().contains(thirdInstance) &&
                unionGroup.getContents().contains(fourthInstance));
    }
    
    @Test
    public void testDifferenceHouseGroupUpdateResponse() {
        final List<IDwellingGroup> sources = new ArrayList<IDwellingGroup>();
        
        final IDwelling firstInstance = mock(IDwelling.class);
        final IDwelling secondInstance = mock(IDwelling.class);
        final IDwelling thirdInstance = mock(IDwelling.class);
        final IDwelling fourthInstance = mock(IDwelling.class);
        
        final Set<IDwelling> oneAndTwo = new HashSet<IDwelling>();
        oneAndTwo.add(firstInstance);
        oneAndTwo.add(secondInstance);
        
        final Set<IDwelling> twoAndThree = new HashSet<IDwelling>();
        twoAndThree.add(secondInstance);
        twoAndThree.add(thirdInstance);
        
        final IDwellingGroup hig1 = mock(IDwellingGroup.class);
        when(hig1.getContents()).thenReturn(oneAndTwo);
        
        final IDwellingGroup hig2 = mock(IDwellingGroup.class);
        when(hig2.getContents()).thenReturn(twoAndThree);
        
        sources.add(hig1);
        sources.add(hig2);

        final SetOperationDwellingGroups unionGroup = new SetOperationDwellingGroups(sources, Operation.DIFFERENCE);
        
        final ArgumentCaptor<IDwellingGroupListener> captor = ArgumentCaptor.forClass(IDwellingGroupListener.class);
        
        verify(hig1).addListener(captor.capture());
        
        final IDwellingGroupListener listener = captor.getValue();
        
        final ArgumentCaptor<IDwellingGroupListener> captor2 = ArgumentCaptor.forClass(IDwellingGroupListener.class);
        
        verify(hig2).addListener(captor2.capture());
        
        final IDwellingGroupListener listener2 = captor2.getValue();

        listener.dwellingGroupChanged(null, hig1, oneAndTwo, Collections.<IDwelling>emptySet());
        listener2.dwellingGroupChanged(null, hig2, twoAndThree, Collections.<IDwelling>emptySet());
        
        Assert.assertTrue("Diff contains only element 1 : " + unionGroup.getContents()+")", 
                unionGroup.getContents().contains(firstInstance) &&
                !unionGroup.getContents().contains(secondInstance) &&
                !unionGroup.getContents().contains(thirdInstance) &&
                !unionGroup.getContents().contains(fourthInstance));
        
        twoAndThree.remove(secondInstance);
        
        listener2.dwellingGroupChanged(null, hig2, Collections.<IDwelling>emptySet(), Collections.singleton(secondInstance));
        
        Assert.assertTrue(firstInstance + " and " + secondInstance + " (actual : " + unionGroup.getContents()+")", 
                unionGroup.getContents().contains(firstInstance) &&
                unionGroup.getContents().contains(secondInstance) &&
                !unionGroup.getContents().contains(thirdInstance) &&
                !unionGroup.getContents().contains(fourthInstance));
    }
}
