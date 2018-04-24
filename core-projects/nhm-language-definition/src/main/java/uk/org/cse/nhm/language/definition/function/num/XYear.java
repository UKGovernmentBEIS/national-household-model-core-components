package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;

@Bind("sim.year")
@Doc("The current simulation year.")
public class XYear extends XNumber {
	private XForesightLevel foresight = null;

	@BindNamedArgument
	@Doc({"The foresight level required to predict the change to this year in a prediction function like predict-sum.",
		"If unset, this is determined from the foresight level where the function is being used; for example, within",
		"the tariff definition this will have foresight level Tariffs."})
	public XForesightLevel getForesight() {
		return foresight;
	}

	public void setForesight(final XForesightLevel foresight) {
		this.foresight = foresight;
	}
}
