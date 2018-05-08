package uk.org.cse.nhm.language.definition.fuel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc({
    "Defines the charge for a portion of units consumed. Range is defined as being from the end of the next smallest range up to and including the value of its own to attribute."
})
@Bind("range")
@Category(CategoryType.TARIFFS)
public class XRangeCharge extends XElement {

    public static class P {

        public static final String to = "to";
        public static final String unitPrice = "unitPrice";
    }

    private int to;
    private XNumber unitPrice;

    @BindNamedArgument
    @Prop(P.to)
    @Min(value = 1, message = "range element's 'to' attribute must be at least 1.")
    @Doc("The count of units this range applies up to (inclusive).")
    public int getTo() {
        return to;
    }

    public void setTo(final int to) {
        this.to = to;
    }

    @Prop(P.unitPrice)
    @NotNull(message = "range element must always contain a function which returns a unit price.")
    @Doc("The price per unit for units consumed within this range.")
    @BindNamedArgument("unit-price")
    public XNumber getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final XNumber unitPrice) {
        this.unitPrice = unitPrice;
    }
}
