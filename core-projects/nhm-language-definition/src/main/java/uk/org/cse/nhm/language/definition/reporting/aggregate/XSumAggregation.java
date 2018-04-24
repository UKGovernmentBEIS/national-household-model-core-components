package uk.org.cse.nhm.language.definition.reporting.aggregate;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.function.num.XNumber;


@Bind("aggregate.sum")
@Doc(
		value = {
			"Sum produces a value for a set of houses by taking the specified quantity for each house and",
			"adding them up."
		}
	)
public class XSumAggregation extends XAggregation implements IHouseContext {
	public static final class P {
		public static final String value = "value";
	}
	private XNumber value;

	
	@BindPositionalArgument(0)
	@Doc("Defines the value which will be summed, for each house in the aggregation.")
	@NotNull(message = "aggregate.sum must contain the function or value to be summed.")
	public XNumber getValue() {
		return value;
	}

	public void setValue(final XNumber value) {
		this.value = value;
	}
}
