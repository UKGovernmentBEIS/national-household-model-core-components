package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.reporting.XTransactionReport;

@Bind("house.sum-transactions")
@Doc({
    "The sum of all the transactions the dwelling has entered into during the simulation.",
    "",
    "When a house installs a measure, pays its fuel bill, is given a subsidy or a loan, and so on, the receipt or spending of money is recorded in its ledger.",
    "This function produces the sum of all the transactions that are in a house's ledger, or optionally a subset which have certain tags or are to a certain counterparty."
})
@SeeAlso({XHouseBalance.class, XTransactionReport.class})
@Category(CategoryType.MONEY)
public class XSumTransactions extends XHouseNumber {

    public static final class P {

        public static final String tags = "tags";
        public static final String counterparty = "counterparty";
    }

    private Glob counterparty;
    private List<Glob> tags = new ArrayList<>();

    @BindNamedArgument
    @Prop(P.counterparty)
    @Doc("Only transactions with this counterparty will be included in the sum.")
    public Glob getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(final Glob counterparty) {
        this.counterparty = counterparty;
    }

    @BindNamedArgument
    @Prop(P.tags)
    @Doc({"Specifies that only transactions which match these patterns will be included.",
        "",
        "For a list of the tags which are automatically added by the model, and what commands produce them, see the Transaction Tags section of the manual."
    })
    public List<Glob> getTags() {
        return tags;
    }

    public void setTags(final List<Glob> tags) {
        this.tags = tags;
    }
}
