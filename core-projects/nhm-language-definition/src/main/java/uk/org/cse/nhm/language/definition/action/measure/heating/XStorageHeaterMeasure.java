package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("measure.storage-heater")
@Doc("Replaces a house's primary heating system with some new storage heaters")
public class XStorageHeaterMeasure extends XHeatingMeasure {

    public static final class P {

        public static final String type = "type";
        public static final String responsiveness = "responsiveness";
    }

    @Doc({
        "Describes a type of storage heater.",
        "This controls the default responsiveness of the storage heater per SAP table 4a",
        "if you have not specified a responsiveness function.",
        "Integrated storage heaters also use peak-time electricity as well."
    })
    public enum XStorageHeaterType {
        OldLargeVolume,
        Slimline,
        Convector,
        Fan,
        SlimlineCelect,
        ConvectorCelect,
        FanCelect,
        Integrated
    }

    private XStorageHeaterType type = XStorageHeaterType.SlimlineCelect;
    private XNumber responsiveness;

    @Doc("The type of storage heater to install")
    @BindNamedArgument
    @Prop(P.type)
    public XStorageHeaterType getType() {
        return type;
    }

    public void setType(final XStorageHeaterType type) {
        this.type = type;
    }

    @Doc({
        "Responsiveness is determined by SAP 2012, table 4a (Category 7).",
        "Optionally, you can provide a function here to override the responsiveness lookup.",
        "The responsiveness is a proportion which is used in determining the mean temperature",
        "in a house. A responsiveness of zero causes a higher mean temperature, which increases",
        "energy use.",
        "Overriding the responsiveness will have no effect in SAP 2012 mode."

    })
    @BindNamedArgument
    @Prop(P.responsiveness)
    public XNumber getResponsiveness() {
        return responsiveness;
    }

    public void setResponsiveness(final XNumber responsiveness) {
        this.responsiveness = responsiveness;
    }
}
