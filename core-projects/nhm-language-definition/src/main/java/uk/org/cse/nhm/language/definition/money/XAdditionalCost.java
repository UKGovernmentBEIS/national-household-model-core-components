package uk.org.cse.nhm.language.definition.money;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XSumOfCosts;

@Doc("Represents an additional named cost to add to the contained action.")
@SeeAlso({XFullSubsidy.class, XSubsidy.class, XSumOfCosts.class})
@Category(CategoryType.MONEY)
@Bind("measure.with-cost")
public class XAdditionalCost extends XFinanceAction {

    public static final class P {

        public static final String COST = "cost";
    }

    private XNumber cost;

    @Prop(P.COST)
    @BindNamedArgument

    @NotNull(message = "measure.with-cost must contain a function which determines the additional cost to be applied")
    @Doc("The amount to be charged to the house.")
    public XNumber getCost() {
        return cost;
    }

    public void setCost(final XNumber cost) {
        this.cost = cost;
    }
}
