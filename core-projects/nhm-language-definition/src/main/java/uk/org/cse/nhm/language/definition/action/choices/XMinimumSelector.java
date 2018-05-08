package uk.org.cse.nhm.language.definition.action.choices;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("select.minimum")
@Doc("Configures its choice to minimise the contained function.")
public class XMinimumSelector extends XChoiceSelector {

    public static final class P {

        public static final String objective = "objective";
    }
    private XNumber objective;

    @Doc("The objective to minimise; the alternative for which this function is minimal will be chosen.")

    @BindPositionalArgument(0)
    @Prop(P.objective)
    @NotNull(message = "select.minimum must contain an objective function.")
    public XNumber getObjective() {
        return objective;
    }

    public void setObjective(final XNumber objective) {
        this.objective = objective;
    }
}
