package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XAggregation;
import uk.org.cse.nhm.language.definition.two.selectors.ISetOfHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XAllTheHouses;

@Bind("summarize")
@Doc("Given a group and an aggregation, runs that aggregation on that group and return the result.")
@Category(CategoryType.AGGREGATE_VALUES)
public class XSummaryFunction extends XNumber {

    public static final class P {

        public static final String group = "group";
        public static final String aggregation = "aggregation";
    }

    private ISetOfHouses group = new XAllTheHouses();
    private XAggregation aggregation;

    @Prop(P.group)
    @NotNull(message = "aggregate must always contain a group")
    @Doc("The group of houses which the aggregation will be computed on.")
    @BindNamedArgument
    public ISetOfHouses getGroup() {
        return group;
    }

    public void setGroup(final ISetOfHouses group) {
        this.group = group;
    }

    @Doc("The group of houses which the aggregation will be computed on [equivalent to group:, but added for consistency with other set-related operations].")
    @BindPositionalArgument(1)
    public ISetOfHouses getGroup2() {
        return group;
    }

    public void setGroup2(final ISetOfHouses group) {
        this.group = group;
    }

    @BindPositionalArgument(0)
    @Prop(P.aggregation)
    @NotNull(message = "aggregate must always contain an aggregation")
    @Doc("The specific aggregation which will be run on the group to produce the result.")
    public XAggregation getAggregation() {
        return aggregation;
    }

    public void setAggregation(final XAggregation aggregation) {
        this.aggregation = aggregation;
    }
}
