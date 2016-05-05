package uk.org.cse.nhm.language.definition.batch;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.sexp.Node;


@Bind("batch")
@Category(CategoryType.BATCH)
@Doc(value = {
		"Designates a scenario as a batch run scenario.",
		"The batch element contains some inputs and a scenario.",
		"The inputs will be either distributions, tables, ranges, or combinations of those. Each input creates values for one or more placeholder names.",
		"The attributes in the scenario which are to be varied should be set with a value which matches a $placeholder.",
		"The inputs will be used to determine how many times the scenario should be varied.",
		"Each time the scenario is run, values will be drawn from the inputs and inserted into the attributes in the scenario which have the matching placeholder name."
		})
public class XBatch extends XElement {
	public static final class P {
		public static final String INPUTS = "inputs";
		public static final String SCENARIO = "scenario";
		public static final String SEED = "seed";
	}

	private XInputs inputs;
	private Node scenario;
	private long seed = 0;

	@BindNamedArgument("inputs")
	@Prop(P.INPUTS)
	@Doc("The inputs which will be used to vary the scenario.")
	@NotNull(message = "batch element must contain some kind of input to tell it how to vary the scenario.")
	@BatchInputsBounded
	public XInputs getInputs() {
		return inputs;
	}

	public void setInputs(final XInputs inputs) {
		this.inputs = inputs;
	}

	@BindNamedArgument("scenario")
	
	@Doc("The scenario to be run.")
	@Prop(P.SCENARIO)
	@NotNull(message = "batch element must contain a scenario.")
	public Node getScenario() {
		return scenario;
	}

	public void setScenario(final Node scenario) {
		this.scenario = scenario;
	}

	
	@BindNamedArgument
	@Prop(P.SEED)
	@Doc("The seed which will be used by any random distributions which are specified as batch inputs. (Note that this is distinct from the seed on the scenario element, and has no direct effect on the scenario)")
	public long getSeed() {
		return seed;
	}

	public void setSeed(final long seed) {
		this.seed = seed;
	}
}
