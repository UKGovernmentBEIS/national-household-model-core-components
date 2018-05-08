package uk.org.cse.nhm.language.definition.action.measure.renewable;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;
import uk.org.cse.nhm.language.definition.function.num.XSizingFunction;

@Bind("measure.solar-photovoltaic")
@Doc({
    "Installs solar photovoltaic panels. These will generate electricity, which may be used to meet the dwelling's needs.",
    "If the electricity generated is greater than the needs of the house, then it will be sold back to the grid.",
    "Panels are assumed to be south facing and tilted at 30 degrees (pi / 6 radians).",
    "Electricity generated is calculated according to SAP 2012 formula (M1).",})
@Unsuitability({
    "dwelling is a mid or ground-floor flat (top floor flats may have solar)",
    "dwelling has a thatched roof",
    "dwelling has an existing solar photovoltaic system"
})
public class XSolarPhotovoltaicMeasure extends XMeasure {

    public static final class P {

        public static final String sizing = "sizing";
        public static final String capex = "capex";
        public static final String ownUseProportion = "ownUseProportion";
    }

    private XSizingFunction sizing;
    private XNumber capex;
    private XNumber ownUseProportion = defaultOwnUseProportion();

    private static XNumber defaultOwnUseProportion() {
        final XNumberConstant result = new XNumberConstant();
        result.setValue(0.5);
        return result;
    }

    @Prop(P.sizing)
    @BindNamedArgument("size")
    @NotNull(message = "measure.solar-photovoltaic must always specify a sizing function.")
    @Doc("A function to compute the peak power in kW of the installed solar panels. This is used to compute its actual output.")
    public XSizingFunction getSizing() {
        return sizing;
    }

    public void setSizing(final XSizingFunction sizing) {
        this.sizing = sizing;
    }

    @NotNull(message = "measure.solar-photovoltaic must specify a capex")
    @Prop(P.capex)
    @Doc({
        "The function which will be used to determine the capital expenditure spent on the installation.",
        "This may be a function of the peak power installed (size.kw), or any other house properties."
    })
    @BindNamedArgument
    public XNumber getCapex() {
        return capex;
    }

    public void setCapex(final XNumber capex) {
        this.capex = capex;
    }

    @Prop(P.ownUseProportion)
    @BindNamedArgument("own-use-proportion")
    @Doc({"The proportion of electricity that is generated when the house is consuming electricity.",
        "This fraction of the generated amount will be taken from the electricity import requirement",
        "from the grid.",
        "Any remaining generation will be exported to the grid, potentially at a different price.",
        "If this proportion is high enough to cause either peak or offpeak electricity demand to become negative, some extra electricity will be exported instead.",
        "Setting this has no effect in SAP 2012 mode, when it will always be 0.5."
    })
    public XNumber getOwnUseProportion() {
        return ownUseProportion;
    }

    public void setOwnUseProportion(final XNumber ownUseProportion) {
        this.ownUseProportion = ownUseProportion;
    }
}
