package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("action.reset-floors")
@Doc({
    "Resets the thermal properties of all the floors in a house one by one, using the supplied functions to compute new values.",
    "When the energy calculator is in SAP 2012 mode, these values will be ignored and the relevant SAP tables will be used instead."
})
@Category(CategoryType.RESETACTIONS)
public class XResetFloors extends XFlaggedDwellingAction {

    public static final class P {

        public static final String uValue = "uValue";
    }

    private XNumber uValue;

    @Prop(P.uValue)
    @BindNamedArgument("u-values")
    @Doc("A function used to compute the new u-value for each floor.")
    public XNumber getuValue() {
        return uValue;
    }

    public void setuValue(final XNumber uValue) {
        this.uValue = uValue;
    }
}
