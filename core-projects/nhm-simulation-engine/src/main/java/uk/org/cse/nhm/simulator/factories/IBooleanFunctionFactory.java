package uk.org.cse.nhm.simulator.factories;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.definition.function.bool.house.*;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.house.*;
import uk.org.cse.nhm.simulator.state.functions.impl.logic.LogicalCombination;
import uk.org.cse.nhm.simulator.state.functions.impl.logic.MatchSimYear;

/**
 * Factory for test functions.
 * 
 * @author hinton
 *
 */
public interface IBooleanFunctionFactory {
	// logical combinator
	public LogicalCombination combine(final LogicalCombination.Mode mode, final List<? extends IComponentsFunction<Boolean>> inputs);
	
	// house property matching functions
	public MatchTenure matchTenure(final TenureType tenure);
	public MatchMorphology matchMorphology(final MorphologyType morphology);
	public MatchAge matchAge(final Predicate<Integer> test);
	public MatchBuildYear matchBuildYear(final Predicate<Integer> test);
	public MatchBuiltForm matchBuiltForm(final BuiltFormType builtForm);
	public MatchMainHeatingFuel matchMainHeatingFuel(final FuelType fuel);
	public MatchRegion matchRegion(final RegionType region);
	public MatchExternalWalls matchSomeWalls(final Predicate<WallConstructionType> predicateMatching,final Predicate<Set<WallInsulationType>> predicateMatching2, final boolean all);

	public MatchSimYear matchSimYear(Optional<XForesightLevel> foresight, final Predicate<Integer> rangeTest);

	public MatchFlag matchFlag(final String named, final boolean b);

    @Adapt(XFlagsAre.class)
	public MatchMultipleFlags matchFlags(@Prop(XFlagsAre.P.match) final List<Glob> globs);

    @Adapt(XSuitableFor.class)
	public SuitabilityTest suitableFor(@Prop(XSuitableFor.P.FOR) final IComponentsAction action);

	public MatchLoftInsulationThickness matchLoftInsulationThickness(final Predicate<Integer> test);

	public MatchNumberFunction matchNumberFunction(final IComponentsFunction<Number> delegate, final Predicate<Double> rangeTest);

    @Adapt(XHouseIsOnTariff.class)
	public MatchTariff isOnTariff(@Prop(XHouseIsOnTariff.P.tariff) final ITariff tariff);

    @Adapt(XHasBoiler.class)
	public MatchBoiler hasBoiler();

    @Adapt(XOnGas.class)
	public MatchOnGas isOnGas();

    @Adapt(XHasCentralHeating.class)
	public MatchHasCentralHeating hasCentralHeating(
        @Prop(XHasCentralHeating.P.includeBroken)
        @Assisted final Boolean includeBroken);
    
    @Adapt(XDependsOnSecondaryHeating.class)
	public MatchDependsOnSecondaryHeating dependsOnSecondaryHeating();

    @Adapt(XHasLoft.class)
	public MatchHasLoft hasLoft();

    @Adapt(XHasHotWaterCylinder.class)
	public MatchHasHotWaterCylinder hasHotWaterCylinder();

    @Adapt(XHasInsulatedHotWaterCylinder.class)
    public MatchHasInsulateHotWaterCylinder hasInsulatedHotWaterCylinder();

    @Adapt(XHasSolarPV.class)
    public MatchSolarPV hasSolarPV();
    
    @Adapt(XHasSolarThermal.class)
    public MatchSolarThermal hasSolarThermal();
    
    public MatchHasHeatingControl createHasHeatingControlFunction(final HeatingSystemControlType controlType);
    
}
