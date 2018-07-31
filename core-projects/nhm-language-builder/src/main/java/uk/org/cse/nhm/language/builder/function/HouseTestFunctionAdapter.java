package uk.org.cse.nhm.language.builder.function;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
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
import uk.org.cse.nhm.language.definition.function.bool.house.*;
import uk.org.cse.nhm.language.definition.action.measure.heating.XHeatingControlMeasure;
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

    @Adapt(XHasHeatingControl.class)
    public IComponentsFunction<Boolean> buildMatchHasHeatingControl(@Prop(XHasHeatingControl.P.type) final XHeatingControlMeasure.XHeatingControlType type) {

        HeatingSystemControlType internalType;
        switch (type) {
            case ApplianceThermostat:
                internalType = HeatingSystemControlType.APPLIANCE_THERMOSTAT;
                break;
            case BypassValve:
                internalType = HeatingSystemControlType.BYPASS;
                break;
            case DelayedStartThermostat:
                internalType = HeatingSystemControlType.DELAYED_START_THERMOSTAT;
                break;
            case Programmer:
                internalType = HeatingSystemControlType.PROGRAMMER;
                break;
            case RoomThermostat:
                internalType = HeatingSystemControlType.ROOM_THERMOSTAT;
                break;
            case ThermostaticRadiatorValve:
                internalType = HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE;
                break;
            case TimeTemperatureZoneControl:
                internalType = HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL;
                break;
            default:
                throw new IllegalArgumentException("Unknown heating system control type " + type);
        }

        return testFunctions.createHasHeatingControlFunction(internalType);
    }
}
