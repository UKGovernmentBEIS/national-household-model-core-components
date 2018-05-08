package uk.org.cse.nhm.language.definition.reporting.aggregate;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc(
        value = {
            "Mean produces a value for a set of houses by taking the specified quantity for each house and",
            "adding them up, and then dividing the total by the size of the set"
        }
)
@Bind("aggregate.mean")
public class XMeanAggregation extends XAggregation implements IHouseContext {

    public static final class P {

        public static final String value = "value";
    }
    private XNumber value;

    @Doc("Defines the value whose arithmetic mean will be computed, for each house in the aggregation.")
    @NotNull(message = "aggregate.mean must contain the function or value to be averaged.")
    @BindPositionalArgument(0)
    public XNumber getValue() {
        return value;
    }

    public void setValue(final XNumber value) {
        this.value = value;
    }
}
