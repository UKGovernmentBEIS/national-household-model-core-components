package uk.org.cse.nhm.language.definition.batch.inputs.combinators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.Min;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;


@Bind("repetitions")
@Doc({
	"The repetitions element is intended for running replicas of scenarios",
	"which contain random behaviour,",
	"whose independent variables are being sampled using another input generator",
	" (the single child of repetitions).",
	"Every set of variables produced by the child generator is repeated count times",
	"with a uniformly random integer variable named $seed appended to the set.",
	"This is akin to holding your independent variables constant, and performing multiple",
	"trials in order to understand the distribution caused by internal random behaviour,",
	"rather than by the independent variables.",
	"As such, it is usually appropriate to use this as the outermost input generator in a",
	"batch run definition, when the scenario being batched is expected to exhibit random behaviour."
})
public class XRepetitions extends XInputs {
	public static final String SEED = "$seed";

	public static final class P {
		public static final String independentVariables = "independentVariables";
		public static final String count = "count";
	}
	
	private int count = 10;
	private XInputs independentVariables;
	
	
	@BindPositionalArgument(0)
	@Doc("The source of the independent variable sets for which the random repetitions are to be performed.")
	public XInputs getIndependentVariables() {
		return independentVariables;
	}

	public void setIndependentVariables(final XInputs independentVariables) {
		this.independentVariables = independentVariables;
	}

	
@BindNamedArgument
	@Doc("The number of repetitions to perform")
	@Min(value=1, message="you may not perform fewer than one repetition of a batch")
	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	@Override
	public boolean hasBound() {
		if(getIndependentVariables() == null) {
			return true;
		} else {
			return getIndependentVariables().hasBound();
		}
	}

	@Override
	@Prop(XInputs.P.PLACEHOLDERS)
	public List<String> getPlaceholders() {
		if(getIndependentVariables() == null) {
			return Collections.singletonList(SEED);
		} else {
			final List<String> result = new ArrayList<>();
			result.addAll(getIndependentVariables().getPlaceholders());
			result.add(SEED);
			return result;
		}
	}
}
