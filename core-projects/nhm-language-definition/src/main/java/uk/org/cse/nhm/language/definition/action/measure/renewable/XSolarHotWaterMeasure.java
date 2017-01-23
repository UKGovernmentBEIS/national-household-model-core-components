package uk.org.cse.nhm.language.definition.action.measure.renewable;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Doc(
		{
			"Installs a solar DHW system in a house.",
			"The defaults for this measure are taken from the Cambridge Housing Model, and are the same as when creating a solar water heater in the stock import."
		}
	)
@Unsuitability({
	"dwelling has no remaining exposed roof area",
	"dwelling is a mid or ground-floor flat (top floor flats may have solar)",
	"dwelling has an existing solar water heater"
	})
@Bind("measure.solar-dhw")
public class XSolarHotWaterMeasure extends XMeasure {
	public static final class P {
		public static final String area = "area";
		public static final String cost = "cost";
        public static final String cylinderVolume = "cylinderVolume";
		public static final String lhlc = "linearHeatLossCoefficient";
        public static final String zle = "zeroLossEfficiency";
	}

	private XNumber area = XNumberConstant.create(3d);
	private XNumber cost = XNumberConstant.create(0d);
	private XNumber cylinderVolume = XNumberConstant.create(50d);
    private XNumber zeroLossEfficiency = XNumberConstant.create(0.8d);
    private XNumber linearHeatLossCoefficient = XNumberConstant.create(4d);

    @Doc("The linear heat-loss coefficient")
    @Prop(P.lhlc)
    @BindNamedArgument("linear-heat-loss-coefficient")
    public XNumber getLinearHeatLossCoefficient() {
        return linearHeatLossCoefficient;
    }

    public void setLinearHeatLossCoefficient(final XNumber linearHeatLossCoefficient) {
        this.linearHeatLossCoefficient = linearHeatLossCoefficient;
    }

    @Doc("The zero-loss efficiency")
    @Prop(P.zle)
    @BindNamedArgument("zero-loss-efficiency")
    public XNumber getZeroLossEfficiency() {
        return zeroLossEfficiency;
    }

    public void setZeroLossEfficiency(final XNumber zeroLossEfficiency) {
        this.zeroLossEfficiency = zeroLossEfficiency;
    }


    @BindNamedArgument
	@Doc("The area in square meters of the solar collector; this will be present in the evaluation of the cost function as size.m2")
	@Prop(P.area)
	public XNumber getArea() {
		return area;
	}
	public void setArea(final XNumber area) {
		this.area = area;
	}

    @BindNamedArgument
	@Doc("The cost of installing this panel")
	@Prop(P.cost)
	public XNumber getCost() {
		return cost;
	}
	public void setCost(final XNumber cost) {
		this.cost = cost;
	}

    @BindNamedArgument
    @Doc("A synonym for the cost: argument, introduced for consistency with other measures")
	public XNumber getCapex() {
		return cost;
	}
	public void setCapex(final XNumber cost) {
		this.cost = cost;
	}

	@Doc("The volume of solar water cylinder to install, in liters")
	@BindNamedArgument("cylinder-volume")
	@Prop(P.cylinderVolume)
	public XNumber getCylinderVolume() {
		return cylinderVolume;
	}
	public void setCylinderVolume(final XNumber cylinderVolume) {
		this.cylinderVolume = cylinderVolume;
	}
}
