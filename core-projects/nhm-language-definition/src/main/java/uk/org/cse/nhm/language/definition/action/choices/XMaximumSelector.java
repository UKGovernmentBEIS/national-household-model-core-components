package uk.org.cse.nhm.language.definition.action.choices;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;


@Bind("select.maximum")
@Doc("Configures its choice to maximise the contained function.")
public class XMaximumSelector extends XChoiceSelector {
	public static class P {
		public static final String objective = "objective";
	}
	
	private XNumber objective;

	
	@BindPositionalArgument(0)
	@Prop(P.objective)
	@Doc("The objective to maximise; the alternative for which this function is maximal will be chosen.")
	@NotNull(message = "select.maximum must contain an objective function.")
	public XNumber getObjective() {
		return objective;
	}

	public void setObjective(final XNumber objective) {
		this.objective = objective;
	}
}
