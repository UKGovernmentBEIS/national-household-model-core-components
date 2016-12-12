package uk.org.cse.nhm.simulator.factories;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
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
import uk.org.cse.nhm.language.definition.function.house.XRoofConstructionType;
import uk.org.cse.nhm.language.definition.function.house.XSAPAgeBand;
import uk.org.cse.nhm.language.definition.function.house.XSurveyCode;
import uk.org.cse.nhm.language.definition.function.house.XTenure;
import uk.org.cse.nhm.language.definition.function.house.XTotalFloorArea;
import uk.org.cse.nhm.language.definition.function.house.XVolume;
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
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetTariff;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetTenure;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetTotalFloorArea;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetVolume;
import uk.org.cse.nhm.simulator.state.functions.impl.house.OldGetMainHeatingFuel;
import uk.org.cse.nhm.simulator.state.functions.impl.house.SurveyCodeFunction;

public interface IHouseValueFunctionFactory {
	public GetTariff getGetTariff(final FuelType fuelType);
	public GetMethodOfPayment createMethodOfPayment(final FuelType fuelType);

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
}
