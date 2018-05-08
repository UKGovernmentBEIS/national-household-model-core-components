package uk.org.cse.nhm.language.definition.reporting.aggregate;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

@Doc(
        {
            "Computes another aggregation only for the subset of input houses which matches a given condition."
        }
)
@Bind("aggregate.where")
public class XWhereAggregation extends XAggregation {

    public static final class P {

        public static final String test = "test";
        public static final String aggregation = "aggregation";
    }
    private XBoolean test;
    private XAggregation aggregation = new XCountAggregation();

    @NotNull(message = "aggregate.where requires a test")
    @Doc("The test to use for reducing the set of houses to calculate over")
    @BindPositionalArgument(0)
    public XBoolean getTest() {
        return test;
    }

    public void setTest(final XBoolean test) {
        this.test = test;
    }

    @Doc("The aggregation to use when calculating the value")
    @BindPositionalArgument(1)
    public XAggregation getAggregation() {
        return aggregation;
    }

    public void setAggregation(final XAggregation aggregation) {
        this.aggregation = aggregation;
    }
}
