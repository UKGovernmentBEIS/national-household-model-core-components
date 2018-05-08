package uk.org.cse.nhm.language.definition.action.measure.heating;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.base.Preconditions;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Bind("measure.heat-pump")
@Doc({"Installs a heat pump; the heat pump may be a hybrid heat pump if the hybrid-fuel argument is supplied.",
    "The model assumes that if the heat pump is hybrid, all hot water demand is met using the hybrid-fuel at the hybrid-efficiency.",
    "The proportion of space heat demand met using the heat pump is determined by the hybrid-ratio value in each month."
})
@Unsuitability({
    "heat pump is air source AND the dwelling has existing community space heating",
    "heat pump is ground source AND the dwelling has existing community space heating",
    "heat pump is ground source AND the dwelling is a flat",
    "heat pump is ground source AND there is insufficient exterior area (required-exterior-area)"
})
public class XHeatPumpMeasure extends XWetHeatingMeasure {

    public enum XHeatPumpType {
        GroundSource,
        AirSource
    }

    public static final class P {

        public static final String type = "type";
        public static final String cop = "cop";
        public static final String cylinderVolume = "cylinderVolume";
        public static final String cylinderInsulation = "cylinderInsulation";
        public static final String fuel = "fuel";
        public static final String hybridFuel = "hybridFuel";
        public static final String hybridEfficiency = "hybridEfficiency";
        public static final String hybridRatio = "hybridRatio";
        public static final String requiredExteriorArea = "requiredExteriorArea";
    }

    private XFuelType fuel = XFuelType.Electricity;

    private XHeatPumpType type = XHeatPumpType.AirSource;
    private XNumber cop = XNumberConstant.create(2.5);

    private XFuelType hybridFuel = null;
    private XNumber hybridEfficiency = XNumberConstant.create(0.85);
    private List<Double> hybridRatio = ones();

    private double requiredExteriorArea = 0;

    @BindNamedArgument("required-exterior-area")
    @Prop(P.requiredExteriorArea)
    @Doc("The heat pump will only be suitable for houses with at least this exterior plot area")
    public double getRequiredExteriorArea() {
        return requiredExteriorArea;
    }

    public void setRequiredExteriorArea(final double requiredExteriorArea) {
        this.requiredExteriorArea = requiredExteriorArea;
    }

    private static List<Double> ones() {
        final ArrayList<Double> out = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            out.add(1d);
        }
        return out;
    }

    private XNumber cylinderVolume = XNumberConstant.create(110);
    private double cylinderInsulation = 50;

    @BindNamedArgument("hybrid-efficiency")
    @Doc("If hybrid-fuel is given, this will be the efficiency with which the hybrid heater produces space heating or hot water.")
    @Prop(P.hybridEfficiency)
    public XNumber getHybridEfficiency() {
        return hybridEfficiency;
    }

    public void setHybridEfficiency(XNumber hybridEfficiency) {
        this.hybridEfficiency = hybridEfficiency;
    }

    @Prop(P.hybridRatio)
    @BindNamedArgument("hybrid-ratio")
    @Doc("If hybrid-fuel is given, then this should be the monthly proportions of energy produced by the heat pump part of the system (so 100% is all heat pump, 0% is all boiler module).")
    public List<Double> getHybridRatio() {
        return hybridRatio;
    }

    public void setHybridRatio(List<Double> hybridRatio) {
        this.hybridRatio = hybridRatio;
    }

    @Prop(P.hybridFuel)
    @BindNamedArgument("hybrid-fuel")
    @Doc("If set, the heat pump will be a hybrid, with some space heating and all water heating energy produced using the given fuel type.")
    public XFuelType getHybridFuel() {
        return hybridFuel;
    }

    public void setHybridFuel(XFuelType hybridFuel) {
        this.hybridFuel = hybridFuel;
    }

    @Prop(P.fuel)
    @BindNamedArgument("fuel")
    @Doc("The fuel for the heat pump")
    @NotNull(message = "measure.heat-pump should have a fuel: argument")
    public XFuelType getFuel() {
        return fuel;
    }

    public void setFuel(XFuelType fuel) {
        Preconditions.checkNotNull(fuel, "Fuel should never be set to null in heat pump");
        this.fuel = fuel;
    }

    @Prop(P.type)
    @BindNamedArgument
    @Doc("The type of heat pump")
    public XHeatPumpType getType() {
        return type;
    }

    public void setType(final XHeatPumpType type) {
        this.type = type;
    }

    @Prop(P.cop)
    @Doc("The heat pump's coefficient of performance")
    @BindNamedArgument
    public XNumber getCop() {
        return cop;
    }

    public void setCop(final XNumber cop) {
        this.cop = cop;
    }

    @Doc("The volume of the associated cylinder that will be installed (l)")
    @Prop(P.cylinderVolume)
    @BindNamedArgument("cylinder-volume")
    public XNumber getCylinderVolume() {
        return cylinderVolume;
    }

    public void setCylinderVolume(final XNumber cylinderVolume) {
        this.cylinderVolume = cylinderVolume;
    }

    @Doc("The insulation thickness of the associated cylinder that will be installed (mm)")
    @Prop(P.cylinderInsulation)
    @BindNamedArgument("cylinder-insulation")
    public double getCylinderInsulation() {
        return cylinderInsulation;
    }

    public void setCylinderInsulation(final double cylinderInsulation) {
        this.cylinderInsulation = cylinderInsulation;
    }
}
