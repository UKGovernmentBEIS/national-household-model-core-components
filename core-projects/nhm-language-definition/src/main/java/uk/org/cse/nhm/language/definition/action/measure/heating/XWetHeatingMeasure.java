package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

public abstract class XWetHeatingMeasure extends XHeatingMeasure {

    public static final class P {

        public static final String WET_HEATING_CAPEX = "wetHeatingCapex";
    }

    private XNumber wetHeatingCapex;

    @Prop(P.WET_HEATING_CAPEX)
    @BindNamedArgument("system-capex")
    @Doc(
            {"Contains a function to compute the capex if a wet central heating system was installed."}
    )
    public XNumber getWetHeatingCapex() {
        return wetHeatingCapex;
    }

    public void setWetHeatingCapex(final XNumber wetHeatingCapex) {
        this.wetHeatingCapex = wetHeatingCapex;
    }
}
