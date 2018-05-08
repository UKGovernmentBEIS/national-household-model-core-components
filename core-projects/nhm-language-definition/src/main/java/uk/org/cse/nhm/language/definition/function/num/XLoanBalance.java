package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Category(CategoryType.MONEY)
public abstract class XLoanBalance extends XHouseNumber {

    public static final class P {

        public static final String tagged = "tagged";
        public static final String creditor = "creditor";
    }

    private String tagged;
    private String creditor;

    @BindNamedArgument("tagged")
    @Doc("A tag to filter on. Only loans which have this tag will be included in the calculation.")
    @Prop(P.tagged)
    public String getTagged() {
        return tagged;
    }

    public void setTagged(final String tagged) {
        this.tagged = tagged;
    }

    @BindNamedArgument("creditor")
    @Doc("A loan provider. Only loans owed to this creditor will be included in the calculation.")
    @Prop(P.creditor)
    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(final String creditor) {
        this.creditor = creditor;
    }

}
