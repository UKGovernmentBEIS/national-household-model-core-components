package uk.org.cse.nhm.simulation.measure.structure;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * AlterWallHeatLossMeasurureTest.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt $
 * @since 3.2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AlterWallHeatLossMeasureTest extends Mockito{
    private AlterWallHeatLossMeasure measure;
    
    @Mock
    private IComponentsScope components;
    
    @Mock
    private ISettableComponentsScope settableComponents;
    
    @Mock
    private IDimension<StructureModel> dimension;
    
    private final double expectedUValue = 1.00;
    
    @Before
    public void initialiseTests(){
       measure = new AlterWallHeatLossMeasure(dimension, expectedUValue);
    }
    
    @Test
    public void testIsSuitableOnlyReturnsTrueForStructureComponents() throws Exception {
    	        assertTrue("isSuitable should always return true", measure.isSuitable(components, ILets.EMPTY));        
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void testApplyModifiesUValueForEachWallInProperty() throws Exception {
		final StructureModel structureModel = mock(StructureModel.class);
        final Storey storey = mock(Storey.class);
        final IMutableWall wallToChange = mock(IMutableWall.class);
        
        final List<Storey> storyList = Arrays.asList(storey);
        final List<IMutableWall> walls = Arrays.asList(wallToChange);
        
        when(structureModel.getStoreys()).thenReturn(storyList);
        when(storey.getWalls()).thenReturn(walls);
        
        measure.apply(settableComponents, ILets.EMPTY);
        
        final ArgumentCaptor<IModifier> captor = ArgumentCaptor.forClass(IModifier.class);
        
        verify(settableComponents).modify(eq(dimension), captor.capture());
        
        for (final IModifier mod : captor.getAllValues()) {
        	mod.modify(structureModel);
        }
        
        verify(wallToChange).setUValue(expectedUValue);
    }
}
