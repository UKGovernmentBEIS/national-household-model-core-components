package uk.org.cse.nhm.language.definition.two.actions;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XAggregation;
import uk.org.cse.nhm.language.definition.two.selectors.ISetOfHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XAllTheHouses;

@Doc({
    "A command to compute and output some aggregate values.",
    "",
    "The aggregate command, when invoked, computes the populations of houses specified with its over: argument,",
    "breaks them into distinct subsets using values of the divide-by: argument, and then takes each population and generates a summary statistic",
    "using the aggregating commands within the aggregate."
})
@Bind("aggregate")
@Category(CategoryType.REPORTING)
public class XAggregateHookAction extends XHookAction implements IHouseContext {

    public static final class P {

        public static final String over = "over";
        public static final String divideBy = "divideBy";
        public static final String values = "values";
    }

    private List<ISetOfHouses> over = createList();
    private List<XFunction> divideBy = new ArrayList<>();
    private List<XAggregation> values = new ArrayList<>();

    private static List<ISetOfHouses> createList() {
        final ArrayList<ISetOfHouses> out = new ArrayList<>();
        final XAllTheHouses xAllTheHouses = new XAllTheHouses();
        xAllTheHouses.setName("all-houses");
        out.add(xAllTheHouses);
        return out;
    }

    @Doc({
        "The over: argument defines the populations for which the aggregations will be computed.",
        "",
        "Each value supplied to over: will be computed whenever the aggregate is producing output.",
        "The resulting sets of houses will each have all the aggregations computed for it."
    })
    @BindNamedArgument
    public List<ISetOfHouses> getOver() {
        return over;
    }

    public void setOver(final List<ISetOfHouses> over) {
        this.over = over;
    }

    @Doc({
        "These functions will be used to cut up the sets in over; for example ",
        "if you use (house.region) and (house.built-form), the aggregations will",
        "be calculated for each set given in the over: argument, broken down by all the distinct combinations of",
        "region and built form."
    })
    @BindNamedArgument("divide-by")
    public List<XFunction> getDivideBy() {
        return divideBy;
    }

    public void setDivideBy(final List<XFunction> divideBy) {
        this.divideBy = divideBy;
    }

    @Doc("These are the aggregations that will be computed. If you give them names, those names will be output as the column names")
    @BindRemainingArguments
    public List<XAggregation> getValues() {
        return values;
    }

    public void setValues(final List<XAggregation> values) {
        this.values = values;
    }
}
