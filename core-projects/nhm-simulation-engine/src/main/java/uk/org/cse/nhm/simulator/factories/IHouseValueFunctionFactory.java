package uk.org.cse.nhm.simulator.factories;

import java.util.List;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XDisease;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XImpact;
import uk.org.cse.nhm.language.definition.function.health.XSITFunction;
import uk.org.cse.nhm.language.definition.function.health.XSITRebateFunction;
import uk.org.cse.nhm.language.definition.function.house.XAgeOfHeatingSystem;
import uk.org.cse.nhm.language.definition.function.house.XBuildYear;
import uk.org.cse.nhm.language.definition.function.house.XBuildYear2;
import uk.org.cse.nhm.language.definition.function.house.XBuiltForm;
import uk.org.cse.nhm.language.definition.function.house.XFloorConstructionType;
import uk.org.cse.nhm.language.definition.function.house.XGetProportionOfDoubleGlazedWindows;
import uk.org.cse.nhm.language.definition.function.house.XHouseholdIncome;
import uk.org.cse.nhm.language.definition.function.house.XMainHeatingFuel;
import uk.org.cse.nhm.language.definition.function.house.XMainHeatingFuel2;
import uk.org.cse.nhm.language.definition.function.house.XMainHeatingSystemType;
import uk.org.cse.nhm.language.definition.function.house.XMorphology;
import uk.org.cse.nhm.language.definition.function.house.XNumberOfBedrooms;
import uk.org.cse.nhm.language.definition.function.house.XNumberOfOccupants;
import uk.org.cse.nhm.language.definition.function.house.XPredominantWallType;
import uk.org.cse.nhm.language.definition.function.house.XRegion;
import uk.org.cse.nhm.language.definition.function.house.XRoofArea;
import uk.org.cse.nhm.language.definition.function.house.XRoofConstructionType;
import uk.org.cse.nhm.language.definition.function.house.XSAPAgeBand;
import uk.org.cse.nhm.language.definition.function.house.XSurfaceArea;
import uk.org.cse.nhm.language.definition.function.house.XSurveyCode;
import uk.org.cse.nhm.language.definition.function.house.XTenure;
import uk.org.cse.nhm.language.definition.function.house.XTotalFloorArea;
import uk.org.cse.nhm.language.definition.function.house.XVolume;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.health.HealthImpactFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.health.SITFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.health.SITRebateFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetAgeOfHeatingSystem;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetBuildYear;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetBuiltForm;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetFloorConstructionType;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetHouseholdIncome;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetInsolation;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetMainHeatingFuel;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetMainHeatingSystemType;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetMethodOfPayment;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetMorphology;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetNumberOfBedrooms;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetNumberOfOccupants;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetPredominantWallType;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetProportionOfDoubleGlazedWindows;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetRegion;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetRoofConstructionType;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetSAPAgeBand;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetSurfaceArea;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetTariff;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetTenure;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetTotalFloorArea;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetVolume;
import uk.org.cse.nhm.simulator.state.functions.impl.house.OldGetMainHeatingFuel;
import uk.org.cse.nhm.simulator.state.functions.impl.house.RoofAreaFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.house.SurveyCodeFunction;

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
	public GetMainHeatingFuel getGetMainHeatingFuel();

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

    @Adapt(XHealthImpactFunction.class)
	public HealthImpactFunction createHealthImpactFunction(
        @Prop(XHealthImpactFunction.P.fromTemperature)
        @Assisted("t1") 	 final IComponentsFunction<Number> e1,
        @Prop(XHealthImpactFunction.P.toTemperature)
        @Assisted("t2") 	 final IComponentsFunction<Number> e2,

        @Prop(XHealthImpactFunction.P.fromH)
        @Assisted("h1") 	 final IComponentsFunction<Number> h1,
        @Prop(XHealthImpactFunction.P.toH)
        @Assisted("h2") 	 final IComponentsFunction<Number> h2,

        @Prop(XHealthImpactFunction.P.fromPermeability)
        @Assisted("p1") 	 final IComponentsFunction<Number> p1,
        @Prop(XHealthImpactFunction.P.toPermeability)
        @Assisted("p2") 	 final IComponentsFunction<Number> p2,

        @Prop(XHealthImpactFunction.P.fromG)
        @Assisted("g1") 	 final IComponentsFunction<Number> g1,
        @Prop(XHealthImpactFunction.P.toG)
        @Assisted("g2") 	 final IComponentsFunction<Number> g2,

        @Prop(XHealthImpactFunction.P.toYear)
        @Assisted("horizon") final IComponentsFunction<Number> horizon,
        @Prop(XHealthImpactFunction.P.fromYear)
        @Assisted("offset")  final IComponentsFunction<Number> offset,

        @Prop(XHealthImpactFunction.P.hasTrickleVents)
        @Assisted("vents")   final IComponentsFunction<Boolean> vents,
        @Prop(XHealthImpactFunction.P.hasExtractFans)
        @Assisted("fans")   final IComponentsFunction<Boolean> fans,

        @Prop(XHealthImpactFunction.P.diseases)
        @Assisted			 final List<XDisease> diseases,
        @Prop(XHealthImpactFunction.P.impact)
        @Assisted			 final XImpact impact
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
}
