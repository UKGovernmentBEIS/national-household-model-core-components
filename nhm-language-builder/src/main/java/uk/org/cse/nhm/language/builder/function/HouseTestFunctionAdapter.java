package uk.org.cse.nhm.language.builder.function;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.enums.XBuiltFormType;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XMorphologyType;
import uk.org.cse.nhm.language.definition.enums.XRegionType;
import uk.org.cse.nhm.language.definition.enums.XTenureType;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.language.definition.enums.XWallInsulationRule;
import uk.org.cse.nhm.language.definition.function.bool.house.XAgeIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XAnyWall;
import uk.org.cse.nhm.language.definition.function.bool.house.XAnyWalls;
import uk.org.cse.nhm.language.definition.function.bool.house.XBuildYearIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XBuiltFormIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XDoubleIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XEveryWall;
import uk.org.cse.nhm.language.definition.function.bool.house.XIntegerIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XLoftInsulationThicknessIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XMainHeatingFuelIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XMorphologyIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XNumberFunctionIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XRegionIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XTenureIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XWallsTest;
import uk.org.cse.nhm.simulator.factories.IBooleanFunctionFactory;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Builder adapter for all the house test functions.
 *
 * @author hinton
 */
public class HouseTestFunctionAdapter extends ReflectingAdapter {
    final IBooleanFunctionFactory testFunctions;

    @Inject
    public HouseTestFunctionAdapter(final Set<IConverter> delegates, final IBooleanFunctionFactory testFunctions,
            final Set<IAdapterInterceptor> interceptors) {
        super(delegates, interceptors);
        this.testFunctions = testFunctions;
    }

    @Adapt(XMorphologyIs.class)
    public IComponentsFunction<Boolean> buildMorphologyIs(
            @Prop(XMorphologyIs.P.EQUAL_TO) final XMorphologyType morphologyType) {
        return testFunctions.matchMorphology(MapEnum.morphology(morphologyType));
    }

    @Adapt(XTenureIs.class)
    public IComponentsFunction<Boolean> buildTenureIs(
            @Prop(XTenureIs.P.EQUAL_TO) final XTenureType tenureType) {
        return testFunctions.matchTenure(MapEnum.tenure(tenureType));
    }

    @Adapt(XAgeIs.class)
    public IComponentsFunction<Boolean> buildAgeIs(
            @Prop(XIntegerIs.P.ABOVE) final Optional<Integer> above,
            @Prop(XIntegerIs.P.EXACTLY) final Optional<Integer> exactly,
            @Prop(XIntegerIs.P.BELOW) final Optional<Integer> below
            ) {
        return testFunctions.matchAge(new RangeTest(above, exactly, below));
    }

    @Adapt(XBuildYearIs.class)
    public IComponentsFunction<Boolean> buildYearIs(
            @Prop(XIntegerIs.P.ABOVE) final Optional<Integer> above,
            @Prop(XIntegerIs.P.EXACTLY) final Optional<Integer> exactly,
            @Prop(XIntegerIs.P.BELOW) final Optional<Integer> below
            ) {
        return testFunctions.matchBuildYear(new RangeTest(above, exactly, below));
    }

    @Adapt(XLoftInsulationThicknessIs.class)
    public IComponentsFunction<Boolean> buildInsulationThicknessIs(
            @Prop(XIntegerIs.P.ABOVE) final Optional<Integer> above,
            @Prop(XIntegerIs.P.EXACTLY) final Optional<Integer> exactly,
            @Prop(XIntegerIs.P.BELOW) final Optional<Integer> below
            ) {
        return testFunctions.matchLoftInsulationThickness(new RangeTest(above, exactly, below));
    }

    @Adapt(XBuiltFormIs.class)
    public IComponentsFunction<Boolean> buildBuiltFormIs(
            @Prop(XBuiltFormIs.P.EQUAL_TO) final XBuiltFormType tenureType) {
        return testFunctions.matchBuiltForm(MapEnum.builtForm(tenureType));
    }

    @Adapt(XMainHeatingFuelIs.class)
    public IComponentsFunction<Boolean> buildMainHeatingFuelIs(
            @Prop(XMainHeatingFuelIs.P.EQUAL_TO) final XFuelType fuelType) {
        return testFunctions.matchMainHeatingFuel(MapEnum.fuel(fuelType));
    }

    @Adapt(XRegionIs.class)
    public IComponentsFunction<Boolean> buildRegionIs(
            @Prop(XRegionIs.P.EQUAL_TO) final XRegionType regionType) {
        return testFunctions.matchRegion(MapEnum.region(regionType));
    }

    @Adapt(XAnyWalls.class)
    public IComponentsFunction<Boolean> buildAnyWalls(
            @Prop(XAnyWalls.P.WITH_CONSTRUCTION) final XWallConstructionTypeRule wallConstructionType,
            @Prop(XAnyWalls.P.WITH_INSULATION) final XWallInsulationRule insulation
            ) {
        return testFunctions.matchSomeWalls(
                MapWallTypes.getPredicateMatching(wallConstructionType),
                MapWallTypes.getPredicateMatching(insulation),
                false);
    }

    @Adapt(XAnyWall.class)
    public IComponentsFunction<Boolean> buildAnyWalls2(
            @Prop(XWallsTest.P.WITH_CONSTRUCTION) final XWallConstructionTypeRule construction,
            @Prop(XWallsTest.P.cavityInsulation) final Optional<Boolean> cavity,
            @Prop(XWallsTest.P.internalInsulation) final Optional<Boolean> internal,
            @Prop(XWallsTest.P.externalInsulation) final Optional<Boolean> external
            ) {
        return testFunctions.matchSomeWalls(
                MapWallTypes.getPredicateMatching(construction),
                MapWallTypes.getPredicateMatching(cavity, internal, external),
                false);
    }

    @Adapt(XEveryWall.class)
    public IComponentsFunction<Boolean> buildAllWalls(
            @Prop(XWallsTest.P.WITH_CONSTRUCTION) final XWallConstructionTypeRule construction,
            @Prop(XWallsTest.P.cavityInsulation) final Optional<Boolean> cavity,
            @Prop(XWallsTest.P.internalInsulation) final Optional<Boolean> internal,
            @Prop(XWallsTest.P.externalInsulation) final Optional<Boolean> external
            ) {
        return testFunctions.matchSomeWalls(
                MapWallTypes.getPredicateMatching(construction),
                MapWallTypes.getPredicateMatching(cavity, internal, external),
                true);
    }

    @Adapt(XNumberFunctionIs.class)
    public IComponentsFunction<Boolean> buildNumberFunctionIs(
            @Prop(XDoubleIs.P.ABOVE) final Optional<Double> above,
            @Prop(XDoubleIs.P.EXACTLY) final Optional<Double> exactly,
            @Prop(XDoubleIs.P.BELOW) final Optional<Double> below,
            @Prop(XNumberFunctionIs.P.number) final IComponentsFunction<Number> delegate
            ) {
        return testFunctions.matchNumberFunction(delegate, new DoubleRangeTest(above, exactly, below));
    }
}
