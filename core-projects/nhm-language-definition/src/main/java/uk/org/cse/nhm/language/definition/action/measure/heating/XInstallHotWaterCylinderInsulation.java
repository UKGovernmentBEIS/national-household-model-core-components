package uk.org.cse.nhm.language.definition.action.measure.heating;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

/**
 * XInstallHotWaterCylinderInsulation.
 *
 * @author richardTiffin
 */
@Bind("measure.hot-water-tank-insulation")
@Doc("Tops up insulation on hot-water-tanks to given levels, either 25mm if factory insulated or 80mm if is a jacket"
        + "Please note that it is possible for insulation to be fitted and energy use then go-up due to edige cases where"
        + "the efficiency of space heating systems is so low the un-insulated cylinder is contributing heat to the dwelling")
@Unsuitability({
    "Dwelling does not have central hot water system with a hot water storage cylinder",
    "Dwelling has a hot water cylinder with factory insulation of less than 25mm",
    "Dwelling has a hot water cylinder with jacket insualtion of less than 80mm"
})
public class XInstallHotWaterCylinderInsulation extends XMeasure {

    private XNumber capex = new XNumberConstant();

    public static final class P {

        public static final String capex = "capex";
    }

    @Prop(P.capex)
    @BindNamedArgument
    @Doc("A function for computing the capital cost of installing the insulation")
    @NotNull(message = "measure.hot-water-tank-insulation must specify a capex.")
    public XNumber getCapex() {
        return capex;
    }

    public void setCapex(final XNumber capex) {
        this.capex = capex;
    }
}
