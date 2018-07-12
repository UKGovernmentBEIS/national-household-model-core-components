package uk.org.cse.nhm.simulation.measure.heatpumps;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.energycalculator.api.types.BuiltFormType;
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
public class GroundSourceHeatPumpMeasureTest {

    protected MockDimensions dims;

    GroundSourceHeatPumpMeasure rightSize;

    StructureModel structure;
    ITechnologyModel technologies;
    IComponentsScope components;

    @Before
    public void setup() {
        dims = Util.getMockDimensions();
        final BuiltFormType suitableBuiltFormType = BuiltFormType.Bungalow;
        structure = new StructureModel(suitableBuiltFormType) {
            {
                setBackPlotDepth(10.0);
                setBackPlotWidth(1.0);
                setFrontPlotDepth(20.0);
                setFrontPlotWidth(1.0);
            }
        };
        technologies = new TechnologyModelImpl() {
        };

        components = Util.mockComponents(dims, structure, technologies);

        rightSize = new GroundSourceHeatPumpMeasure(
                dims.time,
                Util.mockWetHeatingMeasureFactory(),
                dims.technology,
                dims.structure, new TechnologyOperations(),
                Util.mockSizingFunction(Optional.of(1000d)),
                null,
                null,
                null,
                new ConstantComponentsFunction<Number>(null, 1d),
                1d,
                new ConstantComponentsFunction<Number>(null, 1d),
                15d,
                FuelType.ELECTRICITY,
                Optional.<Hybrid>absent());

    }

    @Test
    public void testUnsuitableIfNotEnoughSpace() {
        final GroundSourceHeatPumpMeasure tooBig = new GroundSourceHeatPumpMeasure(
                dims.time,
                Util.mockWetHeatingMeasureFactory(),
                dims.technology,
                dims.structure, new TechnologyOperations(),
                Util.mockSizingFunction(Optional.of(1000d)),
                null,
                null,
                null,
                new ConstantComponentsFunction<Number>(null, 1d),
                1d,
                new ConstantComponentsFunction<Number>(null, 1d),
                21.0,
                FuelType.ELECTRICITY,
                Optional.<Hybrid>absent());

        Assert.assertFalse(tooBig.isSuitable(components, ILets.EMPTY));
    }

    @Test
    public void testSuitableFalseIfFlatTrueForOtherBuiltFormTypes() {

        for (final BuiltFormType bft : BuiltFormType.values()) {
            structure = new StructureModel(bft) {
                {
                    setBackPlotDepth(10.0);
                    setBackPlotWidth(1.0);
                    setFrontPlotDepth(20.0);
                    setFrontPlotWidth(1.0);
                }
            };
            when(components.get(dims.structure)).thenReturn(structure);
            Assert.assertEquals(!bft.isFlat(), rightSize.isSuitable(components, ILets.EMPTY));
        }
    }

    @Test
    public void testUnsuitableIfCommunalHeating() {

        Util.setupCommunityHeating(technologies);
        Assert.assertFalse(rightSize.isSuitable(components, ILets.EMPTY));
    }

}
