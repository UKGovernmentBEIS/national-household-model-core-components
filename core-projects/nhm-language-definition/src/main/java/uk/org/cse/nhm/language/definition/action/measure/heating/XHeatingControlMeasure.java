package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.bool.house.XHasHeatingControl;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Bind("measure.heating-control")
@Doc("Installs a new heating control into a house")
@Unsuitability({
	"None of the house's heating systems (primary or secondary) (a) do not have the type of control AND (b) are suitable for the type of control.",
	"Details of control suitability for different systems are given in the documentation for control types."
})
@SeeAlso(XHasHeatingControl.class)
public class XHeatingControlMeasure extends XMeasure {

    public static final class P {

        public static final String type = "type";
        public static final String capex = "capex";
    }

    @Doc({
        "A type of heating system control; the presence or absence of heating controls",
        "affects a range of values within the SAP calculation, mostly around the mean",
        "temperature in the house."
    })
    public enum XHeatingControlType {
        @Doc("Suitable for wet central heating, warm air systems, and room heaters (including secondary heaters)")
        ApplianceThermostat,
        @Doc("Suitable for wet central heating and warm air systems")
        RoomThermostat,
        @Doc("Suitable for wet central heating and warm air systems")
        ThermostaticRadiatorValve,
        @Doc("Suitable for wet central heating and warm air systems (unless connected to a community heat source)")
        TimeTemperatureZoneControl,
        @Doc("Suitable for wet central heating and warm air systems (unless connected to a community heat source)")
        DelayedStartThermostat,
        @Doc("Suitable for wet central heating and warm air systems")
        Programmer,
        @Doc("Suitable for wet central heating")
        BypassValve
    }

    private XHeatingControlType type = XHeatingControlType.Programmer;
    private XNumber capex = new XNumberConstant();

    @Prop(P.type)
    @BindNamedArgument
    @Doc("The type of heating control to install")
    public XHeatingControlType getType() {
        return type;
    }

    public void setType(final XHeatingControlType type) {
        this.type = type;
    }

    @Prop(P.capex)
    @BindNamedArgument
    @Doc("A function for computing the capital cost of installing the heating control")
    public XNumber getCapex() {
        return capex;
    }

    public void setCapex(final XNumber capex) {
        this.capex = capex;
    }
}
