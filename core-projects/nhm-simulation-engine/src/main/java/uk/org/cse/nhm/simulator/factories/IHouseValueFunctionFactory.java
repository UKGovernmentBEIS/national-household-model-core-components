package uk.org.cse.nhm.simulator.factories;

import java.util.List;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XAdjustNumberOfAirChangeDevices;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XDisease;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XImpact;
import uk.org.cse.nhm.language.definition.function.health.XPermeabilityFunction;
import uk.org.cse.nhm.language.definition.function.health.XSITFunction;
import uk.org.cse.nhm.language.definition.function.health.XSITRebateFunction;
import uk.org.cse.nhm.language.definition.function.house.*;
import uk.org.cse.nhm.language.definition.function.house.XNumberOfAirChangeDevices.P;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.health.HealthImpactFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.health.PermeabilityFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.health.SITFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.health.SITRebateFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.house.*;

public interface IHouseValueFunctionFactory {

    public GetTariff getGetTariff(final FuelType fuelType);

    public GetMethodOfPayment createMethodOfPayment(final FuelType fuelType);

    @Adapt(XSITFunction.class)
    public SITFunction getSitFunction();

    @Adapt(XBuildYear.class)
    public GetBuildYear getGetBuildYear();

    @Adapt(XBuildYear2.class)
    public GetBuildYear getGetBuildYear2();

    @Adapt(XBuiltForm.class)
    public GetBuiltForm getGetBuiltForm();

    @Adapt(XMainHeatingFuel2.class)
    public GetMainHeatingFuel getGetMainHeatingFuel(@Prop("of") Optional<XHeatingSystem> system);

    @Adapt(XMainHeatingFuel.class)
    public OldGetMainHeatingFuel getOldGetMainHeatingFuel();

    @Adapt(XMorphology.class)
    public GetMorphology getGetMorphology();

    @Adapt(XRegion.class)
    public GetRegion getGetRegion();

    @Adapt(XTenure.class)
    public GetTenure getGetTenure();

    @Adapt(XPredominantWallType.class)
    public GetPredominantWallType getGetPredominantWallType();

    @Adapt(XSAPAgeBand.class)
    public GetSAPAgeBand getAgeBandFunction(@Prop(XSAPAgeBand.P.year) int year);

    @Adapt(XFloorConstructionType.class)
    public GetFloorConstructionType getFloorConstructionType();

    @Adapt(XRoofConstructionType.class)
    public GetRoofConstructionType getRoofConstructionType();

    @Adapt(XNumberOfOccupants.class)
    public GetNumberOfOccupants getNumberOfOccupants();

    @Adapt(XHouseholdIncome.class)
    public GetHouseholdIncome getHouseholdIncome();

    @Adapt(XNumberOfBedrooms.class)
    public GetNumberOfBedrooms getNumberOfBedrooms();

    @Adapt(XTotalFloorArea.class)
    public GetTotalFloorArea getTotalFloorArea();

    @Adapt(XNumberOfAirChangeDevices.class)
    public GetNumberOfAirChangeDevices getNumberOfAirChangeDevices(
            @Assisted @Prop(P.airChangeDevice) XAdjustNumberOfAirChangeDevices.XAirChangeDevice device);

    @Adapt(XVolume.class)
    public GetVolume getGetVolume();

    @Adapt(XAgeOfHeatingSystem.class)
    public GetAgeOfHeatingSystem getAgeOfHeatingSystem();

    @Adapt(XSurveyCode.class)
    public SurveyCodeFunction createSurveyCode();

    @Adapt(XMainHeatingSystemType.class)
    public GetMainHeatingSystemType getMainHeatingSystemType();

    @Adapt(XGetProportionOfDoubleGlazedWindows.class)
    public GetProportionOfDoubleGlazedWindows getProportionOfDoubleGlazedWindows();

    public GetInsolation getGetInsolation(@Assisted("orientation") final double orientation,
            @Assisted("inclination") final double inclination);

    @Adapt(XRoofArea.class)
    public RoofAreaFunction createRoofAreaFunction(
            @Prop(XRoofArea.P.pitchCorrection)
            @Assisted boolean pitchCorrection);

    @Adapt(XSurfaceArea.class)
    public GetSurfaceArea getGetSurfaceArea();

    @Adapt(XPermeabilityFunction.class)
    public PermeabilityFunction getPermeabilityFunction(
            @Prop(XPermeabilityFunction.P.includeDeliberate)
            @Assisted final Boolean includeDeliberate);

    @Adapt(XHealthImpactFunction.class)
    public HealthImpactFunction createHealthImpactFunction(
            @Prop(XHealthImpactFunction.P.fromTemperature)
            @Assisted("t1") final IComponentsFunction<Number> e1,
            @Prop(XHealthImpactFunction.P.toTemperature)
            @Assisted("t2") final IComponentsFunction<Number> e2,
            @Prop(XHealthImpactFunction.P.fromH)
            @Assisted("h1") final IComponentsFunction<Number> h1,
            @Prop(XHealthImpactFunction.P.toH)
            @Assisted("h2") final IComponentsFunction<Number> h2,
            @Prop(XHealthImpactFunction.P.fromPermeability)
            @Assisted("p1") final IComponentsFunction<Number> p1,
            @Prop(XHealthImpactFunction.P.toPermeability)
            @Assisted("p2") final IComponentsFunction<Number> p2,
            @Prop(XHealthImpactFunction.P.fromG)
            @Assisted("g1") final IComponentsFunction<Number> g1,
            @Prop(XHealthImpactFunction.P.toG)
            @Assisted("g2") final IComponentsFunction<Number> g2,
            @Prop(XHealthImpactFunction.P.toYear)
            @Assisted("horizon") final IComponentsFunction<Number> horizon,
            @Prop(XHealthImpactFunction.P.fromYear)
            @Assisted("offset") final IComponentsFunction<Number> offset,
            @Prop(XHealthImpactFunction.P.hadTrickleVents)
            @Assisted("had-vents") final IComponentsFunction<Boolean> hadVents,
            @Prop(XHealthImpactFunction.P.hadExtractFans)
            @Assisted("had-fans") final IComponentsFunction<Boolean> hadFans,
            @Prop(XHealthImpactFunction.P.hasTrickleVents)
            @Assisted("has-vents") final IComponentsFunction<Boolean> vents,
            @Prop(XHealthImpactFunction.P.hasExtractFans)
            @Assisted("has-fans") final IComponentsFunction<Boolean> fans,
            @Prop(XHealthImpactFunction.P.diseases)
            @Assisted final List<XDisease> diseases,
            @Prop(XHealthImpactFunction.P.impact)
            @Assisted final XImpact impact
    );

    @Adapt(XSITRebateFunction.class)
    public SITRebateFunction createRebate(
            @Prop(XSITRebateFunction.P.temperature)
            @Assisted("temperature")
            final IComponentsFunction<Number> temperature,
            @Prop(XSITRebateFunction.P.rebate)
            @Assisted("rebate")
            final IComponentsFunction<Number> rebate
    );

    public GetHouseLightingProportion getGetHouseLightingProportion(
            @Assisted List<LightType> lightTypes);

}
