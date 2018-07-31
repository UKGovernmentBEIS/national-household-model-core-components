package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.measure.heating.XHeatingControlMeasure.XHeatingControlType;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

@Bind("house.has-heating-control")
@Doc({ "Checks whether any of the heating systems in a house have the given control type.", "",
		"For appliance thermostat, this includes secondary heating systems." })
public class XHasHeatingControl extends XHouseBoolean {

    public static final class P {

        public static final String type = "type";
    }

    private XHeatingControlType type = XHeatingControlType.Programmer;

    @Prop(P.type)
    @BindNamedArgument
    @Doc("The type of heating control to check for")
    public XHeatingControlType getType() {
        return type;
    }

    public void setType(final XHeatingControlType type) {
        this.type = type;
    }
}
