package uk.org.cse.nhm.simulation.measure.lighting;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

@RunWith(MockitoJUnitRunner.class)
public class LightingProportionMeasureTest {

	@Mock private ISettableComponentsScope scope;
	@Mock private ILets lets;
	@Mock private IDimension<ITechnologyModel> techDimension;
	@Mock private IDimension<StructureModel> strucDimension;
	
	@Mock private IComponentsFunction<Number> proportionOfCfl;
	@Mock private IComponentsFunction<Number> proportionOfIcandescent;
	@Mock private IComponentsFunction<Number> propotionOfHAL;
	@Mock private IComponentsFunction<Number> proportionOfLED;
		
	@Mock private ITechnologyModel techModel;
	@Mock private EList<ILight> lights;
	
	@Test
	public void allExistingLightingProportionsAreReplaced() throws Exception {
		MockDimensions dims = new MockDimensions();
		final LightingProportionMeasure measure = new LightingProportionMeasure(proportionOfCfl,
				proportionOfIcandescent, propotionOfHAL, proportionOfLED, techDimension, strucDimension);
		
		when(scope.get(techDimension)).thenReturn(techModel);
		when(techModel.getLights()).thenReturn(lights);
		
		measure.apply(scope, lets);
		
		verify(techModel, times(1)).getLights();
		verify(lights, times(1)).clear();
	}
}