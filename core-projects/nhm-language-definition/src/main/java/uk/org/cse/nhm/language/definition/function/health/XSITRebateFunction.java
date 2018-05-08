package uk.org.cse.nhm.language.definition.function.health;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Category(CategoryType.HEALTH)
@Doc({"Computes an internal temperature with a fuel bill rebate applied.",
    "",
    "The resulting temperature is a function of the temperature expected without a rebate",
    "and the rebate amount (as a rebate may not lead to increased temperatures if one is already warm enough)."})
@Bind("house.sit-rebate")
public class XSITRebateFunction extends XHouseNumber {

    public static final class P {

        public static final String rebate = "rebate";
        public static final String temperature = "temperature";
    }
    private XNumber rebate = XNumberConstant.create(100d);

    @Prop(P.rebate)
    @BindNamedArgument
    @Doc("The rebate amount, in pounds.")
    public XNumber getRebate() {
        return rebate;
    }

    public void setRebate(final XNumber rebate) {
        this.rebate = rebate;
    }

    private XNumber temperature = new XSITFunction();

    @Prop(P.temperature)
    @BindNamedArgument
    @Doc("The baseline temperature. This should be a value between 14 and 19 degrees.")
    public XNumber getTemperature() {
        return temperature;
    }

    public void setTemperature(final XNumber temperature) {
        this.temperature = temperature;
    }
}
