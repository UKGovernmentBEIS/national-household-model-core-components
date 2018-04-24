package uk.org.cse.nhm.language.definition.function.npv;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;


@Bind("future-value")
@Doc(
		{
			"Predicts the sum of the values of a given function over a certain number of years.",
			"The function to be summed is repeatedly computed in a hypothesis where the date appears to be",
			"advancing. Any time-sensitive inputs to the function will be predicted, so long as they are covered by",
			"the foresight levels given in the predict argument.",
			"This function can be used to compute (for example) a geometrically discounted present value by multiplying",
			"the target function by a discount factor."
		}
	)
@Category(CategoryType.MONEY)
@SeeAlso({XExponentialDiscount.class, XHyperbolicDiscount.class})
public class XFutureValue extends XHouseNumber implements ICalibratedEnergyFunction {
	public static final class P {
		public static final String horizon = "horizon";
		public static final String predict = "predict";
		public static final String value = "value";
	}
	private XNumber horizon = XNumberConstant.create(10);
	private List<XForesightLevel> predict = new ArrayList<>(EnumSet.complementOf(EnumSet.of(XForesightLevel.Never)));
	private XNumber value;
	
	@Prop(P.horizon)
	@BindNamedArgument
	@Doc({"This function will be evaluated and rounded down to determine the horizon in years from now over which the discounted sum is to be computed.",
		"If the horizon evaluates to 1, the value function will be evaluated once, on the present date; if two, it will be evaluated twice, on the present date and one year hence, and so on.",
		"If the rounded value is zero or negative, the prediction will be zero (i.e. you cannot compute a net present regret)."	
	})
	public XNumber getHorizon() {
		return horizon;
	}
	public void setHorizon(final XNumber horizon) {
		this.horizon = horizon;
	}
	
	@Prop(P.predict)
	@BindNamedArgument
	@Doc({
		"The categories of time-sensitive value which should be predictable within the sum; for example, if this list contains only Tariffs, any time-sensitive functions",
		"directly used in the house's tariff will be predictable, but no other time-sensitive functions will be predictable.",
		"This list can never contain the foresight level None (because that level can never be predicted), and it always implicity contains foresight level All, for the converse reason."
		})
	@ForesightRules(message = "the predict: argument of predict-sum must never have foresight level None, as that level cannot be predicted")
	public List<XForesightLevel> getPredict() {
		return predict;
	}
	public void setPredict(final List<XForesightLevel> predict) {
		this.predict = predict;
	}
	
	@Prop(P.value)
	@BindPositionalArgument(0)
	@Doc({
		"This is the function to be computed; it will be computed as many times as the horizon function indicates, and the result of the prediction will be the sum of the resulting values.",
		"Each time it is computed, the date will appear to have changed in those parts of the scenario that are considered predictable, and so any date-dependent values will also change.",
		"Note that things to do with the immediate state of affairs (like the current net cost) are invisible inside the prediction, so (for example) (future-value (cost.sum)) is always zero."
	})
	public XNumber getValue() {
		return value;
	}
	public void setValue(final XNumber value) {
		this.value = value;
	}
}
