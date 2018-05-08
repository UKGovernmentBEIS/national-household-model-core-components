package uk.org.cse.nhm.language.definition.reporting;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XAggregation;
import uk.org.cse.nhm.language.definition.two.actions.XAggregateHookAction;

@Doc({"An aggregated probe, which will compute an aggregation before and after applying another action.",
    "The behaviour is analogous to the <code>aggregate</code> element, except that instead of specifying a group, the aggregation will be computed",
    "for three groups: the group of all exposed houses, before the inner action is applied, and then again for",
    "those exposed houses which were suitable for the action and those which were unsuitable after the action has been applied.",
    "As with <code>aggregate</code>, you can divide these groups into exclusive subsets using the divide-by argument."
})
@Bind("probe.aggregate")
@SeeAlso(XAggregateHookAction.class)
@Category(CategoryType.REPORTING)
public class XAggregateActionProbe extends XAction {

    public static final class P {

        public static final String delegate = "delegate";
        public static final String aggregations = "aggregations";
        public static final String divisions = "divisions";
    }

    private List<XAggregation> aggregations = new ArrayList<>();
    private List<XFunction> divisions = new ArrayList<>();
    private XAction delegate;

    @Doc("An action to perform")
    @NotNull(message = "probe.aggregate must have an action inside it")
    @BindPositionalArgument(0)
    public XAction getDelegate() {
        return delegate;
    }

    public void setDelegate(final XAction delegate) {
        this.delegate = delegate;
    }

    @Doc("These are the aggregations that will be computed. If you give them names, those names will be output as the column names")
    @BindNamedArgument("capture")
    public List<XAggregation> getAggregations() {
        return aggregations;
    }

    public void setAggregations(final List<XAggregation> aggregations) {
        this.aggregations = aggregations;
    }

    @Doc("A set of values to divide the affected houses by")
    @BindNamedArgument("divide-by")
    public List<XFunction> getDivisions() {
        return divisions;
    }

    public void setDivisions(final List<XFunction> divisions) {
        this.divisions = divisions;
    }
}
