package uk.org.cse.nhm.language.definition.action.measure.hotwater;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("measure.point-of-use-hot-water")
@Doc({"Installs an electric point-of-use domestic hot water supply.",
    "This is a standalone device which is not connected to any hot water pipes.",
    "This device is alays installed as a backup, and will only be used if there are no other functioning water heaters in the house."})
@Obsolete(version = "6.4.0", reason = "This measure is no longer necessary. If there are no water heaters present in a house, we now assume electric water heating as specified in SAP Appendix A.")
public class XPointOfUseDHWMeasure extends XMeasure {

    public static class P {

        public static final String CAPEX = "capex";
        public static final String REPLACE_EXISTING = "replaceExisting";
    }

    private XNumber capex;
    private boolean replaceExisting = false;

    @BindNamedArgument
    @Prop(P.CAPEX)
    @Doc("The capital cost of installing the point-of-use domestic water heater.")
    @NotNull(message = "measure.point-of-use-hot-water must always declare a capex function")
    public XNumber getCapex() {
        return capex;
    }

    public void setCapex(final XNumber capex) {
        this.capex = capex;
    }

    @BindNamedArgument("replace-existing")
    @Prop(P.REPLACE_EXISTING)
    @Doc({"If this is set to true, any existing secondary water heater will also be removed and replaced with the new point-of-use dhw.",
        "If this is set to false, the new point-of-use dhw will only be installed in houses which do not already have a secondary water heater."
    })
    public boolean getReplaceExisting() {
        return replaceExisting;
    }

    public void setReplaceExisting(final boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }
}
