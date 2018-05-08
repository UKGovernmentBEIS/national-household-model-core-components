package uk.org.cse.nhm.language.definition.fuel;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc({"A transaction that occurs as part of a tariff. Charges an amount based on a function and pays it to a global account."})
@Bind("charge")
@Category(CategoryType.TARIFFS)
public class XTariffCharge extends XElement {

    public static class P {

        public static final String payee = "payee";
        public static final String VALUE = "value";
    }

    protected String payee;

    private XNumber value;

    @Prop(P.VALUE)

    @BindPositionalArgument(0)
    @Doc("Used to compute the value of the charge")
    @NotNull(message = "a charge must have a function giving the amount to charge as its first unnamed argument")
    public XNumber getValue() {
        return value;
    }

    public void setValue(final XNumber value) {
        this.value = value;
    }

    @BindNamedArgument
    @Prop(P.payee)
    @Doc({"The name of the account which will receive the money paid by houses using this tariff.", "If this account does not exist, it will be created.", "If no account is specified, the name of the tariff will be used."})
    public String getPayee() {
        return payee;
    }

    public void setPayee(final String payee) {
        this.payee = payee;
    }
}
