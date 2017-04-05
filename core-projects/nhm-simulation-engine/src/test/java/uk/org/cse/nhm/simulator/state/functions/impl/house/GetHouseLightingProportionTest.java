package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;

import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

@RunWith(MockitoJUnitRunner.class)
public class GetHouseLightingProportionTest {

	@Mock IDimension<ITechnologyModel> techDimension;
    @Mock IComponentsFunction<Number> min;
	@Mock IComponentsFunction<Number> max;
	
    @Test
	public void returnsLightingEfficiencyIfEqualsMin() throws Exception {
//		GetHouseLightingProportion measure = new GetHouseLightingProportion(min,max,techDimension);
//		
//		
//		ILight lowEnergy = mock(ILight.class);
//		when(lowEnergy.getEfficiency()).thenReturn(0.5d);
//		when(lowEnergy.getProportion()).thenReturn(0.5d);
//		
//		ILight standard = mock(ILight.class);
//		when(standard.getEfficiency()).thenReturn(1d);
//		when(standard.getProportion()).thenReturn(0.5d);
//				
//		EList<ILight> lights = new BasicEList<ILight>(ImmutableList.<ILight>builder()
//				.add(lowEnergy)
//				.add(standard)
//				.build());
//
//		Double proportion = measure.getProportionOfLightingOfGivenEfficiency(lights, Range.closed(0.5, 0.7));
//		assertEquals(0.5d, proportion, 0d);
//		
//		proportion = measure.getProportionOfLightingOfGivenEfficiency(lights, Range.closed(0.5d, 1d));
//		assertEquals(1d, proportion, 0d);
		
	}
	
}
