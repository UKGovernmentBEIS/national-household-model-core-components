package uk.org.cse.nhm.language.definition.action.measure.insulation;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;

@Bind("action.set-wall-u")
@Doc("An action which directly changes the u-value of every exterior wall of a house to a specific value.")
@Unsuitability(alwaysSuitable = true)
public class XModifyUValueAction extends XFlaggedDwellingAction {

    public static final class P {

        public static final String uValue = "uValue";
    }
    private double uValue;

    /**
     * Return the uValue.
     *
     * @return the uValue
     */
    @BindNamedArgument("u-value")
    @Doc("The u-value to set on the wall")
    @Prop(P.uValue)
    @NotNull(message = "action.set-wall-u must define a u-value")
    public double getuValue() {
        return uValue;
    }

    /**
     * Set the uValue.
     *
     * @param uValue the uValue
     */
    public void setuValue(final double uValue) {
        this.uValue = uValue;
    }
}
