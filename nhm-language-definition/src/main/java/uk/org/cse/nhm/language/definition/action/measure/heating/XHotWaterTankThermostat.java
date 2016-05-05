package uk.org.cse.nhm.language.definition.action.measure.heating;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

/**
 * XHotWaterTankThermostat.
 *
 * @author richardTiffin
 * @version $Id$
 */

@Bind("measure.hot-water-tank-thermostat")
@Doc("Installs hot water thermostat onto an existing hot-water cylinder")
@Unsuitability({
        "Dwelling does not have central hot water system with a hot water storage cylinder"
})
public class XHotWaterTankThermostat extends XMeasure {

    public static final class P {
        public static final String capex = "capex";
    }

    private XNumber capex = new XNumberConstant();

    @Prop(P.capex)
    @BindNamedArgument
    @Doc("A function for computing the capital cost of installing the heating control")
    @NotNull(message = "measure.hot-water-tank-thermostat must specify a capex.")
    public XNumber getCapex() {
        return capex;
    }

    public void setCapex(final XNumber capex) {
        this.capex = capex;
    }
}
