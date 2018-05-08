package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

@Doc({
    "Get the specific heat loss computed by the energy calculator for this house.",
    "This is the number of watts required to keep the house warm per degree of",
    "temperature difference between the inside and the outside.",
    "It is composed of the radiative heat loss (the sum of products of u-value and area",
    "for external surfaces) and the ventilation heat loss (the heat lost due to air changes",
    "through flues, chimneys, permeable surfaces and so on).",
    "The SAP notion of the specific heat loss <emphasis>parameter</emphasis> is the ratio between",
    "the specific heat loss and the total floor area of the house."
})
@Bind("house.heat-loss")
@SeeAlso(XPeakHeatDemand.class)
public class XHeatLoss extends XHouseNumber {

    public static final class P {

        public static final String TYPE = "TYPE";
    }

    public enum XHeatLossType {
        Total,
        Fabric,
        Ventilation,
        ThermalBridging
    }

    private XHeatLossType type = XHeatLossType.Total;

    @Prop(P.TYPE)
    @Doc({"Specifies which contributory term to the overall heat loss should be produced."})
    @BindPositionalArgument(0)
    public XHeatLossType getType() {
        return type;
    }

    public void setType(XHeatLossType type) {
        this.type = type;
    }

}
