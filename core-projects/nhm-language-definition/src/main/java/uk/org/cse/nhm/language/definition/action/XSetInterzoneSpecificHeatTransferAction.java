package uk.org.cse.nhm.language.definition.action;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc({
    "Set the interzone specific heat transfer for a dwelling.",
    "This is the average rate in Watts per Kelvin temperature difference that heat moves from Zone 1 to Zone 2."
})
@Bind("action.set-interzone-specific-heat-transfer")
@Category(CategoryType.RESETACTIONS)
public class XSetInterzoneSpecificHeatTransferAction extends XFlaggedDwellingAction {

    public static final class P {

        public static final String interzoneSpecificHeatTransfer = "interzoneSpecificHeatTransfer";
    }

    XNumber interzoneSpecificHeatTransfer;

    @Doc({
        "This should be a number, or a function which returns a number.",
        "The result should always be greater than or equal to 0."
    })
    @BindPositionalArgument(0)
    @Prop(P.interzoneSpecificHeatTransfer)
    @NotNull(message = "action.set-interzone-specific-heat-transfer must specify a number or a number function to use to compute the heat loss.")
    public XNumber getInterzoneSpecificHeatTransfer() {
        return interzoneSpecificHeatTransfer;
    }

    public void setInterzoneSpecificHeatTransfer(XNumber interzoneSpecificHeatTransfer) {
        this.interzoneSpecificHeatTransfer = interzoneSpecificHeatTransfer;
    }
}
