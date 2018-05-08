package uk.org.cse.nhm.language.definition.context.calibration;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.context.XContextParameter;
import uk.org.cse.nhm.language.definition.function.num.XEnergyUse;
import uk.org.cse.nhm.language.validate.contents.ForbidChild;

@Doc(
        {
            "Defines a calibrated version of the energy calculator.",
            "Contains a set of equations, one for each fuel type, which will be",
            "used to compute the energy result for that fuel for each house.",
            "For your convenience, you can reuse the standard energy calculator",
            "by using house.energy-use within the definition here (so you do not have",
            "to write your own energy calculator out by hand).",
            "Has no effect if the energy calculator is in SAP 2012 mode.",}
)
@SeeAlso(XEnergyUse.class)
@Bind("context.calibration")
/* we do not want to allow calibrated energy to determine
 * calibrated energy. We also disallow the use of measures
 * in here, because measures seem like an odd thing to
 * use in this place.
 */
@ForbidChild({ICalibratedEnergyFunction.class, XAction.class})
@Category(CategoryType.CALIBRATION)
public class XCalibrationContext extends XContextParameter {

    public static class P {

        public final static String rules = "rules";
        public static final String always_true = "alwaysTrue";
    }
    private List<XCalibrationRule> rules = new ArrayList<>();

    @Prop(P.rules)
    @BindRemainingArguments
    @Doc(
            {
                "A sequence of rules to use for calibrating the energy calculator.",
                "Because the calibrated energy result for the house depends on these rules,",
                "No rule may be defined in terms of the calibrated result; consequently many",
                "energy-related functions are disallowed within the rules. For example, you cannot",
                "define the calibration in terms of the NHM's prediction of fuel use, because fuel",
                "use depends on calibrated energy use.",
                "You can access the <emphasis>uncalibrated</emphasis> energy use, using house.energy-use",
                "with its calibrated argument set to false, so the calibrated result can depend arbitrarily",
                "on the uncalibrated."
            }
    )
    public List<XCalibrationRule> getRules() {
        return rules;
    }

    public void setRules(final List<XCalibrationRule> rules) {
        this.rules = rules;
    }

    /**
     * This is a hack to allow us to stick true in the adapting scope.
     *
     * @return
     */
    @Prop(P.always_true)
    public boolean isAlwaysTrue() {
        return true;
    }
}
