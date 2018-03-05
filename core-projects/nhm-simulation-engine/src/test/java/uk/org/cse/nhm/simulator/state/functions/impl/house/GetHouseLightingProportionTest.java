package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;

import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

@RunWith(MockitoJUnitRunner.class)
public class GetHouseLightingProportionTest {

	@Mock IDimension<ITechnologyModel> techDimension;
    @Mock IComponentsFunction<Number> min;
	@Mock IComponentsFunction<Number> max;
	
	
	@Mock ITechnologyModel techModel;
	@Mock IComponentsScope scope;
	@Mock ILets lets;
	
	EList<ILight> lights;
	@Mock ILight lowEnergy;
	
    @Before
    public void setUp() {
        when(lowEnergy.getEfficiency()).thenReturn(ILight.CFL_EFFICIENCY);
        when(lowEnergy.getProportion()).thenReturn(0.75d);

        ILight standard = mock(ILight.class);
        when(standard.getEfficiency()).thenReturn(ILight.INCANDESCENT_EFFICIENCY);
        when(standard.getProportion()).thenReturn(0.25d);

        lights = new BasicEList<ILight>(ImmutableList.<ILight> builder()
                .add(lowEnergy)
                .add(standard)
                .build());

        when(scope.get(techDimension)).thenReturn(techModel);
        when(techModel.getLights()).thenReturn(lights);
    }
	
	
    @Test
	public void returnsLightingEfficiencyIfEqualsMin() throws Exception {
		GetHouseLightingProportion measure = new GetHouseLightingProportion(min,max,techDimension);
		Double proportion = measure.getProportionOfLightingOfGivenEfficiency(lights, Range.closed(3.40695, 3.40695));
		assertEquals("CFL", 0.75d, proportion, 0d);
	}
    
    @Test
    public void testComputeMethodWillReturnProportionForMaxMin() throws Exception {
        when(lowEnergy.getEfficiency()).thenReturn(ILight.BRE_CFL_EFFICIENCY);
        when(min.compute(scope, lets)).thenReturn(new Double(ILight.BRE_CFL_EFFICIENCY));
        when(max.compute(scope, lets)).thenReturn(new Double(ILight.BRE_CFL_EFFICIENCY));
                
        GetHouseLightingProportion measure = new GetHouseLightingProportion(min,max,techDimension);
        Double result = measure.compute(scope, lets);
        assertEquals(0.75d, result.doubleValue(), 0d);
    }
}
