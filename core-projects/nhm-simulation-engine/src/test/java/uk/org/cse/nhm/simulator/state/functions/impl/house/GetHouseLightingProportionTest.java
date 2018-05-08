package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

@RunWith(MockitoJUnitRunner.class)
public class GetHouseLightingProportionTest {

    @Mock
    IDimension<ITechnologyModel> techDimension;
    @Mock
    IComponentsFunction<Number> min;
    @Mock
    IComponentsFunction<Number> max;

    @Mock
    ITechnologyModel techModel;
    @Mock
    IComponentsScope scope;
    @Mock
    ILets lets;

    EList<ILight> lights;
    @Mock
    ILight lowEnergy;

    @Before
    public void setUp() {
        when(lowEnergy.getType()).thenReturn(LightType.CFL);
        when(lowEnergy.getProportion()).thenReturn(0.75d);

        ILight standard = mock(ILight.class);
        when(standard.getType()).thenReturn(LightType.Incandescent);
        when(standard.getProportion()).thenReturn(0.25d);

        lights = new BasicEList<ILight>(ImmutableList.<ILight>builder()
                .add(lowEnergy)
                .add(standard)
                .build());

        when(scope.get(techDimension)).thenReturn(techModel);
        when(techModel.getLights()).thenReturn(lights);
    }

    @Test
    public void testComputeMethodWillReturnProportionForMaxMin() throws Exception {
        GetHouseLightingProportion measure = new GetHouseLightingProportion(Collections.singletonList(LightType.CFL), techDimension);
        Double result = measure.compute(scope, lets);
        assertEquals(0.75d, result.doubleValue(), 0d);
    }
}
