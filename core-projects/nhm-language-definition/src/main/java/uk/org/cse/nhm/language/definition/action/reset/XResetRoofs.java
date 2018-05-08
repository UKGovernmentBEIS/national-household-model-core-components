package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc("A special action which resets the thermal properties of the roofs and ceilings in a house")
@Bind("action.reset-roofs")
@Category(CategoryType.RESETACTIONS)
public class XResetRoofs extends XFlaggedDwellingAction {

    public static final class P {

        public static final String uValue = "uValue";
    }

    private XNumber uValue;

    @Prop(P.uValue)
    @BindNamedArgument("u-values")
    @Doc("A function which will be used to compute the u-value for the external area of each roof.")
    public XNumber getuValue() {
        return uValue;
    }

    public void setuValue(final XNumber uValue) {
        this.uValue = uValue;
    }
}
