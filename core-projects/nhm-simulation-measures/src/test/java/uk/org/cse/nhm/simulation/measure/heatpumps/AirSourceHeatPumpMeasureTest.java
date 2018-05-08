package uk.org.cse.nhm.simulation.measure.heatpumps;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.simulation.measure.heatpumps.AbstractHeatPumpMeasure.Hybrid;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

/**
 * Common code is tested by {@link HeatPumpMeasureTest}
 *
 * @author hinton
 *
 */
public class AirSourceHeatPumpMeasureTest {

    private MockDimensions dims;

    @Before
    public void setup() {
        this.dims = Util.getMockDimensions();
    }

    @Test
    public void testUnsuitableIfCommunityHeatingPresent() {
        final BuiltFormType suitableBuiltFormType = BuiltFormType.Detached;
        final ITechnologyModel technologies = new TechnologyModelImpl() {
        };
        Util.setupCommunityHeating(technologies);
        final IComponentsScope components = Util.mockComponents(dims, new StructureModel(suitableBuiltFormType), technologies);
        final GroundSourceHeatPumpMeasure groundSourceHeatPumpMeasure
                = new GroundSourceHeatPumpMeasure(
                        dims.time,
                        Util.mockWetHeatingMeasureFactory(),
                        dims.technology,
                        dims.structure,
                        new TechnologyOperations(),
                        Util.mockSizingFunction(Optional.of(1000d)),
                        null,
                        null,
                        null,
                        new ConstantComponentsFunction<Number>(null, 1),
                        50,
                        new ConstantComponentsFunction<Number>(null, 110),
                        0, FuelType.ELECTRICITY,
                        Optional.<Hybrid>absent());

        Assert.assertFalse(groundSourceHeatPumpMeasure.isSuitable(components, ILets.EMPTY));
    }
}
