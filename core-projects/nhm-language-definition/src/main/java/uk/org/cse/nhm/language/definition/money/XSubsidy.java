package uk.org.cse.nhm.language.definition.money;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ProducesTags;
import uk.org.cse.nhm.language.definition.ProducesTags.Tag;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("finance.with-subsidy")
@Doc({
    "Subsidises another action.",
    "This action first performs the action written inside of it. It then evalutes its subsidy function, and pays the result to the house.",
    "No subsidy will be applied if the subsidy function returns a value which is less than or equal to 0."
})
@ProducesTags(value = {
    @Tag(
            value = TransactionTags.Internal.subsidy,
            detail = "When this action is applied, it will produce a single transaction with this tag.")})
@Category(CategoryType.MONEY)
@SeeAlso({XFullSubsidy.class, XAdditionalCost.class})
public class XSubsidy extends XFinanceAction {

    public static class P {

        public static final String SUBSIDY = "subsidy";
    }

    private XNumber subsidy;

    @Prop(P.SUBSIDY)

    @BindNamedArgument
    @NotNull(message = "finance.with-subsidy must contain a function which determines the subsidy to be granted.")
    @Doc("The amount to be paid to the house.")
    public XNumber getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(final XNumber subsidy) {
        this.subsidy = subsidy;
    }
}
