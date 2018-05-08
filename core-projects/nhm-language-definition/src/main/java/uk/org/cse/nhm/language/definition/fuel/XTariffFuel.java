package uk.org.cse.nhm.language.definition.fuel;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.enums.XFuelType;

@Doc("Defines the charging structure for a particular fuel within a tariff.")
@Bind("fuel")
@Category(CategoryType.TARIFFS)
public class XTariffFuel extends XElement {

    public static class P {

        public static final String fuel = "fuel";
        public static final String components = "components";
    }

    private XFuelType fuel;
    private List<XTariffCharge> components = new ArrayList<XTariffCharge>();

    @BindNamedArgument("type")
    @Prop(P.fuel)
    @NotNull(message = "fuel element must specify which fuel is being charged for.")
    @Doc({"The fuel to charge for."})
    public XFuelType getFuel() {
        return fuel;
    }

    public void setFuel(final XFuelType fuel) {
        this.fuel = fuel;
    }

    @BindRemainingArguments

    @Prop(P.components)
    @Doc({"The component parts which determine the charge for this fuel. Each component produces a separate transaction.",
        "The components will be applied in the order they are written, so a later component may use the result of an earlier one."})
    @Size(min = 1, message = "fuel must contain at least one component.")
    public List<XTariffCharge> getComponents() {
        return components;
    }

    public void setComponents(final List<XTariffCharge> components) {
        this.components = components;
    }
}
